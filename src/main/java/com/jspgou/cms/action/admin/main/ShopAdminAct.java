package com.jspgou.cms.action.admin.main;

import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.ShopAdminMng;
import com.jspgou.cms.manager.WebserviceMng;
import com.jspgou.cms.web.threadvariable.AdminThread;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.security.encoder.PwdEncoder;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.Admin;
import com.jspgou.core.entity.User;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.AdminMng;
import com.jspgou.core.manager.RoleMng;
import com.jspgou.core.manager.UserMng;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopAdminAct {
    private static final Logger log = LoggerFactory.getLogger(ShopAdminAct.class);

    @Autowired
    private ShopAdminMng manager;

    @Autowired
    private UserMng userMng;

    @Autowired
    protected RoleMng roleMng;

    @Autowired
    protected AdminMng adminMng;

    @Autowired
    private WebserviceMng webserviceMng;

    @Autowired
    private PwdEncoder pwdEncoder;

    @RequestMapping({"/admin/v_list.do"})
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Pagination pagination = this.manager.getPage(SiteUtils.getWebId(request), SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute(pagination);
        return "admin/list";
    }

    @RequestMapping({"/admin/v_add.do"})
    public String add(ModelMap model) {
        List roleList = this.roleMng.getList();
        model.addAttribute("roleList", roleList);
        return "admin/add";
    }

    @RequestMapping({"/admin/v_edit.do"})
    public String edit(Long id, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        List roleList = this.roleMng.getList();
        model.addAttribute("roleList", roleList);
        model.addAttribute("admin", this.manager.findById(id));
        model.addAttribute("roleIds", this.manager.findById(id).getAdmin().getRoleIds());
        return "admin/edit";
    }

    @RequestMapping({"/admin/o_save.do"})
    public String save(ShopAdmin bean, String username, String password, Boolean viewonlyAdmin, String email, Boolean disabled, Integer[] roleIds, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Website web = SiteUtils.getWeb(request);
        bean = this.manager.register(username, password, viewonlyAdmin, email, request.getRemoteAddr(), disabled.booleanValue(), web.getId(), bean);

        this.webserviceMng.callWebService("true", username, password, email, null, "addUser");
        this.adminMng.addRole(bean.getAdmin(), roleIds);
        log.info("save ShopAdmin id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/admin/o_update.do"})
    public String update(ShopAdmin bean, String password, Boolean viewonlyAdmin, String email, Boolean disabled, Integer[] roleIds, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.update(bean, password, disabled, email, viewonlyAdmin);

        ShopMember member = new ShopMember();
        member.setRealName(bean.getFirstname());
        this.webserviceMng.callWebService("true", bean.getUsername(), password, email, member, "updateUser");
        this.adminMng.updateRole(bean.getAdmin(), roleIds);
        log.info("update ShopAdmin id={}.", bean.getId());
        return list(pageNo, request, model);
    }

    @RequestMapping({"/admin/o_delete.do"})
    public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        ShopAdmin[] beans = this.manager.deleteByIds(ids);
        for (ShopAdmin bean : beans) {
            Map paramsValues = new HashMap();
            paramsValues.put("username", bean.getUsername());
            paramsValues.put("admin", "true");
            this.webserviceMng.callWebService("deleteUser", paramsValues);
            log.info("delete ShopAdmin id={}", bean.getId());
        }
        return list(pageNo, request, model);
    }

    @RequestMapping({"/admin/v_check_username.do"})
    public String checkUsername(String username, HttpServletRequest request, HttpServletResponse response) {
        if ((StringUtils.isBlank(username)) || (this.userMng.usernameExist(username)))
            ResponseUtils.renderJson(response, "false");
        else {
            ResponseUtils.renderJson(response, "true");
        }
        return null;
    }

    @RequestMapping({"/admin/v_check_email.do"})
    public String checkEmail(String email, HttpServletRequest request, HttpServletResponse response) {
        if ((StringUtils.isBlank(email)) || (this.userMng.emailExist(email)))
            ResponseUtils.renderJson(response, "false");
        else {
            ResponseUtils.renderJson(response, "true");
        }
        return null;
    }

    @RequestMapping({"/admin/v_editPwd.do"})
    public String editPwd(HttpServletRequest request, ModelMap model) {
        ShopAdmin admin = AdminThread.get();
        Long id = admin.getId();
        model.addAttribute("admin", this.manager.findById(id));
        return "admin/password";
    }

    @RequestMapping({"/admin/ajax_update.do"})
    public void ajaxupdate(String pwd, ShopAdmin bean, String password, Boolean disabled, Integer pageNo, Long[] roleIds, HttpServletRequest request, ModelMap model, HttpServletResponse response) throws JSONException {
        ShopAdmin admin = AdminThread.get();
        User user = admin.getAdmin().getUser();
        JSONObject json = new JSONObject();
        if (!this.pwdEncoder.isPasswordValid(user.getPassword(), pwd)) {
            json.put("success", false);
            json.put("status", 1);
            ResponseUtils.renderJson(response, json.toString());
            return;
        }
        bean = this.manager.update(bean, password, disabled, null, null);
        log.info("update ShopAdmin id={}.", bean.getId());
        json.put("success", true);
        json.put("status", 0);
        ResponseUtils.renderJson(response, json.toString());
    }

    @RequestMapping({"/admin/success.do"})
    public String success(HttpServletRequest request, ModelMap model) {
        return "admin/success";
    }

    private WebErrors validateSave(ShopAdmin bean, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        return errors;
    }

    private WebErrors validateEdit(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors.ifNull(id, "id");
        vldExist(id, errors);
        return errors;
    }

    private WebErrors validateUpdate(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(id, errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors.ifEmpty(ids, "ids");
        for (Long id : ids) {
            vldExist(id, errors);
        }
        return errors;
    }

    private boolean vldExist(Long id, WebErrors errors) {
        ShopAdmin entity = this.manager.findById(id);
        return errors.ifNotExist(entity, ShopAdmin.class, id);
    }

    private Set<String> handleperms(String[] perms) {
        Set permSet = new HashSet();

        for (String perm : perms) {
            String[] arr = perm.split("@");
            int i = 0;
            for (int len = arr.length; i < len; i++) {
                permSet.add(arr[i]);
            }
        }
        return permSet;
    }
}

