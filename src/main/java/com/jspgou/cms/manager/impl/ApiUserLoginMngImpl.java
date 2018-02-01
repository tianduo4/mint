package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.ApiUserLoginDao;
import com.jspgou.cms.entity.ApiUserLogin;
import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.manager.ApiUserLoginMng;
import com.jspgou.cms.manager.ShopAdminMng;
import com.jspgou.cms.web.CmsThreadVariable;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.util.AES128Util;
import com.jspgou.common.util.DateUtils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApiUserLoginMngImpl
        implements ApiUserLoginMng {
    private ApiUserLoginDao dao;

    @Autowired
    private ShopAdminMng shopAdminMng;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ApiUserLogin findById(Long id) {
        ApiUserLogin entity = this.dao.findById(id);
        return entity;
    }

    public ApiUserLogin save(ApiUserLogin bean) {
        this.dao.save(bean);
        return bean;
    }

    public ApiUserLogin update(ApiUserLogin bean) {
        Updater updater = new Updater(bean);
        ApiUserLogin entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ApiUserLogin deleteById(Long id) {
        ApiUserLogin bean = this.dao.deleteById(id);
        return bean;
    }

    public ApiUserLogin[] deleteByIds(Long[] ids) {
        ApiUserLogin[] beans = new ApiUserLogin[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public ApiUserLogin findBySessionKey(String sessionKey) {
        return this.dao.findBySessionKey(sessionKey);
    }

    public ApiUserLogin findByUsername(String username) {
        return this.dao.findByUsername(username);
    }

    public void updateLoginSuccess(String username, String sessionKey) {
        ApiUserLogin apiUserLogin = findByUsername(username);
        Date now = new Timestamp(System.currentTimeMillis());
        apiUserLogin.setLoginTime(now);
        apiUserLogin.setSessionKey(sessionKey);
        apiUserLogin.setLoginCount(Integer.valueOf(apiUserLogin.getLoginCount().intValue() + 1));
        update(apiUserLogin);
    }

    public void saveLoginSuccess(String username, String sessionKey) {
        ApiUserLogin apiUserLogin = new ApiUserLogin();
        Date now = new Timestamp(System.currentTimeMillis());
        apiUserLogin.setLoginTime(now);
        apiUserLogin.setSessionKey(sessionKey);
        apiUserLogin.setLoginCount(Integer.valueOf(1));
        apiUserLogin.setUsername(username);
        save(apiUserLogin);
    }

    @Transactional(readOnly = true)
    public ApiUserLogin findUserLogin(String username, String sessionKey) {
        return this.dao.findUserLogin(username, sessionKey);
    }

    public ShopAdmin findUser(String sessionKey, String aesKey, String ivKey) throws Exception {
        String decryptSessionKey = "";
        ShopAdmin user = null;
        if (StringUtils.isNotBlank(sessionKey)) {
            decryptSessionKey = AES128Util.decrypt(sessionKey, aesKey, ivKey);
            ApiUserLogin apiUserLogin = findUserLogin(null, decryptSessionKey);
            if ((apiUserLogin != null) && (StringUtils.isNotBlank(decryptSessionKey))) {
                String username = apiUserLogin.getUsername();
                CmsThreadVariable.setApiUserLogin(apiUserLogin);
                if (StringUtils.isNotBlank(username)) {
                    user = this.shopAdminMng.getByUsername(username);
                }
            }
        }
        return user;
    }

    @Transactional(readOnly = true)
    public Short getUserStatus(String sessionKey) {
        ApiUserLogin login = findUserLogin(null, sessionKey);
        if ((login != null) && (login.getActiveTime() != null) && (login.getSessionKey().equals(sessionKey))) {
            Date activeTime = login.getActiveTime();
            Date now = Calendar.getInstance().getTime();
            if (DateUtils.getDiffMinuteTwoDate(activeTime, now).doubleValue() <= 20.0D) {
                return ApiUserLogin.USER_STATUS_LOGIN;
            }
            return ApiUserLogin.USER_STATUS_LOGOVERTIME;
        }

        return ApiUserLogin.USER_STATUS_LOGOUT;
    }

    public ApiUserLogin userActive(String sessionKey) {
        ApiUserLogin login = findUserLogin(null, sessionKey);
        if (login != null) {
            login.setActiveTime(Calendar.getInstance().getTime());
        }
        return login;
    }

    public ApiUserLogin userLogin(String username, String sessionKey) {
        ApiUserLogin login = findUserLogin(username, sessionKey);
        if (login == null) {
            login = new ApiUserLogin();
            login.setLoginTime(Calendar.getInstance().getTime());
            login.setActiveTime(Calendar.getInstance().getTime());
            login.setLoginCount(Integer.valueOf(1));
            login.setSessionKey(sessionKey);
            login.setUsername(username);
            login = save(login);
        } else {
            login.setLoginTime(Calendar.getInstance().getTime());
            login.setActiveTime(Calendar.getInstance().getTime());
            login.setLoginCount(Integer.valueOf(1 + login.getLoginCount().intValue()));
            login.setSessionKey(sessionKey);
            update(login);
        }
        return login;
    }

    public ApiUserLogin userLogout(String username) {
        ApiUserLogin login = findUserLogin(username, null);
        if (login != null) {
            login.setSessionKey("");
            login.setActiveTime(null);
            update(login);
        }
        return login;
    }

    @Autowired
    public void setDao(ApiUserLoginDao dao) {
        this.dao = dao;
    }
}

