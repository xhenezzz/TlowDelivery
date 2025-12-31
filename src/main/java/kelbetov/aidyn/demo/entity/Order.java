package kelbetov.aidyn.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User client;
    @ManyToOne
    private Restaurant restaurant;
    @ManyToOne
    private User courier;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
