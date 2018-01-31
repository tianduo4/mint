/*     */ package com.jspgou.core.entity;
/*     */ 
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.core.entity.base.BaseWebsite;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class Website extends BaseWebsite
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final String RES_BASE = "r/gou/www";
/*     */   public static final String USER_BASE = "t";
/*     */   public static final String FRONT_RES = "/res/front";
/*     */   public static final String UPLOAD_PATH = "/u";
/*     */   public static final String TEMPLATE_PATH = "gou/tpl";
/*     */   public static final String DEFAULT = "default";
/*     */   public static final String TPL_SUFFIX = ".html";
/*     */   public static final String TPL_PREFIX_SYS = "sys_";
/*     */   public static final String TPL_PREFIX_TAG = "tag_";
/*     */   public static final String TPL_BASE = "/WEB-INF/t/gou";
/*     */   public static final String UA = "ua";
/*     */   public static final String TEMPLATE_MOBILE_PATH = "mobile";
/*     */   public static final String RES_PATH = "/r/gou";
/*     */ 
/*     */   public Website()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Website(Long id)
/*     */   {
/*  58 */     super(id);
/*     */   }
/*     */ 
/*     */   public String getUrlPrefix(String agreeMent)
/*     */   {
/*  67 */     StringBuilder url = new StringBuilder();
/*  68 */     url.append(agreeMent).append(getDomain());
/*  69 */     if (getPort() != null) {
/*  70 */       url.append(":").append(getPort());
/*     */     }
/*  72 */     return url.toString();
/*     */   }
/*     */ 
/*     */   public Website(Long id, Global global, String domain, String name, String currentSystem, String suffix, Integer lft, Integer rgt, Date createTime, Boolean close, Boolean relativePath, String frontEncoding, String frontContentType, String localeFront, String localeAdmin, Integer controlNameMinlen, String company, String copyright, String recordCode, String email, String phone, String mobile, String resourcesPath)
/*     */   {
/*  86 */     super(id, global, domain, name, currentSystem, suffix, lft, 
/*  85 */       rgt, createTime, close, relativePath, frontEncoding, frontContentType, localeFront, 
/*  86 */       localeAdmin, controlNameMinlen, company, copyright, recordCode, email, phone, mobile, resourcesPath);
/*     */   }
/*     */ 
/*     */   public String getTemplate(String dir, String name, String s2, String s3, HttpServletRequest request)
/*     */   {
/*  91 */     String equipment = (String)request.getAttribute("ua");
/*  92 */     StringBuilder stringbuilder = getTemplateRelBuff().append("/").append(dir).append("/");
/*  93 */     if ((StringUtils.isNotBlank(equipment)) && (equipment.equals("mobile"))) {
/*  94 */       stringbuilder = getTemplateMobileRelBuff().append("/").append(dir).append("/");
/*     */     }
/*     */ 
/*  97 */     if (!StringUtils.isBlank(s3)) {
/*  98 */       stringbuilder.append(s3);
/*     */     }
/* 100 */     stringbuilder.append(name);
/* 101 */     if (!StringUtils.isBlank(s2)) {
/* 102 */       stringbuilder.append("_").append(s2);
/*     */     }
/* 104 */     return ".html";
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/* 108 */     return "/";
/*     */   }
/*     */ 
/*     */   public String getUploadRel(String s) {
/* 112 */     StringBuilder stringbuilder = new StringBuilder("/").append("/u");
/* 113 */     if (!StringUtils.isBlank(s)) {
/* 114 */       if (!s.startsWith("/")) {
/* 115 */         stringbuilder.append("/");
/*     */       }
/* 117 */       stringbuilder.append(s);
/*     */     }
/* 119 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   public String getUploadUrls(String s)
/*     */   {
/* 124 */     StringBuilder stringbuilder = getResBaseUrlBuff().append("/").append("/u");
/* 125 */     if (!StringUtils.isBlank(s)) {
/* 126 */       if (!s.startsWith("/")) {
/* 127 */         stringbuilder.append("/");
/*     */       }
/* 129 */       stringbuilder.append(s);
/*     */     }
/* 131 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   public String getUploadUrl(String s) {
/* 135 */     StringBuilder stringbuilder = getResBaseUrlBuff();
/* 136 */     if (!StringUtils.isBlank(s)) {
/* 137 */       if (!s.startsWith("/")) {
/* 138 */         stringbuilder.append("/");
/*     */       }
/* 140 */       stringbuilder.append(s);
/*     */     }
/* 142 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   public String getTplPath()
/*     */   {
/* 152 */     return "/WEB-INF/t/gou";
/*     */   }
/*     */ 
/*     */   public String getTemplateRel(String s, HttpServletRequest request)
/*     */   {
/* 157 */     String equipment = (String)request.getAttribute("ua");
/* 158 */     StringBuilder stringbuilder = getTemplateRelBuff();
/* 159 */     if ((StringUtils.isNotBlank(equipment)) && (equipment.equals("mobile"))) {
/* 160 */       stringbuilder = getTemplateMobileRelBuff();
/*     */     }
/* 162 */     if (!StringUtils.isBlank(s)) {
/* 163 */       if (!s.startsWith("/")) {
/* 164 */         stringbuilder.append("/");
/*     */       }
/* 166 */       stringbuilder.append(s);
/*     */     }
/* 168 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   public String getResBaseRel(String s)
/*     */   {
/* 174 */     StringBuilder stringbuilder = getResBaseRelBuff();
/* 175 */     if (!StringUtils.isBlank(s)) {
/* 176 */       if (!s.startsWith("/")) {
/* 177 */         stringbuilder.append("/");
/*     */       }
/* 179 */       stringbuilder.append(s);
/*     */     }
/* 181 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   public Boolean getSsoEnable() {
/* 185 */     String ssoenable = null;
/*     */     try {
/* 187 */       ssoenable = (String)getAttr().get("ssoEnable");
/*     */     } catch (Exception e) {
/* 189 */       System.out.println(e.getMessage());
/*     */     }
/* 191 */     if (StringUtils.isBlank(ssoenable))
/* 192 */       return Boolean.valueOf(false);
/* 193 */     if (ssoenable.equals("true")) {
/* 194 */       return Boolean.valueOf(true);
/*     */     }
/* 196 */     return Boolean.valueOf(false);
/*     */   }
/*     */ 
/*     */   public Map<String, String> getSsoAttr()
/*     */   {
/* 201 */     Map ssoMap = new HashMap();
/* 202 */     Map<String,String> attr = getAttr();
/* 203 */     for (String ssoKey : attr.keySet()) {
/* 204 */       if (ssoKey.startsWith("sso_")) {
/* 205 */         ssoMap.put(ssoKey, (String)attr.get(ssoKey));
/*     */       }
/*     */     }
/* 208 */     return ssoMap;
/*     */   }
/*     */ 
/*     */   public List<Map<String, String>> getAuthUrl() {
/* 212 */     List attrs = new ArrayList();
/* 213 */     Map<String,String> attr = getAttr();
/* 214 */     for (String ssoKey : attr.keySet()) {
/* 215 */       if (ssoKey.startsWith("sso_")) {
/* 216 */         Map map = new HashMap();
/* 217 */         map.put("attr_" + ssoKey, (String)attr.get(ssoKey));
/* 218 */         attrs.add(map);
/*     */       }
/*     */     }
/* 221 */     return attrs;
/*     */   }
/*     */ 
/*     */   public List<String> getSsoAuthenticateUrls() {
/* 225 */     Map<String, String> ssoMap = getSsoAttr();
/* 226 */     List values = new ArrayList();
/* 227 */     for (String key : ssoMap.keySet()) {
/* 228 */       values.add((String)ssoMap.get(key));
/*     */     }
/* 230 */     return values;
/*     */   }
/*     */ 
/*     */   public WebsiteAttr getWebsiteAttr() {
/* 234 */     WebsiteAttr websiteAttr = new WebsiteAttr(getAttr());
/* 235 */     return websiteAttr;
/*     */   }
/*     */ 
/*     */   public String getFrontResUrl() {
/* 239 */     return "/res/front";
/*     */   }
/*     */ 
/*     */   public String getResBaseUrl(String s) {
/* 243 */     StringBuilder stringbuilder = getResBaseUrlBuff();
/* 244 */     if (!StringUtils.isBlank(s)) {
/* 245 */       if (!s.startsWith("/")) {
/* 246 */         stringbuilder.append("/");
/*     */       }
/* 248 */       stringbuilder.append(s);
/*     */     }
/* 250 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   private StringBuilder getUserBaseRelBuff() {
/* 254 */     return new StringBuilder("/").append("WEB-INF").append("/").append("t");
/*     */   }
/*     */ 
/*     */   private StringBuilder getResBaseRelBuff() {
/* 258 */     return new StringBuilder("/").append("r/gou/www");
/*     */   }
/*     */ 
/*     */   private StringBuilder getResBaseUrlBuff()
/*     */   {
/* 266 */     return getUrlBuff(false).append("/").append("r/gou/www");
/*     */   }
/*     */ 
/*     */   public String getUploadResUrlBuff()
/*     */   {
/* 274 */     String s = getGlobal().getContextPath();
/* 275 */     if (StringUtils.isNotBlank(s))
/*     */     {
/* 277 */       if (s.indexOf("/") < 0)
/* 278 */         s = "/" + s;
/*     */     }
/*     */     else {
/* 281 */       s = "";
/*     */     }
/* 283 */     return s;
/*     */   }
/*     */ 
/*     */   public String getResBaseUrl() {
/* 287 */     return getResBaseUrlBuff().toString();
/*     */   }
/*     */ 
/*     */   public String getTemplateRel(HttpServletRequest request)
/*     */   {
/* 292 */     return getTemplateRel(null, request);
/*     */   }
/*     */   public String getTemplateRel1() {
/* 295 */     StringBuilder stringbuilder = null;
/* 296 */     stringbuilder = getTemplateBuff();
/* 297 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   public StringBuilder getTemplateBuff()
/*     */   {
/* 302 */     StringBuilder stringbuilder = getUserBaseRelBuff().append("/").append("gou").append("/");
/* 303 */     return stringbuilder;
/*     */   }
/*     */ 
/*     */   public String getResPath()
/*     */   {
/* 312 */     return "/r/gou/" + getResourcesPath();
/*     */   }
/*     */ 
/*     */   public StringBuilder getTemplateRelBuff()
/*     */   {
/* 318 */     StringBuilder stringbuilder = getUserBaseRelBuff().append("/").append("gou/tpl");
/* 319 */     return stringbuilder;
/*     */   }
/*     */ 
/*     */   public StringBuilder getTemplateMobileRelBuff() {
/* 323 */     StringBuilder stringbuilder = getUserBaseRelBuff().append("/").append("gou").append("/").append("mobile");
/* 324 */     return stringbuilder;
/*     */   }
/*     */ 
/*     */   public StringBuilder getUrlBuff(boolean flag)
/*     */   {
/* 329 */     StringBuilder stringbuilder = new StringBuilder();
/* 330 */     if ((flag) || (!getRelativePath().booleanValue())) {
/* 331 */       stringbuilder = new StringBuilder("http://").append(getDomain());
/* 332 */       Integer integer = getGlobal().getPort();
/* 333 */       if ((integer != null) && (integer.intValue() != 80)) {
/* 334 */         stringbuilder.append(":").append(integer);
/*     */       }
/*     */     }
/* 337 */     if (getContextPath() != null) {
/* 338 */       stringbuilder.append(getContextPath());
/*     */     }
/* 340 */     return stringbuilder;
/*     */   }
/*     */ 
/*     */   public String getResBaseRel() {
/* 344 */     return getResBaseRelBuff().toString();
/*     */   }
/*     */ 
/*     */   public String getUploadRel() {
/* 348 */     return getUploadRel(null);
/*     */   }
/*     */ 
/*     */   public String getUploadUrl() {
/* 352 */     return getUploadUrl(null);
/*     */   }
/*     */ 
/*     */   public String getTemplate(String dir, String name, HttpServletRequest request) {
/* 356 */     return getTemplate(dir, name, null, null, request);
/*     */   }
/*     */ 
/*     */   public String getTplSys(String dir, String name, HttpServletRequest request) {
/* 360 */     return getTemplate(dir, name, null, null, request);
/*     */   }
/*     */   public String getTplTag(String s, String s1, String s2) {
/* 363 */     return getTemplate(s, s1, s2, null, null);
/*     */   }
/*     */ 
/*     */   public String getContextPath() {
/* 367 */     String s = getGlobal().getContextPath();
/* 368 */     return StringUtils.isBlank(s) ? "" : s;
/*     */   }
/*     */ 
/*     */   public Integer getPort() {
/* 372 */     return getGlobal().getPort();
/*     */   }
/*     */ 
/*     */   public String getMobileSolutionPath()
/*     */   {
/* 378 */     return "/WEB-INF/t/gou/" + getTplMobileSolution();
/*     */   }
/*     */ 
/*     */   public String getSolutionPath()
/*     */   {
/* 386 */     return "/WEB-INF/t/gou/" + getTplSolution();
/*     */   }
/*     */ 
/*     */   public String[] getDomainAliasArray() {
/* 390 */     String s = getDomainAlias();
/* 391 */     if (!StringUtils.isBlank(s)) {
/* 392 */       return s.split(",");
/*     */     }
/* 394 */     return null;
/*     */   }
/*     */ 
/*     */   public JSONObject convertToJson() throws JSONException
/*     */   {
/* 399 */     JSONObject json = new JSONObject();
/* 400 */     json.put("id", CommonUtils.parseId(getId()));
/* 401 */     json.put("name", CommonUtils.parseStr(getName()));
/* 402 */     json.put("domain", CommonUtils.parseStr(getDomain()));
/* 403 */     json.put("baseDomain", CommonUtils.parseStr(getBaseDomain()));
/* 404 */     json.put("relativePath", CommonUtils.parseBoolean(getRelativePath()));
/* 405 */     json.put("suffix", CommonUtils.parseStr(getSuffix()));
/* 406 */     json.put("localeFront", CommonUtils.parseStr(getLocaleFront()));
/* 407 */     json.put("localeAdmin", CommonUtils.parseStr(getLocaleAdmin()));
/* 408 */     json.put("pageSync", CommonUtils.parseBoolean(getPageSync()));
/* 409 */     json.put("resouceSync", CommonUtils.parseBoolean(getResouceSync()));
/* 410 */     json.put("syncPageFtpId", getSyncPageFtp() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getSyncPageFtp().getId())));
/* 411 */     json.put("uploadFtpId", getUploadFtp() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getUploadFtp().getId())));
/* 412 */     return json;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.Website
 * JD-Core Version:    0.6.0
 */