package com.example.st_mission.service;


import com.example.st_mission.dto.auth.JWTResponseDTO;
import com.example.st_mission.dto.auth.LoginDTO;
import com.example.st_mission.dto.auth.RegisterDTO;
import com.example.st_mission.mapper.UserMapper;
import com.example.st_mission.model.ERole;
import com.example.st_mission.model.RoleEntity;
import com.example.st_mission.model.UserEntity;
import com.example.st_mission.repo.RoleRepository;
import com.example.st_mission.repo.UserRepository;
import com.example.st_mission.security.JwtUtil;
import com.example.st_mission.service.userDetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, RoleRepository roleRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
    }


    public JWTResponseDTO register(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.email())) {
            throw new RuntimeException("User already exists with this email");
        }

        UserEntity user = UserMapper.fromRegisterDTO(registerDTO);

        user.setPassword(passwordEncoder.encode(registerDTO.password()));

        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                .orElseThrow(()-> new RuntimeException("ERole User not found"));

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerDTO.email(), registerDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String token = jwtUtil.generateToken(userDetails);

        return UserMapper.toJWTResponse(userDetails,token);
    }

    public JWTResponseDTO login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.email(),
                        loginDTO.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt =  jwtUtil.generateToken(userDetails);

        return UserMapper.toJWTResponse(userDetails, jwt);
    }
}