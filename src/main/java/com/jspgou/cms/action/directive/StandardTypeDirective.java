package com.jspgou.cms.action.directive;

import com.jspgou.cms.action.directive.abs.WebDirective;
import com.jspgou.cms.manager.StandardTypeMng;
import com.jspgou.common.web.freemarker.DirectiveUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.WebsiteMng;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class StandardTypeDirective extends WebDirective {
    public static final String TPL_NAME = "StandardType";

    @Autowired
    private WebsiteMng websiteMng;

    @Autowired
    private StandardTypeMng standardTypeMng;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Website web = getWeb(env, params, this.websiteMng);
        Integer categoryId = getInt("categoryId", params);
        List standardTypeList = this.standardTypeMng
                .getList(categoryId);
        Map paramWrap = new HashMap(
                params);
        paramWrap.put("tag_list",
                ObjectWrapper.DEFAULT_WRAPPER.wrap(standardTypeList));
        Map origMap =
                DirectiveUtils.addParamsToVariable(env, paramWrap);
        if (isInvokeTpl(params))
            includeTpl("shop", "StandardType", web, params, env);
        else {
            renderBody(env, loopVars, body);
        }
        DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
    }

    private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        body.render(env.getOut());
    }
}

