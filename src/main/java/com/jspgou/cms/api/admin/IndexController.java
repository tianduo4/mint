/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.entity.ApiUserLogin;
/*     */ import com.jspgou.cms.manager.ConsultMng;
/*     */ import com.jspgou.cms.manager.OrderItemMng;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.util.DateUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class IndexController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng orderMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private ConsultMng consultMng;
/*     */ 
/*     */   @Autowired
/*     */   private OrderItemMng orderItemMng;
/*     */ 
/*     */   @RequestMapping({"/index/getSystemMemory"})
/*     */   public void getSystemMemory(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  49 */     String body = "\"\"";
/*  50 */     String message = "\"success\"";
/*  51 */     int code = 200;
/*     */     try {
/*  53 */       JSONObject json = new JSONObject();
/*  54 */       json.put("lastLoginTime", DateUtils.parseDate(CmsThreadVariable.getApiUserLogin().getLoginTime(), DateUtils.COMMON_FORMAT_STR));
/*  55 */       json.put("maxMemory", Long.valueOf(Runtime.getRuntime().maxMemory() / 1024L / 1024L));
/*  56 */       json.put("freeMemory", Long.valueOf(Runtime.getRuntime().freeMemory() / 1024L / 1024L));
/*  57 */       json.put("useMemory", Long.valueOf(Runtime.getRuntime().totalMemory() / 1024L / 1024L - Runtime.getRuntime().freeMemory() / 1024L / 1024L));
/*  58 */       body = json.toString();
/*     */     } catch (Exception e) {
/*  60 */       e.printStackTrace();
/*  61 */       code = 100;
/*  62 */       message = "\"system exception\"";
/*     */     }
/*  64 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  65 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/index/statistics"})
/*     */   public void statistics(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  75 */     String body = "\"\"";
/*  76 */     String message = "\"success\"";
/*  77 */     int code = 200;
/*     */     try {
/*  79 */       Date todayDate = new Date();
/*  80 */       JSONObject json = new JSONObject();
/*  81 */       Long siteId = CmsThreadVariable.getSite().getId();
/*  82 */       json.put("orderCount", this.orderMng.getOrderCount(todayDate, siteId));
/*  83 */       json.put("orderSale", this.orderMng.getOrderSale(todayDate, siteId));
/*  84 */       json.put("memberCount", this.shopMemberMng.getMemberCount(siteId));
/*  85 */       json.put("productCount", this.productMng.getProductCount(CmsThreadVariable.getSite().getId()));
/*  86 */       body = json.toString();
/*     */     } catch (Exception e) {
/*  88 */       e.printStackTrace();
/*  89 */       code = 100;
/*  90 */       message = "\"system exception\"";
/*     */     }
/*  92 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  93 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/index/unDealStatistics"})
/*     */   public void unDealStatistics(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 103 */     String body = "\"\"";
/* 104 */     String message = "\"success\"";
/* 105 */     int code = 200;
/*     */     try {
/* 107 */       JSONObject json = new JSONObject();
/* 108 */       Long siteId = CmsThreadVariable.getSite().getId();
/* 109 */       json.put("unSendOrderCount", this.orderMng.getUnSendOrderCount(siteId));
/* 110 */       json.put("unPayOrderCount", this.orderMng.getUnPayOrderCount(siteId));
/* 111 */       json.put("retrunCount", this.orderMng.getReturnCount(siteId));
/* 112 */       json.put("overStockProductCount", this.productMng.getOverStock(siteId));
/* 113 */       json.put("productConsultCount", this.consultMng.getProductConsult());
/* 114 */       body = json.toString();
/*     */     } catch (Exception e) {
/* 116 */       e.printStackTrace();
/* 117 */       code = 100;
/* 118 */       message = "\"system exception\"";
/*     */     }
/* 120 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 121 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/index/saleStatistics"})
/*     */   public void saleStatistics(String type, Integer size, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 132 */     String body = "\"\"";
/* 133 */     String message = "\"success\"";
/* 134 */     int code = 200;
/*     */     try {
/* 136 */       if ((type != null) && (size != null)) {
/* 137 */         Pagination page = this.orderItemMng.getPageProductSaleRank(CmsThreadVariable.getSite().getId(), type, null, 1, size.intValue(), null, null);
/* 138 */         List orderItems = page.getList();
/* 139 */         JSONArray jsons = new JSONArray();
/* 140 */         for (Object[] obj : orderItems) {
/* 141 */           JSONObject json = new JSONObject();
/* 142 */           json.put("productName", obj[0] != null ? obj[0] : "");
/* 143 */           json.put("categoryName", obj[1] != null ? obj[1] : "");
/* 144 */           json.put("saleCount", obj[2] != null ? obj[2] : "");
/* 145 */           jsons.add(json);
/*     */         }
/* 147 */         body = jsons.toString();
/*     */       } else {
/* 149 */         code = 202;
/* 150 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 153 */       e.printStackTrace();
/* 154 */       code = 100;
/* 155 */       message = "\"system exception\"";
/*     */     }
/* 157 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 158 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/index/chatStatistics"})
/*     */   public void chatStatistics(String type, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 169 */     String body = "\"\"";
/* 170 */     String message = "\"success\"";
/* 171 */     int code = 200;
/*     */     try {
/* 173 */       if (StringUtils.isNotEmpty(type)) {
/* 174 */         body = this.orderMng.getOrderSale(CmsThreadVariable.getSite().getId(), type, null, null).toString();
/*     */       } else {
/* 176 */         code = 202;
/* 177 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 180 */       e.printStackTrace();
/* 181 */       code = 100;
/* 182 */       message = "\"system exception\"";
/*     */     }
/* 184 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 185 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.IndexController
 * JD-Core Version:    0.6.0
 */