package com.mint.cms;

import java.util.HashMap;
import java.util.Map;

public class AdminMap {
    public static Map<String, Integer> adminmap = new HashMap();

    public static Integer getAdminMapVal(String username) {
        return (Integer) adminmap.get(username);
    }

    public static void addAdminMapVal(String username) {
        if (adminmap.get(username) == null)
            adminmap.put(username, Integer.valueOf(1));
        else
            adminmap.put(username, Integer.valueOf(((Integer) adminmap.get(username)).intValue() + 1));
    }

    public static void unLoadAdmin(String username) {
        adminmap.put(username, Integer.valueOf(0));
        adminmap.remove(username);
    }
}

