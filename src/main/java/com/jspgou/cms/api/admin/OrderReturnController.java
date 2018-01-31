/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.api.PayUtils;
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.OrderReturn;
/*     */ import com.jspgou.cms.entity.PaymentPlugins;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.OrderReturnMng;
/*     */ import com.jspgou.cms.manager.PaymentPluginsMng;
/*     */ import com.jspgou.cms.manager.ShopDictionaryMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class OrderReturnController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private OrderReturnMng orederReturnMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private PaymentPluginsMng paymentPluginsMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopDictionaryMng shopDictionaryMng;
/*     */ 
/*     */   @RequestMapping({"/orderReturn/list"})
/*     */   public void orerReturn(Integer status, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  42 */     String body = "\"\"";
/*  43 */     String message = "\"success\"";
/*  44 */     int code = 200;
/*     */     try {
/*  46 */       if (pageNo == null) {
/*  47 */         pageNo = Integer.valueOf(1);
/*     */       }
/*  49 */       if (pageSize == null) {
/*  50 */         pageSize = Integer.valueOf(10);
/*     */       }
/*  52 */       Pagination page = this.orederReturnMng.getPage(status, SimplePage.cpn(pageNo), 
/*  53 */         CookieUtils.getPageSize(request));
/*  54 */       List list = page.getList();
/*  55 */       int totalCount = page.getTotalCount();
/*  56 */       JSONArray jsonArray = new JSONArray();
/*  57 */       if (((list != null ? 1 : 0) & (list.size() > 0 ? 1 : 0)) != 0) {
/*  58 */         for (int i = 0; i < list.size(); i++) {
/*  59 */           jsonArray.put(i, ((OrderReturn)list.get(i)).convertToJson());
/*     */         }
/*     */       }
/*  62 */       body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
/*     */     }
/*     */     catch (Exception e) {
/*  65 */       e.printStackTrace();
/*  66 */       code = 100;
/*  67 */       message = "\"system exception\"";
/*     */     }
/*  69 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  70 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/orderReturn/get"})
/*     */   public void getOrderReturn(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  84 */     String body = "\"\"";
/*  85 */     String message = "\"success\"";
/*  86 */     int code = 200;
/*     */     try {
/*  88 */       WebErrors errors = WebErrors.create(request);
/*  89 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/*  91 */       if (errors.hasErrors()) {
/*  92 */         code = 202;
/*  93 */         message = "\"param error\"";
/*     */       } else {
/*  95 */         OrderReturn orderReturn = this.orederReturnMng.findById(id);
/*  96 */         if (orderReturn.getOrder() != null) {
/*  97 */           body = orderReturn.getOrder().convertToJson().toString();
/*     */         } else {
/*  99 */           code = 206;
/* 100 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 104 */       e.printStackTrace();
/* 105 */       code = 100;
/* 106 */       message = "\"system exception\"";
/*     */     }
/* 108 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 109 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/orderReturn/affirm"})
/*     */   public void affirm(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 120 */     String body = "\"\"";
/* 121 */     String message = "\"success\"";
/* 122 */     int code = 200;
/*     */     try {
/* 124 */       WebErrors errors = WebErrors.create(request);
/* 125 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/* 126 */       if (!errors.hasErrors()) {
/* 127 */         OrderReturn entity = this.orederReturnMng.findById(id);
/* 128 */         entity.setStatus(Integer.valueOf(2));
/* 129 */         this.orederReturnMng.update(entity);
/*     */       } else {
/* 131 */         code = 202;
/* 132 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 135 */       e.printStackTrace();
/* 136 */       code = 100;
/* 137 */       message = "\"system exception\"";
/*     */     }
/* 139 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 140 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/orderReturn/sendback"})
/*     */   public void sendback(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 150 */     String body = "\"\"";
/* 151 */     String message = "\"success\"";
/* 152 */     int code = 200;
/*     */     try {
/* 154 */       WebErrors errors = WebErrors.create(request);
/* 155 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/* 156 */       if (!errors.hasErrors()) {
/* 157 */         OrderReturn entity = this.orederReturnMng.findById(id);
/* 158 */         entity.setStatus(Integer.valueOf(3));
/* 159 */         this.orederReturnMng.update(entity);
/*     */       } else {
/* 161 */         code = 202;
/* 162 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 165 */       e.printStackTrace();
/* 166 */       code = 100;
/* 167 */       message = "\"system exception\"";
/*     */     }
/* 169 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 170 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/orderReturn/receive"})
/*     */   public void receive(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 180 */     String body = "\"\"";
/* 181 */     String message = "\"success\"";
/* 182 */     int code = 200;
/*     */     try {
/* 184 */       WebErrors errors = WebErrors.create(request);
/* 185 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/* 186 */       if (!errors.hasErrors()) {
/* 187 */         OrderReturn entity = this.orederReturnMng.findById(id);
/* 188 */         entity.setStatus(Integer.valueOf(5));
/* 189 */         this.orederReturnMng.update(entity);
/*     */       } else {
/* 191 */         code = 202;
/* 192 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 195 */       e.printStackTrace();
/* 196 */       code = 100;
/* 197 */       message = "\"system exception\"";
/*     */     }
/* 199 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 200 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/orderReturn/refund"})
/*     */   public void reimburse(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 210 */     String body = "\"\"";
/* 211 */     String message = "\"success\"";
/* 212 */     int code = 200;
/*     */     try {
/* 214 */       WebErrors errors = WebErrors.create(request);
/* 215 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/* 216 */       if (!errors.hasErrors()) {
/* 217 */         String status = "退款转账失败！";
/* 218 */         OrderReturn entity = this.orederReturnMng.findById(id);
/* 219 */         PaymentPlugins paymentPlugins = this.paymentPluginsMng.findByCode("alipayToaccountTransfer");
/* 220 */         if (paymentPlugins != null) {
/* 221 */           net.sf.json.JSONObject json = PayUtils.alipayReturn(paymentPlugins, entity);
/* 222 */           if (Boolean.parseBoolean(json.get("status").toString())) {
/* 223 */             ShopMember shopMember = entity.getOrder().getMember();
/* 224 */             shopMember.setMoney(Double.valueOf(shopMember.getFreezeScore().intValue() + entity.getMoney().doubleValue()));
/* 225 */             this.shopMemberMng.update(shopMember);
/* 226 */             entity.setStatus(Integer.valueOf(6));
/* 227 */             this.orederReturnMng.update(entity);
/*     */           } else {
/* 229 */             message = "\"delete error\"";
/* 230 */             code = 205;
/* 231 */             status = "退款失败，错误原因：" + json.get("msg").toString();
/* 232 */             json.put("fail", status);
/*     */           }
/* 234 */           body = json.toString();
/*     */         }
/*     */       } else {
/* 237 */         code = 202;
/* 238 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 241 */       e.printStackTrace();
/* 242 */       code = 100;
/* 243 */       message = "\"system exception\"";
/*     */     }
/* 245 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 246 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/orderReturn/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 257 */     String body = "\"\"";
/* 258 */     String message = "\"success\"";
/* 259 */     int code = 200;
/*     */     try
/*     */     {
/* 262 */       if (!StringUtils.isNotBlank(ids)) {
/* 263 */         code = 202;
/* 264 */         message = "\"param error\"";
/*     */       } else {
/* 266 */         Long[] id = null;
/* 267 */         String[] str = ids.split(",");
/* 268 */         id = new Long[str.length];
/* 269 */         for (int i = 0; i < str.length; i++) {
/* 270 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 272 */         this.orederReturnMng.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 275 */       ExceptionUtil.convertException(response, request, e);
/* 276 */       return;
/*     */     }
/* 278 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 279 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.OrderReturnController
 * JD-Core Version:    0.6.0
 */