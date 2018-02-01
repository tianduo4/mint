package com.mint.cms.dao.impl;

import com.mint.cms.dao.DataBackupDao;
import com.mint.cms.entity.DataBackup;
import com.mint.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class DataBackupDaoImpl extends HibernateBaseDao<DataBackup, Integer>
        implements DataBackupDao {
    protected Class<DataBackup> getEntityClass() {
        return DataBackup.class;
    }

    public DataBackup getDataBackup() {
        String hql = " from DataBackup";
        List list = getSession().createQuery(hql).list();
        if (list.size() == 0) {
            return null;
        }
        return (DataBackup) list.get(0);
    }
}

