package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Image saveImage(MultipartFile image) {
        Image img = new Image();
        try {
            img.setFileData(image.getBytes());
            img.setFileSize(image.getSize());
            img.setMimeType(image.getContentType());
            img.setSource("/images/" + image.getOriginalFilename());
            return imageRepository.save(img);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }
}