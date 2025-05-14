package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateAd {

    @Min(4)
    @Max(32)
    private String title;

    @Min(0)
    @Max(10000000)
    private Long price;

    @Min(8)
    @Max(64)
    private String description;
}
