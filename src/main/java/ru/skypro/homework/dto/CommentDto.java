package ru.skypro.homework.dto;

import lombok.*;

import jakarta.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto {


    private Integer author;
    private String image;
    private String authorFirstName;


    @Min(0)
    private Long createdAt;

    private Integer pk;
    private String text;
}
