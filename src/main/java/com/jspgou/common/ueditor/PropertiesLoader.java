/*     */ package com.jspgou.common.ueditor;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class PropertiesLoader
/*     */ {
/*  31 */   private static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
/*     */   private static final String DEFAULT_FILENAME = "default.properties";
/*     */   private static final String LOCAL_PROPERTIES = "/fckeditor.properties";
/*  34 */   private static Properties properties = new Properties();
/*     */ 
/*     */   static
/*     */   {
/*  39 */     InputStream in = PropertiesLoader.class
/*  40 */       .getResourceAsStream("default.properties");
/*     */ 
/*  42 */     if (in == null) {
/*  43 */       logger.error("{} not found", "default.properties");
/*  44 */       throw new RuntimeException("default.properties not found");
/*     */     }
/*  46 */     if (!(in instanceof BufferedInputStream))
/*  47 */       in = new BufferedInputStream(in);
/*     */     try
/*     */     {
/*  50 */       properties.load(in);
/*  51 */       in.close();
/*  52 */       logger.debug("{} loaded", "default.properties");
/*     */     } catch (Exception e) {
/*  54 */       logger.error("Error while processing {}", "default.properties");
/*  55 */       throw new RuntimeException("Error while processing default.properties", 
/*  56 */         e);
/*     */     }
/*     */ 
/*  61 */     InputStream in2 = PropertiesLoader.class
/*  62 */       .getResourceAsStream("/fckeditor.properties");
/*     */ 
/*  64 */     if (in2 == null) {
/*  65 */       logger.info("{} not found", "/fckeditor.properties");
/*     */     }
/*     */     else {
/*  68 */       if (!(in2 instanceof BufferedInputStream))
/*  69 */         in2 = new BufferedInputStream(in2);
/*     */       try
/*     */       {
/*  72 */         properties.load(in2);
/*  73 */         in2.close();
/*  74 */         logger.debug("{} loaded", "/fckeditor.properties");
/*     */       } catch (Exception e) {
/*  76 */         logger.error("Error while processing {}", "/fckeditor.properties");
/*  77 */         throw new RuntimeException("Error while processing /fckeditor.properties", 
/*  78 */           e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getProperty(String key)
/*     */   {
/*  90 */     return properties.getProperty(key);
/*     */   }
/*     */ 
/*     */   public static void setProperty(String key, String value)
/*     */   {
/*  99 */     properties.setProperty(key, value);
/*     */   }
/*     */ 
/*     */   public static String getFileResourceTypePath()
/*     */   {
/* 106 */     return properties.getProperty("connector.resourceType.file.path");
/*     */   }
/*     */ 
/*     */   public static String getFlashResourceTypePath()
/*     */   {
/* 113 */     return properties.getProperty("connector.resourceType.flash.path");
/*     */   }
/*     */ 
/*     */   public static String getImageResourceTypePath()
/*     */   {
/* 120 */     return properties.getProperty("connector.resourceType.image.path");
/*     */   }
/*     */ 
/*     */   public static String getMediaResourceTypePath()
/*     */   {
/* 127 */     return properties.getProperty("connector.resourceType.media.path");
/*     */   }
/*     */ 
/*     */   public static String getFileResourceTypeAllowedExtensions()
/*     */   {
/* 135 */     return properties
/* 136 */       .getProperty("connector.resourceType.file.extensions.allowed");
/*     */   }
/*     */ 
/*     */   public static String getFileResourceTypeDeniedExtensions()
/*     */   {
/* 144 */     return properties
/* 145 */       .getProperty("connector.resourceType.file.extensions.denied");
/*     */   }
/*     */ 
/*     */   public static String getFlashResourceTypeAllowedExtensions()
/*     */   {
/* 153 */     return properties
/* 154 */       .getProperty("connector.resourceType.flash.extensions.allowed");
/*     */   }
/*     */ 
/*     */   public static String getFlashResourceTypeDeniedExtensions()
/*     */   {
/* 162 */     return properties
/* 163 */       .getProperty("connector.resourceType.flash.extensions.denied");
/*     */   }
/*     */ 
/*     */   public static String getImageResourceTypeAllowedExtensions()
/*     */   {
/* 171 */     return properties
/* 172 */       .getProperty("connector.resourceType.image.extensions.allowed");
/*     */   }
/*     */ 
/*     */   public static String getImageResourceTypeDeniedExtensions()
/*     */   {
/* 180 */     return properties
/* 181 */       .getProperty("connector.resourceType.image.extensions.denied");
/*     */   }
/*     */ 
/*     */   public static String getMediaResourceTypeAllowedExtensions()
/*     */   {
/* 189 */     return properties
/* 190 */       .getProperty("connector.resourceType.media.extensions.allowed");
/*     */   }
/*     */ 
/*     */   public static String getMediaResourceTypeDeniedExtensions()
/*     */   {
/* 198 */     return properties
/* 199 */       .getProperty("connector.resourceType.media.extensions.denied");
/*     */   }
/*     */ 
/*     */   public static String getUserFilesPath()
/*     */   {
/* 206 */     return properties.getProperty("connector.userFilesPath");
/*     */   }
/*     */ 
/*     */   public static String getUserFilesAbsolutePath()
/*     */   {
/* 213 */     return properties.getProperty("connector.userFilesAbsolutePath");
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.ueditor.PropertiesLoader
 * JD-Core Version:    0.6.0
 */