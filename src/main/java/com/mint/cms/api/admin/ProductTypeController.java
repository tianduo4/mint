package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.api.ExceptionUtil;
import com.mint.cms.entity.ProductType;
import com.mint.cms.entity.ProductTypeProperty;
import com.mint.cms.manager.ProductTypeMng;
import com.mint.cms.manager.ProductTypePropertyMng;
import com.mint.cms.web.CmsThreadVariable;
import com.mint.cms.web.SignValidate;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.ResponseUtils;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductTypeController {

    @Autowired
    private ProductTypeMng productTypeMng;

    @Autowired
    private ProductTypePropertyMng typePropertyMng;

    @RequestMapping({"/type/all"})
    public void typeList(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Long site = SiteUtils.getWebId(request);
            if (site != null) {
                List list = this.productTypeMng.getList(site);
                JSONArray jsonArray = new JSONArray();
                if ((list != null) && (list.size() > 0)) {
                    for (int i = 0; i < list.size(); i++) {
                        jsonArray.put(i, ((ProductType) list.get(i)).convertToJson(null));
                    }
                }
                body = jsonArray.toString();
            } else {
                code = 206;
                message = "\"object not found\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/type/list"})
    public void typeList(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Long site = SiteUtils.getWebId(request);
            if (site != null) {
                Pagination page = this.productTypeMng.getPage(
                        CmsThreadVariable.getSite().getId(), SimplePage.cpn(pageNo), pageSize.intValue());
                List list = page.getList();
                JSONArray jsonArray = new JSONArray();
                if ((list != null) && (list.size() > 0)) {
                    for (int i = 0; i < list.size(); i++) {
                        jsonArray.put(i, ((ProductType) list.get(i)).convertToJson(null));
                    }
                }
                body = jsonArray.toString() + ",\"totalCount\":" +
                        page.getTotalCount();
            } else {
                code = 206;
                message = "\"object not found\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/typeProperty/list"})
    public void typeProperty(Long typeId, Boolean isCategory, HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);

            errors = ApiValidate.validateRequiredParams(errors, new Object[]{typeId,
                    isCategory});
            if (!errors.hasErrors()) {
                ProductType pType = this.productTypeMng.findById(typeId);
                JSONArray jsonArray = new JSONArray();
                if (isCategory.booleanValue()) {
                    List list = this.typePropertyMng.getList(
                            typeId, true);
                    if ((list != null) && (list.size() > 0))
                        for (int i = 0; i < list.size(); i++)
                            jsonArray.put(
                                    i,
                                    ((ProductTypeProperty) list.get(i)).convertToJson(typeId,
                                            pType.getName()));
                } else {
                    List list = this.typePropertyMng.getList(
                            typeId, false);
                    if ((list != null) && (list.size() > 0)) {
                        for (int i = 0; i < list.size(); i++) {
                            jsonArray.put(
                                    i,
                                    ((ProductTypeProperty) list.get(i)).convertToJson(typeId,
                                            pType.getName()));
                        }
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

    @SignValidate
    @RequestMapping({"/type/save"})
    public void save(ProductType productType, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);

            errors = ApiValidate.validateRequiredParams(errors, new Object[]{
                    productType.getName(), productType.getChannelPrefix(),
                    productType.getContentPrefix()});
            if (!errors.hasErrors()) {
                productType.setWebsite(CmsThreadVariable.getSite());
                this.productTypeMng.save(productType);
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

    @RequestMapping({"/type/getType"})
    public void getType(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);

            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                ProductType bean = new ProductType();
                if (id.longValue() != 0L) {
                    bean = this.productTypeMng.findById(id);
                }
                if (bean != null) {
                    body = bean.convertToJson(null).toString();
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
    @RequestMapping({"/type/update"})
    public void update(ProductType productType, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);

            errors = ApiValidate.validateRequiredParams(errors, new Object[]{
                    productType.getId()});
            if (!errors.hasErrors()) {
                if (code == 200)
                    this.productTypeMng.update(productType);
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
    @RequestMapping({"/type/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
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
                this.productTypeMng.deleteByIds(id);
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/typeProperty/save"})
    public void saveTypeproerty(Long typeId, Boolean isCategory, String field, String propertyName, String dataType, String sort, String single, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{typeId,
                    isCategory, field, propertyName, dataType, dataType, sort});
            if (!errors.hasErrors()) {
                String[] fields = null;
                String[] propertyNames = null;
                Integer[] dataTypes = null;
                Integer[] sorts = null;
                Boolean[] singles = null;
                String[] str = field.split(",");
                fields = new String[str.length];
                for (int i = 0; i < str.length; i++) {
                    fields[i] = str[i];
                }

                String[] str1 = propertyName.split(",");
                propertyNames = new String[str.length];
                for (int i = 0; i < str1.length; i++) {
                    propertyNames[i] = str1[i];
                }

                String[] str2 = dataType.split(",");
                dataTypes = new Integer[str.length];
                for (int i = 0; i < str2.length; i++) {
                    dataTypes[i] = Integer.valueOf(Integer.parseInt(str2[i]));
                }

                String[] str3 = sort.split(",");
                sorts = new Integer[str3.length];
                for (int i = 0; i < str3.length; i++) {
                    sorts[i] = Integer.valueOf(Integer.parseInt(str3[i]));
                }

                String[] str4 = single.split(",");
                singles = new Boolean[str4.length];
                for (int i = 0; i < str3.length; i++) {
                    singles[i] = Boolean.valueOf(str3[i]);
                }

                ProductType pType = this.productTypeMng.findById(typeId);
                List itemList = this.typePropertyMng.getItems(
                        pType, isCategory.booleanValue(), fields, propertyNames, dataTypes,
                        sorts, singles);
                this.typePropertyMng.saveList(itemList);
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
    @RequestMapping({"/typePropertyContent/save"})
    public void saveContent(String id, String sort, String propertyName, String single, Long typeId, Boolean isCategory, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{typeId, id});
            if (!errors.hasErrors()) {
                Integer[] sorts = null;
                Boolean[] singles = null;
                String[] propertyNames = null;
                Long[] ids = null;

                String[] str = sort.split(",");
                sorts = new Integer[str.length];
                for (int i = 0; i < str.length; i++) {
                    sorts[i] = Integer.valueOf(Integer.parseInt(str[i]));
                }

                String[] str1 = single.split(",");
                singles = new Boolean[str1.length];
                for (int i = 0; i < str1.length; i++) {
                    singles[i] = Boolean.valueOf(str1[i]);
                }

                String[] str2 = propertyName.split(",");
                propertyNames = new String[str2.length];
                for (int i = 0; i < str2.length; i++) {
                    propertyNames[i] = str2[i];
                }
                String[] str3 = id.split(",");
                ids = new Long[str3.length];
                for (int i = 0; i < str3.length; i++) {
                    ids[i] = Long.valueOf(Long.parseLong(str3[i]));
                }

                this.typePropertyMng.updatePriority(ids, sorts, propertyNames,
                        singles);
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
    @RequestMapping({"/typePropertyContent/delete"})
    public void delContent(String ids, Integer typeId, Boolean isCategory, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids, typeId, isCategory});
            if (!errors.hasErrors()) {
                Long[] id = null;
                String[] str = ids.split(",");
                id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }
                this.typePropertyMng.deleteByIds(id);
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
}

