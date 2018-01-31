/*     */ package com.jspgou.core.dao.impl;
/*     */ 
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.core.dao.WebsiteDao;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.entity.base.BaseWebsite;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.hibernate.Criteria;
/*     */ import org.hibernate.Hibernate;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.criterion.Criterion;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class WebsiteDaoImpl extends HibernateBaseDao<Website, Long>
/*     */   implements WebsiteDao
/*     */ {
/*     */   public Website getUniqueWebsite()
/*     */   {
/*  23 */     String hql = "select bean from Website bean";
/*  24 */     return (Website)getSession().createQuery(hql).uniqueResult();
/*     */   }
/*     */ 
/*     */   public Website findByDomain(String s)
/*     */   {
/*  29 */     return (Website)findUniqueByProperty(BaseWebsite.PROP_DOMAIN, s);
/*     */   }
/*     */ 
/*     */   public int countWebsite()
/*     */   {
/*  34 */     String hql = "select count(*) from Website";
/*  35 */     return ((Number)getSession().createQuery(hql).iterate().next()).intValue();
/*     */   }
/*     */ 
/*     */   public Pagination getAllPage(int pageNo, int pageSize)
/*     */   {
/*  40 */     Criteria crit = createCriteria(new Criterion[0]);
/*  41 */     Pagination page = findByCriteria(crit, pageNo, pageSize);
/*  42 */     return page;
/*     */   }
/*     */ 
/*     */   public List<Website> getAllList()
/*     */   {
/*  48 */     Criteria crit = createCriteria(new Criterion[0]);
/*  49 */     List list = crit.list();
/*  50 */     return list;
/*     */   }
/*     */ 
/*     */   public Website findById(Long id)
/*     */   {
/*  55 */     Website entity = (Website)get(id);
/*  56 */     Hibernate.initialize(entity);
/*  57 */     return entity;
/*     */   }
/*     */ 
/*     */   public Website findById1(Long id) {
/*  61 */     Website entity = (Website)get(id);
/*  62 */     return entity;
/*     */   }
/*     */ 
/*     */   public Website save(Website bean)
/*     */   {
/*  67 */     getSession().save(bean);
/*  68 */     return bean;
/*     */   }
/*     */ 
/*     */   public Website deleteById(Long id)
/*     */   {
/*  73 */     Website entity = (Website)super.get(id);
/*  74 */     if (entity != null) {
/*  75 */       getSession().delete(entity);
/*     */     }
/*  77 */     return entity;
/*     */   }
/*     */ 
/*     */   public Website findByDomain(String domain, boolean cacheable) {
/*  81 */     String hql = "from Website bean where domain=:domain";
/*  82 */     Query query = getSession().createQuery(hql).setString("domain", domain);
/*  83 */     query.setCacheable(cacheable);
/*  84 */     return (Website)query.uniqueResult();
/*     */   }
/*     */ 
/*     */   protected Class<Website> getEntityClass()
/*     */   {
/*  90 */     return Website.class;
/*     */   }
/*     */ 
/*     */   public List<Website> getList()
/*     */   {
/*  95 */     String hql = "from Website";
/*  96 */     return find(hql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List<Website> getList(boolean cacheable)
/*     */   {
/* 101 */     String hql = "from Website bean order by bean.id asc";
/* 102 */     return getSession().createQuery(hql).setCacheable(cacheable).list();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.dao.impl.WebsiteDaoImpl
 * JD-Core Version:    0.6.0
 */