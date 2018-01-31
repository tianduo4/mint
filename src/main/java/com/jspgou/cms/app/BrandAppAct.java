/*    */ package com.jspgou.cms.app;
/*    */ 
/*    */ import com.jspgou.cms.entity.Brand;
/*    */ import com.jspgou.cms.manager.BrandMng;
/*    */ import com.jspgou.common.util.ConvertMapToJson;
/*    */ import com.jspgou.common.web.ResponseUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.web.SiteUtils;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ public class BrandAppAct
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private BrandMng brandMng;
/*    */ 
/*    */   @RequestMapping({"/api/brandList.jspx"})
/*    */   public void brandList(HttpServletRequest request, HttpServletResponse response)
/*    */     throws JSONException
/*    */   {
/* 38 */     Map map = new HashMap();
/* 39 */     Website web = SiteUtils.getWeb(request);
/* 40 */     String firstResult = request.getParameter("firsrResult");
/* 41 */     String maxResults = request.getParameter("maxResults");
/* 42 */     String callback = request.getParameter("callback");
/* 43 */     int first_result = 0; int max_results = 0;
/* 44 */     String result = "00";
/* 45 */     if (StringUtils.isNotBlank(firstResult)) {
/* 46 */       first_result = Integer.parseInt(firstResult);
/*    */     }
/* 48 */     if (StringUtils.isNotBlank(maxResults)) {
/* 49 */       max_results = Integer.parseInt(maxResults);
/*    */     }
/* 51 */     List list = this.brandMng.getListForTag(web.getId(), first_result, 
/* 52 */       max_results);
/* 53 */     if (list != null) {
/* 54 */       if (list.size() > 0) {
/* 55 */         result = "01";
/* 56 */         map.put("pd", getBrandListJson(list));
/*    */       } else {
/* 58 */         result = "02";
/*    */       }
/*    */     }
/* 61 */     else result = "03";
/*    */ 
/* 63 */     map.put("result", result);
/* 64 */     if (!StringUtils.isBlank(callback))
/* 65 */       ResponseUtils.renderJson(response, callback + "(" + ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
/*    */     else
/* 67 */       ResponseUtils.renderJson(response, ConvertMapToJson.buildJsonBody(map, 0, false));
/*    */   }
/*    */ 
/*    */   private String getBrandListJson(List<Brand> beanList)
/*    */     throws JSONException
/*    */   {
/* 73 */     JSONArray arr = new JSONArray();
/* 74 */     JSONObject o = new JSONObject();
/* 75 */     for (Brand brand : beanList) {
/* 76 */       o.put("id", brand.getId());
/* 77 */       o.put("name", brand.getName());
/* 78 */       o.put("pcImgUrl", brand.getLogoPath());
/*    */     }
/*    */ 
/* 81 */     return arr.toString();
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.app.BrandAppAct
 * JD-Core Version:    0.6.0
 */