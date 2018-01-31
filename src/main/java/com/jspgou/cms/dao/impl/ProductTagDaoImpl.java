/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ProductTagDao;
/*    */ import com.jspgou.cms.entity.ProductTag;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ProductTagDaoImpl extends HibernateBaseDao<ProductTag, Long>
/*    */   implements ProductTagDao
/*    */ {
/*    */   public List<ProductTag> getList(Long webId)
/*    */   {
/* 21 */     String hql = "from ProductTag bean where bean.website.id=?";
/* 22 */     return find(hql, new Object[] { webId });
/*    */   }
/*    */ 
/*    */   public ProductTag findById(Long id)
/*    */   {
/* 27 */     ProductTag entity = (ProductTag)get(id);
/* 28 */     return entity;
/*    */   }
/*    */ 
/*    */   public ProductTag save(ProductTag bean)
/*    */   {
/* 33 */     getSession().save(bean);
/* 34 */     return bean;
/*    */   }
/*    */ 
/*    */   public ProductTag deleteById(Long id)
/*    */   {
/* 39 */     ProductTag entity = (ProductTag)super.get(id);
/* 40 */     if (entity != null) {
/* 41 */       getSession().delete(entity);
/*    */     }
/* 43 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ProductTag> getEntityClass()
/*    */   {
/* 48 */     return ProductTag.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ProductTagDaoImpl
 * JD-Core Version:    0.6.0
 */