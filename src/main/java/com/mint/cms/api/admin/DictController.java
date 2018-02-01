package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.api.ExceptionUtil;
import com.mint.cms.entity.ShopDictionary;
import com.mint.cms.manager.ShopDictionaryMng;
import com.mint.cms.manager.ShopDictionaryTypeMng;
import com.mint.cms.web.SignValidate;
import com.mint.common.page.Pagination;
import com.mint.common.web.ResponseUtils;
import com.mint.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DictController {

    @Autowired
    private ShopDictionaryMng shopDictionaryMng;

    @Autowired
    private ShopDictionaryTypeMng shopDictionaryTypeMng;

    @RequestMapping({"/dict/listByTypeId"})
    public void list(Long typeId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);

            errors = ApiValidate.validateRequiredParams(errors, new Object[]{typeId});
            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                List<ShopDictionary> dicts = this.shopDictionaryMng.getListByType(typeId);
                JSONArray jsons = new JSONArray();
                for (ShopDictionary dict : dicts) {
                    jsons.add(dict.converToJson());
                }
                body = jsons.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/dict/list"})
    public void list(Integer pageNo, Integer pageSize, Long typeId, String name, HttpServletRequest request, HttpServletResponse response) {
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
                Pagination pagination = this.shopDictionaryMng.getPage(name, typeId, pageNo.intValue(), pageSize.intValue());
                List<ShopDictionary> dicts = (List<ShopDictionary>) pagination.getList();
                JSONArray jsons = new JSONArray();
                for (ShopDictionary dict : dicts) {
                    jsons.add(dict.converToJson());
                }
                body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
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
    @RequestMapping({"/dict/save"})
    public void save(ShopDictionary dict, Long typeId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{dict.getName(), typeId});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                dict.setShopDictionaryType(this.shopDictionaryTypeMng.findById(typeId));
                this.shopDictionaryMng.save(dict);
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/dict/get"})
    public void get(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                ShopDictionary dict = new ShopDictionary();
                if ((id != null) && (id.longValue() != 0L)) {
                    dict = this.shopDictionaryMng.findById(id);
                }
                if (dict != null) {
                    body = dict.converToJson().toString();
                } else {
                    code = 206;
                    message = "\"object not found\"";
                }
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
    @RequestMapping({"/dict/update"})
    public void update(ShopDictionary dict, Long typeId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{dict.getId(), dict.getName(), typeId});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                dict.setShopDictionaryType(this.shopDictionaryTypeMng.findById(typeId));
                this.shopDictionaryMng.update(dict);
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
    @RequestMapping({"/dict/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if (!StringUtils.isNotBlank(ids)) {
                code = 202;
                message = "\"param error\"";
            } else {
                String[] str = ids.split(",");
                Long[] id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }
                this.shopDictionaryMng.deleteByIds(id);
            }
        } catch (Exception e) {
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/dict/updateSort"})
    public void updateSort(String ids, String prioritys, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids, prioritys});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                String[] str = ids.split(",");
                String[] pro = prioritys.split(",");
                Long[] id = new Long[str.length];
                Integer[] priority = new Integer[pro.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                    priority[i] = Integer.valueOf(Integer.parseInt(pro[i]));
                }
                this.shopDictionaryMng.updatePriority(id, priority);
            }
        } catch (IndexOutOfBoundsException e) {
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
}

