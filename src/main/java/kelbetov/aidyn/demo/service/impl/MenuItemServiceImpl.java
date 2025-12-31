package kelbetov.aidyn.demo.service.impl;

import kelbetov.aidyn.demo.dto.MenuItemRequestDto;
import kelbetov.aidyn.demo.dto.MenuItemResponseDto;
import kelbetov.aidyn.demo.entity.MenuItem;
import kelbetov.aidyn.demo.entity.Restaurant;
import kelbetov.aidyn.demo.entity.User;
import kelbetov.aidyn.demo.repo.MenuItemRepository;
import kelbetov.aidyn.demo.repo.RestaurantRepository;
import kelbetov.aidyn.demo.repo.UserRepository;
import kelbetov.aidyn.demo.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Override
    public MenuItemResponseDto create(Long restaurantId, MenuItemRequestDto dto) {
        User owner = getCurrentUser();
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Ресторан не найден!"));

        if (!restaurant.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("Отказано в доступе");
        }

        MenuItem menuItem = new MenuItem();
        menuItem.setName(dto.getName());
        menuItem.setPrice(dto.getPrice());
        menuItem.setRestaurant(restaurant);

        return map(menuItemRepository.save(menuItem));
    }

    @Override
    public List<MenuItemResponseDto> getAll(Long restaurantId) {
        return menuItemRepository.findAllByRestaurantId(restaurantId)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public MenuItemResponseDto update(Long restaurantId, Long itemId, MenuItemRequestDto dto) {
        User owner = getCurrentUser();
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Не найдено блюдо в меню!"));

        if (!menuItem.getRestaurant().getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("Отказано в доступе");
        }

        menuItem.setName(dto.getName());
        menuItem.setPrice(dto.getPrice());

        return map(menuItemRepository.save(menuItem));
    }

    @Override
    public void delete(Long restaurantId, Long itemId) {
        User owner = getCurrentUser();
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Не найдено блюдо в меню!"));

        if (!menuItem.getRestaurant().getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("Отказано в доступе");
        }

        menuItemRepository.delete(menuItem);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow();
    }

    private MenuItemResponseDto map(MenuItem item) {
        return MenuItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .restaurantId(item.getRestaurant().getId())
                .build();
    }
}
