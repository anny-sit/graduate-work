package ru.skypro.homework.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "ad_table")
@Entity
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer pk;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id")
    private User author;

    @NotNull
    private String image;

    @NotNull
    @Max(10000000)
    @Min(0)
    private Integer price;

    @NotNull
    @Max(32)
    @Min(4)
    private String title;

    @NotNull
    @Max(64)
    @Min(8)
    private String description;
}
