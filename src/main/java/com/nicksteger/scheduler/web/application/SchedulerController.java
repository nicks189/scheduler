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

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value="/scheduler")
@SuppressWarnings("Duplicates")
public class SchedulerController {

    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    @RequestMapping(method=RequestMethod.GET)
    public String home(Model model) {
        User user = this.userService.getUserByUsername("nsteger");
        if (user == null) {
            return "error";
        }
        model.addAttribute("username", user.getUsername());
        model.addAttribute("mode", "MODE_HOME");
        return "scheduler";
    }

    @RequestMapping(value={"/events"}, method=RequestMethod.GET)
    public String getEvents(Model model){
        List<Event> events = null;
        User user = this.userService.getUserByUsername("nsteger");
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

    @RequestMapping(value={"/{date}"}, method=RequestMethod.GET)
    public String getEventsByDate(@PathVariable(value="date", required=false) String dateString,
                                   Model model){
        List<Event> events = null;
        User user = this.userService.getUserByUsername("nsteger");
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
    public String saveEvent(@Valid @ModelAttribute(value="event") Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        User user = this.userService.getUserByUsername("nsteger");
        event.setUserId(user.getId());
        this.eventService.saveEvent(event);
        return "redirect:/scheduler/events";
    }

    @RequestMapping(value="/new-event", method=RequestMethod.GET)
    public String newEvent(Model model) {
        User user = this.userService.getUserByUsername("nsteger");
        model.addAttribute("event", new Event());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_SAVE");
        return "scheduler";
    }

    @RequestMapping(value="/update-event/{id}", method=RequestMethod.GET)
    public String updateEvent(@PathVariable(value="id") long id, Model model) {
        Event event = this.eventService.getEventById(id);
        User user = this.userService.getUserByUsername("nsteger");
        event.setId(id);
        model.addAttribute("event", event);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_SAVE");
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

    @RequestMapping(value="/delete-expired-events", method=RequestMethod.GET)
    public String deleteExpiredEvents(Model model) {
        User user = this.userService.getUserByUsername("nsteger");
        this.eventService.deleteExpiredEvents(this.eventService.getAllEventsForUser(user));
        return "redirect:/scheduler/events";
    }
}
