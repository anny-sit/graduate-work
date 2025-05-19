package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.model.Ad;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AdDtoMapper.class})
public interface AdsDtoMapper {

    @Mapping(source = "ads", target = "results")
    @Mapping(target = "count", expression = "java(ads.size())")
    AdsDto toDto(List<Ad> ads);

    @Mapping(source = "results", target = ".")
    List<Ad> toEntityList(AdsDto adsDto);
}
