package com.mint.cms.manager.impl;

import com.mint.cms.dao.OrderItemDao;
import com.mint.cms.entity.OrderItem;
import com.mint.cms.manager.OrderItemMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderItemMngImpl
        implements OrderItemMng {

    @Autowired
    private OrderItemDao dao;

    public List<Object[]> profitTop(Long ctgid, Long typeid, Integer pageNo, Integer pageSize) {
        return this.dao.profitTop(ctgid, typeid, pageNo, pageSize);
    }

    public Integer totalCount(Long ctgid, Long typeid) {
        return this.dao.totalCount(ctgid, typeid);
    }

    public OrderItem findById(Long id) {
        return this.dao.findById(id);
    }

    public OrderItem[] findById(Long[] ids) {
        OrderItem[] beans = new OrderItem[ids.length];
        for (int i = 0; i < ids.length; i++) {
            beans[i] = findById(ids[i]);
        }
        return beans;
    }

    public OrderItem updateByUpdater(OrderItem bean) {
        Updater updater = new Updater(bean);
        return this.dao.updateByUpdater(updater);
    }

    public OrderItem[] updateByUpdaters(OrderItem[] item) {
        OrderItem[] beans = new OrderItem[item.length];
        for (int i = 0; i < item.length; i++) {
            beans[i] = updateByUpdater(item[i]);
        }
        return beans;
    }

    public Pagination getPageByMember(Integer status, Long memberId, int pageNo, int pageSize) {
        return this.dao.getPageForMember(memberId, status, pageNo, pageSize);
    }

    public List<Object[]> getOrderItem(Date endTime, Date beginTime) {
        List orderItemList = this.dao.getOrderItem(endTime, beginTime);
        return orderItemList;
    }

    public OrderItem findByMember(Long memberId, Long productId, Long orderId) {
        return this.dao.findByMember(memberId, productId, orderId);
    }

    public Pagination getOrderItem(Integer pageNo, Integer pageSize, Long productId) {
        return this.dao.getPageForProuct(productId, pageNo.intValue(), pageSize.intValue());
    }

    public Pagination getPageProductSaleRank(Long webId, String type, Integer categoryId, int pageNo, int pageSize, Date startTime, Date endTime) {
        return this.dao.getPageProductSaleRank(webId, type, categoryId, pageNo, pageSize, startTime, endTime);
    }
}

