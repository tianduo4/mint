/*    */ package com.jspgou.core.cache.impl;
/*    */ 
/*    */ import com.jspgou.core.cache.CoreCacheSvc;
/*    */ import net.sf.ehcache.Ehcache;
/*    */ import net.sf.ehcache.Element;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Qualifier;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ public class CoreCacheSvcImpl
/*    */   implements CoreCacheSvc
/*    */ {
/*    */   private static final String WEBSITE_COUNT = "core_website_count";
/*    */   private static final String WEBSITE_ID = "core_website_id";
/*    */   private Ehcache globalCache;
/*    */ 
/*    */   @Autowired
/*    */   public void setGlobalCache(@Qualifier("global") Ehcache globalCache)
/*    */   {
/* 24 */     this.globalCache = globalCache;
/*    */   }
/*    */ 
/*    */   public void putWebsiteCount(int paramInt)
/*    */   {
/* 29 */     this.globalCache.put(new Element("core_website_count", Integer.valueOf(paramInt)));
/*    */   }
/*    */ 
/*    */   public Integer getWebsiteCount()
/*    */   {
/* 34 */     Element element = this.globalCache.get("core_website_count");
/* 35 */     if (element != null) {
/* 36 */       return (Integer)element.getValue();
/*    */     }
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   public void putWebsiteId(Long paramLong)
/*    */   {
/* 44 */     this.globalCache.put(new Element("core_website_id", paramLong));
/*    */   }
/*    */ 
/*    */   public Long getWebsiteId()
/*    */   {
/* 49 */     Element element = this.globalCache.get("core_website_id");
/* 50 */     if (element != null) {
/* 51 */       return (Long)element.getValue();
/*    */     }
/* 53 */     return null;
/*    */   }
/*    */ 
/*    */   public boolean removeWebsiteId()
/*    */   {
/* 59 */     return this.globalCache.remove("core_website_id");
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.cache.impl.CoreCacheSvcImpl
 * JD-Core Version:    0.6.0
 */