/*    */ package com.jspgou.core.web.front;
/*    */ 
/*    */ public class URLHelper$URLInfo
/*    */ {
/*    */   private String[] paths;
/*    */   private int pageNo;
/*    */   private String[] params;
/*    */   private String queryString;
/*    */   private String urlPrefix;
/*    */   private String urlSuffix;
/*    */ 
/*    */   public URLHelper$URLInfo(String[] paths, int pageNo, String[] params, String urlPrefix, String urlSuffix, String queryString)
/*    */   {
/* 29 */     this.paths = paths;
/* 30 */     this.pageNo = pageNo;
/* 31 */     this.params = params;
/* 32 */     this.urlPrefix = urlPrefix;
/* 33 */     this.urlSuffix = urlSuffix;
/* 34 */     this.queryString = queryString;
/*    */   }
/*    */ 
/*    */   public String[] getPaths() {
/* 38 */     return this.paths;
/*    */   }
/*    */ 
/*    */   public void setPaths(String[] paths) {
/* 42 */     this.paths = paths;
/*    */   }
/*    */ 
/*    */   public int getPageNo() {
/* 46 */     return this.pageNo;
/*    */   }
/*    */ 
/*    */   public void setPageNo(int pageNo) {
/* 50 */     this.pageNo = pageNo;
/*    */   }
/*    */ 
/*    */   public String[] getParams() {
/* 54 */     return this.params;
/*    */   }
/*    */ 
/*    */   public void setParams(String[] params) {
/* 58 */     this.params = params;
/*    */   }
/*    */ 
/*    */   public String getUrlPrefix() {
/* 62 */     return this.urlPrefix;
/*    */   }
/*    */ 
/*    */   public void setUrlPrefix(String urlPrefix) {
/* 66 */     this.urlPrefix = urlPrefix;
/*    */   }
/*    */ 
/*    */   public String getUrlSuffix() {
/* 70 */     return this.urlSuffix;
/*    */   }
/*    */ 
/*    */   public void setUrlSuffix(String urlSuffix) {
/* 74 */     this.urlSuffix = urlSuffix;
/*    */   }
/*    */ 
/*    */   public String getQueryString() {
/* 78 */     return this.queryString;
/*    */   }
/*    */ 
/*    */   public void setQueryString(String queryString) {
/* 82 */     this.queryString = queryString;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.web.front.URLHelper.URLInfo
 * JD-Core Version:    0.6.0
 */