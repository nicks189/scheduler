package com.nicksteger.scheduler.service;

import com.nicksteger.scheduler.data.dto.UserDto;
import com.nicksteger.scheduler.data.entity.User;
import com.nicksteger.scheduler.data.entity.UserRole;
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
    private UserRoleService userRoleService;

    @Autowired
    public UserService(UserRepository userRepository, EncryptionService encryptionService,
                       UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.userRoleService = userRoleService;
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

    public User saveUser(User user) {
        if (user.getPassword() != null) {
            user.setEncryptedPassword(encryptionService.encryptString(user.getPassword()));
        }
        return this.userRepository.save(user);
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

    public User registerNewUser(UserDto userDto) {
        if (usernameExists(userDto.getUsername())) {
            return null;
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user = saveUser(user);

        UserRole userRole = new UserRole();
        userRole.setUsername(user.getUsername());
        userRole.setRole("ROLE_USER");
        this.userRoleService.saveUserRole(userRole);

        return user;
    }

    public boolean usernameExists(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user != null) {
            return true;
        }
        return false;
    }
}
