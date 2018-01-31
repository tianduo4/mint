/*    */ package com.jspgou.common.web.springmvc;
/*    */ 
/*    */ import java.beans.PropertyEditorSupport;
/*    */ import java.sql.Timestamp;
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import org.springframework.util.StringUtils;
/*    */ 
/*    */ public class DateTypeEditor extends PropertyEditorSupport
/*    */ {
/* 17 */   public static final DateFormat DF_LONG = new SimpleDateFormat(
/* 18 */     "yyyy-MM-dd HH:mm:ss");
/*    */ 
/* 19 */   public static final DateFormat DF_SHORT = new SimpleDateFormat("yyyy-MM-dd");
/*    */   public static final int SHORT_DATE = 10;
/*    */ 
/*    */   public void setAsText(String text)
/*    */     throws IllegalArgumentException
/*    */   {
/* 27 */     text = text.trim();
/* 28 */     if (!StringUtils.hasText(text)) {
/* 29 */       setValue(null);
/* 30 */       return;
/*    */     }
/*    */     try {
/* 33 */       if (text.length() <= 10)
/* 34 */         setValue(new java.sql.Date(DF_SHORT.parse(text).getTime()));
/*    */       else
/* 36 */         setValue(new Timestamp(DF_LONG.parse(text).getTime()));
/*    */     }
/*    */     catch (ParseException ex) {
/* 39 */       IllegalArgumentException iae = new IllegalArgumentException(
/* 40 */         "Could not parse date: " + ex.getMessage());
/* 41 */       iae.initCause(ex);
/* 42 */       throw iae;
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getAsText()
/*    */   {
/* 51 */     java.util.Date value = (java.util.Date)getValue();
/* 52 */     return value != null ? DF_LONG.format(value) : "";
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.springmvc.DateTypeEditor
 * JD-Core Version:    0.6.0
 */