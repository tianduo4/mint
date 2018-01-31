/*    */ package com.jspgou.core.dao.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.core.dao.LogDao;
/*    */ import com.jspgou.core.entity.Log;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class LogDaoImpl extends HibernateBaseDao<Log, Long>
/*    */   implements LogDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 18 */     Criteria crit = createCriteria(new Criterion[0]);
/* 19 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 20 */     return page;
/*    */   }
/*    */ 
/*    */   public Log findById(Long id)
/*    */   {
/* 25 */     Log entity = (Log)get(id);
/* 26 */     return entity;
/*    */   }
/*    */ 
/*    */   public Log save(Log bean)
/*    */   {
/* 31 */     getSession().save(bean);
/* 32 */     return bean;
/*    */   }
/*    */ 
/*    */   public Log deleteById(Long id)
/*    */   {
/* 37 */     Log entity = (Log)super.get(id);
/* 38 */     if (entity != null) {
/* 39 */       getSession().delete(entity);
/*    */     }
/* 41 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Log> getEntityClass()
/*    */   {
/* 46 */     return Log.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.impl.LogDaoImpl
 * JD-Core Version:    0.6.0
 */