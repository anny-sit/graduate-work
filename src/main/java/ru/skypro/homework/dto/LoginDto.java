package ru.skypro.homework.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDto {

    @NotBlank
    @Size(min = 4, max = 32)
    private String username;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;

}
