package kelbetov.aidyn.demo.repo;

import kelbetov.aidyn.demo.entity.Order;
import kelbetov.aidyn.demo.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByClientId(Long clientId);

    List<Order> findAllByRestaurantOwnerId(Long ownerId);

    List<Order> findAllByCourierId(Long courierId);

    List<Order> findAllByStatusAndCourierIsNull(OrderStatus status);
}
