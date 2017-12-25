package com.nicksteger.scheduler.bootstrap;

import com.nicksteger.scheduler.data.entity.Event;
import com.nicksteger.scheduler.data.entity.User;
import com.nicksteger.scheduler.data.entity.UserRole;
import com.nicksteger.scheduler.service.EventService;
import com.nicksteger.scheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
Used to bootstrap data into H2 embedded database
 */
@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private UserService userService;
    private EventService eventService;

    @Autowired
    public SpringJpaBootstrap(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadUsers();
        loadEvents();
        loadUserRoles();
    }

    public void loadUsers() {
        User user = new User();
        user.setUsername("nsteger");
        user.setPassword("password");
        userService.saveUser(user);
    }

    public void loadUserRoles() {
        UserRole userRole1 = new UserRole();
        userRole1.setRole("ROLE_USER");
        userRole1.setUsername("nsteger");

        UserRole userRole2 = new UserRole();
        userRole2.setRole("ROLE_ADMIN");
        userRole2.setUsername("nsteger");
    }

    public void loadEvents() {
        Event event1 = new Event();
        event1.setUserId(1);
        event1.setName("Family Christmas");
        event1.setInfo("Steger family Christmas");
        Date date1 = new Date();
        date1.setDate(30);
        date1.setMonth(12);
        event1.setEventDateTime(date1);
        eventService.saveEvent(event1);

        Event event2 = new Event();
        event2.setUserId(1);
        event2.setName("Work");
        event2.setInfo("I have to work today");
        Date date2 = new Date();
        date2.setDate(27);
        date2.setMonth(12);
        event2.setEventDateTime(date2);
        eventService.saveEvent(event2);
    }
}
