/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.entity.ApiAccount;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.manager.WebserviceMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.security.encoder.PwdEncoder;
/*     */ import com.jspgou.common.util.AES128Util;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class MemberController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private UserMng userMng;
/*     */ 
/*     */   @Autowired
/*     */   private WebserviceMng webserviceMng;
/*     */ 
/*     */   @Autowired
/*     */   private PwdEncoder pwdEncodee;
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng orderMng;
/*     */ 
/*     */   @RequestMapping({"/member/list"})
/*     */   public void list(String username, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  62 */     String body = "\"\"";
/*  63 */     String message = "\"success\"";
/*  64 */     int code = 200;
/*     */     try {
/*  66 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  68 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  69 */       if (errors.hasErrors()) {
/*  70 */         code = 202;
/*  71 */         message = "\"param error\"";
/*     */       } else {
/*  73 */         Pagination pagination = this.shopMemberMng.getPage(CmsThreadVariable.getSite().getId(), 
/*  74 */           SimplePage.cpn(pageNo), pageSize.intValue(), username);
/*  75 */         List members = pagination.getList();
/*  76 */         JSONArray jsons = new JSONArray();
/*  77 */         for (ShopMember member : members) {
/*  78 */           jsons.add(member.converToJson());
/*     */         }
/*  80 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/*  83 */       e.printStackTrace();
/*  84 */       code = 100;
/*  85 */       message = "\"system exception\"";
/*     */     }
/*  87 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  88 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/member/save"})
/*     */   public void save(ShopMember member, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, String username, String password, String email, Boolean disabled, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 112 */     String body = "\"\"";
/* 113 */     String message = "\"success\"";
/* 114 */     int code = 200;
/*     */     try {
/* 116 */       WebErrors errors = WebErrors.create(request);
/* 117 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { username, email, password });
/*     */ 
/* 119 */       if (errors.hasErrors()) {
/* 120 */         code = 202;
/* 121 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 124 */         ApiAccount apiAccount = CmsThreadVariable.getApiAccount();
/*     */ 
/* 126 */         password = AES128Util.decrypt(password, apiAccount.getAesKey(), apiAccount.getIvKey());
/*     */ 
/* 128 */         if (this.userMng.usernameExist(username)) {
/* 129 */           code = 304;
/* 130 */           message = "\"username exist\"";
/*     */         }
/* 133 */         else if (this.userMng.emailExist(email)) {
/* 134 */           code = 301;
/* 135 */           message = "\"email exist\"";
/*     */         }
/*     */         else {
/* 138 */           member = this.shopMemberMng.register(username, password, email, Boolean.valueOf(true), null, request
/* 139 */             .getRemoteAddr(), disabled, SiteUtils.getWebId(request), 
/* 140 */             groupId, userDegreeId, degreeId, incomeDescId, workSeniorityId, 
/* 141 */             familyMembersId, member);
/*     */ 
/* 143 */           this.webserviceMng.callWebService("false", username, password, email, member, "addUser");
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 148 */       e.printStackTrace();
/* 149 */       code = 100;
/* 150 */       message = "\"system exception\"";
/*     */     }
/* 152 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 153 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/member/get"})
/*     */   public void get(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 165 */     String body = "\"\"";
/* 166 */     String message = "\"success\"";
/* 167 */     int code = 200;
/*     */     try {
/* 169 */       WebErrors errors = WebErrors.create(request);
/* 170 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 172 */       if (errors.hasErrors()) {
/* 173 */         code = 202;
/* 174 */         message = "\"param error\"";
/*     */       } else {
/* 176 */         ShopMember member = new ShopMember();
/* 177 */         if ((id != null) && (id.longValue() != 0L)) {
/* 178 */           member = this.shopMemberMng.findById(id);
/*     */         }
/* 180 */         if (member != null) {
/* 181 */           body = member.converToJson().toString();
/*     */         } else {
/* 183 */           code = 206;
/* 184 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 188 */       e.printStackTrace();
/* 189 */       code = 100;
/* 190 */       message = "\"system exception\"";
/*     */     }
/* 192 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 193 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/member/update"})
/*     */   public void update(ShopMember member, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, String password, String email, Boolean disabled, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 212 */     String body = "\"\"";
/* 213 */     String message = "\"success\"";
/* 214 */     int code = 200;
/*     */     try {
/* 216 */       WebErrors errors = WebErrors.create(request);
/* 217 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { email, disabled });
/*     */ 
/* 219 */       if (errors.hasErrors()) {
/* 220 */         code = 202;
/* 221 */         message = "\"param error\"";
/*     */       } else {
/* 223 */         if (StringUtils.isNotBlank(password))
/*     */         {
/* 225 */           ApiAccount apiAccount = CmsThreadVariable.getApiAccount();
/*     */ 
/* 227 */           password = AES128Util.decrypt(password, apiAccount.getAesKey(), apiAccount.getIvKey());
/*     */         }
/* 229 */         ShopMember entity = this.shopMemberMng.findById(member.getId());
/*     */ 
/* 231 */         if (!entity.getEmail().equals(email))
/*     */         {
/* 233 */           if (this.userMng.emailExist(email)) {
/* 234 */             code = 301;
/* 235 */             message = "\"email exist\"";
/*     */           }
/*     */         }
/* 238 */         if (code == 200) {
/* 239 */           member = this.shopMemberMng.update(member, groupId, userDegreeId, 
/* 240 */             degreeId, incomeDescId, workSeniorityId, familyMembersId, 
/* 241 */             password, email, disabled);
/*     */ 
/* 243 */           this.webserviceMng.callWebService("false", member.getUsername(), password, email, member, "updateUser");
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 247 */       e.printStackTrace();
/* 248 */       code = 100;
/* 249 */       message = "\"system exception\"";
/*     */     }
/* 251 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 252 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/member/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 266 */     String body = "\"\"";
/* 267 */     String message = "\"success\"";
/* 268 */     int code = 200;
/*     */     try
/*     */     {
/* 271 */       if (!StringUtils.isNotBlank(ids)) {
/* 272 */         code = 202;
/* 273 */         message = "\"param error\"";
/*     */       } else {
/* 275 */         String[] str = ids.split(",");
/* 276 */         Long[] id = new Long[str.length];
/* 277 */         for (int i = 0; i < str.length; i++) {
/* 278 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 280 */         List orders = this.orderMng.findListByIds(id);
/* 281 */         if ((orders != null) && (orders.size() > 0)) {
/* 282 */           code = 205;
/* 283 */           message = "\"delete error\"";
/* 284 */           String fail = "";
/* 285 */           for (Object[] obj : orders) {
/* 286 */             fail = fail + "," + obj[0];
/*     */           }
/* 288 */           body = "\"\",\"fail\":\"您所选择的删除用户 [" + fail.substring(1) + "] 已产生订单，无法删除，请确认后操作!\"";
/*     */         } else {
/* 290 */           ShopMember[] beans = this.shopMemberMng.deleteByIds(id);
/* 291 */           for (ShopMember bean : beans)
/*     */           {
/* 293 */             Map paramsValues = new HashMap();
/* 294 */             paramsValues.put("username", bean.getUsername());
/* 295 */             paramsValues.put("admin", "true");
/* 296 */             this.webserviceMng.callWebService("deleteUser", paramsValues);
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 301 */       code = 100;
/* 302 */       message = "\"system exception\"";
/*     */     }
/* 304 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 305 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.MemberController
 * JD-Core Version:    0.6.0
 */