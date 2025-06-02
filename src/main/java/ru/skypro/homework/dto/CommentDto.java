package ru.skypro.homework.dto;

import lombok.*;

import jakarta.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto {

    @NotNull
    private Long author;

    @Size(max = 255)
    private String authorImage;

    @NotBlank
    @Size(max = 16)
    private String authorFirstName;

    @NotNull
    @Min(value = 0)
    private Long createdAt;

    private Long pk;

    @NotBlank
    @Size(min = 8, max = 64)
    private String text;
}
