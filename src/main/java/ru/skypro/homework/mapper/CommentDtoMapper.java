package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentDtoMapper {

    @Mapping(source = "author", target = "author.id")
    @Mapping(source = "image", target = "author.image")
    @Mapping(source = "authorFirstName", target = "author.firstName")
    Comment toEntity(CommentDto commentDto);

    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "author.image", target = "image")
    @Mapping(source = "author.firstName", target = "authorFirstName")
    CommentDto toDto(Comment comment);
}
