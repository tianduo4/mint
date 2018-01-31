/*     */ package com.jspgou.cms.action.admin;
/*     */ 
/*     */ import com.jspgou.common.fck.Command;
/*     */ import com.jspgou.common.fck.ResourceType;
/*     */ import com.jspgou.common.fck.UploadResponse;
/*     */ import com.jspgou.common.fck.Utils;
/*     */ import com.jspgou.common.upload.FileRepository;
/*     */ import com.jspgou.common.upload.UploadUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.io.FilenameUtils;
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
/*     */ public class FckAct
/*     */ {
/*     */   public static final String UPLOAD_PATH = "/u/jspgou/";
/*  46 */   private static final Logger log = LoggerFactory.getLogger(FckAct.class);
/*     */   private FileRepository fileRepository;
/*     */ 
/*     */   @RequestMapping(value={"/fck/upload.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public void upload(@RequestParam(value="Command", required=false) String commandStr, @RequestParam(value="Type", required=false) String typeStr, @RequestParam(value="CurrentFolder", required=false) String currentFolderStr, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  56 */     log.debug("Entering Dispatcher#doPost");
/*  57 */     responseInit(response);
/*  58 */     if ((Utils.isEmpty(commandStr)) && (Utils.isEmpty(currentFolderStr))) {
/*  59 */       commandStr = "QuickUpload";
/*  60 */       currentFolderStr = "/";
/*  61 */       if (Utils.isEmpty(typeStr)) {
/*  62 */         typeStr = "File";
/*     */       }
/*     */     }
/*  65 */     if ((currentFolderStr != null) && (!currentFolderStr.startsWith("/"))) {
/*  66 */       currentFolderStr = "/".concat(currentFolderStr);
/*     */     }
/*  68 */     log.debug("Parameter Command: {}", commandStr);
/*  69 */     log.debug("Parameter Type: {}", typeStr);
/*  70 */     log.debug("Parameter CurrentFolder: {}", currentFolderStr);
/*  71 */     UploadResponse ur = validateUpload(request, commandStr, typeStr, 
/*  72 */       currentFolderStr);
/*  73 */     if (ur == null) {
/*  74 */       ur = doUpload(request, typeStr, currentFolderStr);
/*     */     }
/*  76 */     PrintWriter out = response.getWriter();
/*  77 */     out.print(ur);
/*  78 */     out.flush();
/*  79 */     out.close();
/*     */   }
/*     */ 
/*     */   private UploadResponse doUpload(HttpServletRequest request, String typeStr, String currentFolderStr)
/*     */     throws Exception
/*     */   {
/*  85 */     ResourceType type = ResourceType.getDefaultResourceType(typeStr);
/*     */     try {
/*  87 */       MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
/*  88 */       MultipartFile uplFile = (MultipartFile)((Map.Entry)multipartRequest.getFileMap().entrySet().iterator().next()).getValue();
/*  89 */       String filename = FilenameUtils.getName(uplFile
/*  90 */         .getOriginalFilename());
/*  91 */       log.debug("Parameter NewFile: {}", filename);
/*  92 */       String ext = FilenameUtils.getExtension(filename);
/*  93 */       if (type.isDeniedExtension(ext)) {
/*  94 */         return UploadResponse.getInvalidFileTypeError(request);
/*     */       }
/*     */ 
/*  97 */       String fileUrl = this.fileRepository.storeByExt("/u/jspgou/", ext, uplFile);
/*     */ 
/*  99 */       fileUrl = request.getContextPath() + fileUrl;
/* 100 */       return UploadResponse.getOK(request, fileUrl); } catch (IOException e) {
/*     */     }
/* 102 */     return UploadResponse.getFileUploadWriteError(request);
/*     */   }
/*     */ 
/*     */   private void responseInit(HttpServletResponse response)
/*     */   {
/* 107 */     response.setCharacterEncoding("UTF-8");
/* 108 */     response.setContentType("text/html");
/* 109 */     response.setHeader("Cache-Control", "no-cache");
/*     */   }
/*     */ 
/*     */   private UploadResponse validateUpload(HttpServletRequest request, String commandStr, String typeStr, String currentFolderStr)
/*     */   {
/* 116 */     if (!Command.isValidForPost(commandStr)) {
/* 117 */       return UploadResponse.getInvalidCommandError(request);
/*     */     }
/* 119 */     if (!ResourceType.isValidType(typeStr)) {
/* 120 */       return UploadResponse.getInvalidResourceTypeError(request);
/*     */     }
/* 122 */     if (!UploadUtils.isValidPath(currentFolderStr)) {
/* 123 */       return UploadResponse.getInvalidCurrentFolderError(request);
/*     */     }
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setFileRepository(FileRepository fileRepository)
/*     */   {
/* 133 */     this.fileRepository = fileRepository;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.FckAct
 * JD-Core Version:    0.6.0
 */