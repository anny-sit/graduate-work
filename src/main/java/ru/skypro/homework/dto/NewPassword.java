package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPassword {

    @Min(8)
    @Max(16)
    private String currentPassword;

    @Min(8)
    @Max(16)
    private String newPassword;
}
