package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ExendedItemDao;
import com.jspgou.cms.entity.ExendedItem;
import com.jspgou.cms.manager.ExendedItemMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExendedItemMngImpl
        implements ExendedItemMng {
    private ExendedItemDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ExendedItem findById(Long id) {
        ExendedItem entity = this.dao.findById(id);
        return entity;
    }

    public ExendedItem save(ExendedItem item) {
        return this.dao.save(item);
    }

    public ExendedItem update(ExendedItem item) {
        Updater updater = new Updater(item);
        ExendedItem entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ExendedItem deleteById(Long id) {
        ExendedItem bean = this.dao.deleteById(id);
        return bean;
    }

    public ExendedItem[] deleteByIds(Long[] ids) {
        ExendedItem[] beans = new ExendedItem[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ExendedItemDao dao) {
        this.dao = dao;
    }
}

