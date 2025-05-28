package ru.skypro.homework.dto;

import lombok.*;

import jakarta.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateOrUpdateCommentDto {

    @NotBlank
    @Size(min = 8, max = 64)
    private String text;
}
