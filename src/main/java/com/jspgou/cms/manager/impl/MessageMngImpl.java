/*     */ package com.jspgou.cms.manager.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.MessageDao;
/*     */ import com.jspgou.cms.entity.Message;
/*     */ import com.jspgou.cms.entity.ReceiverMessage;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.MessageMng;
/*     */ import com.jspgou.cms.manager.ReceiverMessageMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.core.entity.Member;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class MessageMngImpl
/*     */   implements MessageMng
/*     */ {
/*     */   private MessageDao dao;
/*     */ 
/*     */   @Autowired
/*     */   private ReceiverMessageMng receiverMessageMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(Long sendMemberId, int pageNo, int pageSize)
/*     */   {
/*  30 */     Pagination page = this.dao.getPage(sendMemberId, pageNo, pageSize);
/*  31 */     return page;
/*     */   }
/*     */   @Transactional(readOnly=true)
/*     */   public Message findById(Long id) {
/*  36 */     Message entity = this.dao.findById(id);
/*  37 */     return entity;
/*     */   }
/*     */ 
/*     */   public Message save(Message bean) {
/*  41 */     this.dao.save(bean);
/*  42 */     return bean;
/*     */   }
/*     */ 
/*     */   public Message update(Message bean) {
/*  46 */     Updater updater = new Updater(bean);
/*  47 */     Message entity = this.dao.updateByUpdater(updater);
/*  48 */     return entity;
/*     */   }
/*     */ 
/*     */   public Message deleteById(Long id) {
/*  52 */     Message bean = this.dao.deleteById(id);
/*  53 */     return bean;
/*     */   }
/*     */ 
/*     */   public Message[] deleteByIds(Long[] ids) {
/*  57 */     Message[] beans = new Message[ids.length];
/*  58 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  59 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  61 */     return beans;
/*     */   }
/*     */ 
/*     */   public void send(Message message, ShopAdmin admin, String username, Long groupId) throws Exception
/*     */   {
/*  66 */     Date now = new Date();
/*  67 */     ReceiverMessage receiverMessage = new ReceiverMessage();
/*  68 */     ShopMember msgReceiverUser = null;
/*  69 */     ShopMember Receiver = null;
/*  70 */     if (StringUtils.isNotBlank(username)) {
/*  71 */       Receiver = this.shopMemberMng.findUsername(username);
/*     */     }
/*  73 */     if (Receiver != null) {
/*  74 */       messageInfoSet(message, receiverMessage, admin, Receiver, now);
/*     */     }
/*     */ 
/*  78 */     if ((groupId != null) && (groupId.longValue() != -1L))
/*     */     {
/*  83 */       if (groupId.longValue() == 0L)
/*     */       {
/*  85 */         List users = this.shopMemberMng.getList(null, null);
/*  86 */         if ((users != null) && (users.size() > 0)) {
/*  87 */           for (int i = 0; i < users.size(); i++) {
/*  88 */             ShopMember tempUser = (ShopMember)users.get(i);
/*  89 */             Message tempMsg = new Message();
/*  90 */             tempMsg.setMsgTitle(message.getMsgTitle());
/*  91 */             tempMsg.setMsgContent(message.getMsgContent());
/*  92 */             ReceiverMessage tempReceiverMsg = new ReceiverMessage();
/*  93 */             if (msgReceiverUser != null) {
/*  94 */               if (!tempUser.equals(msgReceiverUser))
/*  95 */                 messageInfoSet(tempMsg, tempReceiverMsg, admin, tempUser, now);
/*     */             }
/*     */             else
/*  98 */               messageInfoSet(tempMsg, tempReceiverMsg, admin, tempUser, now);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 104 */         List users = this.shopMemberMng.getList(null, groupId);
/* 105 */         if ((users != null) && (users.size() > 0))
/* 106 */           for (int i = 0; i < users.size(); i++) {
/* 107 */             ShopMember tempUser = (ShopMember)users.get(i);
/* 108 */             Message tempMsg = new Message();
/* 109 */             tempMsg.setMsgTitle(message.getMsgTitle());
/* 110 */             tempMsg.setMsgContent(message.getMsgContent());
/* 111 */             ReceiverMessage tempReceiverMsg = new ReceiverMessage();
/* 112 */             if (msgReceiverUser != null) {
/* 113 */               if (!tempUser.equals(msgReceiverUser))
/* 114 */                 messageInfoSet(tempMsg, tempReceiverMsg, admin, tempUser, now);
/*     */             }
/*     */             else
/* 117 */               messageInfoSet(tempMsg, tempReceiverMsg, admin, tempUser, now);
/*     */           }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void trash(ShopAdmin admin, Long[] ids)
/*     */     throws Exception
/*     */   {
/* 130 */     for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
/* 131 */       Message message = this.dao.findById(ids[i.intValue()]);
/* 132 */       ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);
/* 133 */       if ((message != null) && (message.getMsgSendUser().equals(admin)))
/*     */       {
/* 135 */         receiverMessage = new ReceiverMessage();
/* 136 */         receiverMessage.setMsgBox(Integer.valueOf(3));
/* 137 */         receiverMessage.setMsgContent(message.getMsgContent());
/* 138 */         receiverMessage.setMsgSendUser(message.getMsgSendUser());
/* 139 */         receiverMessage.setMsgReceiverUser(message
/* 140 */           .getMsgReceiverUser());
/* 141 */         receiverMessage.setMsgStatus(message.getMsgStatus());
/* 142 */         receiverMessage.setMsgTitle(message.getMsgTitle());
/* 143 */         receiverMessage.setSendTime(message.getSendTime());
/* 144 */         receiverMessage.setMessage(null);
/*     */ 
/* 146 */         this.receiverMessageMng.save(receiverMessage);
/*     */ 
/* 148 */         Set receiverMessages = message
/* 149 */           .getReceiverMsgs();
/*     */ 
/* 151 */         if ((receiverMessages != null) && (receiverMessages.size() > 0)) {
/* 152 */           Iterator it = receiverMessages.iterator();
/* 153 */           while (it.hasNext()) {
/* 154 */             ReceiverMessage tempReceiverMessage = (ReceiverMessage)it.next();
/* 155 */             tempReceiverMessage.setMessage(null);
/* 156 */             this.receiverMessageMng.update(tempReceiverMessage);
/*     */           }
/*     */         }
/* 159 */         this.dao.deleteById(ids[i.intValue()]);
/*     */       }
/* 161 */       if ((receiverMessage == null) || 
/* 162 */         (!receiverMessage.getMsgReceiverUser().getMember().getUsername().equals(admin.getUsername()))) continue;
/* 163 */       receiverMessage.setMsgBox(Integer.valueOf(3));
/* 164 */       this.receiverMessageMng.update(receiverMessage);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void revert(ShopAdmin admin, Long[] ids)
/*     */     throws Exception
/*     */   {
/* 174 */     for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
/* 175 */       ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);
/*     */ 
/* 177 */       if ((receiverMessage != null) && (receiverMessage.getMsgSendUser().equals(admin))) {
/* 178 */         receiverMessage.setMsgBox(Integer.valueOf(1));
/* 179 */         this.receiverMessageMng.update(receiverMessage);
/* 180 */         if (receiverMessage.getMsgBox().intValue() == 1) {
/* 181 */           Message message = new Message();
/* 182 */           message.setMsgBox(receiverMessage.getMsgBox());
/* 183 */           message.setId(receiverMessage.getId());
/* 184 */           message.setMsgSendUser(receiverMessage.getMsgSendUser());
/* 185 */           message.setMsgReceiverUser(receiverMessage
/* 186 */             .getMsgReceiverUser());
/* 187 */           message.setMsgStatus(false);
/* 188 */           message.setMsgTitle(receiverMessage.getTitleHtml());
/* 189 */           message.setMsgContent(receiverMessage.getContentHtml());
/* 190 */           message.setSendTime(receiverMessage.getSendTime());
/* 191 */           this.dao.save(message);
/*     */         }
/* 193 */         this.receiverMessageMng.deleteById(ids[i.intValue()]);
/*     */       }
/*     */ 
/* 196 */       if ((receiverMessage == null) || 
/* 197 */         (!receiverMessage.getMsgReceiverUser().getUsername().equals(admin.getUsername()))) continue;
/* 198 */       receiverMessage.setMsgBox(Integer.valueOf(0));
/* 199 */       this.receiverMessageMng.update(receiverMessage);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void messageInfoSet(Message message, ReceiverMessage receiverMessage, ShopAdmin sendUser, ShopMember receiverUser, Date sendTime)
/*     */   {
/* 208 */     message.setMsgBox(Integer.valueOf(1));
/* 209 */     message.setMsgSendUser(sendUser);
/* 210 */     message.setMsgReceiverUser(receiverUser);
/* 211 */     message.setMsgStatus(false);
/* 212 */     message.setSendTime(sendTime);
/* 213 */     save(message);
/* 214 */     receiverMessage.setMsgBox(Integer.valueOf(0));
/* 215 */     receiverMessage.setMsgContent(message.getMsgContent());
/* 216 */     receiverMessage.setMsgSendUser(sendUser);
/* 217 */     receiverMessage.setMsgReceiverUser(receiverUser);
/* 218 */     receiverMessage.setMsgStatus(false);
/* 219 */     receiverMessage.setMsgTitle(message.getMsgTitle());
/* 220 */     receiverMessage.setSendTime(sendTime);
/* 221 */     receiverMessage.setMessage(message);
/*     */ 
/* 223 */     this.receiverMessageMng.save(receiverMessage);
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(MessageDao dao)
/*     */   {
/* 236 */     this.dao = dao;
/*     */   }
/*     */ 
/*     */   public void clean(ShopAdmin admin, Long[] ids)
/*     */     throws Exception
/*     */   {
/* 243 */     for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1))
/*     */     {
/* 245 */       ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);
/*     */ 
/* 247 */       if ((receiverMessage != null) && 
/* 248 */         (receiverMessage.getMsgSendUser().equals(admin))) {
/* 249 */         this.receiverMessageMng.deleteById(ids[i.intValue()]);
/* 250 */       } else if ((receiverMessage != null) && 
/* 251 */         (receiverMessage.getMsgReceiverUser().getUsername().equals(admin.getUsername()))) {
/* 252 */         this.receiverMessageMng.deleteById(ids[i.intValue()]);
/*     */       }
/*     */       else {
/* 255 */         Message message = receiverMessage.getMessage();
/* 256 */         if (receiverMessage.getMsgBox().equals(Integer.valueOf(3)))
/*     */         {
/* 258 */           receiverMessage.setMessage(null);
/* 259 */           if (message != null)
/* 260 */             this.dao.deleteById(message.getId());
/*     */         }
/*     */         else
/*     */         {
/* 264 */           receiverMessage.setMessage(null);
/*     */         }
/* 266 */         if ((message != null) && (message.getMsgSendUser().equals(admin)))
/* 267 */           this.dao.deleteById(message.getId());
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.impl.MessageMngImpl
 * JD-Core Version:    0.6.0
 */