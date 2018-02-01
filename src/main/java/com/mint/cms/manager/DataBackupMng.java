package com.mint.cms.manager;

import com.mint.cms.entity.DataBackup;

public abstract interface DataBackupMng {
    public abstract DataBackup update(DataBackup paramDataBackup);

    public abstract DataBackup getDataBackup();
}

