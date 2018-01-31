/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.plug.weixin.entity.Weixin;
/*     */ import com.jspgou.plug.weixin.manager.WeixinMng;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class WeixinController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private WeixinMng manager;
/*     */ 
/*     */   @RequestMapping({"/weixin/get"})
/*     */   public void get(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  32 */     String body = "\"\"";
/*  33 */     String message = "\"success\"";
/*  34 */     int code = 200;
/*     */     try {
/*  36 */       Weixin entity = this.manager.find(SiteUtils.getWebId(request));
/*  37 */       if (entity.getId() == null) {
/*  38 */         entity = new Weixin();
/*     */       }
/*  40 */       body = entity.converToJson().toString();
/*     */     } catch (Exception e) {
/*  42 */       e.printStackTrace();
/*  43 */       code = 100;
/*  44 */       message = "\"system exception\"";
/*     */     }
/*  46 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  47 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/weixin/update"})
/*     */   public void update(Weixin bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  58 */     String body = "\"\"";
/*  59 */     String message = "\"success\"";
/*  60 */     int code = 200;
/*     */     try {
/*  62 */       WebErrors errors = WebErrors.create(request);
/*  63 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), bean.getAppKey(), 
/*  64 */         bean.getAppSecret(), bean.getToken(), bean.getWelcome() });
/*     */ 
/*  66 */       if (errors.hasErrors()) {
/*  67 */         code = 202;
/*  68 */         message = "\"param error\"";
/*     */       } else {
/*  70 */         this.manager.update(bean);
/*     */       }
/*     */     } catch (Exception e) {
/*  73 */       e.printStackTrace();
/*  74 */       code = 100;
/*  75 */       message = "\"system exception\"";
/*     */     }
/*  77 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  78 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/weixin/save"})
/*     */   public void save(Weixin bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  89 */     String body = "\"\"";
/*  90 */     String message = "\"success\"";
/*  91 */     int code = 200;
/*     */     try {
/*  93 */       WebErrors errors = WebErrors.create(request);
/*  94 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getWelcome(), bean.getAppKey(), 
/*  95 */         bean.getAppSecret(), bean.getToken() });
/*     */ 
/*  97 */       if (errors.hasErrors()) {
/*  98 */         code = 202;
/*  99 */         message = "\"param error\"";
/*     */       } else {
/* 101 */         bean.setSite(SiteUtils.getWeb(request));
/* 102 */         this.manager.save(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 105 */       e.printStackTrace();
/* 106 */       code = 100;
/* 107 */       message = "\"system exception\"";
/*     */     }
/* 109 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 110 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.WeixinController
 * JD-Core Version:    0.6.0
 */