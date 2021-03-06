package com.mint.cms.web;

import com.mint.cms.entity.ShopConfig;

import javax.servlet.http.HttpServletRequest;

public class SiteUtils extends com.mint.core.web.SiteUtils {
    public static ShopConfig getConfig(HttpServletRequest request) {
        ShopConfig config = (ShopConfig) request.getAttribute("_shop_config_key");
        if (config == null) {
            throw new IllegalStateException(
                    "Config not found in Request Attribute '_shop_config_key'");
        }

        return config;
    }
}

