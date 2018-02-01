package com.jspgou.cms.action.directive;

import freemarker.core.Environment;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RepeatDirective
        implements TemplateDirectiveModel {
    private static final String PARAM_NAME_COUNT = "count";
    private static final String PARAM_NAME_HR = "hr";

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        int countParam = 0;
        boolean countParamSet = false;
        boolean hrParam = false;

        Iterator paramIter = params
                .entrySet().iterator();
        while (paramIter.hasNext()) {
            Map.Entry ent =
                    (Map.Entry) paramIter
                            .next();

            String paramName = (String) ent.getKey();
            TemplateModel paramValue = (TemplateModel) ent.getValue();

            if (paramName.equals("count")) {
                if (!(paramValue instanceof TemplateNumberModel)) {
                    throw new TemplateModelException("The \"hr\" parameter must be a number.");
                }

                countParam = ((TemplateNumberModel) paramValue).getAsNumber()
                        .intValue();
                countParamSet = true;
                if (countParam < 0) {
                    throw new TemplateModelException("The \"hr\" parameter can't be negative.");
                }
            } else if (paramName.equals("hr")) {
                if (!(paramValue instanceof TemplateBooleanModel)) {
                    throw new TemplateModelException("The \"hr\" parameter must be a boolean.");
                }

                hrParam = ((TemplateBooleanModel) paramValue).getAsBoolean();
            } else {
                throw new TemplateModelException("Unsupported parameter: " +
                        paramName);
            }
        }
        if (!countParamSet) {
            throw new TemplateModelException("The required \"count\" paramteris missing.");
        }

        if (loopVars.length > 1) {
            throw new TemplateModelException(
                    "At most one loop variable is allowed.");
        }

        Writer out = env.getOut();
        if (body != null)
            for (int i = 0; i < countParam; i++) {
                if ((hrParam) && (i != 0)) {
                    out.write("<hr>");
                }

                if (loopVars.length > 0) {
                    loopVars[0] = new SimpleNumber(i + 1);
                }

                body.render(env.getOut());
            }
    }
}

