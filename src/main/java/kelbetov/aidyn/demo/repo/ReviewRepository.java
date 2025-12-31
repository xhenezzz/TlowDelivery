package kelbetov.aidyn.demo.repo;

import kelbetov.aidyn.demo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByRestaurantId(Long restaurantId);
}
