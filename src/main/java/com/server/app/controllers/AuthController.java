package com.server.app.controllers;

import com.server.app.dto.auth.login.LoginDto;
import com.server.app.dto.auth.login.LoginDtoResponse;
import com.server.app.dto.auth.profile.ProfileResponseDto;
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
    public ResponseEntity<LoginDtoResponse> login(@RequestBody @Valid LoginDto login){
        return ResponseEntity.ok(authService.login(login));
    }

    @PostMapping("signup")
    public ResponseEntity<LoginDtoResponse> signup(@RequestBody @Valid SignUpDto dto){
        return ResponseEntity.ok(authService.signup(dto));
    }
    /*
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(authService.getProfile(user));
    }*/
}
