/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.StandardTypeDao;
/*    */ import com.jspgou.cms.entity.StandardType;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class StandardTypeDaoImpl extends HibernateBaseDao<StandardType, Long>
/*    */   implements StandardTypeDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 21 */     Finder f = Finder.create("from StandardType bean where 1=1");
/* 22 */     f.append(" order by bean.priority");
/* 23 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public StandardType getByField(String field)
/*    */   {
/* 28 */     return (StandardType)findUniqueByProperty("field", field);
/*    */   }
/*    */ 
/*    */   public StandardType findById(Long id)
/*    */   {
/* 33 */     StandardType entity = (StandardType)get(id);
/* 34 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<StandardType> getList()
/*    */   {
/* 40 */     Finder f = Finder.create("from StandardType bean where 1=1");
/* 41 */     f.append(" order by bean.priority");
/* 42 */     return find(f);
/*    */   }
/*    */ 
/*    */   public List<StandardType> getList(Integer categoryId)
/*    */   {
/* 48 */     Finder f = Finder.create("select bean from StandardType bean ");
/* 49 */     f.append(" inner join bean.categorys category");
/* 50 */     f.append(" where category.id=:categoryId").setParam("categoryId", categoryId);
/* 51 */     f.append(" order by bean.priority");
/* 52 */     return find(f);
/*    */   }
/*    */ 
/*    */   public StandardType save(StandardType bean)
/*    */   {
/* 58 */     getSession().save(bean);
/* 59 */     return bean;
/*    */   }
/*    */ 
/*    */   public StandardType deleteById(Long id)
/*    */   {
/* 64 */     StandardType entity = (StandardType)super.get(id);
/* 65 */     if (entity != null) {
/* 66 */       getSession().delete(entity);
/*    */     }
/* 68 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<StandardType> getEntityClass()
/*    */   {
/* 73 */     return StandardType.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.StandardTypeDaoImpl
 * JD-Core Version:    0.6.0
 */