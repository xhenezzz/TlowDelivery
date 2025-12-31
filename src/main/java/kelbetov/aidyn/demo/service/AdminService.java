package kelbetov.aidyn.demo.service;

import kelbetov.aidyn.demo.dto.RestaurantAdminDto;
import kelbetov.aidyn.demo.dto.UserDto;

import java.util.List;

public interface AdminService {

    List<UserDto> getAllUsers();

    List<RestaurantAdminDto> getAllRestaurants();
}
