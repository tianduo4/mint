package com.jspgou.cms.action.front;

import com.jspgou.cms.entity.Brand;
import com.jspgou.cms.entity.Category;
import com.jspgou.cms.entity.Exended;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductExt;
import com.jspgou.cms.entity.ProductFashion;
import com.jspgou.cms.entity.ProductType;
import com.jspgou.cms.entity.Relatedgoods;
import com.jspgou.cms.entity.ShopArticle;
import com.jspgou.cms.entity.ShopChannel;
import com.jspgou.cms.manager.BrandMng;
import com.jspgou.cms.manager.CategoryMng;
import com.jspgou.cms.manager.ExendedMng;
import com.jspgou.cms.manager.ProductMng;
import com.jspgou.cms.manager.ProductStandardMng;
import com.jspgou.cms.manager.RelatedgoodsMng;
import com.jspgou.cms.manager.ShopArticleMng;
import com.jspgou.cms.manager.ShopChannelMng;
import com.jspgou.cms.manager.StandardTypeMng;
import com.jspgou.cms.web.ShopFrontHelper;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.RequestUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;
import com.jspgou.core.web.front.FrontHelper;
import com.jspgou.core.web.front.URLHelper;
import com.jspgou.core.web.front.URLHelper.PageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
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
public class DynamicPageAct {
    public static final String TPL_INDEX = "tpl.index";
    private static final String BRAND = "tpl.brand";
    private static final String BRAND_DETAIL = "tpl.brandDetail";
    public static final String CATEGORY = "category";
    private static final String ALL_PRODUCT_CATEGORY = "tpl.allProductCategory";

    @Autowired
    private CategoryMng categoryMng;

    @Autowired
    private ProductMng productMng;

    @Autowired
    private RelatedgoodsMng relatedgoodsMng;

    @Autowired
    private ShopChannelMng shopChannelMng;

    @Autowired
    private ShopArticleMng shopArticleMng;

    @Autowired
    private BrandMng brandMng;

    @Autowired
    private StandardTypeMng standardTypeMng;

    @Autowired
    private ProductStandardMng productStandardMng;

    @Autowired
    private ExendedMng exendedMng;

    @RequestMapping(value = {"/index.jhtml"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String indexForWeblogic(HttpServletRequest request, ModelMap model) {
        return index(request, model);
    }

    @RequestMapping(value = {"/"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String index(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTemplate("index", MessageResolver.getMessage(request, "tpl.index", new Object[0]), request);
    }

    @RequestMapping(value = {"/**/*.*"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String excute(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String url = request.getRequestURL().toString();

        URLHelper.PageInfo info = URLHelper.getPageInfo(request);
        int pageNo = URLHelper.getPageNo(request);
        String queryString = request.getQueryString();
        Website web = SiteUtils.getWeb(request);
        ShopFrontHelper.setDynamicPageData(request, model, web, url, info.getHrefFormer(), info.getHrefLatter(), pageNo);
        String[] paths = URLHelper.getPaths(request);
        String[] params = URLHelper.getParams(request);
        int len = paths.length;
        if (len == 1) {
            return channel(paths[0], params, pageNo, queryString, url, web, request, response, model);
        }
        if (len == 2) {
            if (paths[1].equals("index")) {
                return channel(paths[0], params, pageNo, queryString, url, web, request, response, model);
            }
            try {
                Long id = Long.valueOf(Long.parseLong(paths[1]));

                return content(paths[0], id, params, pageNo, queryString, url, web, request, response, model);
            } catch (NumberFormatException localNumberFormatException) {
            }
        }
        return FrontHelper.pageNotFound(web, model, request);
    }

    public String channel(String path, String[] params, int pageNo, String queryString, String url, Website web, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Category category = this.categoryMng.getByPath(web.getId(), path);
        if (category != null) {
            List exendeds = this.exendedMng.getList(category.getType().getId());
            Map map = new HashMap();
            Map map1 = new HashMap();
            int num = exendeds.size();
            for (int i = 0; i < num; i++) {
                map.put(((Exended) exendeds.get(i)).getId().toString(), ((Exended) exendeds.get(i)).getItems());
                map1.put(((Exended) exendeds.get(i)).getId().toString(), exendeds.get(i));
            }
            model.addAttribute("brandId", getBrandId(request));
            model.addAttribute("map", map);
            model.addAttribute("map1", map1);
            model.addAttribute("fields", getNames(request));
            model.addAttribute("zhis", getValues(request));
            model.addAttribute("category", category);
            model.addAttribute("pageSize", getpageSize(request));
            model.addAttribute("names", getName(request));
            model.addAttribute("values", getValue(request));
            model.addAttribute("isShow", getIsShow(request));
            model.addAttribute("orderBy", getOrderBy(request));
            model.addAttribute("startPrice", getStartPrice(request));
            model.addAttribute("endPrice", getEndPrice(request));
            return category.getTplChannelRel(request);
        }
        ShopChannel channel = this.shopChannelMng.getByPath(web.getId(), path);
        if (channel != null) {
            model.addAttribute("channel", channel);
            return channel.getTplChannelRel(request);
        }

        return FrontHelper.pageNotFound(web, model, request);
    }

    public String content(String chnlName, Long id, String[] params, int pageNo, String queryString, String url, Website web, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Category category = this.categoryMng.getByPath(web.getId(), chnlName);
        if (category != null) {
            Product product = this.productMng.findById(id);
            if (product != null) {
                if (product.getProductFashion() != null) {
                    String[] arr = null;
                    arr = product.getProductFashion().getNature().split("_");
                    model.addAttribute("arr", arr);
                }

                List relatedgoods = this.relatedgoodsMng.findByIdProductId(id);
                List productList = new ArrayList();
                if (relatedgoods != null) {
                    for (int i = 0; i < relatedgoods.size(); i++) {
                        if (this.productMng.findById(((Relatedgoods) relatedgoods.get(i)).getProductIds()) != null) {
                            Product product1 = this.productMng.findById(((Relatedgoods) relatedgoods.get(i)).getProductIds());
                            productList.add(product1);
                        }
                    }
                    model.addAttribute("productList", productList);
                }
                List psList = this.productStandardMng.findByProductId(id);
                List standardTypeList = this.standardTypeMng.getList(category.getId());
                this.productMng.updateViewCount(product);
                model.addAttribute("standardTypeList", standardTypeList);
                model.addAttribute("psList", psList);
                model.addAttribute("category", category);
                model.addAttribute("product", product);

                return category.getTplContentRel(request);
            }
            return FrontHelper.pageNotFound(web, model, request);
        }

        ShopArticle article = this.shopArticleMng.findById(id);
        if (article != null) {
            ShopChannel channel = article.getChannel();
            model.addAttribute("article", article);
            model.addAttribute("channel", channel);
            return channel.getTplContentRel(request);
        }
        return FrontHelper.pageNotFound(web, model, request);
    }

    public String getHistoryProductIds(HttpServletRequest request) {
        String str = null;
        Cookie[] cookie = request.getCookies();
        int num = cookie.length;
        for (int i = 0; i < num; i++) {
            if (cookie[i].getName().equals("shop_record")) {
                str = cookie[i].getValue();
                break;
            }
        }
        return str;
    }

    @RequestMapping({"/showMessage.jspx"})
    public String showMessage(HttpServletRequest request, ModelMap model, String message) {
        Website web = SiteUtils.getWeb(request);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        model.put("message", message);
        return web.getTplSys("common", MessageResolver.getMessage(request, "tpl.messagePage", new Object[0]), request);
    }

    @RequestMapping(value = {"/brand.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String brand(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateBrand(id, request);
        if (errors.hasErrors())
            return FrontHelper.showError(errors, web, model, request);
        String tpl;
        if (id != null) {
            model.addAttribute("brand", this.brandMng.findById(id));
            tpl = MessageResolver.getMessage(request, "tpl.brandDetail", new Object[0]);
        } else {
            tpl = MessageResolver.getMessage(request, "tpl.brand", new Object[0]);
        }
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("shop", tpl, request);
    }

    public String getBrandId(HttpServletRequest request) {
        String brandId = RequestUtils.getQueryParam(request, "brandId");
        return brandId;
    }

    @RequestMapping({"/allProductCategory.jspx"})
    public String allProductCategory(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTemplate("category", MessageResolver.getMessage(request, "tpl.allProductCategory", new Object[0]), request);
    }

    @RequestMapping({"/cateGoryListLoading.jspx"})
    public void cateGoryListLoading(String brandId, Integer categoryId, Boolean isRecommend, String[] names, String[] values, Boolean isSpecial, int orderBy, Double startPrice, Double endPrice, int pageNo, int count, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        Pagination pagination = this.productMng.getPageForTagChannel(brandId, web.getId(), categoryId,
                null, isRecommend, names, values, isSpecial, orderBy, startPrice, endPrice, SimplePage.cpn(Integer.valueOf(pageNo)),
                count);
        List<Product> list = (List<Product>) pagination.getList();
        JSONArray arr = new JSONArray();

        boolean no = pageNo <= pagination.getTotalPage();
        if ((list != null) && (list.size() > 0) && (no)) {
            try {
                for (Product content : list) {
                    JSONObject o = new JSONObject();
                    o.put("url", content.getUrl());
                    o.put("coverImg", content.getProductExt().getCoverImg());
                    o.put("name", content.getName());
                    o.put("salePrice", content.getSalePrice());
                    arr.put(o);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ResponseUtils.renderJson(response, arr.toString());
        }
    }

    public Integer getpageSize(HttpServletRequest request) {
        String pageSize = RequestUtils.getQueryParam(request, "pageSize");
        Integer pagesize = null;
        if (!StringUtils.isBlank(pageSize)) {
            pagesize = Integer.valueOf(Integer.parseInt(pageSize));
        }
        if (pagesize == null) {
            pagesize = Integer.valueOf(12);
        }
        return pagesize;
    }

    public Integer getIsShow(HttpServletRequest request) {
        String isShow = RequestUtils.getQueryParam(request, "isShow");
        Integer isshow = null;
        if (!StringUtils.isBlank(isShow)) {
            isshow = Integer.valueOf(Integer.parseInt(isShow));
        }
        if (isshow == null) {
            isshow = Integer.valueOf(0);
        }
        return isshow;
    }

    public Integer getOrderBy(HttpServletRequest request) {
        String orderBy = RequestUtils.getQueryParam(request, "orderBy");
        Integer orderby = null;
        if (!StringUtils.isBlank(orderBy)) {
            orderby = Integer.valueOf(Integer.parseInt(orderBy));
        }
        if (orderby == null) {
            orderby = Integer.valueOf(0);
        }
        return orderby;
    }

    public String[] getNames(HttpServletRequest request) {
        Map attr = RequestUtils.getRequestMap(request, "exended_");
        List li = new ArrayList(attr.keySet());
        String name = "";
        for (int i = 0; i < li.size(); i++) {
            if (i + 1 == li.size())
                name = name + (String) li.get(i);
            else {
                name = name + (String) li.get(i) + ",";
            }
        }
        String[] names = StringUtils.split(name, ',');
        return names;
    }

    public String[] getValues(HttpServletRequest request) {
        Map attr = RequestUtils.getRequestMap(request, "exended_");
        List li = new ArrayList(attr.keySet());
        String value = "";
        for (int i = 0; i < li.size(); i++) {
            if (i + 1 == li.size()) {
                if (StringUtils.isBlank((String) attr.get(li.get(i))))
                    value = value + "0";
                else {
                    value = value + (String) attr.get(li.get(i));
                }
            } else if (StringUtils.isBlank((String) attr.get(li.get(i))))
                value = value + "0,";
            else {
                value = value + (String) attr.get(li.get(i)) + ",";
            }
        }

        String[] values = StringUtils.split(value, ',');
        return values;
    }

    public String getName(HttpServletRequest request) {
        Map attr = RequestUtils.getRequestMap(request, "exended_");
        List li = new ArrayList(attr.keySet());
        String name = "";
        for (int i = 0; i < li.size(); i++) {
            if (i + 1 == li.size())
                name = name + (String) li.get(i);
            else {
                name = name + (String) li.get(i) + ",";
            }
        }

        return name;
    }

    public String getValue(HttpServletRequest request) {
        Map attr = RequestUtils.getRequestMap(request, "exended_");
        List li = new ArrayList(attr.keySet());
        String value = "";
        for (int i = 0; i < li.size(); i++) {
            if (i + 1 == li.size()) {
                if (StringUtils.isBlank((String) attr.get(li.get(i))))
                    value = value + "0";
                else {
                    value = value + (String) attr.get(li.get(i));
                }
            } else if (StringUtils.isBlank((String) attr.get(li.get(i))))
                value = value + "0,";
            else {
                value = value + (String) attr.get(li.get(i)) + ",";
            }
        }

        return value;
    }

    private WebErrors validateBrand(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (id != null) {
            Brand brand = this.brandMng.findById(id);
            if (errors.ifNotExist(brand, Brand.class, id)) {
                return errors;
            }
        }
        return errors;
    }

    public Integer getStartPrice(HttpServletRequest request) {
        String startPrice = RequestUtils.getQueryParam(request, "startPrice");
        Integer start = null;
        if (!StringUtils.isBlank(startPrice)) {
            if (!startPrice.equals("￥"))
                start = Integer.valueOf(Integer.parseInt(startPrice.replace("￥", "")));
            else {
                start = Integer.valueOf(0);
            }
        }
        if (start == null) {
            start = Integer.valueOf(0);
        }
        return start;
    }

    public Integer getEndPrice(HttpServletRequest request) {
        String endPrice = RequestUtils.getQueryParam(request, "endPrice");
        Integer end = null;
        if (!StringUtils.isBlank(endPrice)) {
            if (!endPrice.equals("￥"))
                end = Integer.valueOf(Integer.parseInt(endPrice.replace("￥", "")));
            else {
                end = Integer.valueOf(0);
            }
        }
        if (end == null) {
            end = Integer.valueOf(0);
        }
        return end;
    }
}