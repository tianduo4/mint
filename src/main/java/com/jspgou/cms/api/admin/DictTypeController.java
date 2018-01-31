/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.ShopDictionaryType;
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
/*     */ public class DictTypeController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ShopDictionaryTypeMng shopDictionaryTypeMng;
/*     */ 
/*     */   @RequestMapping({"/dictType/all"})
/*     */   public void list(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  37 */     String body = "\"\"";
/*  38 */     String message = "\"success\"";
/*  39 */     int code = 200;
/*     */     try {
/*  41 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  43 */       errors = ApiValidate.validateRequiredParams(errors, new Object[0]);
/*  44 */       if (errors.hasErrors()) {
/*  45 */         code = 202;
/*  46 */         message = "\"param error\"";
/*     */       } else {
/*  48 */         List<ShopDictionaryType> dictTypes = this.shopDictionaryTypeMng.findAll();
/*  49 */         JSONArray jsons = new JSONArray();
/*  50 */         for (ShopDictionaryType dictType : dictTypes) {
/*  51 */           jsons.add(dictType.converToJson());
/*     */         }
/*  53 */         body = jsons.toString();
/*     */       }
/*     */     } catch (Exception e) {
/*  56 */       e.printStackTrace();
/*  57 */       code = 100;
/*  58 */       message = "\"system exception\"";
/*     */     }
/*  60 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  61 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/dictType/list"})
/*     */   public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  75 */     String body = "\"\"";
/*  76 */     String message = "\"success\"";
/*  77 */     int code = 200;
/*     */     try {
/*  79 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  81 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  82 */       if (errors.hasErrors()) {
/*  83 */         code = 202;
/*  84 */         message = "\"param error\"";
/*     */       } else {
/*  86 */         Pagination pagination = this.shopDictionaryTypeMng.getPage(pageNo.intValue(), pageSize.intValue());
/*  87 */         List<ShopDictionaryType> dictTypes = (List<ShopDictionaryType>)pagination.getList();
/*  88 */         JSONArray jsons = new JSONArray();
/*  89 */         for (ShopDictionaryType dictType : dictTypes) {
/*  90 */           jsons.add(dictType.converToJson());
/*     */         }
/*  92 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/*  95 */       e.printStackTrace();
/*  96 */       code = 100;
/*  97 */       message = "\"system exception\"";
/*     */     }
/*  99 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 100 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/dictType/save"})
/*     */   public void save(ShopDictionaryType dictType, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 113 */     String body = "\"\"";
/* 114 */     String message = "\"success\"";
/* 115 */     int code = 200;
/*     */     try {
/* 117 */       WebErrors errors = WebErrors.create(request);
/* 118 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { dictType.getName() });
/*     */ 
/* 120 */       if (errors.hasErrors()) {
/* 121 */         code = 202;
/* 122 */         message = "\"param error\"";
/*     */       } else {
/* 124 */         this.shopDictionaryTypeMng.save(dictType);
/*     */       }
/*     */     } catch (Exception e) {
/* 127 */       e.printStackTrace();
/* 128 */       code = 100;
/* 129 */       message = "\"system exception\"";
/*     */     }
/* 131 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 132 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/dictType/get"})
/*     */   public void get(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 143 */     String body = "\"\"";
/* 144 */     String message = "\"success\"";
/* 145 */     int code = 200;
/*     */     try {
/* 147 */       WebErrors errors = WebErrors.create(request);
/* 148 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 150 */       if (errors.hasErrors()) {
/* 151 */         code = 202;
/* 152 */         message = "\"param error\"";
/*     */       } else {
/* 154 */         ShopDictionaryType dictType = new ShopDictionaryType();
/* 155 */         if ((id != null) && (id.longValue() != 0L)) {
/* 156 */           dictType = this.shopDictionaryTypeMng.findById(id);
/*     */         }
/* 158 */         if (dictType != null) {
/* 159 */           body = dictType.converToJson().toString();
/*     */         } else {
/* 161 */           code = 206;
/* 162 */           message = "\"object not found\"";
/*     */         }
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
/*     */   @SignValidate
/*     */   @RequestMapping({"/dictType/update"})
/*     */   public void update(ShopDictionaryType dictType, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 183 */     String body = "\"\"";
/* 184 */     String message = "\"success\"";
/* 185 */     int code = 200;
/*     */     try {
/* 187 */       WebErrors errors = WebErrors.create(request);
/* 188 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { dictType.getId(), dictType.getName() });
/*     */ 
/* 190 */       if (errors.hasErrors()) {
/* 191 */         code = 202;
/* 192 */         message = "\"param error\"";
/*     */       } else {
/* 194 */         this.shopDictionaryTypeMng.update(dictType);
/*     */       }
/*     */     } catch (Exception e) {
/* 197 */       e.printStackTrace();
/* 198 */       code = 100;
/* 199 */       message = "\"system exception\"";
/*     */     }
/* 201 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 202 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/dictType/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 214 */     String body = "\"\"";
/* 215 */     String message = "\"success\"";
/* 216 */     int code = 200;
/*     */     try
/*     */     {
/* 219 */       if (!StringUtils.isNotBlank(ids)) {
/* 220 */         code = 202;
/* 221 */         message = "\"param error\"";
/*     */       } else {
/* 223 */         String[] str = ids.split(",");
/* 224 */         Long[] id = new Long[str.length];
/* 225 */         for (int i = 0; i < str.length; i++) {
/* 226 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 228 */         this.shopDictionaryTypeMng.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 231 */       ExceptionUtil.convertException(response, request, e);
/* 232 */       return;
/*     */     }
/* 234 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 235 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.DictTypeController
 * JD-Core Version:    0.6.0
 */