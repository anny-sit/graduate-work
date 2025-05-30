package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.service.AdService;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {

    private final AdService adService;

    @GetMapping
    public ResponseEntity<AdsDto> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> addAd(@RequestPart CreateOrUpdateAdDto ad,
                                       @RequestPart MultipartFile image,
                                       Authentication authentication) throws URISyntaxException {
        AdDto createdAd = adService.createAd(ad, image, authentication.getName());
        return ResponseEntity.created(new URI("/ads/" + createdAd.getPk())).body(createdAd);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAds(@PathVariable Long id) {
        ExtendedAdDto ad = adService.getAd(id);
        return ResponseEntity.ok(ad);
    }

    @PreAuthorize("hasRole('ADMIN') or authentication.name == @adService.getAd(#id).author.email")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@PathVariable Long id) {
        adService.deleteAd(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ADMIN') or authentication.name == @adService.getAd(#id).author.email")
    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAds(@PathVariable Long id, @RequestBody CreateOrUpdateAdDto ad) {
        AdDto updatedAd = adService.updateAd(id, ad);
        return ResponseEntity.ok(updatedAd);
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDto> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(adService.getUserAds(authentication.getName()));
    }

    @PreAuthorize("hasRole('ADMIN') or authentication.name == @adService.getAd(#id).author.email")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateImage(@PathVariable Long id, @RequestPart MultipartFile image) {
        String imagePath = adService.updateAdImage(id, image);
        return ResponseEntity.ok(imagePath);
    }
}