package com.nicksteger.scheduler.data.repository;

import com.nicksteger.scheduler.data.entity.Event;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {
    List<Event> findByEventDateTime(Date eventDateTime);

    List<Event> findByUserId(long userId);

    Event findById(long eventId);
}
