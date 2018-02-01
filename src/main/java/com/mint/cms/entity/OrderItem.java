package com.mint.cms.entity;

import com.mint.cms.entity.base.BaseOrderItem;
import com.mint.core.entity.Website;

public class OrderItem extends BaseOrderItem {
    private static final long serialVersionUID = 1L;

    public Double getSubtotal() {
        return Double.valueOf(getFinalPrice().doubleValue() * getCount().intValue());
    }

    public Double getLimitSubtotal() {
        return Double.valueOf(getSeckillprice().doubleValue() * getCount().intValue());
    }

    public int getWeightForFreight() {
        Product p = getProduct();

        return p.getWeight().intValue() * getCount().intValue();
    }

    public int getCountForFreigt() {
        return getCount().intValue();
    }

    public static OrderItem parse(CartItem cartItem, ShopMemberGroup group) {
        return parse(cartItem.getProduct(), cartItem.getCount().intValue(), group,
                cartItem.getWebsite());
    }

    public static OrderItem parse1(CartItem cartItem, ShopMemberGroup group) {
        if (cartItem.getProductFash() == null) {
            return parse(cartItem.getProduct(), cartItem.getCount().intValue(), group,
                    cartItem.getWebsite());
        }
        return parse1(cartItem.getProduct(), cartItem.getProductFash(), cartItem.getCount().intValue(), group,
                cartItem.getWebsite());
    }

    public static OrderItem parse(Product product, int count, ShopMemberGroup group, Website website) {
        product.setStockCount(Integer.valueOf(product.getStockCount().intValue() - count));
        product.setSaleCount(Integer.valueOf(count + product.getSaleCount().intValue()));
        OrderItem item = new OrderItem();
        item.setCount(Integer.valueOf(count));
        item.setWebsite(website);
        item.setProduct(product);

        if ((product.getProductExt().getIslimitTime() != null) && (product.getProductExt().getIslimitTime().booleanValue()) &&
                (product.getProductExt().getSeckillprice() != null)) {
            item.setSeckillprice(product.getProductExt().getSeckillprice());
        }
        item.setSalePrice(product.getSalePrice());
        item.setCostPrice(product.getCostPrice());
        item.setMemberPrice(product.getMemberPrice(group));
        item.setFinalPrice(item.getMemberPrice());
        return item;
    }

    public static OrderItem parse1(Product product, ProductFashion productFash, int count, ShopMemberGroup group, Website website) {
        productFash.setStockCount(Integer.valueOf(productFash.getStockCount().intValue() - count));
        productFash.setSaleCount(Integer.valueOf(count + productFash.getSaleCount().intValue()));
        OrderItem item = new OrderItem();
        item.setCount(Integer.valueOf(count));
        item.setWebsite(website);
        item.setProduct(product);

        if ((product.getProductExt().getIslimitTime() != null) && (product.getProductExt().getIslimitTime().booleanValue()) &&
                (product.getProductExt().getSeckillprice() != null)) {
            item.setSeckillprice(product.getProductExt().getSeckillprice());
        }
        item.setSalePrice(product.getSalePrice());
        item.setCostPrice(product.getCostPrice());
        item.setMemberPrice(product.getMemberPrice(group));
        item.setFinalPrice(item.getMemberPrice());
        item.setProductFash(productFash);
        return item;
    }

    public OrderItem() {
    }

    public OrderItem(Long id) {
        super(id);
    }

    public OrderItem(Long id, Website website, Product product, Order order, Integer count) {
        super(id,
                website,
                product,
                order,
                count);
    }
}

