package com.smartdev.controller;

import com.smartdev.dto.request.ChangePasswordDTO;
import com.smartdev.dto.request.UpdateProfileDTO;
import com.smartdev.entity.UserEntity;
import com.smartdev.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserEntity> getProfile() {
        // Obter o usuário logado a partir do SecurityContextHolder
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Obter os dados do usuário baseado no username
        UserEntity user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserEntity> updateProfile(@RequestBody UpdateProfileDTO updateProfileDTO) {
        // Obter o usuário logado a partir do SecurityContextHolder
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Atualizar o perfil baseado no username do usuário logado
        UserEntity updatedUser = userService.updateProfileByUsername(username, updateProfileDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // Atualizar senha do usuário logado
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        // Obter o usuário logado a partir do SecurityContextHolder
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Atualizar a senha baseada no username do usuário logado
        userService.changePasswordByUsername(username, changePasswordDTO);
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }


}

