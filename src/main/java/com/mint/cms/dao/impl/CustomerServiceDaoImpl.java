package com.mint.cms.dao.impl;

import com.mint.cms.dao.CustomerServiceDao;
import com.mint.cms.entity.CustomerService;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class CustomerServiceDaoImpl extends HibernateBaseDao<CustomerService, Long>
        implements CustomerServiceDao {
    public CustomerService findById(Long id) {
        CustomerService entity = (CustomerService) get(id);
        return entity;
    }

    public Pagination getPagination(Boolean disable, int pageNo, int pageSize) {
        Finder f = Finder.create("from CustomerService bean where 1=1");
        if (disable != null) {
            f.append(" and bean.disable=:disable").setParam("disable", disable);
        }
        f.append(" order by bean.priority");
        return find(f, pageNo, pageSize);
    }

    public List<CustomerService> getList(Boolean disable) {
        Finder f = Finder.create("from CustomerService bean where 1=1");

        if (disable != null) {
            f.append(" and bean.disable=:disable").setParam("disable", disable);
        }
        f.append(" order by bean.priority");
        return find(f);
    }

    public CustomerService save(CustomerService bean) {
        getSession().save(bean);
        return bean;
    }

    public CustomerService deleteById(Long id) {
        CustomerService entity = (CustomerService) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<CustomerService> getEntityClass() {
        return CustomerService.class;
    }
}

