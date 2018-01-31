/*     */ package com.jspgou.cms.app;
/*     */ 
/*     */ import com.jspgou.cms.entity.KeyWord;
/*     */ import com.jspgou.cms.lucene.LuceneProduct;
/*     */ import com.jspgou.cms.lucene.LuceneProductSvc;
/*     */ import com.jspgou.cms.manager.KeyWordMng;
/*     */ import com.jspgou.cms.web.SiteUtils;
/*     */ import com.jspgou.common.util.Apputil;
/*     */ import com.jspgou.common.util.ConvertMapToJson;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.IOException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.lucene.queryParser.ParseException;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ public class SearchAppAct
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ServletContext servletContext;
/*     */ 
/*     */   @Autowired
/*     */   private LuceneProductSvc luceneProductSvc;
/*     */ 
/*     */   @Autowired
/*     */   private KeyWordMng keywordMng;
/*     */ 
/*     */   @RequestMapping({"/api/search.jspx"})
/*     */   public void search(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ParseException, JSONException
/*     */   {
/*  47 */     Website web = SiteUtils.getWeb(request);
/*  48 */     String result = "00";
/*  49 */     Map map = new HashMap();
/*  50 */     String query = request.getParameter("query");
/*  51 */     Long brandId = Apputil.getRequestLong(request.getParameter("brandId"));
/*  52 */     Long ctgId = Apputil.getRequestLong(request.getParameter("ctgId"));
/*  53 */     Date start = Apputil.getRequestDate(request.getParameter("StartDate"));
/*  54 */     Date end = Apputil.getRequestDate(request.getParameter("EndDate"));
/*  55 */     Integer orderBy = Apputil.getRequestInteger(request
/*  56 */       .getParameter("orderBy"));
/*  57 */     Integer firstResult = Apputil.getfirstResult(request
/*  58 */       .getParameter("firstResult"));
/*  59 */     Integer maxResults = Apputil.getmaxResults(request
/*  60 */       .getParameter("maxResults"));
/*  61 */     String path = this.servletContext.getRealPath("/WEB-INF/lucene");
/*  62 */     List list = this.luceneProductSvc.getlist(path, query, 
/*  63 */       web.getId(), ctgId, brandId, start, end, orderBy.intValue(), firstResult.intValue(), 
/*  64 */       maxResults.intValue());
/*  65 */     if (list != null) {
/*  66 */       if (list.size() > 0) {
/*  67 */         result = "01";
/*  68 */         map.put("pd", getLuceneProductJson(list));
/*     */       } else {
/*  70 */         result = "02";
/*     */       }
/*     */     }
/*     */     else {
/*  74 */       result = "02";
/*     */     }
/*  76 */     map.put("result", result);
/*     */   }
/*     */ 
/*     */   private String getLuceneProductJson(List<LuceneProduct> beanList) throws JSONException
/*     */   {
/*  81 */     JSONArray arr = new JSONArray();
/*  82 */     JSONObject o = new JSONObject();
/*  83 */     for (LuceneProduct product : beanList) {
/*  84 */       o.put("id", product.getId());
/*  85 */       o.put("coverImgUrl", product.getCoverImgUrl());
/*  86 */       o.put("name", product.getName());
/*  87 */       o.put("salePrice", product.getSalePrice());
/*  88 */       o.put("marketPrice", product.getMarketPrice());
/*  89 */       o.put("brandId", product.getBrandId());
/*  90 */       o.put("brandName", product.getBrandName());
/*  91 */       arr.put(o);
/*     */     }
/*     */ 
/*  94 */     return arr.toString();
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/keyword.jspx"})
/*     */   public void keyword(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 105 */     Map map = new HashMap();
/* 106 */     String result = "00";
/* 107 */     String callback = request.getParameter("callback");
/* 108 */     Integer count = 
/* 109 */       Apputil.getRequestInteger(request.getParameter("count"));
/* 110 */     List beanList = this.keywordMng.findKeyWord(count);
/* 111 */     if (beanList != null) {
/* 112 */       if (beanList.size() > 0) {
/* 113 */         result = "01";
/* 114 */         map.put("pd", getkeywordJson(beanList));
/*     */       } else {
/* 116 */         result = "02";
/*     */       }
/*     */     }
/* 119 */     else result = "02";
/*     */ 
/* 121 */     if (StringUtils.isBlank(callback)) {
/* 122 */       ResponseUtils.renderJson(response, callback + "(" + 
/* 123 */         ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
/*     */     }
/*     */     else
/* 126 */       ResponseUtils.renderJson(response, 
/* 127 */         ConvertMapToJson.buildJsonBody(map, 0, false));
/*     */   }
/*     */ 
/*     */   private String getkeywordJson(List<KeyWord> beanList) throws JSONException
/*     */   {
/* 132 */     JSONArray arr = new JSONArray();
/* 133 */     JSONObject o = new JSONObject();
/* 134 */     for (KeyWord keyWord : beanList) {
/* 135 */       o.put("keyword", keyWord.getKeyword());
/* 136 */       arr.put(o);
/*     */     }
/*     */ 
/* 139 */     return arr.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.app.SearchAppAct
 * JD-Core Version:    0.6.0
 */