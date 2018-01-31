/*     */ package com.jspgou.common.web.freemarker;
/*     */ 
/*     */ import com.jspgou.common.web.springmvc.DateTypeEditor;
/*     */ import freemarker.core.Environment;
/*     */ import freemarker.template.TemplateDateModel;
/*     */ import freemarker.template.TemplateException;
/*     */ import freemarker.template.TemplateModel;
/*     */ import freemarker.template.TemplateModelException;
/*     */ import freemarker.template.TemplateNumberModel;
/*     */ import freemarker.template.TemplateScalarModel;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public abstract class DirectiveUtils
/*     */ {
/*     */   public static final String OUT_BEAN = "tag_bean";
/*     */ 
/*     */   public static Map<String, TemplateModel> addParamsToVariable(Environment env, Map<String, TemplateModel> params)
/*     */     throws TemplateException
/*     */   {
/*  34 */     Map origMap = new HashMap();
/*  35 */     if (params.size() <= 0) {
/*  36 */       return origMap;
/*     */     }
/*  38 */     Set entrySet = params.entrySet();
/*     */ 
/*  41 */     for (Map.Entry entry : entrySet) {
/*  42 */       String key = (String)entry.getKey();
/*  43 */       TemplateModel value = env.getVariable(key);
/*  44 */       if (value != null) {
/*  45 */         origMap.put(key, value);
/*     */       }
/*  47 */       env.setVariable(key, (TemplateModel)entry.getValue());
/*     */     }
/*  49 */     return origMap;
/*     */   }
/*     */ 
/*     */   public static void removeParamsFromVariable(Environment env, Map<String, TemplateModel> params, Map<String, TemplateModel> origMap)
/*     */     throws TemplateException
/*     */   {
/*  63 */     if (params.size() <= 0) {
/*  64 */       return;
/*     */     }
/*  66 */     for (String key : params.keySet())
/*  67 */       env.setVariable(key, (TemplateModel)origMap.get(key));
/*     */   }
/*     */ 
/*     */   public static boolean getBoolean(TemplateScalarModel templatescalarmodel)
/*     */     throws TemplateModelException
/*     */   {
/*  73 */     return "1".equals(templatescalarmodel.getAsString());
/*     */   }
/*     */ 
/*     */   public static String getString(String name, Map<String, TemplateModel> params) throws TemplateException
/*     */   {
/*  78 */     TemplateModel model = (TemplateModel)params.get(name);
/*  79 */     if (model == null) {
/*  80 */       return null;
/*     */     }
/*  82 */     if ((model instanceof TemplateScalarModel))
/*  83 */       return ((TemplateScalarModel)model).getAsString();
/*  84 */     if ((model instanceof TemplateNumberModel)) {
/*  85 */       return ((TemplateNumberModel)model).getAsNumber().toString();
/*     */     }
/*  87 */     throw new MustStringException(name);
/*     */   }
/*     */ 
/*     */   public static Long getLong(String name, Map<String, TemplateModel> params) throws TemplateException
/*     */   {
/*  92 */     TemplateModel model = (TemplateModel)params.get(name);
/*  93 */     if (model == null) {
/*  94 */       return null;
/*     */     }
/*  96 */     if ((model instanceof TemplateScalarModel)) {
/*  97 */       String s = ((TemplateScalarModel)model).getAsString();
/*  98 */       if (StringUtils.isBlank(s))
/*  99 */         return null;
/*     */       try
/*     */       {
/* 102 */         return Long.valueOf(Long.parseLong(s));
/*     */       } catch (NumberFormatException e) {
/* 104 */         throw new MustNumberException(name);
/*     */       }
/*     */     }
/* 106 */     if ((model instanceof TemplateNumberModel)) {
/* 107 */       return Long.valueOf(((TemplateNumberModel)model).getAsNumber().longValue());
/*     */     }
/* 109 */     throw new MustNumberException(name);
/*     */   }
/*     */ 
/*     */   public static Integer getInt(String name, Map<String, TemplateModel> params)
/*     */     throws TemplateException
/*     */   {
/* 115 */     TemplateModel model = (TemplateModel)params.get(name);
/* 116 */     if (model == null) {
/* 117 */       return null;
/*     */     }
/* 119 */     if ((model instanceof TemplateScalarModel)) {
/* 120 */       String s = ((TemplateScalarModel)model).getAsString();
/* 121 */       if (StringUtils.isBlank(s))
/* 122 */         return null;
/*     */       try
/*     */       {
/* 125 */         return Integer.valueOf(Integer.parseInt(s));
/*     */       } catch (NumberFormatException e) {
/* 127 */         throw new MustNumberException(name);
/*     */       }
/*     */     }
/* 129 */     if ((model instanceof TemplateNumberModel)) {
/* 130 */       return Integer.valueOf(((TemplateNumberModel)model).getAsNumber().intValue());
/*     */     }
/* 132 */     throw new MustNumberException(name);
/*     */   }
/*     */ 
/*     */   public static Date getDate(String name, Map<String, TemplateModel> params)
/*     */     throws TemplateException
/*     */   {
/* 138 */     TemplateModel model = (TemplateModel)params.get(name);
/* 139 */     if (model == null) {
/* 140 */       return null;
/*     */     }
/* 142 */     if ((model instanceof TemplateDateModel))
/* 143 */       return ((TemplateDateModel)model).getAsDate();
/* 144 */     if ((model instanceof TemplateScalarModel)) {
/* 145 */       DateTypeEditor editor = new DateTypeEditor();
/* 146 */       editor.setAsText(((TemplateScalarModel)model).getAsString());
/* 147 */       return (Date)editor.getValue();
/*     */     }
/* 149 */     throw new MustDateException(name);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.freemarker.DirectiveUtils
 * JD-Core Version:    0.6.0
 */