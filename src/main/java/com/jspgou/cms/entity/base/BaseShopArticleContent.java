/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopArticle;
/*     */ import com.jspgou.cms.entity.ShopArticleContent;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseShopArticleContent
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "ShopArticleContent";
/*  20 */   public static String PROP_CONTENT = "content";
/*  21 */   public static String PROP_ID = "id";
/*  22 */   public static String PROP_ARTICLE = "article";
/*     */ 
/*  42 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String content;
/*     */   private ShopArticle article;
/*     */ 
/*     */   public BaseShopArticleContent()
/*     */   {
/*  27 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseShopArticleContent(Long id)
/*     */   {
/*  34 */     setId(id);
/*  35 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  62 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  70 */     this.id = id;
/*  71 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/*  81 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content)
/*     */   {
/*  89 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public ShopArticle getArticle()
/*     */   {
/*  97 */     return this.article;
/*     */   }
/*     */ 
/*     */   public void setArticle(ShopArticle article)
/*     */   {
/* 105 */     this.article = article;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 112 */     if (obj == null) return false;
/* 113 */     if (!(obj instanceof ShopArticleContent)) return false;
/*     */ 
/* 115 */     ShopArticleContent shopArticleContent = (ShopArticleContent)obj;
/* 116 */     if ((getId() == null) || (shopArticleContent.getId() == null)) return false;
/* 117 */     return getId().equals(shopArticleContent.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 123 */     if (-2147483648 == this.hashCode) {
/* 124 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 126 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 127 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 130 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 136 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseShopArticleContent
 * JD-Core Version:    0.6.0
 */