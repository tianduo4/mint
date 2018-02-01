package com.mint.core.dao.impl;

import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.core.dao.WebsiteExtDao;
import com.mint.core.entity.WebsiteExt;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WebsiteExtDaoImpl extends HibernateBaseDao<WebsiteExt, String>
        implements WebsiteExtDao {
    public List<WebsiteExt> getList() {
        String hql = "from WebsiteExt";
        return find(hql, new Object[0]);
    }

    protected Class<WebsiteExt> getEntityClass() {
        return WebsiteExt.class;
    }
}

