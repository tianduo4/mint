package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ShopMemberGroupDao;
import com.jspgou.cms.entity.ShopMemberGroup;
import com.jspgou.cms.manager.ShopMemberGroupMng;
import com.jspgou.common.hibernate4.Updater;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopMemberGroupMngImpl
        implements ShopMemberGroupMng {
    private ShopMemberGroupDao dao;

    public ShopMemberGroup findGroupByScore(Long webId, int score) {
        List groupList = this.dao.getList(webId, true);
        int size = groupList.size();
        if (size < 1)
            throw new IllegalStateException(
                    "ShopMmeberGroup not found in website id=" + webId);
        if (size == 1) {
            return (ShopMemberGroup) groupList.get(0);
        }
        ShopMemberGroup group = (ShopMemberGroup) groupList.get(0);

        for (int i = size - 1; i > 0; i--) {
            ShopMemberGroup temp = (ShopMemberGroup) groupList.get(i);
            if (score > temp.getScore().intValue()) {
                group = temp;
                break;
            }
        }
        return group;
    }

    @Transactional(readOnly = true)
    public List<ShopMemberGroup> getList(Long webId) {
        return this.dao.getList(webId, false);
    }

    @Transactional(readOnly = true)
    public List<ShopMemberGroup> getList() {
        return this.dao.getList();
    }

    @Transactional(readOnly = true)
    public ShopMemberGroup findById(Long id) {
        ShopMemberGroup entity = this.dao.findById(id);
        return entity;
    }

    public ShopMemberGroup save(ShopMemberGroup bean) {
        this.dao.save(bean);
        return bean;
    }

    public ShopMemberGroup update(ShopMemberGroup bean) {
        Updater updater = new Updater(bean);
        ShopMemberGroup entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ShopMemberGroup deleteById(Long id) {
        ShopMemberGroup bean = this.dao.deleteById(id);
        return bean;
    }

    public ShopMemberGroup[] deleteByIds(Long[] ids) {
        ShopMemberGroup[] beans = new ShopMemberGroup[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(ShopMemberGroupDao dao) {
        this.dao = dao;
    }
}

