package kelbetov.aidyn.demo.repo;

import kelbetov.aidyn.demo.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByIdAndOwnerId(Long id, Long ownerId);
}
