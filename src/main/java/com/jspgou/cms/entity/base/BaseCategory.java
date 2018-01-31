/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Brand;
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.StandardType;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseCategory
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "Category";
/*  24 */   public static String PROP_RGT = "rgt";
/*  25 */   public static String PROP_PARENT = "parent";
/*  26 */   public static String PROP_DESCRIPTION = "description";
/*  27 */   public static String PROP_WEBSITE = "website";
/*  28 */   public static String PROP_TPL_CHANNEL = "tplChannel";
/*  29 */   public static String PROP_TYPE = "type";
/*  30 */   public static String PROP_TITLE = "title";
/*  31 */   public static String PROP_PATH = "path";
/*  32 */   public static String PROP_PRIORITY = "priority";
/*  33 */   public static String PROP_NAME = "name";
/*  34 */   public static String PROP_ID = "id";
/*  35 */   public static String PROP_LFT = "lft";
/*  36 */   public static String PROP_IMAGE_PATH = "imagePath";
/*  37 */   public static String PROP_KEYWORDS = "keywords";
/*  38 */   public static String PROP_TPL_CONTENT = "tplContent";
/*     */ 
/*  82 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String name;
/*     */   private String path;
/*     */   private String tplChannel;
/*     */   private String tplContent;
/*     */   private Integer lft;
/*     */   private Integer rgt;
/*     */   private Integer priority;
/*     */   private String title;
/*     */   private String imagePath;
/*     */   private String keywords;
/*     */   private String description;
/*     */   private Boolean colorsize;
/*     */   private Category parent;
/*     */   private ProductType type;
/*     */   private Website website;
/*     */   private Set<Category> child;
/*     */   private Set<Brand> brands;
/*     */   private Set<StandardType> standardType;
/*     */   private Map<String, String> attr;
/*     */ 
/*     */   public BaseCategory()
/*     */   {
/*  43 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCategory(Integer id)
/*     */   {
/*  50 */     setId(id);
/*  51 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCategory(Integer id, ProductType type, Website website, String name, String path, Integer lft, Integer rgt, Integer priority)
/*     */   {
/*  67 */     setId(id);
/*  68 */     setType(type);
/*  69 */     setWebsite(website);
/*  70 */     setName(name);
/*  71 */     setPath(path);
/*  72 */     setLft(lft);
/*  73 */     setRgt(rgt);
/*  74 */     setPriority(priority);
/*  75 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/* 122 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/* 130 */     this.id = id;
/* 131 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 141 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 149 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/* 157 */     return this.path;
/*     */   }
/*     */ 
/*     */   public void setPath(String path)
/*     */   {
/* 165 */     this.path = path;
/*     */   }
/*     */ 
/*     */   public String getTplChannel()
/*     */   {
/* 173 */     return this.tplChannel;
/*     */   }
/*     */ 
/*     */   public void setTplChannel(String tplChannel)
/*     */   {
/* 181 */     this.tplChannel = tplChannel;
/*     */   }
/*     */ 
/*     */   public String getTplContent()
/*     */   {
/* 189 */     return this.tplContent;
/*     */   }
/*     */ 
/*     */   public void setTplContent(String tplContent)
/*     */   {
/* 197 */     this.tplContent = tplContent;
/*     */   }
/*     */ 
/*     */   public Integer getLft()
/*     */   {
/* 205 */     return this.lft;
/*     */   }
/*     */ 
/*     */   public void setLft(Integer lft)
/*     */   {
/* 213 */     this.lft = lft;
/*     */   }
/*     */ 
/*     */   public Integer getRgt()
/*     */   {
/* 221 */     return this.rgt;
/*     */   }
/*     */ 
/*     */   public void setRgt(Integer rgt)
/*     */   {
/* 229 */     this.rgt = rgt;
/*     */   }
/*     */ 
/*     */   public Integer getPriority()
/*     */   {
/* 237 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority)
/*     */   {
/* 245 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public Boolean getColorsize()
/*     */   {
/* 250 */     return this.colorsize;
/*     */   }
/*     */ 
/*     */   public void setColorsize(Boolean colorsize) {
/* 254 */     this.colorsize = colorsize;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 262 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 270 */     this.title = title;
/*     */   }
/*     */ 
/*     */   public String getImagePath()
/*     */   {
/* 278 */     return this.imagePath;
/*     */   }
/*     */ 
/*     */   public void setImagePath(String imagePath)
/*     */   {
/* 286 */     this.imagePath = imagePath;
/*     */   }
/*     */ 
/*     */   public String getKeywords()
/*     */   {
/* 294 */     return this.keywords;
/*     */   }
/*     */ 
/*     */   public void setKeywords(String keywords)
/*     */   {
/* 302 */     this.keywords = keywords;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 310 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/* 318 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public Category getParent()
/*     */   {
/* 326 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public void setParent(Category parent)
/*     */   {
/* 334 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   public ProductType getType()
/*     */   {
/* 342 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(ProductType type)
/*     */   {
/* 350 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 358 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 366 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public Set<Category> getChild()
/*     */   {
/* 374 */     return this.child;
/*     */   }
/*     */ 
/*     */   public void setChild(Set<Category> child)
/*     */   {
/* 382 */     this.child = child;
/*     */   }
/*     */ 
/*     */   public Set<Brand> getBrands()
/*     */   {
/* 390 */     return this.brands;
/*     */   }
/*     */ 
/*     */   public void setBrands(Set<Brand> brands)
/*     */   {
/* 398 */     this.brands = brands;
/*     */   }
/*     */ 
/*     */   public Set<StandardType> getStandardType() {
/* 402 */     return this.standardType;
/*     */   }
/*     */ 
/*     */   public void setStandardType(Set<StandardType> standardType)
/*     */   {
/* 407 */     this.standardType = standardType;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAttr()
/*     */   {
/* 414 */     return this.attr;
/*     */   }
/*     */ 
/*     */   public void setAttr(Map<String, String> attr)
/*     */   {
/* 422 */     this.attr = attr;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 427 */     if (obj == null) return false;
/* 428 */     if (!(obj instanceof Category)) return false;
/*     */ 
/* 430 */     Category category = (Category)obj;
/* 431 */     if ((getId() == null) || (category.getId() == null)) return false;
/* 432 */     return getId().equals(category.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 438 */     if (-2147483648 == this.hashCode) {
/* 439 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 441 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 442 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 445 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 451 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseCategory
 * JD-Core Version:    0.6.0
 */