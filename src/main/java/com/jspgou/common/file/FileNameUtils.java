/*    */ package com.jspgou.common.file;
/*    */ 
/*    */ import com.jspgou.common.image.ImageUtils;
/*    */ import com.jspgou.common.util.Num62;
/*    */ import java.io.PrintStream;
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import org.apache.commons.lang.RandomStringUtils;
/*    */ 
/*    */ public class FileNameUtils
/*    */ {
/* 23 */   public static final DateFormat pathDf = new SimpleDateFormat("yyyyMM");
/*    */ 
/* 27 */   public static final DateFormat nameDf = new SimpleDateFormat("ddHHmmss");
/*    */ 
/*    */   public static String genPathName()
/*    */   {
/* 37 */     return pathDf.format(new Date());
/*    */   }
/*    */ 
/*    */   public static String genFileName()
/*    */   {
/* 48 */     return nameDf.format(new Date()) + 
/* 49 */       RandomStringUtils.random(4, Num62.N36_CHARS);
/*    */   }
/*    */ 
/*    */   public static String genFileName(String ext)
/*    */   {
/* 60 */     return genFileName() + "." + ext;
/*    */   }
/*    */ 
/*    */   public static String getFileSufix(String fileName) {
/* 64 */     boolean normalImg = false;
/* 65 */     for (String imgExt : ImageUtils.IMAGE_EXT) {
/* 66 */       if (fileName.endsWith(imgExt)) {
/* 67 */         normalImg = true;
/*    */       }
/*    */     }
/* 70 */     String suffix = "";
/* 71 */     if (normalImg) {
/* 72 */       int splitIndex = fileName.lastIndexOf(".");
/* 73 */       suffix = fileName.substring(splitIndex + 1);
/*    */     } else {
/* 75 */       suffix = ImageUtils.IMAGE_EXT[0];
/*    */     }
/* 77 */     return suffix;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 81 */     System.out.println(genPathName());
/* 82 */     System.out.println(genFileName());
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.file.FileNameUtils
 * JD-Core Version:    0.6.0
 */