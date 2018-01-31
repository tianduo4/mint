/*    */ package com.jspgou.common.httpClient;
/*    */ 
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import org.apache.commons.httpclient.Header;
/*    */ 
/*    */ public class HttpResponse
/*    */ {
/*    */   private Header[] responseHeaders;
/*    */   private String stringResult;
/*    */   private byte[] byteResult;
/*    */ 
/*    */   public Header[] getResponseHeaders()
/*    */   {
/* 32 */     return this.responseHeaders;
/*    */   }
/*    */ 
/*    */   public void setResponseHeaders(Header[] responseHeaders) {
/* 36 */     this.responseHeaders = responseHeaders;
/*    */   }
/*    */ 
/*    */   public byte[] getByteResult() {
/* 40 */     if (this.byteResult != null) {
/* 41 */       return this.byteResult;
/*    */     }
/* 43 */     if (this.stringResult != null) {
/* 44 */       return this.stringResult.getBytes();
/*    */     }
/* 46 */     return null;
/*    */   }
/*    */ 
/*    */   public void setByteResult(byte[] byteResult) {
/* 50 */     this.byteResult = byteResult;
/*    */   }
/*    */ 
/*    */   public String getStringResult() throws UnsupportedEncodingException {
/* 54 */     if (this.stringResult != null) {
/* 55 */       return this.stringResult;
/*    */     }
/* 57 */     if (this.byteResult != null) {
/* 58 */       return new String(this.byteResult, "utf-8");
/*    */     }
/* 60 */     return null;
/*    */   }
/*    */ 
/*    */   public void setStringResult(String stringResult) {
/* 64 */     this.stringResult = stringResult;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.httpClient.HttpResponse
 * JD-Core Version:    0.6.0
 */