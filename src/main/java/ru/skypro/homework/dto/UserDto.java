package ru.skypro.homework.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDto {

    private Long id;

    @NotBlank(message = "Email must not be blank")
    @Size(min = 4, max = 32)
    private String username;

    @NotBlank(message = "First name must not be blank")
    @Size(min = 2, max = 16)
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Size(min = 2, max = 16)
    private String lastName;

    @NotBlank(message = "Phone must not be blank")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    @NotNull
    private Role role;

    @Size(max = 255)
    private String image;
}
