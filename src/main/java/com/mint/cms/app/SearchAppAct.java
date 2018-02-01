package com.mint.cms.app;

import com.mint.cms.entity.KeyWord;
import com.mint.cms.lucene.LuceneProduct;
import com.mint.cms.lucene.LuceneProductSvc;
import com.mint.cms.manager.KeyWordMng;
import com.mint.cms.web.SiteUtils;
import com.mint.common.util.Apputil;
import com.mint.common.util.ConvertMapToJson;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryParser.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

public class SearchAppAct {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private LuceneProductSvc luceneProductSvc;

    @Autowired
    private KeyWordMng keywordMng;

    @RequestMapping({"/api/search.jspx"})
    public void search(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ParseException, JSONException {
        Website web = SiteUtils.getWeb(request);
        String result = "00";
        Map map = new HashMap();
        String query = request.getParameter("query");
        Long brandId = Apputil.getRequestLong(request.getParameter("brandId"));
        Long ctgId = Apputil.getRequestLong(request.getParameter("ctgId"));
        Date start = Apputil.getRequestDate(request.getParameter("StartDate"));
        Date end = Apputil.getRequestDate(request.getParameter("EndDate"));
        Integer orderBy = Apputil.getRequestInteger(request
                .getParameter("orderBy"));
        Integer firstResult = Apputil.getfirstResult(request
                .getParameter("firstResult"));
        Integer maxResults = Apputil.getmaxResults(request
                .getParameter("maxResults"));
        String path = this.servletContext.getRealPath("/WEB-INF/lucene");
        List list = this.luceneProductSvc.getlist(path, query,
                web.getId(), ctgId, brandId, start, end, orderBy.intValue(), firstResult.intValue(),
                maxResults.intValue());
        if (list != null) {
            if (list.size() > 0) {
                result = "01";
                map.put("pd", getLuceneProductJson(list));
            } else {
                result = "02";
            }
        } else {
            result = "02";
        }
        map.put("result", result);
    }

    private String getLuceneProductJson(List<LuceneProduct> beanList) throws JSONException {
        JSONArray arr = new JSONArray();
        JSONObject o = new JSONObject();
        for (LuceneProduct product : beanList) {
            o.put("id", product.getId());
            o.put("coverImgUrl", product.getCoverImgUrl());
            o.put("name", product.getName());
            o.put("salePrice", product.getSalePrice());
            o.put("marketPrice", product.getMarketPrice());
            o.put("brandId", product.getBrandId());
            o.put("brandName", product.getBrandName());
            arr.put(o);
        }

        return arr.toString();
    }

    @RequestMapping({"/api/keyword.jspx"})
    public void keyword(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        Map map = new HashMap();
        String result = "00";
        String callback = request.getParameter("callback");
        Integer count =
                Apputil.getRequestInteger(request.getParameter("count"));
        List beanList = this.keywordMng.findKeyWord(count);
        if (beanList != null) {
            if (beanList.size() > 0) {
                result = "01";
                map.put("pd", getkeywordJson(beanList));
            } else {
                result = "02";
            }
        } else result = "02";

        if (StringUtils.isBlank(callback)) {
            ResponseUtils.renderJson(response, callback + "(" +
                    ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
        } else
            ResponseUtils.renderJson(response,
                    ConvertMapToJson.buildJsonBody(map, 0, false));
    }

    private String getkeywordJson(List<KeyWord> beanList) throws JSONException {
        JSONArray arr = new JSONArray();
        JSONObject o = new JSONObject();
        for (KeyWord keyWord : beanList) {
            o.put("keyword", keyWord.getKeyword());
            arr.put(o);
        }

        return arr.toString();
    }
}

