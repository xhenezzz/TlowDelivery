package kelbetov.aidyn.demo.dto;

import kelbetov.aidyn.demo.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusDto {
    private OrderStatus status;
}