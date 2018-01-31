/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.CouponDao;
/*     */ import com.jspgou.cms.entity.Coupon;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.cms.manager.CouponMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.springmvc.RealPathResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import java.io.File;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class CouponMngImpl
/*     */   implements CouponMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private RealPathResolver realPathResolver;
/*     */ 
/*     */   @Autowired
/*     */   private CategoryMng categoryMng;
/*     */ 
/*     */   @Autowired
/*     */   private CouponDao dao;
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteMng siteMng;
/*     */ 
/*     */   public Pagination getPage(int pageNo, int pageSize, Integer categoryId)
/*     */   {
/*  31 */     return this.dao.getPage(pageNo, pageSize, categoryId);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPageByUsing(int pageNo, int pageSize) {
/*  37 */     return this.dao.getPageByUsing(new Date(), pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<Coupon> getList()
/*     */   {
/*  44 */     List list = this.dao.getList();
/*  45 */     return list;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Coupon findById(Long id)
/*     */   {
/*  52 */     Coupon entity = this.dao.findById(id);
/*  53 */     return entity;
/*     */   }
/*     */ 
/*     */   public Coupon save(Coupon bean, Long webId)
/*     */   {
/*  58 */     Website site = this.siteMng.findById(webId);
/*  59 */     bean.setWebsite(site);
/*  60 */     String couponname = null;
/*  61 */     String comments = null;
/*     */     try {
/*  63 */       couponname = new String(bean.getCouponName().getBytes("ISO-8859-1"), "UTF-8");
/*  64 */       comments = new String(bean.getComments().getBytes("ISO-8859-1"), "UTF-8");
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/*  67 */       e.printStackTrace();
/*     */     }
/*  69 */     bean.setCouponName(couponname);
/*  70 */     bean.setComments(comments);
/*  71 */     Coupon entity = this.dao.save(bean);
/*  72 */     return entity;
/*     */   }
/*     */ 
/*     */   public Coupon save(Coupon bean, Website site, Integer categoryId)
/*     */   {
/*  77 */     bean.setWebsite(site);
/*  78 */     if ((categoryId != null) && (categoryId.intValue() != 0)) {
/*  79 */       bean.setCategory(this.categoryMng.findById(categoryId));
/*     */     }
/*  81 */     Coupon entity = this.dao.save(bean);
/*  82 */     return entity;
/*     */   }
/*     */ 
/*     */   public Coupon update(Coupon bean)
/*     */   {
/*  87 */     Updater updater = new Updater(bean);
/*  88 */     Coupon entity = this.dao.updateByUpdater(updater);
/*     */ 
/*  90 */     return entity;
/*     */   }
/*     */ 
/*     */   public Coupon deleteById(Long id, String url)
/*     */   {
/*  95 */     Coupon entity = findById(id);
/*  96 */     String path = entity.getCouponPicture();
/*  97 */     String path1 = this.realPathResolver.get(path).replace("\\", File.separator).replace("/", File.separator).replace(url.replace("\\", File.separator).replace("/", File.separator) + url.replace("\\", File.separator).replace("/", File.separator), url.replace("\\", File.separator).replace("/", File.separator));
/*  98 */     File f = new File(path1);
/*  99 */     if (f != null) {
/* 100 */       f.delete();
/*     */     }
/* 102 */     entity = this.dao.deleteById(id);
/* 103 */     return entity;
/*     */   }
/*     */ 
/*     */   public Coupon[] deleteByIds(Long[] ids, String url)
/*     */   {
/* 108 */     Coupon[] beans = new Coupon[ids.length];
/* 109 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 110 */       beans[i] = deleteById(ids[i], url);
/*     */     }
/* 112 */     return beans;
/*     */   }
/*     */ 
/*     */   public void deleteByMemberId(Long memberId)
/*     */   {
/* 117 */     this.dao.deleteByMemberId(memberId);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.CouponMngImpl
 * JD-Core Version:    0.6.0
 */