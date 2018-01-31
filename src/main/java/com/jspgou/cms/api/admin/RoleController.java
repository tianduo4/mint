/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Role;
/*     */ import com.jspgou.core.manager.RoleMng;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class RoleController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   protected RoleMng roleMng;
/*     */ 
/*     */   @RequestMapping({"/role/list"})
/*     */   public void list(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  45 */     String body = "\"\"";
/*  46 */     String message = "\"success\"";
/*  47 */     int code = 200;
/*     */     try {
/*  49 */       List<Role> roles = this.roleMng.getList();
/*  50 */       JSONArray jsons = new JSONArray();
/*  51 */       for (Role role : roles) {
/*  52 */         jsons.add(role.converToJson());
/*     */       }
/*  54 */       body = jsons.toString();
/*     */     } catch (Exception e) {
/*  56 */       e.printStackTrace();
/*  57 */       code = 100;
/*  58 */       message = "\"system exception\"";
/*     */     }
/*  60 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  61 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/role/save"})
/*     */   public void save(Role role, String perms, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  75 */     String body = "\"\"";
/*  76 */     String message = "\"success\"";
/*  77 */     int code = 200;
/*     */     try {
/*  79 */       WebErrors errors = WebErrors.create(request);
/*  80 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { role.getName(), role.getPriority(), role.getSuper() });
/*     */ 
/*  82 */       if (errors.hasErrors()) {
/*  83 */         code = 202;
/*  84 */         message = "\"param error\"";
/*     */       } else {
/*  86 */         this.roleMng.save(role, splitPerms(role.getSuper(), perms));
/*     */       }
/*     */     } catch (Exception e) {
/*  89 */       e.printStackTrace();
/*  90 */       code = 100;
/*  91 */       message = "\"system exception\"";
/*     */     }
/*  93 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  94 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/role/get"})
/*     */   public void get(Integer id, HttpServletRequest request, HttpServletResponse response)
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
/* 117 */         Role role = new Role();
/* 118 */         if ((id != null) && (id.intValue() != 0)) {
/* 119 */           role = this.roleMng.findById(id);
/*     */         }
/* 121 */         if (role != null) {
/* 122 */           body = role.converToJson().toString();
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
/*     */   @RequestMapping({"/role/update"})
/*     */   public void update(Role role, String perms, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 147 */     String body = "\"\"";
/* 148 */     String message = "\"success\"";
/* 149 */     int code = 200;
/*     */     try {
/* 151 */       WebErrors errors = WebErrors.create(request);
/* 152 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { role.getName(), role.getPriority(), role.getSuper() });
/*     */ 
/* 154 */       if (errors.hasErrors()) {
/* 155 */         code = 202;
/* 156 */         message = "\"param error\"";
/*     */       }
/* 159 */       else if (code == 200) {
/* 160 */         this.roleMng.update(role, splitPerms(role.getSuper(), perms));
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 164 */       e.printStackTrace();
/* 165 */       code = 100;
/* 166 */       message = "\"system exception\"";
/*     */     }
/* 168 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 169 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/role/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 181 */     String body = "\"\"";
/* 182 */     String message = "\"success\"";
/* 183 */     int code = 200;
/*     */     try
/*     */     {
/* 186 */       if (!StringUtils.isNotBlank(ids)) {
/* 187 */         code = 202;
/* 188 */         message = "\"param error\"";
/*     */       } else {
/* 190 */         String[] str = ids.split(",");
/* 191 */         Integer[] id = new Integer[str.length];
/* 192 */         for (int i = 0; i < str.length; i++) {
/* 193 */           id[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */         }
/* 195 */         this.roleMng.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 198 */       ExceptionUtil.convertException(response, request, e);
/* 199 */       return;
/*     */     }
/* 201 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 202 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   private Set<String> splitPerms(Boolean isSuper, String perms)
/*     */   {
/* 207 */     Set permSet = new HashSet();
/* 208 */     if ((!isSuper.booleanValue()) && (StringUtils.isNotBlank(perms))) {
/* 209 */       String[] strs = perms.split(",");
/* 210 */       for (String str : strs) {
/* 211 */         permSet.add(str);
/*     */       }
/*     */     }
/* 214 */     return permSet;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.RoleController
 * JD-Core Version:    0.6.0
 */