/*     */ package com.jspgou.core.manager.impl;
/*     */ 
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.core.cache.CoreCacheSvc;
/*     */ import com.jspgou.core.cache.DomainCacheSvc;
/*     */ import com.jspgou.core.dao.WebsiteDao;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.FtpMng;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class WebsiteMngImpl
/*     */   implements WebsiteMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private FtpMng ftpMng;
/*     */   private CoreCacheSvc coreCacheSvc;
/*     */   private DomainCacheSvc domainCacheSvc;
/*     */   private WebsiteDao dao;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Website getWebsite(String s)
/*     */   {
/*  31 */     Integer count = this.coreCacheSvc.getWebsiteCount();
/*     */     int i;
/*  33 */     if (count == null) {
/*  34 */       int i = this.dao.countWebsite();
/*  35 */       this.coreCacheSvc.putWebsiteCount(new Integer(i).intValue());
/*     */     } else {
/*  37 */       i = count.intValue();
/*     */     }
/*     */ 
/*  41 */     if (i == 1) {
/*  42 */       Long id = this.coreCacheSvc.getWebsiteId();
/*     */       Website website;
/*  43 */       if (id != null) {
/*  44 */         website = findById(id);
/*     */       } else {
/*  46 */         Website website = this.dao.getUniqueWebsite();
/*  47 */         this.coreCacheSvc.putWebsiteId(website.getId());
/*     */       }
/*  49 */     } else if (i > 1) {
/*  50 */       Long id = this.domainCacheSvc.get(s);
/*     */       Website website;
/*  51 */       if (id != null) {
/*  52 */         website = findById(id);
/*     */       } else {
/*  54 */         Website website = this.dao.findByDomain(s);
/*  55 */         if (website != null)
/*  56 */           this.domainCacheSvc.put(website.getDomain(), website.getDomainAliasArray(), website.getId());
/*     */       }
/*     */     }
/*     */     else {
/*  60 */       throw new IllegalStateException("no website data in database, please init database!");
/*     */     }
/*     */     Website website;
/*     */     Long id;
/*  62 */     return website;
/*     */   }
/*     */ 
/*     */   public Pagination getAllPage(int pageNo, int pageSize)
/*     */   {
/*  67 */     return this.dao.getAllPage(pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public List<Website> getAllList()
/*     */   {
/*  72 */     return this.dao.getAllList();
/*     */   }
/*     */ 
/*     */   public Website findById(Long id)
/*     */   {
/*  77 */     return this.dao.findById(id);
/*     */   }
/*     */   @Transactional(readOnly=true)
/*     */   public Website findById1(Long id) {
/*  82 */     Website entity = this.dao.findById1(id);
/*  83 */     return entity;
/*     */   }
/*     */ 
/*     */   public Website save(Website bean)
/*     */   {
/*  88 */     Website entity = this.dao.save(bean);
/*  89 */     fireOnSave(entity);
/*  90 */     return entity;
/*     */   }
/*     */ 
/*     */   public Website update1(Website bean, Integer uploadFtpId, Integer syncPageFtpId) {
/*  94 */     Website entity = findById1(bean.getId());
/*  95 */     if (uploadFtpId != null)
/*  96 */       entity.setUploadFtp(this.ftpMng.findById(uploadFtpId));
/*     */     else {
/*  98 */       entity.setUploadFtp(null);
/*     */     }
/* 100 */     if (syncPageFtpId != null)
/* 101 */       entity.setSyncPageFtp(this.ftpMng.findById(syncPageFtpId));
/*     */     else {
/* 103 */       entity.setSyncPageFtp(null);
/*     */     }
/* 105 */     String domain = entity.getDomain();
/* 106 */     String[] as = entity.getDomainAliasArray();
/* 107 */     entity = this.dao.updateByUpdater(new Updater(bean));
/* 108 */     fireOnUpdate(domain, as, entity);
/* 109 */     return entity;
/*     */   }
/*     */ 
/*     */   public Website update(Website bean)
/*     */   {
/* 114 */     Website entity = findById(bean.getId());
/* 115 */     String domain = entity.getDomain();
/* 116 */     String[] as = entity.getDomainAliasArray();
/* 117 */     entity = this.dao.updateByUpdater(new Updater(bean));
/* 118 */     fireOnUpdate(domain, as, entity);
/* 119 */     return entity;
/*     */   }
/*     */ 
/*     */   public Website deleteById(Long id)
/*     */   {
/* 124 */     Website entity = this.dao.deleteById(id);
/* 125 */     fireOnDelete(entity);
/* 126 */     return entity;
/*     */   }
/*     */ 
/*     */   public Website[] deleteByIds(Long[] ids)
/*     */   {
/* 131 */     Website[] beans = new Website[ids.length];
/* 132 */     for (int i = 0; i < ids.length; i++) {
/* 133 */       beans[i] = this.dao.deleteById(ids[i]);
/*     */     }
/* 135 */     fireOnDeleteBatch(beans);
/* 136 */     return beans;
/*     */   }
/*     */ 
/*     */   public void updateAttr(Long siteId, Map<String, String> attr)
/*     */   {
/* 142 */     Website site = findById(siteId);
/* 143 */     site.getAttr().putAll(attr);
/*     */   }
/*     */ 
/*     */   private void resetWebsiteCache()
/*     */   {
/* 148 */     List list = this.dao.getAllList();
/* 149 */     int i = list.size();
/* 150 */     if (i == 0) {
/* 151 */       throw new IllegalStateException("no website data in database, please init database!");
/*     */     }
/* 153 */     this.coreCacheSvc.putWebsiteCount(i);
/*     */ 
/* 155 */     if (i == 1) {
/* 156 */       Website entity = (Website)list.get(0);
/* 157 */       this.coreCacheSvc.putWebsiteId(entity.getId());
/* 158 */       this.domainCacheSvc.removeAll();
/* 159 */       this.domainCacheSvc.put(entity.getDomain(), entity.getDomainAliasArray(), entity.getId());
/*     */     } else {
/* 161 */       this.coreCacheSvc.removeWebsiteId();
/* 162 */       this.domainCacheSvc.removeAll();
/* 163 */       Object object = list.iterator();
/* 164 */       while (((Iterator)object).hasNext()) {
/* 165 */         Website localWebsite = (Website)((Iterator)object).next();
/* 166 */         this.domainCacheSvc.put(localWebsite.getDomain(), localWebsite.getDomainAliasArray(), localWebsite.getId());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void onDomainUpdated(String s, String[] as, Website website) {
/* 172 */     String domain = website.getDomain();
/* 173 */     String[] as1 = website.getDomainAliasArray();
/* 174 */     if ((!domain.equals(s)) || (!Arrays.equals(as1, as))) {
/* 175 */       this.domainCacheSvc.remove(s, as);
/* 176 */       this.domainCacheSvc.put(domain, as1, website.getId());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void onDomainDelete(Website bean) {
/* 181 */     resetWebsiteCache();
/*     */   }
/*     */ 
/*     */   private void onDomainDeleteBatch(Website[] beans) {
/* 185 */     resetWebsiteCache();
/*     */   }
/*     */ 
/*     */   private void onDomainSave(Website bean) {
/* 189 */     resetWebsiteCache();
/*     */   }
/*     */ 
/*     */   private void fireOnUpdate(String s, String[] as, Website bean) {
/* 193 */     onDomainUpdated(s, as, bean);
/*     */   }
/*     */ 
/*     */   private void fireOnDelete(Website bean) {
/* 197 */     onDomainDelete(bean);
/*     */   }
/*     */ 
/*     */   private void fireOnDeleteBatch(Website[] beans) {
/* 201 */     onDomainDeleteBatch(beans);
/*     */   }
/*     */ 
/*     */   private void fireOnSave(Website bean) {
/* 205 */     onDomainSave(bean);
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setCoreCacheSvc(CoreCacheSvc coreCacheSvc)
/*     */   {
/* 216 */     this.coreCacheSvc = coreCacheSvc;
/*     */   }
/* 220 */   @Autowired
/*     */   public void setDomainCacheSvc(DomainCacheSvc domainCacheSvc) { this.domainCacheSvc = domainCacheSvc; }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Website get()
/*     */   {
/* 226 */     Website entity = this.dao.findById(Long.valueOf(1L));
/* 227 */     return entity;
/*     */   }
/*     */ 
/*     */   public void updateSsoAttr(Map<String, String> ssoAttr)
/*     */   {
/* 232 */     Map oldAttr = get().getAttr();
/* 233 */     Iterator keys = oldAttr.keySet().iterator();
/* 234 */     String key = null;
/* 235 */     while (keys.hasNext()) {
/* 236 */       key = (String)keys.next();
/* 237 */       if (key.startsWith("sso_")) {
/* 238 */         keys.remove();
/*     */       }
/*     */     }
/* 241 */     oldAttr.putAll(ssoAttr);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Website findByDomain(String domain, boolean cacheable) {
/* 247 */     return this.dao.findByDomain(domain, cacheable);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Website> getListFromCache() {
/* 253 */     return this.dao.getList(true);
/*     */   }
/*     */   @Autowired
/*     */   public void setDao(WebsiteDao dao) {
/* 258 */     this.dao = dao;
/*     */   }
/*     */ 
/*     */   public void updateTplSolution(Long siteId, String solution, boolean mobile)
/*     */   {
/* 263 */     Website site = findById(siteId);
/* 264 */     if (mobile)
/* 265 */       site.setTplMobileSolution(solution);
/*     */     else
/* 267 */       site.setTplSolution(solution);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.impl.WebsiteMngImpl
 * JD-Core Version:    0.6.0
 */