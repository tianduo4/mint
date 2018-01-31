/*     */ package com.jspgou.cms.app;
/*     */ 
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.manager.CategoryMng;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import java.util.ArrayList;
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
/*     */ public class CategoryAppAct
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private CategoryMng categoryMng;
/*     */ 
/*     */   @RequestMapping({"/api/categoryList.jspx"})
/*     */   public void categoryList(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/*  45 */     Map map = new HashMap();
/*  46 */     String parentId = request.getParameter("parentId");
/*  47 */     String callback = request.getParameter("callback");
/*     */ 
/*  49 */     Website web = SiteUtils.getWeb(request);
/*  50 */     String result = "00";
/*  51 */     JSONArray jsonArray = null;
/*     */     List list;
/*  53 */     if (StringUtils.isNotBlank(parentId)) {
/*  54 */       Category category = this.categoryMng.findById(Integer.valueOf(Integer.parseInt(parentId)));
/*  55 */       if (category != null)
/*  56 */         list = new ArrayList(category.getChild());
/*     */       else
/*  58 */         list = new ArrayList();
/*     */     }
/*     */     else {
/*  61 */       list = this.categoryMng.getTopListForTag(web.getId());
/*     */     }
/*     */ 
/*  64 */     if (list != null) {
/*  65 */       if (list.size() > 0) {
/*  66 */         result = "01";
/*     */ 
/*  68 */         jsonArray = new JSONArray();
/*  69 */         if ((list != null) && (list.size() > 0))
/*  70 */           for (int i = 0; i < list.size(); i++)
/*  71 */             jsonArray.put(i, convertToJson((Category)list.get(i)));
/*     */       }
/*     */       else
/*     */       {
/*  75 */         result = "02";
/*     */       }
/*     */     }
/*  78 */     else result = "02";
/*     */ 
/*  80 */     map.put("result", result);
/*  81 */     ResponseUtils.renderJson(response, jsonArray.toString());
/*     */   }
/*     */ 
/*     */   private String getcategoryJson(List<Category> beanList)
/*     */     throws JSONException
/*     */   {
/*  93 */     JSONArray arr = new JSONArray();
/*     */ 
/*  95 */     for (Category category : beanList) {
/*  96 */       JSONObject o = new JSONObject();
/*  97 */       o.put("id", category.getId());
/*  98 */       o.put("name", category.getName());
/*  99 */       o.put("pcImgUrl", category.getImagePath());
/* 100 */       arr.put(o);
/*     */     }
/* 102 */     return arr.toString();
/*     */   }
/*     */ 
/*     */   private JSONObject convertToJson(Category category) throws JSONException
/*     */   {
/* 107 */     JSONObject o = new JSONObject();
/* 108 */     o.put("id", category.getId());
/* 109 */     o.put("name", category.getName());
/* 110 */     o.put("pcImgUrl", category.getImagePath());
/*     */ 
/* 112 */     return o;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.app.CategoryAppAct
 * JD-Core Version:    0.6.0
 */