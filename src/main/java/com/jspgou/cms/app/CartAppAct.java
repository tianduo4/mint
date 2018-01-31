/*     */ package com.jspgou.cms.app;
/*     */ 
/*     */ import com.jspgou.cms.entity.Address;
/*     */ import com.jspgou.cms.entity.Cart;
/*     */ import com.jspgou.cms.entity.CartItem;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopMemberAddress;
/*     */ import com.jspgou.cms.manager.AddressMng;
/*     */ import com.jspgou.cms.manager.ApiUtilMng;
/*     */ import com.jspgou.cms.manager.ProductFashionMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShopMemberAddressMng;
/*     */ import com.jspgou.cms.service.ShoppingSvc;
/*     */ import com.jspgou.common.util.Apputil;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class CartAppAct
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private ApiUtilMng apiUtilMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShoppingSvc shoppingSvc;
/*     */ 
/*     */   @Autowired
/*     */   private ProductMng productMng;
/*     */ 
/*     */   @Autowired
/*     */   private ProductFashionMng productFashionMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberAddressMng shopMemberAddressMng;
/*     */ 
/*     */   @Autowired
/*     */   private AddressMng addressMng;
/*     */ 
/*     */   @RequestMapping({"/api/user/cart.jspx"})
/*     */   public void shoppingCart(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws JSONException
/*     */   {
/*  47 */     JSONArray arr = new JSONArray();
/*  48 */     JSONObject o = new JSONObject();
/*  49 */     ShopMember member = this.apiUtilMng.findbysessionKey(request);
/*  50 */     if (member != null) {
/*  51 */       Cart cart = this.shoppingSvc.getCart(member, request, response);
/*  52 */       for (CartItem item : cart.getItems()) {
/*  53 */         o.put("name", item.getProduct().getName());
/*  54 */         o.put("imgUrl", item.getProduct().getCoverImgUrl());
/*  55 */         o.put("marketPrice", item.getProduct().getMarketPrice());
/*  56 */         o.put("costPrice", item.getProduct().getCostPrice());
/*  57 */         o.put("status", item.getProduct().getStatus());
/*  58 */         arr.put(o);
/*     */       }
/*     */     }
/*  61 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
/*  62 */       arr.toString(), "/api/user/cart.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/addcarttem.jspx"})
/*     */   public void addcarttem(HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws JSONException
/*     */   {
/*  74 */     JSONObject json = new JSONObject();
/*  75 */     Website web = SiteUtils.getWeb(request);
/*  76 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/*  77 */     if (user != null) {
/*  78 */       Long productId = Apputil.getRequestLong(request
/*  79 */         .getParameter("productId"));
/*  80 */       Integer productAmount = Apputil.getRequestInteger("productAmount");
/*  81 */       Long fashId = Apputil.getRequestLong("fashId");
/*  82 */       Product product = this.productMng.findById(productId);
/*     */ 
/*  84 */       if ((product.getStatus() != null) && (product.getStatus().intValue() == Product.ON_SALE_STATUS)) {
/*  85 */         if (fashId == null) {
/*  86 */           if (productAmount.intValue() > product.getStockCount().intValue()) {
/*  87 */             json.put("status", 2);
/*     */           } else {
/*  89 */             this.shoppingSvc.collectAddToCart(product, fashId, null, 
/*  90 */               productAmount.intValue(), true, user, web, request, 
/*  91 */               response);
/*  92 */             json.put("status", 1);
/*     */           }
/*     */         } else {
/*  95 */           ProductFashion productFashion = this.productFashionMng
/*  96 */             .findById(fashId);
/*  97 */           if (productAmount.intValue() > product.getStockCount().intValue()) {
/*  98 */             json.put("status", 2);
/*     */           } else {
/* 100 */             this.shoppingSvc.collectAddToCart(product, fashId, null, 
/* 101 */               productAmount.intValue(), true, user, web, request, 
/* 102 */               response);
/* 103 */             json.put("status", 1);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/* 108 */       else if ((product.getStatus() == null) || (product.getStatus().intValue() != Product.ON_SALE_STATUS)) {
/* 109 */         json.put("status", 3);
/*     */       }
/*     */     }
/*     */ 
/* 113 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
/* 114 */       json.toString(), "/api/user/cart.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/addresslist.jspx"})
/*     */   public void addresslist(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 126 */     JSONArray arr = new JSONArray();
/* 127 */     JSONObject o = new JSONObject();
/* 128 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/* 129 */     if (user != null) {
/* 130 */       List list = this.shopMemberAddressMng.getList(user
/* 131 */         .getId());
/* 132 */       for (ShopMemberAddress address : list) {
/* 133 */         o.put("username", address.getUsername());
/* 134 */         o.put("detailaddress", address.getProvince() + address.getCity() + address.getCountry() + address.getDetailaddress());
/* 135 */         o.put("tel", address.getTel());
/* 136 */         arr.put(o);
/*     */       }
/*     */     }
/*     */ 
/* 140 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
/* 141 */       arr.toString(), "/api/user/addresslist.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/addressdefault.jspx"})
/*     */   public void addressdefault(HttpServletRequest request, HttpServletResponse response)
/*     */     throws JSONException
/*     */   {
/* 152 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/* 153 */     JSONObject o = new JSONObject();
/* 154 */     if (user != null) {
/* 155 */       ShopMemberAddress address = this.shopMemberAddressMng
/* 156 */         .findByMemberDefault(user.getId(), Boolean.valueOf(true));
/* 157 */       o.put("username", address.getUsername());
/* 158 */       o.put("tel", address.getTel());
/* 159 */       o.put("detailaddress", address.getProvince() + address.getCity() + address.getCountry() + address.getDetailaddress());
/*     */     }
/*     */ 
/* 163 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
/* 164 */       o.toString(), "/api/user/addressdefault.jspx", request));
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/api/user/addresssave.jspx"})
/*     */   public void addressSave(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 174 */     JSONObject o = new JSONObject();
/* 175 */     ShopMember user = this.apiUtilMng.findbysessionKey(request);
/* 176 */     String username = request.getParameter("username");
/* 177 */     String detailaddress = request.getParameter("detailaddress");
/* 178 */     String tel = request.getParameter("tel");
/* 179 */     String postCode = request.getParameter("postCode");
/* 180 */     String provinceId = request.getParameter("provinceId");
/* 181 */     String cityId = request.getParameter("cityId");
/* 182 */     String countryId = request.getParameter("countryId");
/*     */ 
/* 184 */     if (user != null) {
/* 185 */       ShopMemberAddress bean = new ShopMemberAddress();
/* 186 */       bean.setUsername(username);
/* 187 */       bean.setProvinceId(this.addressMng.findById(Long.valueOf(Long.parseLong(provinceId)))
/* 188 */         .getId());
/* 189 */       bean.setProvince(this.addressMng.findById(Long.valueOf(Long.parseLong(provinceId)))
/* 190 */         .getName());
/* 191 */       bean.setCity(this.addressMng.findById(Long.valueOf(Long.parseLong(cityId))).getName());
/* 192 */       bean.setCityId(this.addressMng.findById(Long.valueOf(Long.parseLong(cityId))).getId());
/* 193 */       bean.setCountry(this.addressMng.findById(Long.valueOf(Long.parseLong(countryId)))
/* 194 */         .getName());
/* 195 */       bean.setCountryId(this.addressMng.findById(Long.valueOf(Long.parseLong(countryId)))
/* 196 */         .getId());
/* 197 */       bean.setDetailaddress(detailaddress);
/* 198 */       bean.setTel(tel);
/* 199 */       bean.setPostCode(postCode);
/* 200 */       this.shopMemberAddressMng.save(bean);
/*     */     }
/* 202 */     ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(o.toString(), "/api/user/addresssave.jspx", request));
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.app.CartAppAct
 * JD-Core Version:    0.6.0
 */