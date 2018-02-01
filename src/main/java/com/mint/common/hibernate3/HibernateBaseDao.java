// package com.jspgou.common.hibernate3;
//
// import com.mint.common.page.Pagination;
// import com.mint.common.util.MyBeanUtils;
// import java.io.Serializable;
// import java.util.ArrayList;
// import java.util.Iterator;
// import java.util.List;
// import org.hibernate.Criteria;
// import org.hibernate.EntityMode;
// import org.hibernate.LockMode;
// import org.hibernate.Query;
// import org.hibernate.Session;
// import org.hibernate.SessionFactory;
// import org.hibernate.criterion.CriteriaSpecification;
// import org.hibernate.criterion.Criterion;
// import org.hibernate.criterion.Projection;
// import org.hibernate.criterion.Projections;
// import org.hibernate.criterion.Restrictions;
// import org.hibernate.impl.CriteriaImpl;
// import org.hibernate.metadata.ClassMetadata;
// import org.hibernate.transform.ResultTransformer;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.util.Assert;
//
// public abstract class HibernateBaseDao<T, ID extends Serializable>
// {
//   protected Logger log = LoggerFactory.getLogger(getClass());
//   protected static final String ORDER_ENTRIES = "orderEntries";
//   protected SessionFactory sessionFactory;
//
//   protected T get(ID id)
//   {
//     return get(id, false);
//   }
//
//   protected T get(ID id, boolean lock)
//   {
//     Object entity;
//     if (lock)
//       entity = getSession().get(getEntityClass(), id,
//         LockMode.UPGRADE);
//     else {
//       entity = getSession().get(getEntityClass(), id);
//     }
//     return entity;
//   }
//
//   protected List<T> findByProperty(String property, Object value)
//   {
//     Assert.hasText(property);
//     return createCriteria(new Criterion[] { Restrictions.eq(property, value) }).list();
//   }
//
//   protected T findUniqueByProperty(String property, Object value)
//   {
//     Assert.hasText(property);
//     Assert.notNull(value);
//     return createCriteria(new Criterion[] { Restrictions.eq(property, value) })
//       .uniqueResult();
//   }
//
//   protected int countByProperty(String property, Object value)
//   {
//     Assert.hasText(property);
//     Assert.notNull(value);
//     return ((Number)createCriteria(new Criterion[] { Restrictions.eq(property, value) })
//       .setProjection(Projections.rowCount()).uniqueResult())
//       .intValue();
//   }
//
//   protected List findByCriteria(Criterion[] criterion)
//   {
//     return createCriteria(criterion).list();
//   }
//
//   public T updateByUpdater(Updater<T> updater)
//   {
//     ClassMetadata cm = this.sessionFactory.getClassMetadata(getEntityClass());
//     Object bean = updater.getBean();
//     Object po = getSession().get(getEntityClass(),
//       cm.getIdentifier(bean, EntityMode.POJO));
//     updaterCopyToPersistentObject(updater, po, cm);
//     return po;
//   }
//
//   private void updaterCopyToPersistentObject(Updater<T> updater, T po, ClassMetadata cm)
//   {
//     String[] propNames = cm.getPropertyNames();
//     String identifierName = cm.getIdentifierPropertyName();
//     Object bean = updater.getBean();
//
//     for (String propName : propNames) {
//       if (propName.equals(identifierName))
//         continue;
//       try
//       {
//         Object value = MyBeanUtils.getSimpleProperty(bean, propName);
//         if (!updater.isUpdate(propName, value)) {
//           continue;
//         }
//         cm.setPropertyValue(po, propName, value, EntityMode.POJO);
//       } catch (Exception e) {
//         throw new RuntimeException(
//           "copy property to persistent object failed: '" +
//           propName + "'", e);
//       }
//     }
//   }
//
//   protected Criteria createCriteria(Criterion[] criterions)
//   {
//     Criteria criteria = getSession().createCriteria(getEntityClass());
//     for (Criterion c : criterions) {
//       criteria.add(c);
//     }
//     return criteria;
//   }
//
//   protected abstract Class<T> getEntityClass();
//
//   protected List find(String hql, Object[] values)
//   {
//     return createQuery(hql, values).list();
//   }
//
//   protected Object findUnique(String hql, Object[] values)
//   {
//     return createQuery(hql, values).uniqueResult();
//   }
//
//   protected Pagination find(Finder finder, int pageNo, int pageSize)
//   {
//     int totalCount = countQueryResult(finder);
//     Pagination p = new Pagination(pageNo, pageSize, totalCount);
//     if (totalCount < 1) {
//       p.setList(new ArrayList());
//       return p;
//     }
//     Query query = getSession().createQuery(finder.getOrigHql());
//     finder.setParamsToQuery(query);
//     query.setFirstResult(p.getFirstResult());
//     query.setMaxResults(p.getPageSize());
//     if (finder.isCacheable()) {
//       query.setCacheable(true);
//     }
//     List list = query.list();
//     p.setList(list);
//     return p;
//   }
//
//   protected List find(Finder finder)
//   {
//     Query query = getSession().createQuery(finder.getOrigHql());
//     finder.setParamsToQuery(query);
//     query.setFirstResult(finder.getFirstResult());
//     if (finder.getMaxResults() > 0)
//       query.setMaxResults(finder.getMaxResults());
//     if (finder.isCacheable())
//       query.setCacheable(true);
//     List list = query.list();
//     return list;
//   }
//
//   protected Query createQuery(String queryString, Object[] values)
//   {
//     Assert.hasText(queryString);
//     Query queryObject = getSession().createQuery(queryString);
//     if (values != null) {
//       for (int i = 0; i < values.length; i++) {
//         queryObject.setParameter(i, values[i]);
//       }
//     }
//     return queryObject;
//   }
//
//   protected Pagination findByCriteria(Criteria crit, int pageNo, int pageSize)
//   {
//     CriteriaImpl impl = (CriteriaImpl)crit;
//
//     Projection projection = impl.getProjection();
//     ResultTransformer transformer = impl.getResultTransformer();
//     try
//     {
//       List orderEntries = (List)
//         MyBeanUtils.getFieldValue(impl, "orderEntries");
//       MyBeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
//     } catch (Exception e) {
//       throw new RuntimeException(
//         "cannot read/write 'orderEntries' from CriteriaImpl", e);
//     }
//     List orderEntries;
//     int totalCount = ((Number)crit.setProjection(Projections.rowCount())
//       .uniqueResult()).intValue();
//     Pagination p = new Pagination(pageNo, pageSize, totalCount);
//     if (totalCount < 1) {
//       p.setList(new ArrayList());
//       return p;
//     }
//
//     crit.setProjection(projection);
//     if (projection == null) {
//       crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
//     }
//     if (transformer != null)
//       crit.setResultTransformer(transformer);
//     try
//     {
//       MyBeanUtils.setFieldValue(impl, "orderEntries", orderEntries);
//     } catch (Exception e) {
//       throw new RuntimeException(
//         "set 'orderEntries' to CriteriaImpl faild", e);
//     }
//     crit.setFirstResult(p.getFirstResult());
//     crit.setMaxResults(p.getPageSize());
//     p.setList(crit.list());
//     return p;
//   }
//
//   protected int countQueryResult(Finder finder)
//   {
//     Query query = getSession().createQuery(finder.getRowCountHql());
//     finder.setParamsToQuery(query);
//     if (finder.isCacheable()) {
//       query.setCacheable(true);
//     }
//     return ((Number)query.iterate().next()).intValue();
//   }
//
//   @Autowired
//   public void setSessionFactory(SessionFactory sessionfactory)
//   {
//     this.sessionFactory = sessionfactory;
//   }
//
//   protected Session getSession() {
//     return this.sessionFactory.getCurrentSession();
//   }
// }
////
