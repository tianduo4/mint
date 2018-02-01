package com.mint.cms.action.directive.abs;

import com.mint.common.web.freemarker.DirectiveUtils;
import com.mint.common.web.freemarker.MustNumberException;
import com.mint.common.web.freemarker.MustStringException;
import com.mint.common.web.freemarker.ParamsRequiredException;
import com.mint.core.entity.Website;
import com.mint.core.manager.WebsiteMng;
import freemarker.core.Environment;
import freemarker.template.AdapterTemplateModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.RequestContext;

public abstract class WebDirective
        implements TemplateDirectiveModel {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    public static final String LOCATION = "location";
    public static final String URL_PREFIX = "urlPrefix";
    public static final String URL_SUFFIX = "urlSuffix";
    public static final String PAGE_NO = "pageNo";
    public static final String SYS_RES_ROOT = "sysResRoot";
    public static final String ROOT = "root";
    public static final String WEB = "web";
    public static final String BASE_DOMAIN = "baseDomain";
    public static final String LOGIN_URL = "loginUrl";
    public static final String CONFIG = "config";
    public static final String MEMBER = "member";
    public static final String GROUP = "group";
    public static final String PARAM_WEB_ID = "webId";
    public static final String PARAM_TPL = "tpl";
    public static final String PARAM_TPL_SUB = "tplSub";
    public static final String PARAM_COUNT = "count";
    public static final int MAX_COUNT = 200;
    public static final boolean PARAM_TPL_DEF = false;
    public static final String OUT_LIST = "tag_list";
    public static final String OUT_PAGINATION = "tag_pagination";
    public static final String PARAM_PARENT_ID = "parentId";

    protected void renderBody(Environment env, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        body.render(env.getOut());
    }

    protected RequestContext getContext(Environment env) throws TemplateException {
        TemplateModel templatemodel = env.getGlobalVariable("springMacroRequestContext");
        if ((templatemodel instanceof AdapterTemplateModel)) {
            return (RequestContext) ((AdapterTemplateModel) templatemodel).getAdaptedObject(RequestContext.class);
        }
        throw new TemplateModelException("RequestContext 'springMacroRequestContext' not found in DataModel.");
    }

    protected void includeTpl(String s, String s1, Website website, Map params, Environment env)
            throws IOException, TemplateException {
        String s2 = website.getTplTag(s, s1, getSubTpl(params));
        env.include(s2, "UTF-8", true);
    }

    protected int getPageNo(Environment env) throws TemplateException {
        TemplateModel templatemodel = env.getGlobalVariable("pageNo");
        if ((templatemodel instanceof TemplateNumberModel)) {
            return ((TemplateNumberModel) templatemodel).getAsNumber().intValue();
        }
        throw new TemplateModelException("RequestContext 'pageNo' not found in DataModel.");
    }

    public static Map getMap(String name, Map<String, TemplateModel> params)
            throws TemplateException {
        TemplateModel model = (TemplateModel) params.get(name);
        if (model == null) {
            return null;
        }
        if ((model instanceof TemplateHashModel)) {
            TemplateHashModel s = (TemplateHashModel) model;
            return (Map) s;
        }
        return params;
    }

    protected Website getWeb(Environment env, Map params, WebsiteMng websitemng)
            throws TemplateException {
        Long long1 = getWebId(params);
        if (long1 != null) {
            Website website = websitemng.findById(long1);
            if (website == null) {
                throw new TemplateModelException("Website id=" + long1 + " not exist.");
            }
            return website;
        }

        TemplateModel templatemodel = env.getGlobalVariable("web");
        if ((templatemodel instanceof AdapterTemplateModel)) {
            return (Website) ((AdapterTemplateModel) templatemodel).getAdaptedObject(Website.class);
        }
        throw new TemplateModelException("Website 'web' not found in DataModel");
    }

    protected Long getWebId(Map params)
            throws TemplateException {
        TemplateModel templatemodel = (TemplateModel) params.get("webId");
        if (templatemodel == null) {
            return null;
        }
        if ((templatemodel instanceof TemplateNumberModel)) {
            return Long.valueOf(((TemplateNumberModel) templatemodel).getAsNumber().longValue());
        }
        throw new TemplateModelException("The 'webId' parameter must be a number.");
    }

    protected int getCount(Map params)
            throws TemplateException {
        Integer integer = getInt("count", params);
        if (integer == null) {
            throw new ParamsRequiredException("count");
        }
        if (integer.intValue() > 200) {
            integer = Integer.valueOf(1);
        } else if (integer.intValue() < 1) {
            integer = Integer.valueOf(200);
        }

        return integer.intValue();
    }

    protected boolean isInvokeTpl(Map params) throws TemplateException {
        TemplateModel templatemodel = (TemplateModel) params.get("tpl");
        if (templatemodel == null) {
            return false;
        }
        if ((templatemodel instanceof TemplateScalarModel)) {
            return DirectiveUtils.getBoolean((TemplateScalarModel) templatemodel);
        }
        return false;
    }

    protected String getSubTpl(Map params)
            throws TemplateException {
        TemplateModel templatemodel = (TemplateModel) params.get("tplSub");
        if (templatemodel == null) {
            return null;
        }
        if ((templatemodel instanceof TemplateScalarModel)) {
            return ((TemplateScalarModel) templatemodel).getAsString();
        }
        throw new MustStringException("tplSub");
    }

    protected String getString(String s, Map params)
            throws TemplateException {
        TemplateModel templatemodel = (TemplateModel) params.get(s);
        if (templatemodel == null) {
            return null;
        }
        if ((templatemodel instanceof TemplateScalarModel)) {
            return ((TemplateScalarModel) templatemodel).getAsString();
        }
        throw new MustStringException(s);
    }

    protected Long getLong(String s, Map params)
            throws TemplateException {
        TemplateModel templatemodel = (TemplateModel) params.get(s);
        if (templatemodel == null) {
            return null;
        }
        if ((templatemodel instanceof TemplateScalarModel)) {
            String s1 = ((TemplateScalarModel) templatemodel).getAsString();
            if (StringUtils.isBlank(s1))
                return null;
            try {
                return Long.valueOf(Long.parseLong(s1));
            } catch (NumberFormatException e) {
                throw new MustNumberException(s);
            }
        }
        if ((templatemodel instanceof TemplateNumberModel)) {
            return Long.valueOf(((TemplateNumberModel) templatemodel).getAsNumber().longValue());
        }
        throw new MustNumberException(s);
    }

    protected Integer getInt(String s, Map params)
            throws TemplateException {
        TemplateModel templatemodel = (TemplateModel) params.get(s);
        if (templatemodel == null) {
            return null;
        }
        if ((templatemodel instanceof TemplateScalarModel)) {
            String s1 = ((TemplateScalarModel) templatemodel).getAsString();
            if (StringUtils.isBlank(s1))
                return null;
            try {
                return Integer.valueOf(Integer.parseInt(s1));
            } catch (NumberFormatException e) {
                throw new MustNumberException(s);
            }
        }
        if ((templatemodel instanceof TemplateNumberModel)) {
            return Integer.valueOf(((TemplateNumberModel) templatemodel).getAsNumber().intValue());
        }
        throw new MustNumberException(s);
    }

    protected Double getDouble(String s, Map params)
            throws TemplateException {
        TemplateModel templatemodel = (TemplateModel) params.get(s);
        if (templatemodel == null) {
            return null;
        }
        if ((templatemodel instanceof TemplateScalarModel)) {
            String s1 = ((TemplateScalarModel) templatemodel).getAsString();
            if (StringUtils.isBlank(s1))
                return null;
            try {
                return Double.valueOf(Double.parseDouble(s1));
            } catch (NumberFormatException e) {
                throw new MustNumberException(s);
            }
        }
        if ((templatemodel instanceof TemplateNumberModel)) {
            return Double.valueOf(((TemplateNumberModel) templatemodel).getAsNumber().intValue());
        }
        throw new MustNumberException(s);
    }

    protected Boolean getBool(String s, Map params)
            throws TemplateException {
        TemplateModel templatemodel = (TemplateModel) params.get(s);
        if (templatemodel == null) {
            return null;
        }
        if ((templatemodel instanceof TemplateScalarModel)) {
            String s1 = ((TemplateScalarModel) templatemodel).getAsString();
            if (StringUtils.isBlank(s1)) {
                return null;
            }
            return Boolean.valueOf(!s1.equals("0"));
        }

        if ((templatemodel instanceof TemplateNumberModel)) {
            return Boolean.valueOf(((TemplateNumberModel) templatemodel).getAsNumber().intValue() != 0);
        }
        throw new MustNumberException(s);
    }
}

