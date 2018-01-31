/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.Webservice;
/*     */ import com.jspgou.cms.manager.WebserviceMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class WebserviceController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private WebserviceMng manager;
/*     */ 
/*     */   @RequestMapping({"/webservice/list"})
/*     */   public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  35 */     String body = "\"\"";
/*  36 */     String message = "\"success\"";
/*  37 */     int code = 200;
/*     */     try {
/*  39 */       if (pageNo == null) {
/*  40 */         pageNo = Integer.valueOf(1);
/*     */       }
/*  42 */       if (pageSize == null) {
/*  43 */         pageSize = Integer.valueOf(10);
/*     */       }
/*  45 */       Pagination page = this.manager.getPage(pageNo.intValue(), pageSize.intValue());
/*  46 */       List list = page.getList();
/*  47 */       int totalCount = page.getTotalCount();
/*  48 */       JSONArray jsonArray = new JSONArray();
/*  49 */       if ((list != null) && (list.size() > 0)) {
/*  50 */         for (int i = 0; i < list.size(); i++) {
/*  51 */           jsonArray.put(i, ((Webservice)list.get(i)).convertToJson());
/*     */         }
/*     */       }
/*  54 */       body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
/*     */     }
/*     */     catch (Exception e) {
/*  57 */       e.printStackTrace();
/*  58 */       code = 100;
/*  59 */       message = "\"system exception\"";
/*     */     }
/*  61 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  62 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/webservice/get"})
/*     */   public void getWeservice(Integer id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  71 */     String body = "\"\"";
/*  72 */     String message = "\"success\"";
/*  73 */     int code = 200;
/*     */     try {
/*  75 */       WebErrors errors = WebErrors.create(request);
/*  76 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*  77 */       if (!errors.hasErrors()) {
/*  78 */         Webservice webservice = new Webservice();
/*  79 */         if (id.intValue() != 0) {
/*  80 */           webservice = this.manager.findById(id);
/*     */         }
/*  82 */         if (webservice != null) {
/*  83 */           body = webservice.convertToJson().toString();
/*     */         } else {
/*  85 */           code = 206;
/*  86 */           message = "\"object not found\"";
/*     */         }
/*     */       } else {
/*  89 */         code = 202;
/*  90 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/*  93 */       e.printStackTrace();
/*  94 */       code = 100;
/*  95 */       message = "\"system exception\"";
/*     */     }
/*  97 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  98 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/webservice/save"})
/*     */   public void save(Webservice bean, String paramName, String defaultValue, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 108 */     String body = "\"\"";
/* 109 */     String message = "\"success\"";
/* 110 */     int code = 200;
/*     */     try {
/* 112 */       WebErrors errors = WebErrors.create(request);
/* 113 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { 
/* 114 */         bean.getAddress(), bean.getType() });
/* 115 */       if (!errors.hasErrors()) {
/* 116 */         String[] paramNames = null;
/* 117 */         String[] defaultValues = null;
/* 118 */         if (paramName != null) {
/* 119 */           String[] str = paramName.split(",");
/* 120 */           paramNames = new String[str.length];
/* 121 */           for (int i = 0; i < str.length; i++) {
/* 122 */             paramNames[i] = str[i];
/*     */           }
/*     */         }
/* 125 */         if (defaultValue != null) {
/* 126 */           String[] str1 = defaultValue.split(",");
/* 127 */           defaultValues = new String[str1.length];
/* 128 */           for (int i = 0; i < str1.length; i++) {
/* 129 */             defaultValues[i] = str1[i];
/*     */           }
/*     */         }
/* 132 */         bean = this.manager.save(bean, paramNames, defaultValues);
/*     */       } else {
/* 134 */         code = 202;
/* 135 */         message = "\"param error\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 139 */       e.printStackTrace();
/* 140 */       code = 100;
/* 141 */       message = "\"system exception\"";
/*     */     }
/* 143 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 144 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/webservice/update"})
/*     */   public void updateWebservice(Webservice bean, String paramName, String defaultValue, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 157 */     String body = "\"\"";
/* 158 */     String message = "\"success\"";
/* 159 */     int code = 200;
/*     */     try
/*     */     {
/* 162 */       WebErrors errors = WebErrors.create(request);
/* 163 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), 
/* 164 */         bean.getAddress(), bean.getType() });
/* 165 */       if (!errors.hasErrors()) {
/* 166 */         String[] paramNames = null;
/* 167 */         String[] defaultValues = null;
/* 168 */         if (paramName != null) {
/* 169 */           String[] str = paramName.split(",");
/* 170 */           paramNames = new String[str.length];
/* 171 */           for (int i = 0; i < str.length; i++) {
/* 172 */             paramNames[i] = str[i];
/*     */           }
/*     */         }
/* 175 */         if (defaultValue != null) {
/* 176 */           String[] str1 = defaultValue.split(",");
/* 177 */           defaultValues = new String[str1.length];
/* 178 */           for (int i = 0; i < str1.length; i++) {
/* 179 */             defaultValues[i] = str1[i];
/*     */           }
/*     */         }
/* 182 */         bean = this.manager.update(bean, paramNames, defaultValues);
/*     */       }
/*     */       else {
/* 185 */         code = 202;
/* 186 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 189 */       e.printStackTrace();
/* 190 */       code = 100;
/* 191 */       message = "\"system exception\"";
/*     */     }
/* 193 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 194 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/webservice/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 206 */     String body = "\"\"";
/* 207 */     String message = "\"success\"";
/* 208 */     int code = 200;
/*     */     try {
/* 210 */       WebErrors errors = WebErrors.create(request);
/* 211 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 212 */       if (!errors.hasErrors()) {
/* 213 */         Integer[] id = null;
/* 214 */         String[] str = ids.split(",");
/* 215 */         id = new Integer[str.length];
/* 216 */         for (int i = 0; i < str.length; i++) {
/* 217 */           id[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */         }
/* 219 */         this.manager.deleteByIds(id);
/*     */       } else {
/* 221 */         code = 202;
/* 222 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 225 */       ExceptionUtil.convertException(response, request, e);
/* 226 */       return;
/*     */     }
/* 228 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 229 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.WebserviceController
 * JD-Core Version:    0.6.0
 */