/*     */ package com.jspgou.common.web;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import net.sf.ehcache.CacheException;
/*     */ import net.sf.ehcache.CacheManager;
/*     */ import net.sf.ehcache.ObjectExistsException;
/*     */ import net.sf.ehcache.config.Configuration;
/*     */ import net.sf.ehcache.config.ConfigurationFactory;
/*     */ import net.sf.ehcache.config.DiskStoreConfiguration;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.DisposableBean;
/*     */ import org.springframework.beans.factory.FactoryBean;
/*     */ import org.springframework.beans.factory.InitializingBean;
/*     */ import org.springframework.core.io.Resource;
/*     */ 
/*     */ public class WebEhCacheManagerFacotryBean
/*     */   implements FactoryBean<CacheManager>, InitializingBean, DisposableBean
/*     */ {
/*  27 */   private final Logger log = LoggerFactory.getLogger(WebEhCacheManagerFacotryBean.class);
/*     */   private Resource configLocation;
/*     */   private Resource diskStoreLocation;
/*     */   private String cacheManagerName;
/*     */   private CacheManager cacheManager;
/*     */ 
/*     */   public void setConfigLocation(Resource configLocation)
/*     */   {
/*  48 */     this.configLocation = configLocation;
/*     */   }
/*     */ 
/*     */   public void setdiskStoreLocation(Resource diskStoreLocation)
/*     */   {
/*  59 */     this.diskStoreLocation = diskStoreLocation;
/*     */   }
/*     */ 
/*     */   public void setCacheManagerName(String cacheManagerName)
/*     */   {
/*  68 */     this.cacheManagerName = cacheManagerName;
/*     */   }
/*     */ 
/*     */   public void afterPropertiesSet() throws IOException, CacheException {
/*  72 */     this.log.info("Initializing EHCache CacheManager");
/*  73 */     Configuration config = null;
/*  74 */     if (this.configLocation != null) {
/*  75 */       config = 
/*  76 */         ConfigurationFactory.parseConfiguration(this.configLocation.getInputStream());
/*  77 */       if (this.diskStoreLocation != null) {
/*  78 */         DiskStoreConfiguration dc = new DiskStoreConfiguration();
/*  79 */         dc.setPath(this.diskStoreLocation.getFile().getAbsolutePath());
/*     */         try {
/*  81 */           config.addDiskStore(dc);
/*     */ 
/*  83 */           this.cacheManager = CacheManager.create(config);
/*     */         } catch (ObjectExistsException e) {
/*  85 */           this.log.warn("if you want to config distStore in spring, please remove diskStore in config file!", 
/*  86 */             e);
/*     */         }
/*     */       } else {
/*  89 */         this.cacheManager = CacheManager.create(config);
/*     */       }
/*     */     }
/*  92 */     if (this.cacheManagerName != null)
/*  93 */       this.cacheManager.setName(this.cacheManagerName);
/*     */   }
/*     */ 
/*     */   public CacheManager getObject()
/*     */   {
/*  99 */     return this.cacheManager;
/*     */   }
/*     */ 
/*     */   public Class<? extends CacheManager> getObjectType() {
/* 103 */     return this.cacheManager != null ? this.cacheManager.getClass() : 
/* 104 */       CacheManager.class;
/*     */   }
/*     */ 
/*     */   public boolean isSingleton() {
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   public void destroy() {
/* 112 */     this.log.info("Shutting down EHCache CacheManager");
/* 113 */     this.cacheManager.shutdown();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.WebEhCacheManagerFacotryBean
 * JD-Core Version:    0.6.0
 */