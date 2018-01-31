/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopPlugDao;
/*    */ import com.jspgou.cms.entity.ShopPlug;
/*    */ import com.jspgou.cms.manager.ShopPlugMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShopPlugMngImpl
/*    */   implements ShopPlugMng
/*    */ {
/*    */   private ShopPlugDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 19 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 20 */     return page;
/*    */   }
/*    */ 
/*    */   public List<ShopPlug> getList(String author, Boolean used) {
/* 24 */     return this.dao.getList(author, used);
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public ShopPlug findById(Long id) {
/* 29 */     ShopPlug entity = this.dao.findById(id);
/* 30 */     return entity;
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public ShopPlug findByPath(String plugPath) {
/* 35 */     return this.dao.findByPath(plugPath);
/*    */   }
/*    */ 
/*    */   public ShopPlug save(ShopPlug bean) {
/* 39 */     this.dao.save(bean);
/* 40 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopPlug update(ShopPlug bean) {
/* 44 */     Updater updater = new Updater(bean);
/* 45 */     bean = this.dao.updateByUpdater(updater);
/* 46 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopPlug deleteById(Long id) {
/* 50 */     ShopPlug bean = this.dao.deleteById(id);
/* 51 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopPlug[] deleteByIds(Long[] ids) {
/* 55 */     ShopPlug[] beans = new ShopPlug[ids.length];
/* 56 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 57 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 59 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ShopPlugDao dao)
/*    */   {
/* 67 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopPlugMngImpl
 * JD-Core Version:    0.6.0
 */