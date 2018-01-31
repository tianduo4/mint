/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.Logistics;
/*     */ import com.jspgou.cms.manager.LogisticsMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class LogisticsController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private LogisticsMng logisticsMng;
/*     */ 
/*     */   @RequestMapping({"/logistics/list"})
/*     */   public void list(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  34 */     String body = "\"\"";
/*  35 */     String message = "\"success\"";
/*  36 */     int code = 200;
/*     */     try {
/*  38 */       List list = this.logisticsMng.getAllList();
/*  39 */       org.json.JSONArray jsonArray = new org.json.JSONArray();
/*  40 */       if (((list != null ? 1 : 0) & (list.size() > 0 ? 1 : 0)) != 0) {
/*  41 */         for (int i = 0; i < list.size(); i++) {
/*  42 */           jsonArray.put(i, ((Logistics)list.get(i)).convertToJson());
/*     */         }
/*     */       }
/*  45 */       body = jsonArray.toString();
/*     */     } catch (Exception e) {
/*  47 */       e.printStackTrace();
/*  48 */       code = 100;
/*  49 */       message = "\"system exception\"";
/*     */     }
/*  51 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  52 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/logistics/save"})
/*     */   public void save(Logistics logistics, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  62 */     String body = "\"\"";
/*  63 */     String message = "\"success\"";
/*  64 */     int code = 200;
/*     */     try {
/*  66 */       WebErrors errors = WebErrors.create(request);
/*  67 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { 
/*  68 */         logistics.getName(), logistics.getPriority() });
/*  69 */       if (!errors.hasErrors()) {
/*  70 */         logistics.setCourier(Boolean.valueOf(false));
/*  71 */         this.logisticsMng.save(logistics, null);
/*     */       } else {
/*  73 */         code = 202;
/*  74 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/*  77 */       e.printStackTrace();
/*  78 */       code = 100;
/*  79 */       message = "\"system exception\"";
/*     */     }
/*  81 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  82 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/logistics/get"})
/*     */   public void getLogistics(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  93 */     String body = "\"\"";
/*  94 */     String message = "\"success\"";
/*  95 */     int code = 200;
/*     */     try {
/*  97 */       WebErrors errors = WebErrors.create(request);
/*  98 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*  99 */       if (!errors.hasErrors()) {
/* 100 */         Logistics logistics = new Logistics();
/* 101 */         if (id.longValue() != 0L) {
/* 102 */           logistics = this.logisticsMng.findById(id);
/*     */         }
/* 104 */         if (logistics != null) {
/* 105 */           body = logistics.convertToJson().toString();
/*     */         } else {
/* 107 */           code = 206;
/* 108 */           message = "\"object not found\"";
/*     */         }
/*     */       } else {
/* 111 */         code = 202;
/* 112 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 115 */       e.printStackTrace();
/* 116 */       code = 100;
/* 117 */       message = "\"system exception\"";
/*     */     }
/* 119 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 120 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/logistics/priority"})
/*     */   public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 133 */     String body = "\"\"";
/* 134 */     String message = "\"success\"";
/* 135 */     int code = 200;
/*     */     try {
/* 137 */       WebErrors errors = WebErrors.create(request);
/* 138 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids, priority });
/* 139 */       if (!errors.hasErrors()) {
/* 140 */         Long[] id = null;
/* 141 */         Integer[] prioritys = null;
/* 142 */         String[] str = ids.split(",");
/* 143 */         id = new Long[str.length];
/* 144 */         for (int i = 0; i < str.length; i++) {
/* 145 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/*     */ 
/* 148 */         String[] str1 = priority.split(",");
/* 149 */         prioritys = new Integer[str1.length];
/* 150 */         for (int i = 0; i < str1.length; i++) {
/* 151 */           prioritys[i] = Integer.valueOf(Integer.parseInt(str1[i]));
/*     */         }
/* 153 */         this.logisticsMng.updatePriority(id, prioritys);
/*     */       } else {
/* 155 */         code = 202;
/* 156 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 159 */       e.printStackTrace();
/* 160 */       code = 100;
/* 161 */       message = "\"system exception\"";
/*     */     }
/* 163 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 164 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/logistics/update"})
/*     */   public void update(Logistics bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 175 */     String body = "\"\"";
/* 176 */     String message = "\"success\"";
/* 177 */     int code = 200;
/*     */     try {
/* 179 */       WebErrors errors = WebErrors.create(request);
/* 180 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), bean.getName(), bean.getPriority() });
/* 181 */       if (!errors.hasErrors()) {
/* 182 */         bean = this.logisticsMng.update(bean, null);
/*     */       } else {
/* 184 */         code = 202;
/* 185 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 188 */       e.printStackTrace();
/* 189 */       code = 100;
/* 190 */       message = "\"system exception\"";
/*     */     }
/* 192 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 193 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/logistics/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 205 */     String body = "\"\"";
/* 206 */     String message = "\"success\"";
/* 207 */     int code = 200;
/*     */     try {
/* 209 */       WebErrors errors = WebErrors.create(request);
/* 210 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 211 */       if (!errors.hasErrors()) {
/* 212 */         String[] str = ids.split(",");
/* 213 */         Long[] id = new Long[str.length];
/* 214 */         for (int i = 0; i < str.length; i++) {
/* 215 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 217 */         this.logisticsMng.deleteByIds(id);
/*     */       } else {
/* 219 */         code = 202;
/* 220 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 223 */       ExceptionUtil.convertException(response, request, e);
/* 224 */       return;
/*     */     }
/* 226 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 227 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/logistics/getCourier"})
/*     */   public void getCourier(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 238 */     String body = "\"\"";
/* 239 */     String message = "\"success\"";
/* 240 */     int code = 200;
/*     */     try {
/* 242 */       WebErrors errors = WebErrors.create(request);
/* 243 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/* 244 */       if (!errors.hasErrors()) {
/* 245 */         Logistics logistics = new Logistics();
/* 246 */         if (id.longValue() != 0L) {
/* 247 */           logistics = this.logisticsMng.findById(id);
/*     */         }
/* 249 */         if (logistics != null) {
/* 250 */           body = logistics.convertToJson1().toString();
/*     */         } else {
/* 252 */           code = 206;
/* 253 */           message = "\"object not found\"";
/*     */         }
/*     */       } else {
/* 256 */         code = 202;
/* 257 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 260 */       e.printStackTrace();
/* 261 */       code = 100;
/* 262 */       message = "\"system exception\"";
/*     */     }
/* 264 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 265 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/courier/update"})
/*     */   public void updateCourier(Logistics bean, String courier, String imgUrl, Integer evenSpacing, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 275 */     String body = "\"\"";
/* 276 */     String message = "\"success\"";
/* 277 */     int code = 200;
/*     */     try {
/* 279 */       WebErrors errors = WebErrors.create(request);
/* 280 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), bean.getImgUrl() });
/* 281 */       if (!errors.hasErrors())
/*     */       {
/* 283 */         net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(courier);
/* 284 */         net.sf.json.JSONObject undefined = json.getJSONObject("undefined");
/* 285 */         net.sf.json.JSONArray results = undefined.getJSONArray("elements");
/* 286 */         bean.setFnt(Double.valueOf(((String)results.getJSONObject(0).get("top")).replace("px", "")));
/* 287 */         bean.setFnz(Double.valueOf(((String)results.getJSONObject(0).get("left")).replace("px", "")));
/* 288 */         bean.setFnw(Double.valueOf(((String)results.getJSONObject(0).get("width")).replace("px", "")));
/* 289 */         bean.setFnh(Double.valueOf(((String)results.getJSONObject(0).get("height")).replace("px", "")));
/* 290 */         bean.setFnwh((String)results.getJSONObject(0).get("fontWeight"));
/* 291 */         bean.setFat(Double.valueOf(((String)results.getJSONObject(1).get("top")).replace("px", "")));
/* 292 */         bean.setFaz(Double.valueOf(((String)results.getJSONObject(1).get("left")).replace("px", "")));
/* 293 */         bean.setFaw(Double.valueOf(((String)results.getJSONObject(1).get("width")).replace("px", "")));
/* 294 */         bean.setFah(Double.valueOf(((String)results.getJSONObject(1).get("height")).replace("px", "")));
/* 295 */         bean.setFawh((String)results.getJSONObject(0).get("fontWeight"));
/* 296 */         bean.setFpt(Double.valueOf(((String)results.getJSONObject(2).get("top")).replace("px", "")));
/* 297 */         bean.setFpz(Double.valueOf(((String)results.getJSONObject(2).get("left")).replace("px", "")));
/* 298 */         bean.setFpw(Double.valueOf(((String)results.getJSONObject(2).get("width")).replace("px", "")));
/* 299 */         bean.setFph(Double.valueOf(((String)results.getJSONObject(2).get("height")).replace("px", "")));
/* 300 */         bean.setFpwh((String)results.getJSONObject(0).get("fontWeight"));
/* 301 */         bean.setSnt(Double.valueOf(((String)results.getJSONObject(3).get("top")).replace("px", "")));
/* 302 */         bean.setSnz(Double.valueOf(((String)results.getJSONObject(3).get("left")).replace("px", "")));
/* 303 */         bean.setSnw(Double.valueOf(((String)results.getJSONObject(3).get("width")).replace("px", "")));
/* 304 */         bean.setSnh(Double.valueOf(((String)results.getJSONObject(3).get("height")).replace("px", "")));
/* 305 */         bean.setSnwh((String)results.getJSONObject(0).get("fontWeight"));
/* 306 */         bean.setSat(Double.valueOf(((String)results.getJSONObject(4).get("top")).replace("px", "")));
/* 307 */         bean.setSaz(Double.valueOf(((String)results.getJSONObject(4).get("left")).replace("px", "")));
/* 308 */         bean.setSaw(Double.valueOf(((String)results.getJSONObject(4).get("width")).replace("px", "")));
/* 309 */         bean.setSah(Double.valueOf(((String)results.getJSONObject(4).get("height")).replace("px", "")));
/* 310 */         bean.setSawh((String)results.getJSONObject(0).get("fontWeight"));
/* 311 */         bean.setSpt(Double.valueOf(((String)results.getJSONObject(5).get("top")).replace("px", "")));
/* 312 */         bean.setSpz(Double.valueOf(((String)results.getJSONObject(5).get("left")).replace("px", "")));
/* 313 */         bean.setSpw(Double.valueOf(((String)results.getJSONObject(5).get("width")).replace("px", "")));
/* 314 */         bean.setSph(Double.valueOf(((String)results.getJSONObject(5).get("height")).replace("px", "")));
/* 315 */         bean.setSpwh((String)results.getJSONObject(0).get("fontWeight"));
/* 316 */         bean.setEvenSpacing(evenSpacing);
/* 317 */         bean.setImgUrl(imgUrl);
/* 318 */         this.logisticsMng.update(bean, null);
/*     */       } else {
/* 320 */         code = 202;
/* 321 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 324 */       e.printStackTrace();
/* 325 */       code = 100;
/* 326 */       message = "\"system exception\"";
/*     */     }
/* 328 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 329 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/courier/save"})
/*     */   public void saveCourier(Long id, String courier, String imgUrl, Integer evenSpacing, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 339 */     String body = "\"\"";
/* 340 */     String message = "\"success\"";
/* 341 */     int code = 200;
/*     */     try {
/* 343 */       WebErrors errors = WebErrors.create(request);
/* 344 */       errors = ApiValidate.validateRequiredParams(errors, new Object[0]);
/* 345 */       if (!errors.hasErrors()) {
/* 346 */         Logistics bean = this.logisticsMng.findById(id);
/* 347 */         net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(courier);
/* 348 */         net.sf.json.JSONObject undefined = json.getJSONObject("undefined");
/* 349 */         net.sf.json.JSONArray results = undefined.getJSONArray("elements");
/* 350 */         bean.setFnt(Double.valueOf(((String)results.getJSONObject(0).get("top")).replace("px", "")));
/* 351 */         bean.setFnz(Double.valueOf(((String)results.getJSONObject(0).get("left")).replace("px", "")));
/* 352 */         bean.setFnw(Double.valueOf(((String)results.getJSONObject(0).get("width")).replace("px", "")));
/* 353 */         bean.setFnh(Double.valueOf(((String)results.getJSONObject(0).get("height")).replace("px", "")));
/* 354 */         bean.setFnwh((String)results.getJSONObject(0).get("fontWeight"));
/* 355 */         bean.setFat(Double.valueOf(((String)results.getJSONObject(1).get("top")).replace("px", "")));
/* 356 */         bean.setFaz(Double.valueOf(((String)results.getJSONObject(1).get("left")).replace("px", "")));
/* 357 */         bean.setFaw(Double.valueOf(((String)results.getJSONObject(1).get("width")).replace("px", "")));
/* 358 */         bean.setFah(Double.valueOf(((String)results.getJSONObject(1).get("height")).replace("px", "")));
/* 359 */         bean.setFawh((String)results.getJSONObject(0).get("fontWeight"));
/* 360 */         bean.setFpt(Double.valueOf(((String)results.getJSONObject(2).get("top")).replace("px", "")));
/* 361 */         bean.setFpz(Double.valueOf(((String)results.getJSONObject(2).get("left")).replace("px", "")));
/* 362 */         bean.setFpw(Double.valueOf(((String)results.getJSONObject(2).get("width")).replace("px", "")));
/* 363 */         bean.setFph(Double.valueOf(((String)results.getJSONObject(2).get("height")).replace("px", "")));
/* 364 */         bean.setFpwh((String)results.getJSONObject(0).get("fontWeight"));
/* 365 */         bean.setSnt(Double.valueOf(((String)results.getJSONObject(3).get("top")).replace("px", "")));
/* 366 */         bean.setSnz(Double.valueOf(((String)results.getJSONObject(3).get("left")).replace("px", "")));
/* 367 */         bean.setSnw(Double.valueOf(((String)results.getJSONObject(3).get("width")).replace("px", "")));
/* 368 */         bean.setSnh(Double.valueOf(((String)results.getJSONObject(3).get("height")).replace("px", "")));
/* 369 */         bean.setSnwh((String)results.getJSONObject(0).get("fontWeight"));
/* 370 */         bean.setSat(Double.valueOf(((String)results.getJSONObject(4).get("top")).replace("px", "")));
/* 371 */         bean.setSaz(Double.valueOf(((String)results.getJSONObject(4).get("left")).replace("px", "")));
/* 372 */         bean.setSaw(Double.valueOf(((String)results.getJSONObject(4).get("width")).replace("px", "")));
/* 373 */         bean.setSah(Double.valueOf(((String)results.getJSONObject(4).get("height")).replace("px", "")));
/* 374 */         bean.setSawh((String)results.getJSONObject(0).get("fontWeight"));
/* 375 */         bean.setSpt(Double.valueOf(((String)results.getJSONObject(5).get("top")).replace("px", "")));
/* 376 */         bean.setSpz(Double.valueOf(((String)results.getJSONObject(5).get("left")).replace("px", "")));
/* 377 */         bean.setSpw(Double.valueOf(((String)results.getJSONObject(5).get("width")).replace("px", "")));
/* 378 */         bean.setSph(Double.valueOf(((String)results.getJSONObject(5).get("height")).replace("px", "")));
/* 379 */         bean.setSpwh((String)results.getJSONObject(0).get("fontWeight"));
/* 380 */         bean.setEvenSpacing(evenSpacing);
/* 381 */         if (StringUtils.isNotBlank(imgUrl))
/* 382 */           bean.setImgUrl(imgUrl);
/*     */         else {
/* 384 */           bean.setImgUrl("res/common/img/kd/ems.jpg");
/*     */         }
/* 386 */         bean.setCourier(Boolean.valueOf(true));
/* 387 */         this.logisticsMng.update(bean, null);
/*     */       } else {
/* 389 */         code = 202;
/* 390 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 393 */       e.printStackTrace();
/* 394 */       code = 100;
/* 395 */       message = "\"system exception\"";
/*     */     }
/* 397 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 398 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.LogisticsController
 * JD-Core Version:    0.6.0
 */