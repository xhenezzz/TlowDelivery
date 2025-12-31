package kelbetov.aidyn.demo.controller;

import kelbetov.aidyn.demo.dto.RestaurantAdminDto;
import kelbetov.aidyn.demo.dto.UserDto;
import kelbetov.aidyn.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/restaurants")
    public List<RestaurantAdminDto> getRestaurants() {
        return adminService.getAllRestaurants();
    }
}
