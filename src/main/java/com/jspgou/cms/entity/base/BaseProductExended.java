/*    */ package com.jspgou.cms.entity.base;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public abstract class BaseProductExended
/*    */   implements Serializable
/*    */ {
/* 19 */   public static String REF = "ProductExended";
/* 20 */   public static String PROP_NAME = "name";
/* 21 */   public static String PROP_VALUE = "value";
/*    */   private String name;
/*    */   private String value;
/*    */ 
/*    */   public BaseProductExended()
/*    */   {
/* 26 */     initialize();
/*    */   }
/*    */ 
/*    */   public BaseProductExended(String name)
/*    */   {
/* 35 */     setName(name);
/* 36 */     initialize();
/*    */   }
/*    */ 
/*    */   protected void initialize()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 56 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 64 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 72 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value)
/*    */   {
/* 80 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 90 */     return super.toString();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseProductExended
 * JD-Core Version:    0.6.0
 */