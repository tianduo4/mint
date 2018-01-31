/*    */ package com.jspgou.cms.ueditor.upload;
/*    */ 
/*    */ import com.jspgou.cms.ueditor.PathFormat;
/*    */ import com.jspgou.cms.ueditor.define.BaseState;
/*    */ import com.jspgou.cms.ueditor.define.FileType;
/*    */ import com.jspgou.cms.ueditor.define.State;
/*    */ import com.jspgou.common.web.springmvc.RealPathResolver;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.commons.fileupload.FileItemIterator;
/*    */ import org.apache.commons.fileupload.FileItemStream;
/*    */ import org.apache.commons.fileupload.FileUploadException;
/*    */ import org.apache.commons.fileupload.disk.DiskFileItemFactory;
/*    */ import org.apache.commons.fileupload.servlet.ServletFileUpload;
/*    */ 
/*    */ public class BinaryUploader
/*    */ {
/*    */   public static final State save(HttpServletRequest request, RealPathResolver realPathResolver)
/*    */   {
/* 27 */     FileItemStream fileStream = null;
/* 28 */     boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;
/*    */ 
/* 30 */     if (!ServletFileUpload.isMultipartContent(request)) {
/* 31 */       return new BaseState(false, 5);
/*    */     }
/*    */ 
/* 34 */     ServletFileUpload upload = new ServletFileUpload(
/* 35 */       new DiskFileItemFactory());
/*    */ 
/* 37 */     if (isAjaxUpload) {
/* 38 */       upload.setHeaderEncoding("UTF-8");
/*    */     }
/*    */     try
/*    */     {
/* 42 */       FileItemIterator iterator = upload.getItemIterator(request);
/*    */ 
/* 44 */       while (iterator.hasNext()) {
/* 45 */         fileStream = iterator.next();
/*    */ 
/* 47 */         if (!fileStream.isFormField())
/*    */           break;
/* 49 */         fileStream = null;
/*    */       }
/*    */ 
/* 52 */       if (fileStream == null) {
/* 53 */         return new BaseState(false, 7);
/*    */       }
/*    */ 
/* 56 */       String savePath = "/u/cms/www/";
/* 57 */       String originFileName = fileStream.getName();
/* 58 */       String suffix = FileType.getSuffixByFilename(originFileName);
/*    */ 
/* 60 */       originFileName = originFileName.substring(0, 
/* 61 */         originFileName.length() - suffix.length());
/* 62 */       savePath = savePath + suffix;
/*    */ 
/* 64 */       savePath = PathFormat.parse(savePath, originFileName);
/*    */ 
/* 66 */       String physicalPath = realPathResolver.get(savePath);
/*    */ 
/* 68 */       InputStream is = fileStream.openStream();
/* 69 */       State storageState = StorageManager.saveFileByInputStream(is, 
/* 70 */         physicalPath);
/* 71 */       is.close();
/*    */ 
/* 73 */       if (storageState.isSuccess()) {
/* 74 */         storageState.putInfo("url", PathFormat.format(savePath));
/* 75 */         storageState.putInfo("type", suffix);
/* 76 */         storageState.putInfo("original", originFileName + suffix);
/*    */       }
/*    */ 
/* 79 */       return storageState;
/*    */     } catch (FileUploadException e) {
/* 81 */       return new BaseState(false, 6);
/*    */     } catch (IOException localIOException) {
/*    */     }
/* 84 */     return new BaseState(false, 4);
/*    */   }
/*    */ 
/*    */   private static boolean validType(String type, String[] allowTypes) {
/* 88 */     List list = Arrays.asList(allowTypes);
/*    */ 
/* 90 */     return list.contains(type);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.ueditor.upload.BinaryUploader
 * JD-Core Version:    0.6.0
 */