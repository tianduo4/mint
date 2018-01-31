/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductText;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseProductText
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "ProductText";
/*  20 */   public static String PROP_TEXT = "text";
/*  21 */   public static String PROP_PRODUCT = "product";
/*  22 */   public static String PROP_TEXT1 = "text1";
/*  23 */   public static String PROP_TEXT2 = "text2";
/*  24 */   public static String PROP_ID = "id";
/*     */ 
/*  44 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String text;
/*     */   private String text1;
/*     */   private String text2;
/*     */   private Product product;
/*     */ 
/*     */   public BaseProductText()
/*     */   {
/*  29 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductText(Long id)
/*     */   {
/*  36 */     setId(id);
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  66 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  74 */     this.id = id;
/*  75 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/*  85 */     return this.text;
/*     */   }
/*     */ 
/*     */   public void setText(String text)
/*     */   {
/*  93 */     this.text = text;
/*     */   }
/*     */ 
/*     */   public String getText1()
/*     */   {
/* 101 */     return this.text1;
/*     */   }
/*     */ 
/*     */   public void setText1(String text1)
/*     */   {
/* 109 */     this.text1 = text1;
/*     */   }
/*     */ 
/*     */   public String getText2()
/*     */   {
/* 117 */     return this.text2;
/*     */   }
/*     */ 
/*     */   public void setText2(String text2)
/*     */   {
/* 125 */     this.text2 = text2;
/*     */   }
/*     */ 
/*     */   public Product getProduct()
/*     */   {
/* 133 */     return this.product;
/*     */   }
/*     */ 
/*     */   public void setProduct(Product product)
/*     */   {
/* 141 */     this.product = product;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 148 */     if (obj == null) return false;
/* 149 */     if (!(obj instanceof ProductText)) return false;
/*     */ 
/* 151 */     ProductText productText = (ProductText)obj;
/* 152 */     if ((getId() == null) || (productText.getId() == null)) return false;
/* 153 */     return getId().equals(productText.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 159 */     if (-2147483648 == this.hashCode) {
/* 160 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 162 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 163 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 166 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 172 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseProductText
 * JD-Core Version:    0.6.0
 */