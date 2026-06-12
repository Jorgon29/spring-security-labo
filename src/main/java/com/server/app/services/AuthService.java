package com.server.app.services;

import com.server.app.config.JsonWebToken;
import com.server.app.dto.auth.UpdatePasswordDto;
import com.server.app.dto.auth.login.LoginDto;
import com.server.app.dto.auth.login.UserDataDto;
import com.server.app.dto.auth.login.UserDataWithTokenDto;
import com.server.app.dto.auth.login.mappers.UserDataMapper;
import com.server.app.dto.auth.profile.ProfileResponseDto;
import com.server.app.dto.auth.profile.UpdateProfileDto;
import com.server.app.dto.auth.signup.SignUpDto;
import com.server.app.entities.Role;
import com.server.app.entities.User;
import com.server.app.exceptions.BadRequestException;
import com.server.app.exceptions.NotFoundException;
import com.server.app.exceptions.ServerException;
import com.server.app.exceptions.UnauthorizedException;
import com.server.app.repositories.RoleRepository;
import com.server.app.repositories.UserRepository;
import com.server.app.services.validators.EmailUniquenessValidator;
import com.server.app.services.validators.NameUniquenessValidator;
import jakarta.transaction.Transactional;
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

    public UserDataWithTokenDto login(LoginDto login) {
        User user = userRepository.findUserByUsername(login.getUsername())
                .orElseThrow(() -> new NotFoundException("Credenciales inválidas"));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new NotFoundException("Credenciales inválidas");
        }

        String token = jsonWebToken.createToken(user);

        return new UserDataWithTokenDto(token, userDataMapper.toUserData(user));
    }

    @Transactional
    public UserDataWithTokenDto signup(SignUpDto dto){
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

        return new UserDataWithTokenDto(token, userDataMapper.toUserData(finished));
    }

    public ProfileResponseDto getProfile(User user) {

        return ProfileResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .username(user.getUsername())
                .surname(user.getSurname())
                .role(user.getRole())
                .build();
    }

    @Transactional
    public UserDataWithTokenDto updateProfile(User user, UpdateProfileDto dto) {
        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            nameValidator.uniqueUsername(dto.getUsername(), user.getId());
            user.setUsername(dto.getUsername());
        }

        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }

        if (dto.getSurname() != null && !dto.getSurname().isBlank()) {
            user.setSurname(dto.getSurname());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            emailValidator.uniqueEmail(dto.getEmail(), user.getId());
            user.setEmail(dto.getEmail());
        }

        User finished = userRepository.save(user);

        String token = jsonWebToken.createToken(user);

        return new UserDataWithTokenDto(token, userDataMapper.toUserData(finished));
    }

    @Transactional
    public UserDataDto updatePassword(User user, UpdatePasswordDto dto) {
        if (!dto.getNewpassword().equals(dto.getConfirmpassword())) {
            throw new BadRequestException("La nueva contraseña y la confirmación no coinciden.");
        }

        if (!passwordEncoder.matches(dto.getOldpassword(), user.getPassword())) {
            throw new UnauthorizedException("La contraseña actual es incorrecta.");
        }

        String encryptedPassword = passwordEncoder.encode(dto.getNewpassword());
        user.setPassword(encryptedPassword);

        User updatedUser = userRepository.save(user);

        return userDataMapper.toUserData(updatedUser);
    }
}
