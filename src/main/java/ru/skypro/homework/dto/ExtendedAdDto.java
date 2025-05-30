package ru.skypro.homework.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExtendedAdDto {

    private Integer pk;

    @NotBlank
    @Size(min = 2, max = 16)
    private String authorFirstName;

    @NotBlank
    @Size(min = 2, max = 16)
    private String authorLastName;

    @NotBlank
    @Size(min = 8, max = 64)
    private String description;

    @NotBlank
    @Size(min = 4, max = 32)
    private String email;

    @Size(max = 255)
    private String image;

    @NotBlank(message = "Phone must not be blank")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    @NotNull(message = "Price must not be null")
    @Min(value = 0, message = "Price must be at least 0")
    @Max(value = 10000000, message = "Price must not exceed 10,000,000")
    private Integer price;

    @NotBlank(message = "Title must not be blank")
    @Size(min = 4, max = 32, message = "Title must be between 4 and 32 characters")
    private String title;
}
