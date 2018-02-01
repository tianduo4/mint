/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.PaymentPlugins;
/*     */ import com.jspgou.cms.manager.PaymentPluginsMng;
/*     */ import com.jspgou.cms.web.SignValidate;
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
/*     */ public class PaymentPluginsController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PaymentPluginsMng manager;
/*     */ 
/*     */   @RequestMapping({"/paymentPlugins/list"})
/*     */   public void list(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  35 */     String body = "\"\"";
/*  36 */     String message = "\"success\"";
/*  37 */     int code = 200;
/*     */     try
/*     */     {
/*  40 */       List<PaymentPlugins> list = this.manager.getList();
/*  41 */       JSONArray jsons = new JSONArray();
/*  42 */       for (PaymentPlugins p : list) {
/*  43 */         jsons.add(p.converToJson());
/*     */       }
/*  45 */       body = jsons.toString();
/*     */     }
/*     */     catch (Exception e) {
/*  48 */       e.printStackTrace();
/*  49 */       code = 100;
/*  50 */       message = "\"system exception\"";
/*     */     }
/*  52 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  53 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/paymentPlugins/priority"})
/*     */   public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  68 */     String body = "\"\"";
/*  69 */     String message = "\"success\"";
/*  70 */     int code = 200;
/*     */     try
/*     */     {
/*  73 */       if ((!StringUtils.isNotBlank(ids)) || (!StringUtils.isNotBlank(priority))) {
/*  74 */         code = 202;
/*  75 */         message = "\"param error\"";
/*     */       } else {
/*  77 */         String[] str1 = ids.split(",");
/*  78 */         Long[] wids1 = new Long[str1.length];
/*  79 */         for (int i = 0; i < str1.length; i++) {
/*  80 */           wids1[i] = Long.valueOf(str1[i]);
/*     */         }
/*     */ 
/*  83 */         String[] str2 = priority.split(",");
/*  84 */         Integer[] priority1 = new Integer[str2.length];
/*  85 */         for (int i = 0; i < str2.length; i++) {
/*  86 */           priority1[i] = Integer.valueOf(str2[i]);
/*     */         }
/*  88 */         this.manager.updatePriority(wids1, priority1);
/*     */       }
/*     */     } catch (Exception e) {
/*  91 */       e.printStackTrace();
/*  92 */       code = 100;
/*  93 */       message = "\"system exception\"";
/*     */     }
/*  95 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  96 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/paymentPlugins/get"})
/*     */   public void get(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 106 */     String body = "\"\"";
/* 107 */     String message = "\"success\"";
/* 108 */     int code = 200;
/*     */     try {
/* 110 */       WebErrors errors = WebErrors.create(request);
/* 111 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 113 */       if (errors.hasErrors()) {
/* 114 */         code = 202;
/* 115 */         message = "\"param error\"";
/*     */       } else {
/* 117 */         PaymentPlugins p = new PaymentPlugins();
/* 118 */         if ((id != null) && (id.longValue() != 0L)) {
/* 119 */           p = this.manager.findById(id);
/*     */         }
/* 121 */         if (p != null) {
/* 122 */           body = p.converToJson().toString();
/*     */         } else {
/* 124 */           code = 206;
/* 125 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 129 */       e.printStackTrace();
/* 130 */       code = 100;
/* 131 */       message = "\"system exception\"";
/*     */     }
/* 133 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 134 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/paymentPlugins/update"})
/*     */   public void update(PaymentPlugins bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 145 */     String body = "\"\"";
/* 146 */     String message = "\"success\"";
/* 147 */     int code = 200;
/*     */     try {
/* 149 */       WebErrors errors = WebErrors.create(request);
/* 150 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), bean.getName(), bean.getPriority(), 
/* 151 */         bean.getPartner(), bean.getSellerKey(), bean.getSellerEmail(), 
/* 152 */         bean.getDisabled(), bean.getIsDefault() });
/*     */ 
/* 154 */       if (errors.hasErrors()) {
/* 155 */         code = 202;
/* 156 */         message = "\"param error\"";
/*     */       } else {
/* 158 */         PaymentPlugins plugins = this.manager.findById(bean.getId());
/* 159 */         if (StringUtils.isNotEmpty(bean.getName())) {
/* 160 */           plugins.setName(bean.getName());
/*     */         }
/* 162 */         if (StringUtils.isNotEmpty(bean.getPartner())) {
/* 163 */           plugins.setPartner(bean.getPartner());
/*     */         }
/* 165 */         if (StringUtils.isNotEmpty(bean.getSellerEmail())) {
/* 166 */           plugins.setSellerEmail(bean.getSellerEmail());
/*     */         }
/* 168 */         if (StringUtils.isNotEmpty(bean.getSellerKey())) {
/* 169 */           plugins.setSellerKey(bean.getSellerKey());
/*     */         }
/* 171 */         if (StringUtils.isNotEmpty(bean.getCode())) {
/* 172 */           plugins.setCode(bean.getCode());
/*     */         }
/* 174 */         if (StringUtils.isNotEmpty(bean.getPublicKey())) {
/* 175 */           plugins.setPublicKey(bean.getPublicKey());
/*     */         }
/* 177 */         plugins.setIsDefault(bean.getIsDefault());
/* 178 */         plugins.setDisabled(bean.getDisabled());
/* 179 */         plugins.setPriority(bean.getPriority());
/* 180 */         this.manager.update(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 183 */       e.printStackTrace();
/* 184 */       code = 100;
/* 185 */       message = "\"system exception\"";
/*     */     }
/* 187 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 188 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/paymentPlugins/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 200 */     String body = "\"\"";
/* 201 */     String message = "\"success\"";
/* 202 */     int code = 200;
/*     */     try
/*     */     {
/* 206 */       if (!StringUtils.isNotBlank(ids)) {
/* 207 */         code = 202;
/* 208 */         message = "\"param error\"";
/*     */       } else {
/* 210 */         String[] str = ids.split(",");
/* 211 */         Long[] id = new Long[str.length];
/* 212 */         for (int i = 0; i < str.length; i++) {
/* 213 */           id[i] = Long.valueOf(str[i]);
/*     */         }
/* 215 */         this.manager.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 218 */       e.printStackTrace();
/* 219 */       ExceptionUtil.convertException(response, request, e);
/* 220 */       return;
/*     */     }
/* 222 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 223 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.PaymentPluginsController
 * JD-Core Version:    0.6.0
 */