package kelbetov.aidyn.demo.controller;

import kelbetov.aidyn.demo.dto.UpdateUserDto;
import kelbetov.aidyn.demo.dto.UserDto;
import kelbetov.aidyn.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PutMapping("/me")
    public ResponseEntity<UserDto> updateMe(@RequestBody UpdateUserDto dto) {
        return ResponseEntity.ok(userService.updateCurrentUser(dto));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe() {
        userService.deleteCurrentUser();
        return ResponseEntity.noContent().build();
    }
}
