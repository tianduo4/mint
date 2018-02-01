package com.jspgou.cms.manager;

import com.jspgou.cms.entity.DataBackup;

public abstract interface DataBackupMng {
    public abstract DataBackup update(DataBackup paramDataBackup);

    public abstract DataBackup getDataBackup();
}

