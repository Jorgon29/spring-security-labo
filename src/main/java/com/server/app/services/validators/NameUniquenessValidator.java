package com.server.app.services.validators;

import com.server.app.exceptions.ConfictException;
import com.server.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NameUniquenessValidator {
    private final UserRepository userRepository;

    public void uniqueUsername(String username, Integer id) {
        userRepository.findUserByUsername(username).ifPresent(existing -> {
            if (id == null || existing.getId() != id) {
                throw new ConfictException("El nombre de usuario ya está en uso");
            }
        });
    }

}
