/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Exended;
/*     */ import com.jspgou.cms.entity.ExendedItem;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseExended
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "Exended";
/*  20 */   public static String PROP_NAME = "name";
/*  21 */   public static String PROP_DATA_TYPE = "dataType";
/*  22 */   public static String PROP_FIELD = "field";
/*  23 */   public static String PROP_ID = "id";
/*  24 */   public static String PROP_PRIORITY = "priority";
/*     */ 
/*  58 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private String field;
/*     */   private Byte dataType;
/*     */   private Integer priority;
/*     */   private Set<ProductType> productTypes;
/*     */   private Set<ExendedItem> items;
/*     */ 
/*     */   public BaseExended()
/*     */   {
/*  29 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseExended(Long id)
/*     */   {
/*  36 */     setId(id);
/*  37 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseExended(Long id, String name, String field)
/*     */   {
/*  48 */     setId(id);
/*  49 */     setName(name);
/*  50 */     setField(field);
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
/*     */   public String getField()
/*     */   {
/* 116 */     return this.field;
/*     */   }
/*     */ 
/*     */   public void setField(String field)
/*     */   {
/* 124 */     this.field = field;
/*     */   }
/*     */ 
/*     */   public Byte getDataType()
/*     */   {
/* 132 */     return this.dataType;
/*     */   }
/*     */ 
/*     */   public void setDataType(Byte dataType)
/*     */   {
/* 140 */     this.dataType = dataType;
/*     */   }
/*     */ 
/*     */   public Integer getPriority()
/*     */   {
/* 148 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority)
/*     */   {
/* 156 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public Set<ProductType> getProductTypes()
/*     */   {
/* 164 */     return this.productTypes;
/*     */   }
/*     */ 
/*     */   public void setProductTypes(Set<ProductType> productTypes)
/*     */   {
/* 172 */     this.productTypes = productTypes;
/*     */   }
/*     */ 
/*     */   public void setItems(Set<ExendedItem> items)
/*     */   {
/* 178 */     this.items = items;
/*     */   }
/*     */ 
/*     */   public Set<ExendedItem> getItems() {
/* 182 */     return this.items;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 187 */     if (obj == null) return false;
/* 188 */     if (!(obj instanceof Exended)) return false;
/*     */ 
/* 190 */     Exended exended = (Exended)obj;
/* 191 */     if ((getId() == null) || (exended.getId() == null)) return false;
/* 192 */     return getId().equals(exended.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 198 */     if (-2147483648 == this.hashCode) {
/* 199 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 201 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 202 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 205 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 211 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseExended
 * JD-Core Version:    0.6.0
 */