/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.manager.CmsResourceMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.file.FileWrap;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.tpl.Tpl;
/*     */ import com.jspgou.core.tpl.TplManager;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @Controller
/*     */ public class ResourceController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private TplManager tplManager;
/*     */ 
/*     */   @Autowired
/*     */   private CmsResourceMng resourceMng;
/*     */ 
/*     */   @RequestMapping({"/resource/tree"})
/*     */   public void tree(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/*  48 */     Website site = CmsThreadVariable.getSite();
/*  49 */     JSONArray jsonArray = new JSONArray();
/*  50 */     String body = "\"\"";
/*  51 */     String message = "\"\"";
/*  52 */     int code = 200;
/*  53 */     String root = site.getResPath();
/*  54 */     JSONObject result = new JSONObject();
/*  55 */     List list = this.resourceMng.listFile(root, true);
/*  56 */     if ((list != null) && (list.size() > 0)) {
/*  57 */       for (int i = 0; i < list.size(); i++) {
/*  58 */         jsonArray.put(i, ((FileWrap)list.get(i)).convertToTreeJson((FileWrap)list.get(i)));
/*     */       }
/*     */     }
/*  61 */     message = "\"success\"";
/*  62 */     result.put("resources", jsonArray);
/*  63 */     result.put("rootPath", site.getResPath());
/*  64 */     body = result.toString();
/*  65 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  66 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/resource/list"})
/*     */   public void resourceList(String root, HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/*  76 */     Website site = CmsThreadVariable.getSite();
/*  77 */     JSONArray jsonArray = new JSONArray();
/*  78 */     String body = "\"\"";
/*  79 */     String message = "\"\"";
/*  80 */     int code = 200;
/*  81 */     if (StringUtils.isBlank(root)) {
/*  82 */       root = site.getResPath();
/*     */     }
/*  84 */     WebErrors errors = validateList(root, site.getResPath(), request);
/*  85 */     if (errors.hasErrors()) {
/*  86 */       code = 202;
/*  87 */       message = "\"param error\"";
/*     */     } else {
/*  89 */       List list = this.resourceMng.listFile(root, false);
/*  90 */       if ((list != null) && (list.size() > 0)) {
/*  91 */         for (int i = 0; i < list.size(); i++) {
/*  92 */           jsonArray.put(i, ((FileWrap)list.get(i)).convertToJson());
/*     */         }
/*     */       }
/*  95 */       message = "\"success\"";
/*  96 */       code = 200;
/*  97 */       body = jsonArray.toString();
/*     */     }
/*  99 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 100 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/resource/dir_save"})
/*     */   public void createDir(String root, String dirName, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 108 */     Website site = CmsThreadVariable.getSite();
/* 109 */     String body = "\"\"";
/* 110 */     String message = "\"param required\"";
/* 111 */     int code = 201;
/* 112 */     if (StringUtils.isBlank(root)) {
/* 113 */       root = site.getResPath();
/*     */     }
/* 115 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 117 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { 
/* 118 */       root, dirName });
/* 119 */     if (!errors.hasErrors()) {
/* 120 */       errors = validateList(root, site.getResPath(), request);
/*     */     }
/* 122 */     if (!errors.hasErrors()) {
/* 123 */       this.resourceMng.createDir(root, dirName);
/* 124 */       message = "\"success\"";
/* 125 */       code = 200;
/*     */     } else {
/* 127 */       message = "\"param error\"";
/* 128 */       code = 202;
/*     */     }
/* 130 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 131 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/resource/get"})
/*     */   public void get(String name, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 138 */     Website site = CmsThreadVariable.getSite();
/* 139 */     String body = "";
/* 140 */     String message = "param required";
/* 141 */     int code = 201;
/* 142 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 144 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { name });
/* 145 */     if (!errors.hasErrors()) {
/* 146 */       errors = validateList(name, site.getResPath(), request);
/*     */     }
/* 148 */     if (!errors.hasErrors()) {
/* 149 */       String source = "";
/*     */       try {
/* 151 */         source = this.resourceMng.readFile(name);
/*     */       }
/*     */       catch (IOException localIOException) {
/*     */       }
/* 155 */       message = "success";
/* 156 */       code = 200;
/* 157 */       body = source;
/*     */     } else {
/* 159 */       message = "param error";
/* 160 */       code = 202;
/*     */     }
/*     */ 
/* 163 */     JsonObject json = new JsonObject();
/* 164 */     json.addProperty("body", body);
/* 165 */     json.addProperty("message", message);
/* 166 */     json.addProperty("code", Integer.valueOf(code));
/* 167 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/resource/save"})
/*     */   public void save(String root, String filename, String source, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 175 */     String body = "\"\"";
/* 176 */     String message = "\"param required\"";
/* 177 */     int code = 201;
/* 178 */     Website site = CmsThreadVariable.getSite();
/* 179 */     WebErrors errors = WebErrors.create(request);
/* 180 */     if (StringUtils.isBlank(root)) {
/* 181 */       root = site.getResPath();
/*     */     }
/*     */ 
/* 184 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { 
/* 185 */       root, filename, source });
/* 186 */     if (!errors.hasErrors()) {
/* 187 */       errors = validateList(root, site.getResPath(), request);
/*     */     }
/* 189 */     if (!errors.hasErrors()) {
/*     */       try {
/* 191 */         this.resourceMng.createFile(root, filename, source);
/*     */       }
/*     */       catch (IOException localIOException) {
/*     */       }
/* 195 */       message = "\"success\"";
/* 196 */       code = 200;
/*     */     } else {
/* 198 */       message = "\"param error\"";
/* 199 */       code = 202;
/*     */     }
/* 201 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 202 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/resource/update"})
/*     */   public void update(String filename, String source, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 210 */     String body = "\"\"";
/* 211 */     String message = "\"param required\"";
/* 212 */     int code = 201;
/* 213 */     Website site = CmsThreadVariable.getSite();
/* 214 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 216 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { 
/* 217 */       filename, source });
/* 218 */     if (!errors.hasErrors()) {
/* 219 */       errors = validateList(filename, site.getResPath(), request);
/*     */     }
/* 221 */     if (!errors.hasErrors()) {
/*     */       try {
/* 223 */         this.resourceMng.updateFile(filename, source);
/*     */       } catch (IOException e) {
/* 225 */         e.printStackTrace();
/*     */       }
/* 227 */       message = "\"success\"";
/* 228 */       code = 200;
/*     */     } else {
/* 230 */       message = "\"param error\"";
/* 231 */       code = 202;
/*     */     }
/* 233 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 234 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/resource/delete"})
/*     */   public void delete(String names, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 242 */     String body = "\"\"";
/* 243 */     String message = "\"param required\"";
/* 244 */     int code = 201;
/* 245 */     Website site = CmsThreadVariable.getSite();
/* 246 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 248 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { names });
/* 249 */     String[] nameArray = null;
/* 250 */     if (!errors.hasErrors()) {
/* 251 */       nameArray = names.split(",");
/* 252 */       for (String n : nameArray) {
/* 253 */         errors = validatePath(n, site.getResPath(), errors);
/* 254 */         if (errors.hasErrors()) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 259 */     if (!errors.hasErrors()) {
/*     */       try {
/* 261 */         this.resourceMng.delete(nameArray);
/* 262 */         message = "\"success\"";
/* 263 */         code = 200;
/*     */       } catch (Exception e) {
/* 265 */         message = "\"delete error\"";
/* 266 */         code = 205;
/*     */       }
/*     */     } else {
/* 269 */       message = "\"param error\"";
/* 270 */       code = 202;
/*     */     }
/* 272 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 273 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/resource/rename"})
/*     */   public void rename(String origName, String distName, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 281 */     String body = "\"\"";
/* 282 */     String message = "\"param required\"";
/* 283 */     int code = 201;
/* 284 */     Website site = CmsThreadVariable.getSite();
/* 285 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 287 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { origName, distName });
/* 288 */     if (!errors.hasErrors()) {
/* 289 */       errors = validateRename(origName, distName, site.getResPath(), request);
/*     */     }
/* 291 */     if (!errors.hasErrors()) {
/* 292 */       this.resourceMng.rename(origName, distName);
/* 293 */       message = "\"success\"";
/* 294 */       code = 200;
/*     */     } else {
/* 296 */       message = "\"param error\"";
/* 297 */       code = 202;
/*     */     }
/* 299 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 300 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/resource/upload"})
/*     */   public void upload(String root, @RequestParam(value="uploadFile", required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 309 */     String body = "\"\"";
/* 310 */     String message = "\"param required\"";
/* 311 */     int code = 201;
/* 312 */     Website site = CmsThreadVariable.getSite();
/* 313 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 315 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { root, file });
/* 316 */     if (!errors.hasErrors()) {
/* 317 */       errors = validateUpload(root, site.getResPath(), file, request);
/*     */     }
/* 319 */     if (!errors.hasErrors()) {
/*     */       try {
/* 321 */         String origName = file.getOriginalFilename();
/* 322 */         String ext = FilenameUtils.getExtension(origName).toLowerCase(
/* 323 */           Locale.ENGLISH);
/* 324 */         this.resourceMng.saveFile(root, file);
/* 325 */         JSONObject json = new JSONObject();
/* 326 */         json.put("ext", ext.toUpperCase());
/* 327 */         json.put("size", file.getSize());
/* 328 */         json.put("url", root + "/" + origName);
/* 329 */         json.put("name", file.getOriginalFilename());
/* 330 */         body = json.toString();
/* 331 */         message = "\"success\"";
/* 332 */         code = 200;
/*     */       } catch (Exception e) {
/* 334 */         e.printStackTrace();
/* 335 */         message = "\"upload file error!\"";
/* 336 */         code = 400;
/*     */       }
/*     */     } else {
/* 339 */       message = "\"param error\"";
/* 340 */       code = 202;
/*     */     }
/* 342 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 343 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpload(String root, String tplPath, MultipartFile file, HttpServletRequest request)
/*     */   {
/* 350 */     WebErrors errors = WebErrors.create(request);
/* 351 */     if (file == null) {
/* 352 */       errors.addErrorString("\"error noFileToUpload\"");
/* 353 */       return errors;
/*     */     }
/* 355 */     if (isUnValidName(root, root, tplPath, errors)) {
/* 356 */       errors.addErrorCode("template.invalidParams");
/*     */     }
/* 358 */     String filename = file.getOriginalFilename();
/* 359 */     if ((filename != null) && ((filename.contains("/")) || (filename.contains("\\")) || (filename.indexOf("") != -1))) {
/* 360 */       errors.addErrorString("\"upload error filename\"");
/* 361 */       return errors;
/*     */     }
/* 363 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateList(String name, String tplPath, HttpServletRequest request)
/*     */   {
/* 368 */     WebErrors errors = WebErrors.create(request);
/* 369 */     if (vldExist(name, errors)) {
/* 370 */       return errors;
/*     */     }
/* 372 */     if (isUnValidName(name, name, tplPath, errors)) {
/* 373 */       errors.addErrorCode("template.invalidParams");
/*     */     }
/* 375 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validatePath(String name, String tplPath, WebErrors errors)
/*     */   {
/* 380 */     if (vldExist(name, errors)) {
/* 381 */       return errors;
/*     */     }
/* 383 */     if (isUnValidName(name, name, tplPath, errors)) {
/* 384 */       errors.addErrorCode("template.invalidParams");
/*     */     }
/* 386 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateRename(String name, String newName, String tplPath, HttpServletRequest request)
/*     */   {
/* 391 */     WebErrors errors = WebErrors.create(request);
/* 392 */     if (vldExist(name, errors)) {
/* 393 */       return errors;
/*     */     }
/* 395 */     if (isUnValidName(name, name, tplPath, errors)) {
/* 396 */       errors.addErrorCode("template.invalidParams");
/*     */     }
/* 398 */     if (isUnValidName(newName, newName, tplPath, errors)) {
/* 399 */       errors.addErrorCode("template.invalidParams");
/*     */     }
/* 401 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean isUnValidName(String path, String name, String tplPath, WebErrors errors)
/*     */   {
/* 406 */     return (!path.startsWith(tplPath)) || (path.contains("../")) || (path.contains("..\\")) || (name.contains("..\\")) || (name.contains("../"));
/*     */   }
/*     */ 
/*     */   private boolean vldExist(String name, WebErrors errors)
/*     */   {
/* 413 */     if (errors.ifNull(name, "name")) {
/* 414 */       return true;
/*     */     }
/* 416 */     Tpl entity = this.tplManager.get(name);
/*     */ 
/* 418 */     return errors.ifNotExist(entity, Tpl.class, name);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.ResourceController
 * JD-Core Version:    0.6.0
 */