package kelbetov.aidyn.demo.service.impl;

import kelbetov.aidyn.demo.dto.RestaurantRequestDto;
import kelbetov.aidyn.demo.dto.RestaurantResponseDto;
import kelbetov.aidyn.demo.entity.Restaurant;
import kelbetov.aidyn.demo.entity.User;
import kelbetov.aidyn.demo.repo.RestaurantRepository;
import kelbetov.aidyn.demo.repo.UserRepository;
import kelbetov.aidyn.demo.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Override
    public RestaurantResponseDto create(RestaurantRequestDto dto) {
        User owner = getCurrentUser();

        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setDescription(dto.getDescription());
        restaurant.setOwner(owner);

        return map(restaurantRepository.save(restaurant));
    }

    @Override
    public List<RestaurantResponseDto> getAll() {
        return restaurantRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public RestaurantResponseDto getById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ресторан не найден!"));
        return map(restaurant);
    }

    @Override
    public RestaurantResponseDto update(Long id, RestaurantRequestDto dto) {
        User owner = getCurrentUser();

        Restaurant restaurant = restaurantRepository
                .findByIdAndOwnerId(id, owner.getId())
                .orElseThrow(() -> new RuntimeException("Отказано в доступе"));

        restaurant.setName(dto.getName());
        restaurant.setDescription(dto.getDescription());

        return map(restaurant);
    }

    @Override
    public void delete(Long id) {
        User owner = getCurrentUser();

        Restaurant restaurant = restaurantRepository
                .findByIdAndOwnerId(id, owner.getId())
                .orElseThrow(() -> new RuntimeException("Отказано в доступе"));

        restaurantRepository.delete(restaurant);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow();
    }

    private RestaurantResponseDto map(Restaurant r) {
        return RestaurantResponseDto.builder()
                .id(r.getId())
                .name(r.getName())
                .description(r.getDescription())
                .ownerId(r.getOwner().getId())
                .build();
    }
}
