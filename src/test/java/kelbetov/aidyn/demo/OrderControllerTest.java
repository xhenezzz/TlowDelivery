package kelbetov.aidyn.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import kelbetov.aidyn.demo.controller.OrderController;
import kelbetov.aidyn.demo.dto.CreateOrderDto;
import kelbetov.aidyn.demo.dto.OrderResponseDto;
import kelbetov.aidyn.demo.dto.UpdateOrderStatusDto;
import kelbetov.aidyn.demo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void createOrder_Success() throws Exception {
        CreateOrderDto dto = new CreateOrderDto(); // установи нужные поля
        OrderResponseDto response = new OrderResponseDto(); // установи нужные поля

        when(orderService.create(any(CreateOrderDto.class))).thenReturn(response);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void getById_Success() throws Exception {
        OrderResponseDto response = new OrderResponseDto();

        when(orderService.getById(anyLong())).thenReturn(response);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void updateStatus_Success() throws Exception {
        UpdateOrderStatusDto dto = new UpdateOrderStatusDto();
        OrderResponseDto response = new OrderResponseDto();

        when(orderService.updateStatus(anyLong(), any(UpdateOrderStatusDto.class))).thenReturn(response);

        mockMvc.perform(put("/orders/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void myOrders_Success() throws Exception {
        when(orderService.getMyOrders()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users/me/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void availableOrders_Success() throws Exception {
        when(orderService.getAvailableOrders()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/orders/available"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void assignOrder_Success() throws Exception {
        OrderResponseDto response = new OrderResponseDto();

        when(orderService.assignOrder(anyLong())).thenReturn(response);

        mockMvc.perform(put("/orders/1/assign"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void myDeliveries_Success() throws Exception {
        when(orderService.getMyDeliveries()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users/me/deliveries"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
