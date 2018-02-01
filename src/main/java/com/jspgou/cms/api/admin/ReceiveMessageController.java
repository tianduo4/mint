/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
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
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ReceiveMessageController
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
/*     */   @RequestMapping({"/receivemessage/list"})
/*     */   public void list(Integer pageSize, Integer pageNo, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  48 */     String body = "\"\"";
/*  49 */     String message = "\"success\"";
/*  50 */     int code = 200;
/*     */     try {
/*  52 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  54 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  55 */       if (errors.hasErrors()) {
/*  56 */         code = 202;
/*  57 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/*  60 */         ShopAdmin admin = CmsThreadVariable.getAdminUser();
/*  61 */         ShopMember member = this.shopMemberMng.findUsername(admin.getUsername());
/*  62 */         Pagination pagination = this.receiverMessageMng.getPage(member.getId(), SimplePage.cpn(pageNo), Integer.valueOf(0), CookieUtils.getPageSize(request));
/*  63 */         List<ReceiverMessage> remess =(List<ReceiverMessage>)pagination.getList();
/*  64 */         JSONArray jsons = new JSONArray();
/*  65 */         for (ReceiverMessage remes : remess) {
/*  66 */           jsons.add(remes.converToJson());
/*     */         }
/*  68 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/*  71 */       e.printStackTrace();
/*  72 */       code = 100;
/*  73 */       message = "\"system exception\"";
/*     */     }
/*  75 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  76 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/receivemessage/read"})
/*     */   public void read(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  88 */     String body = "\"\"";
/*  89 */     String message = "\"success\"";
/*  90 */     int code = 200;
/*     */     try {
/*  92 */       WebErrors errors = WebErrors.create(request);
/*  93 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/*  95 */       if (errors.hasErrors()) {
/*  96 */         code = 202;
/*  97 */         message = "\"param error\"";
/*     */       }
/*     */       else {
/* 100 */         ShopAdmin admin = CmsThreadVariable.getAdminUser();
/*     */ 
/* 102 */         ReceiverMessage receiver = this.receiverMessageMng.findById(id);
/* 103 */         if (receiver != null)
/*     */         {
/* 105 */           if (receiver.getMsgReceiverUser().getUsername().equals(admin.getUsername())) {
/* 106 */             receiver.setMsgStatus(true);
/* 107 */             this.receiverMessageMng.update(receiver);
/*     */           }
/*     */         }
/* 110 */         else receiver = this.receiverMessageMng.findById(id);
/*     */ 
/* 112 */         body = receiver.converToJson().toString();
/*     */       }
/*     */     } catch (Exception e) {
/* 115 */       e.printStackTrace();
/* 116 */       code = 100;
/* 117 */       message = "\"system exception\"";
/*     */     }
/* 119 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 120 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.ReceiveMessageController
 * JD-Core Version:    0.6.0
 */