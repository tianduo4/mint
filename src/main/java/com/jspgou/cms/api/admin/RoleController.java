package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.api.ExceptionUtil;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.Role;
import com.jspgou.core.manager.RoleMng;
import com.jspgou.core.manager.UserMng;
import com.jspgou.core.web.WebErrors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoleController {

    @Autowired
    private UserMng userMng;

    @Autowired
    protected RoleMng roleMng;

    @RequestMapping({"/role/list"})
    public void list(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            List<Role> roles = this.roleMng.getList();
            JSONArray jsons = new JSONArray();
            for (Role role : roles) {
                jsons.add(role.converToJson());
            }
            body = jsons.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/role/save"})
    public void save(Role role, String perms, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{role.getName(), role.getPriority(), role.getSuper()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                this.roleMng.save(role, splitPerms(role.getSuper(), perms));
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/role/get"})
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
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
                Role role = new Role();
                if ((id != null) && (id.intValue() != 0)) {
                    role = this.roleMng.findById(id);
                }
                if (role != null) {
                    body = role.converToJson().toString();
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
    @RequestMapping({"/role/update"})
    public void update(Role role, String perms, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{role.getName(), role.getPriority(), role.getSuper()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else if (code == 200) {
                this.roleMng.update(role, splitPerms(role.getSuper(), perms));
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
    @RequestMapping({"/role/delete"})
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
                Integer[] id = new Integer[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Integer.valueOf(Integer.parseInt(str[i]));
                }
                this.roleMng.deleteByIds(id);
            }
        } catch (Exception e) {
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private Set<String> splitPerms(Boolean isSuper, String perms) {
        Set permSet = new HashSet();
        if ((!isSuper.booleanValue()) && (StringUtils.isNotBlank(perms))) {
            String[] strs = perms.split(",");
            for (String str : strs) {
                permSet.add(str);
            }
        }
        return permSet;
    }
}

