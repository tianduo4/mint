package com.jspgou.cms.action.directive;

import com.jspgou.cms.action.directive.abs.WebDirective;
import com.jspgou.cms.manager.ShopArticleMng;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.web.freemarker.DirectiveUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.WebsiteMng;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class ArticlePageDirective extends WebDirective {
    public static final String TPL_NAME = "ArticlePage";
    public static final String PARAM_CHANNEL_ID = "channelId";
    private ShopArticleMng shopArticleMng;
    private WebsiteMng websiteMng;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Website web = getWeb(env, params, this.websiteMng);
        Long channelId = getChannelId(params);
        Pagination pagination = this.shopArticleMng.getPageForTag(web.getId(),
                channelId, getPageNo(env), getCount(params));
        Map paramWrap = new HashMap(
                params);
        paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
                .wrap(pagination));
        Map origMap =
                DirectiveUtils.addParamsToVariable(env, paramWrap);
        if (isInvokeTpl(params))
            includeTpl("shop", "ArticlePage", web, params, env);
        else {
            renderBody(env, loopVars, body);
        }
        DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
    }

    private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        body.render(env.getOut());
    }

    private Long getChannelId(Map<String, TemplateModel> params) throws TemplateException {
        TemplateModel parentId = (TemplateModel) params.get("channelId");
        if (parentId == null) {
            return null;
        }
        if ((parentId instanceof TemplateNumberModel)) {
            return Long.valueOf(((TemplateNumberModel) parentId).getAsNumber().longValue());
        }
        throw new TemplateModelException("The 'channelId' parameter must be a number.");
    }

    @Autowired
    public void setShopArticleMng(ShopArticleMng shopArticleMng) {
        this.shopArticleMng = shopArticleMng;
    }

    @Autowired
    public void setWebsiteMng(WebsiteMng websiteMng) {
        this.websiteMng = websiteMng;
    }
}

