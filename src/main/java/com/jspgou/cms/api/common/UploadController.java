/*     */ package com.jspgou.cms.api.common;
/*     */ 
/*     */ import com.jspgou.cms.api.ApiResponse;
/*     */ import com.jspgou.cms.api.ApiValidate;
/*     */ import com.jspgou.cms.web.CmsUtils;
/*     */ import com.jspgou.cms.web.SignValidate;
/*     */ import com.jspgou.common.image.ImageUtils;
/*     */ import com.jspgou.common.upload.FileRepository;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @Controller
/*     */ public class UploadController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteMng webSiteMng;
/*     */ 
/*     */   @Autowired
/*     */   private FileRepository fileRepository;
/*     */ 
/*     */   @SignValidate
/*     */   @RequestMapping({"/upload/upload"})
/*     */   public void upload(String type, @RequestParam(value="uploadFile", required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  47 */     String body = "\"\"";
/*  48 */     String message = "\"\"";
/*  49 */     int code = 0;
/*     */     try {
/*  51 */       Website site = CmsUtils.getWebsite(request);
/*  52 */       if (StringUtils.isBlank(type)) {
/*  53 */         type = "image";
/*     */       }
/*  55 */       WebErrors errors = validate(type, file, request);
/*  56 */       if (errors.hasErrors()) {
/*  57 */         code = Integer.getInteger((String)errors.getErrors().get(0)).intValue();
/*  58 */         message = (String)errors.getErrors().get(1);
/*     */       }
/*     */       else {
/*  61 */         errors = ApiValidate.validateRequiredParams(errors, new Object[] { file });
/*  62 */         if (!errors.hasErrors()) {
/*  63 */           String origName = file.getOriginalFilename();
/*  64 */           String ext = FilenameUtils.getExtension(origName).toLowerCase(
/*  65 */             Locale.ENGLISH);
/*  66 */           String filepath = "";
/*  67 */           filepath = this.fileRepository.upload(site, ext, file);
/*  68 */           if (StringUtils.isEmpty(filepath)) {
/*  69 */             code = 400;
/*  70 */             message = "\"upload file error!\"";
/*     */           } else {
/*  72 */             JSONObject json = new JSONObject();
/*  73 */             json.put("url", filepath);
/*  74 */             body = json.toString();
/*  75 */             code = 200;
/*  76 */             message = "\"success\"";
/*     */           }
/*     */         } else {
/*  79 */           code = 202;
/*  80 */           message = "\"param error\"";
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/*  84 */       e.printStackTrace();
/*  85 */       code = 400;
/*  86 */       message = "\"upload file error!\"";
/*     */     }
/*  88 */     ApiResponse apiResponse = new ApiResponse(body, message, code);
/*  89 */     ResponseUtils.renderApiJson(response, request, apiResponse);
/*     */   }
/*     */ 
/*     */   private WebErrors validate(String type, MultipartFile file, HttpServletRequest request)
/*     */   {
/*  95 */     WebErrors errors = WebErrors.create(request);
/*     */ 
/*  97 */     if ((file == null) || (type == null)) {
/*  98 */       errors.addErrorCode(String.valueOf(202));
/*  99 */       errors.addErrorString("\"param error\"");
/* 100 */       return errors;
/*     */     }
/*     */ 
/* 103 */     if ((!type.equals("image")) && (!type.equals("attach")) && (!type.equals("video"))) {
/* 104 */       errors.addErrorCode(String.valueOf(401));
/* 105 */       errors.addErrorString("\"not support uploadType\"");
/* 106 */       return errors;
/*     */     }
/* 108 */     String filename = file.getOriginalFilename();
/* 109 */     String ext = FilenameUtils.getExtension(filename);
/*     */ 
/* 111 */     if ((filename != null) && ((filename.contains("/")) || (filename.contains("\\")) || (filename.indexOf("") != -1))) {
/* 112 */       errors.addErrorCode(String.valueOf(402));
/* 113 */       errors.addErrorString("\"fileName contain illegalchar\"");
/* 114 */       return errors;
/*     */     }
/* 116 */     if (type.equals("image"))
/*     */     {
/* 118 */       if (!ImageUtils.isValidImageExt(ext)) {
/* 119 */         errors.addErrorCode(String.valueOf(403));
/* 120 */         errors.addErrorString("\"imafile not support ext\"");
/* 121 */         return errors;
/*     */       }
/*     */     }
/* 124 */     return errors;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.api.common.UploadController
 * JD-Core Version:    0.6.0
 */