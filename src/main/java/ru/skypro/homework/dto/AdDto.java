package ru.skypro.homework.dto;

import jakarta.validation.constraints.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AdDto {

    @NotNull
    private Long author;

    @Size(max = 255)
    private String image;

    private Long pk;

    @NotNull
    @Min(value = 0)
    @Max(value = 10000000)
    private Integer price;

    @NotBlank
    @Size(min = 4, max = 32)
    private String title;

}
