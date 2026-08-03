package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.UserSyncRequest;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.service.FirebaseService;
import com.example.ecommerceapi.service.UserService;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FirebaseService firebaseService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User user) {

        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sync")
    public ResponseEntity<User> syncUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody UserSyncRequest request) {

        String idToken = authorizationHeader.replace("Bearer ", "");

        FirebaseToken firebaseToken = firebaseService.verifyToken(idToken);

        User user = userService.syncUser(
                firebaseToken,
                request
        );

        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(
            @RequestHeader("Authorization") String authorizationHeader) {

        String idToken = authorizationHeader.replace("Bearer ", "");

        FirebaseToken firebaseToken = firebaseService.verifyToken(idToken);

        User user = userService.getCurrentUser(firebaseToken);

        return ResponseEntity.ok(user);
    }
}