package com.jspgou.cms.api;

import com.jspgou.common.util.AES128Util;
import com.jspgou.common.util.PayUtil;
import com.jspgou.common.web.HttpClientUtil;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class TestApi {
    private static String base = "http://127.0.0.1:8080/newjspgou/api/admin";

    private static String appId = "2846613546571490";
    private static String appKey = "7aWxvObyCkdoTagY5H8d49J2H4wvwrB0";
    private static String aesKey = "U4htkr4fYMYeDNmf";
    private static String ivKey = "CCXRVReXgeCWGTxR";

    public static void main(String[] args) {
        try {
            System.out.println(login());

            System.out.println(AES128Util.encrypt("123456", aesKey, ivKey));
            System.out.println(AES128Util.encrypt("password", aesKey, ivKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String login() throws Exception {
        String url = base + "/user/login";
        Map param = new HashMap();
        param.put("userName", "admin");
        param.put("passWord", AES128Util.encrypt("123456", aesKey, ivKey));
        param.put("appId", appId);
        param.put("nonce_str", "nitdcqwetywqlkui");
        String validateSign = PayUtil.createSign(param, appKey);

        Map reparam = new HashMap();
        reparam = param;
        reparam.put("sign", validateSign);
        HttpClientUtil.getInstance();
        String res = HttpClientUtil.postParams(url, param);
        return res;
    }

    private static String getSign() throws Exception {
        String url = base + "/user/loginOut";
        Map param = new HashMap();
        param.put("sessionKey", "73dabae808a2687bf0dac4a3380c57f0e56bca30c5e4a99dfdd01dbee70d91ad1f47d5853d72dcdfcc092cbf99805fc0");
        param.put("appId", appId);
        param.put("type", "image");
        param.put("nonce_str", "nitdcqwetywqlkuis");
        String validateSign = PayUtil.createSign(param, appKey);
        System.out.println(validateSign);

        Map reparam = new HashMap();
        reparam = param;
        reparam.put("sign", validateSign);
        String res = "";
        return res;
    }

    private static String loginOut()
            throws Exception {
        String url = base + "/user/loginOut";
        Map param = new HashMap();
        param.put("sessionKey", "930bc782e63c0dff7aa0507d6d75cc3522b58393a0d44b29f3448ca84659e941be14a6db6c84f456b2aa59f9861a2964");
        param.put("appId", appId);
        param.put("nonce_str", "nitdcqwetywqlkuis");
        String validateSign = PayUtil.createSign(param, appKey);
        System.out.println(validateSign);

        Map reparam = new HashMap();
        reparam = param;
        reparam.put("sign", validateSign);
        HttpClientUtil.getInstance();
        String res = HttpClientUtil.postParams(url, param);
        return res;
    }
}

