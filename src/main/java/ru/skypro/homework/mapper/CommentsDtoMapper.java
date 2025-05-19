package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CommentDtoMapper.class})
public interface CommentsDtoMapper {

    @Mapping(source = "comments", target = "results")
    @Mapping(target = "count", expression = "java(comments.size())")
    CommentsDto toDto(List<Comment> comments);

    @Mapping(source = "results", target = ".")
    List<Comment> toEntityList(CommentsDto commentsDto);
}
