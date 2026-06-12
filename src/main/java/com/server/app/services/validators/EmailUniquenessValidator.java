package com.server.app.services.validators;

import com.server.app.exceptions.ConfictException;
import com.server.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailUniquenessValidator {
    private final UserRepository userRepository;

    public void uniqueEmail(String email, Integer id) {
        userRepository.findUserByEmail(email).ifPresent(existing -> {
            if (id == null || existing.getId() != id) {
                throw new ConfictException("El correo electrónico ya está en uso");
            }
        });
    }
}
