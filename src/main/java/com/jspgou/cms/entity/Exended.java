/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseExended;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class Exended extends BaseExended
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Exended()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Exended(Long id)
/*    */   {
/* 25 */     super(id);
/*    */   }
/*    */ 
/*    */   public Exended(Long id, String name, String field)
/*    */   {
/* 39 */     super(id, 
/* 38 */       name, 
/* 39 */       field);
/*    */   }
/*    */ 
/*    */   public void addToProductTypes(ProductType pType) {
/* 43 */     Set types = getProductTypes();
/* 44 */     if (types == null) {
/* 45 */       types = new HashSet();
/* 46 */       setProductTypes(types);
/*    */     }
/* 48 */     types.add(pType);
/*    */   }
/*    */ 
/*    */   public Long[] getProductTypeIds()
/*    */   {
/* 53 */     Set set = getProductTypes();
/* 54 */     if (set == null) {
/* 55 */       return null;
/*    */     }
/* 57 */     Long[] ids = new Long[set.size()];
/* 58 */     int i = 0;
/* 59 */     for (ProductType productType : set) {
/* 60 */       ids[i] = productType.getId();
/* 61 */       i++;
/*    */     }
/* 63 */     return ids;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Exended
 * JD-Core Version:    0.6.0
 */