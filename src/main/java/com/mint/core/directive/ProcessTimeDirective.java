package com.mint.core.directive;

import com.mint.cms.action.directive.abs.WebDirective;
import com.mint.common.web.freemarker.MustNumberException;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.Map;

public class ProcessTimeDirective extends WebDirective {
    private static final DecimalFormat FORMAT = new DecimalFormat("0.000");

    public void execute(Environment env, Map params, TemplateModel[] atemplatemodel, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        long time = getStartTime(env);
        if (time == -1L) {
            return;
        }
        time = System.currentTimeMillis() - time;
        Writer writer = env.getOut();
        writer.append("Processed in " + FORMAT.format((float) time / 1000.0F) + " second(s)");
    }

    private long getStartTime(Environment env)
            throws TemplateModelException {
        TemplateModel templatemodel = env.getGlobalVariable("_start_time");
        if (templatemodel == null) {
            return -1L;
        }
        if ((templatemodel instanceof TemplateNumberModel)) {
            return ((TemplateNumberModel) templatemodel).getAsNumber().longValue();
        }
        throw new MustNumberException("_start_time");
    }
}

