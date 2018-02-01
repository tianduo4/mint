package com.mint.cms.action.admin.main;

import com.mint.cms.entity.ShopChannel;
import com.mint.cms.manager.ProductMng;
import com.mint.cms.manager.ShopArticleMng;
import com.mint.cms.manager.ShopChannelMng;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

@Controller
public class ShopChannelAct
        implements ServletContextAware {
    private static final Logger log = LoggerFactory.getLogger(ShopChannelAct.class);

    @Autowired
    private ShopChannelMng manager;
    private ServletContext servletContext;

    @Autowired
    private ShopArticleMng shopArticleMng;

    @Autowired
    private ShopChannelMng shopChannelMng;

    @Autowired
    private ProductMng productMng;

    @RequestMapping({"/channel/v_left.do"})
    public String left() {
        return "channel/left";
    }

    @RequestMapping({"/channel/v_tree.do"})
    public String tree(String root, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        log.debug("tree path={}", root);
        boolean isRoot;
        if ((StringUtils.isBlank(root)) || ("source".equals(root)))
            isRoot = true;
        else {
            isRoot = false;
        }
        model.addAttribute("isRoot", Boolean.valueOf(isRoot));
        WebErrors errors = validateTree(root, request);
        if (errors.hasErrors()) {
            log.error((String) errors.getErrors().get(0));
            ResponseUtils.renderJson(response, "[]");
            return null;
        }
        List list;
        if (isRoot) {
            list = this.manager.getTopList(web.getId());
        } else {
            Integer rootId = Integer.valueOf(root);
            list = this.manager.getChildList(web.getId(), rootId);
        }
        model.addAttribute("list", list);
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=UTF-8");
        return "channel/tree";
    }

    @RequestMapping({"/channel/v_list.do"})
    public String list(Integer root, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        List list;
        if (root == null)
            list = this.manager.getTopList(web.getId());
        else {
            list = this.manager.getChildList(web.getId(), root);
        }
        model.addAttribute("root", root);
        model.addAttribute("list", list);
        return "channel/list";
    }

    @RequestMapping({"/channel/v_add.do"})
    public String add(Integer root, Integer type, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopChannel parent = null;
        if (root != null) {
            parent = this.manager.findById(root);
            model.addAttribute("parent", parent);
            model.addAttribute("root", root);
        }

        String tplChannelDirRel = ShopChannel.getChannelTplDirRel(web);
        String realChannelDir = this.servletContext.getRealPath(tplChannelDirRel);
        String relChannelPath = tplChannelDirRel.substring(web.getTemplateRel(request).length());
        String[] channelTpls = ShopChannel.getChannelTpls(type, realChannelDir, relChannelPath);

        String tplContentDirRel = ShopChannel.getContentTplDirRel(web);
        String realContentDir = this.servletContext.getRealPath(tplContentDirRel);
        String relContentPath = tplContentDirRel.substring(web.getTemplateRel(request).length());
        String[] contentTpls = ShopChannel.getContentTpls(type, realContentDir, relContentPath);

        model.addAttribute("channelTpls", channelTpls);
        model.addAttribute("contentTpls", contentTpls);
        model.addAttribute("type", type);
        return "channel/add";
    }

    @RequestMapping({"/channel/v_edit.do"})
    public String edit(Integer id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        ShopChannel shopChannel = this.manager.findById(id);
        Integer type = shopChannel.getType();

        String tplChannelDirRel = ShopChannel.getChannelTplDirRel(web);
        String realChannelDir = this.servletContext.getRealPath(tplChannelDirRel);
        String relChannelPath = tplChannelDirRel.substring(web.getTemplateRel(request).length());
        String[] channelTpls = ShopChannel.getChannelTpls(type, realChannelDir, relChannelPath);

        String tplContentDirRel = ShopChannel.getContentTplDirRel(web);
        String realContentDir = this.servletContext.getRealPath(tplContentDirRel);
        String relContentPath = tplContentDirRel.substring(web.getTemplateRel(request).length());
        String[] contentTpls = ShopChannel.getContentTpls(type, realContentDir, relContentPath);
        model.addAttribute("channelTpls", channelTpls);
        model.addAttribute("contentTpls", contentTpls);

        List parentList = this.manager.getListForParent(web.getId(),
                shopChannel.getId());
        model.addAttribute("parentList", parentList);
        model.addAttribute("shopChannel", shopChannel);
        return "channel/edit";
    }

    @RequestMapping({"/channel/o_save.do"})
    public String save(Integer root, ShopChannel bean, String content, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, root, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.save(bean, root, content);
        log.info("save ShopChannel id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/channel/o_update.do"})
    public String update(ShopChannel bean, Integer parentId, String content, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.update(bean, parentId, content);
        log.info("update ShopChannel id={}.", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/channel/o_delete.do"})
    public String delete(Integer[] ids, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        try {
            ShopChannel[] beans = this.manager.deleteByIds(ids);
            for (ShopChannel bean : beans)
                log.info("delete ShopChannel id={}", bean.getId());
        } catch (Exception e) {
            errors.addErrorString(this.productMng.getTipFile("Please.delete.the.corresponding.article.under.the.change.column"));
            return errors.showErrorPage(model);
        }
        ShopChannel[] beans;
        return "redirect:v_list.do";
    }

    @RequestMapping({"/channel/o_priority.do"})
    public String priority(Integer[] wids, Integer[] priority, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validatePriority(wids, priority, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        this.manager.updatePriority(wids, priority);
        model.addAttribute("message", "global.success");
        return list(null, request, model);
    }

    private WebErrors validatePriority(Integer[] wids, Integer[] priority, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifEmpty(wids, "wids")) {
            return errors;
        }
        if (errors.ifEmpty(priority, "priority")) {
            return errors;
        }
        if (wids.length != priority.length) {
            errors.addErrorString("wids length not equals priority length");
            return errors;
        }
        int i = 0;
        for (int len = wids.length; i < len; i++) {
            vldExist(wids[i], errors);
            if (priority[i] == null) {
                priority[i] = Integer.valueOf(0);
            }
        }
        return errors;
    }

    private void vldExist(Integer id, WebErrors errors) {
        if (errors.hasErrors()) {
            return;
        }
        ShopChannel entity = this.manager.findById(id);
        errors.ifNotExist(entity, ShopChannel.class, id);
    }

    private WebErrors validateSave(ShopChannel bean, Integer parentId, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        bean.setWebsite(web);
        if ((parentId != null) &&
                (vldExist(parentId, web.getId(), errors))) {
            return errors;
        }

        return errors;
    }

    private WebErrors validateEdit(Integer id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        if (vldExist(id, web.getId(), errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        if (vldExist(id, web.getId(), errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        errors.ifEmpty(ids, "ids");
        for (Integer id : ids) {
            vldExist(id, web.getId(), errors);
        }
        return errors;
    }

    private boolean vldExist(Integer id, Long webId, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        ShopChannel entity = this.manager.findById(id);
        if (errors.ifNotExist(entity, ShopChannel.class, id)) {
            return true;
        }
        if (!entity.getWebsite().getId().equals(webId)) {
            errors.notInWeb(ShopChannel.class, id);
            return true;
        }
        return false;
    }

    private WebErrors validateTree(String path, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);

        return errors;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

