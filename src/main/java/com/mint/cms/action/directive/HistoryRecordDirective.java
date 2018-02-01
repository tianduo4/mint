package com.mint.cms.action.directive;

import com.mint.cms.manager.ProductMng;
import com.mint.common.web.freemarker.DirectiveUtils;
import com.mint.core.entity.Website;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class HistoryRecordDirective extends ProductAbstractDirective {
    public static final String TPL_NAME = "ProductList";

    @Autowired
    private ProductMng productMng;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Website web = getWeb(env, params, this.websiteMng);
        HashSet set = new HashSet();
        Integer count = getInt("count", params);
        String historyProductIds = getString("historyProductIds", params);
        if ((historyProductIds != null) && (!historyProductIds.equals(""))) {
            String[] pids = historyProductIds.split(",");
            if (pids.length > 0) {
                for (int i = 0; i < pids.length; i++) {
                    set.add(Long.valueOf(pids[i]));
                }
            }
        }
        List list = this.productMng.getHistoryProduct(set, count);
        Map paramWrap = new HashMap(
                params);
        paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
        Map origMap =
                DirectiveUtils.addParamsToVariable(env, paramWrap);
        if (isInvokeTpl(params))
            includeTpl("shop", "ProductList", web, params, env);
        else {
            renderBody(env, loopVars, body);
        }
        DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
    }
}

