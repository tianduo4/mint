package com.jspgou.cms.service.impl;

import com.jspgou.cms.Constants;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.service.WeixinSvc;
import com.jspgou.cms.service.WeixinTokenCache;
import com.jspgou.cms.web.CmsThreadVariable;
import com.jspgou.common.upload.FileUpload;
import com.jspgou.common.util.PropertyUtils;
import com.jspgou.common.web.springmvc.RealPathResolver;
import com.jspgou.core.entity.Global;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.GlobalMng;
import com.jspgou.plug.weixin.entity.Weixin;
import com.jspgou.plug.weixin.manager.WeixinMng;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeixinSvcImpl
        implements WeixinSvc {
    private static final Logger log = LoggerFactory.getLogger(WeixinSvcImpl.class);
    public static final String TOKEN_KEY = "weixin.address.token";
    public static final String USERS_KEY = "weixin.address.users";
    public static final String SEND_KEY = "weixin.address.send";
    public static final String UPLOAD_KEY = "weixin.address.upload";
    public static final String MENU_KEY = "weixin.address.menu";
    public static final String UPLOAD_NEWS = "weixin.address.uploadnews";
    public static final String SEND_ALL_MESSAGE = "weixin.address.sendAllMessage";
    public static final String UPLOAD_IMG_URL = "weixin.address.uploadimg";
    public static final String ADD_NEWS = "weixin.address.addNews";
    public static final String UPLOAD_MATERIAL_IMG_URL = "weixin.address.addMaterial";
    public static final Integer USERS_QUERY_MAX = Integer.valueOf(10000);
    public static final String WEIXIN_AUTH_CODE_URL = "weixin.auth.getCodeUrl";
    public static final String ACCESSTOKEN_KEY = "weixin.auth.getAccessTokenUrl";
    public static final String TESTTOKEN_KEY = "weixinLogin.address.testtoken";
    public static final String USERINFO_KEY = "weixinLogin.address.userinfo";

    @Autowired
    private RealPathResolver realPathResolver;

    @Autowired
    private WeixinTokenCache weixinTokenCache;

    @Autowired
    private WeixinMng weixinMng;

    @Autowired
    private GlobalMng globalMng;

    public String getToken() {
        String tokenGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.token");
        String appid = "";
        String secret = "";
        Website site = CmsThreadVariable.getSite();
        Weixin weixin = this.weixinMng.findBy();
        if (site != null) {
            appid = weixin.getAppKey();
            secret = weixin.getAppSecret();
        }
        JSONObject tokenJson = new JSONObject();
        if ((StringUtils.isNotBlank(appid)) && (StringUtils.isNotBlank(secret))) {
            tokenGetUrl = tokenGetUrl + "&appid=" + appid + "&secret=" + secret;
            tokenJson = getUrlResponse(tokenGetUrl);
            try {
                return (String) tokenJson.get("access_token");
            } catch (JSONException e) {
                return null;
            }
        }
        return null;
    }

    public Set<String> getUsers(String access_token) {
        String usersGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.users");
        usersGetUrl = usersGetUrl + "?access_token=" + access_token;
        JSONObject data = getUrlResponse(usersGetUrl);
        Set openIds = new HashSet();
        Integer total = Integer.valueOf(0);
        Integer count = Integer.valueOf(0);
        try {
            total = (Integer) data.get("total");
            count = (Integer) data.get("count");

            if (count.intValue() < total.intValue()) {
                openIds.addAll(getUsers(openIds, usersGetUrl, access_token, (String) data.get("next_openid")));
            } else if (count.intValue() > 0) {
                JSONObject openIdData = (JSONObject) data.get("data");
                JSONArray openIdArray = (JSONArray) openIdData.get("openid");
                for (int i = 0; i < openIdArray.length(); i++)
                    openIds.add((String) openIdArray.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return openIds;
    }

    public String uploadFile(String access_token, String filePath, String type) {
        String sendGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.upload");
        String url = sendGetUrl + "?access_token=" + access_token;
        String result = null;
        String mediaId = "";
        FileUpload fileUpload = new FileUpload();
        try {
            result = fileUpload.uploadFile(url, filePath, type);
            if ((result.startsWith("{")) && (result.contains("media_id"))) {
                JSONObject json = new JSONObject(result);
                mediaId = json.getString("media_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return mediaId;
    }

    public void sendText(String access_token, String content) {
        String sendGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.send");
        String url = sendGetUrl + "?access_token=" + access_token;
        Set<String> openIds = getUsers(access_token);
        content = filterCharacters(content);

        for (String openId : openIds) {
            String strJson = "{\"touser\" :\"" + openId + "\",";
            strJson = strJson + "\"msgtype\":\"text\",";
            strJson = strJson + "\"text\":{";
            strJson = strJson + "\"content\":\"" + content + "\"";
            strJson = strJson + "}}";
            post(url, strJson, "application/json");
        }
    }

    public void sendContent(String access_token, String title, String description, String url, String picurl) {
        String sendUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.send");
        sendUrl = sendUrl + "?access_token=" + access_token;
        Set<String> openIds = getUsers(access_token);
        if (description == null) {
            description = "";
        }
        title = filterCharacters(title);
        description = filterCharacters(description);

        for (String openId : openIds) {
            String strJson = "{\"touser\" :\"" + openId + "\",";
            strJson = strJson + "\"msgtype\":\"news\",";
            strJson = strJson + "\"news\":{";
            strJson = strJson + "\"articles\": [{";
            strJson = strJson + "\"title\":\"" + title + "\",";
            strJson = strJson + "\"description\":\"" + description + "\",";
            strJson = strJson + "\"url\":\"" + url + "\",";
            strJson = strJson + "\"picurl\":\"" + picurl + "\"";
            strJson = strJson + "}]}}";
            post(sendUrl, strJson, "application/json");
        }
    }

    public void sendVedio(String access_token, String title, String description, String media_id) {
        String sendGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.send");
        String url = sendGetUrl + "?access_token=" + access_token;
        Set<String> openIds = getUsers(access_token);
        if (description == null) {
            description = "";
        }
        title = filterCharacters(title);
        description = filterCharacters(description);

        for (String openId : openIds) {
            String strJson = "{\"touser\" :\"" + openId + "\",";
            strJson = strJson + "\"msgtype\":\"video\",";
            strJson = strJson + "\"video\":{";
            strJson = strJson + "\"media_id\":\"" + media_id + "\",";
            strJson = strJson + "\"title\":\"" + title + "\",";
            strJson = strJson + "\"description\":\"" + description + "\"";
            strJson = strJson + "}}";
            post(url, strJson, "application/json");
        }
    }

    private Set<String> getUsers(Set<String> openIds, String url, String access_token, String next_openid) {
        JSONObject data = getUrlResponse(url);
        try {
            Integer count = (Integer) data.get("count");
            String nextOpenId = (String) data.get("next_openid");
            if (count.intValue() > 0) {
                JSONObject openIdData = (JSONObject) data.get("data");
                JSONArray openIdArray = (JSONArray) openIdData.get("openid");
                for (int i = 0; i < openIdArray.length(); i++) {
                    openIds.add((String) openIdArray.get(i));
                }
            }
            if (StringUtils.isNotBlank(nextOpenId))
                return getUsers(openIds, url, access_token, nextOpenId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return openIds;
    }

    public void createMenu(String menus) {
        String token = this.weixinTokenCache.getToken();
        String createMenuUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.menu");
        String url = createMenuUrl + "?access_token=" + token;
        post(url, menus, "application/json");
    }

    public void sendTextToAllUser(Product[] beans) {
        String token = this.weixinTokenCache.getToken();

        String articalUploadUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.uploadnews");
        String url = articalUploadUrl + "?access_token=" + token;

        String[] str = articalUpload(token, beans);
        Integer contentCount = Integer.valueOf(0);
        contentCount = Integer.valueOf(Integer.parseInt(str[1]));
        if (contentCount.intValue() > 0) {
            DefaultHttpClient client = new DefaultHttpClient();
            client = (DefaultHttpClient) wrapClient(client);
            HttpPost post = new HttpPost(url);
            try {
                StringEntity s = new StringEntity(str[0], "utf-8");
                s.setContentType("application/json");
                post.setEntity(s);
                HttpResponse res = client.execute(post);
                HttpEntity entity = res.getEntity();
                String contentString = EntityUtils.toString(entity, "utf-8");
                JSONObject json = new JSONObject(contentString);

                String media_id = "";
                if (contentString.contains("media_id")) {
                    media_id = (String) json.get("media_id");
                }
                if (StringUtils.isNotBlank(media_id)) {
                    String sendAllMessageUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.sendAllMessage");
                    String url_send = sendAllMessageUrl + "?access_token=" + token;
                    String str_send = "{\"filter\":{\"is_to_all\":true},\"mpnews\":{\"media_id\":\"" + media_id + "\"},\"msgtype\":\"mpnews\"}";
                    post(url_send, str_send, "application/json");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String[] articalUpload(String token, Product[] beans) {
        Integer count = Integer.valueOf(0);

        String str = "{\"articles\":[";
        for (int i = 0; i < beans.length; i++) {
            Product bean = beans[i];
            String author = bean.getWebsite().getName();
            String sourceUrl = bean.getWeixinProductUrl();
            String mediaId = "";
            if (!StringUtils.isBlank(bean.getCoverImgUrl())) {
                String typeImg = bean.getCoverImgUrl();
                String contextPath = bean.getWebsite().getGlobal().getContextPath();
                if ((StringUtils.isNotBlank(contextPath)) && (typeImg.startsWith(contextPath)))
                    typeImg = this.realPathResolver.get(typeImg.substring(contextPath.length()));
                else {
                    typeImg = this.realPathResolver.get(typeImg);
                }
                mediaId = uploadFile(token, this.realPathResolver.get(bean.getCoverImgUrl()), "image");
                str = str + "{" +
                        "\"thumb_media_id\":\"" + mediaId + "\"," +
                        "\"author\":\"" + author + "\"," +
                        "\"title\":\"" + bean.getName() + "\"," +
                        "\"content_source_url\":\"" + sourceUrl + "\"," +
                        "\"content\":\"" + bean.getText() + "\"," +
                        "\"show_cover_pic\":\"0\"" + "}";
                if (i != beans.length - 1) {
                    str = str + ",";
                }
                count = Integer.valueOf(count.intValue() + 1);
            }
        }
        str = str + "]}";
        String[] result = new String[2];
        result[0] = str;
        result[1] = count.toString();
        return result;
    }

    public String getAccesstoken(String code) {
        String tokenGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.auth.getAccessTokenUrl");
        String content = "";
        Global global = this.globalMng.findIdkey();
        String appid = null;
        String secret = null;
        if (global != null) {
            appid = global.getWeixinID();
            secret = global.getWeixinKey();
        }
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(tokenGetUrl);
            sb.append("&appid=").append(appid);
            sb.append("&secret=").append(secret);
            sb.append("&code=").append(code);
            JSONObject tokenJson = getUrlResponse(sb.toString());
            content = tokenJson.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public String testToken(String accesstoken, String openid) {
        String testTokenUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixinLogin.address.testtoken");
        String content = "";
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(testTokenUrl);
            sb.append("access_token=").append(accesstoken);
            sb.append("&openid=").append(openid);
            JSONObject tokenJson = getUrlResponse(sb.toString());
            content = tokenJson.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public String getUserInfo(String accesstoken, String openid) {
        String userInfoGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixinLogin.address.userinfo");
        String content = "";
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(userInfoGetUrl);
            sb.append("access_token=").append(accesstoken);
            sb.append("&openid=").append(openid);
            JSONObject tokenJson = getUrlResponse(sb.toString());
            content = tokenJson.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    private JSONObject getUrlResponse(String url) {
        CharsetHandler handler = new CharsetHandler("UTF-8");
        try {
            HttpGet httpget = new HttpGet(new URI(url));
            DefaultHttpClient client = new DefaultHttpClient();
            client = (DefaultHttpClient) wrapClient(client);
            return new JSONObject((String) client.execute(httpget, handler));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void post(String url, String json, String contentType) {
        DefaultHttpClient client = new DefaultHttpClient();
        client = (DefaultHttpClient) wrapClient(client);
        HttpPost post = new HttpPost(url);
        try {
            StringEntity s = new StringEntity(json, "utf-8");
            if (StringUtils.isBlank(contentType)) {
                s.setContentType("application/json");
            }
            s.setContentType(contentType);
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            HttpEntity entity = res.getEntity();
            String str = EntityUtils.toString(entity, "utf-8");
            log.info(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String filterCharacters(String txt) {
        if (StringUtils.isNotBlank(txt)) {
            txt = txt.replace("&ldquo;", "“").replace("&rdquo;", "”").replace("&nbsp;", " ");
        }
        return txt;
    }

    private HttpClient wrapClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception ex) {
        }
        return null;
    }

    private class CharsetHandler implements ResponseHandler<String> {
        private String charset;

        public CharsetHandler(String charset) {
            this.charset = charset;
        }

        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(),
                        statusLine.getReasonPhrase());
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                if (!StringUtils.isBlank(this.charset)) {
                    return EntityUtils.toString(entity, this.charset);
                }
                return EntityUtils.toString(entity);
            }

            return null;
        }
    }
}

