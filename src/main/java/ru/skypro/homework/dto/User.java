package ru.skypro.homework.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;

    @NotNull
    private String role;

    private String image;
}
