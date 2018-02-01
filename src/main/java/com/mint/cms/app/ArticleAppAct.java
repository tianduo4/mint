package com.mint.cms.app;

import com.mint.cms.entity.ShopArticle;
import com.mint.cms.manager.ShopArticleMng;
import com.mint.cms.web.SiteUtils;
import com.mint.common.util.ConvertMapToJson;
import com.mint.common.util.StrUtils;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;

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
public class ArticleAppAct {

    @Autowired
    private ShopArticleMng shopArticleMng;

    @RequestMapping({"/api/articleList.jspx"})
    public void articleList(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        Map map = new HashMap();
        Website web = SiteUtils.getWeb(request);
        String channelId = request.getParameter("channelId");
        String firstResult = request.getParameter("firstResult");
        String maxResults = request.getParameter("maxResult");
        String callback = request.getParameter("callback");
        String result = "00";
        Long channel_id = null;
        int first_result = 0;
        if (StringUtils.isNotBlank(channelId)) {
            channel_id = Long.valueOf(Long.parseLong(channelId));
        }
        if (StringUtils.isNotBlank(firstResult)) {
            first_result = Integer.parseInt(firstResult);
        }

        if (StringUtils.isNotBlank(maxResults)) {
            int i = Integer.parseInt(maxResults);
        }
        List list = this.shopArticleMng.getListForTag(web.getId(),
                Integer.valueOf(Integer.parseInt(channelId)), Integer.parseInt(firstResult),
                Integer.parseInt(maxResults));
        if (list != null) {
            if (list.size() > 0) {
                result = "01";
                map.put("pd", getarticleJson(list));
            } else {
                result = "02";
            }
        } else {
            result = "02";
        }
        map.put("result", result);
        if (!StringUtils.isBlank(callback))
            ResponseUtils.renderJson(response, callback + "(" +
                    ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
        else
            ResponseUtils.renderJson(response,
                    ConvertMapToJson.buildJsonBody(map, 0, false));
    }

    private String getarticleJson(List<ShopArticle> beanList)
            throws JSONException {
        JSONObject o = new JSONObject();
        JSONArray arr = new JSONArray();
        for (ShopArticle article : beanList) {
            o.put("id", article.getId());
            o.put("title", article.getTitle());
            o.put("time", article.getPublishTime());
            o.put("content", StrUtils.trimHtml2Txt(article.getArticleContent().getContent()));
            arr.put(o);
        }
        return arr.toString();
    }

    public void article(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        Map map = new HashMap();
        String articleId = request.getParameter("articleId");
        String result = "00";
        if (StringUtils.isNotBlank(articleId)) {
            ShopArticle article = this.shopArticleMng.findById(
                    Long.valueOf(Long.parseLong(articleId)));
            if (article != null) {
                result = "01";
                map.put("pd", getArticleJson(article));
            } else {
                result = "02";
            }
        } else {
            result = "02";
        }
        map.put("result", result);
    }

    private String getArticleJson(ShopArticle article) throws JSONException {
        JSONObject o = new JSONObject();
        o.put("id", article.getId());
        o.put("title", article.getTitle());
        o.put("time", article.getPublishTime());
        o.put("content", StrUtils.trimHtml2Txt(article.getArticleContent().getContent()));
        return o.toString();
    }
}

