package com.tmdb.movie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String convertDate(String dateFormat) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat outputMonthYearFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = originalFormat.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dateInt = calendar.get(Calendar.DATE);
        return String.valueOf(dateInt).concat(" " + getDayOfMonthSuffix(dateInt).concat(" " + outputMonthYearFormat.format(dateInt)));
    }

    public static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
}
