/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductExt;
/*     */ import com.jspgou.cms.entity.ProductStandard;
/*     */ import com.jspgou.cms.entity.Standard;
/*     */ import com.jspgou.cms.lucene.LuceneProductSvc;
/*     */ import com.jspgou.cms.manager.CartItemMng;
/*     */ import com.jspgou.cms.manager.ProductFashionMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ProductStandardMng;
/*     */ import com.jspgou.cms.manager.StandardMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.lucene.queryParser.ParseException;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ProductController
/*     */ {
/*  49 */   private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductStandardMng productStandardMng;
/*     */ 
/*     */   @Autowired
/*     */   private StandardMng standardMng;
/*     */ 
/*     */   @Autowired
/*     */   private LuceneProductSvc luceneProductSvc;
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionMng productFashionMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionMng fashMng;
/*     */ 
/*     */   @Autowired
/*     */   private CartItemMng cartItemMng;
/*     */ 
/*     */   @Autowired
/*     */   private ServletContext servletContext;
/*     */ 
/*  88 */   @RequestMapping({"/product/list"})
/*     */   public void list(Integer ctgId, String productName, String brandName, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) { String body = "\"\"";
/*  89 */     String message = "\"success\"";
/*  90 */     int code = 200;
/*     */     try {
/*  92 */       if (pageNo == null) {
/*  93 */         pageNo = Integer.valueOf(1);
/*     */       }
/*  95 */       if (pageSize == null) {
/*  96 */         pageSize = Integer.valueOf(10);
/*     */       }
/*  98 */       Pagination page = this.productMng.getPage(
/*  99 */         SiteUtils.getWebId(request), ctgId, 
/* 100 */         productName, brandName, status, isRecommend, isSpecial, 
/* 101 */         isHotsale, isNewProduct, typeId, startSalePrice, 
/* 102 */         endSalePrice, startStock, endStock, pageNo.intValue(), pageSize.intValue());
/* 103 */       List list = page.getList();
/* 104 */       int totalCount = page.getTotalCount();
/* 105 */       JSONArray jsonArray = new JSONArray();
/* 106 */       if ((list != null) && (list.size() > 0)) {
/* 107 */         for (int i = 0; i < list.size(); i++) {
/* 108 */           jsonArray.put(i, ((Product)list.get(i)).convertToJson("list"));
/*     */         }
/*     */       }
/* 111 */       body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
/*     */     } catch (Exception e) {
/* 113 */       e.printStackTrace();
/* 114 */       code = 100;
/* 115 */       message = "\"system exception\"";
/*     */     }
/* 117 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 118 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/product/getProduct"})
/*     */   public void getProduct(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 131 */     String body = "\"\"";
/* 132 */     String message = "\"success\"";
/* 133 */     int code = 200;
/*     */     try {
/* 135 */       WebErrors errors = WebErrors.create(request);
/* 136 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/* 137 */       if (!errors.hasErrors()) {
/* 138 */         Product product = new Product();
/* 139 */         if (id.longValue() != 0L) {
/* 140 */           product = this.productMng.findById(id);
/*     */         }
/* 142 */         if (product != null) {
/* 143 */           JSONObject json = product.convertToJson("get");
/* 144 */           JSONArray jsonArray = new JSONArray();
/* 145 */           JSONArray imgIds = new JSONArray();
/* 146 */           List psList = this.productStandardMng
/* 147 */             .findByProductId(id);
/* 148 */           if ((psList != null) && (psList.size() > 0)) {
/* 149 */             for (int i = 0; i < psList.size(); i++) {
/* 150 */               if (StringUtils.isNotEmpty(((ProductStandard)psList.get(i)).getImgPath())) {
/* 151 */                 jsonArray.put(((ProductStandard)psList.get(i)).getImgPath());
/* 152 */                 imgIds.put(((ProductStandard)psList.get(i)).getStandard().getId());
/*     */               }
/*     */             }
/*     */           }
/* 156 */           json.put("productStandardImg", jsonArray);
/* 157 */           json.put("productStandardImgId", imgIds);
/* 158 */           body = json.toString();
/*     */         } else {
/* 160 */           code = 206;
/* 161 */           message = "\"object not found\"";
/*     */         }
/*     */       } else {
/* 164 */         code = 202;
/* 165 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 168 */       e.printStackTrace();
/* 169 */       code = 100;
/* 170 */       message = "\"system exception\"";
/*     */     }
/* 172 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 173 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/product/save"})
/*     */   public void save(Product bean, ProductExt ext, Integer categoryId, Long brandId, String nature, String picture, String colorImg, String character, String isDefaults, String stockCounts, String fashionSwitchPic, String salePrices, String marketPrices, String costPrices, String productKeywords, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 247 */     String body = "\"\"";
/* 248 */     String message = "\"success\"";
/* 249 */     int code = 200;
/*     */     try {
/* 251 */       WebErrors errors = WebErrors.create(request);
/* 252 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getName(), categoryId });
/* 253 */       if (!errors.hasErrors()) {
/* 254 */         String[] natures = null;
/* 255 */         Long[] pictures = null;
/* 256 */         String[] colorImgs = null;
/* 257 */         Long[] characters = null;
/* 258 */         Double[] salePrice = null;
/* 259 */         Double[] marketPrice = null;
/* 260 */         Double[] costPrice = null;
/* 261 */         Boolean[] isDefault = null;
/* 262 */         Integer[] stockCount = null;
/* 263 */         String[] fashionSwitchPics = null;
/*     */ 
/* 269 */         if (StringUtils.isNotBlank(nature)) {
/* 270 */           String[] str = nature.split(",");
/* 271 */           natures = new String[str.length];
/* 272 */           for (int i = 0; i < str.length; i++) {
/* 273 */             natures[i] = str[i];
/*     */           }
/*     */         }
/*     */ 
/* 277 */         if (StringUtils.isNotBlank(stockCounts)) {
/* 278 */           String[] str = stockCounts.split(",");
/* 279 */           stockCount = new Integer[str.length];
/* 280 */           for (int i = 0; i < str.length; i++) {
/* 281 */             stockCount[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 286 */         if (StringUtils.isNotBlank(salePrices)) {
/* 287 */           String[] str = salePrices.split(",");
/* 288 */           salePrice = new Double[str.length];
/* 289 */           for (int i = 0; i < str.length; i++) {
/* 290 */             salePrice[i] = Double.valueOf(Double.parseDouble(str[i]));
/*     */           }
/*     */         }
/*     */ 
/* 294 */         if (StringUtils.isNotBlank(marketPrices)) {
/* 295 */           String[] str = marketPrices.split(",");
/* 296 */           marketPrice = new Double[str.length];
/* 297 */           for (int i = 0; i < str.length; i++) {
/* 298 */             marketPrice[i] = Double.valueOf(Double.parseDouble(str[i]));
/*     */           }
/*     */         }
/*     */ 
/* 302 */         if (StringUtils.isNotBlank(costPrices)) {
/* 303 */           String[] str = costPrices.split(",");
/* 304 */           costPrice = new Double[str.length];
/* 305 */           for (int i = 0; i < str.length; i++) {
/* 306 */             costPrice[i] = Double.valueOf(Double.parseDouble(str[i]));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 312 */         if (StringUtils.isNotBlank(picture)) {
/* 313 */           String[] str = picture.split(",");
/* 314 */           pictures = new Long[str.length];
/* 315 */           for (int i = 0; i < str.length; i++) {
/* 316 */             pictures[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */           }
/*     */         }
/*     */ 
/* 320 */         if (StringUtils.isNotBlank(colorImg)) {
/* 321 */           String[] str = colorImg.split(",");
/* 322 */           colorImgs = new String[str.length];
/* 323 */           for (int i = 0; i < str.length; i++) {
/* 324 */             colorImgs[i] = str[i];
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 329 */         if (StringUtils.isNotBlank(character)) {
/* 330 */           String[] str = character.split(",");
/* 331 */           characters = new Long[str.length];
/* 332 */           for (int i = 0; i < str.length; i++) {
/* 333 */             characters[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */           }
/*     */         }
/*     */ 
/* 337 */         if (StringUtils.isNotBlank(isDefaults)) {
/* 338 */           String[] str = isDefaults.split(",");
/* 339 */           isDefault = new Boolean[str.length];
/* 340 */           for (int i = 0; i < str.length; i++) {
/* 341 */             isDefault[i] = Boolean.valueOf(str[i]);
/*     */           }
/*     */         }
/*     */ 
/* 345 */         if (StringUtils.isNotBlank(fashionSwitchPic)) {
/* 346 */           String[] str = fashionSwitchPic.split(",");
/* 347 */           fashionSwitchPics = new String[str.length];
/* 348 */           for (int i = 0; i < str.length; i++) {
/* 349 */             fashionSwitchPics[i] = str[i];
/*     */           }
/*     */         }
/* 352 */         String[] keywords = StringUtils.split(productKeywords, ",");
/*     */ 
/* 355 */         Map exended = RequestUtils.getRequestMap(
/* 356 */           request, "exended_");
/* 357 */         List li = new ArrayList(exended.keySet());
/* 358 */         String[] names = new String[li.size()];
/* 359 */         String[] values = new String[li.size()];
/* 360 */         if (stockCount != null) {
/* 361 */           Integer stockCount1 = Integer.valueOf(0);
/* 362 */           for (Integer s : stockCount) {
/* 363 */             stockCount1 = Integer.valueOf(stockCount1.intValue() + s.intValue());
/*     */           }
/* 365 */           bean.setStockCount(stockCount1);
/*     */         }
/* 367 */         for (int i = 0; i < li.size(); i++) {
/* 368 */           names[i] = ((String)li.get(i));
/* 369 */           values[i] = ((String)exended.get(li.get(i)));
/*     */         }
/* 371 */         bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
/* 372 */         this.productMng.saveByApi(bean, ext, CmsThreadVariable.getSite().getId(), categoryId, brandId, keywords, names, values, 
/* 373 */           fashionSwitchPics, pictures, colorImgs, characters, natures, isDefault, stockCount, salePrice, marketPrice, costPrice);
/*     */         try
/*     */         {
///* 377 */           if (bean.getStatus().intValue() != Product.ON_SALE_STATUS)  break;  //TODO
                        this.luceneProductSvc.createIndex(bean);
/*     */         } catch (IOException e) {
/* 381 */           e.printStackTrace();
/*     */         }
/*     */       } else {
/* 384 */         code = 202;
/* 385 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (ClassCastException e) {
/* 388 */       code = 202;
/* 389 */       message = "\"param error\"";
/*     */     } catch (Exception e) {
/* 391 */       e.printStackTrace();
/* 392 */       code = 100;
/* 393 */       message = "\"system exception\"";
/*     */     }
/* 395 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 396 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/product/update"})
/*     */   public void update(Long id, Product bean, ProductExt ext, Integer categoryId, Long brandId, String nature, String picture, String colorImg, String character, String isDefaults, String stockCounts, String fashionSwitchPic, String fashionBigPic, String fashionAmpPic, String salePrices, String marketPrices, String costPrices, String fashionIds, String productKeywords, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 413 */     String body = "\"\"";
/* 414 */     String message = "\"success\"";
/* 415 */     int code = 200;
/*     */     try
/*     */     {
/* 418 */       WebErrors errors = WebErrors.create(request);
/* 419 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id, bean.getName(), categoryId });
/* 420 */       if (!errors.hasErrors()) {
/* 421 */         String[] natures = null;
/* 422 */         Long[] pictures = null;
/* 423 */         String[] colorImgs = null;
/* 424 */         Long[] characters = null;
/* 425 */         Double[] salePrice = null;
/* 426 */         Double[] marketPrice = null;
/* 427 */         Double[] costPrice = null;
/* 428 */         Boolean[] isDefault = null;
/* 429 */         Integer[] stockCount = null;
/* 430 */         String[] fashionSwitchPics = null;
/* 431 */         String[] fashionBigPics = null;
/* 432 */         String[] fashionAmpPics = null;
/* 433 */         Long[] fashionId = null;
/*     */ 
/* 437 */         if (StringUtils.isNotBlank(nature)) {
/* 438 */           String[] str = nature.split(",");
/* 439 */           natures = new String[str.length];
/* 440 */           for (int i = 0; i < str.length; i++) {
/* 441 */             natures[i] = str[i];
/*     */           }
/*     */         }
/*     */ 
/* 445 */         if (StringUtils.isNotBlank(stockCounts)) {
/* 446 */           String[] str = stockCounts.split(",");
/* 447 */           stockCount = new Integer[str.length];
/* 448 */           for (int i = 0; i < str.length; i++) {
/* 449 */             stockCount[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 454 */         if (StringUtils.isNotBlank(salePrices)) {
/* 455 */           String[] str = salePrices.split(",");
/* 456 */           salePrice = new Double[str.length];
/* 457 */           for (int i = 0; i < str.length; i++) {
/* 458 */             salePrice[i] = Double.valueOf(Double.parseDouble(str[i]));
/*     */           }
/*     */         }
/*     */ 
/* 462 */         if (StringUtils.isNotBlank(marketPrices)) {
/* 463 */           String[] str = marketPrices.split(",");
/* 464 */           marketPrice = new Double[str.length];
/* 465 */           for (int i = 0; i < str.length; i++) {
/* 466 */             marketPrice[i] = Double.valueOf(Double.parseDouble(str[i]));
/*     */           }
/*     */         }
/*     */ 
/* 470 */         if (StringUtils.isNotBlank(costPrices)) {
/* 471 */           String[] str = costPrices.split(",");
/* 472 */           costPrice = new Double[str.length];
/* 473 */           for (int i = 0; i < str.length; i++) {
/* 474 */             costPrice[i] = Double.valueOf(Double.parseDouble(str[i]));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 480 */         if (StringUtils.isNotBlank(picture)) {
/* 481 */           String[] str = picture.split(",");
/* 482 */           pictures = new Long[str.length];
/* 483 */           for (int i = 0; i < str.length; i++) {
/* 484 */             pictures[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */           }
/*     */         }
/*     */ 
/* 488 */         if (StringUtils.isNotBlank(colorImg)) {
/* 489 */           String[] str = colorImg.split(",");
/* 490 */           colorImgs = new String[str.length];
/* 491 */           for (int i = 0; i < str.length; i++) {
/* 492 */             colorImgs[i] = str[i];
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 497 */         if (StringUtils.isNotBlank(character)) {
/* 498 */           String[] str = character.split(",");
/* 499 */           characters = new Long[str.length];
/* 500 */           for (int i = 0; i < str.length; i++) {
/* 501 */             characters[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */           }
/*     */         }
/*     */ 
/* 505 */         if (StringUtils.isNotBlank(isDefaults)) {
/* 506 */           String[] str = isDefaults.split(",");
/* 507 */           isDefault = new Boolean[str.length];
/* 508 */           for (int i = 0; i < str.length; i++) {
/* 509 */             isDefault[i] = Boolean.valueOf(str[i]);
/*     */           }
/*     */         }
/*     */ 
/* 513 */         if (StringUtils.isNotBlank(fashionSwitchPic)) {
/* 514 */           String[] str = fashionSwitchPic.split(",");
/* 515 */           fashionSwitchPics = new String[str.length];
/* 516 */           for (int i = 0; i < str.length; i++) {
/* 517 */             fashionSwitchPics[i] = str[i];
/*     */           }
/*     */         }
/*     */ 
/* 521 */         if (StringUtils.isNotBlank(fashionBigPic)) {
/* 522 */           String[] str = fashionBigPic.split(",");
/* 523 */           fashionBigPics = new String[str.length];
/* 524 */           for (int i = 0; i < str.length; i++) {
/* 525 */             fashionBigPics[i] = str[i];
/*     */           }
/*     */         }
/*     */ 
/* 529 */         if (StringUtils.isNotBlank(fashionAmpPic)) {
/* 530 */           String[] str = fashionAmpPic.split(",");
/* 531 */           fashionAmpPics = new String[str.length];
/* 532 */           for (int i = 0; i < str.length; i++) {
/* 533 */             fashionAmpPics[i] = str[i];
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 538 */         if (StringUtils.isNotBlank(fashionIds)) {
/* 539 */           String[] str = fashionIds.split(",");
/* 540 */           fashionId = new Long[str.length];
/* 541 */           for (int i = 0; i < str.length; i++) {
/* 542 */             if ((StringUtils.isEmpty(str[i])) || ("0".equals(str[i])))
/* 543 */               fashionId[i] = Long.valueOf(0L);
/*     */             else {
/* 545 */               fashionId[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 550 */         Map exended = RequestUtils.getRequestMap(
/* 551 */           request, "exended_");
/* 552 */         List li = new ArrayList(exended.keySet());
/* 553 */         String[] names = new String[li.size()];
/* 554 */         String[] values = new String[li.size()];
/* 555 */         for (int i = 0; i < li.size(); i++) {
/* 556 */           names[i] = ((String)li.get(i));
/* 557 */           values[i] = ((String)exended.get(li.get(i)));
/*     */         }
/* 559 */         Map attr = RequestUtils.getRequestMap(request, 
/* 560 */           "attr_");
/* 561 */         if (stockCount != null) {
/* 562 */           Integer stockCount1 = Integer.valueOf(0);
/* 563 */           for (Integer s : stockCount) {
/* 564 */             stockCount1 = Integer.valueOf(stockCount1.intValue() + s.intValue());
/*     */           }
/* 566 */           bean.setStockCount(stockCount1);
/*     */         }
/*     */ 
/* 569 */         String[] keywords = StringUtils.split(productKeywords, ",");
/*     */ 
/* 571 */         bean = this.productMng.updateByApi(bean, ext, brandId, keywords, names, values, attr, 
/* 572 */           fashionSwitchPics, fashionBigPics, fashionAmpPics, categoryId, fashionSwitchPics, pictures, colorImgs, 
/* 573 */           characters, fashionId, natures, isDefault, stockCount, salePrice, marketPrice, costPrice);
/*     */         try
/*     */         {
/* 577 */           if (bean.getStatus().intValue() == Product.ON_SALE_STATUS)
/* 578 */             this.luceneProductSvc.updateIndex(bean);
/* 579 */           else if (bean.getStatus().intValue() == Product.NOT_SALE_STATUS)
/* 580 */             this.luceneProductSvc.deleteIndex(bean);
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 584 */           e.printStackTrace();
/*     */         }
/*     */         catch (ParseException e) {
/* 587 */           e.printStackTrace();
/*     */         }
/* 589 */         LOG.info("update Product. id={}.", bean.getId());
/*     */       }
/*     */       else {
/* 592 */         code = 202;
/* 593 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (ClassCastException e) {
/* 596 */       code = 202;
/* 597 */       message = "\"param error\"";
/*     */     } catch (Exception e) {
/* 599 */       ExceptionUtil.convertException(response, request, e);
/* 600 */       return;
/*     */     }
/* 602 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 603 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/product/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 617 */     String body = "\"\"";
/* 618 */     String message = "\"success\"";
/* 619 */     int code = 200;
/*     */     try {
/* 621 */       Long[] id = null;
/* 622 */       if (StringUtils.isNotBlank(ids)) {
/* 623 */         String[] str = ids.split(",");
/* 624 */         id = new Long[str.length];
/* 625 */         for (int i = 0; i < str.length; i++) {
/* 626 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 628 */         Product[] beans = this.productMng.deleteByUpIds(id);
/* 629 */         for (Product bean : beans)
/*     */         {
/* 631 */           this.luceneProductSvc.deleteIndex(bean);
/*     */         }
/*     */       } else {
/* 634 */         code = 202;
/* 635 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 638 */       ExceptionUtil.convertException(response, request, e);
/* 639 */       return;
/*     */     }
/* 641 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 642 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   public Long[] getProductIds(String rightlist)
/*     */   {
/* 649 */     String[] arr = rightlist.split(",");
/* 650 */     Long[] productIds = new Long[arr.length];
/* 651 */     int i = 0; for (int j = arr.length; i < j; i++) {
/* 652 */       if (!arr[i].equals("")) {
/* 653 */         productIds[i] = Long.valueOf(Long.parseLong(arr[i]));
/*     */       }
/*     */     }
/* 656 */     return productIds;
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/product/standup"})
/*     */   public void standup(String ids, Integer status, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 667 */     String body = "\"\"";
/* 668 */     String message = "\"success\"";
/* 669 */     int code = 200;
/*     */     try {
/* 671 */       WebErrors errors = WebErrors.create(request);
/* 672 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 673 */       if (!errors.hasErrors()) {
/* 674 */         Long[] id = null;
/* 675 */         String[] str = ids.split(",");
/* 676 */         id = new Long[str.length];
/* 677 */         for (int i = 0; i < str.length; i++) {
/* 678 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 680 */         for (Long i : id) {
/* 681 */           Product bean = this.productMng.findById(i);
/* 682 */           bean.setStatus(status);
/* 683 */           bean = this.productMng.update(bean);
/*     */ 
/* 685 */           if (bean.getStatus().intValue() == Product.ON_SALE_STATUS)
/* 686 */             this.luceneProductSvc.updateIndex(bean);
/* 687 */           else if (bean.getStatus().intValue() == Product.NOT_SALE_STATUS)
/* 688 */             this.luceneProductSvc.deleteIndex(bean);
/*     */         }
/*     */       }
/*     */       else {
/* 692 */         code = 202;
/* 693 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 696 */       e.printStackTrace();
/* 697 */       code = 100;
/* 698 */       message = "\"system exception\"";
/*     */     }
/* 700 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 701 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/product/getOverStock"})
/*     */   public void createIndex(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 715 */     String body = "\"\"";
/* 716 */     String message = "\"success\"";
/* 717 */     int code = 200;
/*     */     try {
/* 719 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/* 721 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/* 722 */       if (errors.hasErrors()) {
/* 723 */         code = 202;
/* 724 */         message = "\"param error\"";
/*     */       } else {
/* 726 */         Pagination page = this.productMng.getPageOverStockList(CmsThreadVariable.getSite().getId(), SimplePage.cpn(pageNo), pageSize.intValue());
/* 727 */         List<Product> list = (List<Product>)page.getList();
/* 728 */         JSONArray jsons = new JSONArray();
/* 729 */         for (Product product : list) {
/* 730 */           jsons.put(product.convertToJson("list"));
/*     */         }
/* 732 */         body = jsons.toString() + ",\"totalCount\":" + page.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/* 735 */       e.printStackTrace();
/* 736 */       code = 100;
/* 737 */       message = "\"system exception\"";
/*     */     }
/* 739 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 740 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/product/createIndex"})
/*     */   public void createIndex(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 748 */     String body = "\"\"";
/* 749 */     String message = "\"success\"";
/* 750 */     int code = 200;
/*     */     try {
/* 752 */       String path = this.servletContext.getRealPath("/WEB-INF/lucene");
/* 753 */       int count = this.luceneProductSvc.index(path, null, null, null);
/* 754 */       JSONObject json = new JSONObject();
/* 755 */       json.put("count", count);
/* 756 */       body = json.toString();
/*     */     } catch (Exception e) {
/* 758 */       e.printStackTrace();
/* 759 */       code = 100;
/* 760 */       message = "\"system exception\"";
/*     */     }
/* 762 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 763 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.ProductController
 * JD-Core Version:    0.6.0
 */