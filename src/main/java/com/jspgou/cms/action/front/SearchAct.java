/*     */ package com.jspgou.cms.action.front;
/*     */ 
/*     */ import com.jspgou.cms.entity.Exended;
/*     */ import com.jspgou.cms.manager.ExendedMng;
/*     */ import com.jspgou.cms.manager.KeyWordMng;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.front.URLHelper;
/*     */ import com.jspgou.core.web.front.URLHelper.PageInfo;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.context.ServletContextAware;
/*     */ 
/*     */ @Controller
/*     */ public class SearchAct
/*     */   implements ServletContextAware
/*     */ {
/*     */   private static final String SEARCH_INPUT = "tpl.searchInput";
/*     */   private static final String SEARCH_RESULT = "tpl.searchResult";
/*     */   private ServletContext servletContext;
/*     */ 
/*     */   @Autowired
/*     */   private KeyWordMng keyWordMng;
/*     */ 
/*     */   @Autowired
/*     */   private ExendedMng exendedMng;
/*     */ 
/*     */   @RequestMapping(value={"/search*.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String search(HttpServletRequest request, ModelMap model)
/*     */   {
/*  46 */     Website web = SiteUtils.getWeb(request);
/*  47 */     String url = request.getRequestURL().toString();
/*  48 */     URLHelper.PageInfo info = URLHelper.getPageInfo(request);
/*  49 */     int pageNo = URLHelper.getPageNo(request);
/*     */ 
/*  51 */     ShopFrontHelper.setDynamicPageData(request, model, web, url, info.getHrefFormer(), info.getHrefLatter(), pageNo);
/*     */ 
/*  53 */     List exendeds = this.exendedMng.getList(null);
/*  54 */     Map map = new HashMap();
/*  55 */     Map map1 = new HashMap();
/*  56 */     int num = exendeds.size();
/*  57 */     for (int i = 0; i < num; i++) {
/*  58 */       map.put(((Exended)exendeds.get(i)).getId().toString(), ((Exended)exendeds.get(i)).getItems());
/*  59 */       map1.put(((Exended)exendeds.get(i)).getId().toString(), exendeds.get(i));
/*     */     }
/*  61 */     model.addAttribute("map", map);
/*  62 */     model.addAttribute("map1", map1);
/*  63 */     model.addAttribute("brandId", getBrandId(request));
/*  64 */     model.addAttribute("fields", getNames(request));
/*  65 */     model.addAttribute("zhis", getValues(request));
/*  66 */     model.addAttribute("names", getName(request));
/*  67 */     model.addAttribute("values", getValue(request));
/*  68 */     model.addAttribute("isShow", getIsShow(request));
/*  69 */     model.addAttribute("orderBy", getOrderBy(request));
/*  70 */     model.putAll(RequestUtils.getQueryParams(request));
/*  71 */     ShopFrontHelper.setCommon(request, model, web);
/*  72 */     ShopFrontHelper.frontPage(request, model);
/*  73 */     String q = RequestUtils.getQueryParam(request, "q");
/*  74 */     q = StringUtils.trim(q);
/*  75 */     q = parseKeywords(q);
/*  76 */     String ctgId = RequestUtils.getQueryParam(request, "ctgId");
/*  77 */     ctgId = StringUtils.trim(ctgId);
/*  78 */     if ((StringUtils.isBlank(q)) && (StringUtils.isBlank(ctgId))) {
/*  79 */       model.remove("q");
/*  80 */       model.remove("ctgId");
/*  81 */       return web.getTplSys("shop", MessageResolver.getMessage(request, "tpl.searchInput", new Object[0]), request);
/*     */     }
/*  83 */     model.addAttribute("q", q);
/*  84 */     if (StringUtils.isBlank(ctgId))
/*  85 */       model.addAttribute("ctgId", null);
/*     */     else {
/*  87 */       model.addAttribute("ctgId", Integer.valueOf(ctgId));
/*     */     }
/*  89 */     this.keyWordMng.save(q);
/*  90 */     return web.getTplSys("shop", MessageResolver.getMessage(request, "tpl.searchResult", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   public String encodeFilename(HttpServletRequest request, String fileName)
/*     */   {
/*  95 */     String agent = request.getHeader("USER-AGENT");
/*     */     try
/*     */     {
/*  98 */       if ((agent != null) && (-1 != agent.indexOf("MSIE")))
/*  99 */         fileName = URLEncoder.encode(fileName, "UTF8");
/*     */       else
/* 101 */         fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 104 */       e.printStackTrace();
/*     */     }
/* 106 */     return fileName;
/*     */   }
/*     */ 
/*     */   public static String parseKeywords(String q)
/*     */   {
/*     */     try {
/* 112 */       String regular = "[\\+\\-\\&\\|\\!\\(\\)\\{\\}\\[\\]\\^\\~\\*\\?\\:\\\\]";
/* 113 */       Pattern p = Pattern.compile(regular);
/* 114 */       Matcher m = p.matcher(q);
/* 115 */       String src = null;
/* 116 */       while (m.find()) {
/* 117 */         src = m.group();
/* 118 */         q = q.replaceAll("\\" + src, "\\\\" + src);
/*     */       }
/* 120 */       q = q.replaceAll("AND", "and").replaceAll("OR", "or").replace("NOT", "not");
/*     */     } catch (Exception localException) {
/*     */     }
/* 123 */     return q;
/*     */   }
/*     */ 
/*     */   public void setServletContext(ServletContext servletContext)
/*     */   {
/* 130 */     this.servletContext = servletContext;
/*     */   }
/*     */ 
/*     */   public Integer getIsShow(HttpServletRequest request)
/*     */   {
/* 135 */     String isShow = RequestUtils.getQueryParam(request, "isShow");
/* 136 */     Integer isshow = null;
/* 137 */     if (!StringUtils.isBlank(isShow)) {
/* 138 */       isshow = Integer.valueOf(Integer.parseInt(isShow));
/*     */     }
/* 140 */     if (isshow == null) {
/* 141 */       isshow = Integer.valueOf(0);
/*     */     }
/* 143 */     return isshow;
/*     */   }
/*     */ 
/*     */   public Integer getOrderBy(HttpServletRequest request) {
/* 147 */     String orderBy = RequestUtils.getQueryParam(request, "orderBy");
/* 148 */     Integer orderby = null;
/* 149 */     if (!StringUtils.isBlank(orderBy)) {
/* 150 */       orderby = Integer.valueOf(Integer.parseInt(orderBy));
/*     */     }
/* 152 */     if (orderby == null) {
/* 153 */       orderby = Integer.valueOf(0);
/*     */     }
/* 155 */     return orderby;
/*     */   }
/*     */ 
/*     */   public String[] getNames(HttpServletRequest request) {
/* 159 */     Map attr = RequestUtils.getRequestMap(request, "exended_");
/* 160 */     List li = new ArrayList(attr.keySet());
/* 161 */     String name = "";
/* 162 */     for (int i = 0; i < li.size(); i++) {
/* 163 */       if (i + 1 == li.size())
/* 164 */         name = name + (String)li.get(i);
/*     */       else {
/* 166 */         name = name + (String)li.get(i) + ",";
/*     */       }
/*     */     }
/* 169 */     String[] names = StringUtils.split(name, ',');
/* 170 */     return names;
/*     */   }
/*     */ 
/*     */   public String[] getValues(HttpServletRequest request) {
/* 174 */     Map attr = RequestUtils.getRequestMap(request, "exended_");
/* 175 */     List li = new ArrayList(attr.keySet());
/* 176 */     String value = "";
/* 177 */     for (int i = 0; i < li.size(); i++) {
/* 178 */       if (i + 1 == li.size()) {
/* 179 */         if (StringUtils.isBlank((String)attr.get(li.get(i))))
/* 180 */           value = value + "0";
/*     */         else {
/* 182 */           value = value + (String)attr.get(li.get(i));
/*     */         }
/*     */       }
/* 185 */       else if (StringUtils.isBlank((String)attr.get(li.get(i))))
/* 186 */         value = value + "0,";
/*     */       else {
/* 188 */         value = value + (String)attr.get(li.get(i)) + ",";
/*     */       }
/*     */     }
/*     */ 
/* 192 */     String[] values = StringUtils.split(value, ',');
/* 193 */     return values;
/*     */   }
/*     */ 
/*     */   public String getName(HttpServletRequest request)
/*     */   {
/* 198 */     Map attr = RequestUtils.getRequestMap(request, "exended_");
/* 199 */     List li = new ArrayList(attr.keySet());
/* 200 */     String name = "";
/* 201 */     for (int i = 0; i < li.size(); i++) {
/* 202 */       if (i + 1 == li.size())
/* 203 */         name = name + (String)li.get(i);
/*     */       else {
/* 205 */         name = name + (String)li.get(i) + ",";
/*     */       }
/*     */     }
/*     */ 
/* 209 */     return name;
/*     */   }
/*     */ 
/*     */   public String getValue(HttpServletRequest request) {
/* 213 */     Map attr = RequestUtils.getRequestMap(request, "exended_");
/* 214 */     List li = new ArrayList(attr.keySet());
/* 215 */     String value = "";
/* 216 */     for (int i = 0; i < li.size(); i++) {
/* 217 */       if (i + 1 == li.size()) {
/* 218 */         if (StringUtils.isBlank((String)attr.get(li.get(i))))
/* 219 */           value = value + "0";
/*     */         else {
/* 221 */           value = value + (String)attr.get(li.get(i));
/*     */         }
/*     */       }
/* 224 */       else if (StringUtils.isBlank((String)attr.get(li.get(i))))
/* 225 */         value = value + "0,";
/*     */       else {
/* 227 */         value = value + (String)attr.get(li.get(i)) + ",";
/*     */       }
/*     */     }
/*     */ 
/* 231 */     return value;
/*     */   }
/*     */ 
/*     */   public Integer getBrandId(HttpServletRequest request) {
/* 235 */     String brandId = RequestUtils.getQueryParam(request, "brandId");
/* 236 */     Integer id = null;
/* 237 */     if (!StringUtils.isBlank(brandId)) {
/* 238 */       id = Integer.valueOf(Integer.parseInt(brandId));
/*     */     }
/* 240 */     if (id == null) {
/* 241 */       id = Integer.valueOf(0);
/*     */     }
/* 243 */     return id;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.front.SearchAct
 * JD-Core Version:    0.6.0
 */