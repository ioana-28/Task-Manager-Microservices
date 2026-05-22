package org.server.userservice.dto;

import org.server.userservice.entity.User;


public record UserResponseDto(
        Long id,
        String username,
        String role
) {
    public static UserResponseDto from(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole().name());
    }

}
