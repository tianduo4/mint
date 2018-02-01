package com.jspgou.cms.dao;

import com.jspgou.cms.entity.DataBackup;
import com.jspgou.common.hibernate4.Updater;

public abstract interface DataBackupDao {
    public abstract DataBackup updateByUpdater(Updater<DataBackup> paramUpdater);

    public abstract DataBackup getDataBackup();
}

