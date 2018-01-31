/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ApiInfoDao;
/*    */ import com.jspgou.cms.entity.ApiInfo;
/*    */ import com.jspgou.cms.manager.ApiInfoMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ApiInfoMngImpl
/*    */   implements ApiInfoMng
/*    */ {
/*    */   private ApiInfoDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 18 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 19 */     return page;
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public ApiInfo findById(Long id) {
/* 24 */     ApiInfo entity = this.dao.findById(id);
/* 25 */     return entity;
/*    */   }
/*    */ 
/*    */   public ApiInfo findByApiUrl(String apiUrl)
/*    */   {
/* 30 */     return this.dao.findByApiUrl(apiUrl);
/*    */   }
/*    */ 
/*    */   public ApiInfo save(ApiInfo bean)
/*    */   {
/* 36 */     bean.init();
/* 37 */     this.dao.save(bean);
/* 38 */     return bean;
/*    */   }
/*    */ 
/*    */   public ApiInfo update(ApiInfo bean) {
/* 42 */     Updater updater = new Updater(bean);
/* 43 */     ApiInfo entity = this.dao.updateByUpdater(updater);
/* 44 */     return entity;
/*    */   }
/*    */ 
/*    */   public ApiInfo deleteById(Long id) {
/* 48 */     ApiInfo bean = this.dao.deleteById(id);
/* 49 */     return bean;
/*    */   }
/*    */ 
/*    */   public ApiInfo[] deleteByIds(Long[] ids) {
/* 53 */     ApiInfo[] beans = new ApiInfo[ids.length];
/* 54 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 55 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 57 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ApiInfoDao dao)
/*    */   {
/* 64 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ApiInfoMngImpl
 * JD-Core Version:    0.6.0
 */