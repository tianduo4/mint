///*     */ package com.jspgou.common.hibernate3;
///*     */
///*     */ import com.jspgou.common.page.Pagination;
///*     */ import com.jspgou.common.util.MyBeanUtils;
///*     */ import java.io.Serializable;
///*     */ import java.util.ArrayList;
///*     */ import java.util.Iterator;
///*     */ import java.util.List;
///*     */ import org.hibernate.Criteria;
///*     */ import org.hibernate.EntityMode;
///*     */ import org.hibernate.LockMode;
///*     */ import org.hibernate.Query;
///*     */ import org.hibernate.Session;
///*     */ import org.hibernate.SessionFactory;
///*     */ import org.hibernate.criterion.CriteriaSpecification;
///*     */ import org.hibernate.criterion.Criterion;
///*     */ import org.hibernate.criterion.Projection;
///*     */ import org.hibernate.criterion.Projections;
///*     */ import org.hibernate.criterion.Restrictions;
///*     */ import org.hibernate.impl.CriteriaImpl;
///*     */ import org.hibernate.metadata.ClassMetadata;
///*     */ import org.hibernate.transform.ResultTransformer;
///*     */ import org.slf4j.Logger;
///*     */ import org.slf4j.LoggerFactory;
///*     */ import org.springframework.beans.factory.annotation.Autowired;
///*     */ import org.springframework.util.Assert;
///*     */
///*     */ public abstract class HibernateBaseDao<T, ID extends Serializable>
///*     */ {
///*  28 */   protected Logger log = LoggerFactory.getLogger(getClass());
///*     */   protected static final String ORDER_ENTRIES = "orderEntries";
///*     */   protected SessionFactory sessionFactory;
///*     */
///*     */   protected T get(ID id)
///*     */   {
///*  35 */     return get(id, false);
///*     */   }
///*     */
///*     */   protected T get(ID id, boolean lock)
///*     */   {
///*     */     Object entity;
///*  49 */     if (lock)
///*  50 */       entity = getSession().get(getEntityClass(), id,
///*  51 */         LockMode.UPGRADE);
///*     */     else {
///*  53 */       entity = getSession().get(getEntityClass(), id);
///*     */     }
///*  55 */     return entity;
///*     */   }
///*     */
///*     */   protected List<T> findByProperty(String property, Object value)
///*     */   {
///*  63 */     Assert.hasText(property);
///*  64 */     return createCriteria(new Criterion[] { Restrictions.eq(property, value) }).list();
///*     */   }
///*     */
///*     */   protected T findUniqueByProperty(String property, Object value)
///*     */   {
///*  72 */     Assert.hasText(property);
///*  73 */     Assert.notNull(value);
///*  74 */     return createCriteria(new Criterion[] { Restrictions.eq(property, value) })
///*  75 */       .uniqueResult();
///*     */   }
///*     */
///*     */   protected int countByProperty(String property, Object value)
///*     */   {
///*  86 */     Assert.hasText(property);
///*  87 */     Assert.notNull(value);
///*  88 */     return ((Number)createCriteria(new Criterion[] { Restrictions.eq(property, value) })
///*  89 */       .setProjection(Projections.rowCount()).uniqueResult())
///*  90 */       .intValue();
///*     */   }
///*     */
///*     */   protected List findByCriteria(Criterion[] criterion)
///*     */   {
///* 101 */     return createCriteria(criterion).list();
///*     */   }
///*     */
///*     */   public T updateByUpdater(Updater<T> updater)
///*     */   {
///* 112 */     ClassMetadata cm = this.sessionFactory.getClassMetadata(getEntityClass());
///* 113 */     Object bean = updater.getBean();
///* 114 */     Object po = getSession().get(getEntityClass(),
///* 115 */       cm.getIdentifier(bean, EntityMode.POJO));
///* 116 */     updaterCopyToPersistentObject(updater, po, cm);
///* 117 */     return po;
///*     */   }
///*     */
///*     */   private void updaterCopyToPersistentObject(Updater<T> updater, T po, ClassMetadata cm)
///*     */   {
///* 128 */     String[] propNames = cm.getPropertyNames();
///* 129 */     String identifierName = cm.getIdentifierPropertyName();
///* 130 */     Object bean = updater.getBean();
///*     */
///* 132 */     for (String propName : propNames) {
///* 133 */       if (propName.equals(identifierName))
///*     */         continue;
///*     */       try
///*     */       {
///* 137 */         Object value = MyBeanUtils.getSimpleProperty(bean, propName);
///* 138 */         if (!updater.isUpdate(propName, value)) {
///*     */           continue;
///*     */         }
///* 141 */         cm.setPropertyValue(po, propName, value, EntityMode.POJO);
///*     */       } catch (Exception e) {
///* 143 */         throw new RuntimeException(
///* 144 */           "copy property to persistent object failed: '" +
///* 145 */           propName + "'", e);
///*     */       }
///*     */     }
///*     */   }
///*     */
///*     */   protected Criteria createCriteria(Criterion[] criterions)
///*     */   {
///* 154 */     Criteria criteria = getSession().createCriteria(getEntityClass());
///* 155 */     for (Criterion c : criterions) {
///* 156 */       criteria.add(c);
///*     */     }
///* 158 */     return criteria;
///*     */   }
///*     */
///*     */   protected abstract Class<T> getEntityClass();
///*     */
///*     */   protected List find(String hql, Object[] values)
///*     */   {
///* 184 */     return createQuery(hql, values).list();
///*     */   }
///*     */
///*     */   protected Object findUnique(String hql, Object[] values)
///*     */   {
///* 191 */     return createQuery(hql, values).uniqueResult();
///*     */   }
///*     */
///*     */   protected Pagination find(Finder finder, int pageNo, int pageSize)
///*     */   {
///* 207 */     int totalCount = countQueryResult(finder);
///* 208 */     Pagination p = new Pagination(pageNo, pageSize, totalCount);
///* 209 */     if (totalCount < 1) {
///* 210 */       p.setList(new ArrayList());
///* 211 */       return p;
///*     */     }
///* 213 */     Query query = getSession().createQuery(finder.getOrigHql());
///* 214 */     finder.setParamsToQuery(query);
///* 215 */     query.setFirstResult(p.getFirstResult());
///* 216 */     query.setMaxResults(p.getPageSize());
///* 217 */     if (finder.isCacheable()) {
///* 218 */       query.setCacheable(true);
///*     */     }
///* 220 */     List list = query.list();
///* 221 */     p.setList(list);
///* 222 */     return p;
///*     */   }
///*     */
///*     */   protected List find(Finder finder)
///*     */   {
///* 233 */     Query query = getSession().createQuery(finder.getOrigHql());
///* 234 */     finder.setParamsToQuery(query);
///* 235 */     query.setFirstResult(finder.getFirstResult());
///* 236 */     if (finder.getMaxResults() > 0)
///* 237 */       query.setMaxResults(finder.getMaxResults());
///* 238 */     if (finder.isCacheable())
///* 239 */       query.setCacheable(true);
///* 240 */     List list = query.list();
///* 241 */     return list;
///*     */   }
///*     */
///*     */   protected Query createQuery(String queryString, Object[] values)
///*     */   {
///* 248 */     Assert.hasText(queryString);
///* 249 */     Query queryObject = getSession().createQuery(queryString);
///* 250 */     if (values != null) {
///* 251 */       for (int i = 0; i < values.length; i++) {
///* 252 */         queryObject.setParameter(i, values[i]);
///*     */       }
///*     */     }
///* 255 */     return queryObject;
///*     */   }
///*     */
///*     */   protected Pagination findByCriteria(Criteria crit, int pageNo, int pageSize)
///*     */   {
///* 270 */     CriteriaImpl impl = (CriteriaImpl)crit;
///*     */
///* 272 */     Projection projection = impl.getProjection();
///* 273 */     ResultTransformer transformer = impl.getResultTransformer();
///*     */     try
///*     */     {
///* 276 */       List orderEntries = (List)
///* 277 */         MyBeanUtils.getFieldValue(impl, "orderEntries");
///* 278 */       MyBeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
///*     */     } catch (Exception e) {
///* 280 */       throw new RuntimeException(
///* 281 */         "cannot read/write 'orderEntries' from CriteriaImpl", e);
///*     */     }
///*     */     List orderEntries;
///* 284 */     int totalCount = ((Number)crit.setProjection(Projections.rowCount())
///* 285 */       .uniqueResult()).intValue();
///* 286 */     Pagination p = new Pagination(pageNo, pageSize, totalCount);
///* 287 */     if (totalCount < 1) {
///* 288 */       p.setList(new ArrayList());
///* 289 */       return p;
///*     */     }
///*     */
///* 293 */     crit.setProjection(projection);
///* 294 */     if (projection == null) {
///* 295 */       crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
///*     */     }
///* 297 */     if (transformer != null)
///* 298 */       crit.setResultTransformer(transformer);
///*     */     try
///*     */     {
///* 301 */       MyBeanUtils.setFieldValue(impl, "orderEntries", orderEntries);
///*     */     } catch (Exception e) {
///* 303 */       throw new RuntimeException(
///* 304 */         "set 'orderEntries' to CriteriaImpl faild", e);
///*     */     }
///* 306 */     crit.setFirstResult(p.getFirstResult());
///* 307 */     crit.setMaxResults(p.getPageSize());
///* 308 */     p.setList(crit.list());
///* 309 */     return p;
///*     */   }
///*     */
///*     */   protected int countQueryResult(Finder finder)
///*     */   {
///* 319 */     Query query = getSession().createQuery(finder.getRowCountHql());
///* 320 */     finder.setParamsToQuery(query);
///* 321 */     if (finder.isCacheable()) {
///* 322 */       query.setCacheable(true);
///*     */     }
///* 324 */     return ((Number)query.iterate().next()).intValue();
///*     */   }
///*     */
///*     */   @Autowired
///*     */   public void setSessionFactory(SessionFactory sessionfactory)
///*     */   {
///* 331 */     this.sessionFactory = sessionfactory;
///*     */   }
///*     */
///*     */   protected Session getSession() {
///* 335 */     return this.sessionFactory.getCurrentSession();
///*     */   }
///*     */ }
//
///* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
// * Qualified Name:     com.jspgou.common.hibernate3.HibernateBaseDao
// * JD-Core Version:    0.6.0
// */