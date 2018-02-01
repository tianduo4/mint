package com.jspgou.cms.action.admin.main;

import com.jspgou.core.entity.Role;
import com.jspgou.core.manager.LogMng;
import com.jspgou.core.manager.RoleMng;
import com.jspgou.core.security.CmsAuthorizingRealm;
import com.jspgou.core.web.WebErrors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoleAct {
    private static final Logger log = LoggerFactory.getLogger(RoleAct.class);

    @Autowired
    private RoleMng manager;

    @Autowired
    private LogMng cmsLogMng;

    @Autowired
    private CmsAuthorizingRealm authorizingRealm;

    @RequestMapping({"/role/v_list.do"})
    public String list(HttpServletRequest request, ModelMap model) {
        List list = this.manager.getList();
        model.addAttribute("list", list);
        return "role/list";
    }

    @RequestMapping({"/role/v_add.do"})
    public String add(ModelMap model) {
        return "role/add";
    }

    @RequestMapping({"/role/v_edit.do"})
    public String edit(Integer id, HttpServletRequest request, ModelMap model) {
        model.addAttribute("role", this.manager.findById(id));
        return "role/edit";
    }

    @RequestMapping({"/role/o_save.do"})
    public String save(Role bean, String[] perms, HttpServletRequest request, ModelMap model) {
        bean = this.manager.save(bean, splitPerms(perms));
        return "redirect:v_list.do";
    }

    @RequestMapping({"/role/o_update.do"})
    public String update(Role bean, String[] perms, boolean all, HttpServletRequest request, ModelMap model) {
        bean = this.manager.update(bean, splitPerms(perms));

        return list(request, model);
    }

    @RequestMapping({"/role/o_delete.do"})
    public String delete(Integer[] ids, HttpServletRequest request, ModelMap model) {
        Role[] beans = this.manager.deleteByIds(ids);
        return list(request, model);
    }

    private Set<String> splitPerms(String[] perms) {
        Set set = new HashSet();
        if (perms != null) {
            for (String perm : perms) {
                for (String p : StringUtils.split(perm, ',')) {
                    if (!StringUtils.isBlank(p)) {
                        set.add(p);
                    }
                }
            }
        }
        return set;
    }

    private boolean hasChangePermission(boolean all, String[] perms, Role bean) {
        Role role = this.manager.findById(bean.getId());

        return !bean.getPerms().toArray().equals(perms);
    }

    private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(id, errors)) {
            return errors;
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        Role entity = this.manager.findById(id);

        return errors.ifNotExist(entity, Role.class, id);
    }
}

