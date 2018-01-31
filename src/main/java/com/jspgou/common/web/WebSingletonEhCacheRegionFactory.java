/*    */ package com.jspgou.common.web;
/*    */ 
/*    */ import java.util.Properties;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import net.sf.ehcache.CacheManager;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;
/*    */ import org.hibernate.cache.ehcache.management.impl.ProviderMBeanRegistrationHelper;
/*    */ import org.hibernate.cfg.Settings;
/*    */ 
/*    */ public class WebSingletonEhCacheRegionFactory extends SingletonEhCacheRegionFactory
/*    */ {
/*    */   private static final long serialVersionUID = 7227034033610133673L;
/* 60 */   protected final Log logger = LogFactory.getLog(getClass());
/*    */ 
/* 62 */   private static final AtomicInteger REFERENCE_COUNT = new AtomicInteger();
/*    */ 
/*    */   public WebSingletonEhCacheRegionFactory()
/*    */   {
/*    */   }
/*    */ 
/*    */   public WebSingletonEhCacheRegionFactory(Properties prop)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void start(Settings settings, Properties properties)
/*    */     throws org.hibernate.cache.CacheException
/*    */   {
/* 81 */     this.settings = settings;
/*    */     try {
/* 83 */       this.manager = CacheManager.getInstance();
/* 84 */       this.mbeanRegistrationHelper.registerMBean(this.manager, properties);
/*    */     }
/*    */     catch (net.sf.ehcache.CacheException e) {
/* 87 */       throw new org.hibernate.cache.CacheException(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void stop()
/*    */   {
/*    */     try
/*    */     {
/* 95 */       if (this.manager != null) {
/* 96 */         if (REFERENCE_COUNT.decrementAndGet() == 0) {
/* 97 */           this.manager.shutdown();
/*    */         }
/* 99 */         this.manager = null;
/*    */       }
/*    */     }
/*    */     catch (net.sf.ehcache.CacheException e) {
/* 103 */       throw new org.hibernate.cache.CacheException(e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.WebSingletonEhCacheRegionFactory
 * JD-Core Version:    0.6.0
 */