/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.plug.weixin.entity.WeixinMessage;
/*     */ import com.jspgou.plug.weixin.manager.WeixinMessageMng;
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
/*     */ public class WeixinMessageController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private WeixinMessageMng manager;
/*     */ 
/*     */   @RequestMapping({"/weixinMessage/list"})
/*     */   public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  47 */     String body = "\"\"";
/*  48 */     String message = "\"success\"";
/*  49 */     int code = 200;
/*     */     try
/*     */     {
/*  52 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  54 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  55 */       if (errors.hasErrors()) {
/*  56 */         code = 202;
/*  57 */         message = "\"param error\"";
/*     */       } else {
/*  59 */         Pagination pagination = this.manager.getPage(SiteUtils.getWebId(request), SimplePage.cpn(pageNo), pageSize.intValue());
/*  60 */         List<WeixinMessage> messages = (List<WeixinMessage>)pagination.getList();
/*  61 */         JSONArray jsons = new JSONArray();
/*  62 */         for (WeixinMessage msg : messages) {
/*  63 */           jsons.add(msg.converToJson());
/*     */         }
/*  65 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */     } catch (Exception e) {
/*  68 */       e.printStackTrace();
/*  69 */       code = 100;
/*  70 */       message = "\"system exception\"";
/*     */     }
/*  72 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  73 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/weixinMessage/save"})
/*     */   public void save(WeixinMessage bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  84 */     String body = "\"\"";
/*  85 */     String message = "\"success\"";
/*  86 */     int code = 200;
/*     */     try {
/*  88 */       WebErrors errors = WebErrors.create(request);
/*  89 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getTitle(), bean.getNumber() });
/*     */ 
/*  91 */       if (errors.hasErrors()) {
/*  92 */         code = 202;
/*  93 */         message = "\"param error\"";
/*     */       } else {
/*  95 */         bean.setSite(SiteUtils.getWeb(request));
/*  96 */         bean.setWelcome(Boolean.valueOf(false));
/*  97 */         bean.setType(Integer.valueOf(0));
/*  98 */         this.manager.save(bean);
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
/*     */   @RequestMapping({"/weixinMessage/get"})
/*     */   public void get(Integer id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 117 */     String body = "\"\"";
/* 118 */     String message = "\"success\"";
/* 119 */     int code = 200;
/*     */     try {
/* 121 */       WeixinMessage entity = new WeixinMessage();
/* 122 */       if ((id != null) && (id.intValue() != 0)) {
/* 123 */         entity = this.manager.findById(id);
/*     */       }
/* 125 */       body = entity.converToJson().toString();
/*     */     } catch (Exception e) {
/* 127 */       e.printStackTrace();
/* 128 */       code = 100;
/* 129 */       message = "\"system exception\"";
/*     */     }
/* 131 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 132 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/weixinMessage/update"})
/*     */   public void update(WeixinMessage bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 143 */     String body = "\"\"";
/* 144 */     String message = "\"success\"";
/* 145 */     int code = 200;
/*     */     try {
/* 147 */       WebErrors errors = WebErrors.create(request);
/* 148 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), bean.getTitle(), bean.getNumber() });
/*     */ 
/* 150 */       if (errors.hasErrors()) {
/* 151 */         code = 202;
/* 152 */         message = "\"param error\"";
/*     */       } else {
/* 154 */         this.manager.update(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 157 */       e.printStackTrace();
/* 158 */       code = 100;
/* 159 */       message = "\"system exception\"";
/*     */     }
/* 161 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 162 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/weixinMessage/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 174 */     String body = "\"\"";
/* 175 */     String message = "\"success\"";
/* 176 */     int code = 200;
/*     */     try
/*     */     {
/* 180 */       if (!StringUtils.isNotBlank(ids)) {
/* 181 */         code = 202;
/* 182 */         message = "\"param error\"";
/*     */       } else {
/* 184 */         String[] str = ids.split(",");
/* 185 */         Integer[] id = new Integer[str.length];
/* 186 */         for (int i = 0; i < str.length; i++) {
/* 187 */           id[i] = Integer.valueOf(str[i]);
/*     */         }
/* 189 */         this.manager.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 192 */       ExceptionUtil.convertException(response, request, e);
/* 193 */       return;
/*     */     }
/* 195 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 196 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/weixinMessage/getDefault"})
/*     */   public void setDefault(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 206 */     String body = "\"\"";
/* 207 */     String message = "\"success\"";
/* 208 */     int code = 200;
/*     */     try {
/* 210 */       WeixinMessage msg = this.manager.getWelcome(SiteUtils.getWebId(request));
/* 211 */       if (msg != null)
/* 212 */         body = msg.converToJson().toString();
/*     */     }
/*     */     catch (Exception e) {
/* 215 */       e.printStackTrace();
/* 216 */       code = 100;
/* 217 */       message = "\"system exception\"";
/*     */     }
/* 219 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 220 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/weixinMessage/default/save"})
/*     */   public void saveDefault(WeixinMessage bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 231 */     String body = "\"\"";
/* 232 */     String message = "\"success\"";
/* 233 */     int code = 200;
/*     */     try {
/* 235 */       WebErrors errors = WebErrors.create(request);
/* 236 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getTitle() });
/*     */ 
/* 238 */       if (errors.hasErrors()) {
/* 239 */         code = 202;
/* 240 */         message = "\"param error\"";
/*     */       } else {
/* 242 */         bean.setSite(SiteUtils.getWeb(request));
/* 243 */         bean.setWelcome(Boolean.valueOf(true));
/* 244 */         this.manager.save(bean);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 248 */       e.printStackTrace();
/* 249 */       code = 100;
/* 250 */       message = "\"system exception\"";
/*     */     }
/* 252 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 253 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/weixinMessage/default/update"})
/*     */   public void updateDefault(WeixinMessage bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 264 */     String body = "\"\"";
/* 265 */     String message = "\"success\"";
/* 266 */     int code = 200;
/*     */     try {
/* 268 */       WebErrors errors = WebErrors.create(request);
/* 269 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), bean.getTitle() });
/*     */ 
/* 271 */       if (errors.hasErrors()) {
/* 272 */         code = 202;
/* 273 */         message = "\"param error\"";
/*     */       } else {
/* 275 */         this.manager.update(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 278 */       e.printStackTrace();
/* 279 */       code = 100;
/* 280 */       message = "\"system exception\"";
/*     */     }
/* 282 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 283 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.WeixinMessageController
 * JD-Core Version:    0.6.0
 */