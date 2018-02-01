package com.jspgou.cms.service.impl;

import com.jspgou.cms.entity.Cart;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.CartMng;
import com.jspgou.cms.service.ShoppingSvc;
import com.jspgou.core.entity.Website;

import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShoppingSvcImpl
        implements ShoppingSvc {

    @Autowired
    private CartMng cartMng;

    public Cart collectAddToCart(Product product, Long fashId, Long popularityId, int count, boolean isAdd, ShopMember member, Website web, HttpServletRequest request, HttpServletResponse response) {
        Cart cart = this.cartMng.collectAddItem(product, fashId, popularityId, count, isAdd, member.getId(), web.getId());
        Cookie cookie = createCookie(String.valueOf(cart.calTotalItem()), request);
        response.addCookie(cookie);
        return cart;
    }

    public void clearCart(ShopMember member) {
        Cart cart = this.cartMng.findById(member.getId());
        cart.getItems().clear();
        cart.setTotalItems(Integer.valueOf(0));
        this.cartMng.update(cart);
    }

    public Cart getCart(ShopMember member, HttpServletRequest request, HttpServletResponse response) {
        Cart cart = this.cartMng.findById(member.getId());
        if ((cart != null) && (cart.getItems().size() > 0)) {
            Cookie cookie = createCookie(String.valueOf(cart.calTotalItem()), request);
            response.addCookie(cookie);
            return cart;
        }
        Cookie cookie = createCookie("0", request);
        response.addCookie(cookie);
        return null;
    }

    public Cart getCart(Long memberId) {
        Cart cart = this.cartMng.findById(memberId);
        if ((cart != null) && (cart.getItems().size() > 0)) {
            return cart;
        }
        return null;
    }

    public void addCookie(ShopMember member, HttpServletRequest request, HttpServletResponse response) {
        getCart(member, request, response);
    }

    public void clearCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = createCookie(null, request);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private Cookie createCookie(String totalItems, HttpServletRequest request) {
        Cookie cookie = new Cookie("cart_total_items", totalItems);
        String ctx = request.getContextPath();
        cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
        return cookie;
    }
}

