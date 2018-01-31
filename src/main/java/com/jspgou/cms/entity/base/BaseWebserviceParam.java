/*    */ package com.jspgou.cms.entity.base;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public abstract class BaseWebserviceParam
/*    */   implements Serializable
/*    */ {
/* 17 */   public static String REF = "WebserviceParam";
/* 18 */   public static String PROP_PARAM_NAME = "paramName";
/* 19 */   public static String PROP_DEFAULT_VALUE = "defaultValue";
/*    */   private String paramName;
/*    */   private String defaultValue;
/*    */ 
/*    */   public BaseWebserviceParam()
/*    */   {
/* 24 */     initialize();
/*    */   }
/*    */ 
/*    */   public BaseWebserviceParam(String paramName)
/*    */   {
/* 33 */     setParamName(paramName);
/* 34 */     initialize();
/*    */   }
/*    */ 
/*    */   protected void initialize()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getParamName()
/*    */   {
/* 54 */     return this.paramName;
/*    */   }
/*    */ 
/*    */   public void setParamName(String paramName)
/*    */   {
/* 62 */     this.paramName = paramName;
/*    */   }
/*    */ 
/*    */   public String getDefaultValue()
/*    */   {
/* 70 */     return this.defaultValue;
/*    */   }
/*    */ 
/*    */   public void setDefaultValue(String defaultValue)
/*    */   {
/* 78 */     this.defaultValue = defaultValue;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 88 */     return super.toString();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseWebserviceParam
 * JD-Core Version:    0.6.0
 */