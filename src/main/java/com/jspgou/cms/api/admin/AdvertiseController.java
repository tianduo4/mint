/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.Advertise;
/*     */ import com.jspgou.cms.manager.AdvertiseMng;
/*     */ import com.jspgou.cms.web.RequestUtils1;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class AdvertiseController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private AdvertiseMng manager;
/*     */ 
/*     */   @RequestMapping({"/advertise/list"})
/*     */   public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  41 */     String body = "\"\"";
/*  42 */     String message = "\"success\"";
/*  43 */     int code = 200;
/*     */     try {
/*  45 */       if (pageNo == null) {
/*  46 */         pageNo = Integer.valueOf(1);
/*     */       }
/*  48 */       if (pageSize == null) {
/*  49 */         pageSize = Integer.valueOf(10);
/*     */       }
/*  51 */       Pagination page = this.manager.getPage(null, null, pageNo.intValue(), pageSize.intValue());
/*  52 */       List list = page.getList();
/*  53 */       int totalCount = page.getTotalCount();
/*  54 */       JSONArray jsonArray = new JSONArray();
/*  55 */       if ((list != null) && (list.size() > 0)) {
/*  56 */         for (int i = 0; i < list.size(); i++) {
/*  57 */           jsonArray.put(i, ((Advertise)list.get(i)).convertToJson());
/*     */         }
/*     */       }
/*  60 */       body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
/*     */     } catch (Exception e) {
/*  62 */       e.printStackTrace();
/*  63 */       code = 100;
/*  64 */       message = "\"system exception\"";
/*     */     }
/*  66 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  67 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/advertise/save"})
/*     */   public void save(Advertise bean, Integer adspaceId, HttpServletRequest request, HttpServletResponse response) {
/*  75 */     String body = "\"\"";
/*  76 */     String message = "\"success\"";
/*  77 */     int code = 200;
/*     */     try {
/*  79 */       WebErrors errors = WebErrors.create(request);
/*  80 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getName(), adspaceId, bean.getClickCount(), bean.getDisplayCount(), bean.getWeight() });
/*  81 */       if (!errors.hasErrors()) {
/*  82 */         Map attr = RequestUtils1.getRequestMap(request, "attr_");
/*     */ 
/*  84 */         Set toRemove = new HashSet();
/*  85 */         for (Map.Entry entry : attr.entrySet()) {
/*  86 */           if (StringUtils.isBlank((String)entry.getValue())) {
/*  87 */             toRemove.add((String)entry.getKey());
/*     */           }
/*     */         }
/*  90 */         for (String key : toRemove) {
/*  91 */           attr.remove(key);
/*     */         }
/*  93 */         bean = this.manager.save(bean, adspaceId, attr);
/*     */       } else {
/*  95 */         code = 202;
/*  96 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/*  99 */       e.printStackTrace();
/* 100 */       code = 100;
/* 101 */       message = "\"system exception\"";
/*     */     }
/* 103 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 104 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/advertise/get"})
/*     */   public void get(Integer id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 115 */     String body = "\"\"";
/* 116 */     String message = "\"success\"";
/* 117 */     int code = 200;
/*     */     try {
/* 119 */       WebErrors errors = WebErrors.create(request);
/* 120 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/* 121 */       if (!errors.hasErrors()) {
/* 122 */         Advertise advertise = new Advertise();
/* 123 */         if (id.intValue() != 0) {
/* 124 */           advertise = this.manager.findById(id);
/*     */         }
/* 126 */         if (advertise != null) {
/* 127 */           body = advertise.convertToJson().toString();
/*     */         }
/*     */         else {
/* 130 */           code = 206;
/* 131 */           message = "\"object not found\"";
/*     */         }
/*     */       } else {
/* 134 */         code = 202;
/* 135 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 138 */       e.printStackTrace();
/* 139 */       code = 100;
/* 140 */       message = "\"system exception\"";
/*     */     }
/* 142 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 143 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/advertise/update"})
/*     */   public void update(Advertise bean, Integer adspaceId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 152 */     String body = "\"\"";
/* 153 */     String message = "\"success\"";
/* 154 */     int code = 200;
/*     */     try {
/* 156 */       WebErrors errors = WebErrors.create(request);
/* 157 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getName(), adspaceId, bean.getClickCount(), bean.getDisplayCount(), bean.getWeight() });
/* 158 */       if (!errors.hasErrors()) {
/* 159 */         Map attr = RequestUtils1.getRequestMap(request, "attr_");
/*     */ 
/* 161 */         Set toRemove = new HashSet();
/* 162 */         for (Map.Entry entry : attr.entrySet()) {
/* 163 */           if (StringUtils.isBlank((String)entry.getValue())) {
/* 164 */             toRemove.add((String)entry.getKey());
/*     */           }
/*     */         }
/* 167 */         for (String key : toRemove) {
/* 168 */           attr.remove(key);
/*     */         }
/* 170 */         this.manager.update(bean, adspaceId, attr);
/*     */       } else {
/* 172 */         code = 202;
/* 173 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 176 */       e.printStackTrace();
/* 177 */       code = 100;
/* 178 */       message = "\"system exception\"";
/*     */     }
/* 180 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 181 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/advertise/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 193 */     String body = "\"\"";
/* 194 */     String message = "\"success\"";
/* 195 */     int code = 200;
/*     */     try {
/* 197 */       if (StringUtils.isNotBlank(ids)) {
/* 198 */         Integer[] id = null;
/* 199 */         String[] str = ids.split(",");
/* 200 */         id = new Integer[str.length];
/* 201 */         for (int i = 0; i < str.length; i++) {
/* 202 */           id[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */         }
/* 204 */         this.manager.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 207 */       ExceptionUtil.convertException(response, request, e);
/* 208 */       return;
/*     */     }
/* 210 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 211 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.AdvertiseController
 * JD-Core Version:    0.6.0
 */