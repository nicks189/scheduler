package com.nicksteger.scheduler.service;

import com.nicksteger.scheduler.data.entity.User;
import com.nicksteger.scheduler.data.repository.EventRepository;
import com.nicksteger.scheduler.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        this.userRepository.save(user);
    }

    public void deleteUser(User user) {
        this.userRepository.delete(user.getId());
    }
}
