/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.ConfigAttr;
/*     */ import com.jspgou.core.entity.Global;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.entity.WebsiteAttr;
/*     */ import com.jspgou.core.manager.GlobalMng;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class ConfigController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteMng websiteMng;
/*     */ 
/*     */   @Autowired
/*     */   private GlobalMng globalMng;
/*     */ 
/*     */   @RequestMapping({"/config/getsso"})
/*     */   public void ssoAuthenticateGet(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  46 */     String body = "\"\"";
/*  47 */     String message = "\"success\"";
/*  48 */     int code = 200;
/*     */     try {
/*  50 */       Website website = this.websiteMng.get();
/*     */ 
/*  52 */       if (website != null) {
/*  53 */         List ssoAttr = website.getAuthUrl();
/*  54 */         WebsiteAttr attr = website.getWebsiteAttr();
/*  55 */         JSONObject json = new JSONObject();
/*  56 */         json.put("attr", ssoAttr);
/*  57 */         json.put("attr_ssoEnable", attr.getSsoEnable());
/*  58 */         body = json.toString();
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
/*     */   @RequestMapping({"/config/updatesso"})
/*     */   public void update(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  77 */     String body = "\"\"";
/*  78 */     String message = "\"success\"";
/*  79 */     int code = 200;
/*     */     try {
/*  81 */       Map ssoMap = RequestUtils.getRequestMap(request, "attr_");
/*  82 */       this.websiteMng.updateSsoAttr(ssoMap);
/*     */     } catch (Exception e) {
/*  84 */       e.printStackTrace();
/*  85 */       code = 100;
/*  86 */       message = "\"system exception\"";
/*     */     }
/*  88 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  89 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/config/getApi"})
/*     */   public void apiGet(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  99 */     String body = "\"\"";
/* 100 */     String message = "\"success\"";
/* 101 */     int code = 200;
/*     */     try
/*     */     {
/* 104 */       ConfigAttr configAttr = this.globalMng.findIdkey().getConfigAttr();
/* 105 */       JSONArray jsons = new JSONArray();
/* 106 */       jsons.add(configAttr.converToJson());
/* 107 */       body = jsons.toString();
/*     */     }
/*     */     catch (Exception e) {
/* 110 */       e.printStackTrace();
/* 111 */       code = 100;
/* 112 */       message = "\"system exception\"";
/*     */     }
/* 114 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 115 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/config/updateApi"})
/*     */   public void updateApi(ConfigAttr bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 126 */     String body = "\"\"";
/* 127 */     String message = "\"success\"";
/* 128 */     int code = 200;
/*     */     try {
/* 130 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/* 132 */       if (bean.getQqEnable().booleanValue()) {
/* 133 */         errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getQqID() });
/*     */       }
/* 135 */       if (bean.getSinaEnable().booleanValue()) {
/* 136 */         errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getSinaID() });
/*     */       }
/* 138 */       if (bean.getWeixinEnable().booleanValue()) {
/* 139 */         errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getWeixinID() });
/*     */       }
/* 141 */       if (errors.hasErrors()) {
/* 142 */         code = 202;
/* 143 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 146 */         if (StringUtils.isEmpty(bean.getSinaKey())) {
/* 147 */           bean.getAttr().remove("sinaKey");
/*     */         }
/*     */ 
/* 150 */         if (StringUtils.isEmpty(bean.getSinaID())) {
/* 151 */           bean.getAttr().remove("sinaID");
/*     */         }
/*     */ 
/* 154 */         if (StringUtils.isEmpty(bean.getWeixinKey())) {
/* 155 */           bean.getAttr().remove("weixinKey");
/*     */         }
/*     */ 
/* 158 */         if (StringUtils.isEmpty(bean.getWeixinID())) {
/* 159 */           bean.getAttr().remove("weixinID");
/*     */         }
/*     */ 
/* 162 */         if (StringUtils.isEmpty(bean.getQqID())) {
/* 163 */           bean.getAttr().remove("qqWeboKey");
/*     */         }
/*     */ 
/* 166 */         if (StringUtils.isEmpty(bean.getQqKey())) {
/* 167 */           bean.getAttr().remove("qqKey");
/*     */         }
/*     */ 
/* 170 */         if (StringUtils.isEmpty(bean.getQqWeboID())) {
/* 171 */           bean.getAttr().remove("qqWeboID");
/*     */         }
/*     */ 
/* 174 */         if (StringUtils.isEmpty(bean.getQqWeboKey())) {
/* 175 */           bean.getAttr().remove("qqWeboKey");
/*     */         }
/*     */ 
/* 178 */         if (bean.getQqWeboEnable() == null) {
/* 179 */           bean.getAttr().remove("qqWeboEnable");
/*     */         }
/* 181 */         this.globalMng.updateConfigAttr(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 184 */       e.printStackTrace();
/* 185 */       code = 100;
/* 186 */       message = "\"system exception\"";
/*     */     }
/* 188 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 189 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.ConfigController
 * JD-Core Version:    0.6.0
 */