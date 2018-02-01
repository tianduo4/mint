package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.CustomerServiceDao;
import com.jspgou.cms.entity.CustomerService;
import com.jspgou.cms.manager.CustomerServiceMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceMngImpl
        implements CustomerServiceMng {

    @Autowired
    private CustomerServiceDao dao;

    public CustomerService findById(Long id) {
        return this.dao.findById(id);
    }

    public Pagination getPagination(Boolean disable, int pageNo, int pageSize) {
        return this.dao.getPagination(disable, pageNo, pageSize);
    }

    public List<CustomerService> getList() {
        return this.dao.getList(Boolean.valueOf(false));
    }

    public CustomerService update(CustomerService bean) {
        Updater updater = new Updater(bean);
        CustomerService entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public CustomerService save(CustomerService bean) {
        return this.dao.save(bean);
    }

    public CustomerService[] updatePriority(Long[] ids, Integer[] priority) {
        CustomerService[] beans = new CustomerService[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = findById(ids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    public CustomerService[] deleteByIds(Long[] ids) {
        CustomerService[] beans = new CustomerService[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = this.dao.deleteById(ids[i]);
        }
        return beans;
    }
}

