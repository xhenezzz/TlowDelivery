package kelbetov.aidyn.demo.service;

import kelbetov.aidyn.demo.dto.RestaurantRequestDto;
import kelbetov.aidyn.demo.dto.RestaurantResponseDto;

import java.util.List;

public interface RestaurantService {

    RestaurantResponseDto create(RestaurantRequestDto dto);

    List<RestaurantResponseDto> getAll();

    RestaurantResponseDto getById(Long id);

    RestaurantResponseDto update(Long id, RestaurantRequestDto dto);

    void delete(Long id);
}
