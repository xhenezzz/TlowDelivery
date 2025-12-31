package kelbetov.aidyn.demo.service;

import kelbetov.aidyn.demo.dto.UpdateUserDto;
import kelbetov.aidyn.demo.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto getCurrentUser();

    UserDto updateCurrentUser(UpdateUserDto dto);

    void deleteCurrentUser();
}
