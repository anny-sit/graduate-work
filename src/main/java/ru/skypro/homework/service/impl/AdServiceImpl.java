package ru.skypro.homework.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final static Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);


    private final AdRepository adRepository ;
    private final UserRepository userRepository ;
    private final AdMapper adMapper;
    private final ImageService imageService;

    @Override
    public AdsDto getAllAds() {
        List<Ad> ads = adRepository.findAll();
        return new AdsDto(ads.size(), adMapper.toDtoList(ads));
    }

    @Override
    public AdDto createAd(CreateOrUpdateAdDto adDto, MultipartFile image, String userEmail) {

        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Ad ad = adMapper.createOrUpdateAdDtoToEntity(adDto);
        ad.setAuthor(user);
        Image img = null;
        try {
            img = imageService.saveImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ad.setImage(img.getSource());
        ad = adRepository.save(ad);
        return adMapper.toDto(ad);
    }

    @Override
    public ExtendedAdDto getAd(Long id) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ad not found"));
        return adMapper.toExtendedDto(ad);
    }

    @Override
    public void deleteAd(Long id) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ad not found"));
        adRepository.delete(ad);
    }

    @Override
    public AdDto updateAd(Long id, CreateOrUpdateAdDto adDto) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ad not found"));
        adMapper.updateAdFromDto(adDto, ad);
        ad = adRepository.save(ad);
        return adMapper.toDto(ad);
    }

    @Override
    public AdsDto getUserAds(String userEmail) {
        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Ad> ads = adRepository.findByAuthorId(user.getId());
        return new AdsDto(ads.size(), adMapper.toDtoList(ads));
    }

    @Override
    public String updateAdImage(Long id, MultipartFile image) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ad not found"));
        Image img = null;
        try {
            img = imageService.saveImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ad.setImage(img.getSource());
        adRepository.save(ad);
        return img.getSource();
    }
}