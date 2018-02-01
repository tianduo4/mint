package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.StandardTypeDao;
import com.jspgou.cms.entity.Standard;
import com.jspgou.cms.entity.StandardType;
import com.jspgou.cms.manager.StandardMng;
import com.jspgou.cms.manager.StandardTypeMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StandardTypeMngImpl
        implements StandardTypeMng {

    @Autowired
    private StandardMng standardMng;
    private StandardTypeDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    public StandardType getByField(String field) {
        return this.dao.getByField(field);
    }

    @Transactional(readOnly = true)
    public StandardType findById(Long id) {
        StandardType entity = this.dao.findById(id);
        return entity;
    }

    public List<StandardType> getList() {
        return this.dao.getList();
    }

    public List<StandardType> getList(Integer categoryId) {
        return this.dao.getList(categoryId);
    }

    public StandardType save(StandardType bean) {
        bean = this.dao.save(bean);
        return bean;
    }

    public StandardType update(StandardType bean) {
        Updater updater = new Updater(bean);
        StandardType entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public StandardType deleteById(Long id) {
        StandardType bean = this.dao.deleteById(id);
        return bean;
    }

    public StandardType[] deleteByIds(Long[] ids) {
        StandardType[] beans = new StandardType[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public StandardType[] updatePriority(Long[] wids, Integer[] priority) {
        int len = wids.length;
        StandardType[] beans = new StandardType[len];
        for (int i = 0; i < len; i++) {
            beans[i] = findById(wids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    public StandardType addStandard(StandardType bean, String[] itemName, String[] itemColor, Integer[] itemPriority) {
        if (itemName != null) {
            int i = 0;
            for (int len = itemName.length; i < len; i++) {
                if (!StringUtils.isBlank(itemName[i])) {
                    Standard item = new Standard();
                    item.setName(itemName[i]);
                    item.setColor(itemColor[i]);
                    item.setPriority(itemPriority[i]);
                    item.setType(bean);
                    this.standardMng.save(item);
                }
            }
        }
        return bean;
    }

    public StandardType updateStandard(StandardType bean, Long[] itemId, String[] itemName, String[] itemColor, Integer[] itemPriority) {
        Set<Standard> set = bean.getStandardSet();
        if (itemId != null) {
            for (Standard s : set) {
                if (!Arrays.asList(itemId).contains(s.getId()))
                    this.standardMng.deleteById(s.getId());
            }
        } else {
            for (Standard s : set) {
                this.standardMng.deleteById(s.getId());
            }
        }

        if (itemName != null) {
            int i = 0;
            for (int len = itemName.length; i < len; i++) {
                if (!StringUtils.isBlank(itemName[i])) {
                    if ((itemId != null) && (i < itemId.length)) {
                        Standard item = this.standardMng.findById(itemId[i]);
                        item.setName(itemName[i]);
                        item.setColor(itemColor[i]);
                        item.setPriority(itemPriority[i]);
                        item.setType(bean);
                        this.standardMng.update(item);
                    } else {
                        Standard item = new Standard();
                        item.setName(itemName[i]);
                        item.setColor(itemColor[i]);
                        item.setPriority(itemPriority[i]);
                        item.setType(bean);
                        this.standardMng.save(item);
                    }
                }
            }
        }
        return bean;
    }

    @Autowired
    public void setDao(StandardTypeDao dao) {
        this.dao = dao;
    }
}

