/*    */ package com.jspgou.cms.web.threadvariable;
/*    */ 
/*    */ import org.apache.shiro.subject.Subject;
/*    */ 
/*    */ public class SubjectThread
/*    */ {
/*  9 */   private static ThreadLocal<Subject> instance = new ThreadLocal();
/*    */ 
/*    */   public static Subject get() {
/* 12 */     return (Subject)instance.get();
/*    */   }
/*    */ 
/*    */   public static void set(Subject subject) {
/* 16 */     instance.set(subject);
/*    */   }
/*    */ 
/*    */   public static void remove() {
/* 20 */     instance.remove();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.web.threadvariable.SubjectThread
 * JD-Core Version:    0.6.0
 */