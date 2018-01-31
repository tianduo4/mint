/*    */ package com.jspgou.cms.action.member;
/*    */ 
/*    */ import com.jspgou.cms.manager.ProductMng;
/*    */ import com.jspgou.common.image.ImageScale;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.web.SiteUtils;
/*    */ import java.io.File;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.context.ServletContextAware;
/*    */ 
/*    */ @Controller
/*    */ public class ImageCutAct
/*    */   implements ServletContextAware
/*    */ {
/* 29 */   private static final Logger log = LoggerFactory.getLogger(ImageCutAct.class);
/*    */   public static final String IMAGE_SELECT_RESULT = "/common/image_area_select";
/*    */   public static final String IMAGE_CUTED = "/common/image_cuted";
/*    */   public static final String ERROR = "error";
/*    */ 
/*    */   @Autowired
/*    */   private ImageScale imageScale;
/*    */   private ServletContext servletContext;
/*    */ 
/*    */   @Autowired
/*    */   private ProductMng productMng;
/*    */ 
/*    */   @RequestMapping({"/common/v_image_area_select.jspx"})
/*    */   public String imageAreaSelect(String imgSrcRoot, String imgSrcPath, Integer zoomWidth, Integer zoomHeight, Integer uploadNum, HttpServletRequest request, ModelMap model)
/*    */   {
/* 47 */     model.addAttribute("imgSrcRoot", imgSrcRoot);
/* 48 */     model.addAttribute("imgSrcPath", imgSrcPath);
/* 49 */     model.addAttribute("zoomWidth", zoomWidth);
/* 50 */     model.addAttribute("zoomHeight", zoomHeight);
/* 51 */     model.addAttribute("uploadNum", uploadNum);
/* 52 */     return "/common/image_area_select";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/common/o_image_cut.jspx"})
/*    */   public String imageCut(String imgSrcPath, Integer imgTop, Integer imgLeft, Integer imgWidth, Integer imgHeight, Integer reMinWidth, Integer reMinHeight, Float imgScale, Integer uploadNum, HttpServletRequest request, ModelMap model)
/*    */   {
/* 60 */     Website web = SiteUtils.getWeb(request);
/* 61 */     String real = this.servletContext.getRealPath(web.getUploadRel(imgSrcPath));
/* 62 */     File srcFile = new File(real);
/*    */     try {
/* 64 */       if (imgWidth != null) {
/* 65 */         if (imgWidth.intValue() > 0)
/* 66 */           this.imageScale.resizeFix(srcFile, srcFile, reMinWidth.intValue(), reMinHeight.intValue(), 
/* 67 */             getLen(imgTop.intValue(), imgScale.floatValue()), getLen(imgLeft.intValue(), 
/* 68 */             imgScale.floatValue()), getLen(imgWidth.intValue(), imgScale.floatValue()), 
/* 69 */             getLen(imgHeight.intValue(), imgScale.floatValue()));
/*    */         else
/* 71 */           this.imageScale.resizeFix(srcFile, srcFile, reMinWidth.intValue(), reMinHeight.intValue());
/*    */       }
/*    */       else {
/* 74 */         model.addAttribute("error", this.productMng.getTipFile("Picture.width.cannot.beempty"));
/*    */       }
/* 76 */       model.addAttribute("uploadNum", uploadNum);
/*    */     } catch (Exception e) {
/* 78 */       log.error("cut image error", e);
/* 79 */       model.addAttribute("error", e.getMessage());
/*    */     }
/* 81 */     return "/common/image_cuted";
/*    */   }
/*    */ 
/*    */   private int getLen(int len, float imgScale) {
/* 85 */     return Math.round(len / imgScale);
/*    */   }
/*    */ 
/*    */   public void setServletContext(ServletContext servletContext)
/*    */   {
/* 95 */     this.servletContext = servletContext;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.member.ImageCutAct
 * JD-Core Version:    0.6.0
 */