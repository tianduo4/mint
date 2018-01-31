/*     */ package com.jspgou.common.hibernate3;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ import net.sf.ehcache.CacheException;
/*     */ import net.sf.ehcache.CacheManager;
/*     */ import net.sf.ehcache.Ehcache;
/*     */ import net.sf.ehcache.ObjectExistsException;
/*     */ import net.sf.ehcache.config.Configuration;
/*     */ import net.sf.ehcache.config.ConfigurationFactory;
/*     */ import net.sf.ehcache.config.DiskStoreConfiguration;
/*     */ import net.sf.ehcache.hibernate.EhCache;
/*     */ import org.hibernate.cache.Cache;
/*     */ import org.hibernate.cache.CacheProvider;
/*     */ import org.hibernate.cache.Timestamper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.core.io.Resource;
/*     */ 
/*     */ public final class SpringEhCacheProvider
/*     */   implements CacheProvider
/*     */ {
/*  27 */   private static final Logger log = LoggerFactory.getLogger(SpringEhCacheProvider.class);
/*     */   private Resource configLocation;
/*     */   private Resource diskStoreLocation;
/*     */   private CacheManager manager;
/*     */ 
/*     */   public void setConfigLocation(Resource resource)
/*     */   {
/*  34 */     this.configLocation = resource;
/*     */   }
/*     */ 
/*     */   public void setDiskStoreLocation(Resource resource) {
/*  38 */     this.diskStoreLocation = resource;
/*     */   }
/*     */ 
/*     */   public final Cache buildCache(String name, Properties properties) throws CacheException
/*     */   {
/*     */     try {
/*  44 */       Ehcache cache = this.manager.getEhcache(name);
/*  45 */       if (cache == null)
/*     */       {
/*  47 */         String s = "Could not find a specific ehcache configuration for cache named [{}]; using defaults.";
/*  48 */         log.warn(s, name);
/*  49 */         this.manager.addCache(name);
/*  50 */         cache = this.manager.getEhcache(name);
/*  51 */         log.debug("started EHCache region: " + name);
/*     */       }
/*  53 */       return new EhCache(cache); } catch (CacheException e) {
/*     */     }
/*  55 */     throw new CacheException(e);
/*     */   }
/*     */ 
/*     */   public final long nextTimestamp()
/*     */   {
/*  63 */     return Timestamper.next();
/*     */   }
/*     */ 
/*     */   public final void start(Properties properties)
/*     */     throws CacheException
/*     */   {
/*  75 */     if (this.manager != null) {
/*  76 */       String s = "Attempt to restart an already started EhCacheProvider. Use sessionFactory.close()  between repeated calls to buildSessionFactory. Using previously created EhCacheProvider. If this behaviour is required, consider using SingletonEhCacheProvider.";
/*     */ 
/*  79 */       log.warn(s);
/*  80 */       return;
/*     */     }
/*  82 */     Configuration config = null;
/*     */     try {
/*  84 */       if (this.configLocation != null) {
/*  85 */         config = ConfigurationFactory.parseConfiguration(this.configLocation
/*  86 */           .getInputStream());
/*  87 */         if (this.diskStoreLocation != null) {
/*  88 */           DiskStoreConfiguration diskstoreconfiguration = new DiskStoreConfiguration();
/*  89 */           diskstoreconfiguration.setPath(this.diskStoreLocation.getFile().getAbsolutePath());
/*     */           try {
/*  91 */             config.addDiskStore(diskstoreconfiguration);
/*     */           } catch (ObjectExistsException e) {
/*  93 */             String s = "if you want to config distStore in spring, please remove diskStore in config file!";
/*     */ 
/*  95 */             log.warn(s, e);
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (IOException e) {
/* 100 */       log.warn("create ehcache config failed!", e);
/*     */     }
/* 102 */     if (config != null)
/* 103 */       this.manager = new CacheManager(config);
/*     */     else
/* 105 */       this.manager = new CacheManager();
/*     */   }
/*     */ 
/*     */   public final void stop()
/*     */   {
/* 113 */     if (this.manager != null) {
/* 114 */       this.manager.shutdown();
/* 115 */       this.manager = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final boolean isMinimalPutsEnabledByDefault()
/*     */   {
/* 125 */     return false;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.hibernate3.SpringEhCacheProvider
 * JD-Core Version:    0.6.0
 */