package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ApiRecordDao;
import com.jspgou.cms.entity.ApiRecord;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ApiRecordDaoImpl extends HibernateBaseDao<ApiRecord, Long>
        implements ApiRecordDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public ApiRecord findById(Long id) {
        ApiRecord entity = (ApiRecord) get(id);
        return entity;
    }

    public ApiRecord save(ApiRecord bean) {
        getSession().save(bean);
        return bean;
    }

    public ApiRecord deleteById(Long id) {
        ApiRecord entity = (ApiRecord) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    public ApiRecord findBySign(String sign, String appId) {
        String hql = "from ApiRecord bean where bean.sign=? and bean.apiAccount.appId=?";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, sign).setParameter(1, appId);

        query.setMaxResults(1);
        return (ApiRecord) query.uniqueResult();
    }

    protected Class<ApiRecord> getEntityClass() {
        return ApiRecord.class;
    }
}

