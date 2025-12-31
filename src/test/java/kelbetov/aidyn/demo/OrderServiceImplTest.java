package kelbetov.aidyn.demo;

import kelbetov.aidyn.demo.dto.CreateOrderDto;
import kelbetov.aidyn.demo.dto.OrderResponseDto;
import kelbetov.aidyn.demo.dto.UpdateOrderStatusDto;
import kelbetov.aidyn.demo.entity.*;
import kelbetov.aidyn.demo.mapper.OrderMapper;
import kelbetov.aidyn.demo.repo.OrderRepository;
import kelbetov.aidyn.demo.repo.RestaurantRepository;
import kelbetov.aidyn.demo.repo.UserRepository;
import kelbetov.aidyn.demo.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderMapper mapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    private User testUser;
    private Restaurant testRestaurant;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getName()).thenReturn("user@test.com");

        testUser = new User();
        testUser.setId(1L);
        testUser.setRole(Role.USER);
        testUser.setEmail("user@test.com");

        testRestaurant = new Restaurant();
        testRestaurant.setId(1L);
        testRestaurant.setOwner(testUser);

        lenient().when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(testUser));
    }


    @Test
    void createOrder_Success() {
        CreateOrderDto dto = new CreateOrderDto();
        dto.setRestaurantId(1L);

        Order order = new Order();
        order.setClient(testUser);
        order.setRestaurant(testRestaurant);
        order.setStatus(OrderStatus.READY);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponseDto responseDto = new OrderResponseDto();
        when(mapper.toDto(order)).thenReturn(responseDto);

        OrderResponseDto result = orderService.create(dto);

        assertNotNull(result);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(mapper, times(1)).toDto(order);
    }

    @Test
    void getById_Success() {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(mapper.toDto(order)).thenReturn(new OrderResponseDto());

        OrderResponseDto result = orderService.getById(1L);
        assertNotNull(result);
    }

    @Test
    void updateStatus_Throws_WhenNotOwnerOrCourier() {
        User otherUser = new User();
        otherUser.setId(2L);
        otherUser.setRole(Role.USER);
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(otherUser));

        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.CREATED);
        order.setRestaurant(testRestaurant);
        order.setCourier(null);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        UpdateOrderStatusDto dto = new UpdateOrderStatusDto();
        dto.setStatus(OrderStatus.READY);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> orderService.updateStatus(1L, dto));

        assertEquals("Отказано в доступе", exception.getMessage());
    }

    @Test
    void getMyOrders_ReturnsList() {
        Order order = new Order();
        when(orderRepository.findAllByClientId(1L)).thenReturn(List.of(order));
        when(mapper.toDto(order)).thenReturn(new OrderResponseDto());

        List<OrderResponseDto> result = orderService.getMyOrders();
        assertEquals(1, result.size());
    }

    @Test
    void getAvailableOrders_Throws_WhenNotCourier() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> orderService.getAvailableOrders());
        assertEquals("Только курьеры могут видеть доступные заказы!", exception.getMessage());
    }

    @Test
    void assignOrder_Success() {
        User courier = new User();
        courier.setId(1L);
        courier.setRole(Role.USER);
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(courier));

        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.READY);
        order.setCourier(null);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(mapper.toDto(order)).thenReturn(new OrderResponseDto());

        OrderResponseDto result = orderService.assignOrder(1L);
        assertNotNull(result);
        assertEquals(OrderStatus.DELIVERING, order.getStatus());
        assertEquals(courier, order.getCourier());
    }
}
