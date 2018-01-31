/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.api.PayUtils;
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.OrderItem;
/*     */ import com.jspgou.cms.entity.OrderReturn;
/*     */ import com.jspgou.cms.entity.PaymentPlugins;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopScore;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.manager.OrderReturnMng;
/*     */ import com.jspgou.cms.manager.PaymentPluginsMng;
/*     */ import com.jspgou.cms.manager.ProductFashionMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.manager.ShopScoreMng;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class OrderReturnAct
/*     */ {
/*  66 */   private static final Logger log = LoggerFactory.getLogger(OrderReturnAct.class);
/*     */   private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
/*     */ 
/*     */   @Autowired
/*     */   private OrderReturnMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionMng productFashionMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopScoreMng shopScoreMng;
/*     */ 
/*     */   @Autowired
/*     */   private PaymentPluginsMng paymentPluginsMng;
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng orderMng;
/*     */ 
/*     */   @RequestMapping({"/orderReturn/v_list.do"})
/*     */   public String list(Integer status, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  70 */     Pagination pagination = this.manager.getPage(status, SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/*  71 */     model.addAttribute("pagination", pagination);
/*  72 */     model.addAttribute("status", status);
/*  73 */     return "orderReturn/list";
/*     */   }
/*     */   @RequestMapping({"/orderReturn/v_view.do"})
/*     */   public String view(Long id, HttpServletRequest request, ModelMap model) {
/*  78 */     WebErrors errors = validateEdit(id, request);
/*  79 */     if (errors.hasErrors()) {
/*  80 */       return errors.showErrorPage(model);
/*     */     }
/*  82 */     model.addAttribute("order", this.manager.findById(id).getOrder());
/*  83 */     return "orderReturn/view";
/*     */   }
/*     */   @RequestMapping({"/orderReturn/o_affirm.do"})
/*     */   public String affirm(Long id, HttpServletRequest request, ModelMap model) {
/*  88 */     WebErrors errors = validateEdit(id, request);
/*  89 */     if (errors.hasErrors()) {
/*  90 */       return errors.showErrorPage(model);
/*     */     }
/*  92 */     OrderReturn entity = this.manager.findById(id);
/*  93 */     entity.setStatus(Integer.valueOf(2));
/*  94 */     this.manager.update(entity);
/*  95 */     model.addAttribute("order", this.manager.findById(id).getOrder());
/*  96 */     return "orderReturn/view";
/*     */   }
/*     */   @RequestMapping({"/orderReturn/o_sendback.do"})
/*     */   public String sendback(Long id, HttpServletRequest request, ModelMap model) {
/* 101 */     WebErrors errors = validateEdit(id, request);
/* 102 */     if (errors.hasErrors()) {
/* 103 */       return errors.showErrorPage(model);
/*     */     }
/* 105 */     OrderReturn entity = this.manager.findById(id);
/* 106 */     entity.setStatus(Integer.valueOf(3));
/* 107 */     this.manager.update(entity);
/* 108 */     model.addAttribute("order", this.manager.findById(id).getOrder());
/* 109 */     return "orderReturn/view";
/*     */   }
/*     */   @RequestMapping({"/orderReturn/o_accomplish.do"})
/*     */   public String accomplish(Long id, HttpServletRequest request, ModelMap model) {
/* 114 */     WebErrors errors = validateEdit(id, request);
/* 115 */     if (errors.hasErrors()) {
/* 116 */       return errors.showErrorPage(model);
/*     */     }
/* 118 */     OrderReturn entity = this.manager.findById(id);
/* 119 */     entity.setStatus(Integer.valueOf(7));
/* 120 */     Order order = this.manager.findById(id).getOrder();
/* 121 */     order.setStatus(Integer.valueOf(3));
/* 122 */     this.manager.update(entity);
/* 123 */     this.orderMng.updateByUpdater(order);
/* 124 */     model.addAttribute("order", this.manager.findById(id).getOrder());
/* 125 */     return "orderReturn/view";
/*     */   }
/*     */   @RequestMapping({"/orderReturn/o_reimburse.do"})
/*     */   public void reimburse(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 130 */     String status = "退款转账失败！";
/*     */     try {
/* 132 */       OrderReturn entity = this.manager.findById(id);
/* 133 */       PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode("alipayToaccountTransfer");
/* 134 */       if (paymentPlugins != null) {
/* 135 */         JSONObject json = PayUtils.alipayReturn(paymentPlugins, entity);
/* 136 */         if (Boolean.parseBoolean(json.get("status").toString())) {
/* 137 */           ShopMember shopMember = entity.getOrder().getMember();
/* 138 */           shopMember.setMoney(Double.valueOf(shopMember.getFreezeScore().intValue() + entity.getMoney().doubleValue()));
/* 139 */           this.shopMemberMng.update(shopMember);
/* 140 */           entity.setStatus(Integer.valueOf(6));
/* 141 */           this.manager.update(entity);
/* 142 */           status = "success";
/*     */         } else {
/* 144 */           status = "退款转账失败，错误原因：" + json.get("msg").toString();
/*     */         }
/*     */       }
/* 147 */       model.addAttribute("order", this.manager.findById(id).getOrder());
/*     */     } catch (Exception e) {
/* 149 */       e.printStackTrace();
/*     */     }
/* 151 */     ResponseUtils.renderJson(response, status);
/*     */   }
/*     */ 
/*     */   public static String refund_fastpay_by_platform_pwd(Map<String, String> sParaTemp)
/*     */     throws Exception
/*     */   {
/* 168 */     sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
/* 169 */     sParaTemp.put("_input_charset", "UTF-8");
/* 170 */     String strButtonName = "退款";
/* 171 */     return buildForm(sParaTemp, "https://mapi.alipay.com/gateway.do?", "get", strButtonName);
/*     */   }
/*     */ 
/*     */   public static String buildForm(Map<String, String> sParaTemp, String gateway, String strMethod, String strButtonName)
/*     */   {
/* 185 */     Map sPara = buildRequestPara(sParaTemp);
/* 186 */     List keys = new ArrayList(sPara.keySet());
/*     */ 
/* 188 */     StringBuffer sbHtml = new StringBuffer();
/*     */ 
/* 190 */     sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + gateway + 
/* 191 */       "_input_charset=" + "UTF-8" + "\" method=\"" + strMethod + 
/* 192 */       "\">");
/*     */ 
/* 194 */     for (int i = 0; i < keys.size(); i++) {
/* 195 */       String name = (String)keys.get(i);
/* 196 */       String value = (String)sPara.get(name);
/*     */ 
/* 198 */       sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
/*     */     }
/*     */ 
/* 202 */     sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
/* 203 */     sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
/*     */ 
/* 205 */     return sbHtml.toString();
/*     */   }
/*     */ 
/*     */   private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp)
/*     */   {
/* 215 */     Map sPara = paraFilter(sParaTemp);
/*     */ 
/* 217 */     String mysign = buildMysign(sPara);
/*     */ 
/* 220 */     sPara.put("sign", mysign);
/* 221 */     sPara.put("sign_type", "MD5");
/*     */ 
/* 223 */     return sPara;
/*     */   }
/*     */ 
/*     */   public static String buildMysign(Map<String, String> sArray) {
/* 227 */     String prestr = createLinkString(sArray);
/* 228 */     prestr = prestr + (String)sArray.get("key");
/* 229 */     String mysign = md5(prestr);
/* 230 */     return mysign;
/*     */   }
/*     */ 
/*     */   public static String md5(String text)
/*     */   {
/* 235 */     return DigestUtils.md5Hex(getContentBytes(text, "UTF-8"));
/*     */   }
/*     */ 
/*     */   private static byte[] getContentBytes(String content, String charset)
/*     */   {
/* 240 */     if ((charset == null) || ("".equals(charset))) {
/* 241 */       return content.getBytes();
/*     */     }
/*     */     try
/*     */     {
/* 245 */       return content.getBytes(charset); } catch (UnsupportedEncodingException e) {
/*     */     }
/* 247 */     throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
/*     */   }
/*     */ 
/*     */   public static String createLinkString(Map<String, String> params)
/*     */   {
/* 258 */     List keys = new ArrayList(params.keySet());
/* 259 */     Collections.sort(keys);
/*     */ 
/* 261 */     String prestr = "";
/*     */ 
/* 263 */     for (int i = 0; i < keys.size(); i++) {
/* 264 */       String key = (String)keys.get(i);
/* 265 */       String value = (String)params.get(key);
/*     */ 
/* 267 */       if (i == keys.size() - 1)
/* 268 */         prestr = prestr + key + "=" + value;
/*     */       else {
/* 270 */         prestr = prestr + key + "=" + value + "&";
/*     */       }
/*     */     }
/*     */ 
/* 274 */     return prestr;
/*     */   }
/*     */ 
/*     */   public static Map<String, String> paraFilter(Map<String, String> sArray)
/*     */   {
/* 284 */     Map result = new HashMap();
/*     */ 
/* 286 */     if ((sArray == null) || (sArray.size() <= 0)) {
/* 287 */       return result;
/*     */     }
/*     */ 
/* 290 */     for (String key : sArray.keySet()) {
/* 291 */       String value = (String)sArray.get(key);
/* 292 */       if ((value == null) || (value.equals("")) || (key.equalsIgnoreCase("sign")) || 
/* 293 */         (key.equalsIgnoreCase("sign_type"))) {
/*     */         continue;
/*     */       }
/* 296 */       result.put(key, value);
/*     */     }
/*     */ 
/* 299 */     return result;
/*     */   }
/*     */   @RequestMapping({"/orderReturn/o_salesreturn.do"})
/*     */   public String salesreturn(Long id, HttpServletRequest request, ModelMap model) {
/* 304 */     WebErrors errors = validateEdit(id, request);
/* 305 */     if (errors.hasErrors()) {
/* 306 */       return errors.showErrorPage(model);
/*     */     }
/* 308 */     OrderReturn entity = this.manager.findById(id);
/* 309 */     entity.setStatus(Integer.valueOf(5));
/*     */     ProductFashion fashion;
/* 312 */     for (OrderItem item : entity.getOrder().getItems()) {
/* 313 */       Product product = item.getProduct();
/* 314 */       if (item.getProductFash() != null) {
/* 315 */         fashion = item.getProductFash();
/* 316 */         fashion.setStockCount(Integer.valueOf(fashion.getStockCount().intValue() + item.getCount().intValue()));
/* 317 */         product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/* 318 */         product.setSaleCount(Integer.valueOf(product.getSaleCount().intValue() - item.getCount().intValue()));
/* 319 */         product.setLiRun(Double.valueOf(product.getLiRun().doubleValue() - item.getCount().intValue() * (fashion.getSalePrice().doubleValue() - fashion.getCostPrice().doubleValue())));
/* 320 */         this.productFashionMng.update(fashion);
/*     */       } else {
/* 322 */         product.setLiRun(Double.valueOf(product.getLiRun().doubleValue() - item.getCount().intValue() * (product.getSalePrice().doubleValue() - product.getCostPrice().doubleValue())));
/* 323 */         product.setSaleCount(Integer.valueOf(product.getSaleCount().intValue() - item.getCount().intValue()));
/* 324 */         product.setStockCount(Integer.valueOf(product.getStockCount().intValue() + item.getCount().intValue()));
/*     */       }
/* 326 */       this.productMng.updateByUpdater(product);
/*     */     }
/*     */ 
/* 329 */     ShopMember member = entity.getOrder().getMember();
/* 330 */     member.setFreezeScore(Integer.valueOf(member.getScore().intValue() - entity.getOrder().getScore().intValue()));
/* 331 */     this.shopMemberMng.update(member);
/* 332 */     List list = this.shopScoreMng.getlist(Long.toString(entity.getOrder().getCode().longValue()));
/* 333 */     for (ShopScore s : list) {
/* 334 */       this.shopScoreMng.deleteById(s.getId());
/*     */     }
/*     */ 
/* 337 */     this.manager.update(entity);
/* 338 */     model.addAttribute("order", this.manager.findById(id).getOrder());
/* 339 */     return "orderReturn/view";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/orderReturn/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 345 */     WebErrors errors = validateDelete(ids, request);
/* 346 */     if (errors.hasErrors()) {
/* 347 */       return errors.showErrorPage(model);
/*     */     }
/* 349 */     OrderReturn[] beans = this.manager.deleteByIds(ids);
/* 350 */     for (OrderReturn bean : beans) {
/* 351 */       log.info("delete OrderReturn id={}", bean.getId());
/*     */     }
/* 353 */     return list(null, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request)
/*     */   {
/* 358 */     WebErrors errors = WebErrors.create(request);
/* 359 */     Website web = SiteUtils.getWeb(request);
/* 360 */     if (vldExist(id, web.getId(), errors)) {
/* 361 */       return errors;
/*     */     }
/* 363 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request)
/*     */   {
/* 368 */     WebErrors errors = WebErrors.create(request);
/* 369 */     Website web = SiteUtils.getWeb(request);
/* 370 */     errors.ifEmpty(ids, "ids");
/* 371 */     for (Long id : ids) {
/* 372 */       vldExist(id, web.getId(), errors);
/*     */     }
/* 374 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, Long webId, WebErrors errors) {
/* 378 */     if (errors.ifNull(id, "id")) {
/* 379 */       return true;
/*     */     }
/* 381 */     OrderReturn entity = this.manager.findById(id);
/*     */ 
/* 383 */     return errors.ifNotExist(entity, OrderReturn.class, id);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.OrderReturnAct
 * JD-Core Version:    0.6.0
 */