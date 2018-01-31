/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ExendedItemDao;
/*    */ import com.jspgou.cms.entity.ExendedItem;
/*    */ import com.jspgou.cms.manager.ExendedItemMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ExendedItemMngImpl
/*    */   implements ExendedItemMng
/*    */ {
/*    */   private ExendedItemDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 22 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 23 */     return page;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public ExendedItem findById(Long id) {
/* 29 */     ExendedItem entity = this.dao.findById(id);
/* 30 */     return entity;
/*    */   }
/*    */ 
/*    */   public ExendedItem save(ExendedItem item)
/*    */   {
/* 35 */     return this.dao.save(item);
/*    */   }
/*    */ 
/*    */   public ExendedItem update(ExendedItem item)
/*    */   {
/* 40 */     Updater updater = new Updater(item);
/* 41 */     ExendedItem entity = this.dao.updateByUpdater(updater);
/* 42 */     return entity;
/*    */   }
/*    */ 
/*    */   public ExendedItem deleteById(Long id)
/*    */   {
/* 47 */     ExendedItem bean = this.dao.deleteById(id);
/* 48 */     return bean;
/*    */   }
/*    */ 
/*    */   public ExendedItem[] deleteByIds(Long[] ids)
/*    */   {
/* 53 */     ExendedItem[] beans = new ExendedItem[ids.length];
/* 54 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 55 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 57 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ExendedItemDao dao)
/*    */   {
/* 64 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ExendedItemMngImpl
 * JD-Core Version:    0.6.0
 */