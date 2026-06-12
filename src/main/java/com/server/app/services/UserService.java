package com.server.app.services;

import com.server.app.services.validators.EmailUniquenessValidator;
import com.server.app.services.validators.NameUniquenessValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.app.dto.user.UserCreateDto;
import com.server.app.dto.user.UserUpdateDto;
import com.server.app.entities.Role;
import com.server.app.entities.User;
import com.server.app.exceptions.ConfictException;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.RoleRepository;
import com.server.app.repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailUniquenessValidator emailValidator;
    private final NameUniquenessValidator nameValidator;

    @Transactional
    public User create(UserCreateDto dto) {
        nameValidator.uniqueUsername(dto.getUsername(), null);
        emailValidator.uniqueEmail(dto.getEmail(), null);
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (dto.getRole() != null) {
            Role role = roleRepository.findById(dto.getRole())
                    .orElseThrow(() -> new NotFoundException("Rol no encontrado"));
            user.setRole(role);
        }

        return userRepository.save(user);
    }

    public Page<User> findAll(int page, int size, String search) {
        return userRepository.findAll(PageRequest.of(page, size), search);
    }

    @Transactional
    public User updateUser(int userId, UserUpdateDto dto) {
        User user = findById(userId);

        if (user.isBlocked()) {
            throw new ConfictException("The user: " + user.getUsername() + " is locked");
        }

        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            nameValidator.uniqueUsername(dto.getUsername(), userId);
            user.setUsername(dto.getUsername());
        }

        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }

        if (dto.getSurname() != null && !dto.getSurname().isBlank()) {
            user.setSurname(dto.getSurname());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            emailValidator.uniqueEmail(dto.getEmail(), userId);
            user.setEmail(dto.getEmail());
        }

        if (dto.getBlocked() != null) {
            user.setBlocked(dto.getBlocked());
        }

        if (dto.getRole() != null) {
            Role role = roleRepository.findById(dto.getRole())
                    .orElseThrow(() -> new NotFoundException("Rol no encontrado"));
            user.setRole(role);
        }

        return userRepository.save(user);
    }

    public User findById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }
}
