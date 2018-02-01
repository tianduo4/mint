package com.jspgou.cms.web.threadvariable;

import com.jspgou.cms.entity.ShopMember;

public class MemberThread {
    private static ThreadLocal<ShopMember> instance = new ThreadLocal();

    private static ThreadLocal<String> sessionKeyInstance = new ThreadLocal();

    private static ThreadLocal<String> userNameInstance = new ThreadLocal();

    public static ShopMember get() {
        return (ShopMember) instance.get();
    }

    public static String getSessionKey() {
        return (String) sessionKeyInstance.get();
    }

    public static String getUserName() {
        return (String) userNameInstance.get();
    }

    public static void set(ShopMember member) {
        instance.set(member);
    }

    public static void setSessionKey(String sessionKey) {
        sessionKeyInstance.set(sessionKey);
    }

    public static void setUserName(String username) {
        userNameInstance.set(username);
    }

    public static void remove() {
        instance.remove();
    }
}

