/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.PosterDao;
/*    */ import com.jspgou.cms.entity.Poster;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import java.util.List;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class PosterDaoImpl extends HibernateBaseDao<Poster, Integer>
/*    */   implements PosterDao
/*    */ {
/*    */   public Poster findById(Integer id)
/*    */   {
/* 23 */     Poster entity = (Poster)get(id);
/* 24 */     return entity;
/*    */   }
/*    */ 
/*    */   public Poster saveOrUpdate(Poster bean)
/*    */   {
/* 29 */     getSession().saveOrUpdate(bean);
/* 30 */     return bean;
/*    */   }
/*    */ 
/*    */   public Poster update(Poster bean)
/*    */   {
/* 35 */     getSession().update(bean);
/* 36 */     return bean;
/*    */   }
/*    */ 
/*    */   public List<Poster> getPage()
/*    */   {
/* 42 */     return getSession().createQuery("from Poster bean order by bean.time desc").list();
/*    */   }
/*    */ 
/*    */   public Poster deleteById(Integer id)
/*    */   {
/* 47 */     Poster entity = (Poster)super.get(id);
/* 48 */     if (entity != null) {
/* 49 */       getSession().delete(entity);
/*    */     }
/* 51 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Poster> getEntityClass()
/*    */   {
/* 56 */     return Poster.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.PosterDaoImpl
 * JD-Core Version:    0.6.0
 */