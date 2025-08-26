package ru.skypro.homework.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    private ImageServiceImpl imageService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdRepository adRepository;

    @InjectMocks
    private ImageController imageController;

    private MockMvc mockMvc;
    private Authentication authentication;
    private UserDetails userDetails;
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();

        // Настройка SecurityContextHolder
        authentication = mock(Authentication.class);
        userDetails = mock(UserDetails.class);
        securityContext = mock(SecurityContext.class);

        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getPrincipal()).thenReturn(userDetails);
        lenient().when(userDetails.getUsername()).thenReturn("o@o.r");
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void optionsUserImage_ShouldReturnOk() throws Exception {
        mockMvc.perform(options("/users/me/image"))
                .andExpect(status().isOk());
    }

    @Test
    void getUserImage_ShouldReturnImage_WhenImageExists() throws Exception {
        User user = new User();
        user.setImage("/images/user.jpg");
        byte[] imageData = "image content".getBytes();

        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.of(user));
        when(imageService.getImageData("/images/user.jpg"))
                .thenReturn(ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageData));

        mockMvc.perform(get("/users/me/image"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(imageData));

        verify(userRepository).findByUsername("o@o.r");
        verify(imageService).getImageData("/images/user.jpg");
    }

    @Test
    void getUserImage_ShouldReturnNotFound_WhenUserHasNoImage() throws Exception {
        User user = new User();
        user.setImage(null);

        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/me/image"))
                .andExpect(status().isNotFound());

        verify(userRepository).findByUsername("o@o.r");
        verifyNoInteractions(imageService);
    }

    @Test
    void getUserImage_ShouldReturnNotFound_WhenUserNotFound() throws Exception {
        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/me/image"))
                .andExpect(status().isNotFound());

        verify(userRepository).findByUsername("o@o.r");
        verifyNoInteractions(imageService);
    }

    @Test
    void getUserImage_ShouldReturnNotFound_WhenImageDataNotFound() throws Exception {
        User user = new User();
        user.setImage("/images/user.jpg");

        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.of(user));
        when(imageService.getImageData("/images/user.jpg")).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/users/me/image"))
                .andExpect(status().isNotFound());

        verify(userRepository).findByUsername("o@o.r");
        verify(imageService).getImageData("/images/user.jpg");
    }

    @Test
    void optionsAdImage_ShouldReturnOk() throws Exception {
        mockMvc.perform(options("/ads/img/1"))
                .andExpect(status().isOk());

        verifyNoInteractions(userRepository, adRepository, imageService);
    }

    @Test
    void getAdImage_ShouldReturnImage_WhenImageExists() throws Exception {
        Ad ad = new Ad();
        ad.setImage("/images/ad.jpg");
        byte[] imageData = "ad image content".getBytes();

        when(adRepository.findById(1L)).thenReturn(Optional.of(ad));
        when(imageService.getImageData("/images/ad.jpg"))
                .thenReturn(ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageData));

        mockMvc.perform(get("/ads/img/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(imageData));

        verify(adRepository).findById(1L);
        verify(imageService).getImageData("/images/ad.jpg");
    }

    @Test
    void getAdImage_ShouldReturnNotFound_WhenAdNotFound() throws Exception {
        when(adRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/ads/img/1"))
                .andExpect(status().isNotFound());

        verify(adRepository).findById(1L);
        verifyNoInteractions(imageService);
    }

    @Test
    void getAdImage_ShouldReturnNotFound_WhenAdHasNoImage() throws Exception {
        Ad ad = new Ad();
        ad.setImage(null);

        when(adRepository.findById(1L)).thenReturn(Optional.of(ad));

        mockMvc.perform(get("/ads/img/1"))
                .andExpect(status().isNotFound());

        verify(adRepository).findById(1L);
        verifyNoInteractions(imageService);
    }

    @Test
    void getAdImage_ShouldReturnNotFound_WhenImageDataNotFound() throws Exception {
        Ad ad = new Ad();
        ad.setImage("/images/ad.jpg");

        when(adRepository.findById(1L)).thenReturn(Optional.of(ad));
        when(imageService.getImageData("/images/ad.jpg")).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/ads/img/1"))
                .andExpect(status().isNotFound());

        verify(adRepository).findById(1L);
        verify(imageService).getImageData("/images/ad.jpg");
    }

    @Test
    void optionsImage_ShouldReturnOk() throws Exception {
        mockMvc.perform(options("/images/test.jpg"))
                .andExpect(status().isOk());

        verifyNoInteractions(userRepository, adRepository, imageService);
    }

    @Test
    void getImage_ShouldReturnImage_WhenImageExists() throws Exception {
        byte[] imageData = "image content".getBytes();

        when(imageService.getImageData("/images/test.jpg"))
                .thenReturn(ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageData));

        mockMvc.perform(get("/images/test.jpg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(imageData));

        verify(imageService).getImageData("/images/test.jpg");
    }

    @Test
    void getImage_ShouldReturnNotFound_WhenImageNotFound() throws Exception {
        when(imageService.getImageData("/images/test.jpg")).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/images/test.jpg"))
                .andExpect(status().isNotFound());

        verify(imageService).getImageData("/images/test.jpg");
    }
}