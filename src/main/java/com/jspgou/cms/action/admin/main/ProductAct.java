/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Brand;
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.entity.Exended;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductExended;
/*     */ import com.jspgou.cms.entity.ProductExt;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.ProductStandard;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.Relatedgoods;
/*     */ import com.jspgou.cms.entity.Standard;
/*     */ import com.jspgou.cms.lucene.LuceneProductSvc;
/*     */ import com.jspgou.cms.manager.BrandMng;
/*     */ import com.jspgou.cms.manager.CartItemMng;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.cms.manager.ExendedMng;
/*     */ import com.jspgou.cms.manager.ProductFashionMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ProductStandardMng;
/*     */ import com.jspgou.cms.manager.ProductTagMng;
/*     */ import com.jspgou.cms.manager.ProductTypeMng;
/*     */ import com.jspgou.cms.manager.ProductTypePropertyMng;
/*     */ import com.jspgou.cms.manager.RelatedgoodsMng;
/*     */ import com.jspgou.cms.manager.StandardMng;
/*     */ import com.jspgou.cms.manager.StandardTypeMng;
/*     */ import com.jspgou.common.image.ImageUtils;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.lucene.index.CorruptIndexException;
/*     */ import org.apache.lucene.queryParser.ParseException;
/*     */ import org.apache.lucene.store.LockObtainFailedException;
/*     */ import org.apache.shiro.authz.annotation.RequiresPermissions;
/*     */ import org.hibernate.ObjectNotFoundException;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.context.ServletContextAware;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @Controller
/*     */ public class ProductAct
/*     */   implements ServletContextAware
/*     */ {
/*  83 */   private static final Logger log = LoggerFactory.getLogger(ProductAct.class);
/*  84 */   private Boolean title_id1 = Boolean.valueOf(true);
/*     */ 
/*  86 */   private Boolean title_coverImg2 = Boolean.valueOf(true);
/*     */ 
/*  88 */   private Boolean title_prdtName3 = Boolean.valueOf(true);
/*     */ 
/*  90 */   private Boolean title_prdtCategory4 = Boolean.valueOf(true);
/*  91 */   private Boolean title_prdtType5 = Boolean.valueOf(true);
/*  92 */   private Boolean title_prdtSalePrice6 = Boolean.valueOf(true);
/*  93 */   private Boolean title_prdtStockCount7 = Boolean.valueOf(true);
/*  94 */   private Boolean title_prdtBrand8 = Boolean.valueOf(true);
/*  95 */   private Boolean title_prdtOnSale9 = Boolean.valueOf(true);
/*  96 */   private Boolean title_Operate10 = Boolean.valueOf(true);
/*     */ 
/*     */   @Autowired
/*     */   private StandardMng standardMng;
/*     */ 
/*     */   @Autowired
/*     */   private StandardTypeMng standardTypeMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionMng fashMng;
/*     */ 
/*     */   @Autowired
/*     */   private LuceneProductSvc luceneProductSvc;
/*     */ 
/*     */   @Autowired
/*     */   private ProductTagMng productTagMng;
/*     */ 
/*     */   @Autowired
/*     */   private RelatedgoodsMng relatedgoodsMng;
/*     */ 
/*     */   @Autowired
/*     */   private CategoryMng categoryMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ProductTypePropertyMng productTypePropertyMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionMng productFashionMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductTypeMng productTypeMng;
/*     */ 
/*     */   @Autowired
/*     */   private ExendedMng exendedMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductStandardMng productStandardMng;
/*     */ 
/*     */   @Autowired
/*     */   private CartItemMng cartItemMng;
/*     */ 
/*     */   @Autowired
/*     */   private BrandMng brandMng;
/*     */   private ServletContext servletContext;
/*     */ 
/* 105 */   @RequestMapping({"/product/v_title_list.do"})
/*     */   public String get_list_and_title(Integer ctgId, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, Integer pageNo, HttpServletRequest request, ModelMap model) { String productName = RequestUtils.getQueryParam(request, "productName");
/* 106 */     productName = StringUtils.trim(productName);
/* 107 */     String brandName = RequestUtils.getQueryParam(request, "brandName");
/* 108 */     brandName = StringUtils.trim(brandName);
/* 109 */     Website web = SiteUtils.getWeb(request);
/* 110 */     if (ctgId != null) {
/* 111 */       model.addAttribute("category", this.categoryMng.findById(ctgId));
/*     */     }
/* 113 */     Pagination pagination = this.manager.getPage(SiteUtils.getWebId(request), 
/* 114 */       ctgId, productName, brandName, status, isRecommend, isSpecial, isHotsale, isNewProduct, typeId, 
/* 115 */       startSalePrice, endSalePrice, startStock, endStock, 
/* 116 */       SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/* 117 */     List typeList = this.productTypeMng.getList(web.getId());
/*     */ 
/* 120 */     List list = this.categoryMng.getTopList(
/* 121 */       SiteUtils.getWebId(request));
/*     */ 
/* 123 */     if (list.size() > 0) {
/* 124 */       Category treeRoot = new Category();
/* 125 */       treeRoot.setName(MessageResolver.getMessage(request, 
/* 126 */         "product.allCategory", new Object[0]));
/* 127 */       treeRoot.setChild(new LinkedHashSet(list));
/* 128 */       model.addAttribute("treeRoot", treeRoot);
/*     */     }
/*     */ 
/* 131 */     model.addAttribute("typeList", typeList);
/* 132 */     model.addAttribute("productName", productName);
/* 133 */     model.addAttribute("brandName", brandName);
/* 134 */     model.addAttribute("status", status);
/* 135 */     model.addAttribute("isRecommend", isRecommend);
/* 136 */     model.addAttribute("isSpecial", isSpecial);
/* 137 */     model.addAttribute("isHotsale", isHotsale);
/* 138 */     model.addAttribute("isNewProduct", isNewProduct);
/* 139 */     model.addAttribute("typeId", typeId);
/* 140 */     model.addAttribute("startSalePrice", startSalePrice);
/* 141 */     model.addAttribute("endSalePrice", endSalePrice);
/* 142 */     model.addAttribute("startStock", startStock);
/* 143 */     model.addAttribute("endStock", endStock);
/* 144 */     model.addAttribute("pagination", pagination);
/* 145 */     model.addAttribute("ctgId", ctgId);
/* 146 */     model.addAttribute("pageNo", Integer.valueOf(pagination.getPageNo()));
/*     */ 
/* 148 */     get_set_title_status(request);
/*     */ 
/* 150 */     model.addAttribute("title_id1", this.title_id1);
/* 151 */     model.addAttribute("title_coverImg2", this.title_coverImg2);
/* 152 */     model.addAttribute("title_prdtName3", this.title_prdtName3);
/* 153 */     model.addAttribute("title_prdtCategory4", this.title_prdtCategory4);
/* 154 */     model.addAttribute("title_prdtType5", this.title_prdtType5);
/* 155 */     model.addAttribute("title_prdtSalePrice6", this.title_prdtSalePrice6);
/* 156 */     model.addAttribute("title_prdtStockCount7", this.title_prdtStockCount7);
/* 157 */     model.addAttribute("title_prdtBrand8", this.title_prdtBrand8);
/* 158 */     model.addAttribute("title_prdtOnSale9", this.title_prdtOnSale9);
/* 159 */     model.addAttribute("title_Operate10", this.title_Operate10);
/*     */ 
/* 161 */     return "product/list"; }
/*     */ 
/*     */   @RequestMapping({"/product/v_categoryList.do"})
/*     */   public String categoryList(HttpServletRequest request, ModelMap model) {
/* 166 */     Website web = SiteUtils.getWeb(request);
/*     */ 
/* 168 */     List clist = this.categoryMng.getTopList(web.getId());
/* 169 */     model.addAttribute("clist", clist);
/* 170 */     return "product/categoryList";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/product/v_product_categorychild.do"})
/*     */   public void productCategoryChild(Integer parentId, HttpServletRequest request, HttpServletResponse response) throws JSONException {
/* 176 */     Website web = SiteUtils.getWeb(request);
/* 177 */     JSONArray arr = new JSONArray();
/* 178 */     if (parentId != null) {
/* 179 */       List clist = this.categoryMng.getChildList(web.getId(), parentId);
/* 180 */       if (clist.size() >= 0)
/*     */       {
/* 182 */         for (Category t : clist) {
/* 183 */           JSONObject o = new JSONObject();
/* 184 */           o.put("success", true);
/* 185 */           o.put("id", t.getId());
/* 186 */           o.put("name", t.getName());
/* 187 */           arr.put(o);
/*     */         }
/*     */       }
/*     */     }
/* 191 */     ResponseUtils.renderJson(response, arr.toString());
/*     */   }
/*     */   @RequestMapping({"/product/v_left.do"})
/*     */   public String left(HttpServletRequest request, ModelMap model) {
/* 196 */     List list = this.categoryMng.getTopList(
/* 197 */       SiteUtils.getWebId(request));
/*     */ 
/* 199 */     if (list.size() > 0) {
/* 200 */       Category treeRoot = new Category();
/* 201 */       treeRoot.setName(MessageResolver.getMessage(request, 
/* 202 */         "product.allCategory", new Object[0]));
/* 203 */       treeRoot.setChild(new LinkedHashSet(list));
/* 204 */       model.addAttribute("treeRoot", treeRoot);
/*     */     }
/* 206 */     return "product/left";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/product/ajaxcategory.do"})
/*     */   public void ajaxcategory(Integer ctgId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
/* 212 */     Category category = this.categoryMng.findById(ctgId);
/*     */ 
/* 214 */     List list2 = new ArrayList();
/* 215 */     list2.addAll(category.getBrands());
/*     */ 
/* 217 */     Long[] ids = new Long[list2.size()];
/* 218 */     String[] names = new String[list2.size()];
/* 219 */     int i = 0; for (int j = list2.size(); i < j; i++) {
/* 220 */       Brand brand = (Brand)list2.get(i);
/* 221 */       ids[i] = brand.getId();
/* 222 */       names[i] = brand.getName();
/*     */     }
/*     */ 
/* 225 */     JSONObject json = new JSONObject();
/*     */     try {
/* 227 */       json.put("ids", ids);
/* 228 */       json.put("names", names);
/*     */     }
/*     */     catch (JSONException e) {
/* 231 */       e.printStackTrace();
/*     */     }
/* 233 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequiresPermissions({"product:v_add"})
/*     */   @RequestMapping({"/product/v_add.do"})
/*     */   public String add(Integer ctgId, HttpServletRequest request, ModelMap model)
/*     */   {
/* 246 */     Category category = this.categoryMng.findById(ctgId);
/*     */ 
/* 249 */     List itemList = this.productTypePropertyMng.getList(category.getType().getId(), false);
/* 250 */     List standardTypeList = this.standardTypeMng.getList(category.getId());
/* 251 */     List typeList = this.productTypeMng.getList(Long.valueOf(1L));
/* 252 */     List brandList = this.brandMng.getAllList();
/*     */ 
/* 254 */     List brandList1 = this.brandMng.getBrandList(category.getName());
/* 255 */     model.addAttribute("brandList1", brandList1);
/*     */ 
/* 257 */     model.addAttribute("brandList", brandList);
/* 258 */     model.addAttribute("typeList", typeList);
/* 259 */     model.addAttribute("ctgId", ctgId);
/* 260 */     model.addAttribute("category", category);
/*     */ 
/* 262 */     model.addAttribute("categoryList", this.categoryMng.getList(SiteUtils.getWebId(request)));
/*     */ 
/* 264 */     model.addAttribute("tagList", this.productTagMng.getList(SiteUtils.getWebId(request)));
/* 265 */     model.addAttribute("standardTypeList", standardTypeList);
/* 266 */     model.addAttribute("itemList", itemList);
/* 267 */     List exendeds = this.exendedMng.getList(category.getType().getId());
/* 268 */     Map map = new HashMap();
/* 269 */     Map map1 = new HashMap();
/* 270 */     int num = exendeds.size();
/* 271 */     for (int i = 0; i < num; i++) {
/* 272 */       map.put(((Exended)exendeds.get(i)).getId().toString(), ((Exended)exendeds.get(i)).getItems());
/* 273 */       map1.put(((Exended)exendeds.get(i)).getId().toString(), exendeds.get(i));
/*     */     }
/* 275 */     model.addAttribute("map", map);
/* 276 */     model.addAttribute("map1", map1);
/* 277 */     return "product/add";
/*     */   }
/*     */   @RequiresPermissions({"product:v_edit"})
/*     */   @RequestMapping({"/product/v_edit.do"})
/*     */   public String edit(Long id, Long ctgId, HttpServletRequest request, ModelMap model) {
/* 284 */     WebErrors errors = validateEdit(id, request);
/* 285 */     if (errors.hasErrors()) {
/* 286 */       return errors.showErrorPage(model);
/*     */     }
/* 288 */     Product product = this.manager.findById(id);
/* 289 */     List psList = this.productStandardMng.findByProductId(id);
/* 290 */     String productKeywords = StringUtils.join(product.getKeywords(), ",");
/* 291 */     Category category = product.getCategory();
/* 292 */     List standardTypeList = this.standardTypeMng.getList(category.getId());
/* 293 */     List itemList = this.productTypePropertyMng.getList(category.getType().getId(), false);
/* 294 */     List pelist = product.getExendeds();
/* 295 */     List exendeds = this.exendedMng.getList(category.getType().getId());
/* 296 */     List typeList = this.productTypeMng.getList(Long.valueOf(1L));
/* 297 */     List brandList = this.brandMng.getAllList();
/* 298 */     model.addAttribute("brandList", brandList);
/* 299 */     model.addAttribute("typeList", typeList);
/*     */ 
/* 301 */     List relatedgoods = this.relatedgoodsMng.findByIdProductId(id);
/* 302 */     List productList = new ArrayList();
/* 303 */     if (relatedgoods != null) {
/* 304 */       for (int i = 0; i < relatedgoods.size(); i++) {
/* 305 */         if (this.manager.findById(((Relatedgoods)relatedgoods.get(i)).getProductIds()) != null) {
/* 306 */           Product product1 = this.manager.findById(((Relatedgoods)relatedgoods.get(i)).getProductIds());
/* 307 */           productList.add(product1);
/*     */         }
/*     */       }
/* 310 */       model.addAttribute("productList", productList);
/*     */     }
/* 312 */     Map map = new HashMap();
/* 313 */     Map map1 = new HashMap();
/* 314 */     int num = exendeds.size();
/* 315 */     for (int i = 0; i < num; i++) {
/* 316 */       map.put(((Exended)exendeds.get(i)).getId().toString(), ((Exended)exendeds.get(i)).getItems());
/* 317 */       map1.put(((Exended)exendeds.get(i)).getId().toString(), exendeds.get(i));
/*     */     }
/* 319 */     Map map2 = new HashMap();
/* 320 */     for (int i = 0; i < pelist.size(); i++) {
/* 321 */       map2.put(((ProductExended)pelist.get(i)).getName(), ((ProductExended)pelist.get(i)).getValue());
/*     */     }
/* 323 */     model.addAttribute("map2", map2);
/* 324 */     model.addAttribute("map", map);
/* 325 */     model.addAttribute("map1", map1);
/* 326 */     model.addAttribute("productKeywords", productKeywords);
/* 327 */     model.addAttribute("tagList", this.productTagMng.getList(SiteUtils.getWebId(request)));
/* 328 */     model.addAttribute("categoryList", this.categoryMng.getList(SiteUtils.getWebId(request)));
/* 329 */     model.addAttribute("standardTypeList", standardTypeList);
/* 330 */     model.addAttribute("category", category);
/* 331 */     model.addAttribute("product", product);
/* 332 */     model.addAttribute("ctgId", ctgId);
/* 333 */     model.addAttribute("isLimit", product.getProductExt().getIslimitTime());
/* 334 */     model.addAttribute("itemList", itemList);
/* 335 */     model.addAttribute("psList", psList);
/* 336 */     return "product/edit";
/*     */   }
/*     */ 
/*     */   @RequiresPermissions({"product:o_save"})
/*     */   @RequestMapping({"/product/o_save.do"})
/*     */   public String save(Product bean, ProductExt ext, String rightlist, Integer categoryId, Long brandId, Long[] tagIds, String productKeywords, String[] nature, Long[] picture, String[] colorImg, Long[] character, @RequestParam(value="file", required=false) MultipartFile file, String[] fashionSwitchPic, String[] fashionBigPic, String[] fashionAmpPic, Boolean[] isDefaults, Long[] colors, Long[] measures, Integer[] stockCounts, Double[] salePrices, Double[] marketPrices, Double[] costPrices, Integer ctgId, HttpServletRequest request, ModelMap model)
/*     */   {
/* 348 */     WebErrors errors = validateSave(bean, file, request);
/* 349 */     if (errors.hasErrors()) {
/* 350 */       return errors.showErrorPage(model);
/*     */     }
/* 352 */     productKeywords = StringUtils.replace(productKeywords, MessageResolver.getMessage(request, "product.keywords.split", new Object[0]), ",");
/* 353 */     String[] keywords = StringUtils.split(productKeywords, ",");
/* 354 */     Website web = SiteUtils.getWeb(request);
/* 355 */     Map exended = RequestUtils.getRequestMap(request, "exended_");
/* 356 */     List li = new ArrayList(exended.keySet());
/* 357 */     String[] names = new String[li.size()];
/* 358 */     String[] values = new String[li.size()];
/* 359 */     if (stockCounts != null) {
/* 360 */       Integer stockCount = Integer.valueOf(0);
/* 361 */       for (Integer s : stockCounts) {
/* 362 */         stockCount = Integer.valueOf(stockCount.intValue() + s.intValue());
/*     */       }
/* 364 */       bean.setStockCount(stockCount);
/*     */     }
/* 366 */     for (int i = 0; i < li.size(); i++) {
/* 367 */       names[i] = ((String)li.get(i));
/* 368 */       values[i] = ((String)exended.get(li.get(i)));
/*     */     }
/* 370 */     bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
/* 371 */     bean = this.manager.save(bean, ext, web.getId(), categoryId, brandId, tagIds, 
/* 372 */       keywords, names, values, fashionSwitchPic, fashionBigPic, fashionAmpPic, file);
/* 373 */     this.relatedgoodsMng.addProduct(bean.getId(), getProductIds(rightlist));
/* 374 */     if (picture != null) {
/* 375 */       for (int i = 0; i < picture.length; i++) {
/* 376 */         ProductStandard ps = new ProductStandard();
/* 377 */         ps.setImgPath(colorImg[i]);
/* 378 */         ps.setType(this.standardMng.findById(picture[i]).getType());
/* 379 */         ps.setProduct(bean);
/* 380 */         ps.setStandard(this.standardMng.findById(picture[i]));
/* 381 */         ps.setDataType(true);
/* 382 */         this.productStandardMng.save(ps);
/*     */       }
/*     */     }
/* 385 */     if (character != null) {
/* 386 */       for (int i = 0; i < character.length; i++) {
/* 387 */         ProductStandard ps = new ProductStandard();
/* 388 */         ps.setType(this.standardMng.findById(character[i]).getType());
/* 389 */         ps.setProduct(bean);
/* 390 */         ps.setStandard(this.standardMng.findById(character[i]));
/* 391 */         ps.setDataType(false);
/* 392 */         this.productStandardMng.save(ps);
/*     */       }
/*     */     }
/* 395 */     saveProductFash(bean, nature, isDefaults, stockCounts, salePrices, marketPrices, costPrices);
/*     */     try
/*     */     {
/* 400 */       if (bean.getStatus().intValue() == Product.ON_SALE_STATUS)
/* 401 */         this.luceneProductSvc.createIndex(bean);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 405 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 408 */     log.info("save Product. id={}", bean.getId());
/* 409 */     model.addAttribute("message", "global.success");
/* 410 */     model.addAttribute("brandId", brandId);
/* 411 */     return add(ctgId, request, model);
/*     */   }
/*     */ 
/*     */   @RequiresPermissions({"product:o_update"})
/*     */   @RequestMapping({"/product/o_update.do"})
/*     */   public String update(Product bean, ProductExt ext, String rightlist, Integer categoryId, Long brandId, Long[] tagIds, String productKeywords, String[] nature, Long[] picture, String[] colorImg, Long[] character, @RequestParam(value="file", required=false) MultipartFile file, String[] fashionSwitchPic, String[] fashionBigPic, String[] fashionAmpPic, Boolean[] isDefaults, Long[] colors, Long[] measures, Integer[] stockCounts, Double[] salePrices, Double[] marketPrices, Double[] costPrices, Long[] fashId, Integer ctgId, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/* 424 */     WebErrors errors = validateUpdate(bean.getId(), file, request);
/* 425 */     if (errors.hasErrors()) {
/* 426 */       return errors.showErrorPage(model);
/*     */     }
/* 428 */     productKeywords = StringUtils.replace(productKeywords, 
/* 429 */       MessageResolver.getMessage(request, "product.keywords.split", new Object[0]), ",");
/* 430 */     String[] keywords = StringUtils.split(productKeywords, ",");
/* 431 */     Map exended = RequestUtils.getRequestMap(request, "exended_");
/* 432 */     List li = new ArrayList(exended.keySet());
/* 433 */     String[] names = new String[li.size()];
/* 434 */     String[] values = new String[li.size()];
/* 435 */     for (int i = 0; i < li.size(); i++) {
/* 436 */       names[i] = ((String)li.get(i));
/* 437 */       values[i] = ((String)exended.get(li.get(i)));
/*     */     }
/* 439 */     Map attr = RequestUtils.getRequestMap(request, "attr_");
/* 440 */     if (stockCounts != null) {
/* 441 */       Integer stockCount = Integer.valueOf(0);
/* 442 */       for (Integer s : stockCounts) {
/* 443 */         stockCount = Integer.valueOf(stockCount.intValue() + s.intValue());
/*     */       }
/* 445 */       bean.setStockCount(stockCount);
/*     */     }
/* 447 */     bean = this.manager.update(bean, ext, categoryId, brandId, tagIds, keywords, names, values, attr, 
/* 448 */       fashionSwitchPic, fashionBigPic, fashionAmpPic, file);
/*     */ 
/* 450 */     if (this.relatedgoodsMng.findByIdProductId(bean.getId()) != null) {
/* 451 */       this.relatedgoodsMng.updateProduct(bean.getId(), getProductIds(rightlist));
/*     */     }
/* 453 */     List pcList = this.productStandardMng.findByProductId(bean.getId());
/* 454 */     for (int j = 0; j < pcList.size(); j++) {
/* 455 */       this.productStandardMng.deleteById(((ProductStandard)pcList.get(j)).getId());
/*     */     }
/* 457 */     if (picture != null) {
/* 458 */       for (int i = 0; i < picture.length; i++) {
/* 459 */         ProductStandard ps = new ProductStandard();
/* 460 */         ps.setImgPath(colorImg[i]);
/* 461 */         ps.setType(this.standardMng.findById(picture[i]).getType());
/* 462 */         ps.setProduct(bean);
/* 463 */         ps.setStandard(this.standardMng.findById(picture[i]));
/* 464 */         ps.setDataType(true);
/* 465 */         this.productStandardMng.save(ps);
/*     */       }
/*     */     }
/* 468 */     if (character != null)
/* 469 */       for (int i = 0; i < character.length; i++) {
/* 470 */         ProductStandard ps = new ProductStandard();
/* 471 */         ps.setType(this.standardMng.findById(character[i]).getType());
/* 472 */         ps.setProduct(bean);
/* 473 */         ps.setStandard(this.standardMng.findById(character[i]));
/* 474 */         ps.setDataType(false);
/* 475 */         this.productStandardMng.save(ps);
/*     */       }
/*     */     try
/*     */     {
/* 479 */       if (bean.getCategory().getColorsize().booleanValue()) {
/* 480 */         Set pfList = bean.getFashions();
/*     */ 
/* 482 */         if (fashId != null) {
/* 483 */           for (ProductFashion ps : pfList)
/* 484 */             if (!Arrays.asList(fashId).contains(ps.getId())) {
/* 485 */               this.fashMng.deleteById(ps.getId());
/* 486 */               this.cartItemMng.deleteByProductFactionId(ps.getId());
/*     */             }
/*     */         }
/*     */         else {
/* 490 */           for (ProductFashion ps : pfList) {
/* 491 */             this.fashMng.deleteById(ps.getId());
/* 492 */             this.cartItemMng.deleteByProductFactionId(ps.getId());
/*     */           }
/*     */         }
/* 495 */         updateProductFash(bean, fashId, nature, isDefaults, stockCounts, salePrices, marketPrices, costPrices);
/*     */       }
/*     */     } catch (ObjectNotFoundException localObjectNotFoundException) {
/*     */     }
/*     */     catch (Exception e) {
/* 500 */       errors.addErrorString(this.manager.getTipFile("This.ChangeIsContainedInTheCaseOfTheDeletionOfTheGoods"));
/* 501 */       return errors.showErrorPage(model);
/*     */     }
/*     */     try
/*     */     {
/* 505 */       this.luceneProductSvc.updateIndex(bean);
/*     */     }
/*     */     catch (IOException e) {
/* 508 */       e.printStackTrace();
/*     */     }
/*     */     catch (ParseException e) {
/* 511 */       e.printStackTrace();
/*     */     }
/* 513 */     log.info("update Product. id={}.", bean.getId());
/* 514 */     return get_list_and_title(ctgId, null, null, null, null, null, 
/* 515 */       null, null, null, null, null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/product/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer ctgId, Boolean isRecommend, Boolean isSpecial, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/* 523 */     WebErrors errors = validateDelete(ids, request);
/* 524 */     if (errors.hasErrors()) {
/* 525 */       return errors.showErrorPage(model);
/*     */     }
/*     */     try
/*     */     {
/* 529 */       Product[] beans = this.manager.deleteByIds(ids);
/* 530 */       for (Product bean : beans)
/*     */       {
/* 533 */         this.luceneProductSvc.deleteIndex(bean);
/* 534 */         log.info("delete Product. id={}", bean.getId());
/*     */       }
/*     */     } catch (Exception e) {
/* 537 */       return "redirect:v_error.do";
/*     */     }
/*     */     Product[] beans;
/* 539 */     return get_list_and_title(ctgId, null, isRecommend, isSpecial, null, null, null, null, null, null, null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/product/v_error.do"})
/*     */   public String error(HttpServletRequest request, ModelMap model) {
/* 545 */     return "product/error";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/product/v_standardTypes_add.do"})
/*     */   public String standardTypesAdd(Integer categoryId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 551 */     List standardTypeList = this.standardTypeMng.getList(categoryId);
/* 552 */     model.addAttribute("standardTypeList", standardTypeList);
/* 553 */     model.addAttribute("digit", Integer.valueOf(standardTypeList.size()));
/* 554 */     response.setHeader("Cache-Control", "no-cache");
/* 555 */     response.setContentType("text/json;charset=UTF-8");
/* 556 */     return "product/standardTypes_add";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/product/v_standards_add.do"})
/*     */   public String standards(Long standardTypeId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
/* 562 */     List sList = this.standardMng.findByTypeId(standardTypeId);
/* 563 */     model.addAttribute("sList", sList);
/* 564 */     model.addAttribute("standardType", this.standardTypeMng.findById(standardTypeId));
/* 565 */     response.setHeader("Cache-Control", "no-cache");
/* 566 */     response.setContentType("text/json;charset=UTF-8");
/* 567 */     return "product/standards_add";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/product/o_create_index.do"})
/*     */   public String createIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws ParseException {
/* 573 */     String path = this.servletContext.getRealPath("/WEB-INF/lucene");
/* 574 */     boolean success = false;
/*     */     try {
/* 576 */       int count = this.luceneProductSvc.index(path, null, null, null);
/* 577 */       model.addAttribute("count", Integer.valueOf(count));
/* 578 */       success = true;
/*     */     } catch (CorruptIndexException e) {
/* 580 */       log.error("", e);
/*     */     } catch (LockObtainFailedException e) {
/* 582 */       log.error("", e);
/*     */     } catch (IOException e) {
/* 584 */       log.error("", e);
/*     */     }
/* 586 */     model.addAttribute("success", Boolean.valueOf(success));
/* 587 */     return "product/create_index_result";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/product/o_delFashion.do"})
/*     */   public String deleFashion(Long id, Long productId, HttpServletResponse response) throws JSONException
/*     */   {
/* 594 */     Boolean b = this.productFashionMng.getOneFash(productId);
/* 595 */     JSONObject j = new JSONObject();
/* 596 */     if ((b != null) && (b.booleanValue())) {
/* 597 */       this.productFashionMng.deleteById(id);
/* 598 */       j.put("message", "删除成功！");
/* 599 */       j.put("boo", true);
/* 600 */       ResponseUtils.renderJson(response, j.toString());
/* 601 */       return null;
/*     */     }
/* 603 */     j.put("message", "必须留一款式！");
/* 604 */     j.put("boo", false);
/* 605 */     ResponseUtils.renderJson(response, j.toString());
/* 606 */     return null;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/product/v_search.do"})
/*     */   public void search(Long typeId, Long brandId, String productName, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 613 */     List list = this.manager.getList(typeId, brandId, productName);
/* 614 */     JSONObject json = new JSONObject();
/*     */     try {
/* 616 */       int i = 0; for (int j = list.size(); i < j; i++)
/* 617 */         json.append(((Product)list.get(i)).getId(), ((Product)list.get(i)).getName());
/*     */     }
/*     */     catch (JSONException e) {
/* 620 */       e.printStackTrace();
/*     */     }
/* 622 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   public Long[] getProductIds(String rightlist)
/*     */   {
/* 627 */     String[] arr = rightlist.split(",");
/* 628 */     Long[] productIds = new Long[arr.length];
/* 629 */     int i = 0; for (int j = arr.length; i < j; i++) {
/* 630 */       if (!arr[i].equals("")) {
/* 631 */         productIds[i] = Long.valueOf(Long.parseLong(arr[i]));
/*     */       }
/*     */     }
/* 634 */     return productIds;
/*     */   }
/*     */ 
/*     */   private void saveProductFash(Product bean, String[] nature, Boolean[] isDefaults, Integer[] stockCounts, Double[] salePrices, Double[] marketPrices, Double[] costPrices)
/*     */   {
/* 640 */     if (nature != null)
/* 641 */       for (int i = 0; i < nature.length; i++)
/*     */       {
/* 643 */         ProductFashion pfash = new ProductFashion();
/* 644 */         pfash.setCreateTime(new Date());
/* 645 */         pfash.setIsDefault(isDefaults[i]);
/* 646 */         pfash.setStockCount(stockCounts[i]);
/* 647 */         pfash.setMarketPrice(marketPrices[i]);
/* 648 */         pfash.setSalePrice(salePrices[i]);
/* 649 */         pfash.setCostPrice(costPrices[i]);
/* 650 */         pfash.setProductId(bean);
/* 651 */         pfash.setNature(nature[i]);
/* 652 */         String[] arr = nature[i].split("_");
/* 653 */         ProductFashion fashion = this.productFashionMng.save(pfash, arr);
/* 654 */         this.productFashionMng.addStandard(fashion, arr);
/* 655 */         if (isDefaults[i].booleanValue()) {
/* 656 */           bean.setCostPrice(costPrices[i]);
/* 657 */           bean.setMarketPrice(marketPrices[i]);
/* 658 */           bean.setSalePrice(salePrices[i]);
/* 659 */           this.manager.update(bean);
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   private void updateProductFash(Product bean, Long[] fashId, String[] nature, Boolean[] isDefaults, Integer[] stockCounts, Double[] salePrices, Double[] marketPrices, Double[] costPrices)
/*     */   {
/* 668 */     if (nature != null)
/* 669 */       for (int i = 0; i < nature.length; i++)
/*     */       {
/* 672 */         if ((fashId != null) && (i < fashId.length)) {
/* 673 */           ProductFashion pfash = this.productFashionMng.findById(fashId[i]);
/* 674 */           pfash.setCreateTime(new Date());
/* 675 */           pfash.setIsDefault(isDefaults[i]);
/* 676 */           pfash.setStockCount(stockCounts[i]);
/* 677 */           pfash.setMarketPrice(marketPrices[i]);
/* 678 */           pfash.setSalePrice(salePrices[i]);
/* 679 */           pfash.setCostPrice(costPrices[i]);
/* 680 */           pfash.setProductId(bean);
/* 681 */           pfash.setNature(nature[i]);
/* 682 */           String[] arr = nature[i].split("_");
/* 683 */           this.productFashionMng.updateStandard(pfash, arr);
/* 684 */           if (isDefaults[i].booleanValue()) {
/* 685 */             bean.setCostPrice(costPrices[i]);
/* 686 */             bean.setMarketPrice(marketPrices[i]);
/* 687 */             bean.setSalePrice(salePrices[i]);
/* 688 */             this.manager.update(bean);
/*     */           }
/*     */         } else {
/* 691 */           ProductFashion pfash = new ProductFashion();
/* 692 */           pfash.setCreateTime(new Date());
/* 693 */           pfash.setIsDefault(isDefaults[i]);
/* 694 */           pfash.setStockCount(stockCounts[i]);
/* 695 */           pfash.setMarketPrice(marketPrices[i]);
/* 696 */           pfash.setSalePrice(salePrices[i]);
/* 697 */           pfash.setCostPrice(costPrices[i]);
/* 698 */           pfash.setProductId(bean);
/* 699 */           pfash.setNature(nature[i]);
/* 700 */           String[] arr = nature[i].split("_");
/* 701 */           ProductFashion fashion = this.productFashionMng.save(pfash, arr);
/* 702 */           this.productFashionMng.addStandard(fashion, arr);
/* 703 */           if (isDefaults[i].booleanValue()) {
/* 704 */             bean.setCostPrice(costPrices[i]);
/* 705 */             bean.setMarketPrice(marketPrices[i]);
/* 706 */             bean.setSalePrice(salePrices[i]);
/* 707 */             this.manager.update(bean);
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   @RequiresPermissions({"product:v_shangjia"})
/*     */   @RequestMapping({"/product/v_shangjia.do"})
/*     */   public String shangjia(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/* 720 */     for (Long id : ids) {
/* 721 */       Product bean = this.manager.findById(id);
/*     */ 
/* 723 */       bean.setStatus(Integer.valueOf(Product.ON_SALE_STATUS));
/* 724 */       bean = this.manager.update(bean);
/*     */       try {
/* 726 */         this.luceneProductSvc.updateIndex(bean);
/*     */       } catch (IOException e) {
/* 728 */         e.printStackTrace();
/*     */       } catch (ParseException e) {
/* 730 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 733 */     return "redirect:v_title_list.do";
/*     */   }
/*     */ 
/*     */   @RequiresPermissions({"product:v_xiajia"})
/*     */   @RequestMapping({"/product/v_xiajia.do"})
/*     */   public String xiajia(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/* 750 */     for (Long id : ids) {
/* 751 */       Product bean = this.manager.findById(id);
/*     */ 
/* 753 */       bean.setStatus(Integer.valueOf(Product.NOT_SALE_STATUS));
/* 754 */       bean = this.manager.update(bean);
/*     */       try {
/* 756 */         this.luceneProductSvc.updateIndex(bean);
/*     */       } catch (IOException e) {
/* 758 */         e.printStackTrace();
/*     */       } catch (ParseException e) {
/* 760 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 763 */     return "redirect:v_title_list.do";
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Product bean, MultipartFile file, HttpServletRequest request)
/*     */   {
/* 769 */     WebErrors errors = WebErrors.create(request);
/* 770 */     if ((file != null) && (!file.isEmpty())) {
/* 771 */       String name = file.getOriginalFilename();
/* 772 */       vldImage(name, errors);
/*     */     }
/* 774 */     bean.setWebsite(SiteUtils.getWeb(request));
/* 775 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 779 */     WebErrors errors = WebErrors.create(request);
/* 780 */     errors.ifNull(id, "id");
/* 781 */     vldExist(id, errors);
/* 782 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, MultipartFile file, HttpServletRequest request)
/*     */   {
/* 787 */     WebErrors errors = WebErrors.create(request);
/* 788 */     errors.ifNull(id, "id");
/* 789 */     if ((file != null) && (!file.isEmpty())) {
/* 790 */       String name = file.getOriginalFilename();
/* 791 */       vldImage(name, errors);
/* 792 */       if (errors.hasErrors()) {
/* 793 */         return errors;
/*     */       }
/*     */     }
/* 796 */     vldExist(id, errors);
/* 797 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 801 */     WebErrors errors = WebErrors.create(request);
/* 802 */     errors.ifEmpty(ids, "ids");
/* 803 */     for (Long id : ids) {
/* 804 */       vldExist(id, errors);
/*     */     }
/* 806 */     return errors;
/*     */   }
/*     */ 
/*     */   private void vldExist(Long id, WebErrors errors) {
/* 810 */     if (errors.hasErrors()) {
/* 811 */       return;
/*     */     }
/* 813 */     Product entity = this.manager.findById(id);
/* 814 */     errors.ifNotExist(entity, Product.class, id);
/*     */   }
/*     */ 
/*     */   private void vldImage(String filename, WebErrors errors) {
/* 818 */     if (errors.hasErrors()) {
/* 819 */       return;
/*     */     }
/* 821 */     String ext = FilenameUtils.getExtension(filename);
/* 822 */     if (!ImageUtils.isImage(ext))
/* 823 */       errors.addErrorString("not support image extension:" + filename);
/*     */   }
/*     */ 
/*     */   private void get_set_title_status(HttpServletRequest request)
/*     */   {
/* 834 */     String checked = request.getParameter("checked");
/* 835 */     String index = request.getParameter("index");
/*     */ 
/* 837 */     if ((StringUtils.isNotBlank(checked)) && (StringUtils.isNotBlank(index))) {
/* 838 */       if (index.equals("1")) {
/* 839 */         this.title_id1 = Boolean.valueOf(Boolean.parseBoolean(checked));
/*     */       }
/* 841 */       if (index.equals("2")) {
/* 842 */         this.title_coverImg2 = Boolean.valueOf(Boolean.parseBoolean(checked));
/*     */       }
/* 844 */       if (index.equals("3")) {
/* 845 */         this.title_prdtName3 = Boolean.valueOf(Boolean.parseBoolean(checked));
/*     */       }
/* 847 */       if (index.equals("4")) {
/* 848 */         this.title_prdtCategory4 = Boolean.valueOf(Boolean.parseBoolean(checked));
/*     */       }
/* 850 */       if (index.equals("5")) {
/* 851 */         this.title_prdtType5 = Boolean.valueOf(Boolean.parseBoolean(checked));
/*     */       }
/* 853 */       if (index.equals("6")) {
/* 854 */         this.title_prdtSalePrice6 = Boolean.valueOf(Boolean.parseBoolean(checked));
/*     */       }
/* 856 */       if (index.equals("7")) {
/* 857 */         this.title_prdtStockCount7 = Boolean.valueOf(Boolean.parseBoolean(checked));
/*     */       }
/* 859 */       if (index.equals("8")) {
/* 860 */         this.title_prdtBrand8 = Boolean.valueOf(Boolean.parseBoolean(checked));
/*     */       }
/* 862 */       if (index.equals("9")) {
/* 863 */         this.title_prdtOnSale9 = Boolean.valueOf(Boolean.parseBoolean(checked));
/*     */       }
/* 865 */       if (index.equals("10")) {
/* 866 */         this.title_Operate10 = Boolean.valueOf(Boolean.parseBoolean(checked));
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 871 */       this.title_id1 = Boolean.valueOf(true);
/* 872 */       this.title_coverImg2 = Boolean.valueOf(true);
/* 873 */       this.title_prdtName3 = Boolean.valueOf(true);
/* 874 */       this.title_prdtCategory4 = Boolean.valueOf(true);
/* 875 */       this.title_prdtType5 = Boolean.valueOf(true);
/* 876 */       this.title_prdtSalePrice6 = Boolean.valueOf(true);
/* 877 */       this.title_prdtStockCount7 = Boolean.valueOf(true);
/* 878 */       this.title_prdtBrand8 = Boolean.valueOf(true);
/* 879 */       this.title_prdtOnSale9 = Boolean.valueOf(true);
/* 880 */       this.title_Operate10 = Boolean.valueOf(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setServletContext(ServletContext servletContext)
/*     */   {
/* 918 */     this.servletContext = servletContext;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ProductAct
 * JD-Core Version:    0.6.0
 */