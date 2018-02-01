package com.mint.cms.web;

import com.mint.cms.entity.ApiAccount;
import com.mint.cms.entity.ApiUserLogin;
import com.mint.cms.entity.ShopAdmin;
import com.mint.core.entity.User;
import com.mint.core.entity.Website;

public class CmsThreadVariable {
    private static ThreadLocal<User> cmsUserVariable = new ThreadLocal();

    private static ThreadLocal<ApiUserLogin> apiLogVariable = new ThreadLocal();

    private static ThreadLocal<ApiAccount> apiAccountVariable = new ThreadLocal();

    private static ThreadLocal<ShopAdmin> cmsAdminUserVariable = new ThreadLocal();

    private static ThreadLocal<Website> cmsSiteVariable = new ThreadLocal();

    public static User getUser() {
        return (User) cmsUserVariable.get();
    }

    public static void setUser(User user) {
        cmsUserVariable.set(user);
    }

    public static void removeUser() {
        cmsUserVariable.remove();
    }

    public static void setAdminUser(ShopAdmin shopAdmin) {
        cmsAdminUserVariable.set(shopAdmin);
    }

    public static ShopAdmin getAdminUser() {
        return (ShopAdmin) cmsAdminUserVariable.get();
    }

    public static Website getSite() {
        return (Website) cmsSiteVariable.get();
    }

    public static void setSite(Website site) {
        cmsSiteVariable.set(site);
    }

    public static void removeSite() {
        cmsSiteVariable.remove();
    }

    public static ApiAccount getApiAccount() {
        return (ApiAccount) apiAccountVariable.get();
    }

    public static void setApiAccount(ApiAccount apiAccount) {
        apiAccountVariable.set(apiAccount);
    }

    public static ApiUserLogin getApiUserLogin() {
        return (ApiUserLogin) apiLogVariable.get();
    }

    public static void setApiUserLogin(ApiUserLogin apiUserLogin) {
        apiLogVariable.set(apiUserLogin);
    }
}

