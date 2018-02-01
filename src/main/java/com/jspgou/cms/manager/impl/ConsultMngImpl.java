package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ConsultDao;
import com.jspgou.cms.entity.Consult;
import com.jspgou.cms.manager.ConsultMng;
import com.jspgou.cms.manager.ProductMng;
import com.jspgou.cms.manager.ShopMemberMng;
import com.jspgou.common.page.Pagination;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConsultMngImpl
        implements ConsultMng {

    @Autowired
    private ShopMemberMng memberMng;
    private ProductMng productMng;
    private ConsultDao dao;

    @Transactional(readOnly = true)
    public Consult findById(Long id) {
        Consult entity = this.dao.findById(id);
        return entity;
    }

    public Consult saveOrUpdate(Long productId, String content, Long memberId) {
        Consult bean = new Consult();
        bean.setConsult(content);
        bean.setMember(this.memberMng.findById(memberId));
        bean.setTime(new Date());
        bean.setProduct(this.productMng.findById(productId));
        Consult consult = this.dao.getSameConsult(memberId);
        Long time = Long.valueOf(System.currentTimeMillis());
        if (consult == null) {
            return this.dao.saveOrUpdate(bean);
        }
        if ((time.longValue() - consult.getTime().getTime()) / 1000L < 30L) {
            return null;
        }
        return this.dao.saveOrUpdate(bean);
    }

    public List<Consult> findByProductId(Long productId) {
        return this.dao.findByProductId(productId);
    }

    public Pagination getPageByMember(Long memberId, int pageNo, int pageSize, boolean cache) {
        return this.dao.getPageByMember(memberId, pageNo, pageSize, cache);
    }

    public Pagination getPage(Long productId, String userName, String productName, Date startTime, Date endTime, int pageNo, int pageSize, Boolean cache) {
        return this.dao.getPage(productId, userName, productName, startTime, endTime, pageNo, pageSize, cache.booleanValue());
    }

    public Pagination getVisiblePage(String userName, String productName, Date startTime, Date endTime, int pageNo, int pageSize) {
        return this.dao.getVisiblePage(userName, productName, startTime, endTime, pageNo, pageSize);
    }

    public Consult update(Consult Consult) {
        return this.dao.update(Consult);
    }

    public Consult deleteById(Long id) {
        Consult bean = this.dao.deleteById(id);
        return bean;
    }

    public Consult[] deleteByIds(Long[] ids) {
        Consult[] beans = new Consult[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public Long getProductConsult() {
        return this.dao.getProductConsult();
    }

    @Autowired
    public void setProductMng(ProductMng productMng) {
        this.productMng = productMng;
    }

    @Autowired
    public void setDao(ConsultDao dao) {
        this.dao = dao;
    }

    public void deleteByMemberId(Long memberId) {
        this.dao.deleteByMemberId(memberId);
    }
}

