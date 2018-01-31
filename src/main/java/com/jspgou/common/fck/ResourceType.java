/*     */ package com.jspgou.common.fck;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ResourceType
/*     */ {
/*     */   private String name;
/*     */   private String path;
/*     */   private Set<String> allowedEextensions;
/*     */   private Set<String> deniedExtensions;
/*  24 */   private static Map<String, ResourceType> types = new HashMap(
/*  25 */     4);
/*     */ 
/*  28 */   public static final ResourceType FILE = new ResourceType("File", 
/*  29 */     PropertiesLoader.getFileResourceTypePath(), 
/*  30 */     Utils.getSet(
/*  31 */     PropertiesLoader.getFileResourceTypeAllowedExtensions()), 
/*  32 */     Utils.getSet(
/*  33 */     PropertiesLoader.getFileResourceTypeDeniedExtensions()));
/*     */ 
/*  35 */   public static final ResourceType FLASH = new ResourceType("Flash", 
/*  36 */     PropertiesLoader.getFlashResourceTypePath(), 
/*  37 */     Utils.getSet(
/*  38 */     PropertiesLoader.getFlashResourceTypeAllowedExtensions()), 
/*  39 */     Utils.getSet(
/*  40 */     PropertiesLoader.getFlashResourceTypeDeniedExtensions()));
/*     */ 
/*  42 */   public static final ResourceType IMAGE = new ResourceType("Image", 
/*  43 */     PropertiesLoader.getImageResourceTypePath(), 
/*  44 */     Utils.getSet(
/*  45 */     PropertiesLoader.getImageResourceTypeAllowedExtensions()), 
/*  46 */     Utils.getSet(
/*  47 */     PropertiesLoader.getImageResourceTypeDeniedExtensions()));
/*     */ 
/*  49 */   public static final ResourceType MEDIA = new ResourceType("Media", 
/*  50 */     PropertiesLoader.getMediaResourceTypePath(), 
/*  51 */     Utils.getSet(
/*  52 */     PropertiesLoader.getMediaResourceTypeAllowedExtensions()), 
/*  53 */     Utils.getSet(
/*  54 */     PropertiesLoader.getMediaResourceTypeDeniedExtensions()));
/*     */ 
/*     */   static
/*     */   {
/*  57 */     types.put(FILE.getName(), FILE);
/*  58 */     types.put(FLASH.getName(), FLASH);
/*  59 */     types.put(IMAGE.getName(), IMAGE);
/*  60 */     types.put(MEDIA.getName(), MEDIA);
/*     */   }
/*     */ 
/*     */   private ResourceType(String name, String path, Set<String> allowedEextensions, Set<String> deniedExtensions)
/*     */   {
/*  83 */     this.name = name;
/*  84 */     this.path = path;
/*     */ 
/*  86 */     if ((allowedEextensions.isEmpty()) && (deniedExtensions.isEmpty())) {
/*  87 */       throw new IllegalArgumentException(
/*  88 */         "Both sets are empty, one has always to be filled");
/*     */     }
/*  90 */     if ((!allowedEextensions.isEmpty()) && (!deniedExtensions.isEmpty())) {
/*  91 */       throw new IllegalArgumentException(
/*  92 */         "Both sets contain extensions, only one can be filled at the same time");
/*     */     }
/*  94 */     this.allowedEextensions = allowedEextensions;
/*  95 */     this.deniedExtensions = deniedExtensions;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 104 */     return this.name;
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/* 115 */     return this.path;
/*     */   }
/*     */ 
/*     */   public Set<String> getAllowedEextensions()
/*     */   {
/* 124 */     return Collections.unmodifiableSet(this.allowedEextensions);
/*     */   }
/*     */ 
/*     */   public Set<String> getDeniedExtensions()
/*     */   {
/* 133 */     return Collections.unmodifiableSet(this.deniedExtensions);
/*     */   }
/*     */ 
/*     */   public static ResourceType valueOf(String name)
/*     */   {
/* 148 */     if (Utils.isEmpty(name)) {
/* 149 */       throw new NullPointerException("Name is null or empty");
/*     */     }
/* 151 */     ResourceType rt = (ResourceType)types.get(name);
/* 152 */     if (rt == null)
/* 153 */       throw new IllegalArgumentException("No resource type const " + name);
/* 154 */     return rt;
/*     */   }
/*     */ 
/*     */   public static boolean isValidType(String name)
/*     */   {
/* 168 */     return types.containsKey(name);
/*     */   }
/*     */ 
/*     */   public static ResourceType getResourceType(String name)
/*     */   {
/*     */     try
/*     */     {
/* 183 */       return valueOf(name); } catch (Exception e) {
/*     */     }
/* 185 */     return null;
/*     */   }
/*     */ 
/*     */   public static ResourceType getDefaultResourceType(String name)
/*     */   {
/* 200 */     ResourceType rt = getResourceType(name);
/* 201 */     return rt == null ? FILE : rt;
/*     */   }
/*     */ 
/*     */   public boolean isAllowedExtension(String extension)
/*     */   {
/* 216 */     if (Utils.isEmpty(extension))
/* 217 */       return false;
/* 218 */     String ext = extension.toLowerCase();
/* 219 */     if (this.allowedEextensions.isEmpty())
/* 220 */       return !this.deniedExtensions.contains(ext);
/* 221 */     if (this.deniedExtensions.isEmpty())
/* 222 */       return this.allowedEextensions.contains(ext);
/* 223 */     return false;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public boolean isNotAllowedExtension(String extension)
/*     */   {
/* 240 */     return !isAllowedExtension(extension);
/*     */   }
/*     */ 
/*     */   public boolean isDeniedExtension(String extension)
/*     */   {
/* 252 */     return !isAllowedExtension(extension);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 266 */     if (this == obj) {
/* 267 */       return true;
/*     */     }
/* 269 */     if ((obj == null) || (getClass() != obj.getClass())) {
/* 270 */       return false;
/*     */     }
/* 272 */     ResourceType rt = (ResourceType)obj;
/* 273 */     return this.name.equals(rt.getName());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 284 */     return this.name.hashCode();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.fck.ResourceType
 * JD-Core Version:    0.6.0
 */