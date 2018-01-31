/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ConsultDao;
/*     */ import com.jspgou.cms.entity.Consult;
/*     */ import com.jspgou.cms.manager.ConsultMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class ConsultMngImpl
/*     */   implements ConsultMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng memberMng;
/*     */   private ProductMng productMng;
/*     */   private ConsultDao dao;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Consult findById(Long id)
/*     */   {
/*  26 */     Consult entity = this.dao.findById(id);
/*  27 */     return entity;
/*     */   }
/*     */ 
/*     */   public Consult saveOrUpdate(Long productId, String content, Long memberId)
/*     */   {
/*  32 */     Consult bean = new Consult();
/*  33 */     bean.setConsult(content);
/*  34 */     bean.setMember(this.memberMng.findById(memberId));
/*  35 */     bean.setTime(new Date());
/*  36 */     bean.setProduct(this.productMng.findById(productId));
/*  37 */     Consult consult = this.dao.getSameConsult(memberId);
/*  38 */     Long time = Long.valueOf(System.currentTimeMillis());
/*  39 */     if (consult == null) {
/*  40 */       return this.dao.saveOrUpdate(bean);
/*     */     }
/*  42 */     if ((time.longValue() - consult.getTime().getTime()) / 1000L < 30L) {
/*  43 */       return null;
/*     */     }
/*  45 */     return this.dao.saveOrUpdate(bean);
/*     */   }
/*     */ 
/*     */   public List<Consult> findByProductId(Long productId)
/*     */   {
/*  53 */     return this.dao.findByProductId(productId);
/*     */   }
/*     */ 
/*     */   public Pagination getPageByMember(Long memberId, int pageNo, int pageSize, boolean cache)
/*     */   {
/*  58 */     return this.dao.getPageByMember(memberId, pageNo, pageSize, cache);
/*     */   }
/*     */ 
/*     */   public Pagination getPage(Long productId, String userName, String productName, Date startTime, Date endTime, int pageNo, int pageSize, Boolean cache)
/*     */   {
/*  64 */     return this.dao.getPage(productId, userName, productName, startTime, endTime, pageNo, pageSize, cache.booleanValue());
/*     */   }
/*     */ 
/*     */   public Pagination getVisiblePage(String userName, String productName, Date startTime, Date endTime, int pageNo, int pageSize)
/*     */   {
/*  72 */     return this.dao.getVisiblePage(userName, productName, startTime, endTime, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Consult update(Consult Consult)
/*     */   {
/*  77 */     return this.dao.update(Consult);
/*     */   }
/*     */ 
/*     */   public Consult deleteById(Long id)
/*     */   {
/*  82 */     Consult bean = this.dao.deleteById(id);
/*  83 */     return bean;
/*     */   }
/*     */ 
/*     */   public Consult[] deleteByIds(Long[] ids)
/*     */   {
/*  88 */     Consult[] beans = new Consult[ids.length];
/*  89 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  90 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  92 */     return beans;
/*     */   }
/*     */ 
/*     */   public Long getProductConsult()
/*     */   {
/*  97 */     return this.dao.getProductConsult();
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setProductMng(ProductMng productMng)
/*     */   {
/* 107 */     this.productMng = productMng;
/*     */   }
/*     */   @Autowired
/*     */   public void setDao(ConsultDao dao) {
/* 112 */     this.dao = dao;
/*     */   }
/*     */ 
/*     */   public void deleteByMemberId(Long memberId)
/*     */   {
/* 117 */     this.dao.deleteByMemberId(memberId);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ConsultMngImpl
 * JD-Core Version:    0.6.0
 */