package com.mint.plug.store.entity;

import com.mint.plug.store.entity.base.BasePlugStoreConfig;

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

