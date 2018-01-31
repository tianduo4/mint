/*    */ package com.jspgou.core.manager.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.core.dao.ThirdAccountDao;
/*    */ import com.jspgou.core.entity.ThirdAccount;
/*    */ import com.jspgou.core.manager.ThirdAccountMng;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ThirdAccountMngImpl
/*    */   implements ThirdAccountMng
/*    */ {
/*    */   private ThirdAccountDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(String username, String source, int pageNo, int pageSize)
/*    */   {
/* 20 */     Pagination page = this.dao.getPage(username, source, pageNo, pageSize);
/* 21 */     return page;
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public ThirdAccount findById(Long id) {
/* 26 */     ThirdAccount entity = this.dao.findById(id);
/* 27 */     return entity;
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public ThirdAccount findByKey(String key) {
/* 32 */     return this.dao.findByKey(key);
/*    */   }
/*    */ 
/*    */   public ThirdAccount save(ThirdAccount bean) {
/* 36 */     this.dao.save(bean);
/* 37 */     return bean;
/*    */   }
/*    */ 
/*    */   public ThirdAccount update(ThirdAccount bean) {
/* 41 */     Updater updater = new Updater(bean);
/* 42 */     bean = this.dao.updateByUpdater(updater);
/* 43 */     return bean;
/*    */   }
/*    */ 
/*    */   public ThirdAccount deleteById(Long id) {
/* 47 */     ThirdAccount bean = this.dao.deleteById(id);
/* 48 */     return bean;
/*    */   }
/*    */ 
/*    */   public ThirdAccount[] deleteByIds(Long[] ids) {
/* 52 */     ThirdAccount[] beans = new ThirdAccount[ids.length];
/* 53 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 54 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 56 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ThirdAccountDao dao)
/*    */   {
/* 63 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.impl.ThirdAccountMngImpl
 * JD-Core Version:    0.6.0
 */