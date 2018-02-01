package com.mint.cms.manager.impl;

import com.mint.cms.dao.ProductFashionDao;
import com.mint.cms.entity.ProductFashion;
import com.mint.cms.manager.CategoryMng;
import com.mint.cms.manager.ProductFashionMng;
import com.mint.cms.manager.StandardMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.image.ImageScale;
import com.mint.common.page.Pagination;
import com.mint.common.web.springmvc.RealPathResolver;
import com.mint.core.manager.WebsiteMng;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductFashionMngImpl
        implements ProductFashionMng {

    @Autowired
    private ProductFashionDao dao;

    @Autowired
    private WebsiteMng websiteMng;

    @Autowired
    private RealPathResolver realPathResolver;

    @Autowired
    private CategoryMng categoryMng;

    @Autowired
    private ImageScale imageScale;

    @Autowired
    private StandardMng standardMng;

    public ProductFashion deleteById(Long id) {
        return this.dao.deleteById(id);
    }

    public ProductFashion[] deleteByIds(Long[] ids) {
        ProductFashion[] beans = new ProductFashion[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public ProductFashion findById(Long id) {
        return this.dao.findById(id);
    }

    public List<ProductFashion> findByProductId(Long productId) {
        return this.dao.findByProductId(productId);
    }

    public Pagination getPage(Long productId, int pageNo, int pageSize) {
        return this.dao.getPage(productId, pageNo, pageSize);
    }

    public ProductFashion save(ProductFashion bean, String[] arr) {
        String attitude = "";
        for (String a : arr) {
            attitude = attitude + " " + this.standardMng.findById(Long.valueOf(Long.parseLong(a))).getName();
        }
        bean.setAttitude(attitude);
        bean.init();
        return this.dao.save(bean);
    }

    public void addStandard(ProductFashion bean, String[] arr) {
        for (String a : arr)
            bean.addTostandards(this.standardMng.findById(Long.valueOf(Long.parseLong(a))));
    }

    public void updateStandard(ProductFashion bean, String[] arr) {
        bean.getStandards().clear();
        for (String a : arr)
            bean.addTostandards(this.standardMng.findById(Long.valueOf(Long.parseLong(a))));
    }

    public void deleteImage(ProductFashion entity, String uploadPath) {
    }

    public ProductFashion update(ProductFashion bean, String[] arr) {
        String attitude = "";
        for (String a : arr) {
            attitude = attitude + " " + this.standardMng.findById(Long.valueOf(Long.parseLong(a))).getName();
        }
        bean.setAttitude(attitude);
        Updater updater = new Updater(bean);
        ProductFashion entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ProductFashion update(ProductFashion bean) {
        Updater updater = new Updater(bean);
        ProductFashion entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public Pagination productLack(Integer status, Integer count, int pageNo, int pageSize) {
        return this.dao.productLack(status, count, pageNo, pageSize);
    }

    public Integer productLackCount(Integer status, Integer count) {
        return this.dao.productLackCount(status, count);
    }

    public Pagination getSaleTopPage(int pageNo, int pageSize) {
        return this.dao.getSaleTopPage(pageNo, pageSize);
    }

    public Boolean getOneFash(Long productId) {
        return this.dao.getOneFashion(productId);
    }
}

