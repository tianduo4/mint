/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.AdvertiseDao;
/*     */ import com.jspgou.cms.entity.Advertise;
/*     */ import com.jspgou.cms.manager.AdspaceMng;
/*     */ import com.jspgou.cms.manager.AdvertiseMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class AdvertiseMngImpl
/*     */   implements AdvertiseMng
/*     */ {
/*     */   private AdspaceMng adspaceMng;
/*     */   private AdvertiseDao dao;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(Integer adspaceId, Boolean enabled, int pageNo, int pageSize)
/*     */   {
/*  27 */     Pagination page = this.dao.getPage(adspaceId, enabled, pageNo, 
/*  28 */       pageSize);
/*  29 */     return page;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Advertise> getList(Integer adspaceId, Boolean enabled) {
/*  35 */     return this.dao.getList(adspaceId, enabled);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Advertise findById(Integer id) {
/*  41 */     Advertise entity = this.dao.findById(id);
/*  42 */     return entity;
/*     */   }
/*     */ 
/*     */   public Advertise save(Advertise bean, Integer adspaceId, Map<String, String> attr)
/*     */   {
/*  48 */     bean.setAdspace(this.adspaceMng.findById(adspaceId));
/*  49 */     bean.setAttr(attr);
/*  50 */     bean.init();
/*  51 */     this.dao.save(bean);
/*  52 */     return bean;
/*     */   }
/*     */ 
/*     */   public Advertise update(Advertise bean, Integer adspaceId, Map<String, String> attr)
/*     */   {
/*  58 */     Updater updater = new Updater(bean);
/*  59 */     bean = this.dao.updateByUpdater(updater);
/*  60 */     bean.setAdspace(this.adspaceMng.findById(adspaceId));
/*  61 */     bean.getAttr().clear();
/*  62 */     bean.getAttr().putAll(attr);
/*  63 */     return bean;
/*     */   }
/*     */ 
/*     */   public Advertise deleteById(Integer id)
/*     */   {
/*  68 */     Advertise bean = this.dao.deleteById(id);
/*  69 */     return bean;
/*     */   }
/*     */ 
/*     */   public Advertise[] deleteByIds(Integer[] ids)
/*     */   {
/*  74 */     Advertise[] beans = new Advertise[ids.length];
/*  75 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  76 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  78 */     return beans;
/*     */   }
/*     */ 
/*     */   public void display(Integer id)
/*     */   {
/*  83 */     Advertise ad = findById(id);
/*  84 */     if (ad != null)
/*  85 */       ad.setDisplayCount(Integer.valueOf(ad.getDisplayCount().intValue() + 1));
/*     */   }
/*     */ 
/*     */   public void click(Integer id)
/*     */   {
/*  91 */     Advertise ad = findById(id);
/*  92 */     if (ad != null)
/*  93 */       ad.setClickCount(Integer.valueOf(ad.getClickCount().intValue() + 1));
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setAdspaceMng(AdspaceMng adspaceMng)
/*     */   {
/* 105 */     this.adspaceMng = adspaceMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setDao(AdvertiseDao dao) {
/* 110 */     this.dao = dao;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.AdvertiseMngImpl
 * JD-Core Version:    0.6.0
 */