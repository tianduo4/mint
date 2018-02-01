package com.jspgou.plug.store.entity;

import com.jspgou.plug.store.entity.base.BasePlugStoreConfig;

public class PlugStoreConfig extends BasePlugStoreConfig {
    private static final long serialVersionUID = 1L;

    public PlugStoreConfig() {
    }

    public PlugStoreConfig(Integer id) {
        super(id);
    }

    public PlugStoreConfig(Integer id, String serverUrl, String passwod) {
        super(id,
                serverUrl,
                passwod);
    }
}

