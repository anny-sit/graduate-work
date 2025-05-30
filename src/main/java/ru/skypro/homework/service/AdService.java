package ru.skypro.homework.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.model.Ad;

public interface AdService {
    AdsDto getAllAds();
    AdDto createAd(CreateOrUpdateAdDto ad, MultipartFile image, String userEmail);
    ExtendedAdDto getAd(Long id);
    void deleteAd(Long id);
    AdDto updateAd(Long id, CreateOrUpdateAdDto ad);
    AdsDto getUserAds(String userEmail);
    String updateAdImage(Long id, MultipartFile image);
}