/*    */ package com.jspgou.core.dao.impl;
/*    */ 
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.core.dao.GlobalDao;
/*    */ import com.jspgou.core.entity.Global;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class GlobalDaoImpl extends HibernateBaseDao<Global, Long>
/*    */   implements GlobalDao
/*    */ {
/*    */   public Global findById(Long id)
/*    */   {
/* 18 */     Global global = (Global)get(id);
/* 19 */     return global;
/*    */   }
/*    */ 
/*    */   public Global update(Global bean)
/*    */   {
/* 24 */     getSession().update(bean);
/* 25 */     return bean;
/*    */   }
/*    */ 
/*    */   protected Class<Global> getEntityClass()
/*    */   {
/* 30 */     return Global.class;
/*    */   }
/*    */ 
/*    */   public Global findIdkey() {
/* 34 */     String hql = "from Global bean where 1=1";
/* 35 */     Query query = getSession().createQuery(hql);
/* 36 */     query.setMaxResults(1);
/* 37 */     return (Global)query.uniqueResult();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.impl.GlobalDaoImpl
 * JD-Core Version:    0.6.0
 */