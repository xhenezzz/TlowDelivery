package kelbetov.aidyn.demo.service;

import kelbetov.aidyn.demo.dto.CreateReviewDto;
import kelbetov.aidyn.demo.dto.ReviewResponseDto;

import java.util.List;

public interface ReviewService {

    ReviewResponseDto create(Long restaurantId, CreateReviewDto dto);

    List<ReviewResponseDto> getByRestaurant(Long restaurantId);
}
