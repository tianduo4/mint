/*     */ package com.jspgou.common.image;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import magick.DrawInfo;
/*     */ import magick.ImageInfo;
/*     */ import magick.MagickException;
/*     */ import magick.MagickImage;
/*     */ import magick.PixelPacket;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ 
/*     */ public class MagickImageScale
/*     */ {
/*     */   public static void resizeFix(File srcFile, File destFile, int boxWidth, int boxHeight)
/*     */     throws IOException, MagickException
/*     */   {
/*  37 */     ImageInfo info = new ImageInfo(srcFile.getAbsolutePath());
/*  38 */     MagickImage image = new MagickImage(info);
/*     */ 
/*  40 */     Dimension dim = image.getDimension();
/*  41 */     int width = (int)dim.getWidth();
/*  42 */     int height = (int)dim.getHeight();
/*     */     int zoomHeight;
/*     */     int zoomWidth;
/*  45 */     if (width / height > boxWidth / boxHeight) {
/*  46 */        zoomWidth = boxWidth;
/*  47 */       zoomHeight = Math.round(boxWidth * height / width);
/*     */     } else {
/*  49 */       zoomWidth = Math.round(boxHeight * width / height);
/*  50 */       zoomHeight = boxHeight;
/*     */     }
/*     */ 
/*  53 */     MagickImage scaled = image.scaleImage(zoomWidth, zoomHeight);
/*     */ 
/*  55 */     scaled.setFileName(destFile.getAbsolutePath());
/*  56 */     scaled.writeImage(info);
/*  57 */     scaled.destroyImages();
/*     */   }
/*     */ 
/*     */   public static void resizeFix(File srcFile, File destFile, int boxWidth, int boxHeight, int cutTop, int cutLeft, int cutWidth, int catHeight)
/*     */     throws IOException, MagickException
/*     */   {
/*  85 */     ImageInfo info = new ImageInfo(srcFile.getAbsolutePath());
/*  86 */     MagickImage image = new MagickImage(info);
/*     */ 
/*  88 */     Rectangle rect = new Rectangle(cutTop, cutLeft, cutWidth, catHeight);
/*     */ 
/*  90 */     MagickImage cropped = image.cropImage(rect);
/*  91 */     Dimension dim = cropped.getDimension();
/*  92 */     int width = (int)dim.getWidth();
/*  93 */     int height = (int)dim.getHeight();
/*     */     int zoomWidth;
/*     */     int zoomHeight;
/*  96 */     if (width / height > boxWidth / boxHeight) {
/*  97 */        zoomWidth = boxWidth;
/*  98 */       zoomHeight = Math.round(boxWidth * height / width);
/*     */     } else {
/* 100 */       zoomWidth = Math.round(boxHeight * width / height);
/* 101 */       zoomHeight = boxHeight;
/*     */     }
/*     */ 
/* 104 */     MagickImage scaled = cropped.scaleImage(zoomWidth, zoomHeight);
/*     */ 
/* 106 */     scaled.setFileName(destFile.getAbsolutePath());
/* 107 */     scaled.writeImage(info);
/* 108 */     scaled.destroyImages();
/*     */   }
/*     */ 
/*     */   public static void imageMark(File srcFile, File destFile, int minWidth, int minHeight, int pos, int offsetX, int offsetY, String markContent, Color markColor, int markSize, int alpha)
/*     */     throws IOException, MagickException
/*     */   {
/* 115 */     ImageInfo info = new ImageInfo(srcFile.getAbsolutePath());
/* 116 */     MagickImage image = new MagickImage(info);
/*     */ 
/* 118 */     Dimension dim = image.getDimension();
/* 119 */     int width = (int)dim.getWidth();
/* 120 */     int height = (int)dim.getHeight();
/* 121 */     if ((width < minWidth) || (height < minHeight)) {
/* 122 */       image.destroyImages();
/* 123 */       if (!srcFile.equals(destFile))
/* 124 */         FileUtils.copyFile(srcFile, destFile);
/*     */     }
/*     */     else {
/* 127 */       imageMark(image, info, width, height, pos, offsetX, offsetY, 
/* 128 */         markContent, markColor, markSize, alpha);
/* 129 */       image.setFileName(destFile.getAbsolutePath());
/* 130 */       image.writeImage(info);
/* 131 */       image.destroyImages();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void imageMark(File srcFile, File destFile, int minWidth, int minHeight, int pos, int offsetX, int offsetY, File markFile)
/*     */     throws IOException, MagickException
/*     */   {
/* 138 */     ImageInfo info = new ImageInfo(srcFile.getAbsolutePath());
/* 139 */     MagickImage image = new MagickImage(info);
/*     */ 
/* 141 */     Dimension dim = image.getDimension();
/* 142 */     int width = (int)dim.getWidth();
/* 143 */     int height = (int)dim.getHeight();
/* 144 */     if ((width < minWidth) || (height < minHeight)) {
/* 145 */       image.destroyImages();
/* 146 */       if (!srcFile.equals(destFile))
/* 147 */         FileUtils.copyFile(srcFile, destFile);
/*     */     }
/*     */     else {
/* 150 */       imageMark(image, info, width, height, pos, offsetX, offsetY, 
/* 151 */         markFile);
/* 152 */       image.setFileName(destFile.getAbsolutePath());
/* 153 */       image.writeImage(info);
/* 154 */       image.destroyImages();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void imageMark(MagickImage image, ImageInfo info, int width, int height, int pos, int offsetX, int offsetY, String text, Color color, int size, int alpha)
/*     */     throws MagickException
/*     */   {
/* 161 */     ImageUtils.Position p = ImageUtils.markPosition(width, height, pos, offsetX, 
/* 162 */       offsetY);
/* 163 */     DrawInfo draw = new DrawInfo(info);
/* 164 */     int r = color.getRed();
/* 165 */     int g = color.getGreen();
/* 166 */     int b = color.getBlue();
/* 167 */     draw.setFill(
/* 168 */       new PixelPacket(r * r, g * g, b * b, 
/* 168 */       65535 - alpha * 65535 / 100));
/* 169 */     draw.setPointsize(size);
/* 170 */     draw.setTextAntialias(true);
/* 171 */     draw.setText(text);
/* 172 */     draw.setGeometry("+" + p.getX() + "+" + p.getY());
/* 173 */     image.annotateImage(draw);
/*     */   }
/*     */ 
/*     */   private static void imageMark(MagickImage image, ImageInfo info, int width, int height, int pos, int offsetX, int offsetY, File markFile)
/*     */     throws MagickException
/*     */   {
/* 179 */     ImageUtils.Position p = ImageUtils.markPosition(width, height, pos, offsetX, 
/* 180 */       offsetY);
/* 181 */     MagickImage mark = new MagickImage(
/* 182 */       new ImageInfo(markFile
/* 182 */       .getAbsolutePath()));
/* 183 */     image.compositeImage(3, mark, p.getX(), 
/* 184 */       p.getY());
/* 185 */     mark.destroyImages();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/* 189 */     long time = System.currentTimeMillis();
/* 190 */     resizeFix(
/* 191 */       new File("test/com/jeecms/common/util/1.bmp"), 
/* 192 */       new File("test/com/jeecms/common/util/1-n-3.bmp"), 310, 310, 50, 
/* 193 */       50, 320, 320);
/* 194 */     time = System.currentTimeMillis() - time;
/* 195 */     System.out.println("resize new img in " + time + "ms");
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.image.MagickImageScale
 * JD-Core Version:    0.6.0
 */