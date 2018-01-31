/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.entity.Standard;
/*     */ import com.jspgou.cms.entity.StandardType;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseStandardType
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "StandardType";
/*  20 */   public static String PROP_NAME = "name";
/*  21 */   public static String PROP_DATA_TYPE = "dataType";
/*  22 */   public static String PROP_FIELD = "field";
/*  23 */   public static String PROP_ID = "id";
/*  24 */   public static String PROP_PRIORITY = "priority";
/*     */ 
/*  60 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private String field;
/*     */   private String remark;
/*     */   private boolean dataType;
/*     */   private Integer priority;
/*     */   private Set<Standard> standardSet;
/*     */   private Set<Category> categorys;
/*     */ 
/*     */   public BaseStandardType()
/*     */   {
/*  29 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseStandardType(Long id)
/*     */   {
/*  36 */     setId(id);
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseStandardType(Long id, String name, String field, boolean dataType)
/*     */   {
/*  49 */     setId(id);
/*  50 */     setName(name);
/*  51 */     setField(field);
/*  52 */     setDataType(dataType);
/*  53 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  84 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  92 */     this.id = id;
/*  93 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 103 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 111 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getField()
/*     */   {
/* 119 */     return this.field;
/*     */   }
/*     */ 
/*     */   public void setField(String field)
/*     */   {
/* 127 */     this.field = field;
/*     */   }
/*     */ 
/*     */   public boolean getDataType()
/*     */   {
/* 135 */     return this.dataType;
/*     */   }
/*     */ 
/*     */   public void setDataType(boolean dataType)
/*     */   {
/* 143 */     this.dataType = dataType;
/*     */   }
/*     */ 
/*     */   public Integer getPriority()
/*     */   {
/* 151 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority)
/*     */   {
/* 159 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public Set<Standard> getStandardSet()
/*     */   {
/* 166 */     return this.standardSet;
/*     */   }
/*     */ 
/*     */   public void setStandardSet(Set<Standard> standardSet)
/*     */   {
/* 174 */     this.standardSet = standardSet;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 181 */     if (obj == null) return false;
/* 182 */     if (!(obj instanceof StandardType)) return false;
/*     */ 
/* 184 */     StandardType standardType = (StandardType)obj;
/* 185 */     if ((getId() == null) || (standardType.getId() == null)) return false;
/* 186 */     return getId().equals(standardType.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 192 */     if (-2147483648 == this.hashCode) {
/* 193 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 195 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 196 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 199 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 205 */     return super.toString();
/*     */   }
/*     */ 
/*     */   public void setCategorys(Set<Category> categorys) {
/* 209 */     this.categorys = categorys;
/*     */   }
/*     */ 
/*     */   public Set<Category> getCategorys() {
/* 213 */     return this.categorys;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/* 217 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public String getRemark() {
/* 221 */     return this.remark;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseStandardType
 * JD-Core Version:    0.6.0
 */