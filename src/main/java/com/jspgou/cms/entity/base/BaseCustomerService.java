/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.CustomerService;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class BaseCustomerService
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "CustomerService";
/*  18 */   public static String PROP_NAME = "name";
/*  19 */   public static String PROP_DISABLE = "disable";
/*  20 */   public static String PROP_TYPE = "type";
/*  21 */   public static String PROP_ID = "id";
/*  22 */   public static String PROP_CONTENT = "content";
/*  23 */   public static String PROP_PRIORITY = "priority";
/*     */ 
/*  63 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String name;
/*     */   private String type;
/*     */   private String content;
/*     */   private Integer priority;
/*     */   private Boolean disable;
/*     */ 
/*     */   public BaseCustomerService()
/*     */   {
/*  28 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCustomerService(Long id)
/*     */   {
/*  35 */     setId(id);
/*  36 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseCustomerService(Long id, String name, String type, String content, Integer priority, Boolean disable)
/*     */   {
/*  50 */     setId(id);
/*  51 */     setName(name);
/*  52 */     setType(type);
/*  53 */     setContent(content);
/*  54 */     setPriority(priority);
/*  55 */     setDisable(disable);
/*  56 */     initialize();
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
/*     */   public String getType()
/*     */   {
/* 119 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/* 127 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 135 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content)
/*     */   {
/* 143 */     this.content = content;
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
/*     */   public Boolean getDisable()
/*     */   {
/* 167 */     return this.disable;
/*     */   }
/*     */ 
/*     */   public void setDisable(Boolean disable)
/*     */   {
/* 175 */     this.disable = disable;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 182 */     if (obj == null) return false;
/* 183 */     if (!(obj instanceof CustomerService)) return false;
/*     */ 
/* 185 */     CustomerService customerService = (CustomerService)obj;
/* 186 */     if ((getId() == null) || (customerService.getId() == null)) return false;
/* 187 */     return getId().equals(customerService.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 193 */     if (-2147483648 == this.hashCode) {
/* 194 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 196 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 197 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 200 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 206 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseCustomerService
 * JD-Core Version:    0.6.0
 */