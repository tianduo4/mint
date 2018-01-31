/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ProductTag;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseProductTag
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "ProductTag";
/*  20 */   public static String PROP_WEBSITE = "website";
/*  21 */   public static String PROP_NAME = "name";
/*  22 */   public static String PROP_ID = "id";
/*  23 */   public static String PROP_COUNT = "count";
/*     */ 
/*  59 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private Integer count;
/*     */   private Website website;
/*     */ 
/*     */   public BaseProductTag()
/*     */   {
/*  28 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductTag(Long id)
/*     */   {
/*  35 */     setId(id);
/*  36 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseProductTag(Long id, Website website, String name, Integer count)
/*     */   {
/*  48 */     setId(id);
/*  49 */     setWebsite(website);
/*  50 */     setName(name);
/*  51 */     setCount(count);
/*  52 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  80 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  88 */     this.id = id;
/*  89 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  99 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 107 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Integer getCount()
/*     */   {
/* 115 */     return this.count;
/*     */   }
/*     */ 
/*     */   public void setCount(Integer count)
/*     */   {
/* 123 */     this.count = count;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 131 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 139 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 146 */     if (obj == null) return false;
/* 147 */     if (!(obj instanceof ProductTag)) return false;
/*     */ 
/* 149 */     ProductTag productTag = (ProductTag)obj;
/* 150 */     if ((getId() == null) || (productTag.getId() == null)) return false;
/* 151 */     return getId().equals(productTag.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 157 */     if (-2147483648 == this.hashCode) {
/* 158 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 160 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 161 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 164 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 170 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseProductTag
 * JD-Core Version:    0.6.0
 */