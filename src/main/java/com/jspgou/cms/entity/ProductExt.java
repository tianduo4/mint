/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseProductExt;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ProductExt extends BaseProductExt
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public void init()
/*    */   {
/* 15 */     if (getWeight() == null) {
/* 16 */       setWeight(Integer.valueOf(0));
/*    */     }
/* 18 */     if (getUnit() == null)
/* 19 */       setUnit("");
/*    */   }
/*    */ 
/*    */   public ProductExt()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ProductExt(Long id)
/*    */   {
/* 32 */     super(id);
/*    */   }
/*    */ 
/*    */   public ProductExt(Long id, Integer weight, String unit)
/*    */   {
/* 46 */     super(id, 
/* 45 */       weight, 
/* 46 */       unit);
/*    */   }
/*    */ 
/*    */   public List<String> getImgs() {
/* 50 */     String imgs = getProductImgs();
/* 51 */     List t = new ArrayList();
/* 52 */     if ((imgs != null) && (!imgs.equals(""))) {
/* 53 */       String[] c = imgs.split("@@");
/* 54 */       for (int i = 0; i < c.length; i++) {
/* 55 */         if (c[i].indexOf("/") != -1) {
/* 56 */           t.add(c[i]);
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 61 */     return t;
/*    */   }
/*    */ 
/*    */   public List<String> getImgsDesc() {
/* 65 */     String imgs = getProductImgDesc();
/* 66 */     List t = new ArrayList();
/* 67 */     String[] c = imgs.split("@@");
/* 68 */     for (int i = 0; i < c.length; i++)
/*    */     {
/* 70 */       t.add(c[i]);
/*    */     }
/*    */ 
/* 73 */     return t;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ProductExt
 * JD-Core Version:    0.6.0
 */