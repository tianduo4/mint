package com.mint.cms.manager.impl;

import com.mint.cms.dao.AdspaceDao;
import com.mint.cms.entity.Adspace;
import com.mint.cms.manager.AdspaceMng;
import com.mint.common.hibernate4.Updater;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdspaceMngImpl
        implements AdspaceMng {

    @Autowired
    private AdspaceDao dao;

    public Adspace deleteById(Integer id) {
        return this.dao.deleteById(id);
    }

    public Adspace[] deleteByIds(Integer[] ids) {
        Adspace[] beans = new Adspace[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public Adspace findById(Integer id) {
        return this.dao.findById(id);
    }

    public List<Adspace> getList() {
        return this.dao.getList();
    }

    public Adspace save(Adspace bean) {
        return this.dao.save(bean);
    }

    public Adspace updateByAdspacenumb(Integer AdspaceId, Integer AdspaceNumb, Integer shopMemberId) {
        return null;
    }

    public Adspace updateByUpdater(Adspace bean) {
        Updater updater = new Updater(bean);
        return this.dao.updateByUpdater(updater);
    }
}

