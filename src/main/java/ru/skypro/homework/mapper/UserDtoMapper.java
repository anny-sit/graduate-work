package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    User toEntity(UserDto userDto);

    UserDto toDto(User user);

}
