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
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormatter.parse("2016-01-01");
        } catch (ParseException e) {
            throw new ServerErrorException("error parsing date getMatches [LeagueService]");
        }
    }

    public Date endDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today());
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        return calendar.getTime();
    }

    public Date yesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today());
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }
}
