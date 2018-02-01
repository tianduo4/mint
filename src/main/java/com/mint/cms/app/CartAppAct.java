package com.mint.cms.app;

import com.mint.cms.entity.Cart;
import com.mint.cms.entity.CartItem;
import com.mint.cms.entity.Product;
import com.mint.cms.entity.ProductFashion;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.entity.ShopMemberAddress;
import com.mint.cms.manager.AddressMng;
import com.mint.cms.manager.ApiUtilMng;
import com.mint.cms.manager.ProductFashionMng;
import com.mint.cms.manager.ProductMng;
import com.mint.cms.manager.ShopMemberAddressMng;
import com.mint.cms.service.ShoppingSvc;
import com.mint.common.util.Apputil;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartAppAct {

    @Autowired
    private ApiUtilMng apiUtilMng;

    @Autowired
    private ShoppingSvc shoppingSvc;

    @Autowired
    private ProductMng productMng;

    @Autowired
    private ProductFashionMng productFashionMng;

    @Autowired
    private ShopMemberAddressMng shopMemberAddressMng;

    @Autowired
    private AddressMng addressMng;

    @RequestMapping({"/api/user/cart.jspx"})
    public void shoppingCart(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws JSONException {
        JSONArray arr = new JSONArray();
        JSONObject o = new JSONObject();
        ShopMember member = this.apiUtilMng.findbysessionKey(request);
        if (member != null) {
            Cart cart = this.shoppingSvc.getCart(member, request, response);
            for (CartItem item : cart.getItems()) {
                o.put("name", item.getProduct().getName());
                o.put("imgUrl", item.getProduct().getCoverImgUrl());
                o.put("marketPrice", item.getProduct().getMarketPrice());
                o.put("costPrice", item.getProduct().getCostPrice());
                o.put("status", item.getProduct().getStatus());
                arr.put(o);
            }
        }
        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
                arr.toString(), "/api/user/cart.jspx", request));
    }

    @RequestMapping({"/api/user/addcarttem.jspx"})
    public void addcarttem(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws JSONException {
        JSONObject json = new JSONObject();
        Website web = SiteUtils.getWeb(request);
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        if (user != null) {
            Long productId = Apputil.getRequestLong(request
                    .getParameter("productId"));
            Integer productAmount = Apputil.getRequestInteger("productAmount");
            Long fashId = Apputil.getRequestLong("fashId");
            Product product = this.productMng.findById(productId);

            if ((product.getStatus() != null) && (product.getStatus().intValue() == Product.ON_SALE_STATUS)) {
                if (fashId == null) {
                    if (productAmount.intValue() > product.getStockCount().intValue()) {
                        json.put("status", 2);
                    } else {
                        this.shoppingSvc.collectAddToCart(product, fashId, null,
                                productAmount.intValue(), true, user, web, request,
                                response);
                        json.put("status", 1);
                    }
                } else {
                    ProductFashion productFashion = this.productFashionMng
                            .findById(fashId);
                    if (productAmount.intValue() > product.getStockCount().intValue()) {
                        json.put("status", 2);
                    } else {
                        this.shoppingSvc.collectAddToCart(product, fashId, null,
                                productAmount.intValue(), true, user, web, request,
                                response);
                        json.put("status", 1);
                    }
                }

            } else if ((product.getStatus() == null) || (product.getStatus().intValue() != Product.ON_SALE_STATUS)) {
                json.put("status", 3);
            }
        }

        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
                json.toString(), "/api/user/cart.jspx", request));
    }

    @RequestMapping({"/api/user/addresslist.jspx"})
    public void addresslist(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        JSONArray arr = new JSONArray();
        JSONObject o = new JSONObject();
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        if (user != null) {
            List<ShopMemberAddress> list = this.shopMemberAddressMng.getList(user
                    .getId());
            for (ShopMemberAddress address : list) {
                o.put("username", address.getUsername());
                o.put("detailaddress", address.getProvince() + address.getCity() + address.getCountry() + address.getDetailaddress());
                o.put("tel", address.getTel());
                arr.put(o);
            }
        }

        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
                arr.toString(), "/api/user/addresslist.jspx", request));
    }

    @RequestMapping({"/api/user/addressdefault.jspx"})
    public void addressdefault(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        JSONObject o = new JSONObject();
        if (user != null) {
            ShopMemberAddress address = this.shopMemberAddressMng
                    .findByMemberDefault(user.getId(), Boolean.valueOf(true));
            o.put("username", address.getUsername());
            o.put("tel", address.getTel());
            o.put("detailaddress", address.getProvince() + address.getCity() + address.getCountry() + address.getDetailaddress());
        }

        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(
                o.toString(), "/api/user/addressdefault.jspx", request));
    }

    @RequestMapping({"/api/user/addresssave.jspx"})
    public void addressSave(HttpServletRequest request, HttpServletResponse response) {
        JSONObject o = new JSONObject();
        ShopMember user = this.apiUtilMng.findbysessionKey(request);
        String username = request.getParameter("username");
        String detailaddress = request.getParameter("detailaddress");
        String tel = request.getParameter("tel");
        String postCode = request.getParameter("postCode");
        String provinceId = request.getParameter("provinceId");
        String cityId = request.getParameter("cityId");
        String countryId = request.getParameter("countryId");

        if (user != null) {
            ShopMemberAddress bean = new ShopMemberAddress();
            bean.setUsername(username);
            bean.setProvinceId(this.addressMng.findById(Long.valueOf(Long.parseLong(provinceId)))
                    .getId());
            bean.setProvince(this.addressMng.findById(Long.valueOf(Long.parseLong(provinceId)))
                    .getName());
            bean.setCity(this.addressMng.findById(Long.valueOf(Long.parseLong(cityId))).getName());
            bean.setCityId(this.addressMng.findById(Long.valueOf(Long.parseLong(cityId))).getId());
            bean.setCountry(this.addressMng.findById(Long.valueOf(Long.parseLong(countryId)))
                    .getName());
            bean.setCountryId(this.addressMng.findById(Long.valueOf(Long.parseLong(countryId)))
                    .getId());
            bean.setDetailaddress(detailaddress);
            bean.setTel(tel);
            bean.setPostCode(postCode);
            this.shopMemberAddressMng.save(bean);
        }
        ResponseUtils.renderJson(response, this.apiUtilMng.getJsonStrng(o.toString(), "/api/user/addresssave.jspx", request));
    }
}

