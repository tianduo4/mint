/*     */ package com.jspgou.core.entity.base;
/*     */ 
/*     */ import com.jspgou.core.entity.Log;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseLog
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "Log";
/*  18 */   public static String PROP_USER = "user";
/*  19 */   public static String PROP_IP = "ip";
/*  20 */   public static String PROP_CATEGORY = "category";
/*  21 */   public static String PROP_SITE = "site";
/*  22 */   public static String PROP_TIME = "time";
/*  23 */   public static String PROP_URL = "url";
/*  24 */   public static String PROP_ID = "id";
/*  25 */   public static String PROP_CONTENT = "content";
/*  26 */   public static String PROP_TITLE = "title";
/*     */ 
/*  60 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private Integer category;
/*     */   private Date time;
/*     */   private String ip;
/*     */   private String url;
/*     */   private String title;
/*     */   private String content;
/*     */   private User user;
/*     */   private Website site;
/*     */ 
/*     */   public BaseLog()
/*     */   {
/*  31 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseLog(Long id)
/*     */   {
/*  38 */     setId(id);
/*  39 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseLog(Long id, Integer category, Date time)
/*     */   {
/*  50 */     setId(id);
/*  51 */     setCategory(category);
/*  52 */     setTime(time);
/*  53 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  85 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  93 */     this.id = id;
/*  94 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public Integer getCategory()
/*     */   {
/* 104 */     return this.category;
/*     */   }
/*     */ 
/*     */   public void setCategory(Integer category)
/*     */   {
/* 112 */     this.category = category;
/*     */   }
/*     */ 
/*     */   public Date getTime()
/*     */   {
/* 120 */     return this.time;
/*     */   }
/*     */ 
/*     */   public void setTime(Date time)
/*     */   {
/* 128 */     this.time = time;
/*     */   }
/*     */ 
/*     */   public String getIp()
/*     */   {
/* 136 */     return this.ip;
/*     */   }
/*     */ 
/*     */   public void setIp(String ip)
/*     */   {
/* 144 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/* 152 */     return this.url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url)
/*     */   {
/* 160 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 168 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 176 */     this.title = title;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 184 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content)
/*     */   {
/* 192 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public User getUser()
/*     */   {
/* 200 */     return this.user;
/*     */   }
/*     */ 
/*     */   public void setUser(User user)
/*     */   {
/* 208 */     this.user = user;
/*     */   }
/*     */ 
/*     */   public Website getSite()
/*     */   {
/* 215 */     return this.site;
/*     */   }
/*     */ 
/*     */   public void setSite(Website site)
/*     */   {
/* 223 */     this.site = site;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 230 */     if (obj == null) return false;
/* 231 */     if (!(obj instanceof Log)) return false;
/*     */ 
/* 233 */     Log log = (Log)obj;
/* 234 */     if ((getId() == null) || (log.getId() == null)) return false;
/* 235 */     return getId().equals(log.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 241 */     if (-2147483648 == this.hashCode) {
/* 242 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 244 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 245 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 248 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 254 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.base.BaseLog
 * JD-Core Version:    0.6.0
 */