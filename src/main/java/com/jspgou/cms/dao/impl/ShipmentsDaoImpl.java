/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ShipmentsDao;
/*    */ import com.jspgou.cms.entity.Logistics;
/*    */ import com.jspgou.cms.entity.Shipments;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.hibernate.criterion.Order;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ShipmentsDaoImpl extends HibernateBaseDao<Shipments, Long>
/*    */   implements ShipmentsDao
/*    */ {
/*    */   public Pagination getPage(Boolean isPrint, int pageNo, int pageSize)
/*    */   {
/* 26 */     Finder f = Finder.create("from Shipments bean where 1=1");
/*    */ 
/* 28 */     if (isPrint != null) {
/* 29 */       f.append(" and bean.isPrint=:isPrint");
/* 30 */       f.setParam("isPrint", isPrint);
/*    */     }
/* 32 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public List<Logistics> getAllList()
/*    */   {
/* 39 */     Criteria crit = createCriteria(new Criterion[0]);
/* 40 */     crit.addOrder(Order.asc(Logistics.PROP_PRIORITY));
/* 41 */     List list = crit.list();
/* 42 */     return list;
/*    */   }
/*    */ 
/*    */   public List<Shipments> getlist(Long orderId)
/*    */   {
/* 47 */     Finder f = Finder.create("from Shipments bean where bean.indent.id=:id");
/* 48 */     f.setParam("id", orderId);
/* 49 */     return find(f);
/*    */   }
/*    */ 
/*    */   public Shipments findById(Long id)
/*    */   {
/* 54 */     Shipments entity = (Shipments)get(id);
/* 55 */     return entity;
/*    */   }
/*    */ 
/*    */   public Shipments save(Shipments bean)
/*    */   {
/* 60 */     getSession().save(bean);
/* 61 */     return bean;
/*    */   }
/*    */ 
/*    */   public Shipments deleteById(Long id)
/*    */   {
/* 66 */     Shipments entity = (Shipments)super.get(id);
/* 67 */     if (entity != null) {
/* 68 */       getSession().delete(entity);
/*    */     }
/* 70 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Shipments> getEntityClass()
/*    */   {
/* 75 */     return Shipments.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ShipmentsDaoImpl
 * JD-Core Version:    0.6.0
 */