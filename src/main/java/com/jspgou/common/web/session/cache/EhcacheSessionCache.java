/*    */ package com.jspgou.common.web.session.cache;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.sf.ehcache.Ehcache;
/*    */ import net.sf.ehcache.Element;
/*    */ import org.springframework.beans.factory.InitializingBean;
/*    */ import org.springframework.util.Assert;
/*    */ 
/*    */ public class EhcacheSessionCache
/*    */   implements SessionCache, InitializingBean
/*    */ {
/*    */   private Ehcache cache;
/*    */ 
/*    */   public Map<String, Serializable> getSession(String root)
/*    */   {
/* 19 */     Element e = this.cache.get(root);
/* 20 */     return e != null ? (HashMap)e.getValue() : null;
/*    */   }
/*    */ 
/*    */   public void setSession(String root, Map<String, Serializable> session, int exp)
/*    */   {
/* 25 */     this.cache.put(new Element(root, session));
/*    */   }
/*    */ 
/*    */   public Serializable getAttribute(String root, String name)
/*    */   {
/* 30 */     Map session = getSession(root);
/* 31 */     return session != null ? (Serializable)session.get(name) : null;
/*    */   }
/*    */ 
/*    */   public void setAttribute(String root, String name, Serializable value, int exp)
/*    */   {
/* 37 */     Map session = getSession(root);
/* 38 */     if (session == null) {
/* 39 */       session = new HashMap();
/*    */     }
/* 41 */     session.put(name, value);
/* 42 */     this.cache.put(new Element(root, session));
/*    */   }
/*    */ 
/*    */   public void clear(String root)
/*    */   {
/* 47 */     this.cache.remove(root);
/*    */   }
/*    */ 
/*    */   public boolean exist(String root)
/*    */   {
/* 52 */     return this.cache.isKeyInCache(root);
/*    */   }
/*    */ 
/*    */   public void afterPropertiesSet() throws Exception
/*    */   {
/* 57 */     Assert.notNull(this.cache);
/*    */   }
/*    */ 
/*    */   public void setCache(Ehcache cache)
/*    */   {
/* 63 */     this.cache = cache;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.session.cache.EhcacheSessionCache
 * JD-Core Version:    0.6.0
 */