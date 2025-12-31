package kelbetov.aidyn.demo.controller;

import kelbetov.aidyn.demo.dto.CreateOrderDto;
import kelbetov.aidyn.demo.dto.OrderResponseDto;
import kelbetov.aidyn.demo.dto.UpdateOrderStatusDto;
import kelbetov.aidyn.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDto> create(
            @RequestBody CreateOrderDto dto
    ) {
        return ResponseEntity.ok(orderService.create(dto));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<OrderResponseDto> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusDto dto
    ) {
        return ResponseEntity.ok(orderService.updateStatus(id, dto));
    }

    @GetMapping("/users/me/orders")
    public ResponseEntity<List<OrderResponseDto>> myOrders() {
        return ResponseEntity.ok(orderService.getMyOrders());
    }

    @GetMapping("/orders/available")
    public ResponseEntity<List<OrderResponseDto>> availableOrders() {
        return ResponseEntity.ok(orderService.getAvailableOrders());
    }

    @PutMapping("/orders/{id}/assign")
    public ResponseEntity<OrderResponseDto> assignOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.assignOrder(id));
    }

    @GetMapping("/users/me/deliveries")
    public ResponseEntity<List<OrderResponseDto>> myDeliveries() {
        return ResponseEntity.ok(orderService.getMyDeliveries());
    }
}

