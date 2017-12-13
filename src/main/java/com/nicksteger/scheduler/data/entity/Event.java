package com.nicksteger.scheduler.data.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="EVENT")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="EVENT_ID")
    private long id;
    @Column(name="NAME")
    @NotEmpty(message = "*Please provide an event name")
    @Length(max = 32, message = "*Event name is too long")
    private String name;
    @Column(name="INFO")
    @Length(max = 255, message = "*Event info is too long")
    private String info;
    @Column(name="EVENT_DATETIME")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @NotEmpty(message = "*Please provide an event date and time")
    private Date eventDateTime;
    @Column(name="USER_ID")
    @NotEmpty
    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(Date eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
