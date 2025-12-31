package kelbetov.aidyn.demo.controller;

import kelbetov.aidyn.demo.dto.CreateReviewDto;
import kelbetov.aidyn.demo.dto.RestaurantRequestDto;
import kelbetov.aidyn.demo.dto.RestaurantResponseDto;
import kelbetov.aidyn.demo.dto.ReviewResponseDto;
import kelbetov.aidyn.demo.service.RestaurantService;
import kelbetov.aidyn.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @PostMapping("/reviews/{id}")
    public ReviewResponseDto create(
            @PathVariable Long id,
            @RequestBody CreateReviewDto dto
    ) {
        return reviewService.create(id, dto);
    }

    @GetMapping("/reviews/{id}")
    public List<ReviewResponseDto> getAll(@PathVariable Long id) {
        return reviewService.getByRestaurant(id);
    }

    @PostMapping
    public ResponseEntity<RestaurantResponseDto> create(
            @RequestBody RestaurantRequestDto dto
    ) {
        return ResponseEntity.ok(restaurantService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDto>> getAll() {
        return ResponseEntity.ok(restaurantService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> update(
            @PathVariable Long id,
            @RequestBody RestaurantRequestDto dto
    ) {
        return ResponseEntity.ok(restaurantService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restaurantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
