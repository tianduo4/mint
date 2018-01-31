/*    */ package com.jspgou.core.dao.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.core.dao.UserDao;
/*    */ import com.jspgou.core.entity.User;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class UserDaoImpl extends HibernateBaseDao<User, Long>
/*    */   implements UserDao
/*    */ {
/*    */   public User getByUsername(String username)
/*    */   {
/* 20 */     return (User)findUniqueByProperty("username", username);
/*    */   }
/*    */ 
/*    */   public User getByEmail(String email)
/*    */   {
/* 25 */     return (User)findUniqueByProperty("email", email);
/*    */   }
/*    */ 
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 30 */     Criteria criteria = createCriteria(new Criterion[0]);
/* 31 */     Pagination page = findByCriteria(criteria, pageNo, pageSize);
/* 32 */     return page;
/*    */   }
/*    */ 
/*    */   public User findById(Long id)
/*    */   {
/* 37 */     User entity = (User)get(id);
/* 38 */     return entity;
/*    */   }
/*    */ 
/*    */   public User save(User bean)
/*    */   {
/* 43 */     getSession().save(bean);
/* 44 */     return bean;
/*    */   }
/*    */ 
/*    */   public User deleteById(Long id)
/*    */   {
/* 49 */     User entity = (User)super.get(id);
/* 50 */     if (entity != null)
/* 51 */       getSession().delete(entity);
/* 52 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<User> getEntityClass()
/*    */   {
/* 57 */     return User.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.impl.UserDaoImpl
 * JD-Core Version:    0.6.0
 */