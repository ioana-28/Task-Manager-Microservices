package org.server.userservice.service;

import org.server.userservice.dto.UserCreateRequestDto;
import org.server.userservice.dto.UserResponseDto;
import org.server.userservice.entity.User;
import org.server.userservice.exception.ResourceNotFoundException;
import org.server.userservice.exception.UsernameAlreadyExistsException;
import org.server.userservice.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException(request.username());
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setRole(request.role());

        try {
            User savedUser = userRepository.save(user);
            return UserResponseDto.from(savedUser);
        } catch (DataIntegrityViolationException ex) {
            throw new UsernameAlreadyExistsException(request.username());
        }
    }

    @Override
    public UserResponseDto getCurrentUserByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username" + username));

        return UserResponseDto.from(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return UserResponseDto.from(user);
    }


}