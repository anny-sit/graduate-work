package ru.skypro.homework.dto;

import lombok.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewPasswordDto {

    @Min(8)
    @Max(16)
    private String currentPassword;

    @Min(8)
    @Max(16)
    private String newPassword;
}
