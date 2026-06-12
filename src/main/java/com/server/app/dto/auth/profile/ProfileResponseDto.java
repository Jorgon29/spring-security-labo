package com.server.app.dto.auth.profile;

import com.server.app.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProfileResponseDto {
    private Integer id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private Role role;
}
