package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.model.Ad;

@Mapper(componentModel = "spring")
public interface AdDtoMapper {

    @Mapping(source = "author.id", target = "author")
    AdDto toDto(Ad ad);

    @Mapping(source = "author", target = "author.id")
    Ad toEntity(AdDto adDto);
}
