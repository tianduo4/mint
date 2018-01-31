/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.ShopMemberGroup;
/*     */ import com.jspgou.cms.manager.ShopMemberGroupMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
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
/*     */ public class MemberGroupController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   protected ShopMemberGroupMng shopMemberGroupMng;
/*     */ 
/*     */   @RequestMapping({"/memberGroup/list"})
/*     */   public void list(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  37 */     String body = "\"\"";
/*  38 */     String message = "\"success\"";
/*  39 */     int code = 200;
/*     */     try {
/*  41 */       List groups = this.shopMemberGroupMng.getList();
/*  42 */       JSONArray jsons = new JSONArray();
/*  43 */       for (ShopMemberGroup group : groups) {
/*  44 */         jsons.add(group.converToJson());
/*     */       }
/*  46 */       body = jsons.toString();
/*     */     } catch (Exception e) {
/*  48 */       e.printStackTrace();
/*  49 */       code = 100;
/*  50 */       message = "\"system exception\"";
/*     */     }
/*  52 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  53 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/memberGroup/save"})
/*     */   public void save(ShopMemberGroup group, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  66 */     String body = "\"\"";
/*  67 */     String message = "\"success\"";
/*  68 */     int code = 200;
/*     */     try {
/*  70 */       WebErrors errors = WebErrors.create(request);
/*  71 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { group.getName(), group.getScore(), group.getDiscount() });
/*     */ 
/*  73 */       if (errors.hasErrors()) {
/*  74 */         code = 202;
/*  75 */         message = "\"param error\"";
/*     */       } else {
/*  77 */         group.setWebsite(CmsThreadVariable.getSite());
/*  78 */         this.shopMemberGroupMng.save(group);
/*     */       }
/*     */     } catch (Exception e) {
/*  81 */       e.printStackTrace();
/*  82 */       code = 100;
/*  83 */       message = "\"system exception\"";
/*     */     }
/*  85 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  86 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/memberGroup/get"})
/*     */   public void get(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  97 */     String body = "\"\"";
/*  98 */     String message = "\"success\"";
/*  99 */     int code = 200;
/*     */     try {
/* 101 */       WebErrors errors = WebErrors.create(request);
/* 102 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 104 */       if (errors.hasErrors()) {
/* 105 */         code = 202;
/* 106 */         message = "\"param error\"";
/*     */       } else {
/* 108 */         ShopMemberGroup group = new ShopMemberGroup();
/* 109 */         if ((id != null) && (id.longValue() != 0L)) {
/* 110 */           group = this.shopMemberGroupMng.findById(id);
/*     */         }
/* 112 */         if (group != null) {
/* 113 */           body = group.converToJson().toString();
/*     */         } else {
/* 115 */           code = 206;
/* 116 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 120 */       e.printStackTrace();
/* 121 */       code = 100;
/* 122 */       message = "\"system exception\"";
/*     */     }
/* 124 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 125 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/memberGroup/update"})
/*     */   public void update(ShopMemberGroup group, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 136 */     String body = "\"\"";
/* 137 */     String message = "\"success\"";
/* 138 */     int code = 200;
/*     */     try {
/* 140 */       WebErrors errors = WebErrors.create(request);
/* 141 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { group.getName(), group.getScore(), group.getDiscount() });
/*     */ 
/* 143 */       if (errors.hasErrors()) {
/* 144 */         code = 202;
/* 145 */         message = "\"param error\"";
/*     */       } else {
/* 147 */         group.setWebsite(CmsThreadVariable.getSite());
/* 148 */         this.shopMemberGroupMng.update(group);
/*     */       }
/*     */     } catch (Exception e) {
/* 151 */       e.printStackTrace();
/* 152 */       code = 100;
/* 153 */       message = "\"system exception\"";
/*     */     }
/* 155 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 156 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/memberGroup/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 168 */     String body = "\"\"";
/* 169 */     String message = "\"success\"";
/* 170 */     int code = 200;
/*     */     try
/*     */     {
/* 173 */       if (!StringUtils.isNotBlank(ids)) {
/* 174 */         code = 202;
/* 175 */         message = "\"param error\"";
/*     */       } else {
/* 177 */         String[] str = ids.split(",");
/* 178 */         Long[] id = new Long[str.length];
/* 179 */         for (int i = 0; i < str.length; i++) {
/* 180 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 182 */         this.shopMemberGroupMng.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 185 */       ExceptionUtil.convertException(response, request, e);
/* 186 */       return;
/*     */     }
/* 188 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 189 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.MemberGroupController
 * JD-Core Version:    0.6.0
 */