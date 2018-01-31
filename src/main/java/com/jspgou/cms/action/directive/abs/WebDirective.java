/*     */ package com.jspgou.cms.action.directive.abs;
/*     */ 
/*     */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*     */ import com.jspgou.common.web.freemarker.MustNumberException;
/*     */ import com.jspgou.common.web.freemarker.MustStringException;
/*     */ import com.jspgou.common.web.freemarker.ParamsRequiredException;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.WebsiteMng;
/*     */ import freemarker.core.Environment;
/*     */ import freemarker.template.AdapterTemplateModel;
/*     */ import freemarker.template.TemplateDirectiveBody;
/*     */ import freemarker.template.TemplateDirectiveModel;
/*     */ import freemarker.template.TemplateException;
/*     */ import freemarker.template.TemplateHashModel;
/*     */ import freemarker.template.TemplateModel;
/*     */ import freemarker.template.TemplateModelException;
/*     */ import freemarker.template.TemplateNumberModel;
/*     */ import freemarker.template.TemplateScalarModel;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.web.servlet.support.RequestContext;
/*     */ 
/*     */ public abstract class WebDirective
/*     */   implements TemplateDirectiveModel
/*     */ {
/*  22 */   protected final Logger log = LoggerFactory.getLogger(getClass());
/*     */   public static final String LOCATION = "location";
/*     */   public static final String URL_PREFIX = "urlPrefix";
/*     */   public static final String URL_SUFFIX = "urlSuffix";
/*     */   public static final String PAGE_NO = "pageNo";
/*     */   public static final String SYS_RES_ROOT = "sysResRoot";
/*     */   public static final String ROOT = "root";
/*     */   public static final String WEB = "web";
/*     */   public static final String BASE_DOMAIN = "baseDomain";
/*     */   public static final String LOGIN_URL = "loginUrl";
/*     */   public static final String CONFIG = "config";
/*     */   public static final String MEMBER = "member";
/*     */   public static final String GROUP = "group";
/*     */   public static final String PARAM_WEB_ID = "webId";
/*     */   public static final String PARAM_TPL = "tpl";
/*     */   public static final String PARAM_TPL_SUB = "tplSub";
/*     */   public static final String PARAM_COUNT = "count";
/*     */   public static final int MAX_COUNT = 200;
/*     */   public static final boolean PARAM_TPL_DEF = false;
/*     */   public static final String OUT_LIST = "tag_list";
/*     */   public static final String OUT_PAGINATION = "tag_pagination";
/*     */   public static final String PARAM_PARENT_ID = "parentId";
/*     */ 
/*     */   protected void renderBody(Environment env, TemplateDirectiveBody body)
/*     */     throws TemplateException, IOException
/*     */   {
/*  51 */     body.render(env.getOut());
/*     */   }
/*     */ 
/*     */   protected RequestContext getContext(Environment env) throws TemplateException
/*     */   {
/*  56 */     TemplateModel templatemodel = env.getGlobalVariable("springMacroRequestContext");
/*  57 */     if ((templatemodel instanceof AdapterTemplateModel)) {
/*  58 */       return (RequestContext)((AdapterTemplateModel)templatemodel).getAdaptedObject(RequestContext.class);
/*     */     }
/*  60 */     throw new TemplateModelException("RequestContext 'springMacroRequestContext' not found in DataModel.");
/*     */   }
/*     */ 
/*     */   protected void includeTpl(String s, String s1, Website website, Map params, Environment env)
/*     */     throws IOException, TemplateException
/*     */   {
/*  67 */     String s2 = website.getTplTag(s, s1, getSubTpl(params));
/*  68 */     env.include(s2, "UTF-8", true);
/*     */   }
/*     */ 
/*     */   protected int getPageNo(Environment env) throws TemplateException
/*     */   {
/*  73 */     TemplateModel templatemodel = env.getGlobalVariable("pageNo");
/*  74 */     if ((templatemodel instanceof TemplateNumberModel)) {
/*  75 */       return ((TemplateNumberModel)templatemodel).getAsNumber().intValue();
/*     */     }
/*  77 */     throw new TemplateModelException("RequestContext 'pageNo' not found in DataModel.");
/*     */   }
/*     */ 
/*     */   public static Map getMap(String name, Map<String, TemplateModel> params)
/*     */     throws TemplateException
/*     */   {
/*  84 */     TemplateModel model = (TemplateModel)params.get(name);
/*  85 */     if (model == null) {
/*  86 */       return null;
/*     */     }
/*  88 */     if ((model instanceof TemplateHashModel)) {
/*  89 */       TemplateHashModel s = (TemplateHashModel)model;
/*  90 */       return (Map)s;
/*     */     }
/*  92 */     return params;
/*     */   }
/*     */ 
/*     */   protected Website getWeb(Environment env, Map params, WebsiteMng websitemng)
/*     */     throws TemplateException
/*     */   {
/*  98 */     Long long1 = getWebId(params);
/*  99 */     if (long1 != null) {
/* 100 */       Website website = websitemng.findById(long1);
/* 101 */       if (website == null) {
/* 102 */         throw new TemplateModelException("Website id=" + long1 + " not exist.");
/*     */       }
/* 104 */       return website;
/*     */     }
/*     */ 
/* 107 */     TemplateModel templatemodel = env.getGlobalVariable("web");
/* 108 */     if ((templatemodel instanceof AdapterTemplateModel)) {
/* 109 */       return (Website)((AdapterTemplateModel)templatemodel).getAdaptedObject(Website.class);
/*     */     }
/* 111 */     throw new TemplateModelException("Website 'web' not found in DataModel");
/*     */   }
/*     */ 
/*     */   protected Long getWebId(Map params)
/*     */     throws TemplateException
/*     */   {
/* 117 */     TemplateModel templatemodel = (TemplateModel)params.get("webId");
/* 118 */     if (templatemodel == null) {
/* 119 */       return null;
/*     */     }
/* 121 */     if ((templatemodel instanceof TemplateNumberModel)) {
/* 122 */       return Long.valueOf(((TemplateNumberModel)templatemodel).getAsNumber().longValue());
/*     */     }
/* 124 */     throw new TemplateModelException("The 'webId' parameter must be a number.");
/*     */   }
/*     */ 
/*     */   protected int getCount(Map params)
/*     */     throws TemplateException
/*     */   {
/* 130 */     Integer integer = getInt("count", params);
/* 131 */     if (integer == null) {
/* 132 */       throw new ParamsRequiredException("count");
/*     */     }
/* 134 */     if (integer.intValue() > 200) {
/* 135 */       integer = Integer.valueOf(1);
/*     */     }
/* 137 */     else if (integer.intValue() < 1) {
/* 138 */       integer = Integer.valueOf(200);
/*     */     }
/*     */ 
/* 141 */     return integer.intValue();
/*     */   }
/*     */ 
/*     */   protected boolean isInvokeTpl(Map params) throws TemplateException
/*     */   {
/* 146 */     TemplateModel templatemodel = (TemplateModel)params.get("tpl");
/* 147 */     if (templatemodel == null) {
/* 148 */       return false;
/*     */     }
/* 150 */     if ((templatemodel instanceof TemplateScalarModel)) {
/* 151 */       return DirectiveUtils.getBoolean((TemplateScalarModel)templatemodel);
/*     */     }
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */   protected String getSubTpl(Map params)
/*     */     throws TemplateException
/*     */   {
/* 159 */     TemplateModel templatemodel = (TemplateModel)params.get("tplSub");
/* 160 */     if (templatemodel == null) {
/* 161 */       return null;
/*     */     }
/* 163 */     if ((templatemodel instanceof TemplateScalarModel)) {
/* 164 */       return ((TemplateScalarModel)templatemodel).getAsString();
/*     */     }
/* 166 */     throw new MustStringException("tplSub");
/*     */   }
/*     */ 
/*     */   protected String getString(String s, Map params)
/*     */     throws TemplateException
/*     */   {
/* 172 */     TemplateModel templatemodel = (TemplateModel)params.get(s);
/* 173 */     if (templatemodel == null) {
/* 174 */       return null;
/*     */     }
/* 176 */     if ((templatemodel instanceof TemplateScalarModel)) {
/* 177 */       return ((TemplateScalarModel)templatemodel).getAsString();
/*     */     }
/* 179 */     throw new MustStringException(s);
/*     */   }
/*     */ 
/*     */   protected Long getLong(String s, Map params)
/*     */     throws TemplateException
/*     */   {
/* 185 */     TemplateModel templatemodel = (TemplateModel)params.get(s);
/* 186 */     if (templatemodel == null) {
/* 187 */       return null;
/*     */     }
/* 189 */     if ((templatemodel instanceof TemplateScalarModel)) {
/* 190 */       String s1 = ((TemplateScalarModel)templatemodel).getAsString();
/* 191 */       if (StringUtils.isBlank(s1))
/* 192 */         return null;
/*     */       try
/*     */       {
/* 195 */         return Long.valueOf(Long.parseLong(s1));
/*     */       } catch (NumberFormatException e) {
/* 197 */         throw new MustNumberException(s);
/*     */       }
/*     */     }
/* 200 */     if ((templatemodel instanceof TemplateNumberModel)) {
/* 201 */       return Long.valueOf(((TemplateNumberModel)templatemodel).getAsNumber().longValue());
/*     */     }
/* 203 */     throw new MustNumberException(s);
/*     */   }
/*     */ 
/*     */   protected Integer getInt(String s, Map params)
/*     */     throws TemplateException
/*     */   {
/* 209 */     TemplateModel templatemodel = (TemplateModel)params.get(s);
/* 210 */     if (templatemodel == null) {
/* 211 */       return null;
/*     */     }
/* 213 */     if ((templatemodel instanceof TemplateScalarModel)) {
/* 214 */       String s1 = ((TemplateScalarModel)templatemodel).getAsString();
/* 215 */       if (StringUtils.isBlank(s1))
/* 216 */         return null;
/*     */       try
/*     */       {
/* 219 */         return Integer.valueOf(Integer.parseInt(s1));
/*     */       } catch (NumberFormatException e) {
/* 221 */         throw new MustNumberException(s);
/*     */       }
/*     */     }
/* 224 */     if ((templatemodel instanceof TemplateNumberModel)) {
/* 225 */       return Integer.valueOf(((TemplateNumberModel)templatemodel).getAsNumber().intValue());
/*     */     }
/* 227 */     throw new MustNumberException(s);
/*     */   }
/*     */ 
/*     */   protected Double getDouble(String s, Map params)
/*     */     throws TemplateException
/*     */   {
/* 233 */     TemplateModel templatemodel = (TemplateModel)params.get(s);
/* 234 */     if (templatemodel == null) {
/* 235 */       return null;
/*     */     }
/* 237 */     if ((templatemodel instanceof TemplateScalarModel)) {
/* 238 */       String s1 = ((TemplateScalarModel)templatemodel).getAsString();
/* 239 */       if (StringUtils.isBlank(s1))
/* 240 */         return null;
/*     */       try
/*     */       {
/* 243 */         return Double.valueOf(Double.parseDouble(s1));
/*     */       } catch (NumberFormatException e) {
/* 245 */         throw new MustNumberException(s);
/*     */       }
/*     */     }
/* 248 */     if ((templatemodel instanceof TemplateNumberModel)) {
/* 249 */       return Double.valueOf(((TemplateNumberModel)templatemodel).getAsNumber().intValue());
/*     */     }
/* 251 */     throw new MustNumberException(s);
/*     */   }
/*     */ 
/*     */   protected Boolean getBool(String s, Map params)
/*     */     throws TemplateException
/*     */   {
/* 257 */     TemplateModel templatemodel = (TemplateModel)params.get(s);
/* 258 */     if (templatemodel == null) {
/* 259 */       return null;
/*     */     }
/* 261 */     if ((templatemodel instanceof TemplateScalarModel)) {
/* 262 */       String s1 = ((TemplateScalarModel)templatemodel).getAsString();
/* 263 */       if (StringUtils.isBlank(s1)) {
/* 264 */         return null;
/*     */       }
/* 266 */       return Boolean.valueOf(!s1.equals("0"));
/*     */     }
/*     */ 
/* 269 */     if ((templatemodel instanceof TemplateNumberModel)) {
/* 270 */       return Boolean.valueOf(((TemplateNumberModel)templatemodel).getAsNumber().intValue() != 0);
/*     */     }
/* 272 */     throw new MustNumberException(s);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.abs.WebDirective
 * JD-Core Version:    0.6.0
 */