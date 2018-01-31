/*     */ package com.jspgou.cms.action.front;
/*     */ 
/*     */ import com.jspgou.cms.entity.Brand;
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.entity.Exended;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductExt;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.Relatedgoods;
/*     */ import com.jspgou.cms.entity.ShopArticle;
/*     */ import com.jspgou.cms.entity.ShopChannel;
/*     */ import com.jspgou.cms.manager.BrandMng;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.cms.manager.ExendedMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ProductStandardMng;
/*     */ import com.jspgou.cms.manager.RelatedgoodsMng;
/*     */ import com.jspgou.cms.manager.ShopArticleMng;
/*     */ import com.jspgou.cms.manager.ShopChannelMng;
/*     */ import com.jspgou.cms.manager.StandardTypeMng;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.core.web.front.FrontHelper;
/*     */ import com.jspgou.core.web.front.URLHelper;
/*     */ import com.jspgou.core.web.front.URLHelper.PageInfo;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class DynamicPageAct
/*     */ {
/*     */   public static final String TPL_INDEX = "tpl.index";
/*     */   private static final String BRAND = "tpl.brand";
/*     */   private static final String BRAND_DETAIL = "tpl.brandDetail";
/*     */   public static final String CATEGORY = "category";
/*     */   private static final String ALL_PRODUCT_CATEGORY = "tpl.allProductCategory";
/*     */ 
/*     */   @Autowired
/*     */   private CategoryMng categoryMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private RelatedgoodsMng relatedgoodsMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopChannelMng shopChannelMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopArticleMng shopArticleMng;
/*     */ 
/*     */   @Autowired
/*     */   private BrandMng brandMng;
/*     */ 
/*     */   @Autowired
/*     */   private StandardTypeMng standardTypeMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductStandardMng productStandardMng;
/*     */ 
/*     */   @Autowired
/*     */   private ExendedMng exendedMng;
/*     */ 
/*     */   @RequestMapping(value={"/index.jhtml"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String indexForWeblogic(HttpServletRequest request, ModelMap model)
/*     */   {
/* 101 */     return index(request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String index(HttpServletRequest request, ModelMap model)
/*     */   {
/* 113 */     Website web = SiteUtils.getWeb(request);
/* 114 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 115 */     return web.getTemplate("index", MessageResolver.getMessage(request, "tpl.index", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/**/*.*"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String excute(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 123 */     String url = request.getRequestURL().toString();
/*     */ 
/* 126 */     URLHelper.PageInfo info = URLHelper.getPageInfo(request);
/* 127 */     int pageNo = URLHelper.getPageNo(request);
/* 128 */     String queryString = request.getQueryString();
/* 129 */     Website web = SiteUtils.getWeb(request);
/* 130 */     ShopFrontHelper.setDynamicPageData(request, model, web, url, info.getHrefFormer(), info.getHrefLatter(), pageNo);
/* 131 */     String[] paths = URLHelper.getPaths(request);
/* 132 */     String[] params = URLHelper.getParams(request);
/* 133 */     int len = paths.length;
/* 134 */     if (len == 1)
/*     */     {
/* 136 */       return channel(paths[0], params, pageNo, queryString, url, web, request, response, model);
/* 137 */     }if (len == 2) {
/* 138 */       if (paths[1].equals("index"))
/*     */       {
/* 140 */         return channel(paths[0], params, pageNo, queryString, url, web, request, response, model);
/*     */       }
/*     */       try {
/* 143 */         Long id = Long.valueOf(Long.parseLong(paths[1]));
/*     */ 
/* 145 */         return content(paths[0], id, params, pageNo, queryString, url, web, request, response, model);
/*     */       } catch (NumberFormatException localNumberFormatException) {
/*     */       }
/*     */     }
/* 149 */     return FrontHelper.pageNotFound(web, model, request);
/*     */   }
/*     */ 
/*     */   public String channel(String path, String[] params, int pageNo, String queryString, String url, Website web, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 158 */     Category category = this.categoryMng.getByPath(web.getId(), path);
/* 159 */     if (category != null) {
/* 160 */       List exendeds = this.exendedMng.getList(category.getType().getId());
/* 161 */       Map map = new HashMap();
/* 162 */       Map map1 = new HashMap();
/* 163 */       int num = exendeds.size();
/* 164 */       for (int i = 0; i < num; i++) {
/* 165 */         map.put(((Exended)exendeds.get(i)).getId().toString(), ((Exended)exendeds.get(i)).getItems());
/* 166 */         map1.put(((Exended)exendeds.get(i)).getId().toString(), exendeds.get(i));
/*     */       }
/* 168 */       model.addAttribute("brandId", getBrandId(request));
/* 169 */       model.addAttribute("map", map);
/* 170 */       model.addAttribute("map1", map1);
/* 171 */       model.addAttribute("fields", getNames(request));
/* 172 */       model.addAttribute("zhis", getValues(request));
/* 173 */       model.addAttribute("category", category);
/* 174 */       model.addAttribute("pageSize", getpageSize(request));
/* 175 */       model.addAttribute("names", getName(request));
/* 176 */       model.addAttribute("values", getValue(request));
/* 177 */       model.addAttribute("isShow", getIsShow(request));
/* 178 */       model.addAttribute("orderBy", getOrderBy(request));
/* 179 */       model.addAttribute("startPrice", getStartPrice(request));
/* 180 */       model.addAttribute("endPrice", getEndPrice(request));
/* 181 */       return category.getTplChannelRel(request);
/*     */     }
/* 183 */     ShopChannel channel = this.shopChannelMng.getByPath(web.getId(), path);
/* 184 */     if (channel != null) {
/* 185 */       model.addAttribute("channel", channel);
/* 186 */       return channel.getTplChannelRel(request);
/*     */     }
/*     */ 
/* 189 */     return FrontHelper.pageNotFound(web, model, request);
/*     */   }
/*     */ 
/*     */   public String content(String chnlName, Long id, String[] params, int pageNo, String queryString, String url, Website web, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 199 */     Category category = this.categoryMng.getByPath(web.getId(), chnlName);
/* 200 */     if (category != null) {
/* 201 */       Product product = this.productMng.findById(id);
/* 202 */       if (product != null) {
/* 203 */         if (product.getProductFashion() != null) {
/* 204 */           String[] arr = null;
/* 205 */           arr = product.getProductFashion().getNature().split("_");
/* 206 */           model.addAttribute("arr", arr);
/*     */         }
/*     */ 
/* 209 */         List relatedgoods = this.relatedgoodsMng.findByIdProductId(id);
/* 210 */         List productList = new ArrayList();
/* 211 */         if (relatedgoods != null) {
/* 212 */           for (int i = 0; i < relatedgoods.size(); i++) {
/* 213 */             if (this.productMng.findById(((Relatedgoods)relatedgoods.get(i)).getProductIds()) != null) {
/* 214 */               Product product1 = this.productMng.findById(((Relatedgoods)relatedgoods.get(i)).getProductIds());
/* 215 */               productList.add(product1);
/*     */             }
/*     */           }
/* 218 */           model.addAttribute("productList", productList);
/*     */         }
/* 220 */         List psList = this.productStandardMng.findByProductId(id);
/* 221 */         List standardTypeList = this.standardTypeMng.getList(category.getId());
/* 222 */         this.productMng.updateViewCount(product);
/* 223 */         model.addAttribute("standardTypeList", standardTypeList);
/* 224 */         model.addAttribute("psList", psList);
/* 225 */         model.addAttribute("category", category);
/* 226 */         model.addAttribute("product", product);
/*     */ 
/* 228 */         return category.getTplContentRel(request);
/*     */       }
/* 230 */       return FrontHelper.pageNotFound(web, model, request);
/*     */     }
/*     */ 
/* 233 */     ShopArticle article = this.shopArticleMng.findById(id);
/* 234 */     if (article != null) {
/* 235 */       ShopChannel channel = article.getChannel();
/* 236 */       model.addAttribute("article", article);
/* 237 */       model.addAttribute("channel", channel);
/* 238 */       return channel.getTplContentRel(request);
/*     */     }
/* 240 */     return FrontHelper.pageNotFound(web, model, request);
/*     */   }
/*     */ 
/*     */   public String getHistoryProductIds(HttpServletRequest request)
/*     */   {
/* 248 */     String str = null;
/* 249 */     Cookie[] cookie = request.getCookies();
/* 250 */     int num = cookie.length;
/* 251 */     for (int i = 0; i < num; i++) {
/* 252 */       if (cookie[i].getName().equals("shop_record")) {
/* 253 */         str = cookie[i].getValue();
/* 254 */         break;
/*     */       }
/*     */     }
/* 257 */     return str;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/showMessage.jspx"})
/*     */   public String showMessage(HttpServletRequest request, ModelMap model, String message)
/*     */   {
/* 271 */     Website web = SiteUtils.getWeb(request);
/* 272 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 273 */     model.put("message", message);
/* 274 */     return web.getTplSys("common", MessageResolver.getMessage(request, "tpl.messagePage", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/brand.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String brand(Long id, HttpServletRequest request, ModelMap model)
/*     */   {
/* 281 */     Website web = SiteUtils.getWeb(request);
/* 282 */     WebErrors errors = validateBrand(id, request);
/* 283 */     if (errors.hasErrors())
/* 284 */       return FrontHelper.showError(errors, web, model, request);
/*     */     String tpl;
/* 286 */     if (id != null) {
/* 287 */       model.addAttribute("brand", this.brandMng.findById(id));
/* 288 */       tpl = MessageResolver.getMessage(request, "tpl.brandDetail", new Object[0]);
/*     */     } else {
/* 290 */       tpl = MessageResolver.getMessage(request, "tpl.brand", new Object[0]);
/*     */     }
/* 292 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 293 */     return web.getTplSys("shop", tpl, request);
/*     */   }
/*     */ 
/*     */   public String getBrandId(HttpServletRequest request)
/*     */   {
/* 307 */     String brandId = RequestUtils.getQueryParam(request, "brandId");
/* 308 */     return brandId;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/allProductCategory.jspx"})
/*     */   public String allProductCategory(HttpServletRequest request, ModelMap model)
/*     */   {
/* 319 */     Website web = SiteUtils.getWeb(request);
/* 320 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 321 */     return web.getTemplate("category", MessageResolver.getMessage(request, "tpl.allProductCategory", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/cateGoryListLoading.jspx"})
/*     */   public void cateGoryListLoading(String brandId, Integer categoryId, Boolean isRecommend, String[] names, String[] values, Boolean isSpecial, int orderBy, Double startPrice, Double endPrice, int pageNo, int count, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 331 */     Website web = SiteUtils.getWeb(request);
/* 332 */     Pagination pagination = this.productMng.getPageForTagChannel(brandId, web.getId(), categoryId, 
/* 333 */       null, isRecommend, names, values, isSpecial, orderBy, startPrice, endPrice, SimplePage.cpn(Integer.valueOf(pageNo)), 
/* 334 */       count);
/* 335 */     List<Product> list = ( List<Product>)pagination.getList();
/* 336 */     JSONArray arr = new JSONArray();
/*     */ 
/* 338 */     boolean no = pageNo <= pagination.getTotalPage();
/* 339 */     if ((list != null) && (list.size() > 0) && (no)) {
/*     */       try {
/* 341 */         for (Product content : list) {
/* 342 */           JSONObject o = new JSONObject();
/* 343 */           o.put("url", content.getUrl());
/* 344 */           o.put("coverImg", content.getProductExt().getCoverImg());
/* 345 */           o.put("name", content.getName());
/* 346 */           o.put("salePrice", content.getSalePrice());
/* 347 */           arr.put(o);
/*     */         }
/*     */       } catch (JSONException e) {
/* 350 */         e.printStackTrace();
/*     */       }
/* 352 */       ResponseUtils.renderJson(response, arr.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Integer getpageSize(HttpServletRequest request)
/*     */   {
/* 359 */     String pageSize = RequestUtils.getQueryParam(request, "pageSize");
/* 360 */     Integer pagesize = null;
/* 361 */     if (!StringUtils.isBlank(pageSize)) {
/* 362 */       pagesize = Integer.valueOf(Integer.parseInt(pageSize));
/*     */     }
/* 364 */     if (pagesize == null) {
/* 365 */       pagesize = Integer.valueOf(12);
/*     */     }
/* 367 */     return pagesize;
/*     */   }
/*     */ 
/*     */   public Integer getIsShow(HttpServletRequest request) {
/* 371 */     String isShow = RequestUtils.getQueryParam(request, "isShow");
/* 372 */     Integer isshow = null;
/* 373 */     if (!StringUtils.isBlank(isShow)) {
/* 374 */       isshow = Integer.valueOf(Integer.parseInt(isShow));
/*     */     }
/* 376 */     if (isshow == null) {
/* 377 */       isshow = Integer.valueOf(0);
/*     */     }
/* 379 */     return isshow;
/*     */   }
/*     */ 
/*     */   public Integer getOrderBy(HttpServletRequest request) {
/* 383 */     String orderBy = RequestUtils.getQueryParam(request, "orderBy");
/* 384 */     Integer orderby = null;
/* 385 */     if (!StringUtils.isBlank(orderBy)) {
/* 386 */       orderby = Integer.valueOf(Integer.parseInt(orderBy));
/*     */     }
/* 388 */     if (orderby == null) {
/* 389 */       orderby = Integer.valueOf(0);
/*     */     }
/* 391 */     return orderby;
/*     */   }
/*     */ 
/*     */   public String[] getNames(HttpServletRequest request) {
/* 395 */     Map attr = RequestUtils.getRequestMap(request, "exended_");
/* 396 */     List li = new ArrayList(attr.keySet());
/* 397 */     String name = "";
/* 398 */     for (int i = 0; i < li.size(); i++) {
/* 399 */       if (i + 1 == li.size())
/* 400 */         name = name + (String)li.get(i);
/*     */       else {
/* 402 */         name = name + (String)li.get(i) + ",";
/*     */       }
/*     */     }
/* 405 */     String[] names = StringUtils.split(name, ',');
/* 406 */     return names;
/*     */   }
/*     */ 
/*     */   public String[] getValues(HttpServletRequest request) {
/* 410 */     Map attr = RequestUtils.getRequestMap(request, "exended_");
/* 411 */     List li = new ArrayList(attr.keySet());
/* 412 */     String value = "";
/* 413 */     for (int i = 0; i < li.size(); i++) {
/* 414 */       if (i + 1 == li.size()) {
/* 415 */         if (StringUtils.isBlank((String)attr.get(li.get(i))))
/* 416 */           value = value + "0";
/*     */         else {
/* 418 */           value = value + (String)attr.get(li.get(i));
/*     */         }
/*     */       }
/* 421 */       else if (StringUtils.isBlank((String)attr.get(li.get(i))))
/* 422 */         value = value + "0,";
/*     */       else {
/* 424 */         value = value + (String)attr.get(li.get(i)) + ",";
/*     */       }
/*     */     }
/*     */ 
/* 428 */     String[] values = StringUtils.split(value, ',');
/* 429 */     return values;
/*     */   }
/*     */ 
/*     */   public String getName(HttpServletRequest request)
/*     */   {
/* 434 */     Map attr = RequestUtils.getRequestMap(request, "exended_");
/* 435 */     List li = new ArrayList(attr.keySet());
/* 436 */     String name = "";
/* 437 */     for (int i = 0; i < li.size(); i++) {
/* 438 */       if (i + 1 == li.size())
/* 439 */         name = name + (String)li.get(i);
/*     */       else {
/* 441 */         name = name + (String)li.get(i) + ",";
/*     */       }
/*     */     }
/*     */ 
/* 445 */     return name;
/*     */   }
/*     */ 
/*     */   public String getValue(HttpServletRequest request) {
/* 449 */     Map attr = RequestUtils.getRequestMap(request, "exended_");
/* 450 */     List li = new ArrayList(attr.keySet());
/* 451 */     String value = "";
/* 452 */     for (int i = 0; i < li.size(); i++) {
/* 453 */       if (i + 1 == li.size()) {
/* 454 */         if (StringUtils.isBlank((String)attr.get(li.get(i))))
/* 455 */           value = value + "0";
/*     */         else {
/* 457 */           value = value + (String)attr.get(li.get(i));
/*     */         }
/*     */       }
/* 460 */       else if (StringUtils.isBlank((String)attr.get(li.get(i))))
/* 461 */         value = value + "0,";
/*     */       else {
/* 463 */         value = value + (String)attr.get(li.get(i)) + ",";
/*     */       }
/*     */     }
/*     */ 
/* 467 */     return value;
/*     */   }
/*     */ 
/*     */   private WebErrors validateBrand(Long id, HttpServletRequest request) {
/* 471 */     WebErrors errors = WebErrors.create(request);
/* 472 */     if (id != null) {
/* 473 */       Brand brand = this.brandMng.findById(id);
/* 474 */       if (errors.ifNotExist(brand, Brand.class, id)) {
/* 475 */         return errors;
/*     */       }
/*     */     }
/* 478 */     return errors;
/*     */   }
/*     */ 
/*     */   public Integer getStartPrice(HttpServletRequest request)
/*     */   {
/* 483 */     String startPrice = RequestUtils.getQueryParam(request, "startPrice");
/* 484 */     Integer start = null;
/* 485 */     if (!StringUtils.isBlank(startPrice)) {
/* 486 */       if (!startPrice.equals("￥"))
/* 487 */         start = Integer.valueOf(Integer.parseInt(startPrice.replace("￥", "")));
/*     */       else {
/* 489 */         start = Integer.valueOf(0);
/*     */       }
/*     */     }
/* 492 */     if (start == null) {
/* 493 */       start = Integer.valueOf(0);
/*     */     }
/* 495 */     return start;
/*     */   }
/*     */ 
/*     */   public Integer getEndPrice(HttpServletRequest request) {
/* 499 */     String endPrice = RequestUtils.getQueryParam(request, "endPrice");
/* 500 */     Integer end = null;
/* 501 */     if (!StringUtils.isBlank(endPrice)) {
/* 502 */       if (!endPrice.equals("￥"))
/* 503 */         end = Integer.valueOf(Integer.parseInt(endPrice.replace("￥", "")));
/*     */       else {
/* 505 */         end = Integer.valueOf(0);
/*     */       }
/*     */     }
/* 508 */     if (end == null) {
/* 509 */       end = Integer.valueOf(0);
/*     */     }
/* 511 */     return end;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.front.DynamicPageAct
 * JD-Core Version:    0.6.0
 */