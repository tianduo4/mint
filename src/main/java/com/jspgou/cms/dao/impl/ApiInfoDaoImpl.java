/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ApiInfoDao;
/*    */ import com.jspgou.cms.entity.ApiInfo;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ApiInfoDaoImpl extends HibernateBaseDao<ApiInfo, Long>
/*    */   implements ApiInfoDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 14 */     Criteria crit = createCriteria(new Criterion[0]);
/* 15 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 16 */     return page;
/*    */   }
/*    */ 
/*    */   public ApiInfo findById(Long id) {
/* 20 */     ApiInfo entity = (ApiInfo)get(id);
/* 21 */     return entity;
/*    */   }
/*    */ 
/*    */   public ApiInfo findByApiUrl(String apiUrl)
/*    */   {
/* 26 */     return (ApiInfo)findUniqueByProperty("apiUrl", apiUrl);
/*    */   }
/*    */ 
/*    */   public ApiInfo save(ApiInfo bean) {
/* 30 */     getSession().save(bean);
/* 31 */     return bean;
/*    */   }
/*    */ 
/*    */   public ApiInfo deleteById(Long id) {
/* 35 */     ApiInfo entity = (ApiInfo)super.get(id);
/* 36 */     if (entity != null) {
/* 37 */       getSession().delete(entity);
/*    */     }
/* 39 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ApiInfo> getEntityClass()
/*    */   {
/* 44 */     return ApiInfo.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ApiInfoDaoImpl
 * JD-Core Version:    0.6.0
 */