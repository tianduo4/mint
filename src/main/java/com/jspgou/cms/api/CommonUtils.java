package com.jspgou.cms.api;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class CommonUtils extends StringUtils {
    public static String parseStr(String str) {
        return isNotBlank(str) ? str : "";
    }

    public static Object parseId(Object id) {
        if (id != null) {
            return id;
        }
        return "";
    }

    public static String parseDate(Date date, String formate) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(formate);
            return sdf.format(date);
        }
        return "";
    }

    public static int parseInteger(Integer i) {
        return i != null ? i.intValue() : 0;
    }

    public static Long parseLong(Long l) {
        return Long.valueOf(l != null ? l.longValue() : 0L);
    }

    public static Double parseDouble(Double d) {
        return Double.valueOf(d != null ? d.doubleValue() : 0.0D);
    }

    public static Boolean parseBoolean(Boolean b) {
        return Boolean.valueOf(b != null ? b.booleanValue() : false);
    }

    public static BigDecimal parseBigDecimal(BigDecimal d) {
        return d != null ? d : new BigDecimal(0.0D);
    }
}

