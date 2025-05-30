package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdMapper {
    @Mapping(target = "image", source = "image", qualifiedByName = "imageToString")
    @Mapping(target = "author", source = "author.id")
    AdDto toDto(Ad ad);

    List<AdDto> toDtoList(List<Ad> ads);

    @Mapping(target = "image", source = "image", qualifiedByName = "imageToString")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "email", source = "author.email")
    ExtendedAdDto toExtendedDto(Ad ad);

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ad createOrUpdateAdDtoToEntity(CreateOrUpdateAdDto dto);

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    void updateAdFromDto(CreateOrUpdateAdDto dto, @MappingTarget Ad ad);

    @Named("imageToString")
    default String imageToString(Object image) {
        if (image instanceof Image img) {
            return img.getSource();
        } else if (image instanceof String str) {
            return str;
        }
        return null;
    }

}
