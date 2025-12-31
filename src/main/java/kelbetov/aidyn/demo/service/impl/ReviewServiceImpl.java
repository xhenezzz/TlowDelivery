package kelbetov.aidyn.demo.service.impl;

import kelbetov.aidyn.demo.dto.CreateReviewDto;
import kelbetov.aidyn.demo.dto.ReviewResponseDto;
import kelbetov.aidyn.demo.entity.Restaurant;
import kelbetov.aidyn.demo.entity.Review;
import kelbetov.aidyn.demo.entity.User;
import kelbetov.aidyn.demo.mapper.ReviewMapper;
import kelbetov.aidyn.demo.repo.RestaurantRepository;
import kelbetov.aidyn.demo.repo.ReviewRepository;
import kelbetov.aidyn.demo.repo.UserRepository;
import kelbetov.aidyn.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final ReviewMapper mapper;

    @Override
    public ReviewResponseDto create(Long restaurantId, CreateReviewDto dto) {
        User client = getCurrentUser();

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Ресторан не найден!"));

        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setRestaurant(restaurant);
        review.setClient(client);

        Review saved = reviewRepository.save(review);
        return mapper.toDto(saved);
    }

    @Override
    public List<ReviewResponseDto> getByRestaurant(Long restaurantId) {
        return reviewRepository.findAllByRestaurantId(restaurantId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
    }
}
