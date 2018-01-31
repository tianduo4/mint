/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.ProductType;
/*     */ import com.jspgou.cms.entity.ProductTypeProperty;
/*     */ import com.jspgou.cms.manager.ProductTypeMng;
/*     */ import com.jspgou.cms.manager.ProductTypePropertyMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ProductTypeController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ProductTypeMng productTypeMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductTypePropertyMng typePropertyMng;
/*     */ 
/*     */   @RequestMapping({"/type/all"})
/*     */   public void typeList(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/*  44 */     String body = "\"\"";
/*  45 */     String message = "\"success\"";
/*  46 */     int code = 200;
/*     */     try {
/*  48 */       Long site = SiteUtils.getWebId(request);
/*  49 */       if (site != null) {
/*  50 */         List list = this.productTypeMng.getList(site);
/*  51 */         JSONArray jsonArray = new JSONArray();
/*  52 */         if ((list != null) && (list.size() > 0)) {
/*  53 */           for (int i = 0; i < list.size(); i++) {
/*  54 */             jsonArray.put(i, ((ProductType)list.get(i)).convertToJson(null));
/*     */           }
/*     */         }
/*  57 */         body = jsonArray.toString();
/*     */       } else {
/*  59 */         code = 206;
/*  60 */         message = "\"object not found\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  64 */       e.printStackTrace();
/*  65 */       code = 100;
/*  66 */       message = "\"system exception\"";
/*     */     }
/*  68 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  69 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/type/list"})
/*     */   public void typeList(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/*  85 */     String body = "\"\"";
/*  86 */     String message = "\"success\"";
/*  87 */     int code = 200;
/*     */     try {
/*  89 */       Long site = SiteUtils.getWebId(request);
/*  90 */       if (site != null) {
/*  91 */         Pagination page = this.productTypeMng.getPage(
/*  92 */           CmsThreadVariable.getSite().getId(), SimplePage.cpn(pageNo), pageSize.intValue());
/*  93 */         List list = page.getList();
/*  94 */         JSONArray jsonArray = new JSONArray();
/*  95 */         if ((list != null) && (list.size() > 0)) {
/*  96 */           for (int i = 0; i < list.size(); i++) {
/*  97 */             jsonArray.put(i, ((ProductType)list.get(i)).convertToJson(null));
/*     */           }
/*     */         }
/* 100 */         body = jsonArray.toString() + ",\"totalCount\":" + 
/* 101 */           page.getTotalCount();
/*     */       } else {
/* 103 */         code = 206;
/* 104 */         message = "\"object not found\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 108 */       e.printStackTrace();
/* 109 */       code = 100;
/* 110 */       message = "\"system exception\"";
/*     */     }
/* 112 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 113 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/typeProperty/list"})
/*     */   public void typeProperty(Long typeId, Boolean isCategory, HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 129 */     String body = "\"\"";
/* 130 */     String message = "\"success\"";
/* 131 */     int code = 200;
/*     */     try {
/* 133 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/* 135 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { typeId, 
/* 136 */         isCategory });
/* 137 */       if (!errors.hasErrors()) {
/* 138 */         ProductType pType = this.productTypeMng.findById(typeId);
/* 139 */         JSONArray jsonArray = new JSONArray();
/* 140 */         if (isCategory.booleanValue()) {
/* 141 */           List list = this.typePropertyMng.getList(
/* 142 */             typeId, true);
/* 143 */           if ((list != null) && (list.size() > 0))
/* 144 */             for (int i = 0; i < list.size(); i++)
/* 145 */               jsonArray.put(
/* 146 */                 i, 
/* 147 */                 ((ProductTypeProperty)list.get(i)).convertToJson(typeId, 
/* 148 */                 pType.getName()));
/*     */         }
/*     */         else
/*     */         {
/* 152 */           List list = this.typePropertyMng.getList(
/* 153 */             typeId, false);
/* 154 */           if ((list != null) && (list.size() > 0)) {
/* 155 */             for (int i = 0; i < list.size(); i++) {
/* 156 */               jsonArray.put(
/* 157 */                 i, 
/* 158 */                 ((ProductTypeProperty)list.get(i)).convertToJson(typeId, 
/* 159 */                 pType.getName()));
/*     */             }
/*     */           }
/*     */         }
/* 163 */         body = jsonArray.toString();
/*     */       } else {
/* 165 */         code = 202;
/* 166 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 169 */       e.printStackTrace();
/* 170 */       code = 100;
/* 171 */       message = "\"system exception\"";
/*     */     }
/*     */ 
/* 174 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 175 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/type/save"})
/*     */   public void save(ProductType productType, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 204 */     String body = "\"\"";
/* 205 */     String message = "\"success\"";
/* 206 */     int code = 200;
/*     */     try {
/* 208 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/* 210 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { 
/* 211 */         productType.getName(), productType.getChannelPrefix(), 
/* 212 */         productType.getContentPrefix() });
/* 213 */       if (!errors.hasErrors()) {
/* 214 */         productType.setWebsite(CmsThreadVariable.getSite());
/* 215 */         this.productTypeMng.save(productType);
/*     */       } else {
/* 217 */         code = 202;
/* 218 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 221 */       e.printStackTrace();
/* 222 */       code = 100;
/* 223 */       message = "\"system exception\"";
/*     */     }
/* 225 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 226 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/type/getType"})
/*     */   public void getType(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 238 */     String body = "\"\"";
/* 239 */     String message = "\"success\"";
/* 240 */     int code = 200;
/*     */     try {
/* 242 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/* 244 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/* 245 */       if (!errors.hasErrors()) {
/* 246 */         ProductType bean = new ProductType();
/* 247 */         if (id.longValue() != 0L) {
/* 248 */           bean = this.productTypeMng.findById(id);
/*     */         }
/* 250 */         if (bean != null) {
/* 251 */           body = bean.convertToJson(null).toString();
/*     */         } else {
/* 253 */           code = 206;
/* 254 */           message = "\"object not found\"";
/*     */         }
/*     */       } else {
/* 257 */         code = 202;
/* 258 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 261 */       e.printStackTrace();
/* 262 */       code = 100;
/* 263 */       message = "\"system exception\"";
/*     */     }
/* 265 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 266 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/type/update"})
/*     */   public void update(ProductType productType, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 279 */     String body = "\"\"";
/* 280 */     String message = "\"success\"";
/* 281 */     int code = 200;
/*     */     try {
/* 283 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/* 285 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { 
/* 286 */         productType.getId() });
/* 287 */       if (!errors.hasErrors()) {
/* 288 */         if (code == 200)
/* 289 */           this.productTypeMng.update(productType);
/*     */       }
/*     */       else {
/* 292 */         code = 202;
/* 293 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 296 */       e.printStackTrace();
/* 297 */       code = 100;
/* 298 */       message = "\"system exception\"";
/*     */     }
/* 300 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 301 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/type/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 313 */     String body = "\"\"";
/* 314 */     String message = "\"success\"";
/* 315 */     int code = 200;
/*     */     try {
/* 317 */       WebErrors errors = WebErrors.create(request);
/* 318 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 319 */       if (!errors.hasErrors()) {
/* 320 */         Long[] id = null;
/* 321 */         String[] str = ids.split(",");
/* 322 */         id = new Long[str.length];
/* 323 */         for (int i = 0; i < str.length; i++) {
/* 324 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 326 */         this.productTypeMng.deleteByIds(id);
/*     */       } else {
/* 328 */         code = 202;
/* 329 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 332 */       e.printStackTrace();
/* 333 */       ExceptionUtil.convertException(response, request, e);
/* 334 */       return;
/*     */     }
/* 336 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 337 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/typeProperty/save"})
/*     */   public void saveTypeproerty(Long typeId, Boolean isCategory, String field, String propertyName, String dataType, String sort, String single, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 350 */     String body = "\"\"";
/* 351 */     String message = "\"success\"";
/* 352 */     int code = 200;
/*     */     try {
/* 354 */       WebErrors errors = WebErrors.create(request);
/* 355 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { typeId, 
/* 356 */         isCategory, field, propertyName, dataType, dataType, sort });
/* 357 */       if (!errors.hasErrors()) {
/* 358 */         String[] fields = null;
/* 359 */         String[] propertyNames = null;
/* 360 */         Integer[] dataTypes = null;
/* 361 */         Integer[] sorts = null;
/* 362 */         Boolean[] singles = null;
/* 363 */         String[] str = field.split(",");
/* 364 */         fields = new String[str.length];
/* 365 */         for (int i = 0; i < str.length; i++) {
/* 366 */           fields[i] = str[i];
/*     */         }
/*     */ 
/* 369 */         String[] str1 = propertyName.split(",");
/* 370 */         propertyNames = new String[str.length];
/* 371 */         for (int i = 0; i < str1.length; i++) {
/* 372 */           propertyNames[i] = str1[i];
/*     */         }
/*     */ 
/* 375 */         String[] str2 = dataType.split(",");
/* 376 */         dataTypes = new Integer[str.length];
/* 377 */         for (int i = 0; i < str2.length; i++) {
/* 378 */           dataTypes[i] = Integer.valueOf(Integer.parseInt(str2[i]));
/*     */         }
/*     */ 
/* 381 */         String[] str3 = sort.split(",");
/* 382 */         sorts = new Integer[str3.length];
/* 383 */         for (int i = 0; i < str3.length; i++) {
/* 384 */           sorts[i] = Integer.valueOf(Integer.parseInt(str3[i]));
/*     */         }
/*     */ 
/* 387 */         String[] str4 = single.split(",");
/* 388 */         singles = new Boolean[str4.length];
/* 389 */         for (int i = 0; i < str3.length; i++) {
/* 390 */           singles[i] = Boolean.valueOf(str3[i]);
/*     */         }
/*     */ 
/* 393 */         ProductType pType = this.productTypeMng.findById(typeId);
/* 394 */         List itemList = this.typePropertyMng.getItems(
/* 395 */           pType, isCategory.booleanValue(), fields, propertyNames, dataTypes, 
/* 396 */           sorts, singles);
/* 397 */         this.typePropertyMng.saveList(itemList);
/*     */       } else {
/* 399 */         code = 202;
/* 400 */         message = "\"param error\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 404 */       e.printStackTrace();
/* 405 */       code = 100;
/* 406 */       message = "\"system exception\"";
/*     */     }
/* 408 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 409 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/typePropertyContent/save"})
/*     */   public void saveContent(String id, String sort, String propertyName, String single, Long typeId, Boolean isCategory, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 423 */     String body = "\"\"";
/* 424 */     String message = "\"success\"";
/* 425 */     int code = 200;
/*     */     try {
/* 427 */       WebErrors errors = WebErrors.create(request);
/* 428 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { typeId, id });
/* 429 */       if (!errors.hasErrors()) {
/* 430 */         Integer[] sorts = null;
/* 431 */         Boolean[] singles = null;
/* 432 */         String[] propertyNames = null;
/* 433 */         Long[] ids = null;
/*     */ 
/* 435 */         String[] str = sort.split(",");
/* 436 */         sorts = new Integer[str.length];
/* 437 */         for (int i = 0; i < str.length; i++) {
/* 438 */           sorts[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */         }
/*     */ 
/* 441 */         String[] str1 = single.split(",");
/* 442 */         singles = new Boolean[str1.length];
/* 443 */         for (int i = 0; i < str1.length; i++) {
/* 444 */           singles[i] = Boolean.valueOf(str1[i]);
/*     */         }
/*     */ 
/* 447 */         String[] str2 = propertyName.split(",");
/* 448 */         propertyNames = new String[str2.length];
/* 449 */         for (int i = 0; i < str2.length; i++) {
/* 450 */           propertyNames[i] = str2[i];
/*     */         }
/* 452 */         String[] str3 = id.split(",");
/* 453 */         ids = new Long[str3.length];
/* 454 */         for (int i = 0; i < str3.length; i++) {
/* 455 */           ids[i] = Long.valueOf(Long.parseLong(str3[i]));
/*     */         }
/*     */ 
/* 458 */         this.typePropertyMng.updatePriority(ids, sorts, propertyNames, 
/* 459 */           singles);
/*     */       }
/*     */       else {
/* 462 */         code = 202;
/* 463 */         message = "\"param error\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 467 */       e.printStackTrace();
/* 468 */       code = 100;
/* 469 */       message = "\"system exception\"";
/*     */     }
/* 471 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 472 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/typePropertyContent/delete"})
/*     */   public void delContent(String ids, Integer typeId, Boolean isCategory, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 484 */     String body = "\"\"";
/* 485 */     String message = "\"success\"";
/* 486 */     int code = 200;
/*     */     try {
/* 488 */       WebErrors errors = WebErrors.create(request);
/* 489 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids, typeId, isCategory });
/* 490 */       if (!errors.hasErrors()) {
/* 491 */         Long[] id = null;
/* 492 */         String[] str = ids.split(",");
/* 493 */         id = new Long[str.length];
/* 494 */         for (int i = 0; i < str.length; i++) {
/* 495 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 497 */         this.typePropertyMng.deleteByIds(id);
/*     */       } else {
/* 499 */         code = 202;
/* 500 */         message = "\"param error\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 505 */       e.printStackTrace();
/* 506 */       code = 100;
/* 507 */       message = "\"system exception\"";
/*     */     }
/* 509 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 510 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.ProductTypeController
 * JD-Core Version:    0.6.0
 */