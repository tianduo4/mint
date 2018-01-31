/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.GatheringDao;
/*    */ import com.jspgou.cms.entity.Gathering;
/*    */ import com.jspgou.cms.manager.GatheringMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class GatheringMngImpl
/*    */   implements GatheringMng
/*    */ {
/*    */   private GatheringDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 24 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 25 */     return page;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Gathering findById(Long id) {
/* 31 */     Gathering entity = this.dao.findById(id);
/* 32 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<Gathering> getlist(Long orderId)
/*    */   {
/* 37 */     return this.dao.getlist(orderId);
/*    */   }
/*    */ 
/*    */   public void deleteByorderId(Long orderId)
/*    */   {
/* 42 */     List list = getlist(orderId);
/* 43 */     for (Gathering gathering : list)
/* 44 */       deleteById(gathering.getId());
/*    */   }
/*    */ 
/*    */   public Gathering save(Gathering bean)
/*    */   {
/* 50 */     this.dao.save(bean);
/* 51 */     return bean;
/*    */   }
/*    */ 
/*    */   public Gathering update(Gathering bean)
/*    */   {
/* 56 */     Updater updater = new Updater(bean);
/* 57 */     Gathering entity = this.dao.updateByUpdater(updater);
/* 58 */     return entity;
/*    */   }
/*    */ 
/*    */   public Gathering deleteById(Long id)
/*    */   {
/* 63 */     Gathering bean = this.dao.deleteById(id);
/* 64 */     return bean;
/*    */   }
/*    */ 
/*    */   public Gathering[] deleteByIds(Long[] ids)
/*    */   {
/* 69 */     Gathering[] beans = new Gathering[ids.length];
/* 70 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 71 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 73 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(GatheringDao dao)
/*    */   {
/* 80 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.GatheringMngImpl
 * JD-Core Version:    0.6.0
 */