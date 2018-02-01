package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.api.CommonUtils;
import com.jspgou.cms.api.ExceptionUtil;
import com.jspgou.cms.entity.Brand;
import com.jspgou.cms.entity.Category;
import com.jspgou.cms.entity.ProductType;
import com.jspgou.cms.entity.StandardType;
import com.jspgou.cms.manager.BrandMng;
import com.jspgou.cms.manager.CategoryMng;
import com.jspgou.cms.manager.ProductTypeMng;
import com.jspgou.cms.manager.StandardTypeMng;
import com.jspgou.cms.web.CmsThreadVariable;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.web.RequestUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

@Controller
public class CategoryController
        implements ServletContextAware {

    @Autowired
    private CategoryMng categoryMng;
    private ServletContext servletContext;

    @Autowired
    private ProductTypeMng productTypeMng;

    @Autowired
    private BrandMng brandMng;

    @Autowired
    private StandardTypeMng standardTypeMng;

    @RequestMapping({"/category/list"})
    public void category(Integer pid, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"\"";
        int code = 200;
        try {
            Website web = SiteUtils.getWeb(request);
            List list;
            if ((pid == null) || (pid.intValue() == 0))
                list = this.categoryMng.getTopList(web.getId());
            else {
                list = this.categoryMng.getChildList(web.getId(), pid);
            }
            JSONArray jsonArray = new JSONArray();
            if ((list != null) && (list.size() > 0)) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, ((Category) list.get(i)).convertToJson1());
                }
            }
            message = "\"success\"";
            body = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/category/tree"})
    public void tree(Integer pid, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"\"";
        int code = 200;
        try {
            Website web = SiteUtils.getWeb(request);
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{pid});
            if (!errors.hasErrors()) {
                boolean isRoot;
                if (pid.intValue() == 0)
                    isRoot = true;
                else
                    isRoot = false;
                List list;
                if (isRoot)
                    list = this.categoryMng.getTopList(web.getId());
                else {
                    list = this.categoryMng.getChildList(web.getId(), pid);
                }
                JSONArray jsonArray = new JSONArray();
                if ((list != null) && (list.size() > 0)) {
                    for (int i = 0; i < list.size(); i++) {
                        jsonArray.put(((Category) list.get(i)).convertToJson2(isRoot));
                    }
                }
                body = jsonArray.toString();
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

    @RequestMapping({"/category/template"})
    public void template(Long typeId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Website web = SiteUtils.getWeb(request);
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{typeId});
            if (!errors.hasErrors()) {
                if (typeId != null) {
                    ProductType type = this.productTypeMng.findById(typeId);

                    String ctgTplDirRel = type.getCtgTplDirRel();
                    String realDir = this.servletContext.getRealPath(ctgTplDirRel);
                    String relPath = ctgTplDirRel.substring(web.getTemplateRel(
                            request).length());

                    String txtTplDirRel = type.getTxtTplDirRel();
                    String txtrealDir = this.servletContext
                            .getRealPath(txtTplDirRel);
                    String txtrelPath = txtTplDirRel.substring(web
                            .getTemplateRel(request).length());

                    String[] channelTpls = type
                            .getChannelTpls(realDir, relPath);

                    String[] contentTpls = type.getContentTpls(txtrealDir,
                            txtrelPath);
                    JSONArray jsonArray1 = new JSONArray();
                    JSONArray jsonArray2 = new JSONArray();
                    if ((channelTpls != null) && (channelTpls.length > 0)) {
                        for (int i = 0; i < channelTpls.length; i++) {
                            jsonArray1.put(i, channelTpls[i]);
                        }
                    }
                    if ((contentTpls != null) && (contentTpls.length > 0)) {
                        for (int i = 0; i < contentTpls.length; i++) {
                            jsonArray2.put(i, contentTpls[i]);
                        }
                    }
                    JSONObject json = new JSONObject();
                    json.put("channelTpls", jsonArray1);
                    json.put("contentTpls", jsonArray2);
                    body = json.toString();
                } else {
                    code = 202;
                    message = "\"param error\"";
                }
            } else
                message = (String) errors.getErrors().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/category/brand"})
    public void nature(Integer categoryId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject json = new JSONObject();
            if (categoryId != null) {
                Category category = this.categoryMng.findById(categoryId);
                json.put("brandIds", category.getBrandIds());
                body = json.toString();
            } else {
                List brandList = this.brandMng.getAllList();
                if ((brandList != null) && (brandList.size() > 0)) {
                    for (int i = 0; i < brandList.size(); i++) {
                        jsonArray.put(i, ((Brand) brandList.get(i)).convertToJson1());
                    }
                }
                body = jsonArray.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/category/standardtype"})
    public void standardtype(Integer categoryId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            JSONArray jsonArray = new JSONArray();
            if (categoryId != null) {
                List standardTypeList = this.standardTypeMng
                        .getList(categoryId);
                if ((standardTypeList != null) && (standardTypeList.size() > 0))
                    for (int i = 0; i < standardTypeList.size(); i++)
                        jsonArray.put(i, ((StandardType) standardTypeList.get(i))
                                .convertToJson1());
            } else {
                List standardTypeList = this.standardTypeMng.getList();
                if ((standardTypeList != null) && (standardTypeList.size() > 0)) {
                    for (int i = 0; i < standardTypeList.size(); i++) {
                        jsonArray.put(i, ((StandardType) standardTypeList.get(i))
                                .convertToJson1());
                    }
                }
            }
            body = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/category/getType"})
    public void add(Long typeId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{typeId});
            if (!errors.hasErrors()) {
                ProductType type = this.productTypeMng.findById(typeId);
                JSONObject json = new JSONObject();
                json.put("typeName", type.getName());
                body = json.toString();
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
    @RequestMapping({"/category/save"})
    public void save(Category bean, Integer parentId, Long typeId, String brandIds, String standardTypeIds, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getName(),
                    bean.getPath(), parentId, typeId});
            if (!errors.hasErrors()) {
                if (this.categoryMng.getByPath(CmsThreadVariable.getSite().getId(), bean.getPath()) == null) {
                    Long[] brandId = null;
                    Long[] standardTypeId = null;
                    if (StringUtils.isNotBlank(brandIds)) {
                        String[] str = brandIds.split(",");
                        brandId = new Long[str.length];
                        for (int i = 0; i < str.length; i++) {
                            brandId[i] = Long.valueOf(Long.parseLong(str[i]));
                        }
                    }
                    if (StringUtils.isNotBlank(standardTypeIds)) {
                        String[] str = standardTypeIds.split(",");
                        standardTypeId = new Long[str.length];
                        for (int i = 0; i < str.length; i++) {
                            standardTypeId[i] = Long.valueOf(Long.parseLong(str[i]));
                        }
                    }
                    bean.setWebsite(CmsThreadVariable.getSite());
                    bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
                    bean = this.categoryMng.save(bean, parentId, typeId, brandId,
                            standardTypeId);
                } else {
                    code = 502;
                    message = "\"path not repeate\"";
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

    @RequestMapping({"/category/getCategory"})
    public void getCategory(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                Category category = new Category();
                if (id.intValue() != 0) {
                    category = this.categoryMng.findById(id);
                }
                if (category != null) {
                    JSONObject json = category.convertToJson3();
                    JSONArray jsonArray = new JSONArray();
                    List standardTypeList = this.standardTypeMng
                            .getList(id);
                    if ((standardTypeList != null) && (standardTypeList.size() > 0)) {
                        for (int i = 0; i < standardTypeList.size(); i++) {
                            jsonArray.put(CommonUtils.parseId(((StandardType) standardTypeList.get(i)).getId()));
                        }
                    }
                    json.put("brandIds", category.getBrandIds());
                    json.put("standardTypeIds", jsonArray);
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
    @RequestMapping({"/category/update"})
    public void update(Category bean, Integer parentId, Long typeId, String brandIds, String standardTypeIds, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getId(),
                    bean.getName(), bean.getPath()});
            if (!errors.hasErrors()) {
                if (this.categoryMng.getByPath(CmsThreadVariable.getSite().getId(), bean.getPath()) == null) {
                    Long[] brandId = null;
                    Long[] standardTypeId = null;
                    if (StringUtils.isNotBlank(brandIds)) {
                        String[] str = brandIds.split(",");
                        brandId = new Long[str.length];
                        for (int i = 0; i < str.length; i++) {
                            brandId[i] = Long.valueOf(Long.parseLong(str[i]));
                        }
                    }
                    if (StringUtils.isNotBlank(standardTypeIds)) {
                        String[] str = standardTypeIds.split(",");
                        standardTypeId = new Long[str.length];
                        for (int i = 0; i < str.length; i++) {
                            standardTypeId[i] = Long.valueOf(Long.parseLong(str[i]));
                        }
                    }
                    Map attr = RequestUtils.getRequestMap(request,
                            "attr_");
                    bean = this.categoryMng.update(bean, parentId, null, brandId, attr,
                            standardTypeId);
                } else {
                    code = 502;
                    message = "\"path not repeate\"";
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
    @RequestMapping({"/category/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids});
            if (!errors.hasErrors()) {
                Integer[] id = null;
                if (StringUtils.isNotBlank(ids)) {
                    String[] str = ids.split(",");
                    id = new Integer[str.length];
                    for (int i = 0; i < id.length; i++) {
                        id[i] = Integer.valueOf(Integer.parseInt(str[i]));
                    }
                }
                this.categoryMng.deleteByIds(id);
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

    @SignValidate
    @RequestMapping({"/category/priority"})
    public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids, priority});
            if (!errors.hasErrors()) {
                Integer[] wid = null;
                Integer[] prioritys = null;
                if (StringUtils.isNotBlank(ids)) {
                    String[] str = ids.split(",");
                    wid = new Integer[str.length];
                    for (int i = 0; i < str.length; i++) {
                        wid[i] = Integer.valueOf(Integer.parseInt(str[i]));
                    }
                }
                if (StringUtils.isNotBlank(priority)) {
                    String[] str = priority.split(",");
                    prioritys = new Integer[str.length];
                    for (int i = 0; i < str.length; i++) {
                        prioritys[i] = Integer.valueOf(Integer.parseInt(str[i]));
                    }
                }
                this.categoryMng.updatePriority(wid, prioritys);
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

    @RequestMapping({"/category/topCategory"})
    public void topCategory(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Long site = SiteUtils.getWebId(request);
            List list = this.categoryMng.getListForParent(site, id);
            JSONArray jsonArray = new JSONArray();
            if ((list != null) && (list.size() > 0)) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, ((Category) list.get(i)).convertToJson());
                }
            }
            body = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

