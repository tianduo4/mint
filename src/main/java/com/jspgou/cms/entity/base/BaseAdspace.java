/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Adspace;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseAdspace
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "Adspace";
/*  20 */   public static String PROP_NAME = "name";
/*  21 */   public static String PROP_DESCRIPTION = "description";
/*  22 */   public static String PROP_ENABLE = "enable";
/*  23 */   public static String PROP_ID = "id";
/*     */ 
/*  43 */   private int hashCode = -2147483648;
/*     */   private Integer id;
/*     */   private String name;
/*     */   private String description;
/*     */   private Boolean enabled;
/*     */ 
/*     */   public BaseAdspace()
/*     */   {
/*  28 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseAdspace(Integer id)
/*     */   {
/*  35 */     setId(id);
/*  36 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Integer getId()
/*     */   {
/*  62 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id)
/*     */   {
/*  70 */     this.id = id;
/*  71 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  81 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  89 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  97 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/* 105 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public Boolean getEnabled()
/*     */   {
/* 113 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   public void setEnabled(Boolean enabled)
/*     */   {
/* 121 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 128 */     if (obj == null) return false;
/* 129 */     if (!(obj instanceof Adspace)) return false;
/*     */ 
/* 131 */     Adspace adspace = (Adspace)obj;
/* 132 */     if ((getId() == null) || (adspace.getId() == null)) return false;
/* 133 */     return getId().equals(adspace.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 139 */     if (-2147483648 == this.hashCode) {
/* 140 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 142 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 143 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 146 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 152 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseAdspace
 * JD-Core Version:    0.6.0
 */