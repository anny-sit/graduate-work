package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.LoginDto;
import ru.skypro.homework.model.User;

@Mapper(componentModel = "spring")
public interface LoginDtoMapper {

    @Mapping(source = "email", target = "username")
    LoginDto toDto(User user);

    @Mapping(source = "username", target = "email")
    User toEntity(LoginDto loginDto);

}
