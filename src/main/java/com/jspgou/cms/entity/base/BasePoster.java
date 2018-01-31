/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Poster;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BasePoster
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "Poster";
/*  20 */   public static String PROP_TIME = "time";
/*  21 */   public static String PROP_PIC_URL = "picUrl";
/*  22 */   public static String PROP_INTER_URL = "interUrl";
/*  23 */   public static String PROP_ID = "id";
/*     */ 
/*  55 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String picUrl;
/*     */   private Date time;
/*     */   private String interUrl;
/*     */ 
/*     */   public BasePoster()
/*     */   {
/*  28 */     initialize();
/*     */   }
/*     */ 
/*     */   public BasePoster(Integer id)
/*     */   {
/*  35 */     setId(id);
/*  36 */     initialize();
/*     */   }
/*     */ 
/*     */   public BasePoster(Integer id, String picUrl)
/*     */   {
/*  46 */     setId(id);
/*  47 */     setPicUrl(picUrl);
/*  48 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  74 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  82 */     this.id = id;
/*  83 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getPicUrl()
/*     */   {
/*  93 */     return this.picUrl;
/*     */   }
/*     */ 
/*     */   public void setPicUrl(String picUrl)
/*     */   {
/* 101 */     this.picUrl = picUrl;
/*     */   }
/*     */ 
/*     */   public Date getTime()
/*     */   {
/* 109 */     return this.time;
/*     */   }
/*     */ 
/*     */   public void setTime(Date time)
/*     */   {
/* 117 */     this.time = time;
/*     */   }
/*     */ 
/*     */   public String getInterUrl()
/*     */   {
/* 125 */     return this.interUrl;
/*     */   }
/*     */ 
/*     */   public void setInterUrl(String interUrl)
/*     */   {
/* 133 */     this.interUrl = interUrl;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 140 */     if (obj == null) return false;
/* 141 */     if (!(obj instanceof Poster)) return false;
/*     */ 
/* 143 */     Poster poster = (Poster)obj;
/* 144 */     if ((getId() == null) || (poster.getId() == null)) return false;
/* 145 */     return getId().equals(poster.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 151 */     if (-2147483648 == this.hashCode) {
/* 152 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 154 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 155 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 158 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 164 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BasePoster
 * JD-Core Version:    0.6.0
 */