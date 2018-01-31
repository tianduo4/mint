/*    */ package com.jspgou.cms.entity.base;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public abstract class BaseOrderReturnPicture
/*    */   implements Serializable
/*    */ {
/* 19 */   public static String REF = "OrderReturnPicture";
/* 20 */   public static String PROP_DESCRIPTION = "description";
/* 21 */   public static String PROP_IMG_PATH = "imgPath";
/*    */   private String imgPath;
/*    */   private String description;
/*    */ 
/*    */   public BaseOrderReturnPicture()
/*    */   {
/* 26 */     initialize();
/*    */   }
/*    */ 
/*    */   public BaseOrderReturnPicture(String imgPath)
/*    */   {
/* 35 */     setImgPath(imgPath);
/* 36 */     initialize();
/*    */   }
/*    */ 
/*    */   protected void initialize()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getImgPath()
/*    */   {
/* 56 */     return this.imgPath;
/*    */   }
/*    */ 
/*    */   public void setImgPath(String imgPath)
/*    */   {
/* 64 */     this.imgPath = imgPath;
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 72 */     return this.description;
/*    */   }
/*    */ 
/*    */   public void setDescription(String description)
/*    */   {
/* 80 */     this.description = description;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 90 */     return super.toString();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseOrderReturnPicture
 * JD-Core Version:    0.6.0
 */