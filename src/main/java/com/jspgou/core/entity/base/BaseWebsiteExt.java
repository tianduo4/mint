/*     */ package com.jspgou.core.entity.base;
/*     */ 
/*     */ import com.jspgou.core.entity.WebsiteExt;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseWebsiteExt
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "WebsiteExt";
/*  18 */   public static String PROP_VALUE = "value";
/*  19 */   public static String PROP_ID = "id";
/*     */ 
/*  39 */   private int hashCode = -2147483648;
/*     */   private String id;
/*     */   private String value;
/*     */ 
/*     */   public BaseWebsiteExt()
/*     */   {
/*  24 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseWebsiteExt(String id)
/*     */   {
/*  31 */     setId(id);
/*  32 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  56 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id)
/*     */   {
/*  64 */     this.id = id;
/*  65 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */   {
/*  75 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/*  83 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  90 */     if (obj == null) return false;
/*  91 */     if (!(obj instanceof WebsiteExt)) return false;
/*     */ 
/*  93 */     WebsiteExt websiteExt = (WebsiteExt)obj;
/*  94 */     if ((getId() == null) || (websiteExt.getId() == null)) return false;
/*  95 */     return getId().equals(websiteExt.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 101 */     if (-2147483648 == this.hashCode) {
/* 102 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 104 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 105 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 108 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 114 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.base.BaseWebsiteExt
 * JD-Core Version:    0.6.0
 */