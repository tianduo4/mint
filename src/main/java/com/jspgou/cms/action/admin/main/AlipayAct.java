/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.Alipay;
/*     */ import com.jspgou.cms.entity.Logistics;
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.Payment;
/*     */ import com.jspgou.cms.entity.PaymentPlugins;
/*     */ import com.jspgou.cms.entity.Shipments;
/*     */ import com.jspgou.cms.entity.Shipping;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopShipments;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.manager.PaymentPluginsMng;
/*     */ import com.jspgou.cms.manager.ShipmentsMng;
/*     */ import com.jspgou.cms.manager.ShopShipmentsMng;
/*     */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class AlipayAct extends Alipay
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private PaymentPluginsMng paymentPluginsMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShipmentsMng shipmentMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopShipmentsMng shopShipmentsMng;
/*     */ 
/*     */   @RequestMapping({"/order/o_shipments.do"})
/*     */   public String shipments(Long ids, Shipments bean, Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  36 */     Order order = this.manager.findById(id);
/*  37 */     ShopAdmin admin = AdminThread.get();
/*  38 */     bean.setShopAdmin(admin);
/*  39 */     bean.setIndent(order);
/*  40 */     bean.setIsPrint(Boolean.valueOf(false));
/*  41 */     if (ids != null) {
/*  42 */       ShopShipments shipment = this.shopShipmentsMng.findById(ids);
/*  43 */       bean.setShippingName(shipment.getName());
/*  44 */       bean.setShippingMobile(shipment.getMobile());
/*  45 */       bean.setShippingAddress(shipment.getAddress());
/*     */     }
/*     */ 
/*  49 */     if (order.getPayment().getType().byteValue() == 1) {
/*  50 */       if ((order.getShippingStatus().intValue() == 1) && (order.getStatus().intValue() == 2) && (order.getPaymentStatus().intValue() == 2)) {
/*  51 */         shipments(bean, order, id, response);
/*     */       }
/*     */     }
/*  54 */     else if ((order.getShippingStatus().intValue() == 1) && (order.getStatus().intValue() == 2)) {
/*  55 */       shipments(bean, order, id, response);
/*     */     }
/*     */ 
/*  58 */     model.addAttribute("order", order);
/*  59 */     return "order/view";
/*     */   }
/*     */ 
/*     */   public void shipments(Shipments bean, Order order, Long id, HttpServletResponse response) {
/*  63 */     Shipments shipments = this.shipmentMng.save(bean);
/*  64 */     order.setShippingStatus(Integer.valueOf(2));
/*  65 */     this.manager.updateByUpdater(order);
/*  66 */     this.manager.updateSaleCount(id);
/*  67 */     if (order.getPaymentCode() != null) {
/*  68 */       PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode(order.getPaymentCode());
/*  69 */       if ((!StringUtils.isBlank(order.getPaymentCode())) && (order.getPaymentCode().equals("alipayPartner")))
/*     */         try {
/*  71 */           alipay(paymentPlugins, order, shipments.getWaybill());
/*     */         } catch (Exception e) {
/*  73 */           e.printStackTrace();
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String alipay(PaymentPlugins paymentPlugins, Order order, String waybill)
/*     */     throws Exception
/*     */   {
/*  86 */     String trade_no = order.getTradeNo();
/*     */ 
/*  90 */     String logistics_name = order.getShipping().getLogistics().getName();
/*     */ 
/*  94 */     String invoice_no = waybill;
/*     */ 
/*  96 */     String transport_type = order.getShipping().getLogisticsType();
/*     */ 
/* 100 */     Map sParaTemp = new HashMap();
/* 101 */     sParaTemp.put("service", "send_goods_confirm_by_platform");
/* 102 */     sParaTemp.put("partner", paymentPlugins.getPartner());
/* 103 */     sParaTemp.put("_input_charset", "utf-8");
/* 104 */     sParaTemp.put("trade_no", trade_no);
/* 105 */     sParaTemp.put("logistics_name", logistics_name);
/* 106 */     sParaTemp.put("invoice_no", invoice_no);
/* 107 */     sParaTemp.put("transport_type", transport_type);
/*     */ 
/* 110 */     String sHtmlText = buildRequest("", "", sParaTemp, paymentPlugins.getSellerKey());
/* 111 */     return sHtmlText;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.AlipayAct
 * JD-Core Version:    0.6.0
 */