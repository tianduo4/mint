/*    */ package com.jspgou.core.cache.impl;
/*    */ 
/*    */ import com.jspgou.core.cache.DomainCacheSvc;
/*    */ import net.sf.ehcache.Ehcache;
/*    */ import net.sf.ehcache.Element;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Qualifier;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ public class DomainCacheSvcImpl
/*    */   implements DomainCacheSvc
/*    */ {
/*    */   private Ehcache domainCache;
/*    */ 
/*    */   @Autowired
/*    */   public void setDomainCache(@Qualifier("domain") Ehcache domainCache)
/*    */   {
/* 22 */     this.domainCache = domainCache;
/*    */   }
/*    */ 
/*    */   public void put(String paramString, String[] paramArrayOfString, Long paramLong)
/*    */   {
/* 27 */     this.domainCache.put(new Element(paramString, paramLong));
/* 28 */     if (paramArrayOfString != null)
/* 29 */       for (String s1 : paramArrayOfString)
/* 30 */         this.domainCache.put(new Element(s1, paramLong));
/*    */   }
/*    */ 
/*    */   public boolean remove(String paramString, String[] as)
/*    */   {
/* 37 */     if (as != null) {
/* 38 */       for (String s1 : as) {
/* 39 */         this.domainCache.remove(s1);
/*    */       }
/*    */     }
/* 42 */     return this.domainCache.remove(paramString);
/*    */   }
/*    */ 
/*    */   public Long get(String paramString)
/*    */   {
/* 47 */     Element element = this.domainCache.get(paramString);
/* 48 */     if (element != null) {
/* 49 */       return (Long)element.getValue();
/*    */     }
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   public void removeAll()
/*    */   {
/* 57 */     this.domainCache.removeAll();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.cache.impl.DomainCacheSvcImpl
 * JD-Core Version:    0.6.0
 */