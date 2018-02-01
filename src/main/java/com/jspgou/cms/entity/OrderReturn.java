package com.jspgou.cms.entity;

import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.entity.base.BaseOrderReturn;
import com.jspgou.common.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderReturn extends BaseOrderReturn {
    private static final long serialVersionUID = 1L;

    public OrderReturn() {
    }

    public OrderReturn(Long id) {
        super(id);
    }

    public OrderReturn(Long id, Order order, ShopDictionary shopDictionary, Integer payType, Integer status, Double money, Integer returnType, Date createTime) {
        super(id,
                order,
                shopDictionary,
                payType,
                status,
                money,
                returnType,
                createTime);
    }

    public void addToPictures(String path, String desc) {
        List list = getPictures();
        if (list == null) {
            list = new ArrayList();
            setPictures(list);
        }
        OrderReturnPicture cp = new OrderReturnPicture();
        cp.setImgPath(path);
        cp.setDescription(desc);
        list.add(cp);
    }

    public OrderReturnStatus getOrderReturnStatus() {
        Integer status = getReturnType();
        if (status != null) {
            return OrderReturnStatus.valueOf(status.intValue());
        }
        return null;
    }

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("code", CommonUtils.parseStr(getCode()));
        json.put("orderId", getOrder() == null ? "" : CommonUtils.parseLong(getOrder().getId()));
        json.put("reason", CommonUtils.parseStr(getReason()));
        json.put("createTime", CommonUtils.parseDate(getCreateTime(), DateUtils.COMMON_FORMAT_STR));
        json.put("status", CommonUtils.parseInteger(getStatus()));
        json.put("money", CommonUtils.parseDouble(getMoney()));
        json.put("returnType", CommonUtils.parseInteger(getReturnType()));
        json.put("orderCode", getOrder() == null ? "" : CommonUtils.parseLong(getOrder().getCode()));
        json.put("username", getOrder().getMember().getUsername() == null ? "" : CommonUtils.parseStr(getOrder().getMember().getUsername()));
        json.put("orderTime", getOrder() == null ? "" : CommonUtils.parseDate(getOrder().getCreateTime(), DateUtils.COMMON_FORMAT_STR));
        return json;
    }

    public static enum OrderReturnStatus {
        SELLER_NODELIVERY_RETURN(1),

        SELLER_DELIVERY_RETURN(2);

        private int value;

        private OrderReturnStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static OrderReturnStatus valueOf(int value) {
            for (OrderReturnStatus s : values()) {
                if (s.getValue() == value) {
                    return s;
                }
            }
            throw new IllegalArgumentException("Connot find value " + value +
                    " in eunu OrderStatus.");
        }
    }
}

