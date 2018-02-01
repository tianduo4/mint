package com.jspgou.cms.manager.impl;

import com.jspgou.cms.dao.DataBackupDao;
import com.jspgou.cms.entity.DataBackup;
import com.jspgou.cms.manager.DataBackupMng;
import com.jspgou.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DataBackupMngImpl
        implements DataBackupMng {

    @Autowired
    private DataBackupDao dao;

    public DataBackup getDataBackup() {
        return this.dao.getDataBackup();
    }

    public DataBackup update(DataBackup bean) {
        Updater updater = new Updater(bean);
        DataBackup entity = this.dao.updateByUpdater(updater);
        return entity;
    }
}

