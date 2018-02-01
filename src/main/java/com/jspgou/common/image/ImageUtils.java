/*     */ package com.jspgou.common.image;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public abstract class ImageUtils
/*     */ {
/*  17 */   public static final String[] IMAGE_EXT = { "jpg", "jpeg", 
/*  18 */     "gif", "png", "bmp" };
/*     */ 
/*     */   public static boolean isValidImageExt(String ext)
/*     */   {
/*  27 */     ext = ext.toLowerCase(Locale.ENGLISH);
/*  28 */     for (String s : IMAGE_EXT) {
/*  29 */       if (s.equalsIgnoreCase(ext)) {
/*  30 */         return true;
/*     */       }
/*     */     }
/*  33 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean isImage(InputStream in)
/*     */   {
/*  45 */     ImageInfo ii = new ImageInfo();
/*  46 */     ii.setInput(in);
/*  47 */     return ii.check();
/*     */   }
/*     */ 
/*     */   public static Position markPosition(int width, int height, int p, int offsetx, int offsety)
/*     */   {
/*  67 */     if ((p < 1) || (p > 5))
/*  68 */       p = (int)(Math.random() * 5.0D) + 1;
/*     */     int y;
              int x;
/*  71 */     switch (p)
/*     */     {
/*     */     case 1:
/*  74 */        x = offsetx;
/*  75 */       y = offsety;
/*  76 */       break;
/*     */     case 2:
/*  79 */        x = width + offsetx;
/*  80 */       y = offsety;
/*  81 */       break;
/*     */     case 3:
/*  84 */        x = offsetx;
/*  85 */       y = height + offsety;
/*  86 */       break;
/*     */     case 4:
/*  89 */        x = width + offsetx;
/*  90 */       y = height + offsety;
/*  91 */       break;
/*     */     case 5:
/*  94 */        x = width / 2 + offsetx;
/*  95 */       y = height / 2 + offsety;
/*  96 */       break;
/*     */     default:
/*  98 */       throw new RuntimeException("never reach ...");
/*     */     }
/* 100 */     return new Position(x, y);
/*     */   }
/*     */ 
/*     */   public static List<String> getImageSrc(String htmlCode)
/*     */   {
/* 136 */     List imageSrcList = new ArrayList();
/* 137 */     String regular = "<img(.*?)src=\"(.*?)\"";
/* 138 */     String img_pre = "(?i)<img(.*?)src=\"";
/* 139 */     String img_sub = "\"";
/* 140 */     Pattern p = Pattern.compile(regular, 2);
/* 141 */     Matcher m = p.matcher(htmlCode);
/* 142 */     String src = null;
/* 143 */     while (m.find()) {
/* 144 */       src = m.group();
/* 145 */       src = src.replaceAll(img_pre, "").replaceAll(img_sub, "").trim();
/* 146 */       imageSrcList.add(src);
/*     */     }
/* 148 */     return imageSrcList;
/*     */   }
/*     */ 
/*     */   public static boolean isImage(String s)
/*     */   {
/* 161 */     s = s.toLowerCase();
/* 162 */     String[] as = IMAGE_EXT;
/* 163 */     int i = as.length;
/* 164 */     for (int j = 0; j < i; j++) {
/* 165 */       String s1 = as[j];
/* 166 */       if (s1.equals(s))
/* 167 */         return true;
/*     */     }
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */   public static class Position
/*     */   {
/*     */     private int x;
/*     */     private int y;
/*     */ 
/*     */     public Position(int x, int y)
/*     */     {
/* 113 */       this.x = x;
/* 114 */       this.y = y;
/*     */     }
/*     */ 
/*     */     public int getX() {
/* 118 */       return this.x;
/*     */     }
/*     */ 
/*     */     public void setX(int x) {
/* 122 */       this.x = x;
/*     */     }
/*     */ 
/*     */     public int getY() {
/* 126 */       return this.y;
/*     */     }
/*     */ 
/*     */     public void setY(int y) {
/* 130 */       this.y = y;
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.image.ImageUtils
 * JD-Core Version:    0.6.0
 */