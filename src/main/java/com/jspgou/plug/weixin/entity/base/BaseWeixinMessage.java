/*     */ package com.jspgou.plug.weixin.entity.base;
/*     */ 
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.plug.weixin.entity.WeixinMessage;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseWeixinMessage
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "WeixinMessage";
/*  18 */   public static String PROP_SITE = "site";
/*  19 */   public static String PROP_URL = "url";
/*  20 */   public static String PROP_NUMBER = "number";
/*  21 */   public static String PROP_ID = "id";
/*  22 */   public static String PROP_CONTENT = "content";
/*  23 */   public static String PROP_WELCOME = "welcome";
/*  24 */   public static String PROP_TITLE = "title";
/*  25 */   public static String PROP_PATH = "path";
/*     */ 
/*  45 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String number;
/*     */   private String title;
/*     */   private String path;
/*     */   private String url;
/*     */   private String content;
/*     */   private Boolean welcome;
/*     */   private Integer type;
/*     */   private Website site;
/*     */ 
/*     */   public BaseWeixinMessage()
/*     */   {
/*  30 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseWeixinMessage(Integer id)
/*     */   {
/*  37 */     setId(id);
/*  38 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  71 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  79 */     this.id = id;
/*  80 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getNumber()
/*     */   {
/*  90 */     return this.number;
/*     */   }
/*     */ 
/*     */   public void setNumber(String number)
/*     */   {
/*  98 */     this.number = number;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 107 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 115 */     this.title = title;
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/* 124 */     return this.path;
/*     */   }
/*     */ 
/*     */   public void setPath(String path)
/*     */   {
/* 132 */     this.path = path;
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/* 141 */     return this.url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url)
/*     */   {
/* 149 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 158 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content)
/*     */   {
/* 166 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public Boolean isWelcome()
/*     */   {
/* 175 */     return this.welcome;
/*     */   }
/*     */ 
/*     */   public void setWelcome(Boolean welcome)
/*     */   {
/* 183 */     this.welcome = welcome;
/*     */   }
/*     */ 
/*     */   public Integer getType() {
/* 187 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(Integer type) {
/* 191 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public Website getSite()
/*     */   {
/* 198 */     return this.site;
/*     */   }
/*     */ 
/*     */   public void setSite(Website site)
/*     */   {
/* 206 */     this.site = site;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 213 */     if (obj == null) return false;
/* 214 */     if (!(obj instanceof WeixinMessage)) return false;
/*     */ 
/* 216 */     WeixinMessage weixinMessage = (WeixinMessage)obj;
/* 217 */     if ((getId() == null) || (weixinMessage.getId() == null)) return false;
/* 218 */     return getId().equals(weixinMessage.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 223 */     if (-2147483648 == this.hashCode) {
/* 224 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 226 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 227 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 230 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 235 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.weixin.entity.base.BaseWeixinMessage
 * JD-Core Version:    0.6.0
 */