/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ExendedDao;
/*    */ import com.jspgou.cms.entity.Exended;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ExendedDaoImpl extends HibernateBaseDao<Exended, Long>
/*    */   implements ExendedDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 21 */     Finder f = Finder.create("from Exended bean where 1=1");
/* 22 */     f.append(" order by bean.priority");
/* 23 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public Exended findById(Long id)
/*    */   {
/* 28 */     Exended entity = (Exended)get(id);
/* 29 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<Exended> getList()
/*    */   {
/* 35 */     Finder f = Finder.create("from Exended bean where 1=1");
/* 36 */     f.append(" order by bean.priority");
/* 37 */     return find(f);
/*    */   }
/*    */ 
/*    */   public List<Exended> getList(Long typeId)
/*    */   {
/* 43 */     Finder f = Finder.create("select bean from Exended bean");
/* 44 */     if (typeId != null) {
/* 45 */       f.append(" inner join bean.productTypes ptype ");
/* 46 */       f.append(" where ptype.id=:typeId").setParam("typeId", typeId);
/*    */     }
/* 48 */     f.append(" order by bean.priority");
/* 49 */     return find(f);
/*    */   }
/*    */ 
/*    */   public Exended save(Exended bean)
/*    */   {
/* 55 */     getSession().save(bean);
/* 56 */     return bean;
/*    */   }
/*    */ 
/*    */   public Exended deleteById(Long id)
/*    */   {
/* 61 */     Exended entity = (Exended)super.get(id);
/* 62 */     if (entity != null) {
/* 63 */       getSession().delete(entity);
/*    */     }
/* 65 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<Exended> getEntityClass()
/*    */   {
/* 70 */     return Exended.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ExendedDaoImpl
 * JD-Core Version:    0.6.0
 */