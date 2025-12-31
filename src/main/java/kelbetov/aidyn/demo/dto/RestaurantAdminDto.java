package kelbetov.aidyn.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantAdminDto {
    private Long id;
    private String name;
    private String description;
    private Long ownerId;
}
