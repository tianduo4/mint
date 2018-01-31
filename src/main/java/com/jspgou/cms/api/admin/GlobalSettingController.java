/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.CommonUtils;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.EmailSender;
/*     */ import com.jspgou.core.entity.Global;
/*     */ import com.jspgou.core.entity.MessageTemplate;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.GlobalMng;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class GlobalSettingController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private GlobalMng globalMng;
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteMng websiteMng;
/*     */ 
/*     */   @RequestMapping({"/global/get"})
/*     */   public void edit(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  38 */     String body = "\"\"";
/*  39 */     String message = "\"success\"";
/*  40 */     int code = 200;
/*     */     try {
/*  42 */       if (SiteUtils.getWeb(request).getGlobal() != null) {
/*  43 */         Global goobal = SiteUtils.getWeb(request)
/*  44 */           .getGlobal();
/*  45 */         body = goobal.convertToJson().toString();
/*     */       } else {
/*  47 */         code = 206;
/*  48 */         message = "\"object not found\"";
/*     */       }
/*     */     } catch (Exception e) {
/*  51 */       e.printStackTrace();
/*  52 */       code = 100;
/*  53 */       message = "\"system exception\"";
/*     */     }
/*  55 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  56 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/global/update"})
/*     */   public void update(Global bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  68 */     String body = "\"\"";
/*  69 */     String message = "\"success\"";
/*  70 */     int code = 200;
/*     */     try {
/*  72 */       WebErrors errors = WebErrors.create(request);
/*  73 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), bean.getPort(), bean.getDefImg(), bean.getActiveScore(), bean.getStockWarning(), bean.getDefImg() });
/*  74 */       if (!errors.hasErrors()) {
/*  75 */         this.globalMng.update(bean);
/*     */       } else {
/*  77 */         code = 202;
/*  78 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/*  81 */       e.printStackTrace();
/*  82 */       code = 100;
/*  83 */       message = "\"system exception\"";
/*     */     }
/*  85 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  86 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/Basic/get"})
/*     */   public void getBasic(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  94 */     String body = "\"\"";
/*  95 */     String message = "\"success\"";
/*  96 */     int code = 200;
/*     */     try {
/*  98 */       Website website = SiteUtils.getWeb(request);
/*  99 */       if (website != null) {
/* 100 */         body = website.convertToJson().toString();
/*     */       } else {
/* 102 */         code = 206;
/* 103 */         message = "\"object not found\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 106 */       e.printStackTrace();
/* 107 */       code = 100;
/* 108 */       message = "\"system exception\"";
/*     */     }
/* 110 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 111 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/Basic/update"})
/*     */   public void updateBase(Website bean, Integer syncPageFtpId, Integer uploadFtpId, HttpServletRequest request, HttpServletResponse response) {
/* 119 */     String body = "\"\"";
/* 120 */     String message = "\"success\"";
/* 121 */     int code = 200;
/*     */     try {
/* 123 */       WebErrors errors = WebErrors.create(request);
/* 124 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), bean.getName(), bean.getDomain(), bean.getRelativePath(), bean.getSuffix(), bean.getLocaleFront(), bean.getLocaleAdmin(), bean.getPageSync(), bean.getResouceSync() });
/* 125 */       if (!errors.hasErrors()) {
/* 126 */         Website site = SiteUtils.getWeb(request);
/* 127 */         bean.setId(site.getId());
/* 128 */         bean = this.websiteMng.update1(bean, uploadFtpId, syncPageFtpId);
/*     */       } else {
/* 130 */         code = 202;
/* 131 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 134 */       e.printStackTrace();
/* 135 */       code = 100;
/* 136 */       message = "\"system exception\"";
/*     */     }
/* 138 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 139 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/email/getEmail"})
/*     */   public void getEmail(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 147 */     String body = "\"\"";
/* 148 */     String message = "\"success\"";
/* 149 */     int code = 200;
/*     */     try {
/* 151 */       Website web = SiteUtils.getWeb(request);
/* 152 */       Map templates = web.getMessages();
/* 153 */       JSONObject json = new JSONObject();
/* 154 */       JSONObject obj = new JSONObject();
/* 155 */       JSONArray jsonArray = new JSONArray();
/* 156 */       JSONArray jsonArray1 = new JSONArray();
/* 157 */       if (templates != null) {
/* 158 */         MessageTemplate template = (MessageTemplate)templates.get("resetPassword");
/* 159 */         obj.put("resetPasswordSubject", CommonUtils.parseStr(template.getSubject()));
/* 160 */         obj.put("text", CommonUtils.parseStr(template.getText()));
/* 161 */         obj.put("activeTitle", CommonUtils.parseStr(template.getActiveTitle()));
/* 162 */         obj.put("activeTxt", CommonUtils.parseStr(template.getActiveTxt()));
/*     */       } else {
/* 164 */         obj.put("resetPasswordSubject", "");
/* 165 */         obj.put("text", "");
/* 166 */         obj.put("activeTitle", "");
/* 167 */         obj.put("activeTxt", "");
/*     */       }
/* 169 */       jsonArray.put(obj);
/* 170 */       jsonArray1.put(web.getEmailSender().convertToJson());
/* 171 */       json.put("messageMap", jsonArray);
/* 172 */       json.put("emailSender", jsonArray1);
/* 173 */       body = json.toString();
/*     */     } catch (Exception e) {
/* 175 */       e.printStackTrace();
/* 176 */       code = 100;
/* 177 */       message = "\"system exception\"";
/*     */     }
/* 179 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 180 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/email/update"})
/*     */   public void updateEmail(EmailSender emailSender, String resetPasswordSubject, String text, String activeTitle, String activeTxt, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 193 */     String body = "\"\"";
/* 194 */     String message = "\"success\"";
/* 195 */     int code = 200;
/*     */     try {
/* 197 */       WebErrors errors = WebErrors.create(request);
/* 198 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { emailSender.getUsername(), emailSender.getEncoding(), emailSender.getHost(), emailSender.getPersonal(), emailSender.getPassword(), resetPasswordSubject, text, activeTitle, activeTxt });
/* 199 */       if (!errors.hasErrors()) {
/* 200 */         Website web = SiteUtils.getWeb(request);
/* 201 */         web.setEmailSender(emailSender);
/* 202 */         Map messages = web.getMessages();
/* 203 */         MessageTemplate resetPassword = new MessageTemplate();
/* 204 */         resetPassword.setSubject(resetPasswordSubject);
/* 205 */         resetPassword.setText(text);
/* 206 */         resetPassword.setActiveTitle(activeTitle);
/* 207 */         resetPassword.setActiveTxt(activeTxt);
/* 208 */         messages.put("resetPassword", resetPassword);
/* 209 */         this.websiteMng.update(web);
/*     */       } else {
/* 211 */         code = 202;
/* 212 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 215 */       e.printStackTrace();
/* 216 */       code = 100;
/* 217 */       message = "\"system exception\"";
/*     */     }
/* 219 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 220 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.GlobalSettingController
 * JD-Core Version:    0.6.0
 */