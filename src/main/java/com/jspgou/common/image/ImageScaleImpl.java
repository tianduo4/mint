/*    */ package com.jspgou.common.image;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import magick.Magick;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class ImageScaleImpl
/*    */   implements ImageScale
/*    */ {
/* 16 */   private static final Logger log = LoggerFactory.getLogger(ImageScaleImpl.class);
/* 17 */   private boolean isMagick = false;
/*    */ 
/*    */   public void resizeFix(File srcFile, File destFile, int boxWidth, int boxHeight) throws Exception
/*    */   {
/*    */     try
/*    */     {
/* 23 */       if (this.isMagick)
/* 24 */         MagickImageScale.resizeFix(srcFile, destFile, boxWidth, boxHeight);
/*    */       else
/* 26 */         AverageImageScale.resizeFix(srcFile, destFile, boxWidth, boxHeight);
/*    */     }
/*    */     catch (Exception e) {
/* 29 */       log.error("裁剪图片出错，请重新裁剪", e);
/* 30 */       System.out.println(e.getMessage());
/*    */     }
/*    */   }
/*    */ 
/*    */   public void resizeFix(File srcFile, File destFile, int boxWidth, int boxHeight, int cutTop, int cutLeft, int cutWidth, int catHeight)
/*    */     throws Exception
/*    */   {
/*    */     try
/*    */     {
/* 39 */       if (this.isMagick)
/* 40 */         MagickImageScale.resizeFix(srcFile, destFile, boxWidth, boxHeight, 
/* 41 */           cutTop, cutLeft, cutWidth, catHeight);
/*    */       else
/* 43 */         AverageImageScale.resizeFix(srcFile, destFile, boxWidth, boxHeight, 
/* 44 */           cutTop, cutLeft, cutWidth, catHeight);
/*    */     }
/*    */     catch (Exception e) {
/* 47 */       log.error("裁剪图片出错，请重新裁剪", e);
/* 48 */       System.out.println(e.getMessage());
/*    */     }
/*    */   }
/*    */ 
/*    */   public void init() {
/*    */     try {
/* 54 */       System.setProperty("jmagick.systemclassloader", "no");
/* 55 */       new Magick();
/* 56 */       log.info("use jmagick");
/* 57 */       this.isMagick = true;
/*    */     } catch (Throwable throwable) {
/* 59 */       log.warn("load magick fail, use java image scale. message:{}", throwable.getMessage());
/* 60 */       this.isMagick = false;
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.image.ImageScaleImpl
 * JD-Core Version:    0.6.0
 */