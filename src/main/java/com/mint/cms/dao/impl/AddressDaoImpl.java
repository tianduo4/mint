package com.mint.cms.dao.impl;

import com.mint.cms.dao.AddressDao;
import com.mint.cms.entity.Address;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoImpl extends HibernateBaseDao<Address, Long>
        implements AddressDao {
    public List<Address> getListById(Long parentId) {
        Finder f = Finder.create("from Address bean where 1=1 ");
        if (parentId == null) {
            f.append(" and bean.parent.id is null");
        } else {
            f.append(" and bean.parent.id=:parentId");
            f.setParam("parentId", parentId);
        }
        return find(f);
    }

    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria(new Criterion[0]);
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    public Pagination getPageByParentId(Long parentId, int pageNo, int pageSize) {
        Finder f = Finder.create("from Address bean where 1=1 ");
        if (parentId != null) {
            f.append(" and bean.parent.id=:id");
            f.setParam("id", parentId);
        } else {
            f.append(" and bean.parent.id is null");
        }
        f.append(" order by bean.priority");
        return find(f, pageNo, pageSize);
    }

    public Address findById(Long id) {
        Address entity = (Address) get(id);
        return entity;
    }

    public Address save(Address bean) {
        getSession().save(bean);
        return bean;
    }

    public Address deleteById(Long id) {
        Address entity = (Address) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Address> getEntityClass() {
        return Address.class;
    }
}

