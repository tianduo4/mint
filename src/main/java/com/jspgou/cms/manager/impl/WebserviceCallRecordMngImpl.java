/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.WebserviceCallRecordDao;
/*    */ import com.jspgou.cms.entity.WebserviceCallRecord;
/*    */ import com.jspgou.cms.manager.WebserviceAuthMng;
/*    */ import com.jspgou.cms.manager.WebserviceCallRecordMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.Calendar;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class WebserviceCallRecordMngImpl
/*    */   implements WebserviceCallRecordMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private WebserviceAuthMng webserviceAuthMng;
/*    */   private WebserviceCallRecordDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 22 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 23 */     return page;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public WebserviceCallRecord findById(Integer id) {
/* 29 */     WebserviceCallRecord entity = this.dao.findById(id);
/* 30 */     return entity;
/*    */   }
/*    */ 
/*    */   public WebserviceCallRecord save(String clientUsername, String serviceCode)
/*    */   {
/* 35 */     WebserviceCallRecord record = new WebserviceCallRecord();
/* 36 */     record.setAuth(this.webserviceAuthMng.findByUsername(clientUsername));
/* 37 */     record.setRecordTime(Calendar.getInstance().getTime());
/* 38 */     record.setServiceCode(serviceCode);
/* 39 */     return save(record);
/*    */   }
/*    */ 
/*    */   public WebserviceCallRecord save(WebserviceCallRecord bean)
/*    */   {
/* 44 */     this.dao.save(bean);
/* 45 */     return bean;
/*    */   }
/*    */ 
/*    */   public WebserviceCallRecord update(WebserviceCallRecord bean)
/*    */   {
/* 50 */     Updater updater = new Updater(bean);
/* 51 */     WebserviceCallRecord entity = this.dao.updateByUpdater(updater);
/* 52 */     return entity;
/*    */   }
/*    */ 
/*    */   public WebserviceCallRecord deleteById(Integer id)
/*    */   {
/* 57 */     WebserviceCallRecord bean = this.dao.deleteById(id);
/* 58 */     return bean;
/*    */   }
/*    */ 
/*    */   public WebserviceCallRecord[] deleteByIds(Integer[] ids)
/*    */   {
/* 63 */     WebserviceCallRecord[] beans = new WebserviceCallRecord[ids.length];
/* 64 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 65 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 67 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(WebserviceCallRecordDao dao)
/*    */   {
/* 76 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.WebserviceCallRecordMngImpl
 * JD-Core Version:    0.6.0
 */