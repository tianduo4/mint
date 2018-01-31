/*     */ package com.jspgou.core.directive;
/*     */ 
/*     */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.web.threadvariable.AdminThread;
/*     */ import com.jspgou.common.web.freemarker.MustStringException;
/*     */ import com.jspgou.common.web.freemarker.ParamsRequiredException;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.web.admin.AdminSecureInterceptor;
/*     */ import freemarker.core.Environment;
/*     */ import freemarker.template.TemplateDirectiveBody;
/*     */ import freemarker.template.TemplateException;
/*     */ import freemarker.template.TemplateModel;
/*     */ import freemarker.template.TemplateModelException;
/*     */ import freemarker.template.TemplateScalarModel;
/*     */ import freemarker.template.TemplateSequenceModel;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import org.springframework.web.servlet.support.RequestContext;
/*     */ 
/*     */ public class AuthorizeDirective extends WebDirective
/*     */ {
/*     */   public static final String PARAM_URL = "url";
/*  23 */   private boolean develop = false;
/*     */ 
/*     */   public void setDevelop(boolean develop) {
/*  26 */     this.develop = develop;
/*     */   }
/*     */ 
/*     */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*     */     throws TemplateException, IOException
/*     */   {
/*  33 */     ShopAdmin admin = AdminThread.get();
/*  34 */     if (this.develop) {
/*  35 */       body.render(env.getOut());
/*     */     }
/*  37 */     String url = getUrl(params);
/*  38 */     RequestContext requestcontext = getContext(env);
/*  39 */     String s1 = AdminSecureInterceptor.getURI(requestcontext.getRequestUri(), requestcontext.getContextPath());
/*  40 */     s1 = getRealURI(s1, url);
/*  41 */     if (s1.contains("//")) {
/*  42 */       String[] a = s1.split("//");
/*  43 */       s1 = "/" + a[1];
/*     */     }
/*  45 */     TemplateSequenceModel templatesequencemodel = getPerms(env);
/*     */     boolean flag;
/*     */     boolean flag;
/*  47 */     if (admin.getAdmin().isSuper())
/*  48 */       flag = true;
/*     */     else {
/*  50 */       flag = hasRight(templatesequencemodel, s1);
/*     */     }
/*     */ 
/*  53 */     if (flag)
/*  54 */       body.render(env.getOut());
/*     */   }
/*     */ 
/*     */   private static String getRealURI(String s, String s1)
/*     */   {
/*  59 */     int i = 0;
/*     */ 
/*  61 */     for (int j = s.lastIndexOf("/"); s1.startsWith("../", i); j = s.lastIndexOf("/", j - 1)) {
/*  62 */       i += 3;
/*     */     }
/*  64 */     return s.substring(0, j + 1) + s1.substring(i);
/*     */   }
/*     */ 
/*     */   private boolean hasRight(TemplateSequenceModel templatesequencemodel, String s) throws TemplateModelException
/*     */   {
/*  69 */     int i = 0;
/*  70 */     for (int j = templatesequencemodel.size(); i < j; i++) {
/*  71 */       String s1 = ((TemplateScalarModel)templatesequencemodel.get(i)).getAsString();
/*  72 */       if ((s.equals(s1)) || (s.startsWith(s1))) {
/*  73 */         return true;
/*     */       }
/*     */     }
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   private TemplateSequenceModel getPerms(Environment env) throws TemplateModelException
/*     */   {
/*  81 */     TemplateModel templatemodel = env.getGlobalVariable("_permission_key");
/*  82 */     if (templatemodel == null) {
/*  83 */       throw new TemplateModelException("'_permission_key' not found in DataModel.");
/*     */     }
/*  85 */     if ((templatemodel instanceof TemplateSequenceModel)) {
/*  86 */       return (TemplateSequenceModel)templatemodel;
/*     */     }
/*  88 */     throw new TemplateModelException("'_permission_key' not instanse of TemplateSequenceModel: " + templatemodel.getClass().getName());
/*     */   }
/*     */ 
/*     */   private String getUrl(Map params)
/*     */     throws TemplateException
/*     */   {
/*  94 */     TemplateModel templatemodel = (TemplateModel)params.get("url");
/*  95 */     if (templatemodel == null) {
/*  96 */       throw new ParamsRequiredException("url");
/*     */     }
/*  98 */     if ((templatemodel instanceof TemplateScalarModel)) {
/*  99 */       return ((TemplateScalarModel)templatemodel).getAsString();
/*     */     }
/* 101 */     throw new MustStringException("url");
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.directive.AuthorizeDirective
 * JD-Core Version:    0.6.0
 */