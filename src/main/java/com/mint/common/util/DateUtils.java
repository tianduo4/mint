package com.mint.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
    public static SimpleDateFormat common_format = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static String COMMON_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    public static String COMMON_FORMAT_SHORT = "yyyy-MM-dd";
    public static String COMMON_FORMAT_MONTH = "yyyy-MM";
    public static String COMMON_FORMAT_MONTH_STR = "MM";
    public static String COMMON_FORMAT_YEAR = "yyyy";
    public static SimpleDateFormat dayFormat = new SimpleDateFormat(
            "yyyy-MM-dd");

    public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat format1 = new SimpleDateFormat(
            "yyyyMMdd HH:mm:ss");

    public static String formateDate(Date date, String formatStr) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.format(date);
        }
        return "";
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(5);
    }

    public static Date pasreToDate(String date, String formatStr)
            throws ParseException {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.parse(date);
        }
        return null;
    }

    public static Date getStartDate(Date date) {
        String temp = format.format(date);
        temp = temp + " 00:00:00";
        try {
            return format1.parse(temp);
        } catch (Exception e) {
        }
        return null;
    }

    public static Date getFinallyDate(Date date) {
        String temp = format.format(date);
        temp = temp + " 23:59:59";
        try {
            return format1.parse(temp);
        } catch (ParseException e) {
        }
        return null;
    }

    public static boolean isToday(Date date) {
        long day = date.getTime() / 1000L / 60L / 60L / 24L;
        long currentDay = System.currentTimeMillis() / 1000L / 60L / 60L / 24L;
        return day == currentDay;
    }

    public static boolean isThisWeek(Date date) {
        List<Date> dates = dateToWeek(date);
        Boolean flag = Boolean.valueOf(false);
        for (Date d : dates) {
            if (isToday(d)) {
                flag = Boolean.valueOf(true);
                break;
            }

        }

        return flag.booleanValue();
    }

    public static boolean isThisMonth(Date date) {
        long year = date.getYear();
        long month = date.getMonth();
        Calendar calendar = Calendar.getInstance();
        return (calendar.getTime().getYear() == year) &&
                (calendar.getTime().getMonth() == month);
    }

    public static boolean isThisYear(Date date) {
        long year = date.getYear();
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime().getYear() == year;
    }

    public static List<Date> dateToWeek(Date mdate) {
        int day = mdate.getDay();

        List list = new ArrayList();
        Long fTime = Long.valueOf(mdate.getTime() - day * 24 * 3600000);
        for (int i = 0; i < 7; i++) {
            Date fdate = new Date();
            fdate.setTime(fTime.longValue() + (i + 1) * 24 * 3600000);
            list.add(i, fdate);
        }
        return list;
    }

    public static Double getDiffMinuteTwoDate(Date begin, Date end) {
        return Double.valueOf((end.getTime() - begin.getTime()) / 1000L / 60.0D);
    }

    public static Double getDiffHourTwoDate(Date begin, Date end) {
        return Double.valueOf(getDiffHourTwoDate(begin, end).doubleValue() / 60.0D);
    }

    public static final int getDaysBetween(Date early, Date late) {
        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();
        calst.setTime(early);
        caled.setTime(late);

        calst.set(11, 0);
        calst.set(12, 0);
        calst.set(13, 0);
        caled.set(11, 0);
        caled.set(12, 0);
        caled.set(13, 0);

        int days = ((int) (caled.getTime().getTime() / 1000L) -
                (int) (calst
                        .getTime().getTime() / 1000L)) / 3600 / 24;
        return days;
    }

    public static String parseDate(Date date, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);

        String dateString = formater.format(date);
        return dateString;
    }

    public static Date parseDateTimeToDay(Date date) {
        return parseDateFormat(date, "yyyy-MM-dd");
    }

    public static Date parseDateFormat(Date date, String format) {
        String dateString = common_format.format(date);
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        try {
            return dateformat.parse(dateString);
        } catch (ParseException localParseException) {
        }
        return null;
    }

    public static Date afterDate(Date date, Integer after) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.get(5) + after.intValue());
        return calendar.getTime();
    }

    public static String parseDateToTimeStr(Date date) {
        return common_format.format(date);
    }

    public static String parseDateToDateStr(Date date) {
        return dayFormat.format(date);
    }

    public static Date parseDayStrToDate(String dateStr) {
        try {
            return dayFormat.parse(dateStr);
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date parseTimeStrToDate(String dateStr) {
        try {
            return common_format.parse(dateStr);
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date getSpecficMonthStart(Date date, int amount) {
        if (date == null) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, amount);
        cal.set(5, 1);
        return getStartDate(cal.getTime());
    }

    public static Date getSpecficMonthEnd(Date date, int amount) {
        if (date == null) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(getSpecficMonthStart(date, amount + 1));
        cal.add(6, -1);
        return getFinallyDate(cal.getTime());
    }

    public static Date getSpecficYearStart(Date date, int amount) {
        if (date == null) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(1, amount);
        cal.set(6, 1);
        return getStartDate(cal.getTime());
    }

    public static Date getSpecficYearEnd(Date date, int amount) {
        if (date == null) {
            date = new Date();
        }
        Date temp = getStartDate(getSpecficYearStart(date, amount + 1));
        Calendar cal = Calendar.getInstance();
        cal.setTime(temp);
        cal.add(6, -1);
        return getFinallyDate(cal.getTime());
    }

    public static Date getSpecficDateStart(Date date, int amount) {
        if (date == null) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(6, amount);
        return getStartDate(cal.getTime());
    }

    public static Date getSpecficDateEnd(Date date, int amount) {
        if (date == null) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(6, amount);
        return getFinallyDate(cal.getTime());
    }

    public static Date getSpecficDate(Date date, int amount) {
        if (date == null) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(6, amount);
        return cal.getTime();
    }

    public static Date getDateMinute(Date date, int minute) {
        if (date == null) {
            date = new Date();
        }
        long t = date.getTime();
        t += minute * 60 * 1000;
        Date d = new Date(t);
        return d;
    }

    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(2) + 1;
    }

    public static int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(5);
    }

    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(1);
    }

    public static void main(String[] arg) {
        String d = "2017-9-24 11:13:45";

        Long l = Long.valueOf(1505145599L);

        for (int i = 0; i < 10000; i++) {
            System.out.print(i + "----" + getFinallyDate(new Date()));
        }

        List<Date> list = dateToWeek(new Date());
        for (Date s : list)
            System.out.println(s);
    }
}

