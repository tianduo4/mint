/*     */ package com.jspgou.cms.api.admin;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.manager.CmsResourceMng;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.util.Zipper;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import com.jspgou.core.tpl.FileTpl;
/*     */ import com.jspgou.core.tpl.Tpl;
/*     */ import com.jspgou.core.tpl.TplManager;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.io.File;
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
/*     */ public class TemplateController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private TplManager tplManager;
/*     */ 
/*     */   @Autowired
/*     */   private CmsResourceMng resourceMng;
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteMng cmsSiteMng;
/*     */ 
/*     */   @RequestMapping({"/template/tree"})
/*     */   public void tree(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/*  56 */     Website site = CmsThreadVariable.getSite();
/*  57 */     JSONArray jsonArray = new JSONArray();
/*  58 */     String body = "\"\"";
/*  59 */     String message = "\"\"";
/*  60 */     int code = 200;
/*  61 */     String root = site.getTplPath();
/*  62 */     JSONObject result = new JSONObject();
/*  63 */     List list = this.tplManager.getChild(root);
/*  64 */     if ((list != null) && (list.size() > 0)) {
/*  65 */       for (int i = 0; i < list.size(); i++) {
/*  66 */         jsonArray.put(i, ((FileTpl)list.get(i)).convertToTreeJson((FileTpl)list.get(i)));
/*     */       }
/*     */     }
/*  69 */     result.put("resources", jsonArray);
/*  70 */     result.put("rootPath", root);
/*  71 */     message = "\"success\"";
/*  72 */     body = result.toString();
/*  73 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  74 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/template/list"})
/*     */   public void templateList(String root, HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/*  85 */     Website site = CmsThreadVariable.getSite();
/*  86 */     JSONArray jsonArray = new JSONArray();
/*  87 */     String body = "\"\"";
/*  88 */     String message = "\"\"";
/*  89 */     int code = 200;
/*  90 */     if (StringUtils.isBlank(root)) {
/*  91 */       root = site.getTplPath();
/*     */     }
/*  93 */     WebErrors errors = validateList(root, site.getTplPath(), request);
/*  94 */     if (errors.hasErrors()) {
/*  95 */       code = 202;
/*  96 */       message = "\"param error\"";
/*     */     } else {
/*  98 */       List list = this.tplManager.getChild(root);
/*  99 */       if ((list != null) && (list.size() > 0)) {
/* 100 */         for (int i = 0; i < list.size(); i++) {
/* 101 */           jsonArray.put(i, ((FileTpl)list.get(i)).convertToJson());
/*     */         }
/*     */       }
/* 104 */       message = "\"success\"";
/* 105 */       code = 200;
/* 106 */       body = jsonArray.toString();
/*     */     }
/* 108 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 109 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/template/dir_save"})
/*     */   public void createDir(String root, String dirName, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 117 */     Website site = CmsThreadVariable.getSite();
/* 118 */     String body = "\"\"";
/* 119 */     String message = "\"param required\"";
/* 120 */     int code = 201;
/* 121 */     if (StringUtils.isBlank(root)) {
/* 122 */       root = site.getTplPath();
/*     */     }
/* 124 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 126 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { 
/* 127 */       root, dirName });
/* 128 */     if (!errors.hasErrors()) {
/* 129 */       errors = validateList(root, site.getTplPath(), request);
/*     */     }
/* 131 */     if (!errors.hasErrors()) {
/* 132 */       this.tplManager.save(root + "/" + dirName, null, true);
/* 133 */       message = "\"success\"";
/* 134 */       code = 200;
/*     */     } else {
/* 136 */       message = "\"param error\"";
/* 137 */       code = 202;
/*     */     }
/* 139 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 140 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/template/get"})
/*     */   public void get(String name, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 147 */     Website site = CmsThreadVariable.getSite();
/* 148 */     String body = "";
/* 149 */     String message = "param required";
/* 150 */     int code = 201;
/* 151 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 153 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { name });
/* 154 */     if (!errors.hasErrors()) {
/* 155 */       errors = validateList(name, site.getTplPath(), request);
/*     */     }
/* 157 */     if (!errors.hasErrors()) {
/* 158 */       FileTpl tpl = (FileTpl)this.tplManager.get(name);
/* 159 */       message = "success";
/* 160 */       code = 200;
/* 161 */       body = tpl.getSource();
/*     */     } else {
/* 163 */       message = "param error";
/* 164 */       code = 202;
/*     */     }
/* 166 */     JsonObject json = new JsonObject();
/* 167 */     json.addProperty("body", body);
/* 168 */     json.addProperty("message", message);
/* 169 */     json.addProperty("code", Integer.valueOf(code));
/* 170 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/template/save"})
/*     */   public void save(String root, String filename, String source, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 178 */     String body = "\"\"";
/* 179 */     String message = "\"param required\"";
/* 180 */     int code = 201;
/* 181 */     Website site = CmsThreadVariable.getSite();
/* 182 */     WebErrors errors = WebErrors.create(request);
/* 183 */     if (StringUtils.isBlank(root)) {
/* 184 */       root = site.getTplPath();
/*     */     }
/*     */ 
/* 187 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { 
/* 188 */       root, filename, source });
/* 189 */     if (!errors.hasErrors()) {
/* 190 */       errors = validateList(root, site.getTplPath(), request);
/*     */     }
/* 192 */     if (!errors.hasErrors()) {
/* 193 */       String name = root + "/" + filename + ".html";
/* 194 */       this.tplManager.save(name, source, false);
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
/*     */   @RequestMapping({"/template/update"})
/*     */   public void update(String filename, String source, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 210 */     String body = "\"\"";
/* 211 */     String message = "\"param required\"";
/* 212 */     int code = 201;
/* 213 */     Website site = CmsThreadVariable.getSite();
/* 214 */     WebErrors errors = WebErrors.create(request);
/* 215 */     String root = site.getTplPath();
/*     */ 
/* 217 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { 
/* 218 */       filename, source });
/* 219 */     if (!errors.hasErrors()) {
/* 220 */       errors = validateList(root, site.getTplPath(), request);
/*     */     }
/* 222 */     if (!errors.hasErrors()) {
/* 223 */       this.tplManager.update(filename, source);
/* 224 */       message = "\"success\"";
/* 225 */       code = 200;
/*     */     } else {
/* 227 */       message = "\"param error\"";
/* 228 */       code = 202;
/*     */     }
/* 230 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 231 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/template/delete"})
/*     */   public void delete(String names, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 239 */     String body = "\"\"";
/* 240 */     String message = "\"param required\"";
/* 241 */     int code = 201;
/* 242 */     Website site = CmsThreadVariable.getSite();
/* 243 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 245 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { names });
/* 246 */     String[] nameArray = null;
/* 247 */     if (!errors.hasErrors()) {
/* 248 */       nameArray = names.split(",");
/* 249 */       for (String n : nameArray) {
/* 250 */         errors = validatePath(n, site.getTplPath(), errors);
/* 251 */         if (errors.hasErrors()) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 256 */     if (!errors.hasErrors()) {
/*     */       try {
/* 258 */         if (nameArray != null) {
/* 259 */           this.tplManager.delete(nameArray);
/*     */         }
/* 261 */         message = "\"success\"";
/* 262 */         code = 200;
/*     */       } catch (Exception e) {
/* 264 */         message = "\"delete error\"";
/* 265 */         code = 205;
/*     */       }
/*     */     } else {
/* 268 */       message = "\"param error\"";
/* 269 */       code = 202;
/*     */     }
/* 271 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 272 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/template/rename"})
/*     */   public void rename(String origName, String distName, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 280 */     String body = "\"\"";
/* 281 */     String message = "\"param required\"";
/* 282 */     int code = 201;
/* 283 */     Website site = CmsThreadVariable.getSite();
/* 284 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 286 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { origName, distName });
/* 287 */     if (!errors.hasErrors()) {
/* 288 */       errors = validateRename(origName, distName, site.getTplPath(), request);
/*     */     }
/* 290 */     if (!errors.hasErrors()) {
/* 291 */       this.tplManager.rename(origName, distName);
/* 292 */       message = "\"success\"";
/* 293 */       code = 200;
/*     */     } else {
/* 295 */       message = "\"param error\"";
/* 296 */       code = 202;
/*     */     }
/* 298 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 299 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/template/getSolutions"})
/*     */   public void getSolutions(HttpServletRequest request, HttpServletResponse response) throws JSONException {
/* 305 */     Website site = CmsThreadVariable.getSite();
/* 306 */     JSONArray jsonArray = new JSONArray();
/* 307 */     String body = "\"\"";
/* 308 */     String message = "\"\"";
/* 309 */     int code = 200;
/* 310 */     String[] solutions = this.resourceMng.getSolutions(site.getTplPath());
/* 311 */     for (int i = 0; i < solutions.length; i++) {
/* 312 */       jsonArray.put(i, solutions[i]);
/*     */     }
/* 314 */     message = "\"success\"";
/* 315 */     code = 200;
/* 316 */     body = jsonArray.toString();
/* 317 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 318 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/template/solutionupdate"})
/*     */   public void setTempate(String solution, Boolean mobile, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 326 */     String body = "\"\"";
/* 327 */     String message = "\"param required\"";
/* 328 */     int code = 201;
/* 329 */     Website site = CmsThreadVariable.getSite();
/* 330 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 332 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { solution });
/* 333 */     if (mobile == null) {
/* 334 */       mobile = Boolean.valueOf(false);
/*     */     }
/* 336 */     if (!errors.hasErrors()) {
/* 337 */       this.cmsSiteMng.updateTplSolution(site.getId(), solution, mobile.booleanValue());
/* 338 */       message = "\"success\"";
/* 339 */       code = 200;
/*     */     } else {
/* 341 */       message = "\"param error\"";
/* 342 */       code = 202;
/*     */     }
/* 344 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 345 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/template/exportTpl"})
/*     */   public void tplExport(String solution, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 352 */     String body = "\"\"";
/* 353 */     String message = "\"param required\"";
/* 354 */     int code = 201;
/* 355 */     Website site = CmsThreadVariable.getSite();
/* 356 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 358 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { solution });
/* 359 */     if (!errors.hasErrors()) {
/* 360 */       List fileEntrys = this.resourceMng.export(site, solution);
/* 361 */       response.setContentType("application/x-download;charset=UTF-8");
/* 362 */       response.addHeader("Content-disposition", "filename=template-" + 
/* 363 */         solution + ".zip");
/*     */       try
/*     */       {
/* 366 */         Zipper.zip(response.getOutputStream(), fileEntrys, "GBK");
/*     */       } catch (IOException e) {
/* 368 */         e.printStackTrace();
/* 369 */         code = 600;
/* 370 */         message = "\"export template faill\"";
/*     */       }
/* 372 */       message = "\"success\"";
/* 373 */       code = 200;
/*     */     }
/* 375 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 376 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/template/importTpl"})
/*     */   public void tplImport(@RequestParam(value="uploadFile", required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
/* 384 */     String body = "\"\"";
/* 385 */     String message = "\"\"";
/* 386 */     int code = 200;
/* 387 */     Website site = CmsThreadVariable.getSite();
/* 388 */     WebErrors errors = validate(file, request);
/* 389 */     if (errors.hasErrors()) {
/* 390 */       code = 202;
/*     */     }
/*     */     else {
/* 393 */       errors = ApiValidate.validateRequiredParams(errors, new Object[] { file });
/* 394 */       if (!errors.hasErrors()) {
/* 395 */         String origName = file.getOriginalFilename();
/* 396 */         String ext = FilenameUtils.getExtension(origName).toLowerCase(
/* 397 */           Locale.ENGLISH);
/* 398 */         String filepath = "";
/*     */         try {
/* 400 */           File tempFile = File.createTempFile("tplZip", "temp");
/* 401 */           file.transferTo(tempFile);
/* 402 */           this.resourceMng.imoport(tempFile, site);
/* 403 */           tempFile.delete();
/* 404 */           JSONObject json = new JSONObject();
/* 405 */           json.put("ext", ext.toUpperCase());
/* 406 */           json.put("size", file.getSize());
/* 407 */           json.put("url", filepath);
/* 408 */           json.put("name", file.getOriginalFilename());
/* 409 */           body = json.toString();
/* 410 */           message = "\"success\"";
/*     */         } catch (Exception e) {
/* 412 */           e.printStackTrace();
/* 413 */           code = 400;
/*     */         }
/*     */       } else {
/* 416 */         code = 201;
/*     */       }
/*     */     }
/* 419 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 420 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/template/upload"})
/*     */   public void upload(String root, @RequestParam(value="uploadFile", required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 429 */     String body = "\"\"";
/* 430 */     String message = "\"param required\"";
/* 431 */     int code = 201;
/* 432 */     Website site = CmsThreadVariable.getSite();
/* 433 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/* 435 */     errors = ApiValidate.validateRequiredParams(errors, new Object[] { file });
/* 436 */     if (!errors.hasErrors()) {
/* 437 */       errors = validateUpload(root, site.getTplPath(), file, request);
/*     */     }
/* 439 */     if (!errors.hasErrors()) {
/*     */       try {
/* 441 */         String origName = file.getOriginalFilename();
/* 442 */         String ext = FilenameUtils.getExtension(origName).toLowerCase(
/* 443 */           Locale.ENGLISH);
/* 444 */         this.tplManager.save(root, file);
/* 445 */         JSONObject json = new JSONObject();
/* 446 */         json.put("ext", ext.toUpperCase());
/* 447 */         json.put("size", file.getSize());
/* 448 */         json.put("url", root + "/" + origName);
/* 449 */         json.put("name", file.getOriginalFilename());
/* 450 */         body = json.toString();
/* 451 */         message = "\"success\"";
/* 452 */         code = 200;
/*     */       } catch (Exception e) {
/* 454 */         e.printStackTrace();
/* 455 */         message = "\"upload file error!\"";
/* 456 */         code = 400;
/*     */       }
/*     */     } else {
/* 459 */       message = "\"param error\"";
/* 460 */       code = 202;
/*     */     }
/* 462 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/* 463 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpload(String root, String tplPath, MultipartFile file, HttpServletRequest request)
/*     */   {
/* 469 */     WebErrors errors = WebErrors.create(request);
/* 470 */     if (file == null) {
/* 471 */       errors.addErrorString("\"error noFileToUpload\"");
/* 472 */       return errors;
/*     */     }
/* 474 */     if (isUnValidName(root, root, tplPath, errors)) {
/* 475 */       errors.addErrorCode("template.invalidParams");
/*     */     }
/* 477 */     String filename = file.getOriginalFilename();
/* 478 */     if ((filename != null) && ((filename.contains("/")) || (filename.contains("\\")) || (filename.indexOf("") != -1))) {
/* 479 */       errors.addErrorString("\"upload error filename\"");
/* 480 */       return errors;
/*     */     }
/* 482 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validate(MultipartFile file, HttpServletRequest request)
/*     */   {
/* 487 */     WebErrors errors = WebErrors.create(request);
/* 488 */     if (file == null) {
/* 489 */       errors.addErrorString("imageupload error noFileToUpload");
/* 490 */       return errors;
/*     */     }
/* 492 */     String filename = file.getOriginalFilename();
/* 493 */     if ((filename != null) && ((filename.contains("/")) || (filename.contains("\\")) || (filename.indexOf("") != -1))) {
/* 494 */       errors.addErrorString("upload error filename");
/* 495 */       return errors;
/*     */     }
/* 497 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateList(String name, String tplPath, HttpServletRequest request)
/*     */   {
/* 502 */     WebErrors errors = WebErrors.create(request);
/* 503 */     if (vldExist(name, errors)) {
/* 504 */       return errors;
/*     */     }
/* 506 */     if (isUnValidName(name, name, tplPath, errors)) {
/* 507 */       errors.addErrorCode("template.invalidParams");
/*     */     }
/* 509 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validatePath(String name, String tplPath, WebErrors errors)
/*     */   {
/* 514 */     if (vldExist(name, errors)) {
/* 515 */       return errors;
/*     */     }
/* 517 */     if (isUnValidName(name, name, tplPath, errors)) {
/* 518 */       errors.addErrorCode("template.invalidParams");
/*     */     }
/* 520 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateRename(String name, String newName, String tplPath, HttpServletRequest request)
/*     */   {
/* 525 */     WebErrors errors = WebErrors.create(request);
/* 526 */     if (vldExist(name, errors)) {
/* 527 */       return errors;
/*     */     }
/* 529 */     if (isUnValidName(name, name, tplPath, errors)) {
/* 530 */       errors.addErrorCode("template.invalidParams");
/*     */     }
/* 532 */     if (isUnValidName(newName, newName, tplPath, errors)) {
/* 533 */       errors.addErrorCode("template.invalidParams");
/*     */     }
/* 535 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean isUnValidName(String path, String name, String tplPath, WebErrors errors)
/*     */   {
/* 540 */     return (!path.startsWith(tplPath)) || (path.contains("../")) || (path.contains("..\\")) || (name.contains("..\\")) || (name.contains("../"));
/*     */   }
/*     */ 
/*     */   private boolean vldExist(String name, WebErrors errors)
/*     */   {
/* 547 */     if (errors.ifNull(name, "name")) {
/* 548 */       return true;
/*     */     }
/* 550 */     Tpl entity = this.tplManager.get(name);
/*     */ 
/* 552 */     return errors.ifNotExist(entity, Tpl.class, name);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.admin.TemplateController
 * JD-Core Version:    0.6.0
 */