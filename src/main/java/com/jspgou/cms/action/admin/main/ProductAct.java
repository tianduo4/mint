package com.jspgou.cms.action.admin.main;

import com.jspgou.cms.entity.Brand;
import com.jspgou.cms.entity.Category;
import com.jspgou.cms.entity.Exended;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductExended;
import com.jspgou.cms.entity.ProductExt;
import com.jspgou.cms.entity.ProductFashion;
import com.jspgou.cms.entity.ProductStandard;
import com.jspgou.cms.entity.ProductType;
import com.jspgou.cms.entity.Relatedgoods;
import com.jspgou.cms.entity.Standard;
import com.jspgou.cms.lucene.LuceneProductSvc;
import com.jspgou.cms.manager.BrandMng;
import com.jspgou.cms.manager.CartItemMng;
import com.jspgou.cms.manager.CategoryMng;
import com.jspgou.cms.manager.ExendedMng;
import com.jspgou.cms.manager.ProductFashionMng;
import com.jspgou.cms.manager.ProductMng;
import com.jspgou.cms.manager.ProductStandardMng;
import com.jspgou.cms.manager.ProductTagMng;
import com.jspgou.cms.manager.ProductTypeMng;
import com.jspgou.cms.manager.ProductTypePropertyMng;
import com.jspgou.cms.manager.RelatedgoodsMng;
import com.jspgou.cms.manager.StandardMng;
import com.jspgou.cms.manager.StandardTypeMng;
import com.jspgou.common.image.ImageUtils;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.common.web.RequestUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.ObjectNotFoundException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductAct
        implements ServletContextAware {
    private static final Logger log = LoggerFactory.getLogger(ProductAct.class);
    private Boolean title_id1 = Boolean.valueOf(true);

    private Boolean title_coverImg2 = Boolean.valueOf(true);

    private Boolean title_prdtName3 = Boolean.valueOf(true);

    private Boolean title_prdtCategory4 = Boolean.valueOf(true);
    private Boolean title_prdtType5 = Boolean.valueOf(true);
    private Boolean title_prdtSalePrice6 = Boolean.valueOf(true);
    private Boolean title_prdtStockCount7 = Boolean.valueOf(true);
    private Boolean title_prdtBrand8 = Boolean.valueOf(true);
    private Boolean title_prdtOnSale9 = Boolean.valueOf(true);
    private Boolean title_Operate10 = Boolean.valueOf(true);

    @Autowired
    private StandardMng standardMng;

    @Autowired
    private StandardTypeMng standardTypeMng;

    @Autowired
    private ProductFashionMng fashMng;

    @Autowired
    private LuceneProductSvc luceneProductSvc;

    @Autowired
    private ProductTagMng productTagMng;

    @Autowired
    private RelatedgoodsMng relatedgoodsMng;

    @Autowired
    private CategoryMng categoryMng;

    @Autowired
    private ProductMng manager;

    @Autowired
    private ProductTypePropertyMng productTypePropertyMng;

    @Autowired
    private ProductFashionMng productFashionMng;

    @Autowired
    private ProductTypeMng productTypeMng;

    @Autowired
    private ExendedMng exendedMng;

    @Autowired
    private ProductStandardMng productStandardMng;

    @Autowired
    private CartItemMng cartItemMng;

    @Autowired
    private BrandMng brandMng;
    private ServletContext servletContext;

    @RequestMapping({"/product/v_title_list.do"})
    public String get_list_and_title(Integer ctgId, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, Integer pageNo, HttpServletRequest request, ModelMap model) {
        String productName = RequestUtils.getQueryParam(request, "productName");
        productName = StringUtils.trim(productName);
        String brandName = RequestUtils.getQueryParam(request, "brandName");
        brandName = StringUtils.trim(brandName);
        Website web = SiteUtils.getWeb(request);
        if (ctgId != null) {
            model.addAttribute("category", this.categoryMng.findById(ctgId));
        }
        Pagination pagination = this.manager.getPage(SiteUtils.getWebId(request),
                ctgId, productName, brandName, status, isRecommend, isSpecial, isHotsale, isNewProduct, typeId,
                startSalePrice, endSalePrice, startStock, endStock,
                SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        List typeList = this.productTypeMng.getList(web.getId());

        List list = this.categoryMng.getTopList(
                SiteUtils.getWebId(request));

        if (list.size() > 0) {
            Category treeRoot = new Category();
            treeRoot.setName(MessageResolver.getMessage(request,
                    "product.allCategory", new Object[0]));
            treeRoot.setChild(new LinkedHashSet(list));
            model.addAttribute("treeRoot", treeRoot);
        }

        model.addAttribute("typeList", typeList);
        model.addAttribute("productName", productName);
        model.addAttribute("brandName", brandName);
        model.addAttribute("status", status);
        model.addAttribute("isRecommend", isRecommend);
        model.addAttribute("isSpecial", isSpecial);
        model.addAttribute("isHotsale", isHotsale);
        model.addAttribute("isNewProduct", isNewProduct);
        model.addAttribute("typeId", typeId);
        model.addAttribute("startSalePrice", startSalePrice);
        model.addAttribute("endSalePrice", endSalePrice);
        model.addAttribute("startStock", startStock);
        model.addAttribute("endStock", endStock);
        model.addAttribute("pagination", pagination);
        model.addAttribute("ctgId", ctgId);
        model.addAttribute("pageNo", Integer.valueOf(pagination.getPageNo()));

        get_set_title_status(request);

        model.addAttribute("title_id1", this.title_id1);
        model.addAttribute("title_coverImg2", this.title_coverImg2);
        model.addAttribute("title_prdtName3", this.title_prdtName3);
        model.addAttribute("title_prdtCategory4", this.title_prdtCategory4);
        model.addAttribute("title_prdtType5", this.title_prdtType5);
        model.addAttribute("title_prdtSalePrice6", this.title_prdtSalePrice6);
        model.addAttribute("title_prdtStockCount7", this.title_prdtStockCount7);
        model.addAttribute("title_prdtBrand8", this.title_prdtBrand8);
        model.addAttribute("title_prdtOnSale9", this.title_prdtOnSale9);
        model.addAttribute("title_Operate10", this.title_Operate10);

        return "product/list";
    }

    @RequestMapping({"/product/v_categoryList.do"})
    public String categoryList(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);

        List clist = this.categoryMng.getTopList(web.getId());
        model.addAttribute("clist", clist);
        return "product/categoryList";
    }

    @RequestMapping({"/product/v_product_categorychild.do"})
    public void productCategoryChild(Integer parentId, HttpServletRequest request, HttpServletResponse response) throws JSONException {
        Website web = SiteUtils.getWeb(request);
        JSONArray arr = new JSONArray();
        if (parentId != null) {
            List<Category> clist = this.categoryMng.getChildList(web.getId(), parentId);
            if (clist.size() >= 0) {
                for (Category t : clist) {
                    JSONObject o = new JSONObject();
                    o.put("success", true);
                    o.put("id", t.getId());
                    o.put("name", t.getName());
                    arr.put(o);
                }
            }
        }
        ResponseUtils.renderJson(response, arr.toString());
    }

    @RequestMapping({"/product/v_left.do"})
    public String left(HttpServletRequest request, ModelMap model) {
        List list = this.categoryMng.getTopList(
                SiteUtils.getWebId(request));

        if (list.size() > 0) {
            Category treeRoot = new Category();
            treeRoot.setName(MessageResolver.getMessage(request,
                    "product.allCategory", new Object[0]));
            treeRoot.setChild(new LinkedHashSet(list));
            model.addAttribute("treeRoot", treeRoot);
        }
        return "product/left";
    }

    @RequestMapping({"/product/ajaxcategory.do"})
    public void ajaxcategory(Integer ctgId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
        Category category = this.categoryMng.findById(ctgId);

        List list2 = new ArrayList();
        list2.addAll(category.getBrands());

        Long[] ids = new Long[list2.size()];
        String[] names = new String[list2.size()];
        int i = 0;
        for (int j = list2.size(); i < j; i++) {
            Brand brand = (Brand) list2.get(i);
            ids[i] = brand.getId();
            names[i] = brand.getName();
        }

        JSONObject json = new JSONObject();
        try {
            json.put("ids", ids);
            json.put("names", names);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ResponseUtils.renderJson(response, json.toString());
    }

    @RequiresPermissions({"product:v_add"})
    @RequestMapping({"/product/v_add.do"})
    public String add(Integer ctgId, HttpServletRequest request, ModelMap model) {
        Category category = this.categoryMng.findById(ctgId);

        List itemList = this.productTypePropertyMng.getList(category.getType().getId(), false);
        List standardTypeList = this.standardTypeMng.getList(category.getId());
        List typeList = this.productTypeMng.getList(Long.valueOf(1L));
        List brandList = this.brandMng.getAllList();

        List brandList1 = this.brandMng.getBrandList(category.getName());
        model.addAttribute("brandList1", brandList1);

        model.addAttribute("brandList", brandList);
        model.addAttribute("typeList", typeList);
        model.addAttribute("ctgId", ctgId);
        model.addAttribute("category", category);

        model.addAttribute("categoryList", this.categoryMng.getList(SiteUtils.getWebId(request)));

        model.addAttribute("tagList", this.productTagMng.getList(SiteUtils.getWebId(request)));
        model.addAttribute("standardTypeList", standardTypeList);
        model.addAttribute("itemList", itemList);
        List exendeds = this.exendedMng.getList(category.getType().getId());
        Map map = new HashMap();
        Map map1 = new HashMap();
        int num = exendeds.size();
        for (int i = 0; i < num; i++) {
            map.put(((Exended) exendeds.get(i)).getId().toString(), ((Exended) exendeds.get(i)).getItems());
            map1.put(((Exended) exendeds.get(i)).getId().toString(), exendeds.get(i));
        }
        model.addAttribute("map", map);
        model.addAttribute("map1", map1);
        return "product/add";
    }

    @RequiresPermissions({"product:v_edit"})
    @RequestMapping({"/product/v_edit.do"})
    public String edit(Long id, Long ctgId, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Product product = this.manager.findById(id);
        List psList = this.productStandardMng.findByProductId(id);
        String productKeywords = StringUtils.join(product.getKeywords(), ",");
        Category category = product.getCategory();
        List standardTypeList = this.standardTypeMng.getList(category.getId());
        List itemList = this.productTypePropertyMng.getList(category.getType().getId(), false);
        List pelist = product.getExendeds();
        List exendeds = this.exendedMng.getList(category.getType().getId());
        List typeList = this.productTypeMng.getList(Long.valueOf(1L));
        List brandList = this.brandMng.getAllList();
        model.addAttribute("brandList", brandList);
        model.addAttribute("typeList", typeList);

        List relatedgoods = this.relatedgoodsMng.findByIdProductId(id);
        List productList = new ArrayList();
        if (relatedgoods != null) {
            for (int i = 0; i < relatedgoods.size(); i++) {
                if (this.manager.findById(((Relatedgoods) relatedgoods.get(i)).getProductIds()) != null) {
                    Product product1 = this.manager.findById(((Relatedgoods) relatedgoods.get(i)).getProductIds());
                    productList.add(product1);
                }
            }
            model.addAttribute("productList", productList);
        }
        Map map = new HashMap();
        Map map1 = new HashMap();
        int num = exendeds.size();
        for (int i = 0; i < num; i++) {
            map.put(((Exended) exendeds.get(i)).getId().toString(), ((Exended) exendeds.get(i)).getItems());
            map1.put(((Exended) exendeds.get(i)).getId().toString(), exendeds.get(i));
        }
        Map map2 = new HashMap();
        for (int i = 0; i < pelist.size(); i++) {
            map2.put(((ProductExended) pelist.get(i)).getName(), ((ProductExended) pelist.get(i)).getValue());
        }
        model.addAttribute("map2", map2);
        model.addAttribute("map", map);
        model.addAttribute("map1", map1);
        model.addAttribute("productKeywords", productKeywords);
        model.addAttribute("tagList", this.productTagMng.getList(SiteUtils.getWebId(request)));
        model.addAttribute("categoryList", this.categoryMng.getList(SiteUtils.getWebId(request)));
        model.addAttribute("standardTypeList", standardTypeList);
        model.addAttribute("category", category);
        model.addAttribute("product", product);
        model.addAttribute("ctgId", ctgId);
        model.addAttribute("isLimit", product.getProductExt().getIslimitTime());
        model.addAttribute("itemList", itemList);
        model.addAttribute("psList", psList);
        return "product/edit";
    }

    @RequiresPermissions({"product:o_save"})
    @RequestMapping({"/product/o_save.do"})
    public String save(Product bean, ProductExt ext, String rightlist, Integer categoryId, Long brandId, Long[] tagIds, String productKeywords, String[] nature, Long[] picture, String[] colorImg, Long[] character, @RequestParam(value = "file", required = false) MultipartFile file, String[] fashionSwitchPic, String[] fashionBigPic, String[] fashionAmpPic, Boolean[] isDefaults, Long[] colors, Long[] measures, Integer[] stockCounts, Double[] salePrices, Double[] marketPrices, Double[] costPrices, Integer ctgId, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, file, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        productKeywords = StringUtils.replace(productKeywords, MessageResolver.getMessage(request, "product.keywords.split", new Object[0]), ",");
        String[] keywords = StringUtils.split(productKeywords, ",");
        Website web = SiteUtils.getWeb(request);
        Map exended = RequestUtils.getRequestMap(request, "exended_");
        List li = new ArrayList(exended.keySet());
        String[] names = new String[li.size()];
        String[] values = new String[li.size()];
        if (stockCounts != null) {
            Integer stockCount = Integer.valueOf(0);
            for (Integer s : stockCounts) {
                stockCount = Integer.valueOf(stockCount.intValue() + s.intValue());
            }
            bean.setStockCount(stockCount);
        }
        for (int i = 0; i < li.size(); i++) {
            names[i] = ((String) li.get(i));
            values[i] = ((String) exended.get(li.get(i)));
        }
        bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
        bean = this.manager.save(bean, ext, web.getId(), categoryId, brandId, tagIds,
                keywords, names, values, fashionSwitchPic, fashionBigPic, fashionAmpPic, file);
        this.relatedgoodsMng.addProduct(bean.getId(), getProductIds(rightlist));
        if (picture != null) {
            for (int i = 0; i < picture.length; i++) {
                ProductStandard ps = new ProductStandard();
                ps.setImgPath(colorImg[i]);
                ps.setType(this.standardMng.findById(picture[i]).getType());
                ps.setProduct(bean);
                ps.setStandard(this.standardMng.findById(picture[i]));
                ps.setDataType(true);
                this.productStandardMng.save(ps);
            }
        }
        if (character != null) {
            for (int i = 0; i < character.length; i++) {
                ProductStandard ps = new ProductStandard();
                ps.setType(this.standardMng.findById(character[i]).getType());
                ps.setProduct(bean);
                ps.setStandard(this.standardMng.findById(character[i]));
                ps.setDataType(false);
                this.productStandardMng.save(ps);
            }
        }
        saveProductFash(bean, nature, isDefaults, stockCounts, salePrices, marketPrices, costPrices);
        try {
            if (bean.getStatus().intValue() == Product.ON_SALE_STATUS)
                this.luceneProductSvc.createIndex(bean);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("save Product. id={}", bean.getId());
        model.addAttribute("message", "global.success");
        model.addAttribute("brandId", brandId);
        return add(ctgId, request, model);
    }

    @RequiresPermissions({"product:o_update"})
    @RequestMapping({"/product/o_update.do"})
    public String update(Product bean, ProductExt ext, String rightlist, Integer categoryId, Long brandId, Long[] tagIds, String productKeywords, String[] nature, Long[] picture, String[] colorImg, Long[] character, @RequestParam(value = "file", required = false) MultipartFile file, String[] fashionSwitchPic, String[] fashionBigPic, String[] fashionAmpPic, Boolean[] isDefaults, Long[] colors, Long[] measures, Integer[] stockCounts, Double[] salePrices, Double[] marketPrices, Double[] costPrices, Long[] fashId, Integer ctgId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), file, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        productKeywords = StringUtils.replace(productKeywords,
                MessageResolver.getMessage(request, "product.keywords.split", new Object[0]), ",");
        String[] keywords = StringUtils.split(productKeywords, ",");
        Map exended = RequestUtils.getRequestMap(request, "exended_");
        List li = new ArrayList(exended.keySet());
        String[] names = new String[li.size()];
        String[] values = new String[li.size()];
        for (int i = 0; i < li.size(); i++) {
            names[i] = ((String) li.get(i));
            values[i] = ((String) exended.get(li.get(i)));
        }
        Map attr = RequestUtils.getRequestMap(request, "attr_");
        if (stockCounts != null) {
            Integer stockCount = Integer.valueOf(0);
            for (Integer s : stockCounts) {
                stockCount = Integer.valueOf(stockCount.intValue() + s.intValue());
            }
            bean.setStockCount(stockCount);
        }
        bean = this.manager.update(bean, ext, categoryId, brandId, tagIds, keywords, names, values, attr,
                fashionSwitchPic, fashionBigPic, fashionAmpPic, file);

        if (this.relatedgoodsMng.findByIdProductId(bean.getId()) != null) {
            this.relatedgoodsMng.updateProduct(bean.getId(), getProductIds(rightlist));
        }
        List pcList = this.productStandardMng.findByProductId(bean.getId());
        for (int j = 0; j < pcList.size(); j++) {
            this.productStandardMng.deleteById(((ProductStandard) pcList.get(j)).getId());
        }
        if (picture != null) {
            for (int i = 0; i < picture.length; i++) {
                ProductStandard ps = new ProductStandard();
                ps.setImgPath(colorImg[i]);
                ps.setType(this.standardMng.findById(picture[i]).getType());
                ps.setProduct(bean);
                ps.setStandard(this.standardMng.findById(picture[i]));
                ps.setDataType(true);
                this.productStandardMng.save(ps);
            }
        }
        if (character != null)
            for (int i = 0; i < character.length; i++) {
                ProductStandard ps = new ProductStandard();
                ps.setType(this.standardMng.findById(character[i]).getType());
                ps.setProduct(bean);
                ps.setStandard(this.standardMng.findById(character[i]));
                ps.setDataType(false);
                this.productStandardMng.save(ps);
            }
        try {
            if (bean.getCategory().getColorsize().booleanValue()) {
                Set<ProductFashion> pfList = bean.getFashions();

                if (fashId != null) {
                    for (ProductFashion ps : pfList)
                        if (!Arrays.asList(fashId).contains(ps.getId())) {
                            this.fashMng.deleteById(ps.getId());
                            this.cartItemMng.deleteByProductFactionId(ps.getId());
                        }
                } else {
                    for (ProductFashion ps : pfList) {
                        this.fashMng.deleteById(ps.getId());
                        this.cartItemMng.deleteByProductFactionId(ps.getId());
                    }
                }
                updateProductFash(bean, fashId, nature, isDefaults, stockCounts, salePrices, marketPrices, costPrices);
            }
        } catch (ObjectNotFoundException localObjectNotFoundException) {
        } catch (Exception e) {
            errors.addErrorString(this.manager.getTipFile("This.ChangeIsContainedInTheCaseOfTheDeletionOfTheGoods"));
            return errors.showErrorPage(model);
        }
        try {
            this.luceneProductSvc.updateIndex(bean);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("update Product. id={}.", bean.getId());
        return get_list_and_title(ctgId, null, null, null, null, null,
                null, null, null, null, null, pageNo, request, model);
    }

    @RequestMapping({"/product/o_delete.do"})
    public String delete(Long[] ids, Integer ctgId, Boolean isRecommend, Boolean isSpecial, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        try {
            Product[] beans = this.manager.deleteByIds(ids);
            for (Product bean : beans) {
                this.luceneProductSvc.deleteIndex(bean);
                log.info("delete Product. id={}", bean.getId());
            }
        } catch (Exception e) {
            return "redirect:v_error.do";
        }
        Product[] beans;
        return get_list_and_title(ctgId, null, isRecommend, isSpecial, null, null, null, null, null, null, null, pageNo, request, model);
    }

    @RequestMapping({"/product/v_error.do"})
    public String error(HttpServletRequest request, ModelMap model) {
        return "product/error";
    }

    @RequestMapping({"/product/v_standardTypes_add.do"})
    public String standardTypesAdd(Integer categoryId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List standardTypeList = this.standardTypeMng.getList(categoryId);
        model.addAttribute("standardTypeList", standardTypeList);
        model.addAttribute("digit", Integer.valueOf(standardTypeList.size()));
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=UTF-8");
        return "product/standardTypes_add";
    }

    @RequestMapping({"/product/v_standards_add.do"})
    public String standards(Long standardTypeId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
        List sList = this.standardMng.findByTypeId(standardTypeId);
        model.addAttribute("sList", sList);
        model.addAttribute("standardType", this.standardTypeMng.findById(standardTypeId));
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=UTF-8");
        return "product/standards_add";
    }

    @RequestMapping({"/product/o_create_index.do"})
    public String createIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws ParseException {
        String path = this.servletContext.getRealPath("/WEB-INF/lucene");
        boolean success = false;
        try {
            int count = this.luceneProductSvc.index(path, null, null, null);
            model.addAttribute("count", Integer.valueOf(count));
            success = true;
        } catch (CorruptIndexException e) {
            log.error("", e);
        } catch (LockObtainFailedException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        }
        model.addAttribute("success", Boolean.valueOf(success));
        return "product/create_index_result";
    }

    @RequestMapping({"/product/o_delFashion.do"})
    public String deleFashion(Long id, Long productId, HttpServletResponse response) throws JSONException {
        Boolean b = this.productFashionMng.getOneFash(productId);
        JSONObject j = new JSONObject();
        if ((b != null) && (b.booleanValue())) {
            this.productFashionMng.deleteById(id);
            j.put("message", "删除成功！");
            j.put("boo", true);
            ResponseUtils.renderJson(response, j.toString());
            return null;
        }
        j.put("message", "必须留一款式！");
        j.put("boo", false);
        ResponseUtils.renderJson(response, j.toString());
        return null;
    }

    @RequestMapping({"/product/v_search.do"})
    public void search(Long typeId, Long brandId, String productName, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List list = this.manager.getList(typeId, brandId, productName);
        JSONObject json = new JSONObject();
        try {
            int i = 0;
            for (int j = list.size(); i < j; i++)
                json.append(((Product) list.get(i)).getId() + "", ((Product) list.get(i)).getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ResponseUtils.renderJson(response, json.toString());
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

    private void saveProductFash(Product bean, String[] nature, Boolean[] isDefaults, Integer[] stockCounts, Double[] salePrices, Double[] marketPrices, Double[] costPrices) {
        if (nature != null)
            for (int i = 0; i < nature.length; i++) {
                ProductFashion pfash = new ProductFashion();
                pfash.setCreateTime(new Date());
                pfash.setIsDefault(isDefaults[i]);
                pfash.setStockCount(stockCounts[i]);
                pfash.setMarketPrice(marketPrices[i]);
                pfash.setSalePrice(salePrices[i]);
                pfash.setCostPrice(costPrices[i]);
                pfash.setProductId(bean);
                pfash.setNature(nature[i]);
                String[] arr = nature[i].split("_");
                ProductFashion fashion = this.productFashionMng.save(pfash, arr);
                this.productFashionMng.addStandard(fashion, arr);
                if (isDefaults[i].booleanValue()) {
                    bean.setCostPrice(costPrices[i]);
                    bean.setMarketPrice(marketPrices[i]);
                    bean.setSalePrice(salePrices[i]);
                    this.manager.update(bean);
                }
            }
    }

    private void updateProductFash(Product bean, Long[] fashId, String[] nature, Boolean[] isDefaults, Integer[] stockCounts, Double[] salePrices, Double[] marketPrices, Double[] costPrices) {
        if (nature != null)
            for (int i = 0; i < nature.length; i++) {
                if ((fashId != null) && (i < fashId.length)) {
                    ProductFashion pfash = this.productFashionMng.findById(fashId[i]);
                    pfash.setCreateTime(new Date());
                    pfash.setIsDefault(isDefaults[i]);
                    pfash.setStockCount(stockCounts[i]);
                    pfash.setMarketPrice(marketPrices[i]);
                    pfash.setSalePrice(salePrices[i]);
                    pfash.setCostPrice(costPrices[i]);
                    pfash.setProductId(bean);
                    pfash.setNature(nature[i]);
                    String[] arr = nature[i].split("_");
                    this.productFashionMng.updateStandard(pfash, arr);
                    if (isDefaults[i].booleanValue()) {
                        bean.setCostPrice(costPrices[i]);
                        bean.setMarketPrice(marketPrices[i]);
                        bean.setSalePrice(salePrices[i]);
                        this.manager.update(bean);
                    }
                } else {
                    ProductFashion pfash = new ProductFashion();
                    pfash.setCreateTime(new Date());
                    pfash.setIsDefault(isDefaults[i]);
                    pfash.setStockCount(stockCounts[i]);
                    pfash.setMarketPrice(marketPrices[i]);
                    pfash.setSalePrice(salePrices[i]);
                    pfash.setCostPrice(costPrices[i]);
                    pfash.setProductId(bean);
                    pfash.setNature(nature[i]);
                    String[] arr = nature[i].split("_");
                    ProductFashion fashion = this.productFashionMng.save(pfash, arr);
                    this.productFashionMng.addStandard(fashion, arr);
                    if (isDefaults[i].booleanValue()) {
                        bean.setCostPrice(costPrices[i]);
                        bean.setMarketPrice(marketPrices[i]);
                        bean.setSalePrice(salePrices[i]);
                        this.manager.update(bean);
                    }
                }
            }
    }

    @RequiresPermissions({"product:v_shangjia"})
    @RequestMapping({"/product/v_shangjia.do"})
    public String shangjia(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        for (Long id : ids) {
            Product bean = this.manager.findById(id);

            bean.setStatus(Integer.valueOf(Product.ON_SALE_STATUS));
            bean = this.manager.update(bean);
            try {
                this.luceneProductSvc.updateIndex(bean);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "redirect:v_title_list.do";
    }

    @RequiresPermissions({"product:v_xiajia"})
    @RequestMapping({"/product/v_xiajia.do"})
    public String xiajia(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        for (Long id : ids) {
            Product bean = this.manager.findById(id);

            bean.setStatus(Integer.valueOf(Product.NOT_SALE_STATUS));
            bean = this.manager.update(bean);
            try {
                this.luceneProductSvc.updateIndex(bean);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "redirect:v_title_list.do";
    }

    private WebErrors validateSave(Product bean, MultipartFile file, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if ((file != null) && (!file.isEmpty())) {
            String name = file.getOriginalFilename();
            vldImage(name, errors);
        }
        bean.setWebsite(SiteUtils.getWeb(request));
        return errors;
    }

    private WebErrors validateEdit(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors.ifNull(id, "id");
        vldExist(id, errors);
        return errors;
    }

    private WebErrors validateUpdate(Long id, MultipartFile file, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors.ifNull(id, "id");
        if ((file != null) && (!file.isEmpty())) {
            String name = file.getOriginalFilename();
            vldImage(name, errors);
            if (errors.hasErrors()) {
                return errors;
            }
        }
        vldExist(id, errors);
        return errors;
    }

    private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors.ifEmpty(ids, "ids");
        for (Long id : ids) {
            vldExist(id, errors);
        }
        return errors;
    }

    private void vldExist(Long id, WebErrors errors) {
        if (errors.hasErrors()) {
            return;
        }
        Product entity = this.manager.findById(id);
        errors.ifNotExist(entity, Product.class, id);
    }

    private void vldImage(String filename, WebErrors errors) {
        if (errors.hasErrors()) {
            return;
        }
        String ext = FilenameUtils.getExtension(filename);
        if (!ImageUtils.isImage(ext))
            errors.addErrorString("not support image extension:" + filename);
    }

    private void get_set_title_status(HttpServletRequest request) {
        String checked = request.getParameter("checked");
        String index = request.getParameter("index");

        if ((StringUtils.isNotBlank(checked)) && (StringUtils.isNotBlank(index))) {
            if (index.equals("1")) {
                this.title_id1 = Boolean.valueOf(Boolean.parseBoolean(checked));
            }
            if (index.equals("2")) {
                this.title_coverImg2 = Boolean.valueOf(Boolean.parseBoolean(checked));
            }
            if (index.equals("3")) {
                this.title_prdtName3 = Boolean.valueOf(Boolean.parseBoolean(checked));
            }
            if (index.equals("4")) {
                this.title_prdtCategory4 = Boolean.valueOf(Boolean.parseBoolean(checked));
            }
            if (index.equals("5")) {
                this.title_prdtType5 = Boolean.valueOf(Boolean.parseBoolean(checked));
            }
            if (index.equals("6")) {
                this.title_prdtSalePrice6 = Boolean.valueOf(Boolean.parseBoolean(checked));
            }
            if (index.equals("7")) {
                this.title_prdtStockCount7 = Boolean.valueOf(Boolean.parseBoolean(checked));
            }
            if (index.equals("8")) {
                this.title_prdtBrand8 = Boolean.valueOf(Boolean.parseBoolean(checked));
            }
            if (index.equals("9")) {
                this.title_prdtOnSale9 = Boolean.valueOf(Boolean.parseBoolean(checked));
            }
            if (index.equals("10")) {
                this.title_Operate10 = Boolean.valueOf(Boolean.parseBoolean(checked));
            }
        } else {
            this.title_id1 = Boolean.valueOf(true);
            this.title_coverImg2 = Boolean.valueOf(true);
            this.title_prdtName3 = Boolean.valueOf(true);
            this.title_prdtCategory4 = Boolean.valueOf(true);
            this.title_prdtType5 = Boolean.valueOf(true);
            this.title_prdtSalePrice6 = Boolean.valueOf(true);
            this.title_prdtStockCount7 = Boolean.valueOf(true);
            this.title_prdtBrand8 = Boolean.valueOf(true);
            this.title_prdtOnSale9 = Boolean.valueOf(true);
            this.title_Operate10 = Boolean.valueOf(true);
        }
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

