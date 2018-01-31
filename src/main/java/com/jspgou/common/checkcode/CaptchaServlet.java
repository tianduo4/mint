/*    */ package com.jspgou.common.checkcode;
/*    */ 
/*    */ import com.jspgou.common.web.session.SessionProvider;
/*    */ import com.octo.captcha.service.CaptchaServiceException;
/*    */ import com.octo.captcha.service.multitype.MultiTypeCaptchaService;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import javax.imageio.ImageIO;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletOutputStream;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.io.output.ByteArrayOutputStream;
/*    */ import org.springframework.beans.factory.BeanFactoryUtils;
/*    */ import org.springframework.web.context.WebApplicationContext;
/*    */ import org.springframework.web.context.support.WebApplicationContextUtils;
/*    */ 
/*    */ public class CaptchaServlet extends HttpServlet
/*    */ {
/*    */   public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";
/*    */   private MultiTypeCaptchaService captchaService;
/*    */   private SessionProvider session;
/*    */ 
/*    */   public void init(ServletConfig servletconfig)
/*    */     throws ServletException
/*    */   {
/* 30 */     super.init(servletconfig);
/* 31 */     WebApplicationContext webapplicationcontext = WebApplicationContextUtils.getWebApplicationContext(servletconfig.getServletContext());
/* 32 */     this.captchaService = ((MultiTypeCaptchaService)BeanFactoryUtils.beanOfTypeIncludingAncestors(webapplicationcontext, MultiTypeCaptchaService.class));
/* 33 */     this.session = ((SessionProvider)BeanFactoryUtils.beanOfTypeIncludingAncestors(webapplicationcontext, SessionProvider.class));
/*    */   }
/*    */ 
/*    */   protected void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
/*    */     throws ServletException, IOException
/*    */   {
/* 39 */     byte[] abyte0 = null;
/* 40 */     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/*    */     try {
/* 42 */       String s = this.session.getSessionId(httpservletrequest, httpservletresponse);
/* 43 */       BufferedImage bufferedimage = this.captchaService.getImageChallengeForID(s, httpservletrequest.getLocale());
/* 44 */       ImageIO.write(bufferedimage, "jpeg", bytearrayoutputstream);
/*    */     } catch (IllegalArgumentException illegalargumentexception) {
/* 46 */       httpservletresponse.sendError(404);
/* 47 */       return;
/*    */     } catch (CaptchaServiceException captchaserviceexception) {
/* 49 */       httpservletresponse.sendError(500);
/* 50 */       return;
/*    */     }
/* 52 */     abyte0 = bytearrayoutputstream.toByteArray();
/* 53 */     httpservletresponse.setHeader("Cache-Control", "no-store");
/* 54 */     httpservletresponse.setHeader("Pragma", "no-cache");
/* 55 */     httpservletresponse.setDateHeader("Expires", 0L);
/* 56 */     httpservletresponse.setContentType("image/jpeg");
/* 57 */     ServletOutputStream servletoutputstream = httpservletresponse.getOutputStream();
/* 58 */     servletoutputstream.write(abyte0);
/* 59 */     servletoutputstream.flush();
/* 60 */     servletoutputstream.close();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.checkcode.CaptchaServlet
 * JD-Core Version:    0.6.0
 */