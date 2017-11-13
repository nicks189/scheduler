package com.nicksteger.scheduler.data.entity;

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
    private String name;
    @Column(name="INFO")
    private String info;
    @Column(name="EVENT_DATETIME")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date eventDateTime;
    @Column(name="USER_ID")
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

    public void setEventDatetime(Date eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
