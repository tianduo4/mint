/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.manager.OrderItemMng;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.util.DateUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.text.ParseException;
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
/*     */ public class SaleChatStatisticsController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng orderMng;
/*     */ 
/*     */   @Autowired
/*     */   private OrderItemMng orderItemMng;
/*     */ 
/*     */   @RequestMapping({"/statistics/saleChat"})
/*     */   public void chatStatistics(String type, String month, String year, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  45 */     String body = "\"\"";
/*  46 */     String message = "\"success\"";
/*  47 */     int code = 200;
/*  48 */     boolean flage = true;
/*     */     try {
/*  50 */       if (StringUtils.isNotEmpty(type)) {
/*  51 */         if ("month".equals(type)) {
/*  52 */           flage = StringUtils.isNotEmpty(month);
/*  53 */           DateUtils.pasreToDate(month, DateUtils.COMMON_FORMAT_MONTH);
/*  54 */         } else if ("year".equals(type)) {
/*  55 */           flage = StringUtils.isNotEmpty(year);
/*  56 */           DateUtils.pasreToDate(year, DateUtils.COMMON_FORMAT_YEAR);
/*     */         }
/*     */       }
/*  59 */       else flage = false;
/*     */ 
/*  61 */       if (flage) {
/*  62 */         body = this.orderMng.getOrderSale(CmsThreadVariable.getSite().getId(), type, month, year).toString();
/*     */       } else {
/*  64 */         code = 202;
/*  65 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (ParseException e) {
/*  68 */       code = 202;
/*  69 */       message = "\"param error\"";
/*     */     } catch (Exception e) {
/*  71 */       e.printStackTrace();
/*  72 */       code = 100;
/*  73 */       message = "\"system exception\"";
/*     */     }
/*  75 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  76 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/statistics/saleRank"})
/*     */   public void saleStatistics(Integer pageSize, Integer pageNo, Integer categoryId, Date startTime, Date endTime, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  93 */     String body = "\"\"";
/*  94 */     String message = "\"success\"";
/*  95 */     int code = 200;
/*     */     try {
/*  97 */       if ((pageSize != null) && (pageNo != null)) {
/*  98 */         Pagination page = this.orderItemMng.getPageProductSaleRank(CmsThreadVariable.getSite().getId(), null, categoryId, SimplePage.cpn(pageNo), pageSize.intValue(), startTime, endTime);
/*  99 */         List<Object[]> orderItems = (List<Object[]>)page.getList();
/* 100 */         JSONArray jsons = new JSONArray();
/* 101 */         for (Object[] obj : orderItems) {
/* 102 */           JSONObject json = new JSONObject();
/* 103 */           json.put("productName", obj[0] != null ? obj[0] : "");
/* 104 */           json.put("categoryName", obj[1] != null ? obj[1] : "");
/* 105 */           json.put("saleCount", obj[2] != null ? obj[2] : "");
/* 106 */           jsons.add(json);
/*     */         }
/* 108 */         body = jsons.toString() + ",\"totalCount\":" + page.getTotalCount();
/*     */       } else {
/* 110 */         code = 202;
/* 111 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 114 */       e.printStackTrace();
/* 115 */       code = 100;
/* 116 */       message = "\"system exception\"";
/*     */     }
/* 118 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 119 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.SaleChatStatisticsController
 * JD-Core Version:    0.6.0
 */