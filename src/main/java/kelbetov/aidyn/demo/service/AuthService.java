package kelbetov.aidyn.demo.service;

import kelbetov.aidyn.demo.dto.AuthResponseDto;
import kelbetov.aidyn.demo.dto.AuthUserDto;
import kelbetov.aidyn.demo.dto.RegisterUserDto;

public interface AuthService {
    AuthResponseDto register(RegisterUserDto registerUserDto);
    AuthResponseDto auth(AuthUserDto authUserDto);
    void logout(String token);
    boolean isTokenRevoked(String token);
}
