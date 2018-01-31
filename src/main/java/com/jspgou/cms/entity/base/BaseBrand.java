/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Brand;
/*     */ import com.jspgou.cms.entity.BrandText;
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseBrand
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "Brand";
/*  24 */   public static String PROP_NAME = "name";
/*  25 */   public static String PROP_ALIAS = "alias";
/*  26 */   public static String PROP_WEBSITE = "website";
/*  27 */   public static String PROP_ID = "id";
/*  28 */   public static String PROP_WEB_URL = "webUrl";
/*  29 */   public static String PROP_SIFT = "sift";
/*  30 */   public static String PROP_PRIORITY = "priority";
/*  31 */   public static String PROP_LOGO_PATH = "logoPath";
/*  32 */   public static String PROP_CATEGORY = "category";
/*     */ 
/*  70 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private String alias;
/*     */   private String webUrl;
/*     */   private String logoPath;
/*     */   private Integer priority;
/*     */   private Boolean sift;
/*     */   private Boolean disabled;
/*     */   private String firstCharacter;
/*     */   private String brandtype;
/*     */   private Website website;
/*     */   private Category category;
/*     */   private Set<Category> categorys;
/*     */   private Set<BrandText> brandTextSet;
/*     */ 
/*     */   public BaseBrand()
/*     */   {
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseBrand(Long id)
/*     */   {
/*  44 */     setId(id);
/*  45 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseBrand(Long id, Website website, Category category, String name, Integer priority)
/*     */   {
/*  58 */     setId(id);
/*  59 */     setWebsite(website);
/*  60 */     setCategory(category);
/*  61 */     setName(name);
/*  62 */     setPriority(priority);
/*  63 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 103 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 111 */     this.id = id;
/* 112 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Boolean getDisabled()
/*     */   {
/* 118 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(Boolean disabled) {
/* 122 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public String getFirstCharacter() {
/* 126 */     return this.firstCharacter;
/*     */   }
/*     */ 
/*     */   public void setFirstCharacter(String firstCharacter) {
/* 130 */     this.firstCharacter = firstCharacter;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 137 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 145 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public void setBrandtype(String brandtype) {
/* 149 */     this.brandtype = brandtype;
/*     */   }
/*     */ 
/*     */   public String getBrandtype() {
/* 153 */     return this.brandtype;
/*     */   }
/*     */ 
/*     */   public String getAlias()
/*     */   {
/* 160 */     return this.alias;
/*     */   }
/*     */ 
/*     */   public void setAlias(String alias)
/*     */   {
/* 168 */     this.alias = alias;
/*     */   }
/*     */ 
/*     */   public String getWebUrl()
/*     */   {
/* 176 */     return this.webUrl;
/*     */   }
/*     */ 
/*     */   public void setWebUrl(String webUrl)
/*     */   {
/* 184 */     this.webUrl = webUrl;
/*     */   }
/*     */ 
/*     */   public String getLogoPath()
/*     */   {
/* 192 */     return this.logoPath;
/*     */   }
/*     */ 
/*     */   public void setLogoPath(String logoPath)
/*     */   {
/* 200 */     this.logoPath = logoPath;
/*     */   }
/*     */ 
/*     */   public Integer getPriority()
/*     */   {
/* 208 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority)
/*     */   {
/* 216 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public Boolean getSift()
/*     */   {
/* 224 */     return this.sift;
/*     */   }
/*     */ 
/*     */   public void setSift(Boolean sift)
/*     */   {
/* 232 */     this.sift = sift;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 240 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 248 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public Set<Category> getCategorys()
/*     */   {
/* 255 */     return this.categorys;
/*     */   }
/*     */ 
/*     */   public void setCategorys(Set<Category> categorys)
/*     */   {
/* 263 */     this.categorys = categorys;
/*     */   }
/*     */ 
/*     */   public Set<BrandText> getBrandTextSet()
/*     */   {
/* 271 */     return this.brandTextSet;
/*     */   }
/*     */ 
/*     */   public void setBrandTextSet(Set<BrandText> brandTextSet)
/*     */   {
/* 279 */     this.brandTextSet = brandTextSet;
/*     */   }
/*     */ 
/*     */   public Category getCategory()
/*     */   {
/* 289 */     return this.category;
/*     */   }
/*     */ 
/*     */   public void setCategory(Category category)
/*     */   {
/* 297 */     this.category = category;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 303 */     if (obj == null) return false;
/* 304 */     if (!(obj instanceof Brand)) return false;
/*     */ 
/* 306 */     Brand brand = (Brand)obj;
/* 307 */     if ((getId() == null) || (brand.getId() == null)) return false;
/* 308 */     return getId().equals(brand.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 314 */     if (-2147483648 == this.hashCode) {
/* 315 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 317 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 318 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 321 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 327 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseBrand
 * JD-Core Version:    0.6.0
 */