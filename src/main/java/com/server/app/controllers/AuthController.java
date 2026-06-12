package com.server.app.controllers;

import com.server.app.dto.auth.login.LoginDto;
import com.server.app.dto.auth.login.LoginDtoResponse;
import com.server.app.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<LoginDtoResponse> login(@RequestBody @Valid LoginDto login){
        return ResponseEntity.ok(authService.login(login));
    }
}
