package ru.skypro.homework.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "comment")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer pk;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id")
    private User author;

    @NotNull
    @Min(0)
    private Long createdAt;

    @NotNull
    @Min(8)
    @Max(64)
    private String text;
}
