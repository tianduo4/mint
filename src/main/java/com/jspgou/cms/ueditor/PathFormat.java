/*     */ package com.jspgou.cms.ueditor;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class PathFormat
/*     */ {
/*     */   private static final String TIME = "time";
/*     */   private static final String FULL_YEAR = "yyyy";
/*     */   private static final String YEAR = "yy";
/*     */   private static final String MONTH = "mm";
/*     */   private static final String DAY = "dd";
/*     */   private static final String HOUR = "hh";
/*     */   private static final String MINUTE = "ii";
/*     */   private static final String SECOND = "ss";
/*     */   private static final String RAND = "rand";
/*  20 */   private static Date currentDate = null;
/*     */ 
/*     */   public static String parse(String input)
/*     */   {
/*  24 */     Pattern pattern = Pattern.compile("\\{([^\\}]+)\\}", 2);
/*  25 */     Matcher matcher = pattern.matcher(input);
/*     */ 
/*  27 */     currentDate = new Date();
/*     */ 
/*  29 */     StringBuffer sb = new StringBuffer();
/*     */ 
/*  31 */     while (matcher.find())
/*     */     {
/*  33 */       matcher.appendReplacement(sb, getString(matcher.group(1)));
/*     */     }
/*     */ 
/*  37 */     matcher.appendTail(sb);
/*     */ 
/*  39 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String format(String input)
/*     */   {
/*  49 */     return input.replace("\\", "/");
/*     */   }
/*     */ 
/*     */   public static String parse(String input, String filename)
/*     */   {
/*  55 */     Pattern pattern = Pattern.compile("\\{([^\\}]+)\\}", 2);
/*  56 */     Matcher matcher = pattern.matcher(input);
/*  57 */     String matchStr = null;
/*     */ 
/*  59 */     currentDate = new Date();
/*     */ 
/*  61 */     StringBuffer sb = new StringBuffer();
/*     */ 
/*  63 */     while (matcher.find())
/*     */     {
/*  65 */       matchStr = matcher.group(1);
/*  66 */       if (matchStr.indexOf("filename") != -1) {
/*  67 */         filename = filename.replace("$", "\\$").replaceAll("[\\/:*?\"<>|]", "");
/*  68 */         matcher.appendReplacement(sb, filename);
/*     */       } else {
/*  70 */         matcher.appendReplacement(sb, getString(matchStr));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  75 */     matcher.appendTail(sb);
/*     */ 
/*  77 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private static String getString(String pattern)
/*     */   {
/*  82 */     pattern = pattern.toLowerCase();
/*     */ 
/*  85 */     if (pattern.indexOf("time") != -1)
/*  86 */       return getTimestamp();
/*  87 */     if (pattern.indexOf("yyyy") != -1)
/*  88 */       return getFullYear();
/*  89 */     if (pattern.indexOf("yy") != -1)
/*  90 */       return getYear();
/*  91 */     if (pattern.indexOf("mm") != -1)
/*  92 */       return getMonth();
/*  93 */     if (pattern.indexOf("dd") != -1)
/*  94 */       return getDay();
/*  95 */     if (pattern.indexOf("hh") != -1)
/*  96 */       return getHour();
/*  97 */     if (pattern.indexOf("ii") != -1)
/*  98 */       return getMinute();
/*  99 */     if (pattern.indexOf("ss") != -1)
/* 100 */       return getSecond();
/* 101 */     if (pattern.indexOf("rand") != -1) {
/* 102 */       return getRandom(pattern);
/*     */     }
/*     */ 
/* 105 */     return pattern;
/*     */   }
/*     */ 
/*     */   private static String getTimestamp()
/*     */   {
/* 110 */     return System.currentTimeMillis()+"";
/*     */   }
/*     */ 
/*     */   private static String getFullYear() {
/* 114 */     return new SimpleDateFormat("yyyy").format(currentDate);
/*     */   }
/*     */ 
/*     */   private static String getYear() {
/* 118 */     return new SimpleDateFormat("yy").format(currentDate);
/*     */   }
/*     */ 
/*     */   private static String getMonth() {
/* 122 */     return new SimpleDateFormat("MM").format(currentDate);
/*     */   }
/*     */ 
/*     */   private static String getDay() {
/* 126 */     return new SimpleDateFormat("dd").format(currentDate);
/*     */   }
/*     */ 
/*     */   private static String getHour() {
/* 130 */     return new SimpleDateFormat("HH").format(currentDate);
/*     */   }
/*     */ 
/*     */   private static String getMinute() {
/* 134 */     return new SimpleDateFormat("mm").format(currentDate);
/*     */   }
/*     */ 
/*     */   private static String getSecond() {
/* 138 */     return new SimpleDateFormat("ss").format(currentDate);
/*     */   }
/*     */ 
/*     */   private static String getRandom(String pattern)
/*     */   {
/* 143 */     int length = 0;
/* 144 */     pattern = pattern.split(":")[1].trim();
/*     */ 
/* 146 */     length = Integer.parseInt(pattern);
/*     */ 
/* 148 */     return Math.random()+"".replace(".", "").substring(0, length);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.ueditor.PathFormat
 * JD-Core Version:    0.6.0
 */