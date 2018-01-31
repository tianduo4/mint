/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.AdspaceDao;
/*    */ import com.jspgou.cms.entity.Adspace;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import java.util.List;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class AdspaceDaoImpl extends HibernateBaseDao<Adspace, Integer>
/*    */   implements AdspaceDao
/*    */ {
/*    */   public Adspace findById(Integer id)
/*    */   {
/* 21 */     Adspace entity = (Adspace)get(id);
/* 22 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<Adspace> getList()
/*    */   {
/* 28 */     return getSession().createQuery("from Adspace bean ").list();
/*    */   }
/*    */ 
/*    */   public Adspace save(Adspace bean)
/*    */   {
/* 33 */     getSession().save(bean);
/* 34 */     return bean;
/*    */   }
/*    */ 
/*    */   public Adspace deleteById(Integer id)
/*    */   {
/* 39 */     Adspace entity = (Adspace)super.get(id);
/* 40 */     if (entity != null) {
/* 41 */       getSession().delete(entity);
/*    */     }
/* 43 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Adspace> getEntityClass()
/*    */   {
/* 48 */     return Adspace.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.AdspaceDaoImpl
 * JD-Core Version:    0.6.0
 */