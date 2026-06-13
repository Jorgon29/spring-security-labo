package com.server.app.dto.auth.login;

import com.server.app.dto.role.RoleDto;
import com.server.app.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserDataDto {
    private Integer id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private Role role;
}
