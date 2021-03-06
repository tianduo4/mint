package com.mint.cms.web.threadvariable;

import com.mint.cms.entity.ShopMemberGroup;

public class GroupThread {
    private static ThreadLocal<ShopMemberGroup> instance = new ThreadLocal();

    public static ShopMemberGroup get() {
        return (ShopMemberGroup) instance.get();
    }

    public static void set(ShopMemberGroup group) {
        instance.set(group);
    }

    public static void remove() {
        instance.remove();
    }
}

