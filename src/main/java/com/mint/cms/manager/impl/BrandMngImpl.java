package com.mint.cms.manager.impl;

import com.mint.cms.dao.BrandDao;
import com.mint.cms.entity.Brand;
import com.mint.cms.entity.Category;
import com.mint.cms.manager.BrandMng;
import com.mint.cms.manager.BrandTextMng;
import com.mint.cms.manager.CategoryMng;
import com.mint.cms.manager.ProductTypeMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BrandMngImpl
        implements BrandMng {
    private ProductTypeMng productTypeMng;
    private BrandTextMng brandTextMng;

    @Autowired
    private CategoryMng categoryMng;
    private BrandDao dao;

    @Transactional(readOnly = true)
    public List<Brand> getAllList() {
        List list = this.dao.getAllList();
        return list;
    }

    public List<Brand> getList() {
        return this.dao.getList();
    }

    public List<Brand> getListForTag(Long webId, int firstResult, int maxResults) {
        return this.dao.getList(webId, firstResult, maxResults, true);
    }

    public Brand getsiftBrand() {
        return this.dao.getsiftBrand();
    }

    @Transactional(readOnly = true)
    public Brand findById(Long id) {
        Brand entity = this.dao.findById(id);
        return entity;
    }

    public Brand save(Brand bean, String text) {
        Brand entity = this.dao.save(bean);
        this.brandTextMng.save(entity, text);
        return entity;
    }

    public Brand update(Brand bean, String text) {
        Updater updater = new Updater(bean);
        Brand entity = this.dao.updateByUpdater(updater);

        entity.getBrandText().setText(text);
        return entity;
    }

    public Brand deleteById(Long id) {
        Brand entity = findById(id);
        entity = this.dao.deleteById(id);
        return entity;
    }

    public Brand[] deleteByIds(Long[] ids) {
        Brand[] beans = new Brand[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public Brand[] updatePriority(Long[] ids, Integer[] priority) {
        Brand[] beans = new Brand[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = findById(ids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    public boolean brandNameNotExist(String brandName) {
        return this.dao.countByBrandName(brandName) <= 0;
    }

    @Autowired
    public void setProductTypeMng(ProductTypeMng productTypeMng) {
        this.productTypeMng = productTypeMng;
    }

    @Autowired
    public void setBrandTextMng(BrandTextMng brandTextMng) {
        this.brandTextMng = brandTextMng;
    }

    @Autowired
    public void setDao(BrandDao dao) {
        this.dao = dao;
    }

    public Pagination getPage(String name, String brandtype, int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(name, brandtype, pageNo, pageSize);
        return page;
    }

    public List<Brand> getBrandList(String name) {
        return this.dao.getBrandList(name);
    }

    public List<Brand> getListByCateoryId(Integer cateoryId) {
        Category cate = this.categoryMng.findById(cateoryId);
        List list = new ArrayList(cate.getBrands());
        return list;
    }
}

