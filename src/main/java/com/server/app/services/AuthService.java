package com.server.app.services;

import com.server.app.config.JsonWebToken;
import com.server.app.dto.auth.login.LoginDto;
import com.server.app.dto.auth.login.LoginDtoResponse;
import com.server.app.dto.auth.login.mappers.UserDataMapper;
import com.server.app.entities.User;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JsonWebToken jsonWebToken;
    private final PasswordEncoder passwordEncoder;
    private final UserDataMapper userDataMapper;

    public LoginDtoResponse login(LoginDto login) {
        User user = userRepository.findUserByUsername(login.getUsername())
                .orElseThrow(() -> new NotFoundException("Credenciales inválidas"));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new NotFoundException("Credenciales inválidas");
        }

        String token = jsonWebToken.createToken(user);

        return new LoginDtoResponse(token, userDataMapper.toUserData(user));
    }
}
