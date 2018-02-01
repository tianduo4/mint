package com.mint.core.manager.impl;

import com.mint.core.dao.WebsiteExtDao;
import com.mint.core.entity.WebsiteExt;
import com.mint.core.manager.WebsiteExtMng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WebsiteExtMngImpl
        implements WebsiteExtMng {
    private WebsiteExtDao dao;

    @Transactional(readOnly = true)
    public Map<String, String> getMap() {
        List<WebsiteExt> list = this.dao.getList();
        Map map = new HashMap(list.size());
        for (WebsiteExt websiteExt : list) {
            map.put(websiteExt.getId(), websiteExt.getValue());
        }
        return map;
    }

    @Transactional(readOnly = true)
    public WebsiteExt.ConfigLogin getConfigLogin() {
        return WebsiteExt.ConfigLogin.create(getMap());
    }

    @Autowired
    public void setDao(WebsiteExtDao dao) {
        this.dao = dao;
    }
}

