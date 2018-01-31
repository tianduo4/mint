/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Message;
/*     */ import com.jspgou.cms.entity.ReceiverMessage;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.ReceiverMessageMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ReceiverMessageAct
/*     */ {
/*  31 */   private static final Logger log = LoggerFactory.getLogger(ReceiverMessageAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private ReceiverMessageMng receiverMessageMng;
/*     */ 
/*  37 */   @RequestMapping({"/receiverMessage/v_list.do"})
/*     */   public String list(Integer pageNo, Integer box, HttpServletRequest request, ModelMap model) { ShopAdmin member = AdminThread.get();
/*  38 */     ShopMember Receiver = this.shopMemberMng.findUsername(member.getUsername());
/*  39 */     Pagination pagination = this.receiverMessageMng.getPage(Receiver.getId(), SimplePage.cpn(pageNo), Integer.valueOf(0), 
/*  40 */       CookieUtils.getPageSize(request));
/*  41 */     model.addAttribute("pagination", pagination);
/*  42 */     return "message/receiver";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/receiverMessage/v_read.do"})
/*     */   public String read(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  50 */     Website web = SiteUtils.getWeb(request);
/*     */ 
/*  52 */     ShopAdmin member = AdminThread.get();
/*     */ 
/*  54 */     ReceiverMessage receiver = this.receiverMessageMng.findById(id);
/*  55 */     if (receiver != null)
/*     */     {
/*  57 */       if (receiver.getMsgReceiverUser().getUsername().equals(member.getUsername())) {
/*  58 */         receiver.setMsgStatus(true);
/*  59 */         this.receiverMessageMng.update(receiver);
/*     */       }
/*  61 */       model.addAttribute("message", receiver);
/*     */     } else {
/*  63 */       ReceiverMessage msg = this.receiverMessageMng.findById(id);
/*  64 */       model.addAttribute("message", msg);
/*     */     }
/*     */ 
/*  67 */     return "message/view";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/receiverMessage/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, Integer box, HttpServletRequest request, ModelMap model) {
/*  73 */     WebErrors errors = validateDelete(ids, request);
/*  74 */     if (errors.hasErrors()) {
/*  75 */       return errors.showErrorPage(model);
/*     */     }
/*     */ 
/*  78 */     ReceiverMessage[] beans = this.receiverMessageMng.deleteByIds(ids);
/*  79 */     for (ReceiverMessage bean : beans) {
/*  80 */       log.info("delete ReceiverMessage id={}", bean.getId());
/*     */     }
/*  82 */     return list(pageNo, box, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/*  86 */     WebErrors errors = WebErrors.create(request);
/*  87 */     Website web = SiteUtils.getWeb(request);
/*  88 */     errors.ifEmpty(ids, "ids");
/*  89 */     for (Long id : ids) {
/*  90 */       vldExist(id, web.getId(), errors);
/*     */     }
/*  92 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/*  96 */     if (errors.ifNull(id, "id")) {
/*  97 */       return true;
/*     */     }
/*  99 */     ReceiverMessage entity = this.receiverMessageMng.findById(id);
/*     */ 
/* 101 */     return errors.ifNotExist(entity, Message.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ReceiverMessageAct
 * JD-Core Version:    0.6.0
 */