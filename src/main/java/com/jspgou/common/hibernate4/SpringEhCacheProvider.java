/*     */ package com.jspgou.common.hibernate4;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ import net.sf.ehcache.CacheManager;
/*     */ import net.sf.ehcache.ObjectExistsException;
/*     */ import net.sf.ehcache.config.Configuration;
/*     */ import net.sf.ehcache.config.ConfigurationFactory;
/*     */ import net.sf.ehcache.config.DiskStoreConfiguration;
/*     */ import org.hibernate.cache.CacheException;
/*     */ import org.hibernate.cache.ehcache.EhCacheRegionFactory;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.core.io.Resource;
/*     */ 
/*     */ public final class SpringEhCacheProvider extends EhCacheRegionFactory
/*     */ {
/*     */   private static final long serialVersionUID = -8476537268197646948L;
/*  28 */   private static final Logger log = LoggerFactory.getLogger(SpringEhCacheProvider.class);
/*     */   private Resource configLocation;
/*     */   private Resource diskStoreLocation;
/*     */   private CacheManager manager;
/*     */ 
/*     */   public void setConfigLocation(Resource resource)
/*     */   {
/*  35 */     this.configLocation = resource;
/*     */   }
/*     */ 
/*     */   public void setDiskStoreLocation(Resource resource) {
/*  39 */     this.diskStoreLocation = resource;
/*     */   }
/*     */ 
/*     */   public final void start(Properties properties)
/*     */     throws CacheException
/*     */   {
/*  51 */     if (this.manager != null) {
/*  52 */       String s = "Attempt to restart an already started EhCacheProvider. Use sessionFactory.close()  between repeated calls to buildSessionFactory. Using previously created EhCacheProvider. If this behaviour is required, consider using SingletonEhCacheProvider.";
/*     */ 
/*  55 */       log.warn(s);
/*  56 */       return;
/*     */     }
/*  58 */     Configuration config = null;
/*     */     try {
/*  60 */       if (this.configLocation != null) {
/*  61 */         config = ConfigurationFactory.parseConfiguration(this.configLocation
/*  62 */           .getInputStream());
/*  63 */         if (this.diskStoreLocation != null) {
/*  64 */           DiskStoreConfiguration dc = new DiskStoreConfiguration();
/*  65 */           dc.setPath(this.diskStoreLocation.getFile()
/*  66 */             .getAbsolutePath());
/*     */           try {
/*  68 */             config.addDiskStore(dc);
/*     */           } catch (ObjectExistsException e) {
/*  70 */             String s = "if you want to config distStore in spring, please remove diskStore in config file!";
/*     */ 
/*  72 */             log.warn(s, e);
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (IOException e) {
/*  77 */       log.warn("create ehcache config failed!", e);
/*     */     }
/*  79 */     if (config != null)
/*  80 */       this.manager = new CacheManager(config);
/*     */     else
/*  82 */       this.manager = new CacheManager();
/*     */   }
/*     */ 
/*     */   public final void stop()
/*     */   {
/*  91 */     if (this.manager != null) {
/*  92 */       this.manager.shutdown();
/*  93 */       this.manager = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final boolean isMinimalPutsEnabledByDefault()
/*     */   {
/* 103 */     return false;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.hibernate4.SpringEhCacheProvider
 * JD-Core Version:    0.6.0
 */