package com.jspgou.cms.action.directive;

import com.jspgou.cms.action.directive.abs.WebDirective;
import com.jspgou.cms.manager.ProductMng;
import com.jspgou.core.manager.WebsiteMng;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class ProductAbstractDirective extends WebDirective {
    public static final String PARAM_CATEGORY_ID = "categoryId";
    public static final String PARAM_TAG_ID = "tagId";
    public static final String PARAM_RECOMMEND = "recommend";
    public static final String PARAM_SPECIAL = "special";
    protected ProductMng productMng;
    protected WebsiteMng websiteMng;

    protected Integer getCategoryId(Map<String, TemplateModel> params)
            throws TemplateException {
        return getInt("categoryId", params);
    }

    protected Long getPtypeId(Map<String, TemplateModel> params) throws TemplateException {
        return getLong("ptypeId", params);
    }

    protected Long getTagId(Map<String, TemplateModel> params) throws TemplateException {
        return getLong("tagId", params);
    }

    protected Boolean isRecommend(Map<String, TemplateModel> params) throws TemplateException {
        return getBool("recommend", params);
    }

    protected Boolean isSpecial(Map<String, TemplateModel> params) throws TemplateException {
        return getBool("special", params);
    }

    protected Boolean isHostSale(Map<String, TemplateModel> params) throws TemplateException {
        return getBool("hostSale", params);
    }

    protected Boolean isNewProduct(Map<String, TemplateModel> params) throws TemplateException {
        return getBool("newProduct", params);
    }

    protected void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        body.render(env.getOut());
    }

    @Autowired
    public void setProductMng(ProductMng productMng) {
        this.productMng = productMng;
    }

    @Autowired
    public void setWebsiteMng(WebsiteMng websiteMng) {
        this.websiteMng = websiteMng;
    }
}

