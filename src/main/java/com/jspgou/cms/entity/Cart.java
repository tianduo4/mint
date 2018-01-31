/*     */ package com.jspgou.cms.entity;
/*     */ 
/*     */ import com.jspgou.cms.entity.base.BaseCart;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Cart extends BaseCart
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   public Double getTotalPrice()
/*     */   {
/*  24 */     return getSubtotal();
/*     */   }
/*     */ 
/*     */   public Double getProductTotalPrice()
/*     */   {
/*  33 */     return getSubtotal();
/*     */   }
/*     */ 
/*     */   public int getWeightForFreight()
/*     */   {
/*  44 */     int weight = 0;
/*  45 */     for (CartItem item : getItems()) {
/*  46 */       weight += item.getWeightForFreight();
/*     */     }
/*  48 */     return weight;
/*     */   }
/*     */ 
/*     */   public int getCountForFreight()
/*     */   {
/*  57 */     int count = 0;
/*  58 */     for (CartItem item : getItems()) {
/*  59 */       count += item.getCountForFreigt();
/*     */     }
/*  61 */     return count;
/*     */   }
/*     */ 
/*     */   public Double getSubtotal()
/*     */   {
/*  70 */     Set<CartItem> items = getItems();
/*  71 */     Double total = Double.valueOf(0.0D);
/*  72 */     if (items != null) {
/*  73 */       for (CartItem item : items) {
/*  74 */         total = Double.valueOf(total.doubleValue() + item.getSubtotal().doubleValue());
/*     */       }
/*     */     }
/*  77 */     return total;
/*     */   }
/*     */   public Double getTotalSubtotal() {
/*  80 */     Set<CartItem> items = getItems();
/*  81 */     Double total = Double.valueOf(0.0D);
/*  82 */     if (items != null) {
/*  83 */       for (CartItem item : items) {
/*  84 */         if ((item.getProduct().getProductExt().getIslimitTime() != null) && (item.getProduct().getProductExt().getIslimitTime().booleanValue()))
/*  85 */           total = Double.valueOf(total.doubleValue() + item.getLimitSubtotal().doubleValue());
/*     */         else {
/*  87 */           total = Double.valueOf(total.doubleValue() + item.getSubtotal().doubleValue());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  92 */     return total;
/*     */   }
/*     */ 
/*     */   public int calTotalItem()
/*     */   {
/* 100 */     Set<CartItem> items = getItems();
/* 101 */     int count = 0;
/* 102 */     if (items != null) {
/* 103 */       for (CartItem item : items) {
/* 104 */         count += item.getCount().intValue();
/*     */       }
/*     */     }
/* 107 */     setTotalItems(Integer.valueOf(count));
/* 108 */     return count;
/*     */   }
/*     */ 
/*     */   public int getTotalScore()
/*     */   {
/* 116 */     Set<CartItem> items = getItems();
/* 117 */     int score = 0;
/* 118 */     if (items != null) {
/* 119 */       for (CartItem item : items) {
/* 120 */         score += item.getScore().intValue();
/*     */       }
/*     */     }
/* 123 */     return score;
/*     */   }
/*     */ 
/*     */   public int calTotalGift()
/*     */   {
/* 131 */     Set<CartGift> items = getGifts();
/* 132 */     int count = 0;
/* 133 */     if (items != null) {
/* 134 */       for (CartGift item : items) {
/* 135 */         count += item.getCount().intValue();
/*     */       }
/*     */     }
/* 138 */     setTotalGifts(Integer.valueOf(count));
/* 139 */     return count;
/*     */   }
/*     */ 
/*     */   public void addItem(Product product, ProductFashion productFashion, PopularityGroup popularityGroup, int count, boolean isAdd)
/*     */   {
/* 156 */     CartItem item = extensionItem(product, productFashion, popularityGroup);
/* 157 */     count = extensionCount(product, productFashion, popularityGroup, count, isAdd);
/* 158 */     if (count > 0) {
/* 159 */       if (item == null) {
/* 160 */         item = new CartItem();
/*     */       }
/* 162 */       item.setCount(Integer.valueOf(count));
/* 163 */       item.setScore(Integer.valueOf(count * product.getScore().intValue()));
/* 164 */       if (extensionItem(product, productFashion, popularityGroup) == null) {
/* 165 */         if (productFashion != null) {
/* 166 */           item.setProductFash(productFashion);
/*     */         }
/* 168 */         if (popularityGroup != null) {
/* 169 */           item.setPopularityGroup(popularityGroup);
/*     */         }
/* 171 */         item.setProduct(product);
/* 172 */         addToItems(item);
/*     */       }
/* 174 */     } else if (item != null) {
/* 175 */       getItems().remove(item);
/*     */     }
/*     */ 
/* 178 */     calTotalItem();
/*     */   }
/*     */ 
/*     */   public CartItem extensionItem(Product product, ProductFashion productFashion, PopularityGroup popularityGroup)
/*     */   {
/* 183 */     CartItem item = null;
/* 184 */     if (productFashion != null)
/* 185 */       item = findItemfashion(productFashion.getId(), popularityGroup);
/*     */     else {
/* 187 */       item = findItem(product.getId(), popularityGroup);
/*     */     }
/* 189 */     return item;
/*     */   }
/*     */ 
/*     */   public int extensionCount(Product product, ProductFashion productFashion, PopularityGroup popularityGroup, int count, boolean isAdd) {
/* 193 */     CartItem item = extensionItem(product, productFashion, popularityGroup);
/* 194 */     if ((item != null) && 
/* 195 */       (isAdd)) {
/* 196 */       count += item.getCount().intValue();
/*     */     }
/*     */ 
/* 199 */     if (productFashion != null) {
/* 200 */       if (count > productFashion.getStockCount().intValue()) {
/* 201 */         count = productFashion.getStockCount().intValue();
/*     */       }
/*     */     }
/* 204 */     else if (count > product.getStockCount().intValue()) {
/* 205 */       count = product.getStockCount().intValue();
/*     */     }
/*     */ 
/* 208 */     return count;
/*     */   }
/*     */ 
/*     */   public void addGift(Gift gift, int count, boolean isAdd) {
/* 212 */     CartGift gifts = findGift(gift.getId());
/* 213 */     if (gifts != null) {
/* 214 */       if (isAdd) {
/* 215 */         count += gifts.getCount().intValue();
/*     */       }
/*     */ 
/* 218 */       if (count <= 0) {
/* 219 */         getGifts().remove(gifts);
/*     */       } else {
/* 221 */         if (count > gift.getGiftStock().intValue()) {
/* 222 */           count = gift.getGiftStock().intValue();
/*     */         }
/* 224 */         gifts.setCount(Integer.valueOf(count));
/*     */       }
/*     */     }
/* 227 */     else if (count > 0) {
/* 228 */       if (count > gift.getGiftStock().intValue()) {
/* 229 */         count = gift.getGiftStock().intValue();
/*     */       }
/* 231 */       gifts = new CartGift();
/* 232 */       gifts.setGift(gift);
/* 233 */       gifts.setCount(Integer.valueOf(count));
/* 234 */       addToGift(gifts);
/*     */     }
/*     */ 
/* 238 */     calTotalGift();
/*     */   }
/*     */ 
/*     */   public CartItem findItem(Long productId, PopularityGroup popularityGroup)
/*     */   {
/* 249 */     Set<CartItem> items = getItems();
/* 250 */     if ((items != null) && (items.size() > 0)) {
/* 251 */       if (popularityGroup != null) {
/* 252 */         for (CartItem item : items) {
/* 253 */           if ((item.getPopularityGroup() != null) && 
/* 254 */             (item.getProduct().getId().equals(productId)) && (item.getPopularityGroup().getId().equals(popularityGroup.getId()))) {
/* 255 */             return item;
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/* 260 */         for (CartItem item : items) {
/* 261 */           if ((item.getProduct().getId().equals(productId)) && (item.getPopularityGroup() == null)) {
/* 262 */             return item;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 267 */     return null;
/*     */   }
/*     */   public CartItem findItemfashion(Long productFashId, PopularityGroup popularityGroup) {
/* 270 */     Set<CartItem> items = getItems();
/* 271 */     if ((items != null) && (items.size() > 0)) {
/* 272 */       if (popularityGroup != null) {
/* 273 */         for (CartItem item : items) {
/* 274 */           if ((item.getProductFash() != null) && (item.getPopularityGroup() != null) && 
/* 275 */             (item.getProductFash().getId().equals(productFashId)) && (item.getPopularityGroup().getId().equals(popularityGroup.getId()))) {
/* 276 */             return item;
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/* 281 */         for (CartItem item : items) {
/* 282 */           if ((item.getProductFash() != null) && 
/* 283 */             (item.getProductFash().getId().equals(productFashId)) && (item.getPopularityGroup() == null)) {
/* 284 */             return item;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 290 */     return null;
/*     */   }
/*     */ 
/*     */   public CartGift findGift(Long giftId) {
/* 294 */     Set<CartGift> gifts = getGifts();
/* 295 */     if ((gifts != null) && (gifts.size() > 0)) {
/* 296 */       for (CartGift gift : gifts) {
/* 297 */         if (gift.getGift().getId().equals(giftId)) {
/* 298 */           return gift;
/*     */         }
/*     */       }
/*     */     }
/* 302 */     return null;
/*     */   }
/*     */ 
/*     */   public void addToGift(CartGift item) {
/* 306 */     Set items = getGifts();
/* 307 */     if (items == null) {
/* 308 */       items = new LinkedHashSet();
/* 309 */       setGifts(items);
/*     */     }
/* 311 */     item.setWebsite(getWebsite());
/* 312 */     item.setCart(this);
/* 313 */     items.add(item);
/*     */   }
/*     */ 
/*     */   public void addToItems(CartItem item) {
/* 317 */     Set items = getItems();
/* 318 */     if (items == null) {
/* 319 */       items = new LinkedHashSet();
/* 320 */       setItems(items);
/*     */     }
/* 322 */     item.setWebsite(getWebsite());
/* 323 */     item.setCart(this);
/* 324 */     items.add(item);
/*     */   }
/*     */ 
/*     */   public List<PopularityGroup> getPopularits() {
/* 328 */     List list = new ArrayList();
/* 329 */     Set<CartItem> items = getItems();
/* 330 */     if (items != null) {
/* 331 */       for (CartItem item : items) {
/* 332 */         if ((item.getPopularityGroup() == null) || 
/* 333 */           (list.contains(item.getPopularityGroup()))) continue;
/* 334 */         list.add(item.getPopularityGroup());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 340 */     return list;
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 345 */     if (getTotalItems() == null)
/* 346 */       setTotalItems(Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   public Cart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Cart(Long id)
/*     */   {
/* 359 */     super(id);
/*     */   }
/*     */ 
/*     */   public Cart(Long id, Website website, Integer totalItems)
/*     */   {
/* 373 */     super(id, 
/* 372 */       website, 
/* 373 */       totalItems);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Cart
 * JD-Core Version:    0.6.0
 */