/*     */ package com.jspgou.cms.action.admin.assist;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopPlug;
/*     */ import com.jspgou.cms.manager.ResourceMng;
/*     */ import com.jspgou.cms.manager.ShopPlugMng;
/*     */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.page.SimplePage;
/*     */ import com.jspgou.common.upload.FileRepository;
/*     */ import com.jspgou.common.web.CookieUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.springmvc.RealPathResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.LogMng;
/*     */ import com.jspgou.core.tpl.TplManager;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.tools.zip.ZipEntry;
/*     */ import org.apache.tools.zip.ZipFile;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @Controller
/*     */ public class PlugAct
/*     */ {
/*  50 */   private static final Logger log = LoggerFactory.getLogger(PlugAct.class);
/*     */   private static final String PLUG_CONFIG_INI = "plug.ini";
/*     */   private static final String PLUG_CONFIG_KEY_REPAIR = "plug_repair";
/*     */ 
/*     */   @Autowired
/*     */   private ShopPlugMng manager;
/*     */ 
/*     */   @Autowired
/*     */   private ResourceMng resourceMng;
/*     */ 
/*     */   @Autowired
/*     */   private TplManager tplManager;
/*     */ 
/*     */   @Autowired
/*     */   protected FileRepository fileRepository;
/*     */ 
/*     */   @Autowired
/*     */   private RealPathResolver realPathResolver;
/*     */ 
/*     */   @Autowired
/*     */   private LogMng cmsLogMng;
/*     */ 
/*     */   @RequestMapping({"/plug/v_list.do"})
/*     */   public String list(Integer pageNo, HttpServletRequest request, ModelMap model)
/*     */   {
/*  57 */     Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo), 
/*  58 */       CookieUtils.getPageSize(request));
/*  59 */     model.addAttribute("pagination", pagination);
/*  60 */     model.addAttribute("pageNo", Integer.valueOf(pagination.getPageNo()));
/*  61 */     return "plug/list";
/*     */   }
/*     */   @RequestMapping({"/plug/v_add.do"})
/*     */   public String add(ModelMap model) {
/*  66 */     return "plug/add";
/*     */   }
/*     */   @RequestMapping({"/plug/v_edit.do"})
/*     */   public String edit(Long id, Integer pageNo, HttpServletRequest request, ModelMap model) {
/*  71 */     WebErrors errors = validateEdit(id, request);
/*  72 */     if (errors.hasErrors()) {
/*  73 */       return errors.showErrorPage(model);
/*     */     }
/*  75 */     model.addAttribute("plug", this.manager.findById(id));
/*  76 */     model.addAttribute("pageNo", pageNo);
/*  77 */     return "plug/edit";
/*     */   }
/*     */   @RequestMapping({"/plug/o_save.do"})
/*     */   public String save(ShopPlug bean, HttpServletRequest request, ModelMap model) throws IOException {
/*  82 */     WebErrors errors = validateSave(bean, request);
/*  83 */     if (errors.hasErrors()) {
/*  84 */       return errors.showErrorPage(model);
/*     */     }
/*     */ 
/*  87 */     File file = new File(this.realPathResolver.get(bean.getPath()));
/*  88 */     if (file.exists()) {
/*  89 */       if (!isRepairPlug(file)) {
/*  90 */         boolean fileConflict = isFileConflict(file);
/*  91 */         bean.setFileConflict(fileConflict);
/*     */       } else {
/*  93 */         bean.setFileConflict(false);
/*     */       }
/*     */     }
/*  96 */     bean.setUploadTime(Calendar.getInstance().getTime());
/*  97 */     bean = this.manager.save(bean);
/*  98 */     log.info("save CmsPlug id={}", bean.getId());
/*  99 */     return "redirect:v_list.do";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/plug/o_update.do"})
/*     */   public String update(ShopPlug bean, Integer pageNo, HttpServletRequest request, ModelMap model) throws IOException {
/* 105 */     WebErrors errors = validateUpdate(bean, request);
/* 106 */     if (errors.hasErrors()) {
/* 107 */       return errors.showErrorPage(model);
/*     */     }
/*     */ 
/* 110 */     File file = new File(this.realPathResolver.get(bean.getPath()));
/* 111 */     if (file.exists()) {
/* 112 */       if (!isRepairPlug(file)) {
/* 113 */         boolean fileConflict = isFileConflict(file);
/* 114 */         bean.setFileConflict(fileConflict);
/*     */       } else {
/* 116 */         bean.setFileConflict(false);
/*     */       }
/*     */     }
/* 119 */     bean = this.manager.update(bean);
/* 120 */     log.info("update CmsPlug id={}.", bean.getId());
/* 121 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/plug/o_upload.do"})
/*     */   public String uploadSubmit(@RequestParam(value="plugFile", required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws IOException
/*     */   {
/* 139 */     WebErrors errors = validateUpload(file, request);
/* 140 */     if (errors.hasErrors()) {
/* 141 */       model.addAttribute("error", errors.getErrors().get(0));
/* 142 */       return "plug/upload_iframe";
/*     */     }
/* 144 */     String origName = file.getOriginalFilename();
/* 145 */     String ext = FilenameUtils.getExtension(origName).toLowerCase(
/* 146 */       Locale.ENGLISH);
/*     */     try
/*     */     {
/* 150 */       String filename = "/WEB-INF/plug/" + file.getOriginalFilename();
/* 151 */       File oldFile = new File(this.realPathResolver.get(filename));
/* 152 */       if (oldFile.exists()) {
/* 153 */         oldFile.delete();
/*     */       }
/* 155 */       String fileUrl = this.fileRepository.storeByFilePath("/WEB-INF/plug/", file.getOriginalFilename(), file);
/* 156 */       model.addAttribute("plugPath", fileUrl);
/* 157 */       model.addAttribute("plugExt", ext);
/*     */     } catch (IllegalStateException e) {
/* 159 */       model.addAttribute("error", e.getMessage());
/* 160 */       log.error("upload file error!", e);
/*     */     } catch (IOException e) {
/* 162 */       model.addAttribute("error", e.getMessage());
/* 163 */       log.error("upload file error!", e);
/*     */     }
/* 165 */     this.cmsLogMng.operating(request, "plug.log.upload", "filename=" + file.getName());
/* 166 */     return "plug/upload_iframe";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/plug/o_install.do"})
/*     */   public void install(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws IOException, JSONException
/*     */   {
/* 182 */     ShopAdmin user = AdminThread.get();
/* 183 */     JSONObject object = new JSONObject();
/* 184 */     if (user == null) {
/* 185 */       object.put("login", false);
/*     */     }
/*     */     else {
/* 188 */       ShopPlug plug = this.manager.findById(id);
/* 189 */       if ((plug != null) && (fileExist(plug.getPath()))) {
/* 190 */         File zipFile = new File(this.realPathResolver.get(plug.getPath()));
/* 191 */         boolean isRepairPlug = isRepairPlug(zipFile);
/*     */ 
/* 193 */         if (isRepairPlug) {
/* 194 */           installPlug(zipFile, plug, true, request);
/*     */         }
/*     */         else {
/* 197 */           boolean fileConflict = isFileConflict(zipFile);
/* 198 */           if (fileConflict) {
/* 199 */             object.put("conflict", true);
/*     */           } else {
/* 201 */             object.put("conflict", false);
/* 202 */             installPlug(zipFile, plug, false, request);
/*     */           }
/*     */         }
/* 205 */         object.put("exist", true);
/*     */       } else {
/* 207 */         object.put("exist", false);
/*     */       }
/* 209 */       object.put("login", true);
/*     */     }
/* 211 */     ResponseUtils.renderJson(response, object.toString());
/*     */   }
/*     */ 
/*     */   private void installPlug(File zipFile, ShopPlug plug, boolean isRepairPlug, HttpServletRequest request) throws IOException
/*     */   {
/* 216 */     plug.setInstallTime(Calendar.getInstance().getTime());
/* 217 */     plug.setUsed(true);
/* 218 */     plug.setPlugRepair(isRepairPlug);
/* 219 */     this.resourceMng.installPlug(zipFile, plug);
/* 220 */     this.cmsLogMng.operating(request, "plug.log.install", "name=" + plug.getName());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/plug/o_uninstall.do"})
/*     */   public void uninstall(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws IOException, JSONException
/*     */   {
/* 236 */     ShopAdmin user = AdminThread.get();
/* 237 */     JSONObject object = new JSONObject();
/* 238 */     if (user == null) {
/* 239 */       object.put("login", false);
/*     */     } else {
/* 241 */       ShopPlug plug = this.manager.findById(id);
/* 242 */       if ((plug != null) && (fileExist(plug.getPath()))) {
/* 243 */         File file = new File(this.realPathResolver.get(plug.getPath()));
/*     */ 
/* 245 */         if (plug.getPlugRepair()) {
/* 246 */           object.put("repair", true);
/*     */         } else {
/* 248 */           object.put("repair", false);
/* 249 */           boolean fileConflict = plug.getFileConflict();
/* 250 */           if (!fileConflict) {
/* 251 */             this.resourceMng.deleteZipFile(file);
/* 252 */             plug.setUninstallTime(Calendar.getInstance().getTime());
/* 253 */             plug.setUsed(false);
/* 254 */             this.manager.update(plug);
/* 255 */             log.info("delete plug name={}", plug.getPath());
/* 256 */             this.cmsLogMng.operating(request, "plug.log.uninstall", "filename=" + plug.getName());
/* 257 */             object.put("conflict", false);
/*     */           } else {
/* 259 */             object.put("conflict", true);
/*     */           }
/*     */         }
/* 262 */         object.put("exist", true);
/*     */       } else {
/* 264 */         object.put("exist", false);
/*     */       }
/* 266 */       object.put("login", true);
/*     */     }
/* 268 */     ResponseUtils.renderJson(response, object.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/plug/o_delete.do"})
/*     */   public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
/* 274 */     WebErrors errors = validateDelete(ids, request);
/* 275 */     if (errors.hasErrors()) {
/* 276 */       return errors.showErrorPage(model);
/*     */     }
/* 278 */     ShopPlug[] beans = this.manager.deleteByIds(ids);
/* 279 */     for (ShopPlug bean : beans) {
/* 280 */       this.tplManager.delete(new String[] { bean.getPath() });
/* 281 */       log.info("delete CmsPlug id={}", bean.getId());
/*     */     }
/* 283 */     return list(pageNo, request, model);
/*     */   }
/*     */ 
/*     */   private boolean isRepairPlug(File file) {
/* 287 */     boolean isRepairPlug = false;
/* 288 */     String plugIni = "";
/* 289 */     String repairStr = "";
/*     */     try {
/* 291 */       plugIni = this.resourceMng.readFileFromZip(file, "plug.ini");
/* 292 */       if (StringUtils.isNotBlank(plugIni)) {
/* 293 */         String[] configs = plugIni.split(";");
/* 294 */         for (String c : configs) {
/* 295 */           String[] configAtt = c.split("=");
/* 296 */           if ((configAtt == null) || (configAtt.length != 2) || 
/* 297 */             (!configAtt[0].equals("plug_repair"))) continue;
/* 298 */           repairStr = configAtt[1];
/* 299 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 305 */       e.printStackTrace();
/*     */     }
/* 307 */     if ((StringUtils.isNotBlank(repairStr)) && (repairStr.toLowerCase().equals("true"))) {
/* 308 */       isRepairPlug = true;
/*     */     }
/* 310 */     return isRepairPlug;
/*     */   }
/*     */ 
/*     */   private boolean isFileConflict(File file) throws IOException
/*     */   {
/* 315 */     ZipFile zip = new ZipFile(file, "GBK");
/*     */ 
/* 320 */     boolean fileConflict = false;
/* 321 */     Enumeration en = zip.getEntries();
/* 322 */     while (en.hasMoreElements()) {
/* 323 */       ZipEntry entry = (ZipEntry)en.nextElement();
/* 324 */       String name = entry.getName();
/* 325 */       if (!entry.isDirectory()) {
/* 326 */         name = entry.getName();
/* 327 */         String filename = name;
/* 328 */         File outFile = new File(this.realPathResolver.get(filename));
/* 329 */         if (outFile.exists()) {
/* 330 */           fileConflict = true;
/* 331 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 335 */     zip.close();
/* 336 */     return fileConflict;
/*     */   }
/*     */ 
/*     */   private WebErrors validateSave(ShopPlug bean, HttpServletRequest request)
/*     */   {
/* 342 */     WebErrors errors = WebErrors.create(request);
/* 343 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long id, HttpServletRequest request) {
/* 347 */     WebErrors errors = WebErrors.create(request);
/* 348 */     Website site = SiteUtils.getWeb(request);
/* 349 */     if (vldExist(id, site.getId().longValue(), errors)) {
/* 350 */       return errors;
/*     */     }
/* 352 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpdate(ShopPlug plug, HttpServletRequest request) {
/* 356 */     WebErrors errors = WebErrors.create(request);
/* 357 */     Website site = SiteUtils.getWeb(request);
/* 358 */     if (vldExist(plug.getId(), site.getId().longValue(), errors)) {
/* 359 */       return errors;
/*     */     }
/* 361 */     ShopPlug dbPlug = this.manager.findById(plug.getId());
/*     */ 
/* 363 */     if ((dbPlug != null) && (dbPlug.getUsed()) && (!dbPlug.getPath().equals(plug.getPath()))) {
/* 364 */       errors.addErrorCode("error.plug.upload", new Object[] { dbPlug.getName() });
/*     */     }
/* 366 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long[] ids, HttpServletRequest request)
/*     */   {
/* 371 */     WebErrors errors = WebErrors.create(request);
/* 372 */     Website site = SiteUtils.getWeb(request);
/* 373 */     if (errors.ifEmpty(ids, "ids")) {
/* 374 */       return errors;
/*     */     }
/* 376 */     for (Long id : ids) {
/* 377 */       vldExist(id, site.getId().longValue(), errors);
/* 378 */       vldUsed(id, errors);
/*     */     }
/* 380 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateUpload(MultipartFile file, HttpServletRequest request)
/*     */   {
/* 385 */     WebErrors errors = WebErrors.create(request);
/* 386 */     if (errors.ifNull(file, "file")) {
/* 387 */       return errors;
/*     */     }
/* 389 */     String filename = file.getOriginalFilename();
/* 390 */     if ((filename != null) && (filename.indexOf("") != -1)) {
/* 391 */       errors.addErrorCode("upload.error.filename", new Object[] { filename });
/*     */     }
/* 393 */     String filePath = "/WEB-INF/plug/" + filename;
/*     */ 
/* 395 */     ShopPlug plug = this.manager.findByPath(filePath);
/* 396 */     File tempFile = new File(this.realPathResolver.get(filePath));
/*     */ 
/* 398 */     if ((plug != null) && (plug.getUsed()) && (tempFile.exists())) {
/* 399 */       errors.addErrorCode("error.plug.upload", new Object[] { plug.getName() });
/*     */     }
/* 401 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldExist(Long id, long siteId, WebErrors errors) {
/* 405 */     if (errors.ifNull(id, "id")) {
/* 406 */       return true;
/*     */     }
/* 408 */     ShopPlug entity = this.manager.findById(id);
/*     */ 
/* 410 */     return errors.ifNotExist(entity, ShopPlug.class, id);
/*     */   }
/*     */ 
/*     */   private boolean vldUsed(Long id, WebErrors errors)
/*     */   {
/* 416 */     if (errors.ifNull(id, "id")) {
/* 417 */       return true;
/*     */     }
/* 419 */     ShopPlug entity = this.manager.findById(id);
/* 420 */     if (entity.getUsed()) {
/* 421 */       errors.addErrorCode("error.plug.delele", new Object[] { entity.getName() });
/*     */     }
/* 423 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean fileExist(String filePath) {
/* 427 */     File file = new File(this.realPathResolver.get(filePath));
/* 428 */     return file.exists();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.assist.PlugAct
 * JD-Core Version:    0.6.0
 */