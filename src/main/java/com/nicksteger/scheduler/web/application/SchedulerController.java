package com.nicksteger.scheduler.web.application;

import com.nicksteger.scheduler.data.dto.UserDto;
import com.nicksteger.scheduler.view.GeneralFormView;
import com.nicksteger.scheduler.service.DateService;
import com.nicksteger.scheduler.service.EventService;
import com.nicksteger.scheduler.service.UserService;
import com.nicksteger.scheduler.data.entity.Event;
import com.nicksteger.scheduler.data.entity.User;
import com.nicksteger.scheduler.service.security.UserDetailsImpl;
import com.nicksteger.scheduler.view.UserFormView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/scheduler")
@SuppressWarnings("Duplicates")
public class SchedulerController {

    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model, Authentication authentication) {
        User user = this.userService.getUserFromAuthentication(authentication);
        if (user != null) {
            model.addAttribute("firstname", user.getFirstName());
        }
        model.addAttribute("mode", "MODE_HOME");
        return "scheduler";
    }

    @RequestMapping(value ={"/events"}, method = RequestMethod.GET)
    public String getEvents(Model model, Authentication authentication) {
        List<Event> events = null;
        User user = this.userService.getUserFromAuthentication(authentication);
        if (user == null) {
            return "error";
        }
        events = this.eventService.getAllEventsForUser(user);
        model.addAttribute("events", events);
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_EVENTS");
        return "scheduler";
    }

    @RequestMapping(value ={"/calendar/{date}"}, method = RequestMethod.GET)
    public String getEventsByDate(@PathVariable(value = "date", required=false) String dateString,
                                   Model model, Authentication authentication) {
        List<Event> events = null;
        User user = this.userService.getUserFromAuthentication(authentication);
        if (user == null) {
            return "error";
        }
        events = this.eventService.getEventsForDateAndUser(dateString, user);
        model.addAttribute("events", events);
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_EVENTS");
        return "scheduler";
    }

    @RequestMapping(value ={"/calendar"}, method = RequestMethod.GET)
    public String calendar(Model model) {
        model.addAttribute("generalFormView", new GeneralFormView());
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_CALENDAR");
        return "scheduler";
    }

    @RequestMapping(value ={"/calendar-form"}, method = RequestMethod.POST)
    public String calendarForm(@ModelAttribute(value = "generalForm") GeneralFormView generalFormView,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        return "redirect:/scheduler/calendar/" + generalFormView.getFormText();
    }

    @RequestMapping(value = "/new-event", method = RequestMethod.GET)
    public String newEvent(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_SAVE");
        return "scheduler";
    }

    @RequestMapping(value = "/update-event/{id}", method = RequestMethod.GET)
    public String updateEvent(@PathVariable(value = "id") long id, Model model, Authentication authentication) {
        User user = this.userService.getUserFromAuthentication(authentication);
        if (user == null) {
            return "error";
        }
        Event event = this.eventService.getEventById(id);
        event.setId(id);
        if (event.getUserId() != user.getId()) {
            return "redirect:/scheduler/events";
        }
        model.addAttribute("event", event);
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_SAVE");
        return "scheduler";
    }

    @RequestMapping(value = "/save-event", method = RequestMethod.POST)
    public String saveEvent(@Valid @ModelAttribute(value = "event") Event event, BindingResult bindingResult,
                            Authentication authentication) {
        User user = this.userService.getUserFromAuthentication(authentication);
        if (bindingResult.hasErrors() || user == null) {
            return "error";
        }
        event.setUserId(user.getId());
        this.eventService.saveEvent(event);
        return "redirect:/scheduler/events";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute(value = "userDto") UserDto userDto, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        User user = this.userService.registerNewUser(userDto);
        if (user == null) {
            bindingResult.rejectValue("username", "error.username", "Username is unavailable");
            return "register";
        }
        try {
            httpServletRequest.login(user.getUsername(), user.getPassword());
        } catch (ServletException e) {
            return "error";
        }
        return "redirect:/scheduler";
    }

    @RequestMapping(value = "/delete-event/{id}", method = RequestMethod.GET)
    public String deleteEvent(@PathVariable(value = "id") long id, Model model, Authentication authentication) {
        User user = this.userService.getUserFromAuthentication(authentication);
        if (user == null) {
            return "redirect:/scheduler/events";
        }
        this.eventService.deleteEventForUser(this.eventService.getEventById(id), user);
        List<Event> events = this.eventService.getAllEventsForUser(user);
        model.addAttribute("events", events);
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_EVENTS");
        return "scheduler";
    }

    @RequestMapping(value = "/delete-expired-events", method = RequestMethod.GET)
    public String deleteExpiredEvents() {
        User user = this.userService.getUserByUsername("nsteger");
        this.eventService.deleteExpiredEvents(this.eventService.getAllEventsForUser(user));
        return "redirect:/scheduler/events";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "logout";
    }

}
