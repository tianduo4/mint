/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.PaymentPlugins;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BasePaymentPlugins
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "PaymentPlugins";
/*  20 */   public static String PROP_NAME = "name";
/*  21 */   public static String PROP_DESCRIPTION = "description";
/*  22 */   public static String PROP_SELLER_KEY = "sellerKey";
/*  23 */   public static String PROP_ID = "id";
/*  24 */   public static String PROP_IMG_PATH = "imgPath";
/*  25 */   public static String PROP_SELLER_EMAIL = "sellerEmail";
/*  26 */   public static String PROP_PARTNER = "partner";
/*  27 */   public static String PROP_CODE = "code";
/*  28 */   public static String PROP_PRIORITY = "priority";
/*  29 */   public static String PROP_DISABLED = "disabled";
/*     */ 
/*  67 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private String code;
/*     */   private String description;
/*     */   private Integer priority;
/*     */   private String imgPath;
/*     */   private String partner;
/*     */   private String sellerKey;
/*     */   private String sellerEmail;
/*     */   private String platform;
/*     */   private String publicKey;
/*     */   private Boolean disabled;
/*     */   private Boolean isDefault;
/*     */ 
/*     */   public BasePaymentPlugins()
/*     */   {
/*  34 */     initialize();
/*     */   }
/*     */ 
/*     */   public BasePaymentPlugins(Long id)
/*     */   {
/*  41 */     setId(id);
/*  42 */     initialize();
/*     */   }
/*     */ 
/*     */   public BasePaymentPlugins(Long id, String name, String code, Integer priority, Boolean disabled)
/*     */   {
/*  55 */     setId(id);
/*  56 */     setName(name);
/*  57 */     setCode(code);
/*  58 */     setPriority(priority);
/*  59 */     setDisabled(disabled);
/*  60 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  95 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 103 */     this.id = id;
/* 104 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 114 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 122 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getCode()
/*     */   {
/* 130 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code)
/*     */   {
/* 138 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 146 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/* 154 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public Integer getPriority()
/*     */   {
/* 162 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority)
/*     */   {
/* 170 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public String getImgPath()
/*     */   {
/* 178 */     return this.imgPath;
/*     */   }
/*     */ 
/*     */   public void setImgPath(String imgPath)
/*     */   {
/* 186 */     this.imgPath = imgPath;
/*     */   }
/*     */ 
/*     */   public String getPartner()
/*     */   {
/* 194 */     return this.partner;
/*     */   }
/*     */ 
/*     */   public void setPartner(String partner)
/*     */   {
/* 202 */     this.partner = partner;
/*     */   }
/*     */ 
/*     */   public String getSellerKey()
/*     */   {
/* 210 */     return this.sellerKey;
/*     */   }
/*     */ 
/*     */   public void setSellerKey(String sellerKey)
/*     */   {
/* 218 */     this.sellerKey = sellerKey;
/*     */   }
/*     */ 
/*     */   public String getSellerEmail()
/*     */   {
/* 226 */     return this.sellerEmail;
/*     */   }
/*     */ 
/*     */   public void setSellerEmail(String sellerEmail)
/*     */   {
/* 234 */     this.sellerEmail = sellerEmail;
/*     */   }
/*     */ 
/*     */   public String getPlatform()
/*     */   {
/* 239 */     return this.platform;
/*     */   }
/*     */ 
/*     */   public void setPlatform(String platform) {
/* 243 */     this.platform = platform;
/*     */   }
/*     */ 
/*     */   public String getPublicKey() {
/* 247 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */   public void setPublicKey(String publicKey) {
/* 251 */     this.publicKey = publicKey;
/*     */   }
/*     */ 
/*     */   public Boolean getDisabled()
/*     */   {
/* 257 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(Boolean disabled)
/*     */   {
/* 262 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public Boolean getIsDefault()
/*     */   {
/* 267 */     return this.isDefault;
/*     */   }
/*     */ 
/*     */   public void setIsDefault(Boolean isDefault) {
/* 271 */     this.isDefault = isDefault;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 277 */     if (obj == null) return false;
/* 278 */     if (!(obj instanceof PaymentPlugins)) return false;
/*     */ 
/* 280 */     PaymentPlugins paymentPlugins = (PaymentPlugins)obj;
/* 281 */     if ((getId() == null) || (paymentPlugins.getId() == null)) return false;
/* 282 */     return getId().equals(paymentPlugins.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 288 */     if (-2147483648 == this.hashCode) {
/* 289 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 291 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 292 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 295 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 301 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BasePaymentPlugins
 * JD-Core Version:    0.6.0
 */