/*     */ package com.jspgou.common.ueditor;
/*     */ 
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class LocalizedMessages
/*     */ {
/*     */   private static String getMessage(HttpServletRequest request, String key, Object[] args)
/*     */   {
/*  35 */     return MessageResolver.getMessage(request, key, args);
/*     */   }
/*     */ 
/*     */   public static String getCompatibleBrowserYes(HttpServletRequest request)
/*     */   {
/*  42 */     return getMessage(request, "fck.editor.compatibleBrowser.yes", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getCompatibleBrowserNo(HttpServletRequest request)
/*     */   {
/*  47 */     return getMessage(request, "fck.editor.compatibleBrowser.no", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getFileUploadEnabled(HttpServletRequest request)
/*     */   {
/*  54 */     return getMessage(request, "fck.connector.fileUpload.enabled", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getFileUploadDisabled(HttpServletRequest request)
/*     */   {
/*  62 */     return getMessage(request, "fck.connector.fileUpload.disabled", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getFileRenamedWarning(HttpServletRequest request, String newFileName)
/*     */   {
/*  75 */     return getMessage(request, 
/*  76 */       "fck.connector.fileUpload.file_renamed_warning", new Object[] { newFileName });
/*     */   }
/*     */ 
/*     */   public static String getInvalidFileTypeSpecified(HttpServletRequest request)
/*     */   {
/*  85 */     return getMessage(request, 
/*  86 */       "fck.connector.fileUpload.invalid_file_type_specified", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getFileUploadWriteError(HttpServletRequest request)
/*     */   {
/*  94 */     return getMessage(request, "fck.connector.fileUpload.write_error", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getGetResourcesEnabled(HttpServletRequest request)
/*     */   {
/* 102 */     return getMessage(request, "fck.connector.getResources.enabled", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getGetResourcesDisabled(HttpServletRequest request)
/*     */   {
/* 110 */     return getMessage(request, "fck.connector.getResources.disabled", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getGetResourcesReadError(HttpServletRequest request)
/*     */   {
/* 118 */     return getMessage(request, "fck.connector.getResources.read_error", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getCreateFolderEnabled(HttpServletRequest request)
/*     */   {
/* 126 */     return getMessage(request, "fck.connector.createFolder.enabled", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getCreateFolderDisabled(HttpServletRequest request)
/*     */   {
/* 134 */     return getMessage(request, "fck.connector.createFolder.disabled", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getInvalidCommandSpecified(HttpServletRequest request)
/*     */   {
/* 142 */     return getMessage(request, "fck.connector.invalid_command_specified", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getFolderAlreadyExistsError(HttpServletRequest request)
/*     */   {
/* 151 */     return getMessage(request, 
/* 152 */       "fck.connector.createFolder.folder_already_exists_error", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getInvalidNewFolderNameSpecified(HttpServletRequest request)
/*     */   {
/* 162 */     return getMessage(request, 
/* 163 */       "fck.connector.createFolder.invalid_new_folder_name_specified", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getCreateFolderWriteError(HttpServletRequest request)
/*     */   {
/* 171 */     return getMessage(request, "fck.connector.createFolder.write_error", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getInvalidResouceTypeSpecified(HttpServletRequest request)
/*     */   {
/* 180 */     return getMessage(request, 
/* 181 */       "fck.connector.invalid_resource_type_specified", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getInvalidCurrentFolderSpecified(HttpServletRequest request)
/*     */   {
/* 190 */     return getMessage(request, 
/* 191 */       "fck.connector.invalid_current_folder_specified", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getInvalidFileSuffixSpecified(HttpServletRequest request) {
/* 195 */     return getMessage(request, 
/* 196 */       "fck.connector.invalid_file_suffix_specified", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getInvalidFileToLargeSpecified(HttpServletRequest request, String filename, Integer max) {
/* 200 */     return getMessage(request, "fck.connector.invalid_file_toolarge_specified", new Object[] { filename, max });
/*     */   }
/*     */ 
/*     */   public static String getInvalidUploadDailyLimitSpecified(HttpServletRequest request, String lavesize) {
/* 204 */     return getMessage(request, "fck.connector.invalid_file_dailylimit_specified", new Object[] { lavesize });
/*     */   }
/*     */ 
/*     */   public static String getInvalidUploadMultipartSpecified(HttpServletRequest request) {
/* 208 */     return getMessage(request, "fck.connector.invalid_multipart_specified", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getInvalidUploadInputStreamSpecified(HttpServletRequest request) {
/* 212 */     return getMessage(request, "fck.connector.invalid_inputstream_specified", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getRemoteImageSuccessSpecified(HttpServletRequest request) {
/* 216 */     return getMessage(request, "fck.connector.getremoteimage_success", new Object[0]);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.ueditor.LocalizedMessages
 * JD-Core Version:    0.6.0
 */