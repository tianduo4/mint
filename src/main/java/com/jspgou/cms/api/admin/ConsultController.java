/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.entity.Consult;
/*     */ import com.jspgou.cms.manager.ConsultMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.Date;
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
/*     */ public class ConsultController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ConsultMng manager;
/*     */   private String product_name;
/*     */ 
/*     */   @RequestMapping({"/consult/list"})
/*     */   public void list(String userName, String productName, Date startTime, Date endTime, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
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
/*  52 */         Pagination pagination = this.manager.getPage(null, userName, productName, startTime, endTime, pageNo.intValue(), pageSize.intValue(), Boolean.valueOf(true));
/*  53 */         List<Consult> consults = (List<Consult>)pagination.getList();
/*  54 */         JSONArray jsons = new JSONArray();
/*  55 */         for (Consult consult : consults) {
/*  56 */           jsons.add(consult.converToJson());
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
/*     */   @RequestMapping({"/consult/get"})
/*     */   public void get(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  77 */     String body = "\"\"";
/*  78 */     String message = "\"success\"";
/*  79 */     int code = 200;
/*     */     try
/*     */     {
/*  82 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  84 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*  85 */       if (errors.hasErrors()) {
/*  86 */         message = "\"param error\"";
/*  87 */         code = 202;
/*     */       } else {
/*  89 */         Consult entity = new Consult();
/*  90 */         if ((id != null) && (id.longValue() != 0L)) {
/*  91 */           entity = this.manager.findById(id);
/*     */         }
/*  93 */         if (entity != null) {
/*  94 */           body = entity.converToJson().toString();
/*     */         } else {
/*  96 */           code = 206;
/*  97 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 102 */       e.printStackTrace();
/* 103 */       code = 100;
/* 104 */       message = "\"system exception\"";
/*     */     }
/*     */ 
/* 107 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 108 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/consult/update"})
/*     */   public void update(Long id, String adminReplay, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 121 */     String body = "\"\"";
/* 122 */     String message = "\"success\"";
/* 123 */     int code = 200;
/*     */     try
/*     */     {
/* 126 */       WebErrors errors = WebErrors.create(request);
/* 127 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id, adminReplay });
/* 128 */       if (errors.hasErrors()) {
/* 129 */         message = "\"param error\"";
/* 130 */         code = 202;
/*     */       } else {
/* 132 */         Consult entity = this.manager.findById(id);
/* 133 */         if (entity != null) {
/* 134 */           entity.setAdminReplay(adminReplay);
/* 135 */           this.manager.update(entity);
/*     */         } else {
/* 137 */           message = "\"object not found\"";
/* 138 */           code = 206;
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 142 */       e.printStackTrace();
/* 143 */       message = "\"system exception\"";
/* 144 */       code = 100;
/*     */     }
/* 146 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 147 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/consult/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 159 */     String body = "\"\"";
/* 160 */     String message = "\"success\"";
/* 161 */     int code = 200;
/*     */     try
/*     */     {
/* 164 */       WebErrors errors = WebErrors.create(request);
/* 165 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 166 */       if (errors.hasErrors()) {
/* 167 */         message = "\"param error\"";
/* 168 */         code = 202;
/*     */       } else {
/* 170 */         String[] str = ids.split(",");
/* 171 */         Long[] id = new Long[str.length];
/* 172 */         for (int i = 0; i < str.length; i++) {
/* 173 */           id[i] = Long.valueOf(str[i]);
/*     */         }
/* 175 */         this.manager.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 178 */       message = "\"system exception\"";
/* 179 */       code = 100;
/*     */     }
/* 181 */     ApiResponse apiRequest = new ApiResponse(body, message, code);
/* 182 */     ResponseUtils.renderApiJson(response, request, apiRequest);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/consult/visual/list"})
/*     */   public void getVisualList(String userName, String productName, Date startTime, Date endTime, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 200 */     String body = "\"\"";
/* 201 */     String message = "\"success\"";
/* 202 */     int code = 200;
/*     */     try
/*     */     {
/* 205 */       WebErrors errors = WebErrors.create(request);
/* 206 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/* 207 */       if (errors.hasErrors()) {
/* 208 */         message = "\"param error\"";
/* 209 */         code = 202;
/*     */       }
/*     */       else {
/* 212 */         if (StringUtils.isNotBlank(productName)) {
/* 213 */           this.product_name = productName;
/*     */         }
/*     */         else {
/* 216 */           productName = this.product_name;
/*     */         }
/* 218 */         Pagination pagination = this.manager.getPage(null, userName, productName, startTime, endTime, pageNo.intValue(), pageSize.intValue(), Boolean.valueOf(true));
/* 219 */         List<Consult> consults =(List<Consult>) pagination.getList();
/* 220 */         JSONArray jsons = new JSONArray();
/* 221 */         for (Consult consult : consults) {
/* 222 */           jsons.add(consult.converToJson());
/*     */         }
/* 224 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/* 227 */       e.printStackTrace();
/* 228 */       code = 100;
/* 229 */       message = "\"system exception\"";
/*     */     }
/* 231 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 232 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/consult/visual/delete"})
/*     */   public void visualDelete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 246 */     String body = "\"\"";
/* 247 */     String message = "\"success\"";
/* 248 */     int code = 200;
/*     */     try
/*     */     {
/* 251 */       WebErrors errors = WebErrors.create(request);
/* 252 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 253 */       if (errors.hasErrors()) {
/* 254 */         message = "\"param error\"";
/* 255 */         code = 202;
/*     */       } else {
/* 257 */         String[] str = ids.split(",");
/* 258 */         Long[] id = new Long[str.length];
/* 259 */         for (int i = 0; i < str.length; i++) {
/* 260 */           id[i] = Long.valueOf(str[i]);
/*     */         }
/* 262 */         this.manager.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 265 */       message = "\"system exception\"";
/* 266 */       code = 100;
/*     */     }
/* 268 */     ApiResponse apiRequest = new ApiResponse(body, message, code);
/* 269 */     ResponseUtils.renderApiJson(response, request, apiRequest);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.ConsultController
 * JD-Core Version:    0.6.0
 */