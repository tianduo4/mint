/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.entity.base.BaseCartItem;
/*    */ import com.jspgou.core.entity.Website;
/*    */ 
/*    */ public class CartItem extends BaseCartItem
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Double getSubtotal()
/*    */   {
/* 19 */     return Double.valueOf(getProduct().getMemberPrice().doubleValue() * getCount().intValue());
/*    */   }
/*    */ 
/*    */   public Double getLimitSubtotal()
/*    */   {
/* 27 */     int count = getCount().intValue();
/* 28 */     Double b1 = getProduct().getProductExt().getSeckillprice();
/* 29 */     Double b3 = Double.valueOf(b1.doubleValue() * count);
/* 30 */     return b3;
/*    */   }
/*    */ 
/*    */   public int getWeightForFreight()
/*    */   {
/* 39 */     Product p = getProduct();
/*    */ 
/* 41 */     return p.getWeight().intValue() * getCount().intValue();
/*    */   }
/*    */ 
/*    */   public int getCountForFreigt()
/*    */   {
/* 51 */     return getCount().intValue();
/*    */   }
/*    */ 
/*    */   public CartItem()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CartItem(Long id)
/*    */   {
/* 63 */     super(id);
/*    */   }
/*    */ 
/*    */   public CartItem(Long id, Website website, Cart cart, Product product, Integer count)
/*    */   {
/* 81 */     super(id, 
/* 78 */       website, 
/* 79 */       cart, 
/* 80 */       product, 
/* 81 */       count);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.CartItem
 * JD-Core Version:    0.6.0
 */