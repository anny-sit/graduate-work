package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {


    private String author;
    private String authorImage;
    private String authorFirstName;

    @Min(0)
    private Integer createdAt;

    private Integer pk;
    private String text;
}
