/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.entity.ApiAccount;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.manager.ShopAdminMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.cms.web.CmsUtils;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.security.encoder.PwdEncoder;
/*     */ import com.jspgou.common.util.AES128Util;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.Global;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.GlobalMng;
/*     */ import com.jspgou.core.manager.RoleMng;
/*     */ import com.jspgou.core.manager.UserMng;
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
/*     */ public class AdminController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ShopAdminMng shopAdminMng;
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   protected RoleMng roleMng;
/*     */ 
/*     */   @Autowired
/*     */   private PwdEncoder pwdEncodee;
/*     */ 
/*     */   @Autowired
/*     */   private GlobalMng globalMng;
/*     */ 
/*     */   @RequestMapping({"/admin/list"})
/*     */   public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  60 */     String body = "\"\"";
/*  61 */     String message = "\"success\"";
/*  62 */     int code = 200;
/*     */     try {
/*  64 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  66 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  67 */       if (errors.hasErrors()) {
/*  68 */         code = 202;
/*  69 */         message = "\"param error\"";
/*     */       } else {
/*  71 */         Pagination pagination = this.shopAdminMng.getPage(CmsThreadVariable.getSite().getId(), SimplePage.cpn(pageNo), pageSize.intValue());
/*  72 */         List<ShopAdmin> admins = (List<ShopAdmin>)pagination.getList();
/*  73 */         JSONArray jsons = new JSONArray();
/*  74 */         for (ShopAdmin admin : admins) {
/*  75 */           jsons.add(admin.converToJson());
/*     */         }
/*  77 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/*  80 */       e.printStackTrace();
/*  81 */       code = 100;
/*  82 */       message = "\"system exception\"";
/*     */     }
/*  84 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  85 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/admin/updpwd"})
/*     */   public void updatePwd(String oldPassword, String password, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  99 */     String body = "\"\"";
/* 100 */     String message = "\"success\"";
/* 101 */     int code = 200;
/*     */     try {
/* 103 */       WebErrors errors = WebErrors.create(request);
/* 104 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { oldPassword, password });
/*     */ 
/* 106 */       if (errors.hasErrors()) {
/* 107 */         code = 202;
/* 108 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 111 */         ApiAccount apiAccount = CmsThreadVariable.getApiAccount();
/*     */ 
/* 113 */         ShopAdmin shopAdmin = CmsThreadVariable.getAdminUser();
/*     */ 
/* 115 */         oldPassword = AES128Util.decrypt(oldPassword, apiAccount.getAesKey(), apiAccount.getIvKey());
/* 116 */         if (this.pwdEncodee.isPasswordValid(shopAdmin.getAdmin().getUser().getPassword(), oldPassword))
/*     */         {
/* 118 */           password = AES128Util.decrypt(password, apiAccount.getAesKey(), apiAccount.getIvKey());
/* 119 */           this.userMng.updateUser(shopAdmin.getAdmin().getUser().getId(), password, null);
/*     */         } else {
/* 121 */           code = 303;
/* 122 */           message = "\"password error\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 126 */       e.printStackTrace();
/* 127 */       code = 100;
/* 128 */       message = "\"system exception\"";
/*     */     }
/* 130 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 131 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/admin/save"})
/*     */   public void save(ShopAdmin shopAdmin, String username, String password, Boolean viewonlyAdmin, String email, Boolean disabled, String roleIds, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 150 */     String body = "\"\"";
/* 151 */     String message = "\"success\"";
/* 152 */     int code = 200;
/*     */     try {
/* 154 */       WebErrors errors = WebErrors.create(request);
/* 155 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { username, password, viewonlyAdmin, email, disabled });
/*     */ 
/* 157 */       if (errors.hasErrors()) {
/* 158 */         code = 202;
/* 159 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 162 */         ApiAccount apiAccount = CmsThreadVariable.getApiAccount();
/*     */ 
/* 164 */         password = AES128Util.decrypt(password, apiAccount.getAesKey(), apiAccount.getIvKey());
/*     */ 
/* 166 */         if (this.userMng.usernameExist(username)) {
/* 167 */           code = 304;
/* 168 */           message = "\"username exist\"";
/*     */         }
/* 171 */         else if (this.userMng.emailExist(email)) {
/* 172 */           code = 301;
/* 173 */           message = "\"email exist\"";
/*     */         } else {
/* 175 */           Long siteId = CmsUtils.getWebsiteId(request);
/* 176 */           Integer[] ids = null;
/* 177 */           if (StringUtils.isNotBlank(roleIds)) {
/* 178 */             String[] str = roleIds.split(",");
/* 179 */             ids = new Integer[str.length];
/* 180 */             for (int i = 0; i < str.length; i++) {
/* 181 */               ids[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */             }
/*     */           }
/* 184 */           this.shopAdminMng.register(username, password, viewonlyAdmin, email, request.getRemoteAddr(), disabled, siteId, ids, shopAdmin);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 189 */       e.printStackTrace();
/* 190 */       code = 100;
/* 191 */       message = "\"system exception\"";
/*     */     }
/* 193 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 194 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/admin/get"})
/*     */   public void get(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 206 */     String body = "\"\"";
/* 207 */     String message = "\"success\"";
/* 208 */     int code = 200;
/*     */     try {
/* 210 */       WebErrors errors = WebErrors.create(request);
/* 211 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 213 */       if (errors.hasErrors()) {
/* 214 */         code = 202;
/* 215 */         message = "\"param error\"";
/*     */       } else {
/* 217 */         ShopAdmin admin = new ShopAdmin();
/* 218 */         if ((id != null) && (id.longValue() != 0L)) {
/* 219 */           admin = this.shopAdminMng.findById(id);
/*     */         }
/* 221 */         if (admin != null) {
/* 222 */           body = admin.converToJson().toString();
/*     */         } else {
/* 224 */           code = 206;
/* 225 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 229 */       e.printStackTrace();
/* 230 */       code = 100;
/* 231 */       message = "\"system exception\"";
/*     */     }
/* 233 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 234 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/admin/update"})
/*     */   public void update(ShopAdmin shopAdmin, String password, Boolean viewonlyAdmin, String email, Boolean disabled, String roleIds, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 252 */     String body = "\"\"";
/* 253 */     String message = "\"success\"";
/* 254 */     int code = 200;
/*     */     try {
/* 256 */       WebErrors errors = WebErrors.create(request);
/* 257 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { viewonlyAdmin, email, disabled, shopAdmin.getId() });
/*     */ 
/* 259 */       if (errors.hasErrors()) {
/* 260 */         code = 202;
/* 261 */         message = "\"param error\"";
/*     */       } else {
/* 263 */         if (StringUtils.isNotBlank(password))
/*     */         {
/* 265 */           ApiAccount apiAccount = CmsThreadVariable.getApiAccount();
/*     */ 
/* 267 */           password = AES128Util.decrypt(password, apiAccount.getAesKey(), apiAccount.getIvKey());
/*     */         }
/* 269 */         ShopAdmin entity = this.shopAdminMng.findById(shopAdmin.getId());
/*     */ 
/* 271 */         if (!entity.getEmail().equals(email))
/*     */         {
/* 273 */           if (this.userMng.emailExist(email)) {
/* 274 */             code = 301;
/* 275 */             message = "\"email exist\"";
/*     */           }
/*     */         }
/* 278 */         if (code == 200) {
/* 279 */           Integer[] ids = null;
/* 280 */           if (StringUtils.isNotBlank(roleIds)) {
/* 281 */             String[] str = roleIds.split(",");
/* 282 */             ids = new Integer[str.length];
/* 283 */             for (int i = 0; i < str.length; i++) {
/* 284 */               ids[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */             }
/*     */           }
/* 287 */           this.shopAdminMng.update(password, disabled, email, viewonlyAdmin, shopAdmin, ids);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 291 */       e.printStackTrace();
/* 292 */       code = 100;
/* 293 */       message = "\"system exception\"";
/*     */     }
/* 295 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 296 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/admin/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 308 */     String body = "\"\"";
/* 309 */     String message = "\"success\"";
/* 310 */     int code = 200;
/*     */     try
/*     */     {
/* 313 */       if (!StringUtils.isNotBlank(ids)) {
/* 314 */         code = 202;
/* 315 */         message = "\"param error\"";
/*     */       } else {
/* 317 */         String[] str = ids.split(",");
/* 318 */         Long[] id = new Long[str.length];
/* 319 */         for (int i = 0; i < str.length; i++) {
/* 320 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 322 */         this.shopAdminMng.delete(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 325 */       code = 100;
/* 326 */       message = "\"system exception\"";
/*     */     }
/* 328 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 329 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/admin/updatePassword"})
/*     */   public void updatePassword(String oldPassword, String password, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 342 */     String body = "\"\"";
/* 343 */     String message = "\"success\"";
/* 344 */     int code = 200;
/*     */     try {
/* 346 */       WebErrors errors = WebErrors.create(request);
/* 347 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { oldPassword, password });
/*     */ 
/* 349 */       if (errors.hasErrors()) {
/* 350 */         code = 202;
/* 351 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 354 */         ApiAccount apiAccount = CmsThreadVariable.getApiAccount();
/*     */ 
/* 356 */         oldPassword = AES128Util.decrypt(oldPassword, apiAccount.getAesKey(), apiAccount.getIvKey());
/* 357 */         if (this.pwdEncodee.isPasswordValid(this.globalMng.get().getPassword(), oldPassword))
/*     */         {
/* 359 */           password = AES128Util.decrypt(password, apiAccount.getAesKey(), apiAccount.getIvKey());
/* 360 */           this.globalMng.updateGlobalPwd(Long.valueOf(1L), password);
/*     */         } else {
/* 362 */           code = 303;
/* 363 */           message = "\"password error\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 367 */       e.printStackTrace();
/* 368 */       code = 100;
/* 369 */       message = "\"system exception\"";
/*     */     }
/* 371 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 372 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/admin/checkPassword"})
/*     */   public void checkPassword(String oldPassword, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 385 */     String body = "\"\"";
/* 386 */     String message = "\"success\"";
/* 387 */     int code = 200;
/*     */     try {
/* 389 */       WebErrors errors = WebErrors.create(request);
/* 390 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { oldPassword });
/*     */ 
/* 392 */       if (errors.hasErrors()) {
/* 393 */         code = 202;
/* 394 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 397 */         ApiAccount apiAccount = CmsThreadVariable.getApiAccount();
/*     */ 
/* 399 */         oldPassword = AES128Util.decrypt(oldPassword, apiAccount.getAesKey(), apiAccount.getIvKey());
/* 400 */         if (!this.pwdEncodee.isPasswordValid(this.globalMng.get().getPassword(), oldPassword)) {
/* 401 */           code = 303;
/* 402 */           message = "\"password error\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 406 */       e.printStackTrace();
/* 407 */       code = 100;
/* 408 */       message = "\"system exception\"";
/*     */     }
/* 410 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 411 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.AdminController
 * JD-Core Version:    0.6.0
 */