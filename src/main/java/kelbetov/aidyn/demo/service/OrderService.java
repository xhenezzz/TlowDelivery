package kelbetov.aidyn.demo.service;

import kelbetov.aidyn.demo.dto.CreateOrderDto;
import kelbetov.aidyn.demo.dto.OrderResponseDto;
import kelbetov.aidyn.demo.dto.UpdateOrderStatusDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto create(CreateOrderDto dto);

    OrderResponseDto getById(Long id);

    OrderResponseDto updateStatus(Long id, UpdateOrderStatusDto dto);

    List<OrderResponseDto> getMyOrders();

    List<OrderResponseDto> getAvailableOrders();

    OrderResponseDto assignOrder(Long orderId);

    List<OrderResponseDto> getMyDeliveries();
}