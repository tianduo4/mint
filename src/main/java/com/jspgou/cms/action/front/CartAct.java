/*     */ package com.jspgou.cms.action.front;
/*     */ 
/*     */ import com.jspgou.cms.entity.Cart;
/*     */ import com.jspgou.cms.entity.CartItem;
/*     */ import com.jspgou.cms.entity.Category;
/*     */ import com.jspgou.cms.entity.Order;
/*     */ import com.jspgou.cms.entity.OrderItem;
/*     */ import com.jspgou.cms.entity.PopularityGroup;
/*     */ import com.jspgou.cms.entity.PopularityItem;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductFashion;
/*     */ import com.jspgou.cms.entity.Shipping;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.manager.AddressMng;
/*     */ import com.jspgou.cms.manager.CartItemMng;
/*     */ import com.jspgou.cms.manager.CartMng;
/*     */ import com.jspgou.cms.manager.MemberCouponMng;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.manager.PaymentMng;
/*     */ import com.jspgou.cms.manager.PopularityGroupMng;
/*     */ import com.jspgou.cms.manager.PopularityItemMng;
/*     */ import com.jspgou.cms.manager.ProductFashionMng;
/*     */ import com.jspgou.cms.manager.ProductMng;
/*     */ import com.jspgou.cms.manager.ShippingMng;
/*     */ import com.jspgou.cms.manager.ShopMemberAddressMng;
/*     */ import com.jspgou.cms.service.ShoppingSvc;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class CartAct
/*     */ {
/*  62 */   private static final Logger log = LoggerFactory.getLogger(CartAct.class);
/*     */   private static final String SHOPPING_CART = "tpl.shoppingCart";
/*     */   private static final String CHECKOUT_SHIPPING = "tpl.checkoutShipping";
/*     */   private static final String BUY_NOW = "tpl.buyNow";
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng orderMng;
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
/*     */   private CartItemMng cartItemMng;
/*     */ 
/*     */   @Autowired
/*     */   private CartMng cartMng;
/*     */ 
/*     */   @Autowired
/*     */   private AddressMng addressMng;
/*     */ 
/*     */   @Autowired
/*     */   private PaymentMng paymentMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShippingMng shippingMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberAddressMng shopMemberAddressMng;
/*     */ 
/*     */   @Autowired
/*     */   private MemberCouponMng memberCouponMng;
/*     */ 
/*     */   @Autowired
/*     */   private PopularityGroupMng popularityGroupMng;
/*     */ 
/*     */   @Autowired
/*     */   private PopularityItemMng popularityItemMng;
/*     */ 
/*     */   @RequestMapping({"/cart/shopping_cart.jspx"})
/*     */   public String shoppingCart(String backUrl, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  71 */     ShopMember member = MemberThread.get();
/*  72 */     if (member == null) {
/*  73 */       return "redirect:../login.jspx";
/*     */     }
/*  75 */     Website web = SiteUtils.getWeb(request);
/*  76 */     Cart cart = this.shoppingSvc.getCart(member, request, response);
/*  77 */     List popularityItems = null;
/*  78 */     if (cart != null) {
/*  79 */       popularityItems = this.popularityItemMng.getlist(cart.getId(), null);
/*     */     }
/*  81 */     model.addAttribute("cart", cart);
/*  82 */     if (!StringUtils.isBlank(backUrl)) {
/*  83 */       model.addAttribute("backUrl", backUrl);
/*     */     }
/*  85 */     model.addAttribute("popularityItems", popularityItems);
/*  86 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  87 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.shoppingCart", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/cart/add_orderItem.jspx"})
/*     */   public void addToCart(Long productId, Integer productAmount, Long fashId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws JSONException
/*     */   {
/*  95 */     Website web = SiteUtils.getWeb(request);
/*     */ 
/*  97 */     ShopMember member = MemberThread.get();
/*  98 */     JSONObject json = new JSONObject();
/*  99 */     if (member == null) {
/* 100 */       json.put("status", 0);
/*     */     } else {
/* 102 */       Product product = this.productMng.findById(productId);
/*     */ 
/* 105 */       Integer status = Integer.valueOf(product.getStatus() != null ? product.getStatus().intValue() : 0);
/* 106 */       if (fashId == null) {
/* 107 */         if ((productAmount == null) || (productAmount.intValue() == 0)) {
/* 108 */           json.put("status", 2);
/* 109 */           json.put("error", "商品选择数量为空或者为0，不能购买");
/*     */         }
/* 111 */         else if (productAmount.intValue() > product.getStockCount().intValue()) {
/* 112 */           json.put("status", 2);
/* 113 */           json.put("error", "库存不足");
/* 114 */         } else if (status.intValue() != Product.ON_SALE_STATUS) {
/* 115 */           json.put("status", 2);
/* 116 */           json.put("error", "商品已经下架，不能购买");
/*     */         } else {
/* 118 */           Cart cart = this.shoppingSvc.collectAddToCart(product, fashId, null, productAmount.intValue(), true, member, web, request, response);
/* 119 */           json.put("status", 1);
/* 120 */           json.put("count", cart.getTotalItems());
/*     */         }
/*     */       }
/*     */       else {
/* 124 */         ProductFashion productFashion = this.productFashionMng.findById(fashId);
/* 125 */         if (productAmount.intValue() > productFashion.getStockCount().intValue()) {
/* 126 */           json.put("status", 2);
/* 127 */           json.put("error", productFashion.getAttitude() + "库存不足");
/* 128 */         } else if (status.intValue() != Product.ON_SALE_STATUS) {
/* 129 */           json.put("status", 2);
/* 130 */           json.put("error", "商品已经下架，不能购买");
/*     */         } else {
/* 132 */           Cart cart = this.shoppingSvc.collectAddToCart(product, fashId, null, productAmount.intValue(), true, member, web, request, response);
/* 133 */           json.put("status", 1);
/* 134 */           json.put("count", cart.getTotalItems());
/*     */         }
/*     */       }
/*     */     }
/* 138 */     log.info("add to cart productId={}, count={}", productId, productAmount);
/* 139 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/cart/add_popularity.jspx"})
/*     */   public void addToPopularity(Long popularityId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException
/*     */   {
/* 146 */     Website web = SiteUtils.getWeb(request);
/*     */ 
/* 148 */     ShopMember member = MemberThread.get();
/* 149 */     JSONObject json = new JSONObject();
/* 150 */     if (member == null) {
/* 151 */       json.put("status", 0);
/*     */     }
/* 153 */     else if (getinventory(popularityId)) {
/* 154 */       Cart cart = null;
/* 155 */       for (Product product : this.popularityGroupMng.findById(popularityId).getProducts()) {
/* 156 */         cart = this.shoppingSvc.collectAddToCart(product, null, popularityId, 1, true, member, web, request, response);
/*     */       }
/* 158 */       this.popularityItemMng.save(cart, popularityId);
/* 159 */       json.put("status", 1);
/* 160 */       json.put("count", cart.getTotalItems());
/*     */     } else {
/* 162 */       json.put("status", 2);
/* 163 */       json.put("error", "库存不足");
/*     */     }
/*     */ 
/* 166 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   public boolean getinventory(Long popularityId)
/*     */   {
/* 171 */     for (Product product : this.popularityGroupMng.findById(popularityId).getProducts()) {
/* 172 */       if (1 > product.getStockCount().intValue()) {
/* 173 */         return false;
/*     */       }
/*     */     }
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/cart/add_orderItem1.jspx"})
/*     */   public String orderAddToCart(Long orderId, Boolean isAdd, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws JSONException
/*     */   {
/* 185 */     Website web = SiteUtils.getWeb(request);
/*     */ 
/* 187 */     ShopMember member = MemberThread.get();
/* 188 */     if (member == null) {
/* 189 */       return "redirect:../login.jspx";
/*     */     }
/* 191 */     Order order = this.orderMng.findById(orderId);
/* 192 */     Product product = null;
/* 193 */     Integer productAmount = Integer.valueOf(0);
/* 194 */     Long fashId = null;
/* 195 */     Cart cart = null;
/* 196 */     for (OrderItem item : order.getItems()) {
/* 197 */       product = item.getProduct();
/* 198 */       productAmount = item.getCount();
/* 199 */       if (item.getProductFash() != null) {
/* 200 */         fashId = item.getProductFash().getId();
/*     */       }
/* 202 */       cart = this.shoppingSvc.collectAddToCart(product, fashId, null, productAmount.intValue(), true, member, web, request, response);
/*     */     }
/* 204 */     return "redirect:shopping_cart.jspx";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/cart/ajaxUpdateCartItem.jspx"})
/*     */   public void ajaxUpdateCartItem(Long cartItemId, Integer count, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws JSONException
/*     */   {
/* 212 */     ShopMember member = MemberThread.get();
/* 213 */     JSONObject json = new JSONObject();
/* 214 */     if (member == null) {
/* 215 */       json.put("status", 0);
/*     */     }
/* 217 */     CartItem cartItem = this.cartItemMng.findById(cartItemId);
/* 218 */     cartItem.setCount(count);
/* 219 */     cartItem.setScore(Integer.valueOf(cartItem.getProduct().getScore().intValue() * count.intValue()));
/* 220 */     this.cartItemMng.updateByUpdater(cartItem);
/* 221 */     log.info("update to cartItem cartItemId={}", cartItemId);
/* 222 */     json.put("status", 1);
/* 223 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/cart/ajaxDeleteCartItem.jspx"})
/*     */   public void ajaxDeleteCartItem(Long cartItemId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws JSONException
/*     */   {
/* 231 */     ShopMember member = MemberThread.get();
/* 232 */     JSONObject json = new JSONObject();
/* 233 */     if (member == null) {
/* 234 */       json.put("status", 0);
/*     */     }
/* 236 */     CartItem cartItem = this.cartItemMng.findById(cartItemId);
/* 237 */     Cart cart = cartItem.getCart();
/* 238 */     PopularityGroup popularityGroup = cartItem.getPopularityGroup();
/* 239 */     cart.getItems().remove(cartItem);
/* 240 */     cart.setTotalItems(Integer.valueOf(cart.calTotalItem()));
/* 241 */     this.cartMng.update(cart);
/* 242 */     if ((cart != null) && (popularityGroup != null)) {
/* 243 */       List list = this.cartItemMng.getlist(cart.getId(), popularityGroup.getId());
/* 244 */       list.remove(cartItem);
/* 245 */       for (CartItem item : list) {
/* 246 */         item.setPopularityGroup(null);
/* 247 */         this.cartItemMng.updateByUpdater(item);
/*     */       }
/* 249 */       update(cart, popularityGroup);
/*     */     }
/* 251 */     log.info("delete to cartItem cartItemId={}", cartItemId);
/* 252 */     json.put("status", 1);
/* 253 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   public void update(Cart cart, PopularityGroup popularityGroup)
/*     */   {
/* 259 */     if (popularityGroup != null) {
/* 260 */       PopularityItem popularityItem = this.popularityItemMng.findById(cart.getId(), popularityGroup.getId());
/* 261 */       if (popularityItem != null)
/* 262 */         this.popularityItemMng.deleteById(popularityItem.getId());
/*     */     }
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/cart/checkStockCount.jspx"})
/*     */   public void checkStockCount(Long productId, String productFashionId, Integer count, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws JSONException
/*     */   {
/* 273 */     ShopMember member = MemberThread.get();
/* 274 */     JSONObject json = new JSONObject();
/* 275 */     boolean check = true;
/* 276 */     if (member == null) {
/* 277 */       json.put("status", 0);
/*     */     }
/* 279 */     else if (this.productMng.findById(productId) == null) {
/* 280 */       check = false;
/* 281 */       json.put("status", 2);
/* 282 */       json.put("error", "购物车中含有商品已经被删除的情况。");
/*     */     } else {
/* 284 */       Product product = this.productMng.findById(productId);
/*     */ 
/* 287 */       Integer status = Integer.valueOf(product.getStatus() != null ? product.getStatus().intValue() : 0);
/* 288 */       if (productFashionId.equals("undefined")) {
/* 289 */         if (count.intValue() > product.getStockCount().intValue()) {
/* 290 */           check = false;
/* 291 */           json.put("status", 2);
/* 292 */           json.put("error", product.getName() + "该商品库存不足。");
/* 293 */         } else if (status.intValue() != Product.ON_SALE_STATUS) {
/* 294 */           check = false;
/* 295 */           json.put("status", 2);
/* 296 */           json.put("error", product.getName() + "商品已经下架，不能购买");
/*     */         }
/*     */       }
/* 299 */       else if (this.productFashionMng.findById(Long.valueOf(Long.parseLong(productFashionId))) == null) {
/* 300 */         check = false;
/* 301 */         json.put("status", 2);
/* 302 */         json.put("error", "购物车中含有款式商品已经被删除的情况。");
/*     */       } else {
/* 304 */         ProductFashion productFashion = this.productFashionMng.findById(Long.valueOf(Long.parseLong(productFashionId)));
/* 305 */         if (count.intValue() > productFashion.getStockCount().intValue()) {
/* 306 */           check = false;
/* 307 */           json.put("error", product.getName() + productFashion.getAttitude() + "该款式库存不足。");
/* 308 */           json.put("status", 2);
/* 309 */         } else if (status.intValue() != Product.ON_SALE_STATUS) {
/* 310 */           check = false;
/* 311 */           json.put("status", 2);
/* 312 */           json.put("error", product.getName() + "商品已经下架，不能购买");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 318 */     if (check) {
/* 319 */       json.put("status", 1);
/*     */     }
/* 321 */     log.info("Submit shopping cart productId={}, count={}", productId, count);
/* 322 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/cart/ajaxtotalDeliveryFee.jspx"})
/*     */   public void ajaxtotalDeliveryFee(Long deliveryMethod, Double weight, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */     throws JSONException
/*     */   {
/* 330 */     ShopMember member = MemberThread.get();
/* 331 */     JSONObject json = new JSONObject();
/* 332 */     if (member == null) {
/* 333 */       json.put("status", 0);
/*     */     }
/* 335 */     Shipping shipping = this.shippingMng.findById(deliveryMethod);
/*     */ 
/* 337 */     Double freight = shipping.calPrice(weight);
/* 338 */     json.put("status", 1);
/* 339 */     json.put("freight", freight);
/* 340 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/cart/checkout_shipping.jspx"})
/*     */   public String shippingInput(Long[] cart2Checkbox, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 347 */     Website web = SiteUtils.getWeb(request);
/* 348 */     ShopMember member = MemberThread.get();
/* 349 */     if (member == null) {
/* 350 */       return "redirect:../login.jspx";
/*     */     }
/* 352 */     Cart cart = this.shoppingSvc.getCart(member.getId());
/* 353 */     if (cart == null) {
/* 354 */       return "redirect:shopping_cart.jspx";
/*     */     }
/* 356 */     List popularityItems = null;
/* 357 */     Double popularityPrice = Double.valueOf(0.0D);
/* 358 */     if (cart != null) {
/* 359 */       popularityItems = this.popularityItemMng.getlist(cart.getId(), null);
/* 360 */       for (PopularityItem popularityItem : popularityItems) {
/* 361 */         popularityPrice = Double.valueOf(popularityPrice.doubleValue() + popularityItem.getPopularityGroup().getPrivilege().doubleValue() * popularityItem.getCount().intValue());
/*     */       }
/*     */     }
/*     */ 
/* 365 */     Set items = getItems(cart2Checkbox, cart);
/* 366 */     if (items == null) {
/* 367 */       return "redirect:/cart/shopping_cart.jspx";
/*     */     }
/*     */ 
/* 370 */     for (CartItem item : items) {
/* 371 */       if (item == null) {
/* 372 */         return "redirect:/cart/shopping_cart.jspx";
/*     */       }
/*     */     }
/*     */ 
/* 376 */     Double price = getPrice(items);
/*     */ 
/* 378 */     List splist = this.shippingMng.getList(web.getId(), true);
/*     */ 
/* 380 */     List smalist = this.shopMemberAddressMng.getList(member.getId());
/*     */ 
/* 382 */     List plist = this.addressMng.getListById(null);
/*     */ 
/* 384 */     List paylist = this.paymentMng.getList(Long.valueOf(1L), true);
/* 385 */     model.addAttribute("memberCouponlist", this.memberCouponMng.getList(member.getId(), new BigDecimal(price.doubleValue())));
/* 386 */     model.addAttribute("items", getItems(cart2Checkbox, cart));
/* 387 */     model.addAttribute("smalist", smalist);
/* 388 */     model.addAttribute("plist", plist);
/* 389 */     model.addAttribute("paylist", paylist);
/* 390 */     model.addAttribute("splist", splist);
/* 391 */     model.addAttribute("popularityPrice", popularityPrice);
/* 392 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 393 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.checkoutShipping", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/buyNow.jspx"})
/*     */   public String buyNoe(Long productId, Integer count, Long fashId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/* 399 */     Website web = SiteUtils.getWeb(request);
/* 400 */     ShopMember member = MemberThread.get();
/* 401 */     if (member == null) {
/* 402 */       return "redirect:../login.jspx";
/*     */     }
/* 404 */     Product product = this.productMng.findById(productId);
/* 405 */     Double popularityPrice = Double.valueOf(0.0D);
/*     */ 
/* 407 */     Double price = getPrice(product, count);
/*     */ 
/* 409 */     String cateGory = getCategoryId(product);
/*     */ 
/* 411 */     List smalist = this.shopMemberAddressMng.getList(member.getId());
/*     */ 
/* 413 */     List plist = this.addressMng.getListById(null);
/*     */ 
/* 415 */     List paylist = this.paymentMng.getList(Long.valueOf(1L), true);
/*     */ 
/* 417 */     List splist = this.shippingMng.getList(web.getId(), true);
/*     */ 
/* 419 */     model.addAttribute("product", product);
/* 420 */     model.addAttribute("count", count);
/* 421 */     model.addAttribute("price", price);
/* 422 */     model.addAttribute("cateGory", cateGory);
/* 423 */     model.addAttribute("smalist", smalist);
/* 424 */     model.addAttribute("plist", plist);
/* 425 */     model.addAttribute("paylist", paylist);
/* 426 */     model.addAttribute("splist", splist);
/* 427 */     model.addAttribute("popularityPrice", popularityPrice);
/* 428 */     model.addAttribute("memberCouponlist", this.memberCouponMng.getList(member.getId(), new BigDecimal(price.doubleValue())));
/* 429 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 430 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.buyNow", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   public Double getPrice(Product entity, Integer count)
/*     */   {
/* 440 */     Double price = Double.valueOf(0.0D);
/* 441 */     if (entity.getProductFashion() != null)
/* 442 */       price = Double.valueOf(entity.getProductFashion().getSalePrice().doubleValue() * count.intValue());
/*     */     else {
/* 444 */       price = Double.valueOf(entity.getSalePrice().doubleValue() * count.intValue());
/*     */     }
/* 446 */     return price;
/*     */   }
/*     */ 
/*     */   public String getCategoryId(Product entity)
/*     */   {
/* 454 */     String categoryId = "";
/* 455 */     if (entity.getCategory() != null) {
/* 456 */       categoryId = entity.getCategory().getId() + "&";
/*     */     }
/* 458 */     return categoryId;
/*     */   }
/*     */ 
/*     */   public Set<CartItem> getItems(Long[] cart2Checkbox, Cart cart) {
/* 462 */     Set items = new HashSet();
/* 463 */     if (cart2Checkbox != null) {
/* 464 */       for (Long id : cart2Checkbox)
/* 465 */         items.add(this.cartItemMng.findById(id));
/*     */     }
/*     */     else {
/* 468 */       items = cart.getItems();
/*     */     }
/* 470 */     return items;
/*     */   }
/*     */ 
/*     */   public Double getPrice(Set<CartItem> items) {
/* 474 */     Double price = Double.valueOf(0.0D);
/* 475 */     for (CartItem item : items) {
/* 476 */       if (item.getProductFash() != null)
/* 477 */         price = Double.valueOf(price.doubleValue() + item.getProductFash().getSalePrice().doubleValue() * item.getCount().intValue());
/*     */       else {
/* 479 */         price = Double.valueOf(price.doubleValue() + item.getProduct().getSalePrice().doubleValue() * item.getCount().intValue());
/*     */       }
/*     */     }
/* 482 */     return price;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.front.CartAct
 * JD-Core Version:    0.6.0
 */