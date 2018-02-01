package com.jspgou.core.directive;

import com.jspgou.cms.action.directive.abs.WebDirective;
import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.web.threadvariable.AdminThread;
import com.jspgou.common.web.freemarker.MustStringException;
import com.jspgou.common.web.freemarker.ParamsRequiredException;
import com.jspgou.core.entity.Admin;
import com.jspgou.core.web.admin.AdminSecureInterceptor;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import freemarker.template.TemplateSequenceModel;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.servlet.support.RequestContext;

public class AuthorizeDirective extends WebDirective {
    public static final String PARAM_URL = "url";
    private boolean develop = false;

    public void setDevelop(boolean develop) {
        this.develop = develop;
    }

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        ShopAdmin admin = AdminThread.get();
        if (this.develop) {
            body.render(env.getOut());
        }
        String url = getUrl(params);
        RequestContext requestcontext = getContext(env);
        String s1 = AdminSecureInterceptor.getURI(requestcontext.getRequestUri(), requestcontext.getContextPath());
        s1 = getRealURI(s1, url);
        if (s1.contains("//")) {
            String[] a = s1.split("//");
            s1 = "/" + a[1];
        }
        TemplateSequenceModel templatesequencemodel = getPerms(env);
        boolean flag;
        if (admin.getAdmin().isSuper())
            flag = true;
        else {
            flag = hasRight(templatesequencemodel, s1);
        }

        if (flag)
            body.render(env.getOut());
    }

    private static String getRealURI(String s, String s1) {
        int i = 0;

        for (int j = s.lastIndexOf("/"); s1.startsWith("../", i);
             j = s.lastIndexOf("/", j - 1)
                ) {
            i += 3;
        }
        int j = 0; //TODO
        return s.substring(0, j + 1) + s1.substring(i);
    }

    private boolean hasRight(TemplateSequenceModel templatesequencemodel, String s) throws TemplateModelException {
        int i = 0;
        for (int j = templatesequencemodel.size(); i < j; i++) {
            String s1 = ((TemplateScalarModel) templatesequencemodel.get(i)).getAsString();
            if ((s.equals(s1)) || (s.startsWith(s1))) {
                return true;
            }
        }
        return false;
    }

    private TemplateSequenceModel getPerms(Environment env) throws TemplateModelException {
        TemplateModel templatemodel = env.getGlobalVariable("_permission_key");
        if (templatemodel == null) {
            throw new TemplateModelException("'_permission_key' not found in DataModel.");
        }
        if ((templatemodel instanceof TemplateSequenceModel)) {
            return (TemplateSequenceModel) templatemodel;
        }
        throw new TemplateModelException("'_permission_key' not instanse of TemplateSequenceModel: " + templatemodel.getClass().getName());
    }

    private String getUrl(Map params)
            throws TemplateException {
        TemplateModel templatemodel = (TemplateModel) params.get("url");
        if (templatemodel == null) {
            throw new ParamsRequiredException("url");
        }
        if ((templatemodel instanceof TemplateScalarModel)) {
            return ((TemplateScalarModel) templatemodel).getAsString();
        }
        throw new MustStringException("url");
    }
}

