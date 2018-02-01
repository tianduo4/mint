package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ExendedDao;
import com.jspgou.cms.entity.Exended;
import com.jspgou.cms.entity.ProductType;
import com.jspgou.cms.manager.ExendedMng;
import com.jspgou.cms.manager.ProductTypeMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExendedMngImpl
        implements ExendedMng {
    private ExendedDao dao;

    @Autowired
    private ProductTypeMng productTypeMng;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public Exended findById(Long id) {
        Exended entity = this.dao.findById(id);
        return entity;
    }

    public List<Exended> getList() {
        return this.dao.getList();
    }

    public List<Exended> getList(Long typeId) {
        return this.dao.getList(typeId);
    }

    public Exended save(Exended bean, Long[] typeIds) {
        bean = this.dao.save(bean);
        if ((typeIds != null) && (typeIds.length > 0)) {
            for (Long tid : typeIds) {
                this.productTypeMng.findById(tid).addToexendeds(bean);
            }
        }
        return bean;
    }

    public Exended update(Exended bean, Long[] typeIds) {
        Updater updater = new Updater(bean);
        Exended entity = this.dao.updateByUpdater(updater);
        Set<ProductType> types = entity.getProductTypes();
        for (ProductType type : types) {
            type.removeFromExendeds(entity);
        }
        types.clear();
        if ((typeIds != null) && (typeIds.length > 0)) {
            for (Long tid : typeIds) {
                this.productTypeMng.findById(tid).addToexendeds(entity);
            }
        }
        return entity;
    }

    public Exended deleteById(Long id) {
        Exended bean = this.dao.deleteById(id);
        return bean;
    }

    public Exended[] deleteByIds(Long[] ids) {
        Exended[] beans = new Exended[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public Exended[] updatePriority(Long[] wids, Integer[] priority) {
        int len = wids.length;
        Exended[] beans = new Exended[len];
        for (int i = 0; i < len; i++) {
            beans[i] = findById(wids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ExendedDao dao) {
        this.dao = dao;
    }
}

