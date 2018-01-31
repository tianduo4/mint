/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.WebserviceCallRecordDao;
/*    */ import com.jspgou.cms.entity.WebserviceCallRecord;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class WebserviceCallRecordDaoImpl extends HibernateBaseDao<WebserviceCallRecord, Integer>
/*    */   implements WebserviceCallRecordDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 15 */     Criteria crit = createCriteria(new Criterion[0]);
/* 16 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 17 */     return page;
/*    */   }
/*    */ 
/*    */   public WebserviceCallRecord findById(Integer id)
/*    */   {
/* 22 */     WebserviceCallRecord entity = (WebserviceCallRecord)get(id);
/* 23 */     return entity;
/*    */   }
/*    */ 
/*    */   public WebserviceCallRecord save(WebserviceCallRecord bean)
/*    */   {
/* 28 */     getSession().save(bean);
/* 29 */     return bean;
/*    */   }
/*    */ 
/*    */   public WebserviceCallRecord deleteById(Integer id)
/*    */   {
/* 34 */     WebserviceCallRecord entity = (WebserviceCallRecord)super.get(id);
/* 35 */     if (entity != null) {
/* 36 */       getSession().delete(entity);
/*    */     }
/* 38 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<WebserviceCallRecord> getEntityClass()
/*    */   {
/* 43 */     return WebserviceCallRecord.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.WebserviceCallRecordDaoImpl
 * JD-Core Version:    0.6.0
 */