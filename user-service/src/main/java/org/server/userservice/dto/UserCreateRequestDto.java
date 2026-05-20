package org.server.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.server.userservice.entity.Role;

public record UserCreateRequestDto(
    @NotBlank(message = "Username is required")
    String username,

    @NotBlank(message = "Password is required")
    String password,

    @NotNull(message = "Role is required")
    Role role
) {}
