package com.mint.cms.action.directive;

import com.mint.cms.action.directive.abs.WebDirective;
import com.mint.cms.manager.DiscussMng;
import com.mint.common.page.Pagination;
import com.mint.common.web.freemarker.DirectiveUtils;
import com.mint.core.entity.Website;
import com.mint.core.manager.WebsiteMng;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class DiscussPageDirective extends WebDirective {
    public static final String TPL_NAME = "DiscussPage";

    @Autowired
    private DiscussMng discussMng;
    private WebsiteMng websiteMng;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Website web = getWeb(env, params, this.websiteMng);
        Integer count = Integer.valueOf(getCount(params));
        Long productId = getLong("productId", params);

        Long memberId = getLong("memberId", params);
        String discussType = getString("discussType", params);
        Pagination pagination = this.discussMng.getPage(memberId, productId, discussType, null, null, null, null, getPageNo(env), count.intValue(), true);

        Map paramWrap = new HashMap(
                params);
        paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
                .wrap(pagination));
        paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination.getList()));
        Map origMap =
                DirectiveUtils.addParamsToVariable(env, paramWrap);
        if (isInvokeTpl(params))
            includeTpl("shop", "DiscussPage", web, params, env);
        else {
            renderBody(env, loopVars, body);
        }
        DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
    }

    private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        body.render(env.getOut());
    }

    @Autowired
    public void setWebsiteMng(WebsiteMng websiteMng) {
        this.websiteMng = websiteMng;
    }
}

