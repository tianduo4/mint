/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseShopPlug;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class ShopPlug extends BaseShopPlug
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public boolean getUsed()
/*    */   {
/*  9 */     return isUsed();
/*    */   }
/*    */ 
/*    */   public boolean getFileConflict() {
/* 13 */     return isFileConflict();
/*    */   }
/*    */ 
/*    */   public boolean getCanInstall()
/*    */   {
/* 18 */     return (!getUsed()) && (!getFileConflict());
/*    */   }
/*    */ 
/*    */   public boolean getCanUnInstall()
/*    */   {
/* 26 */     return (getUsed()) && (!getFileConflict());
/*    */   }
/*    */ 
/*    */   public ShopPlug()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ShopPlug(Long id)
/*    */   {
/* 41 */     super(id);
/*    */   }
/*    */ 
/*    */   public ShopPlug(Long id, String name, String path, Date uploadTime, boolean fileConflict, boolean used)
/*    */   {
/* 61 */     super(id, 
/* 57 */       name, 
/* 58 */       path, 
/* 59 */       uploadTime, 
/* 60 */       fileConflict, 
/* 61 */       used);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ShopPlug
 * JD-Core Version:    0.6.0
 */