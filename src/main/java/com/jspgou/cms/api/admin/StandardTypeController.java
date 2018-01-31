/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.api.ExceptionUtil;
/*     */ import com.jspgou.cms.entity.Standard;
/*     */ import com.jspgou.cms.entity.StandardType;
/*     */ import com.jspgou.cms.manager.StandardMng;
/*     */ import com.jspgou.cms.manager.StandardTypeMng;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class StandardTypeController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private StandardTypeMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private StandardMng standardMng;
/*     */ 
/*     */   @Autowired
/*     */   private StandardTypeMng standardTypeMng;
/*     */ 
/*     */   @RequestMapping({"/standardType/list"})
/*     */   public void standardType(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
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
/*  52 */       Pagination page = this.manager.getPage(pageNo.intValue(), pageSize.intValue());
/*  53 */       List list = page.getList();
/*  54 */       int totalCount = page.getTotalCount();
/*  55 */       JSONArray jsonArray = new JSONArray();
/*  56 */       if ((list != null) && (list.size() > 0)) {
/*  57 */         for (int i = 0; i < list.size(); i++) {
/*  58 */           jsonArray.put(i, ((StandardType)list.get(i)).convertToJson(null));
/*     */         }
/*     */       }
/*  61 */       body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
/*     */     } catch (Exception e) {
/*  63 */       e.printStackTrace();
/*  64 */       code = 100;
/*  65 */       message = "\"system exception\"";
/*     */     }
/*  67 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  68 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/standardType/save"})
/*     */   public void save(StandardType standardType, String itemName, String itemColor, String itemPriority, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  86 */     String body = "\"\"";
/*  87 */     String message = "\"success\"";
/*  88 */     int code = 200;
/*     */     try {
/*  90 */       WebErrors errors = WebErrors.create(request);
/*  91 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { 
/*  92 */         standardType.getName() });
/*  93 */       if (!errors.hasErrors()) {
/*  94 */         String[] itemNames = null;
/*  95 */         String[] itemColors = null;
/*  96 */         Integer[] itemPrioritys = null;
/*  97 */         if (StringUtils.isNotBlank(itemName)) {
/*  98 */           String[] str = itemName.split(",");
/*  99 */           itemNames = new String[str.length];
/* 100 */           for (int i = 0; i < str.length; i++) {
/* 101 */             itemNames[i] = str[i];
/*     */           }
/*     */         }
/* 104 */         if (StringUtils.isNotBlank(itemColor)) {
/* 105 */           String[] str = itemColor.split(",");
/* 106 */           itemColors = new String[str.length];
/* 107 */           for (int i = 0; i < str.length; i++) {
/* 108 */             itemColors[i] = str[i];
/*     */           }
/*     */         }
/* 111 */         if (StringUtils.isNotBlank(itemPriority)) {
/* 112 */           String[] str = itemPriority.split(",");
/* 113 */           itemPrioritys = new Integer[str.length];
/* 114 */           for (int i = 0; i < str.length; i++) {
/* 115 */             itemPrioritys[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */           }
/*     */         }
/* 118 */         standardType = this.manager.save(standardType);
/* 119 */         this.standardTypeMng.addStandard(standardType, itemNames, 
/* 120 */           itemColors, itemPrioritys);
/*     */       } else {
/* 122 */         code = 202;
/* 123 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 126 */       e.printStackTrace();
/* 127 */       code = 100;
/* 128 */       message = "\"system exception\"";
/*     */     }
/* 130 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 131 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/standardType/getStandard"})
/*     */   public void edit(Long id, HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 140 */     String body = "\"\"";
/* 141 */     String message = "\"success\"";
/* 142 */     int code = 200;
/*     */     try {
/* 144 */       WebErrors errors = WebErrors.create(request);
/* 145 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { id });
/* 146 */       if (!errors.hasErrors()) {
/* 147 */         StandardType standardType = new StandardType();
/* 148 */         if (id.longValue() != 0L) {
/* 149 */           standardType = this.manager.findById(id);
/*     */         }
/* 151 */         if (standardType != null) {
/* 152 */           JSONArray jsonArray = new JSONArray();
/* 153 */           JSONObject json = new JSONObject();
/* 154 */           List list = this.standardMng.findByTypeId(id);
/* 155 */           if ((list != null) && (list.size() > 0)) {
/* 156 */             for (int i = 0; i < list.size(); i++) {
/* 157 */               jsonArray.put(i, ((Standard)list.get(i)).convertToJson1());
/*     */             }
/*     */           }
/* 160 */           json.put("standardType", standardType.convertToJson1());
/* 161 */           json.put("standard", jsonArray);
/* 162 */           body = json.toString();
/*     */         } else {
/* 164 */           code = 206;
/* 165 */           message = "\"object not found\"";
/*     */         }
/*     */       } else {
/* 168 */         code = 202;
/* 169 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 172 */       e.printStackTrace();
/* 173 */       code = 100;
/* 174 */       message = "\"system exception\"";
/*     */     }
/* 176 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 177 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/standardType/update"})
/*     */   public void update(StandardType standardType, String itemId, String itemName, String itemColor, String itemPriority, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 189 */     String body = "\"\"";
/* 190 */     String message = "\"success\"";
/* 191 */     int code = 200;
/*     */     try {
/* 193 */       WebErrors errors = WebErrors.create(request);
/* 194 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { 
/* 195 */         standardType.getName() });
/* 196 */       if (!errors.hasErrors()) {
/* 197 */         Long[] itemIds = null;
/* 198 */         String[] itemNames = null;
/* 199 */         String[] itemColors = null;
/* 200 */         Integer[] itemPrioritys = null;
/* 201 */         if (StringUtils.isNotBlank(itemId)) {
/* 202 */           String[] str = itemId.split(",");
/* 203 */           itemIds = new Long[str.length];
/* 204 */           for (int i = 0; i < str.length; i++) {
/* 205 */             itemIds[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */           }
/*     */         }
/* 208 */         if (StringUtils.isNotBlank(itemName)) {
/* 209 */           String[] str = itemName.split(",");
/* 210 */           itemNames = new String[str.length];
/* 211 */           for (int i = 0; i < str.length; i++) {
/* 212 */             itemNames[i] = str[i];
/*     */           }
/*     */         }
/* 215 */         if (StringUtils.isNotBlank(itemColor)) {
/* 216 */           String[] str = itemColor.split(",");
/* 217 */           itemColors = new String[str.length];
/* 218 */           for (int i = 0; i < str.length; i++) {
/* 219 */             itemColors[i] = str[i];
/*     */           }
/*     */         }
/* 222 */         if (StringUtils.isNotBlank(itemPriority)) {
/* 223 */           String[] str = itemPriority.split(",");
/* 224 */           itemPrioritys = new Integer[str.length];
/* 225 */           for (int i = 0; i < str.length; i++) {
/* 226 */             itemPrioritys[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */           }
/*     */         }
/* 229 */         standardType = this.manager.update(standardType);
/* 230 */         this.standardTypeMng.updateStandard(standardType, itemIds, 
/* 231 */           itemNames, itemColors, itemPrioritys);
/*     */       }
/*     */       else {
/* 234 */         code = 202;
/* 235 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 238 */       e.printStackTrace();
/* 239 */       code = 100;
/* 240 */       message = "\"system exception\"";
/*     */     }
/* 242 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 243 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/standardType/delete"})
/*     */   public void delete(String ids, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 254 */     String body = "\"\"";
/* 255 */     String message = "\"success\"";
/* 256 */     int code = 200;
/*     */     try {
/* 258 */       WebErrors errors = WebErrors.create(request);
/* 259 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids });
/* 260 */       if (!errors.hasErrors())
/*     */       {
/* 262 */         if (!StringUtils.isNotBlank(ids)) {
/* 263 */           code = 202;
/* 264 */           message = "\"param error\"";
/*     */         } else {
/* 266 */           Long[] id = null;
/* 267 */           String[] str = ids.split(",");
/* 268 */           id = new Long[str.length];
/* 269 */           for (int i = 0; i < str.length; i++) {
/* 270 */             id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */           }
/* 272 */           this.manager.deleteByIds(id);
/*     */         }
/*     */       } else {
/* 275 */         code = 202;
/* 276 */         message = "\"param error\"";
/*     */       }
/*     */     } catch (Exception e) {
/* 279 */       ExceptionUtil.convertException(response, request, e);
/* 280 */       return;
/*     */     }
/* 282 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 283 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/standardType/priority"})
/*     */   public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 296 */     String body = "\"\"";
/* 297 */     String message = "\"success\"";
/* 298 */     int code = 200;
/*     */     try {
/* 300 */       WebErrors errors = WebErrors.create(request);
/* 301 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { ids, priority });
/* 302 */       if (!errors.hasErrors()) {
/* 303 */         Long[] id = null;
/* 304 */         Integer[] prioritys = null;
/* 305 */         if (StringUtils.isNotBlank(ids)) {
/* 306 */           String[] str = ids.split(",");
/* 307 */           id = new Long[str.length];
/* 308 */           for (int i = 0; i < str.length; i++) {
/* 309 */             id[i] = Long.valueOf(Long.parseLong(str[i]));
/*     */           }
/*     */         }
/* 312 */         if (StringUtils.isNotBlank(priority)) {
/* 313 */           String[] str = priority.split(",");
/* 314 */           prioritys = new Integer[str.length];
/* 315 */           for (int i = 0; i < str.length; i++) {
/* 316 */             prioritys[i] = Integer.valueOf(Integer.parseInt(str[i]));
/*     */           }
/*     */         }
/* 319 */         this.manager.updatePriority(id, prioritys);
/*     */       } else {
/* 321 */         code = 202;
/* 322 */         message = "\"param error\"";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 326 */       e.printStackTrace();
/* 327 */       code = 100;
/* 328 */       message = "\"system exception\"";
/*     */     }
/* 330 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 331 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.StandardTypeController
 * JD-Core Version:    0.6.0
 */