/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BasePopularityGroup;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class PopularityGroup extends BasePopularityGroup
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public void init()
/*    */   {
/* 16 */     if (getPrice() == null) {
/* 17 */       setPrice(Double.valueOf(0.0D));
/*    */     }
/* 19 */     if (getPrivilege() == null)
/* 20 */       setPrivilege(Double.valueOf(0.0D));
/*    */   }
/*    */ 
/*    */   public void addToProducts(Product product)
/*    */   {
/* 26 */     if (product == null) {
/* 27 */       return;
/*    */     }
/* 29 */     Set set = getProducts();
/* 30 */     if (set == null) {
/* 31 */       set = new HashSet();
/* 32 */       setProducts(set);
/*    */     }
/* 34 */     set.add(product);
/*    */   }
/*    */ 
/*    */   public PopularityGroup()
/*    */   {
/*    */   }
/*    */ 
/*    */   public PopularityGroup(Long id)
/*    */   {
/* 46 */     super(id);
/*    */   }
/*    */ 
/*    */   public PopularityGroup(Long id, String name, Double price, Double privilege)
/*    */   {
/* 62 */     super(id, 
/* 60 */       name, 
/* 61 */       price, 
/* 62 */       privilege);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.PopularityGroup
 * JD-Core Version:    0.6.0
 */