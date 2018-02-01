package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.WebserviceCallRecordDao;
import com.jspgou.cms.entity.WebserviceCallRecord;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class WebserviceCallRecordDaoImpl extends HibernateBaseDao<WebserviceCallRecord, Integer>
        implements WebserviceCallRecordDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public WebserviceCallRecord findById(Integer id) {
        WebserviceCallRecord entity = (WebserviceCallRecord) get(id);
        return entity;
    }

    public WebserviceCallRecord save(WebserviceCallRecord bean) {
        getSession().save(bean);
        return bean;
    }

    public WebserviceCallRecord deleteById(Integer id) {
        WebserviceCallRecord entity = (WebserviceCallRecord) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<WebserviceCallRecord> getEntityClass() {
        return WebserviceCallRecord.class;
    }
}

