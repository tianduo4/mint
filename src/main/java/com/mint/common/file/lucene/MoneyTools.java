package com.mint.common.file.lucene;

import org.apache.lucene.document.NumberTools;
import org.springframework.util.Assert;

public class MoneyTools {
    private static final Double MULTIPLE = new Double(1000.0D);

    public static String moneyToString(Double bigdecimal) {
        Assert.notNull(bigdecimal);
        return NumberTools.longToString(new Double(bigdecimal.doubleValue() * MULTIPLE.doubleValue()).longValue());
    }

    public static Double stringToMoney(String s) {
        Double bigdecimal = new Double(NumberTools.stringToLong(s));
        return Double.valueOf(bigdecimal.doubleValue() / MULTIPLE.doubleValue());
    }
}

