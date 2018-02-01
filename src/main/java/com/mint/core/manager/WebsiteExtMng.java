package com.mint.core.manager;

import com.mint.core.entity.WebsiteExt;

import java.util.Map;

public abstract interface WebsiteExtMng {
    public abstract Map<String, String> getMap();

    public abstract WebsiteExt.ConfigLogin getConfigLogin();
}

