/*     */ package com.jspgou.cms.app;
/*     */ 
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductExt;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.web.SiteUtils;
/*     */ import com.jspgou.common.util.Apputil;
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
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ProductAppAct
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @RequestMapping({"/api/product.jspx"})
/*     */   public void product(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  40 */     Map map = new HashMap();
/*  41 */     String productId = request.getParameter("productId");
/*  42 */     String callback = request.getParameter("callback");
/*  43 */     Product product = null;
/*  44 */     String result = "00";
/*  45 */     if (StringUtils.isNotBlank(productId)) {
/*  46 */       product = this.productMng.findById(Long.valueOf(Long.parseLong(productId)));
/*     */     }
/*  48 */     if (product != null) {
/*  49 */       map.put("product", beantoJson(product));
/*  50 */       result = product == null ? "02" : "01";
/*     */     } else {
/*  52 */       result = "02";
/*     */     }
/*  54 */     map.put("result", result);
/*  55 */     if (!StringUtils.isBlank(callback))
/*  56 */       ResponseUtils.renderJson(response, callback + "(" + 
/*  57 */         ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
/*     */     else
/*  59 */       ResponseUtils.renderJson(response, 
/*  60 */         ConvertMapToJson.buildJsonBody(map, 0, false));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/producttext.jspx"})
/*     */   public void producttext(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  72 */     Map map = new HashMap();
/*  73 */     String productId = request.getParameter("productId");
/*  74 */     String callback = request.getParameter("callback");
/*  75 */     Product product = null;
/*  76 */     String result = "00";
/*  77 */     if (StringUtils.isNotBlank(productId)) {
/*  78 */       product = this.productMng.findById(Long.valueOf(Long.parseLong(productId)));
/*     */     }
/*  80 */     if (product != null) {
/*  81 */       map.put("product", beantext(product));
/*  82 */       result = result == null ? "02" : "01";
/*     */     }
/*     */     else {
/*  85 */       result = "02";
/*     */     }
/*  87 */     map.put("result", result);
/*  88 */     if (!StringUtils.isBlank(callback))
/*  89 */       ResponseUtils.renderJson(response, callback + "(" + 
/*  90 */         ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
/*     */     else
/*  92 */       ResponseUtils.renderJson(response, 
/*  93 */         ConvertMapToJson.buildJsonBody(map, 0, false));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/producttext1.jspx"})
/*     */   public void producttext1(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 104 */     Map map = new HashMap();
/* 105 */     String productId = request.getParameter("productId");
/* 106 */     String callback = request.getParameter("callback");
/* 107 */     Product product = null;
/* 108 */     String result = "00";
/* 109 */     if (StringUtils.isNotBlank("productId")) {
/* 110 */       product = this.productMng.findById(Long.valueOf(Long.parseLong(productId)));
/*     */     }
/* 112 */     if (product != null) {
/* 113 */       map.put("product", beantext1(product));
/* 114 */       result = product == null ? "02" : "01";
/*     */     } else {
/* 116 */       result = "02";
/*     */     }
/* 118 */     map.put("result", result);
/* 119 */     if (!StringUtils.isBlank(callback))
/* 120 */       ResponseUtils.renderJson(response, callback + "(" + 
/* 121 */         ConvertMapToJson.buildJsonBody(map, 0, false) + ")");
/*     */     else
/* 123 */       ResponseUtils.renderJson(response, 
/* 124 */         ConvertMapToJson.buildJsonBody(map, 0, false));
/*     */   }
/*     */ 
/*     */   private String beantoJson(Product product)
/*     */   {
/* 129 */     JSONObject o = new JSONObject();
/*     */     try {
/* 131 */       o.put("id", product.getId());
/* 132 */       o.put("name", product.getName());
/* 133 */       o.put("coverImg", product.getCoverImg());
/*     */ 
/* 135 */       if (product.getBrand() != null) {
/* 136 */         o.put("brandname", product.getBrandName());
/* 137 */         o.put("brandId", product.getBrandId());
/*     */       } else {
/* 139 */         o.put("brandname", "");
/* 140 */         o.put("brandId", "");
/*     */       }
/*     */ 
/* 143 */       if (product.getPictures() != null)
/* 144 */         o.put("picture", product.getPictures());
/*     */       else {
/* 146 */         o.put("picture", "");
/*     */       }
/*     */ 
/* 149 */       if (product.getFashions() != null) {
/* 150 */         o.put("salePrice", product.getProductFashion().getSalePrice());
/* 151 */         o.put("marketPrice", product.getProductFashion()
/* 152 */           .getMarketPrice());
/* 153 */         o.put("saleCount", product.getProductFashion().getSaleCount());
/*     */       } else {
/* 155 */         o.put("sale", "");
/* 156 */         o.put("marketPrice", "");
/* 157 */         o.put("saleCount", "");
/*     */       }
/*     */ 
/* 160 */       o.put("code", product.getProductExt().getCode());
/*     */     }
/*     */     catch (JSONException e)
/*     */     {
/* 164 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 167 */     return o.toString();
/*     */   }
/*     */ 
/*     */   private String beantext(Product product) {
/* 171 */     JSONObject o = new JSONObject();
/*     */     try
/*     */     {
/* 174 */       o.put("id", product.getId());
/*     */ 
/* 176 */       o.put("text", StrUtils.trimHtml2Txt(product.getText()));
/*     */     }
/*     */     catch (JSONException e) {
/* 179 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 182 */     return o.toString();
/*     */   }
/*     */ 
/*     */   private String beantext1(Product product) {
/* 186 */     JSONObject o = new JSONObject();
/*     */     try {
/* 188 */       o.put("id", product.getId());
/* 189 */       o.put("text1", StrUtils.trimHtml2Txt(product.getText1()));
/*     */     } catch (JSONException e) {
/* 191 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 194 */     return o.toString();
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/productlist.jspx"})
/*     */   public void productlist(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 205 */     Map map = new HashMap();
/* 206 */     Website site = SiteUtils.getWeb(request);
/* 207 */     String result = "00";
/* 208 */     JSONArray jsonArray = null;
/* 209 */     Integer ctgId = Apputil.getRequestInteger(request.getParameter("ctgId"));
/* 210 */     Long tagId = Apputil.getRequestLong(request.getParameter("tagId"));
/* 211 */     Boolean isRecommend = Apputil.getRequestBoolean(request
/* 212 */       .getParameter("isRecommend"));
/* 213 */     Boolean isSpecial = Apputil.getRequestBoolean(request
/* 214 */       .getParameter("isSpecial"));
/* 215 */     Boolean isHostSale = Apputil.getRequestBoolean(request
/* 216 */       .getParameter("isHostSale"));
/* 217 */     Boolean isNewProduct = Apputil.getRequestBoolean(request
/* 218 */       .getParameter("isNewProduct"));
/* 219 */     Integer firstResult = Apputil.getfirstResult(request
/* 220 */       .getParameter("firsResult"));
/* 221 */     Integer maxResults = Apputil.getmaxResults(request
/* 222 */       .getParameter("maxResults"));
/* 223 */     String callback = request.getParameter("callback");
/*     */ 
/* 225 */     List list = this.productMng.getListForTag(site.getId(), ctgId, 
/* 226 */       tagId, isRecommend, isSpecial, isHostSale, isNewProduct, 
/* 227 */       firstResult.intValue(), maxResults.intValue());
/* 228 */     if (list != null) {
/* 229 */       if (list.size() > 0) {
/* 230 */         result = "01";
/*     */ 
/* 232 */         jsonArray = new JSONArray();
/* 233 */         for (int i = 0; i < list.size(); i++)
/* 234 */           jsonArray.put(i, convertToJson((Product)list.get(i)));
/*     */       }
/*     */       else {
/* 237 */         result = "02";
/*     */       }
/*     */     }
/* 240 */     else result = "02";
/*     */ 
/* 242 */     map.put("result", result);
/*     */ 
/* 251 */     ResponseUtils.renderJson(response, jsonArray.toString());
/*     */   }
/*     */ 
/*     */   private String getproductJson(List<Product> beanlist) throws JSONException {
/* 255 */     JSONObject o = new JSONObject();
/* 256 */     JSONArray arr = new JSONArray();
/* 257 */     for (Product product : beanlist) {
/* 258 */       o.put("id", product.getId());
/* 259 */       o.put("name", product.getName());
/* 260 */       o.put("coverImg", product.getCoverImg());
/* 261 */       arr.put(o);
/*     */     }
/* 263 */     return arr.toString();
/*     */   }
/*     */ 
/*     */   private JSONObject convertToJson(Product product) throws JSONException
/*     */   {
/* 268 */     JSONObject o = new JSONObject();
/* 269 */     o.put("id", product.getId());
/* 270 */     o.put("name", product.getName());
/* 271 */     o.put("coverImg", product.getCoverImg());
/* 272 */     return o;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.app.ProductAppAct
 * JD-Core Version:    0.6.0
 */