package com.mint.cms.app;

import com.mint.cms.entity.Brand;
import com.mint.cms.manager.BrandMng;
import com.mint.common.util.ConvertMapToJson;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BrandAppAct {

    @Autowired
    private BrandMng brandMng;

    @RequestMapping({"/api/brandList.jspx"})
    public void brandList(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        Map map = new HashMap();
        Website web = SiteUtils.getWeb(request);
        String firstResult = request.getParameter("firsrResult");
        String maxResults = request.getParameter("maxResults");
        String callback = request.getParameter("callback");
        int first_result = 0;
        int max_results = 0;
        String result = "00";
        if (StringUtils.isNotBlank(firstResult)) {
            first_result = Integer.parseInt(firstResult);
        }
        if (StringUtils.isNotBlank(maxResults)) {
            max_results = Integer.parseInt(maxResults);
        }
        List list = this.brandMng.getListForTag(web.getId(), first_result,
                max_results);
        if (list != null) {
            if (list.size() > 0) {
                result = "01";
                map.put("pd", getBrandListJson(list));
            } else {
                result = "02";
            }
        } else result = "03";

        map.put("result", result);
        if (!StringUtils.isBlank(callback))
            ResponseUtils.renderJson(response, callback + "(" + ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
        else
            ResponseUtils.renderJson(response, ConvertMapToJson.buildJsonBody(map, 0, false));
    }

    private String getBrandListJson(List<Brand> beanList)
            throws JSONException {
        JSONArray arr = new JSONArray();
        JSONObject o = new JSONObject();
        for (Brand brand : beanList) {
            o.put("id", brand.getId());
            o.put("name", brand.getName());
            o.put("pcImgUrl", brand.getLogoPath());
        }

        return arr.toString();
    }
}

