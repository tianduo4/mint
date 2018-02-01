package com.mint.cms.api;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.mint.cms.entity.OrderReturn;
import com.mint.cms.entity.PaymentPlugins;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

public class PayUtils {
    public static JSONObject alipayReturn(PaymentPlugins paymentPlugins, OrderReturn orderReturn)
            throws AlipayApiException {
        String appId = paymentPlugins.getPartner();
        String privatekey = paymentPlugins.getSellerKey();
        String pubkey = paymentPlugins.getPublicKey();

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privatekey, "json", "UTF-8", pubkey, "RSA2");
        AlipayFundTransToaccountTransferRequest req = new AlipayFundTransToaccountTransferRequest();

        req.setBizContent("{\"out_biz_no\":" +
                orderReturn.getCode() + "," +
                "\"payee_type\":\"ALIPAY_LOGONID\"," +
                "\"payee_account\":" + orderReturn.getAlipayId() + "," +
                "\"amount\":" + orderReturn.getMoney() + "," +
                "\"payer_show_name\":\"金磊科技\"," +
                "\"payee_real_name\":\"\"," +
                "\"remark\":" + orderReturn + "订单退款" +
                "}");
        AlipayFundTransToaccountTransferResponse response = (AlipayFundTransToaccountTransferResponse) alipayClient.execute(req);
        System.out.println("支付宝AlipayFundTransToaccountTransfer返回参数:" + response.getBody());
        JSONObject json = new JSONObject();
        json.put("status", Boolean.valueOf(false));
        JSONObject body = new JSONObject();
        if (StringUtils.isNotEmpty(response.getBody())) {
            body = JSONObject.fromObject(response.getBody());
            if ("10000".equals(body.get("code"))) {
                json.put("status", Boolean.valueOf(true));
                json.put("msg", "");
            } else {
                body = JSONObject.fromObject(body.get("alipay_fund_trans_toaccount_transfer_response"));
                json.put("msg", body.get("sub_msg"));
            }
        } else {
            json.put("msg", "");
        }
        return json;
    }
}

