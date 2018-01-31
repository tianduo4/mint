/*     */ package com.jspgou.cms.service.impl;
/*     */ 
/*     */ import com.jspgou.cms.Constants;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.service.WeixinSvc;
/*     */ import com.jspgou.cms.service.WeixinTokenCache;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.common.upload.FileUpload;
/*     */ import com.jspgou.common.util.PropertyUtils;
/*     */ import com.jspgou.common.web.springmvc.RealPathResolver;
/*     */ import com.jspgou.core.entity.Global;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.GlobalMng;
/*     */ import com.jspgou.plug.weixin.entity.Weixin;
/*     */ import com.jspgou.plug.weixin.manager.WeixinMng;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.http.client.HttpClient;
/*     */ import org.apache.http.client.HttpResponseException;
/*     */ import org.apache.http.client.ResponseHandler;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.conn.ClientConnectionManager;
/*     */ import org.apache.http.conn.scheme.Scheme;
/*     */ import org.apache.http.conn.scheme.SchemeRegistry;
/*     */ import org.apache.http.conn.ssl.SSLSocketFactory;
/*     */ import org.apache.http.entity.StringEntity;
/*     */ import org.apache.http.impl.client.DefaultHttpClient;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class WeixinSvcImpl
/*     */   implements WeixinSvc
/*     */ {
/*  61 */   private static final Logger log = LoggerFactory.getLogger(WeixinSvcImpl.class);
/*     */   public static final String TOKEN_KEY = "weixin.address.token";
/*     */   public static final String USERS_KEY = "weixin.address.users";
/*     */   public static final String SEND_KEY = "weixin.address.send";
/*     */   public static final String UPLOAD_KEY = "weixin.address.upload";
/*     */   public static final String MENU_KEY = "weixin.address.menu";
/*     */   public static final String UPLOAD_NEWS = "weixin.address.uploadnews";
/*     */   public static final String SEND_ALL_MESSAGE = "weixin.address.sendAllMessage";
/*     */   public static final String UPLOAD_IMG_URL = "weixin.address.uploadimg";
/*     */   public static final String ADD_NEWS = "weixin.address.addNews";
/*     */   public static final String UPLOAD_MATERIAL_IMG_URL = "weixin.address.addMaterial";
/*  83 */   public static final Integer USERS_QUERY_MAX = Integer.valueOf(10000);
/*     */   public static final String WEIXIN_AUTH_CODE_URL = "weixin.auth.getCodeUrl";
/*     */   public static final String ACCESSTOKEN_KEY = "weixin.auth.getAccessTokenUrl";
/*     */   public static final String TESTTOKEN_KEY = "weixinLogin.address.testtoken";
/*     */   public static final String USERINFO_KEY = "weixinLogin.address.userinfo";
/*     */ 
/*     */   @Autowired
/*     */   private RealPathResolver realPathResolver;
/*     */ 
/*     */   @Autowired
/*     */   private WeixinTokenCache weixinTokenCache;
/*     */ 
/*     */   @Autowired
/*     */   private WeixinMng weixinMng;
/*     */ 
/*     */   @Autowired
/*     */   private GlobalMng globalMng;
/*     */ 
/*     */   public String getToken()
/*     */   {
/*  94 */     String tokenGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.token");
/*  95 */     String appid = "";
/*  96 */     String secret = "";
/*  97 */     Website site = CmsThreadVariable.getSite();
/*  98 */     Weixin weixin = this.weixinMng.findBy();
/*  99 */     if (site != null) {
/* 100 */       appid = weixin.getAppKey();
/* 101 */       secret = weixin.getAppSecret();
/*     */     }
/* 103 */     JSONObject tokenJson = new JSONObject();
/* 104 */     if ((StringUtils.isNotBlank(appid)) && (StringUtils.isNotBlank(secret))) {
/* 105 */       tokenGetUrl = tokenGetUrl + "&appid=" + appid + "&secret=" + secret;
/* 106 */       tokenJson = getUrlResponse(tokenGetUrl);
/*     */       try {
/* 108 */         return (String)tokenJson.get("access_token");
/*     */       } catch (JSONException e) {
/* 110 */         return null;
/*     */       }
/*     */     }
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   public Set<String> getUsers(String access_token)
/*     */   {
/* 118 */     String usersGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.users");
/* 119 */     usersGetUrl = usersGetUrl + "?access_token=" + access_token;
/* 120 */     JSONObject data = getUrlResponse(usersGetUrl);
/* 121 */     Set openIds = new HashSet();
/* 122 */     Integer total = Integer.valueOf(0); Integer count = Integer.valueOf(0);
/*     */     try {
/* 124 */       total = (Integer)data.get("total");
/* 125 */       count = (Integer)data.get("count");
/*     */ 
/* 127 */       if (count.intValue() < total.intValue()) {
/* 128 */         openIds.addAll(getUsers(openIds, usersGetUrl, access_token, (String)data.get("next_openid")));
/*     */       }
/* 131 */       else if (count.intValue() > 0) {
/* 132 */         JSONObject openIdData = (JSONObject)data.get("data");
/* 133 */         JSONArray openIdArray = (JSONArray)openIdData.get("openid");
/* 134 */         for (int i = 0; i < openIdArray.length(); i++)
/* 135 */           openIds.add((String)openIdArray.get(i));
/*     */       }
/*     */     }
/*     */     catch (JSONException e)
/*     */     {
/* 140 */       e.printStackTrace();
/*     */     }
/* 142 */     return openIds;
/*     */   }
/*     */ 
/*     */   public String uploadFile(String access_token, String filePath, String type) {
/* 146 */     String sendGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.upload");
/* 147 */     String url = sendGetUrl + "?access_token=" + access_token;
/* 148 */     String result = null;
/* 149 */     String mediaId = "";
/* 150 */     FileUpload fileUpload = new FileUpload();
/*     */     try {
/* 152 */       result = fileUpload.uploadFile(url, filePath, type);
/* 153 */       if ((result.startsWith("{")) && (result.contains("media_id"))) {
/* 154 */         JSONObject json = new JSONObject(result);
/* 155 */         mediaId = json.getString("media_id");
/*     */       }
/*     */     } catch (Exception e) {
/* 158 */       e.printStackTrace();
/* 159 */       log.error(e.getMessage());
/*     */     }
/* 161 */     return mediaId;
/*     */   }
/*     */ 
/*     */   public void sendText(String access_token, String content) {
/* 165 */     String sendGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.send");
/* 166 */     String url = sendGetUrl + "?access_token=" + access_token;
/* 167 */     Set<String> openIds = getUsers(access_token);
/* 168 */     content = filterCharacters(content);
/*     */ 
/* 170 */     for (String openId : openIds) {
/* 171 */       String strJson = "{\"touser\" :\"" + openId + "\",";
/* 172 */       strJson = strJson + "\"msgtype\":\"text\",";
/* 173 */       strJson = strJson + "\"text\":{";
/* 174 */       strJson = strJson + "\"content\":\"" + content + "\"";
/* 175 */       strJson = strJson + "}}";
/* 176 */       post(url, strJson, "application/json");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void sendContent(String access_token, String title, String description, String url, String picurl)
/*     */   {
/* 182 */     String sendUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.send");
/* 183 */     sendUrl = sendUrl + "?access_token=" + access_token;
/* 184 */     Set<String> openIds = getUsers(access_token);
/* 185 */     if (description == null) {
/* 186 */       description = "";
/*     */     }
/* 188 */     title = filterCharacters(title);
/* 189 */     description = filterCharacters(description);
/*     */ 
/* 191 */     for (String openId : openIds) {
/* 192 */       String strJson = "{\"touser\" :\"" + openId + "\",";
/* 193 */       strJson = strJson + "\"msgtype\":\"news\",";
/* 194 */       strJson = strJson + "\"news\":{";
/* 195 */       strJson = strJson + "\"articles\": [{";
/* 196 */       strJson = strJson + "\"title\":\"" + title + "\",";
/* 197 */       strJson = strJson + "\"description\":\"" + description + "\",";
/* 198 */       strJson = strJson + "\"url\":\"" + url + "\",";
/* 199 */       strJson = strJson + "\"picurl\":\"" + picurl + "\"";
/* 200 */       strJson = strJson + "}]}}";
/* 201 */       post(sendUrl, strJson, "application/json");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void sendVedio(String access_token, String title, String description, String media_id) {
/* 206 */     String sendGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.send");
/* 207 */     String url = sendGetUrl + "?access_token=" + access_token;
/* 208 */     Set<String> openIds = getUsers(access_token);
/* 209 */     if (description == null) {
/* 210 */       description = "";
/*     */     }
/* 212 */     title = filterCharacters(title);
/* 213 */     description = filterCharacters(description);
/*     */ 
/* 215 */     for (String openId : openIds) {
/* 216 */       String strJson = "{\"touser\" :\"" + openId + "\",";
/* 217 */       strJson = strJson + "\"msgtype\":\"video\",";
/* 218 */       strJson = strJson + "\"video\":{";
/* 219 */       strJson = strJson + "\"media_id\":\"" + media_id + "\",";
/* 220 */       strJson = strJson + "\"title\":\"" + title + "\",";
/* 221 */       strJson = strJson + "\"description\":\"" + description + "\"";
/* 222 */       strJson = strJson + "}}";
/* 223 */       post(url, strJson, "application/json");
/*     */     }
/*     */   }
/*     */ 
/*     */   private Set<String> getUsers(Set<String> openIds, String url, String access_token, String next_openid) {
/* 228 */     JSONObject data = getUrlResponse(url);
/*     */     try {
/* 230 */       Integer count = (Integer)data.get("count");
/* 231 */       String nextOpenId = (String)data.get("next_openid");
/* 232 */       if (count.intValue() > 0) {
/* 233 */         JSONObject openIdData = (JSONObject)data.get("data");
/* 234 */         JSONArray openIdArray = (JSONArray)openIdData.get("openid");
/* 235 */         for (int i = 0; i < openIdArray.length(); i++) {
/* 236 */           openIds.add((String)openIdArray.get(i));
/*     */         }
/*     */       }
/* 239 */       if (StringUtils.isNotBlank(nextOpenId))
/* 240 */         return getUsers(openIds, url, access_token, nextOpenId);
/*     */     }
/*     */     catch (JSONException e) {
/* 243 */       e.printStackTrace();
/*     */     }
/* 245 */     return openIds;
/*     */   }
/*     */ 
/*     */   public void createMenu(String menus)
/*     */   {
/* 252 */     String token = this.weixinTokenCache.getToken();
/* 253 */     String createMenuUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.menu");
/* 254 */     String url = createMenuUrl + "?access_token=" + token;
/* 255 */     post(url, menus, "application/json");
/*     */   }
/*     */ 
/*     */   public void sendTextToAllUser(Product[] beans)
/*     */   {
/* 263 */     String token = this.weixinTokenCache.getToken();
/*     */ 
/* 265 */     String articalUploadUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.uploadnews");
/* 266 */     String url = articalUploadUrl + "?access_token=" + token;
/*     */ 
/* 268 */     String[] str = articalUpload(token, beans);
/* 269 */     Integer contentCount = Integer.valueOf(0);
/* 270 */     contentCount = Integer.valueOf(Integer.parseInt(str[1]));
/* 271 */     if (contentCount.intValue() > 0) {
/* 272 */       DefaultHttpClient client = new DefaultHttpClient();
/* 273 */       client = (DefaultHttpClient)wrapClient(client);
/* 274 */       HttpPost post = new HttpPost(url);
/*     */       try {
/* 276 */         StringEntity s = new StringEntity(str[0], "utf-8");
/* 277 */         s.setContentType("application/json");
/* 278 */         post.setEntity(s);
/* 279 */         HttpResponse res = client.execute(post);
/* 280 */         HttpEntity entity = res.getEntity();
/* 281 */         String contentString = EntityUtils.toString(entity, "utf-8");
/* 282 */         JSONObject json = new JSONObject(contentString);
/*     */ 
/* 284 */         String media_id = "";
/* 285 */         if (contentString.contains("media_id")) {
/* 286 */           media_id = (String)json.get("media_id");
/*     */         }
/* 288 */         if (StringUtils.isNotBlank(media_id)) {
/* 289 */           String sendAllMessageUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.address.sendAllMessage");
/* 290 */           String url_send = sendAllMessageUrl + "?access_token=" + token;
/* 291 */           String str_send = "{\"filter\":{\"is_to_all\":true},\"mpnews\":{\"media_id\":\"" + media_id + "\"},\"msgtype\":\"mpnews\"}";
/* 292 */           post(url_send, str_send, "application/json");
/*     */         }
/*     */       } catch (Exception e) {
/* 295 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String[] articalUpload(String token, Product[] beans) {
/* 301 */     Integer count = Integer.valueOf(0);
/*     */ 
/* 303 */     String str = "{\"articles\":[";
/* 304 */     for (int i = 0; i < beans.length; i++) {
/* 305 */       Product bean = beans[i];
/* 306 */       String author = bean.getWebsite().getName();
/* 307 */       String sourceUrl = bean.getWeixinProductUrl();
/* 308 */       String mediaId = "";
/* 309 */       if (!StringUtils.isBlank(bean.getCoverImgUrl())) {
/* 310 */         String typeImg = bean.getCoverImgUrl();
/* 311 */         String contextPath = bean.getWebsite().getGlobal().getContextPath();
/* 312 */         if ((StringUtils.isNotBlank(contextPath)) && (typeImg.startsWith(contextPath)))
/* 313 */           typeImg = this.realPathResolver.get(typeImg.substring(contextPath.length()));
/*     */         else {
/* 315 */           typeImg = this.realPathResolver.get(typeImg);
/*     */         }
/* 317 */         mediaId = uploadFile(token, this.realPathResolver.get(bean.getCoverImgUrl()), "image");
/* 318 */         str = str + "{" + 
/* 319 */           "\"thumb_media_id\":\"" + mediaId + "\"," + 
/* 320 */           "\"author\":\"" + author + "\"," + 
/* 321 */           "\"title\":\"" + bean.getName() + "\"," + 
/* 322 */           "\"content_source_url\":\"" + sourceUrl + "\"," + 
/* 323 */           "\"content\":\"" + bean.getText() + "\"," + 
/* 324 */           "\"show_cover_pic\":\"0\"" + "}";
/* 325 */         if (i != beans.length - 1) {
/* 326 */           str = str + ",";
/*     */         }
/* 328 */         count = Integer.valueOf(count.intValue() + 1);
/*     */       }
/*     */     }
/* 331 */     str = str + "]}";
/* 332 */     String[] result = new String[2];
/* 333 */     result[0] = str;
/* 334 */     result[1] = count.toString();
/* 335 */     return result;
/*     */   }
/*     */ 
/*     */   public String getAccesstoken(String code)
/*     */   {
/* 341 */     String tokenGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixin.auth.getAccessTokenUrl");
/* 342 */     String content = "";
/* 343 */     Global global = this.globalMng.findIdkey();
/* 344 */     String appid = null;
/* 345 */     String secret = null;
/* 346 */     if (global != null) {
/* 347 */       appid = global.getWeixinID();
/* 348 */       secret = global.getWeixinKey();
/*     */     }
/*     */     try {
/* 351 */       StringBuffer sb = new StringBuffer();
/* 352 */       sb.append(tokenGetUrl);
/* 353 */       sb.append("&appid=").append(appid);
/* 354 */       sb.append("&secret=").append(secret);
/* 355 */       sb.append("&code=").append(code);
/* 356 */       JSONObject tokenJson = getUrlResponse(sb.toString());
/* 357 */       content = tokenJson.toString();
/*     */     } catch (Exception e) {
/* 359 */       e.printStackTrace();
/*     */     }
/* 361 */     return content;
/*     */   }
/*     */ 
/*     */   public String testToken(String accesstoken, String openid)
/*     */   {
/* 368 */     String testTokenUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixinLogin.address.testtoken");
/* 369 */     String content = "";
/*     */     try {
/* 371 */       StringBuffer sb = new StringBuffer();
/* 372 */       sb.append(testTokenUrl);
/* 373 */       sb.append("access_token=").append(accesstoken);
/* 374 */       sb.append("&openid=").append(openid);
/* 375 */       JSONObject tokenJson = getUrlResponse(sb.toString());
/* 376 */       content = tokenJson.toString();
/*     */     } catch (Exception e) {
/* 378 */       e.printStackTrace();
/*     */     }
/* 380 */     return content;
/*     */   }
/*     */ 
/*     */   public String getUserInfo(String accesstoken, String openid)
/*     */   {
/* 387 */     String userInfoGetUrl = PropertyUtils.getPropertyValue(new File(this.realPathResolver.get(Constants.JSPGOU_CONFIG)), "weixinLogin.address.userinfo");
/* 388 */     String content = "";
/*     */     try {
/* 390 */       StringBuffer sb = new StringBuffer();
/* 391 */       sb.append(userInfoGetUrl);
/* 392 */       sb.append("access_token=").append(accesstoken);
/* 393 */       sb.append("&openid=").append(openid);
/* 394 */       JSONObject tokenJson = getUrlResponse(sb.toString());
/* 395 */       content = tokenJson.toString();
/*     */     } catch (Exception e) {
/* 397 */       e.printStackTrace();
/*     */     }
/* 399 */     return content;
/*     */   }
/*     */ 
/*     */   private JSONObject getUrlResponse(String url) {
/* 403 */     CharsetHandler handler = new CharsetHandler("UTF-8");
/*     */     try {
/* 405 */       HttpGet httpget = new HttpGet(new URI(url));
/* 406 */       DefaultHttpClient client = new DefaultHttpClient();
/* 407 */       client = (DefaultHttpClient)wrapClient(client);
/* 408 */       return new JSONObject((String)client.execute(httpget, handler));
/*     */     } catch (Exception e) {
/* 410 */       e.printStackTrace();
/* 411 */     }return null;
/*     */   }
/*     */ 
/*     */   private void post(String url, String json, String contentType)
/*     */   {
/* 416 */     DefaultHttpClient client = new DefaultHttpClient();
/* 417 */     client = (DefaultHttpClient)wrapClient(client);
/* 418 */     HttpPost post = new HttpPost(url);
/*     */     try {
/* 420 */       StringEntity s = new StringEntity(json, "utf-8");
/* 421 */       if (StringUtils.isBlank(contentType)) {
/* 422 */         s.setContentType("application/json");
/*     */       }
/* 424 */       s.setContentType(contentType);
/* 425 */       post.setEntity(s);
/* 426 */       HttpResponse res = client.execute(post);
/* 427 */       HttpEntity entity = res.getEntity();
/* 428 */       String str = EntityUtils.toString(entity, "utf-8");
/* 429 */       log.info(str);
/*     */     } catch (Exception e) {
/* 431 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private String filterCharacters(String txt) {
/* 436 */     if (StringUtils.isNotBlank(txt)) {
/* 437 */       txt = txt.replace("&ldquo;", "“").replace("&rdquo;", "”").replace("&nbsp;", " ");
/*     */     }
/* 439 */     return txt;
/*     */   }
/*     */ 
/*     */   private HttpClient wrapClient(HttpClient base) {
/*     */     try {
/* 444 */       SSLContext ctx = SSLContext.getInstance("TLS");
/* 445 */       X509TrustManager tm = new X509TrustManager()
/*     */       {
/*     */         public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException
/*     */         {
/*     */         }
/*     */ 
/*     */         public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
/*     */         }
/*     */ 
/*     */         public X509Certificate[] getAcceptedIssuers() {
/* 455 */           return null;
/*     */         }
/*     */       };
/* 458 */       ctx.init(null, new TrustManager[] { tm }, null);
/* 459 */       SSLSocketFactory ssf = new SSLSocketFactory(ctx);
/* 460 */       ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
/* 461 */       ClientConnectionManager ccm = base.getConnectionManager();
/* 462 */       SchemeRegistry sr = ccm.getSchemeRegistry();
/* 463 */       sr.register(new Scheme("https", ssf, 443));
/* 464 */       return new DefaultHttpClient(ccm, base.getParams()); } catch (Exception ex) {
/*     */     }
/* 466 */     return null;
/*     */   }
/*     */ 
/*     */   private class CharsetHandler implements ResponseHandler<String> {
/*     */     private String charset;
/*     */ 
/*     */     public CharsetHandler(String charset) {
/* 474 */       this.charset = charset;
/*     */     }
/*     */ 
/*     */     public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException
/*     */     {
/* 479 */       StatusLine statusLine = response.getStatusLine();
/* 480 */       if (statusLine.getStatusCode() >= 300) {
/* 481 */         throw new HttpResponseException(statusLine.getStatusCode(), 
/* 482 */           statusLine.getReasonPhrase());
/*     */       }
/* 484 */       HttpEntity entity = response.getEntity();
/* 485 */       if (entity != null) {
/* 486 */         if (!StringUtils.isBlank(this.charset)) {
/* 487 */           return EntityUtils.toString(entity, this.charset);
/*     */         }
/* 489 */         return EntityUtils.toString(entity);
/*     */       }
/*     */ 
/* 492 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.service.impl.WeixinSvcImpl
 * JD-Core Version:    0.6.0
 */