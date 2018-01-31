/*     */ package com.jspgou.cms.ueditor.hunter;
/*     */ 
/*     */ import com.jspgou.cms.service.ImageSvc;
/*     */ import com.jspgou.cms.ueditor.PathFormat;
/*     */ import com.jspgou.cms.ueditor.define.BaseState;
/*     */ import com.jspgou.cms.ueditor.define.MultiState;
/*     */ import com.jspgou.cms.ueditor.define.State;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ImageHunter
/*     */ {
/*  25 */   private String filename = null;
/*  26 */   private String savePath = null;
/*  27 */   private String rootPath = null;
/*  28 */   private List<String> allowTypes = null;
/*  29 */   private long maxSize = -1L;
/*     */ 
/*  31 */   private List<String> filters = null;
/*     */   private ImageSvc imgSvc;
/*     */   private Website site;
/*     */ 
/*     */   public ImageHunter(ImageSvc imgSvc, Website site)
/*     */   {
/*  38 */     this.imgSvc = imgSvc;
/*  39 */     this.site = site;
/*     */   }
/*     */ 
/*     */   public State capture(String[] list)
/*     */   {
/*  44 */     MultiState state = new MultiState(true);
/*  45 */     if ((list != null) && (list.length > 0)) {
/*  46 */       for (String source : list) {
/*  47 */         state.addState(captureRemoteData(source));
/*     */       }
/*     */     }
/*     */ 
/*  51 */     return state;
/*     */   }
/*     */ 
/*     */   public State captureByApi(String urlPrefix, String[] list)
/*     */   {
/*  57 */     MultiState state = new MultiState(true);
/*  58 */     if ((list != null) && (list.length > 0)) {
/*  59 */       for (String source : list) {
/*  60 */         state.addState(captureRemoteDataByApi(urlPrefix, source));
/*     */       }
/*     */     }
/*     */ 
/*  64 */     return state;
/*     */   }
/*     */ 
/*     */   public State captureRemoteData(String urlStr)
/*     */   {
/*  70 */     String imgUrl = this.imgSvc.crawlImg(urlStr, this.site);
/*  71 */     State state = new BaseState();
/*  72 */     state.putInfo("url", imgUrl);
/*  73 */     state.putInfo("source", urlStr);
/*  74 */     return state;
/*     */   }
/*     */ 
/*     */   public State captureRemoteDataByApi(String urlPrefix, String urlStr) {
/*  78 */     String imgUrl = this.imgSvc.crawlImg(urlStr, this.site);
/*  79 */     State state = new BaseState();
/*  80 */     state.putInfo("url", urlPrefix + imgUrl);
/*  81 */     state.putInfo("source", urlStr);
/*  82 */     return state;
/*     */   }
/*     */ 
/*     */   private String getPath(String savePath, String filename, String suffix)
/*     */   {
/*  87 */     return PathFormat.parse(savePath + suffix, filename);
/*     */   }
/*     */ 
/*     */   private boolean validHost(String hostname)
/*     */   {
/*     */     try {
/*  93 */       InetAddress ip = InetAddress.getByName(hostname);
/*     */ 
/*  95 */       if (ip.isSiteLocalAddress())
/*  96 */         return false;
/*     */     }
/*     */     catch (UnknownHostException e) {
/*  99 */       return false;
/*     */     }
/*     */ 
/* 102 */     return !this.filters.contains(hostname);
/*     */   }
/*     */ 
/*     */   private boolean validContentState(int code)
/*     */   {
/* 108 */     return 200 == code;
/*     */   }
/*     */ 
/*     */   private boolean validFileType(String type)
/*     */   {
/* 114 */     return this.allowTypes.contains(type);
/*     */   }
/*     */ 
/*     */   private boolean validFileSize(int size)
/*     */   {
/* 119 */     return size < this.maxSize;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.ueditor.hunter.ImageHunter
 * JD-Core Version:    0.6.0
 */