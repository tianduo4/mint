package com.jspgou.cms.action.directive;

import com.jspgou.cms.manager.ProductMng;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.web.freemarker.DirectiveUtils;
import com.jspgou.core.entity.Website;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductPageDirective extends ProductAbstractDirective {
    public static final String TPL_NAME = "ProductPage";

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Website web = getWeb(env, params, this.websiteMng);
        Integer ctgId = getCategoryId(params);
        Long tagId = getTagId(params);
        Pagination pagination = this.productMng.getPageForTag(web.getId(), ctgId,
                tagId, isRecommend(params), isSpecial(params), getPageNo(env),
                getCount(params));
        Map paramWrap = new HashMap(
                params);
        paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
                .wrap(pagination));
        Map origMap =
                DirectiveUtils.addParamsToVariable(env, paramWrap);
        if (isInvokeTpl(params))
            includeTpl("shop", "ProductPage", web, params, env);
        else {
            renderBody(env, loopVars, body);
        }
        DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
    }
}

