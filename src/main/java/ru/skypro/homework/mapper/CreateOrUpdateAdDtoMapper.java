package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.model.Ad;

@Mapper(componentModel = "spring")
public interface CreateOrUpdateAdDtoMapper {

    CreateOrUpdateAdDto toDto(Ad ad);

    Ad toEntity(CreateOrUpdateAdDto createOrUpdateAdDto);
}
