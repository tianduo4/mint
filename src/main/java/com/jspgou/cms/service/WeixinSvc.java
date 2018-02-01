package com.jspgou.cms.service;

import com.jspgou.cms.entity.Product;

import java.util.Set;

public abstract interface WeixinSvc {
    public abstract String getToken();

    public abstract Set<String> getUsers(String paramString);

    public abstract void sendText(String paramString1, String paramString2);

    public abstract String uploadFile(String paramString1, String paramString2, String paramString3);

    public abstract void sendVedio(String paramString1, String paramString2, String paramString3, String paramString4);

    public abstract void sendContent(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

    public abstract void createMenu(String paramString);

    public abstract void sendTextToAllUser(Product[] paramArrayOfProduct);

    public abstract String getAccesstoken(String paramString);

    public abstract String testToken(String paramString1, String paramString2);

    public abstract String getUserInfo(String paramString1, String paramString2);
}

