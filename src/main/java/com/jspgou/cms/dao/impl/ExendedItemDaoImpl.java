/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ExendedItemDao;
/*    */ import com.jspgou.cms.entity.ExendedItem;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ExendedItemDaoImpl extends HibernateBaseDao<ExendedItem, Long>
/*    */   implements ExendedItemDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 20 */     Criteria crit = createCriteria(new Criterion[0]);
/* 21 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 22 */     return page;
/*    */   }
/*    */ 
/*    */   public ExendedItem findById(Long id)
/*    */   {
/* 27 */     ExendedItem entity = (ExendedItem)get(id);
/* 28 */     return entity;
/*    */   }
/*    */ 
/*    */   public ExendedItem save(ExendedItem bean)
/*    */   {
/* 33 */     getSession().save(bean);
/* 34 */     return bean;
/*    */   }
/*    */ 
/*    */   public ExendedItem deleteById(Long id)
/*    */   {
/* 39 */     ExendedItem entity = (ExendedItem)super.get(id);
/* 40 */     if (entity != null) {
/* 41 */       getSession().delete(entity);
/*    */     }
/* 43 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ExendedItem> getEntityClass()
/*    */   {
/* 48 */     return ExendedItem.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ExendedItemDaoImpl
 * JD-Core Version:    0.6.0
 */