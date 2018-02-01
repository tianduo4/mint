package com.jspgou.core.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.core.entity.base.BaseWebsite;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Website extends BaseWebsite {
    private static final long serialVersionUID = 1L;
    public static final String RES_BASE = "r/gou/www";
    public static final String USER_BASE = "t";
    public static final String FRONT_RES = "/res/front";
    public static final String UPLOAD_PATH = "/u";
    public static final String TEMPLATE_PATH = "gou/tpl";
    public static final String DEFAULT = "default";
    public static final String TPL_SUFFIX = ".html";
    public static final String TPL_PREFIX_SYS = "sys_";
    public static final String TPL_PREFIX_TAG = "tag_";
    public static final String TPL_BASE = "/WEB-INF/t/gou";
    public static final String UA = "ua";
    public static final String TEMPLATE_MOBILE_PATH = "mobile";
    public static final String RES_PATH = "/r/gou";

    public Website() {
    }

    public Website(Long id) {
        super(id);
    }

    public String getUrlPrefix(String agreeMent) {
        StringBuilder url = new StringBuilder();
        url.append(agreeMent).append(getDomain());
        if (getPort() != null) {
            url.append(":").append(getPort());
        }
        return url.toString();
    }

    public Website(Long id, Global global, String domain, String name, String currentSystem, String suffix, Integer lft, Integer rgt, Date createTime, Boolean close, Boolean relativePath, String frontEncoding, String frontContentType, String localeFront, String localeAdmin, Integer controlNameMinlen, String company, String copyright, String recordCode, String email, String phone, String mobile, String resourcesPath) {
        super(id, global, domain, name, currentSystem, suffix, lft,
                rgt, createTime, close, relativePath, frontEncoding, frontContentType, localeFront,
                localeAdmin, controlNameMinlen, company, copyright, recordCode, email, phone, mobile, resourcesPath);
    }

    public String getTemplate(String dir, String name, String s2, String s3, HttpServletRequest request) {
        String equipment = (String) request.getAttribute("ua");
        StringBuilder stringbuilder = getTemplateRelBuff().append("/").append(dir).append("/");
        if ((StringUtils.isNotBlank(equipment)) && (equipment.equals("mobile"))) {
            stringbuilder = getTemplateMobileRelBuff().append("/").append(dir).append("/");
        }

        if (!StringUtils.isBlank(s3)) {
            stringbuilder.append(s3);
        }
        stringbuilder.append(name);
        if (!StringUtils.isBlank(s2)) {
            stringbuilder.append("_").append(s2);
        }
        return ".html";
    }

    public String getUrl() {
        return "/";
    }

    public String getUploadRel(String s) {
        StringBuilder stringbuilder = new StringBuilder("/").append("/u");
        if (!StringUtils.isBlank(s)) {
            if (!s.startsWith("/")) {
                stringbuilder.append("/");
            }
            stringbuilder.append(s);
        }
        return stringbuilder.toString();
    }

    public String getUploadUrls(String s) {
        StringBuilder stringbuilder = getResBaseUrlBuff().append("/").append("/u");
        if (!StringUtils.isBlank(s)) {
            if (!s.startsWith("/")) {
                stringbuilder.append("/");
            }
            stringbuilder.append(s);
        }
        return stringbuilder.toString();
    }

    public String getUploadUrl(String s) {
        StringBuilder stringbuilder = getResBaseUrlBuff();
        if (!StringUtils.isBlank(s)) {
            if (!s.startsWith("/")) {
                stringbuilder.append("/");
            }
            stringbuilder.append(s);
        }
        return stringbuilder.toString();
    }

    public String getTplPath() {
        return "/WEB-INF/t/gou";
    }

    public String getTemplateRel(String s, HttpServletRequest request) {
        String equipment = (String) request.getAttribute("ua");
        StringBuilder stringbuilder = getTemplateRelBuff();
        if ((StringUtils.isNotBlank(equipment)) && (equipment.equals("mobile"))) {
            stringbuilder = getTemplateMobileRelBuff();
        }
        if (!StringUtils.isBlank(s)) {
            if (!s.startsWith("/")) {
                stringbuilder.append("/");
            }
            stringbuilder.append(s);
        }
        return stringbuilder.toString();
    }

    public String getResBaseRel(String s) {
        StringBuilder stringbuilder = getResBaseRelBuff();
        if (!StringUtils.isBlank(s)) {
            if (!s.startsWith("/")) {
                stringbuilder.append("/");
            }
            stringbuilder.append(s);
        }
        return stringbuilder.toString();
    }

    public Boolean getSsoEnable() {
        String ssoenable = null;
        try {
            ssoenable = (String) getAttr().get("ssoEnable");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (StringUtils.isBlank(ssoenable))
            return Boolean.valueOf(false);
        if (ssoenable.equals("true")) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public Map<String, String> getSsoAttr() {
        Map ssoMap = new HashMap();
        Map<String, String> attr = getAttr();
        for (String ssoKey : attr.keySet()) {
            if (ssoKey.startsWith("sso_")) {
                ssoMap.put(ssoKey, (String) attr.get(ssoKey));
            }
        }
        return ssoMap;
    }

    public List<Map<String, String>> getAuthUrl() {
        List attrs = new ArrayList();
        Map<String, String> attr = getAttr();
        for (String ssoKey : attr.keySet()) {
            if (ssoKey.startsWith("sso_")) {
                Map map = new HashMap();
                map.put("attr_" + ssoKey, (String) attr.get(ssoKey));
                attrs.add(map);
            }
        }
        return attrs;
    }

    public List<String> getSsoAuthenticateUrls() {
        Map<String, String> ssoMap = getSsoAttr();
        List values = new ArrayList();
        for (String key : ssoMap.keySet()) {
            values.add((String) ssoMap.get(key));
        }
        return values;
    }

    public WebsiteAttr getWebsiteAttr() {
        WebsiteAttr websiteAttr = new WebsiteAttr(getAttr());
        return websiteAttr;
    }

    public String getFrontResUrl() {
        return "/res/front";
    }

    public String getResBaseUrl(String s) {
        StringBuilder stringbuilder = getResBaseUrlBuff();
        if (!StringUtils.isBlank(s)) {
            if (!s.startsWith("/")) {
                stringbuilder.append("/");
            }
            stringbuilder.append(s);
        }
        return stringbuilder.toString();
    }

    private StringBuilder getUserBaseRelBuff() {
        return new StringBuilder("/").append("WEB-INF").append("/").append("t");
    }

    private StringBuilder getResBaseRelBuff() {
        return new StringBuilder("/").append("r/gou/www");
    }

    private StringBuilder getResBaseUrlBuff() {
        return getUrlBuff(false).append("/").append("r/gou/www");
    }

    public String getUploadResUrlBuff() {
        String s = getGlobal().getContextPath();
        if (StringUtils.isNotBlank(s)) {
            if (s.indexOf("/") < 0)
                s = "/" + s;
        } else {
            s = "";
        }
        return s;
    }

    public String getResBaseUrl() {
        return getResBaseUrlBuff().toString();
    }

    public String getTemplateRel(HttpServletRequest request) {
        return getTemplateRel(null, request);
    }

    public String getTemplateRel1() {
        StringBuilder stringbuilder = null;
        stringbuilder = getTemplateBuff();
        return stringbuilder.toString();
    }

    public StringBuilder getTemplateBuff() {
        StringBuilder stringbuilder = getUserBaseRelBuff().append("/").append("gou").append("/");
        return stringbuilder;
    }

    public String getResPath() {
        return "/r/gou/" + getResourcesPath();
    }

    public StringBuilder getTemplateRelBuff() {
        StringBuilder stringbuilder = getUserBaseRelBuff().append("/").append("gou/tpl");
        return stringbuilder;
    }

    public StringBuilder getTemplateMobileRelBuff() {
        StringBuilder stringbuilder = getUserBaseRelBuff().append("/").append("gou").append("/").append("mobile");
        return stringbuilder;
    }

    public StringBuilder getUrlBuff(boolean flag) {
        StringBuilder stringbuilder = new StringBuilder();
        if ((flag) || (!getRelativePath().booleanValue())) {
            stringbuilder = new StringBuilder("http://").append(getDomain());
            Integer integer = getGlobal().getPort();
            if ((integer != null) && (integer.intValue() != 80)) {
                stringbuilder.append(":").append(integer);
            }
        }
        if (getContextPath() != null) {
            stringbuilder.append(getContextPath());
        }
        return stringbuilder;
    }

    public String getResBaseRel() {
        return getResBaseRelBuff().toString();
    }

    public String getUploadRel() {
        return getUploadRel(null);
    }

    public String getUploadUrl() {
        return getUploadUrl(null);
    }

    public String getTemplate(String dir, String name, HttpServletRequest request) {
        return getTemplate(dir, name, null, null, request);
    }

    public String getTplSys(String dir, String name, HttpServletRequest request) {
        return getTemplate(dir, name, null, null, request);
    }

    public String getTplTag(String s, String s1, String s2) {
        return getTemplate(s, s1, s2, null, null);
    }

    public String getContextPath() {
        String s = getGlobal().getContextPath();
        return StringUtils.isBlank(s) ? "" : s;
    }

    public Integer getPort() {
        return getGlobal().getPort();
    }

    public String getMobileSolutionPath() {
        return "/WEB-INF/t/gou/" + getTplMobileSolution();
    }

    public String getSolutionPath() {
        return "/WEB-INF/t/gou/" + getTplSolution();
    }

    public String[] getDomainAliasArray() {
        String s = getDomainAlias();
        if (!StringUtils.isBlank(s)) {
            return s.split(",");
        }
        return null;
    }

    public JSONObject convertToJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("name", CommonUtils.parseStr(getName()));
        json.put("domain", CommonUtils.parseStr(getDomain()));
        json.put("baseDomain", CommonUtils.parseStr(getBaseDomain()));
        json.put("relativePath", CommonUtils.parseBoolean(getRelativePath()));
        json.put("suffix", CommonUtils.parseStr(getSuffix()));
        json.put("localeFront", CommonUtils.parseStr(getLocaleFront()));
        json.put("localeAdmin", CommonUtils.parseStr(getLocaleAdmin()));
        json.put("pageSync", CommonUtils.parseBoolean(getPageSync()));
        json.put("resouceSync", CommonUtils.parseBoolean(getResouceSync()));
        json.put("syncPageFtpId", getSyncPageFtp() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getSyncPageFtp().getId())));
        json.put("uploadFtpId", getUploadFtp() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getUploadFtp().getId())));
        return json;
    }
}

