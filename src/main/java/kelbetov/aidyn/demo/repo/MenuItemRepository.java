package kelbetov.aidyn.demo.repo;

import kelbetov.aidyn.demo.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findAllByRestaurantId(Long restaurantId);

    Optional<MenuItem> findByIdAndRestaurantId(Long itemId, Long restaurantId);
}