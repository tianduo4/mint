/*     */ package com.jspgou.cms.action.admin.main;
/*     */ 
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.core.entity.Global;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ProductStatisticsAct
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private CategoryMng categoryMng;
/*     */ 
/*     */   @RequestMapping({"/productStatistics/v_productLack.do"})
/*     */   public String productLack(Integer count, Boolean status, Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  43 */     Website web = SiteUtils.getWeb(request);
/*  44 */     Global global = web.getGlobal();
/*  45 */     if (count == null) {
/*  46 */       count = global.getStockWarning();
/*     */     }
/*  48 */     Pagination pagination = this.productMng.getPageByStockWarning(web.getId(), count, status, SimplePage.cpn(pageNo), 
/*  49 */       CookieUtils.getPageSize(request));
/*  50 */     model.addAttribute("pagination", pagination);
/*  51 */     model.addAttribute("count", count);
/*  52 */     model.addAttribute("status", status);
/*  53 */     return "sale/productLack";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/productStatistics/o_updateRemind.do"})
/*     */   public String updateRemind(Boolean status, Integer count, Integer pageNo, Long productId, HttpServletRequest request, ModelMap model) {
/*  59 */     Product product = this.productMng.findById(productId);
/*  60 */     product.setLackRemind(status);
/*  61 */     this.productMng.updateByUpdater(product);
/*  62 */     if (status.booleanValue())
/*  63 */       status = Boolean.valueOf(false);
/*     */     else {
/*  65 */       status = Boolean.valueOf(true);
/*     */     }
/*  67 */     model.addAttribute("status", status);
/*  68 */     model.addAttribute("count", count);
/*  69 */     return "redirect:v_productLack.do";
/*     */   }
/*     */   @RequestMapping({"/productStatistics/o_resetSaleTop.do"})
/*     */   public String resetSaleTop(Long typeId, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  74 */     this.productMng.resetSaleTop();
/*  75 */     return productSaleTop(typeId, pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/productStatistics/v_productSaleTop.do"})
/*     */   public String productSaleTop(Long typeId, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  81 */     Website web = SiteUtils.getWeb(request);
/*  82 */     List list = this.productMng.findAll();
/*  83 */     for (int i = 0; i < list.size() - 1; i++) {
/*  84 */       for (int j = list.size() - 1; j > i; j--)
/*     */       {
/*  86 */         if (((Product)list.get(j)).getCategory().getName().equals(((Product)list.get(i)).getCategory().getName())) {
/*  87 */           list.remove(j);
/*     */         }
/*     */       }
/*     */     }
/*  91 */     Pagination pagination = this.productMng.getPage1(typeId, 4, SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/*  92 */     model.addAttribute("pagination", pagination);
/*  93 */     model.addAttribute("pageNo", pageNo);
/*  94 */     model.addAttribute("list", list);
/*  95 */     model.addAttribute("typeId", typeId);
/*  96 */     return "sale/productSaleTop";
/*     */   }
/*     */   @RequestMapping({"/productStatistics/o_resetProfitTop.do"})
/*     */   public String resetProfitTop(Long typeId, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 101 */     this.productMng.resetProfitTop();
/* 102 */     return productProfitTop(typeId, pageNo, request, model);
/*     */   }
/*     */   @RequestMapping({"/productStatistics/v_productProfitTop.do"})
/*     */   public String productProfitTop(Long typeId, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 107 */     Website web = SiteUtils.getWeb(request);
/*     */ 
/* 109 */     List list = this.productMng.findAll();
/* 110 */     for (int i = 0; i < list.size() - 1; i++) {
/* 111 */       for (int j = list.size() - 1; j > i; j--)
/*     */       {
/* 113 */         if (((Product)list.get(j)).getCategory().getName().equals(((Product)list.get(i)).getCategory().getName())) {
/* 114 */           list.remove(j);
/*     */         }
/*     */       }
/*     */     }
/* 118 */     Pagination pagination = this.productMng.getPage1(typeId, 8, SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
/* 119 */     model.addAttribute("pagination", pagination);
/* 120 */     model.addAttribute("pageNo", pageNo);
/* 121 */     model.addAttribute("list", list);
/* 122 */     model.addAttribute("typeId", typeId);
/* 123 */     return "sale/productProfitTop";
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.main.ProductStatisticsAct
 * JD-Core Version:    0.6.0
 */