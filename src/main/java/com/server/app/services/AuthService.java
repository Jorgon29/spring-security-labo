package com.server.app.services;

import com.server.app.config.JsonWebToken;
import com.server.app.dto.auth.login.LoginDto;
import com.server.app.dto.auth.login.LoginDtoResponse;
import com.server.app.dto.auth.login.mappers.UserDataMapper;
import com.server.app.dto.auth.signup.SignUpDto;
import com.server.app.entities.Role;
import com.server.app.entities.User;
import com.server.app.exceptions.NotFoundException;
import com.server.app.exceptions.ServerException;
import com.server.app.repositories.RoleRepository;
import com.server.app.repositories.UserRepository;
import com.server.app.services.validators.EmailUniquenessValidator;
import com.server.app.services.validators.NameUniquenessValidator;
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
    private final EmailUniquenessValidator emailValidator;
    private final NameUniquenessValidator nameValidator;
    private final RoleRepository roleRepository;

    public LoginDtoResponse login(LoginDto login) {
        User user = userRepository.findUserByUsername(login.getUsername())
                .orElseThrow(() -> new NotFoundException("Credenciales inválidas"));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new NotFoundException("Credenciales inválidas");
        }

        String token = jsonWebToken.createToken(user);

        return new LoginDtoResponse(token, userDataMapper.toUserData(user));
    }

    public LoginDtoResponse signup(SignUpDto dto){
        nameValidator.uniqueUsername(dto.getUsername(), null);
        emailValidator.uniqueEmail(dto.getEmail(), null);
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role role;
        if (dto.getRole() != null) {
            role = roleRepository.findByName(dto.getRole().toUpperCase())
                    .orElseThrow(() -> new NotFoundException("Rol no encontrado"));
        } else {
            role = roleRepository.findByName("ADMIN").orElseThrow(() -> new ServerException("Error al asignar rol ADMIN"));
        }
        user.setRole(role);

        User finished = userRepository.save(user);
        String token = jsonWebToken.createToken(finished);

        return new LoginDtoResponse(token, userDataMapper.toUserData(finished));
    }
}
