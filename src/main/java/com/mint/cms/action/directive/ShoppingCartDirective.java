package com.mint.cms.action.directive;

import com.mint.cms.action.directive.abs.WebDirective;
import com.mint.common.web.freemarker.DirectiveUtils;
import com.mint.core.entity.Website;
import com.mint.core.manager.WebsiteMng;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.RequestContext;

public class ShoppingCartDirective extends WebDirective {
    private WebsiteMng websiteMng;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Website web = getWeb(env, params, this.websiteMng);
        RequestContext ctx = getContext(env);
        Map origMap =
                DirectiveUtils.addParamsToVariable(env, params);
        includeTpl(web, ctx, env, null);
        DirectiveUtils.removeParamsFromVariable(env, params, origMap);
    }

    private void includeTpl(Website web, RequestContext ctx, Environment env, HttpServletRequest request) throws IOException, TemplateException {
        String tpl = web
                .getTplSys("shop", ctx.getMessage("tpl.shoppingCart"), request);
        env.include(tpl, "UTF-8", true);
    }

    @Autowired
    public void setWebsiteMng(WebsiteMng websiteMng) {
        this.websiteMng = websiteMng;
    }
}

