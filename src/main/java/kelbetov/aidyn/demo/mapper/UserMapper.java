package kelbetov.aidyn.demo.mapper;


import kelbetov.aidyn.demo.dto.RegisterUserDto;
import kelbetov.aidyn.demo.dto.UserDto;
import kelbetov.aidyn.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User entity);

    User toEntity(UserDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", constant = "RESTAURANT")
    User fromRegisterDto(RegisterUserDto dto);
}

