package com.jspgou.cms.app;

import com.jspgou.cms.entity.Category;
import com.jspgou.cms.manager.CategoryMng;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;

import java.util.ArrayList;
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
public class CategoryAppAct {

    @Autowired
    private CategoryMng categoryMng;

    @RequestMapping({"/api/categoryList.jspx"})
    public void categoryList(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        Map map = new HashMap();
        String parentId = request.getParameter("parentId");
        String callback = request.getParameter("callback");

        Website web = SiteUtils.getWeb(request);
        String result = "00";
        JSONArray jsonArray = null;
        List list;
        if (StringUtils.isNotBlank(parentId)) {
            Category category = this.categoryMng.findById(Integer.valueOf(Integer.parseInt(parentId)));
            if (category != null)
                list = new ArrayList(category.getChild());
            else
                list = new ArrayList();
        } else {
            list = this.categoryMng.getTopListForTag(web.getId());
        }

        if (list != null) {
            if (list.size() > 0) {
                result = "01";

                jsonArray = new JSONArray();
                if ((list != null) && (list.size() > 0))
                    for (int i = 0; i < list.size(); i++)
                        jsonArray.put(i, convertToJson((Category) list.get(i)));
            } else {
                result = "02";
            }
        } else result = "02";

        map.put("result", result);
        ResponseUtils.renderJson(response, jsonArray.toString());
    }

    private String getcategoryJson(List<Category> beanList)
            throws JSONException {
        JSONArray arr = new JSONArray();

        for (Category category : beanList) {
            JSONObject o = new JSONObject();
            o.put("id", category.getId());
            o.put("name", category.getName());
            o.put("pcImgUrl", category.getImagePath());
            arr.put(o);
        }
        return arr.toString();
    }

    private JSONObject convertToJson(Category category) throws JSONException {
        JSONObject o = new JSONObject();
        o.put("id", category.getId());
        o.put("name", category.getName());
        o.put("pcImgUrl", category.getImagePath());

        return o;
    }
}

