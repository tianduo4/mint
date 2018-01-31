/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ApiUserLoginDao;
/*    */ import com.jspgou.cms.entity.ApiUserLogin;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ApiUserLoginDaoImpl extends HibernateBaseDao<ApiUserLogin, Long>
/*    */   implements ApiUserLoginDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 18 */     Criteria crit = createCriteria(new Criterion[0]);
/* 19 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 20 */     return page;
/*    */   }
/*    */ 
/*    */   public ApiUserLogin findById(Long id) {
/* 24 */     ApiUserLogin entity = (ApiUserLogin)get(id);
/* 25 */     return entity;
/*    */   }
/*    */ 
/*    */   public ApiUserLogin save(ApiUserLogin bean) {
/* 29 */     getSession().save(bean);
/* 30 */     return bean;
/*    */   }
/*    */ 
/*    */   public ApiUserLogin deleteById(Long id) {
/* 34 */     ApiUserLogin entity = (ApiUserLogin)super.get(id);
/* 35 */     if (entity != null) {
/* 36 */       getSession().delete(entity);
/*    */     }
/* 38 */     return entity;
/*    */   }
/*    */ 
/*    */   public ApiUserLogin findBySessionKey(String sessionKey) {
/* 42 */     return (ApiUserLogin)findUniqueByProperty("sessionKey", sessionKey);
/*    */   }
/*    */ 
/*    */   public ApiUserLogin findByUsername(String username)
/*    */   {
/* 47 */     return (ApiUserLogin)findUniqueByProperty("username", username);
/*    */   }
/*    */ 
/*    */   public ApiUserLogin findUserLogin(String username, String sessionKey) {
/* 51 */     String hql = "from ApiUserLogin bean where 1=1";
/* 52 */     Finder f = Finder.create(hql);
/* 53 */     if (StringUtils.isNotBlank(username)) {
/* 54 */       f.append(" and bean.username=:username").setParam("username", username);
/*    */     }
/* 56 */     if (StringUtils.isNotBlank(sessionKey)) {
/* 57 */       f.append(" and bean.sessionKey=:sessionKey").setParam("sessionKey", sessionKey);
/*    */     }
/* 59 */     List li = find(f);
/* 60 */     if (li.size() > 0) {
/* 61 */       return (ApiUserLogin)li.get(0);
/*    */     }
/* 63 */     return null;
/*    */   }
/*    */ 
/*    */   protected Class<ApiUserLogin> getEntityClass()
/*    */   {
/* 69 */     return ApiUserLogin.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ApiUserLoginDaoImpl
 * JD-Core Version:    0.6.0
 */