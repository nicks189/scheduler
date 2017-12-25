package com.nicksteger.scheduler.converters;

import com.nicksteger.scheduler.data.entity.User;
import com.nicksteger.scheduler.data.entity.UserRole;
import com.nicksteger.scheduler.service.UserRoleService;
import com.nicksteger.scheduler.service.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserToUserDetails implements Converter<User, UserDetails> {
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserDetails convert(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        if (user != null) {
            userDetails.setUsername(user.getUsername());
            userDetails.setPassword(user.getPassword());
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            List<UserRole> userRoles = this.userRoleService.getUserRolesByUsername(user.getUsername());
            for (UserRole userRole : userRoles) {
                authorities.add(new SimpleGrantedAuthority(userRole.getRole()));
            }
            userDetails.setAuthorities(authorities);
        }
        return userDetails;
    }
}
