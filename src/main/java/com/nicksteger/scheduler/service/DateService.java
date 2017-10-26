package com.nicksteger.scheduler.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateService {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final int DATE_STRING_MAX_LENGTH = 10;

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

    public static boolean compareDates(Date f, Date s) {
        return f.getMonth() == s.getMonth() && f.getYear() == s.getYear()
                && f.getDate() == s.getDate();
    }

    public static String getCurrentDateString() {
        // Date currentDate = new Date();
        // return currentDate.getYear() + "-" + currentDate.getMonth() + "-" + currentDate.getDate();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dtf.format(LocalDateTime.now());
    }
}
