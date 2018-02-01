package com.mint.cms.manager;

import com.mint.cms.entity.ShopMember;

import javax.servlet.http.HttpServletRequest;

public abstract interface ApiUtilMng {
    public abstract ShopMember findbysessionKey(HttpServletRequest paramHttpServletRequest);

    public abstract String getJsonStrng(String paramString1, String paramString2, Boolean paramBoolean, HttpServletRequest paramHttpServletRequest);

    public abstract String getJsonStrng(String paramString1, String paramString2, HttpServletRequest paramHttpServletRequest);

    public abstract String getEncryptpass(HttpServletRequest paramHttpServletRequest)
            throws Exception;

    public abstract Boolean verify(HttpServletRequest paramHttpServletRequest);
}

