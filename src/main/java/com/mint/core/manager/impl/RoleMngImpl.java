package com.mint.core.manager.impl;

import com.mint.common.hibernate4.Updater;
import com.mint.core.dao.RoleDao;
import com.mint.core.entity.Role;
import com.mint.core.manager.RoleMng;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleMngImpl
        implements RoleMng {
    private RoleDao dao;

    @Transactional(readOnly = true)
    public List<Role> getList() {
        return this.dao.getList();
    }

    @Transactional(readOnly = true)
    public Role findById(Integer id) {
        Role entity = this.dao.findById(id);
        return entity;
    }

    public Role save(Role bean, Set<String> perms) {
        bean.setPerms(perms);
        this.dao.save(bean);
        return bean;
    }

    public Role update(Role bean, Set<String> perms) {
        Updater updater = new Updater(bean);
        bean = this.dao.updateByUpdater(updater);
        bean.getPerms().clear();
        bean.setPerms(perms);
        return bean;
    }

    public Role deleteById(Integer id) {
        Role bean = this.dao.deleteById(id);
        return bean;
    }

    public Role[] deleteByIds(Integer[] ids) {
        Role[] beans = new Role[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(RoleDao dao) {
        this.dao = dao;
    }
}

