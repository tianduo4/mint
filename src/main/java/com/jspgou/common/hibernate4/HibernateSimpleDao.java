/*     */ package com.jspgou.common.hibernate4;
/*     */ 
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.util.MyBeanUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.hibernate.Criteria;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.SessionFactory;
/*     */ import org.hibernate.criterion.CriteriaSpecification;
/*     */ import org.hibernate.criterion.Projection;
/*     */ import org.hibernate.criterion.Projections;
/*     */ import org.hibernate.internal.CriteriaImpl;
/*     */ import org.hibernate.transform.ResultTransformer;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ public abstract class HibernateSimpleDao
/*     */ {
/*  30 */   protected Logger log = LoggerFactory.getLogger(getClass());
/*     */   protected static final String ORDER_ENTRIES = "orderEntries";
/*     */   protected SessionFactory sessionFactory;
/*     */ 
/*     */   protected List find(String hql, Object[] values)
/*     */   {
/*  46 */     return createQuery(hql, values).list();
/*     */   }
/*     */ 
/*     */   protected Object findUnique(String hql, Object[] values)
/*     */   {
/*  53 */     return createQuery(hql, values).uniqueResult();
/*     */   }
/*     */ 
/*     */   protected Pagination find(Finder finder, int pageNo, int pageSize)
/*     */   {
/*  69 */     int totalCount = countQueryResult(finder);
/*  70 */     Pagination p = new Pagination(pageNo, pageSize, totalCount);
/*  71 */     if (totalCount < 1) {
/*  72 */       p.setList(new ArrayList());
/*  73 */       return p;
/*     */     }
/*  75 */     Query query = getSession().createQuery(finder.getOrigHql());
/*  76 */     finder.setParamsToQuery(query);
/*  77 */     query.setFirstResult(p.getFirstResult());
/*  78 */     query.setMaxResults(p.getPageSize());
/*  79 */     if (finder.isCacheable()) {
/*  80 */       query.setCacheable(true);
/*     */     }
/*  82 */     List list = query.list();
/*  83 */     p.setList(list);
/*  84 */     return p;
/*     */   }
/*     */ 
/*     */   protected Pagination findBigDataPage(Finder finder, int pageNo, int pageSize) {
/*  88 */     int totalCount = countQueryResult(finder);
/*  89 */     Pagination p = new Pagination(pageNo, pageSize, totalCount);
/*  90 */     if (totalCount < 1) {
/*  91 */       p.setList(new ArrayList());
/*  92 */       return p;
/*     */     }
/*  94 */     Query query = getSession().createQuery(finder.getOrigHql());
/*  95 */     finder.setParamsToQuery(query);
/*  96 */     query.setFirstResult(p.getFirstResult());
/*  97 */     query.setMaxResults(p.getPageSize());
/*  98 */     if (finder.isCacheable()) {
/*  99 */       query.setCacheable(true);
/*     */     }
/* 101 */     return p;
/*     */   }
/*     */ 
/*     */   protected Pagination findBigData(Finder finder, int pageNo, int pageSize) {
/* 105 */     int totalCount = pageNo * pageSize;
/* 106 */     Pagination p = new Pagination(pageNo, pageSize, totalCount);
/* 107 */     Query query = getSession().createQuery(finder.getOrigHql());
/* 108 */     finder.setParamsToQuery(query);
/* 109 */     query.setFirstResult(p.getFirstResult());
/* 110 */     query.setMaxResults(p.getPageSize());
/* 111 */     if (finder.isCacheable()) {
/* 112 */       query.setCacheable(true);
/*     */     }
/* 114 */     List list = query.list();
/* 115 */     p.setList(list);
/* 116 */     return p;
/*     */   }
/*     */ 
/*     */   protected Pagination findByGroup(Finder finder, String selectSql, int pageNo, int pageSize) {
/* 120 */     return findByTotalCount(finder, pageNo, pageSize, countQueryResultByGroup(finder, selectSql));
/*     */   }
/*     */ 
/*     */   protected List find(Finder finder)
/*     */   {
/* 131 */     Query query = finder.createQuery(getSession());
/* 132 */     List list = query.list();
/* 133 */     return list;
/*     */   }
/*     */ 
/*     */   protected Query createQuery(String queryString, Object[] values)
/*     */   {
/* 140 */     Assert.hasText(queryString);
/* 141 */     Query queryObject = getSession().createQuery(queryString);
/* 142 */     if (values != null) {
/* 143 */       for (int i = 0; i < values.length; i++) {
/* 144 */         queryObject.setParameter(i, values[i]);
/*     */       }
/*     */     }
/* 147 */     return queryObject;
/*     */   }
/*     */ 
/*     */   protected Pagination findByCriteria(Criteria crit, int pageNo, int pageSize)
/*     */   {
/* 162 */     CriteriaImpl impl = (CriteriaImpl)crit;
/*     */ 
/* 164 */     Projection projection = impl.getProjection();
/* 165 */     ResultTransformer transformer = impl.getResultTransformer();
/*     */     try
/*     */     {
/* 168 */       List orderEntries = (List)
/* 169 */         MyBeanUtils.getFieldValue(impl, "orderEntries");
/* 170 */       MyBeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
/*     */     } catch (Exception e) {
/* 172 */       throw new RuntimeException(
/* 173 */         "cannot read/write 'orderEntries' from CriteriaImpl", e);
/*     */     }
/*     */     List orderEntries;
/* 176 */     int totalCount = ((Number)crit.setProjection(Projections.rowCount())
/* 177 */       .uniqueResult()).intValue();
/* 178 */     Pagination p = new Pagination(pageNo, pageSize, totalCount);
/* 179 */     if (totalCount < 1) {
/* 180 */       p.setList(new ArrayList());
/* 181 */       return p;
/*     */     }
/*     */ 
/* 185 */     crit.setProjection(projection);
/* 186 */     if (projection == null) {
/* 187 */       crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
/*     */     }
/* 189 */     if (transformer != null)
/* 190 */       crit.setResultTransformer(transformer);
/*     */     try
/*     */     {
/* 193 */       MyBeanUtils.setFieldValue(impl, "orderEntries", orderEntries);
/*     */     } catch (Exception e) {
/* 195 */       throw new RuntimeException(
/* 196 */         "set 'orderEntries' to CriteriaImpl faild", e);
/*     */     }
/* 198 */     crit.setFirstResult(p.getFirstResult());
/* 199 */     crit.setMaxResults(p.getPageSize());
/* 200 */     p.setList(crit.list());
/* 201 */     return p;
/*     */   }
/*     */ 
/*     */   protected int countQueryResult(Finder finder)
/*     */   {
/* 211 */     Query query = getSession().createQuery(finder.getRowCountHql());
/* 212 */     finder.setParamsToQuery(query);
/* 213 */     if (finder.isCacheable()) {
/* 214 */       query.setCacheable(true);
/*     */     }
/* 216 */     return ((Number)query.iterate().next()).intValue();
/*     */   }
/*     */ 
/*     */   protected int countQueryResultByGroup(Finder finder, String selectSql) {
/* 220 */     Query query = getSession().createQuery(finder.getRowCountTotalHql(selectSql));
/* 221 */     setParamsToQuery(finder, query);
/* 222 */     return ((Number)query.iterate().next()).intValue();
/*     */   }
/*     */ 
/*     */   private Pagination findByTotalCount(Finder finder, int pageNo, int pageSize, int totalCount) {
/* 226 */     Pagination p = new Pagination(pageNo, pageSize, totalCount);
/* 227 */     if (totalCount < 1) {
/* 228 */       p.setList(new ArrayList());
/* 229 */       return p;
/*     */     }
/* 231 */     Query query = getSession().createQuery(finder.getOrigHql());
/* 232 */     finder.setParamsToQuery(query);
/* 233 */     query.setFirstResult(p.getFirstResult());
/* 234 */     query.setMaxResults(p.getPageSize());
/* 235 */     if (finder.isCacheable()) {
/* 236 */       query.setCacheable(true);
/*     */     }
/* 238 */     List list = query.list();
/* 239 */     p.setList(list);
/* 240 */     return p;
/*     */   }
/*     */ 
/*     */   private Query setParamsToQuery(Finder finder, Query query) {
/* 244 */     finder.setParamsToQuery(query);
/* 245 */     if (finder.isCacheable()) {
/* 246 */       query.setCacheable(true);
/*     */     }
/* 248 */     return query;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setSessionFactory(SessionFactory sessionfactory)
/*     */   {
/* 255 */     this.sessionFactory = sessionfactory;
/*     */   }
/*     */ 
/*     */   protected Session getSession() {
/* 259 */     return this.sessionFactory.getCurrentSession();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.hibernate4.HibernateSimpleDao
 * JD-Core Version:    0.6.0
 */