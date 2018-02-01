/*     */ package com.jspgou.common.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class DateUtils
/*     */ {
/*  12 */   public static SimpleDateFormat common_format = new SimpleDateFormat(
/*  13 */     "yyyy-MM-dd HH:mm:ss");
/*     */ 
/*  14 */   public static String COMMON_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
/*  15 */   public static String COMMON_FORMAT_SHORT = "yyyy-MM-dd";
/*  16 */   public static String COMMON_FORMAT_MONTH = "yyyy-MM";
/*  17 */   public static String COMMON_FORMAT_MONTH_STR = "MM";
/*  18 */   public static String COMMON_FORMAT_YEAR = "yyyy";
/*  19 */   public static SimpleDateFormat dayFormat = new SimpleDateFormat(
/*  20 */     "yyyy-MM-dd");
/*     */ 
/*  21 */   public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
/*  22 */   public static SimpleDateFormat format1 = new SimpleDateFormat(
/*  23 */     "yyyyMMdd HH:mm:ss");
/*     */ 
/*     */   public static String formateDate(Date date, String formatStr)
/*     */   {
/*  32 */     if (date != null) {
/*  33 */       SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
/*  34 */       return sdf.format(date);
/*     */     }
/*  36 */     return "";
/*     */   }
/*     */ 
/*     */   public static int getDaysOfMonth(Date date)
/*     */   {
/*  45 */     Calendar calendar = Calendar.getInstance();
/*  46 */     calendar.setTime(date);
/*  47 */     return calendar.getActualMaximum(5);
/*     */   }
/*     */ 
/*     */   public static Date pasreToDate(String date, String formatStr)
/*     */     throws ParseException
/*     */   {
/*  59 */     if (date != null) {
/*  60 */       SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
/*  61 */       return sdf.parse(date);
/*     */     }
/*  63 */     return null;
/*     */   }
/*     */ 
/*     */   public static Date getStartDate(Date date)
/*     */   {
/*  74 */     String temp = format.format(date);
/*  75 */     temp = temp + " 00:00:00";
/*     */     try
/*     */     {
/*  78 */       return format1.parse(temp); } catch (Exception e) {
/*     */     }
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */   public static Date getFinallyDate(Date date)
/*     */   {
/*  90 */     String temp = format.format(date);
/*  91 */     temp = temp + " 23:59:59";
/*     */     try
/*     */     {
/*  94 */       return format1.parse(temp); } catch (ParseException e) {
/*     */     }
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */   public static boolean isToday(Date date)
/*     */   {
/* 106 */     long day = date.getTime() / 1000L / 60L / 60L / 24L;
/* 107 */     long currentDay = System.currentTimeMillis() / 1000L / 60L / 60L / 24L;
/* 108 */     return day == currentDay;
/*     */   }
/*     */ 
/*     */   public static boolean isThisWeek(Date date)
/*     */   {
/* 118 */     List<Date> dates = dateToWeek(date);
/* 119 */     Boolean flag = Boolean.valueOf(false);
/* 120 */     for (Date d : dates) {
/* 121 */       if (isToday(d)) {
/* 122 */         flag = Boolean.valueOf(true);
/* 123 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 128 */     return flag.booleanValue();
/*     */   }
/*     */ 
/*     */   public static boolean isThisMonth(Date date)
/*     */   {
/* 138 */     long year = date.getYear();
/* 139 */     long month = date.getMonth();
/* 140 */     Calendar calendar = Calendar.getInstance();
/* 141 */     return (calendar.getTime().getYear() == year) && 
/* 142 */       (calendar.getTime().getMonth() == month);
/*     */   }
/*     */ 
/*     */   public static boolean isThisYear(Date date)
/*     */   {
/* 152 */     long year = date.getYear();
/* 153 */     Calendar calendar = Calendar.getInstance();
/* 154 */     return calendar.getTime().getYear() == year;
/*     */   }
/*     */ 
/*     */   public static List<Date> dateToWeek(Date mdate)
/*     */   {
/* 164 */     int day = mdate.getDay();
/*     */ 
/* 166 */     List list = new ArrayList();
/* 167 */     Long fTime = Long.valueOf(mdate.getTime() - day * 24 * 3600000);
/* 168 */     for (int i = 0; i < 7; i++) {
/* 169 */       Date fdate = new Date();
/* 170 */       fdate.setTime(fTime.longValue() + (i + 1) * 24 * 3600000);
/* 171 */       list.add(i, fdate);
/*     */     }
/* 173 */     return list;
/*     */   }
/*     */ 
/*     */   public static Double getDiffMinuteTwoDate(Date begin, Date end) {
/* 177 */     return Double.valueOf((end.getTime() - begin.getTime()) / 1000L / 60.0D);
/*     */   }
/*     */ 
/*     */   public static Double getDiffHourTwoDate(Date begin, Date end) {
/* 181 */     return Double.valueOf(getDiffHourTwoDate(begin, end).doubleValue() / 60.0D);
/*     */   }
/*     */ 
/*     */   public static final int getDaysBetween(Date early, Date late) {
/* 185 */     Calendar calst = Calendar.getInstance();
/* 186 */     Calendar caled = Calendar.getInstance();
/* 187 */     calst.setTime(early);
/* 188 */     caled.setTime(late);
/*     */ 
/* 190 */     calst.set(11, 0);
/* 191 */     calst.set(12, 0);
/* 192 */     calst.set(13, 0);
/* 193 */     caled.set(11, 0);
/* 194 */     caled.set(12, 0);
/* 195 */     caled.set(13, 0);
/*     */ 
/* 197 */     int days = ((int)(caled.getTime().getTime() / 1000L) - 
/* 198 */       (int)(calst
/* 198 */       .getTime().getTime() / 1000L)) / 3600 / 24;
/* 199 */     return days;
/*     */   }
/*     */ 
/*     */   public static String parseDate(Date date, String format)
/*     */   {
/* 204 */     SimpleDateFormat formater = new SimpleDateFormat(format);
/*     */ 
/* 206 */     String dateString = formater.format(date);
/* 207 */     return dateString;
/*     */   }
/*     */ 
/*     */   public static Date parseDateTimeToDay(Date date) {
/* 211 */     return parseDateFormat(date, "yyyy-MM-dd");
/*     */   }
/*     */ 
/*     */   public static Date parseDateFormat(Date date, String format)
/*     */   {
/* 216 */     String dateString = common_format.format(date);
/* 217 */     SimpleDateFormat dateformat = new SimpleDateFormat(format);
/*     */     try {
/* 219 */       return dateformat.parse(dateString);
/*     */     }
/*     */     catch (ParseException localParseException) {
/*     */     }
/* 223 */     return null;
/*     */   }
/*     */ 
/*     */   public static Date afterDate(Date date, Integer after) {
/* 227 */     Calendar calendar = Calendar.getInstance();
/* 228 */     calendar.setTime(date);
/* 229 */     calendar.set(5, calendar.get(5) + after.intValue());
/* 230 */     return calendar.getTime();
/*     */   }
/*     */ 
/*     */   public static String parseDateToTimeStr(Date date) {
/* 234 */     return common_format.format(date);
/*     */   }
/*     */ 
/*     */   public static String parseDateToDateStr(Date date) {
/* 238 */     return dayFormat.format(date);
/*     */   }
/*     */ 
/*     */   public static Date parseDayStrToDate(String dateStr) {
/*     */     try {
/* 243 */       return dayFormat.parse(dateStr); } catch (ParseException e) {
/*     */     }
/* 245 */     return null;
/*     */   }
/*     */ 
/*     */   public static Date parseTimeStrToDate(String dateStr)
/*     */   {
/*     */     try {
/* 251 */       return common_format.parse(dateStr); } catch (ParseException e) {
/*     */     }
/* 253 */     return null;
/*     */   }
/*     */ 
/*     */   public static Date getSpecficMonthStart(Date date, int amount)
/*     */   {
/* 265 */     if (date == null) {
/* 266 */       date = new Date();
/*     */     }
/* 268 */     Calendar cal = Calendar.getInstance();
/* 269 */     cal.setTime(date);
/* 270 */     cal.add(2, amount);
/* 271 */     cal.set(5, 1);
/* 272 */     return getStartDate(cal.getTime());
/*     */   }
/*     */ 
/*     */   public static Date getSpecficMonthEnd(Date date, int amount)
/*     */   {
/* 283 */     if (date == null) {
/* 284 */       date = new Date();
/*     */     }
/* 286 */     Calendar cal = Calendar.getInstance();
/* 287 */     cal.setTime(getSpecficMonthStart(date, amount + 1));
/* 288 */     cal.add(6, -1);
/* 289 */     return getFinallyDate(cal.getTime());
/*     */   }
/*     */ 
/*     */   public static Date getSpecficYearStart(Date date, int amount)
/*     */   {
/* 300 */     if (date == null) {
/* 301 */       date = new Date();
/*     */     }
/* 303 */     Calendar cal = Calendar.getInstance();
/* 304 */     cal.setTime(date);
/* 305 */     cal.add(1, amount);
/* 306 */     cal.set(6, 1);
/* 307 */     return getStartDate(cal.getTime());
/*     */   }
/*     */ 
/*     */   public static Date getSpecficYearEnd(Date date, int amount)
/*     */   {
/* 318 */     if (date == null) {
/* 319 */       date = new Date();
/*     */     }
/* 321 */     Date temp = getStartDate(getSpecficYearStart(date, amount + 1));
/* 322 */     Calendar cal = Calendar.getInstance();
/* 323 */     cal.setTime(temp);
/* 324 */     cal.add(6, -1);
/* 325 */     return getFinallyDate(cal.getTime());
/*     */   }
/*     */ 
/*     */   public static Date getSpecficDateStart(Date date, int amount) {
/* 329 */     if (date == null) {
/* 330 */       date = new Date();
/*     */     }
/* 332 */     Calendar cal = Calendar.getInstance();
/* 333 */     cal.setTime(date);
/* 334 */     cal.add(6, amount);
/* 335 */     return getStartDate(cal.getTime());
/*     */   }
/*     */ 
/*     */   public static Date getSpecficDateEnd(Date date, int amount) {
/* 339 */     if (date == null) {
/* 340 */       date = new Date();
/*     */     }
/* 342 */     Calendar cal = Calendar.getInstance();
/* 343 */     cal.setTime(date);
/* 344 */     cal.add(6, amount);
/* 345 */     return getFinallyDate(cal.getTime());
/*     */   }
/*     */ 
/*     */   public static Date getSpecficDate(Date date, int amount) {
/* 349 */     if (date == null) {
/* 350 */       date = new Date();
/*     */     }
/* 352 */     Calendar cal = Calendar.getInstance();
/* 353 */     cal.setTime(date);
/* 354 */     cal.add(6, amount);
/* 355 */     return cal.getTime();
/*     */   }
/*     */ 
/*     */   public static Date getDateMinute(Date date, int minute) {
/* 359 */     if (date == null) {
/* 360 */       date = new Date();
/*     */     }
/* 362 */     long t = date.getTime();
/* 363 */     t += minute * 60 * 1000;
/* 364 */     Date d = new Date(t);
/* 365 */     return d;
/*     */   }
/*     */ 
/*     */   public static int getCurrentMonth() {
/* 369 */     Calendar cal = Calendar.getInstance();
/* 370 */     return cal.get(2) + 1;
/*     */   }
/*     */ 
/*     */   public static int getCurrentDay() {
/* 374 */     Calendar cal = Calendar.getInstance();
/* 375 */     return cal.get(5);
/*     */   }
/*     */ 
/*     */   public static int getCurrentYear()
/*     */   {
/* 380 */     Calendar cal = Calendar.getInstance();
/* 381 */     return cal.get(1);
/*     */   }
/*     */ 
/*     */   public static void main(String[] arg)
/*     */   {
/* 387 */     String d = "2017-9-24 11:13:45";
/*     */ 
/* 390 */     Long l = Long.valueOf(1505145599L);
/*     */ 
/* 394 */     for (int i = 0; i < 10000; i++) {
/* 395 */       System.out.print(i + "----" + getFinallyDate(new Date()));
/*     */     }
/*     */ 
/* 398 */     List<Date> list = dateToWeek(new Date());
/* 399 */     for (Date s : list)
/* 400 */       System.out.println(s);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.util.DateUtils
 * JD-Core Version:    0.6.0
 */