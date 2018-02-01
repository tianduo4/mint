package com.jspgou.common.hibernate4;

import com.jspgou.common.util.MyBeanUtils;
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


public abstract class HibernateBaseDao<T, ID extends Serializable> extends HibernateSimpleDao {
    protected T get(ID id) {
        return get(id, false);
    }

    protected T get(ID id, boolean lock) {
        T entity;
        if (lock)
            entity = (T) getSession().get(getEntityClass(), id,
                    LockMode.PESSIMISTIC_WRITE);
        else {
            entity = (T) getSession().get(getEntityClass(), id);
        }
        return entity;
    }

    protected List<T> findByProperty(String property, Object value) {
        Assert.hasText(property);
        return createCriteria(new Criterion[]{Restrictions.eq(property, value)}).list();
    }

    protected T findUniqueByProperty(String property, Object value) {
        Assert.hasText(property);
        Assert.notNull(value);
        return (T) createCriteria(new Criterion[]{Restrictions.eq(property, value)}).uniqueResult();
    }

    protected int countByProperty(String property, Object value) {
        Assert.hasText(property);
        Assert.notNull(value);
        return ((Number) createCriteria(new Criterion[]{Restrictions.eq(property, value)})
                .setProjection(Projections.rowCount()).uniqueResult())
                .intValue();
    }

    protected List findByCriteria(Criterion[] criterion) {
        return createCriteria(criterion).list();
    }

    public T updateByUpdater(Updater<T> updater) {
        ClassMetadata cm = this.sessionFactory.getClassMetadata(getEntityClass());
        Object bean = updater.getBean();

        T po = (T) getSession().get(getEntityClass(), cm.getIdentifier(bean, (SessionImplementor) this.sessionFactory.getCurrentSession()));
        updaterCopyToPersistentObject(updater, po, cm);
        return po;
    }

    private void updaterCopyToPersistentObject(Updater<T> updater, T po, ClassMetadata cm) {
        String[] propNames = cm.getPropertyNames();
        String identifierName = cm.getIdentifierPropertyName();
        Object bean = updater.getBean();

        for (String propName : propNames) {
            if (propName.equals(identifierName))
                continue;
            try {
                Object value = MyBeanUtils.getSimpleProperty(bean, propName);
                if (!updater.isUpdate(propName, value)) {
                    continue;
                }
                cm.setPropertyValue(po, propName, value);
            } catch (Exception e) {
                throw new RuntimeException(
                        "copy property to persistent object failed: '" +
                                propName + "'", e);
            }
        }
    }

    protected Criteria createCriteria(Criterion[] criterions) {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        for (Criterion c : criterions) {
            criteria.add(c);
        }

        criteria.setCacheable(true);
        return criteria;
    }

    protected abstract Class<T> getEntityClass();
}

