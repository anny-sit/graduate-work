package ru.skypro.homework.dto;

import jakarta.validation.constraints.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdDto {

    @NotNull
    private Integer author;

    @Size(max = 255)
    private String image;

    private Integer pk;

    @NotNull
    @Min(value = 0)
    @Max(value = 10000000)
    private Integer price;

    @NotBlank
    @Size(min = 4, max = 32)
    private String title;

}
