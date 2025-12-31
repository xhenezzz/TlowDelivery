package kelbetov.aidyn.demo.service.impl;

import kelbetov.aidyn.demo.dto.AuthResponseDto;
import kelbetov.aidyn.demo.dto.AuthUserDto;
import kelbetov.aidyn.demo.dto.RegisterUserDto;
import kelbetov.aidyn.demo.entity.RevokedToken;
import kelbetov.aidyn.demo.entity.User;
import kelbetov.aidyn.demo.mapper.UserMapper;
import kelbetov.aidyn.demo.repo.RevokedTokenRepository;
import kelbetov.aidyn.demo.repo.UserRepository;
import kelbetov.aidyn.demo.service.AuthService;
import kelbetov.aidyn.demo.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RevokedTokenRepository revokedTokenRepository;


    @Override
    public AuthResponseDto register(RegisterUserDto dto) {
        if(emailExistet(dto.getEmail())){
            throw new RuntimeException("Такой пользователь уже есть");
        }

        User user = userMapper.fromRegisterDto(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        String jwt = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                ));
                return new AuthResponseDto(jwt);
    }

    private boolean emailExistet(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public AuthResponseDto auth(AuthUserDto authUserDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authUserDto.getEmail(), authUserDto.getPassword()));
        User user = userRepository.findByEmail(authUserDto.getEmail()).orElseThrow();

        String jwt = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                ));
        return new AuthResponseDto(jwt);
    }

    @Override
    public void logout(String token) {
        RevokedToken revoked = new RevokedToken();
        revoked.setToken(token);
        revoked.setRevokedAt(new Date());

        revokedTokenRepository.save(revoked);
    }

    @Override
    public boolean isTokenRevoked(String token) {
        return revokedTokenRepository.existsByToken(token);
    }
}
