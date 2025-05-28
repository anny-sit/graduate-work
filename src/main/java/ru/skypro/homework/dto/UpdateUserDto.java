package ru.skypro.homework.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateUserDto {

    @NotBlank(message = "First name must not be blank")
    @Size(min = 3, max = 10)
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Size(min = 3, max = 10)
    private String lastName;

    @NotBlank(message = "Phone must not be blank")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;
}
