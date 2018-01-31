/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.entity.ApiAccount;
/*     */ import com.jspgou.cms.manager.ApiAccountMng;
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
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ApiAccountController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ApiAccountMng manager;
/*     */ 
/*     */   @RequestMapping({"/apiAccount/list"})
/*     */   public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  39 */     String body = "\"\"";
/*  40 */     String message = "\"success\"";
/*  41 */     int code = 200;
/*     */     try
/*     */     {
/*  44 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  46 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  47 */       if (errors.hasErrors()) {
/*  48 */         code = 202;
/*  49 */         message = "\"param error\"";
/*     */       } else {
/*  51 */         Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), pageSize.intValue());
/*  52 */         List<ApiAccount> apiAccounts =(List<ApiAccount>)pagination.getList();
/*  53 */         JSONArray jsons = new JSONArray();
/*  54 */         for (ApiAccount api : apiAccounts) {
/*  55 */           jsons.add(api.converToJson());
/*     */         }
/*  57 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/*  60 */       e.printStackTrace();
/*  61 */       code = 100;
/*  62 */       message = "\"system exception\"";
/*     */     }
/*  64 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  65 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/apiAccount/save"})
/*     */   public void save(ApiAccount bean, String acount, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  78 */     String body = "\"\"";
/*  79 */     String message = "\"success\"";
/*  80 */     int code = 200;
/*     */     try {
/*  82 */       WebErrors errors = WebErrors.create(request);
/*  83 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getAesKey(), acount, bean.getAppKey(), Boolean.valueOf(bean.getDisabled()), bean.getIvKey() });
/*     */ 
/*  85 */       if (errors.hasErrors()) {
/*  86 */         code = 202;
/*  87 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/*  90 */         ApiAccount apiAccount = this.manager.findByAppId(acount);
/*  91 */         if (apiAccount != null) {
/*  92 */           code = 210;
/*  93 */           message = "\"appid exist\"";
/*     */         } else {
/*  95 */           bean.setAppId(acount);
/*  96 */           this.manager.save(bean);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 100 */       e.printStackTrace();
/* 101 */       code = 100;
/* 102 */       message = "\"system exception\"";
/*     */     }
/* 104 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 105 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/apiAccount/get"})
/*     */   public void get(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 115 */     String body = "\"\"";
/* 116 */     String message = "\"success\"";
/* 117 */     int code = 200;
/*     */     try {
/* 119 */       WebErrors errors = WebErrors.create(request);
/* 120 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 122 */       if (errors.hasErrors()) {
/* 123 */         code = 202;
/* 124 */         message = "\"param error\"";
/*     */       } else {
/* 126 */         ApiAccount apiAccount = new ApiAccount();
/* 127 */         if ((id != null) && (id.longValue() != 0L)) {
/* 128 */           apiAccount = this.manager.findById(id);
/*     */         }
/* 130 */         if (apiAccount != null) {
/* 131 */           body = apiAccount.converToJson().toString();
/*     */         } else {
/* 133 */           code = 206;
/* 134 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 138 */       e.printStackTrace();
/* 139 */       code = 100;
/* 140 */       message = "\"system exception\"";
/*     */     }
/* 142 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 143 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.ApiAccountController
 * JD-Core Version:    0.6.0
 */