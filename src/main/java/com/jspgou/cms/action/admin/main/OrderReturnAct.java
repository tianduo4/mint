package com.jspgou.cms.action.admin.main;

import com.jspgou.cms.api.PayUtils;
import com.jspgou.cms.entity.Order;
import com.jspgou.cms.entity.OrderItem;
import com.jspgou.cms.entity.OrderReturn;
import com.jspgou.cms.entity.PaymentPlugins;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductFashion;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.entity.ShopScore;
import com.jspgou.cms.manager.OrderMng;
import com.jspgou.cms.manager.OrderReturnMng;
import com.jspgou.cms.manager.PaymentPluginsMng;
import com.jspgou.cms.manager.ProductFashionMng;
import com.jspgou.cms.manager.ProductMng;
import com.jspgou.cms.manager.ShopMemberMng;
import com.jspgou.cms.manager.ShopScoreMng;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderReturnAct {
    private static final Logger log = LoggerFactory.getLogger(OrderReturnAct.class);
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";

    @Autowired
    private OrderReturnMng manager;

    @Autowired
    private ProductMng productMng;

    @Autowired
    private ProductFashionMng productFashionMng;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private ShopScoreMng shopScoreMng;

    @Autowired
    private PaymentPluginsMng paymentPluginsMng;

    @Autowired
    private OrderMng orderMng;

    @RequestMapping({"/orderReturn/v_list.do"})
    public String list(Integer status, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Pagination pagination = this.manager.getPage(status, SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("status", status);
        return "orderReturn/list";
    }

    @RequestMapping({"/orderReturn/v_view.do"})
    public String view(Long id, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("order", this.manager.findById(id).getOrder());
        return "orderReturn/view";
    }

    @RequestMapping({"/orderReturn/o_affirm.do"})
    public String affirm(Long id, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        OrderReturn entity = this.manager.findById(id);
        entity.setStatus(Integer.valueOf(2));
        this.manager.update(entity);
        model.addAttribute("order", this.manager.findById(id).getOrder());
        return "orderReturn/view";
    }

    @RequestMapping({"/orderReturn/o_sendback.do"})
    public String sendback(Long id, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        OrderReturn entity = this.manager.findById(id);
        entity.setStatus(Integer.valueOf(3));
        this.manager.update(entity);
        model.addAttribute("order", this.manager.findById(id).getOrder());
        return "orderReturn/view";
    }

    @RequestMapping({"/orderReturn/o_accomplish.do"})
    public String accomplish(Long id, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        OrderReturn entity = this.manager.findById(id);
        entity.setStatus(Integer.valueOf(7));
        Order order = this.manager.findById(id).getOrder();
        order.setStatus(Integer.valueOf(3));
        this.manager.update(entity);
        this.orderMng.updateByUpdater(order);
        model.addAttribute("order", this.manager.findById(id).getOrder());
        return "orderReturn/view";
    }

    @RequestMapping({"/orderReturn/o_reimburse.do"})
    public void reimburse(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String status = "退款转账失败！";
        try {
            OrderReturn entity = this.manager.findById(id);
            PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode("alipayToaccountTransfer");
            if (paymentPlugins != null) {
                JSONObject json = PayUtils.alipayReturn(paymentPlugins, entity);
                if (Boolean.parseBoolean(json.get("status").toString())) {
                    ShopMember shopMember = entity.getOrder().getMember();
                    shopMember.setMoney(Double.valueOf(shopMember.getFreezeScore().intValue() + entity.getMoney().doubleValue()));
                    this.shopMemberMng.update(shopMember);
                    entity.setStatus(Integer.valueOf(6));
                    this.manager.update(entity);
                    status = "success";
                } else {
                    status = "退款转账失败，错误原因：" + json.get("msg").toString();
                }
            }
            model.addAttribute("order", this.manager.findById(id).getOrder());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtils.renderJson(response, status);
    }

    public static String refund_fastpay_by_platform_pwd(Map<String, String> sParaTemp)
            throws Exception {
        sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
        sParaTemp.put("_input_charset", "UTF-8");
        String strButtonName = "退款";
        return buildForm(sParaTemp, "https://mapi.alipay.com/gateway.do?", "get", strButtonName);
    }

    public static String buildForm(Map<String, String> sParaTemp, String gateway, String strMethod, String strButtonName) {
        Map sPara = buildRequestPara(sParaTemp);
        List keys = new ArrayList(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + gateway +
                "_input_charset=" + "UTF-8" + "\" method=\"" + strMethod +
                "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

        return sbHtml.toString();
    }

    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        Map sPara = paraFilter(sParaTemp);

        String mysign = buildMysign(sPara);

        sPara.put("sign", mysign);
        sPara.put("sign_type", "MD5");

        return sPara;
    }

    public static String buildMysign(Map<String, String> sArray) {
        String prestr = createLinkString(sArray);
        prestr = prestr + (String) sArray.get("key");
        String mysign = md5(prestr);
        return mysign;
    }

    public static String md5(String text) {
        return DigestUtils.md5Hex(getContentBytes(text, "UTF-8"));
    }

    private static byte[] getContentBytes(String content, String charset) {
        if ((charset == null) || ("".equals(charset))) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
        }
        throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
    }

    public static String createLinkString(Map<String, String> params) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) params.get(key);

            if (i == keys.size() - 1)
                prestr = prestr + key + "=" + value;
            else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map result = new HashMap();

        if ((sArray == null) || (sArray.size() <= 0)) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = (String) sArray.get(key);
            if ((value == null) || (value.equals("")) || (key.equalsIgnoreCase("sign")) ||
                    (key.equalsIgnoreCase("sign_type"))) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    @RequestMapping({"/orderReturn/o_salesreturn.do"})
    public String salesreturn(Long id, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        OrderReturn entity = this.manager.findById(id);
        entity.setStatus(Integer.valueOf(5));
        ProductFashion fashion;
        for (OrderItem item : entity.getOrder().getItems()) {
            Product product = item.getProduct();
            if (item.getProductFash() != null) {
                fashion = item.getProductFash();
                fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() + item.getCount().intValue()));
                product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
                product.setSaleCount(Integer.valueOf(product.getSaleCount().intValue() - item.getCount().intValue()));
                product.setLiRun(Double.valueOf(product.getLiRun().doubleValue() - item.getCount().intValue() * (fashion.getSalePrice().doubleValue() - fashion.getCostPrice().doubleValue())));
                this.productFashionMng.update(fashion);
            } else {
                product.setLiRun(Double.valueOf(product.getLiRun().doubleValue() - item.getCount().intValue() * (product.getSalePrice().doubleValue() - product.getCostPrice().doubleValue())));
                product.setSaleCount(Integer.valueOf(product.getSaleCount().intValue() - item.getCount().intValue()));
                product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
            }
            this.productMng.updateByUpdater(product);
        }

        ShopMember member = entity.getOrder().getMember();
        member.setFreezeScore(Integer.valueOf(member.getScore().intValue() - entity.getOrder().getScore().intValue()));
        this.shopMemberMng.update(member);
        List<ShopScore> list = this.shopScoreMng.getlist(Long.toString(entity.getOrder().getCode().longValue()));
        for (ShopScore s : list) {
            this.shopScoreMng.deleteById(s.getId());
        }

        this.manager.update(entity);
        model.addAttribute("order", this.manager.findById(id).getOrder());
        return "orderReturn/view";
    }

    @RequestMapping({"/orderReturn/o_delete.do"})
    public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        OrderReturn[] beans = this.manager.deleteByIds(ids);
        for (OrderReturn bean : beans) {
            log.info("delete OrderReturn id={}", bean.getId());
        }
        return list(null, pageNo, request, model);
    }

    private WebErrors validateEdit(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        if (vldExist(id, web.getId(), errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        errors.ifEmpty(ids, "ids");
        for (Long id : ids) {
            vldExist(id, web.getId(), errors);
        }
        return errors;
    }

    private boolean vldExist(Long id, Long webId, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        OrderReturn entity = this.manager.findById(id);

        return errors.ifNotExist(entity, OrderReturn.class, id);
    }
}

