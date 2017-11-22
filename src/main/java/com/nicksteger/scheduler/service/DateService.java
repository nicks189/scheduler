package com.nicksteger.scheduler.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SuppressWarnings("Duplicates")
public class DateService {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final int DATE_STRING_MAX_LENGTH = 10;
    private static final int DATE_TIME_STRING_MAX_LENGTH = 19;

    public static Date createDateFromDateString(String dateString){
        Date date = null;
        if (dateString != null) {
            try {
                // Limit date strings to yyyy-MM-dd form
                dateString = dateString.substring(0, DATE_STRING_MAX_LENGTH);
                date = DATE_FORMAT.parse(dateString);
            } catch (Exception e){
                date = new Date();
            }
        } else {
            date = new Date();
        }
        return date;
    }

    public static Date createDateTimeFromDateTimeString(String dateTimeString){
        Date date = null;
        if (dateTimeString != null) {
            try {
                // Limit date strings to yyyy-MM-dd HH:mm:ss form
                dateTimeString = dateTimeString.substring(0, DATE_TIME_STRING_MAX_LENGTH);
                date = DATE_TIME_FORMAT.parse(dateTimeString);
            } catch (Exception e){
                date = new Date();
            }
        } else {
            date = new Date();
        }
        return date;
    }

    public static boolean compareDates(Date f, Date s) {
        return f.getMonth() == s.getMonth() && f.getYear() == s.getYear()
                && f.getDate() == s.getDate();
    }

    public static String getCurrentDateString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dtf.format(LocalDateTime.now());
    }

    // Not used right now: maybe still useful at some point?
    public static Date convertTo24HourDate(Date date) {
        if (date != null) {
            return createDateTimeFromDateTimeString(DATE_TIME_FORMAT.format(date));
        }
        return new Date();
    }
}
