package kelbetov.aidyn.demo.controller;

import kelbetov.aidyn.demo.dto.MenuItemRequestDto;
import kelbetov.aidyn.demo.dto.MenuItemResponseDto;
import kelbetov.aidyn.demo.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/menu")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemResponseDto> create(
            @PathVariable Long restaurantId,
            @RequestBody MenuItemRequestDto dto
    ) {
        return ResponseEntity.ok(menuItemService.create(restaurantId, dto));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDto>> getAll(
            @PathVariable Long restaurantId
    ) {
        return ResponseEntity.ok(menuItemService.getAll(restaurantId));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<MenuItemResponseDto> update(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId,
            @RequestBody MenuItemRequestDto dto
    ) {
        return ResponseEntity.ok(menuItemService.update(restaurantId, itemId, dto));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId
    ) {
        menuItemService.delete(restaurantId, itemId);
        return ResponseEntity.noContent().build();
    }
}
