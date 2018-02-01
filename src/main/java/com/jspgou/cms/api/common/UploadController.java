package com.jspgou.cms.api.common;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.web.CmsUtils;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.image.ImageUtils;
import com.jspgou.common.upload.FileRepository;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.WebsiteMng;
import com.jspgou.core.web.WebErrors;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

    @Autowired
    private WebsiteMng webSiteMng;

    @Autowired
    private FileRepository fileRepository;

    @SignValidate
    @RequestMapping({"/upload/upload"})
    public void upload(String type, @RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"\"";
        int code = 0;
        try {
            Website site = CmsUtils.getWebsite(request);
            if (StringUtils.isBlank(type)) {
                type = "image";
            }
            WebErrors errors = validate(type, file, request);
            if (errors.hasErrors()) {
                code = Integer.getInteger((String) errors.getErrors().get(0)).intValue();
                message = (String) errors.getErrors().get(1);
            } else {
                errors = ApiValidate.validateRequiredParams(errors, new Object[]{file});
                if (!errors.hasErrors()) {
                    String origName = file.getOriginalFilename();
                    String ext = FilenameUtils.getExtension(origName).toLowerCase(
                            Locale.ENGLISH);
                    String filepath = "";
                    filepath = this.fileRepository.upload(site, ext, file);
                    if (StringUtils.isEmpty(filepath)) {
                        code = 400;
                        message = "\"upload file error!\"";
                    } else {
                        JSONObject json = new JSONObject();
                        json.put("url", filepath);
                        body = json.toString();
                        code = 200;
                        message = "\"success\"";
                    }
                } else {
                    code = 202;
                    message = "\"param error\"";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 400;
            message = "\"upload file error!\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validate(String type, MultipartFile file, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);

        if ((file == null) || (type == null)) {
            errors.addErrorCode(String.valueOf(202));
            errors.addErrorString("\"param error\"");
            return errors;
        }

        if ((!type.equals("image")) && (!type.equals("attach")) && (!type.equals("video"))) {
            errors.addErrorCode(String.valueOf(401));
            errors.addErrorString("\"not support uploadType\"");
            return errors;
        }
        String filename = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(filename);

        if ((filename != null) && ((filename.contains("/")) || (filename.contains("\\")) || (filename.indexOf("") != -1))) {
            errors.addErrorCode(String.valueOf(402));
            errors.addErrorString("\"fileName contain illegalchar\"");
            return errors;
        }
        if (type.equals("image")) {
            if (!ImageUtils.isValidImageExt(ext)) {
                errors.addErrorCode(String.valueOf(403));
                errors.addErrorString("\"imafile not support ext\"");
                return errors;
            }
        }
        return errors;
    }
}

