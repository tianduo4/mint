package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseOrder;
import com.mint.common.util.DateUtils;
import com.mint.core.entity.Website;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Order extends BaseOrder {
    private static final long serialVersionUID = 1L;

    public int calWeight() {
        int weight = 0;
        for (OrderItem item : getItems()) {
            weight += item.getProduct().getWeight().intValue();
        }
        return weight;
    }

    public Double getWeightForFreight() {
        Double weight = Double.valueOf(0.0D);
        for (OrderItem item : getItems()) {
            weight = Double.valueOf(weight.doubleValue() + item.getWeightForFreight());
        }
        return weight;
    }

    public int getCountForFreight() {
        int count = 0;
        for (OrderItem item : getItems()) {
            count += item.getCountForFreigt();
        }
        return count;
    }

    public void addToItems(OrderItem item) {
        Set items = getItems();
        if (items == null) {
            items = new LinkedHashSet();
            setItems(items);
        }
        item.setOrdeR(this);
        items.add(item);
    }

    public void init() {
        Date now = new Timestamp(System.currentTimeMillis());
        if (getCreateTime() == null) {
            setCreateTime(now);
        }
        if (getLastModified() == null)
            setLastModified(now);
    }

    public JSONObject convertToJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("userName", CommonUtils.parseStr(getMember().getUsername()));
        json.put("receiveName", CommonUtils.parseStr(getReceiveName()));
        json.put("total", CommonUtils.parseDouble(getTotal()));
        json.put("freight", CommonUtils.parseDouble(getFreight()));
        json.put("createTime", CommonUtils.parseDate(getCreateTime(), DateUtils.COMMON_FORMAT_STR));
        json.put("shippingName", getShipping() == null ? "" : CommonUtils.parseStr(getShipping().getName()));

        json.put("shippingId", getShipping() == null ? "" : CommonUtils.parseLong(getShipping().getId()));
        json.put("paymentId", getPayment() == null ? "" : CommonUtils.parseLong(getPayment().getId()));

        json.put("paymentName", getPayment() == null ? "" : CommonUtils.parseStr(getPayment().getName()));
        json.put("orderStatus", CommonUtils.parseInteger(getStatus()));
        json.put("paymentStatus", CommonUtils.parseInteger(getPaymentStatus()));
        json.put("shippingStatus", CommonUtils.parseInteger(getShippingStatus()));
        Set<OrderItem> order = getItems();
        JSONArray jsons = new JSONArray();
        for (OrderItem item : order) {
            JSONObject obj = new JSONObject();

            obj.put("productCode", CommonUtils.parseLong(item.getProduct().getProductExt().getCode()));
            obj.put("coverImg", CommonUtils.parseStr(item.getProduct().getProductExt().getCoverImg()));
            obj.put("productName", CommonUtils.parseStr(item.getProduct().getName()));
            obj.put("itemWeight", CommonUtils.parseInteger(item.getProduct().getWeight()));
            obj.put("itemId", CommonUtils.parseId(item.getId()));
            obj.put("itemPrice", CommonUtils.parseDouble(item.getSalePrice()));
            obj.put("itemCount", CommonUtils.parseInteger(item.getCount()));
            jsons.put(obj);
        }
        json.put("product", jsons);
        json.put("code", CommonUtils.parseLong(getCode()));
        json.put("receiveMobile", CommonUtils.parseStr(getReceiveMobile()));
        json.put("receivePhone", CommonUtils.parseStr(getReceivePhone()));
        json.put("receiveZip", CommonUtils.parseStr(getReceiveZip()));
        json.put("receiveAddress", CommonUtils.parseStr(getReceiveAddress()));
        json.put("comments", CommonUtils.parseStr(getComments()));

        json.put("returnCode", getReturnOrder() == null ? "" : CommonUtils.parseStr(getReturnOrder().getCode()));
        json.put("returnTime", getReturnOrder() == null ? "" : CommonUtils.parseDate(getReturnOrder().getCreateTime(), DateUtils.COMMON_FORMAT_STR));
        json.put("returnType", getReturnOrder() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getReturnOrder().getReturnType())));
        json.put("status", getReturnOrder() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getReturnOrder().getStatus())));
        json.put("money", getReturnOrder() == null ? "" : CommonUtils.parseDouble(getReturnOrder().getMoney()));
        json.put("reason", getReturnOrder() == null ? "" : CommonUtils.parseStr(getReturnOrder().getReason()));
        json.put("invoiceNo", getReturnOrder() == null ? "" : CommonUtils.parseStr(getReturnOrder().getInvoiceNo()));
        json.put("shippingLogistics", getReturnOrder() == null ? "" : CommonUtils.parseStr(getReturnOrder().getShippingLogistics()));
        json.put("returnPayType", getReturnOrder() == null ? "" : Integer.valueOf(CommonUtils.parseInteger(getReturnOrder().getPayType())));
        json.put("returnReasonTypeName", (getReturnOrder() != null) && (getReturnOrder().getShopDictionary() != null) ? CommonUtils.parseStr(getReturnOrder().getShopDictionary().getName()) : "");

        return json;
    }

    public Order() {
    }

    public Order(Long id) {
        super(id);
    }

    public Order(Long id, Website website, ShopMember member, Payment payment, Shipping shipping, Shipping shopDirectory, Long code, String ip, Date createTime, Date lastModified, Double total, Integer score, Double weight, Integer status) {
        super(id,
                website,
                member,
                payment,
                shipping,
                shopDirectory,
                code.longValue(),
                ip,
                createTime,
                lastModified,
                total,
                score,
                weight);
    }
}

