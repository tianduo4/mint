package com.mint.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckMobile {
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i|windows (phone|ce)|blackberry|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp|laystation portable)|nokia|fennec|htc[-_]|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    static Pattern phonePat = Pattern.compile(phoneReg, 2);
    static Pattern tablePat = Pattern.compile(tableReg, 2);

    public static boolean check(String userAgent) {
        if (userAgent == null) {
            userAgent = "";
        }

        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);

        return (matcherPhone.find()) || (matcherTable.find());
    }
}

