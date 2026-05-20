package org.server.userservice.service;

import org.server.userservice.dto.UserCreateRequestDto;
import org.server.userservice.dto.UserResponseDto;

public interface UserService {

    UserResponseDto createUser(UserCreateRequestDto request);
    UserResponseDto getCurrentUserByUsername(String username);
    UserResponseDto getUserById(Long id);
}