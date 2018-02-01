package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.ApiInfoDao;
import com.jspgou.cms.entity.ApiInfo;
import com.jspgou.common.hibernate4.HibernateBaseDao;
import com.jspgou.common.page.Pagination;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class ApiInfoDaoImpl extends HibernateBaseDao<ApiInfo, Long>
        implements ApiInfoDao {
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public ApiInfo findById(Long id) {
        ApiInfo entity = (ApiInfo) get(id);
        return entity;
    }

    public ApiInfo findByApiUrl(String apiUrl) {
        return (ApiInfo) findUniqueByProperty("apiUrl", apiUrl);
    }

    public ApiInfo save(ApiInfo bean) {
        getSession().save(bean);
        return bean;
    }

    public ApiInfo deleteById(Long id) {
        ApiInfo entity = (ApiInfo) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<ApiInfo> getEntityClass() {
        return ApiInfo.class;
    }
}

