package com.server.app.dto.auth.profile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProfileDto {
    private String username;
    private String email;
    private String surname;
    private String name;

}
