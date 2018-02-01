package com.jspgou.common.web.freemarker;

import com.jspgou.common.util.StrUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class TextCutDirective
        implements TemplateDirectiveModel {
    public static final String PARAM_S = "s";
    public static final String PARAM_LEN = "len";
    public static final String PARAM_APPEND = "append";

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String s = DirectiveUtils.getString("s", params);
        Integer len = DirectiveUtils.getInt("len", params);
        String append = DirectiveUtils.getString("append", params);
        if (s != null) {
            Writer out = env.getOut();
            if (len != null)
                out.append(StrUtils.textCut(s, len.intValue(), append));
            else
                out.append(s);
        }
    }
}

