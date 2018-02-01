package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.api.ExceptionUtil;
import com.mint.cms.entity.Shipping;
import com.mint.cms.manager.LogisticsMng;
import com.mint.cms.manager.ShippingMng;
import com.mint.cms.web.SignValidate;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShippingController {

    @Autowired
    private ShippingMng manager;

    @Autowired
    private LogisticsMng logisticsMng;

    @RequestMapping({"/ship/list"})
    public void list(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            List list = this.manager.getList(SiteUtils.getWebId(request),
                    true);
            JSONArray jsonArray = new JSONArray();
            if ((list != null) && (list.size() > 0)) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(((Shipping) list.get(i)).converToJson());
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

    @RequestMapping({"/ship/get"})
    public void getShip(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                Shipping ship = new Shipping();
                if (id.longValue() != 0L) {
                    ship = this.manager.findById(id);
                }
                if (ship != null) {
                    body = ship.converToJson().toString();
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
    @RequestMapping({"/ship/save"})
    public void save(Shipping bean, Long logisticsId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Website web = SiteUtils.getWeb(request);
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getName(),
                    logisticsId, bean.getLogisticsType(),
                    bean.getUniformPrice(), bean.getPriority(),
                    bean.getDisabled(), bean.getIsDefault()});
            if (!errors.hasErrors()) {
                if (bean.getIsDefault().booleanValue()) {
                    List list = this.manager.getList(Long.valueOf(1L), true);
                    for (int i = 0; i < list.size(); i++) {
                        ((Shipping) list.get(i)).setIsDefault(Boolean.valueOf(false));
                        this.manager.update((Shipping) list.get(i));
                    }
                }
                bean.setWebsite(web);
                bean.setLogistics(this.logisticsMng.findById(logisticsId));
                bean = this.manager.save(bean);
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
    @RequestMapping({"/ship/update"})
    public void update(Shipping bean, Long logisticsId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getId(),
                    bean.getName(), logisticsId, bean.getLogisticsType(),
                    bean.getUniformPrice(), bean.getPriority(),
                    bean.getDisabled(), bean.getIsDefault()});
            if (!errors.hasErrors()) {
                if (bean.getIsDefault().booleanValue()) {
                    List list = this.manager.getList(Long.valueOf(1L), true);
                    for (int i = 0; i < list.size(); i++) {
                        ((Shipping) list.get(i)).setIsDefault(Boolean.valueOf(false));
                        this.manager.update(bean);
                    }
                }
                bean.setLogistics(this.logisticsMng.findById(logisticsId));
                bean = this.manager.update(bean);
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
    @RequestMapping({"/ship/delete"})
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
                this.manager.deleteByIds(id);
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
    @RequestMapping({"/ship/priority"})
    public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids, priority});
            if (!errors.hasErrors()) {
                Long[] id = null;
                Integer[] prioritys = null;
                String[] str = ids.split(",");
                id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }
                String[] str1 = priority.split(",");
                prioritys = new Integer[str1.length];
                for (int i = 0; i < str1.length; i++) {
                    prioritys[i] = Integer.valueOf(Integer.parseInt(str1[i]));
                }
                this.manager.updatePriority(id, prioritys);
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
}

