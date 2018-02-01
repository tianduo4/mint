package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.api.ExceptionUtil;
import com.mint.cms.entity.Brand;
import com.mint.cms.entity.Category;
import com.mint.cms.manager.BrandMng;
import com.mint.cms.manager.CategoryMng;
import com.mint.cms.web.SignValidate;
import com.mint.common.page.Pagination;
import com.mint.common.util.CnToSpell;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BrandController {

    @Autowired
    private BrandMng brandMng;

    @Autowired
    private CategoryMng categoryMng;

    @RequestMapping({"/brand/list"})
    public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
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
            Pagination page = this.brandMng.getPage(null, null, pageNo.intValue(), pageSize.intValue());
            List list = page.getList();
            int totalCount = page.getTotalCount();
            JSONArray jsonArray = new JSONArray();
            if ((list != null) && (list.size() > 0)) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, ((Brand) list.get(i)).convertToJson());
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

    @RequestMapping({"/brand/listByCategoryId"})
    public void list(Integer categoryId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{categoryId});
            if (!errors.hasErrors()) {
                List list = this.brandMng.getListByCateoryId(categoryId);
                JSONArray jsonArray = new JSONArray();
                if ((list != null) && (list.size() > 0)) {
                    for (int i = 0; i < list.size(); i++) {
                        jsonArray.put(i, ((Brand) list.get(i)).convertToJson());
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
    @RequestMapping({"/brand/save"})
    public void save(Brand brand, Integer categoryId, String text, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{
                    brand.getName(), categoryId});
            if (!errors.hasErrors()) {
                CnToSpell cts = new CnToSpell();
                brand.setFirstCharacter(cts.getBeginCharacter(brand.getName())
                        .substring(0, 1));
                Website web = SiteUtils.getWeb(request);
                brand.setWebsite(web);
                brand.setCategory(this.categoryMng.findById(categoryId));
                brand = this.brandMng.save(brand, text);
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

    @RequestMapping({"/brand/getBrand"})
    public void edit(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                Brand brand = new Brand();
                if (id.longValue() != 0L) {
                    brand = this.brandMng.findById(id);
                }
                if (brand != null) {
                    body = brand.convertToJson().toString();
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

    @RequestMapping({"/brand/getCategory"})
    public void getCategory(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Long site = SiteUtils.getWebId(request);
            List list = this.categoryMng.getListForParent(site, null);
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

    @SignValidate
    @RequestMapping({"/brand/update"})
    public void update(Brand brand, Integer categoryId, String text, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{brand.getId(),
                    brand.getName()});
            if (!errors.hasErrors()) {
                CnToSpell cts = new CnToSpell();
                brand.setFirstCharacter(cts.getBeginCharacter(brand.getName())
                        .substring(0, 1));
                brand.setCategory(this.categoryMng.findById(categoryId));
                brand = this.brandMng.update(brand, text);
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
    @RequestMapping({"/brand/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids});
            if (!errors.hasErrors()) {
                Long[] id = null;
                if (StringUtils.isNotBlank(ids)) {
                    String[] str = ids.split(",");
                    id = new Long[str.length];
                    for (int i = 0; i < str.length; i++)
                        id[i] = Long.valueOf(Long.parseLong(str[i]));
                } else {
                    code = 202;
                    message = "\"param error\"";
                }
                this.brandMng.deleteByIds(id);
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
    @RequestMapping({"/brand/priority"})
    public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response) {
        String message = "\"success\"";
        int code = 200;
        String body = "\"\"";
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids, priority});
            if (!errors.hasErrors()) {
                Long[] id = null;
                Integer[] prioritys = null;
                if (StringUtils.isNotBlank(ids)) {
                    String[] str = ids.split(",");
                    id = new Long[str.length];
                    for (int i = 0; i < str.length; i++) {
                        id[i] = Long.valueOf(Long.parseLong(str[i]));
                    }
                }
                if (StringUtils.isNotBlank(priority)) {
                    String[] str = priority.split(",");
                    prioritys = new Integer[str.length];
                    for (int i = 0; i < str.length; i++) {
                        prioritys[i] = Integer.valueOf(Integer.parseInt(str[i]));
                    }
                }
                this.brandMng.updatePriority(id, prioritys);
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

