package ru.skypro.homework.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ImageServiceImpl imageService;

    @Override
    public void updatePassword(String userEmail, NewPasswordDto newPassword) {
        logger.info("updating pass started. incoming dto: {}", newPassword.toString());
        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (!passwordEncoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
        logger.debug("updated pass user: {}", user.toString());
        userRepository.save(user);
        logger.info("updating pass success");

    }

    @Override
    public UserDto getUser(String userEmail) {
        logger.info("getting user started. incoming dto: {}", userEmail);
        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        logger.debug("found user: {}", user.toString());
        UserDto toReturn = userMapper.toDto(user);
        logger.debug("user to return: {}", toReturn.toString());
        return toReturn;
    }

    @Override
    public UpdateUserDto updateUser(String userEmail, UpdateUserDto updateUser) {
        logger.info("updating user" +
                " started. incoming dto: {}", updateUser.toString());

        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userMapper.updateUserFromDto(updateUser, user);

        logger.debug("user updated: {}", user.toString());

        user = userRepository.save(user);
        return userMapper.toUpdateDto(user);
    }

    @Override
    public void updateUserImage(String userEmail, MultipartFile image) {
        logger.info("updating user image started. incoming dto: {}", userEmail);

        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Image img = null;
        try {
            img = imageService.saveImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.setImage(img.getSource());
        userRepository.save(user);
    }
}