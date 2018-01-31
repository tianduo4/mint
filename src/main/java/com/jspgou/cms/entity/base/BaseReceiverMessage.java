/*     */ package com.jspgou.cms.entity.base;
/*     */ 
/*     */ import com.jspgou.cms.entity.Message;
/*     */ import com.jspgou.cms.entity.ReceiverMessage;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ public abstract class BaseReceiverMessage
/*     */   implements Serializable
/*     */ {
/*  17 */   public static String REF = "ReceiverMessage";
/*  18 */   public static String PROP_MSG_STATUS = "msgStatus";
/*  19 */   public static String PROP_MESSAGE = "message";
/*  20 */   public static String PROP_MSG_SEND_USER = "msgSendUser";
/*  21 */   public static String PROP_MSG_CONTENT = "msgContent";
/*  22 */   public static String PROP_MSG_BOX = "msgBox";
/*  23 */   public static String PROP_SEND_TIME = "sendTime";
/*  24 */   public static String PROP_ID = "id";
/*  25 */   public static String PROP_MSG_RECEIVER_USER = "msgReceiverUser";
/*  26 */   public static String PROP_MSG_TITLE = "msgTitle";
/*     */ 
/*  66 */   private int hashCode = -2147483648;
/*     */   private Long id;
/*     */   private String msgTitle;
/*     */   private String msgContent;
/*     */   private Date sendTime;
/*     */   private boolean msgStatus;
/*     */   private Integer msgBox;
/*     */   private ShopMember msgReceiverUser;
/*     */   private ShopAdmin msgSendUser;
/*     */   private Message message;
/*     */ 
/*     */   public BaseReceiverMessage()
/*     */   {
/*  31 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseReceiverMessage(Long id)
/*     */   {
/*  38 */     setId(id);
/*  39 */     initialize();
/*     */   }
/*     */ 
/*     */   public BaseReceiverMessage(Long id, ShopMember msgReceiverUser, ShopAdmin msgSendUser, String msgTitle, boolean msgStatus, Integer msgBox)
/*     */   {
/*  53 */     setId(id);
/*  54 */     setMsgReceiverUser(msgReceiverUser);
/*  55 */     setMsgSendUser(msgSendUser);
/*  56 */     setMsgTitle(msgTitle);
/*  57 */     setMsgStatus(msgStatus);
/*  58 */     setMsgBox(msgBox);
/*  59 */     initialize();
/*     */   }
/*     */ 
/*     */   protected void initialize()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/*  92 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id)
/*     */   {
/* 100 */     this.id = id;
/* 101 */     this.hashCode = -2147483648;
/*     */   }
/*     */ 
/*     */   public String getMsgTitle()
/*     */   {
/* 111 */     return this.msgTitle;
/*     */   }
/*     */ 
/*     */   public void setMsgTitle(String msgTitle)
/*     */   {
/* 119 */     this.msgTitle = msgTitle;
/*     */   }
/*     */ 
/*     */   public String getMsgContent()
/*     */   {
/* 127 */     return this.msgContent;
/*     */   }
/*     */ 
/*     */   public void setMsgContent(String msgContent)
/*     */   {
/* 135 */     this.msgContent = msgContent;
/*     */   }
/*     */ 
/*     */   public Date getSendTime()
/*     */   {
/* 143 */     return this.sendTime;
/*     */   }
/*     */ 
/*     */   public void setSendTime(Date sendTime)
/*     */   {
/* 151 */     this.sendTime = sendTime;
/*     */   }
/*     */ 
/*     */   public boolean getMsgStatus()
/*     */   {
/* 159 */     return this.msgStatus;
/*     */   }
/*     */ 
/*     */   public void setMsgStatus(boolean msgStatus)
/*     */   {
/* 167 */     this.msgStatus = msgStatus;
/*     */   }
/*     */ 
/*     */   public Integer getMsgBox()
/*     */   {
/* 175 */     return this.msgBox;
/*     */   }
/*     */ 
/*     */   public void setMsgBox(Integer msgBox)
/*     */   {
/* 183 */     this.msgBox = msgBox;
/*     */   }
/*     */ 
/*     */   public ShopMember getMsgReceiverUser()
/*     */   {
/* 191 */     return this.msgReceiverUser;
/*     */   }
/*     */ 
/*     */   public void setMsgReceiverUser(ShopMember msgReceiverUser)
/*     */   {
/* 199 */     this.msgReceiverUser = msgReceiverUser;
/*     */   }
/*     */ 
/*     */   public ShopAdmin getMsgSendUser()
/*     */   {
/* 207 */     return this.msgSendUser;
/*     */   }
/*     */ 
/*     */   public void setMsgSendUser(ShopAdmin msgSendUser)
/*     */   {
/* 215 */     this.msgSendUser = msgSendUser;
/*     */   }
/*     */ 
/*     */   public Message getMessage()
/*     */   {
/* 223 */     return this.message;
/*     */   }
/*     */ 
/*     */   public void setMessage(Message message)
/*     */   {
/* 231 */     this.message = message;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 237 */     if (obj == null) return false;
/* 238 */     if (!(obj instanceof ReceiverMessage)) return false;
/*     */ 
/* 240 */     ReceiverMessage receiverMessage = (ReceiverMessage)obj;
/* 241 */     if ((getId() == null) || (receiverMessage.getId() == null)) return false;
/* 242 */     return getId().equals(receiverMessage.getId());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 247 */     if (-2147483648 == this.hashCode) {
/* 248 */       if (getId() == null) return super.hashCode();
/*     */ 
/* 250 */       String hashStr = getClass().getName() + ":" + getId().hashCode();
/* 251 */       this.hashCode = hashStr.hashCode();
/*     */     }
/*     */ 
/* 254 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 259 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.base.BaseReceiverMessage
 * JD-Core Version:    0.6.0
 */