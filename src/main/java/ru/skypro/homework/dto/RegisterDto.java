package ru.skypro.homework.dto;

import lombok.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterDto {

    @Min(4)
    @Max(32)
    private String username;

    @Min(8)
    @Max(16)
    private String password;

    @Min(2)
    @Max(16)
    private String firstName;

    @Min(2)
    @Max(16)
    private String lastName;

    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    @NotNull
    private Role role;
}
