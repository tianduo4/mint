/*     */ package com.jspgou.core.security;
/*     */ 
/*     */ import org.apache.shiro.UnavailableSecurityManagerException;
/*     */ import org.apache.shiro.mgt.SecurityManager;
/*     */ import org.apache.shiro.subject.Subject;
/*     */ import org.apache.shiro.subject.Subject.Builder;
/*     */ import org.apache.shiro.util.ThreadContext;
/*     */ 
/*     */ public abstract class SecurityUtils
/*     */ {
/*     */   private static SecurityManager securityManager;
/*     */ 
/*     */   public static Subject getSubject()
/*     */   {
/*  56 */     Subject subject = ThreadContext.getSubject();
/*  57 */     if (subject == null) {
/*  58 */       subject = new Subject.Builder().buildSubject();
/*  59 */       ThreadContext.bind(subject);
/*     */     }
/*  61 */     return subject;
/*     */   }
/*     */ 
/*     */   public static void setSecurityManager(SecurityManager securityManager)
/*     */   {
/*  97 */     securityManager = securityManager;
/*     */   }
/*     */ 
/*     */   public static SecurityManager getSecurityManager()
/*     */     throws UnavailableSecurityManagerException
/*     */   {
/* 117 */     SecurityManager securityManager = ThreadContext.getSecurityManager();
/* 118 */     if (securityManager == null) {
/* 119 */       securityManager = securityManager;
/*     */     }
/* 121 */     if (securityManager == null) {
/* 122 */       String msg = "No SecurityManager accessible to the calling code, either bound to the " + 
/* 123 */         ThreadContext.class.getName() + " or as a vm static singleton.  This is an invalid application " + 
/* 124 */         "configuration.";
/* 125 */       throw new UnavailableSecurityManagerException(msg);
/*     */     }
/* 127 */     return securityManager;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.security.SecurityUtils
 * JD-Core Version:    0.6.0
 */