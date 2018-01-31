/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.StandardDao;
/*    */ import com.jspgou.cms.entity.Standard;
/*    */ import com.jspgou.cms.manager.StandardMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class StandardMngImpl
/*    */   implements StandardMng
/*    */ {
/*    */   private StandardDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 25 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 26 */     return page;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Standard findById(Long id) {
/* 32 */     Standard entity = this.dao.findById(id);
/* 33 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<Standard> findByTypeId(Long typeId)
/*    */   {
/* 38 */     return this.dao.findByTypeId(typeId);
/*    */   }
/*    */ 
/*    */   public Standard save(Standard bean)
/*    */   {
/* 43 */     this.dao.save(bean);
/* 44 */     return bean;
/*    */   }
/*    */ 
/*    */   public Standard update(Standard bean)
/*    */   {
/* 49 */     Updater updater = new Updater(bean);
/* 50 */     Standard entity = this.dao.updateByUpdater(updater);
/* 51 */     return entity;
/*    */   }
/*    */ 
/*    */   public Standard deleteById(Long id)
/*    */   {
/* 56 */     Standard bean = this.dao.deleteById(id);
/* 57 */     return bean;
/*    */   }
/*    */ 
/*    */   public Standard[] deleteByIds(Long[] ids)
/*    */   {
/* 62 */     Standard[] beans = new Standard[ids.length];
/* 63 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 64 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 66 */     return beans;
/*    */   }
/*    */ 
/*    */   public Standard[] updatePriority(Long[] ids, Integer[] priority)
/*    */   {
/* 71 */     int len = ids.length;
/* 72 */     Standard[] beans = new Standard[len];
/* 73 */     for (int i = 0; i < len; i++) {
/* 74 */       beans[i] = findById(ids[i]);
/*    */     }
/* 76 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(StandardDao dao)
/*    */   {
/* 83 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.StandardMngImpl
 * JD-Core Version:    0.6.0
 */