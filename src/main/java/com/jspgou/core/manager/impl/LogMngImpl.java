package com.jspgou.core.manager.impl;

import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.web.RequestUtils;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.dao.LogDao;
import com.jspgou.core.entity.Log;
import com.jspgou.core.entity.User;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.LogMng;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UrlPathHelper;

@Service
@Transactional
public class LogMngImpl
        implements LogMng {
    private LogDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public Log findById(Long id) {
        Log entity = this.dao.findById(id);
        return entity;
    }

    public Log save(Log bean) {
        this.dao.save(bean);
        return bean;
    }

    public void save(String versions, String updatelog) {
        Date date = new Date();
        Log bean = new Log();
        bean.setContent(updatelog);
        bean.setTitle(versions);
        bean.setCategory(Integer.valueOf(1));
        bean.setTime(date);
        this.dao.save(bean);
    }

    public Log update(Log bean) {
        Updater updater = new Updater(bean);
        Log entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public Log deleteById(Long id) {
        Log bean = this.dao.deleteById(id);
        return bean;
    }

    public Log[] deleteByIds(Long[] ids) {
        Log[] beans = new Log[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public Log save(Integer category, Website site, User user, String url, String ip, Date date, String title, String content) {
        Log log = new Log();
        log.setSite(site);
        log.setUser(user);
        log.setCategory(category);
        log.setIp(ip);
        log.setTime(date);
        log.setUrl(url);
        log.setTitle(title);
        log.setContent(content);
        save(log);
        return log;
    }

    public Log loginFailure(HttpServletRequest request, String content) {
        String ip = RequestUtils.getIpAddr(request);
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        Date date = new Date();
        Log log = save(Integer.valueOf(2), null, null, uri, ip, date, "login failure", content);
        return log;
    }

    public Log loginSuccess(HttpServletRequest request, User user) {
        String ip = RequestUtils.getIpAddr(request);
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        Date date = new Date();
        Log log = save(Integer.valueOf(1), null, user, uri, ip, date, "login success", null);
        return log;
    }

    @Autowired
    public void setDao(LogDao dao) {
        this.dao = dao;
    }

    public Log operating(HttpServletRequest request, String title, String content) {
        String ip = RequestUtils.getIpAddr(request);
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        Date date = new Date();
        Log log = save(Integer.valueOf(3), null, null, uri, ip, date,
                MessageResolver.getMessage(request, title, new Object[0]), content);
        return log;
    }
}

