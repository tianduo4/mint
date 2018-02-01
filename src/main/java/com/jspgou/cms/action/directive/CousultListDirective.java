package com.jspgou.cms.action.directive;

import com.jspgou.cms.action.directive.abs.WebDirective;
import com.jspgou.cms.manager.ConsultMng;
import com.jspgou.common.page.Pagination;
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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class CousultListDirective extends WebDirective {
    public static final String TPL_NAME = "CousultList";

    @Autowired
    private WebsiteMng websiteMng;

    @Autowired
    private ConsultMng consultMng;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Website web = getWeb(env, params, this.websiteMng);
        Integer count = Integer.valueOf(getCount(params));
        Long productId = getLong("productId", params);
        Pagination pagination = this.consultMng.getPage(productId, null, null, null,
                null, getPageNo(env), count.intValue(), Boolean.valueOf(true));
        Map paramWrap = new HashMap(
                params);
        paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
                .wrap(pagination));
        paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination.getList()));
        Map origMap =
                DirectiveUtils.addParamsToVariable(env, paramWrap);
        if (isInvokeTpl(params))
            includeTpl("shop", "CousultList", web, params, env);
        else {
            renderBody(env, loopVars, body);
        }
        DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
    }

    private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        body.render(env.getOut());
    }
}

