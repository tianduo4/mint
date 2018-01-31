/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopScoreDao;
/*    */ import com.jspgou.cms.entity.ShopScore;
/*    */ import com.jspgou.cms.manager.ShopScoreMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShopScoreMngImpl
/*    */   implements ShopScoreMng
/*    */ {
/*    */   private ShopScoreDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(Long memberId, Boolean status, Boolean useStatus, Date startTime, Date endTime, Integer pageSize, Integer pageNo)
/*    */   {
/* 27 */     Pagination page = this.dao.getPage(memberId, status, useStatus, 
/* 28 */       startTime, endTime, pageNo, pageSize);
/* 29 */     return page;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<ShopScore> getlist(String code) {
/* 35 */     return this.dao.getlist(code);
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public ShopScore findById(Long id) {
/* 41 */     ShopScore entity = this.dao.findById(id);
/* 42 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopScore save(ShopScore bean)
/*    */   {
/* 47 */     this.dao.save(bean);
/* 48 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopScore update(ShopScore bean)
/*    */   {
/* 53 */     Updater updater = new Updater(bean);
/* 54 */     ShopScore entity = this.dao.updateByUpdater(updater);
/* 55 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopScore deleteById(Long id)
/*    */   {
/* 60 */     ShopScore bean = this.dao.deleteById(id);
/* 61 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopScore[] deleteByIds(Long[] ids)
/*    */   {
/* 66 */     ShopScore[] beans = new ShopScore[ids.length];
/* 67 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 68 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 70 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ShopScoreDao dao)
/*    */   {
/* 77 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public void deleteByMemberId(Long memberId)
/*    */   {
/* 82 */     this.dao.deleteByMemberId(memberId);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopScoreMngImpl
 * JD-Core Version:    0.6.0
 */