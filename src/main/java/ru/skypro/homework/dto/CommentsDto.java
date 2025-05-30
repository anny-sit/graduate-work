package ru.skypro.homework.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentsDto {
    private Integer count;
    private List<CommentDto> results;
}
