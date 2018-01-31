/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Payment;
/*     */ import com.jspgou.cms.entity.Shipping;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BasePayment
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "Payment";
/*  24 */   public static String PROP_DESCRIPTION = "description";
/*  25 */   public static String PROP_WEBSITE = "website";
/*  26 */   public static String PROP_DISABLED = "disabled";
/*  27 */   public static String PROP_CODE = "code";
/*  28 */   public static String PROP_PRIORITY = "priority";
/*  29 */   public static String PROP_NAME = "name";
/*  30 */   public static String PROP_ID = "id";
/*  31 */   public static String PROP_CONFIG = "config";
/*     */ 
/*  69 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private String description;
/*     */   private Integer priority;
/*     */   private Boolean disabled;
/*     */   private Boolean isDefault;
/*     */   private Byte type;
/*     */   private String introduce;
/*     */   private Website website;
/*     */   private Set<Shipping> shippings;
/*     */ 
/*     */   public BasePayment()
/*     */   {
/*  36 */     initialize();
/*     */   }
/*     */ 
/*     */   public BasePayment(Long id)
/*     */   {
/*  43 */     setId(id);
/*  44 */     initialize();
/*     */   }
/*     */ 
/*     */   public BasePayment(Long id, Website website, String name, Integer priority, Boolean disabled)
/*     */   {
/*  57 */     setId(id);
/*  58 */     setWebsite(website);
/*  59 */     setName(name);
/*  60 */     setPriority(priority);
/*  61 */     setDisabled(disabled);
/*  62 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  98 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 106 */     this.id = id;
/* 107 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 117 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 125 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 133 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/* 141 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public Integer getPriority()
/*     */   {
/* 149 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority)
/*     */   {
/* 157 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public Boolean getDisabled()
/*     */   {
/* 165 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(Boolean disabled)
/*     */   {
/* 173 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public Boolean getIsDefault()
/*     */   {
/* 178 */     return this.isDefault;
/*     */   }
/*     */ 
/*     */   public void setIsDefault(Boolean isDefault) {
/* 182 */     this.isDefault = isDefault;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 189 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 197 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 204 */     if (obj == null) return false;
/* 205 */     if (!(obj instanceof Payment)) return false;
/*     */ 
/* 207 */     Payment payment = (Payment)obj;
/* 208 */     if ((getId() == null) || (payment.getId() == null)) return false;
/* 209 */     return getId().equals(payment.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 215 */     if (-2147483648 == this.hashCode) {
/* 216 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 218 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 219 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 222 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 228 */     return super.toString();
/*     */   }
/*     */ 
/*     */   public void setType(Byte type) {
/* 232 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public Byte getType() {
/* 236 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setShippings(Set<Shipping> shippings) {
/* 240 */     this.shippings = shippings;
/*     */   }
/*     */ 
/*     */   public Set<Shipping> getShippings() {
/* 244 */     return this.shippings;
/*     */   }
/*     */ 
/*     */   public void setIntroduce(String introduce) {
/* 248 */     this.introduce = introduce;
/*     */   }
/*     */ 
/*     */   public String getIntroduce() {
/* 252 */     return this.introduce;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BasePayment
 * JD-Core Version:    0.6.0
 */