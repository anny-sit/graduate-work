package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @Min(4)
    @Max(32)
    private String username;

    @Min(8)
    @Max(16)
    private String password;

}
