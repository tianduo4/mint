package com.mint.cms.action.admin;

import com.mint.cms.manager.ProductMng;
import com.mint.common.image.ImageScale;
import com.mint.common.web.springmvc.RealPathResolver;

import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

@Controller
public class ImageCutAct
        implements ServletContextAware {
    private static final Logger log = LoggerFactory.getLogger(ImageCutAct.class);
    public static final String IMAGE_SELECT_RESULT = "/common/image_area_select";
    public static final String IMAGE_CUTED = "/common/image_cuted";
    public static final String ERROR = "error";

    @Autowired
    private ImageScale imageScale;
    private ServletContext servletContext;

    @Autowired
    private ProductMng productMng;
    private RealPathResolver realPathResolver;

    @RequestMapping({"/common/v_image_area_select.do"})
    public String imageAreaSelect(String imgSrcPath, Integer zoomWidth, Integer zoomHeight, Integer uploadNum, HttpServletRequest request, ModelMap model) {
        model.addAttribute("imgSrcPath", imgSrcPath);
        model.addAttribute("zoomWidth", zoomWidth);
        model.addAttribute("zoomHeight", zoomHeight);
        model.addAttribute("uploadNum", uploadNum);
        return "/common/image_area_select";
    }

    @RequestMapping({"/common/o_image_cut.do"})
    public String imageCut(String imgSrcPath, Integer imgTop, Integer imgLeft, Integer imgWidth, Integer imgHeight, Integer reMinWidth, Integer reMinHeight, Float imgScale, Integer uploadNum, HttpServletRequest request, ModelMap model) {
        String ctx = request.getContextPath();
        imgSrcPath = imgSrcPath.substring(ctx.length());
        String real = this.realPathResolver.get(imgSrcPath);
        File srcFile = new File(real);
        model.addAttribute("uploadNum", uploadNum);
        try {
            if (imgWidth != null) {
                if (imgWidth.intValue() > 0)
                    this.imageScale.resizeFix(srcFile, srcFile, reMinWidth.intValue(), reMinHeight.intValue(), getLen(imgTop.intValue(), imgScale.floatValue()), getLen(imgLeft.intValue(),
                            imgScale.floatValue()), getLen(imgWidth.intValue(), imgScale.floatValue()), getLen(imgHeight.intValue(), imgScale.floatValue()));
                else
                    this.imageScale.resizeFix(srcFile, srcFile, reMinWidth.intValue(), reMinHeight.intValue());
            } else
                model.addAttribute("error", this.productMng.getTipFile("Picture.width.cannot.beempty"));
        } catch (Exception e) {
            log.error("cut image error", e);
            model.addAttribute("error", e.getMessage());
        }
        return "/common/image_cuted";
    }

    private int getLen(int len, float imgScale) {
        return Math.round(len / imgScale);
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Autowired
    public void setRealPathResolver(RealPathResolver realPathResolver) {
        this.realPathResolver = realPathResolver;
    }
}

