package com.jspgou.cms.action.front;

import com.jspgou.cms.entity.Exended;
import com.jspgou.cms.manager.ExendedMng;
import com.jspgou.cms.manager.KeyWordMng;
import com.jspgou.cms.web.ShopFrontHelper;
import com.jspgou.common.web.RequestUtils;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.front.URLHelper;
import com.jspgou.core.web.front.URLHelper.PageInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

@Controller
public class SearchAct
        implements ServletContextAware {
    private static final String SEARCH_INPUT = "tpl.searchInput";
    private static final String SEARCH_RESULT = "tpl.searchResult";
    private ServletContext servletContext;

    @Autowired
    private KeyWordMng keyWordMng;

    @Autowired
    private ExendedMng exendedMng;

    @RequestMapping(value = {"/search*.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String search(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String url = request.getRequestURL().toString();
        URLHelper.PageInfo info = URLHelper.getPageInfo(request);
        int pageNo = URLHelper.getPageNo(request);

        ShopFrontHelper.setDynamicPageData(request, model, web, url, info.getHrefFormer(), info.getHrefLatter(), pageNo);

        List exendeds = this.exendedMng.getList(null);
        Map map = new HashMap();
        Map map1 = new HashMap();
        int num = exendeds.size();
        for (int i = 0; i < num; i++) {
            map.put(((Exended) exendeds.get(i)).getId().toString(), ((Exended) exendeds.get(i)).getItems());
            map1.put(((Exended) exendeds.get(i)).getId().toString(), exendeds.get(i));
        }
        model.addAttribute("map", map);
        model.addAttribute("map1", map1);
        model.addAttribute("brandId", getBrandId(request));
        model.addAttribute("fields", getNames(request));
        model.addAttribute("zhis", getValues(request));
        model.addAttribute("names", getName(request));
        model.addAttribute("values", getValue(request));
        model.addAttribute("isShow", getIsShow(request));
        model.addAttribute("orderBy", getOrderBy(request));
        model.putAll(RequestUtils.getQueryParams(request));
        ShopFrontHelper.setCommon(request, model, web);
        ShopFrontHelper.frontPage(request, model);
        String q = RequestUtils.getQueryParam(request, "q");
        q = StringUtils.trim(q);
        q = parseKeywords(q);
        String ctgId = RequestUtils.getQueryParam(request, "ctgId");
        ctgId = StringUtils.trim(ctgId);
        if ((StringUtils.isBlank(q)) && (StringUtils.isBlank(ctgId))) {
            model.remove("q");
            model.remove("ctgId");
            return web.getTplSys("shop", MessageResolver.getMessage(request, "tpl.searchInput", new Object[0]), request);
        }
        model.addAttribute("q", q);
        if (StringUtils.isBlank(ctgId))
            model.addAttribute("ctgId", null);
        else {
            model.addAttribute("ctgId", Integer.valueOf(ctgId));
        }
        this.keyWordMng.save(q);
        return web.getTplSys("shop", MessageResolver.getMessage(request, "tpl.searchResult", new Object[0]), request);
    }

    public String encodeFilename(HttpServletRequest request, String fileName) {
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE")))
                fileName = URLEncoder.encode(fileName, "UTF8");
            else
                fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static String parseKeywords(String q) {
        try {
            String regular = "[\\+\\-\\&\\|\\!\\(\\)\\{\\}\\[\\]\\^\\~\\*\\?\\:\\\\]";
            Pattern p = Pattern.compile(regular);
            Matcher m = p.matcher(q);
            String src = null;
            while (m.find()) {
                src = m.group();
                q = q.replaceAll("\\" + src, "\\\\" + src);
            }
            q = q.replaceAll("AND", "and").replaceAll("OR", "or").replace("NOT", "not");
        } catch (Exception localException) {
        }
        return q;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public Integer getIsShow(HttpServletRequest request) {
        String isShow = RequestUtils.getQueryParam(request, "isShow");
        Integer isshow = null;
        if (!StringUtils.isBlank(isShow)) {
            isshow = Integer.valueOf(Integer.parseInt(isShow));
        }
        if (isshow == null) {
            isshow = Integer.valueOf(0);
        }
        return isshow;
    }

    public Integer getOrderBy(HttpServletRequest request) {
        String orderBy = RequestUtils.getQueryParam(request, "orderBy");
        Integer orderby = null;
        if (!StringUtils.isBlank(orderBy)) {
            orderby = Integer.valueOf(Integer.parseInt(orderBy));
        }
        if (orderby == null) {
            orderby = Integer.valueOf(0);
        }
        return orderby;
    }

    public String[] getNames(HttpServletRequest request) {
        Map attr = RequestUtils.getRequestMap(request, "exended_");
        List li = new ArrayList(attr.keySet());
        String name = "";
        for (int i = 0; i < li.size(); i++) {
            if (i + 1 == li.size())
                name = name + (String) li.get(i);
            else {
                name = name + (String) li.get(i) + ",";
            }
        }
        String[] names = StringUtils.split(name, ',');
        return names;
    }

    public String[] getValues(HttpServletRequest request) {
        Map attr = RequestUtils.getRequestMap(request, "exended_");
        List li = new ArrayList(attr.keySet());
        String value = "";
        for (int i = 0; i < li.size(); i++) {
            if (i + 1 == li.size()) {
                if (StringUtils.isBlank((String) attr.get(li.get(i))))
                    value = value + "0";
                else {
                    value = value + (String) attr.get(li.get(i));
                }
            } else if (StringUtils.isBlank((String) attr.get(li.get(i))))
                value = value + "0,";
            else {
                value = value + (String) attr.get(li.get(i)) + ",";
            }
        }

        String[] values = StringUtils.split(value, ',');
        return values;
    }

    public String getName(HttpServletRequest request) {
        Map attr = RequestUtils.getRequestMap(request, "exended_");
        List li = new ArrayList(attr.keySet());
        String name = "";
        for (int i = 0; i < li.size(); i++) {
            if (i + 1 == li.size())
                name = name + (String) li.get(i);
            else {
                name = name + (String) li.get(i) + ",";
            }
        }

        return name;
    }

    public String getValue(HttpServletRequest request) {
        Map attr = RequestUtils.getRequestMap(request, "exended_");
        List li = new ArrayList(attr.keySet());
        String value = "";
        for (int i = 0; i < li.size(); i++) {
            if (i + 1 == li.size()) {
                if (StringUtils.isBlank((String) attr.get(li.get(i))))
                    value = value + "0";
                else {
                    value = value + (String) attr.get(li.get(i));
                }
            } else if (StringUtils.isBlank((String) attr.get(li.get(i))))
                value = value + "0,";
            else {
                value = value + (String) attr.get(li.get(i)) + ",";
            }
        }

        return value;
    }

    public Integer getBrandId(HttpServletRequest request) {
        String brandId = RequestUtils.getQueryParam(request, "brandId");
        Integer id = null;
        if (!StringUtils.isBlank(brandId)) {
            id = Integer.valueOf(Integer.parseInt(brandId));
        }
        if (id == null) {
            id = Integer.valueOf(0);
        }
        return id;
    }
}

