/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ApiAccountDao;
/*    */ import com.jspgou.cms.entity.ApiAccount;
/*    */ import com.jspgou.cms.manager.ApiAccountMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ApiAccountMngImpl
/*    */   implements ApiAccountMng
/*    */ {
/*    */   private ApiAccountDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 18 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 19 */     return page;
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public ApiAccount findById(Long id) {
/* 24 */     ApiAccount entity = this.dao.findById(id);
/* 25 */     return entity;
/*    */   }
/*    */ 
/*    */   public ApiAccount save(ApiAccount bean) {
/* 29 */     this.dao.save(bean);
/* 30 */     return bean;
/*    */   }
/*    */ 
/*    */   public ApiAccount update(ApiAccount bean) {
/* 34 */     Updater updater = new Updater(bean);
/* 35 */     ApiAccount entity = this.dao.updateByUpdater(updater);
/* 36 */     return entity;
/*    */   }
/*    */ 
/*    */   public ApiAccount deleteById(Long id) {
/* 40 */     ApiAccount bean = this.dao.deleteById(id);
/* 41 */     return bean;
/*    */   }
/*    */ 
/*    */   public ApiAccount[] deleteByIds(Long[] ids) {
/* 45 */     ApiAccount[] beans = new ApiAccount[ids.length];
/* 46 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 47 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 49 */     return beans;
/*    */   }
/*    */ 
/*    */   public ApiAccount findByAppId(String appId) {
/* 53 */     return this.dao.findByAppId(appId);
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ApiAccountDao dao)
/*    */   {
/* 61 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ApiAccountMngImpl
 * JD-Core Version:    0.6.0
 */