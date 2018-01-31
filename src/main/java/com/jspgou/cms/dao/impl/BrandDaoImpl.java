/*     */ package com.jspgou.cms.dao.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.BrandDao;
/*     */ import com.jspgou.cms.entity.Brand;
/*     */ import com.jspgou.cms.entity.base.BaseBrand;
/*     */ import com.jspgou.common.hibernate4.Finder;
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.hibernate.Criteria;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.criterion.Criterion;
/*     */ import org.hibernate.criterion.Order;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class BrandDaoImpl extends HibernateBaseDao<Brand, Long>
/*     */   implements BrandDao
/*     */ {
/*     */   public List<Brand> getAllList()
/*     */   {
/*  28 */     Criteria crit = createCriteria(new Criterion[0]);
/*  29 */     crit.addOrder(Order.asc(BaseBrand.PROP_PRIORITY));
/*  30 */     List list = crit.list();
/*  31 */     return list;
/*     */   }
/*     */ 
/*     */   public List<Brand> getList(Long webId, int firstResult, int maxResults, boolean cacheable)
/*     */   {
/*  38 */     Finder f = Finder.create("select bean from Brand bean where bean.website.id=:webId order by bean.priority");
/*  39 */     f.setParam("webId", webId);
/*  40 */     f.setFirstResult(firstResult);
/*  41 */     f.setMaxResults(maxResults);
/*  42 */     f.setCacheable(cacheable);
/*  43 */     return find(f);
/*     */   }
/*     */ 
/*     */   public List<Brand> getList()
/*     */   {
/*  50 */     Finder f = Finder.create("from Brand bean where bean.disabled=false");
/*  51 */     f.append(" order by bean.priority");
/*  52 */     return find(f);
/*     */   }
/*     */ 
/*     */   public List<Brand> getListByCate(Integer categoryId)
/*     */   {
/*  58 */     Finder f = Finder.create("select bean from Brand bean");
/*  59 */     if (categoryId != null) {
/*  60 */       f.append(" join bean.categorys cate where cate.id=:categoryId");
/*  61 */       f.setParam("categoryId", categoryId);
/*     */     }
/*  63 */     return find(f);
/*     */   }
/*     */ 
/*     */   public Brand getsiftBrand()
/*     */   {
/*  68 */     return (Brand)getSession().createQuery("from Brand bean where bean.sift=true order by bean.id desc")
/*  69 */       .setMaxResults(1).uniqueResult();
/*     */   }
/*     */ 
/*     */   public Brand findById(Long id)
/*     */   {
/*  74 */     Brand entity = (Brand)get(id);
/*  75 */     return entity;
/*     */   }
/*     */ 
/*     */   public Brand save(Brand bean)
/*     */   {
/*  80 */     getSession().save(bean);
/*  81 */     return bean;
/*     */   }
/*     */ 
/*     */   public Brand deleteById(Long id)
/*     */   {
/*  86 */     Brand entity = (Brand)super.get(id);
/*  87 */     if (entity != null) {
/*  88 */       getSession().delete(entity);
/*     */     }
/*  90 */     return entity;
/*     */   }
/*     */ 
/*     */   public int countByBrandName(String brandName)
/*     */   {
/*  95 */     String hql = "select count(*) from Brand bean where bean.name=:brandName";
/*  96 */     Query query = getSession().createQuery(hql);
/*  97 */     query.setParameter("brandName", brandName);
/*  98 */     return ((Number)query.iterate().next()).intValue();
/*     */   }
/*     */ 
/*     */   protected Class<Brand> getEntityClass()
/*     */   {
/* 103 */     return Brand.class;
/*     */   }
/*     */ 
/*     */   public Pagination getPage(String name, String brandtype, int pageNo, int pageSize)
/*     */   {
/* 108 */     Finder f = Finder.create("from Brand bean where 1=1");
/* 109 */     if (name != null) {
/* 110 */       f.append(" and bean.name like :name");
/* 111 */       f.setParam("name", "%" + name + "%");
/*     */     }
/* 113 */     if (brandtype != null) {
/* 114 */       f.append(" and bean.brandtype like :brandtype");
/* 115 */       f.setParam("brandtype", "%" + brandtype + "%");
/*     */     }
/* 117 */     f.append(" order by bean.priority asc, bean.id asc");
/* 118 */     f.setCacheable(true);
/* 119 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public List<Brand> getBrandList(String name)
/*     */   {
/* 124 */     Finder f = Finder.create("from Brand bean where 1=1");
/* 125 */     if (name != null) {
/* 126 */       f.append(" and bean.name like :name");
/* 127 */       f.setParam("name", "%" + name + "%");
/*     */     }
/* 129 */     return find(f);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.BrandDaoImpl
 * JD-Core Version:    0.6.0
 */