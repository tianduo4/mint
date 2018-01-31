/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.entity.Message;
/*     */ import com.jspgou.cms.entity.ReceiverMessage;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.MessageMng;
/*     */ import com.jspgou.cms.manager.ReceiverMessageMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.User;
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
/*     */ public class MessageController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private MessageMng messageMng;
/*     */ 
/*     */   @Autowired
/*     */   private ReceiverMessageMng receiverMessageMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @RequestMapping({"/message/list"})
/*     */   public void list(Integer pageSize, Integer pageNo, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  51 */     String body = "\"\"";
/*  52 */     String message = "\"success\"";
/*  53 */     int code = 200;
/*     */     try {
/*  55 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  57 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  58 */       if (errors.hasErrors()) {
/*  59 */         code = 202;
/*  60 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/*  63 */         ShopAdmin admin = CmsThreadVariable.getAdminUser();
/*  64 */         Pagination pagination = this.messageMng.getPage(admin.getId(), pageNo.intValue(), pageSize.intValue());
/*  65 */         List<Message> mess =(List<Message>) pagination.getList();
/*  66 */         JSONArray jsons = new JSONArray();
/*  67 */         for (Message mes : mess) {
/*  68 */           jsons.add(mes.converToJson());
/*     */         }
/*  70 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/*  73 */       e.printStackTrace();
/*  74 */       code = 100;
/*  75 */       message = "\"system exception\"";
/*     */     }
/*  77 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  78 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/message/recycle"})
/*     */   public void recycle(Integer pageNo, Integer pageSize, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  92 */     String body = "\"\"";
/*  93 */     String message = "\"success\"";
/*  94 */     int code = 200;
/*     */     try {
/*  96 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  98 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  99 */       if (errors.hasErrors()) {
/* 100 */         code = 202;
/* 101 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 104 */         ShopAdmin admin = CmsThreadVariable.getAdminUser();
/* 105 */         Pagination pagination = this.receiverMessageMng.getPage(admin.getId(), 
/* 106 */           admin.getAdmin().getUser().getId(), title, sendBeginTime, sendEndTime, status, 
/* 107 */           Integer.valueOf(3), Boolean.valueOf(false), SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/* 108 */         List<ReceiverMessage> remess =(List<ReceiverMessage> ) pagination.getList();
/* 109 */         JSONArray jsons = new JSONArray();
/* 110 */         for (ReceiverMessage remes : remess) {
/* 111 */           jsons.add(remes.converToJson());
/*     */         }
/* 113 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/* 116 */       e.printStackTrace();
/* 117 */       code = 100;
/* 118 */       message = "\"system exception\"";
/*     */     }
/* 120 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 121 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/message/send"})
/*     */   public void send(Message mes, String username, Long groupId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 138 */     String body = "\"\"";
/* 139 */     String message = "\"success\"";
/* 140 */     int code = 200;
/*     */     try {
/* 142 */       WebErrors errors = WebErrors.create(request);
/* 143 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { mes.getMsgTitle(), mes.getMsgContent() });
/*     */ 
/* 145 */       if (errors.hasErrors()) {
/* 146 */         code = 202;
/* 147 */         message = "\"param error\"";
/*     */       } else {
/* 149 */         ShopAdmin admin = CmsThreadVariable.getAdminUser();
/* 150 */         this.messageMng.send(mes, admin, username, groupId);
/*     */       }
/*     */     } catch (Exception e) {
/* 153 */       e.printStackTrace();
/* 154 */       code = 100;
/* 155 */       message = "\"system exception\"";
/*     */     }
/* 157 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 158 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/message/read"})
/*     */   public void read(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 170 */     String body = "\"\"";
/* 171 */     String message = "\"success\"";
/* 172 */     int code = 200;
/*     */     try {
/* 174 */       WebErrors errors = WebErrors.create(request);
/* 175 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 177 */       if (errors.hasErrors()) {
/* 178 */         code = 202;
/* 179 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 182 */         ShopAdmin admin = CmsThreadVariable.getAdminUser();
/*     */ 
/* 184 */         Message mes = this.messageMng.findById(id);
/* 185 */         ReceiverMessage receiverMessage = this.receiverMessageMng.findById(id);
/* 186 */         if (mes != null)
/*     */         {
/* 189 */           if (mes.getMsgSendUser().equals(admin)) {
/* 190 */             mes.setMsgStatus(true);
/* 191 */             this.messageMng.update(mes);
/*     */           }
/* 193 */           body = mes.converToJson().toString();
/* 194 */         } else if (receiverMessage != null)
/*     */         {
/* 197 */           if ((!receiverMessage.getMsgReceiverUser().getUsername().equals(admin.getUsername())) && 
/* 198 */             (!receiverMessage.getMsgSendUser().getUsername().equals(admin.getUsername()))) {
/* 199 */             code = 500;
/* 200 */             message = "\"not perms reading\"";
/* 201 */             ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 202 */             ResponseUtils.renderApiJson(response, request, apiResponse);
/* 203 */             return;
/*     */           }
/*     */ 
/* 206 */           if (receiverMessage.getMsgReceiverUser().getUsername().equals(admin.getUsername())) {
/* 207 */             receiverMessage.setMsgStatus(true);
/* 208 */             this.receiverMessageMng.update(receiverMessage);
/*     */           }
/* 210 */           body = receiverMessage.converToJson().toString();
/*     */         }
/*     */         else {
/* 213 */           mes = this.messageMng.findById(id);
/* 214 */           body = mes.converToJson().toString();
/*     */         }
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
/*     */   @RequestMapping({"/message/trash"})
/*     */   public void trash(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 235 */     String body = "\"\"";
/* 236 */     String message = "\"success\"";
/* 237 */     int code = 200;
/*     */     try
/*     */     {
/* 240 */       if (!StringUtils.isNotBlank(ids)) {
/* 241 */         code = 202;
/* 242 */         message = "\"param error\"";
/*     */       } else {
/* 244 */         String[] str = ids.split(",");
/* 245 */         Long[] id = new Long[str.length];
/* 246 */         for (int i = 0; i < str.length; i++) {
/* 247 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/*     */ 
/* 250 */         ShopAdmin admin = CmsThreadVariable.getAdminUser();
/* 251 */         this.messageMng.trash(admin, id);
/*     */       }
/*     */     } catch (Exception e) {
/* 254 */       e.printStackTrace();
/* 255 */       code = 100;
/* 256 */       message = "\"system exception\"";
/*     */     }
/* 258 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 259 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/message/revert"})
/*     */   public void revert(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 272 */     String body = "\"\"";
/* 273 */     String message = "\"success\"";
/* 274 */     int code = 200;
/*     */     try
/*     */     {
/* 277 */       if (!StringUtils.isNotBlank(ids)) {
/* 278 */         code = 202;
/* 279 */         message = "\"param error\"";
/*     */       } else {
/* 281 */         String[] str = ids.split(",");
/* 282 */         Long[] id = new Long[str.length];
/* 283 */         for (int i = 0; i < str.length; i++) {
/* 284 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/*     */ 
/* 287 */         ShopAdmin admin = CmsThreadVariable.getAdminUser();
/* 288 */         this.messageMng.revert(admin, id);
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
/*     */   @RequestMapping({"/message/clean"})
/*     */   public void clean(String ids, HttpServletRequest request, HttpServletResponse response)
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
/*     */ 
/* 323 */         ShopAdmin admin = CmsThreadVariable.getAdminUser();
/* 324 */         this.messageMng.clean(admin, id);
/*     */       }
/*     */     } catch (Exception e) {
/* 327 */       e.printStackTrace();
/* 328 */       code = 100;
/* 329 */       message = "\"system exception\"";
/*     */     }
/* 331 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 332 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/message/checkUsername"})
/*     */   public void checkUsername(String username, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 343 */     String body = "\"\"";
/* 344 */     String message = "\"Invalid recipient user or user not exist\"";
/* 345 */     int code = 501;
/*     */     try
/*     */     {
/* 348 */       WebErrors errors = WebErrors.create(request);
/* 349 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { username });
/* 350 */       if (errors.hasErrors()) {
/* 351 */         code = 202;
/* 352 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 355 */         ShopAdmin admin = CmsThreadVariable.getAdminUser();
/* 356 */         ShopMember user = this.shopMemberMng.findUsername(username);
/* 357 */         if ((user != null) && 
/* 358 */           (!admin.getUsername().equals(user.getUsername()))) {
/* 359 */           code = 200;
/* 360 */           message = "\"success\"";
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 365 */       e.printStackTrace();
/* 366 */       code = 100;
/* 367 */       message = "\"system exception\"";
/*     */     }
/* 369 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 370 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.MessageController
 * JD-Core Version:    0.6.0
 */