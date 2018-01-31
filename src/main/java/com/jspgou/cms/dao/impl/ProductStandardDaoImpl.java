/*    */ package com.jspgou.cms.dao.impl;
/*    */ 
/*    */ import com.jspgou.cms.dao.ProductStandardDao;
/*    */ import com.jspgou.cms.entity.ProductStandard;
/*    */ import com.jspgou.common.hibernate4.Finder;
/*    */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import java.util.List;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.Session;
/*    */ import org.hibernate.criterion.Criterion;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository
/*    */ public class ProductStandardDaoImpl extends HibernateBaseDao<ProductStandard, Long>
/*    */   implements ProductStandardDao
/*    */ {
/*    */   public Pagination getPage(int pageNo, int pageSize)
/*    */   {
/* 22 */     Criteria crit = createCriteria(new Criterion[0]);
/* 23 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/* 24 */     return page;
/*    */   }
/*    */ 
/*    */   public ProductStandard findById(Long id)
/*    */   {
/* 29 */     ProductStandard entity = (ProductStandard)get(id);
/* 30 */     return entity;
/*    */   }
/*    */ 
/*    */   public List<ProductStandard> findByProductIdAndStandardId(Long productId, Long standardId)
/*    */   {
/* 36 */     Finder f = Finder.create("from ProductStandard bean where 1=1");
/* 37 */     f.append(" and bean.product.id=:productId").setParam("productId", productId);
/* 38 */     f.append(" and bean.standard.id=:standardId").setParam("standardId", standardId);
/* 39 */     return find(f);
/*    */   }
/*    */ 
/*    */   public List<ProductStandard> findByProductId(Long productId)
/*    */   {
/* 45 */     Finder f = Finder.create("from ProductStandard bean where 1=1");
/* 46 */     f.append(" and bean.product.id=:productId").setParam("productId", productId);
/* 47 */     return find(f);
/*    */   }
/*    */ 
/*    */   public ProductStandard save(ProductStandard bean)
/*    */   {
/* 52 */     getSession().save(bean);
/* 53 */     return bean;
/*    */   }
/*    */ 
/*    */   public ProductStandard deleteById(Long id)
/*    */   {
/* 58 */     ProductStandard entity = (ProductStandard)super.get(id);
/* 59 */     if (entity != null) {
/* 60 */       getSession().delete(entity);
/*    */     }
/* 62 */     return entity;
/*    */   }
/*    */ 
/*    */   protected Class<ProductStandard> getEntityClass()
/*    */   {
/* 67 */     return ProductStandard.class;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ProductStandardDaoImpl
 * JD-Core Version:    0.6.0
 */