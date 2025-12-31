package kelbetov.aidyn.demo.controller;

import kelbetov.aidyn.demo.dto.AuthResponseDto;
import kelbetov.aidyn.demo.dto.AuthUserDto;
import kelbetov.aidyn.demo.dto.RegisterUserDto;
import kelbetov.aidyn.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterUserDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthUserDto dto){
        return ResponseEntity.ok(authService.auth(dto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }

        String token = authHeader.substring(7);
        authService.logout(token);
        return ResponseEntity.ok().build();
    }

}
