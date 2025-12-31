package kelbetov.aidyn.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long restaurantId;
}
