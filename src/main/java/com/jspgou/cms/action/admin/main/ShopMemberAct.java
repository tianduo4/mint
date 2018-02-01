package com.jspgou.cms.action.admin.main;

import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.ShopDictionaryMng;
import com.jspgou.cms.manager.ShopMemberGroupMng;
import com.jspgou.cms.manager.ShopMemberMng;
import com.jspgou.cms.manager.WebserviceMng;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.UserMng;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopMemberAct {
    private static final Logger log = LoggerFactory.getLogger(ShopMemberAct.class);

    @Autowired
    private ShopMemberGroupMng shopMemberGroupMng;

    @Autowired
    private UserMng userMng;

    @Autowired
    private ShopMemberMng manager;

    @Autowired
    private ShopDictionaryMng shopDictionaryMng;

    @Autowired
    private WebserviceMng webserviceMng;

    @RequestMapping({"/member/v_list.do"})
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Pagination pagination = this.manager.getPage(SiteUtils.getWebId(request),
                SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        return "member/list";
    }

    @RequestMapping({"/member/v_add.do"})
    public String add(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        List groupList = this.shopMemberGroupMng.getList(
                SiteUtils.getWebId(request));

        List userDegreeList = this.shopDictionaryMng.getListByType(Long.valueOf(1L));

        List familyMembersList = this.shopDictionaryMng.getListByType(Long.valueOf(2L));

        List incomeDescList = this.shopDictionaryMng.getListByType(Long.valueOf(3L));

        List workSeniorityList = this.shopDictionaryMng.getListByType(Long.valueOf(4L));

        List degreeList = this.shopDictionaryMng.getListByType(Long.valueOf(5L));

        model.addAttribute("groupList", groupList);
        model.addAttribute("userDegreeList", userDegreeList);
        model.addAttribute("familyMembersList", familyMembersList);
        model.addAttribute("incomeDescList", incomeDescList);
        model.addAttribute("workSeniorityList", workSeniorityList);
        model.addAttribute("degreeList", degreeList);
        return "member/add";
    }

    @RequestMapping({"/member/o_save.do"})
    public String save(ShopMember bean, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, String username, String password, String email, Boolean disabled, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, username, email, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }

        bean = this.manager.register(username, password, email, Boolean.valueOf(true), null, request
                        .getRemoteAddr(), disabled, SiteUtils.getWebId(request),
                groupId, userDegreeId, degreeId, incomeDescId, workSeniorityId,
                familyMembersId, bean);

        this.webserviceMng.callWebService("false", username, password, email, bean, "addUser");
        log.info("save ShopMember, id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/member/v_edit.do"})
    public String edit(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        List groupList = this.shopMemberGroupMng.getList(
                SiteUtils.getWebId(request));

        List userDegreeList = this.shopDictionaryMng.getListByType(Long.valueOf(1L));

        List familyMembersList = this.shopDictionaryMng.getListByType(Long.valueOf(2L));

        List incomeDescList = this.shopDictionaryMng.getListByType(Long.valueOf(3L));

        List workSeniorityList = this.shopDictionaryMng.getListByType(Long.valueOf(4L));

        List degreeList = this.shopDictionaryMng.getListByType(Long.valueOf(5L));

        ShopMember member = this.manager.findById(id);
        model.addAttribute("groupList", groupList);
        model.addAttribute("member", member);
        model.addAttribute("groupList", groupList);
        model.addAttribute("userDegreeList", userDegreeList);
        model.addAttribute("familyMembersList", familyMembersList);
        model.addAttribute("incomeDescList", incomeDescList);
        model.addAttribute("workSeniorityList", workSeniorityList);
        model.addAttribute("degreeList", degreeList);
        return "member/edit";
    }

    @RequestMapping({"/member/o_update.do"})
    public String update(ShopMember bean, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, String password, String email, Boolean disabled, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), email, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.update(bean, groupId, userDegreeId,
                degreeId, incomeDescId, workSeniorityId, familyMembersId,
                password, email, disabled);

        this.webserviceMng.callWebService("false", bean.getUsername(), password, email, bean, "updateUser");
        log.info("update ShopMember, id={}.", bean.getId());
        return list(pageNo, request, model);
    }

    @RequestMapping({"/member/o_delete.do"})
    public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        ShopMember[] beans = this.manager.deleteByIds(ids);
        for (ShopMember bean : beans) {
            Map paramsValues = new HashMap();
            paramsValues.put("username", bean.getUsername());
            paramsValues.put("admin", "true");
            this.webserviceMng.callWebService("deleteUser", paramsValues);
            log.info("delete ShopMember, id={}", bean.getId());
        }
        return list(pageNo, request, model);
    }

    private WebErrors validateSave(ShopMember bean, String username, String email, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        bean.setWebsite(web);
        if (vldUsername(username, errors)) {
            return errors;
        }
        if (vldEmail(email, errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateEdit(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        if (vldExist(id, web.getId(), errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateUpdate(Long id, String email, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        if (vldEmailUpdate(id, email, errors)) {
            return errors;
        }
        if (vldExist(id, web.getId(), errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        errors.ifEmpty(ids, "ids");
        for (Long id : ids) {
            vldExist(id, web.getId(), errors);
        }
        return errors;
    }

    private boolean vldExist(Long id, Long webId, WebErrors errors) {
        if (errors.hasErrors()) {
            return true;
        }
        if (errors.ifNull(id, "id")) {
            return true;
        }
        ShopMember entity = this.manager.findById(id);
        if (errors.ifNotExist(entity, ShopMember.class, id)) {
            return true;
        }
        if (!entity.getWebsite().getId().equals(webId)) {
            errors.notInWeb(ShopMember.class, id);
            return true;
        }
        return false;
    }

    private boolean vldEmailUpdate(Long id, String email, WebErrors errors) {
        if (!StringUtils.isBlank(email)) {
            ShopMember member = this.manager.findById(id);
            if ((member.getEmail() != null) && (!member.getEmail().equals(email)) &&
                    (vldEmail(email, errors))) {
                return true;
            }
        }

        return false;
    }

    private boolean vldEmail(String email, WebErrors errors) {
        if (errors.ifNotEmail(email, "email", 100)) {
            return true;
        }
        if (this.userMng.emailExist(email)) {
            errors.addErrorCode("error.emailExist");
            return true;
        }
        return false;
    }

    private boolean vldUsername(String username, WebErrors errors) {
        if (errors.ifNotUsername(username, "username", 3, 100)) {
            return true;
        }
        if (this.userMng.usernameExist(username)) {
            errors.addErrorCode("error.usernameExist");
            return true;
        }
        return false;
    }
}

