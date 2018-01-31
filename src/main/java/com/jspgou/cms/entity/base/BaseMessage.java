/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Message;
/*     */ import com.jspgou.cms.entity.ReceiverMessage;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseMessage
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "Message";
/*  18 */   public static String PROP_MSG_STATUS = "msgStatus";
/*  19 */   public static String PROP_MSG_SEND_USER = "msgSendUser";
/*  20 */   public static String PROP_MSG_CONTENT = "msgContent";
/*  21 */   public static String PROP_MSG_BOX = "msgBox";
/*  22 */   public static String PROP_SEND_TIME = "sendTime";
/*  23 */   public static String PROP_ID = "id";
/*  24 */   public static String PROP_MSG_RECEIVER_USER = "msgReceiverUser";
/*  25 */   public static String PROP_MSG_TITLE = "msgTitle";
/*     */ 
/*  65 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String msgTitle;
/*     */   private String msgContent;
/*     */   private Date sendTime;
/*     */   private boolean msgStatus;
/*     */   private Integer msgBox;
/*     */   private ShopMember msgReceiverUser;
/*     */   private ShopAdmin msgSendUser;
/*     */   private Set<ReceiverMessage> receiverMsgs;
/*     */ 
/*     */   public BaseMessage()
/*     */   {
/*  30 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseMessage(Long id)
/*     */   {
/*  37 */     setId(id);
/*  38 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseMessage(Long id, ShopMember msgReceiverUser, ShopAdmin msgSendUser, String msgTitle, boolean msgStatus, Integer msgBox)
/*     */   {
/*  52 */     setId(id);
/*  53 */     setMsgReceiverUser(msgReceiverUser);
/*  54 */     setMsgSendUser(msgSendUser);
/*  55 */     setMsgTitle(msgTitle);
/*  56 */     setMsgStatus(msgStatus);
/*  57 */     setMsgBox(msgBox);
/*  58 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Set<ReceiverMessage> getReceiverMsgs()
/*     */   {
/*  88 */     return this.receiverMsgs;
/*     */   }
/*     */ 
/*     */   public void setReceiverMsgs(Set<ReceiverMessage> receiverMsgs)
/*     */   {
/*  96 */     this.receiverMsgs = receiverMsgs;
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 106 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 114 */     this.id = id;
/* 115 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getMsgTitle()
/*     */   {
/* 125 */     return this.msgTitle;
/*     */   }
/*     */ 
/*     */   public void setMsgTitle(String msgTitle)
/*     */   {
/* 133 */     this.msgTitle = msgTitle;
/*     */   }
/*     */ 
/*     */   public String getMsgContent()
/*     */   {
/* 141 */     return this.msgContent;
/*     */   }
/*     */ 
/*     */   public void setMsgContent(String msgContent)
/*     */   {
/* 149 */     this.msgContent = msgContent;
/*     */   }
/*     */ 
/*     */   public Date getSendTime()
/*     */   {
/* 157 */     return this.sendTime;
/*     */   }
/*     */ 
/*     */   public void setSendTime(Date sendTime)
/*     */   {
/* 165 */     this.sendTime = sendTime;
/*     */   }
/*     */ 
/*     */   public boolean getMsgStatus()
/*     */   {
/* 173 */     return this.msgStatus;
/*     */   }
/*     */ 
/*     */   public void setMsgStatus(boolean msgStatus)
/*     */   {
/* 181 */     this.msgStatus = msgStatus;
/*     */   }
/*     */ 
/*     */   public Integer getMsgBox()
/*     */   {
/* 189 */     return this.msgBox;
/*     */   }
/*     */ 
/*     */   public void setMsgBox(Integer msgBox)
/*     */   {
/* 197 */     this.msgBox = msgBox;
/*     */   }
/*     */ 
/*     */   public ShopMember getMsgReceiverUser()
/*     */   {
/* 205 */     return this.msgReceiverUser;
/*     */   }
/*     */ 
/*     */   public void setMsgReceiverUser(ShopMember msgReceiverUser)
/*     */   {
/* 213 */     this.msgReceiverUser = msgReceiverUser;
/*     */   }
/*     */ 
/*     */   public ShopAdmin getMsgSendUser()
/*     */   {
/* 221 */     return this.msgSendUser;
/*     */   }
/*     */ 
/*     */   public void setMsgSendUser(ShopAdmin msgSendUser)
/*     */   {
/* 229 */     this.msgSendUser = msgSendUser;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 235 */     if (obj == null) return false;
/* 236 */     if (!(obj instanceof Message)) return false;
/*     */ 
/* 238 */     Message message = (Message)obj;
/* 239 */     if ((getId() == null) || (message.getId() == null)) return false;
/* 240 */     return getId().equals(message.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 245 */     if (-2147483648 == this.hashCode) {
/* 246 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 248 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 249 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 252 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 257 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseMessage
 * JD-Core Version:    0.6.0
 */