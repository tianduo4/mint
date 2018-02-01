package com.mint.cms.entity.base;

import com.mint.cms.entity.Message;
import com.mint.cms.entity.ReceiverMessage;
import com.mint.cms.entity.ShopAdmin;
import com.mint.cms.entity.ShopMember;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public abstract class BaseMessage
        implements Serializable {
    public static String REF = "Message";
    public static String PROP_MSG_STATUS = "msgStatus";
    public static String PROP_MSG_SEND_USER = "msgSendUser";
    public static String PROP_MSG_CONTENT = "msgContent";
    public static String PROP_MSG_BOX = "msgBox";
    public static String PROP_SEND_TIME = "sendTime";
    public static String PROP_ID = "id";
    public static String PROP_MSG_RECEIVER_USER = "msgReceiverUser";
    public static String PROP_MSG_TITLE = "msgTitle";

    private int hashCode = -2147483648;
    private Long id;
    private String msgTitle;
    private String msgContent;
    private Date sendTime;
    private boolean msgStatus;
    private Integer msgBox;
    private ShopMember msgReceiverUser;
    private ShopAdmin msgSendUser;
    private Set<ReceiverMessage> receiverMsgs;

    public BaseMessage() {
        initialize();
    }

    public BaseMessage(Long id) {
        setId(id);
        initialize();
    }

    public BaseMessage(Long id, ShopMember msgReceiverUser, ShopAdmin msgSendUser, String msgTitle, boolean msgStatus, Integer msgBox) {
        setId(id);
        setMsgReceiverUser(msgReceiverUser);
        setMsgSendUser(msgSendUser);
        setMsgTitle(msgTitle);
        setMsgStatus(msgStatus);
        setMsgBox(msgBox);
        initialize();
    }

    protected void initialize() {
    }

    public Set<ReceiverMessage> getReceiverMsgs() {
        return this.receiverMsgs;
    }

    public void setReceiverMsgs(Set<ReceiverMessage> receiverMsgs) {
        this.receiverMsgs = receiverMsgs;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getMsgTitle() {
        return this.msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return this.msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Date getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public boolean getMsgStatus() {
        return this.msgStatus;
    }

    public void setMsgStatus(boolean msgStatus) {
        this.msgStatus = msgStatus;
    }

    public Integer getMsgBox() {
        return this.msgBox;
    }

    public void setMsgBox(Integer msgBox) {
        this.msgBox = msgBox;
    }

    public ShopMember getMsgReceiverUser() {
        return this.msgReceiverUser;
    }

    public void setMsgReceiverUser(ShopMember msgReceiverUser) {
        this.msgReceiverUser = msgReceiverUser;
    }

    public ShopAdmin getMsgSendUser() {
        return this.msgSendUser;
    }

    public void setMsgSendUser(ShopAdmin msgSendUser) {
        this.msgSendUser = msgSendUser;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Message)) return false;

        Message message = (Message) obj;
        if ((getId() == null) || (message.getId() == null)) return false;
        return getId().equals(message.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (getId() == null) return super.hashCode();

            String hashStr = getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

