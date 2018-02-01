package com.mint.cms.dao.impl;

import com.mint.cms.dao.LogisticsTextDao;
import com.mint.cms.entity.LogisticsText;
import com.mint.common.hibernate4.HibernateBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class LogisticsTextDaoImpl extends HibernateBaseDao<LogisticsText, Long>
        implements LogisticsTextDao {
    public LogisticsText save(LogisticsText bean) {
        getSession().save(bean);
        return bean;
    }

    protected Class<LogisticsText> getEntityClass() {
        return LogisticsText.class;
    }
}

