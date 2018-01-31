/*     */ package com.jspgou.common.web.session;
/*     */ 
/*     */ import com.jspgou.common.web.session.cache.SessionCache;
/*     */ import com.jspgou.common.web.session.id.SessionIdGenerator;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.InitializingBean;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ public class CacheSessionProvider
/*     */   implements SessionProvider, InitializingBean
/*     */ {
/*     */   public static final String CURRENT_SESSION = "_current_session";
/*     */   public static final String CURRENT_SESSION_ID = "_current_session_id";
/*     */   private SessionCache sessionCache;
/*     */   private SessionIdGenerator sessionIdGenerator;
/*     */   private int sessionTimeout;
/*     */ 
/*     */   public CacheSessionProvider()
/*     */   {
/*  34 */     this.sessionTimeout = 30;
/*     */   }
/*     */ 
/*     */   public Serializable getAttribute(HttpServletRequest request, String name)
/*     */   {
/*  41 */     Map session = (Map)request
/*  42 */       .getAttribute("_current_session");
/*  43 */     if (session != null) {
/*  44 */       return (Serializable)session.get(name);
/*     */     }
/*  46 */     String root = (String)request.getAttribute("_current_session_id");
/*  47 */     if (root == null) {
/*  48 */       root = request.getRequestedSessionId();
/*     */     }
/*  50 */     if (StringUtils.isBlank(root)) {
/*  51 */       request.setAttribute("_current_session", 
/*  52 */         new HashMap());
/*  53 */       return null;
/*     */     }
/*  55 */     session = this.sessionCache.getSession(root);
/*  56 */     if (session != null) {
/*  57 */       request.setAttribute("_current_session_id", root);
/*  58 */       request.setAttribute("_current_session", session);
/*  59 */       return (Serializable)session.get(name);
/*     */     }
/*  61 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, Serializable value)
/*     */   {
/*  69 */     Map session = (Map)request
/*  70 */       .getAttribute("_current_session");
/*     */     String root;
/*  72 */     if (session == null) {
/*  73 */        root = request.getRequestedSessionId();
/*  74 */       if ((root != null) && (root.length() == 32)) {
/*  75 */         session = this.sessionCache.getSession(root);
/*     */       }
/*  77 */       if (session == null) {
/*  78 */         session = new HashMap();
/*     */         do
/*  80 */           root = this.sessionIdGenerator.get();
/*  81 */         while (this.sessionCache.exist(root));
/*  82 */         response.addCookie(createCookie(request, root));
/*     */       }
/*  84 */       request.setAttribute("_current_session", session);
/*  85 */       request.setAttribute("_current_session_id", root);
/*     */     } else {
/*  87 */       root = (String)request.getAttribute("_current_session_id");
/*  88 */       if (root == null) {
/*     */         do
/*  90 */           root = this.sessionIdGenerator.get();
/*  91 */         while (this.sessionCache.exist(root));
/*  92 */         response.addCookie(createCookie(request, root));
/*  93 */         request.setAttribute("_current_session_id", root);
/*     */       }
/*     */     }
/*  96 */     session.put(name, value);
/*  97 */     this.sessionCache.setSession(root, session, this.sessionTimeout);
/*     */   }
/*     */ 
/*     */   public String getSessionId(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 103 */     String root = (String)request.getAttribute("_current_session_id");
/* 104 */     if (root != null) {
/* 105 */       return root;
/*     */     }
/* 107 */     root = request.getRequestedSessionId();
/* 108 */     if ((root == null) || (root.length() != 32) || (!this.sessionCache.exist(root))) {
/*     */       do
/* 110 */         root = this.sessionIdGenerator.get();
/* 111 */       while (this.sessionCache.exist(root));
/* 112 */       this.sessionCache.setSession(root, new HashMap(), 
/* 113 */         this.sessionTimeout);
/* 114 */       response.addCookie(createCookie(request, root));
/*     */     }
/* 116 */     request.setAttribute("_current_session_id", root);
/* 117 */     return root;
/*     */   }
/*     */ 
/*     */   public void logout(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 122 */     request.removeAttribute("_current_session");
/* 123 */     request.removeAttribute("_current_session_id");
/* 124 */     String root = request.getRequestedSessionId();
/* 125 */     if (!StringUtils.isBlank(root)) {
/* 126 */       this.sessionCache.clear(root);
/* 127 */       Cookie cookie = createCookie(request, null);
/* 128 */       cookie.setMaxAge(0);
/* 129 */       response.addCookie(cookie);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Cookie createCookie(HttpServletRequest request, String value) {
/* 134 */     Cookie cookie = new Cookie("JSESSIONID", value);
/* 135 */     String ctx = request.getContextPath();
/* 136 */     cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
/* 137 */     return cookie;
/*     */   }
/*     */ 
/*     */   public void afterPropertiesSet() throws Exception
/*     */   {
/* 142 */     Assert.notNull(this.sessionCache);
/* 143 */     Assert.notNull(this.sessionIdGenerator);
/*     */   }
/*     */ 
/*     */   public void setSessionCache(SessionCache sessionCache) {
/* 147 */     this.sessionCache = sessionCache;
/*     */   }
/*     */ 
/*     */   public void setSessionTimeout(int sessionTimeout)
/*     */   {
/* 157 */     Assert.isTrue(sessionTimeout > 0);
/* 158 */     this.sessionTimeout = sessionTimeout;
/*     */   }
/*     */ 
/*     */   public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
/* 162 */     this.sessionIdGenerator = sessionIdGenerator;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.session.CacheSessionProvider
 * JD-Core Version:    0.6.0
 */