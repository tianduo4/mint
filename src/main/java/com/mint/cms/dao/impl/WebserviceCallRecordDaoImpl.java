package com.mint.cms.dao.impl;

import com.mint.cms.dao.WebserviceCallRecordDao;
import com.mint.cms.entity.WebserviceCallRecord;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;
import org.hibernate.Criteria;
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

