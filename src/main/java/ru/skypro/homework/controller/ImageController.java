package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.ImageServiceImpl;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final static Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final ImageServiceImpl imageService;
    private final UserRepository userRepository;
    private final AdRepository adRepository;

    @RequestMapping(value = "/users/me/image", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> optionsUserImage() {
        log.info("Вызван метод обработки OPTIONS для /users/me/image");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/users/me/image", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/*"})
    public ResponseEntity<byte[]> getUserImage() {
        log.info("Вызван метод контроллера возвращающий массив байт аватарки пользователя");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = ((UserDetails) principal).getUsername();
        String filePath = userRepository.findByUsername(userName)
                .map(User::getImage)
                .orElse(null);

        if (filePath == null) {
            log.warn("Image not found for user: {}", userName);
            return ResponseEntity.notFound().build();
        }

        ResponseEntity<byte[]> response = imageService.getImageData(filePath);
        if (response.getBody() == null) {
            log.warn("No image data returned for path: {}", filePath);
            return ResponseEntity.notFound().build();
        }
        return response;
    }

    @RequestMapping(value = "/ads/img/{adId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> optionsAdImage() {
        log.info("Вызван метод обработки OPTIONS для /ads/img/{adId}");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/ads/img/{adId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/*"})
    public ResponseEntity<byte[]> getAdImage(@PathVariable Long adId) {
        log.info("Вызван метод контроллера возвращающий массив байт изображения объявления");
        String filePath = adRepository.findById(adId)
                .map(Ad::getImage)
                .orElse(null);

        if (filePath == null) {
            log.warn("Image not found for ad: {}", adId);
            return ResponseEntity.notFound().build();
        }

        ResponseEntity<byte[]> response = imageService.getImageData(filePath);
        if (response.getBody() == null) {
            log.warn("No image data returned for path: {}", filePath);
            return ResponseEntity.notFound().build();
        }
        return response;
    }

    @RequestMapping(value = "/images/{filename:.+}", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> optionsImage() {
        log.info("Вызван метод обработки OPTIONS для /images/{filename}");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/images/{filename:.+}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/*"})
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        logger.info("Вызван метод получения изображения: {}", filename);
        String filePath = "/images/" + filename;
        return imageService.getImageData(filePath);
    }

}
