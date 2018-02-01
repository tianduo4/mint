package com.mint.cms.manager.impl;

import com.mint.cms.dao.ProductTypePropertyDao;
import com.mint.cms.entity.ProductType;
import com.mint.cms.entity.ProductTypeProperty;
import com.mint.cms.manager.ProductTypePropertyMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductTypePropertyMngImpl
        implements ProductTypePropertyMng {

    @Autowired
    private ProductTypePropertyDao dao;

    public ProductTypeProperty deleteById(Long id) {
        return this.dao.deleteById(id);
    }

    public ProductTypeProperty[] deleteByIds(Long[] ids) {
        ProductTypeProperty[] beans = new ProductTypeProperty[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public ProductTypeProperty findById(Long id) {
        return this.dao.findById(id);
    }

    public Pagination getList(int pageNo, int pageSize, Long typeid, Boolean isCategory, String searchType, String searchContent) {
        return this.dao.getList(pageNo, pageSize, typeid, isCategory, searchType, searchContent);
    }

    public List<ProductTypeProperty> getList(Long typeId, Boolean isCategory) {
        return this.dao.getList(typeId, isCategory);
    }

    public void saveList(List<ProductTypeProperty> list) {
        for (ProductTypeProperty item : list)
            save(item);
    }

    public ProductTypeProperty save(ProductTypeProperty bean) {
        return this.dao.save(bean);
    }

    public void updatePriority(Long[] wids, Integer[] sort, String[] propertyName, Boolean[] single) {
        int i = 0;
        for (int len = wids.length; i < len; i++) {
            ProductTypeProperty item = findById(wids[i]);
            item.setPropertyName(propertyName[i]);
            item.setSort(sort[i]);
            item.setSingle(single[i]);
        }
    }

    public List<ProductTypeProperty> findBySearch(String searchType, String searchContent) {
        return this.dao.findBySearch(searchType, searchContent);
    }

    public List<ProductTypeProperty> findListByTypeId(Long typeid) {
        return this.dao.findListByTypeId(typeid);
    }

    public List<ProductTypeProperty> getList(Long typeId, boolean isCategory) {
        return this.dao.getList(typeId, isCategory);
    }

    public ProductTypeProperty update(ProductTypeProperty bean) {
        Updater updater = new Updater(bean);
        ProductTypeProperty entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public List<ProductTypeProperty> getItems(ProductType model, boolean isCategory, String[] fields, String[] propertyNames, Integer[] dataTypes, Integer[] sorts, Boolean[] singles) {
        return this.dao.getItems(model, isCategory, fields, propertyNames, dataTypes, sorts, singles);
    }
}

