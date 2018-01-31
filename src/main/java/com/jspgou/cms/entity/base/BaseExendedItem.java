/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Exended;
/*     */ import com.jspgou.cms.entity.ExendedItem;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseExendedItem
/*     */   implements Serializable
/*     */ {
/*  19 */   public static String REF = "ExendedItem";
/*  20 */   public static String PROP_NAME = "name";
/*  21 */   public static String PROP_ID = "id";
/*  22 */   public static String PROP_EXENDED = "exended";
/*  23 */   public static String PROP_PRIORITY = "priority";
/*     */ 
/*  57 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private Exended exended;
/*     */ 
/*     */   public BaseExendedItem()
/*     */   {
/*  28 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseExendedItem(Long id)
/*     */   {
/*  35 */     setId(id);
/*  36 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseExendedItem(Long id, Exended exended, String name)
/*     */   {
/*  47 */     setId(id);
/*  48 */     setExended(exended);
/*  49 */     setName(name);
/*  50 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  77 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  85 */     this.id = id;
/*  86 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  96 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 104 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Exended getExended()
/*     */   {
/* 112 */     return this.exended;
/*     */   }
/*     */ 
/*     */   public void setExended(Exended exended)
/*     */   {
/* 120 */     this.exended = exended;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 127 */     if (obj == null) return false;
/* 128 */     if (!(obj instanceof ExendedItem)) return false;
/*     */ 
/* 130 */     ExendedItem exendedItem = (ExendedItem)obj;
/* 131 */     if ((getId() == null) || (exendedItem.getId() == null)) return false;
/* 132 */     return getId().equals(exendedItem.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 138 */     if (-2147483648 == this.hashCode) {
/* 139 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 141 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 142 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 145 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 151 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseExendedItem
 * JD-Core Version:    0.6.0
 */