package com.jspgou.common.web;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

public class RequestUtils {
    private static final Logger log = LoggerFactory.getLogger(RequestUtils.class);

    public static String getRequestAgreement(HttpServletRequest request) {
        return request.getRequestURL() != null ? request.getRequestURL().substring(0, request.getRequestURL().indexOf("://") + 3) : "http://";
    }

    public static String getQueryParam(HttpServletRequest request, String name) {
        String s = request.getQueryString();
        if (StringUtils.isBlank(s))
            return null;
        try {
            s = URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("encoding UTF-8 not support?", e);
        }
        if (StringUtils.isBlank(s)) {
            return null;
        }
        String[] as = StringUtils.split(s, "&");
        String[] as1 = as;
        int i = as1.length;
        for (int j = 0; j < i; j++) {
            String s2 = as1[j];
            String[] as2 = StringUtils.split(s2, "=");
            int k = as2.length;
            if ((k >= 1) && (as2[0].equals(name))) {
                if (k == 2) {
                    return as2[1];
                }
                return "";
            }

        }

        return null;
    }

    public static Map<String, Object> getQueryParams(HttpServletRequest request) {
        Map<String, String[]> map;
        if (request.getMethod().equalsIgnoreCase("POST")) {
            map = request.getParameterMap();
        } else {
            String s = request.getQueryString();
            if (StringUtils.isBlank(s))
                return new HashMap();
            try {
                s = URLDecoder.decode(s, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("encoding UTF-8 not support?", e);
            }
            map = parseQueryString(s);
        }

        Map params = new HashMap(map.size());

        for (Map.Entry entry : map.entrySet()) {
            int len = ((String[]) entry.getValue()).length;
            if (len == 1)
                params.put((String) entry.getKey(), ((String[]) entry.getValue())[0]);
            else if (len > 1) {
                params.put((String) entry.getKey(), entry.getValue());
            }
        }
        return params;
    }

    public static Map<String, String[]> parseQueryString(String s) {
        String[] valArray = null;
        if (s == null) {
            throw new IllegalArgumentException();
        }
        Map ht = new HashMap();
        StringTokenizer st = new StringTokenizer(s, "&");
        while (st.hasMoreTokens()) {
            String pair = st.nextToken();
            int pos = pair.indexOf('=');
            if (pos == -1) {
                continue;
            }
            String key = pair.substring(0, pos);
            String val = pair.substring(pos + 1, pair.length());
            if (ht.containsKey(key)) {
                String[] oldVals = (String[]) ht.get(key);
                valArray = new String[oldVals.length + 1];
                for (int i = 0; i < oldVals.length; i++) {
                    valArray[i] = oldVals[i];
                }
                valArray[oldVals.length] = val;
            } else {
                valArray = new String[1];
                valArray[0] = val;
            }
            ht.put(key, valArray);
        }
        return ht;
    }

    public static String getLocation(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        StringBuffer buff = request.getRequestURL();
        String uri = request.getRequestURI();
        String origUri = helper.getOriginatingRequestUri(request);
        buff.replace(buff.length() - uri.length(), buff.length(), origUri);
        String queryString = helper.getOriginatingQueryString(request);
        if (queryString != null) {
            buff.append("?").append(queryString);
        }
        return buff.toString();
    }

    public static Map<String, String> getRequestMap(HttpServletRequest request, String prefix) {
        return getRequestMap(request, prefix, false);
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if ((!StringUtils.isBlank(ip)) && (!"unknown".equalsIgnoreCase(ip))) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if ((!StringUtils.isBlank(ip)) && (!"unknown".equalsIgnoreCase(ip))) {
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            }
            return ip;
        }

        return request.getRemoteAddr();
    }

    private static Map<String, String> getRequestMap(HttpServletRequest request, String prefix, boolean nameWithPrefix) {
        Map map = new HashMap();
        Enumeration names = request.getParameterNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            if (name.startsWith(prefix)) {
                String key = nameWithPrefix ? name : name.substring(prefix.length());
                String value = StringUtils.join(request.getParameterValues(name), ',');
                map.put(key, value);
            }
        }
        return map;
    }

    public static Map<String, String> getSignMap(HttpServletRequest request) {
        Map param = new HashMap();
        Enumeration penum = request.getParameterNames();
        while (penum.hasMoreElements()) {
            String pKey = (String) penum.nextElement();
            String value = request.getParameter(pKey);

            if ((pKey.equals("sign")) || (pKey.equals("uploadFile")) ||
                    (!StringUtils.isNotBlank(value))) continue;
            param.put(pKey, value);
        }

        return param;
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.split("", "=").length);
    }
}

