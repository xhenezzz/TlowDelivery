package kelbetov.aidyn.demo.service.impl;

import kelbetov.aidyn.demo.dto.CreateOrderDto;
import kelbetov.aidyn.demo.dto.OrderResponseDto;
import kelbetov.aidyn.demo.dto.UpdateOrderStatusDto;
import kelbetov.aidyn.demo.entity.*;
import kelbetov.aidyn.demo.mapper.OrderMapper;
import kelbetov.aidyn.demo.repo.OrderRepository;
import kelbetov.aidyn.demo.repo.RestaurantRepository;
import kelbetov.aidyn.demo.repo.UserRepository;
import kelbetov.aidyn.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final OrderMapper mapper;

    @Override
    public OrderResponseDto create(CreateOrderDto dto) {
        User client = getCurrentUser();

        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow();

        Order order = new Order();
        order.setClient(client);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.CREATED);

        Order saved = orderRepository.save(order);
        return mapper.toDto(saved);
    }

    @Override
    public OrderResponseDto getById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        return mapper.toDto(order);
    }

    @Override
    public OrderResponseDto updateStatus(Long id, UpdateOrderStatusDto dto) {
        User user = getCurrentUser();
        Order order = orderRepository.findById(id).orElseThrow();

        boolean isRestaurantOwner =
                order.getRestaurant().getOwner().getId().equals(user.getId());

        boolean isCourier =
                order.getCourier() != null &&
                        order.getCourier().getId().equals(user.getId());

        if (!isRestaurantOwner && !isCourier) {
            throw new RuntimeException("Отказано в доступе");
        }

        order.setStatus(dto.getStatus());
        Order saved = orderRepository.save(order);

        return mapper.toDto(saved);
    }

    @Override
    public List<OrderResponseDto> getMyOrders() {
        User user = getCurrentUser();

        return orderRepository.findAllByClientId(user.getId())
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<OrderResponseDto> getAvailableOrders() {
        User courier = getCurrentUser();

        if (courier.getRole() != Role.COURIER) {
            throw new RuntimeException("Только курьеры могут видеть доступные заказы!");
        }

        return orderRepository
                .findAllByStatusAndCourierIsNull(OrderStatus.READY)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public OrderResponseDto assignOrder(Long orderId) {
        User courier = getCurrentUser();

        if (courier.getRole() != Role.COURIER) {
            throw new RuntimeException("Только курьеры могут брать заказ в работу!");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Заказ не найден!"));

        if (order.getCourier() != null) {
            throw new RuntimeException("Заказ уже доставляется!");
        }

        if (order.getStatus() != OrderStatus.READY) {
            throw new RuntimeException("Заказ еще не готов к отправке!");
        }

        order.setCourier(courier);
        order.setStatus(OrderStatus.DELIVERING);

        Order saved = orderRepository.save(order);
        return mapper.toDto(saved);
    }

    @Override
    public List<OrderResponseDto> getMyDeliveries() {
        User courier = getCurrentUser();

        if (courier.getRole() != Role.COURIER) {
            throw new RuntimeException("Отказано в доступе!");
        }

        return orderRepository.findAllByCourierId(courier.getId())
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email).orElseThrow();
    }
}
