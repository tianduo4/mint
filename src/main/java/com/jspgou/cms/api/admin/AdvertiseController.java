package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.api.ExceptionUtil;
import com.jspgou.cms.entity.Advertise;
import com.jspgou.cms.manager.AdvertiseMng;
import com.jspgou.cms.web.RequestUtils1;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.web.WebErrors;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdvertiseController {

    @Autowired
    private AdvertiseMng manager;

    @RequestMapping({"/advertise/list"})
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
            Pagination page = this.manager.getPage(null, null, pageNo.intValue(), pageSize.intValue());
            List list = page.getList();
            int totalCount = page.getTotalCount();
            JSONArray jsonArray = new JSONArray();
            if ((list != null) && (list.size() > 0)) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, ((Advertise) list.get(i)).convertToJson());
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

    @SignValidate
    @RequestMapping({"/advertise/save"})
    public void save(Advertise bean, Integer adspaceId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getName(), adspaceId, bean.getClickCount(), bean.getDisplayCount(), bean.getWeight()});
            if (!errors.hasErrors()) {
                Map<String, String> attr = RequestUtils1.getRequestMap(request, "attr_");

                Set<String> toRemove = new HashSet();
                for (Map.Entry entry : attr.entrySet()) {
                    if (StringUtils.isBlank((String) entry.getValue())) {
                        toRemove.add((String) entry.getKey());
                    }
                }
                for (String key : toRemove) {
                    attr.remove(key);
                }
                bean = this.manager.save(bean, adspaceId, attr);
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

    @RequestMapping({"/advertise/get"})
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                Advertise advertise = new Advertise();
                if (id.intValue() != 0) {
                    advertise = this.manager.findById(id);
                }
                if (advertise != null) {
                    body = advertise.convertToJson().toString();
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
    @RequestMapping({"/advertise/update"})
    public void update(Advertise bean, Integer adspaceId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getName(), adspaceId, bean.getClickCount(), bean.getDisplayCount(), bean.getWeight()});
            if (!errors.hasErrors()) {
                Map<String, String> attr = RequestUtils1.getRequestMap(request, "attr_");

                Set<String> toRemove = new HashSet();
                for (Map.Entry entry : attr.entrySet()) {
                    if (StringUtils.isBlank((String) entry.getValue())) {
                        toRemove.add((String) entry.getKey());
                    }
                }
                for (String key : toRemove) {
                    attr.remove(key);
                }
                this.manager.update(bean, adspaceId, attr);
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
    @RequestMapping({"/advertise/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if (StringUtils.isNotBlank(ids)) {
                Integer[] id = null;
                String[] str = ids.split(",");
                id = new Integer[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Integer.valueOf(Integer.parseInt(str[i]));
                }
                this.manager.deleteByIds(id);
            }
        } catch (Exception e) {
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

