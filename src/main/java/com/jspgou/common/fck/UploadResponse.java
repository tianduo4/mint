/*     */ package com.jspgou.common.fck;
/*     */ 
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class UploadResponse
/*     */ {
/*     */   protected Object[] parameters;
/*     */   public static final int EN_OK = 0;
/*     */   public static final int EN_CUSTOM_ERROR = 1;
/*     */   public static final int EN_CUSTOM_WARNING = 101;
/*     */   public static final int EN_FILE_RENAMED_WARNING = 201;
/*     */   public static final int EN_INVALID_FILE_TYPE_ERROR = 202;
/*     */   public static final int EN_SECURITY_ERROR = 203;
/*     */ 
/*     */   public UploadResponse(Object[] arguments)
/*     */   {
/*  52 */     if ((arguments.length < 1) || (arguments.length > 4)) {
/*  53 */       throw new IllegalArgumentException(
/*  54 */         "The amount of arguments has to be between 1 and 4");
/*     */     }
/*  56 */     this.parameters = new Object[arguments.length];
/*     */ 
/*  58 */     if (!(arguments[0] instanceof Integer)) {
/*  59 */       throw new IllegalArgumentException(
/*  60 */         "The first argument has to be an error number (int)");
/*     */     }
/*  62 */     System.arraycopy(arguments, 0, this.parameters, 0, arguments.length);
/*     */   }
/*     */ 
/*     */   public void setCustomMessage(String customMassage)
/*     */   {
/*  75 */     if (!StringUtils.isBlank(customMassage)) {
/*  76 */       if (this.parameters.length == 1) {
/*  77 */         Object errorNumber = this.parameters[0];
/*  78 */         this.parameters = new Object[4];
/*  79 */         this.parameters[0] = errorNumber;
/*  80 */         this.parameters[1] = null;
/*  81 */         this.parameters[2] = null;
/*     */       }
/*  83 */       this.parameters[3] = customMassage;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static UploadResponse getOK(HttpServletRequest request, String fileUrl)
/*     */   {
/*  91 */     String callback = request.getParameter("CKEditorFuncNum");
/*  92 */     return new UploadResponse(new Object[] { Integer.valueOf(Integer.parseInt(callback)), fileUrl });
/*     */   }
/*     */ 
/*     */   public static UploadResponse getFileRenamedWarning(HttpServletRequest request, String fileUrl, String newFileName)
/*     */   {
/*  98 */     return new UploadResponse(new Object[] { Integer.valueOf(201), fileUrl, 
/*  99 */       newFileName, LocalizedMessages.getFileRenamedWarning(request, 
/* 100 */       newFileName) });
/*     */   }
/*     */ 
/*     */   public static UploadResponse getInvalidFileTypeError(HttpServletRequest request)
/*     */   {
/* 106 */     return new UploadResponse(new Object[] { Integer.valueOf(202), 
/* 107 */       LocalizedMessages.getInvalidFileTypeSpecified(request) });
/*     */   }
/*     */ 
/*     */   public static UploadResponse getInvalidCommandError(HttpServletRequest request)
/*     */   {
/* 113 */     return new UploadResponse(new Object[] { Integer.valueOf(1), null, null, 
/* 114 */       LocalizedMessages.getInvalidCommandSpecified(request) });
/*     */   }
/*     */ 
/*     */   public static UploadResponse getInvalidResourceTypeError(HttpServletRequest request)
/*     */   {
/* 120 */     return new UploadResponse(new Object[] { Integer.valueOf(1), null, null, 
/* 121 */       LocalizedMessages.getInvalidResouceTypeSpecified(request) });
/*     */   }
/*     */ 
/*     */   public static UploadResponse getInvalidCurrentFolderError(HttpServletRequest request)
/*     */   {
/* 127 */     return new UploadResponse(new Object[] { Integer.valueOf(1), null, null, 
/* 128 */       LocalizedMessages.getInvalidCurrentFolderSpecified(request) });
/*     */   }
/*     */ 
/*     */   public static UploadResponse getFileUploadDisabledError(HttpServletRequest request)
/*     */   {
/* 134 */     return new UploadResponse(new Object[] { Integer.valueOf(203), null, null, 
/* 135 */       LocalizedMessages.getFileUploadDisabled(request) });
/*     */   }
/*     */ 
/*     */   public static UploadResponse getFileUploadWriteError(HttpServletRequest request)
/*     */   {
/* 141 */     return new UploadResponse(new Object[] { Integer.valueOf(1), null, null, 
/* 142 */       LocalizedMessages.getFileUploadWriteError(request) });
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 152 */     StringBuffer sb = new StringBuffer(400);
/* 153 */     sb.append("<script type=\"text/javascript\">\n");
/*     */ 
/* 157 */     sb
/* 158 */       .append("(function(){var d=document.domain;while (true){try{var A=window.parent.document.domain;break;}catch(e) {};d=d.replace(/.*?(?:\\.|$)/,'');if (d.length==0) break;try{document.domain=d;}catch (e){break;}}})();\n");
/* 159 */     sb.append("window.parent.CKEDITOR.tools.callFunction(");
/*     */ 
/* 161 */     for (Object parameter : this.parameters) {
/* 162 */       if ((parameter instanceof Integer)) {
/* 163 */         sb.append(parameter);
/*     */       } else {
/* 165 */         sb.append("'");
/* 166 */         if (parameter != null)
/* 167 */           sb.append(parameter);
/* 168 */         sb.append("'");
/*     */       }
/* 170 */       sb.append(",");
/*     */     }
/*     */ 
/* 173 */     sb.deleteCharAt(sb.length() - 1);
/* 174 */     sb.append(");\n");
/* 175 */     sb.append("</script>");
/*     */ 
/* 177 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.fck.UploadResponse
 * JD-Core Version:    0.6.0
 */