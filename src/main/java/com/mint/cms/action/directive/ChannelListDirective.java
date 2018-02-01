package com.mint.cms.action.directive;

import com.mint.cms.action.directive.abs.WebDirective;
import com.mint.cms.entity.ShopChannel;
import com.mint.cms.manager.ShopChannelMng;
import com.mint.common.web.freemarker.DirectiveUtils;
import com.mint.core.entity.Website;
import com.mint.core.manager.WebsiteMng;
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

public class ChannelListDirective extends WebDirective {
    public static final String TPL_NAME = "TopChannel";
    private ShopChannelMng shopChannelMng;
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
            ShopChannel channel = this.shopChannelMng.findById(parentId);
            if (channel != null)
                list = new ArrayList(channel.getChild());
            else
                list = new ArrayList();
        } else {
            list = this.shopChannelMng.getTopListForTag(web.getId(), Integer.valueOf(getCount(params)));
        }
        Map paramsWrap = new HashMap(
                params);
        paramsWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
        Map origMap =
                DirectiveUtils.addParamsToVariable(env, paramsWrap);
        if (isInvokeTpl(params))
            includeTpl("shop", "TopChannel", web, params, env);
        else {
            renderBody(env, loopVars, body);
        }
        DirectiveUtils.removeParamsFromVariable(env, paramsWrap, origMap);
    }

    private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        body.render(env.getOut());
    }

    @Autowired
    public void setShopChannelMng(ShopChannelMng shopChannelMng) {
        this.shopChannelMng = shopChannelMng;
    }

    public void setWebsiteMng(WebsiteMng websiteMng) {
        this.websiteMng = websiteMng;
    }
}

