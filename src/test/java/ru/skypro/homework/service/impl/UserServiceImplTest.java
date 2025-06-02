package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.EntityNotFoundException;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ImageServiceImpl imageService;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;
    private UpdateUserDto updateUserDto;
    private NewPasswordDto newPasswordDto;
    private MultipartFile image;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(102L);
        user.setUsername("o@o.r");
        user.setPassword("encodedOldPass");
        user.setFirstName("fgsdfg");
        user.setLastName("fdsf");
        user.setPhone("+78888888888");
        user.setRole(Role.valueOf("ADMIN"));
        user.setImage("/images/meowrrr.jpg");

        userDto = new UserDto();
        userDto.setId(102L);
        userDto.setUsername("o@o.r");
        userDto.setFirstName("fgsdfg");
        userDto.setLastName("fdsf");
        userDto.setPhone("+78888888888");
        userDto.setRole(Role.valueOf("ADMIN"));
        userDto.setImage("/images/meowrrr.jpg");

        updateUserDto = new UpdateUserDto();
        updateUserDto.setFirstName("NewFirstName");
        updateUserDto.setLastName("NewLastName");
        updateUserDto.setPhone("+79999999999");

        newPasswordDto = new NewPasswordDto();
        newPasswordDto.setCurrentPassword("oldPass");
        newPasswordDto.setNewPassword("newPass");

        image = mock(MultipartFile.class);
    }

    @Test
    void updatePassword_ShouldUpdatePassword_WhenValidInput() {
        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("oldPass", "encodedOldPass")).thenReturn(true);
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updatePassword("o@o.r", newPasswordDto);

        verify(userRepository).findByUsername("o@o.r");
        verify(passwordEncoder).matches("oldPass", "encodedOldPass");
        verify(passwordEncoder).encode("newPass");
        verify(userRepository).save(user);
        assertEquals("encodedNewPass", user.getPassword());
    }

    @Test
    void updatePassword_ShouldThrowEntityNotFound_WhenUserNotFound() {
        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.updatePassword("o@o.r", newPasswordDto));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findByUsername("o@o.r");
        verifyNoInteractions(passwordEncoder, userMapper);
    }

    @Test
    void updatePassword_ShouldThrowIllegalArgument_WhenPasswordIncorrect() {
        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("oldPass", "encodedOldPass")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updatePassword("o@o.r", newPasswordDto));

        assertEquals("Current password is incorrect", exception.getMessage());
        verify(userRepository).findByUsername("o@o.r");
        verify(passwordEncoder).matches("oldPass", "encodedOldPass");
        verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    void getUser_ShouldReturnUserDto_WhenUserExists() {
        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.getUser("o@o.r");

        assertNotNull(result);
        assertEquals(userDto, result);
        verify(userRepository).findByUsername("o@o.r");
        verify(userMapper).toDto(user);
    }

    @Test
    void getUser_ShouldThrowEntityNotFound_WhenUserNotFound() {
        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.getUser("o@o.r"));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findByUsername("o@o.r");
        verifyNoInteractions(userMapper);
    }

    @Test
    void updateUser_ShouldUpdateAndReturnDto_WhenValidInput() {
        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUpdateDto(user)).thenReturn(updateUserDto);

        UpdateUserDto result = userService.updateUser("o@o.r", updateUserDto);

        assertNotNull(result);
        assertEquals(updateUserDto, result);
        verify(userRepository).findByUsername("o@o.r");
        verify(userMapper).updateUserFromDto(updateUserDto, user);
        verify(userRepository).save(user);
        verify(userMapper).toUpdateDto(user);
    }

    @Test
    void updateUser_ShouldThrowEntityNotFound_WhenUserNotFound() {
        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.updateUser("o@o.r", updateUserDto));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findByUsername("o@o.r");
        verifyNoInteractions(userMapper, imageService);
    }

    @Test
    void updateUserImage_ShouldUpdateImage_WhenValidInput() throws IOException {
        Image savedImage = new Image();
        savedImage.setSource("/images/test.jpg");

        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.of(user));
        when(imageService.saveImage(image)).thenReturn(savedImage);
        when(userRepository.save(user)).thenReturn(user);

        userService.updateUserImage("o@o.r", image);

        verify(userRepository).findByUsername("o@o.r");
        verify(imageService).saveImage(image);
        verify(userRepository).save(user);
        assertEquals("/images/test.jpg", user.getImage());
    }

    @Test
    void updateUserImage_ShouldThrowEntityNotFound_WhenUserNotFound() {
        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.updateUserImage("o@o.r", image));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findByUsername("o@o.r");
        verifyNoInteractions(imageService, userMapper);
    }

    @Test
    void updateUserImage_ShouldThrowRuntimeException_WhenImageSaveFails() throws IOException {
        when(userRepository.findByUsername("o@o.r")).thenReturn(Optional.of(user));
        when(imageService.saveImage(image)).thenThrow(new IOException("Image save failed"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.updateUserImage("o@o.r", image));

        assertEquals("Image save failed", exception.getCause().getMessage());
        verify(userRepository).findByUsername("o@o.r");
        verify(imageService).saveImage(image);
        verifyNoMoreInteractions(userRepository);
    }
}