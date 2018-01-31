/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Address;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseAddress
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  22 */   public static String REF = "Address";
/*  23 */   public static String PROP_NAME = "name";
/*  24 */   public static String PROP_PARENT = "parent";
/*  25 */   public static String PROP_ID = "id";
/*     */ 
/*  57 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private Integer priority;
/*     */   private Address parent;
/*     */ 
/*     */   public BaseAddress()
/*     */   {
/*  30 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseAddress(Long id)
/*     */   {
/*  37 */     setId(id);
/*  38 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseAddress(Long id, String name)
/*     */   {
/*  48 */     setId(id);
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
/*  78 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/*  86 */     this.id = id;
/*  87 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  97 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 105 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Integer getPriority()
/*     */   {
/* 112 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(Integer priority)
/*     */   {
/* 120 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public Address getParent()
/*     */   {
/* 128 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public void setParent(Address parent)
/*     */   {
/* 136 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 143 */     if (obj == null) return false;
/* 144 */     if (!(obj instanceof Address)) return false;
/*     */ 
/* 146 */     Address address = (Address)obj;
/* 147 */     if ((getId() == null) || (address.getId() == null)) return false;
/* 148 */     return getId().equals(address.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 154 */     if (-2147483648 == this.hashCode) {
/* 155 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 157 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 158 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 161 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 167 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseAddress
 * JD-Core Version:    0.6.0
 */