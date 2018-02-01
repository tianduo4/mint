package com.jspgou.plug.weixin.action.admin;

import com.jspgou.cms.service.WeixinSvc;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;
import com.jspgou.plug.weixin.entity.WeixinMenu;
import com.jspgou.plug.weixin.manager.WeixinMenuMng;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WeixinMenuAct {
    private static final Logger log = LoggerFactory.getLogger(WeixinMenuAct.class);

    @Autowired
    private WeixinMenuMng manager;

    @Autowired
    private WeixinSvc weixinSvc;

    @RequiresPermissions({"weixinMenu:v_list"})
    @RequestMapping({"/weixinMenu/v_list.do"})
    public String list(Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Website site = SiteUtils.getWeb(request);
        Pagination p = this.manager.getPage(site.getId(), parentId, SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        if (parentId != null) {
            WeixinMenu entity = this.manager.findById(parentId);
            if (entity.getParent() != null) {
                model.addAttribute("parentListId", entity.getParent().getId());
            }
        }
        model.addAttribute("pagination", p);
        model.addAttribute("parentId", parentId);
        model.addAttribute("pageNo", pageNo);
        return "weixinMenu/list";
    }

    @RequiresPermissions({"weixinMenu:v_add"})
    @RequestMapping({"/weixinMenu/v_add.do"})
    public String add(Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        model.addAttribute("parentId", parentId);
        model.addAttribute("pageNo", pageNo);
        return "weixinMenu/add";
    }

    @RequiresPermissions({"weixinMenu:v_edit"})
    @RequestMapping({"/weixinMenu/v_edit.do"})
    public String edit(Integer id, Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WeixinMenu entity = this.manager.findById(id);
        model.addAttribute("parentId", parentId);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("menu", entity);
        return "weixinMenu/edit";
    }

    @RequiresPermissions({"weixinMenu:o_save"})
    @RequestMapping({"/weixinMenu/o_save.do"})
    public String save(WeixinMenu bean, Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Website site = SiteUtils.getWeb(request);
        bean.setSite(site);
        if (parentId != null) {
            bean.setParent(this.manager.findById(parentId));
        }
        this.manager.save(bean);
        return list(parentId, pageNo, request, model);
    }

    @RequiresPermissions({"weixinMenu:o_update"})
    @RequestMapping({"/weixinMenu/o_update.do"})
    public String update(WeixinMenu bean, Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        this.manager.update(bean);
        return list(parentId, pageNo, request, model);
    }

    @RequiresPermissions({"weixinMenu:o_menu"})
    @RequestMapping({"/weixinMenu/o_menu.do"})
    public String menu(WeixinMenu bean, Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Website site = SiteUtils.getWeb(request);
        List menus = this.manager.getList(site.getId(), Integer.valueOf(100));
        this.weixinSvc.createMenu(getMenuJsonString(menus));
        return list(parentId, pageNo, request, model);
    }

    @RequiresPermissions({"weixinMenu:o_delete"})
    @RequestMapping({"/weixinMenu/o_delete.do"})
    public String delete(Integer[] ids, Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        WeixinMenu[] beans = this.manager.deleteByIds(ids);
        for (WeixinMenu bean : beans) {
            log.info("delete Brief id={}", bean.getId());
        }
        return list(parentId, pageNo, request, model);
    }

    private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifEmpty(ids, "ids")) {
            return errors;
        }
        for (Integer id : ids) {
            vldExist(id, errors);
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        WeixinMenu entity = this.manager.findById(id);

        return errors.ifNotExist(entity, WeixinMenu.class, id);
    }

    public String getMenuJsonString(List<WeixinMenu> menus) {
        String strJson = "{\"button\":[";

        for (int i = 0; i < menus.size(); i++) {
            strJson = strJson + "{\t";
            WeixinMenu menu = (WeixinMenu) menus.get(i);
            if (menu.getChild().size() > 0) {
                strJson = strJson +
                        "\"name\":\"" + menu.getName() + "\"," +
                        "\"sub_button\":[";
                Set sets = menu.getChild();
                Iterator iter = sets.iterator();
                int no = 1;
                while (iter.hasNext()) {
                    if (no > 5) {
                        break;
                    }
                    if (no == 1)
                        strJson = strJson + "{";
                    else {
                        strJson = strJson + ",{";
                    }
                    WeixinMenu child = (WeixinMenu) iter.next();
                    if (child.getType().equals("click"))
                        strJson = strJson +
                                "\"type\":\"click\"," +
                                "\"name\":\"" + child.getName() + "\"," +
                                "\"key\":\"" + child.getKey() + "\"}";
                    else {
                        strJson = strJson +
                                "\"type\":\"view\"," +
                                "\"name\":\"" + child.getName() + "\"," +
                                "\"url\":\"" + child.getUrl() + "\"}";
                    }
                    no++;
                }

                strJson = strJson + "]";
            } else if (menu.getType().equals("click")) {
                strJson = strJson +
                        "\"type\":\"click\"," +
                        "\"name\":\"" + menu.getName() + "\"," +
                        "\"key\":\"" + menu.getKey() + "\"";
            } else {
                strJson = strJson +
                        "\"type\":\"view\"," +
                        "\"name\":\"" + menu.getName() + "\"," +
                        "\"url\":\"" + menu.getUrl() + "\"";
            }
            if (i == menus.size() - 1)
                strJson = strJson + "}";
            else {
                strJson = strJson + "},";
            }
        }
        strJson = strJson + "]}";
        return strJson;
    }
}

