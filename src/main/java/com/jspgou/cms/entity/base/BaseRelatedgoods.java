/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Address;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseRelatedgoods
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  12 */   public static String PROP_PRODUCTIDS = "productIds";
/*  13 */   public static String PROP_PRODUCTID = "productId";
/*  14 */   public static String PROP_ID = "id";
/*     */ 
/*  48 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Long productId;
/*     */   private Long productIds;
/*     */ 
/*     */   public BaseRelatedgoods()
/*     */   {
/*  19 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseRelatedgoods(Long id)
/*     */   {
/*  26 */     setId(id);
/*  27 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseRelatedgoods(Long id, Long productId, Long productIds)
/*     */   {
/*  38 */     setId(id);
/*  39 */     setProductId(productId);
/*  40 */     setProductIds(productIds);
/*  41 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getProductId()
/*     */   {
/*  61 */     return this.productId;
/*     */   }
/*     */ 
/*     */   public void setProductId(Long productId) {
/*  65 */     this.productId = productId;
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  75 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  83 */     this.id = id;
/*  84 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Long getProductIds()
/*     */   {
/*  94 */     return this.productIds;
/*     */   }
/*     */ 
/*     */   public void setProductIds(Long productIds)
/*     */   {
/* 102 */     this.productIds = productIds;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 107 */     if (obj == null) return false;
/* 108 */     if (!(obj instanceof Address)) return false;
/*     */ 
/* 110 */     Address address = (Address)obj;
/* 111 */     if ((getId() == null) || (address.getId() == null)) return false;
/* 112 */     return getId().equals(address.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 118 */     if (-2147483648 == this.hashCode) {
/* 119 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 121 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 122 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 125 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 131 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseRelatedgoods
 * JD-Core Version:    0.6.0
 */