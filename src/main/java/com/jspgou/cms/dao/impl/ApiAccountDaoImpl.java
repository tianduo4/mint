/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ApiAccountDao;
/*    */ import com.jspgou.cms.entity.ApiAccount;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ApiAccountDaoImpl extends HibernateBaseDao<ApiAccount, Long>
/*    */   implements ApiAccountDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 14 */     Criteria crit = createCriteria(new Criterion[0]);
/* 15 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 16 */     return page;
/*    */   }
/*    */ 
/*    */   public ApiAccount findById(Long id) {
/* 20 */     ApiAccount entity = (ApiAccount)get(id);
/* 21 */     return entity;
/*    */   }
/*    */ 
/*    */   public ApiAccount save(ApiAccount bean) {
/* 25 */     getSession().save(bean);
/* 26 */     return bean;
/*    */   }
/*    */ 
/*    */   public ApiAccount deleteById(Long id) {
/* 30 */     ApiAccount entity = (ApiAccount)super.get(id);
/* 31 */     if (entity != null) {
/* 32 */       getSession().delete(entity);
/*    */     }
/* 34 */     return entity;
/*    */   }
/*    */ 
/*    */   public ApiAccount findByAppId(String appId)
/*    */   {
/* 39 */     return (ApiAccount)findUniqueByProperty("appId", appId);
/*    */   }
/*    */ 
/*    */   protected Class<ApiAccount> getEntityClass()
/*    */   {
/* 45 */     return ApiAccount.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ApiAccountDaoImpl
 * JD-Core Version:    0.6.0
 */