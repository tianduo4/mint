package com.mint.common.hibernate4;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class TreeIntercptor extends EmptyInterceptor
        implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(TreeIntercptor.class);
    private ApplicationContext appCtx;
    private SessionFactory sessionFactory;
    public static final String SESSION_FACTORY = "sessionFactory";

    public void setApplicationContext(ApplicationContext appCtx)
            throws BeansException {
        this.appCtx = appCtx;
    }

    protected SessionFactory getSessionFactory() {
        if (this.sessionFactory == null) {
            this.sessionFactory = ((SessionFactory) this.appCtx.getBean("sessionFactory",
                    SessionFactory.class));
            if (this.sessionFactory == null) {
                throw new IllegalStateException("not found bean named 'sessionFactory',please check you spring config file.");
            }

        }

        return this.sessionFactory;
    }

    protected Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if ((entity instanceof HibernateTree)) {
            HibernateTree tree = (HibernateTree) entity;
            Number parentId = tree.getParentId();
            String beanName = tree.getClass().getName();
            Session session = getSession();
            FlushMode model = session.getFlushMode();
            session.setFlushMode(FlushMode.MANUAL);
            Integer myPosition;
            if (parentId != null) {
                String hql = "select bean." + tree.getRgtName() + " from " +
                        beanName + " bean where bean.id=:pid";
                myPosition =
                        Integer.valueOf(((Number) session.createQuery(hql).setParameter(
                                "pid", parentId).uniqueResult()).intValue());
                String hql1 = "update " + beanName + " bean set bean." +
                        tree.getRgtName() + " = bean." + tree.getRgtName() +
                        " + 2 WHERE bean." + tree.getRgtName() +
                        " >= :myPosition";
                String hql2 = "update " + beanName + " bean set bean." +
                        tree.getLftName() + " = bean." + tree.getLftName() +
                        " + 2 WHERE bean." + tree.getLftName() +
                        " >= :myPosition";
                if (!StringUtils.isBlank(tree.getTreeCondition())) {
                    hql1 = hql1 + " and (" + tree.getTreeCondition() + ")";
                    hql2 = hql2 + " and (" + tree.getTreeCondition() + ")";
                }
                session.createQuery(hql1)
                        .setParameter("myPosition", myPosition).executeUpdate();
                session.createQuery(hql2)
                        .setParameter("myPosition", myPosition).executeUpdate();
            } else {
                String hql = "select max(bean." + tree.getRgtName() + ") from " +
                        beanName + " bean";
                if (!StringUtils.isBlank(tree.getTreeCondition())) {
                    hql = hql + " where " + tree.getTreeCondition();
                }
                Number myPositionNumber = (Number) session.createQuery(hql)
                        .uniqueResult();
                if (myPositionNumber == null)
                    myPosition = Integer.valueOf(1);
                else {
                    myPosition = Integer.valueOf(myPositionNumber.intValue() + 1);
                }
            }
            session.setFlushMode(model);
            for (int i = 0; i < propertyNames.length; i++) {
                if (propertyNames[i].equals(tree.getLftName())) {
                    state[i] = myPosition;
                }
                if (propertyNames[i].equals(tree.getRgtName())) {
                    state[i] = Integer.valueOf(myPosition.intValue() + 1);
                }
            }
            return true;
        }
        return false;
    }

    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (!(entity instanceof HibernateTree)) {
            return false;
        }
        HibernateTree tree = (HibernateTree) entity;
        for (int i = 0; i < propertyNames.length; i++) {
            if (propertyNames[i].equals(tree.getParentName())) {
                HibernateTree preParent = (HibernateTree) previousState[i];
                HibernateTree currParent = (HibernateTree) currentState[i];
                return updateParent(tree, preParent, currParent);
            }
        }
        return false;
    }

    private boolean updateParent(HibernateTree<?> tree, HibernateTree<?> preParent, HibernateTree<?> currParent) {
        if (((preParent == null) && (currParent == null)) || (
                (preParent != null) && (currParent != null) &&
                        (preParent
                                .getId().equals(currParent.getId())))) {
            return false;
        }
        String beanName = tree.getClass().getName();
        if (log.isDebugEnabled()) {
            log.debug("update Tree {}, id={}, pre-parent id={}, curr-parent id={}",
                    new Object[]{
                            beanName, tree.getId(),
                            preParent == null ? null : preParent.getId(),
                            currParent == null ? null : currParent.getId()});
        }
        Session session = getSession();

        FlushMode model = session.getFlushMode();
        session.setFlushMode(FlushMode.MANUAL);
        Integer currParentRgt;
        if (currParent != null) {
            String hql = "select bean." + tree.getLftName() + ",bean." +
                    tree.getRgtName() + " from " + beanName +
                    " bean where bean.id=:id";
            Object[] position = (Object[]) session.createQuery(hql)
                    .setParameter("id", tree.getId()).uniqueResult();
            int nodeLft = ((Number) position[0]).intValue();
            int nodeRgt = ((Number) position[1]).intValue();
            int span = nodeRgt - nodeLft + 1;
            log.debug("current node span={}", Integer.valueOf(span));

            Object[] currPosition = (Object[]) session.createQuery(hql)
                    .setParameter("id", currParent.getId()).uniqueResult();
            int currParentLft = ((Number) currPosition[0]).intValue();
            currParentRgt = Integer.valueOf(((Number) currPosition[1]).intValue());
            log.debug("current parent lft={} rgt={}", Integer.valueOf(currParentLft),
                    currParentRgt);

            String hql1 = "update " + beanName + " bean set bean." +
                    tree.getRgtName() + " = bean." + tree.getRgtName() +
                    " + " + span + " WHERE bean." + tree.getRgtName() +
                    " >= :parentRgt";
            String hql2 = "update " + beanName + " bean set bean." +
                    tree.getLftName() + " = bean." + tree.getLftName() +
                    " + " + span + " WHERE bean." + tree.getLftName() +
                    " >= :parentRgt";
            if (!StringUtils.isBlank(tree.getTreeCondition())) {
                hql1 = hql1 + " and (" + tree.getTreeCondition() + ")";
                hql2 = hql2 + " and (" + tree.getTreeCondition() + ")";
            }
            session.createQuery(hql1).setInteger("parentRgt", currParentRgt.intValue())
                    .executeUpdate();
            session.createQuery(hql2).setInteger("parentRgt", currParentRgt.intValue())
                    .executeUpdate();
            log.debug("vacated span hql: {}, {}, parentRgt={}", new Object[]{
                    hql1, hql2, currParentRgt});
        } else {
            String hql = "select max(bean." + tree.getRgtName() + ") from " +
                    beanName + " bean";
            if (!StringUtils.isBlank(tree.getTreeCondition())) {
                hql = hql + " where " + tree.getTreeCondition();
            }
            currParentRgt =
                    Integer.valueOf(((Number) session.createQuery(hql).uniqueResult())
                            .intValue());
            currParentRgt = Integer.valueOf(currParentRgt.intValue() + 1);
            log.debug("max node left={}", currParentRgt);
        }

        String hql = "select bean." + tree.getLftName() + ",bean." +
                tree.getRgtName() + " from " + beanName +
                " bean where bean.id=:id";
        Object[] position = (Object[]) session.createQuery(hql).setParameter(
                "id", tree.getId()).uniqueResult();
        int nodeLft = ((Number) position[0]).intValue();
        int nodeRgt = ((Number) position[1]).intValue();
        int span = nodeRgt - nodeLft + 1;
        if (log.isDebugEnabled()) {
            log.debug("before adjust self left={} right={} span={}",
                    new Object[]{Integer.valueOf(nodeLft), Integer.valueOf(nodeRgt), Integer.valueOf(span)});
        }
        int offset = currParentRgt.intValue() - nodeLft;
        hql = "update " + beanName + " bean set bean." + tree.getLftName() +
                "=bean." + tree.getLftName() + "+:offset, bean." +
                tree.getRgtName() + "=bean." + tree.getRgtName() +
                "+:offset WHERE bean." + tree.getLftName() +
                " between :nodeLft and :nodeRgt";
        if (!StringUtils.isBlank(tree.getTreeCondition())) {
            hql = hql + " and (" + tree.getTreeCondition() + ")";
        }
        session.createQuery(hql).setParameter("offset", Integer.valueOf(offset)).setParameter(
                "nodeLft", Integer.valueOf(nodeLft)).setParameter("nodeRgt", Integer.valueOf(nodeRgt))
                .executeUpdate();
        if (log.isDebugEnabled()) {
            log.debug("adjust self hql: {}, offset={}, nodeLft={}, nodeRgt={}",
                    new Object[]{hql, Integer.valueOf(offset), Integer.valueOf(nodeLft), Integer.valueOf(nodeRgt)});
        }

        String hql1 = "update " + beanName + " bean set bean." +
                tree.getRgtName() + " = bean." + tree.getRgtName() + " - " +
                span + " WHERE bean." + tree.getRgtName() + " > :nodeRgt";
        String hql2 = "update " + beanName + " bean set bean." +
                tree.getLftName() + " = bean." + tree.getLftName() + " - " +
                span + " WHERE bean." + tree.getLftName() + " > :nodeRgt";
        if (!StringUtils.isBlank(tree.getTreeCondition())) {
            hql1 = hql1 + " and (" + tree.getTreeCondition() + ")";
            hql2 = hql2 + " and (" + tree.getTreeCondition() + ")";
        }
        session.createQuery(hql1).setParameter("nodeRgt", Integer.valueOf(nodeRgt))
                .executeUpdate();
        session.createQuery(hql2).setParameter("nodeRgt", Integer.valueOf(nodeRgt))
                .executeUpdate();
        if (log.isDebugEnabled()) {
            log.debug("clear span hql1:{}, hql2:{}, nodeRgt:{}", new Object[]{
                    hql1, hql2, Integer.valueOf(nodeRgt)});
        }
        session.setFlushMode(model);
        return true;
    }

    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if ((entity instanceof HibernateTree)) {
            HibernateTree tree = (HibernateTree) entity;
            String beanName = tree.getClass().getName();
            Session session = getSession();
            FlushMode model = session.getFlushMode();
            session.setFlushMode(FlushMode.MANUAL);
            String hql = "select bean." + tree.getLftName() + " from " +
                    beanName + " bean where bean.id=:id";
            Integer myPosition =
                    Integer.valueOf(((Number) session.createQuery(hql)
                            .setParameter("id", tree.getId()).uniqueResult())
                            .intValue());
            String hql1 = "update " + beanName + " bean set bean." +
                    tree.getRgtName() + " = bean." + tree.getRgtName() +
                    " - 2 WHERE bean." + tree.getRgtName() + " > :myPosition";
            String hql2 = "update " + beanName + " bean set bean." +
                    tree.getLftName() + " = bean." + tree.getLftName() +
                    " - 2 WHERE bean." + tree.getLftName() + " > :myPosition";
            if (!StringUtils.isBlank(tree.getTreeCondition())) {
                hql1 = hql1 + " and (" + tree.getTreeCondition() + ")";
                hql2 = hql2 + " and (" + tree.getTreeCondition() + ")";
            }
            session.createQuery(hql1).setInteger("myPosition", myPosition.intValue())
                    .executeUpdate();
            session.createQuery(hql2).setInteger("myPosition", myPosition.intValue())
                    .executeUpdate();
            session.setFlushMode(model);
        }
    }
}

