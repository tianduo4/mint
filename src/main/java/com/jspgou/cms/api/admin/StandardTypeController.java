package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.api.ExceptionUtil;
import com.jspgou.cms.entity.Standard;
import com.jspgou.cms.entity.StandardType;
import com.jspgou.cms.manager.StandardMng;
import com.jspgou.cms.manager.StandardTypeMng;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StandardTypeController {

    @Autowired
    private StandardTypeMng manager;

    @Autowired
    private StandardMng standardMng;

    @Autowired
    private StandardTypeMng standardTypeMng;

    @RequestMapping({"/standardType/list"})
    public void standardType(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
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
            Pagination page = this.manager.getPage(pageNo.intValue(), pageSize.intValue());
            List list = page.getList();
            int totalCount = page.getTotalCount();
            JSONArray jsonArray = new JSONArray();
            if ((list != null) && (list.size() > 0)) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, ((StandardType) list.get(i)).convertToJson(null));
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
    @RequestMapping({"/standardType/save"})
    public void save(StandardType standardType, String itemName, String itemColor, String itemPriority, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{
                    standardType.getName()});
            if (!errors.hasErrors()) {
                String[] itemNames = null;
                String[] itemColors = null;
                Integer[] itemPrioritys = null;
                if (StringUtils.isNotBlank(itemName)) {
                    String[] str = itemName.split(",");
                    itemNames = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        itemNames[i] = str[i];
                    }
                }
                if (StringUtils.isNotBlank(itemColor)) {
                    String[] str = itemColor.split(",");
                    itemColors = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        itemColors[i] = str[i];
                    }
                }
                if (StringUtils.isNotBlank(itemPriority)) {
                    String[] str = itemPriority.split(",");
                    itemPrioritys = new Integer[str.length];
                    for (int i = 0; i < str.length; i++) {
                        itemPrioritys[i] = Integer.valueOf(Integer.parseInt(str[i]));
                    }
                }
                standardType = this.manager.save(standardType);
                this.standardTypeMng.addStandard(standardType, itemNames,
                        itemColors, itemPrioritys);
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

    @RequestMapping({"/standardType/getStandard"})
    public void edit(Long id, HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                StandardType standardType = new StandardType();
                if (id.longValue() != 0L) {
                    standardType = this.manager.findById(id);
                }
                if (standardType != null) {
                    JSONArray jsonArray = new JSONArray();
                    JSONObject json = new JSONObject();
                    List list = this.standardMng.findByTypeId(id);
                    if ((list != null) && (list.size() > 0)) {
                        for (int i = 0; i < list.size(); i++) {
                            jsonArray.put(i, ((Standard) list.get(i)).convertToJson1());
                        }
                    }
                    json.put("standardType", standardType.convertToJson1());
                    json.put("standard", jsonArray);
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
    @RequestMapping({"/standardType/update"})
    public void update(StandardType standardType, String itemId, String itemName, String itemColor, String itemPriority, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{
                    standardType.getName()});
            if (!errors.hasErrors()) {
                Long[] itemIds = null;
                String[] itemNames = null;
                String[] itemColors = null;
                Integer[] itemPrioritys = null;
                if (StringUtils.isNotBlank(itemId)) {
                    String[] str = itemId.split(",");
                    itemIds = new Long[str.length];
                    for (int i = 0; i < str.length; i++) {
                        itemIds[i] = Long.valueOf(Long.parseLong(str[i]));
                    }
                }
                if (StringUtils.isNotBlank(itemName)) {
                    String[] str = itemName.split(",");
                    itemNames = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        itemNames[i] = str[i];
                    }
                }
                if (StringUtils.isNotBlank(itemColor)) {
                    String[] str = itemColor.split(",");
                    itemColors = new String[str.length];
                    for (int i = 0; i < str.length; i++) {
                        itemColors[i] = str[i];
                    }
                }
                if (StringUtils.isNotBlank(itemPriority)) {
                    String[] str = itemPriority.split(",");
                    itemPrioritys = new Integer[str.length];
                    for (int i = 0; i < str.length; i++) {
                        itemPrioritys[i] = Integer.valueOf(Integer.parseInt(str[i]));
                    }
                }
                standardType = this.manager.update(standardType);
                this.standardTypeMng.updateStandard(standardType, itemIds,
                        itemNames, itemColors, itemPrioritys);
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
    @RequestMapping({"/standardType/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids});
            if (!errors.hasErrors()) {
                if (!StringUtils.isNotBlank(ids)) {
                    code = 202;
                    message = "\"param error\"";
                } else {
                    Long[] id = null;
                    String[] str = ids.split(",");
                    id = new Long[str.length];
                    for (int i = 0; i < str.length; i++) {
                        id[i] = Long.valueOf(Long.parseLong(str[i]));
                    }
                    this.manager.deleteByIds(id);
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

    @SignValidate
    @RequestMapping({"/standardType/priority"})
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
                this.manager.updatePriority(id, prioritys);
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

