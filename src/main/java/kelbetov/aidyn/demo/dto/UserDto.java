package kelbetov.aidyn.demo.dto;

import kelbetov.aidyn.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long Id;
    private String username;
    private String surname;
    private String email;
    private Role role;
}
