package com.mint.cms.app;

import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.ApiUtilMng;
import com.mint.cms.web.SiteUtils;
import com.mint.common.file.FileNameUtils;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageUploadAct
        implements ServletContextAware {
    private ServletContext servletContext;

    @Autowired
    private ApiUtilMng apiUtilMng;

    @RequestMapping({"/api/upload/o_upload.jspx"})
    public void execute(@RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        JSONObject o = new JSONObject();
        try {
            if (user != null) {
                Website web = SiteUtils.getWeb(request);
                String real = this.servletContext.getRealPath(web.getUploadRel());
                String dateDir = FileNameUtils.genPathName();

                File root = new File(real, dateDir);
                if (!root.exists()) {
                    root.mkdirs();
                }

                String ext = FilenameUtils.getExtension(file
                        .getOriginalFilename());
                String fileName = FileNameUtils.genFileName(ext);

                File tempFile = new File(root, fileName);

                String relPath = "/u/" + dateDir + "/" +
                        fileName;
                try {
                    file.transferTo(tempFile);
                    o.put("relPath", relPath);
                    o.put("message", "01");
                } catch (IllegalStateException e) {
                    o.put("message", "00");
                } catch (IOException e) {
                    o.put("message", "00");
                }
            }
        } catch (JSONException localJSONException) {
        }
        ResponseUtils.renderJson(response,
                this.apiUtilMng.getJsonStrng(o.toString(), "", request));
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

