package com.mint.cms.lucene;

import com.mint.common.page.Pagination;
import com.mint.common.web.freemarker.DirectiveUtils;
import com.mint.core.entity.Website;
import com.mint.core.manager.WebsiteMng;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateNumberModel;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;

import org.apache.lucene.queryParser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

public class LuceneDirectivePage extends LuceneDirectiveAbstract {
    public static final String TPL_NAME = "ProductPage";

    @Autowired
    private LuceneProductSvc luceneProductSvc;

    @Autowired
    private ServletContext servletContext;
    protected WebsiteMng websiteMng;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Website web = this.websiteMng.findById(Long.valueOf(1L));
        String query = getQuery(params);
        Long ctgId = getCtgId(params);
        Long brandId = getBrandId(params);

        Long type = getPtypeId(params);
        Date start = getStartDate(params);
        Date end = getEndDate(params);
        Integer orderBy = getInt("orderBy", params);
        int pageNo = ((TemplateNumberModel) env.getGlobalVariable("pageNo")).getAsNumber().intValue();
        Pagination pagination;
        try {
            String path = this.servletContext.getRealPath("/WEB-INF/lucene");
            pagination = this.luceneProductSvc.search(path, query, web.getId(), ctgId, brandId, start, end, orderBy.intValue(), pageNo, getCount(params));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Map paramWrap = new HashMap(
                params);
        paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination));
        paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination.getList()));
        Map origMap =
                DirectiveUtils.addParamsToVariable(env, paramWrap);
        if (isInvokeTpl(params))
            includeTpl("shop", "ProductPage", web, params, env);
        else {
            renderBody(env, loopVars, body);
        }
        DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
    }

    @Autowired
    public void setWebsiteMng(WebsiteMng websiteMng) {
        this.websiteMng = websiteMng;
    }
}

