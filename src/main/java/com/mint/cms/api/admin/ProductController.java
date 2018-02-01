package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.api.ExceptionUtil;
import com.mint.cms.entity.Product;
import com.mint.cms.entity.ProductExt;
import com.mint.cms.entity.ProductStandard;
import com.mint.cms.lucene.LuceneProductSvc;
import com.mint.cms.manager.CartItemMng;
import com.mint.cms.manager.ProductFashionMng;
import com.mint.cms.manager.ProductMng;
import com.mint.cms.manager.ProductStandardMng;
import com.mint.cms.manager.StandardMng;
import com.mint.cms.web.CmsThreadVariable;
import com.mint.cms.web.SignValidate;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.RequestUtils;
import com.mint.common.web.ResponseUtils;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryParser.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductMng productMng;

    @Autowired
    private ProductStandardMng productStandardMng;

    @Autowired
    private StandardMng standardMng;

    @Autowired
    private LuceneProductSvc luceneProductSvc;

    @Autowired
    private ProductFashionMng productFashionMng;

    @Autowired
    private ProductFashionMng fashMng;

    @Autowired
    private CartItemMng cartItemMng;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping({"/product/list"})
    public void list(Integer ctgId, String productName, String brandName, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if (pageNo == null) {
                pageNo = Integer.valueOf(1);
            }
            if (pageSize == null) {
                pageSize = Integer.valueOf(10);
            }
            Pagination page = this.productMng.getPage(
                    SiteUtils.getWebId(request), ctgId,
                    productName, brandName, status, isRecommend, isSpecial,
                    isHotsale, isNewProduct, typeId, startSalePrice,
                    endSalePrice, startStock, endStock, pageNo.intValue(), pageSize.intValue());
            List list = page.getList();
            int totalCount = page.getTotalCount();
            JSONArray jsonArray = new JSONArray();
            if ((list != null) && (list.size() > 0)) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, ((Product) list.get(i)).convertToJson("list"));
                }
            }
            body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/product/getProduct"})
    public void getProduct(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                Product product = new Product();
                if (id.longValue() != 0L) {
                    product = this.productMng.findById(id);
                }
                if (product != null) {
                    JSONObject json = product.convertToJson("get");
                    JSONArray jsonArray = new JSONArray();
                    JSONArray imgIds = new JSONArray();
                    List psList = this.productStandardMng
                            .findByProductId(id);
                    if ((psList != null) && (psList.size() > 0)) {
                        for (int i = 0; i < psList.size(); i++) {
                            if (StringUtils.isNotEmpty(((ProductStandard) psList.get(i)).getImgPath())) {
                                jsonArray.put(((ProductStandard) psList.get(i)).getImgPath());
                                imgIds.put(((ProductStandard) psList.get(i)).getStandard().getId());
                            }
                        }
                    }
                    json.put("productStandardImg", jsonArray);
                    json.put("productStandardImgId", imgIds);
                    body = json.toString();
                } else {
                    code = 206;
                    message = "\"object not found\"";
                }
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/product/save"})
    public void save(Product bean, ProductExt ext, Integer categoryId, Long brandId, String nature, String picture, String colorImg, String character, String isDefaults, String stockCounts, String fashionSwitchPic, String salePrices, String marketPrices, String costPrices, String productKeywords, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getName(), categoryId});
            if (!errors.hasErrors()) {
                String[] natures = null;
                Long[] pictures = null;
                String[] colorImgs = null;
                Long[] characters = null;
                Double[] salePrice = null;
                Double[] marketPrice = null;
                Double[] costPrice = null;
                Boolean[] isDefault = null;
                Integer[] stockCount = null;
                String[] fashionSwitchPics = null;

                if (StringUtils.isNotBlank(nature)) {
                    String[] str = nature.split(",");
                    natures = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        natures[i] = str[i];
                    }
                }

                if (StringUtils.isNotBlank(stockCounts)) {
                    String[] str = stockCounts.split(",");
                    stockCount = new Integer[str.length];
                    for (int i = 0; i < str.length; i++) {
                        stockCount[i] = Integer.valueOf(Integer.parseInt(str[i]));
                    }

                }

                if (StringUtils.isNotBlank(salePrices)) {
                    String[] str = salePrices.split(",");
                    salePrice = new Double[str.length];
                    for (int i = 0; i < str.length; i++) {
                        salePrice[i] = Double.valueOf(Double.parseDouble(str[i]));
                    }
                }

                if (StringUtils.isNotBlank(marketPrices)) {
                    String[] str = marketPrices.split(",");
                    marketPrice = new Double[str.length];
                    for (int i = 0; i < str.length; i++) {
                        marketPrice[i] = Double.valueOf(Double.parseDouble(str[i]));
                    }
                }

                if (StringUtils.isNotBlank(costPrices)) {
                    String[] str = costPrices.split(",");
                    costPrice = new Double[str.length];
                    for (int i = 0; i < str.length; i++) {
                        costPrice[i] = Double.valueOf(Double.parseDouble(str[i]));
                    }

                }

                if (StringUtils.isNotBlank(picture)) {
                    String[] str = picture.split(",");
                    pictures = new Long[str.length];
                    for (int i = 0; i < str.length; i++) {
                        pictures[i] = Long.valueOf(Long.parseLong(str[i]));
                    }
                }

                if (StringUtils.isNotBlank(colorImg)) {
                    String[] str = colorImg.split(",");
                    colorImgs = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        colorImgs[i] = str[i];
                    }

                }

                if (StringUtils.isNotBlank(character)) {
                    String[] str = character.split(",");
                    characters = new Long[str.length];
                    for (int i = 0; i < str.length; i++) {
                        characters[i] = Long.valueOf(Long.parseLong(str[i]));
                    }
                }

                if (StringUtils.isNotBlank(isDefaults)) {
                    String[] str = isDefaults.split(",");
                    isDefault = new Boolean[str.length];
                    for (int i = 0; i < str.length; i++) {
                        isDefault[i] = Boolean.valueOf(str[i]);
                    }
                }

                if (StringUtils.isNotBlank(fashionSwitchPic)) {
                    String[] str = fashionSwitchPic.split(",");
                    fashionSwitchPics = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        fashionSwitchPics[i] = str[i];
                    }
                }
                String[] keywords = StringUtils.split(productKeywords, ",");

                Map exended = RequestUtils.getRequestMap(
                        request, "exended_");
                List li = new ArrayList(exended.keySet());
                String[] names = new String[li.size()];
                String[] values = new String[li.size()];
                if (stockCount != null) {
                    Integer stockCount1 = Integer.valueOf(0);
                    for (Integer s : stockCount) {
                        stockCount1 = Integer.valueOf(stockCount1.intValue() + s.intValue());
                    }
                    bean.setStockCount(stockCount1);
                }
                for (int i = 0; i < li.size(); i++) {
                    names[i] = ((String) li.get(i));
                    values[i] = ((String) exended.get(li.get(i)));
                }
                bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
                this.productMng.saveByApi(bean, ext, CmsThreadVariable.getSite().getId(), categoryId, brandId, keywords, names, values,
                        fashionSwitchPics, pictures, colorImgs, characters, natures, isDefault, stockCount, salePrice, marketPrice, costPrice);
                try {
//           if (bean.getStatus().intValue() != Product.ON_SALE_STATUS)  break;  //TODO
                    this.luceneProductSvc.createIndex(bean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (ClassCastException e) {
            code = 202;
            message = "\"param error\"";
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/product/update"})
    public void update(Long id, Product bean, ProductExt ext, Integer categoryId, Long brandId, String nature, String picture, String colorImg, String character, String isDefaults, String stockCounts, String fashionSwitchPic, String fashionBigPic, String fashionAmpPic, String salePrices, String marketPrices, String costPrices, String fashionIds, String productKeywords, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id, bean.getName(), categoryId});
            if (!errors.hasErrors()) {
                String[] natures = null;
                Long[] pictures = null;
                String[] colorImgs = null;
                Long[] characters = null;
                Double[] salePrice = null;
                Double[] marketPrice = null;
                Double[] costPrice = null;
                Boolean[] isDefault = null;
                Integer[] stockCount = null;
                String[] fashionSwitchPics = null;
                String[] fashionBigPics = null;
                String[] fashionAmpPics = null;
                Long[] fashionId = null;

                if (StringUtils.isNotBlank(nature)) {
                    String[] str = nature.split(",");
                    natures = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        natures[i] = str[i];
                    }
                }

                if (StringUtils.isNotBlank(stockCounts)) {
                    String[] str = stockCounts.split(",");
                    stockCount = new Integer[str.length];
                    for (int i = 0; i < str.length; i++) {
                        stockCount[i] = Integer.valueOf(Integer.parseInt(str[i]));
                    }

                }

                if (StringUtils.isNotBlank(salePrices)) {
                    String[] str = salePrices.split(",");
                    salePrice = new Double[str.length];
                    for (int i = 0; i < str.length; i++) {
                        salePrice[i] = Double.valueOf(Double.parseDouble(str[i]));
                    }
                }

                if (StringUtils.isNotBlank(marketPrices)) {
                    String[] str = marketPrices.split(",");
                    marketPrice = new Double[str.length];
                    for (int i = 0; i < str.length; i++) {
                        marketPrice[i] = Double.valueOf(Double.parseDouble(str[i]));
                    }
                }

                if (StringUtils.isNotBlank(costPrices)) {
                    String[] str = costPrices.split(",");
                    costPrice = new Double[str.length];
                    for (int i = 0; i < str.length; i++) {
                        costPrice[i] = Double.valueOf(Double.parseDouble(str[i]));
                    }

                }

                if (StringUtils.isNotBlank(picture)) {
                    String[] str = picture.split(",");
                    pictures = new Long[str.length];
                    for (int i = 0; i < str.length; i++) {
                        pictures[i] = Long.valueOf(Long.parseLong(str[i]));
                    }
                }

                if (StringUtils.isNotBlank(colorImg)) {
                    String[] str = colorImg.split(",");
                    colorImgs = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        colorImgs[i] = str[i];
                    }

                }

                if (StringUtils.isNotBlank(character)) {
                    String[] str = character.split(",");
                    characters = new Long[str.length];
                    for (int i = 0; i < str.length; i++) {
                        characters[i] = Long.valueOf(Long.parseLong(str[i]));
                    }
                }

                if (StringUtils.isNotBlank(isDefaults)) {
                    String[] str = isDefaults.split(",");
                    isDefault = new Boolean[str.length];
                    for (int i = 0; i < str.length; i++) {
                        isDefault[i] = Boolean.valueOf(str[i]);
                    }
                }

                if (StringUtils.isNotBlank(fashionSwitchPic)) {
                    String[] str = fashionSwitchPic.split(",");
                    fashionSwitchPics = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        fashionSwitchPics[i] = str[i];
                    }
                }

                if (StringUtils.isNotBlank(fashionBigPic)) {
                    String[] str = fashionBigPic.split(",");
                    fashionBigPics = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        fashionBigPics[i] = str[i];
                    }
                }

                if (StringUtils.isNotBlank(fashionAmpPic)) {
                    String[] str = fashionAmpPic.split(",");
                    fashionAmpPics = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        fashionAmpPics[i] = str[i];
                    }

                }

                if (StringUtils.isNotBlank(fashionIds)) {
                    String[] str = fashionIds.split(",");
                    fashionId = new Long[str.length];
                    for (int i = 0; i < str.length; i++) {
                        if ((StringUtils.isEmpty(str[i])) || ("0".equals(str[i])))
                            fashionId[i] = Long.valueOf(0L);
                        else {
                            fashionId[i] = Long.valueOf(Long.parseLong(str[i]));
                        }
                    }
                }

                Map exended = RequestUtils.getRequestMap(
                        request, "exended_");
                List li = new ArrayList(exended.keySet());
                String[] names = new String[li.size()];
                String[] values = new String[li.size()];
                for (int i = 0; i < li.size(); i++) {
                    names[i] = ((String) li.get(i));
                    values[i] = ((String) exended.get(li.get(i)));
                }
                Map attr = RequestUtils.getRequestMap(request,
                        "attr_");
                if (stockCount != null) {
                    Integer stockCount1 = Integer.valueOf(0);
                    for (Integer s : stockCount) {
                        stockCount1 = Integer.valueOf(stockCount1.intValue() + s.intValue());
                    }
                    bean.setStockCount(stockCount1);
                }

                String[] keywords = StringUtils.split(productKeywords, ",");

                bean = this.productMng.updateByApi(bean, ext, brandId, keywords, names, values, attr,
                        fashionSwitchPics, fashionBigPics, fashionAmpPics, categoryId, fashionSwitchPics, pictures, colorImgs,
                        characters, fashionId, natures, isDefault, stockCount, salePrice, marketPrice, costPrice);
                try {
                    if (bean.getStatus().intValue() == Product.ON_SALE_STATUS)
                        this.luceneProductSvc.updateIndex(bean);
                    else if (bean.getStatus().intValue() == Product.NOT_SALE_STATUS)
                        this.luceneProductSvc.deleteIndex(bean);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                LOG.info("update Product. id={}.", bean.getId());
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (ClassCastException e) {
            code = 202;
            message = "\"param error\"";
        } catch (Exception e) {
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/product/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Long[] id = null;
            if (StringUtils.isNotBlank(ids)) {
                String[] str = ids.split(",");
                id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }
                Product[] beans = this.productMng.deleteByUpIds(id);
                for (Product bean : beans) {
                    this.luceneProductSvc.deleteIndex(bean);
                }
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    public Long[] getProductIds(String rightlist) {
        String[] arr = rightlist.split(",");
        Long[] productIds = new Long[arr.length];
        int i = 0;
        for (int j = arr.length; i < j; i++) {
            if (!arr[i].equals("")) {
                productIds[i] = Long.valueOf(Long.parseLong(arr[i]));
            }
        }
        return productIds;
    }

    @SignValidate
    @RequestMapping({"/product/standup"})
    public void standup(String ids, Integer status, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids});
            if (!errors.hasErrors()) {
                Long[] id = null;
                String[] str = ids.split(",");
                id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }
                for (Long i : id) {
                    Product bean = this.productMng.findById(i);
                    bean.setStatus(status);
                    bean = this.productMng.update(bean);

                    if (bean.getStatus().intValue() == Product.ON_SALE_STATUS)
                        this.luceneProductSvc.updateIndex(bean);
                    else if (bean.getStatus().intValue() == Product.NOT_SALE_STATUS)
                        this.luceneProductSvc.deleteIndex(bean);
                }
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/product/getOverStock"})
    public void createIndex(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);

            errors = ApiValidate.validateRequiredParams(errors, new Object[]{pageNo, pageSize});
            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                Pagination page = this.productMng.getPageOverStockList(CmsThreadVariable.getSite().getId(), SimplePage.cpn(pageNo), pageSize.intValue());
                List<Product> list = (List<Product>) page.getList();
                JSONArray jsons = new JSONArray();
                for (Product product : list) {
                    jsons.put(product.convertToJson("list"));
                }
                body = jsons.toString() + ",\"totalCount\":" + page.getTotalCount();
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/product/createIndex"})
    public void createIndex(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            String path = this.servletContext.getRealPath("/WEB-INF/lucene");
            int count = this.luceneProductSvc.index(path, null, null, null);
            JSONObject json = new JSONObject();
            json.put("count", count);
            body = json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

