package com.mint.cms.manager.impl;

import com.mint.cms.dao.DiscussDao;
import com.mint.cms.entity.Discuss;
import com.mint.cms.manager.DiscussMng;
import com.mint.cms.manager.ProductMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DiscussMngImpl
        implements DiscussMng {

    @Autowired
    private ShopMemberMng memberMng;
    private ProductMng productMng;
    private DiscussDao dao;

    @Transactional(readOnly = true)
    public Discuss findById(Long id) {
        Discuss entity = this.dao.findById(id);
        return entity;
    }

    public Discuss saveOrUpdate(Long productId, String content, Long memberId, String discussType) {
        Discuss bean = new Discuss();
        bean.setContent(content);
        bean.setDiscussType(discussType);
        bean.setMember(this.memberMng.findById(memberId));
        bean.setProduct(this.productMng.findById(productId));
        bean.setTime(new Date());
        this.dao.saveOrUpdate(bean);
        return bean;
    }

    public Pagination getPage(Long memberId, Long productId, String discussType, String userName, String productName, Date startTime, Date endTime, int pageNo, int pageSize, boolean cache) {
        return this.dao.getPageByProduct(memberId, productId, discussType, userName, productName,
                startTime, endTime, pageNo, pageSize, cache);
    }

    public Pagination getPageByMember(Long memberId, int pageNo, int pageSize, boolean cache) {
        return this.dao.getPageByMember(memberId, pageNo, pageSize, cache);
    }

    public List<Discuss> findByType(Long productId, String discussType) {
        return this.dao.findByType(productId, discussType);
    }

    public Discuss update(Discuss entity) {
        Updater updater = new Updater(entity);
        entity = this.dao.updateByUpdater(updater);
        return this.dao.update(entity);
    }

    public Discuss[] deleteByIds(Long[] ids) {
        Discuss[] beans = new Discuss[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public Discuss deleteById(Long id) {
        Discuss bean = this.dao.deleteById(id);
        return bean;
    }

    @Autowired
    public void setProductMng(ProductMng productMng) {
        this.productMng = productMng;
    }

    @Autowired
    public void setDao(DiscussDao dao) {
        this.dao = dao;
    }

    public void deleteByMemberId(Long memberId) {
        this.dao.deleteByMemberId(memberId);
    }
}

