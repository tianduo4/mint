package com.jspgou.cms.service;

import com.jspgou.cms.entity.Cart;
import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.core.entity.Website;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface ShoppingSvc
{
  public abstract Cart getCart(ShopMember paramShopMember, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);

  public abstract void addCookie(ShopMember paramShopMember, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);

  public abstract void clearCookie(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);

  public abstract Cart getCart(Long paramLong);

  public abstract void clearCart(ShopMember paramShopMember);

  public abstract Cart collectAddToCart(Product paramProduct, Long paramLong1, Long paramLong2, int paramInt, boolean paramBoolean, ShopMember paramShopMember, Website paramWebsite, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.service.ShoppingSvc
 * JD-Core Version:    0.6.0
 */