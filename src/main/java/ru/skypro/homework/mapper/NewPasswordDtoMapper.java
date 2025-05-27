package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.model.User;

@Mapper(componentModel = "spring")
public interface NewPasswordDtoMapper {

    @Mapping(source = "newPassword", target = "password")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "image", ignore = true)
    User toEntity(NewPasswordDto newPasswordDto);
}
