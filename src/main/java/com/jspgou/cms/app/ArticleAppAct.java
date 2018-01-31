/*     */ package com.jspgou.cms.app;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopArticle;
/*     */ import com.jspgou.cms.entity.ShopArticleContent;
/*     */ import com.jspgou.cms.manager.ShopArticleMng;
/*     */ import com.jspgou.cms.web.SiteUtils;
/*     */ import com.jspgou.common.util.ConvertMapToJson;
/*     */ import com.jspgou.common.util.StrUtils;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ArticleAppAct
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ShopArticleMng shopArticleMng;
/*     */ 
/*     */   @RequestMapping({"/api/articleList.jspx"})
/*     */   public void articleList(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/*  40 */     Map map = new HashMap();
/*  41 */     Website web = SiteUtils.getWeb(request);
/*  42 */     String channelId = request.getParameter("channelId");
/*  43 */     String firstResult = request.getParameter("firstResult");
/*  44 */     String maxResults = request.getParameter("maxResult");
/*  45 */     String callback = request.getParameter("callback");
/*  46 */     String result = "00";
/*  47 */     Long channel_id = null;
/*  48 */     int first_result = 0;
/*  49 */     if (StringUtils.isNotBlank(channelId)) {
/*  50 */       channel_id = Long.valueOf(Long.parseLong(channelId));
/*     */     }
/*  52 */     if (StringUtils.isNotBlank(firstResult)) {
/*  53 */       first_result = Integer.parseInt(firstResult);
/*     */     }
/*     */ 
/*  56 */     if (StringUtils.isNotBlank(maxResults)) {
/*  57 */       int i = Integer.parseInt(maxResults);
/*     */     }
/*  59 */     List list = this.shopArticleMng.getListForTag(web.getId(), 
/*  60 */       Integer.valueOf(Integer.parseInt(channelId)), Integer.parseInt(firstResult), 
/*  61 */       Integer.parseInt(maxResults));
/*  62 */     if (list != null) {
/*  63 */       if (list.size() > 0) {
/*  64 */         result = "01";
/*  65 */         map.put("pd", getarticleJson(list));
/*     */       } else {
/*  67 */         result = "02";
/*     */       }
/*     */     }
/*     */     else {
/*  71 */       result = "02";
/*     */     }
/*  73 */     map.put("result", result);
/*  74 */     if (!StringUtils.isBlank(callback))
/*  75 */       ResponseUtils.renderJson(response, callback + "(" + 
/*  76 */         ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
/*     */     else
/*  78 */       ResponseUtils.renderJson(response, 
/*  79 */         ConvertMapToJson.buildJsonBody(map, 0, false));
/*     */   }
/*     */ 
/*     */   private String getarticleJson(List<ShopArticle> beanList)
/*     */     throws JSONException
/*     */   {
/*  86 */     JSONObject o = new JSONObject();
/*  87 */     JSONArray arr = new JSONArray();
/*  88 */     for (ShopArticle article : beanList) {
/*  89 */       o.put("id", article.getId());
/*  90 */       o.put("title", article.getTitle());
/*  91 */       o.put("time", article.getPublishTime());
/*  92 */       o.put("content", StrUtils.trimHtml2Txt(article.getArticleContent().getContent()));
/*  93 */       arr.put(o);
/*     */     }
/*  95 */     return arr.toString();
/*     */   }
/*     */ 
/*     */   public void article(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 104 */     Map map = new HashMap();
/* 105 */     String articleId = request.getParameter("articleId");
/* 106 */     String result = "00";
/* 107 */     if (StringUtils.isNotBlank(articleId)) {
/* 108 */       ShopArticle article = this.shopArticleMng.findById(
/* 109 */         Long.valueOf(Long.parseLong(articleId)));
/* 110 */       if (article != null) {
/* 111 */         result = "01";
/* 112 */         map.put("pd", getArticleJson(article));
/*     */       } else {
/* 114 */         result = "02";
/*     */       }
/*     */     } else {
/* 117 */       result = "02";
/*     */     }
/* 119 */     map.put("result", result);
/*     */   }
/*     */ 
/*     */   private String getArticleJson(ShopArticle article) throws JSONException {
/* 123 */     JSONObject o = new JSONObject();
/* 124 */     o.put("id", article.getId());
/* 125 */     o.put("title", article.getTitle());
/* 126 */     o.put("time", article.getPublishTime());
/* 127 */     o.put("content", StrUtils.trimHtml2Txt(article.getArticleContent().getContent()));
/* 128 */     return o.toString();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.app.ArticleAppAct
 * JD-Core Version:    0.6.0
 */