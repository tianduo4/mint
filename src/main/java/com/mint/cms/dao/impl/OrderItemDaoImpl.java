package com.mint.cms.dao.impl;

import com.mint.cms.dao.OrderItemDao;
import com.mint.cms.entity.OrderItem;
import com.mint.cms.entity.Product;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;
import com.mint.common.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemDaoImpl extends HibernateBaseDao<OrderItem, Long>
        implements OrderItemDao {
    protected Class<OrderItem> getEntityClass() {
        return OrderItem.class;
    }

    public List<Object[]> profitTop(Long ctgid, Long typeid, Integer pageNo, Integer pageSize) {
        Query query = getSession().createQuery(getHQL(ctgid, typeid));
        if (ctgid != null) {
            query.setLong("ctgid", ctgid.longValue());
        }
        if (typeid != null) {
            query.setLong("typeid", typeid.longValue());
        }
        Iterator iter = query.setFirstResult((pageNo.intValue() - 1) * pageSize.intValue()).setMaxResults(pageSize.intValue()).iterate();
        List list = new ArrayList();
        while (iter.hasNext()) {
            list.add((Object[]) iter.next());
        }
        return list;
    }

    public Integer totalCount(Long ctgid, Long typeid) {
        Integer allPage = Integer.valueOf(0);
        Query query = getSession().createQuery(getCountHQL(ctgid, typeid));
        if (ctgid != null) {
            query.setLong("ctgid", ctgid.longValue());
        }
        if (typeid != null) {
            query.setLong("typeid", typeid.longValue());
        }
        Iterator iterator = query.iterate();
        if (iterator.hasNext()) {
            allPage = Integer.valueOf(Integer.parseInt((String) iterator.next()));
        }
        return allPage;
    }

    public Pagination getPageForMember(Long memberId, Integer status, int pageNo, int pageSize) {
        Finder f = Finder.create("select bean from OrderItem bean");
        f.append(" join bean.ordeR indent");
        f.append(" where indent.member.id=:memberId and indent.status=:status");
        f.setParam("memberId", memberId).setParam("status", status);
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public Pagination getPageForProuct(Long productId, int pageNo, int pageSize) {
        Finder f = Finder.create("select bean from OrderItem bean");
        if (productId != null) {
            f.append(" where bean.product.id=:productId");
            f.setParam("productId", productId);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public static String getHQL(Long ctgid, Long typeid) {
        StringBuffer sb = new StringBuffer();
        sb.append("select bean.product.id, sum(bean.count), sum((bean.finalPrice-bean.costPrice)*bean.count) ");
        sb.append(" from OrderItem bean where 1=1 ");
        if (ctgid != null) {
            sb.append(" and bean.product.category.id=:ctgid ");
        }
        if (typeid != null) {
            sb.append(" and bean.product.type.id=:typeid ");
        }
        sb.append(" group by bean.product.id order by sum((bean.finalPrice-bean.costPrice)*bean.count) desc");
        return sb.toString();
    }

    public static String getCountHQL(Long ctgid, Long typeid) {
        StringBuffer sb = new StringBuffer();
        sb.append("select count(DISTINCT bean.product.id) ");
        sb.append(" from OrderItem bean where 1=1 ");
        if (ctgid != null) {
            sb.append(" and bean.product.category.id=:ctgid ");
        }
        if (typeid != null) {
            sb.append(" and bean.product.type.id=:typeid ");
        }
        return sb.toString();
    }

    public List<Object[]> getOrderItem(Date endTime, Date beginTime) {
        String hql = "select bean,sum(bean.count) from OrderItem bean where bean.ordeR.createTime<=:endTime and bean.ordeR.createTime>=:beginTime group by bean.id,bean.productFash.attitude,bean.count,bean.salePrice,bean.memberPrice,bean.costPrice,bean.finalPrice,bean.seckillprice,bean.website.id,bean.product.id,bean.ordeR.id,bean.productFash.id order by sum(bean.count) desc";

        List list = getSession().createQuery(hql).setParameter("endTime", endTime).setParameter("beginTime", beginTime).list();
        return list;
    }

    public OrderItem findById(Long id) {
        OrderItem entity = (OrderItem) get(id);
        return entity;
    }

    public OrderItem findByMember(Long memberId, Long productId, Long orderId) {
        String hql = "from OrderItem bean where bean.product.id=:productId and bean.ordeR.member.id=:memberId ";
        if (orderId != null) {
            String hql1 = hql + " and bean.ordeR.id=:orderId ";
            Iterator it = getSession().createQuery(hql1).setParameter("memberId", memberId).setParameter("productId", productId).setParameter("orderId", orderId)
                    .iterate();
            if (it.hasNext()) {
                return (OrderItem) it.next();
            }
            return null;
        }

        Iterator it = getSession().createQuery(hql).setParameter("memberId", memberId).setParameter("productId", productId)
                .iterate();
        if (it.hasNext()) {
            return (OrderItem) it.next();
        }
        return null;
    }

    public Pagination getPageProductSaleRank(Long webId, String type, Integer categoryId, int pageNo, int pageSize, Date startTime, Date endTime) {
        Finder f = Finder.create("select bean.product.name,bean.product.category.name,sum(bean.count) from OrderItem bean");
        f.append("  where bean.product.status=" + Product.ON_SALE_STATUS + "  and  bean.website.id=:webId and bean.ordeR.delStatus =  1 ");

        StringBuffer totalHql = new StringBuffer(" select count(distinct bean.product.category.name) from OrderItem bean  where  bean.product.status=" + Product.ON_SALE_STATUS + "  and bean.website.id=:webId and bean.ordeR.delStatus =  1");

        f.setParam("webId", webId);
        StringBuffer whereStr = new StringBuffer();
        if (type != null) {
            Date date = new Date();
            if (type.equals("month")) {
                whereStr.append(" and bean.ordeR.createTime >=:startTime");
                f.setParam("startTime", DateUtils.getSpecficMonthStart(date, 0));
                whereStr.append(" and bean.ordeR.createTime <=:endTime");
                f.setParam("endTime", DateUtils.getSpecficMonthEnd(date, 0));
            } else if (type.equals("year")) {
                whereStr.append(" and bean.ordeR.createTime >=:startTime");
                f.setParam("startTime", DateUtils.getSpecficYearStart(date, 0));
                whereStr.append(" and bean.ordeR.createTime <=:endTime");
                f.setParam("endTime", DateUtils.getSpecficYearEnd(date, 0));
            }
        }
        if (startTime != null) {
            whereStr.append(" and bean.ordeR.createTime >=:startTime");
            f.setParam("startTime", DateUtils.getStartDate(startTime));
        }
        if (endTime != null) {
            whereStr.append(" and bean.ordeR.createTime <=:endTime");
            f.setParam("endTime", DateUtils.getFinallyDate(endTime));
        }

        if (categoryId != null) {
            whereStr.append(" and bean.product.category.id =:categoryId");
            f.setParam("categoryId", categoryId);
        }
        totalHql.append(whereStr);
        f.append(whereStr.toString());
        f.append(" group by bean.product.name,bean.product.category.name ");
        f.append(" order by sum(bean.count)  desc ");
        return find(f, totalHql.toString(), pageNo, pageSize);
    }

    private Pagination find(Finder finder, String totalHql, int pageNo, int pageSize) {
        int totalCount = countQueryResultByGroup(finder, totalHql);
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        List list = query.list();
        for (int i = 0; i < list.size(); i++) {
            Object[] arrayOfObject = (Object[]) list.get(i);
        }
        p.setList(list);
        return p;
    }

    protected int countQueryResultByGroup(Finder finder, String selectSql) {
        Query query = getSession().createQuery(selectSql);
        finder.setParamsToQuery(query);
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        return ((Number) query.iterate().next()).intValue();
    }
}

