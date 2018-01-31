/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.entity.Discuss;
/*     */ import com.jspgou.cms.manager.DiscussMng;
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
/*     */ public class DiscussController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private DiscussMng manager;
/*  28 */   private String product_name = "";
/*     */ 
/*     */   @RequestMapping({"/discuss/list"})
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
/*  52 */         Pagination pagination = this.manager.getPage(null, null, null, userName, productName, startTime, endTime, pageNo.intValue(), pageSize.intValue(), true);
/*  53 */         List discussList = pagination.getList();
/*  54 */         JSONArray jsons = new JSONArray();
/*  55 */         for (Discuss discuss : discussList) {
/*  56 */           jsons.add(discuss.converToJson());
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
/*     */   @RequestMapping({"/discuss/get"})
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
/*  89 */         Discuss entity = new Discuss();
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
/*     */   @RequestMapping({"/discuss/update"})
/*     */   public void update(Discuss bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 119 */     String body = "\"\"";
/* 120 */     String message = "\"success\"";
/* 121 */     int code = 200;
/*     */     try
/*     */     {
/* 124 */       WebErrors errors = WebErrors.create(request);
/* 125 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId() });
/* 126 */       if (errors.hasErrors()) {
/* 127 */         message = "\"param error\"";
/* 128 */         code = 202;
/*     */       } else {
/* 130 */         this.manager.update(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 133 */       e.printStackTrace();
/* 134 */       message = "\"system exception\"";
/* 135 */       code = 100;
/*     */     }
/* 137 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 138 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/discuss/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 150 */     String body = "\"\"";
/* 151 */     String message = "\"success\"";
/* 152 */     int code = 200;
/*     */     try
/*     */     {
/* 155 */       WebErrors errors = WebErrors.create(request);
/* 156 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 157 */       if (errors.hasErrors()) {
/* 158 */         message = "\"param error\"";
/* 159 */         code = 202;
/*     */       } else {
/* 161 */         String[] str = ids.split(",");
/* 162 */         Long[] id = new Long[str.length];
/* 163 */         for (int i = 0; i < str.length; i++) {
/* 164 */           id[i] = Long.valueOf(str[i]);
/*     */         }
/* 166 */         this.manager.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 169 */       message = "\"system exception\"";
/* 170 */       code = 100;
/*     */     }
/* 172 */     ApiResponse apiRequest = new ApiResponse(body, message, code);
/* 173 */     ResponseUtils.renderApiJson(response, request, apiRequest);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/discuss/visual/list"})
/*     */   public void getVisualList(String userName, String productName, Date startTime, Date endTime, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 191 */     String body = "\"\"";
/* 192 */     String message = "\"success\"";
/* 193 */     int code = 200;
/*     */     try
/*     */     {
/* 196 */       WebErrors errors = WebErrors.create(request);
/* 197 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/* 198 */       if (errors.hasErrors()) {
/* 199 */         message = "\"param error\"";
/* 200 */         code = 202;
/*     */       }
/*     */       else {
/* 203 */         if (StringUtils.isNotBlank(productName)) {
/* 204 */           this.product_name = productName;
/*     */         }
/*     */         else {
/* 207 */           productName = this.product_name;
/*     */         }
/* 209 */         Pagination pagination = this.manager.getPage(null, null, null, userName, productName, startTime, endTime, pageNo.intValue(), pageSize.intValue(), true);
/* 210 */         List discussList = pagination.getList();
/* 211 */         JSONArray jsons = new JSONArray();
/* 212 */         for (Discuss discuss : discussList) {
/* 213 */           jsons.add(discuss.converToJson());
/*     */         }
/* 215 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/* 218 */       e.printStackTrace();
/* 219 */       code = 100;
/* 220 */       message = "\"system exception\"";
/*     */     }
/* 222 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 223 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/discuss/visual/delete"})
/*     */   public void visualDelete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 236 */     String body = "\"\"";
/* 237 */     String message = "\"success\"";
/* 238 */     int code = 200;
/*     */     try
/*     */     {
/* 241 */       WebErrors errors = WebErrors.create(request);
/* 242 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 243 */       if (errors.hasErrors()) {
/* 244 */         message = "\"param error\"";
/* 245 */         code = 202;
/*     */       } else {
/* 247 */         String[] str = ids.split(",");
/* 248 */         Long[] id = new Long[str.length];
/* 249 */         for (int i = 0; i < str.length; i++) {
/* 250 */           id[i] = Long.valueOf(str[i]);
/*     */         }
/* 252 */         this.manager.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 255 */       message = "\"system exception\"";
/* 256 */       code = 100;
/*     */     }
/* 258 */     ApiResponse apiRequest = new ApiResponse(body, message, code);
/* 259 */     ResponseUtils.renderApiJson(response, request, apiRequest);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.DiscussController
 * JD-Core Version:    0.6.0
 */