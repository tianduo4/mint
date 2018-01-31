/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.Shipping;
/*     */ import com.jspgou.cms.manager.LogisticsMng;
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
/*     */ public class ShippingController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ShippingMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private LogisticsMng logisticsMng;
/*     */ 
/*     */   @RequestMapping({"/ship/list"})
/*     */   public void list(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  35 */     String body = "\"\"";
/*  36 */     String message = "\"success\"";
/*  37 */     int code = 200;
/*     */     try {
/*  39 */       List list = this.manager.getList(SiteUtils.getWebId(request), 
/*  40 */         true);
/*  41 */       JSONArray jsonArray = new JSONArray();
/*  42 */       if ((list != null) && (list.size() > 0)) {
/*  43 */         for (int i = 0; i < list.size(); i++) {
/*  44 */           jsonArray.put(((Shipping)list.get(i)).converToJson());
/*     */         }
/*  46 */         body = jsonArray.toString();
/*     */       } else {
/*  48 */         code = 206;
/*  49 */         message = "\"object not found\"";
/*     */       }
/*     */     } catch (Exception e) {
/*  52 */       e.printStackTrace();
/*  53 */       code = 100;
/*  54 */       message = "\"system exception\"";
/*     */     }
/*  56 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  57 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/ship/get"})
/*     */   public void getShip(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  66 */     String body = "\"\"";
/*  67 */     String message = "\"success\"";
/*  68 */     int code = 200;
/*     */     try {
/*  70 */       WebErrors errors = WebErrors.create(request);
/*  71 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*  72 */       if (!errors.hasErrors()) {
/*  73 */         Shipping ship = new Shipping();
/*  74 */         if (id.longValue() != 0L) {
/*  75 */           ship = this.manager.findById(id);
/*     */         }
/*  77 */         if (ship != null) {
/*  78 */           body = ship.converToJson().toString();
/*     */         } else {
/*  80 */           code = 206;
/*  81 */           message = "\"object not found\"";
/*     */         }
/*     */       } else {
/*  84 */         code = 202;
/*  85 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/*  88 */       e.printStackTrace();
/*  89 */       code = 100;
/*  90 */       message = "\"system exception\"";
/*     */     }
/*  92 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  93 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/ship/save"})
/*     */   public void save(Shipping bean, Long logisticsId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 105 */     String body = "\"\"";
/* 106 */     String message = "\"success\"";
/* 107 */     int code = 200;
/*     */     try {
/* 109 */       Website web = SiteUtils.getWeb(request);
/* 110 */       WebErrors errors = WebErrors.create(request);
/* 111 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getName(), 
/* 112 */         logisticsId, bean.getLogisticsType(), 
/* 113 */         bean.getUniformPrice(), bean.getPriority(), 
/* 114 */         bean.getDisabled(), bean.getIsDefault() });
/* 115 */       if (!errors.hasErrors()) {
/* 116 */         if (bean.getIsDefault().booleanValue()) {
/* 117 */           List list = this.manager.getList(Long.valueOf(1L), true);
/* 118 */           for (int i = 0; i < list.size(); i++) {
/* 119 */             ((Shipping)list.get(i)).setIsDefault(Boolean.valueOf(false));
/* 120 */             this.manager.update((Shipping)list.get(i));
/*     */           }
/*     */         }
/* 123 */         bean.setWebsite(web);
/* 124 */         bean.setLogistics(this.logisticsMng.findById(logisticsId));
/* 125 */         bean = this.manager.save(bean);
/*     */       } else {
/* 127 */         code = 202;
/* 128 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 131 */       e.printStackTrace();
/* 132 */       code = 100;
/* 133 */       message = "\"system exception\"";
/*     */     }
/* 135 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 136 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/ship/update"})
/*     */   public void update(Shipping bean, Long logisticsId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 147 */     String body = "\"\"";
/* 148 */     String message = "\"success\"";
/* 149 */     int code = 200;
/*     */     try {
/* 151 */       WebErrors errors = WebErrors.create(request);
/* 152 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), 
/* 153 */         bean.getName(), logisticsId, bean.getLogisticsType(), 
/* 154 */         bean.getUniformPrice(), bean.getPriority(), 
/* 155 */         bean.getDisabled(), bean.getIsDefault() });
/* 156 */       if (!errors.hasErrors()) {
/* 157 */         if (bean.getIsDefault().booleanValue()) {
/* 158 */           List list = this.manager.getList(Long.valueOf(1L), true);
/* 159 */           for (int i = 0; i < list.size(); i++) {
/* 160 */             ((Shipping)list.get(i)).setIsDefault(Boolean.valueOf(false));
/* 161 */             this.manager.update(bean);
/*     */           }
/*     */         }
/* 164 */         bean.setLogistics(this.logisticsMng.findById(logisticsId));
/* 165 */         bean = this.manager.update(bean);
/*     */       } else {
/* 167 */         code = 202;
/* 168 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 171 */       e.printStackTrace();
/* 172 */       code = 100;
/* 173 */       message = "\"system exception\"";
/*     */     }
/* 175 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 176 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/ship/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 186 */     String body = "\"\"";
/* 187 */     String message = "\"success\"";
/* 188 */     int code = 200;
/*     */     try {
/* 190 */       WebErrors errors = WebErrors.create(request);
/* 191 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 192 */       if (!errors.hasErrors()) {
/* 193 */         Long[] id = null;
/* 194 */         String[] str = ids.split(",");
/* 195 */         id = new Long[str.length];
/* 196 */         for (int i = 0; i < str.length; i++) {
/* 197 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 199 */         this.manager.deleteByIds(id);
/*     */       } else {
/* 201 */         code = 202;
/* 202 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 205 */       ExceptionUtil.convertException(response, request, e);
/* 206 */       return;
/*     */     }
/* 208 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 209 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/ship/priority"})
/*     */   public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 218 */     String body = "\"\"";
/* 219 */     String message = "\"success\"";
/* 220 */     int code = 200;
/*     */     try {
/* 222 */       WebErrors errors = WebErrors.create(request);
/* 223 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids, priority });
/* 224 */       if (!errors.hasErrors()) {
/* 225 */         Long[] id = null;
/* 226 */         Integer[] prioritys = null;
/* 227 */         String[] str = ids.split(",");
/* 228 */         id = new Long[str.length];
/* 229 */         for (int i = 0; i < str.length; i++) {
/* 230 */           id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/* 232 */         String[] str1 = priority.split(",");
/* 233 */         prioritys = new Integer[str1.length];
/* 234 */         for (int i = 0; i < str1.length; i++) {
/* 235 */           prioritys[i] = Integer.valueOf(Integer.parseInt(str1[i]));
/*     */         }
/* 237 */         this.manager.updatePriority(id, prioritys);
/*     */       } else {
/* 239 */         code = 202;
/* 240 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 243 */       ExceptionUtil.convertException(response, request, e);
/* 244 */       return;
/*     */     }
/* 246 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 247 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.ShippingController
 * JD-Core Version:    0.6.0
 */