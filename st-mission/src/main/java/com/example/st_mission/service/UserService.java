package com.example.st_mission.service;


import com.example.st_mission.dto.user.UserDTO;
import com.example.st_mission.dto.user.UserUpdateDTO;
import com.example.st_mission.mapper.UserMapper;
import com.example.st_mission.model.ERole;
import com.example.st_mission.model.RoleEntity;
import com.example.st_mission.model.UserEntity;
import com.example.st_mission.repo.RoleRepository;
import com.example.st_mission.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toUserDTO)
                .toList();
    }

    public UserDTO getUserById(long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toUserDTO(user);
    }

    @Transactional
    public UserDTO updateUser(long id, UserUpdateDTO updateDTO) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional.ofNullable(updateDTO.name())
                .filter(name -> !name.isBlank())
                .ifPresent(user::setName);

        if (updateDTO.email() != null && !updateDTO.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(updateDTO.email())) {
                throw new RuntimeException("Email already in use");
            }
            user.setEmail(updateDTO.email());
        }

        if (updateDTO.password() != null) {
            user.setPassword(passwordEncoder.encode(updateDTO.password()));
        }

        return UserMapper.toUserDTO(user);
    }

    public void deleteUser(long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Transactional
    public UserDTO changeUserRole(long userId, ERole roleName) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));

        RoleEntity role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));

        user.getRoles().add(role);

        return UserMapper.toUserDTO(user);
    }



}