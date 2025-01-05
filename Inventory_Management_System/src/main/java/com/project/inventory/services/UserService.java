package com.project.inventory.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.inventory.dtos.UserDTO;
import com.project.inventory.dtos.UserResponseDto;
import com.project.inventory.dtos.UserUpdateDTO;
import com.project.inventory.exceptions.ResourceNotFoundException;
import com.project.inventory.model.User;
import com.project.inventory.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(UserDTO userDTO) {
        log.info("Saving new user: {}", userDTO.getUsername());
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        User savedUser = userRepository.save(user);
        log.info("User saved successfully with ID: {}", savedUser.getId());
        return savedUser;
    }

    public List<User> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        log.info("Fetched {} users", users.size());
        return users;
    }

    public UserDTO toDTO(User user) {
        log.info("Converting User entity to DTO for user ID: {}", user.getId());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        log.info("Converted User entity to DTO for user ID: {}", user.getId());
        return userDTO;
    }

    public UserResponseDto getUser(Long id) {
        log.info("Fetching user by ID: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setRole(user.getRole());
        userResponseDto.setUsername(user.getUsername());
        log.info("User fetched successfully with ID: {}", user.getId());
        return userResponseDto;
    }

    public void deleteUser(Long id) {
        log.info("Deleting user by ID: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("User with ID {} deleted successfully from the database.", id);
        } else {
            log.error("User with ID {} not found in the database.", id);
            throw new ResourceNotFoundException("User not found.");
        }
    }

    public User updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        log.info("Updating user with ID: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));

        if (userUpdateDTO.getUsername() != null && !userUpdateDTO.getUsername().isEmpty()) {
            user.setUsername(userUpdateDTO.getUsername());
        }

        if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        }

        if (userUpdateDTO.getRole() != null && !userUpdateDTO.getRole().isEmpty()) {
            user.setRole(userUpdateDTO.getRole());
        }

        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with ID: {}", updatedUser.getId());
        return updatedUser;
    }
}
