package kelbetov.aidyn.demo.mapper;

import kelbetov.aidyn.demo.dto.ReviewResponseDto;
import kelbetov.aidyn.demo.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "client.id", target = "clientId")
    ReviewResponseDto toDto(Review review);
}
