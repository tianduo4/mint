/*     */ package com.jspgou.common.upload;
/*     */ 
/*     */ import com.jspgou.common.util.Num62;
/*     */ import java.io.File;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.lang.RandomStringUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class UploadUtils
/*     */ {
/*  24 */   public static final DateFormat MONTH_FORMAT = new SimpleDateFormat(
/*  25 */     "/yyyyMM/ddHHmmss");
/*     */ 
/*  27 */   public static final DateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat(
/*  28 */     "yyyyMM");
/*     */ 
/*  56 */   protected static final Pattern ILLEGAL_CURRENT_FOLDER_PATTERN = Pattern.compile("^[^/]|[^/]$|/\\.{1,2}|\\\\|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}");
/*     */ 
/*     */   public static String generateFilename(String path, String ext)
/*     */   {
/*  37 */     return path + MONTH_FORMAT.format(new Date()) + 
/*  38 */       RandomStringUtils.random(4, Num62.N36_CHARS) + "." + ext;
/*     */   }
/*     */ 
/*     */   public static String generateRamdonFilename(String ext) {
/*  42 */     return MONTH_FORMAT.format(Calendar.getInstance().getTime()) + 
/*  43 */       RandomStringUtils.random(4, Num62.N36_CHARS) + "." + ext;
/*     */   }
/*     */ 
/*     */   public static String generateMonthname() {
/*  47 */     return YEAR_MONTH_FORMAT.format(new Date());
/*     */   }
/*     */ 
/*     */   public static String generateByFilename(String path, String fileName, String ext) {
/*  51 */     return path + fileName + "." + ext;
/*     */   }
/*     */ 
/*     */   public static String sanitizeFileName(String filename)
/*     */   {
/*  71 */     if (StringUtils.isBlank(filename)) {
/*  72 */       return filename;
/*     */     }
/*  74 */     String name = forceSingleExtension(filename);
/*     */ 
/*  77 */     return name.replaceAll("\\\\|/|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_");
/*     */   }
/*     */ 
/*     */   public static String sanitizeFolderName(String folderName)
/*     */   {
/*  92 */     if (StringUtils.isBlank(folderName)) {
/*  93 */       return folderName;
/*     */     }
/*     */ 
/*  96 */     return folderName.replaceAll(
/*  97 */       "\\.|\\\\|/|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_");
/*     */   }
/*     */ 
/*     */   public static boolean isValidPath(String path)
/*     */   {
/* 110 */     if (StringUtils.isBlank(path)) {
/* 111 */       return false;
/*     */     }
/*     */ 
/* 114 */     return !ILLEGAL_CURRENT_FOLDER_PATTERN.matcher(path).find();
/*     */   }
/*     */ 
/*     */   public static String forceSingleExtension(String filename)
/*     */   {
/* 127 */     return filename.replaceAll("\\.(?![^.]+$)", "_");
/*     */   }
/*     */ 
/*     */   public static boolean isSingleExtension(String filename)
/*     */   {
/* 139 */     return filename.matches("[^\\.]+\\.[^\\.]+");
/*     */   }
/*     */ 
/*     */   public static void checkDirAndCreate(File dir)
/*     */   {
/* 149 */     if (!dir.exists())
/* 150 */       dir.mkdirs();
/*     */   }
/*     */ 
/*     */   public static File getUniqueFile(File file)
/*     */   {
/* 164 */     if (!file.exists()) {
/* 165 */       return file;
/*     */     }
/* 167 */     File tmpFile = new File(file.getAbsolutePath());
/* 168 */     File parentDir = tmpFile.getParentFile();
/* 169 */     int count = 1;
/* 170 */     String extension = FilenameUtils.getExtension(tmpFile.getName());
/* 171 */     String baseName = FilenameUtils.getBaseName(tmpFile.getName());
/*     */     do
/* 173 */       tmpFile = new File(parentDir, baseName + "(" + count++ + ")." + 
/* 174 */         extension);
/* 175 */     while (tmpFile.exists());
/* 176 */     return tmpFile;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.upload.UploadUtils
 * JD-Core Version:    0.6.0
 */