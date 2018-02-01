package com.mint.common.fck;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Utils {
    public static final String EMPTY_STRING = "";

    public static Set<String> getSet(String stringList, String delimiter) {
        if (isEmpty(delimiter))
            throw new IllegalArgumentException(
                    "Argument 'delimiter' shouldn't be empty!");
        if (isEmpty(stringList)) {
            return new HashSet();
        }
        Set set = new HashSet();
        StringTokenizer st = new StringTokenizer(stringList, delimiter);
        while (st.hasMoreTokens()) {
            String tmp = st.nextToken();
            if (isNotEmpty(tmp))
                set.add(tmp.toLowerCase());
        }
        return set;
    }

    public static Set<String> getSet(String stringList) {
        return getSet(stringList, "|");
    }

    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        if (isEmpty(str)) {
            return true;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}

