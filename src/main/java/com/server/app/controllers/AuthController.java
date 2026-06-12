package com.server.app.controllers;

import com.server.app.dto.auth.UpdatePasswordDto;
import com.server.app.dto.auth.login.LoginDto;
import com.server.app.dto.auth.login.UserDataDto;
import com.server.app.dto.auth.login.UserDataWithTokenDto;
import com.server.app.dto.auth.profile.ProfileResponseDto;
import com.server.app.dto.auth.profile.UpdateProfileDto;
import com.server.app.dto.auth.signup.SignUpDto;
import com.server.app.entities.User;
import com.server.app.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<UserDataWithTokenDto> login(@RequestBody @Valid LoginDto login){
        return ResponseEntity.ok(authService.login(login));
    }

    @PostMapping("signup")
    public ResponseEntity<UserDataWithTokenDto> signup(@RequestBody @Valid SignUpDto dto){
        return ResponseEntity.ok(authService.signup(dto));
    }

    @GetMapping("profile")
    public ResponseEntity<ProfileResponseDto> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(authService.getProfile(user));
    }

    @PutMapping("update/profile")
    public ResponseEntity<UserDataWithTokenDto> updateProfile(@AuthenticationPrincipal User user, @RequestBody UpdateProfileDto dto){
        return ResponseEntity.ok(authService.updateProfile(user, dto));
    }

    @PutMapping("update/password")
    public ResponseEntity<UserDataDto> updatePassword(@AuthenticationPrincipal User user, @RequestBody @Valid UpdatePasswordDto dto){
        return ResponseEntity.ok(authService.updatePassword(user, dto));
    }
}
