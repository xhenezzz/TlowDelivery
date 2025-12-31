package kelbetov.aidyn.demo.mapper;

import kelbetov.aidyn.demo.dto.RestaurantAdminDto;
import kelbetov.aidyn.demo.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantAdminMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    RestaurantAdminDto toDto(Restaurant restaurant);
}
