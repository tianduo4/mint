/*    */ package com.jspgou.plug.store.manager.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.common.security.encoder.PwdEncoder;
/*    */ import com.jspgou.plug.store.dao.PlugStoreConfigDao;
/*    */ import com.jspgou.plug.store.entity.PlugStoreConfig;
/*    */ import com.jspgou.plug.store.manager.PlugStoreConfigMng;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class PlugStoreConfigMngImpl
/*    */   implements PlugStoreConfigMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private PlugStoreConfigDao dao;
/*    */ 
/*    */   @Autowired
/*    */   private PwdEncoder pwdEncoder;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 23 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 24 */     return page;
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public PlugStoreConfig findById(Integer id) {
/* 29 */     return this.dao.findById(id);
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public PlugStoreConfig getDefault() {
/* 34 */     return findById(Integer.valueOf(1));
/*    */   }
/*    */ 
/*    */   public PlugStoreConfig save(PlugStoreConfig bean) {
/* 38 */     this.dao.save(bean);
/* 39 */     return bean;
/*    */   }
/*    */ 
/*    */   public PlugStoreConfig update(PlugStoreConfig bean, String password) {
/* 43 */     Updater updater = new Updater(bean);
/* 44 */     if (StringUtils.isNotBlank(password))
/* 45 */       bean.setPassword(this.pwdEncoder.encodePassword(password));
/*    */     else {
/* 47 */       updater.exclude("password");
/*    */     }
/* 49 */     bean = this.dao.updateByUpdater(updater);
/* 50 */     return bean;
/*    */   }
/*    */ 
/*    */   public PlugStoreConfig deleteById(Integer id) {
/* 54 */     PlugStoreConfig bean = this.dao.deleteById(id);
/* 55 */     return bean;
/*    */   }
/*    */ 
/*    */   public PlugStoreConfig[] deleteByIds(Integer[] ids) {
/* 59 */     PlugStoreConfig[] beans = new PlugStoreConfig[ids.length];
/* 60 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 61 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 63 */     return beans;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.store.manager.impl.PlugStoreConfigMngImpl
 * JD-Core Version:    0.6.0
 */