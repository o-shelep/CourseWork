package com.example.st_mission.mapper;

import com.example.st_mission.dto.auth.JWTResponseDTO;
import com.example.st_mission.dto.auth.RegisterDTO;
import com.example.st_mission.dto.user.UserDTO;
import com.example.st_mission.model.UserEntity;
import com.example.st_mission.service.userDetails.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;

public class UserMapper {

    public static UserEntity fromRegisterDTO(RegisterDTO registerDTO)
    {
        UserEntity user = new UserEntity();
        user.setName(registerDTO.name());
        user.setEmail(registerDTO.email());
        user.setPassword(registerDTO.password());

        return user;
    }

    public static JWTResponseDTO toJWTResponse(UserDetailsImpl user, String jwtToken){

        return new JWTResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList(),
                "Bearer",
                jwtToken
        );
    }

    public static UserDTO toUserDTO(UserEntity user)
    {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList(),
                user.getLevel(),
                user.getPoints(),
                user.getAchievements(),
                user.getSubmissions()
        );
    }



}
