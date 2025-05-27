package ru.skypro.homework.dto;

import lombok.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateOrUpdateCommentDto {

    @Min(8)
    @Max(64)
    private String text;
}
