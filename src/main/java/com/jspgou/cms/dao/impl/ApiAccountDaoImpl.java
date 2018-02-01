package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ApiAccountDao;
import com.jspgou.cms.entity.ApiAccount;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ApiAccountDaoImpl extends HibernateBaseDao<ApiAccount, Long>
        implements ApiAccountDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public ApiAccount findById(Long id) {
        ApiAccount entity = (ApiAccount) get(id);
        return entity;
    }

    public ApiAccount save(ApiAccount bean) {
        getSession().save(bean);
        return bean;
    }

    public ApiAccount deleteById(Long id) {
        ApiAccount entity = (ApiAccount) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    public ApiAccount findByAppId(String appId) {
        return (ApiAccount) findUniqueByProperty("appId", appId);
    }

    protected Class<ApiAccount> getEntityClass() {
        return ApiAccount.class;
    }
}

