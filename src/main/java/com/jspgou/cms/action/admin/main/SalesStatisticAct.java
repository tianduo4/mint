/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class SalesStatisticAct
/*     */ {
/*  27 */   private static final Logger log = LoggerFactory.getLogger(SalesStatisticAct.class);
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng orderMng;
/*     */ 
/*  35 */   @RequestMapping({"/salesstatistic/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { List orders = this.orderMng.getCountByStatus(null, null, null);
/*  36 */     List notOrders = this.orderMng.getCountByStatus(null, null, Integer.valueOf(3));
/*  37 */     model.addAttribute("notOrders", notOrders);
/*  38 */     Integer flag = Integer.valueOf(1);
/*     */ 
/*  40 */     model.addAttribute("flag", flag);
/*  41 */     model.addAttribute("pageNo", pageNo);
/*  42 */     model.addAttribute("orders", orders);
/*     */ 
/*  44 */     return "salesstatistic/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/salesstatistic/v_sale.do"})
/*     */   public String sale(Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  53 */     List orders = this.orderMng.getCountByStatus1(null, null, null);
/*  54 */     List notOrders = this.orderMng.getCountByStatus1(null, null, Integer.valueOf(3));
/*  55 */     Integer flag = Integer.valueOf(1);
/*     */ 
/*  57 */     model.addAttribute("notOrders", notOrders);
/*  58 */     model.addAttribute("orders", orders);
/*  59 */     model.addAttribute("flag", flag);
/*  60 */     model.addAttribute("pageNo", pageNo);
/*     */ 
/*  62 */     return "salesstatistic/sale";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/salesStatistic/saleroom.do"})
/*     */   public String saleroom(Integer flag, Integer pageNo, Date startTime, Date endTime, HttpServletRequest request, ModelMap model)
/*     */   {
/*  72 */     Calendar calendar = Calendar.getInstance();
/*  73 */     if ((startTime == null) && (endTime == null)) {
/*  74 */       endTime = calendar.getTime();
/*  75 */       calendar.add(2, -1);
/*  76 */       startTime = calendar.getTime();
/*     */     }
/*     */ 
/*  79 */     if (flag.intValue() == 1) {
/*  80 */       List orders = this.orderMng.getCountByStatus1(startTime, endTime, null);
/*  81 */       model.addAttribute("orders", orders);
/*  82 */     } else if (flag.intValue() == 2)
/*     */     {
/*     */       int y;
/*     */       int y;
/*  84 */       if ((request.getParameter("year") != null) && (!request.getParameter("year").equals("")))
/*  85 */         y = Integer.parseInt(request.getParameter("year"));
/*     */       else {
/*  87 */         y = calendar.get(1);
/*     */       }
/*  89 */       List orders = this.orderMng.getStatisticByYear1(Integer.valueOf(y), null);
/*  90 */       model.addAttribute("year", Integer.valueOf(y));
/*  91 */       model.addAttribute("orders", orders);
/*     */     }
/*     */ 
/*  94 */     model.addAttribute("flag", flag);
/*  95 */     model.addAttribute("startTime", startTime);
/*  96 */     model.addAttribute("endTime", endTime);
/*     */ 
/*  98 */     return "salesstatistic/sale";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/salesStatistic/refer.do"})
/*     */   public String refer(Integer flag, Integer pageNo, Date startTime, Date endTime, HttpServletRequest request, ModelMap model)
/*     */   {
/* 110 */     Calendar calendar = Calendar.getInstance();
/*     */ 
/* 112 */     if ((startTime == null) && (endTime == null)) {
/* 113 */       endTime = calendar.getTime();
/* 114 */       calendar.add(2, -1);
/* 115 */       startTime = calendar.getTime();
/*     */     }
/*     */ 
/* 118 */     if (flag.intValue() == 1) {
/* 120 */       List orders = this.orderMng.getCountByStatus(startTime, endTime, 
/* 121 */         null);
/*     */ 
/* 123 */       for (int i = 0; i < orders.size(); i++);
/*     */     }
/* 126 */     else if (flag.intValue() == 2)
/*     */     {
/*     */       int y;
/*     */       int y;
/* 128 */       if ((request.getParameter("year") != null) && 
/* 129 */         (!request.getParameter("year").equals(""))) {
/* 130 */         y = Integer.parseInt(request.getParameter("year"));
/*     */       }
/*     */       else
/*     */       {
/* 134 */         y = calendar.get(1);
/*     */       }
/*     */ 
/* 137 */       List orders = this.orderMng.getStatisticByYear(Integer.valueOf(y), null);
/* 138 */       model.addAttribute("orders", orders);
/* 139 */       model.addAttribute("year", Integer.valueOf(y));
/*     */     }
/* 141 */     model.addAttribute("flag", flag);
/* 142 */     model.addAttribute("startTime", startTime);
/* 143 */     model.addAttribute("endTime", endTime);
/* 144 */     return "salesstatistic/list";
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.SalesStatisticAct
 * JD-Core Version:    0.6.0
 */