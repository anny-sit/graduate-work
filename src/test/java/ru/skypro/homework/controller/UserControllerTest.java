package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
        authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("o@o.r");
    }

    @Test
    void setPassword_ShouldReturnOk_WhenPasswordUpdated() throws Exception {
        NewPasswordDto newPassword = new NewPasswordDto();
        newPassword.setCurrentPassword("oldPass");
        newPassword.setNewPassword("newPass");

        doNothing().when(userService).updatePassword(eq("o@o.r"), any(NewPasswordDto.class));

        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPassword))
                        .principal(authentication))
                .andExpect(status().isOk());

        verify(userService).updatePassword(eq("o@o.r"), any(NewPasswordDto.class));
    }

    @Test
    void setPassword_ShouldReturnNotFound_WhenUserNotFound() throws Exception {
        NewPasswordDto newPassword = new NewPasswordDto();
        newPassword.setCurrentPassword("oldPass");
        newPassword.setNewPassword("newPass");

        doThrow(new EntityNotFoundException("User not found"))
                .when(userService).updatePassword(eq("o@o.r"), any(NewPasswordDto.class));

        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPassword))
                        .principal(authentication))
                .andExpect(status().isNotFound());

        verify(userService).updatePassword(eq("o@o.r"), any(NewPasswordDto.class));
    }

    @Test
    void setPassword_ShouldReturnBadRequest_WhenPasswordIncorrect() throws Exception {
        NewPasswordDto newPassword = new NewPasswordDto();
        newPassword.setCurrentPassword("oldPass");
        newPassword.setNewPassword("newPass");

        doThrow(new IllegalArgumentException("Current password is incorrect"))
                .when(userService).updatePassword(eq("o@o.r"), any(NewPasswordDto.class));

        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPassword))
                        .principal(authentication))
                .andExpect(status().isBadRequest());

        verify(userService).updatePassword(eq("o@o.r"), any(NewPasswordDto.class));
    }

    @Test
    void getUser_ShouldReturnUserDto_WhenUserExists() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(102L);
        userDto.setUsername("o@o.r");
        userDto.setFirstName("fgsdfg");
        userDto.setLastName("fdsf");
        userDto.setPhone("+78888888888");
        userDto.setRole(Role.valueOf("ADMIN"));
        userDto.setImage("/images/meowrrr.jpg");

        when(userService.getUser("o@o.r")).thenReturn(userDto);

        mockMvc.perform(get("/users/me")
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(102L))
                .andExpect(jsonPath("$.username").value("o@o.r"))
                .andExpect(jsonPath("$.firstName").value("fgsdfg"))
                .andExpect(jsonPath("$.lastName").value("fdsf"))
                .andExpect(jsonPath("$.phone").value("+78888888888"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.image").value("/images/meowrrr.jpg"));

        verify(userService).getUser("o@o.r");
    }

    @Test
    void getUser_ShouldReturnNotFound_WhenUserNotFound() throws Exception {
        when(userService.getUser("o@o.r")).thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(get("/users/me")
                        .principal(authentication))
                .andExpect(status().isNotFound());

        verify(userService).getUser("o@o.r");
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser_WhenUpdateSuccessful() throws Exception {
        UpdateUserDto updateUser = new UpdateUserDto();
        updateUser.setFirstName("NewFirstName");
        updateUser.setLastName("NewLastName");
        updateUser.setPhone("+79999999999");

        UpdateUserDto updatedUser = new UpdateUserDto();
        updatedUser.setFirstName("NewFirstName");
        updatedUser.setLastName("NewLastName");
        updatedUser.setPhone("+79999999999");

        when(userService.updateUser("o@o.r", updateUser)).thenReturn(updatedUser);

        mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser))
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("NewFirstName"))
                .andExpect(jsonPath("$.lastName").value("NewLastName"))
                .andExpect(jsonPath("$.phone").value("+79999999999"));

        verify(userService).updateUser("o@o.r", updateUser);
    }

    @Test
    void updateUser_ShouldReturnNotFound_WhenUserNotFound() throws Exception {
        UpdateUserDto updateUser = new UpdateUserDto();
        updateUser.setFirstName("NewFirstName");
        updateUser.setLastName("NewLastName");
        updateUser.setPhone("+79999999999");

        when(userService.updateUser("o@o.r", updateUser))
                .thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser))
                        .principal(authentication))
                .andExpect(status().isNotFound());

        verify(userService).updateUser("o@o.r", updateUser);
    }

    @Test
    void updateUserImage_ShouldReturnOk_WhenImageUpdated() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "image content".getBytes()
        );

        mockMvc.perform(multipart(HttpMethod.PATCH, "/users/me/image")
                        .file(image)
                        .principal(authentication))
                .andExpect(status().isOk());

        verify(userService).updateUserImage("o@o.r", image);
    }

    @Test
    void updateUserImage_ShouldReturnNotFound_WhenUserNotFound() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "image content".getBytes()
        );

        doThrow(new EntityNotFoundException("User not found"))
                .when(userService).updateUserImage("o@o.r", image);

        mockMvc.perform(multipart(HttpMethod.PATCH, "/users/me/image")
                        .file(image)
                        .principal(authentication))
                .andExpect(status().isNotFound());

        verify(userService).updateUserImage("o@o.r", image);
    }

    @Test
    void updateUserImage_ShouldReturnBadRequest_WhenImageSaveFails() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "image content".getBytes()
        );

        doThrow(new RuntimeException("Image save failed", new IOException("Image save failed")))
                .when(userService).updateUserImage("o@o.r", image);

        mockMvc.perform(multipart(HttpMethod.PATCH, "/users/me/image")
                        .file(image)
                        .principal(authentication))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Image save failed"));

        verify(userService).updateUserImage("o@o.r", image);
    }
}