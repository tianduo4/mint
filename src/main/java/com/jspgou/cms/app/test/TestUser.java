package com.jspgou.cms.app.test;

import com.jspgou.common.util.AES128Util;
import com.jspgou.common.util.Num62;
import com.jspgou.common.web.HttpClientUtil;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class TestUser {
    public static String appId = "4505818634615678";
    private static String appKey = "2sHKEWFCxNofq1EbwusziayFJlIfRJ8o";
    private static String aesKey = "S9u978Q31NGPGc5H";
    private static String ivKey = "X83yESM9iShLxfwS";

    public static void main(String[] args) {
        testLogin();
    }

    private static String testLogin() {
        String url = "http:192.168.0.167:8080/newjspgou/api/user/login.jspx";
        StringBuffer paramBuffer = new StringBuffer();
        String password = "password";
        paramBuffer.append("username=admin");
        try {
            paramBuffer.append("&aesPassword=" + AES128Util.encrypt(password, aesKey, ivKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String nonce_str = RandomStringUtils.random(16, Num62.N62_CHARS);
        paramBuffer.append("&appId=" + appId);
        paramBuffer.append("&nonce_str=" + nonce_str);

        Map param = new HashMap();
        String[] params = paramBuffer.toString().split("&");
        for (String p : params) {
            String[] keyValue = p.split("=");
            param.put(keyValue[0], keyValue[1]);
        }
        url = url + paramBuffer.toString();
        String res = HttpClientUtil.getInstance().get(url);
        System.out.println(res);
        try {
            JSONObject json = new JSONObject(res);
            String sessionKey = (String) json.get("body");
            try {
                String descryptKey = AES128Util.decrypt(sessionKey, aesKey, ivKey);
                System.out.println(descryptKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
}

