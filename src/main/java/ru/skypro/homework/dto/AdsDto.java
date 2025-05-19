package ru.skypro.homework.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdsDto {

    private Integer count;
    private List<AdDto> results;
}
