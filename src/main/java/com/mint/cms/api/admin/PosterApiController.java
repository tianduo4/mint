package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.entity.Poster;
import com.mint.cms.manager.PosterMng;
import com.mint.cms.web.SignValidate;
import com.mint.common.web.ResponseUtils;
import com.mint.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PosterApiController {

    @Autowired
    private PosterMng posterMng;

    @RequestMapping({"/poster/list"})
    public void list(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            JSONArray jsons = new JSONArray();
            List<Poster> lists = this.posterMng.getPage();
            for (Poster poster : lists) {
                jsons.add(poster.converToJson());
            }
            body = jsons.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/poster/update"})
    public void update(String ids, String picUrls, String interUrls, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{picUrls, ids, interUrls});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                String[] idstrs = ids.split(",");
                Integer[] id = new Integer[idstrs.length];
                for (int i = 0; i < idstrs.length; i++) {
                    if ((StringUtils.isEmpty(idstrs[i])) || ("0".equals(idstrs[i])))
                        id[i] = Integer.valueOf(0);
                    else {
                        id[i] = Integer.valueOf(Integer.parseInt(idstrs[i]));
                    }
                }
                String[] picUrl = picUrls.split(",");
                String[] interUrl = interUrls.split(",");
                if ((idstrs != null) && (picUrl != null) && (interUrl != null) && (id.length == picUrl.length) && (id.length == interUrl.length)) {
                    this.posterMng.updateByApi(id, picUrl, interUrl);
                } else {
                    code = 202;
                    message = "\"param error\"";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

