package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.PopularityGroupDao;
import com.jspgou.cms.entity.PopularityGroup;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.manager.PopularityGroupMng;
import com.jspgou.cms.manager.ProductMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PopularityGroupMngImpl
        implements PopularityGroupMng {
    private PopularityGroupDao dao;

    @Autowired
    private ProductMng productMng;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public PopularityGroup findById(Long id) {
        PopularityGroup entity = null;
        if (id != null) {
            entity = this.dao.findById(id);
        }
        return entity;
    }

    public PopularityGroup save(PopularityGroup bean) {
        bean.init();
        this.dao.save(bean);
        return bean;
    }

    public PopularityGroup update(PopularityGroup bean) {
        Updater updater = new Updater(bean);
        PopularityGroup entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public PopularityGroup deleteById(Long id) {
        PopularityGroup bean = findById(id);
        bean.getProducts().clear();
        this.dao.deleteById(id);
        return bean;
    }

    public PopularityGroup[] deleteByIds(Long[] ids) {
        PopularityGroup[] beans = new PopularityGroup[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public void addProduct(PopularityGroup bean, Long[] productIds) {
        double price = 0.0D;
        if (productIds != null) {
            for (Long productId : productIds) {
                bean.addToProducts(this.productMng.findById(productId));
                price += this.productMng.findById(productId).getPrice().doubleValue();
            }
        }
        bean.setPrice(Double.valueOf(price));
    }

    public void updateProduct(PopularityGroup bean, Long[] productIds) {
        double price = 0.0D;
        bean.getProducts().clear();
        if (productIds != null) {
            for (Long productId : productIds) {
                bean.addToProducts(this.productMng.findById(productId));
                price += this.productMng.findById(productId).getPrice().doubleValue();
            }
        }
        bean.setPrice(Double.valueOf(price));
    }

    @Autowired
    public void setDao(PopularityGroupDao dao) {
        this.dao = dao;
    }
}

