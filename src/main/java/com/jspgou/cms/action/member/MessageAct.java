/*     */ package com.jspgou.cms.action.member;
/*     */ 
/*     */ import com.jspgou.cms.entity.Message;
/*     */ import com.jspgou.cms.entity.ReceiverMessage;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.MessageMng;
/*     */ import com.jspgou.cms.manager.ReceiverMessageMng;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.cms.web.SiteUtils;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
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
/*  46 */   private static final Logger log = LoggerFactory.getLogger(MessageAct.class);
/*     */   public static final String MESSAGE_IN_BOX_LIST = "tpl.messageInBoxLists";
/*     */   public static final String MESSAGE_TRASH_LIST = "tpl.messageTrashLists";
/*     */   public static final String MESSAGE_MNG = "tpl.messageMng";
/*     */   public static final String MESSAGE_READ = "tpl.messageRead";
/*     */ 
/*     */   @Autowired
/*     */   private MessageMng messageMng;
/*     */ 
/*     */   @Autowired
/*     */   private ReceiverMessageMng receiverMessageMng;
/*     */ 
/*     */   @RequestMapping({"/member/message_mng.jspx"})
/*     */   public String message_mng(String cl, Integer box, String msg, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  68 */     Website web = SiteUtils.getWeb(request);
/*  69 */     ShopMember member = MemberThread.get();
/*     */ 
/*  71 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*     */ 
/*  73 */     ShopFrontHelper.frontPageData(request, model);
/*     */ 
/*  75 */     if (member == null) {
/*  76 */       return "redirect:../login.jspx";
/*     */     }
/*  78 */     if (box != null)
/*  79 */       model.addAttribute("box", box);
/*     */     else {
/*  81 */       model.addAttribute("box", Integer.valueOf(0));
/*     */     }
/*  83 */     model.addAttribute("msg", msg);
/*  84 */     model.addAttribute("cl", cl);
/*  85 */     return web.getTplSys("message", MessageResolver.getMessage(request, "tpl.messageMng", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/member/message_list.jspx"})
/*     */   public String message_inbox(Integer pageNo, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 109 */     Website web = SiteUtils.getWeb(request);
/* 110 */     ShopMember member = MemberThread.get();
/*     */ 
/* 112 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*     */ 
/* 114 */     if (member == null) {
/* 115 */       return "redirect:../login.jspx";
/*     */     }
/* 117 */     Pagination pagination = null;
/* 118 */     String returnPage = "tpl.messageInBoxLists";
/* 119 */     if (box.equals(Integer.valueOf(0)))
/*     */     {
/* 121 */       pagination = this.receiverMessageMng.getPage(null, member
/* 122 */         .getId(), title, sendBeginTime, sendEndTime, status, box, 
/* 123 */         Boolean.valueOf(false), SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/* 124 */       returnPage = "tpl.messageInBoxLists";
/* 125 */     } else if (box.equals(Integer.valueOf(3)))
/*     */     {
/* 127 */       pagination = this.receiverMessageMng.getPage(member.getId(), 
/* 128 */         member.getId(), title, sendBeginTime, sendEndTime, status, 
/* 129 */         box, Boolean.valueOf(false), SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/* 130 */       returnPage = "tpl.messageTrashLists";
/*     */     }
/* 132 */     model.addAttribute("msg", request.getAttribute("msg"));
/* 133 */     model.addAttribute("pagination", pagination);
/* 134 */     model.addAttribute("pageNo", pageNo);
/* 135 */     model.addAttribute("title", title);
/* 136 */     model.addAttribute("sendBeginTime", sendBeginTime);
/* 137 */     model.addAttribute("sendEndTime", sendEndTime);
/* 138 */     model.addAttribute("status", status);
/* 139 */     model.addAttribute("box", box);
/* 140 */     return web.getTplSys("message", MessageResolver.getMessage(request, returnPage, new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/member/message_read.jspx"})
/*     */   public String message_read(Long id, Integer box, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 149 */     Website web = SiteUtils.getWeb(request);
/* 150 */     ShopMember member = MemberThread.get();
/*     */ 
/* 152 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 153 */     if (member == null) {
/* 154 */       return "redirect:../login.jspx";
/*     */     }
/* 156 */     ReceiverMessage message = this.receiverMessageMng.findById(id);
/* 157 */     if (message != null)
/*     */     {
/* 160 */       if ((!message.getMsgReceiverUser().equals(member)) && 
/* 161 */         (!message.getMsgSendUser().equals(member))) {
/* 162 */         WebErrors errors = WebErrors.create(request);
/* 163 */         errors.addErrorCode("error.noPermissionsView");
/* 164 */         return ShopFrontHelper.showError(request, response, model, errors);
/*     */       }
/*     */ 
/* 167 */       if (message.getMsgReceiverUser().equals(member)) {
/* 168 */         message.setMsgStatus(true);
/* 169 */         this.receiverMessageMng.update(message);
/* 170 */         log.info("member Message read Message success. id={}", id);
/*     */       }
/* 172 */       model.addAttribute("message", message);
/*     */     }
/*     */     else {
/* 175 */       Message msg = this.messageMng.findById(id);
/* 176 */       model.addAttribute("message", msg);
/*     */     }
/* 178 */     model.addAttribute("box", box);
/* 179 */     return web.getTplSys("message", MessageResolver.getMessage(request, "tpl.messageRead", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/member/message_trash.jspx"})
/*     */   public void message_trash(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException
/*     */   {
/* 186 */     ShopMember member = MemberThread.get();
/* 187 */     JSONObject object = new JSONObject();
/*     */ 
/* 190 */     if (member == null) {
/* 191 */       object.put("result", false);
/*     */     }
/*     */     else {
/* 194 */       for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
/* 195 */         Message message = this.messageMng.findById(ids[i.intValue()]);
/* 196 */         ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);
/* 197 */         if ((message != null) && (message.getMsgSendUser().equals(member))) {
/* 198 */           message.setMsgBox(Integer.valueOf(3));
/* 199 */           receiverMessage = new ReceiverMessage();
/* 200 */           receiverMessage.setMsgBox(Integer.valueOf(3));
/* 201 */           receiverMessage.setMsgContent(message.getMsgContent());
/* 202 */           receiverMessage.setMsgSendUser(message.getMsgSendUser());
/* 203 */           receiverMessage.setMsgReceiverUser(member);
/* 204 */           receiverMessage.setMsgStatus(message.getMsgStatus());
/* 205 */           receiverMessage.setMsgTitle(message.getMsgTitle());
/* 206 */           receiverMessage.setSendTime(message.getSendTime());
/* 207 */           receiverMessage.setMessage(null);
/*     */ 
/* 209 */           this.receiverMessageMng.save(receiverMessage);
/*     */ 
/* 211 */           Set receiverMessages = message
/* 212 */             .getReceiverMsgs();
/* 213 */           if ((receiverMessages != null) && (receiverMessages.size() > 0)) {
/* 214 */             Iterator it = receiverMessages.iterator();
/*     */ 
/* 216 */             while (it.hasNext()) {
/* 217 */               ReceiverMessage tempReceiverMessage = (ReceiverMessage)it.next();
/* 218 */               tempReceiverMessage.setMessage(null);
/* 219 */               this.receiverMessageMng.update(tempReceiverMessage);
/*     */             }
/*     */           }
/* 222 */           this.messageMng.deleteById(ids[i.intValue()]);
/*     */         }
/* 224 */         if ((receiverMessage != null) && 
/* 225 */           (receiverMessage.getMsgReceiverUser().equals(member))) {
/* 226 */           receiverMessage.setMsgBox(Integer.valueOf(3));
/* 227 */           this.receiverMessageMng.update(receiverMessage);
/*     */         }
/* 229 */         log.info("member Message trash Message success. id={}", 
/* 230 */           ids[i.intValue()]);
/*     */       }
/* 232 */       object.put("result", true);
/*     */     }
/* 234 */     ResponseUtils.renderJson(response, object.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/member/message_revert.jspx"})
/*     */   public void message_revert(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException
/*     */   {
/* 241 */     ShopMember member = MemberThread.get();
/* 242 */     JSONObject object = new JSONObject();
/*     */ 
/* 244 */     if (member == null) {
/* 245 */       object.put("result", false);
/*     */     } else {
/* 247 */       for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
/* 248 */         ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);
/*     */ 
/* 250 */         if ((receiverMessage != null) && 
/* 251 */           (receiverMessage.getMsgReceiverUser().equals(member))) {
/* 252 */           receiverMessage.setMsgBox(Integer.valueOf(0));
/* 253 */           this.receiverMessageMng.update(receiverMessage);
/*     */         }
/* 255 */         log.info("member Message revert Message success. id={}", 
/* 256 */           ids[i.intValue()]);
/*     */       }
/* 258 */       object.put("result", true);
/*     */     }
/* 260 */     ResponseUtils.renderJson(response, object.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/member/message_empty.jspx"})
/*     */   public void message_empty(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException
/*     */   {
/* 267 */     ShopMember member = MemberThread.get();
/* 268 */     JSONObject object = new JSONObject();
/*     */ 
/* 271 */     if (member == null) {
/* 272 */       object.put("result", false);
/*     */     } else {
/* 274 */       for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1))
/*     */       {
/* 276 */         ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);
/* 277 */         if ((receiverMessage != null) && 
/* 278 */           (receiverMessage.getMsgReceiverUser().equals(member))) {
/* 279 */           this.receiverMessageMng.deleteById(ids[i.intValue()]);
/*     */         }
/*     */         else {
/* 282 */           Message message = receiverMessage.getMessage();
/* 283 */           if (receiverMessage.getMsgBox().equals(Integer.valueOf(3)))
/*     */           {
/* 285 */             receiverMessage.setMessage(null);
/* 286 */             if (message != null)
/* 287 */               this.messageMng.deleteById(message.getId());
/*     */           }
/*     */           else
/*     */           {
/* 291 */             receiverMessage.setMessage(null);
/*     */           }
/* 293 */           if ((message != null) && 
/* 294 */             (message.getMsgSendUser().equals(member))) {
/* 295 */             this.messageMng.deleteById(message.getId());
/*     */           }
/*     */         }
/* 298 */         log.info("member Message empty Message success. id={}", 
/* 299 */           ids[i.intValue()]);
/*     */       }
/* 301 */       object.put("result", true);
/*     */     }
/* 303 */     ResponseUtils.renderJson(response, object.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/member/message_countUnreadMsg.jspx"})
/*     */   public void findUnreadMessagesByUser(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException
/*     */   {
/* 310 */     ShopMember member = MemberThread.get();
/* 311 */     Website web = SiteUtils.getWeb(request);
/*     */ 
/* 313 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 314 */     JSONObject object = new JSONObject();
/* 315 */     if (member == null) {
/* 316 */       object.put("result", false);
/*     */     } else {
/* 318 */       List receiverMessages = this.receiverMessageMng
/* 319 */         .getList(null, member.getId(), null, null, 
/* 320 */         null, Boolean.valueOf(false), Integer.valueOf(0), Boolean.valueOf(false));
/* 321 */       object.put("result", true);
/* 322 */       if ((receiverMessages != null) && (receiverMessages.size() > 0))
/* 323 */         object.put("count", receiverMessages.size());
/*     */       else {
/* 325 */         object.put("count", 0);
/*     */       }
/* 327 */       object.put("result", true);
/*     */     }
/* 329 */     ResponseUtils.renderJson(response, object.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/member/message_delete.jspx"})
/*     */   public String message_delete(Long[] ids, String nextUrl, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 337 */     Website web = SiteUtils.getWeb(request);
/* 338 */     ShopMember member = MemberThread.get();
/*     */ 
/* 340 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 341 */     if (member == null) {
/* 342 */       return "redirect:../login.jspx";
/*     */     }
/*     */ 
/* 345 */     Boolean permission = Boolean.valueOf(true);
/* 346 */     if ((ids != null) && (ids.length > 0)) {
/* 347 */       for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
/* 348 */         Message message = this.messageMng.findById(ids[i.intValue()]);
/*     */ 
/* 350 */         if ((message.getMsgReceiverUser().equals(member)) || 
/* 351 */           (message.getMsgSendUser().equals(member))) continue;
/* 352 */         permission = Boolean.valueOf(false);
/*     */       }
/*     */ 
/* 355 */       if (permission.booleanValue()) {
/* 356 */         this.messageMng.deleteByIds(ids);
/* 357 */         for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1))
/* 358 */           log.info("member Message delete Message success. id={}", ids[i.intValue()]);
/*     */       }
/*     */       else {
/* 361 */         WebErrors errors = WebErrors.create(request);
/* 362 */         errors.addErrorCode("error.noPermissionsView");
/* 363 */         return ShopFrontHelper.showError(request, response, model, errors);
/*     */       }
/*     */     }
/* 366 */     return ShopFrontHelper.showSuccess(request, model, nextUrl);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.member.MessageAct
 * JD-Core Version:    0.6.0
 */