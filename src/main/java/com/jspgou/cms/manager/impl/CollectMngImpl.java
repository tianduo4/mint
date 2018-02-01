package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.CollectDao;
import com.jspgou.cms.entity.Collect;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.CollectMng;
import com.jspgou.cms.manager.ProductFashionMng;
import com.jspgou.cms.manager.ProductMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CollectMngImpl
        implements CollectMng {

    @Autowired
    private ProductMng productMng;

    @Autowired
    private ProductFashionMng fashionMng;

    @Autowired
    private CollectDao dao;

    @Transactional(readOnly = true)
    public Pagination getList(Integer pageSize, Integer pageNo, Long memberId) {
        Pagination list = this.dao.getList(pageSize, pageNo, memberId);
        return list;
    }

    @Transactional(readOnly = true)
    public Collect findById(Integer id) {
        Collect entity = this.dao.findById(id);
        return entity;
    }

    public Collect save(ShopMember bean, Long productId, Long fashionId) {
        Collect entity = new Collect();
        entity.setProduct(this.productMng.findById(productId));
        if (fashionId != null) {
            entity.setFashion(this.fashionMng.findById(fashionId));
        }
        entity.setMember(bean);
        entity.setTime(new Date());
        this.dao.save(entity);
        return entity;
    }

    public List<Collect> getList(Long memberId, int firstResult, int maxResults) {
        return this.dao.getList(memberId, firstResult, maxResults);
    }

    public List<Collect> getList(Long productId, Long memberId) {
        return this.dao.getList(productId, memberId);
    }

    public Collect update(Collect bean, Long pTypeid) {
        Updater updater = new Updater(bean);
        Collect entity = this.dao.updateByUpdater(updater);

        return entity;
    }

    public List<Collect> findByProductId(Long productId) {
        return this.dao.findByProductId(productId);
    }

    public Collect findByProductFashionId(Long id) {
        return this.dao.findByProductFashionId(id);
    }

    public Collect deleteById(Integer id) {
        Collect entity = findById(id);

        entity = this.dao.deleteById(id);
        return entity;
    }

    public Collect[] deleteByIds(Integer[] ids) {
        Collect[] beans = new Collect[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public void deleteByMemberId(Long memberId) {
        this.dao.deleteByMemberId(memberId);
    }
}

