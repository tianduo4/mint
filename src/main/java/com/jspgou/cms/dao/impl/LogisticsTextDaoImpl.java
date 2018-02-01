package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.LogisticsTextDao;
import com.jspgou.cms.entity.LogisticsText;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import org.hibernate.Session;
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

