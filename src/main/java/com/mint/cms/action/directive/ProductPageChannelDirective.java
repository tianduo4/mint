package com.mint.cms.action.directive;

import com.mint.common.page.Pagination;
import com.mint.common.web.freemarker.DirectiveUtils;
import com.mint.core.entity.Website;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ProductPageChannelDirective extends ProductAbstractDirective {
    public static final String TPL_NAME = "ProductPage";

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Website web = getWeb(env, params, this.websiteMng);
        Integer ctgId = getCategoryId(params);

        String brandId = getString("brandId", params);
        Long tagId = getTagId(params);
        String[] names = StringUtils.split(getString("names", params), ',');
        String[] values = StringUtils.split(getString("values", params), ',');
        Integer orderBy = getInt("orderBy", params);
        Double startPrice = getDouble("startPrice", params);
        Double endPrice = getDouble("endPrice", params);
        Pagination pagination = this.productMng.getPageForTagChannel(brandId, web.getId(), ctgId,
                tagId, isRecommend(params), names, values, isSpecial(params), orderBy.intValue(), startPrice, endPrice, getPageNo(env),
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

