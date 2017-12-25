package com.nicksteger.scheduler.web.application;

import com.nicksteger.scheduler.data.view.GeneralFormView;
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
            return "temp_error";
        }
        model.addAttribute("mode", "MODE_HOME");
        return "scheduler";
    }

    @RequestMapping(value={"/events"}, method=RequestMethod.GET)
    public String getEvents(Model model){
        List<Event> events = null;
        User user = this.userService.getUserByUsername("nsteger");
        if (user == null) {
            return "temp_error";
        }
        events = this.eventService.getAllEventsForUser(user);
        model.addAttribute("events", events);
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_EVENTS");
        return "scheduler";
    }

    @RequestMapping(value={"/calendar/{date}"}, method=RequestMethod.GET)
    public String getEventsByDate(@PathVariable(value="date", required=false) String dateString,
                                   Model model){
        List<Event> events = null;
        User user = this.userService.getUserByUsername("nsteger");
        if (user == null) {
            return "temp_error";
        }
        events = this.eventService.getEventsForDateAndUser(dateString, user);
        model.addAttribute("events", events);
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_EVENTS");
        return "scheduler";
    }

    @RequestMapping(value={"/calendar"}, method=RequestMethod.GET)
    public String calendar(Model model) {
        User user = this.userService.getUserByUsername("nsteger");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("generalFormView", new GeneralFormView());
        model.addAttribute("currentDate", DateService.getCurrentDateString());
        model.addAttribute("mode", "MODE_CALENDAR");
        return "scheduler";
    }

    @RequestMapping(value={"/calendar-form"}, method=RequestMethod.POST)
    public String calendarForm(@ModelAttribute(value="generalForm") GeneralFormView generalFormView, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "temp_error";
        }
        return "redirect:/scheduler/calendar/" + generalFormView.getFormText();
    }

    @RequestMapping(value="/save-event", method=RequestMethod.POST)
    public String saveEvent(@Valid @ModelAttribute(value="event") Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "temp_error";
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "logout";
    }
}
