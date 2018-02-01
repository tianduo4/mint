package com.jspgou.common.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpRequestUtil {
    public static final String REQUEST_TYPE_GET = "get";
    public static final String REQUEST_TYPE_POST = "post";

    public static String request(String uri, Map<String, String> params, String type, String encoding)
            throws ClientProtocolException, IOException {
        String result = "";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;

        if ("get".equals(type)) {
            if (params != null) {
                if (uri.indexOf("?") != -1)
                    uri = uri + "&";
                else {
                    uri = uri + "?";
                }
                for (String key : params.keySet()) {
                    uri = uri + key + "=" + (String) params.get(key) + "&";
                }
            }
            HttpGet httpGet = new HttpGet(uri);
            httpResponse = httpClient.execute(httpGet);
        } else if ("post".equals(type)) {
            HttpPost httpPost = new HttpPost(uri);

            if (params != null) {
                Object paramList = new ArrayList();
                for (String key : params.keySet()) {
                    if (key != null) {
                        ((List) paramList).add(new BasicNameValuePair(key, (String) params.get(key)));
                    }
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity((List) paramList, "UTF-8");
                httpPost.setEntity(entity);
            }
            httpResponse = httpClient.execute(httpPost);
        }

        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            InputStream is = entity.getContent();

            byte[] buff = new byte[9192];
            int l;
            while ((l = is.read(buff)) != -1) {
                result = result + new String(buff, 0, l, encoding);
            }
        }
        return (String) result;
    }
}

