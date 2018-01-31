/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ReceiverMessageDao;
/*    */ import com.jspgou.cms.entity.ReceiverMessage;
/*    */ import com.jspgou.cms.manager.ReceiverMessageMng;
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
/*    */ public class ReceiverMessageMngImpl
/*    */   implements ReceiverMessageMng
/*    */ {
/*    */   private ReceiverMessageDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 21 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 22 */     return page;
/*    */   }
/*    */ 
/*    */   public Pagination getPage(Long sendMemberId, int pageNo, Integer box, int pageSize) {
/* 26 */     return this.dao.getPage(sendMemberId, pageNo, box, pageSize);
/*    */   }
/*    */ 
/*    */   public Pagination getPage(Long sendMemberId, Long receiverMemberId, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, Boolean cacheable, int pageNo, int pageSize)
/*    */   {
/* 34 */     return this.dao.getPage(sendMemberId, receiverMemberId, title, 
/* 35 */       sendBeginTime, sendEndTime, status, box, cacheable, pageNo, 
/* 36 */       pageSize);
/*    */   }
/*    */ 
/*    */   public List<ReceiverMessage> getList(Long sendMemberId, Long receiverMemberId, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, Boolean cacheable)
/*    */   {
/* 42 */     return this.dao.getList(sendMemberId, receiverMemberId, title, 
/* 43 */       sendBeginTime, sendEndTime, status, box, cacheable);
/*    */   }
/*    */ 
/*    */   public Pagination getUnreadPage(Long sendMemberId, int pageNo, int pageSize) {
/* 47 */     return this.dao.getUnreadPage(sendMemberId, pageNo, pageSize);
/*    */   }
/* 51 */   @Transactional(readOnly=true)
/*    */   public ReceiverMessage findById(Long id) { ReceiverMessage entity = this.dao.findById(id);
/* 52 */     return entity; }
/*    */ 
/*    */   public ReceiverMessage save(ReceiverMessage bean)
/*    */   {
/* 56 */     this.dao.save(bean);
/* 57 */     return bean;
/*    */   }
/*    */ 
/*    */   public ReceiverMessage update(ReceiverMessage bean) {
/* 61 */     Updater updater = new Updater(bean);
/* 62 */     ReceiverMessage entity = this.dao.updateByUpdater(updater);
/* 63 */     return entity;
/*    */   }
/*    */ 
/*    */   public ReceiverMessage deleteById(Long id) {
/* 67 */     ReceiverMessage bean = this.dao.deleteById(id);
/* 68 */     return bean;
/*    */   }
/*    */ 
/*    */   public ReceiverMessage[] deleteByIds(Long[] ids) {
/* 72 */     ReceiverMessage[] beans = new ReceiverMessage[ids.length];
/* 73 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 74 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 76 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ReceiverMessageDao dao)
/*    */   {
/* 83 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ReceiverMessageMngImpl
 * JD-Core Version:    0.6.0
 */