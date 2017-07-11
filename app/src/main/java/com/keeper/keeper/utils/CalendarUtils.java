package com.keeper.keeper.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtils {

    public static String dateFormat = "dd-MM-yyyy hh:mm";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

    public static String monthFormat = "M";
    private static SimpleDateFormat simpleMonthFormat = new SimpleDateFormat(monthFormat);

    public static String ConvertToDateString(long milliSeconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String ConvertToMonthString(long milliSeconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleMonthFormat.format(calendar.getTime());
    }
}