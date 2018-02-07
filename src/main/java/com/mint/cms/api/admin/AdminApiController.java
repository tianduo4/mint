package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.entity.ApiAccount;
import com.mint.cms.entity.ShopAdmin;
import com.mint.cms.manager.ShopAdminMng;
import com.mint.cms.web.CmsThreadVariable;
import com.mint.cms.web.CmsUtils;
import com.mint.cms.web.SignValidate;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.security.encoder.PwdEncoder;
import com.mint.common.util.AES128Util;
import com.mint.common.web.ResponseUtils;
import com.mint.core.manager.GlobalMng;
import com.mint.core.manager.RoleMng;
import com.mint.core.manager.UserMng;
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
public class AdminApiController {

    @Autowired
    private ShopAdminMng shopAdminMng;

    @Autowired
    private UserMng userMng;

    @Autowired
    protected RoleMng roleMng;

    @Autowired
    private PwdEncoder pwdEncodee;

    @Autowired
    private GlobalMng globalMng;

    @RequestMapping({"/admin/list"})
    public void list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
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
                Pagination pagination = this.shopAdminMng.getPage(CmsThreadVariable.getSite().getId(), SimplePage.cpn(pageNo), pageSize.intValue());
                List<ShopAdmin> admins = (List<ShopAdmin>) pagination.getList();
                JSONArray jsons = new JSONArray();
                for (ShopAdmin admin : admins) {
                    jsons.add(admin.converToJson());
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
    @RequestMapping({"/admin/updpwd"})
    public void updatePwd(String oldPassword, String password, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{oldPassword, password});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                ApiAccount apiAccount = CmsThreadVariable.getApiAccount();

                ShopAdmin shopAdmin = CmsThreadVariable.getAdminUser();

                oldPassword = AES128Util.decrypt(oldPassword, apiAccount.getAesKey(), apiAccount.getIvKey());
                if (this.pwdEncodee.isPasswordValid(shopAdmin.getAdmin().getUser().getPassword(), oldPassword)) {
                    password = AES128Util.decrypt(password, apiAccount.getAesKey(), apiAccount.getIvKey());
                    this.userMng.updateUser(shopAdmin.getAdmin().getUser().getId(), password, null);
                } else {
                    code = 303;
                    message = "\"password error\"";
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
    @RequestMapping({"/admin/save"})
    public void save(ShopAdmin shopAdmin, String username, String password, Boolean viewonlyAdmin, String email, Boolean disabled, String roleIds, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{username, password, viewonlyAdmin, email, disabled});

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
                    Long siteId = CmsUtils.getWebsiteId(request);
                    Integer[] ids = null;
                    if (StringUtils.isNotBlank(roleIds)) {
                        String[] str = roleIds.split(",");
                        ids = new Integer[str.length];
                        for (int i = 0; i < str.length; i++) {
                            ids[i] = Integer.valueOf(Integer.parseInt(str[i]));
                        }
                    }
                    this.shopAdminMng.register(username, password, viewonlyAdmin, email, request.getRemoteAddr(), disabled, siteId, ids, shopAdmin);
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

    @RequestMapping({"/admin/get"})
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
                ShopAdmin admin = new ShopAdmin();
                if ((id != null) && (id.longValue() != 0L)) {
                    admin = this.shopAdminMng.findById(id);
                }
                if (admin != null) {
                    body = admin.converToJson().toString();
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
    @RequestMapping({"/admin/update"})
    public void update(ShopAdmin shopAdmin, String password, Boolean viewonlyAdmin, String email, Boolean disabled, String roleIds, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{viewonlyAdmin, email, disabled, shopAdmin.getId()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                if (StringUtils.isNotBlank(password)) {
                    ApiAccount apiAccount = CmsThreadVariable.getApiAccount();

                    password = AES128Util.decrypt(password, apiAccount.getAesKey(), apiAccount.getIvKey());
                }
                ShopAdmin entity = this.shopAdminMng.findById(shopAdmin.getId());

                if (!entity.getEmail().equals(email)) {
                    if (this.userMng.emailExist(email)) {
                        code = 301;
                        message = "\"email exist\"";
                    }
                }
                if (code == 200) {
                    Integer[] ids = null;
                    if (StringUtils.isNotBlank(roleIds)) {
                        String[] str = roleIds.split(",");
                        ids = new Integer[str.length];
                        for (int i = 0; i < str.length; i++) {
                            ids[i] = Integer.valueOf(Integer.parseInt(str[i]));
                        }
                    }
                    this.shopAdminMng.update(password, disabled, email, viewonlyAdmin, shopAdmin, ids);
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
    @RequestMapping({"/admin/delete"})
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
                this.shopAdminMng.delete(id);
            }
        } catch (Exception e) {
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/admin/updatePassword"})
    public void updatePassword(String oldPassword, String password, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{oldPassword, password});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                ApiAccount apiAccount = CmsThreadVariable.getApiAccount();

                oldPassword = AES128Util.decrypt(oldPassword, apiAccount.getAesKey(), apiAccount.getIvKey());
                if (this.pwdEncodee.isPasswordValid(this.globalMng.get().getPassword(), oldPassword)) {
                    password = AES128Util.decrypt(password, apiAccount.getAesKey(), apiAccount.getIvKey());
                    this.globalMng.updateGlobalPwd(Long.valueOf(1L), password);
                } else {
                    code = 303;
                    message = "\"password error\"";
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
    @RequestMapping({"/admin/checkPassword"})
    public void checkPassword(String oldPassword, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{oldPassword});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                ApiAccount apiAccount = CmsThreadVariable.getApiAccount();

                oldPassword = AES128Util.decrypt(oldPassword, apiAccount.getAesKey(), apiAccount.getIvKey());
                if (!this.pwdEncodee.isPasswordValid(this.globalMng.get().getPassword(), oldPassword)) {
                    code = 303;
                    message = "\"password error\"";
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
}

