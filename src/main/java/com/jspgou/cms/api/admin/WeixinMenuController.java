/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.service.WeixinSvc;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.plug.weixin.entity.WeixinMenu;
/*     */ import com.jspgou.plug.weixin.manager.WeixinMenuMng;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class WeixinMenuController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private WeixinMenuMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private WeixinSvc weixinSvc;
/*     */ 
/*     */   @RequestMapping({"/weixinMenu/list"})
/*     */   public void list(Integer parentId, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  49 */     String body = "\"\"";
/*  50 */     String message = "\"success\"";
/*  51 */     int code = 200;
/*     */     try
/*     */     {
/*  54 */       WebErrors errors = WebErrors.create(request);
/*     */ 
/*  56 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { pageNo, pageSize });
/*  57 */       if (errors.hasErrors()) {
/*  58 */         code = 202;
/*  59 */         message = "\"param error\"";
/*     */       } else {
/*  61 */         Pagination pagination = this.manager.getPage(SiteUtils.getWebId(request), parentId, SimplePage.cpn(pageNo), pageSize.intValue());
/*  62 */         List<WeixinMenu> menus = (List<WeixinMenu> )pagination.getList();
/*  63 */         JSONArray jsons = new JSONArray();
/*  64 */         for (WeixinMenu menu : menus) {
/*  65 */           jsons.add(menu.converToJson());
/*     */         }
/*  67 */         body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  77 */       e.printStackTrace();
/*  78 */       code = 100;
/*  79 */       message = "\"system exception\"";
/*     */     }
/*  81 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  82 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/weixinMenu/save"})
/*     */   public void save(WeixinMenu bean, Integer parentId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  93 */     String body = "\"\"";
/*  94 */     String message = "\"success\"";
/*  95 */     int code = 200;
/*     */     try {
/*  97 */       WebErrors errors = WebErrors.create(request);
/*  98 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getName() });
/*     */ 
/* 100 */       if (errors.hasErrors()) {
/* 101 */         code = 202;
/* 102 */         message = "\"param error\"";
/*     */       } else {
/* 104 */         bean.setSite(SiteUtils.getWeb(request));
/* 105 */         if (parentId != null) {
/* 106 */           WeixinMenu parent = this.manager.findById(parentId);
/* 107 */           bean.setParent(parent);
/*     */         }
/* 109 */         this.manager.save(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 112 */       e.printStackTrace();
/* 113 */       code = 100;
/* 114 */       message = "\"system exception\"";
/*     */     }
/* 116 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 117 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/weixinMenu/get"})
/*     */   public void get(Integer id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 128 */     String body = "\"\"";
/* 129 */     String message = "\"success\"";
/* 130 */     int code = 200;
/*     */     try {
/* 132 */       WeixinMenu entity = new WeixinMenu();
/* 133 */       if ((id != null) && (id.intValue() != 0)) {
/* 134 */         entity = this.manager.findById(id);
/*     */       }
/* 136 */       body = entity.converToJson().toString();
/*     */     } catch (Exception e) {
/* 138 */       e.printStackTrace();
/* 139 */       code = 100;
/* 140 */       message = "\"system exception\"";
/*     */     }
/* 142 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 143 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/weixinMenu/update"})
/*     */   public void update(WeixinMenu bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 154 */     String body = "\"\"";
/* 155 */     String message = "\"success\"";
/* 156 */     int code = 200;
/*     */     try {
/* 158 */       WebErrors errors = WebErrors.create(request);
/* 159 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getId(), bean.getName() });
/*     */ 
/* 161 */       if (errors.hasErrors()) {
/* 162 */         code = 202;
/* 163 */         message = "\"param error\"";
/*     */       } else {
/* 165 */         this.manager.update(bean);
/*     */       }
/*     */     } catch (Exception e) {
/* 168 */       e.printStackTrace();
/* 169 */       code = 100;
/* 170 */       message = "\"system exception\"";
/*     */     }
/* 172 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 173 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/weixinMenu/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 185 */     String body = "\"\"";
/* 186 */     String message = "\"success\"";
/* 187 */     int code = 200;
/*     */     try
/*     */     {
/* 191 */       if (!StringUtils.isNotBlank(ids)) {
/* 192 */         code = 202;
/* 193 */         message = "\"param error\"";
/*     */       } else {
/* 195 */         String[] str = ids.split(",");
/* 196 */         Integer[] id = new Integer[str.length];
/* 197 */         for (int i = 0; i < str.length; i++) {
/* 198 */           id[i] = Integer.valueOf(str[i]);
/*     */         }
/* 200 */         this.manager.deleteByIds(id);
/*     */       }
/*     */     } catch (Exception e) {
/* 203 */       ExceptionUtil.convertException(response, request, e);
/* 204 */       return;
/*     */     }
/* 206 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 207 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/weixinMenu/menu"})
/*     */   public void menu(WeixinMenu bean, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 218 */     String body = "\"\"";
/* 219 */     String message = "\"success\"";
/* 220 */     int code = 200;
/*     */     try {
/* 222 */       WebErrors errors = WebErrors.create(request);
/* 223 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { bean.getName() });
/*     */ 
/* 225 */       if (errors.hasErrors()) {
/* 226 */         code = 202;
/* 227 */         message = "\"param error\"";
/*     */       } else {
/* 229 */         Website site = SiteUtils.getWeb(request);
/* 230 */         List menus = this.manager.getList(site.getId(), Integer.valueOf(100));
/* 231 */         this.weixinSvc.createMenu(getMenuJsonString(menus));
/*     */       }
/*     */     } catch (Exception e) {
/* 234 */       e.printStackTrace();
/* 235 */       code = 100;
/* 236 */       message = "\"system exception\"";
/*     */     }
/* 238 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 239 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   public String getMenuJsonString(List<WeixinMenu> menus)
/*     */   {
/* 244 */     String strJson = "{\"button\":[";
/*     */ 
/* 247 */     for (int i = 0; i < menus.size(); i++) {
/* 248 */       strJson = strJson + "{\t";
/* 249 */       WeixinMenu menu = (WeixinMenu)menus.get(i);
/* 250 */       if (menu.getChild().size() > 0) {
/* 251 */         strJson = strJson + 
/* 252 */           "\"name\":\"" + menu.getName() + "\"," + 
/* 253 */           "\"sub_button\":[";
/* 254 */         Set sets = menu.getChild();
/* 255 */         Iterator iter = sets.iterator();
/* 256 */         int no = 1;
/* 257 */         while (iter.hasNext()) {
/* 258 */           if (no > 5) {
/*     */             break;
/*     */           }
/* 261 */           if (no == 1)
/* 262 */             strJson = strJson + "{";
/*     */           else {
/* 264 */             strJson = strJson + ",{";
/*     */           }
/* 266 */           WeixinMenu child = (WeixinMenu)iter.next();
/* 267 */           if (child.getType().equals("click"))
/* 268 */             strJson = strJson + 
/* 269 */               "\"type\":\"click\"," + 
/* 270 */               "\"name\":\"" + child.getName() + "\"," + 
/* 271 */               "\"key\":\"" + child.getKey() + "\"}";
/*     */           else {
/* 273 */             strJson = strJson + 
/* 274 */               "\"type\":\"view\"," + 
/* 275 */               "\"name\":\"" + child.getName() + "\"," + 
/* 276 */               "\"url\":\"" + child.getUrl() + "\"}";
/*     */           }
/* 278 */           no++;
/*     */         }
/*     */ 
/* 281 */         strJson = strJson + "]";
/* 282 */       } else if (menu.getType().equals("click")) {
/* 283 */         strJson = strJson + 
/* 284 */           "\"type\":\"click\"," + 
/* 285 */           "\"name\":\"" + menu.getName() + "\"," + 
/* 286 */           "\"key\":\"" + menu.getKey() + "\"";
/*     */       } else {
/* 288 */         strJson = strJson + 
/* 289 */           "\"type\":\"view\"," + 
/* 290 */           "\"name\":\"" + menu.getName() + "\"," + 
/* 291 */           "\"url\":\"" + menu.getUrl() + "\"";
/*     */       }
/* 293 */       if (i == menus.size() - 1)
/* 294 */         strJson = strJson + "}";
/*     */       else {
/* 296 */         strJson = strJson + "},";
/*     */       }
/*     */     }
/* 299 */     strJson = strJson + "]}";
/* 300 */     return strJson;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.WeixinMenuController
 * JD-Core Version:    0.6.0
 */