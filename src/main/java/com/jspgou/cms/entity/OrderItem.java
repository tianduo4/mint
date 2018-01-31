/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.entity.base.BaseOrderItem;
/*     */ import com.jspgou.core.entity.Website;
/*     */ 
/*     */ public class OrderItem extends BaseOrderItem
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public Double getSubtotal()
/*     */   {
/*  19 */     return Double.valueOf(getFinalPrice().doubleValue() * getCount().intValue());
/*     */   }
/*     */ 
/*     */   public Double getLimitSubtotal()
/*     */   {
/*  28 */     return Double.valueOf(getSeckillprice().doubleValue() * getCount().intValue());
/*     */   }
/*     */ 
/*     */   public int getWeightForFreight()
/*     */   {
/*  37 */     Product p = getProduct();
/*     */ 
/*  39 */     return p.getWeight().intValue() * getCount().intValue();
/*     */   }
/*     */ 
/*     */   public int getCountForFreigt()
/*     */   {
/*  49 */     return getCount().intValue();
/*     */   }
/*     */ 
/*     */   public static OrderItem parse(CartItem cartItem, ShopMemberGroup group)
/*     */   {
/*  58 */     return parse(cartItem.getProduct(), cartItem.getCount().intValue(), group, 
/*  59 */       cartItem.getWebsite());
/*     */   }
/*     */ 
/*     */   public static OrderItem parse1(CartItem cartItem, ShopMemberGroup group)
/*     */   {
/*  67 */     if (cartItem.getProductFash() == null) {
/*  68 */       return parse(cartItem.getProduct(), cartItem.getCount().intValue(), group, 
/*  69 */         cartItem.getWebsite());
/*     */     }
/*  71 */     return parse1(cartItem.getProduct(), cartItem.getProductFash(), cartItem.getCount().intValue(), group, 
/*  72 */       cartItem.getWebsite());
/*     */   }
/*     */ 
/*     */   public static OrderItem parse(Product product, int count, ShopMemberGroup group, Website website)
/*     */   {
/*  86 */     product.setStockCount(Integer.valueOf(product.getStockCount().intValue() - count));
/*  87 */     product.setSaleCount(Integer.valueOf(count + product.getSaleCount().intValue()));
/*  88 */     OrderItem item = new OrderItem();
/*  89 */     item.setCount(Integer.valueOf(count));
/*  90 */     item.setWebsite(website);
/*  91 */     item.setProduct(product);
/*     */ 
/*  93 */     if ((product.getProductExt().getIslimitTime() != null) && (product.getProductExt().getIslimitTime().booleanValue()) && 
/*  94 */       (product.getProductExt().getSeckillprice() != null)) {
/*  95 */       item.setSeckillprice(product.getProductExt().getSeckillprice());
/*     */     }
/*  97 */     item.setSalePrice(product.getSalePrice());
/*  98 */     item.setCostPrice(product.getCostPrice());
/*  99 */     item.setMemberPrice(product.getMemberPrice(group));
/* 100 */     item.setFinalPrice(item.getMemberPrice());
/* 101 */     return item;
/*     */   }
/*     */ 
/*     */   public static OrderItem parse1(Product product, ProductFashion productFash, int count, ShopMemberGroup group, Website website)
/*     */   {
/* 115 */     productFash.setStockCount(Integer.valueOf(productFash.getStockCount().intValue() - count));
/* 116 */     productFash.setSaleCount(Integer.valueOf(count + productFash.getSaleCount().intValue()));
/* 117 */     OrderItem item = new OrderItem();
/* 118 */     item.setCount(Integer.valueOf(count));
/* 119 */     item.setWebsite(website);
/* 120 */     item.setProduct(product);
/*     */ 
/* 122 */     if ((product.getProductExt().getIslimitTime() != null) && (product.getProductExt().getIslimitTime().booleanValue()) && 
/* 123 */       (product.getProductExt().getSeckillprice() != null)) {
/* 124 */       item.setSeckillprice(product.getProductExt().getSeckillprice());
/*     */     }
/* 126 */     item.setSalePrice(product.getSalePrice());
/* 127 */     item.setCostPrice(product.getCostPrice());
/* 128 */     item.setMemberPrice(product.getMemberPrice(group));
/* 129 */     item.setFinalPrice(item.getMemberPrice());
/* 130 */     item.setProductFash(productFash);
/* 131 */     return item;
/*     */   }
/*     */ 
/*     */   public OrderItem()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OrderItem(Long id)
/*     */   {
/* 145 */     super(id);
/*     */   }
/*     */ 
/*     */   public OrderItem(Long id, Website website, Product product, Order order, Integer count)
/*     */   {
/* 163 */     super(id, 
/* 160 */       website, 
/* 161 */       product, 
/* 162 */       order, 
/* 163 */       count);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.OrderItem
 * JD-Core Version:    0.6.0
 */