/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Message;
/*     */ import com.jspgou.cms.entity.ReceiverMessage;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.MessageMng;
/*     */ import com.jspgou.cms.manager.ReceiverMessageMng;
/*     */ import com.jspgou.cms.manager.ShopMemberGroupMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.service.LoginSvc;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.entity.Member;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class MessageAct
/*     */ {
/*  45 */   private static final Logger log = LoggerFactory.getLogger(MessageAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private LoginSvc loginSvc;
/*     */ 
/*     */   @Autowired
/*     */   private MessageMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private MessageMng messageMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberGroupMng groupMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private ReceiverMessageMng receiverMessageMng;
/*     */ 
/*  51 */   @RequestMapping({"/message/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { ShopAdmin member = AdminThread.get();
/*  52 */     Pagination pagination = this.messageMng.getPage(member.getId(), SimplePage.cpn(pageNo), 
/*  53 */       CookieUtils.getPageSize(request));
/*     */ 
/*  55 */     model.addAttribute("pagination", pagination);
/*  56 */     return "message/list"; }
/*     */ 
/*     */   @RequestMapping({"/message/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  61 */     List groups = this.groupMng.getList();
/*  62 */     model.addAttribute("groupList", groups);
/*  63 */     return "message/add";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/message/v_trashbox.do"})
/*     */   public String trashbox(Integer pageNo, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/*  69 */     ShopAdmin member = AdminThread.get();
/*  70 */     Pagination pagination = this.receiverMessageMng.getPage(member.getId(), 
/*  71 */       member.getAdmin().getUser().getId(), title, sendBeginTime, sendEndTime, status, 
/*  72 */       Integer.valueOf(3), Boolean.valueOf(false), SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/*  73 */     model.addAttribute("pagination", pagination);
/*  74 */     model.addAttribute("msg", request.getAttribute("msg"));
/*  75 */     model.addAttribute("pageNo", pageNo);
/*  76 */     model.addAttribute("title", title);
/*  77 */     model.addAttribute("sendBeginTime", sendBeginTime);
/*  78 */     model.addAttribute("sendEndTime", sendEndTime);
/*  79 */     model.addAttribute("status", status);
/*  80 */     model.addAttribute("box", box);
/*  81 */     return "message/trashbox";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/message/v_send.do"})
/*     */   public String send(Message message, String username, Long groupId, Integer pageNo, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  91 */     Website web = SiteUtils.getWeb(request);
/*     */ 
/*  93 */     ShopAdmin member = AdminThread.get();
/*     */ 
/*  95 */     Date now = new Date();
/*  96 */     ReceiverMessage receiverMessage = new ReceiverMessage();
/*  97 */     ShopMember msgReceiverUser = null;
/*  98 */     ShopMember Receiver = null;
/*  99 */     if (StringUtils.isNotBlank(username))
/*     */     {
/* 101 */       Receiver = this.shopMemberMng.findUsername(username);
/*     */     }
/*     */ 
/* 105 */     if (Receiver != null) {
/* 106 */       messageInfoSet(message, receiverMessage, member, Receiver, now, 
/* 107 */         request);
/*     */     }
/*     */ 
/* 112 */     if ((groupId != null) && (groupId.longValue() != -1L))
/*     */     {
/* 117 */       if (groupId.longValue() == 0L)
/*     */       {
/* 119 */         List users = this.shopMemberMng.getList(null, null);
/* 120 */         if ((users != null) && (users.size() > 0)) {
/* 121 */           for (int i = 0; i < users.size(); i++) {
/* 122 */             ShopMember tempUser = (ShopMember)users.get(i);
/* 123 */             Message tempMsg = new Message();
/* 124 */             tempMsg.setMsgTitle(message.getMsgTitle());
/* 125 */             tempMsg.setMsgContent(message.getMsgContent());
/* 126 */             ReceiverMessage tempReceiverMsg = new ReceiverMessage();
/* 127 */             if (msgReceiverUser != null) {
/* 128 */               if (!tempUser.equals(msgReceiverUser))
/* 129 */                 messageInfoSet(tempMsg, tempReceiverMsg, 
/* 130 */                   member, tempUser, now, request);
/*     */             }
/*     */             else
/* 133 */               messageInfoSet(tempMsg, tempReceiverMsg, member, 
/* 134 */                 tempUser, now, request);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 140 */         List users = this.shopMemberMng.getList(null, groupId);
/* 141 */         if ((users != null) && (users.size() > 0)) {
/* 142 */           for (int i = 0; i < users.size(); i++) {
/* 143 */             ShopMember tempUser = (ShopMember)users.get(i);
/* 144 */             Message tempMsg = new Message();
/* 145 */             tempMsg.setMsgTitle(message.getMsgTitle());
/* 146 */             tempMsg.setMsgContent(message.getMsgContent());
/* 147 */             ReceiverMessage tempReceiverMsg = new ReceiverMessage();
/* 148 */             if (msgReceiverUser != null) {
/* 149 */               if (!tempUser.equals(msgReceiverUser))
/* 150 */                 messageInfoSet(tempMsg, tempReceiverMsg, 
/* 151 */                   member, tempUser, now, request);
/*     */             }
/*     */             else {
/* 154 */               messageInfoSet(tempMsg, tempReceiverMsg, member, 
/* 155 */                 tempUser, now, request);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 162 */     return list(pageNo, request, model);
/*     */   }
/*     */   @RequestMapping({"/message/v_edit.do"})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/* 167 */     WebErrors errors = validateEdit(id, request);
/* 168 */     if (errors.hasErrors()) {
/* 169 */       return errors.showErrorPage(model);
/*     */     }
/* 171 */     model.addAttribute("message", this.manager.findById(id));
/* 172 */     return "message/edit";
/*     */   }
/*     */   @RequestMapping({"/message/o_save.do"})
/*     */   public String save(Message bean, HttpServletRequest request, ModelMap model) {
/* 177 */     WebErrors errors = validateSave(bean, request);
/* 178 */     if (errors.hasErrors()) {
/* 179 */       return errors.showErrorPage(model);
/*     */     }
/* 181 */     bean = this.manager.save(bean);
/* 182 */     log.info("save Message id={}", bean.getId());
/* 183 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/message/o_update.do"})
/*     */   public String update(Message bean, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/* 193 */     bean = this.manager.update(bean);
/* 194 */     log.info("update Message id={}.", bean.getId());
/* 195 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/message/v_read.do"})
/*     */   public String read(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 216 */     Website web = SiteUtils.getWeb(request);
/* 217 */     ShopAdmin member = AdminThread.get();
/*     */ 
/* 219 */     Message message = this.messageMng.findById(id);
/* 220 */     ReceiverMessage receiverMessage = this.receiverMessageMng.findById(id);
/* 221 */     if (message != null)
/*     */     {
/* 224 */       if (message.getMsgSendUser().equals(member)) {
/* 225 */         message.setMsgStatus(true);
/* 226 */         this.messageMng.update(message);
/*     */       }
/* 228 */       model.addAttribute("message", message);
/* 229 */     } else if (receiverMessage != null)
/*     */     {
/* 232 */       if ((!receiverMessage.getMsgReceiverUser().getUsername().equals(member.getUsername())) && 
/* 233 */         (!receiverMessage.getMsgSendUser().getUsername().equals(member.getUsername()))) {
/* 234 */         WebErrors errors = WebErrors.create(request);
/* 235 */         errors.addErrorCode("error.noPermissionsView");
/* 236 */         return ShopFrontHelper.showError(request, response, model, errors);
/*     */       }
/*     */ 
/* 239 */       if (receiverMessage.getMsgReceiverUser().getUsername().equals(member.getUsername())) {
/* 240 */         receiverMessage.setMsgStatus(true);
/* 241 */         this.receiverMessageMng.update(receiverMessage);
/* 242 */         log.info("member Message read Message success. id={}", id);
/*     */       }
/* 244 */       model.addAttribute("message", receiverMessage);
/*     */     }
/*     */     else {
/* 247 */       Message msg = this.messageMng.findById(id);
/* 248 */       model.addAttribute("message", msg);
/*     */     }
/* 250 */     return "message/read";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/message/v_trash.do"})
/*     */   public void trash(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException
/*     */   {
/* 257 */     Website web = SiteUtils.getWeb(request);
/* 258 */     ShopAdmin member = AdminThread.get();
/* 259 */     JSONObject object = new JSONObject();
/*     */ 
/* 262 */     if (member == null) {
/* 263 */       object.put("result", false);
/*     */     }
/*     */     else {
/* 266 */       for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
/* 267 */         Message message = this.messageMng.findById(ids[i.intValue()]);
/* 268 */         ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);
/* 269 */         if ((message != null) && (message.getMsgSendUser().equals(member)))
/*     */         {
/* 271 */           receiverMessage = new ReceiverMessage();
/* 272 */           receiverMessage.setMsgBox(Integer.valueOf(3));
/* 273 */           receiverMessage.setMsgContent(message.getMsgContent());
/* 274 */           receiverMessage.setMsgSendUser(message.getMsgSendUser());
/* 275 */           receiverMessage.setMsgReceiverUser(message
/* 276 */             .getMsgReceiverUser());
/* 277 */           receiverMessage.setMsgStatus(message.getMsgStatus());
/* 278 */           receiverMessage.setMsgTitle(message.getMsgTitle());
/* 279 */           receiverMessage.setSendTime(message.getSendTime());
/* 280 */           receiverMessage.setMessage(null);
/*     */ 
/* 282 */           this.receiverMessageMng.save(receiverMessage);
/*     */ 
/* 284 */           Set receiverMessages = message
/* 285 */             .getReceiverMsgs();
/*     */ 
/* 287 */           if ((receiverMessages != null) && (receiverMessages.size() > 0)) {
/* 288 */             Iterator it = receiverMessages.iterator();
/* 289 */             while (it.hasNext()) {
/* 290 */               ReceiverMessage tempReceiverMessage = (ReceiverMessage)it.next();
/* 291 */               tempReceiverMessage.setMessage(null);
/* 292 */               this.receiverMessageMng.update(tempReceiverMessage);
/*     */             }
/*     */           }
/* 295 */           this.messageMng.deleteById(ids[i.intValue()]);
/*     */         }
/* 297 */         if ((receiverMessage != null) && 
/* 298 */           (receiverMessage.getMsgReceiverUser().getMember().getUsername().equals(member.getUsername()))) {
/* 299 */           receiverMessage.setMsgBox(Integer.valueOf(3));
/* 300 */           this.receiverMessageMng.update(receiverMessage);
/*     */         }
/* 302 */         log.info("member Message trash Message success. id={}", ids[i.intValue()]);
/*     */       }
/* 304 */       object.put("result", true);
/*     */     }
/* 306 */     ResponseUtils.renderJson(response, object.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/message/v_revert.do"})
/*     */   public void revert(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException
/*     */   {
/* 313 */     Website web = SiteUtils.getWeb(request);
/* 314 */     ShopAdmin member = AdminThread.get();
/* 315 */     JSONObject object = new JSONObject();
/*     */ 
/* 317 */     if (member == null) {
/* 318 */       object.put("result", false);
/*     */     } else {
/* 320 */       for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
/* 321 */         ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);
/*     */ 
/* 323 */         if ((receiverMessage != null) && (receiverMessage.getMsgSendUser().equals(member))) {
/* 324 */           receiverMessage.setMsgBox(Integer.valueOf(1));
/* 325 */           this.receiverMessageMng.update(receiverMessage);
/* 326 */           if (receiverMessage.getMsgBox().intValue() == 1) {
/* 327 */             Message message = new Message();
/* 328 */             message.setMsgBox(receiverMessage.getMsgBox());
/* 329 */             message.setId(receiverMessage.getId());
/* 330 */             message.setMsgSendUser(receiverMessage.getMsgSendUser());
/* 331 */             message.setMsgReceiverUser(receiverMessage
/* 332 */               .getMsgReceiverUser());
/* 333 */             message.setMsgStatus(false);
/* 334 */             message.setMsgTitle(receiverMessage.getTitleHtml());
/* 335 */             message.setMsgContent(receiverMessage.getContentHtml());
/* 336 */             message.setSendTime(receiverMessage.getSendTime());
/* 337 */             this.messageMng.save(message);
/*     */           }
/* 339 */           this.receiverMessageMng.deleteById(ids[i.intValue()]);
/*     */         }
/*     */ 
/* 342 */         if ((receiverMessage != null) && 
/* 343 */           (receiverMessage.getMsgReceiverUser().getUsername().equals(member.getUsername()))) {
/* 344 */           receiverMessage.setMsgBox(Integer.valueOf(0));
/* 345 */           this.receiverMessageMng.update(receiverMessage);
/*     */         }
/* 347 */         log.info("member Message revert Message success. id={}", ids[i.intValue()]);
/*     */       }
/* 349 */       object.put("result", true);
/*     */     }
/* 351 */     ResponseUtils.renderJson(response, object.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/message/v_empty.do"})
/*     */   public void empty(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException
/*     */   {
/* 358 */     Website web = SiteUtils.getWeb(request);
/* 359 */     ShopAdmin member = AdminThread.get();
/* 360 */     JSONObject object = new JSONObject();
/*     */ 
/* 363 */     if (member == null) {
/* 364 */       object.put("result", false);
/*     */     } else {
/* 366 */       for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1))
/*     */       {
/* 368 */         ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);
/*     */ 
/* 370 */         if ((receiverMessage != null) && 
/* 371 */           (receiverMessage.getMsgSendUser().equals(member))) {
/* 372 */           this.receiverMessageMng.deleteById(ids[i.intValue()]);
/* 373 */         } else if ((receiverMessage != null) && 
/* 374 */           (receiverMessage.getMsgReceiverUser().getUsername().equals(member.getUsername()))) {
/* 375 */           this.receiverMessageMng.deleteById(ids[i.intValue()]);
/*     */         }
/*     */         else {
/* 378 */           Message message = receiverMessage.getMessage();
/* 379 */           if (receiverMessage.getMsgBox().equals(Integer.valueOf(3)))
/*     */           {
/* 381 */             receiverMessage.setMessage(null);
/* 382 */             if (message != null)
/* 383 */               this.messageMng.deleteById(message.getId());
/*     */           }
/*     */           else
/*     */           {
/* 387 */             receiverMessage.setMessage(null);
/*     */           }
/* 389 */           if ((message != null) && 
/* 390 */             (message.getMsgSendUser().equals(member))) {
/* 391 */             this.messageMng.deleteById(message.getId());
/*     */           }
/*     */         }
/* 394 */         log.info("member Message empty Message success. id={}", ids[i.intValue()]);
/*     */       }
/* 396 */       object.put("result", true);
/*     */     }
/* 398 */     ResponseUtils.renderJson(response, object.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/message/v_findUser.do"})
/*     */   public void findUserByUserName(String username, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException
/*     */   {
/* 405 */     Website web = SiteUtils.getWeb(request);
/* 406 */     JSONObject object = new JSONObject();
/*     */ 
/* 408 */     ShopAdmin member = AdminThread.get();
/* 409 */     if (member == null) {
/* 410 */       object.put("result", false);
/*     */     }
/* 412 */     Boolean exist = Boolean.valueOf(this.shopMemberMng.usernameNotExist(username));
/* 413 */     ShopMember user = this.shopMemberMng.findUsername(username);
/* 414 */     if (user != null) {
/* 415 */       if (member.getUsername().equals(user.getUsername())) {
/* 416 */         object.put("result", true);
/* 417 */         object.put("frontuser", user.getUsername());
/*     */       }
/*     */     }
/* 420 */     else if (exist.booleanValue()) {
/* 421 */       object.put("result", true);
/* 422 */       object.put("exist", exist);
/*     */     }
/*     */ 
/* 425 */     ResponseUtils.renderJson(response, object.toString());
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(Message bean, HttpServletRequest request) {
/* 429 */     WebErrors errors = WebErrors.create(request);
/* 430 */     Website web = SiteUtils.getWeb(request);
/*     */ 
/* 432 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 436 */     WebErrors errors = WebErrors.create(request);
/* 437 */     Website web = SiteUtils.getWeb(request);
/* 438 */     if (vldExist(id, web.getId(), errors)) {
/* 439 */       return errors;
/*     */     }
/* 441 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(Long id, HttpServletRequest request) {
/* 445 */     WebErrors errors = WebErrors.create(request);
/* 446 */     Website web = SiteUtils.getWeb(request);
/* 447 */     if (vldExist(id, web.getId(), errors)) {
/* 448 */       return errors;
/*     */     }
/* 450 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
/* 454 */     WebErrors errors = WebErrors.create(request);
/* 455 */     Website web = SiteUtils.getWeb(request);
/* 456 */     errors.ifEmpty(ids, "ids");
/* 457 */     for (Long id : ids) {
/* 458 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 460 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 464 */     if (errors.ifNull(id, "id")) {
/* 465 */       return true;
/*     */     }
/* 467 */     Message entity = this.manager.findById(id);
/*     */ 
/* 469 */     return errors.ifNotExist(entity, Message.class, id);
/*     */   }
/*     */ 
/*     */   private void messageInfoSet(Message message, ReceiverMessage receiverMessage, ShopAdmin sendUser, ShopMember receiverUser, Date sendTime, HttpServletRequest request)
/*     */   {
/* 481 */     message.setMsgBox(Integer.valueOf(1));
/* 482 */     message.setMsgSendUser(sendUser);
/* 483 */     message.setMsgReceiverUser(receiverUser);
/* 484 */     message.setMsgStatus(false);
/* 485 */     message.setSendTime(sendTime);
/* 486 */     this.messageMng.save(message);
/* 487 */     receiverMessage.setMsgBox(Integer.valueOf(0));
/* 488 */     receiverMessage.setMsgContent(message.getMsgContent());
/* 489 */     receiverMessage.setMsgSendUser(sendUser);
/* 490 */     receiverMessage.setMsgReceiverUser(receiverUser);
/* 491 */     receiverMessage.setMsgStatus(false);
/* 492 */     receiverMessage.setMsgTitle(message.getMsgTitle());
/* 493 */     receiverMessage.setSendTime(sendTime);
/* 494 */     receiverMessage.setMessage(message);
/*     */ 
/* 496 */     this.receiverMessageMng.save(receiverMessage);
/* 497 */     log.info("member Message send Message success. id={}", message.getId());
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.MessageAct
 * JD-Core Version:    0.6.0
 */