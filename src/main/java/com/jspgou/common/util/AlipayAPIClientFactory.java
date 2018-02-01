package com.jspgou.common.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AlipayAPIClientFactory {
    private static AlipayClient alipayClient;

    public static AlipayClient getAlipayClient(String url, String appId, String privateKey, String publicKey, String charset) {
        if (alipayClient == null) {
            alipayClient = new DefaultAlipayClient(url, appId, privateKey, "json", charset, publicKey);
        }
        return alipayClient;
    }
}

