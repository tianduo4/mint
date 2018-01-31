/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.PopularityGroup;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BasePopularityGroup
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "PopularityGroup";
/*  18 */   public static String PROP_NAME = "name";
/*  19 */   public static String PROP_DESCRIPTION = "description";
/*  20 */   public static String PROP_BEGIN_TIME = "beginTime";
/*  21 */   public static String PROP_PRICE = "price";
/*  22 */   public static String PROP_PRIVILEGE = "privilege";
/*  23 */   public static String PROP_ID = "id";
/*  24 */   public static String PROP_END_TIME = "endTime";
/*     */ 
/*  60 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private Double price;
/*     */   private Double privilege;
/*     */   private String description;
/*     */   private Set<Product> products;
/*     */ 
/*     */   public BasePopularityGroup()
/*     */   {
/*  29 */     initialize();
/*     */   }
/*     */ 
/*     */   public BasePopularityGroup(Long id)
/*     */   {
/*  36 */     setId(id);
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   public BasePopularityGroup(Long id, String name, Double price, Double privilege)
/*     */   {
/*  49 */     setId(id);
/*  50 */     setName(name);
/*  51 */     setPrice(price);
/*  52 */     setPrivilege(privilege);
/*  53 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  83 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  91 */     this.id = id;
/*  92 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 102 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 110 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Double getPrice()
/*     */   {
/* 117 */     return this.price;
/*     */   }
/*     */ 
/*     */   public void setPrice(Double price)
/*     */   {
/* 125 */     this.price = price;
/*     */   }
/*     */ 
/*     */   public Double getPrivilege()
/*     */   {
/* 133 */     return this.privilege;
/*     */   }
/*     */ 
/*     */   public void setPrivilege(Double privilege)
/*     */   {
/* 141 */     this.privilege = privilege;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 149 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/* 157 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public Set<Product> getProducts()
/*     */   {
/* 165 */     return this.products;
/*     */   }
/*     */ 
/*     */   public void setProducts(Set<Product> products)
/*     */   {
/* 173 */     this.products = products;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 180 */     if (obj == null) return false;
/* 181 */     if (!(obj instanceof PopularityGroup)) return false;
/*     */ 
/* 183 */     PopularityGroup popularityGroup = (PopularityGroup)obj;
/* 184 */     if ((getId() == null) || (popularityGroup.getId() == null)) return false;
/* 185 */     return getId().equals(popularityGroup.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 191 */     if (-2147483648 == this.hashCode) {
/* 192 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 194 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 195 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 198 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 204 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BasePopularityGroup
 * JD-Core Version:    0.6.0
 */