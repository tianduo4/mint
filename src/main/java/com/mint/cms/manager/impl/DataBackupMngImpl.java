package com.mint.cms.manager.impl;

import com.mint.cms.dao.DataBackupDao;
import com.mint.cms.entity.DataBackup;
import com.mint.cms.manager.DataBackupMng;
import com.mint.common.hibernate4.Updater;
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

