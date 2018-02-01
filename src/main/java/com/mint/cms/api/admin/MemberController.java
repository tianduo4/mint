package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.entity.ApiAccount;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.OrderMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.cms.manager.WebserviceMng;
import com.mint.cms.web.CmsThreadVariable;
import com.mint.cms.web.SignValidate;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.security.encoder.PwdEncoder;
import com.mint.common.util.AES128Util;
import com.mint.common.web.ResponseUtils;
import com.mint.core.manager.UserMng;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private UserMng userMng;

    @Autowired
    private WebserviceMng webserviceMng;

    @Autowired
    private PwdEncoder pwdEncodee;

    @Autowired
    private OrderMng orderMng;

    @RequestMapping({"/member/list"})
    public void list(String username, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
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
                Pagination pagination = this.shopMemberMng.getPage(CmsThreadVariable.getSite().getId(),
                        SimplePage.cpn(pageNo), pageSize.intValue(), username);
                List<ShopMember> members = (List<ShopMember>) pagination.getList();
                JSONArray jsons = new JSONArray();
                for (ShopMember member : members) {
                    jsons.add(member.converToJson());
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
    @RequestMapping({"/member/save"})
    public void save(ShopMember member, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, String username, String password, String email, Boolean disabled, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{username, email, password});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                ApiAccount apiAccount = CmsThreadVariable.getApiAccount();

                password = AES128Util.decrypt(password, apiAccount.getAesKey(), apiAccount.getIvKey());

                if (this.userMng.usernameExist(username)) {
                    code = 304;
                    message = "\"username exist\"";
                } else if (this.userMng.emailExist(email)) {
                    code = 301;
                    message = "\"email exist\"";
                } else {
                    member = this.shopMemberMng.register(username, password, email, Boolean.valueOf(true), null, request
                                    .getRemoteAddr(), disabled, SiteUtils.getWebId(request),
                            groupId, userDegreeId, degreeId, incomeDescId, workSeniorityId,
                            familyMembersId, member);

                    this.webserviceMng.callWebService("false", username, password, email, member, "addUser");
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

    @RequestMapping({"/member/get"})
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
                ShopMember member = new ShopMember();
                if ((id != null) && (id.longValue() != 0L)) {
                    member = this.shopMemberMng.findById(id);
                }
                if (member != null) {
                    body = member.converToJson().toString();
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
    @RequestMapping({"/member/update"})
    public void update(ShopMember member, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, String password, String email, Boolean disabled, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{email, disabled});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                if (StringUtils.isNotBlank(password)) {
                    ApiAccount apiAccount = CmsThreadVariable.getApiAccount();

                    password = AES128Util.decrypt(password, apiAccount.getAesKey(), apiAccount.getIvKey());
                }
                ShopMember entity = this.shopMemberMng.findById(member.getId());

                if (!entity.getEmail().equals(email)) {
                    if (this.userMng.emailExist(email)) {
                        code = 301;
                        message = "\"email exist\"";
                    }
                }
                if (code == 200) {
                    member = this.shopMemberMng.update(member, groupId, userDegreeId,
                            degreeId, incomeDescId, workSeniorityId, familyMembersId,
                            password, email, disabled);

                    this.webserviceMng.callWebService("false", member.getUsername(), password, email, member, "updateUser");
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
    @RequestMapping({"/member/delete"})
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
                List<Object[]> orders = this.orderMng.findListByIds(id);
                if ((orders != null) && (orders.size() > 0)) {
                    code = 205;
                    message = "\"delete error\"";
                    String fail = "";
                    for (Object[] obj : orders) {
                        fail = fail + "," + obj[0];
                    }
                    body = "\"\",\"fail\":\"您所选择的删除用户 [" + fail.substring(1) + "] 已产生订单，无法删除，请确认后操作!\"";
                } else {
                    ShopMember[] beans = this.shopMemberMng.deleteByIds(id);
                    for (ShopMember bean : beans) {
                        Map paramsValues = new HashMap();
                        paramsValues.put("username", bean.getUsername());
                        paramsValues.put("admin", "true");
                        this.webserviceMng.callWebService("deleteUser", paramsValues);
                    }
                }
            }
        } catch (Exception e) {
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

