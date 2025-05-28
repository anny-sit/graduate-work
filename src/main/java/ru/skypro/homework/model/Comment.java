package ru.skypro.homework.model;

import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "comments")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer pk;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    @Size(max = 64, min = 8)
    @Column(length = 64)
    private String text;

    @Transient
    public String getImage() {
        return author != null ? author.getImage() : null;
    }

}
