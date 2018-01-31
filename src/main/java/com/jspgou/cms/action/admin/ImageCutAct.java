/*    */ package com.jspgou.cms.action.admin;
/*    */ 
/*    */ import com.jspgou.cms.manager.ProductMng;
/*    */ import com.jspgou.common.image.ImageScale;
/*    */ import com.jspgou.common.web.springmvc.RealPathResolver;
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
/* 26 */   private static final Logger log = LoggerFactory.getLogger(ImageCutAct.class);
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
/*    */   private RealPathResolver realPathResolver;
/*    */ 
/*    */   @RequestMapping({"/common/v_image_area_select.do"})
/*    */   public String imageAreaSelect(String imgSrcPath, Integer zoomWidth, Integer zoomHeight, Integer uploadNum, HttpServletRequest request, ModelMap model)
/*    */   {
/* 44 */     model.addAttribute("imgSrcPath", imgSrcPath);
/* 45 */     model.addAttribute("zoomWidth", zoomWidth);
/* 46 */     model.addAttribute("zoomHeight", zoomHeight);
/* 47 */     model.addAttribute("uploadNum", uploadNum);
/* 48 */     return "/common/image_area_select";
/*    */   }
/*    */ 
/*    */   @RequestMapping({"/common/o_image_cut.do"})
/*    */   public String imageCut(String imgSrcPath, Integer imgTop, Integer imgLeft, Integer imgWidth, Integer imgHeight, Integer reMinWidth, Integer reMinHeight, Float imgScale, Integer uploadNum, HttpServletRequest request, ModelMap model)
/*    */   {
/* 56 */     String ctx = request.getContextPath();
/* 57 */     imgSrcPath = imgSrcPath.substring(ctx.length());
/* 58 */     String real = this.realPathResolver.get(imgSrcPath);
/* 59 */     File srcFile = new File(real);
/* 60 */     model.addAttribute("uploadNum", uploadNum);
/*    */     try {
/* 62 */       if (imgWidth != null) {
/* 63 */         if (imgWidth.intValue() > 0)
/* 64 */           this.imageScale.resizeFix(srcFile, srcFile, reMinWidth.intValue(), reMinHeight.intValue(), getLen(imgTop.intValue(), imgScale.floatValue()), getLen(imgLeft.intValue(), 
/* 65 */             imgScale.floatValue()), getLen(imgWidth.intValue(), imgScale.floatValue()), getLen(imgHeight.intValue(), imgScale.floatValue()));
/*    */         else
/* 67 */           this.imageScale.resizeFix(srcFile, srcFile, reMinWidth.intValue(), reMinHeight.intValue());
/*    */       }
/*    */       else
/* 70 */         model.addAttribute("error", this.productMng.getTipFile("Picture.width.cannot.beempty"));
/*    */     }
/*    */     catch (Exception e) {
/* 73 */       log.error("cut image error", e);
/* 74 */       model.addAttribute("error", e.getMessage());
/*    */     }
/* 76 */     return "/common/image_cuted";
/*    */   }
/*    */ 
/*    */   private int getLen(int len, float imgScale) {
/* 80 */     return Math.round(len / imgScale);
/*    */   }
/*    */ 
/*    */   public void setServletContext(ServletContext servletContext)
/*    */   {
/* 91 */     this.servletContext = servletContext;
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setRealPathResolver(RealPathResolver realPathResolver)
/*    */   {
/* 98 */     this.realPathResolver = realPathResolver;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.admin.ImageCutAct
 * JD-Core Version:    0.6.0
 */