package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseReceiverMessage;
import com.jspgou.common.util.DateUtils;
import com.jspgou.common.util.StrUtils;
import net.sf.json.JSONObject;

public class ReceiverMessage extends BaseReceiverMessage {
    private static final long serialVersionUID = 1L;

    public ReceiverMessage() {
    }

    public ReceiverMessage(Long id) {
        super(id);
    }

    public ReceiverMessage(Long id, ShopMember msgReceiverUser, ShopAdmin msgSendUser, String msgTitle, boolean msgStatus, Integer msgBox) {
        super(id,
                msgReceiverUser,
                msgSendUser,
                msgTitle,
                msgStatus,
                msgBox);
    }

    public String getTitleHtml() {
        return StrUtils.txt2htm(getMsgTitle());
    }

    public String getContentHtml() {
        return StrUtils.txt2htm(getMsgContent());
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

