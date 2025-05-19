package ru.skypro.homework.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;

    @NotNull
    private Role role;

    private String image;
}
//стоит ли дто в рекорды переписать?