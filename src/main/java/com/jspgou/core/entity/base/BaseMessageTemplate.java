/*    */ package com.jspgou.core.entity.base;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public abstract class BaseMessageTemplate
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 22 */   public static String REF = "MessageTemplate";
/* 23 */   public static String PROP_SUBJECT = "subject";
/* 24 */   public static String PROP_TEXT = "text";
/*    */   private String subject;
/*    */   private String text;
/*    */   private String activeTitle;
/*    */   private String activeTxt;
/*    */ 
/*    */   public BaseMessageTemplate()
/*    */   {
/* 28 */     initialize();
/*    */   }
/*    */ 
/*    */   protected void initialize()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getSubject()
/*    */   {
/* 40 */     return this.subject;
/*    */   }
/*    */ 
/*    */   public void setSubject(String subject) {
/* 44 */     this.subject = subject;
/*    */   }
/*    */ 
/*    */   public String getText() {
/* 48 */     return this.text;
/*    */   }
/*    */ 
/*    */   public void setText(String text) {
/* 52 */     this.text = text;
/*    */   }
/*    */ 
/*    */   public String getActiveTitle() {
/* 56 */     return this.activeTitle;
/*    */   }
/*    */ 
/*    */   public void setActiveTitle(String activeTitle) {
/* 60 */     this.activeTitle = activeTitle;
/*    */   }
/*    */ 
/*    */   public String getActiveTxt() {
/* 64 */     return this.activeTxt;
/*    */   }
/*    */ 
/*    */   public void setActiveTxt(String activeTxt) {
/* 68 */     this.activeTxt = activeTxt;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 73 */     return super.toString();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.base.BaseMessageTemplate
 * JD-Core Version:    0.6.0
 */