/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.Payment;
/*     */ import com.jspgou.cms.entity.Shipping;
/*     */ import com.jspgou.cms.manager.PaymentMng;
/*     */ import com.jspgou.cms.manager.ShippingMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class PaymentController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PaymentMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ShippingMng shippingMng;
/*     */ 
/*     */   @RequestMapping({"/payment/list"})
/*     */   public void list(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  37 */     String body = "\"\"";
/*  38 */     String message = "\"success\"";
/*  39 */     int code = 200;
/*     */     try {
/*  41 */       List list = this.manager.getList(SiteUtils.getWebId(request), 
/*  42 */         true);
/*  43 */       JSONArray jsonArray = new JSONArray();
/*  44 */       if ((list != null) && (list.size() > 0)) {
/*  45 */         for (int i = 0; i < list.size(); i++) {
/*  46 */           jsonArray.put(((Payment)list.get(i)).converToJson());
/*     */         }
/*  48 */         body = jsonArray.toString();
/*     */       } else {
/*  50 */         code = 206;
/*  51 */         message = "\"object not found\"";
/*     */       }
/*     */     } catch (Exception e) {
/*  54 */       e.printStackTrace();
/*  55 */       code = 100;
/*  56 */       message = "\"system exception\"";
/*     */     }
/*  58 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  59 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/payment/get"})
/*     */   public void getPayment(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  69 */     String body = "\"\"";
/*  70 */     String message = "\"success\"";
/*  71 */     int code = 200;
/*     */     try {
/*  73 */       WebErrors errors = WebErrors.create(request);
/*  74 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*  75 */       if (!errors.hasErrors()) {
/*  76 */         JSONObject json = new JSONObject();
/*  77 */         JSONArray jsonArray = new JSONArray();
/*  78 */         Payment payment = new Payment();
/*  79 */         if (id.longValue() != 0L) {
/*  80 */           payment = this.manager.findById(id);
/*     */         }
/*  82 */         if (payment != null) {
/*  83 */           List list = this.shippingMng.getList(SiteUtils.getWebId(request), false);
/*  84 */           if ((list != null) && (list.size() > 0)) {
/*  85 */             for (int i = 0; i < list.size(); i++) {
/*  86 */               jsonArray.put(((Shipping)list.get(i)).converToJson());
/*     */             }
/*     */           }
/*  89 */           json.put("shipping", jsonArray);
/*  90 */           json.put("payment", payment.converToJson());
/*  91 */           body = json.toString();
/*     */         } else {
/*  93 */           code = 206;
/*  94 */           message = "\"object not found\"";
/*     */         }
/*     */       } else {
/*  97 */         code = 202;
/*  98 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 101 */       e.printStackTrace();
/* 102 */       code = 100;
/* 103 */       message = "\"system exception\"";
/*     */     }
/* 105 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 106 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/payment/save"})
/*     */   public void save(Payment bean, String shippingIds, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 116 */     String body = "\"\"";
/* 117 */     String message = "\"success\"";
/* 118 */     int code = 200;
/*     */     try {
/* 120 */       Website web = SiteUtils.getWeb(request);
/* 121 */       WebErrors errors = WebErrors.create(request);
/* 122 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getName(), bean.getType(), bean.getPriority(), bean.getIntroduce(), bean.getDisabled(), bean.getIsDefault() });
/* 123 */       if (!errors.hasErrors()) {
/* 124 */         if (bean.getIsDefault().booleanValue()) {
/* 125 */           List list = this.manager.getList(Long.valueOf(1L), true);
/* 126 */           for (int i = 0; i < list.size(); i++) {
/* 127 */             ((Payment)list.get(i)).setIsDefault(Boolean.valueOf(false));
/* 128 */             this.manager.update((Payment)list.get(i));
/*     */           }
/*     */         }
/*     */ 
/* 132 */         long[] shippingId = null;
/* 133 */         String[] str = shippingIds.split(",");
/* 134 */         shippingId = new long[str.length];
/* 135 */         for (int i = 0; i < str.length; i++) {
/* 136 */           shippingId[i] = Long.parseLong(str[i]);
/*     */         }
/* 138 */         bean.setWebsite(web);
/* 139 */         bean = this.manager.save(bean);
/* 140 */         this.manager.addShipping(bean, shippingId);
/*     */       } else {
/* 142 */         code = 202;
/* 143 */         message = "\"param error\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 147 */       e.printStackTrace();
/* 148 */       code = 100;
/* 149 */       message = "\"system exception\"";
/*     */     }
/* 151 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 152 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/payment/update"})
/*     */   public void update(Payment bean, String shippingIds, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 163 */     String body = "\"\"";
/* 164 */     String message = "\"success\"";
/* 165 */     int code = 200;
/*     */     try
/*     */     {
/* 168 */       WebErrors errors = WebErrors.create(request);
/* 169 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), bean.getName(), bean.getType(), bean.getPriority(), bean.getIntroduce(), bean.getDisabled(), bean.getIsDefault() });
/* 170 */       if (!errors.hasErrors()) {
/* 171 */         if (bean.getIsDefault().booleanValue()) {
/* 172 */           List list = this.manager.getList(Long.valueOf(1L), true);
/* 173 */           for (int i = 0; i < list.size(); i++) {
/* 174 */             ((Payment)list.get(i)).setIsDefault(Boolean.valueOf(false));
/* 175 */             this.manager.update((Payment)list.get(i));
/*     */           }
/*     */         }
/* 178 */         long[] shippingId = null;
/* 179 */         String[] str = shippingIds.split(",");
/* 180 */         shippingId = new long[str.length];
/* 181 */         for (int i = 0; i < str.length; i++) {
/* 182 */           shippingId[i] = Long.parseLong(str[i]);
/*     */         }
/* 184 */         bean = this.manager.update(bean);
/* 185 */         this.manager.updateShipping(bean, shippingId);
/*     */       } else {
/* 187 */         code = 202;
/* 188 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 191 */       e.printStackTrace();
/* 192 */       code = 100;
/* 193 */       message = "\"system exception\"";
/*     */     }
/* 195 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 196 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/payment/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 205 */     String body = "\"\"";
/* 206 */     String message = "\"success\"";
/* 207 */     int code = 200;
/*     */     try {
/* 209 */       WebErrors errors = WebErrors.create(request);
/* 210 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 211 */       if (!errors.hasErrors()) {
/* 212 */         Long[] id = null;
/* 213 */         String[] str = ids.split(",");
/* 214 */         id = new Long[str.length];
/* 215 */         for (int i = 0; i < str.length; i++) {
/* 216 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 218 */         this.manager.deleteByIds(id);
/*     */       } else {
/* 220 */         code = 202;
/* 221 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 224 */       ExceptionUtil.convertException(response, request, e);
/* 225 */       return;
/*     */     }
/* 227 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 228 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/payment/priority"})
/*     */   public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 238 */     String body = "\"\"";
/* 239 */     String message = "\"success\"";
/* 240 */     int code = 200;
/*     */     try {
/* 242 */       WebErrors errors = WebErrors.create(request);
/* 243 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids, priority });
/* 244 */       if (!errors.hasErrors()) {
/* 245 */         Long[] id = null;
/* 246 */         Integer[] prioritys = null;
/* 247 */         String[] str = ids.split(",");
/* 248 */         id = new Long[str.length];
/* 249 */         for (int i = 0; i < str.length; i++) {
/* 250 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 252 */         String[] str1 = priority.split(",");
/* 253 */         prioritys = new Integer[str1.length];
/* 254 */         for (int i = 0; i < str1.length; i++) {
/* 255 */           prioritys[i] = Integer.valueOf(Integer.parseInt(str1[i]));
/*     */         }
/* 257 */         this.manager.updatePriority(id, prioritys);
/*     */       } else {
/* 259 */         code = 202;
/* 260 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 263 */       ExceptionUtil.convertException(response, request, e);
/* 264 */       return;
/*     */     }
/* 266 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 267 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.PaymentController
 * JD-Core Version:    0.6.0
 */