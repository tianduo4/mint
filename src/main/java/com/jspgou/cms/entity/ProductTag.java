/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseProductTag;
/*    */ import com.jspgou.core.entity.Website;
/*    */ 
/*    */ public class ProductTag extends BaseProductTag
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public void increaseCount()
/*    */   {
/* 15 */     addCount(1);
/*    */   }
/*    */ 
/*    */   public void reduceCount()
/*    */   {
/* 22 */     addCount(-1);
/*    */   }
/*    */ 
/*    */   public void addCount(int count)
/*    */   {
/* 31 */     Integer c = getCount();
/* 32 */     if (c != null) {
/* 33 */       count += c.intValue();
/*    */     }
/* 35 */     if (count < 0) {
/* 36 */       count = 0;
/*    */     }
/* 38 */     setCount(Integer.valueOf(count));
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/* 45 */     setCount(Integer.valueOf(0));
/*    */   }
/*    */ 
/*    */   public ProductTag()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ProductTag(Long id)
/*    */   {
/* 57 */     super(id);
/*    */   }
/*    */ 
/*    */   public ProductTag(Long id, Website website, String name, Integer count)
/*    */   {
/* 73 */     super(id, 
/* 71 */       website, 
/* 72 */       name, 
/* 73 */       count);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ProductTag
 * JD-Core Version:    0.6.0
 */