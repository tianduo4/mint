/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ProductTypeDao;
/*    */ import com.jspgou.cms.entity.ProductType;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Session;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ProductTypeDaoImpl extends HibernateBaseDao<ProductType, Long>
/*    */   implements ProductTypeDao
/*    */ {
/*    */   public List<ProductType> getList(Long webId)
/*    */   {
/* 23 */     String hql = "from ProductType bean where bean.website.id=?";
/* 24 */     return find(hql, new Object[] { webId });
/*    */   }
/*    */ 
/*    */   public Pagination getPage(Long webId, int pageNo, int pageSize)
/*    */   {
/* 29 */     Finder f = 
/* 30 */       Finder.create("from ProductType bean where bean.website.id=:webId  order by bean.id desc");
/* 31 */     f.setParam("webId", webId);
/* 32 */     return find(f, pageNo, pageSize);
/*    */   }
/*    */ 
/*    */   public ProductType findById(Long id)
/*    */   {
/* 37 */     ProductType entity = (ProductType)get(id);
/* 38 */     return entity;
/*    */   }
/*    */ 
/*    */   public ProductType save(ProductType bean)
/*    */   {
/* 43 */     getSession().save(bean);
/* 44 */     return bean;
/*    */   }
/*    */ 
/*    */   public ProductType deleteById(Long id)
/*    */   {
/* 49 */     ProductType entity = (ProductType)super.get(id);
/* 50 */     if (entity != null) {
/* 51 */       getSession().delete(entity);
/*    */     }
/* 53 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ProductType> getEntityClass()
/*    */   {
/* 58 */     return ProductType.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ProductTypeDaoImpl
 * JD-Core Version:    0.6.0
 */