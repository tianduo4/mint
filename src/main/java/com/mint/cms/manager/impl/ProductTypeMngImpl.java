package com.mint.cms.manager.impl;

import com.mint.cms.dao.ProductTypeDao;
import com.mint.cms.entity.ProductType;
import com.mint.cms.manager.BrandMng;
import com.mint.cms.manager.ProductTypeMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductTypeMngImpl
        implements ProductTypeMng {
    private BrandMng brandMng;
    private ProductTypeDao dao;

    @Transactional(readOnly = true)
    public List<ProductType> getList(Long webId) {
        return this.dao.getList(webId);
    }

    @Transactional(readOnly = true)
    public ProductType findById(Long id) {
        ProductType entity = this.dao.findById(id);
        return entity;
    }

    public ProductType save(ProductType bean) {
        this.dao.save(bean);
        return bean;
    }

    public ProductType update(ProductType bean) {
        ProductType entity = this.dao
                .updateByUpdater(new Updater(bean));
        return entity;
    }

    public ProductType deleteById(Long id) {
        ProductType entity = this.dao.deleteById(id);
        return entity;
    }

    public ProductType[] deleteByIds(Long[] ids) {
        ProductType[] beans = new ProductType[ids.length];
        for (int i = 0; i < ids.length; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setBrandMng(BrandMng brandMng) {
        this.brandMng = brandMng;
    }

    @Autowired
    public void setDao(ProductTypeDao dao) {
        this.dao = dao;
    }

    public Pagination getPage(Long webId, int pageNo, int pageSize) {
        return this.dao.getPage(webId, pageNo, pageSize);
    }
}

