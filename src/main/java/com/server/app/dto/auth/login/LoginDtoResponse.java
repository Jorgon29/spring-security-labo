package com.server.app.dto.auth.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginDtoResponse {
    private String token;

    private UserDataDto data;
}
