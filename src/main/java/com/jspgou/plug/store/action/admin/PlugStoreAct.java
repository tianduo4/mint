/*     */ package com.jspgou.plug.store.action.admin;
/*     */ 
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.security.encoder.Md5PwdEncoder;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.session.SessionProvider;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.plug.store.entity.PlugStoreConfig;
/*     */ import com.jspgou.plug.store.entity.StorePlug;
/*     */ import com.jspgou.plug.store.manager.PlugStoreConfigMng;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.http.client.HttpClient;
/*     */ import org.apache.http.client.HttpResponseException;
/*     */ import org.apache.http.client.ResponseHandler;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.impl.client.DefaultHttpClient;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class PlugStoreAct
/*     */ {
/*  48 */   private static final Logger log = LoggerFactory.getLogger(PlugStoreAct.class);
/*     */   private static final String STORE_LOGIN = "store_login";
/*     */   private Integer totalCount;
/*     */ 
/*     */   @Autowired
/*     */   private PlugStoreConfigMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private SessionProvider session;
/*     */ 
/*     */   @Autowired
/*     */   private Md5PwdEncoder pwdEncoder;
/*     */ 
/*     */   @RequestMapping({"/store/v_center.do"})
/*     */   public String list(Integer productType, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  56 */     if ((this.totalCount == null) || (productType != null)) {
/*  57 */       this.totalCount = getPlugTotal(productType);
/*     */     }
/*  59 */     int pageSize = CookieUtils.getPageSize(request);
/*  60 */     Pagination p = new Pagination(SimplePage.cpn(pageNo), pageSize, this.totalCount.intValue());
/*  61 */     if (this.totalCount.intValue() < 1)
/*  62 */       p.setList(new ArrayList());
/*     */     else {
/*  64 */       p.setList(getPlugs(productType, Integer.valueOf(pageSize * (SimplePage.cpn(pageNo) - 1)), 
/*  65 */         Integer.valueOf(pageSize)));
/*     */     }
/*  67 */     String plugUrlPrefix = this.manager.getDefault().getServerUrl() + "/plug";
/*  68 */     model.addAttribute("pagination", p);
/*  69 */     model.addAttribute("pageNo", Integer.valueOf(p.getPageNo()));
/*  70 */     model.addAttribute("plugUrlPrefix", plugUrlPrefix);
/*  71 */     model.addAttribute("productType", productType);
/*  72 */     return "plugStore/list";
/*     */   }
/*     */   @RequestMapping({"/store/v_config.do"})
/*     */   public String config(HttpServletRequest request, ModelMap model) {
/*  77 */     Boolean is_login = (Boolean)this.session.getAttribute(request, "store_login");
/*  78 */     if ((is_login != null) && (is_login.booleanValue())) {
/*  79 */       model.addAttribute("plugStoreConfig", this.manager.getDefault());
/*  80 */       return "plugStore/config";
/*     */     }
/*  82 */     return "plugStore/login";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/store/o_login.do"})
/*     */   public String o_login(String password, ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException
/*     */   {
/*  91 */     if (this.pwdEncoder.encodePassword(password).equals(
/*  91 */       this.manager.getDefault().getPassword())) {
/*  92 */       this.session.setAttribute(request, response, "store_login", Boolean.valueOf(true));
/*  93 */       return config(request, model);
/*     */     }
/*  95 */     return "plugStore/login_error";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/store/config_update.do"})
/*     */   public String update(PlugStoreConfig bean, String password, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/* 102 */     WebErrors errors = validateUpdate(bean.getId(), request);
/* 103 */     if (errors.hasErrors()) {
/* 104 */       return errors.showErrorPage(model);
/*     */     }
/* 106 */     bean = this.manager.update(bean, password);
/* 107 */     log.info("update PlugStoreConfig id={}.", bean.getId());
/* 108 */     return config(request, model);
/*     */   }
/*     */ 
/*     */   private Integer getPlugTotal(Integer productType) {
/* 112 */     String serverUrl = this.manager.getDefault().getServerUrl();
/* 113 */     String url = serverUrl + "/json/plug_sum.jspx?productId=1";
/* 114 */     if (productType != null) {
/* 115 */       url = url + "&productType=" + productType;
/*     */     }
/* 117 */     CharsetHandler handler = new CharsetHandler("utf-8");
/* 118 */     HttpClient client = new DefaultHttpClient();
/* 119 */     String total = "0";
/*     */     try {
/* 121 */       HttpGet httpget = new HttpGet(new URI(url));
/* 122 */       total = (String)client.execute(httpget, handler);
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/* 126 */     return Integer.valueOf(Integer.parseInt(total));
/*     */   }
/*     */ 
/*     */   private List<StorePlug> getPlugs(Integer productType, Integer first, Integer count)
/*     */   {
/* 131 */     String serverUrl = this.manager.getDefault().getServerUrl();
/* 132 */     String url = serverUrl + "/json/plug_list.jspx?productId=4" + "&first=" + 
/* 133 */       first + "&count=" + count;
/* 134 */     if (productType != null) {
/* 135 */       url = url + "&productType=" + productType;
/*     */     }
/* 137 */     CharsetHandler handler = new CharsetHandler("utf-8");
/* 138 */     HttpClient client = new DefaultHttpClient();
/* 139 */     String json = "";
/*     */     try {
/* 141 */       HttpGet httpget = new HttpGet(new URI(url));
/* 142 */       json = (String)client.execute(httpget, handler);
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/* 146 */     List list = new ArrayList();
/*     */     try {
/* 148 */       JSONArray jarray = new JSONArray(json);
/* 149 */       for (int i = 0; i < jarray.length(); i++) {
/* 150 */         JSONObject jobject = (JSONObject)jarray.get(i);
/* 151 */         StorePlug plug = new StorePlug();
/* 152 */         plug.setId(Integer.valueOf(jobject.getInt("id")));
/* 153 */         plug.setChargeAmount(Double.valueOf(jobject.getDouble("chargeAmount")));
/* 154 */         plug.setShortDesc(jobject.getString("shortDesc"));
/* 155 */         plug.setIsCharge(Boolean.valueOf(jobject.getBoolean("isCharge")));
/* 156 */         plug.setProductId(Integer.valueOf(jobject.getInt("productId")));
/* 157 */         plug.setProductName(jobject.getString("productName"));
/* 158 */         plug.setReleaseDate(jobject.getString("releaseDate"));
/* 159 */         plug.setTitle(jobject.getString("title"));
/* 160 */         plug.setType(Integer.valueOf(jobject.getInt("type")));
/* 161 */         list.add(plug);
/*     */       }
/*     */     } catch (JSONException e) {
/* 164 */       e.printStackTrace();
/*     */     }
/* 166 */     return list;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Integer id, HttpServletRequest request)
/*     */   {
/* 197 */     WebErrors errors = WebErrors.create(request);
/* 198 */     if (vldExist(id, errors)) {
/* 199 */       return errors;
/*     */     }
/* 201 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Integer id, WebErrors errors) {
/* 205 */     if (errors.ifNull(id, "id")) {
/* 206 */       return true;
/*     */     }
/* 208 */     PlugStoreConfig entity = this.manager.findById(id);
/*     */ 
/* 210 */     return errors.ifNotExist(entity, PlugStoreConfig.class, id);
/*     */   }
/*     */ 
/*     */   private class CharsetHandler
/*     */     implements ResponseHandler<String>
/*     */   {
/*     */     private String charset;
/*     */ 
/*     */     public CharsetHandler(String charset)
/*     */     {
/* 173 */       this.charset = charset;
/*     */     }
/*     */ 
/*     */     public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException
/*     */     {
/* 178 */       StatusLine statusLine = response.getStatusLine();
/* 179 */       if (statusLine.getStatusCode() >= 300) {
/* 180 */         throw new HttpResponseException(statusLine.getStatusCode(), 
/* 181 */           statusLine.getReasonPhrase());
/*     */       }
/* 183 */       HttpEntity entity = response.getEntity();
/* 184 */       if (entity != null) {
/* 185 */         if (!StringUtils.isBlank(this.charset)) {
/* 186 */           return EntityUtils.toString(entity, this.charset);
/*     */         }
/* 188 */         return EntityUtils.toString(entity);
/*     */       }
/*     */ 
/* 191 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.plug.store.action.admin.PlugStoreAct
 * JD-Core Version:    0.6.0
 */