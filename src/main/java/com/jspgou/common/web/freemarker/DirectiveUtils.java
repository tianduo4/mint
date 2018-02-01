package com.jspgou.common.web.freemarker;

import com.jspgou.common.web.springmvc.DateTypeEditor;
import freemarker.core.Environment;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public abstract class DirectiveUtils {
    public static final String OUT_BEAN = "tag_bean";

    public static Map<String, TemplateModel> addParamsToVariable(Environment env, Map<String, TemplateModel> params)
            throws TemplateException {
        Map origMap = new HashMap();
        if (params.size() <= 0) {
            return origMap;
        }
        Set<Map.Entry<String, TemplateModel>> entrySet = params.entrySet();

        for (Map.Entry entry : entrySet) {
            String key = (String) entry.getKey();
            TemplateModel value = env.getVariable(key);
            if (value != null) {
                origMap.put(key, value);
            }
            env.setVariable(key, (TemplateModel) entry.getValue());
        }
        return origMap;
    }

    public static void removeParamsFromVariable(Environment env, Map<String, TemplateModel> params, Map<String, TemplateModel> origMap)
            throws TemplateException {
        if (params.size() <= 0) {
            return;
        }
        for (String key : params.keySet())
            env.setVariable(key, (TemplateModel) origMap.get(key));
    }

    public static boolean getBoolean(TemplateScalarModel templatescalarmodel)
            throws TemplateModelException {
        return "1".equals(templatescalarmodel.getAsString());
    }

    public static String getString(String name, Map<String, TemplateModel> params) throws TemplateException {
        TemplateModel model = (TemplateModel) params.get(name);
        if (model == null) {
            return null;
        }
        if ((model instanceof TemplateScalarModel))
            return ((TemplateScalarModel) model).getAsString();
        if ((model instanceof TemplateNumberModel)) {
            return ((TemplateNumberModel) model).getAsNumber().toString();
        }
        throw new MustStringException(name);
    }

    public static Long getLong(String name, Map<String, TemplateModel> params) throws TemplateException {
        TemplateModel model = (TemplateModel) params.get(name);
        if (model == null) {
            return null;
        }
        if ((model instanceof TemplateScalarModel)) {
            String s = ((TemplateScalarModel) model).getAsString();
            if (StringUtils.isBlank(s))
                return null;
            try {
                return Long.valueOf(Long.parseLong(s));
            } catch (NumberFormatException e) {
                throw new MustNumberException(name);
            }
        }
        if ((model instanceof TemplateNumberModel)) {
            return Long.valueOf(((TemplateNumberModel) model).getAsNumber().longValue());
        }
        throw new MustNumberException(name);
    }

    public static Integer getInt(String name, Map<String, TemplateModel> params)
            throws TemplateException {
        TemplateModel model = (TemplateModel) params.get(name);
        if (model == null) {
            return null;
        }
        if ((model instanceof TemplateScalarModel)) {
            String s = ((TemplateScalarModel) model).getAsString();
            if (StringUtils.isBlank(s))
                return null;
            try {
                return Integer.valueOf(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                throw new MustNumberException(name);
            }
        }
        if ((model instanceof TemplateNumberModel)) {
            return Integer.valueOf(((TemplateNumberModel) model).getAsNumber().intValue());
        }
        throw new MustNumberException(name);
    }

    public static Date getDate(String name, Map<String, TemplateModel> params)
            throws TemplateException {
        TemplateModel model = (TemplateModel) params.get(name);
        if (model == null) {
            return null;
        }
        if ((model instanceof TemplateDateModel))
            return ((TemplateDateModel) model).getAsDate();
        if ((model instanceof TemplateScalarModel)) {
            DateTypeEditor editor = new DateTypeEditor();
            editor.setAsText(((TemplateScalarModel) model).getAsString());
            return (Date) editor.getValue();
        }
        throw new MustDateException(name);
    }
}

