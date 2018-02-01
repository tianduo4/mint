package com.jspgou.cms.web.threadvariable;

import com.jspgou.cms.entity.ShopAdmin;

public class AdminThread {
    private static ThreadLocal<ShopAdmin> instance = new ThreadLocal();

    public static ShopAdmin get() {
        return (ShopAdmin) instance.get();
    }

    public static void set(ShopAdmin group) {
        instance.set(group);
    }

    public static void remove() {
        instance.remove();
    }
}

