/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Logistics;
/*     */ import com.jspgou.cms.entity.Shipping;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseShipping
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "Shipping";
/*  24 */   public static String PROP_FIRST_WEIGHT = "firstWeight";
/*  25 */   public static String PROP_DESCRIPTION = "description";
/*  26 */   public static String PROP_WEBSITE = "website";
/*  27 */   public static String PROP_FIRST_PRICE = "firstPrice";
/*  28 */   public static String PROP_DISABLED = "disabled";
/*  29 */   public static String PROP_UNIFORM_PRICE = "uniformPrice";
/*  30 */   public static String PROP_METHOD = "method";
/*  31 */   public static String PROP_PRIORITY = "priority";
/*  32 */   public static String PROP_ADDITIONAL_WEIGHT = "additionalWeight";
/*  33 */   public static String PROP_NAME = "name";
/*  34 */   public static String PROP_ID = "id";
/*  35 */   public static String PROP_ADDITIONAL_PRICE = "additionalPrice";
/*     */ 
/*  75 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private String description;
/*     */   private Double uniformPrice;
/*     */   private Integer firstWeight;
/*     */   private Integer additionalWeight;
/*     */   private Double firstPrice;
/*     */   private Double additionalPrice;
/*     */   private Integer method;
/*     */   private Integer priority;
/*     */   private Boolean disabled;
/*     */   private Boolean isDefault;
/*     */   private String logisticsType;
/*     */   private Website website;
/*     */   private Logistics logistics;
/*     */ 
/*     */   public BaseShipping()
/*     */   {
/*  40 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShipping(Long id)
/*     */   {
/*  47 */     setId(id);
/*  48 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShipping(Long id, Website website, String name, Integer method, Integer priority, Boolean disabled)
/*     */   {
/*  62 */     setId(id);
/*  63 */     setWebsite(website);
/*  64 */     setName(name);
/*  65 */     setMethod(method);
/*  66 */     setPriority(priority);
/*  67 */     setDisabled(disabled);
/*  68 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 108 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 116 */     this.id = id;
/* 117 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 127 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 135 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 143 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/* 151 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public Double getUniformPrice()
/*     */   {
/* 159 */     return this.uniformPrice;
/*     */   }
/*     */ 
/*     */   public void setUniformPrice(Double uniformPrice)
/*     */   {
/* 167 */     this.uniformPrice = uniformPrice;
/*     */   }
/*     */ 
/*     */   public Integer getFirstWeight()
/*     */   {
/* 175 */     return this.firstWeight;
/*     */   }
/*     */ 
/*     */   public void setFirstWeight(Integer firstWeight)
/*     */   {
/* 185 */     this.firstWeight = firstWeight;
/*     */   }
/*     */ 
/*     */   public Integer getAdditionalWeight()
/*     */   {
/* 193 */     return this.additionalWeight;
/*     */   }
/*     */ 
/*     */   public void setAdditionalWeight(Integer additionalWeight)
/*     */   {
/* 201 */     this.additionalWeight = additionalWeight;
/*     */   }
/*     */ 
/*     */   public Boolean getIsDefault()
/*     */   {
/* 206 */     return this.isDefault;
/*     */   }
/*     */ 
/*     */   public void setIsDefault(Boolean isDefault) {
/* 210 */     this.isDefault = isDefault;
/*     */   }
/*     */ 
/*     */   public Double getFirstPrice()
/*     */   {
/* 217 */     return this.firstPrice;
/*     */   }
/*     */ 
/*     */   public void setFirstPrice(Double firstPrice)
/*     */   {
/* 225 */     this.firstPrice = firstPrice;
/*     */   }
/*     */ 
/*     */   public Double getAdditionalPrice()
/*     */   {
/* 233 */     return this.additionalPrice;
/*     */   }
/*     */ 
/*     */   public void setAdditionalPrice(Double additionalPrice)
/*     */   {
/* 241 */     this.additionalPrice = additionalPrice;
/*     */   }
/*     */ 
/*     */   public Integer getMethod()
/*     */   {
/* 249 */     return this.method;
/*     */   }
/*     */ 
/*     */   public void setMethod(Integer method)
/*     */   {
/* 257 */     this.method = method;
/*     */   }
/*     */ 
/*     */   public Integer getPriority()
/*     */   {
/* 265 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority)
/*     */   {
/* 273 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public Boolean getDisabled()
/*     */   {
/* 281 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(Boolean disabled)
/*     */   {
/* 289 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 297 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 305 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public void setLogistics(Logistics logistics)
/*     */   {
/* 310 */     this.logistics = logistics;
/*     */   }
/*     */ 
/*     */   public Logistics getLogistics() {
/* 314 */     return this.logistics;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 325 */     if (obj == null) return false;
/* 326 */     if (!(obj instanceof Shipping)) return false;
/*     */ 
/* 328 */     Shipping shipping = (Shipping)obj;
/* 329 */     if ((getId() == null) || (shipping.getId() == null)) return false;
/* 330 */     return getId().equals(shipping.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 336 */     if (-2147483648 == this.hashCode) {
/* 337 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 339 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 340 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 343 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 349 */     return super.toString();
/*     */   }
/*     */ 
/*     */   public void setLogisticsType(String logisticsType) {
/* 353 */     this.logisticsType = logisticsType;
/*     */   }
/*     */ 
/*     */   public String getLogisticsType() {
/* 357 */     return this.logisticsType;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShipping
 * JD-Core Version:    0.6.0
 */