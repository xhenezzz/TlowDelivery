package kelbetov.aidyn.demo.service.impl;

import kelbetov.aidyn.demo.dto.RestaurantAdminDto;
import kelbetov.aidyn.demo.dto.UserDto;
import kelbetov.aidyn.demo.mapper.RestaurantAdminMapper;
import kelbetov.aidyn.demo.mapper.UserAdminMapper;
import kelbetov.aidyn.demo.repo.RestaurantRepository;
import kelbetov.aidyn.demo.repo.UserRepository;
import kelbetov.aidyn.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserAdminMapper userMapper;
    private final RestaurantAdminMapper restaurantMapper;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public List<RestaurantAdminDto> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::toDto)
                .toList();
    }
}
