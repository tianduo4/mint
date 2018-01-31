/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShippingDao;
/*    */ import com.jspgou.cms.entity.Shipping;
/*    */ import com.jspgou.cms.manager.ShippingMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class ShippingMngImpl
/*    */   implements ShippingMng
/*    */ {
/*    */   private ShippingDao dao;
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<Shipping> getList(Long webId, boolean all)
/*    */   {
/* 24 */     return this.dao.getList(webId, all, false);
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public List<Shipping> getListForCart(Long webId, Long countryId, int weight, int count)
/*    */   {
/* 31 */     List list = this.dao.getList(webId, false, true);
/*    */     Shipping localShipping;
/* 32 */     for (Iterator localIterator = list.iterator(); localIterator.hasNext(); localShipping = (Shipping)localIterator.next());
/* 35 */     return list;
/*    */   }
/*    */ 
/*    */   @Transactional(readOnly=true)
/*    */   public Shipping findById(Long id) {
/* 41 */     Shipping entity = this.dao.findById(id);
/* 42 */     return entity;
/*    */   }
/*    */ 
/*    */   public Shipping save(Shipping bean)
/*    */   {
/* 47 */     this.dao.save(bean);
/* 48 */     return bean;
/*    */   }
/*    */ 
/*    */   public Shipping update(Shipping bean)
/*    */   {
/* 53 */     Shipping entity = findById(bean.getId());
/* 54 */     Updater updater = new Updater(bean);
/* 55 */     entity = this.dao.updateByUpdater(updater);
/* 56 */     return entity;
/*    */   }
/*    */ 
/*    */   public Shipping deleteById(Long id)
/*    */   {
/* 61 */     Shipping bean = this.dao.deleteById(id);
/* 62 */     return bean;
/*    */   }
/*    */ 
/*    */   public Shipping[] deleteByIds(Long[] ids)
/*    */   {
/* 67 */     Shipping[] beans = new Shipping[ids.length];
/* 68 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 69 */       beans[i] = deleteById(ids[i]);
/*    */     }
/* 71 */     return beans;
/*    */   }
/*    */ 
/*    */   public Shipping[] updatePriority(Long[] ids, Integer[] priority)
/*    */   {
/* 76 */     Shipping[] beans = new Shipping[ids.length];
/* 77 */     int i = 0; for (int len = ids.length; i < len; i++) {
/* 78 */       beans[i] = findById(ids[i]);
/* 79 */       beans[i].setPriority(priority[i]);
/*    */     }
/* 81 */     return beans;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setDao(ShippingDao dao)
/*    */   {
/* 89 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.ShippingMngImpl
 * JD-Core Version:    0.6.0
 */