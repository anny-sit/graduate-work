package ru.skypro.homework.model;

import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
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
    @JoinColumn(name = "author_id")
    private User author;

    @Column(nullable = false, length = 255)
    private String image;

    @NotNull
    @Max(10000000)
    @Min(0)
    private Integer price;

    @NotNull
    @Size(max = 32, min = 4)
    @Column(length = 32)
    private String title;

    @NotNull
    @Size(max = 64, min = 8)
    @Column(length = 64)
    private String description;

}
