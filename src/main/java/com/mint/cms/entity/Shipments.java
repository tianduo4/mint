package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseShipments;
import org.json.JSONException;
import org.json.JSONObject;

public class Shipments extends BaseShipments {
    private static final long serialVersionUID = 1L;

    public Shipments() {
    }

    public Shipments(Long id) {
        super(id);
    }

    public Shipments(Long id, Order indent, ShopAdmin shopAdmin, String waybill, String receiving, String comment) {
        super(id,
                indent,
                shopAdmin,
                waybill,
                receiving,
                comment);
    }

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("orderId", getIndent() == null ? "" : CommonUtils.parseLong(getIndent().getId()));
        json.put("code", getIndent() == null ? "" : CommonUtils.parseLong(getIndent().getCode()));
        json.put("receiveName", getIndent() == null ? "" : CommonUtils.parseStr(getIndent().getReceiveName()));
        json.put("receiveAddress", getIndent() == null ? "" : CommonUtils.parseStr(getIndent().getReceiveAddress()));
        json.put("receiveZip", getIndent() == null ? "" : CommonUtils.parseStr(getIndent().getReceiveZip()));
        json.put("receiveMobile", getIndent() == null ? "" : CommonUtils.parseStr(getIndent().getReceiveMobile()));
        json.put("receivePhone", getIndent() == null ? "" : CommonUtils.parseStr(getIndent().getReceivePhone()));
        json.put("username", getShopAdmin() == null ? "" : CommonUtils.parseStr(getShopAdmin().getUsername()));
        json.put("paymentStatus", getIndent() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getIndent().getPaymentStatus())));
        json.put("waybill", CommonUtils.parseStr(getWaybill()));
        json.put("money", CommonUtils.parseDouble(Double.valueOf(getMoney())));
        json.put("shippingName", CommonUtils.parseStr(getShippingName()));
        json.put("shippingMobile", CommonUtils.parseStr(getShippingMobile()));
        json.put("shipppingAddress", CommonUtils.parseStr(getShippingAddress()));
        json.put("isPrint", CommonUtils.parseBoolean(getIsPrint()));
        json.put("comment", CommonUtils.parseStr(getComment()));
        return json;
    }
}

