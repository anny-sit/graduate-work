package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Value("${image.dir.path}")
    private String imagesDir;

    private final static Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageRepository imageRepository;

    public ResponseEntity<byte[]> getImageData(String imagePath) {
        if (imagePath == null) {
            logger.warn("Image path is null");
            return ResponseEntity.notFound().build();
        }
        try {
            String filename = imagePath.replace("/images/", "").replace("\\", "/");
            Path path = Paths.get(imagesDir, filename).normalize();
            if (!Files.exists(path)) {
                logger.warn("File not found: {}", path);
                return ResponseEntity.notFound().build();
            }
            byte[] imageData = Files.readAllBytes(path);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageData);
        } catch (IOException e) {
            logger.error("Failed to read image at path: {}", imagePath, e);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public Image saveImage(MultipartFile image) throws IOException{
        logger.info("Was invoked method for upload image");

        Path filePath = Path.of(imagesDir, image.getOriginalFilename());
        logger.debug("file path = + {}", filePath);
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        System.out.println(filePath.getParent());
        try (InputStream is = image.getInputStream();
             OutputStream as = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bas = new BufferedOutputStream(as, 1024)) {
            bis.transferTo(bas);
        }
        Image img = new Image();
        img.setSource("/images/" + image.getOriginalFilename());
        img.setFileSize(image.getSize());
        img.setMimeType(image.getContentType());
        img.setFileData(generateDataForDB(filePath));
        imageRepository.save(img);
        return img;
    }

    byte[] generateDataForDB(Path filePath) throws IOException {
        logger.info("Was invoked method for generate data for DB");
        if (filePath == null) {
            logger.error("Path is empty");
        }

        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight();
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();
            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtensions(String fileName) {
        logger.info("Was invoked method for getting extensions of avatar files");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}