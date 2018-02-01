package com.mint.plug.weixin.action.directive;

import com.mint.cms.web.FrontUtils;
import com.mint.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
import com.mint.common.web.freemarker.DirectiveUtils;
import com.mint.core.entity.Website;
import com.mint.plug.weixin.entity.Weixin;
import com.mint.plug.weixin.manager.WeixinMng;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class WeixinDirective
        implements TemplateDirectiveModel {

    @Autowired
    private WeixinMng manager;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Website site = FrontUtils.getSite(env);
        Weixin entity = this.manager.find(site.getId());

        Map paramWrap = new HashMap(
                params);
        paramWrap.put("tag_bean", DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(entity));
        Map origMap =
                DirectiveUtils.addParamsToVariable(env, paramWrap);
        body.render(env.getOut());
        DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
    }
}

