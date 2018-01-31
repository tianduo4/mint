/*    */ package com.jspgou.core.dao.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.core.dao.AdminDao;
/*    */ import com.jspgou.core.entity.Admin;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class AdminDaoImpl extends HibernateBaseDao<Admin, Long>
/*    */   implements AdminDao
/*    */ {
/*    */   public Admin getByUsername(String username)
/*    */   {
/* 18 */     String f = "from Admin bean where bean.user.username=:username";
/* 19 */     return (Admin)getSession().createQuery(f).setParameter("username", username).uniqueResult();
/*    */   }
/*    */ 
/*    */   public Admin getByUserId(Long userId, Long webId)
/*    */   {
/* 24 */     String s = "from Admin bean where bean.user.id=:userId and bean.website.id=:webId";
/* 25 */     return (Admin)getSession().createQuery(s).setParameter("userId", userId).setParameter("webId", webId).uniqueResult();
/*    */   }
/*    */ 
/*    */   public Admin findById(Long id)
/*    */   {
/* 30 */     Admin entity = (Admin)get(id);
/* 31 */     return entity;
/*    */   }
/*    */ 
/*    */   public Admin save(Admin bean)
/*    */   {
/* 36 */     getSession().save(bean);
/* 37 */     return bean;
/*    */   }
/*    */ 
/*    */   public Admin deleteById(Long id)
/*    */   {
/* 42 */     Admin entity = (Admin)super.get(id);
/* 43 */     if (entity != null) {
/* 44 */       getSession().delete(entity);
/*    */     }
/* 46 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Admin> getEntityClass()
/*    */   {
/* 51 */     return Admin.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.impl.AdminDaoImpl
 * JD-Core Version:    0.6.0
 */