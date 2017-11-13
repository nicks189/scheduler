package com.nicksteger.scheduler.web.service;

import com.nicksteger.scheduler.service.EventService;
import com.nicksteger.scheduler.data.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/api")
public class SchedulerServiceController {

    @Autowired
    private EventService eventService;

    // For testing only
    @RequestMapping(value="/events", method=RequestMethod.GET)
    public List<Event> getAllEvents() {
        return this.eventService.getAllEvents();
    }

    // For testing only
    // @RequestMapping(method= RequestMethod.GET)
    // public String getReservations(@RequestParam(value="date", required=false)String dateString){
    //     User user = this.userService.getUserById(1);
    //     List<Event> events = this.eventService.getEventsForDateAndUser(dateString, user);
    //     model.addAttribute("events", events);
    //     return "scheduler";
    // }

    // For testing only
    @RequestMapping(value="/save-event", method=RequestMethod.GET)
    public List<Event> saveEvent(@RequestParam(value="name") String name, @RequestParam(value="info") String info) {
        Event event = new Event();
        event.setName(name);
        event.setInfo(info);
        event.setEventDateTime(new Date());
        event.setUserId(1);
        this.eventService.saveEvent(event);
        return this.eventService.getAllEvents();
    }

    // For testing only
    @RequestMapping(value="/delete-event", method = RequestMethod.GET)
    public List<Event> deleteEvent(@RequestParam(value="id") long id) {
        this.eventService.deleteEvent(id);
        return this.eventService.getAllEvents();
    }
}
