package com.mint.core.dao.impl;

import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.core.dao.FtpDao;
import com.mint.core.entity.Ftp;

import java.util.List;

public class FtpDaoImpl extends HibernateBaseDao<Ftp, Integer>
        implements FtpDao {
    public List<Ftp> getList() {
        String hql = "from Ftp bean";
        return find(hql, new Object[0]);
    }

    public Ftp findById(Integer id) {
        Ftp entity = (Ftp) get(id);
        return entity;
    }

    public Ftp save(Ftp bean) {
        getSession().save(bean);
        return bean;
    }

    public Ftp deleteById(Integer id) {
        Ftp entity = (Ftp) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Ftp> getEntityClass() {
        return Ftp.class;
    }
}

