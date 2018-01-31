/*    */ package com.jspgou.cms.app;
/*    */ 
/*    */ import com.jspgou.cms.entity.ShopMember;
/*    */ import com.jspgou.cms.manager.ApiUtilMng;
/*    */ import com.jspgou.cms.web.SiteUtils;
/*    */ import com.jspgou.common.file.FileNameUtils;
/*    */ import com.jspgou.common.web.ResponseUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.io.FilenameUtils;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestParam;
/*    */ import org.springframework.web.context.ServletContextAware;
/*    */ import org.springframework.web.multipart.MultipartFile;
/*    */ 
/*    */ @Controller
/*    */ public class ImageUploadAct
/*    */   implements ServletContextAware
/*    */ {
/*    */   private ServletContext servletContext;
/*    */ 
/*    */   @Autowired
/*    */   private ApiUtilMng apiUtilMng;
/*    */ 
/*    */   @RequestMapping({"/api/upload/o_upload.jspx"})
/*    */   public void execute(@RequestParam(value="uploadFile", required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response)
/*    */     throws JSONException
/*    */   {
/* 40 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/* 41 */     JSONObject o = new JSONObject();
/*    */     try {
/* 43 */       if (user != null) {
/* 44 */         Website web = SiteUtils.getWeb(request);
/* 45 */         String real = this.servletContext.getRealPath(web.getUploadRel());
/* 46 */         String dateDir = FileNameUtils.genPathName();
/*    */ 
/* 48 */         File root = new File(real, dateDir);
/* 49 */         if (!root.exists()) {
/* 50 */           root.mkdirs();
/*    */         }
/*    */ 
/* 53 */         String ext = FilenameUtils.getExtension(file
/* 54 */           .getOriginalFilename());
/* 55 */         String fileName = FileNameUtils.genFileName(ext);
/*    */ 
/* 57 */         File tempFile = new File(root, fileName);
/*    */ 
/* 59 */         String relPath = "/u/" + dateDir + "/" + 
/* 60 */           fileName;
/*    */         try {
/* 62 */           file.transferTo(tempFile);
/* 63 */           o.put("relPath", relPath);
/* 64 */           o.put("message", "01");
/*    */         } catch (IllegalStateException e) {
/* 66 */           o.put("message", "00");
/*    */         } catch (IOException e) {
/* 68 */           o.put("message", "00");
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (JSONException localJSONException) {
/*    */     }
/* 74 */     ResponseUtils.renderJson(response, 
/* 75 */       this.apiUtilMng.getJsonStrng(o.toString(), "", request));
/*    */   }
/*    */ 
/*    */   public void setServletContext(ServletContext servletContext)
/*    */   {
/* 81 */     this.servletContext = servletContext;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.app.ImageUploadAct
 * JD-Core Version:    0.6.0
 */