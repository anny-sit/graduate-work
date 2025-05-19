package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.model.Comment;

@Mapper(componentModel = "spring")
public interface CreateOrUpdateCommentDtoMapper {

    CreateOrUpdateCommentDto toDto(Comment comment);

    Comment toEntity(CreateOrUpdateCommentDto createOrUpdateCommentDto);

}
