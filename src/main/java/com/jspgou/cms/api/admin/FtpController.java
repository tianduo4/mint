/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Ftp;
/*     */ import com.jspgou.core.manager.FtpMng;
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
/*     */ public class FtpController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private FtpMng manager;
/*     */ 
/*     */   @RequestMapping({"/ftp/list"})
/*     */   public void list(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  38 */     String body = "\"\"";
/*  39 */     String message = "\"success\"";
/*  40 */     int code = 200;
/*     */     try
/*     */     {
/*  43 */       List ftps = this.manager.getList();
/*  44 */       JSONArray jsons = new JSONArray();
/*  45 */       for (Ftp ftp : ftps) {
/*  46 */         jsons.add(ftp.converToJson());
/*     */       }
/*  48 */       body = jsons.toString();
/*     */     }
/*     */     catch (Exception e) {
/*  51 */       e.printStackTrace();
/*  52 */       code = 100;
/*  53 */       message = "\"system exception\"";
/*     */     }
/*  55 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  56 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/ftp/save"})
/*     */   public void save(Ftp bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  68 */     String body = "\"\"";
/*  69 */     String message = "\"success\"";
/*  70 */     int code = 200;
/*     */     try {
/*  72 */       WebErrors errors = WebErrors.create(request);
/*  73 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getName(), bean.getIp(), 
/*  74 */         bean.getPort(), bean.getTimeout(), bean.getUsername(), bean.getPassword(), 
/*  75 */         bean.getEncoding(), bean.getUrl() });
/*     */ 
/*  77 */       if (errors.hasErrors()) {
/*  78 */         code = 202;
/*  79 */         message = "\"param error\"";
/*     */       } else {
/*  81 */         this.manager.save(bean);
/*     */       }
/*     */     } catch (Exception e) {
/*  84 */       e.printStackTrace();
/*  85 */       code = 100;
/*  86 */       message = "\"system exception\"";
/*     */     }
/*  88 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  89 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/ftp/get"})
/*     */   public void get(Integer id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  99 */     String body = "\"\"";
/* 100 */     String message = "\"success\"";
/* 101 */     int code = 200;
/*     */     try {
/* 103 */       WebErrors errors = WebErrors.create(request);
/* 104 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 106 */       if (errors.hasErrors()) {
/* 107 */         code = 202;
/* 108 */         message = "\"param error\"";
/*     */       } else {
/* 110 */         Ftp ftp = new Ftp();
/* 111 */         if ((id != null) && (id.intValue() != 0)) {
/* 112 */           ftp = this.manager.findById(id);
/*     */         }
/* 114 */         if (ftp != null) {
/* 115 */           body = ftp.converToJson().toString();
/*     */         } else {
/* 117 */           code = 206;
/* 118 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 122 */       e.printStackTrace();
/* 123 */       code = 100;
/* 124 */       message = "\"system exception\"";
/*     */     }
/* 126 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 127 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/ftp/update"})
/*     */   public void update(Ftp bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 138 */     String body = "\"\"";
/* 139 */     String message = "\"success\"";
/* 140 */     int code = 200;
/*     */     try {
/* 142 */       WebErrors errors = WebErrors.create(request);
/* 143 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getName(), bean.getId(), bean.getIp(), 
/* 144 */         bean.getPort(), bean.getTimeout(), bean.getUsername(), bean.getEncoding(), 
/* 145 */         bean.getUrl() });
/*     */ 
/* 147 */       if (errors.hasErrors()) {
/* 148 */         code = 202;
/* 149 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 152 */         if (StringUtils.isBlank(bean.getPassword())) {
/* 153 */           bean.setPassword(this.manager.findById(bean.getId()).getPassword());
/*     */         }
/* 155 */         this.manager.update(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 158 */       e.printStackTrace();
/* 159 */       code = 100;
/* 160 */       message = "\"system exception\"";
/*     */     }
/* 162 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 163 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/ftp/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 175 */     String body = "\"\"";
/* 176 */     String message = "\"success\"";
/* 177 */     int code = 200;
/*     */     try
/*     */     {
/* 181 */       if (!StringUtils.isNotBlank(ids)) {
/* 182 */         code = 202;
/* 183 */         message = "\"param error\"";
/*     */       } else {
/* 185 */         String[] str = ids.split(",");
/* 186 */         Integer[] id = new Integer[str.length];
/* 187 */         for (int i = 0; i < str.length; i++) {
/* 188 */           id[i] = Integer.valueOf(str[i]);
/*     */         }
/* 190 */         this.manager.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 193 */       ExceptionUtil.convertException(response, request, e);
/* 194 */       return;
/*     */     }
/* 196 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 197 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.FtpController
 * JD-Core Version:    0.6.0
 */