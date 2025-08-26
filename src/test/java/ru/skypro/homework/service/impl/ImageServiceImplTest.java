package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    @TempDir
    Path tempDir;

    private String imagesDir;

    @BeforeEach
    void setUp() {
        imagesDir = tempDir.resolve("images").toString();
        setField(imageService, "imagesDir", imagesDir);
        try {
            Files.createDirectories(Paths.get(imagesDir));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create images directory", e);
        }
    }

    @Test
    void getImageData_ShouldReturnImage_WhenFileExists() throws IOException {
        String imagePath = "/images/test.jpg";
        Path filePath = Paths.get(imagesDir, "test.jpg");
        byte[] imageData = "test image content".getBytes();
        Files.write(filePath, imageData);

        ResponseEntity<byte[]> response = imageService.getImageData(imagePath);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
        assertArrayEquals(imageData, response.getBody());
    }

    @Test
    void getImageData_ShouldReturnNotFound_WhenFileDoesNotExist() {
        String imagePath = "/images/nonexistent.jpg";

        ResponseEntity<byte[]> response = imageService.getImageData(imagePath);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getImageData_ShouldReturnNotFound_WhenPathIsNull() {
        ResponseEntity<byte[]> response = imageService.getImageData(null);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void saveImage_ShouldSaveImageAndReturnEntity_WhenFileIsValid() throws IOException {
        // Создаём валидное изображение
        BufferedImage bufferedImage = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] imageData = baos.toByteArray();

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                imageData
        );

        Image savedImage = new Image();
        savedImage.setSource("/images/test.jpg");
        savedImage.setFileSize(file.getSize());
        savedImage.setMimeType(MediaType.IMAGE_JPEG_VALUE);
        savedImage.setFileData(new byte[]{1, 2, 3}); // Заглушка для fileData

        when(imageRepository.save(any(Image.class))).thenReturn(savedImage);

        Image result = imageService.saveImage(file);

        assertNotNull(result);
        assertEquals("/images/test.jpg", result.getSource());
        assertEquals(file.getSize(), result.getFileSize());
        assertEquals(MediaType.IMAGE_JPEG_VALUE, result.getMimeType());
        System.out.println("Checking file at: " + Paths.get(imagesDir, "test.jpg"));
        assertTrue(Files.exists(Paths.get(imagesDir, "test.jpg")));
        verify(imageRepository).save(any(Image.class));
    }

    @Test
    void generateDataForDB_ShouldReturnPreview_WhenImageIsValid() throws IOException {
        BufferedImage original = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(original, "jpg", baos);
        Path filePath = Paths.get(imagesDir, "test.jpg");
        Files.write(filePath, baos.toByteArray());

        byte[] previewData = imageService.generateDataForDB(filePath);

        assertNotNull(previewData);
        BufferedImage preview = ImageIO.read(new ByteArrayInputStream(previewData));
        assertEquals(100, preview.getWidth());
        assertEquals(100, preview.getHeight());
    }

    @Test
    void generateDataForDB_ShouldThrowIOException_WhenPathIsNull() {
        assertThrows(NullPointerException.class, () -> imageService.generateDataForDB(null));
    }

    @Test
    void getExtension_ShouldReturnExtension_WhenFileNameIsValid() {
        String extension = invokePrivateMethod(imageService, "getExtensions", "test.jpg");

        assertEquals("jpg", extension);
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set field: " + fieldName, e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T invokePrivateMethod(Object target, String methodName, Object... args) {
        try {
            var method = target.getClass().getDeclaredMethod(methodName, String.class);
            method.setAccessible(true);
            return (T) method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}