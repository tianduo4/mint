/*    */ package com.jspgou.cms.manager.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.OrderItemDao;
/*    */ import com.jspgou.cms.entity.OrderItem;
/*    */ import com.jspgou.cms.manager.OrderItemMng;
/*    */ import com.jspgou.common.hibernate4.Updater;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ @Transactional
/*    */ public class OrderItemMngImpl
/*    */   implements OrderItemMng
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private OrderItemDao dao;
/*    */ 
/*    */   public List<Object[]> profitTop(Long ctgid, Long typeid, Integer pageNo, Integer pageSize)
/*    */   {
/* 25 */     return this.dao.profitTop(ctgid, typeid, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public Integer totalCount(Long ctgid, Long typeid)
/*    */   {
/* 30 */     return this.dao.totalCount(ctgid, typeid);
/*    */   }
/*    */ 
/*    */   public OrderItem findById(Long id)
/*    */   {
/* 35 */     return this.dao.findById(id);
/*    */   }
/*    */ 
/*    */   public OrderItem[] findById(Long[] ids) {
/* 39 */     OrderItem[] beans = new OrderItem[ids.length];
/* 40 */     for (int i = 0; i < ids.length; i++) {
/* 41 */       beans[i] = findById(ids[i]);
/*    */     }
/* 43 */     return beans;
/*    */   }
/*    */ 
/*    */   public OrderItem updateByUpdater(OrderItem bean)
/*    */   {
/* 48 */     Updater updater = new Updater(bean);
/* 49 */     return this.dao.updateByUpdater(updater);
/*    */   }
/*    */ 
/*    */   public OrderItem[] updateByUpdaters(OrderItem[] item) {
/* 53 */     OrderItem[] beans = new OrderItem[item.length];
/* 54 */     for (int i = 0; i < item.length; i++) {
/* 55 */       beans[i] = updateByUpdater(item[i]);
/*    */     }
/* 57 */     return beans;
/*    */   }
/*    */ 
/*    */   public Pagination getPageByMember(Integer status, Long memberId, int pageNo, int pageSize)
/*    */   {
/* 63 */     return this.dao.getPageForMember(memberId, status, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public List<Object[]> getOrderItem(Date endTime, Date beginTime)
/*    */   {
/* 68 */     List orderItemList = this.dao.getOrderItem(endTime, beginTime);
/* 69 */     return orderItemList;
/*    */   }
/*    */ 
/*    */   public OrderItem findByMember(Long memberId, Long productId, Long orderId)
/*    */   {
/* 75 */     return this.dao.findByMember(memberId, productId, orderId);
/*    */   }
/*    */ 
/*    */   public Pagination getOrderItem(Integer pageNo, Integer pageSize, Long productId)
/*    */   {
/* 80 */     return this.dao.getPageForProuct(productId, pageNo.intValue(), pageSize.intValue());
/*    */   }
/*    */ 
/*    */   public Pagination getPageProductSaleRank(Long webId, String type, Integer categoryId, int pageNo, int pageSize, Date startTime, Date endTime)
/*    */   {
/* 85 */     return this.dao.getPageProductSaleRank(webId, type, categoryId, pageNo, pageSize, startTime, endTime);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.OrderItemMngImpl
 * JD-Core Version:    0.6.0
 */