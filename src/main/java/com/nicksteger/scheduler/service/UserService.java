package com.nicksteger.scheduler.service;

import com.nicksteger.scheduler.data.entity.User;
import com.nicksteger.scheduler.data.repository.EventRepository;
import com.nicksteger.scheduler.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> getAllUsers() {
        List<User> events = new ArrayList<>();
        this.userRepository.findAll().forEach(events::add);
        return events;
    }

    public User getUserByEmail(String username) {
        return this.userRepository.findByEmail(username);
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void saveUser(User user) {
        user.setHashedPassword(bCryptPasswordEncoder.encode(user.getHashedPassword()));
        this.userRepository.save(user);
    }

    public void deleteUser(User user) {
        this.userRepository.delete(user.getId());
    }
}
