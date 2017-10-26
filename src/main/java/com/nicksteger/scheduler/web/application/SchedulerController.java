package com.nicksteger.scheduler.web.application;

import com.nicksteger.scheduler.service.DateService;
import com.nicksteger.scheduler.service.EventService;
import com.nicksteger.scheduler.service.UserService;
import com.nicksteger.scheduler.data.entity.Event;
import com.nicksteger.scheduler.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="/scheduler")
@SuppressWarnings("Duplicates")
public class SchedulerController {

    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    @RequestMapping(value="/{username}", method=RequestMethod.GET)
    public String home(@PathVariable(value="username") String username, Model model) {
        User user = this.userService.getUserByUsername(username);
        if (user == null) {
            return "error";
        }
        model.addAttribute("username", user.getUsername());
        model.addAttribute("mode", "MODE_HOME");
        return "scheduler";
    }

    @RequestMapping(value={"/{username}/events"}, method=RequestMethod.GET)
    public String getEventsForUser(@PathVariable(value="username")String username, Model model){
        List<Event> events = null;
        User user = this.userService.getUserByUsername(username);
        if (user != null) {
            events = this.eventService.getAllEventsForUser(user);
        } else {
            return "error";
        }
        model.addAttribute("event", new Event());
        model.addAttribute("events", events);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_EVENTS");
        return "scheduler";
    }

    @RequestMapping(value={"/{username}/{date}"}, method=RequestMethod.GET)
    public String getEventsForUserByDate(@PathVariable(value="date", required=false) String dateString,
                                   @PathVariable(value="username")String username, Model model){
        List<Event> events = null;
        User user = this.userService.getUserByUsername(username);
        if (user != null) {
            events = this.eventService.getEventsForDateAndUser(dateString, user);
        } else {
            return "error";
        }
        model.addAttribute("event", new Event());
        model.addAttribute("events", events);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_EVENTS");
        return "scheduler";
    }

    @RequestMapping(value="/save-event", method=RequestMethod.POST)
    public String saveEvent(@ModelAttribute(value="event") Event event, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        this.eventService.saveEvent(event);
        User user = this.userService.getUserByUsername("nsteger");
        List<Event> events = this.eventService.getAllEventsForUser(user);
        model.addAttribute("events", events);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_EVENTS");
        return "result";
    }

    @RequestMapping(value="/new-event", method=RequestMethod.GET)
    public String newEvent(Model model) {
        User user = this.userService.getUserByUsername("nsteger");
        model.addAttribute("event", new Event());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_NEW");
        return "scheduler";
    }

    @RequestMapping(value="/update-event/{id}", method=RequestMethod.GET)
    public String updateEvent(@PathVariable(value="id") long id, Model model) {
        Event event = this.eventService.getEventById(id);
        User user = this.userService.getUserByUsername("nsteger");
        model.addAttribute("event", event);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_UPDATE");
        return "scheduler";
    }

    @RequestMapping(value="/delete-event/{id}", method=RequestMethod.GET)
    public String deleteEvent(@PathVariable(value="id") long id, Model model) {
        this.eventService.deleteEvent(id);
        User user = this.userService.getUserByUsername("nsteger");
        List<Event> events = this.eventService.getAllEventsForUser(user);
        model.addAttribute("events", events);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_EVENTS");
        return "scheduler";
    }
}
