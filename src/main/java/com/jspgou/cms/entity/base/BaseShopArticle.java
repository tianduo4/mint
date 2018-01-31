/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopArticle;
/*     */ import com.jspgou.cms.entity.ShopArticleContent;
/*     */ import com.jspgou.cms.entity.ShopChannel;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseShopArticle
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   public static String REF = "ShopArticle";
/*  24 */   public static String PROP_ARTICLE_CONTENT = "articleContent";
/*  25 */   public static String PROP_PUBLISH_TIME = "publishTime";
/*  26 */   public static String PROP_LINK = "link";
/*  27 */   public static String PROP_WEBSITE = "website";
/*  28 */   public static String PROP_PARAM3 = "param3";
/*  29 */   public static String PROP_DISABLED = "disabled";
/*  30 */   public static String PROP_CHANNEL = "channel";
/*  31 */   public static String PROP_TITLE = "title";
/*  32 */   public static String PROP_PARAM2 = "param2";
/*  33 */   public static String PROP_ID = "id";
/*     */ 
/*  73 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String title;
/*     */   private Date publishTime;
/*     */   private Boolean disabled;
/*     */   private String link;
/*     */   private String param2;
/*     */   private String param3;
/*     */   private ShopArticleContent articleContent;
/*     */   private Website website;
/*     */   private ShopChannel channel;
/*     */ 
/*     */   public BaseShopArticle()
/*     */   {
/*  38 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopArticle(Long id)
/*     */   {
/*  45 */     setId(id);
/*  46 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopArticle(Long id, Website website, ShopChannel channel, String title, Date publishTime, Boolean disabled)
/*     */   {
/*  60 */     setId(id);
/*  61 */     setWebsite(website);
/*  62 */     setChannel(channel);
/*  63 */     setTitle(title);
/*  64 */     setPublishTime(publishTime);
/*  65 */     setDisabled(disabled);
/*  66 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 102 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 110 */     this.id = id;
/* 111 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 121 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 129 */     this.title = title;
/*     */   }
/*     */ 
/*     */   public Date getPublishTime()
/*     */   {
/* 137 */     return this.publishTime;
/*     */   }
/*     */ 
/*     */   public void setPublishTime(Date publishTime)
/*     */   {
/* 145 */     this.publishTime = publishTime;
/*     */   }
/*     */ 
/*     */   public Boolean getDisabled()
/*     */   {
/* 153 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(Boolean disabled)
/*     */   {
/* 161 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */   public String getLink()
/*     */   {
/* 169 */     return this.link;
/*     */   }
/*     */ 
/*     */   public void setLink(String link)
/*     */   {
/* 177 */     this.link = link;
/*     */   }
/*     */ 
/*     */   public String getParam2()
/*     */   {
/* 185 */     return this.param2;
/*     */   }
/*     */ 
/*     */   public void setParam2(String param2)
/*     */   {
/* 193 */     this.param2 = param2;
/*     */   }
/*     */ 
/*     */   public String getParam3()
/*     */   {
/* 201 */     return this.param3;
/*     */   }
/*     */ 
/*     */   public void setParam3(String param3)
/*     */   {
/* 209 */     this.param3 = param3;
/*     */   }
/*     */ 
/*     */   public ShopArticleContent getArticleContent()
/*     */   {
/* 217 */     return this.articleContent;
/*     */   }
/*     */ 
/*     */   public void setArticleContent(ShopArticleContent articleContent)
/*     */   {
/* 225 */     this.articleContent = articleContent;
/*     */   }
/*     */ 
/*     */   public Website getWebsite()
/*     */   {
/* 233 */     return this.website;
/*     */   }
/*     */ 
/*     */   public void setWebsite(Website website)
/*     */   {
/* 241 */     this.website = website;
/*     */   }
/*     */ 
/*     */   public ShopChannel getChannel()
/*     */   {
/* 249 */     return this.channel;
/*     */   }
/*     */ 
/*     */   public void setChannel(ShopChannel channel)
/*     */   {
/* 257 */     this.channel = channel;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 264 */     if (obj == null) return false;
/* 265 */     if (!(obj instanceof ShopArticle)) return false;
/*     */ 
/* 267 */     ShopArticle shopArticle = (ShopArticle)obj;
/* 268 */     if ((getId() == null) || (shopArticle.getId() == null)) return false;
/* 269 */     return getId().equals(shopArticle.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 275 */     if (-2147483648 == this.hashCode) {
/* 276 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 278 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 279 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 282 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 288 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopArticle
 * JD-Core Version:    0.6.0
 */