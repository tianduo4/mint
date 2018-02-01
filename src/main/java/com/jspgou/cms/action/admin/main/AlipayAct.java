package com.jspgou.cms.action.admin.main;

import com.jspgou.cms.Alipay;
import com.jspgou.cms.entity.Logistics;
import com.jspgou.cms.entity.Order;
import com.jspgou.cms.entity.Payment;
import com.jspgou.cms.entity.PaymentPlugins;
import com.jspgou.cms.entity.Shipments;
import com.jspgou.cms.entity.Shipping;
import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.entity.ShopShipments;
import com.jspgou.cms.manager.OrderMng;
import com.jspgou.cms.manager.PaymentPluginsMng;
import com.jspgou.cms.manager.ShipmentsMng;
import com.jspgou.cms.manager.ShopShipmentsMng;
import com.jspgou.cms.web.threadvariable.AdminThread;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AlipayAct extends Alipay {

    @Autowired
    private OrderMng manager;

    @Autowired
    private PaymentPluginsMng paymentPluginsMng;

    @Autowired
    private ShipmentsMng shipmentMng;

    @Autowired
    private ShopShipmentsMng shopShipmentsMng;

    @RequestMapping({"/order/o_shipments.do"})
    public String shipments(Long ids, Shipments bean, Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Order order = this.manager.findById(id);
        ShopAdmin admin = AdminThread.get();
        bean.setShopAdmin(admin);
        bean.setIndent(order);
        bean.setIsPrint(Boolean.valueOf(false));
        if (ids != null) {
            ShopShipments shipment = this.shopShipmentsMng.findById(ids);
            bean.setShippingName(shipment.getName());
            bean.setShippingMobile(shipment.getMobile());
            bean.setShippingAddress(shipment.getAddress());
        }

        if (order.getPayment().getType().byteValue() == 1) {
            if ((order.getShippingStatus().intValue() == 1) && (order.getStatus().intValue() == 2) && (order.getPaymentStatus().intValue() == 2)) {
                shipments(bean, order, id, response);
            }
        } else if ((order.getShippingStatus().intValue() == 1) && (order.getStatus().intValue() == 2)) {
            shipments(bean, order, id, response);
        }

        model.addAttribute("order", order);
        return "order/view";
    }

    public void shipments(Shipments bean, Order order, Long id, HttpServletResponse response) {
        Shipments shipments = this.shipmentMng.save(bean);
        order.setShippingStatus(Integer.valueOf(2));
        this.manager.updateByUpdater(order);
        this.manager.updateSaleCount(id);
        if (order.getPaymentCode() != null) {
            PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode(order.getPaymentCode());
            if ((!StringUtils.isBlank(order.getPaymentCode())) && (order.getPaymentCode().equals("alipayPartner")))
                try {
                    alipay(paymentPlugins, order, shipments.getWaybill());
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    private String alipay(PaymentPlugins paymentPlugins, Order order, String waybill)
            throws Exception {
        String trade_no = order.getTradeNo();

        String logistics_name = order.getShipping().getLogistics().getName();

        String invoice_no = waybill;

        String transport_type = order.getShipping().getLogisticsType();

        Map sParaTemp = new HashMap();
        sParaTemp.put("service", "send_goods_confirm_by_platform");
        sParaTemp.put("partner", paymentPlugins.getPartner());
        sParaTemp.put("_input_charset", "utf-8");
        sParaTemp.put("trade_no", trade_no);
        sParaTemp.put("logistics_name", logistics_name);
        sParaTemp.put("invoice_no", invoice_no);
        sParaTemp.put("transport_type", transport_type);

        String sHtmlText = buildRequest("", "", sParaTemp, paymentPlugins.getSellerKey());
        return sHtmlText;
    }
}

