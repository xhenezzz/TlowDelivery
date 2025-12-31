package kelbetov.aidyn.demo.dto;
import kelbetov.aidyn.demo.entity.OrderStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Long id;
    private Long clientId;
    private Long restaurantId;
    private Long courierId;
    private OrderStatus status;
}
