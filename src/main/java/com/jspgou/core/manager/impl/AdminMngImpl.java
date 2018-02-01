package com.jspgou.core.manager.impl;

import com.jspgou.cms.manager.ShopMemberGroupMng;
import com.jspgou.cms.manager.WebserviceMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.core.dao.AdminDao;
import com.jspgou.core.entity.Admin;
import com.jspgou.core.entity.User;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.AdminMng;
import com.jspgou.core.manager.RoleMng;
import com.jspgou.core.manager.UserMng;

import java.sql.Timestamp;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminMngImpl
        implements AdminMng {

    @Autowired
    private WebserviceMng webserviceMng;

    @Autowired
    private ShopMemberGroupMng shopmemberGroupMng;
    private UserMng userMng;
    private AdminDao dao;
    protected RoleMng roleMng;

    public Admin getByUsername(String username) {
        return this.dao.getByUsername(username);
    }

    public Admin getByUserId(Long userId, Long webId) {
        return this.dao.getByUserId(userId, webId);
    }

    public Admin register(String username, String password, String email, String ip, Boolean disabled, Website website, Boolean viewonlyAdmin) {
        Admin entity = new Admin();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = this.userMng.register(username, password, email, ip, timestamp);
        entity.setCreateTime(timestamp);
        entity.setUser(user);
        entity.setWebsite(website);
        entity.setDisabled(disabled);
        entity.setViewonlyAdmin(viewonlyAdmin);
        return save(entity);
    }

    public Admin findById(Long id) {
        return this.dao.findById(id);
    }

    public Admin save(Admin bean) {
        bean.init();
        return this.dao.save(bean);
    }

    public Admin update(Admin bean) {
        return this.dao.updateByUpdater(new Updater(bean));
    }

    public Admin deleteById(Long id) {
        return this.dao.deleteById(id);
    }

    public Admin[] deleteByIds(Long[] ids) {
        Admin[] beans = new Admin[ids.length];
        for (int i = 0; i < ids.length; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public void updateRole(Admin admin, Integer[] roleIds) {
        admin.getRoles().clear();
        if (roleIds != null)
            for (Integer rid : roleIds)
                admin.addToRoles(this.roleMng.findById(rid));
    }

    public void addRole(Admin admin, Integer[] roleIds) {
        if (roleIds != null)
            for (Integer rid : roleIds)
                admin.addToRoles(this.roleMng.findById(rid));
    }

    @Autowired
    public void setDao(AdminDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setUserMng(UserMng userMng) {
        this.userMng = userMng;
    }

    @Autowired
    public void setRoleMng(RoleMng roleMng) {
        this.roleMng = roleMng;
    }
}

