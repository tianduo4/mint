/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.Brand;
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.manager.BrandMng;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.util.CnToSpell;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class BrandController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private BrandMng brandMng;
/*     */ 
/*     */   @Autowired
/*     */   private CategoryMng categoryMng;
/*     */ 
/*     */   @RequestMapping({"/brand/list"})
/*     */   public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  40 */     String body = "\"\"";
/*  41 */     String message = "\"success\"";
/*  42 */     int code = 200;
/*     */     try {
/*  44 */       if (pageNo == null) {
/*  45 */         pageNo = Integer.valueOf(1);
/*     */       }
/*  47 */       if (pageSize == null) {
/*  48 */         pageSize = Integer.valueOf(10);
/*     */       }
/*  50 */       Pagination page = this.brandMng.getPage(null, null, pageNo.intValue(), pageSize.intValue());
/*  51 */       List list = page.getList();
/*  52 */       int totalCount = page.getTotalCount();
/*  53 */       JSONArray jsonArray = new JSONArray();
/*  54 */       if ((list != null) && (list.size() > 0)) {
/*  55 */         for (int i = 0; i < list.size(); i++) {
/*  56 */           jsonArray.put(i, ((Brand)list.get(i)).convertToJson());
/*     */         }
/*     */       }
/*  59 */       body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
/*     */     } catch (Exception e) {
/*  61 */       e.printStackTrace();
/*  62 */       code = 100;
/*  63 */       message = "\"system exception\"";
/*     */     }
/*  65 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  66 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/brand/listByCategoryId"})
/*     */   public void list(Integer categoryId, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  76 */     String body = "\"\"";
/*  77 */     String message = "\"success\"";
/*  78 */     int code = 200;
/*     */     try {
/*  80 */       WebErrors errors = WebErrors.create(request);
/*  81 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { categoryId });
/*  82 */       if (!errors.hasErrors()) {
/*  83 */         List list = this.brandMng.getListByCateoryId(categoryId);
/*  84 */         JSONArray jsonArray = new JSONArray();
/*  85 */         if ((list != null) && (list.size() > 0)) {
/*  86 */           for (int i = 0; i < list.size(); i++) {
/*  87 */             jsonArray.put(i, ((Brand)list.get(i)).convertToJson());
/*     */           }
/*     */         }
/*  90 */         body = jsonArray.toString();
/*     */       } else {
/*  92 */         code = 202;
/*  93 */         message = "\"param error\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  97 */       e.printStackTrace();
/*  98 */       code = 100;
/*  99 */       message = "\"system exception\"";
/*     */     }
/* 101 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 102 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/brand/save"})
/*     */   public void save(Brand brand, Integer categoryId, String text, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 116 */     String body = "\"\"";
/* 117 */     String message = "\"success\"";
/* 118 */     int code = 200;
/*     */     try {
/* 120 */       WebErrors errors = WebErrors.create(request);
/* 121 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { 
/* 122 */         brand.getName(), categoryId });
/* 123 */       if (!errors.hasErrors())
/*     */       {
/* 125 */         CnToSpell cts = new CnToSpell();
/* 126 */         brand.setFirstCharacter(cts.getBeginCharacter(brand.getName())
/* 127 */           .substring(0, 1));
/* 128 */         Website web = SiteUtils.getWeb(request);
/* 129 */         brand.setWebsite(web);
/* 130 */         brand.setCategory(this.categoryMng.findById(categoryId));
/* 131 */         brand = this.brandMng.save(brand, text);
/*     */       } else {
/* 133 */         code = 202;
/* 134 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 137 */       e.printStackTrace();
/* 138 */       code = 100;
/* 139 */       message = "\"system exception\"";
/*     */     }
/* 141 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 142 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/brand/getBrand"})
/*     */   public void edit(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 151 */     String body = "\"\"";
/* 152 */     String message = "\"success\"";
/* 153 */     int code = 200;
/*     */     try {
/* 155 */       WebErrors errors = WebErrors.create(request);
/* 156 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/* 157 */       if (!errors.hasErrors()) {
/* 158 */         Brand brand = new Brand();
/* 159 */         if (id.longValue() != 0L) {
/* 160 */           brand = this.brandMng.findById(id);
/*     */         }
/* 162 */         if (brand != null) {
/* 163 */           body = brand.convertToJson().toString();
/*     */         } else {
/* 165 */           code = 206;
/* 166 */           message = "\"object not found\"";
/*     */         }
/*     */       } else {
/* 169 */         code = 202;
/* 170 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 173 */       e.printStackTrace();
/* 174 */       code = 100;
/* 175 */       message = "\"system exception\"";
/*     */     }
/* 177 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 178 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/brand/getCategory"})
/*     */   public void getCategory(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 188 */     String body = "\"\"";
/* 189 */     String message = "\"success\"";
/* 190 */     int code = 200;
/*     */     try {
/* 192 */       Long site = SiteUtils.getWebId(request);
/* 193 */       List list = this.categoryMng.getListForParent(site, null);
/* 194 */       JSONArray jsonArray = new JSONArray();
/* 195 */       if ((list != null) && (list.size() > 0)) {
/* 196 */         for (int i = 0; i < list.size(); i++) {
/* 197 */           jsonArray.put(i, ((Category)list.get(i)).convertToJson());
/*     */         }
/*     */       }
/* 200 */       body = jsonArray.toString();
/*     */     } catch (Exception e) {
/* 202 */       e.printStackTrace();
/* 203 */       code = 100;
/* 204 */       message = "\"system exception\"";
/*     */     }
/* 206 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 207 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/brand/update"})
/*     */   public void update(Brand brand, Integer categoryId, String text, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 217 */     String body = "\"\"";
/* 218 */     String message = "\"success\"";
/* 219 */     int code = 200;
/*     */     try {
/* 221 */       WebErrors errors = WebErrors.create(request);
/* 222 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { brand.getId(), 
/* 223 */         brand.getName() });
/* 224 */       if (!errors.hasErrors()) {
/* 225 */         CnToSpell cts = new CnToSpell();
/* 226 */         brand.setFirstCharacter(cts.getBeginCharacter(brand.getName())
/* 227 */           .substring(0, 1));
/* 228 */         brand.setCategory(this.categoryMng.findById(categoryId));
/* 229 */         brand = this.brandMng.update(brand, text);
/*     */       } else {
/* 231 */         code = 202;
/* 232 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 235 */       e.printStackTrace();
/* 236 */       code = 100;
/* 237 */       message = "\"system exception\"";
/*     */     }
/* 239 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 240 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/brand/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 250 */     String body = "\"\"";
/* 251 */     String message = "\"success\"";
/* 252 */     int code = 200;
/*     */     try {
/* 254 */       WebErrors errors = WebErrors.create(request);
/* 255 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 256 */       if (!errors.hasErrors()) {
/* 257 */         Long[] id = null;
/* 258 */         if (StringUtils.isNotBlank(ids)) {
/* 259 */           String[] str = ids.split(",");
/* 260 */           id = new Long[str.length];
/* 261 */           for (int i = 0; i < str.length; i++)
/* 262 */             id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */         }
/*     */         else {
/* 265 */           code = 202;
/* 266 */           message = "\"param error\"";
/*     */         }
/* 268 */         this.brandMng.deleteByIds(id);
/*     */       } else {
/* 270 */         code = 202;
/* 271 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 274 */       ExceptionUtil.convertException(response, request, e);
/* 275 */       return;
/*     */     }
/* 277 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 278 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/brand/priority"})
/*     */   public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 293 */     String message = "\"success\"";
/* 294 */     int code = 200;
/* 295 */     String body = "\"\"";
/*     */     try {
/* 297 */       WebErrors errors = WebErrors.create(request);
/* 298 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids, priority });
/* 299 */       if (!errors.hasErrors()) {
/* 300 */         Long[] id = null;
/* 301 */         Integer[] prioritys = null;
/* 302 */         if (StringUtils.isNotBlank(ids)) {
/* 303 */           String[] str = ids.split(",");
/* 304 */           id = new Long[str.length];
/* 305 */           for (int i = 0; i < str.length; i++) {
/* 306 */             id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */           }
/*     */         }
/* 309 */         if (StringUtils.isNotBlank(priority)) {
/* 310 */           String[] str = priority.split(",");
/* 311 */           prioritys = new Integer[str.length];
/* 312 */           for (int i = 0; i < str.length; i++) {
/* 313 */             prioritys[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */           }
/*     */         }
/* 316 */         this.brandMng.updatePriority(id, prioritys);
/*     */       } else {
/* 318 */         code = 202;
/* 319 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 322 */       code = 100;
/* 323 */       message = "\"system exception\"";
/*     */     }
/* 325 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 326 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.BrandController
 * JD-Core Version:    0.6.0
 */