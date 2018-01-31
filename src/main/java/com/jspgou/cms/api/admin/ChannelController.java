/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.ShopChannel;
/*     */ import com.jspgou.cms.manager.ShopChannelMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletContext;
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
/*     */ public class ChannelController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ShopChannelMng shopChannelMng;
/*     */ 
/*     */   @Autowired
/*     */   private ServletContext servletContext;
/*     */ 
/*     */   @RequestMapping({"/channel/tree"})
/*     */   public void tree(Integer pid, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  43 */     String body = "\"\"";
/*  44 */     String message = "\"success\"";
/*  45 */     int code = 200;
/*     */     try {
/*  47 */       Website site = CmsThreadVariable.getSite();
/*  48 */       List list = null;
/*  49 */       if ((pid != null) && (pid.intValue() == 0))
/*  50 */         list = this.shopChannelMng.getTopList(site.getId());
/*     */       else {
/*  52 */         list = this.shopChannelMng.getChildList(site.getId(), pid);
/*     */       }
/*  54 */       JSONArray jsons = new JSONArray();
/*  55 */       for (ShopChannel channel : list) {
/*  56 */         jsons.add(channel.converToTreeJson());
/*     */       }
/*  58 */       body = jsons.toString();
/*     */     } catch (Exception e) {
/*  60 */       e.printStackTrace();
/*  61 */       code = 100;
/*  62 */       message = "\"system exception\"";
/*     */     }
/*  64 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  65 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/channel/list"})
/*     */   public void list(Integer pid, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  79 */     String body = "\"\"";
/*  80 */     String message = "\"success\"";
/*  81 */     int code = 200;
/*     */     try {
/*  83 */       Website site = CmsThreadVariable.getSite();
/*  84 */       List list = null;
/*  85 */       if ((pid != null) && (pid.intValue() == 0))
/*  86 */         list = this.shopChannelMng.getTopList(site.getId());
/*     */       else {
/*  88 */         list = this.shopChannelMng.getChildList(site.getId(), pid);
/*     */       }
/*  90 */       JSONArray jsons = new JSONArray();
/*  91 */       for (ShopChannel channel : list) {
/*  92 */         jsons.add(channel.converToJson());
/*     */       }
/*  94 */       body = jsons.toString();
/*     */     } catch (Exception e) {
/*  96 */       e.printStackTrace();
/*  97 */       code = 100;
/*  98 */       message = "\"system exception\"";
/*     */     }
/* 100 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 101 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/channel/save"})
/*     */   public void save(ShopChannel channel, Integer pid, String content, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 116 */     String body = "\"\"";
/* 117 */     String message = "\"success\"";
/* 118 */     int code = 200;
/*     */     try {
/* 120 */       WebErrors errors = WebErrors.create(request);
/* 121 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { channel.getName(), 
/* 122 */         channel.getPriority(), channel.getDisplay(), channel.getBlank() });
/*     */ 
/* 124 */       if (errors.hasErrors()) {
/* 125 */         code = 202;
/* 126 */         message = "\"param error\"";
/*     */       }
/* 129 */       else if (this.shopChannelMng.getByPath(CmsThreadVariable.getSite().getId(), channel.getPath()) == null) {
/* 130 */         channel.setWebsite(CmsThreadVariable.getSite());
/* 131 */         this.shopChannelMng.save(channel, pid, content);
/*     */       } else {
/* 133 */         code = 502;
/* 134 */         message = "\"path not repeate\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 138 */       e.printStackTrace();
/* 139 */       code = 100;
/* 140 */       message = "\"system exception\"";
/*     */     }
/* 142 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 143 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/channel/getTpl"})
/*     */   public void getTpl(Integer type, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 154 */     String body = "\"\"";
/* 155 */     String message = "\"success\"";
/* 156 */     int code = 200;
/*     */     try {
/* 158 */       WebErrors errors = WebErrors.create(request);
/* 159 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { type });
/* 160 */       if (errors.hasErrors()) {
/* 161 */         message = "\"param error\"";
/* 162 */         code = 202;
/*     */       } else {
/* 164 */         JSONObject json = new JSONObject();
/* 165 */         Website site = new Website();
/* 166 */         if ((type != null) && ((type.intValue() == 1) || (type.intValue() == 2)))
/*     */         {
/* 168 */           String tplChannelDirRel = ShopChannel.getChannelTplDirRel(site);
/* 169 */           String realChannelDir = this.servletContext.getRealPath(tplChannelDirRel);
/* 170 */           String relChannelPath = tplChannelDirRel.substring(site.getTemplateRel(request).length());
/* 171 */           String[] channelTpls = ShopChannel.getChannelTpls(type, realChannelDir, relChannelPath);
/* 172 */           json.put("channelTpls", channelTpls);
/*     */ 
/* 174 */           if (type.intValue() == 2)
/*     */           {
/* 176 */             String tplContentDirRel = ShopChannel.getContentTplDirRel(site);
/* 177 */             String realContentDir = this.servletContext.getRealPath(tplContentDirRel);
/* 178 */             String relContentPath = tplContentDirRel.substring(site.getTemplateRel(request).length());
/* 179 */             String[] contentTpls = ShopChannel.getContentTpls(type, realContentDir, relContentPath);
/* 180 */             json.put("contentTpls", contentTpls);
/*     */           }
/* 182 */           body = json.toString();
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 186 */       e.printStackTrace();
/* 187 */       code = 100;
/* 188 */       message = "\"system exception\"";
/*     */     }
/* 190 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 191 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/channel/get"})
/*     */   public void get(Integer id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 203 */     String body = "\"\"";
/* 204 */     String message = "\"success\"";
/* 205 */     int code = 200;
/*     */     try {
/* 207 */       WebErrors errors = WebErrors.create(request);
/* 208 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 210 */       if (errors.hasErrors()) {
/* 211 */         code = 202;
/* 212 */         message = "\"param error\"";
/*     */       } else {
/* 214 */         ShopChannel shopChannel = new ShopChannel();
/* 215 */         if ((id != null) && (id.intValue() != 0)) {
/* 216 */           shopChannel = this.shopChannelMng.findById(id);
/*     */         }
/* 218 */         if (shopChannel != null) {
/* 219 */           body = shopChannel.converToJson().toString();
/*     */         } else {
/* 221 */           code = 206;
/* 222 */           message = "\"object not found\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 226 */       e.printStackTrace();
/* 227 */       code = 100;
/* 228 */       message = "\"system exception\"";
/*     */     }
/* 230 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 231 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/channel/getParentList"})
/*     */   public void getParentList(Integer id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 242 */     String body = "\"\"";
/* 243 */     String message = "\"success\"";
/* 244 */     int code = 200;
/*     */     try {
/* 246 */       WebErrors errors = WebErrors.create(request);
/* 247 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/*     */ 
/* 249 */       if (errors.hasErrors()) {
/* 250 */         code = 202;
/* 251 */         message = "\"param error\"";
/*     */       } else {
/* 253 */         Website site = CmsThreadVariable.getSite();
/* 254 */         List list = null;
/* 255 */         if ((id != null) && (id.intValue() == 0))
/* 256 */           list = this.shopChannelMng.getTopList(CmsThreadVariable.getSite().getId());
/*     */         else {
/* 258 */           list = this.shopChannelMng.getListForParent(site.getId(), id);
/*     */         }
/* 260 */         JSONArray jsons = new JSONArray();
/* 261 */         for (ShopChannel channel : list) {
/* 262 */           jsons.add(channel.converToTreeJson());
/*     */         }
/* 264 */         body = jsons.toString();
/*     */       }
/*     */     } catch (Exception e) {
/* 267 */       e.printStackTrace();
/* 268 */       code = 100;
/* 269 */       message = "\"system exception\"";
/*     */     }
/* 271 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 272 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/channel/update"})
/*     */   public void update(ShopChannel channel, Integer pid, String content, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 286 */     String body = "\"\"";
/* 287 */     String message = "\"success\"";
/* 288 */     int code = 200;
/*     */     try {
/* 290 */       WebErrors errors = WebErrors.create(request);
/* 291 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { channel.getName(), channel.getPath(), 
/* 292 */         channel.getPriority(), channel.getDisplay(), channel.getBlank() });
/*     */ 
/* 294 */       if (errors.hasErrors()) {
/* 295 */         code = 202;
/* 296 */         message = "\"param error\"";
/*     */       }
/* 299 */       else if (this.shopChannelMng.getByPath(CmsThreadVariable.getSite().getId(), channel.getPath()) == null) {
/* 300 */         this.shopChannelMng.update(channel, pid, content);
/*     */       } else {
/* 302 */         code = 502;
/* 303 */         message = "\"path not repeate\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 307 */       e.printStackTrace();
/* 308 */       code = 100;
/* 309 */       message = "\"system exception\"";
/*     */     }
/* 311 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 312 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/channel/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 324 */     String body = "\"\"";
/* 325 */     String message = "\"success\"";
/* 326 */     int code = 200;
/*     */     try
/*     */     {
/* 329 */       if (!StringUtils.isNotBlank(ids)) {
/* 330 */         code = 202;
/* 331 */         message = "\"param error\"";
/*     */       } else {
/* 333 */         String[] str = ids.split(",");
/* 334 */         Integer[] id = new Integer[str.length];
/* 335 */         for (int i = 0; i < str.length; i++) {
/* 336 */           id[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */         }
/* 338 */         this.shopChannelMng.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 341 */       ExceptionUtil.convertException(response, request, e);
/* 342 */       return;
/*     */     }
/* 344 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 345 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/channel/updateSort"})
/*     */   public void updateSort(String ids, String prioritys, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 359 */     String body = "\"\"";
/* 360 */     String message = "\"success\"";
/* 361 */     int code = 200;
/*     */     try {
/* 363 */       WebErrors errors = WebErrors.create(request);
/* 364 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids, prioritys });
/*     */ 
/* 366 */       if (errors.hasErrors()) {
/* 367 */         code = 202;
/* 368 */         message = "\"param error\"";
/*     */       } else {
/* 370 */         String[] str = ids.split(",");
/* 371 */         String[] pro = prioritys.split(",");
/* 372 */         Integer[] id = new Integer[str.length];
/* 373 */         Integer[] priority = new Integer[pro.length];
/* 374 */         for (int i = 0; i < str.length; i++) {
/* 375 */           id[i] = Integer.valueOf(Integer.parseInt(str[i]));
/* 376 */           priority[i] = Integer.valueOf(Integer.parseInt(pro[i]));
/*     */         }
/* 378 */         this.shopChannelMng.updatePriority(id, priority);
/*     */       }
/*     */     }
/*     */     catch (IndexOutOfBoundsException e) {
/* 382 */       code = 202;
/* 383 */       message = "\"param error\"";
/*     */     } catch (Exception e) {
/* 385 */       e.printStackTrace();
/* 386 */       code = 100;
/* 387 */       message = "\"system exception\"";
/*     */     }
/* 389 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 390 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.ChannelController
 * JD-Core Version:    0.6.0
 */