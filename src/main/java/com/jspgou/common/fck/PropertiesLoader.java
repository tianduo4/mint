/*     */ package com.jspgou.common.fck;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class PropertiesLoader
/*     */ {
/*  16 */   private static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
/*     */   private static final String DEFAULT_FILENAME = "default.properties";
/*     */   private static final String LOCAL_PROPERTIES = "/fckeditor.properties";
/*  19 */   private static Properties properties = new Properties();
/*     */ 
/*     */   static
/*     */   {
/*  24 */     InputStream in = PropertiesLoader.class
/*  25 */       .getResourceAsStream("default.properties");
/*     */ 
/*  27 */     if (in == null) {
/*  28 */       logger.error("{} not found", "default.properties");
/*  29 */       throw new RuntimeException("default.properties not found");
/*     */     }
/*  31 */     if (!(in instanceof BufferedInputStream))
/*  32 */       in = new BufferedInputStream(in);
/*     */     try
/*     */     {
/*  35 */       properties.load(in);
/*  36 */       in.close();
/*  37 */       logger.debug("{} loaded", "default.properties");
/*     */     } catch (Exception e) {
/*  39 */       logger.error("Error while processing {}", "default.properties");
/*  40 */       throw new RuntimeException("Error while processing default.properties", 
/*  41 */         e);
/*     */     }
/*     */ 
/*  46 */     InputStream in2 = PropertiesLoader.class
/*  47 */       .getResourceAsStream("/fckeditor.properties");
/*     */ 
/*  49 */     if (in2 == null) {
/*  50 */       logger.info("{} not found", "/fckeditor.properties");
/*     */     }
/*     */     else {
/*  53 */       if (!(in2 instanceof BufferedInputStream))
/*  54 */         in2 = new BufferedInputStream(in2);
/*     */       try
/*     */       {
/*  57 */         properties.load(in2);
/*  58 */         in2.close();
/*  59 */         logger.debug("{} loaded", "/fckeditor.properties");
/*     */       } catch (Exception e) {
/*  61 */         logger.error("Error while processing {}", "/fckeditor.properties");
/*  62 */         throw new RuntimeException("Error while processing /fckeditor.properties", 
/*  63 */           e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getProperty(String key)
/*     */   {
/*  75 */     return properties.getProperty(key);
/*     */   }
/*     */ 
/*     */   public static void setProperty(String key, String value)
/*     */   {
/*  84 */     properties.setProperty(key, value);
/*     */   }
/*     */ 
/*     */   public static String getFileResourceTypePath()
/*     */   {
/*  91 */     return properties.getProperty("connector.resourceType.file.path");
/*     */   }
/*     */ 
/*     */   public static String getFlashResourceTypePath()
/*     */   {
/*  98 */     return properties.getProperty("connector.resourceType.flash.path");
/*     */   }
/*     */ 
/*     */   public static String getImageResourceTypePath()
/*     */   {
/* 105 */     return properties.getProperty("connector.resourceType.image.path");
/*     */   }
/*     */ 
/*     */   public static String getMediaResourceTypePath()
/*     */   {
/* 112 */     return properties.getProperty("connector.resourceType.media.path");
/*     */   }
/*     */ 
/*     */   public static String getFileResourceTypeAllowedExtensions()
/*     */   {
/* 120 */     return properties
/* 121 */       .getProperty("connector.resourceType.file.extensions.allowed");
/*     */   }
/*     */ 
/*     */   public static String getFileResourceTypeDeniedExtensions()
/*     */   {
/* 129 */     return properties
/* 130 */       .getProperty("connector.resourceType.file.extensions.denied");
/*     */   }
/*     */ 
/*     */   public static String getFlashResourceTypeAllowedExtensions()
/*     */   {
/* 138 */     return properties
/* 139 */       .getProperty("connector.resourceType.flash.extensions.allowed");
/*     */   }
/*     */ 
/*     */   public static String getFlashResourceTypeDeniedExtensions()
/*     */   {
/* 147 */     return properties
/* 148 */       .getProperty("connector.resourceType.flash.extensions.denied");
/*     */   }
/*     */ 
/*     */   public static String getImageResourceTypeAllowedExtensions()
/*     */   {
/* 156 */     return properties
/* 157 */       .getProperty("connector.resourceType.image.extensions.allowed");
/*     */   }
/*     */ 
/*     */   public static String getImageResourceTypeDeniedExtensions()
/*     */   {
/* 165 */     return properties
/* 166 */       .getProperty("connector.resourceType.image.extensions.denied");
/*     */   }
/*     */ 
/*     */   public static String getMediaResourceTypeAllowedExtensions()
/*     */   {
/* 174 */     return properties
/* 175 */       .getProperty("connector.resourceType.media.extensions.allowed");
/*     */   }
/*     */ 
/*     */   public static String getMediaResourceTypeDeniedExtensions()
/*     */   {
/* 183 */     return properties
/* 184 */       .getProperty("connector.resourceType.media.extensions.denied");
/*     */   }
/*     */ 
/*     */   public static String getUserFilesPath()
/*     */   {
/* 191 */     return properties.getProperty("connector.userFilesPath");
/*     */   }
/*     */ 
/*     */   public static String getUserFilesAbsolutePath()
/*     */   {
/* 198 */     return properties.getProperty("connector.userFilesAbsolutePath");
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.fck.PropertiesLoader
 * JD-Core Version:    0.6.0
 */