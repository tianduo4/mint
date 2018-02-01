package com.mint.common.checkcode;

import com.mint.common.web.session.SessionProvider;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.multitype.MultiTypeCaptchaService;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class CaptchaServlet extends HttpServlet {
    public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";
    private MultiTypeCaptchaService captchaService;
    private SessionProvider session;

    public void init(ServletConfig servletconfig)
            throws ServletException {
        super.init(servletconfig);
        WebApplicationContext webapplicationcontext = WebApplicationContextUtils.getWebApplicationContext(servletconfig.getServletContext());
        this.captchaService = ((MultiTypeCaptchaService) BeanFactoryUtils.beanOfTypeIncludingAncestors(webapplicationcontext, MultiTypeCaptchaService.class));
        this.session = ((SessionProvider) BeanFactoryUtils.beanOfTypeIncludingAncestors(webapplicationcontext, SessionProvider.class));
    }

    protected void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
            throws ServletException, IOException {
        byte[] abyte0 = null;
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        try {
            String s = this.session.getSessionId(httpservletrequest, httpservletresponse);
            BufferedImage bufferedimage = this.captchaService.getImageChallengeForID(s, httpservletrequest.getLocale());
            ImageIO.write(bufferedimage, "jpeg", bytearrayoutputstream);
        } catch (IllegalArgumentException illegalargumentexception) {
            httpservletresponse.sendError(404);
            return;
        } catch (CaptchaServiceException captchaserviceexception) {
            httpservletresponse.sendError(500);
            return;
        }
        abyte0 = bytearrayoutputstream.toByteArray();
        httpservletresponse.setHeader("Cache-Control", "no-store");
        httpservletresponse.setHeader("Pragma", "no-cache");
        httpservletresponse.setDateHeader("Expires", 0L);
        httpservletresponse.setContentType("image/jpeg");
        ServletOutputStream servletoutputstream = httpservletresponse.getOutputStream();
        servletoutputstream.write(abyte0);
        servletoutputstream.flush();
        servletoutputstream.close();
    }
}

