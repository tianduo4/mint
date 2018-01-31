/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.PopularityGroupDao;
/*    */ import com.jspgou.cms.entity.PopularityGroup;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class PopularityGroupDaoImpl extends HibernateBaseDao<PopularityGroup, Long>
/*    */   implements PopularityGroupDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 15 */     Criteria crit = createCriteria(new Criterion[0]);
/* 16 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 17 */     return page;
/*    */   }
/*    */ 
/*    */   public PopularityGroup findById(Long id)
/*    */   {
/* 22 */     PopularityGroup entity = (PopularityGroup)get(id);
/* 23 */     return entity;
/*    */   }
/*    */ 
/*    */   public PopularityGroup save(PopularityGroup bean)
/*    */   {
/* 28 */     getSession().save(bean);
/* 29 */     return bean;
/*    */   }
/*    */ 
/*    */   public PopularityGroup deleteById(Long id)
/*    */   {
/* 34 */     PopularityGroup entity = (PopularityGroup)super.get(id);
/* 35 */     if (entity != null) {
/* 36 */       getSession().delete(entity);
/*    */     }
/* 38 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<PopularityGroup> getEntityClass()
/*    */   {
/* 43 */     return PopularityGroup.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.PopularityGroupDaoImpl
 * JD-Core Version:    0.6.0
 */