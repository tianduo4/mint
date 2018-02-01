package com.mint.cms.manager.impl;

import com.mint.cms.dao.OrderReturnDao;
import com.mint.cms.entity.Order;
import com.mint.cms.entity.OrderReturn;
import com.mint.cms.manager.OrderMng;
import com.mint.cms.manager.OrderReturnMng;
import com.mint.cms.manager.ShopDictionaryMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderReturnMngImpl
        implements OrderReturnMng {

    @Autowired
    private OrderMng orderMng;

    @Autowired
    private OrderReturnDao dao;

    @Autowired
    private ShopDictionaryMng shopDictionaryMng;

    @Transactional(readOnly = true)
    public Pagination getPage(Integer status, int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(status, pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public OrderReturn findById(Long id) {
        OrderReturn entity = this.dao.findById(id);
        return entity;
    }

    public OrderReturn findByOrderId(Long orderId) {
        List list = this.dao.findByOrderId(orderId);
        if (list.size() > 0) {
            return (OrderReturn) list.get(0);
        }
        return null;
    }

    public OrderReturn save(OrderReturn bean) {
        this.dao.save(bean);
        return bean;
    }

    public OrderReturn save(OrderReturn bean, Order order, Long reasonId, Boolean delivery, String[] picPaths, String[] picDescs) {
        bean.setOrder(order);
        bean.setShopDictionary(this.shopDictionaryMng.findById(reasonId));
        if (delivery.booleanValue())
            bean.setReturnType(Integer.valueOf(OrderReturn.OrderReturnStatus.SELLER_NODELIVERY_RETURN.getValue()));
        else {
            bean.setReturnType(Integer.valueOf(OrderReturn.OrderReturnStatus.SELLER_DELIVERY_RETURN.getValue()));
        }
        Long date = Long.valueOf(new Date().getTime() + order.getId().longValue());
        bean.setCode(String.valueOf(date));
        bean.setStatus(Integer.valueOf(1));
        bean.setCreateTime(new Date());

        if ((picPaths != null) && (picPaths.length > 0)) {
            int i = 0;
            for (int len = picPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(picPaths[i])) {
                    bean.addToPictures(picPaths[i], picDescs[i]);
                }
            }
        }
        bean = this.dao.save(bean);
        return bean;
    }

    public OrderReturn update(OrderReturn bean) {
        Updater updater = new Updater(bean);
        OrderReturn entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public OrderReturn deleteById(Long id) {
        Order order = this.dao.findById(id).getOrder();
        order.setReturnOrder(null);
        this.orderMng.updateByUpdater(order);
        OrderReturn bean = this.dao.deleteById(id);
        return bean;
    }

    public OrderReturn[] deleteByIds(Long[] ids) {
        OrderReturn[] beans = new OrderReturn[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(OrderReturnDao dao) {
        this.dao = dao;
    }
}

