/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.entity.Adspace;
/*     */ import com.jspgou.cms.manager.AdspaceMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
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
/*     */ public class AdsapceController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private AdspaceMng adspaceMng;
/*     */ 
/*     */   @RequestMapping({"/adspace/list"})
/*     */   public void list(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  35 */     String body = "\"\"";
/*  36 */     String message = "\"success\"";
/*  37 */     int code = 200;
/*     */     try {
/*  39 */       List list = this.adspaceMng.getList();
/*  40 */       JSONArray jsonArray = new JSONArray();
/*  41 */       if (((list != null ? 1 : 0) & (list.size() > 0 ? 1 : 0)) != 0) {
/*  42 */         for (int i = 0; i < list.size(); i++)
/*  43 */           jsonArray.put(i, ((Adspace)list.get(i)).convertToJson());
/*     */       }
/*     */       else {
/*  46 */         code = 206;
/*  47 */         message = "\"object not found\"";
/*     */       }
/*  49 */       body = jsonArray.toString();
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
/*     */   @RequestMapping({"/adspace/save"})
/*     */   public void save(Adspace bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  66 */     String body = "\"\"";
/*  67 */     String message = "\"success\"";
/*  68 */     int code = 200;
/*     */     try {
/*  70 */       WebErrors errors = WebErrors.create(request);
/*  71 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getName(), 
/*  72 */         bean.getEnabled() });
/*  73 */       if (!errors.hasErrors()) {
/*  74 */         this.adspaceMng.save(bean);
/*     */       } else {
/*  76 */         code = 202;
/*  77 */         message = "\"param error\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  81 */       e.printStackTrace();
/*  82 */       code = 100;
/*  83 */       message = "\"system exception\"";
/*     */     }
/*  85 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  86 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/adspace/get"})
/*     */   public void get(Integer id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  97 */     String body = "\"\"";
/*  98 */     String message = "\"success\"";
/*  99 */     int code = 200;
/*     */     try {
/* 101 */       WebErrors errors = WebErrors.create(request);
/* 102 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/* 103 */       if (!errors.hasErrors()) {
/* 104 */         Adspace bean = new Adspace();
/* 105 */         if (id.intValue() != 0) {
/* 106 */           bean = this.adspaceMng.findById(id);
/*     */         }
/* 108 */         body = bean.convertToJson().toString();
/*     */       } else {
/* 110 */         code = 202;
/* 111 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 114 */       e.printStackTrace();
/* 115 */       code = 100;
/* 116 */       message = "\"system exception\"";
/*     */     }
/* 118 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 119 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/adspace/update"})
/*     */   public void update(Adspace bean, HttpServletRequest request, HttpServletResponse response) {
/* 127 */     String body = "\"\"";
/* 128 */     String message = "\"success\"";
/* 129 */     int code = 200;
/*     */     try {
/* 131 */       WebErrors errors = WebErrors.create(request);
/* 132 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), bean.getName(), bean.getEnabled() });
/* 133 */       if (!errors.hasErrors()) {
/* 134 */         this.adspaceMng.updateByUpdater(bean);
/*     */       } else {
/* 136 */         code = 202;
/* 137 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 140 */       e.printStackTrace();
/* 141 */       code = 100;
/* 142 */       message = "\"system exception\"";
/*     */     }
/* 144 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 145 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/adspace/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 155 */     String body = "\"\"";
/* 156 */     String message = "\"success\"";
/* 157 */     int code = 200;
/*     */     try {
/* 159 */       if (StringUtils.isNotBlank(ids)) {
/* 160 */         Integer[] id = null;
/* 161 */         String[] str = ids.split(",");
/* 162 */         id = new Integer[str.length];
/* 163 */         for (int i = 0; i < str.length; i++) {
/* 164 */           id[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */         }
/* 166 */         this.adspaceMng.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 169 */       e.printStackTrace();
/* 170 */       code = 100;
/* 171 */       message = "\"system exception\"";
/*     */     }
/* 173 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 174 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.AdsapceController
 * JD-Core Version:    0.6.0
 */