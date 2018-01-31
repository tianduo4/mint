/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShopShipmentsDao;
/*    */ import com.jspgou.cms.entity.ShopShipments;
/*    */ import com.jspgou.cms.manager.ShopShipmentsMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShopShipmentsMngImpl
/*    */   implements ShopShipmentsMng
/*    */ {
/*    */   private ShopShipmentsDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 20 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/* 21 */     return page;
/*    */   }
/*    */   @Transactional(readOnly=true)
/*    */   public ShopShipments findById(Long id) {
/* 26 */     ShopShipments entity = this.dao.findById(id);
/* 27 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<ShopShipments> getList(Boolean isDefault) {
/* 31 */     return this.dao.getList(isDefault);
/*    */   }
/*    */ 
/*    */   public ShopShipments save(ShopShipments bean) {
/* 35 */     this.dao.save(bean);
/* 36 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopShipments update(ShopShipments bean) {
/* 40 */     Updater updater = new Updater(bean);
/* 41 */     ShopShipments entity = this.dao.updateByUpdater(updater);
/* 42 */     return entity;
/*    */   }
/*    */ 
/*    */   public ShopShipments deleteById(Long id) {
/* 46 */     ShopShipments bean = this.dao.deleteById(id);
/* 47 */     return bean;
/*    */   }
/*    */ 
/*    */   public ShopShipments[] deleteByIds(Long[] ids) {
/* 51 */     ShopShipments[] beans = new ShopShipments[ids.length];
/* 52 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 53 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 55 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ShopShipmentsDao dao)
/*    */   {
/* 62 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShopShipmentsMngImpl
 * JD-Core Version:    0.6.0
 */