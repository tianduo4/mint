package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.AddressDao;
import com.jspgou.cms.entity.Address;
import com.jspgou.cms.manager.AddressMng;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressMngImpl
        implements AddressMng {
    private AddressDao dao;

    public List<Address> getListById(Long parentId) {
        return this.dao.getListById(parentId);
    }

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    public Pagination getPageByParentId(Long parentId, int pageNo, int pageSize) {
        Pagination page = this.dao.getPageByParentId(parentId, pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public Address findById(Long id) {
        Address entity = this.dao.findById(id);
        return entity;
    }

    public Address save(Address bean) {
        this.dao.save(bean);
        return bean;
    }

    public Address update(Address bean) {
        Updater updater = new Updater(bean);
        Address entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public Address deleteById(Long id) {
        Address bean = this.dao.deleteById(id);
        return bean;
    }

    public Address[] deleteByIds(Long[] ids) {
        Address[] beans = new Address[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public Address[] updatePriority(Long[] ids, Integer[] priority) {
        Address[] beans = new Address[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = findById(ids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(AddressDao dao) {
        this.dao = dao;
    }
}

