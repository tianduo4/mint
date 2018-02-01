package com.mint.core.entity;

import com.mint.core.entity.base.BaseThirdAccount;

public class ThirdAccount extends BaseThirdAccount {
    private static final long serialVersionUID = 1L;
    public static final String QQ_KEY = "openId";
    public static final String SINA_KEY = "uid";
    public static final String QQ_PLAT = "QQ";
    public static final String SINA_PLAT = "SINA";
    public static final String QQ_WEBO_KEY = "weboOpenId";
    public static final String QQ_WEBO_PLAT = "QQWEBO";
    public static final String WECHAT_KEY = "weChatId";
    public static final String WECHAT_PLAT = "WECHAT";

    public ThirdAccount() {
    }

    public ThirdAccount(Long id) {
        super(id);
    }

    public ThirdAccount(Long id, String accountKey, String username, String source) {
        super(id,
                accountKey,
                username,
                source);
    }
}

