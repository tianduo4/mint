/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.entity.WebserviceAuth;
/*     */ import com.jspgou.cms.manager.WebserviceAuthMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.security.encoder.Md5PwdEncoder;
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
/*     */ public class WebServiceAuthController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private WebserviceAuthMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private Md5PwdEncoder pwdEncoder;
/*     */ 
/*     */   @RequestMapping({"/webserviceAuth/list"})
/*     */   public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  47 */     String body = "\"\"";
/*  48 */     String message = "\"success\"";
/*  49 */     int code = 200;
/*     */     try
/*     */     {
/*  52 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  54 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  55 */       if (errors.hasErrors()) {
/*  56 */         code = 202;
/*  57 */         message = "\"param error\"";
/*     */       } else {
/*  59 */         Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), pageSize.intValue());
/*  60 */         List<WebserviceAuth> webserviceAuthList = (List<WebserviceAuth> )pagination.getList();
/*  61 */         JSONArray jsons = new JSONArray();
/*  62 */         for (WebserviceAuth webserviceAuth : webserviceAuthList) {
/*  63 */           jsons.add(webserviceAuth.converToJson());
/*     */         }
/*  65 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/*  68 */       e.printStackTrace();
/*  69 */       code = 100;
/*  70 */       message = "\"system exception\"";
/*     */     }
/*  72 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  73 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/webserviceAuth/save"})
/*     */   public void save(WebserviceAuth bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  85 */     String body = "\"\"";
/*  86 */     String message = "\"success\"";
/*  87 */     int code = 200;
/*     */     try {
/*  89 */       WebErrors errors = WebErrors.create(request);
/*  90 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { Boolean.valueOf(bean.getEnable()), bean.getPassword(), 
/*  91 */         bean.getSystem(), bean.getUsername() });
/*     */ 
/*  93 */       if (errors.hasErrors()) {
/*  94 */         code = 202;
/*  95 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/*  98 */         bean.setPassword(this.pwdEncoder.encodePassword(bean.getPassword()));
/*  99 */         this.manager.save(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 102 */       e.printStackTrace();
/* 103 */       code = 100;
/* 104 */       message = "\"system exception\"";
/*     */     }
/* 106 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 107 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/webserviceAuth/get"})
/*     */   public void get(Integer id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 117 */     String body = "\"\"";
/* 118 */     String message = "\"success\"";
/* 119 */     int code = 200;
/*     */     try {
/* 121 */       WebErrors errors = WebErrors.create(request);
/* 122 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 124 */       if (errors.hasErrors()) {
/* 125 */         code = 202;
/* 126 */         message = "\"param error\"";
/*     */       } else {
/* 128 */         WebserviceAuth webserviceAuth = new WebserviceAuth();
/* 129 */         if ((id != null) && (id.intValue() != 0)) {
/* 130 */           webserviceAuth = this.manager.findById(id);
/*     */         }
/* 132 */         if (webserviceAuth != null) {
/* 133 */           body = webserviceAuth.converToJson().toString();
/*     */         } else {
/* 135 */           code = 206;
/* 136 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 140 */       e.printStackTrace();
/* 141 */       code = 100;
/* 142 */       message = "\"system exception\"";
/*     */     }
/* 144 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 145 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/webserviceAuth/update"})
/*     */   public void update(WebserviceAuth bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 156 */     String body = "\"\"";
/* 157 */     String message = "\"success\"";
/* 158 */     int code = 200;
/*     */     try {
/* 160 */       WebErrors errors = WebErrors.create(request);
/* 161 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), Boolean.valueOf(bean.getEnable()), 
/* 162 */         bean.getSystem(), bean.getUsername() });
/*     */ 
/* 164 */       if (errors.hasErrors()) {
/* 165 */         code = 202;
/* 166 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 169 */         if (StringUtils.isBlank(bean.getPassword())) {
/* 170 */           WebserviceAuth entity = this.manager.findById(bean.getId());
/* 171 */           bean.setPassword(entity.getPassword());
/*     */         }
/*     */         else {
/* 174 */           bean.setPassword(this.pwdEncoder.encodePassword(bean.getPassword()));
/*     */         }
/* 176 */         this.manager.update(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 179 */       e.printStackTrace();
/* 180 */       code = 100;
/* 181 */       message = "\"system exception\"";
/*     */     }
/* 183 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 184 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/webserviceAuth/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 196 */     String body = "\"\"";
/* 197 */     String message = "\"success\"";
/* 198 */     int code = 200;
/*     */     try
/*     */     {
/* 202 */       if (!StringUtils.isNotBlank(ids)) {
/* 203 */         code = 202;
/* 204 */         message = "\"param error\"";
/*     */       } else {
/* 206 */         String[] str = ids.split(",");
/* 207 */         Integer[] id = new Integer[str.length];
/* 208 */         for (int i = 0; i < str.length; i++) {
/* 209 */           id[i] = Integer.valueOf(str[i]);
/*     */         }
/* 211 */         this.manager.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 214 */       e.printStackTrace();
/* 215 */       code = 100;
/* 216 */       message = "\"system exception\"";
/*     */     }
/* 218 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 219 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.WebServiceAuthController
 * JD-Core Version:    0.6.0
 */