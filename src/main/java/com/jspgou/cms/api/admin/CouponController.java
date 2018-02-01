/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.Coupon;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.cms.manager.CouponMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.WebErrors;
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
/*     */ public class CouponController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private CouponMng couponMng;
/*     */ 
/*     */   @Autowired
/*     */   private CategoryMng categoryMng;
/*     */ 
/*     */   @RequestMapping({"/coupon/list"})
/*     */   public void list(Integer pageNo, Integer pageSize, Integer categoryId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  49 */     String body = "\"\"";
/*  50 */     String message = "\"success\"";
/*  51 */     int code = 200;
/*     */     try {
/*  53 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  55 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  56 */       if (errors.hasErrors()) {
/*  57 */         code = 202;
/*  58 */         message = "\"param error\"";
/*     */       } else {
/*  60 */         Pagination pagination = this.couponMng.getPage(pageNo.intValue(), pageSize.intValue(), categoryId);
/*  61 */         List<Coupon> coupons = (List<Coupon>)pagination.getList();
/*  62 */         JSONArray jsons = new JSONArray();
/*  63 */         for (Coupon coupon : coupons) {
/*  64 */           jsons.add(coupon.converToJson());
/*     */         }
/*  66 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/*  69 */       e.printStackTrace();
/*  70 */       code = 100;
/*  71 */       message = "\"system exception\"";
/*     */     }
/*  73 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  74 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/coupon/save"})
/*     */   public void save(Coupon coupon, Integer categoryId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  86 */     String body = "\"\"";
/*  87 */     String message = "\"success\"";
/*  88 */     int code = 200;
/*     */     try {
/*  90 */       WebErrors errors = WebErrors.create(request);
/*  91 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { coupon.getCouponName(), coupon.getCouponTime(), 
/*  92 */         coupon.getCouponEndTime(), coupon.getIsusing(), coupon.getLeastPrice(), coupon.getCouponPrice(), coupon.getCouponCount() });
/*     */ 
/*  94 */       if (errors.hasErrors()) {
/*  95 */         code = 202;
/*  96 */         message = "\"param error\"";
/*     */       } else {
/*  98 */         Website site = CmsThreadVariable.getSite();
/*  99 */         this.couponMng.save(coupon, site, categoryId);
/*     */       }
/*     */     } catch (Exception e) {
/* 102 */       e.printStackTrace();
/* 103 */       code = 100;
/* 104 */       message = "\"system exception\"";
/*     */     }
/* 106 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 107 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/coupon/get"})
/*     */   public void get(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 118 */     String body = "\"\"";
/* 119 */     String message = "\"success\"";
/* 120 */     int code = 200;
/*     */     try {
/* 122 */       WebErrors errors = WebErrors.create(request);
/* 123 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 125 */       if (errors.hasErrors()) {
/* 126 */         code = 202;
/* 127 */         message = "\"param error\"";
/*     */       } else {
/* 129 */         Coupon coupon = new Coupon();
/* 130 */         if ((id != null) && (id.longValue() != 0L)) {
/* 131 */           coupon = this.couponMng.findById(id);
/*     */         }
/* 133 */         if (coupon != null) {
/* 134 */           body = coupon.converToJson().toString();
/*     */         } else {
/* 136 */           code = 206;
/* 137 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 141 */       e.printStackTrace();
/* 142 */       code = 100;
/* 143 */       message = "\"system exception\"";
/*     */     }
/* 145 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 146 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/coupon/disabled"})
/*     */   public void update(String ids, Boolean isUsing, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 159 */     String body = "\"\"";
/* 160 */     String message = "\"success\"";
/* 161 */     int code = 200;
/*     */     try {
/* 163 */       WebErrors errors = WebErrors.create(request);
/* 164 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { isUsing, ids });
/*     */ 
/* 166 */       if (errors.hasErrors()) {
/* 167 */         code = 202;
/* 168 */         message = "\"param error\"";
/*     */       } else {
/* 170 */         String[] str = ids.split(",");
/* 171 */         for (int i = 0; i < str.length; i++) {
/* 172 */           Coupon coupon = this.couponMng.findById(Long.valueOf(Long.parseLong(str[i])));
/* 173 */           coupon.setIsusing(isUsing);
/* 174 */           this.couponMng.update(coupon);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 178 */       e.printStackTrace();
/* 179 */       code = 100;
/* 180 */       message = "\"system exception\"";
/*     */     }
/* 182 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 183 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/coupon/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 195 */     String body = "\"\"";
/* 196 */     String message = "\"success\"";
/* 197 */     int code = 200;
/*     */     try
/*     */     {
/* 200 */       if (!StringUtils.isNotBlank(ids)) {
/* 201 */         code = 202;
/* 202 */         message = "\"param error\"";
/*     */       } else {
/* 204 */         String[] str = ids.split(",");
/* 205 */         Long[] id = new Long[str.length];
/* 206 */         for (int i = 0; i < str.length; i++) {
/* 207 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 209 */         Website site = CmsThreadVariable.getSite();
/* 210 */         this.couponMng.deleteByIds(id, site.getUploadUrl());
/*     */       }
/*     */     } catch (Exception e) {
/* 213 */       ExceptionUtil.convertException(response, request, e);
/* 214 */       return;
/*     */     }
/* 216 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 217 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.CouponController
 * JD-Core Version:    0.6.0
 */