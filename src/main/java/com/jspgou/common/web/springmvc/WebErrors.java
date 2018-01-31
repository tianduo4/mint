/*     */ package com.jspgou.common.web.springmvc;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.context.MessageSource;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.util.Assert;
/*     */ import org.springframework.web.context.WebApplicationContext;
/*     */ import org.springframework.web.servlet.LocaleResolver;
/*     */ import org.springframework.web.servlet.support.RequestContextUtils;
/*     */ 
/*     */ public abstract class WebErrors
/*     */ {
/*  35 */   public static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+(\\.\\w+)*@\\w+(\\.\\w+)+$");
/*     */ 
/*  40 */   public static final Pattern USERNAME_PATTERN = Pattern.compile("^[0-9a-zA-Z\\u4e00-\\u9fa5\\.\\-@_]+$");
/*     */   private MessageSource messageSource;
/*     */   private Locale locale;
/*     */   private List<String> errors;
/*     */ 
/*     */   public WebErrors(HttpServletRequest request)
/*     */   {
/*  49 */     WebApplicationContext webapplicationcontext = RequestContextUtils.getWebApplicationContext(request);
/*  50 */     if (webapplicationcontext != null) {
/*  51 */       LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
/*     */ 
/*  53 */       if (localeResolver != null) {
/*  54 */         Locale locale = localeResolver.resolveLocale(request);
/*  55 */         this.messageSource = webapplicationcontext;
/*  56 */         this.locale = locale;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public WebErrors()
/*     */   {
/*     */   }
/*     */ 
/*     */   public WebErrors(MessageSource messageSource, Locale locale)
/*     */   {
/*  71 */     this.messageSource = messageSource;
/*  72 */     this.locale = locale;
/*     */   }
/*     */ 
/*     */   public String getMessage(String code, Object[] args) {
/*  76 */     if (this.messageSource == null) {
/*  77 */       throw new IllegalStateException("MessageSource cannot be null.");
/*     */     }
/*  79 */     return this.messageSource.getMessage(code, args, this.locale);
/*     */   }
/*     */ 
/*     */   public void addErrorCode(String code, Object[] args)
/*     */   {
/*  92 */     getErrors().add(getMessage(code, args));
/*     */   }
/*     */ 
/*     */   public void addErrorCode(String code)
/*     */   {
/* 103 */     getErrors().add(getMessage(code, new Object[0]));
/*     */   }
/*     */ 
/*     */   public void addErrorString(String error)
/*     */   {
/* 112 */     getErrors().add(error);
/*     */   }
/*     */ 
/*     */   public void addError(String error)
/*     */   {
/* 122 */     if (this.messageSource != null) {
/* 123 */       error = this.messageSource.getMessage(error, null, error, this.locale);
/*     */     }
/* 125 */     getErrors().add(error);
/*     */   }
/*     */ 
/*     */   public boolean hasErrors()
/*     */   {
/* 134 */     return (this.errors != null) && (this.errors.size() > 0);
/*     */   }
/*     */ 
/*     */   public int getCount()
/*     */   {
/* 143 */     return this.errors == null ? 0 : this.errors.size();
/*     */   }
/*     */ 
/*     */   public List<String> getErrors()
/*     */   {
/* 152 */     if (this.errors == null) {
/* 153 */       this.errors = new ArrayList();
/*     */     }
/* 155 */     return this.errors;
/*     */   }
/*     */ 
/*     */   public String showErrorPage(ModelMap model)
/*     */   {
/* 167 */     toModel(model);
/* 168 */     return getErrorPage();
/*     */   }
/*     */ 
/*     */   public void toModel(Map<String, Object> model)
/*     */   {
/* 177 */     Assert.notNull(model);
/* 178 */     if (!hasErrors()) {
/* 179 */       throw new IllegalStateException("no errors found!");
/*     */     }
/* 181 */     model.put(getErrorAttrName(), getErrors());
/*     */   }
/*     */ 
/*     */   public boolean ifNull(Object o, String field) {
/* 185 */     if (o == null) {
/* 186 */       addErrorCode("error.required", new Object[] { field });
/* 187 */       return true;
/*     */     }
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ifEmpty(Object[] o, String field)
/*     */   {
/* 194 */     if ((o == null) || (o.length <= 0)) {
/* 195 */       addErrorCode("error.required", new Object[] { field });
/* 196 */       return true;
/*     */     }
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ifBlank(String s, String field, int maxLength)
/*     */   {
/* 203 */     if (StringUtils.isBlank(s)) {
/* 204 */       addErrorCode("error.required", new Object[] { field });
/* 205 */       return true;
/*     */     }
/*     */ 
/* 208 */     return ifMaxLength(s, field, maxLength);
/*     */   }
/*     */ 
/*     */   public boolean ifMaxLength(String s, String field, int maxLength)
/*     */   {
/* 214 */     if ((s != null) && (s.length() > maxLength)) {
/* 215 */       addErrorCode("error.maxLength", new Object[] { field, Integer.valueOf(maxLength) });
/* 216 */       return true;
/*     */     }
/* 218 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ifOutOfLength(String s, String field, int minLength, int maxLength)
/*     */   {
/* 223 */     if (s == null) {
/* 224 */       addErrorCode("error.required", new Object[] { field });
/* 225 */       return true;
/*     */     }
/* 227 */     int len = s.length();
/* 228 */     if ((len < minLength) || (len > maxLength)) {
/* 229 */       addErrorCode("error.outOfLength", new Object[] { field, Integer.valueOf(minLength), Integer.valueOf(maxLength) });
/* 230 */       return true;
/*     */     }
/* 232 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ifNotEmail(String email, String field, int maxLength) {
/* 236 */     if (ifBlank(email, field, maxLength)) {
/* 237 */       return true;
/*     */     }
/* 239 */     Matcher m = EMAIL_PATTERN.matcher(email);
/* 240 */     if (!m.matches()) {
/* 241 */       addErrorCode("error.email", new Object[] { field });
/* 242 */       return true;
/*     */     }
/* 244 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ifNotUsername(String username, String field, int minLength, int maxLength)
/*     */   {
/* 249 */     if (ifOutOfLength(username, field, minLength, maxLength)) {
/* 250 */       return true;
/*     */     }
/* 252 */     Matcher m = USERNAME_PATTERN.matcher(username);
/* 253 */     if (!m.matches()) {
/* 254 */       addErrorCode("error.username", new Object[] { field });
/* 255 */       return true;
/*     */     }
/* 257 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean ifNotExist(Object o, Class<?> clazz, Serializable id) {
/* 261 */     if (o == null) {
/* 262 */       addErrorCode("error.notExist", new Object[] { clazz.getSimpleName(), id });
/* 263 */       return true;
/*     */     }
/* 265 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean notInReturn(Object o, Class<?> clazz, Serializable id)
/*     */   {
/* 270 */     if (o != null) {
/* 271 */       addErrorCode("error.notReturnOrder", new Object[] { clazz.getSimpleName(), id });
/* 272 */       return true;
/*     */     }
/* 274 */     return false;
/*     */   }
/*     */ 
/*     */   public void noPermission(Class<?> clazz, Serializable id)
/*     */   {
/* 279 */     addErrorCode("error.noPermission", new Object[] { clazz.getSimpleName(), id });
/*     */   }
/*     */ 
/*     */   public MessageSource getMessageSource()
/*     */   {
/* 287 */     return this.messageSource;
/*     */   }
/*     */ 
/*     */   public void setMessageSource(MessageSource messageSource) {
/* 291 */     this.messageSource = messageSource;
/*     */   }
/*     */ 
/*     */   public Locale getLocale()
/*     */   {
/* 300 */     return this.locale;
/*     */   }
/*     */ 
/*     */   public void setLocale(Locale locale)
/*     */   {
/* 309 */     this.locale = locale;
/*     */   }
/*     */ 
/*     */   protected abstract String getErrorPage();
/*     */ 
/*     */   protected abstract String getErrorAttrName();
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.web.springmvc.WebErrors
 * JD-Core Version:    0.6.0
 */