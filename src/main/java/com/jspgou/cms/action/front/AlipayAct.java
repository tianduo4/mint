package com.jspgou.cms.action.front;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.jspgou.cms.Alipay;
import com.jspgou.cms.entity.Order;
import com.jspgou.cms.entity.PaymentPlugins;
import com.jspgou.cms.entity.Shipping;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.OrderMng;
import com.jspgou.cms.manager.PaymentPluginsMng;
import com.jspgou.cms.manager.ShopMemberMng;
import com.jspgou.cms.web.FrontUtils;
import com.jspgou.cms.web.ShopFrontHelper;
import com.jspgou.cms.web.threadvariable.MemberThread;
import com.jspgou.common.web.RequestUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Global;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;
import com.jspgou.core.web.front.FrontHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.ProtocolException;
import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AlipayAct extends Alipay {
    private static final Logger log = LoggerFactory.getLogger(AlipayAct.class);
    public static final String WEIXIN_AUTH_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    private static final String WECHAT_CODE = "tpl.weChatCode";
    public static final String WECHAT_PUBLIC_PAY = "tpl.weChatPublicPay";
    public static final String SUCCESSFULLY_ORDER = "tpl.successfullyOrder";
    public static final String JSAPI = "JSAPI";
    public static final String PUBLIC_PAY_RETURN_URL = "/WeChatPublicPayReturn.jspx";
    public static final String WEIXIN_AUTH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";
    private static final String ALIPAY_TRADE_WAP_PAY = "https://openapi.alipay.com/gateway.do";
    public static final String ALIPAY_MOBILE_RETURN_URL = "/shopMember/index.jspx";
    public static final String ALIPAY_MOBILE_PAY_NOTIFY_URL = "/alipayMobilePayReturn.jspx";
    public static final String UTF8 = "UTF-8";
    private static AlipayClient alipayClient;
    private static final String JSON = "json";

    @Autowired
    private OrderMng manager;

    @Autowired
    private PaymentPluginsMng paymentPluginsMng;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @RequestMapping({"/pay.jspx"})
    public String pay(Long orderId, String code, String pay, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws JDOMException, IOException {
        Website web = SiteUtils.getWeb(request);
        if ((orderId != null) && (this.manager.findById(orderId) != null)) {
            Order order = this.manager.findById(orderId);
            PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode(code);
            PrintWriter out = null;
            String aliURL = null;
            if ((!StringUtils.isBlank(code)) && (code.equals("weChatPayment"))) {
                return weChatPayment(paymentPlugins, web, order, request, response, model);
            }
            if ((!StringUtils.isBlank(code)) && (code.equals("alipayMobile"))) {
                String alipayUrl = null;
                return alipayMobile(alipayUrl, paymentPlugins, web, order, null,
                        null, request, response, model);
            }
            if ((!StringUtils.isBlank(code)) && (code.equals("weChatPublicPay"))) {
                ShopMember member = MemberThread.get();

                if (member == null) {
                    return "redirect:../login.jspx";
                }
                String wechatOppenId = member.getWechatOppenid();
                if (StringUtils.isNotEmpty(wechatOppenId)) {
                    return weChatPublicPay(paymentPlugins, web, pay, order, wechatOppenId,
                            request, model);
                }
                return weChatCode(paymentPlugins, orderId, pay, Integer.valueOf(1), request, response, model);
            }
            try {
                if ((!StringUtils.isBlank(code)) && (code.equals("alipayPartner")))
                    aliURL = alipay(paymentPlugins, web, order, request, model);
                else if ((!StringUtils.isBlank(code)) && (code.equals("alipay"))) {
                    aliURL = alipayapi(paymentPlugins, web, order, request, model);
                }
                response.setContentType("text/html;charset=UTF-8");
                out = response.getWriter();
                out.print(aliURL);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                out.close();
            }
            return null;
        }

        return FrontUtils.showMessage(request, model, "请回到我的订单页面，再进行支付");
    }

    @RequestMapping({"/aliReturn.jspx"})
    public String aliReturn(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
        Map params = new HashMap();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode("alipayPartner");
        Long orderId = Long.valueOf(Long.parseLong(out_trade_no));
        Order order = this.manager.findById(orderId);
        if (verify(params, paymentPlugins.getPartner(), paymentPlugins.getSellerKey())) {
            if (trade_status.equals("WAIT_BUYER_PAY")) {
                return FrontUtils.showMessage(request, model, "付款失败！");
            }
            if (trade_status.equals("WAIT_SELLER_SEND_GOODS")) {
                order.setPaymentStatus(Integer.valueOf(2));
                order.setTradeNo(trade_no);
                order.setPaymentCode("alipayPartner");
                this.manager.updateByUpdater(order);
                return FrontUtils.showMessage(request, model, "付款成功，请等待发货！");
            }
            if (trade_status.equals("WAIT_BUYER_CONFIRM_GOODS")) {
                return FrontUtils.showMessage(request, model, "已发货，未确认收货！");
            }
            if (trade_status.equals("TRADE_FINISHED")) {
                return FrontUtils.showMessage(request, model, "交易完成！");
            }
            return FrontUtils.showMessage(request, model, "success！");
        }

        return FrontUtils.showMessage(request, model, "付款异常！");
    }

    private String alipay(PaymentPlugins paymentPlugins, Website web, Order order, HttpServletRequest request, ModelMap model) {
        String payment_type = "1";

        String notify_url = "http://" + web.getDomain() + "/aliReturn.jspx";

        String return_url = "http://" + web.getDomain() + "/aliReturn.jspx";

        String seller_email = paymentPlugins.getSellerEmail();

        String out_trade_no = order.getId().toString();

        String subject = "(" + order.getId() + ")";

        String price = String.valueOf(order.getTotal());

        String quantity = "1";

        String logistics_fee = String.valueOf(order.getFreight());

        String logistics_type = getLogisticsType(order);

        String logistics_payment = "BUYER_PAY";

        String body = "(" + order.getId() + ")";

        String show_url = "http://" + web.getDomain() + "/";

        String receive_name = order.getReceiveName();

        String receive_address = order.getReceiveAddress();

        String receive_zip = order.getReceiveZip();

        String receive_phone = order.getReceivePhone();

        String receive_mobile = order.getReceiveMobile();

        Map sParaTemp = new HashMap();
        sParaTemp.put("service", "create_partner_trade_by_buyer");
        sParaTemp.put("partner", paymentPlugins.getPartner());
        sParaTemp.put("_input_charset", "utf-8");
        sParaTemp.put("payment_type", payment_type);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("seller_email", seller_email);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("price", price);
        sParaTemp.put("quantity", quantity);
        sParaTemp.put("logistics_fee", logistics_fee);
        sParaTemp.put("logistics_type", logistics_type);
        sParaTemp.put("logistics_payment", logistics_payment);
        sParaTemp.put("body", body);
        sParaTemp.put("show_url", show_url);
        sParaTemp.put("receive_name", receive_name);
        sParaTemp.put("receive_address", receive_address);
        sParaTemp.put("receive_zip", receive_zip);
        sParaTemp.put("receive_phone", receive_phone);
        sParaTemp.put("receive_mobile", receive_mobile);

        String sHtmlText = buildRequest(sParaTemp, paymentPlugins.getSellerKey(), "get", "确认");
        return sHtmlText;
    }

    @RequestMapping({"/aliReturnUrl.jspx"})
    public String aliReturndirect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
        Map params = new HashMap();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr =
                        valueStr + values[i] + ",";
            }

            params.put(name, valueStr);
        }

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

        PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode("alipay");
        Long orderId = Long.valueOf(Long.parseLong(out_trade_no));

        Order order = this.manager.findByCode(orderId);
        if (verify(params, paymentPlugins.getPartner(), paymentPlugins.getSellerKey())) {
            if (trade_status.equals("TRADE_FINISHED")) {
                order.setPaymentStatus(Integer.valueOf(2));
                this.manager.updateByUpdater(order);
                return FrontUtils.showMessage(request, model, "付款成功，请等待发货！");
            }

            if (trade_status.equals("TRADE_SUCCESS")) {
                order.setPaymentStatus(Integer.valueOf(2));
                this.manager.updateByUpdater(order);
                return FrontUtils.showMessage(request, model, "付款成功，请等待发货！");
            }
        } else {
            return FrontUtils.showMessage(request, model, "验证失败！");
        }
        return FrontUtils.showMessage(request, model, "付款异常！");
    }

    private String alipayapi(PaymentPlugins paymentPlugins, Website web, Order order, HttpServletRequest request, ModelMap model) {
        String payment_type = "1";

        String notify_url = "http://" + web.getDomain() + "/aliReturnUrl.jspx";

        String return_url = "http://" + web.getDomain() + "/aliReturnUrl.jspx";

        String seller_email = paymentPlugins.getSellerEmail();

        String out_trade_no = order.getCode().toString();

        String subject = order.getProductName();

        String total_fee = String.valueOf(order.getTotal());

        String body = "(" + order.getId() + ")";

        String show_url = "http://" + web.getDomain() + "/";

        String anti_phishing_key = "";

        String exter_invoke_ip = RequestUtils.getIpAddr(request);
        Map sParaTemp = new HashMap();
        sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", paymentPlugins.getPartner());
        sParaTemp.put("_input_charset", "utf-8");
        sParaTemp.put("payment_type", payment_type);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("seller_email", seller_email);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);
        sParaTemp.put("show_url", show_url);
        sParaTemp.put("anti_phishing_key", anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

        String sHtmlText = buildRequest(sParaTemp, paymentPlugins.getSellerKey(), "get", "确认");
        return sHtmlText;
    }

    @RequestMapping({"/WeChatScanCodePayReturn.jspx"})
    public void WeChatScanCodePayReturn(Long code, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JDOMException, IOException, JSONException {
        JSONObject json = new JSONObject();

        if (code != null) {
            if (this.manager.findByCode(code).getPaymentStatus().intValue() == 2) {
                json.put("status", 4);
                json.put("error", "支付成功,点击确定，跳转到我的订单");
            } else {
                json.put("status", 6);
                json.put("error", "订单未支付");
            }
        } else {
            String xml_receive_result = getXmlRequest(request);
            if ((StringUtils.isBlank(xml_receive_result)) || (xml_receive_result == "")) {
                json.put("status", 5);
                json.put("error", "检测到您可能没有进行扫码支付，请支付");
            } else {
                Map result_map = doXMLParse(xml_receive_result);
                String sign_receive = (String) result_map.get("sign");
                result_map.remove("sign");
                String key = this.paymentPluginsMng.findByCode("weChatPayment").getSellerKey();
                if (key == null) {
                    json.put("status", 1);
                    json.put("error", "微信扫码支付密钥错误，请通知商户");
                }
                String checkSign = createSign(result_map, key);
                if ((checkSign != null) && (checkSign.equals(sign_receive))) {
                    try {
                        noticeWeChatSuccess();
//             if (result_map == null)
//                          break label551; //TODO
                        String return_code = (String) result_map.get("return_code");
                        if (("SUCCESS".equals(return_code)) && ("SUCCESS".equals(result_map.get("result_code")))) {
                            String transaction_id = (String) result_map.get("transaction_id");

                            String out_trade_no = (String) result_map.get("out_trade_no");
                            Order order = this.manager.findByCode(Long.valueOf(Long.parseLong(out_trade_no)));
                            order.setPaymentStatus(Integer.valueOf(2));
                            order.setTradeNo(transaction_id);
                            order.setPaymentCode("weChatPayment");
                            this.manager.updateByUpdater(order);
                            json.put("status", 0);
                            json.put("error", "支付成功,点击确定，跳转到我的订单");
//               break label551;  //TODO
                        }
//                      if ((!"SUCCESS".equals(return_code)) || (result_map.get("err_code") == null))
//                          break label551; //TODO
                        String message = (String) result_map.get("err_code_des");
                        json.put("status", 2);
                        json.put("error", message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Map parames = new HashMap();
                    parames.put("return_code", "FAIL");
                    parames.put("return_msg", "校验错误");

                    String xmlWeChat = getRequestXml(parames);
                    try {
                        testPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlWeChat);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    json.put("status", 3);
                    json.put("error", "支付参数错误，请重新支付!");
                }
            }
        }
        label551:
        ResponseUtils.renderJson(response, json.toString());
    }

    private String weChatPayment(PaymentPlugins paymentPlugins, Website web, Order order, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JDOMException, IOException {
        Map paramMap = new HashMap();
        if ((StringUtils.isNotBlank(paymentPlugins.getPartner())) && (StringUtils.isNotBlank(paymentPlugins.getSellerEmail()))) {
            paramMap.put("appid", paymentPlugins.getPartner());

            paramMap.put("mch_id", paymentPlugins.getSellerEmail());

            paramMap.put("device_info", "WEB");

            paramMap.put("nonce_str", getWCRandomNumber(Integer.valueOf(10)));

            paramMap.put("body", order.getProductName());

            paramMap.put("out_trade_no", order.getCode().toString());

            paramMap.put("fee_type", "CNY");

            paramMap.put("total_fee", changeY2F(Double.valueOf(order.getTotal().doubleValue() + order.getFreight().doubleValue())));

            paramMap.put("spbill_create_ip", "127.0.0.1");

            paramMap.put("notify_url", "http://" + web.getDomain() + "/WeChatScanCodePayReturn.jspx");

            paramMap.put("trade_type", "NATIVE");

            paramMap.put("product_id", order.getId().toString());
            if (StringUtils.isNotBlank(paymentPlugins.getSellerKey())) {
                paramMap.put("sign", createSign(paramMap, paymentPlugins.getSellerKey()));

                String xmlWeChat = getRequestXml(paramMap);
                String resXml = testPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlWeChat);
                Map map = doXMLParse(resXml);
                String returnCode = (String) map.get("return_code");
                if (returnCode.equalsIgnoreCase("FAIL"))
                    return FrontUtils.showMessage(request, model, (String) map.get("return_msg"));
                if (returnCode.equalsIgnoreCase("SUCCESS")) {
                    if (map.get("err_code") != null)
                        return FrontUtils.showMessage(request, model, (String) map.get("err_code_des"));
                    if (((String) map.get("result_code")).equalsIgnoreCase("SUCCESS")) {
                        String code_url = (String) map.get("code_url");
                        model.addAttribute("code_url", code_url);
                        ShopFrontHelper.setCommonData(request, model, web, 1);

                        model.addAttribute("orderId", order.getId());
                        model.addAttribute("out_trade_no", order.getCode());
                        model.addAttribute("weChatPayment", "weChatPayment");
                        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.weChatPayment", new Object[0]), request);
                    }
                }
                return FrontUtils.showMessage(request, model, "系统超时，请重试!");
            }
            return FrontUtils.showMessage(request, model, "请通知商城管理员，微信扫码支付密钥没有设置，请检查!");
        }

        return FrontUtils.showMessage(request, model, "请通知商城管理员，微信扫码支付商户号或者appid没有设置，请检查!");
    }

    private String weChatCode(PaymentPlugins paymentPlugins, Long orderId, String pay, Integer type, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String codeUrl = "";
        String redirect_uri = "/member/wechat_auth_call.jspx";
        if (StringUtils.isNotBlank(web.getGlobal().getContextPath()))
            redirect_uri = "http://" + web.getDomain() + web.getGlobal().getContextPath() + redirect_uri;
        else {
            redirect_uri = "http://" + web.getDomain() + redirect_uri;
        }
        codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + paymentPlugins.getPartner().trim() + "&redirect_uri=" + redirect_uri +
                "&response_type=code&scope=snsapi_base&state=" + orderId + "@" + pay + "@" + type + "#wechat_redirect";
        model.addAttribute("codeUrl", codeUrl);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.weChatCode", new Object[0]), request);
    }

    @RequestMapping({"/member/wechat_auth_call.jspx"})
    public String weixinAuthCall(String code, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws JDOMException, IOException {
        Website web = SiteUtils.getWeb(request);
        String[] state = request.getParameter("state").split("@");
        String orderId = state[0];
        String pay = state[1];
        String type = state[2];

        Order order = this.manager.findById(Long.valueOf(orderId));

        PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode("weChatPublicPay");

        String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&appid=" + paymentPlugins.getPartner().trim() + "&secret=" + paymentPlugins.getPublicKey().trim() + "&code=" + code;
        JSONObject json = httpsRequestToJsonObject(tokenUrl, "POST", null);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        String openid = null;
        if (json != null) {
            try {
                openid = json.getString("openid");
                if (StringUtils.isNotBlank(openid)) {
                    ShopMember member = MemberThread.get();

                    if (member == null) {
                        return "redirect:../login.jspx";
                    }
                    member.setWechatOppenid(openid);
                    this.shopMemberMng.update(member);
                }
            } catch (JSONException e) {
                WebErrors errors = WebErrors.create(request);
                String errcode = null;
                try {
                    errcode = json.getString("errcode");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                if (StringUtils.isNotBlank(errcode)) {
                    errors.addErrorCode("wechat.auth.fail");
                }
                return FrontHelper.showError(errors, web, model, request);
            }
        }
        WebErrors errors = WebErrors.create(request);
        if (openid == null) {
            errors.addErrorCode("wechat.auth.fail");
            return FrontHelper.showError(errors, web, model, request);
        }
        if (Integer.valueOf(type).intValue() == 0) {
            errors.addErrorCode("wechat.pay.fail");
            return FrontHelper.showError(errors, web, model, request);
        }

        if (order == null) {
            errors.addErrorCode("wechat.pay.fail");
            return FrontHelper.showError(errors, web, model, request);
        }
        if (Integer.valueOf(type).intValue() == 1) {
            return weChatPublicPay(paymentPlugins, web, pay, order, openid, request, model);
        }

        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.weChatPublicPay", new Object[0]), request);
    }

    public static JSONObject httpsRequestToJsonObject(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            StringBuffer buffer = httpsRequest(requestUrl, requestMethod, outputStr);
            jsonObject = new JSONObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("connect.timeout" + ce.getMessage());
        } catch (Exception e) {
            log.error("https.request.exception" + e.getMessage());
        }
        return jsonObject;
    }

    private static StringBuffer httpsRequest(String requestUrl, String requestMethod, String output)
            throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException, MalformedURLException, IOException, ProtocolException, UnsupportedEncodingException {
        URL url = new URL(requestUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod(requestMethod);
        if (output != null) {
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(output.getBytes("UTF-8"));
            outputStream.close();
        }

        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buffer = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        inputStream = null;
        connection.disconnect();
        return buffer;
    }

    public String weChatPublicPay(PaymentPlugins paymentPlugins, Website web, String pay, Order order, String wechatOppenId, HttpServletRequest request, ModelMap model) {
        if ((StringUtils.isNotBlank(paymentPlugins.getPartner())) && (StringUtils.isNotBlank(paymentPlugins.getSellerEmail()))) {
            if (StringUtils.isNotBlank(paymentPlugins.getSellerKey())) {
                Map map = weixinUniformOrder("JSAPI", "/WeChatPublicPayReturn.jspx", web, paymentPlugins, order, wechatOppenId, null, request);
                String returnCode = (String) map.get("return_code");
                if (returnCode.equalsIgnoreCase("FAIL")) {
                    return FrontUtils.showMessage(request, model, (String) map.get("return_msg"));
                }
                if (returnCode.equalsIgnoreCase("SUCCESS")) {
                    if (map.get("err_code") != null)
                        return FrontUtils.showMessage(request, model, (String) map.get("err_code_des"));
                    if (((String) map.get("result_code")).equalsIgnoreCase("SUCCESS")) {
                        String prepay_id = (String) map.get("prepay_id");
                        Long time = Long.valueOf(System.currentTimeMillis() / 1000L);
                        String nonceStr = getWCRandomNumber(Integer.valueOf(16));

                        model.addAttribute("appId", paymentPlugins.getPartner().trim());

                        model.addAttribute("timeStamp", time);

                        model.addAttribute("nonceStr", nonceStr);

                        model.addAttribute("package", "prepay_id=" + prepay_id);

                        model.addAttribute("signType", "MD5");
                        Map paramMapSign = new HashMap();

                        paramMapSign.put("appId", paymentPlugins.getPartner().trim());
                        paramMapSign.put("timeStamp", time.toString());
                        paramMapSign.put("nonceStr", nonceStr);
                        paramMapSign.put("package", "prepay_id=" + prepay_id);
                        paramMapSign.put("signType", "MD5");

                        model.addAttribute("paySign", createSign(paramMapSign, paymentPlugins.getSellerKey().trim()));
                        model.addAttribute("order", order);
                        ShopFrontHelper.setCommonData(request, model, web, 1);

                        model.addAttribute("publicPay", "payOrderAgain");

                        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.weChatPublicPay", new Object[0]), request);
                    }
                }

                return FrontUtils.showMessage(request, model, "system.timeout.please.try.again");
            }
            return FrontUtils.showMessage(request, model, "weChat.publickey.null");
        }

        return FrontUtils.showMessage(request, model, "weChat.publicMchidorApenid.null");
    }

    public static Map<String, String> weixinUniformOrder(String tradeType, String returnUrl, Website web, PaymentPlugins paymentPlugins, Order order, String wechatOppenId, String retainage, HttpServletRequest request) {
        Map paramMap = new HashMap();

        paramMap.put("appid", paymentPlugins.getPartner().trim());

        paramMap.put("mch_id", paymentPlugins.getSellerEmail().trim());

        paramMap.put("device_info", "WEB");

        paramMap.put("nonce_str", getWCRandomNumber(Integer.valueOf(10)));

        paramMap.put("body", order.getProductName());

        paramMap.put("out_trade_no", order.getCode().toString());

        paramMap.put("fee_type", "CNY");

        paramMap.put("total_fee", changeY2F(Double.valueOf(order.getTotal().doubleValue() + order.getFreight().doubleValue())));

        paramMap.put("spbill_create_ip", RequestUtils.getIpAddr(request));

        paramMap.put("notify_url", "http://" + web.getDomain() +
                returnUrl);

        paramMap.put("trade_type", tradeType);

        if ((tradeType.equals("JSAPI")) && (wechatOppenId != null)) {
            paramMap.put("openid", wechatOppenId.trim());
        }

        paramMap.put("product_id", order.getId().toString());

        if (StringUtils.isNotBlank(paymentPlugins.getSellerKey())) {
            paramMap.put("sign", createSign(paramMap, paymentPlugins.getSellerKey().trim()));
        }

        String xmlWeChat = getRequestXml(paramMap);
        String resXml = testPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlWeChat);
        Map map = new HashMap();
        try {
            map = doXMLParse(resXml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping({"/WeChatPublicPayReturn.jspx"})
    public void WeChatPublicPayReturn(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws JDOMException, IOException, JSONException {
        String xml_receive_result = getXmlRequest(request);
        if ((StringUtils.isNotBlank(xml_receive_result)) || (xml_receive_result != "")) {
            Map result_map = doXMLParse(xml_receive_result);
            String sign_receive = (String) result_map.get("sign");
            result_map.remove("sign");
            String key = this.paymentPluginsMng.findByCode("weChatPublicPay").getSellerKey().trim();
            if (StringUtils.isNotBlank(key)) {
                String checkSign = createSign(result_map, key);
                if ((checkSign != null) && (checkSign.equals(sign_receive))) {
                    try {
                        noticeWeChatSuccess();
                        if (result_map == null) return;
                        String return_code = (String) result_map.get("return_code");
                        if ((!"SUCCESS".equals(return_code)) || (!"SUCCESS".equals(result_map.get("result_code"))))
                            return;
                        String transaction_id = (String) result_map.get("transaction_id");

                        String out_trade_no = (String) result_map.get("out_trade_no");
                        Order order = this.manager.findByCode(Long.valueOf(out_trade_no));
                        order.setPaymentStatus(Integer.valueOf(2));
                        order.setTradeNo(transaction_id);
                        order.setPaymentCode("weChatPublicPay");
                        this.manager.updateByUpdater(order);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Map parames = new HashMap();
                    parames.put("return_code", "FAIL");
                    parames.put("return_msg", "check.error");

                    String xmlWeChat = getRequestXml(parames);
                    try {
                        testPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlWeChat);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public String alipayMobile(String aliPayUrl, PaymentPlugins paymentPlugins, Website web, Order order, String deposit, String retainage, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String aliPayUrl1 = "https://openapi.alipay.com/gateway.do";

        AlipayClient alipayClient = getAlipayClient(aliPayUrl1, paymentPlugins.getPublicKey(),
                paymentPlugins.getSellerKey(), paymentPlugins.getSellerEmail(), "UTF-8");
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        alipayRequest.setReturnUrl("http://" + web.getDomain() + "/shopMember/index.jspx");

        alipayRequest.setNotifyUrl("http://" + web.getDomain() + "/alipayMobilePayReturn.jspx");
        String subject = null;
        String out_trade_no = null;
        Double total_amount = null;

        subject = order.getProductName();
        out_trade_no = order.getCode().toString().trim();
        total_amount = order.getTotal();

        alipayRequest.setBizContent("{    \"out_trade_no\":\"" +
                out_trade_no + "\"," +
                "    \"seller_id\":\"" + paymentPlugins.getPartner() + "\"," +
                "    \"subject\":\"" + subject + "\"," +
                "    \"total_amount\":" + total_amount + "," +
                "    \"timeout_express\":\"90m\"" +
                "  }");
        String form = null;
        try {
            form = ((AlipayTradeWapPayResponse) alipayClient.pageExecute(alipayRequest)).getBody();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(form);
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return form;
    }

    public static AlipayClient getAlipayClient(String aliPayUrl, String appId, String privateKey, String publicKey, String charset) {
        if (alipayClient == null) {
            alipayClient = new DefaultAlipayClient(aliPayUrl, appId,
                    privateKey, "json", charset, publicKey);
        }
        return alipayClient;
    }

    @RequestMapping({"/alipayMobilePayReturn.jspx"})
    public void alipayMobileReturndirect(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws UnsupportedEncodingException, AlipayApiException {
        Map params = new HashMap();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode("alipayMobile");
        Order order = this.manager.findByCode(Long.valueOf(out_trade_no));
        boolean signVerified = AlipaySignature.rsaCheckV1(params, paymentPlugins.getSellerEmail(), "UTF-8");
        if (signVerified)
            if (trade_status.equals("TRADE_FINISHED")) {
                order.setStatus(Integer.valueOf(2));
                order.setTradeNo(trade_no);
                this.manager.updateByUpdater(order);
                ((PrintWriter) response).write("success");
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                order.setStatus(Integer.valueOf(2));
                order.setTradeNo(trade_no);
                this.manager.updateByUpdater(order);

                ((PrintWriter) response).write("success");
            }
    }

    public String getLogisticsType(Order order) {
        String logistics_type;
        if (!StringUtils.isBlank(order.getShipping().getLogisticsType()))
            logistics_type = order.getShipping().getLogisticsType();
        else {
            logistics_type = "EXPRESS";
        }
        return logistics_type;
    }
}

