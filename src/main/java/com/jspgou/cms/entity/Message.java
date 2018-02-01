package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseMessage;
import com.jspgou.common.util.DateUtils;
import net.sf.json.JSONObject;

public class Message extends BaseMessage {
    private static final long serialVersionUID = 1L;

    public Message() {
    }

    public Message(Long id) {
        super(id);
    }

    public Message(Long id, ShopMember msgReceiverUser, ShopAdmin msgSendUser, String msgTitle, boolean msgStatus, Integer msgBox) {
        super(id,
                msgReceiverUser,
                msgSendUser,
                msgTitle,
                msgStatus,
                msgBox);
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("msgTitle", CommonUtils.parseStr(getMsgTitle()));
        json.put("msgContent", CommonUtils.parseStr(getMsgContent()));
        json.put("sendTime", CommonUtils.parseDate(getSendTime(), DateUtils.COMMON_FORMAT_STR));
        json.put("sendUserId", getMsgSendUser() != null ? CommonUtils.parseLong(getMsgSendUser().getId()) : "");
        json.put("sendUserName", getMsgSendUser() != null ? CommonUtils.parseStr(getMsgSendUser().getUsername()) : "");
        json.put("receiverUserId", getMsgReceiverUser() != null ? CommonUtils.parseLong(getMsgReceiverUser().getId()) : "");
        json.put("receiverUserName", getMsgReceiverUser() != null ? CommonUtils.parseStr(getMsgReceiverUser().getUsername()) : "");
        return json;
    }
}

