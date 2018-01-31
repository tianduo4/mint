/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseReceiverMessage;
/*    */ import com.jspgou.common.util.DateUtils;
/*    */ import com.jspgou.common.util.StrUtils;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class ReceiverMessage extends BaseReceiverMessage
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ReceiverMessage()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ReceiverMessage(Long id)
/*    */   {
/* 24 */     super(id);
/*    */   }
/*    */ 
/*    */   public ReceiverMessage(Long id, ShopMember msgReceiverUser, ShopAdmin msgSendUser, String msgTitle, boolean msgStatus, Integer msgBox)
/*    */   {
/* 44 */     super(id, 
/* 40 */       msgReceiverUser, 
/* 41 */       msgSendUser, 
/* 42 */       msgTitle, 
/* 43 */       msgStatus, 
/* 44 */       msgBox);
/*    */   }
/*    */   public String getTitleHtml() {
/* 47 */     return StrUtils.txt2htm(getMsgTitle());
/*    */   }
/*    */   public String getContentHtml() {
/* 50 */     return StrUtils.txt2htm(getMsgContent());
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */   {
/* 56 */     JSONObject json = new JSONObject();
/* 57 */     json.put("id", CommonUtils.parseId(getId()));
/* 58 */     json.put("msgTitle", CommonUtils.parseStr(getMsgTitle()));
/* 59 */     json.put("msgContent", CommonUtils.parseStr(getMsgContent()));
/* 60 */     json.put("sendTime", CommonUtils.parseDate(getSendTime(), DateUtils.COMMON_FORMAT_STR));
/* 61 */     json.put("sendUserId", getMsgSendUser() != null ? CommonUtils.parseLong(getMsgSendUser().getId()) : "");
/* 62 */     json.put("sendUserName", getMsgSendUser() != null ? CommonUtils.parseStr(getMsgSendUser().getUsername()) : "");
/* 63 */     json.put("receiverUserId", getMsgReceiverUser() != null ? CommonUtils.parseLong(getMsgReceiverUser().getId()) : "");
/* 64 */     json.put("receiverUserName", getMsgReceiverUser() != null ? CommonUtils.parseStr(getMsgReceiverUser().getUsername()) : "");
/* 65 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ReceiverMessage
 * JD-Core Version:    0.6.0
 */