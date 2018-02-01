package com.mint.cms.dao;

import com.mint.cms.entity.DataBackup;
import com.mint.common.hibernate4.Updater;

public abstract interface DataBackupDao {
    public abstract DataBackup updateByUpdater(Updater<DataBackup> paramUpdater);

    public abstract DataBackup getDataBackup();
}

