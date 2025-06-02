package ru.skypro.homework.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreateOrUpdateAdDto {

    @NotBlank
    @Size(min = 4, max = 32)
    private String title;

    @NotNull
    @Min(value = 0)
    @Max(value = 10000000)
    private Integer price;

    @NotBlank
    @Size(min = 8, max = 64)
    private String description;
}
