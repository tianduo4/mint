package com.mint.cms.manager.impl;

import com.mint.cms.dao.ProductStandardDao;
import com.mint.cms.entity.ProductStandard;
import com.mint.cms.manager.ProductStandardMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductStandardMngImpl
        implements ProductStandardMng {
    private ProductStandardDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ProductStandard findById(Long id) {
        ProductStandard entity = this.dao.findById(id);
        return entity;
    }

    public ProductStandard findByProductIdAndStandardId(Long productId, Long standardId) {
        List list = this.dao.findByProductIdAndStandardId(productId, standardId);
        if (list.size() > 0) {
            return (ProductStandard) list.get(0);
        }
        return null;
    }

    public List<ProductStandard> findByProductId(Long productId) {
        return this.dao.findByProductId(productId);
    }

    public ProductStandard save(ProductStandard bean) {
        this.dao.save(bean);
        return bean;
    }

    public ProductStandard update(ProductStandard bean) {
        Updater updater = new Updater(bean);
        ProductStandard entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ProductStandard deleteById(Long id) {
        ProductStandard bean = this.dao.deleteById(id);
        return bean;
    }

    public ProductStandard[] deleteByIds(Long[] ids) {
        ProductStandard[] beans = new ProductStandard[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ProductStandardDao dao) {
        this.dao = dao;
    }
}

