/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.OrderReturnDao;
/*    */ import com.jspgou.cms.entity.OrderReturn;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class OrderReturnDaoImpl extends HibernateBaseDao<OrderReturn, Long>
/*    */   implements OrderReturnDao
/*    */ {
/*    */   public Pagination getPage(Integer status, int pageNo, int pageSize)
/*    */   {
/* 21 */     Finder f = Finder.create("from OrderReturn bean where 1=1");
/* 22 */     if (status != null) {
/* 23 */       f.append(" and bean.status=:status");
/* 24 */       f.setParam("status", status);
/*    */     }
/* 26 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public OrderReturn findById(Long id)
/*    */   {
/* 31 */     OrderReturn entity = (OrderReturn)get(id);
/* 32 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<OrderReturn> findByOrderId(Long orderId)
/*    */   {
/* 38 */     Finder f = Finder.create("from OrderReturn bean where bean.order.id=:orderId");
/* 39 */     f.setParam("orderId", orderId);
/* 40 */     return find(f);
/*    */   }
/*    */ 
/*    */   public OrderReturn save(OrderReturn bean)
/*    */   {
/* 45 */     getSession().save(bean);
/* 46 */     return bean;
/*    */   }
/*    */ 
/*    */   public OrderReturn deleteById(Long id)
/*    */   {
/* 51 */     OrderReturn entity = (OrderReturn)super.get(id);
/* 52 */     if (entity != null) {
/* 53 */       getSession().delete(entity);
/*    */     }
/* 55 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<OrderReturn> getEntityClass()
/*    */   {
/* 60 */     return OrderReturn.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.OrderReturnDaoImpl
 * JD-Core Version:    0.6.0
 */