/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShipmentsDao;
/*    */ import com.jspgou.cms.entity.Logistics;
/*    */ import com.jspgou.cms.entity.Shipments;
/*    */ import com.jspgou.cms.manager.ShipmentsMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShipmentsMngImpl
/*    */   implements ShipmentsMng
/*    */ {
/*    */   private ShipmentsDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(Boolean isPrint, int pageNo, int pageSize)
/*    */   {
/* 26 */     Pagination page = this.dao.getPage(isPrint, pageNo, pageSize);
/* 27 */     return page;
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public List<Logistics> getAllList() {
/* 32 */     List list = this.dao.getAllList();
/* 33 */     return list;
/*    */   }
/*    */ 
/*    */   public List<Shipments> getlist(Long orderId)
/*    */   {
/* 38 */     return this.dao.getlist(orderId);
/*    */   }
/*    */ 
/*    */   public void deleteByorderId(Long orderId)
/*    */   {
/* 43 */     List list = getlist(orderId);
/* 44 */     for (Shipments shipments : list)
/* 45 */       deleteById(shipments.getId());
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Shipments findById(Long id)
/*    */   {
/* 52 */     Shipments entity = this.dao.findById(id);
/* 53 */     return entity;
/*    */   }
/*    */ 
/*    */   public Shipments save(Shipments bean)
/*    */   {
/* 58 */     this.dao.save(bean);
/* 59 */     return bean;
/*    */   }
/*    */ 
/*    */   public Shipments update(Shipments bean)
/*    */   {
/* 64 */     Updater updater = new Updater(bean);
/* 65 */     Shipments entity = this.dao.updateByUpdater(updater);
/* 66 */     return entity;
/*    */   }
/*    */ 
/*    */   public Shipments deleteById(Long id)
/*    */   {
/* 71 */     Shipments bean = this.dao.deleteById(id);
/* 72 */     return bean;
/*    */   }
/*    */ 
/*    */   public Shipments[] deleteByIds(Long[] ids)
/*    */   {
/* 77 */     Shipments[] beans = new Shipments[ids.length];
/* 78 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 79 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 81 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ShipmentsDao dao)
/*    */   {
/* 88 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShipmentsMngImpl
 * JD-Core Version:    0.6.0
 */