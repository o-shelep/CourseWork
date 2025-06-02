package com.example.st_mission.controller;


import com.example.st_mission.dto.ChangeRoleDTO;
import com.example.st_mission.dto.user.MessageResponse;
import com.example.st_mission.dto.user.UserDTO;
import com.example.st_mission.dto.user.UserUpdateDTO;
import com.example.st_mission.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        UserDTO user = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageResponse("Delete user with id " + id + " successful"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/role")
    public ResponseEntity<UserDTO> changeRole(@PathVariable long id, @RequestBody ChangeRoleDTO dto) {
        UserDTO user = userService.changeUserRole(id, dto.role());
        return ResponseEntity.ok(user);
    }


}
