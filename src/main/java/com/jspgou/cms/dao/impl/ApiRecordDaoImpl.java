/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ApiRecordDao;
/*    */ import com.jspgou.cms.entity.ApiRecord;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ApiRecordDaoImpl extends HibernateBaseDao<ApiRecord, Long>
/*    */   implements ApiRecordDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 15 */     Criteria crit = createCriteria(new Criterion[0]);
/* 16 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 17 */     return page;
/*    */   }
/*    */ 
/*    */   public ApiRecord findById(Long id) {
/* 21 */     ApiRecord entity = (ApiRecord)get(id);
/* 22 */     return entity;
/*    */   }
/*    */ 
/*    */   public ApiRecord save(ApiRecord bean) {
/* 26 */     getSession().save(bean);
/* 27 */     return bean;
/*    */   }
/*    */ 
/*    */   public ApiRecord deleteById(Long id) {
/* 31 */     ApiRecord entity = (ApiRecord)super.get(id);
/* 32 */     if (entity != null) {
/* 33 */       getSession().delete(entity);
/*    */     }
/* 35 */     return entity;
/*    */   }
/*    */ 
/*    */   public ApiRecord findBySign(String sign, String appId)
/*    */   {
/* 40 */     String hql = "from ApiRecord bean where bean.sign=? and bean.apiAccount.appId=?";
/* 41 */     Query query = getSession().createQuery(hql);
/* 42 */     query.setParameter(0, sign).setParameter(1, appId);
/*    */ 
/* 44 */     query.setMaxResults(1);
/* 45 */     return (ApiRecord)query.uniqueResult();
/*    */   }
/*    */ 
/*    */   protected Class<ApiRecord> getEntityClass()
/*    */   {
/* 51 */     return ApiRecord.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ApiRecordDaoImpl
 * JD-Core Version:    0.6.0
 */