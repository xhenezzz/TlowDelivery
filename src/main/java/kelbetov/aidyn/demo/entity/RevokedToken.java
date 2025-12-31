package kelbetov.aidyn.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RevokedToken {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 512, nullable = false, unique = true)
    private String token;

    private Date revokedAt;
}

