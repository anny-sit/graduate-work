package ru.skypro.homework.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdDto {

    private Integer author;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;

}
