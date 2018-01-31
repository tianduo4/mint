/*     */ package com.jspgou.core.manager.impl;
/*     */ 
/*     */ import com.jspgou.common.hibernate4.Updater;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.web.RequestUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.dao.LogDao;
/*     */ import com.jspgou.core.entity.Log;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.manager.LogMng;
/*     */ import java.util.Date;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ import org.springframework.web.util.UrlPathHelper;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class LogMngImpl
/*     */   implements LogMng
/*     */ {
/*     */   private LogDao dao;
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Pagination getPage(int pageNo, int pageSize)
/*     */   {
/*  31 */     Pagination page = this.dao.getPage(pageNo, pageSize);
/*  32 */     return page;
/*     */   }
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Log findById(Long id) {
/*  38 */     Log entity = this.dao.findById(id);
/*  39 */     return entity;
/*     */   }
/*     */ 
/*     */   public Log save(Log bean)
/*     */   {
/*  44 */     this.dao.save(bean);
/*  45 */     return bean;
/*     */   }
/*     */ 
/*     */   public void save(String versions, String updatelog)
/*     */   {
/*  50 */     Date date = new Date();
/*  51 */     Log bean = new Log();
/*  52 */     bean.setContent(updatelog);
/*  53 */     bean.setTitle(versions);
/*  54 */     bean.setCategory(Integer.valueOf(1));
/*  55 */     bean.setTime(date);
/*  56 */     this.dao.save(bean);
/*     */   }
/*     */ 
/*     */   public Log update(Log bean)
/*     */   {
/*  61 */     Updater updater = new Updater(bean);
/*  62 */     Log entity = this.dao.updateByUpdater(updater);
/*  63 */     return entity;
/*     */   }
/*     */ 
/*     */   public Log deleteById(Long id)
/*     */   {
/*  68 */     Log bean = this.dao.deleteById(id);
/*  69 */     return bean;
/*     */   }
/*     */ 
/*     */   public Log[] deleteByIds(Long[] ids)
/*     */   {
/*  74 */     Log[] beans = new Log[ids.length];
/*  75 */     int i = 0; for (int len = ids.length; i < len; i++) {
/*  76 */       beans[i] = deleteById(ids[i]);
/*     */     }
/*  78 */     return beans;
/*     */   }
/*     */ 
/*     */   public Log save(Integer category, Website site, User user, String url, String ip, Date date, String title, String content)
/*     */   {
/*  83 */     Log log = new Log();
/*  84 */     log.setSite(site);
/*  85 */     log.setUser(user);
/*  86 */     log.setCategory(category);
/*  87 */     log.setIp(ip);
/*  88 */     log.setTime(date);
/*  89 */     log.setUrl(url);
/*  90 */     log.setTitle(title);
/*  91 */     log.setContent(content);
/*  92 */     save(log);
/*  93 */     return log;
/*     */   }
/*     */ 
/*     */   public Log loginFailure(HttpServletRequest request, String content)
/*     */   {
/*  98 */     String ip = RequestUtils.getIpAddr(request);
/*  99 */     UrlPathHelper helper = new UrlPathHelper();
/* 100 */     String uri = helper.getOriginatingRequestUri(request);
/* 101 */     Date date = new Date();
/* 102 */     Log log = save(Integer.valueOf(2), null, null, uri, ip, date, "login failure", content);
/* 103 */     return log;
/*     */   }
/*     */ 
/*     */   public Log loginSuccess(HttpServletRequest request, User user)
/*     */   {
/* 108 */     String ip = RequestUtils.getIpAddr(request);
/* 109 */     UrlPathHelper helper = new UrlPathHelper();
/* 110 */     String uri = helper.getOriginatingRequestUri(request);
/* 111 */     Date date = new Date();
/* 112 */     Log log = save(Integer.valueOf(1), null, user, uri, ip, date, "login success", null);
/* 113 */     return log;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setDao(LogDao dao)
/*     */   {
/* 120 */     this.dao = dao;
/*     */   }
/*     */ 
/*     */   public Log operating(HttpServletRequest request, String title, String content)
/*     */   {
/* 127 */     String ip = RequestUtils.getIpAddr(request);
/* 128 */     UrlPathHelper helper = new UrlPathHelper();
/* 129 */     String uri = helper.getOriginatingRequestUri(request);
/* 130 */     Date date = new Date();
/* 131 */     Log log = save(Integer.valueOf(3), null, null, uri, ip, date, 
/* 132 */       MessageResolver.getMessage(request, title, new Object[0]), content);
/* 133 */     return log;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.impl.LogMngImpl
 * JD-Core Version:    0.6.0
 */