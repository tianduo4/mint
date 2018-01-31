/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.LogisticsDao;
/*    */ import com.jspgou.cms.entity.Logistics;
/*    */ import com.jspgou.cms.entity.LogisticsText;
/*    */ import com.jspgou.cms.manager.LogisticsMng;
/*    */ import com.jspgou.cms.manager.LogisticsTextMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class LogisticsMngImpl
/*    */   implements LogisticsMng
/*    */ {
/*    */   private LogisticsTextMng logisticsTextMng;
/*    */   private LogisticsDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<Logistics> getAllList()
/*    */   {
/* 25 */     List list = this.dao.getAllList();
/* 26 */     return list;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Logistics findById(Long id) {
/* 32 */     Logistics entity = this.dao.findById(id);
/* 33 */     return entity;
/*    */   }
/*    */ 
/*    */   public Logistics save(Logistics bean, String text)
/*    */   {
/* 38 */     Logistics entity = this.dao.save(bean);
/* 39 */     this.logisticsTextMng.save(entity, text);
/* 40 */     return entity;
/*    */   }
/*    */ 
/*    */   public Logistics update(Logistics bean, String text)
/*    */   {
/* 45 */     Updater updater = new Updater(bean);
/* 46 */     Logistics entity = this.dao.updateByUpdater(updater);
/* 47 */     entity.getLogisticsText().setText(text);
/* 48 */     return entity;
/*    */   }
/*    */ 
/*    */   public Logistics deleteById(Long id)
/*    */   {
/* 53 */     return this.dao.deleteById(id);
/*    */   }
/*    */ 
/*    */   public Logistics[] deleteByIds(Long[] ids)
/*    */   {
/* 58 */     Logistics[] beans = new Logistics[ids.length];
/* 59 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 60 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 62 */     return beans;
/*    */   }
/*    */ 
/*    */   public Logistics[] updatePriority(Long[] ids, Integer[] priority)
/*    */   {
/* 67 */     Logistics[] beans = new Logistics[ids.length];
/* 68 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 69 */       beans[i] = findById(ids[i]);
/* 70 */       beans[i].setPriority(priority[i]);
/*    */     }
/* 72 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setLogisticsTextMng(LogisticsTextMng logisticsTextMng)
/*    */   {
/* 81 */     this.logisticsTextMng = logisticsTextMng;
/*    */   }
/*    */   @Autowired
/*    */   public void setDao(LogisticsDao dao) {
/* 86 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.LogisticsMngImpl
 * JD-Core Version:    0.6.0
 */