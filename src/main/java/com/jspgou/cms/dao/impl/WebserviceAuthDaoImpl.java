/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.WebserviceAuthDao;
/*    */ import com.jspgou.cms.entity.WebserviceAuth;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class WebserviceAuthDaoImpl extends HibernateBaseDao<WebserviceAuth, Integer>
/*    */   implements WebserviceAuthDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 18 */     Criteria crit = createCriteria(new Criterion[0]);
/* 19 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 20 */     return page;
/*    */   }
/*    */ 
/*    */   public WebserviceAuth findByUsername(String username)
/*    */   {
/* 25 */     String hql = "from WebserviceAuth bean where bean.username=:username";
/* 26 */     Finder f = Finder.create(hql).setParam("username", username);
/* 27 */     f.setCacheable(true);
/* 28 */     List list = find(f);
/* 29 */     if (list.size() > 0) {
/* 30 */       return (WebserviceAuth)list.get(0);
/*    */     }
/* 32 */     return null;
/*    */   }
/*    */ 
/*    */   public WebserviceAuth findById(Integer id)
/*    */   {
/* 38 */     WebserviceAuth entity = (WebserviceAuth)get(id);
/* 39 */     return entity;
/*    */   }
/*    */ 
/*    */   public WebserviceAuth save(WebserviceAuth bean)
/*    */   {
/* 44 */     getSession().save(bean);
/* 45 */     return bean;
/*    */   }
/*    */ 
/*    */   public WebserviceAuth deleteById(Integer id)
/*    */   {
/* 50 */     WebserviceAuth entity = (WebserviceAuth)super.get(id);
/* 51 */     if (entity != null) {
/* 52 */       getSession().delete(entity);
/*    */     }
/* 54 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<WebserviceAuth> getEntityClass()
/*    */   {
/* 59 */     return WebserviceAuth.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.WebserviceAuthDaoImpl
 * JD-Core Version:    0.6.0
 */