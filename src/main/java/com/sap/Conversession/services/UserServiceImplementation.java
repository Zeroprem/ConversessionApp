package com.sap.Conversession.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.Conversession.model.User;
import com.sap.Conversession.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Method to register a user
    public void register(User user) {
        user.setStatus("online");
        userRepository.save(user);
    }

    // Method to login a user
    public User login(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!existingUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Password incorrect");
        }

        existingUser.setStatus("online");
        userRepository.save(existingUser); // Update the status
        return existingUser;
    }

    // Method to logout a user
    public void logout(String email) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setStatus("offline");
        userRepository.save(existingUser); // Update the status
    }

    // Method to fetch all users
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
