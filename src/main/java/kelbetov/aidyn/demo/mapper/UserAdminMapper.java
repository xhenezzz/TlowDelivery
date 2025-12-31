package kelbetov.aidyn.demo.mapper;

import kelbetov.aidyn.demo.dto.UserDto;
import kelbetov.aidyn.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAdminMapper {
    UserDto toDto(User user);
}
