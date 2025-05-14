package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ad {


    private String author;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;

}
