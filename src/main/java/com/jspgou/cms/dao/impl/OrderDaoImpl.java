package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.OrderDao;
import com.jspgou.cms.entity.Order;
import com.jspgou.cms.entity.Product;
import com.jspgou.common.hibernate4.Finder;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.util.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends HibernateBaseDao<Order, Long>
        implements OrderDao {
    public static final Integer CHECKING = Integer.valueOf(1);

    public static final Integer CHECKED = Integer.valueOf(2);

    public Pagination getPageForMember(Long memberId, int pageNo, int pageSize) {
        Finder f =
                Finder.create("from Order bean where bean.member.id=:memberId and bean.delStatus=:delStatus");
        f.setParam("memberId", memberId);
        f.setParam("delStatus", Boolean.valueOf(true));
        f.append(" and bean.status=40");
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public Pagination getPageForOrderReturn(Long memberId, int pageNo, int pageSize) {
        Finder f =
                Finder.create("from Order bean where bean.returnOrder.id is not null and  bean.delStatus=:delStatus ");
        f.setParam("delStatus", Boolean.valueOf(true));
        if (memberId != null) {
            f.append(" and bean.member.id=:memberId");
            f.setParam("memberId", memberId);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public Pagination getPageForMember1(Long memberId, int pageNo, int pageSize) {
        Finder f =
                Finder.create("from Order bean where bean.member.id=:memberId and  bean.delStatus=:delStatus ");
        f.setParam("memberId", memberId);
        f.setParam("delStatus", Boolean.valueOf(true));
        f.append(" and bean.status>=10 and bean.status<=19");
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public Pagination getPageForMember2(Long memberId, int pageNo, int pageSize) {
        Finder f =
                Finder.create("from Order bean where bean.member.id=:memberId  and  bean.delStatus=:delStatus ");
        f.setParam("memberId", memberId);
        f.setParam("delStatus", Boolean.valueOf(true));
        f.append(" and bean.status>=20 and bean.status<=29");
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public List<Order> getlist(Date endDate) {
        Finder f = Finder.create("from Order bean where bean.payment.type=1   and  bean.delStatus=:delStatus ");
        f.append(" and bean.paymentStatus=:paymentStatus");
        f.append(" and bean.createTime<:endTime");
        f.append(" and (bean.status=:checking or bean.status=:checked)");
        f.setParam("delStatus", Boolean.valueOf(true));
        f.setParam("checking", CHECKING);
        f.setParam("checked", CHECKED);
        f.setParam("endTime", endDate);
        f.setParam("paymentStatus", CHECKING);
        return find(f);
    }

    public Pagination getPageForMember3(Long memberId, int pageNo, int pageSize) {
        Finder f =
                Finder.create("from Order bean where bean.member.id=:memberId and  bean.delStatus=:delStatus  ");
        f.setParam("memberId", memberId);
        f.setParam("delStatus", Boolean.valueOf(true));
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public Pagination getPage(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Integer paymentStatus, Integer shippingStatus, Long code, int pageNo, int pageSize) {
        Finder f =
                Finder.create("from Order bean where bean.returnOrder.id is null and bean.delStatus=:delStatus   ");
        f.setParam("delStatus", Boolean.valueOf(true));
        if (webId != null) {
            f.append(" and bean.website.id=:webId");
            f.setParam("webId", webId);
        }
        if (memberId != null) {
            f.append(" and bean.member.id=:memberId");
            f.setParam("memberId", memberId);
        }
        if (!StringUtils.isBlank(userName)) {
            f.append(" and bean.receiveName like:userName");
            f.setParam("userName", "%" + userName + "%");
        }
        if (!StringUtils.isBlank(productName)) {
            f.append(" and bean.productName like:productName");
            f.setParam("productName", "%" + productName + "%");
        }
        if (paymentId != null) {
            f.append(" and bean.payment.id=:paymentId");
            f.setParam("paymentId", paymentId);
        }
        if (shippingId != null) {
            f.append(" and bean.shipping.id=:shippingId");
            f.setParam("shippingId", shippingId);
        }
        if (startTime != null) {
            f.append(" and bean.createTime>:startTime");
            f.setParam("startTime", startTime);
        }
        if (endTime != null) {
            f.append(" and bean.createTime<:endTime");
            f.setParam("endTime", endTime);
        }
        if (startOrderTotal != null) {
            f.append(" and bean.total>=:startOrderTotal");
            f.setParam("startOrderTotal", startOrderTotal);
        }
        if (endOrderTotal != null) {
            f.append(" and bean.total<=:endOrderTotal");
            f.setParam("endOrderTotal", endOrderTotal);
        }
        if (status != null) {
            if (status.intValue() == 5) {
                f.append(" and (bean.status=:checking or bean.status=:checked)");
                f.append(" and bean.paymentStatus=:payment");
                f.setParam("checking", CHECKING);
                f.setParam("checked", CHECKED);
                f.setParam("payment", CHECKING);
            } else if (status.intValue() == 6) {
                f.append(" and ((bean.payment.type=1 and bean.paymentStatus=:payment)or(bean.payment.type=2))");
                f.append(" and bean.status=:checked");
                f.append(" and bean.shippingStatus=:shipping");
                f.setParam("checked", CHECKED);
                f.setParam("shipping", CHECKING);
                f.setParam("payment", CHECKING);
            } else {
                f.append(" and bean.status=:status");
                f.setParam("status", status);
            }
        }
        if (paymentStatus != null) {
            f.append(" and bean.paymentStatus=:paymentStatus");
            f.setParam("paymentStatus", paymentStatus);
        }
        if (shippingStatus != null) {
            f.append(" and bean.shippingStatus=:shippingStatus");
            f.setParam("shippingStatus", shippingStatus);
        }
        if (code != null) {
            f.append(" and bean.code=:code");
            f.setParam("code", code);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public Pagination getPage1(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Integer paymentStatus, Integer shippingStatus, Long code, int pageNo, int pageSize) {
        Finder f =
                Finder.create("from Order bean where bean.returnOrder.id is null ");
        if (webId != null) {
            f.append(" and bean.website.id=:webId");
            f.setParam("webId", webId);
        }
        if (memberId != null) {
            f.append(" and bean.member.id=:memberId");
            f.setParam("memberId", memberId);
        }
        if (!StringUtils.isBlank(userName)) {
            f.append(" and bean.receiveName like:userName");
            f.setParam("userName", "%" + userName + "%");
        }
        if (!StringUtils.isBlank(productName)) {
            f.append(" and bean.productName like:productName");
            f.setParam("productName", "%" + productName + "%");
        }
        if (paymentId != null) {
            f.append(" and bean.payment.id=:paymentId");
            f.setParam("paymentId", paymentId);
        }
        if (shippingId != null) {
            f.append(" and bean.shipping.id=:shippingId");
            f.setParam("shippingId", shippingId);
        }
        if (startTime != null) {
            f.append(" and bean.createTime>:startTime");
            f.setParam("startTime", startTime);
        }
        if (endTime != null) {
            f.append(" and bean.createTime<:endTime");
            f.setParam("endTime", endTime);
        }
        if (startOrderTotal != null) {
            f.append(" and bean.total>=:startOrderTotal");
            f.setParam("startOrderTotal", startOrderTotal);
        }
        if (endOrderTotal != null) {
            f.append(" and bean.total<=:endOrderTotal");
            f.setParam("endOrderTotal", endOrderTotal);
        }
        if (status != null) {
            if (status.intValue() == 5) {
                f.append(" and (bean.status=:checking or bean.status=:checked)");
                f.append(" and bean.paymentStatus=:payment");
                f.setParam("checking", CHECKING);
                f.setParam("checked", CHECKED);
                f.setParam("payment", CHECKING);
            } else if (status.intValue() == 6) {
                f.append(" and ((bean.payment.type=1 and bean.paymentStatus=:payment)or(bean.payment.type=2))");
                f.append(" and bean.status=:checked");
                f.append(" and bean.shippingStatus=:shipping");
                f.setParam("checked", CHECKED);
                f.setParam("shipping", CHECKING);
                f.setParam("payment", CHECKING);
            } else {
                f.append(" and bean.status=:status");
                f.setParam("status", status);
            }
        }
        if (paymentStatus != null) {
            f.append(" and bean.paymentStatus=:paymentStatus");
            f.setParam("paymentStatus", paymentStatus);
        }
        if (shippingStatus != null) {
            f.append(" and bean.shippingStatus=:shippingStatus");
            f.setParam("shippingStatus", shippingStatus);
        }
        if (code != null) {
            f.append(" and bean.code=:code");
            f.setParam("code", code);
        }
        f.append(" and bean.delStatus=true");
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public Pagination getPage(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Long code, int pageNo, int pageSize) {
        Finder f = Finder.create("from Order bean where 1=1 and bean.delStatus=true ");
        if (webId != null) {
            f.append(" and bean.website.id=:webId");
            f.setParam("webId", webId);
        }
        if (memberId != null) {
            f.append(" and bean.member.id=:memberId");
            f.setParam("memberId", memberId);
        }
        if (!StringUtils.isBlank(userName)) {
            f.append(" and bean.receiveName like:userName");
            f.setParam("userName", "%" + userName + "%");
        }
        if (!StringUtils.isBlank(productName)) {
            f.append(" and bean.productName like:productName");
            f.setParam("productName", "%" + productName + "%");
        }
        if (paymentId != null) {
            f.append(" and bean.payment.id=:paymentId");
            f.setParam("paymentId", paymentId);
        }
        if (shippingId != null) {
            f.append(" and bean.shipping.id=:shippingId");
            f.setParam("shippingId", shippingId);
        }
        if (startTime != null) {
            f.append(" and bean.createTime>:startTime");
            f.setParam("startTime", startTime);
        }
        if (endTime != null) {
            f.append(" and bean.createTime<:endTime");
            f.setParam("endTime", endTime);
        }
        if (startOrderTotal != null) {
            f.append(" and bean.total>=:startOrderTotal");
            f.setParam("startOrderTotal", startOrderTotal);
        }
        if (endOrderTotal != null) {
            f.append(" and bean.total<=:endOrderTotal");
            f.setParam("endOrderTotal", endOrderTotal);
        }
        if (status != null) {
            if (status.intValue() == 5) {
                f.append(" and (bean.status=:checking or bean.status=:checked)");
                f.append(" and bean.paymentStatus=:payment");
                f.setParam("checking", CHECKING);
                f.setParam("checked", CHECKED);
                f.setParam("payment", CHECKING);
            } else if (status.intValue() == 6) {
                f.append(" and (bean.status=:checking or bean.status=:checked)");
                f.append(" and bean.shippingStatus=:shipping");
                f.setParam("checking", CHECKING);
                f.setParam("checked", CHECKED);
                f.setParam("shipping", CHECKED);
            } else {
                f.append(" and bean.status=:status");
                f.setParam("status", status);
            }
        }

        if (code != null) {
            f.append(" and bean.code=:code");
            f.setParam("code", code);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    public List<Object> getTotlaOrder() {
        List o = new ArrayList();
        Long totalOrder = (Long) getSession()
                .createQuery("select count(*) from Order bean where  bean.delStatus=true ").uniqueResult();
        Long noCompleteOrder = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Order bean where bean.status between 1 and 2 and  bean.delStatus=true ")
                        .uniqueResult();
        Calendar c = Calendar.getInstance();
        String month = c.get(2) + "1";
        String year = c.get(1) + "";
        if (month.length() == 1)
            month = "0" + month;
        else {
            month = month;
        }
        String str = year + "-" + month;
        Long thisMontyOrder = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Order bean where bean.delStatus=true and  bean.createTime like :time")
                        .setString("time", "%" + str + "%").uniqueResult();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String tady = sf.format(new Date());
        Long todayOrder = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Order bean where bean.delStatus=true and  bean.createTime like :tody")
                        .setString("tody", "%" + tady + "%").uniqueResult();
        Long totalProduct = (Long) getSession()
                .createQuery("select count(*) from Product bean")
                .uniqueResult();
        Long newProductMonth = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Product bean where bean.status !=" + Product.DEL_STATUS + " and  bean.createTime like :time")
                        .setString("time", "%" + str + "%").uniqueResult();
        Long dateProduct = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Product bean where bean.status !=" + Product.DEL_STATUS + " and bean.createTime like :time")
                        .setString("time", "%" + tady + "%").uniqueResult();
        Long putawayProduct = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Product bean where bean.status=" + Product.ON_SALE_STATUS)
                        .uniqueResult();

        Long totalMember = (Long) getSession()
                .createQuery("select count(*) from ShopMember bean")
                .uniqueResult();
        Long totalMonthMember = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Member bean where bean.createTime like :time")
                        .setString("time", "%" + str + "%").uniqueResult();
        Long totalDateMember = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Member bean where bean.createTime like :time")
                        .setString("time", "%" + tady + "%").uniqueResult();
        c.add(5, -7);
        Date oldDate = c.getTime();
        Long date7Member = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Member bean where bean.createTime >:time")
                        .setParameter("time", oldDate).uniqueResult();

        Long[] cc = new Long[12];
        cc[0] = Long.valueOf(totalOrder == null ? 0L : totalOrder.longValue());
        cc[1] = Long.valueOf(noCompleteOrder == null ? 0L : noCompleteOrder.longValue());
        cc[2] = Long.valueOf(thisMontyOrder == null ? 0L : thisMontyOrder.longValue());
        cc[3] = Long.valueOf(todayOrder == null ? 0L : todayOrder.longValue());

        cc[4] = Long.valueOf(totalProduct == null ? 0L : totalProduct.longValue());
        cc[5] = Long.valueOf(newProductMonth == null ? 0L : newProductMonth.longValue());
        cc[6] = Long.valueOf(dateProduct == null ? 0L : dateProduct.longValue());

        cc[7] = Long.valueOf(totalMember == null ? 0L : totalMember.longValue());
        cc[8] = Long.valueOf(totalMonthMember == null ? 0L : totalMonthMember.longValue());
        cc[9] = Long.valueOf(totalDateMember == null ? 0L : totalDateMember.longValue());
        cc[10] = Long.valueOf(date7Member == null ? 0L : date7Member.longValue());
        cc[11] = Long.valueOf(putawayProduct == null ? 0L : putawayProduct.longValue());

        o.add(cc);
        return o;
    }

    public BigDecimal getMemberMoneyByYear(Long memberId) {
        Calendar c = Calendar.getInstance();
        String year = c.get(1) + "";
        Query q =
                getSession()
                        .createQuery(
                                "select sum((bean.salePrice)* bean.count) from OrderItem bean where bean.ordeR.member.id=:id and bean.ordeR.createTime like:time and bean.ordeR.status=4");

        q.setParameter("id", memberId).setString("time", "%" + year + "%");
        Double v1 = (Double) q.uniqueResult();
        if (v1 == null) {
            v1 = Double.valueOf(0.0D);
        }
        return new BigDecimal(v1.doubleValue());
    }

    public Integer[] getOrderByMember(Long memberId) {
        Long succOrder = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Order bean where bean.member.id=:id")
                        .setParameter("id", memberId).uniqueResult();
        Long failOrder = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Order bean where bean.member.id=:id")
                        .setParameter("id", memberId).uniqueResult();

        Long totalOrder = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Order bean where bean.member.id=:id")
                        .setParameter("id", memberId).uniqueResult();

        Long pendIngOrder = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Order bean where bean.member.id=:id")
                        .setParameter("id", memberId).uniqueResult();

        Long proceOrder = (Long)
                getSession()
                        .createQuery(
                                "select count(*) from Order bean where bean.member.id=:id")
                        .setParameter("id", memberId).uniqueResult();

        Integer[] orders = new Integer[5];
        orders[0] = Integer.valueOf(succOrder.intValue());
        orders[1] = Integer.valueOf(failOrder.intValue());
        orders[2] = Integer.valueOf(totalOrder.intValue());
        orders[3] = Integer.valueOf(pendIngOrder.intValue());
        orders[4] = Integer.valueOf(proceOrder.intValue());
        return orders;
    }

    public Pagination getOrderByReturn(Long memberId, Integer pageNo, Integer pageSize) {
        Finder f =
                Finder.create("from Order bean where bean.member.id=:id and bean.status=41");
        f.setParam("id", memberId);
        return find(f, pageNo.intValue(), pageSize.intValue());
    }

    public Order findById(Long id) {
        Finder entity = Finder.create("from Order bean where bean.id=:id");
        entity.setParam("id", id);
        List list = find(entity);
        if (list.size() == 1) {
            return (Order) list.get(0);
        }
        return null;
    }

    public Order findByCode(Long code) {
        Finder f = Finder.create("from Order bean where bean.code=:code")
                .setParam("code", code);
        List list = find(f);
        if (list.size() == 1) {
            return (Order) list.get(0);
        }
        return null;
    }

    public Order save(Order bean) {
        getSession().save(bean);
        return bean;
    }

    public Order deleteById(Long id) {
        Order entity = (Order) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    public Order updateById(Long id) {
        Order entity = (Order) super.get(id);
        if (entity != null) {
            entity.setDelStatus(Boolean.valueOf(false));
        }
        return entity;
    }

    protected Class<Order> getEntityClass() {
        return Order.class;
    }

    public List<Order> getCountByStatus(Date startTime, Date endTime, Integer status) {
        Finder finder =
                Finder.create("SELECT DATE_FORMAT(createTime, '%Y-%m-%d' ), COUNT(*) from Order bean where 1=1");

        if (startTime != null) {
            finder.append(" and bean.createTime>:startTime");
            finder.setParam("startTime", startTime);
        }
        if (endTime != null) {
            finder.append(" and bean.createTime<=:endTime");
            finder.setParam("endTime", endTime);
        }

        if (status != null) {
            finder.append(" and bean.status=:status");
            finder.setParam("status", status);
        }

        finder.append(" GROUP BY DATE_FORMAT( createTime, '%Y-%m-%d' )");

        return find(finder);
    }

    public List<Order> getCountByStatus1(Date startTime, Date endTime, Integer status) {
        Finder finder =
                Finder.create("SELECT DATE_FORMAT(createTime, '%Y-%m-%d' ), sum(bean.total) from Order bean where 1=1");

        if (startTime != null) {
            finder.append(" and bean.createTime>:startTime");
            finder.setParam("startTime", startTime);
        }
        if (endTime != null) {
            finder.append(" and bean.createTime<=:endTime");
            finder.setParam("endTime", endTime);
        }

        if (status != null) {
            finder.append(" and bean.status=:status");
            finder.setParam("status", status);
        }

        finder.append(" GROUP BY DATE_FORMAT( createTime, '%Y-%m-%d' )");

        return find(finder);
    }

    public List<Order> getStatisticByYear(Integer year, Integer status) {
        Finder finder =
                Finder.create("SELECT bean.createTime,COUNT(*) from Order bean where 1=1");
        if (status != null) {
            finder.append(" and bean.status=:status");
            finder.setParam("status", status);
        }
        if (year != null) {
            String y = year.toString();
            finder.append(" and DATE_FORMAT(bean.createTime,'%Y') =:year");
            finder.setParam("year", y);
        }
        finder.append(" GROUP BY DATE_FORMAT(bean.createTime,'%Y%m')");
        return find(finder);
    }

    public List<Order> getStatisticByYear1(Integer year, Integer status) {
        Finder finder =
                Finder.create("SELECT bean.createTime,sum(bean.total) from Order bean where 1=1");
        if (status != null) {
            finder.append(" and bean.status=:status");
            finder.setParam("status", status);
        }
        if (year != null) {
            String y = year.toString();
            finder.append(" and DATE_FORMAT(bean.createTime,'%Y') =:year");
            finder.setParam("year", y);
        }
        finder.append(" GROUP BY DATE_FORMAT(bean.createTime,'%Y%m')");
        return find(finder);
    }

    public List<Order> getOrderList(Long webId, Long memberId, String productName, String userName, Long paymentId, Long shippingId, Date startTime, Date endTime, Double startOrderTotal, Double endOrderTotal, Integer status, Long code, int firstResult, int maxResults) {
        Finder f = Finder.create("from Order bean where 1=1");
        if (webId != null) {
            f.append(" and bean.website.id=:webId");
            f.setParam("webId", webId);
        }
        if (memberId != null) {
            f.append(" and bean.member.id=:memberId");
            f.setParam("memberId", memberId);
        }
        if (!StringUtils.isBlank(productName)) {
            f.append(" and bean.productName like:productName");
            f.setParam("productName", "%" + productName + "%");
        }
        if (!StringUtils.isBlank("userName")) {
            f.append(" and bean.receiveName like:userName");
            f.setParam("userName", "%" + userName + "%");
        }
        if (paymentId != null) {
            f.append("and bean.payment.id=:paymentId");
            f.setParam("paymentId", paymentId);
        }
        if (shippingId != null) {
            f.append(" and bean.shipping.id=:shippingId");
            f.setParam("shippingId", shippingId);
        }
        if (startTime != null) {
            f.append(" and bean.createTime>:startTime");
            f.setParam("startTime", startTime);
        }
        if (endTime != null) {
            f.append(" and bean.createTime<:endTime");
            f.setParam("endTime", endTime);
        }
        if (startOrderTotal != null) {
            f.append(" and bean.total>=:startOrderTotal");
            f.setParam("startOrderTotal", startOrderTotal);
        }
        if (endOrderTotal != null) {
            f.append(" and bean.total<=:endOrderTotal");
            f.setParam("endOrderTotal", endOrderTotal);
        }
        if (status != null) {
            f.append(" and bean.status=:status");
            f.setParam("status", status);
        }
        f.setFirstResult(firstResult);
        f.setMaxResults(maxResults);
        f.append(" order by bean.id desc");
        return find(f);
    }

    public List<Object[]> findListByIds(Long[] ids) {
        String sql = "select t2.username,sum(1) from jc_shop_order t left join jc_core_member t1 on t1.member_id = t.member_id left join jc_core_user t2 on t1.user_id = t2.user_id where t1.member_id in (" +
                StringUtils.join(ids, ",") + ")  group by t2.username";
        return getSession().createSQLQuery(sql).list();
    }

    public BigDecimal getOrderSale(Date date, Long webId) {
        Finder finder = Finder.create("select sum(total) from Order bean where bean.createTime >=:startTime and bean.createTime <= :endTime and  bean.status = 4 and bean.delStatus = 1  and bean.website.id=:webId");
        finder.setParam("startTime", DateUtils.getStartDate(date));
        finder.setParam("endTime", DateUtils.getFinallyDate(date));
        finder.setParam("webId", webId);
        List list = find(finder);
        return new BigDecimal(list.get(0) != null ? ((Double) list.get(0)).doubleValue() : 0.0D);
    }

    public Long getOrderCount(Date date, Long webId) {
        Finder finder = Finder.create("select Count(1) from Order bean where bean.createTime >=:startTime and bean.createTime <= :endTime and bean.delStatus = 1 and bean.website.id=:webId ");
        finder.setParam("startTime", DateUtils.getStartDate(date));
        finder.setParam("endTime", DateUtils.getFinallyDate(date));
        finder.setParam("webId", webId);
        List list = find(finder);
        return (Long) list.get(0);
    }

    public Long getUnSendOrderCount(Long webId) {
        Finder finder = Finder.create("select Count(1) from Order bean where  bean.delStatus = 1  and bean.shippingStatus = 1 and   bean.website.id=:webId");
        finder.setParam("webId", webId);
        List list = find(finder);
        return (Long) list.get(0);
    }

    public Long getUnPayOrderCount(Long webId) {
        Finder finder = Finder.create("select Count(1) from Order bean where  bean.delStatus = 1  and bean.paymentStatus = 1 and bean.website.id=:webId");
        finder.setParam("webId", webId);
        List list = find(finder);
        return (Long) list.get(0);
    }

    public Long getReturnCount(Long webId) {
        Finder finder = Finder.create("select Count(1) from Order bean where  bean.returnOrder.id is not null and bean.returnOrder.status < 7   and bean.delStatus = 1 and bean.website.id=:webId ");
        finder.setParam("webId", webId);
        List list = find(finder);
        return (Long) list.get(0);
    }

    public List getOrderSale(Long webId, String type, String month, String year)
            throws ParseException {
        Finder finder = Finder.create("select sum(bean.total), count(bean.id) ");

        if ("month".equals(type))
            finder.append(" , day(bean.createTime)");
        else if ("year".equals(type)) {
            finder.append(" , month(bean.createTime)");
        }

        finder.append(" from Order bean  where bean.status = 4");

        Date date = new Date();

        if (type.equals("month")) {
            if (StringUtils.isNotEmpty(month)) {
                date = DateUtils.pasreToDate(month, DateUtils.COMMON_FORMAT_MONTH);
            }
            finder.append(" and bean.createTime >=:startTime");
            finder.setParam("startTime", DateUtils.getSpecficMonthStart(date, 0));
            finder.append(" and bean.createTime <=:endTime");
            finder.setParam("endTime", DateUtils.getSpecficMonthEnd(date, 0));
        } else if (type.equals("year")) {
            if (StringUtils.isNotEmpty(year)) {
                date = DateUtils.pasreToDate(year, DateUtils.COMMON_FORMAT_YEAR);
            }
            finder.append(" and bean.createTime >=:startTime");
            finder.setParam("startTime", DateUtils.getSpecficYearStart(date, 0));
            finder.append(" and bean.createTime <=:endTime");
            finder.setParam("endTime", DateUtils.getSpecficYearEnd(date, 0));
        }

        if ("month".equals(type))
            finder.append(" group by  day(bean.createTime)");
        else if ("year".equals(type)) {
            finder.append(" group by month(bean.createTime)");
        }
        return find(finder);
    }
}

