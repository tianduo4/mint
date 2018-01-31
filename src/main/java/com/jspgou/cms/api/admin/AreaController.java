/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.Address;
/*     */ import com.jspgou.cms.manager.AddressMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
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
/*     */ public class AreaController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private AddressMng addressMng;
/*     */ 
/*     */   @RequestMapping({"/area/list"})
/*     */   public void list(Long parentId, Integer pageSize, Integer pageNo, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  41 */     String body = "\"\"";
/*  42 */     String message = "\"success\"";
/*  43 */     int code = 200;
/*     */     try {
/*  45 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  47 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  48 */       if (errors.hasErrors()) {
/*  49 */         code = 202;
/*  50 */         message = "\"param error\"";
/*     */       } else {
/*  52 */         Pagination pagination = this.addressMng.getPageByParentId(parentId, pageNo.intValue(), pageSize.intValue());
/*  53 */         List areas = pagination.getList();
/*  54 */         JSONArray jsons = new JSONArray();
/*  55 */         for (Address area : areas) {
/*  56 */           jsons.add(area.converToJson());
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
/*     */   @RequestMapping({"/area/save"})
/*     */   public void save(Address area, Long parentId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  80 */     String body = "\"\"";
/*  81 */     String message = "\"success\"";
/*  82 */     int code = 200;
/*     */     try {
/*  84 */       WebErrors errors = WebErrors.create(request);
/*  85 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { area.getName() });
/*     */ 
/*  87 */       if (errors.hasErrors()) {
/*  88 */         code = 202;
/*  89 */         message = "\"param error\"";
/*     */       } else {
/*  91 */         if (parentId != null) {
/*  92 */           Address address = this.addressMng.findById(parentId);
/*  93 */           area.setParent(address);
/*     */         }
/*  95 */         this.addressMng.save(area);
/*     */       }
/*     */     } catch (Exception e) {
/*  98 */       e.printStackTrace();
/*  99 */       code = 100;
/* 100 */       message = "\"system exception\"";
/*     */     }
/* 102 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 103 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/area/get"})
/*     */   public void get(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 114 */     String body = "\"\"";
/* 115 */     String message = "\"success\"";
/* 116 */     int code = 200;
/*     */     try {
/* 118 */       WebErrors errors = WebErrors.create(request);
/* 119 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 121 */       if (errors.hasErrors()) {
/* 122 */         code = 202;
/* 123 */         message = "\"param error\"";
/*     */       } else {
/* 125 */         Address area = new Address();
/* 126 */         if ((id != null) && (id.longValue() != 0L)) {
/* 127 */           area = this.addressMng.findById(id);
/*     */         }
/* 129 */         if (area != null) {
/* 130 */           body = area.converToJson().toString();
/*     */         } else {
/* 132 */           code = 206;
/* 133 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 137 */       e.printStackTrace();
/* 138 */       code = 100;
/* 139 */       message = "\"system exception\"";
/*     */     }
/* 141 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 142 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/area/update"})
/*     */   public void update(Address area, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 155 */     String body = "\"\"";
/* 156 */     String message = "\"success\"";
/* 157 */     int code = 200;
/*     */     try {
/* 159 */       WebErrors errors = WebErrors.create(request);
/* 160 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { area.getId(), area.getName() });
/*     */ 
/* 162 */       if (errors.hasErrors()) {
/* 163 */         code = 202;
/* 164 */         message = "\"param error\"";
/*     */       } else {
/* 166 */         this.addressMng.update(area);
/*     */       }
/*     */     } catch (Exception e) {
/* 169 */       e.printStackTrace();
/* 170 */       code = 100;
/* 171 */       message = "\"system exception\"";
/*     */     }
/* 173 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 174 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/area/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 186 */     String body = "\"\"";
/* 187 */     String message = "\"success\"";
/* 188 */     int code = 200;
/*     */     try
/*     */     {
/* 191 */       if (!StringUtils.isNotBlank(ids)) {
/* 192 */         code = 202;
/* 193 */         message = "\"param error\"";
/*     */       } else {
/* 195 */         String[] str = ids.split(",");
/* 196 */         Long[] id = new Long[str.length];
/* 197 */         for (int i = 0; i < str.length; i++) {
/* 198 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 200 */         this.addressMng.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 203 */       ExceptionUtil.convertException(response, request, e);
/* 204 */       return;
/*     */     }
/* 206 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 207 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/area/updateSort"})
/*     */   public void updateSort(String ids, String prioritys, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 221 */     String body = "\"\"";
/* 222 */     String message = "\"success\"";
/* 223 */     int code = 200;
/*     */     try {
/* 225 */       WebErrors errors = WebErrors.create(request);
/* 226 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids, prioritys });
/*     */ 
/* 228 */       if (errors.hasErrors()) {
/* 229 */         code = 202;
/* 230 */         message = "\"param error\"";
/*     */       } else {
/* 232 */         String[] str = ids.split(",");
/* 233 */         String[] pro = prioritys.split(",");
/* 234 */         Long[] id = new Long[str.length];
/* 235 */         Integer[] priority = new Integer[pro.length];
/* 236 */         for (int i = 0; i < str.length; i++) {
/* 237 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/* 238 */           priority[i] = Integer.valueOf(Integer.parseInt(pro[i]));
/*     */         }
/* 240 */         this.addressMng.updatePriority(id, priority);
/*     */       }
/*     */     }
/*     */     catch (IndexOutOfBoundsException e) {
/* 244 */       code = 202;
/* 245 */       message = "\"param error\"";
/*     */     } catch (Exception e) {
/* 247 */       e.printStackTrace();
/* 248 */       code = 100;
/* 249 */       message = "\"system exception\"";
/*     */     }
/* 251 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 252 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.AreaController
 * JD-Core Version:    0.6.0
 */