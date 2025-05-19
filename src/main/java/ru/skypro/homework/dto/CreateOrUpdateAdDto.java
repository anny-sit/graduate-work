package ru.skypro.homework.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateOrUpdateAdDto {

    private String title;
    private Integer price;
    private String description;
}
