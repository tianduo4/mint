package com.mint.cms.action.admin.main;

import com.mint.cms.entity.ShopArticle;
import com.mint.cms.entity.ShopChannel;
import com.mint.cms.manager.ShopArticleMng;
import com.mint.cms.manager.ShopChannelMng;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.CookieUtils;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopArticleAct {
    private static final Logger log = LoggerFactory.getLogger(ShopArticleAct.class);

    @Autowired
    private ShopChannelMng channelMng;

    @Autowired
    private ShopArticleMng manager;

    @RequestMapping({"/article/v_left.do"})
    public String left() {
        return "article/left";
    }

    @RequestMapping({"/article/v_tree.do"})
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
            list = this.channelMng.getTopList(web.getId());
        } else {
            Integer rootId = Integer.valueOf(root);
            list = this.channelMng.getChildList(web.getId(), rootId);
        }
        model.addAttribute("list", list);
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=UTF-8");
        return "article/tree";
    }

    @RequestMapping({"/article/v_list.do"})
    public String list(Integer cid, Integer pageNo, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        Pagination pagination = this.manager.getPage(cid, web.getId(), SimplePage.cpn(pageNo),
                CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        if (cid != null) {
            ShopChannel channel = this.channelMng.findById(cid);
            model.addAttribute("channel", channel);
        }
        model.addAttribute("cid", cid);
        return "article/list";
    }

    @RequestMapping({"/article/v_add.do"})
    public String add(Long cid, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        List channelList = this.channelMng.getArticleList(web
                .getId());
        model.addAttribute("channelList", channelList);
        if (cid != null) {
            model.addAttribute("cid", cid);
        }
        return "article/add";
    }

    @RequestMapping({"/article/v_edit.do"})
    public String edit(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        List articleChannelList = this.channelMng.getArticleList(web
                .getId());
        model.addAttribute("articleChannelList", articleChannelList);
        model.addAttribute("shopArticle", this.manager.findById(id));
        return "article/edit";
    }

    @RequestMapping({"/article/o_save.do"})
    public String save(ShopArticle bean, Integer channelId, String content, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, channelId, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.save(bean, channelId, content);
        log.info("save ShopArticle id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/article/o_update.do"})
    public String update(ShopArticle bean, Integer channelId, String content, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), channelId, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.update(bean, channelId, content);
        log.info("update ShopArticle id={}.", bean.getId());
        return list(null, pageNo, request, model);
    }

    @RequestMapping({"/article/o_delete.do"})
    public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        ShopArticle[] beans = this.manager.deleteByIds(ids);
        for (ShopArticle bean : beans) {
            log.info("delete ShopArticle id={}", bean.getId());
        }
        return list(null, pageNo, request, model);
    }

    private WebErrors validateSave(ShopArticle bean, Integer channelId, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        bean.setWebsite(web);
        if (errors.ifNull(channelId, "channelId")) {
            return errors;
        }
        ShopChannel channel = this.channelMng.findById(channelId);
        if (!channel.getWebsite().getId().equals(web.getId())) {
            errors.notInWeb(ShopChannel.class, channelId);
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

    private WebErrors validateUpdate(Long id, Integer channelId, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        if (vldExist(id, web.getId(), errors)) {
            return errors;
        }
        ShopChannel channel = this.channelMng.findById(channelId);
        if (!channel.getWebsite().getId().equals(web.getId())) {
            errors.notInWeb(ShopChannel.class, channelId);
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
        if (errors.ifNull(id, "id")) {
            return true;
        }
        ShopArticle entity = this.manager.findById(id);
        if (errors.ifNotExist(entity, ShopArticle.class, id)) {
            return true;
        }
        if (!entity.getWebsite().getId().equals(webId)) {
            errors.notInWeb(ShopArticle.class, id);
            return true;
        }
        return false;
    }

    private WebErrors validateTree(String path, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);

        return errors;
    }
}

