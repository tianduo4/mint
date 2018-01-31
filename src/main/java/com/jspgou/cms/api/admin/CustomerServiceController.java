/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.api.ExceptionUtil;
import com.jspgou.cms.entity.CustomerService;
import com.jspgou.cms.manager.CustomerServiceMng;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.web.WebErrors;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ @Controller
/*     */ public class CustomerServiceController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private CustomerServiceMng manager;
/*     */ 
/*     */   @RequestMapping({"/customerService/list"})
/*     */   public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
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
/*  52 */         Pagination pagination = this.manager.getPagination(null, SimplePage.cpn(pageNo), pageSize.intValue());
/*  53 */         List<CustomerService> list = (List<CustomerService>)pagination.getList();
/*  54 */         JSONArray jsons = new JSONArray();
/*  55 */         for (CustomerService customerService : list) {
/*  56 */           jsons.add(customerService.converToJson());
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
/*     */   @SignValidate
/*     */   @RequestMapping({"/customerService/priority"})
/*     */   public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  81 */     String body = "\"\"";
/*  82 */     String message = "\"success\"";
/*  83 */     int code = 200;
/*     */     try
/*     */     {
/*  86 */       if ((!StringUtils.isNotBlank(ids)) || (!StringUtils.isNotBlank(priority))) {
/*  87 */         code = 202;
/*  88 */         message = "\"param error\"";
/*     */       } else {
/*  90 */         String[] str1 = ids.split(",");
/*  91 */         Long[] wids1 = new Long[str1.length];
/*  92 */         for (int i = 0; i < str1.length; i++) {
/*  93 */           wids1[i] = Long.valueOf(str1[i]);
/*     */         }
/*     */ 
/*  96 */         String[] str2 = priority.split(",");
/*  97 */         Integer[] priority1 = new Integer[str2.length];
/*  98 */         for (int i = 0; i < str2.length; i++) {
/*  99 */           priority1[i] = Integer.valueOf(str2[i]);
/*     */         }
/* 101 */         this.manager.updatePriority(wids1, priority1);
/*     */       }
/*     */     } catch (Exception e) {
/* 104 */       e.printStackTrace();
/* 105 */       code = 100;
/* 106 */       message = "\"system exception\"";
/*     */     }
/* 108 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 109 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/customerService/save"})
/*     */   public void save(CustomerService bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 121 */     String body = "\"\"";
/* 122 */     String message = "\"success\"";
/* 123 */     int code = 200;
/*     */     try {
/* 125 */       WebErrors errors = WebErrors.create(request);
/* 126 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getName(), 
/* 127 */         bean.getContent(), bean.getType(), bean.getPriority(), bean.getDisable() });
/*     */ 
/* 129 */       if (errors.hasErrors()) {
/* 130 */         code = 202;
/* 131 */         message = "\"param error\"";
/*     */       } else {
/* 133 */         this.manager.save(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 136 */       e.printStackTrace();
/* 137 */       code = 100;
/* 138 */       message = "\"system exception\"";
/*     */     }
/* 140 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 141 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/customerService/get"})
/*     */   public void get(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 151 */     String body = "\"\"";
/* 152 */     String message = "\"success\"";
/* 153 */     int code = 200;
/*     */     try {
/* 155 */       WebErrors errors = WebErrors.create(request);
/* 156 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 158 */       if (errors.hasErrors()) {
/* 159 */         code = 202;
/* 160 */         message = "\"param error\"";
/*     */       } else {
/* 162 */         CustomerService customerService = new CustomerService();
/* 163 */         if ((id != null) && (id.longValue() != 0L)) {
/* 164 */           customerService = this.manager.findById(id);
/*     */         }
/* 166 */         if (customerService != null) {
/* 167 */           body = customerService.converToJson().toString();
/*     */         } else {
/* 169 */           code = 206;
/* 170 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 174 */       e.printStackTrace();
/* 175 */       code = 100;
/* 176 */       message = "\"system exception\"";
/*     */     }
/* 178 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 179 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/customerService/update"})
/*     */   public void update(CustomerService bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 190 */     String body = "\"\"";
/* 191 */     String message = "\"success\"";
/* 192 */     int code = 200;
/*     */     try {
/* 194 */       WebErrors errors = WebErrors.create(request);
/* 195 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), bean.getName(), bean.getPriority(), 
/* 196 */         bean.getContent(), bean.getType(), bean.getDisable() });
/*     */ 
/* 198 */       if (errors.hasErrors()) {
/* 199 */         code = 202;
/* 200 */         message = "\"param error\"";
/*     */       } else {
/* 202 */         this.manager.update(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 205 */       e.printStackTrace();
/* 206 */       code = 100;
/* 207 */       message = "\"system exception\"";
/*     */     }
/* 209 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 210 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/customerService/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 222 */     String body = "\"\"";
/* 223 */     String message = "\"success\"";
/* 224 */     int code = 200;
/*     */     try
/*     */     {
/* 228 */       if (!StringUtils.isNotBlank(ids)) {
/* 229 */         code = 202;
/* 230 */         message = "\"param error\"";
/*     */       } else {
/* 232 */         String[] str = ids.split(",");
/* 233 */         Long[] id = new Long[str.length];
/* 234 */         for (int i = 0; i < str.length; i++) {
/* 235 */           id[i] = Long.valueOf(str[i]);
/*     */         }
/* 237 */         this.manager.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 240 */       e.printStackTrace();
/* 241 */       ExceptionUtil.convertException(response, request, e);
/* 242 */       return;
/*     */     }
/* 244 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 245 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.CustomerServiceController
 * JD-Core Version:    0.6.0
 */