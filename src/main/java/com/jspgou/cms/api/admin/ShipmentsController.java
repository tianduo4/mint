/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.Logistics;
/*     */ import com.jspgou.cms.entity.Shipments;
/*     */ import com.jspgou.cms.entity.ShopShipments;
/*     */ import com.jspgou.cms.manager.LogisticsMng;
/*     */ import com.jspgou.cms.manager.ShipmentsMng;
/*     */ import com.jspgou.cms.manager.ShopShipmentsMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ShipmentsController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ShipmentsMng shipmentsMng;
/*     */ 
/*     */   @Autowired
/*     */   private LogisticsMng logisticsMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopShipmentsMng shopShipmentsMng;
/*     */ 
/*     */   @RequestMapping({"/shipments/list"})
/*     */   public void list(Boolean isPrint, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  42 */     String body = "\"\"";
/*  43 */     String message = "\"success\"";
/*  44 */     int code = 200;
/*     */     try {
/*  46 */       if (pageNo == null) {
/*  47 */         pageNo = Integer.valueOf(1);
/*     */       }
/*  49 */       if (pageSize == null) {
/*  50 */         pageSize = Integer.valueOf(10);
/*     */       }
/*  52 */       Pagination page = this.shipmentsMng.getPage(isPrint, pageNo.intValue(), pageSize.intValue());
/*  53 */       List list = page.getList();
/*  54 */       int totalCount = page.getTotalCount();
/*  55 */       JSONArray jsonArray = new JSONArray();
/*  56 */       if ((list != null) && (list.size() > 0)) {
/*  57 */         for (int i = 0; i < list.size(); i++) {
/*  58 */           jsonArray.put(i, ((Shipments)list.get(i)).convertToJson());
/*     */         }
/*     */       }
/*  61 */       body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
/*     */     } catch (Exception e) {
/*  63 */       e.printStackTrace();
/*  64 */       code = 100;
/*  65 */       message = "\"system exception\"";
/*     */     }
/*  67 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  68 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shipments/view"})
/*     */   public void view(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  80 */     String body = "\"\"";
/*  81 */     String message = "\"success\"";
/*  82 */     int code = 200;
/*     */     try {
/*  84 */       WebErrors errors = WebErrors.create(request);
/*  85 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/*  87 */       if (errors.hasErrors()) {
/*  88 */         code = 202;
/*  89 */         message = "\"param error\"";
/*     */       } else {
/*  91 */         Shipments shipments = this.shipmentsMng.findById(id);
/*  92 */         if (shipments != null) {
/*  93 */           body = shipments.convertToJson().toString();
/*     */         } else {
/*  95 */           code = 206;
/*  96 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 101 */       e.printStackTrace();
/* 102 */       code = 100;
/* 103 */       message = "\"system exception\"";
/*     */     }
/* 105 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 106 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shipments/printOrder"})
/*     */   public void printOrder(String ids, Long logisticsId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 123 */     String body = "\"\"";
/* 124 */     String message = "\"success\"";
/* 125 */     int code = 200;
/*     */     try {
/* 127 */       WebErrors errors = WebErrors.create(request);
/* 128 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids, 
/* 129 */         logisticsId });
/* 130 */       if (!errors.hasErrors()) {
/* 131 */         Long[] id = null;
/*     */ 
/* 133 */         JSONObject json = new JSONObject();
/* 134 */         JSONArray jsonArray = new JSONArray();
/* 135 */         JSONArray jsonArray1 = new JSONArray();
/*     */ 
/* 137 */         String[] str = ids.split(",");
/* 138 */         id = new Long[str.length];
/* 139 */         for (int i = 0; i < str.length; i++) {
/* 140 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/*     */ 
/* 143 */         List shipments = new ArrayList();
/* 144 */         if (shipments != null) {
/* 145 */           for (int i = 0; i < id.length; i++) {
/* 146 */             shipments.add(this.shipmentsMng.findById(id[i]));
/* 147 */             jsonArray.put(((Shipments)shipments.get(i)).convertToJson());
/*     */           }
/* 149 */           json.put("shipments", jsonArray);
/*     */         }
/*     */ 
/* 152 */         Logistics logistics = this.logisticsMng.findById(logisticsId);
/* 153 */         if (logistics != null) {
/* 154 */           jsonArray1.put(logistics.convertToJson());
/* 155 */           json.put("logistics", jsonArray1);
/*     */         } else {
/* 157 */           code = 206;
/* 158 */           message = "\"object not found\"";
/*     */         }
/* 160 */         body = json.toString();
/*     */       } else {
/* 162 */         code = 202;
/* 163 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 166 */       e.printStackTrace();
/* 167 */       code = 100;
/* 168 */       message = "\"system exception\"";
/*     */     }
/* 170 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 171 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shipments/isPrintTrue"})
/*     */   public void isprintTrue(String ids, Boolean status, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 184 */     String body = "\"\"";
/* 185 */     String message = "\"success\"";
/* 186 */     int code = 200;
/*     */     try {
/* 188 */       WebErrors errors = WebErrors.create(request);
/* 189 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 190 */       if (!errors.hasErrors()) {
/* 191 */         Long[] id = null;
/* 192 */         String[] str = ids.split(",");
/* 193 */         id = new Long[str.length];
/* 194 */         for (int i = 0; i < str.length; i++) {
/* 195 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 197 */         for (Long i : id) {
/* 198 */           Shipments shipments = this.shipmentsMng.findById(i);
/* 199 */           shipments.setIsPrint(status);
/* 200 */           this.shipmentsMng.update(shipments);
/*     */         }
/*     */       } else {
/* 203 */         code = 202;
/* 204 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 207 */       e.printStackTrace();
/* 208 */       code = 100;
/* 209 */       message = "\"system exception\"";
/*     */     }
/* 211 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 212 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/shipments/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 227 */     String body = "\"\"";
/* 228 */     String message = "\"success\"";
/* 229 */     int code = 200;
/*     */     try
/*     */     {
/* 232 */       if (!StringUtils.isNotBlank(ids)) {
/* 233 */         code = 202;
/* 234 */         message = "\"param error\"";
/*     */       } else {
/* 236 */         Long[] id = null;
/* 237 */         String[] str = ids.split(",");
/* 238 */         id = new Long[str.length];
/* 239 */         for (int i = 0; i < str.length; i++) {
/* 240 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/*     */ 
/* 243 */         this.shipmentsMng.deleteByIds(id);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 247 */       ExceptionUtil.convertException(response, request, e);
/* 248 */       return;
/*     */     }
/* 250 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 251 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shopShipments/list"})
/*     */   public void shipmentsList(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 262 */     String body = "\"\"";
/* 263 */     String message = "\"success\"";
/* 264 */     int code = 200;
/*     */     try {
/* 266 */       if (pageNo == null) {
/* 267 */         pageNo = Integer.valueOf(1);
/*     */       }
/* 269 */       if (pageSize == null) {
/* 270 */         pageSize = Integer.valueOf(10);
/*     */       }
/* 272 */       Pagination page = this.shopShipmentsMng.getPage(pageNo.intValue(), pageSize.intValue());
/* 273 */       List list = page.getList();
/* 274 */       int totalCount = page.getTotalCount();
/* 275 */       JSONArray jsonArray = new JSONArray();
/* 276 */       if ((list != null) && (list.size() > 0)) {
/* 277 */         for (int i = 0; i < list.size(); i++) {
/* 278 */           jsonArray.put(i, ((ShopShipments)list.get(i)).convertToJson());
/*     */         }
/*     */       }
/* 281 */       body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
/*     */     } catch (Exception e) {
/* 283 */       ExceptionUtil.convertException(response, request, e);
/* 284 */       return;
/*     */     }
/* 286 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 287 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/shopShipments/save"})
/*     */   public void shipShipmentsSave(ShopShipments bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 298 */     String body = "\"\"";
/* 299 */     String message = "\"success\"";
/* 300 */     int code = 200;
/*     */     try {
/* 302 */       WebErrors errors = WebErrors.create(request);
/* 303 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getName(), bean.getMobile(), bean.getAddress(), bean.getIsDefault() });
/* 304 */       if (!errors.hasErrors()) {
/* 305 */         this.shopShipmentsMng.save(bean);
/*     */       } else {
/* 307 */         code = 202;
/* 308 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 311 */       ExceptionUtil.convertException(response, request, e);
/* 312 */       return;
/*     */     }
/* 314 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 315 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shopShipments/get"})
/*     */   public void shipShipmentsget(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 324 */     String body = "\"\"";
/* 325 */     String message = "\"success\"";
/* 326 */     int code = 200;
/*     */     try {
/* 328 */       WebErrors errors = WebErrors.create(request);
/* 329 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/* 330 */       if (!errors.hasErrors()) {
/* 331 */         ShopShipments bean = new ShopShipments();
/* 332 */         if (id.longValue() != 0L) {
/* 333 */           bean = this.shopShipmentsMng.findById(id);
/*     */         }
/* 335 */         if (bean != null) {
/* 336 */           body = bean.convertToJson().toString();
/*     */         } else {
/* 338 */           code = 206;
/* 339 */           message = "\"object not found\"";
/*     */         }
/*     */       } else {
/* 342 */         code = 202;
/* 343 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 346 */       ExceptionUtil.convertException(response, request, e);
/* 347 */       return;
/*     */     }
/* 349 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 350 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/* 356 */   @SignValidate
/*     */   @RequestMapping({"/shopShipments/update"})
/*     */   public void shipShipmentsUpdate(ShopShipments bean, HttpServletRequest request, HttpServletResponse response) { String body = "\"\"";
/* 357 */     String message = "\"success\"";
/* 358 */     int code = 200;
/*     */     try {
/* 360 */       WebErrors errors = WebErrors.create(request);
/* 361 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId() });
/* 362 */       if (!errors.hasErrors()) {
/* 363 */         this.shopShipmentsMng.update(bean);
/*     */       } else {
/* 365 */         code = 202;
/* 366 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 369 */       ExceptionUtil.convertException(response, request, e);
/* 370 */       return;
/*     */     }
/* 372 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 373 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/shopShipments/delete"})
/*     */   public void shipShipmentsDelete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 385 */     String body = "\"\"";
/* 386 */     String message = "\"success\"";
/* 387 */     int code = 200;
/*     */     try {
/* 389 */       if (StringUtils.isNotBlank(ids)) {
/* 390 */         Long[] id = null;
/* 391 */         String[] str = ids.split(",");
/* 392 */         id = new Long[str.length];
/* 393 */         for (int i = 0; i < str.length; i++) {
/* 394 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 396 */         this.shopShipmentsMng.deleteByIds(id);
/*     */       } else {
/* 398 */         code = 202;
/* 399 */         message = "\"param error\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 403 */       ExceptionUtil.convertException(response, request, e);
/* 404 */       return;
/*     */     }
/* 406 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 407 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.ShipmentsController
 * JD-Core Version:    0.6.0
 */