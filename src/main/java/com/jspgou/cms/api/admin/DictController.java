/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.ShopDictionary;
/*     */ import com.jspgou.cms.manager.ShopDictionaryMng;
/*     */ import com.jspgou.cms.manager.ShopDictionaryTypeMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class DictController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ShopDictionaryMng shopDictionaryMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopDictionaryTypeMng shopDictionaryTypeMng;
/*     */ 
/*     */   @RequestMapping({"/dict/listByTypeId"})
/*     */   public void list(Long typeId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  44 */     String body = "\"\"";
/*  45 */     String message = "\"success\"";
/*  46 */     int code = 200;
/*     */     try {
/*  48 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  50 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { typeId });
/*  51 */       if (errors.hasErrors()) {
/*  52 */         code = 202;
/*  53 */         message = "\"param error\"";
/*     */       } else {
/*  55 */         List<ShopDictionary> dicts = this.shopDictionaryMng.getListByType(typeId);
/*  56 */         JSONArray jsons = new JSONArray();
/*  57 */         for (ShopDictionary dict : dicts) {
/*  58 */           jsons.add(dict.converToJson());
/*     */         }
/*  60 */         body = jsons.toString();
/*     */       }
/*     */     } catch (Exception e) {
/*  63 */       e.printStackTrace();
/*  64 */       code = 100;
/*  65 */       message = "\"system exception\"";
/*     */     }
/*  67 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  68 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/dict/list"})
/*     */   public void list(Integer pageNo, Integer pageSize, Long typeId, String name, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  84 */     String body = "\"\"";
/*  85 */     String message = "\"success\"";
/*  86 */     int code = 200;
/*     */     try {
/*  88 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  90 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  91 */       if (errors.hasErrors()) {
/*  92 */         code = 202;
/*  93 */         message = "\"param error\"";
/*     */       } else {
/*  95 */         Pagination pagination = this.shopDictionaryMng.getPage(name, typeId, pageNo.intValue(), pageSize.intValue());
/*  96 */         List<ShopDictionary> dicts = (List<ShopDictionary>)pagination.getList();
/*  97 */         JSONArray jsons = new JSONArray();
/*  98 */         for (ShopDictionary dict : dicts) {
/*  99 */           jsons.add(dict.converToJson());
/*     */         }
/* 101 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/* 104 */       e.printStackTrace();
/* 105 */       code = 100;
/* 106 */       message = "\"system exception\"";
/*     */     }
/* 108 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 109 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/dict/save"})
/*     */   public void save(ShopDictionary dict, Long typeId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 122 */     String body = "\"\"";
/* 123 */     String message = "\"success\"";
/* 124 */     int code = 200;
/*     */     try {
/* 126 */       WebErrors errors = WebErrors.create(request);
/* 127 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { dict.getName(), typeId });
/*     */ 
/* 129 */       if (errors.hasErrors()) {
/* 130 */         code = 202;
/* 131 */         message = "\"param error\"";
/*     */       } else {
/* 133 */         dict.setShopDictionaryType(this.shopDictionaryTypeMng.findById(typeId));
/* 134 */         this.shopDictionaryMng.save(dict);
/*     */       }
/*     */     } catch (Exception e) {
/* 137 */       e.printStackTrace();
/* 138 */       code = 100;
/* 139 */       message = "\"system exception\"";
/*     */     }
/* 141 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 142 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/dict/get"})
/*     */   public void get(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 153 */     String body = "\"\"";
/* 154 */     String message = "\"success\"";
/* 155 */     int code = 200;
/*     */     try {
/* 157 */       WebErrors errors = WebErrors.create(request);
/* 158 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 160 */       if (errors.hasErrors()) {
/* 161 */         code = 202;
/* 162 */         message = "\"param error\"";
/*     */       } else {
/* 164 */         ShopDictionary dict = new ShopDictionary();
/* 165 */         if ((id != null) && (id.longValue() != 0L)) {
/* 166 */           dict = this.shopDictionaryMng.findById(id);
/*     */         }
/* 168 */         if (dict != null) {
/* 169 */           body = dict.converToJson().toString();
/*     */         } else {
/* 171 */           code = 206;
/* 172 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 176 */       e.printStackTrace();
/* 177 */       code = 100;
/* 178 */       message = "\"system exception\"";
/*     */     }
/* 180 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 181 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/dict/update"})
/*     */   public void update(ShopDictionary dict, Long typeId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 194 */     String body = "\"\"";
/* 195 */     String message = "\"success\"";
/* 196 */     int code = 200;
/*     */     try {
/* 198 */       WebErrors errors = WebErrors.create(request);
/* 199 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { dict.getId(), dict.getName(), typeId });
/*     */ 
/* 201 */       if (errors.hasErrors()) {
/* 202 */         code = 202;
/* 203 */         message = "\"param error\"";
/*     */       } else {
/* 205 */         dict.setShopDictionaryType(this.shopDictionaryTypeMng.findById(typeId));
/* 206 */         this.shopDictionaryMng.update(dict);
/*     */       }
/*     */     } catch (Exception e) {
/* 209 */       e.printStackTrace();
/* 210 */       code = 100;
/* 211 */       message = "\"system exception\"";
/*     */     }
/* 213 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 214 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/dict/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 226 */     String body = "\"\"";
/* 227 */     String message = "\"success\"";
/* 228 */     int code = 200;
/*     */     try
/*     */     {
/* 231 */       if (!StringUtils.isNotBlank(ids)) {
/* 232 */         code = 202;
/* 233 */         message = "\"param error\"";
/*     */       } else {
/* 235 */         String[] str = ids.split(",");
/* 236 */         Long[] id = new Long[str.length];
/* 237 */         for (int i = 0; i < str.length; i++) {
/* 238 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 240 */         this.shopDictionaryMng.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 243 */       ExceptionUtil.convertException(response, request, e);
/* 244 */       return;
/*     */     }
/* 246 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 247 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/dict/updateSort"})
/*     */   public void updateSort(String ids, String prioritys, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 261 */     String body = "\"\"";
/* 262 */     String message = "\"success\"";
/* 263 */     int code = 200;
/*     */     try {
/* 265 */       WebErrors errors = WebErrors.create(request);
/* 266 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids, prioritys });
/*     */ 
/* 268 */       if (errors.hasErrors()) {
/* 269 */         code = 202;
/* 270 */         message = "\"param error\"";
/*     */       } else {
/* 272 */         String[] str = ids.split(",");
/* 273 */         String[] pro = prioritys.split(",");
/* 274 */         Long[] id = new Long[str.length];
/* 275 */         Integer[] priority = new Integer[pro.length];
/* 276 */         for (int i = 0; i < str.length; i++) {
/* 277 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/* 278 */           priority[i] = Integer.valueOf(Integer.parseInt(pro[i]));
/*     */         }
/* 280 */         this.shopDictionaryMng.updatePriority(id, priority);
/*     */       }
/*     */     }
/*     */     catch (IndexOutOfBoundsException e) {
/* 284 */       code = 202;
/* 285 */       message = "\"param error\"";
/*     */     } catch (Exception e) {
/* 287 */       e.printStackTrace();
/* 288 */       code = 100;
/* 289 */       message = "\"system exception\"";
/*     */     }
/* 291 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 292 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.DictController
 * JD-Core Version:    0.6.0
 */