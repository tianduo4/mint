/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.DiscussDao;
/*     */ import com.jspgou.cms.entity.Discuss;
/*     */ import com.jspgou.cms.manager.DiscussMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class DiscussMngImpl
/*     */   implements DiscussMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng memberMng;
/*     */   private ProductMng productMng;
/*     */   private DiscussDao dao;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Discuss findById(Long id)
/*     */   {
/*  27 */     Discuss entity = this.dao.findById(id);
/*  28 */     return entity;
/*     */   }
/*     */ 
/*     */   public Discuss saveOrUpdate(Long productId, String content, Long memberId, String discussType)
/*     */   {
/*  33 */     Discuss bean = new Discuss();
/*  34 */     bean.setContent(content);
/*  35 */     bean.setDiscussType(discussType);
/*  36 */     bean.setMember(this.memberMng.findById(memberId));
/*  37 */     bean.setProduct(this.productMng.findById(productId));
/*  38 */     bean.setTime(new Date());
/*  39 */     this.dao.saveOrUpdate(bean);
/*  40 */     return bean;
/*     */   }
/*     */ 
/*     */   public Pagination getPage(Long memberId, Long productId, String discussType, String userName, String productName, Date startTime, Date endTime, int pageNo, int pageSize, boolean cache)
/*     */   {
/*  47 */     return this.dao.getPageByProduct(memberId, productId, discussType, userName, productName, 
/*  48 */       startTime, endTime, pageNo, pageSize, cache);
/*     */   }
/*     */ 
/*     */   public Pagination getPageByMember(Long memberId, int pageNo, int pageSize, boolean cache) {
/*  52 */     return this.dao.getPageByMember(memberId, pageNo, pageSize, cache);
/*     */   }
/*     */ 
/*     */   public List<Discuss> findByType(Long productId, String discussType)
/*     */   {
/*  57 */     return this.dao.findByType(productId, discussType);
/*     */   }
/*     */ 
/*     */   public Discuss update(Discuss entity)
/*     */   {
/*  62 */     Updater updater = new Updater(entity);
/*  63 */     entity = this.dao.updateByUpdater(updater);
/*  64 */     return this.dao.update(entity);
/*     */   }
/*     */ 
/*     */   public Discuss[] deleteByIds(Long[] ids)
/*     */   {
/*  75 */     Discuss[] beans = new Discuss[ids.length];
/*  76 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  77 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  79 */     return beans;
/*     */   }
/*     */ 
/*     */   public Discuss deleteById(Long id)
/*     */   {
/*  84 */     Discuss bean = this.dao.deleteById(id);
/*  85 */     return bean;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setProductMng(ProductMng productMng)
/*     */   {
/*  96 */     this.productMng = productMng;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(DiscussDao dao) {
/* 102 */     this.dao = dao;
/*     */   }
/*     */ 
/*     */   public void deleteByMemberId(Long memberId)
/*     */   {
/* 107 */     this.dao.deleteByMemberId(memberId);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.DiscussMngImpl
 * JD-Core Version:    0.6.0
 */