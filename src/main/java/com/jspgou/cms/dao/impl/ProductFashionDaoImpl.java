/*     */ package com.jspgou.cms.dao.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.ProductFashionDao;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.common.hibernate4.Finder;
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class ProductFashionDaoImpl extends HibernateBaseDao<ProductFashion, Long>
/*     */   implements ProductFashionDao
/*     */ {
/*     */   protected Class<ProductFashion> getEntityClass()
/*     */   {
/*  24 */     return ProductFashion.class;
/*     */   }
/*     */ 
/*     */   public ProductFashion deleteById(Long id)
/*     */   {
/*  29 */     ProductFashion entity = (ProductFashion)super.get(id);
/*  30 */     if (entity != null) {
/*  31 */       getSession().delete(entity);
/*     */     }
/*  33 */     return entity;
/*     */   }
/*     */ 
/*     */   public ProductFashion findById(Long id)
/*     */   {
/*  38 */     ProductFashion entity = (ProductFashion)get(id);
/*  39 */     return entity;
/*     */   }
/*     */ 
/*     */   public List<ProductFashion> findByProductId(Long productId)
/*     */   {
/*  45 */     Finder f = Finder.create(" from ProductFashion bean where bean.productId.id=:productId ");
/*  46 */     f.setParam("productId", productId);
/*  47 */     f.setCacheable(true);
/*  48 */     return find(f);
/*     */   }
/*     */ 
/*     */   public Pagination getPage(Long productId, int pageNo, int pageSize)
/*     */   {
/*  53 */     Finder f = Finder.create(" from ProductFashion bean where bean.productId.id=:productId ");
/*  54 */     f.setParam("productId", productId);
/*  55 */     f.setCacheable(true);
/*  56 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public ProductFashion save(ProductFashion bean)
/*     */   {
/*  61 */     getSession().save(bean);
/*  62 */     return bean;
/*     */   }
/*     */ 
/*     */   public Pagination productLack(Integer status, Integer count, int pageNo, int pageSize)
/*     */   {
/*  67 */     Finder f = Finder.create("from ProductFashion bean where bean.lackRemind=:status ");
/*  68 */     f.setParam("status", status);
/*  69 */     if (count == null) {
/*  70 */       count = Integer.valueOf(5);
/*     */     }
/*  72 */     f.append(" and bean.stockCount < :count").setParam("count", count);
/*  73 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Integer productLackCount(Integer status, Integer count)
/*     */   {
/*  78 */     String hql = " select count(bean) from ProductFashion bean where bean.lackRemind=:status ";
/*  79 */     if (count == null) {
/*  80 */       count = Integer.valueOf(5);
/*     */     }
/*  82 */     hql = hql + " and bean.stockCount < :count";
/*  83 */     Iterator ite = getSession().createQuery(hql).setInteger("count", count.intValue()).setInteger("status", status.intValue()).iterate();
/*  84 */     Integer result = Integer.valueOf(0);
/*  85 */     if (ite.hasNext()) {
/*  86 */       result = Integer.valueOf(Integer.parseInt(ite.next()));
/*     */     }
/*  88 */     return result;
/*     */   }
/*     */ 
/*     */   public Pagination getSaleTopPage(int pageNo, int pageSize)
/*     */   {
/*  94 */     Finder f = Finder.create(" from ProductFashion bean order by bean.saleCount desc");
/*  95 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public ProductFashion getPfashion(Long productId, Long fashId)
/*     */   {
/* 100 */     return (ProductFashion)getSession().createQuery("from ProductFashion bean where bean.productId.id=:pid and bean.id=:fid")
/* 101 */       .setParameter("pid", productId).setParameter("fid", fashId).uniqueResult();
/*     */   }
/*     */ 
/*     */   public Boolean getOneFashion(Long productId)
/*     */   {
/* 106 */     Finder f = Finder.create("from ProductFashion bean where bean.productId.id=:id").setParam("id", productId);
/* 107 */     List l = find(f);
/* 108 */     if (l.size() <= 1) {
/* 109 */       return Boolean.valueOf(false);
/*     */     }
/* 111 */     return Boolean.valueOf(true);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.ProductFashionDaoImpl
 * JD-Core Version:    0.6.0
 */