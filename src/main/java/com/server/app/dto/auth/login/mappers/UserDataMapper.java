package com.server.app.dto.auth.login.mappers;

import com.server.app.dto.auth.login.UserDataDto;
import com.server.app.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserDataMapper {
    public UserDataDto toUserData(User user){
        return UserDataDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .username(user.getUsername())
                .surname(user.getSurname())
                .role(user.getRole())
                .build();
    }
}
