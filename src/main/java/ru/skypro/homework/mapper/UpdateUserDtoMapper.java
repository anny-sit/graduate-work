package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.model.User;

@Mapper(componentModel = "spring")
public interface UpdateUserDtoMapper {

    User toEntity(UpdateUserDto updateUserDto);

    UpdateUserDto toDto(User user);
}
//нужно ли смаппить все остальные поля, чтобы не создавать нового юзера?
