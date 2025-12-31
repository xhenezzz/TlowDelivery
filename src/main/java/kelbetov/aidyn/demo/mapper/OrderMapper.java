package kelbetov.aidyn.demo.mapper;

import kelbetov.aidyn.demo.dto.OrderResponseDto;
import kelbetov.aidyn.demo.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "courier.id", target = "courierId")
    OrderResponseDto toDto(Order order);
}
