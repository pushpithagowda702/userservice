package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.respository.UserRepository;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public UserResponse getUserProfile(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() ->
            new RuntimeException("User not found")
        );
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }

    public UserResponse register(@Valid RegisterRequest request) {
        if(userRepo.existsByEmail(request.getEmail())) {
            User existingUser = userRepo.findByEmail(request.getEmail());
            return formatResponse(existingUser);
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setKeycloakId(request.getKeycloakId());
        User savedUser = userRepo.save(user);
        return formatResponse(savedUser);
    }

    private UserResponse formatResponse(User savedUser) {
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setEmail(savedUser.getEmail());
        response.setPassword(savedUser.getPassword());
        response.setKeycloakId(savedUser.getKeycloakId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());
        return response;
    }

    public Boolean validateUser(String userId) {
        return userRepo.existsByKeycloakId(userId);
    }
}
