package com.esgi.stats19.api.common.services;

import com.esgi.stats19.api.common.exceptions.ServerErrorException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

@Service
public class DateService {
    public Date today() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -4);
        calendar.add(Calendar.MONTH, -5);
        calendar.add(Calendar.DAY_OF_YEAR, -27);

        return calendar.getTime();
    }

    public Date endDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today());
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        return calendar.getTime();
    }

    public String format(Date date) {
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        return dt1.format(date);
    }

    public String getSeason() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today());
        var year = calendar.get(Calendar.YEAR);
        var month = calendar.get(Calendar.MONTH);

        if (month < 8) {
            calendar.add(Calendar.YEAR, -1);
            var limit = calendar.get(Calendar.YEAR);
            return limit + "/" + year;
        } else {
            calendar.add(Calendar.YEAR, 1);
            var limit = calendar.get(Calendar.YEAR);
            return year + "/" + limit;
        }
    }
}
