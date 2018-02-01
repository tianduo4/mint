package com.mint.cms.entity;

import com.mint.cms.entity.base.BaseDataBackup;

public class DataBackup extends BaseDataBackup {
    private static final long serialVersionUID = 1L;

    public DataBackup() {
    }

    public DataBackup(Integer id) {
        super(id);
    }

    public DataBackup(Integer id, String dataBasePath, String address, String dataBaseName, String username, String password) {
        super(id,
                dataBasePath,
                address,
                dataBaseName,
                username,
                password);
    }
}

