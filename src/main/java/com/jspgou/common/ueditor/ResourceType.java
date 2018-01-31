/*     */ package com.jspgou.common.ueditor;
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
/*  35 */   private static Map<String, ResourceType> types = new HashMap(
/*  36 */     4);
/*     */ 
/*  39 */   public static final ResourceType FILE = new ResourceType("File", 
/*  40 */     PropertiesLoader.getFileResourceTypePath(), 
/*  41 */     Utils.getSet(
/*  42 */     PropertiesLoader.getFileResourceTypeAllowedExtensions()), 
/*  43 */     Utils.getSet(
/*  44 */     PropertiesLoader.getFileResourceTypeDeniedExtensions()));
/*     */ 
/*  46 */   public static final ResourceType FLASH = new ResourceType("Flash", 
/*  47 */     PropertiesLoader.getFlashResourceTypePath(), Utils.getSet(PropertiesLoader.getFlashResourceTypeAllowedExtensions()), 
/*  48 */     Utils.getSet(
/*  49 */     PropertiesLoader.getFlashResourceTypeDeniedExtensions()));
/*     */ 
/*  51 */   public static final ResourceType IMAGE = new ResourceType("Image", 
/*  52 */     PropertiesLoader.getImageResourceTypePath(), 
/*  53 */     Utils.getSet(
/*  54 */     PropertiesLoader.getImageResourceTypeAllowedExtensions()), 
/*  55 */     Utils.getSet(
/*  56 */     PropertiesLoader.getImageResourceTypeDeniedExtensions()));
/*     */ 
/*  58 */   public static final ResourceType MEDIA = new ResourceType("Media", 
/*  59 */     PropertiesLoader.getMediaResourceTypePath(), 
/*  60 */     Utils.getSet(
/*  61 */     PropertiesLoader.getMediaResourceTypeAllowedExtensions()), 
/*  62 */     Utils.getSet(
/*  63 */     PropertiesLoader.getMediaResourceTypeDeniedExtensions()));
/*     */ 
/*     */   static
/*     */   {
/*  66 */     types.put(FILE.getName(), FILE);
/*  67 */     types.put(FLASH.getName(), FLASH);
/*  68 */     types.put(IMAGE.getName(), IMAGE);
/*  69 */     types.put(MEDIA.getName(), MEDIA);
/*     */   }
/*     */ 
/*     */   private ResourceType(String name, String path, Set<String> allowedEextensions, Set<String> deniedExtensions)
/*     */   {
/*  92 */     this.name = name;
/*  93 */     this.path = path;
/*     */ 
/*  95 */     if ((allowedEextensions.isEmpty()) && (deniedExtensions.isEmpty())) {
/*  96 */       throw new IllegalArgumentException(
/*  97 */         "Both sets are empty, one has always to be filled");
/*     */     }
/*  99 */     if ((!allowedEextensions.isEmpty()) && (!deniedExtensions.isEmpty())) {
/* 100 */       throw new IllegalArgumentException(
/* 101 */         "Both sets contain extensions, only one can be filled at the same time");
/*     */     }
/* 103 */     this.allowedEextensions = allowedEextensions;
/* 104 */     this.deniedExtensions = deniedExtensions;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 113 */     return this.name;
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/* 124 */     return this.path;
/*     */   }
/*     */ 
/*     */   public Set<String> getAllowedEextensions()
/*     */   {
/* 133 */     return Collections.unmodifiableSet(this.allowedEextensions);
/*     */   }
/*     */ 
/*     */   public Set<String> getDeniedExtensions()
/*     */   {
/* 142 */     return Collections.unmodifiableSet(this.deniedExtensions);
/*     */   }
/*     */ 
/*     */   public static ResourceType valueOf(String name)
/*     */   {
/* 157 */     if (Utils.isEmpty(name)) {
/* 158 */       throw new NullPointerException("Name is null or empty");
/*     */     }
/* 160 */     ResourceType rt = (ResourceType)types.get(name);
/* 161 */     if (rt == null)
/* 162 */       throw new IllegalArgumentException("No resource type const " + name);
/* 163 */     return rt;
/*     */   }
/*     */ 
/*     */   public static boolean isValidType(String name)
/*     */   {
/* 177 */     return types.containsKey(name);
/*     */   }
/*     */ 
/*     */   public static ResourceType getResourceType(String name)
/*     */   {
/*     */     try
/*     */     {
/* 192 */       return valueOf(name); } catch (Exception e) {
/*     */     }
/* 194 */     return null;
/*     */   }
/*     */ 
/*     */   public static ResourceType getDefaultResourceType(String name)
/*     */   {
/* 209 */     ResourceType rt = getResourceType(name);
/* 210 */     return rt == null ? FILE : rt;
/*     */   }
/*     */ 
/*     */   public boolean isAllowedExtension(String extension)
/*     */   {
/* 225 */     if (Utils.isEmpty(extension))
/* 226 */       return false;
/* 227 */     String ext = extension.toLowerCase();
/* 228 */     if (this.allowedEextensions.isEmpty())
/* 229 */       return !this.deniedExtensions.contains(ext);
/* 230 */     if (this.deniedExtensions.isEmpty())
/* 231 */       return this.allowedEextensions.contains(ext);
/* 232 */     return false;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public boolean isNotAllowedExtension(String extension)
/*     */   {
/* 249 */     return !isAllowedExtension(extension);
/*     */   }
/*     */ 
/*     */   public boolean isDeniedExtension(String extension)
/*     */   {
/* 261 */     return !isAllowedExtension(extension);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 275 */     if (this == obj) {
/* 276 */       return true;
/*     */     }
/* 278 */     if ((obj == null) || (getClass() != obj.getClass())) {
/* 279 */       return false;
/*     */     }
/* 281 */     ResourceType rt = (ResourceType)obj;
/* 282 */     return this.name.equals(rt.getName());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 293 */     return this.name.hashCode();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.ueditor.ResourceType
 * JD-Core Version:    0.6.0
 */