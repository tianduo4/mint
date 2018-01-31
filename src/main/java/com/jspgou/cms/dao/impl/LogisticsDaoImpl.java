/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.LogisticsDao;
/*    */ import com.jspgou.cms.entity.Logistics;
/*    */ import com.jspgou.cms.entity.base.BaseLogistics;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import java.util.List;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.hibernate.criterion.Order;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class LogisticsDaoImpl extends HibernateBaseDao<Logistics, Long>
/*    */   implements LogisticsDao
/*    */ {
/*    */   public List<Logistics> getAllList()
/*    */   {
/* 24 */     Criteria crit = createCriteria(new Criterion[0]);
/* 25 */     crit.addOrder(Order.asc(BaseLogistics.PROP_PRIORITY));
/* 26 */     List list = crit.list();
/* 27 */     return list;
/*    */   }
/*    */ 
/*    */   public Logistics findById(Long id)
/*    */   {
/* 32 */     Logistics entity = (Logistics)get(id);
/* 33 */     return entity;
/*    */   }
/*    */ 
/*    */   public Logistics save(Logistics bean)
/*    */   {
/* 38 */     getSession().save(bean);
/* 39 */     return bean;
/*    */   }
/*    */ 
/*    */   public Logistics deleteById(Long id)
/*    */   {
/* 44 */     Logistics entity = (Logistics)super.get(id);
/* 45 */     if (entity != null) {
/* 46 */       getSession().delete(entity);
/*    */     }
/* 48 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Logistics> getEntityClass()
/*    */   {
/* 53 */     return Logistics.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.LogisticsDaoImpl
 * JD-Core Version:    0.6.0
 */