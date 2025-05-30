package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;

public interface UserService {
    void updatePassword(String userEmail, NewPasswordDto newPassword);
    UserDto getUser(String userEmail);
    UpdateUserDto updateUser(String userEmail, UpdateUserDto updateUser);
    void updateUserImage(String userEmail, MultipartFile image);
}