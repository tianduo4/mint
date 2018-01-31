/*     */ package com.jspgou.common.fck;
/*     */ 
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class LocalizedMessages
/*     */ {
/*     */   private static String getMessage(HttpServletRequest request, String key, Object[] args)
/*     */   {
/*  21 */     return MessageResolver.getMessage(request, key, args);
/*     */   }
/*     */ 
/*     */   public static String getCompatibleBrowserYes(HttpServletRequest request)
/*     */   {
/*  28 */     return getMessage(request, "fck.editor.compatibleBrowser.yes", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getCompatibleBrowserNo(HttpServletRequest request)
/*     */   {
/*  33 */     return getMessage(request, "fck.editor.compatibleBrowser.no", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getFileUploadEnabled(HttpServletRequest request)
/*     */   {
/*  40 */     return getMessage(request, "fck.connector.fileUpload.enabled", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getFileUploadDisabled(HttpServletRequest request)
/*     */   {
/*  48 */     return getMessage(request, "fck.connector.fileUpload.disabled", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getFileRenamedWarning(HttpServletRequest request, String newFileName)
/*     */   {
/*  61 */     return getMessage(request, 
/*  62 */       "fck.connector.fileUpload.file_renamed_warning", new Object[] { newFileName });
/*     */   }
/*     */ 
/*     */   public static String getInvalidFileTypeSpecified(HttpServletRequest request)
/*     */   {
/*  71 */     return getMessage(request, 
/*  72 */       "fck.connector.fileUpload.invalid_file_type_specified", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getFileUploadWriteError(HttpServletRequest request)
/*     */   {
/*  80 */     return getMessage(request, "fck.connector.fileUpload.write_error", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getGetResourcesEnabled(HttpServletRequest request)
/*     */   {
/*  88 */     return getMessage(request, "fck.connector.getResources.enabled", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getGetResourcesDisabled(HttpServletRequest request)
/*     */   {
/*  96 */     return getMessage(request, "fck.connector.getResources.disabled", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getGetResourcesReadError(HttpServletRequest request)
/*     */   {
/* 104 */     return getMessage(request, "fck.connector.getResources.read_error", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getCreateFolderEnabled(HttpServletRequest request)
/*     */   {
/* 112 */     return getMessage(request, "fck.connector.createFolder.enabled", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getCreateFolderDisabled(HttpServletRequest request)
/*     */   {
/* 120 */     return getMessage(request, "fck.connector.createFolder.disabled", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getInvalidCommandSpecified(HttpServletRequest request)
/*     */   {
/* 128 */     return getMessage(request, "fck.connector.invalid_command_specified", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getFolderAlreadyExistsError(HttpServletRequest request)
/*     */   {
/* 137 */     return getMessage(request, 
/* 138 */       "fck.connector.createFolder.folder_already_exists_error", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getInvalidNewFolderNameSpecified(HttpServletRequest request)
/*     */   {
/* 148 */     return getMessage(request, 
/* 149 */       "fck.connector.createFolder.invalid_new_folder_name_specified", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getCreateFolderWriteError(HttpServletRequest request)
/*     */   {
/* 157 */     return getMessage(request, "fck.connector.createFolder.write_error", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getInvalidResouceTypeSpecified(HttpServletRequest request)
/*     */   {
/* 166 */     return getMessage(request, 
/* 167 */       "fck.connector.invalid_resource_type_specified", new Object[0]);
/*     */   }
/*     */ 
/*     */   public static String getInvalidCurrentFolderSpecified(HttpServletRequest request)
/*     */   {
/* 176 */     return getMessage(request, 
/* 177 */       "fck.connector.invalid_current_folder_specified", new Object[0]);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.fck.LocalizedMessages
 * JD-Core Version:    0.6.0
 */