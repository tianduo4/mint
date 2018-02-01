package com.jspgou.common.util;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class ConvertMapToJson {
    private static final String QUOTE = "\"";

    public static String buildJsonBody(Map<String, Object> body, int tabCount, boolean addComma) {
        StringBuilder sbJsonBody = new StringBuilder();
        sbJsonBody.append("{");
        Set<String> keySet = body.keySet();
        int count = 0;
        int size = keySet.size();
        for (String key : keySet) {
            count++;
            sbJsonBody.append(buildJsonField(key, body.get(key), tabCount + 1, count != size));
        }
        sbJsonBody.append("}");
        return sbJsonBody.toString();
    }

    private static String buildJsonField(String key, Object value, int tabCount, boolean addComma) {
        StringBuilder sbJsonField = new StringBuilder();
        sbJsonField.append("\"").append(key).append("\"").append(": ");
        sbJsonField.append(buildJsonValue(value, tabCount, addComma));
        return sbJsonField.toString();
    }

    private static String buildJsonValue(Object value, int tabCount, boolean addComma) {
        StringBuilder sbJsonValue = new StringBuilder();
        if ((value instanceof String))
            sbJsonValue.append("\"").append(value).append("\"");
        else if (((value instanceof Integer)) || ((value instanceof Long)) || ((value instanceof Double)))
            sbJsonValue.append(value);
        else if ((value instanceof Date))
            sbJsonValue.append("\"").append(formatDate((Date) value)).append("\"");
        else if ((value.getClass().isArray()) || ((value instanceof Collection)))
            sbJsonValue.append(buildJsonArray(value, tabCount, addComma));
        else if ((value instanceof Map)) {
            sbJsonValue.append(buildJsonBody((Map) value, tabCount, addComma));
        }
        sbJsonValue.append(buildJsonTail(addComma));
        return sbJsonValue.toString();
    }

    private static String buildJsonArray(Object value, int tabCount, boolean addComma) {
        StringBuilder sbJsonArray = new StringBuilder();
        sbJsonArray.append("[\n");
        Object[] objArray = null;
        if (value.getClass().isArray())
            objArray = (Object[]) value;
        else if ((value instanceof Collection)) {
            objArray = ((Collection) value).toArray();
        }
        int size = objArray.length;
        int count = 0;

        for (Object obj : objArray) {
            count++;
            sbJsonArray.append(buildJsonValue(obj, tabCount + 1, count != size));
        }
        sbJsonArray.append("]");
        return sbJsonArray.toString();
    }

    private static String buildJsonTail(boolean addComma) {
        return addComma ? "," : "";
    }

    private static String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}

