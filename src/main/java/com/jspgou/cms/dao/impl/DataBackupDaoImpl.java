package com.jspgou.cms.dao.impl;

import com.jspgou.cms.dao.DataBackupDao;
import com.jspgou.cms.entity.DataBackup;
import com.jspgou.common.hibernate4.HibernateBaseDao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
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

