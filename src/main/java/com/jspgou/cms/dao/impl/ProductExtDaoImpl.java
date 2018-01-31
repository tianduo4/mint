/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ProductExtDao;
/*    */ import com.jspgou.cms.entity.ProductExt;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ProductExtDaoImpl extends HibernateBaseDao<ProductExt, Long>
/*    */   implements ProductExtDao
/*    */ {
/*    */   public ProductExt findById(Long id)
/*    */   {
/* 19 */     ProductExt entity = (ProductExt)get(id);
/* 20 */     return entity;
/*    */   }
/*    */ 
/*    */   public ProductExt save(ProductExt bean)
/*    */   {
/* 25 */     getSession().save(bean);
/* 26 */     return bean;
/*    */   }
/*    */ 
/*    */   protected Class<ProductExt> getEntityClass()
/*    */   {
/* 31 */     return ProductExt.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ProductExtDaoImpl
 * JD-Core Version:    0.6.0
 */