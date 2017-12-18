package com.nicksteger.scheduler.service;

import com.nicksteger.scheduler.data.entity.UserRole;
import com.nicksteger.scheduler.data.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleService {
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserRole> getAllUsersRoles() {
        List<UserRole> userRoles = new ArrayList<>();
        this.userRoleRepository.findAll().forEach(userRoles::add);
        return userRoles;
    }

    public List<UserRole> getUserRolesByUserId(long id) {
        return this.userRoleRepository.findByUserId(id);
    }

    public List<UserRole> getUserRoleById(long id) {
        return this.userRoleRepository.findByUserRoleId(id);
    }

    public void saveUserRole(UserRole userRole) {
        this.userRoleRepository.save(userRole);
    }

    public void deleteUserRole(UserRole userRole) {
        this.userRoleRepository.delete(userRole.getId());
    }
}
