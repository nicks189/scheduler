package com.nicksteger.scheduler.service;

import com.nicksteger.scheduler.data.entity.User;
import com.nicksteger.scheduler.data.repository.EventRepository;
import com.nicksteger.scheduler.data.repository.UserRepository;
import com.nicksteger.scheduler.service.security.EncryptionService;
import com.nicksteger.scheduler.service.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private EncryptionService encryptionService;

    @Autowired
    public UserService(UserRepository userRepository, EncryptionService encryptionService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        this.userRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void saveUser(User user) {
        if (user.getPassword() != null) {
            user.setEncryptedPassword(encryptionService.encryptString(user.getPassword()));
        }
        this.userRepository.save(user);
    }

    public void deleteUser(User user) {
        this.userRepository.delete(user.getId());
    }

    public User getUserFromAuthentication(Authentication authentication) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return getUserByUsername(userDetails.getUsername());
        }
        return null;
    }
}
