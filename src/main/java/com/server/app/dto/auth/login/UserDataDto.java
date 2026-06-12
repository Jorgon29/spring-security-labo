package com.server.app.dto.auth.login;

import com.server.app.dto.role.RoleDto;
import com.server.app.entities.Role;
import lombok.Builder;

@Builder
public class UserDataDto {
    private Integer id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private Role role;
}
