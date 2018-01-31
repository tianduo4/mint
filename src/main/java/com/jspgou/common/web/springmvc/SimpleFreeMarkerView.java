/*     */ package com.jspgou.common.web.springmvc;
/*     */ 
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import freemarker.core.ParseException;
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.Template;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.beans.BeansException;
/*     */ import org.springframework.beans.factory.BeanFactoryUtils;
/*     */ import org.springframework.beans.factory.NoSuchBeanDefinitionException;
/*     */ import org.springframework.context.ApplicationContextException;
/*     */ import org.springframework.web.servlet.view.AbstractTemplateView;
/*     */ import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
/*     */ 
/*     */ public class SimpleFreeMarkerView extends AbstractTemplateView
/*     */ {
/*     */   public static final String CONTEXT_PATH = "base";
/*     */   public static final String SSO_ENABLE = "ssoEnable";
/*     */   public static final String USER = "user";
/*     */   private Configuration configuration;
/*     */ 
/*     */   public void setConfiguration(Configuration configuration)
/*     */   {
/*  46 */     this.configuration = configuration;
/*     */   }
/*     */ 
/*     */   protected Configuration getConfiguration() {
/*  50 */     return this.configuration;
/*     */   }
/*     */ 
/*     */   protected FreeMarkerConfig autodetectConfiguration()
/*     */     throws BeansException
/*     */   {
/*     */     try
/*     */     {
/*  61 */       return 
/*  62 */         (FreeMarkerConfig)BeanFactoryUtils.beanOfTypeIncludingAncestors(getApplicationContext(), 
/*  63 */         FreeMarkerConfig.class, true, false); } catch (NoSuchBeanDefinitionException ex) {
/*     */     }
/*  65 */     throw new ApplicationContextException(
/*  66 */       "Must define a single FreeMarkerConfig bean in this web application context (may be inherited): FreeMarkerConfigurer is the usual implementation. This bean may be given any name.",
/*  68 */       ex);
/*     */   }
/*     */ 
/*     */   protected void initApplicationContext()
/*     */     throws BeansException
/*     */   {
/*  84 */     super.initApplicationContext();
/*     */ 
/*  86 */     if (getConfiguration() == null) {
/*  87 */       FreeMarkerConfig freemarkerconfig = autodetectConfiguration();
/*  88 */       setConfiguration(freemarkerconfig.getConfiguration());
/*     */     }
/*  90 */     checkTemplate();
/*     */   }
/*     */ 
/*     */   protected void checkTemplate()
/*     */     throws ApplicationContextException
/*     */   {
/*     */     try
/*     */     {
/* 107 */       getConfiguration().getTemplate(getUrl());
/*     */     } catch (ParseException ex) {
/* 109 */       throw new ApplicationContextException(
/* 110 */         "Failed to parse FreeMarker template for URL [" + getUrl() + 
/* 111 */         "]", ex);
/*     */     } catch (IOException ex) {
/* 113 */       throw new ApplicationContextException(
/* 114 */         "Could not load FreeMarker template for URL [" + getUrl() + 
/* 115 */         "]", ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void renderMergedTemplateModel(Map model, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 124 */     model.put("base", request.getContextPath());
/* 125 */     Website web = SiteUtils.getWeb(request);
/* 126 */     model.put("ssoEnable", web.getSsoEnable());
/* 127 */     model.put("user", MemberThread.get());
/* 128 */     getConfiguration().getTemplate(getUrl()).process(model, 
/* 129 */       response.getWriter());
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.springmvc.SimpleFreeMarkerView
 * JD-Core Version:    0.6.0
 */