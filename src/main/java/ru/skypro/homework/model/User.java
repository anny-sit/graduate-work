package ru.skypro.homework.model;

import jakarta.validation.constraints.*;
import lombok.*;
import ru.skypro.homework.dto.Role;

import jakarta.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 32, min = 4)
    @Column(length = 32)
    private String username;

    @NotNull
    @Size(max = 16, min = 2)
    @Column(length = 16)
    private String firstName;

    @NotNull
    @Size(max = 16, min = 2)
    @Column(length = 16)
    private String lastName;

    @NotNull
    @Pattern(regexp = "^\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}$")
    private String phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(length = 255)
    private String image;

    @NotNull
    private String password;

}
