/*     */ package com.jspgou.common.hibernate4;
/*     */ 
/*     */ import com.jspgou.common.util.MyBeanUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ public abstract class HibernateBaseDao<T, ID extends Serializable> extends HibernateSimpleDao
/*     */ {
/*     */   protected T get(ID id)
/*     */   {
/*  32 */     return get(id, false);
/*     */   }
/*     */ 
/*     */   protected T get(ID id, boolean lock)
/*     */   {
/*     */     T entity;
/*  46 */     if (lock)
/*  47 */       entity = (T)getSession().get(getEntityClass(), id,
/*  48 */         LockMode.PESSIMISTIC_WRITE);
/*     */     else {
/*  50 */       entity = (T)getSession().get(getEntityClass(), id);
/*     */     }
/*  52 */     return entity;
/*     */   }
/*     */ 
/*     */   protected List<T> findByProperty(String property, Object value)
/*     */   {
/*  60 */     Assert.hasText(property);
/*  61 */     return createCriteria(new Criterion[] { Restrictions.eq(property, value) }).list();
/*     */   }
/*     */ 
/*     */   protected T findUniqueByProperty(String property, Object value)
/*     */   {
/*  69 */     Assert.hasText(property);
/*  70 */     Assert.notNull(value);
/*  71 */     return (T)createCriteria(new Criterion[] { Restrictions.eq(property, value) }).uniqueResult();
/*     */   }
/*     */ 
/*     */   protected int countByProperty(String property, Object value)
/*     */   {
/*  82 */     Assert.hasText(property);
/*  83 */     Assert.notNull(value);
/*  84 */     return ((Number)createCriteria(new Criterion[] { Restrictions.eq(property, value) })
/*  85 */       .setProjection(Projections.rowCount()).uniqueResult())
/*  86 */       .intValue();
/*     */   }
/*     */ 
/*     */   protected List findByCriteria(Criterion[] criterion)
/*     */   {
/*  96 */     return createCriteria(criterion).list();
/*     */   }
/*     */ 
/*     */   public T updateByUpdater(Updater<T> updater)
/*     */   {
/* 107 */     ClassMetadata cm = this.sessionFactory.getClassMetadata(getEntityClass());
/* 108 */     Object bean = updater.getBean();
/*     */ 
/* 110 */     T po = (T)getSession().get(getEntityClass(), cm.getIdentifier(bean, (SessionImplementor)this.sessionFactory.getCurrentSession()));
/* 111 */     updaterCopyToPersistentObject(updater, po, cm);
/* 112 */     return po;
/*     */   }
/*     */ 
/*     */   private void updaterCopyToPersistentObject(Updater<T> updater, T po, ClassMetadata cm)
/*     */   {
/* 123 */     String[] propNames = cm.getPropertyNames();
/* 124 */     String identifierName = cm.getIdentifierPropertyName();
/* 125 */     Object bean = updater.getBean();
/*     */ 
/* 127 */     for (String propName : propNames) {
/* 128 */       if (propName.equals(identifierName))
/*     */         continue;
/*     */       try
/*     */       {
/* 132 */         Object value = MyBeanUtils.getSimpleProperty(bean, propName);
/* 133 */         if (!updater.isUpdate(propName, value))
/*     */         {
/*     */           continue;
/*     */         }
/* 137 */         cm.setPropertyValue(po, propName, value);
/*     */       } catch (Exception e) {
/* 139 */         throw new RuntimeException(
/* 140 */           "copy property to persistent object failed: '" + 
/* 141 */           propName + "'", e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Criteria createCriteria(Criterion[] criterions)
/*     */   {
/* 150 */     Criteria criteria = getSession().createCriteria(getEntityClass());
/* 151 */     for (Criterion c : criterions) {
/* 152 */       criteria.add(c);
/*     */     }
/*     */ 
/* 155 */     criteria.setCacheable(true);
/* 156 */     return criteria;
/*     */   }
/*     */ 
/*     */   protected abstract Class<T> getEntityClass();
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.hibernate4.HibernateBaseDao
 * JD-Core Version:    0.6.0
 */