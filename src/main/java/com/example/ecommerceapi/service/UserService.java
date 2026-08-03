package com.example.ecommerceapi.service;

import com.example.ecommerceapi.dto.UserSyncRequest;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.repository.UserRepository;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User syncUser(FirebaseToken firebaseToken, UserSyncRequest request) {

        User user = userRepository
                .findByFirebaseUid(firebaseToken.getUid())
                .orElse(new User());

        user.setFirebaseUid(firebaseToken.getUid());
        user.setEmail(firebaseToken.getEmail());

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            user.setName(request.getFullName());
        }

        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            user.setPhone(request.getPhone());
        }

        if (user.getRole() == null) {
            user.setRole("USER");
        }

        return userRepository.save(user);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long id, User userDetails) {

        User user = getUserById(id);

        user.setName(userDetails.getName());
        user.setPhone(userDetails.getPhone());
        user.setPhotoUrl(userDetails.getPhotoUrl());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public User getCurrentUser(FirebaseToken firebaseToken) {
        return userRepository.findByFirebaseUid(firebaseToken.getUid())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}