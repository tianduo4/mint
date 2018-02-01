package com.jspgou.cms.entity;

import com.jspgou.cms.entity.base.BaseApiUserLogin;

import java.util.Date;

public class ApiUserLogin extends BaseApiUserLogin {
    public static Short USER_STATUS_LOGIN = Short.valueOf("1");
    public static Short USER_STATUS_LOGOUT = Short.valueOf("2");
    public static Short USER_STATUS_LOGOVERTIME = Short.valueOf("3");
    private static final long serialVersionUID = 1L;

    public ApiUserLogin() {
    }

    public ApiUserLogin(Long id) {
        super(id);
    }

    public ApiUserLogin(Long id, String username, Date loginTime, Integer loginCount, Date activeTime) {
        super(id,
                username,
                loginTime,
                loginCount,
                activeTime);
    }
}

