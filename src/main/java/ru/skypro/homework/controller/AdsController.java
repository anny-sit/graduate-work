package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {

    @GetMapping
    public ResponseEntity<?> getAllAds() {
        List<AdDto> ads = new ArrayList<>();
        return ResponseEntity.ok(ads);
    }

    @PostMapping
    public ResponseEntity<?> addAd(@RequestParam AdDto ad, @RequestParam MultipartFile image) throws URISyntaxException {

        return ResponseEntity.created(new URI("/ads/" + ad.getTitle())).body(ad);

        //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAds(@RequestParam Long id) {
        return ResponseEntity.ok(new ExtendedAdDto());

        /*return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();*/
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@RequestParam Long id) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        /*return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();*/
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAds(@RequestParam Long id) {

        return ResponseEntity.ok(new AdDto());

        /*return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();*/
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAdsMe() {

        return ResponseEntity.ok(new AdsDto());

        //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<?> updateImage(@RequestParam Long id, @RequestBody MultipartFile image) {

        return ResponseEntity.ok(new AdDto().getImage());

        /*return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();*/
    }
}
