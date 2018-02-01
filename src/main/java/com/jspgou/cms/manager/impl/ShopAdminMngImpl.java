package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ShopAdminDao;
import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.ShopAdminMng;
import com.jspgou.cms.manager.WebserviceMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import com.jspgou.core.entity.Admin;
import com.jspgou.core.entity.User;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.AdminMng;
import com.jspgou.core.manager.MemberMng;
import com.jspgou.core.manager.UserMng;
import com.jspgou.core.manager.WebsiteMng;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopAdminMngImpl
        implements ShopAdminMng {
    private UserMng userMng;
    private WebsiteMng websiteMng;
    private AdminMng adminMng;
    private ShopAdminDao dao;
    private WebserviceMng webserviceMng;
    private MemberMng memberMng;

    public ShopAdmin getByUserId(Long userId, Long webId) {
        Admin admin = this.adminMng.getByUserId(userId, webId);
        if (admin == null) {
            return null;
        }
        return findById(admin.getId());
    }

    public void register(String username, String password, Boolean viewonlyAdmin, String email, String ip, Boolean disabled, Long webId, Integer[] reoles, ShopAdmin shopAdmin) {
        shopAdmin = register(username, password, viewonlyAdmin, email, ip, disabled.booleanValue(), webId, shopAdmin);

        this.webserviceMng.callWebService("true", username, password, email, null, "addUser");
        this.adminMng.addRole(shopAdmin.getAdmin(), reoles);
    }

    public ShopAdmin register(String username, String password, Boolean viewonlyAdmin, String email, String ip, boolean disabled, Long webId, ShopAdmin shopAdmin) {
        Website web = this.websiteMng.findById(webId);
        Admin admin = this.adminMng.register(username, password, email, ip, Boolean.valueOf(disabled), web, viewonlyAdmin);
        shopAdmin.setAdmin(admin);
        shopAdmin.setWebsite(web);
        return save(shopAdmin);
    }

    @Transactional(readOnly = true)
    public Pagination getPage(Long webId, int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(webId, pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ShopAdmin findById(Long id) {
        ShopAdmin entity = this.dao.findById(id);
        return entity;
    }

    public ShopAdmin save(ShopAdmin bean) {
        this.dao.save(bean);
        return bean;
    }

    public ShopAdmin update(ShopAdmin bean, String password, Boolean disabled, String email, Boolean viewonlyAdmin) {
        ShopAdmin entity = findById(bean.getId());
        Admin admin = entity.getAdmin();
        this.userMng.updateUser(admin.getUser().getId(), password, email);
        if (disabled != null) {
            admin.setDisabled(disabled);
        }

        Updater updater = new Updater(bean);
        entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public void update(String password, Boolean disabled, String email, Boolean viewonlyAdmin, ShopAdmin shopAdmin, Integer[] roleIds) {
        shopAdmin = update(shopAdmin, password, disabled, email, viewonlyAdmin);

        ShopMember member = new ShopMember();
        member.setRealName(shopAdmin.getFirstname());
        this.webserviceMng.callWebService("true", shopAdmin.getUsername(), password, email, member, "updateUser");
        this.adminMng.updateRole(shopAdmin.getAdmin(), roleIds);
    }

    public void delete(Long[] ids) {
        ShopAdmin[] beans = deleteByIds(ids);
        for (ShopAdmin bean : beans) {
            Map paramsValues = new HashMap();
            paramsValues.put("username", bean.getUsername());
            paramsValues.put("admin", "true");
            this.webserviceMng.callWebService("deleteUser", paramsValues);
        }
    }

    public ShopAdmin deleteById(Long id) {
        ShopAdmin bean = this.dao.deleteById(id);

        if (this.memberMng.getByUsername(bean.getUsername()) == null) {
            this.userMng.deleteById(bean.getAdmin().getUser().getId());
        }
        return bean;
    }

    public ShopAdmin[] deleteByIds(Long[] ids) {
        ShopAdmin[] beans = new ShopAdmin[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public ShopAdmin findByName(String name) {
        return this.dao.findByName(name);
    }

    @Autowired
    public void setDao(ShopAdminDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setAdminMng(AdminMng adminMng) {
        this.adminMng = adminMng;
    }

    @Autowired
    public void setWebsiteMng(WebsiteMng websiteMng) {
        this.websiteMng = websiteMng;
    }

    @Autowired
    public void setUserMng(UserMng userMng) {
        this.userMng = userMng;
    }

    public ShopAdmin getByUsername(String username) {
        return this.dao.getByUsername(username);
    }

    @Autowired
    public void setWebserviceMng(WebserviceMng webserviceMng) {
        this.webserviceMng = webserviceMng;
    }

    @Autowired
    public void setMemberMng(MemberMng memberMng) {
        this.memberMng = memberMng;
    }
}

