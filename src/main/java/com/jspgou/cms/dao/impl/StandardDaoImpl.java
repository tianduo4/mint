/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.StandardDao;
/*    */ import com.jspgou.cms.entity.Standard;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class StandardDaoImpl extends HibernateBaseDao<Standard, Long>
/*    */   implements StandardDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 21 */     Finder f = Finder.create("from Standard bean where 1=1 ");
/* 22 */     f.append(" order by bean.priority");
/* 23 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public Standard findById(Long id)
/*    */   {
/* 29 */     Standard entity = (Standard)get(id);
/* 30 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<Standard> findByTypeId(Long typeId)
/*    */   {
/* 36 */     String hql = "from Standard bean where 1=1";
/* 37 */     Finder f = Finder.create(hql);
/* 38 */     f.append(" and bean.type.id=:typeId").setParam("typeId", typeId);
/* 39 */     f.append(" order by bean.priority");
/* 40 */     return find(f);
/*    */   }
/*    */ 
/*    */   public Standard save(Standard bean)
/*    */   {
/* 45 */     getSession().save(bean);
/* 46 */     return bean;
/*    */   }
/*    */ 
/*    */   public Standard deleteById(Long id)
/*    */   {
/* 51 */     Standard entity = (Standard)super.get(id);
/* 52 */     if (entity != null) {
/* 53 */       getSession().delete(entity);
/*    */     }
/* 55 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Standard> getEntityClass()
/*    */   {
/* 60 */     return Standard.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.StandardDaoImpl
 * JD-Core Version:    0.6.0
 */