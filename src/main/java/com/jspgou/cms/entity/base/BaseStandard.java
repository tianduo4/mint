/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.Standard;
/*     */ import com.jspgou.cms.entity.StandardType;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseStandard
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "Standard";
/*  20 */   public static String PROP_NAME = "name";
/*  21 */   public static String PROP_TYPE = "type";
/*  22 */   public static String PROP_ID = "id";
/*  23 */   public static String PROP_PRIORITY = "priority";
/*  24 */   public static String PROP_COLOR = "color";
/*     */ 
/*  58 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private String color;
/*     */   private Integer priority;
/*     */   private StandardType type;
/*     */   private Set<ProductFashion> fashions;
/*     */ 
/*     */   public BaseStandard()
/*     */   {
/*  29 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseStandard(Long id)
/*     */   {
/*  36 */     setId(id);
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseStandard(Long id, StandardType type, String name)
/*     */   {
/*  48 */     setId(id);
/*  49 */     setType(type);
/*  50 */     setName(name);
/*  51 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  81 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  89 */     this.id = id;
/*  90 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 100 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 108 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getColor()
/*     */   {
/* 116 */     return this.color;
/*     */   }
/*     */ 
/*     */   public void setColor(String color)
/*     */   {
/* 124 */     this.color = color;
/*     */   }
/*     */ 
/*     */   public Integer getPriority()
/*     */   {
/* 132 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority)
/*     */   {
/* 140 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public StandardType getType()
/*     */   {
/* 148 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(StandardType type)
/*     */   {
/* 156 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public void setFashions(Set<ProductFashion> fashions) {
/* 160 */     this.fashions = fashions;
/*     */   }
/*     */ 
/*     */   public Set<ProductFashion> getFashions() {
/* 164 */     return this.fashions;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 169 */     if (obj == null) return false;
/* 170 */     if (!(obj instanceof Standard)) return false;
/*     */ 
/* 172 */     Standard standard = (Standard)obj;
/* 173 */     if ((getId() == null) || (standard.getId() == null)) return false;
/* 174 */     return getId().equals(standard.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 180 */     if (-2147483648 == this.hashCode) {
/* 181 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 183 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 184 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 187 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 193 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseStandard
 * JD-Core Version:    0.6.0
 */