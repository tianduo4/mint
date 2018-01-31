/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.ApiInfo;
/*     */ import com.jspgou.cms.manager.ApiInfoMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
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
/*     */ public class ApiInfoController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ApiInfoMng manager;
/*     */ 
/*     */   @RequestMapping({"/apiInfo/list"})
/*     */   public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  40 */     String body = "\"\"";
/*  41 */     String message = "\"success\"";
/*  42 */     int code = 200;
/*     */     try
/*     */     {
/*  45 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  47 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  48 */       if (errors.hasErrors()) {
/*  49 */         code = 202;
/*  50 */         message = "\"param error\"";
/*     */       } else {
/*  52 */         Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), pageSize.intValue());
/*  53 */         List<ApiInfo> apiRecord = (List<ApiInfo>)pagination.getList();
/*  54 */         JSONArray jsons = new JSONArray();
/*  55 */         for (ApiInfo api : apiRecord) {
/*  56 */           jsons.add(api.converToJson());
/*     */         }
/*  58 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/*  61 */       e.printStackTrace();
/*  62 */       code = 100;
/*  63 */       message = "\"system exception\"";
/*     */     }
/*  65 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  66 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/apiInfo/save"})
/*     */   public void save(ApiInfo bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  78 */     String body = "\"\"";
/*  79 */     String message = "\"success\"";
/*  80 */     int code = 200;
/*     */     try {
/*  82 */       WebErrors errors = WebErrors.create(request);
/*  83 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getApiName(), bean.getApiUrl(), bean.getApiCode(), bean.getLimitCallDay(), bean.getDisabled() });
/*     */ 
/*  85 */       if (errors.hasErrors()) {
/*  86 */         code = 202;
/*  87 */         message = "\"param error\"";
/*     */       } else {
/*  89 */         this.manager.save(bean);
/*     */       }
/*     */     } catch (Exception e) {
/*  92 */       e.printStackTrace();
/*  93 */       code = 100;
/*  94 */       message = "\"system exception\"";
/*     */     }
/*  96 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  97 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/apiInfo/get"})
/*     */   public void get(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 108 */     String body = "\"\"";
/* 109 */     String message = "\"success\"";
/* 110 */     int code = 200;
/*     */     try {
/* 112 */       WebErrors errors = WebErrors.create(request);
/* 113 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 115 */       if (errors.hasErrors()) {
/* 116 */         code = 202;
/* 117 */         message = "\"param error\"";
/*     */       } else {
/* 119 */         ApiInfo apiInfo = new ApiInfo();
/* 120 */         if ((id != null) && (id.longValue() != 0L)) {
/* 121 */           apiInfo = this.manager.findById(id);
/*     */         }
/* 123 */         if (apiInfo != null) {
/* 124 */           body = apiInfo.converToJson().toString();
/*     */         } else {
/* 126 */           code = 206;
/* 127 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 131 */       e.printStackTrace();
/* 132 */       code = 100;
/* 133 */       message = "\"system exception\"";
/*     */     }
/* 135 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 136 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/apiInfo/update"})
/*     */   public void update(ApiInfo bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 147 */     String body = "\"\"";
/* 148 */     String message = "\"success\"";
/* 149 */     int code = 200;
/*     */     try {
/* 151 */       WebErrors errors = WebErrors.create(request);
/* 152 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), bean.getDisabled(), bean.getApiName(), bean.getApiUrl(), bean.getApiCode(), bean.getLimitCallDay() });
/*     */ 
/* 154 */       if (errors.hasErrors()) {
/* 155 */         code = 202;
/* 156 */         message = "\"param error\"";
/*     */       } else {
/* 158 */         this.manager.update(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 161 */       e.printStackTrace();
/* 162 */       code = 100;
/* 163 */       message = "\"system exception\"";
/*     */     }
/* 165 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 166 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/apiInfo/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 178 */     String body = "\"\"";
/* 179 */     String message = "\"success\"";
/* 180 */     int code = 200;
/*     */     try
/*     */     {
/* 184 */       if (!StringUtils.isNotBlank(ids)) {
/* 185 */         code = 202;
/* 186 */         message = "\"param error\"";
/*     */       } else {
/* 188 */         String[] str = ids.split(",");
/* 189 */         Long[] id = new Long[str.length];
/* 190 */         for (int i = 0; i < str.length; i++) {
/* 191 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 193 */         this.manager.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 196 */       ExceptionUtil.convertException(response, request, e);
/* 197 */       return;
/*     */     }
/* 199 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 200 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.ApiInfoController
 * JD-Core Version:    0.6.0
 */