package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.RelatedgoodsDao;
import com.jspgou.cms.entity.Relatedgoods;
import com.jspgou.cms.manager.RelatedgoodsMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RelatedgoodsMngImpl
        implements RelatedgoodsMng {
    private RelatedgoodsDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    public List<Relatedgoods> findByIdProductId(Long productId) {
        List entity = this.dao.findByIdProductId(productId);
        return entity;
    }

    @Transactional(readOnly = true)
    public Relatedgoods findById(Long id) {
        Relatedgoods entity = this.dao.findById(id);
        return entity;
    }

    public Relatedgoods save(Relatedgoods bean) {
        this.dao.save(bean);
        return bean;
    }

    public Relatedgoods update(Relatedgoods bean) {
        Updater updater = new Updater(bean);
        Relatedgoods entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public Relatedgoods deleteById(Long id) {
        Relatedgoods bean = this.dao.deleteById(id);
        return bean;
    }

    public Relatedgoods[] deleteByIds(Long[] ids) {
        Relatedgoods[] beans = new Relatedgoods[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public void addProduct(Long id, Long[] productIds) {
        if (productIds != null)
            for (Long productId : productIds)
                if (productId != null) {
                    Relatedgoods r = new Relatedgoods();
                    r.setProductId(id);
                    r.setProductIds(productId);
                    this.dao.save(r);
                }
    }

    public void updateProduct(Long id, Long[] productIds) {
        List Relatedgoods = this.dao.findByIdProductId(id);
        for (int i = 0; i < Relatedgoods.size(); i++) {
            deleteById(((Relatedgoods) Relatedgoods.get(i)).getId());
        }
        if (productIds != null)
            for (Long productId : productIds)
                if (productId != null) {
                    Relatedgoods r = new Relatedgoods();
                    r.setProductId(id);
                    r.setProductIds(productId);
                    this.dao.save(r);
                }
    }

    public void deleteProduct(Long productid) {
        List Relatedgoods = this.dao.findByIdProductId(productid);
        for (int i = 0; i < Relatedgoods.size(); i++)
            deleteById(((Relatedgoods) Relatedgoods.get(i)).getId());
    }

    @Autowired
    public void setDao(RelatedgoodsDao dao) {
        this.dao = dao;
    }
}

