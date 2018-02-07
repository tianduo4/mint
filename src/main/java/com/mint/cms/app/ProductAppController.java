package com.mint.cms.app;

import com.mint.cms.entity.Product;
import com.mint.cms.manager.ProductMng;
import com.mint.cms.web.SiteUtils;
import com.mint.common.util.Apputil;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductAppController {

    @Autowired
    private ProductMng productMng;

    @RequestMapping({"/api/product.jspx"})
    public void product(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Map map = new HashMap();
        String productId = request.getParameter("productId");
        String callback = request.getParameter("callback");
        Product product = null;
        String result = "00";
        if (StringUtils.isNotBlank(productId)) {
            product = this.productMng.findById(Long.valueOf(Long.parseLong(productId)));
        }
        if (product != null) {
            map.put("product", beantoJson(product));
            result = product == null ? "02" : "01";
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

    @RequestMapping({"/api/producttext.jspx"})
    public void producttext(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Map map = new HashMap();
        String productId = request.getParameter("productId");
        String callback = request.getParameter("callback");
        Product product = null;
        String result = "00";
        if (StringUtils.isNotBlank(productId)) {
            product = this.productMng.findById(Long.valueOf(Long.parseLong(productId)));
        }
        if (product != null) {
            map.put("product", beantext(product));
            result = result == null ? "02" : "01";
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

    @RequestMapping({"/api/producttext1.jspx"})
    public void producttext1(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Map map = new HashMap();
        String productId = request.getParameter("productId");
        String callback = request.getParameter("callback");
        Product product = null;
        String result = "00";
        if (StringUtils.isNotBlank("productId")) {
            product = this.productMng.findById(Long.valueOf(Long.parseLong(productId)));
        }
        if (product != null) {
            map.put("product", beantext1(product));
            result = product == null ? "02" : "01";
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

    private String beantoJson(Product product) {
        JSONObject o = new JSONObject();
        try {
            o.put("id", product.getId());
            o.put("name", product.getName());
            o.put("coverImg", product.getCoverImg());

            if (product.getBrand() != null) {
                o.put("brandname", product.getBrandName());
                o.put("brandId", product.getBrandId());
            } else {
                o.put("brandname", "");
                o.put("brandId", "");
            }

            if (product.getPictures() != null)
                o.put("picture", product.getPictures());
            else {
                o.put("picture", "");
            }

            if (product.getFashions() != null) {
                o.put("salePrice", product.getProductFashion().getSalePrice());
                o.put("marketPrice", product.getProductFashion()
                        .getMarketPrice());
                o.put("saleCount", product.getProductFashion().getSaleCount());
            } else {
                o.put("sale", "");
                o.put("marketPrice", "");
                o.put("saleCount", "");
            }

            o.put("code", product.getProductExt().getCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return o.toString();
    }

    private String beantext(Product product) {
        JSONObject o = new JSONObject();
        try {
            o.put("id", product.getId());

            o.put("text", StrUtils.trimHtml2Txt(product.getText()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return o.toString();
    }

    private String beantext1(Product product) {
        JSONObject o = new JSONObject();
        try {
            o.put("id", product.getId());
            o.put("text1", StrUtils.trimHtml2Txt(product.getText1()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return o.toString();
    }

    @RequestMapping({"/api/productlist.jspx"})
    public void productlist(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        Map map = new HashMap();
        Website site = SiteUtils.getWeb(request);
        String result = "00";
        JSONArray jsonArray = null;
        Integer ctgId = Apputil.getRequestInteger(request.getParameter("ctgId"));
        Long tagId = Apputil.getRequestLong(request.getParameter("tagId"));
        Boolean isRecommend = Apputil.getRequestBoolean(request
                .getParameter("isRecommend"));
        Boolean isSpecial = Apputil.getRequestBoolean(request
                .getParameter("isSpecial"));
        Boolean isHostSale = Apputil.getRequestBoolean(request
                .getParameter("isHostSale"));
        Boolean isNewProduct = Apputil.getRequestBoolean(request
                .getParameter("isNewProduct"));
        Integer firstResult = Apputil.getfirstResult(request
                .getParameter("firsResult"));
        Integer maxResults = Apputil.getmaxResults(request
                .getParameter("maxResults"));
        String callback = request.getParameter("callback");

        List list = this.productMng.getListForTag(site.getId(), ctgId,
                tagId, isRecommend, isSpecial, isHostSale, isNewProduct,
                firstResult.intValue(), maxResults.intValue());
        if (list != null) {
            if (list.size() > 0) {
                result = "01";

                jsonArray = new JSONArray();
                for (int i = 0; i < list.size(); i++)
                    jsonArray.put(i, convertToJson((Product) list.get(i)));
            } else {
                result = "02";
            }
        } else result = "02";

        map.put("result", result);

        ResponseUtils.renderJson(response, jsonArray.toString());
    }

    private String getproductJson(List<Product> beanlist) throws JSONException {
        JSONObject o = new JSONObject();
        JSONArray arr = new JSONArray();
        for (Product product : beanlist) {
            o.put("id", product.getId());
            o.put("name", product.getName());
            o.put("coverImg", product.getCoverImg());
            arr.put(o);
        }
        return arr.toString();
    }

    private JSONObject convertToJson(Product product) throws JSONException {
        JSONObject o = new JSONObject();
        o.put("id", product.getId());
        o.put("name", product.getName());
        o.put("coverImg", product.getCoverImg());
        return o;
    }
}

