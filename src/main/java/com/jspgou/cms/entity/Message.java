/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseMessage;
/*    */ import com.jspgou.common.util.DateUtils;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class Message extends BaseMessage
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Message()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Message(Long id)
/*    */   {
/* 24 */     super(id);
/*    */   }
/*    */ 
/*    */   public Message(Long id, ShopMember msgReceiverUser, ShopAdmin msgSendUser, String msgTitle, boolean msgStatus, Integer msgBox)
/*    */   {
/* 44 */     super(id, 
/* 40 */       msgReceiverUser, 
/* 41 */       msgSendUser, 
/* 42 */       msgTitle, 
/* 43 */       msgStatus, 
/* 44 */       msgBox);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */   {
/* 50 */     JSONObject json = new JSONObject();
/* 51 */     json.put("id", CommonUtils.parseId(getId()));
/* 52 */     json.put("msgTitle", CommonUtils.parseStr(getMsgTitle()));
/* 53 */     json.put("msgContent", CommonUtils.parseStr(getMsgContent()));
/* 54 */     json.put("sendTime", CommonUtils.parseDate(getSendTime(), DateUtils.COMMON_FORMAT_STR));
/* 55 */     json.put("sendUserId", getMsgSendUser() != null ? CommonUtils.parseLong(getMsgSendUser().getId()) : "");
/* 56 */     json.put("sendUserName", getMsgSendUser() != null ? CommonUtils.parseStr(getMsgSendUser().getUsername()) : "");
/* 57 */     json.put("receiverUserId", getMsgReceiverUser() != null ? CommonUtils.parseLong(getMsgReceiverUser().getId()) : "");
/* 58 */     json.put("receiverUserName", getMsgReceiverUser() != null ? CommonUtils.parseStr(getMsgReceiverUser().getUsername()) : "");
/* 59 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Message
 * JD-Core Version:    0.6.0
 */