package com.jspgou.cms.action.directive;

import com.jspgou.cms.action.directive.abs.WebDirective;
import com.jspgou.cms.entity.Category;
import com.jspgou.cms.manager.CategoryMng;
import com.jspgou.common.web.freemarker.DirectiveUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.WebsiteMng;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class CategoryListDirective extends WebDirective {
    public static final String TPL_NAME = "category_list";
    private CategoryMng categoryMng;
    private WebsiteMng websiteMng;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Long webId = getWebId(params);
        Website web;
        if (webId == null)
            web = getWeb(env, params, this.websiteMng);
        else {
            web = this.websiteMng.findById(webId);
        }
        if (web == null) {
            throw new TemplateModelException("webId=" + webId + " not exist!");
        }
        Integer parentId = DirectiveUtils.getInt("parentId", params);
        List list;
        if (parentId != null) {
            Category category = this.categoryMng.findById(parentId);
            if (category != null)
                list = new ArrayList(category.getChild());
            else
                list = new ArrayList();
        } else {
            list = this.categoryMng.getTopListForTag(web.getId());
        }

        Map paramsWrap = new HashMap(
                params);
        paramsWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
        Map origMap =
                DirectiveUtils.addParamsToVariable(env, paramsWrap);
        if (isInvokeTpl(params))
            includeTpl("tag", "category_list", web, params, env);
        else {
            renderBody(env, loopVars, body);
        }
        DirectiveUtils.removeParamsFromVariable(env, paramsWrap, origMap);
    }

    private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        body.render(env.getOut());
    }

    @Autowired
    public void setCategoryMng(CategoryMng categoryMng) {
        this.categoryMng = categoryMng;
    }

    @Autowired
    public void setWebsiteMng(WebsiteMng websiteMng) {
        this.websiteMng = websiteMng;
    }
}

