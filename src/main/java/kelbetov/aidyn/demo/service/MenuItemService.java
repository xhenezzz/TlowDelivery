package kelbetov.aidyn.demo.service;

import kelbetov.aidyn.demo.dto.MenuItemRequestDto;
import kelbetov.aidyn.demo.dto.MenuItemResponseDto;

import java.util.List;

public interface MenuItemService {

    MenuItemResponseDto create(Long restaurantId, MenuItemRequestDto dto);

    List<MenuItemResponseDto> getAll(Long restaurantId);

    MenuItemResponseDto update(Long restaurantId, Long itemId, MenuItemRequestDto dto);

    void delete(Long restaurantId, Long itemId);
}
