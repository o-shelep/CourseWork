package com.example.st_mission.controller;


import com.example.st_mission.dto.user.MessageResponse;
import com.example.st_mission.dto.user.UserDTO;
import com.example.st_mission.dto.user.UserUpdateDTO;
import com.example.st_mission.service.UserService;
import com.example.st_mission.service.userDetails.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUserById(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.getUserById(userDetails.getId()));
    }

    @PatchMapping
    public ResponseEntity<UserDTO> updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        UserDTO user = userService.updateUser(userDetails.getId(), userUpdateDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUser(userDetails.getId());
        return ResponseEntity.ok(new MessageResponse("Delete user with id " + userDetails.getId() + " successful"));
    }

}
