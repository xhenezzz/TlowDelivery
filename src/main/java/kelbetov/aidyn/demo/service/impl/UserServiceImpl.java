package kelbetov.aidyn.demo.service.impl;

import kelbetov.aidyn.demo.dto.UpdateUserDto;
import kelbetov.aidyn.demo.dto.UserDto;
import kelbetov.aidyn.demo.mapper.UserMapper;
import kelbetov.aidyn.demo.repo.UserRepository;
import kelbetov.aidyn.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        kelbetov.aidyn.demo.entity.User user = userRepository.findByEmail(email).orElseThrow();
        return new User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    @Override
    public UserDto getCurrentUser() {
        kelbetov.aidyn.demo.entity.User user = getAuthenticatedUser();
        return userMapper.toDto(user);
    }


    @Override
    public UserDto updateCurrentUser(UpdateUserDto dto) {
        kelbetov.aidyn.demo.entity.User user = getAuthenticatedUser();

        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getSurname() != null) {
            user.setSurname(dto.getSurname());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public void deleteCurrentUser() {
        kelbetov.aidyn.demo.entity.User user = getAuthenticatedUser();
        userRepository.delete(user);
    }

    private kelbetov.aidyn.demo.entity.User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
    }


}
