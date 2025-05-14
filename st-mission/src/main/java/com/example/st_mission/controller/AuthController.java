package com.example.st_mission.controller;


import com.example.st_mission.dto.ChangeRoleDTO;
import com.example.st_mission.dto.auth.JWTResponseDTO;
import com.example.st_mission.dto.auth.RegisterDTO;
import com.example.st_mission.dto.user.MessageResponse;
import com.example.st_mission.dto.auth.LoginDTO;
import com.example.st_mission.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<JWTResponseDTO> signUp(@RequestBody @Valid RegisterDTO register) {
        JWTResponseDTO response = authService.register(register);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTResponseDTO> signIn(@RequestBody LoginDTO login){
        JWTResponseDTO response = authService.login(login);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletResponse response) {
//        Cookie cookie = new Cookie("jwt", "");
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//        return ResponseEntity.ok("Logout successful");
//    }
}
