package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.model.User;

@Mapper(componentModel = "spring")
public interface RegisterDtoMapper {

    @Mapping(source = "username", target = "email")
    User toEntity(RegisterDto registerDto);

    @Mapping(source = "email", target = "username")
    RegisterDto toDto(User user);

}
