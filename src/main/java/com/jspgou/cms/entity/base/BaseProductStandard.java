/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductStandard;
/*     */ import com.jspgou.cms.entity.Standard;
/*     */ import com.jspgou.cms.entity.StandardType;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseProductStandard
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "ProductStandard";
/*  20 */   public static String PROP_STANDARD = "standard";
/*  21 */   public static String PROP_DATA_TYPE = "dataType";
/*  22 */   public static String PROP_PRODUCT = "product";
/*  23 */   public static String PROP_TYPE = "type";
/*  24 */   public static String PROP_ID = "id";
/*  25 */   public static String PROP_IMG_PATH = "imgPath";
/*     */ 
/*  65 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String imgPath;
/*     */   private boolean dataType;
/*     */   private Product product;
/*     */   private Standard standard;
/*     */   private StandardType type;
/*     */ 
/*     */   public BaseProductStandard()
/*     */   {
/*  30 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductStandard(Long id)
/*     */   {
/*  37 */     setId(id);
/*  38 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductStandard(Long id, Product product, Standard standard, StandardType type, String imgPath, boolean dataType)
/*     */   {
/*  52 */     setId(id);
/*  53 */     setProduct(product);
/*  54 */     setStandard(standard);
/*  55 */     setType(type);
/*  56 */     setImgPath(imgPath);
/*  57 */     setDataType(dataType);
/*  58 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  88 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  96 */     this.id = id;
/*  97 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getImgPath()
/*     */   {
/* 107 */     return this.imgPath;
/*     */   }
/*     */ 
/*     */   public void setImgPath(String imgPath)
/*     */   {
/* 115 */     this.imgPath = imgPath;
/*     */   }
/*     */ 
/*     */   public boolean isDataType()
/*     */   {
/* 123 */     return this.dataType;
/*     */   }
/*     */ 
/*     */   public void setDataType(boolean dataType)
/*     */   {
/* 131 */     this.dataType = dataType;
/*     */   }
/*     */ 
/*     */   public Product getProduct()
/*     */   {
/* 139 */     return this.product;
/*     */   }
/*     */ 
/*     */   public void setProduct(Product product)
/*     */   {
/* 147 */     this.product = product;
/*     */   }
/*     */ 
/*     */   public Standard getStandard()
/*     */   {
/* 155 */     return this.standard;
/*     */   }
/*     */ 
/*     */   public void setStandard(Standard standard)
/*     */   {
/* 163 */     this.standard = standard;
/*     */   }
/*     */ 
/*     */   public StandardType getType()
/*     */   {
/* 171 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(StandardType type)
/*     */   {
/* 179 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 186 */     if (obj == null) return false;
/* 187 */     if (!(obj instanceof ProductStandard)) return false;
/*     */ 
/* 189 */     ProductStandard productStandard = (ProductStandard)obj;
/* 190 */     if ((getId() == null) || (productStandard.getId() == null)) return false;
/* 191 */     return getId().equals(productStandard.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 197 */     if (-2147483648 == this.hashCode) {
/* 198 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 200 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 201 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 204 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 210 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseProductStandard
 * JD-Core Version:    0.6.0
 */