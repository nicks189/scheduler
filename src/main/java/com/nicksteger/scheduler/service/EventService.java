package com.nicksteger.scheduler.service;

import com.nicksteger.scheduler.data.entity.Event;
import com.nicksteger.scheduler.data.entity.User;
import com.nicksteger.scheduler.data.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService {
    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        this.eventRepository.findAll().forEach(events::add);
        return events;
    }

    public Event getEventById(long id) {
        return this.eventRepository.findById(id);
    }

    public Event saveEvent(Event event) {
        return this.eventRepository.save(event);
    }

    public void deleteEvent(long id) {
        this.eventRepository.delete(id);
    }

    public List<Event> getAllEventsForUser(User user) {
        List<Event> events = new ArrayList<>();
        this.eventRepository.findByUserId(user.getId()).forEach(events::add);
        return events;
    }

    public List<Event> getEventsForDateAndUser(String dateString, User user) {
        List<Event> events = new ArrayList<>();
        Iterable<Event> eventsByUserId = this.eventRepository.findByUserId(user.getId());
        eventsByUserId.forEach(event -> {
            if (DateService.compareDates(event.getEventDateTime(), DateService.createDateFromDateString(dateString))) {
                events.add(event);
            }
        });
        return events;
    }

    public List<Event> deleteExpiredEvents(List<Event> events) {
        List<Event> returnEvents = new ArrayList<>();
        Date currentDate = new Date();
        for (Event event : events) {
            if (event.getEventDateTime().before(currentDate)) {
                deleteEvent(event.getId());
            } else {
                returnEvents.add(event);
            }
        }
        return returnEvents;
    }

    public void deleteEventForUser(Event event, User user) {
        if (event.getUserId() == user.getId()) {
            deleteEvent(event.getId());
        }
    }
}
