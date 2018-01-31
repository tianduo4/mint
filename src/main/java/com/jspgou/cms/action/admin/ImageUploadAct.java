/*     */ package com.jspgou.cms.action.admin;
/*     */ 
/*     */ import com.jspgou.common.file.FileNameUtils;
/*     */ import com.jspgou.common.image.ImageUtils;
/*     */ import com.jspgou.core.entity.Ftp;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.context.ServletContextAware;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @Controller
/*     */ public class ImageUploadAct
/*     */   implements ServletContextAware
/*     */ {
/*  36 */   private static final Logger log = LoggerFactory.getLogger(ImageUploadAct.class);
/*     */   private static final String RESULT_PAGE = "/common/iframe_upload";
/*     */   private static final String RESULT_SWITCH_PAGE = "/common/iframe_switch_upload";
/*     */   private static final String RESULT_BIG_PAGE = "/common/iframe_big_upload";
/*     */   private static final String RESULT_AMP_PAGE = "/common/iframe_amp_upload";
/*     */   public static final String ERROR = "error";
/*     */   private ServletContext servletContext;
/*     */ 
/*     */   @RequestMapping({"/common/o_upload_image.do"})
/*     */   public String execute(String fileName, Integer uploadNum, Integer zoomWidth, Integer zoomHeight, @RequestParam(value="uploadFile", required=false) MultipartFile file, HttpServletRequest request, ModelMap model)
/*     */     throws IOException
/*     */   {
/*  61 */     WebErrors errors = validate(fileName, file, request);
/*  62 */     if (errors.hasErrors()) {
/*  63 */       model.addAttribute("error", errors.getErrors().get(0));
/*  64 */       return "/common/iframe_upload";
/*     */     }
/*  66 */     Website web = SiteUtils.getWeb(request);
/*  67 */     String real = this.servletContext.getRealPath(web.getUploadRel());
/*  68 */     String dateDir = FileNameUtils.genPathName();
/*  69 */     String ext = FilenameUtils.getExtension(file.getOriginalFilename());
/*     */     try
/*     */     {
/*     */       String relPath;
/*  72 */       if (web.getUploadFtp() != null) {
/*  73 */         Ftp ftp = web.getUploadFtp();
/*  74 */         String ftpUrl = ftp.getUrl();
/*     */         String relPath;
/*  75 */         if (!StringUtils.isBlank(fileName)) {
/*  76 */           fileName = fileName.substring(ftpUrl.length());
/*  77 */           relPath = ftp.storeByFilename(fileName, file
/*  78 */             .getInputStream());
/*     */         } else {
/*  80 */           String ctx = request.getContextPath();
/*  81 */           String relPath = ctx + "/" + "u" + "/" + fileName;
/*  82 */           relPath = ftp.storeByExt(relPath, ext, 
/*  83 */             file.getInputStream());
/*     */ 
/*  85 */           relPath = ftpUrl + relPath;
/*     */         }
/*     */       }
/*     */       else {
/*  89 */         File root = new File(real, dateDir);
/*  90 */         if (!root.exists()) {
/*  91 */           root.mkdirs();
/*     */         }
/*     */ 
/*  94 */         if (StringUtils.isBlank(fileName))
/*  95 */           fileName = FileNameUtils.genFileName(ext);
/*     */         else {
/*  97 */           fileName = FilenameUtils.getName(fileName);
/*     */         }
/*     */ 
/* 100 */         File tempFile = new File(root, fileName);
/*     */ 
/* 103 */         String ctx = request.getContextPath();
/* 104 */         relPath = ctx + "/" + "u" + "/" + dateDir + "/" + fileName;
/* 105 */         model.addAttribute("zoomWidth", zoomWidth);
/* 106 */         model.addAttribute("zoomHeight", zoomHeight);
/* 107 */         file.transferTo(tempFile);
/*     */       }
/* 109 */       model.addAttribute("uploadPath", relPath);
/* 110 */       model.addAttribute("uploadNum", uploadNum);
/*     */     } catch (IllegalStateException e) {
/* 112 */       model.addAttribute("error", e.getMessage());
/* 113 */       log.error("upload file error!", e);
/*     */     } catch (IOException e) {
/* 115 */       model.addAttribute("error", e.getMessage());
/* 116 */       log.error("upload file error!", e);
/*     */     }
/* 118 */     return "/common/iframe_upload";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/common/o_upload_switch_image.do"})
/*     */   public String executeSwitch(String fileName, Integer uploadNum, @RequestParam(value="uploadFileSwitch", required=false) MultipartFile file, HttpServletRequest request, ModelMap model)
/*     */   {
/* 128 */     WebErrors errors = validate(fileName, file, request);
/* 129 */     if (errors.hasErrors()) {
/* 130 */       model.addAttribute("error", errors.getErrors().get(0));
/* 131 */       return "/common/iframe_switch_upload";
/*     */     }
/* 133 */     Website web = SiteUtils.getWeb(request);
/* 134 */     String real = this.servletContext.getRealPath(web.getUploadRel());
/* 135 */     String dateDir = FileNameUtils.genPathName();
/* 136 */     String ext = FilenameUtils.getExtension(file.getOriginalFilename());
/*     */     try
/*     */     {
/*     */       String relPath;
/* 139 */       if (web.getUploadFtp() != null) {
/* 140 */         Ftp ftp = web.getUploadFtp();
/* 141 */         String ftpUrl = ftp.getUrl();
/*     */         String relPath;
/* 142 */         if (!StringUtils.isBlank(fileName)) {
/* 143 */           fileName = fileName.substring(ftpUrl.length());
/* 144 */           relPath = ftp.storeByFilename(fileName, file
/* 145 */             .getInputStream());
/*     */         } else {
/* 147 */           String ctx = request.getContextPath();
/* 148 */           String relPath = ctx + "/" + "u" + "/" + fileName;
/* 149 */           relPath = ftp.storeByExt(relPath, ext, 
/* 150 */             file.getInputStream());
/*     */ 
/* 152 */           relPath = ftpUrl + relPath;
/*     */         }
/*     */       }
/*     */       else {
/* 156 */         File root = new File(real, dateDir);
/* 157 */         if (!root.exists()) {
/* 158 */           root.mkdirs();
/*     */         }
/*     */ 
/* 161 */         if (StringUtils.isBlank(fileName))
/* 162 */           fileName = FileNameUtils.genFileName(ext);
/*     */         else {
/* 164 */           fileName = FilenameUtils.getName(fileName);
/*     */         }
/*     */ 
/* 167 */         File tempFile = new File(root, fileName);
/*     */ 
/* 169 */         String ctx = request.getContextPath();
/* 170 */         relPath = ctx + "/" + "u" + "/" + dateDir + "/" + fileName;
/* 171 */         file.transferTo(tempFile);
/*     */       }
/* 173 */       model.addAttribute("uploadPath", relPath);
/* 174 */       model.addAttribute("uploadNum", uploadNum);
/*     */     } catch (IllegalStateException e) {
/* 176 */       model.addAttribute("error", e.getMessage());
/* 177 */       log.error("upload file error!", e);
/*     */     } catch (IOException e) {
/* 179 */       model.addAttribute("error", e.getMessage());
/* 180 */       log.error("upload file error!", e);
/*     */     }
/* 182 */     return "/common/iframe_switch_upload";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/common/o_upload_big_image.do"})
/*     */   public String executeBig(String fileName, Integer uploadNum, @RequestParam(value="uploadFileBig", required=false) MultipartFile file, HttpServletRequest request, ModelMap model)
/*     */   {
/* 192 */     WebErrors errors = validate(fileName, file, request);
/* 193 */     if (errors.hasErrors()) {
/* 194 */       model.addAttribute("error", errors.getErrors().get(0));
/* 195 */       return "/common/iframe_big_upload";
/*     */     }
/* 197 */     Website web = SiteUtils.getWeb(request);
/* 198 */     String real = this.servletContext.getRealPath(web.getUploadRel());
/* 199 */     String dateDir = FileNameUtils.genPathName();
/* 200 */     String ext = FilenameUtils.getExtension(file.getOriginalFilename());
/*     */     try
/*     */     {
/*     */       String relPath;
/* 203 */       if (web.getUploadFtp() != null) {
/* 204 */         Ftp ftp = web.getUploadFtp();
/* 205 */         String ftpUrl = ftp.getUrl();
/*     */         String relPath;
/* 206 */         if (!StringUtils.isBlank(fileName)) {
/* 207 */           fileName = fileName.substring(ftpUrl.length());
/* 208 */           relPath = ftp.storeByFilename(fileName, file
/* 209 */             .getInputStream());
/*     */         } else {
/* 211 */           String ctx = request.getContextPath();
/* 212 */           String relPath = ctx + "/" + "u" + "/" + fileName;
/* 213 */           relPath = ftp.storeByExt(relPath, ext, 
/* 214 */             file.getInputStream());
/*     */ 
/* 216 */           relPath = ftpUrl + relPath;
/*     */         }
/*     */       }
/*     */       else {
/* 220 */         File root = new File(real, dateDir);
/* 221 */         if (!root.exists()) {
/* 222 */           root.mkdirs();
/*     */         }
/*     */ 
/* 225 */         if (StringUtils.isBlank(fileName))
/*     */         {
/* 227 */           fileName = FileNameUtils.genFileName(ext);
/*     */         }
/* 229 */         else fileName = FilenameUtils.getName(fileName);
/*     */ 
/* 232 */         File tempFile = new File(root, fileName);
/*     */ 
/* 234 */         String ctx = request.getContextPath();
/* 235 */         relPath = ctx + "/" + "u" + "/" + dateDir + "/" + fileName;
/*     */ 
/* 237 */         file.transferTo(tempFile);
/*     */       }
/* 239 */       model.addAttribute("uploadPath", relPath);
/* 240 */       model.addAttribute("uploadNum", uploadNum);
/*     */     } catch (IllegalStateException e) {
/* 242 */       model.addAttribute("error", e.getMessage());
/* 243 */       log.error("upload file error!", e);
/*     */     } catch (IOException e) {
/* 245 */       model.addAttribute("error", e.getMessage());
/* 246 */       log.error("upload file error!", e);
/*     */     }
/* 248 */     return "/common/iframe_big_upload";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/common/o_upload_amp_image.do"})
/*     */   public String executeAmp(String fileName, Integer uploadNum, @RequestParam(value="uploadFileAmp", required=false) MultipartFile file, HttpServletRequest request, ModelMap model)
/*     */   {
/* 258 */     WebErrors errors = validate(fileName, file, request);
/* 259 */     if (errors.hasErrors()) {
/* 260 */       model.addAttribute("error", errors.getErrors().get(0));
/* 261 */       return "/common/iframe_amp_upload";
/*     */     }
/* 263 */     Website web = SiteUtils.getWeb(request);
/* 264 */     String real = this.servletContext.getRealPath(web.getUploadRel());
/* 265 */     String dateDir = FileNameUtils.genPathName();
/* 266 */     String ext = FilenameUtils.getExtension(file.getOriginalFilename());
/*     */     try
/*     */     {
/*     */       String relPath;
/* 269 */       if (web.getUploadFtp() != null) {
/* 270 */         Ftp ftp = web.getUploadFtp();
/* 271 */         String ftpUrl = ftp.getUrl();
/*     */         String relPath;
/* 272 */         if (!StringUtils.isBlank(fileName)) {
/* 273 */           fileName = fileName.substring(ftpUrl.length());
/* 274 */           relPath = ftp.storeByFilename(fileName, file
/* 275 */             .getInputStream());
/*     */         } else {
/* 277 */           String ctx = request.getContextPath();
/* 278 */           String relPath = ctx + "/" + "u" + "/" + fileName;
/* 279 */           relPath = ftp.storeByExt(relPath, ext, 
/* 280 */             file.getInputStream());
/*     */ 
/* 282 */           relPath = ftpUrl + relPath;
/*     */         }
/*     */       }
/*     */       else {
/* 286 */         File root = new File(real, dateDir);
/* 287 */         if (!root.exists()) {
/* 288 */           root.mkdirs();
/*     */         }
/*     */ 
/* 291 */         if (StringUtils.isBlank(fileName))
/*     */         {
/* 293 */           fileName = FileNameUtils.genFileName(ext);
/*     */         }
/* 295 */         else fileName = FilenameUtils.getName(fileName);
/*     */ 
/* 298 */         File tempFile = new File(root, fileName);
/*     */ 
/* 300 */         String ctx = request.getContextPath();
/* 301 */         relPath = ctx + "/" + "u" + "/" + dateDir + "/" + fileName;
/*     */ 
/* 303 */         file.transferTo(tempFile);
/*     */       }
/* 305 */       model.addAttribute("uploadPath", relPath);
/* 306 */       model.addAttribute("uploadNum", uploadNum);
/*     */     } catch (IllegalStateException e) {
/* 308 */       model.addAttribute("error", e.getMessage());
/* 309 */       log.error("upload file error!", e);
/*     */     } catch (IOException e) {
/* 311 */       model.addAttribute("error", e.getMessage());
/* 312 */       log.error("upload file error!", e);
/*     */     }
/* 314 */     return "/common/iframe_amp_upload";
/*     */   }
/*     */ 
/*     */   private WebErrors validate(String fileName, MultipartFile file, HttpServletRequest request)
/*     */   {
/* 319 */     WebErrors errors = WebErrors.create(request);
/* 320 */     if (file == null) {
/* 321 */       errors.addErrorCode("imageupload.error.noFileToUpload");
/* 322 */       return errors;
/*     */     }
/* 324 */     if (StringUtils.isBlank(fileName)) {
/* 325 */       fileName = file.getOriginalFilename();
/*     */     }
/* 327 */     String ext = FilenameUtils.getExtension(fileName);
/* 328 */     if (!ImageUtils.isImage(ext)) {
/* 329 */       errors.addErrorCode("imageupload.error.notSupportExt", new Object[] { ext });
/* 330 */       return errors;
/*     */     }
/* 332 */     return errors;
/*     */   }
/*     */ 
/*     */   public void setServletContext(ServletContext servletContext)
/*     */   {
/* 339 */     this.servletContext = servletContext;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.ImageUploadAct
 * JD-Core Version:    0.6.0
 */