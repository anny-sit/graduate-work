package ru.skypro.homework.dto;

import lombok.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDto {

    @Min(4)
    @Max(32)
    private String username;

    @Min(8)
    @Max(16)
    private String password;

}
