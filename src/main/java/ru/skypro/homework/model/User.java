package ru.skypro.homework.model;

import lombok.*;
import ru.skypro.homework.dto.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "user_table")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Min(4)
    @Max(32)
    private String email;

    @NotNull
    @Min(2)
    @Max(16)
    private String firstName;

    @NotNull
    @Min(2)
    @Max(16)
    private String lastName;

    @NotNull
    @Pattern(regexp = "^\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}$")
    private String phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @NotNull
    private String image;

    @NotNull
    @Max(16)
    @Min(8)
    private String password;

}
