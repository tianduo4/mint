/*     */ package com.jspgou.common.image;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ 
/*     */ public class AverageImageScale
/*     */ {
/*     */   public static void resizeFix(File srcFile, File destFile, int boxWidth, int boxHeight)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  33 */       BufferedImage srcImgBuff = ImageIO.read(srcFile);
/*  34 */       int width = srcImgBuff.getWidth();
/*  35 */       int height = srcImgBuff.getHeight();
/*  36 */       if ((width <= boxWidth) && (height <= boxHeight)) {
/*  37 */         FileUtils.copyFile(srcFile, destFile);
/*  38 */         return;
/*     */       }
/*     */       int zoomWidth;
/*     */       int zoomHeight;
/*  42 */       if (width / height > boxWidth / boxHeight) {
/*  43 */          zoomWidth = boxWidth;
/*  44 */         zoomHeight = Math.round(boxWidth * height / width);
/*     */       } else {
/*  46 */         zoomWidth = Math.round(boxHeight * width / height);
/*  47 */         zoomHeight = boxHeight;
/*     */       }
/*  49 */       BufferedImage imgBuff = scaleImage(srcImgBuff, width, height, 
/*  50 */         zoomWidth, zoomHeight);
/*  51 */       writeFile(imgBuff, destFile);
/*     */     } catch (Exception e) {
/*  53 */       System.out.println(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void resizeFix(File file, File file1, int i, int j, int k, int l, int i1, int j1) throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  61 */       BufferedImage bufferedimage = ImageIO.read(file);
/*  62 */       bufferedimage = bufferedimage.getSubimage(k, l, i1, j1);
/*  63 */       int k1 = bufferedimage.getWidth();
/*  64 */       int l1 = bufferedimage.getHeight();
/*  65 */       if ((k1 <= i) && (l1 <= j))
/*     */       {
/*  67 */         writeFile(bufferedimage, file1);
/*  68 */         return;
/*     */       }
/*     */       int j2;
/*     */       int i2;
/*  72 */       if (k1 / l1 > i / j)
/*     */       {
/*  74 */          i2 = i;
/*  75 */         j2 = Math.round(i * l1 / k1);
/*     */       }
/*     */       else {
/*  78 */         i2 = Math.round(j * k1 / l1);
/*  79 */         j2 = j;
/*     */       }
/*  81 */       BufferedImage bufferedimage1 = scaleImage(bufferedimage, k1, l1, i2, j2);
/*  82 */       writeFile(bufferedimage1, file1);
/*     */     } catch (Exception e) {
/*  84 */       System.out.println(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void writeFile(BufferedImage imgBuf, File destFile) throws IOException
/*     */   {
/*  90 */     File parent = destFile.getParentFile();
/*  91 */     if (!parent.exists()) {
/*  92 */       parent.mkdirs();
/*     */     }
/*  94 */     ImageIO.write(imgBuf, "jpeg", destFile);
/*     */   }
/*     */ 
/*     */   private static BufferedImage scaleImage(BufferedImage srcImgBuff, int width, int height, int zoomWidth, int zoomHeight)
/*     */   {
/*  99 */     int[] colorArray = srcImgBuff.getRGB(0, 0, width, height, null, 0, 
/* 100 */       width);
/* 101 */     BufferedImage outBuff = new BufferedImage(zoomWidth, zoomHeight, 
/* 102 */       1);
/*     */ 
/* 104 */     float wScale = width / zoomWidth;
/* 105 */     int wScaleInt = (int)(wScale + 0.5D);
/*     */ 
/* 107 */     float hScale = height / zoomHeight;
/* 108 */     int hScaleInt = (int)(hScale + 0.5D);
/* 109 */     int area = wScaleInt * hScaleInt;
/*     */ 
/* 114 */     for (int y = 0; y < zoomHeight; y++)
/*     */     {
/* 116 */       int y0 = (int)(y * hScale);
/* 117 */       int y1 = y0 + hScaleInt;
/* 118 */       for (int x = 0; x < zoomWidth; x++) {
/* 119 */         int x0 = (int)(x * wScale);
/* 120 */         int x1 = x0 + wScaleInt;
/*     */         long blue;
/*     */         long green;
/* 121 */         long red = green = blue = 0L;
/* 122 */         for (int i = x0; i < x1; i++) {
/* 123 */           for (int j = y0; j < y1; j++) {
/* 124 */             int color = colorArray[(width * j + i)];
/* 125 */             red += getRedValue(color);
/* 126 */             green += getGreenValue(color);
/* 127 */             blue += getBlueValue(color);
/*     */           }
/*     */         }
/* 130 */         outBuff.setRGB(x, y, comRGB((int)(red / area), 
/* 131 */           (int)(green / area), (int)(blue / area)));
/*     */       }
/*     */     }
/* 134 */     return outBuff;
/*     */   }
/*     */ 
/*     */   private static int getRedValue(int rgbValue) {
/* 138 */     return (rgbValue & 0xFF0000) >> 16;
/*     */   }
/*     */ 
/*     */   private static int getGreenValue(int rgbValue) {
/* 142 */     return (rgbValue & 0xFF00) >> 8;
/*     */   }
/*     */ 
/*     */   private static int getBlueValue(int rgbValue) {
/* 146 */     return rgbValue & 0xFF;
/*     */   }
/*     */ 
/*     */   private static int comRGB(int redValue, int greenValue, int blueValue) {
/* 150 */     return (redValue << 16) + (greenValue << 8) + blueValue;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception
/*     */   {
/* 155 */     long time = System.currentTimeMillis();
/* 156 */     resizeFix(new File("test/com/jeecms/common/util/1.bmp"), 
/* 157 */       new File("test/com/jeecms/common/util/1-n-2.bmp"), 310, 310, 50, 50, 
/* 158 */       320, 320);
/* 159 */     time = System.currentTimeMillis() - time;
/* 160 */     System.out.println("resize2 img in " + time + "ms");
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.image.AverageImageScale
 * JD-Core Version:    0.6.0
 */