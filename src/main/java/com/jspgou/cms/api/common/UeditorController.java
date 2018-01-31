/*     */ package com.jspgou.cms.api.common;
/*     */ 
/*     */ import com.jspgou.cms.service.ImageSvc;
/*     */ import com.jspgou.cms.ueditor.PathFormat;
/*     */ import com.jspgou.cms.ueditor.define.BaseState;
/*     */ import com.jspgou.cms.ueditor.define.MultiState;
/*     */ import com.jspgou.cms.ueditor.define.State;
/*     */ import com.jspgou.cms.ueditor.hunter.ImageHunter;
/*     */ import com.jspgou.cms.ueditor.upload.StorageManager;
/*     */ import com.jspgou.cms.web.CmsThreadVariable;
/*     */ import com.jspgou.common.image.ImageUtils;
/*     */ import com.jspgou.common.ueditor.LocalizedMessages;
/*     */ import com.jspgou.common.ueditor.ResourceType;
/*     */ import com.jspgou.common.ueditor.Utils;
/*     */ import com.jspgou.common.upload.FileRepository;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.springmvc.RealPathResolver;
/*     */ import com.jspgou.core.entity.Ftp;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ import org.springframework.web.multipart.MultipartHttpServletRequest;
/*     */ 
/*     */ @Controller
/*     */ public class UeditorController
/*     */ {
/*  57 */   private static final Logger log = LoggerFactory.getLogger(UeditorController.class);
/*  58 */   private static final String[] allowFilesuffix = { "jpg", "png", "bmp", "gif", "txt", "doc", "docx", "xls", "xlsl", "ppt", "pptx", "wps", "odt" };
/*     */   private static final String STATE = "state";
/*     */   private static final String SUCCESS = "SUCCESS";
/*     */   private static final String URL = "url";
/*     */   private static final String ORIGINAL = "original";
/*     */   private static final String TITLE = "title";
/*     */   private FileRepository fileRepository;
/*     */   private RealPathResolver realPathResolver;
/*     */ 
/*     */   @Autowired
/*     */   private ImageSvc imgSvc;
/*     */ 
/*     */   @RequestMapping({"/ueditor/upload"})
/*     */   public void upload(@RequestParam(value="Type", required=false) String typeStr, Boolean mark, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  77 */     responseInit(response);
/*  78 */     if (Utils.isEmpty(typeStr)) {
/*  79 */       typeStr = "File";
/*     */     }
/*  81 */     if (mark == null) {
/*  82 */       mark = Boolean.valueOf(false);
/*     */     }
/*  84 */     JSONObject json = new JSONObject();
/*  85 */     JSONObject ob = validateUpload(request, typeStr);
/*  86 */     if (ob == null)
/*  87 */       json = doUpload(request, typeStr, mark);
/*     */     else {
/*  89 */       json = ob;
/*     */     }
/*     */ 
/*  92 */     ResponseUtils.renderText(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/ueditor/getRemoteImage"})
/*     */   public void getRemoteImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  98 */     String[] list = request.getParameterValues("source[]");
/*  99 */     Website site = CmsThreadVariable.getSite();
/* 100 */     State state = new ImageHunter(this.imgSvc, site).captureByApi(site.getUrlPrefix(RequestUtils.getRequestAgreement(request)), list);
/*     */ 
/* 108 */     System.out.println("state.toJSONString()->" + state.toJSONString());
/* 109 */     ResponseUtils.renderJson(response, state.toJSONString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/ueditor/imageManager"})
/*     */   public void imageManager(Integer picNum, Boolean insite, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 121 */     State state = listFile(request, getStartIndex(request));
/* 122 */     ResponseUtils.renderText(response, state.toJSONString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/ueditor/scrawlImage"})
/*     */   public void scrawlImage(HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 129 */     State state = scrawlImage(request.getParameter("upfile"), CmsThreadVariable.getSite());
/* 130 */     ResponseUtils.renderText(response, state.toString());
/*     */   }
/*     */ 
/*     */   private JSONObject doUpload(HttpServletRequest request, String typeStr, Boolean mark) throws Exception
/*     */   {
/* 135 */     ResourceType type = ResourceType.getDefaultResourceType(typeStr);
/* 136 */     JSONObject result = new JSONObject();
/*     */     try {
/* 138 */       MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
/*     */ 
/* 140 */       MultipartFile uplFile = 
/* 141 */         (MultipartFile)((Map.Entry)multipartRequest.getFileMap().entrySet()
/* 141 */         .iterator().next()).getValue();
/*     */ 
/* 144 */       String filename = FilenameUtils.getName(uplFile
/* 145 */         .getOriginalFilename());
/* 146 */       log.debug("Parameter NewFile: {}", filename);
/* 147 */       String ext = FilenameUtils.getExtension(filename);
/* 148 */       if (type.isDeniedExtension(ext)) {
/* 149 */         result.put("state", 
/* 150 */           LocalizedMessages.getInvalidFileTypeSpecified(request));
/* 151 */         return result;
/*     */       }
/* 153 */       if ((type.equals(ResourceType.IMAGE)) && 
/* 154 */         (!ImageUtils.isImage(uplFile.getInputStream()))) {
/* 155 */         result.put("state", 
/* 156 */           LocalizedMessages.getInvalidFileTypeSpecified(request));
/* 157 */         return result;
/*     */       }
/*     */ 
/* 160 */       Website site = CmsThreadVariable.getSite();
/*     */       String fileUrl;
/* 161 */       if (site.getUploadFtp() != null) {
/* 162 */         Ftp ftp = site.getUploadFtp();
/* 163 */         String fileUrl = ftp.storeByExt("/u", ext, uplFile
/* 164 */           .getInputStream());
/*     */ 
/* 166 */         fileUrl = ftp.getUrl() + fileUrl;
/*     */       } else {
/* 168 */         fileUrl = this.fileRepository.storeByExt("/u", 
/* 169 */           ext, uplFile);
/*     */ 
/* 171 */         fileUrl = site.getUrlPrefix(RequestUtils.getRequestAgreement(request)) + request.getContextPath() + fileUrl;
/*     */       }
/*     */ 
/* 175 */       result.put("url", fileUrl);
/* 176 */       result.put("original", filename);
/* 177 */       result.put("type", ext);
/*     */ 
/* 179 */       result.put("state", "SUCCESS");
/* 180 */       result.put("title", filename);
/*     */ 
/* 182 */       return result;
/*     */     } catch (IOException e) {
/* 184 */       result.put("state", 
/* 185 */         LocalizedMessages.getFileUploadWriteError(request));
/* 186 */     }return result;
/*     */   }
/*     */ 
/*     */   public State listFile(HttpServletRequest request, int index)
/*     */   {
/* 191 */     Website site = CmsThreadVariable.getSite();
/* 192 */     String uploadPath = "/u";
/* 193 */     File dir = new File(this.realPathResolver.get(uploadPath));
/* 194 */     State state = null;
/* 195 */     if (!dir.exists()) {
/* 196 */       return new BaseState(false, 302);
/*     */     }
/*     */ 
/* 199 */     if (!dir.isDirectory()) {
/* 200 */       return new BaseState(false, 301);
/*     */     }
/*     */ 
/* 203 */     Collection list = FileUtils.listFiles(dir, null, true);
/*     */ 
/* 205 */     if ((index < 0) || (index > list.size())) {
/* 206 */       state = new MultiState(true);
/*     */     } else {
/* 208 */       Object[] fileList = Arrays.copyOfRange(list.toArray(), index, index + 20);
/* 209 */       state = getState(this.realPathResolver.get(""), site.getContextPath(), fileList);
/*     */     }
/*     */ 
/* 212 */     state.putInfo("start", index);
/* 213 */     state.putInfo("total", list.size());
/*     */ 
/* 215 */     return state;
/*     */   }
/*     */ 
/*     */   public int getStartIndex(HttpServletRequest request)
/*     */   {
/* 220 */     String start = request.getParameter("start");
/*     */     try {
/* 222 */       return Integer.parseInt(start); } catch (Exception e) {
/*     */     }
/* 224 */     return 0;
/*     */   }
/*     */ 
/*     */   private State getState(String rootPath, String contextPath, Object[] files)
/*     */   {
/* 230 */     MultiState state = new MultiState(true);
/* 231 */     BaseState fileState = null;
/*     */ 
/* 233 */     File file = null;
/*     */ 
/* 235 */     for (Object obj : files) {
/* 236 */       if (obj == null) {
/*     */         break;
/*     */       }
/* 239 */       file = (File)obj;
/* 240 */       fileState = new BaseState(true);
/* 241 */       fileState.putInfo("url", PathFormat.format(getPath(rootPath, contextPath, file)));
/* 242 */       state.addState(fileState);
/*     */     }
/* 244 */     return state;
/*     */   }
/*     */ 
/*     */   private String getPath(String rootPath, String contextPath, File file) {
/* 248 */     String path = file.getAbsolutePath();
/* 249 */     if (StringUtils.isNotBlank(contextPath)) {
/* 250 */       return contextPath + path.replace(rootPath, "/");
/*     */     }
/* 252 */     return path.replace(rootPath, "/");
/*     */   }
/*     */ 
/*     */   private void responseInit(HttpServletResponse response)
/*     */   {
/* 258 */     response.setCharacterEncoding("UTF-8");
/* 259 */     response.setContentType("text/html");
/*     */ 
/* 261 */     response.setHeader("Cache-Control", "no-cache");
/*     */   }
/*     */ 
/*     */   private JSONObject validateUpload(HttpServletRequest request, String typeStr)
/*     */     throws JSONException
/*     */   {
/* 267 */     JSONObject result = new JSONObject();
/* 268 */     MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
/* 269 */     MultipartFile uplFile = 
/* 270 */       (MultipartFile)((Map.Entry)multipartRequest.getFileMap().entrySet()
/* 270 */       .iterator().next()).getValue();
/* 271 */     String filename = FilenameUtils.getName(uplFile.getOriginalFilename());
/* 272 */     String ext = FilenameUtils.getExtension(filename).toLowerCase(
/* 273 */       Locale.ENGLISH);
/* 274 */     boolean isallowSuffix = false;
/* 275 */     int i = 0; for (int len = allowFilesuffix.length; i < len; i++) {
/* 276 */       if (allowFilesuffix[i].equals(ext)) {
/* 277 */         isallowSuffix = true;
/*     */       }
/*     */     }
/*     */ 
/* 281 */     if (!isallowSuffix) {
/* 282 */       result.put("state", 
/* 283 */         LocalizedMessages.getInvalidFileSuffixSpecified(request));
/* 284 */       return result;
/*     */     }
/* 286 */     if (!ResourceType.isValidType(typeStr)) {
/* 287 */       result.put("state", 
/* 288 */         LocalizedMessages.getInvalidResouceTypeSpecified(request));
/* 289 */       return result;
/*     */     }
/* 291 */     return null;
/*     */   }
/*     */ 
/*     */   public State scrawlImage(String content, Website site)
/*     */   {
/* 296 */     byte[] data = decode(content);
/*     */ 
/* 298 */     String suffix = "jpg";
/*     */ 
/* 300 */     String savePath = site.getContextPath() + "/u" + "/temp.jpg";
/*     */ 
/* 302 */     String physicalPath = this.realPathResolver.get(savePath);
/*     */ 
/* 304 */     State storageState = StorageManager.saveBinaryFile(data, physicalPath);
/*     */ 
/* 306 */     File file = new File(physicalPath);
/*     */ 
/* 309 */     String fileUrl = "";
/*     */     try {
/* 311 */       FileInputStream fileInputStream = new FileInputStream(file);
/* 312 */       if (site.getUploadFtp() != null) {
/* 313 */         Ftp ftp = site.getUploadFtp();
/* 314 */         fileUrl = ftp.storeByExt("/u", suffix, fileInputStream);
/*     */ 
/* 316 */         fileUrl = ftp.getUrl() + fileUrl;
/*     */       } else {
/* 318 */         fileUrl = this.fileRepository.storeByExt("/u", suffix, file);
/*     */ 
/* 320 */         fileUrl = site.getContextPath() + fileUrl;
/*     */       }
/*     */     } catch (Exception e) {
/* 323 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 326 */     if (storageState.isSuccess()) {
/* 327 */       storageState.putInfo("url", fileUrl);
/* 328 */       storageState.putInfo("type", suffix);
/* 329 */       storageState.putInfo("original", "");
/*     */     }
/*     */ 
/* 332 */     return storageState;
/*     */   }
/*     */ 
/*     */   private static byte[] decode(String content) {
/* 336 */     return Base64.decodeBase64(content);
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setFileRepository(FileRepository fileRepository)
/*     */   {
/* 346 */     this.fileRepository = fileRepository;
/*     */   }
/* 350 */   @Autowired
/*     */   public void setRealPathResolver(RealPathResolver realPathResolver) { this.realPathResolver = realPathResolver;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.common.UeditorController
 * JD-Core Version:    0.6.0
 */