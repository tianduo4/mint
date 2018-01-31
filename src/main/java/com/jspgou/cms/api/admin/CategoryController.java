/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.Brand;
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.StandardType;
/*     */ import com.jspgou.cms.manager.BrandMng;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.cms.manager.ProductTypeMng;
/*     */ import com.jspgou.cms.manager.StandardTypeMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.context.ServletContextAware;
/*     */ 
/*     */ @Controller
/*     */ public class CategoryController
/*     */   implements ServletContextAware
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private CategoryMng categoryMng;
/*     */   private ServletContext servletContext;
/*     */ 
/*     */   @Autowired
/*     */   private ProductTypeMng productTypeMng;
/*     */ 
/*     */   @Autowired
/*     */   private BrandMng brandMng;
/*     */ 
/*     */   @Autowired
/*     */   private StandardTypeMng standardTypeMng;
/*     */ 
/*     */   @RequestMapping({"/category/list"})
/*     */   public void category(Integer pid, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  50 */     String body = "\"\"";
/*  51 */     String message = "\"\"";
/*  52 */     int code = 200;
/*     */     try
/*     */     {
/*  55 */       Website web = SiteUtils.getWeb(request);
/*     */       List list;
/*  56 */       if ((pid == null) || (pid.intValue() == 0))
/*  57 */         list = this.categoryMng.getTopList(web.getId());
/*     */       else {
/*  59 */         list = this.categoryMng.getChildList(web.getId(), pid);
/*     */       }
/*  61 */       JSONArray jsonArray = new JSONArray();
/*  62 */       if ((list != null) && (list.size() > 0)) {
/*  63 */         for (int i = 0; i < list.size(); i++) {
/*  64 */           jsonArray.put(i, ((Category)list.get(i)).convertToJson1());
/*     */         }
/*     */       }
/*  67 */       message = "\"success\"";
/*  68 */       body = jsonArray.toString();
/*     */     } catch (Exception e) {
/*  70 */       e.printStackTrace();
/*  71 */       code = 100;
/*  72 */       message = "\"system exception\"";
/*     */     }
/*  74 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  75 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/category/tree"})
/*     */   public void tree(Integer pid, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  88 */     String body = "\"\"";
/*  89 */     String message = "\"\"";
/*  90 */     int code = 200;
/*     */     try {
/*  92 */       Website web = SiteUtils.getWeb(request);
/*  93 */       WebErrors errors = WebErrors.create(request);
/*  94 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pid });
/*  95 */       if (!errors.hasErrors())
/*     */       {
/*     */         boolean isRoot;
/*  98 */         if (pid.intValue() == 0)
/*  99 */           isRoot = true;
/*     */         else
/* 101 */           isRoot = false;
/*     */         List list;
/* 104 */         if (isRoot)
/* 105 */           list = this.categoryMng.getTopList(web.getId());
/*     */         else {
/* 107 */           list = this.categoryMng.getChildList(web.getId(), pid);
/*     */         }
/* 109 */         JSONArray jsonArray = new JSONArray();
/* 110 */         if ((list != null) && (list.size() > 0)) {
/* 111 */           for (int i = 0; i < list.size(); i++) {
/* 112 */             jsonArray.put(((Category)list.get(i)).convertToJson2(isRoot));
/*     */           }
/*     */         }
/* 115 */         body = jsonArray.toString();
/*     */       } else {
/* 117 */         code = 202;
/* 118 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 121 */       e.printStackTrace();
/* 122 */       code = 100;
/* 123 */       message = "\"system exception\"";
/*     */     }
/* 125 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 126 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/category/template"})
/*     */   public void template(Long typeId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 138 */     String body = "\"\"";
/* 139 */     String message = "\"success\"";
/* 140 */     int code = 200;
/*     */     try {
/* 142 */       Website web = SiteUtils.getWeb(request);
/* 143 */       WebErrors errors = WebErrors.create(request);
/* 144 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { typeId });
/* 145 */       if (!errors.hasErrors()) {
/* 146 */         if (typeId != null) {
/* 147 */           ProductType type = this.productTypeMng.findById(typeId);
/*     */ 
/* 149 */           String ctgTplDirRel = type.getCtgTplDirRel();
/* 150 */           String realDir = this.servletContext.getRealPath(ctgTplDirRel);
/* 151 */           String relPath = ctgTplDirRel.substring(web.getTemplateRel(
/* 152 */             request).length());
/*     */ 
/* 154 */           String txtTplDirRel = type.getTxtTplDirRel();
/* 155 */           String txtrealDir = this.servletContext
/* 156 */             .getRealPath(txtTplDirRel);
/* 157 */           String txtrelPath = txtTplDirRel.substring(web
/* 158 */             .getTemplateRel(request).length());
/*     */ 
/* 160 */           String[] channelTpls = type
/* 161 */             .getChannelTpls(realDir, relPath);
/*     */ 
/* 163 */           String[] contentTpls = type.getContentTpls(txtrealDir, 
/* 164 */             txtrelPath);
/* 165 */           JSONArray jsonArray1 = new JSONArray();
/* 166 */           JSONArray jsonArray2 = new JSONArray();
/* 167 */           if ((channelTpls != null) && (channelTpls.length > 0)) {
/* 168 */             for (int i = 0; i < channelTpls.length; i++) {
/* 169 */               jsonArray1.put(i, channelTpls[i]);
/*     */             }
/*     */           }
/* 172 */           if ((contentTpls != null) && (contentTpls.length > 0)) {
/* 173 */             for (int i = 0; i < contentTpls.length; i++) {
/* 174 */               jsonArray2.put(i, contentTpls[i]);
/*     */             }
/*     */           }
/* 177 */           JSONObject json = new JSONObject();
/* 178 */           json.put("channelTpls", jsonArray1);
/* 179 */           json.put("contentTpls", jsonArray2);
/* 180 */           body = json.toString();
/*     */         } else {
/* 182 */           code = 202;
/* 183 */           message = "\"param error\"";
/*     */         }
/*     */       }
/*     */       else
/* 187 */         message = (String)errors.getErrors().get(0);
/*     */     }
/*     */     catch (Exception e) {
/* 190 */       e.printStackTrace();
/* 191 */       code = 100;
/* 192 */       message = "\"system exception\"";
/*     */     }
/* 194 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 195 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/category/brand"})
/*     */   public void nature(Integer categoryId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 207 */     String body = "\"\"";
/* 208 */     String message = "\"success\"";
/* 209 */     int code = 200;
/*     */     try {
/* 211 */       JSONArray jsonArray = new JSONArray();
/* 212 */       JSONObject json = new JSONObject();
/* 213 */       if (categoryId != null) {
/* 214 */         Category category = this.categoryMng.findById(categoryId);
/* 215 */         json.put("brandIds", category.getBrandIds());
/* 216 */         body = json.toString();
/*     */       } else {
/* 218 */         List brandList = this.brandMng.getAllList();
/* 219 */         if ((brandList != null) && (brandList.size() > 0)) {
/* 220 */           for (int i = 0; i < brandList.size(); i++) {
/* 221 */             jsonArray.put(i, ((Brand)brandList.get(i)).convertToJson1());
/*     */           }
/*     */         }
/* 224 */         body = jsonArray.toString();
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 228 */       e.printStackTrace();
/* 229 */       code = 100;
/* 230 */       message = "\"system exception\"";
/*     */     }
/* 232 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 233 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/category/standardtype"})
/*     */   public void standardtype(Integer categoryId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 245 */     String body = "\"\"";
/* 246 */     String message = "\"success\"";
/* 247 */     int code = 200;
/*     */     try {
/* 249 */       JSONArray jsonArray = new JSONArray();
/* 250 */       if (categoryId != null) {
/* 251 */         List standardTypeList = this.standardTypeMng
/* 252 */           .getList(categoryId);
/* 253 */         if ((standardTypeList != null) && (standardTypeList.size() > 0))
/* 254 */           for (int i = 0; i < standardTypeList.size(); i++)
/* 255 */             jsonArray.put(i, ((StandardType)standardTypeList.get(i))
/* 256 */               .convertToJson1());
/*     */       }
/*     */       else
/*     */       {
/* 260 */         List standardTypeList = this.standardTypeMng.getList();
/* 261 */         if ((standardTypeList != null) && (standardTypeList.size() > 0)) {
/* 262 */           for (int i = 0; i < standardTypeList.size(); i++) {
/* 263 */             jsonArray.put(i, ((StandardType)standardTypeList.get(i))
/* 264 */               .convertToJson1());
/*     */           }
/*     */         }
/*     */       }
/* 268 */       body = jsonArray.toString();
/*     */     } catch (Exception e) {
/* 270 */       e.printStackTrace();
/* 271 */       code = 100;
/* 272 */       message = "\"system exception\"";
/*     */     }
/* 274 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 275 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/category/getType"})
/*     */   public void add(Long typeId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 285 */     String body = "\"\"";
/* 286 */     String message = "\"success\"";
/* 287 */     int code = 200;
/*     */     try {
/* 289 */       WebErrors errors = WebErrors.create(request);
/* 290 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { typeId });
/* 291 */       if (!errors.hasErrors())
/*     */       {
/* 293 */         ProductType type = this.productTypeMng.findById(typeId);
/* 294 */         JSONObject json = new JSONObject();
/* 295 */         json.put("typeName", type.getName());
/* 296 */         body = json.toString();
/*     */       } else {
/* 298 */         code = 202;
/* 299 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 302 */       e.printStackTrace();
/* 303 */       code = 100;
/* 304 */       message = "\"system exception\"";
/*     */     }
/* 306 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 307 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/category/save"})
/*     */   public void save(Category bean, Integer parentId, Long typeId, String brandIds, String standardTypeIds, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 324 */     String body = "\"\"";
/* 325 */     String message = "\"success\"";
/* 326 */     int code = 200;
/*     */     try {
/* 328 */       WebErrors errors = WebErrors.create(request);
/* 329 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getName(), 
/* 330 */         bean.getPath(), parentId, typeId });
/* 331 */       if (!errors.hasErrors())
/*     */       {
/* 333 */         if (this.categoryMng.getByPath(CmsThreadVariable.getSite().getId(), bean.getPath()) == null) {
/* 334 */           Long[] brandId = null;
/* 335 */           Long[] standardTypeId = null;
/* 336 */           if (StringUtils.isNotBlank(brandIds)) {
/* 337 */             String[] str = brandIds.split(",");
/* 338 */             brandId = new Long[str.length];
/* 339 */             for (int i = 0; i < str.length; i++) {
/* 340 */               brandId[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */             }
/*     */           }
/* 343 */           if (StringUtils.isNotBlank(standardTypeIds)) {
/* 344 */             String[] str = standardTypeIds.split(",");
/* 345 */             standardTypeId = new Long[str.length];
/* 346 */             for (int i = 0; i < str.length; i++) {
/* 347 */               standardTypeId[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */             }
/*     */           }
/* 350 */           bean.setWebsite(CmsThreadVariable.getSite());
/* 351 */           bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
/* 352 */           bean = this.categoryMng.save(bean, parentId, typeId, brandId, 
/* 353 */             standardTypeId);
/*     */         } else {
/* 355 */           code = 502;
/* 356 */           message = "\"path not repeate\"";
/*     */         }
/*     */       } else {
/* 359 */         code = 202;
/* 360 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 363 */       e.printStackTrace();
/* 364 */       code = 100;
/* 365 */       message = "\"system exception\"";
/*     */     }
/* 367 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 368 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/category/getCategory"})
/*     */   public void getCategory(Integer id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 383 */     String body = "\"\"";
/* 384 */     String message = "\"success\"";
/* 385 */     int code = 200;
/*     */     try {
/* 387 */       WebErrors errors = WebErrors.create(request);
/* 388 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/* 389 */       if (!errors.hasErrors()) {
/* 390 */         Category category = new Category();
/* 391 */         if (id.intValue() != 0) {
/* 392 */           category = this.categoryMng.findById(id);
/*     */         }
/* 394 */         if (category != null) {
/* 395 */           JSONObject json = category.convertToJson3();
/* 396 */           JSONArray jsonArray = new JSONArray();
/* 397 */           List standardTypeList = this.standardTypeMng
/* 398 */             .getList(id);
/* 399 */           if ((standardTypeList != null) && (standardTypeList.size() > 0)) {
/* 400 */             for (int i = 0; i < standardTypeList.size(); i++) {
/* 401 */               jsonArray.put(CommonUtils.parseId(((StandardType)standardTypeList.get(i)).getId()));
/*     */             }
/*     */           }
/* 404 */           json.put("brandIds", category.getBrandIds());
/* 405 */           json.put("standardTypeIds", jsonArray);
/* 406 */           body = json.toString();
/*     */         } else {
/* 408 */           code = 206;
/* 409 */           message = "\"object not found\"";
/*     */         }
/*     */       } else {
/* 412 */         code = 202;
/* 413 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 416 */       e.printStackTrace();
/* 417 */       code = 100;
/* 418 */       message = "\"system exception\"";
/*     */     }
/* 420 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 421 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/category/update"})
/*     */   public void update(Category bean, Integer parentId, Long typeId, String brandIds, String standardTypeIds, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 433 */     String body = "\"\"";
/* 434 */     String message = "\"success\"";
/* 435 */     int code = 200;
/*     */     try {
/* 437 */       WebErrors errors = WebErrors.create(request);
/* 438 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), 
/* 439 */         bean.getName(), bean.getPath() });
/* 440 */       if (!errors.hasErrors())
/*     */       {
/* 442 */         if (this.categoryMng.getByPath(CmsThreadVariable.getSite().getId(), bean.getPath()) == null) {
/* 443 */           Long[] brandId = null;
/* 444 */           Long[] standardTypeId = null;
/* 445 */           if (StringUtils.isNotBlank(brandIds)) {
/* 446 */             String[] str = brandIds.split(",");
/* 447 */             brandId = new Long[str.length];
/* 448 */             for (int i = 0; i < str.length; i++) {
/* 449 */               brandId[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */             }
/*     */           }
/* 452 */           if (StringUtils.isNotBlank(standardTypeIds)) {
/* 453 */             String[] str = standardTypeIds.split(",");
/* 454 */             standardTypeId = new Long[str.length];
/* 455 */             for (int i = 0; i < str.length; i++) {
/* 456 */               standardTypeId[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */             }
/*     */           }
/* 459 */           Map attr = RequestUtils.getRequestMap(request, 
/* 460 */             "attr_");
/* 461 */           bean = this.categoryMng.update(bean, parentId, null, brandId, attr, 
/* 462 */             standardTypeId);
/*     */         } else {
/* 464 */           code = 502;
/* 465 */           message = "\"path not repeate\"";
/*     */         }
/*     */       } else {
/* 468 */         code = 202;
/* 469 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 472 */       e.printStackTrace();
/* 473 */       code = 100;
/* 474 */       message = "\"system exception\"";
/*     */     }
/* 476 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 477 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/category/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 490 */     String body = "\"\"";
/* 491 */     String message = "\"success\"";
/* 492 */     int code = 200;
/*     */     try {
/* 494 */       WebErrors errors = WebErrors.create(request);
/* 495 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 496 */       if (!errors.hasErrors()) {
/* 497 */         Integer[] id = null;
/* 498 */         if (StringUtils.isNotBlank(ids)) {
/* 499 */           String[] str = ids.split(",");
/* 500 */           id = new Integer[str.length];
/* 501 */           for (int i = 0; i < id.length; i++) {
/* 502 */             id[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */           }
/*     */         }
/* 505 */         this.categoryMng.deleteByIds(id);
/*     */       } else {
/* 507 */         code = 202;
/* 508 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 511 */       ExceptionUtil.convertException(response, request, e);
/* 512 */       return;
/*     */     }
/* 514 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 515 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/category/priority"})
/*     */   public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 527 */     String body = "\"\"";
/* 528 */     String message = "\"success\"";
/* 529 */     int code = 200;
/*     */     try {
/* 531 */       WebErrors errors = WebErrors.create(request);
/* 532 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids, priority });
/* 533 */       if (!errors.hasErrors()) {
/* 534 */         Integer[] wid = null;
/* 535 */         Integer[] prioritys = null;
/* 536 */         if (StringUtils.isNotBlank(ids)) {
/* 537 */           String[] str = ids.split(",");
/* 538 */           wid = new Integer[str.length];
/* 539 */           for (int i = 0; i < str.length; i++) {
/* 540 */             wid[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */           }
/*     */         }
/* 543 */         if (StringUtils.isNotBlank(priority)) {
/* 544 */           String[] str = priority.split(",");
/* 545 */           prioritys = new Integer[str.length];
/* 546 */           for (int i = 0; i < str.length; i++) {
/* 547 */             prioritys[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */           }
/*     */         }
/* 550 */         this.categoryMng.updatePriority(wid, prioritys);
/*     */       } else {
/* 552 */         code = 202;
/* 553 */         message = "\"param error\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 557 */       e.printStackTrace();
/* 558 */       code = 100;
/* 559 */       message = "\"system exception\"";
/*     */     }
/* 561 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 562 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/category/topCategory"})
/*     */   public void topCategory(Integer id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 573 */     String body = "\"\"";
/* 574 */     String message = "\"success\"";
/* 575 */     int code = 200;
/*     */     try {
/* 577 */       Long site = SiteUtils.getWebId(request);
/* 578 */       List list = this.categoryMng.getListForParent(site, id);
/* 579 */       JSONArray jsonArray = new JSONArray();
/* 580 */       if ((list != null) && (list.size() > 0)) {
/* 581 */         for (int i = 0; i < list.size(); i++) {
/* 582 */           jsonArray.put(i, ((Category)list.get(i)).convertToJson());
/*     */         }
/*     */       }
/* 585 */       body = jsonArray.toString();
/*     */     } catch (Exception e) {
/* 587 */       e.printStackTrace();
/* 588 */       code = 100;
/* 589 */       message = "\"system exception\"";
/*     */     }
/* 591 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 592 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   public void setServletContext(ServletContext servletContext)
/*     */   {
/* 608 */     this.servletContext = servletContext;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.CategoryController
 * JD-Core Version:    0.6.0
 */