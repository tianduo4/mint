/*    */ package com.jspgou.cms.service.impl;
/*    */ 
/*    */ import com.jspgou.cms.service.WeixinSvc;
/*    */ import com.jspgou.cms.service.WeixinTokenCache;
/*    */ import net.sf.ehcache.Ehcache;
/*    */ import net.sf.ehcache.Element;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Qualifier;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ public class WeixinTokenCacheImpl
/*    */   implements WeixinTokenCache
/*    */ {
/* 56 */   private int interval = 3600000;
/*    */ 
/* 58 */   private long refreshTime = System.currentTimeMillis();
/*    */ 
/*    */   @Autowired
/*    */   private WeixinSvc weixinSvc;
/*    */   private Ehcache cache;
/*    */ 
/*    */   public String getToken()
/*    */   {
/* 25 */     String token = "";
/* 26 */     Element e = this.cache.get("token");
/* 27 */     if (e != null) {
/* 28 */       token = (String)e.getValue();
/* 29 */       String tokenFresh = refreshCache();
/* 30 */       if (StringUtils.isNotBlank(tokenFresh))
/* 31 */         token = tokenFresh;
/*    */     }
/*    */     else {
/* 34 */       token = this.weixinSvc.getToken();
/* 35 */       this.cache.put(new Element("token", token));
/*    */     }
/* 37 */     return token;
/*    */   }
/*    */ 
/*    */   private String refreshCache()
/*    */   {
/* 42 */     long time = System.currentTimeMillis();
/* 43 */     String token = "";
/* 44 */     if (time > this.refreshTime + this.interval) {
/* 45 */       this.refreshTime = time;
/*    */ 
/* 47 */       token = this.weixinSvc.getToken();
/* 48 */       this.cache.put(new Element("token", token));
/*    */     }
/* 50 */     return token;
/*    */   }
/*    */ 
/*    */   public void setInterval(int interval)
/*    */   {
/* 73 */     this.interval = (interval * 60 * 1000);
/*    */   }
/*    */   @Autowired
/*    */   public void setCache(@Qualifier("tokenCache") Ehcache cache) {
/* 78 */     this.cache = cache;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.service.impl.WeixinTokenCacheImpl
 * JD-Core Version:    0.6.0
 */