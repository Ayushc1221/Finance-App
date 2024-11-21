package com.financeapp.services;

import com.financeapp.entities.User;

import java.util.Optional;

public interface UserService {
    User registerUser(String email, String password, String fullName);
    Optional<User> findByEmail(String email);
    boolean verifyPassword(User user, String rawPassword);
}
