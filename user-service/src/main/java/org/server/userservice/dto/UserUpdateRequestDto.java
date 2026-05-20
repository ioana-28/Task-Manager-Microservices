package org.server.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequestDto(
        @NotBlank(message = "Username is required")
        String username

) {}
