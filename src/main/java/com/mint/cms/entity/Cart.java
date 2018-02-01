package com.mint.cms.entity;

import com.mint.cms.entity.base.BaseCart;
import com.mint.core.entity.Website;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Cart extends BaseCart {
    private static final long serialVersionUID = 1L;

    public Double getTotalPrice() {
        return getSubtotal();
    }

    public Double getProductTotalPrice() {
        return getSubtotal();
    }

    public int getWeightForFreight() {
        int weight = 0;
        for (CartItem item : getItems()) {
            weight += item.getWeightForFreight();
        }
        return weight;
    }

    public int getCountForFreight() {
        int count = 0;
        for (CartItem item : getItems()) {
            count += item.getCountForFreigt();
        }
        return count;
    }

    public Double getSubtotal() {
        Set<CartItem> items = getItems();
        Double total = Double.valueOf(0.0D);
        if (items != null) {
            for (CartItem item : items) {
                total = Double.valueOf(total.doubleValue() + item.getSubtotal().doubleValue());
            }
        }
        return total;
    }

    public Double getTotalSubtotal() {
        Set<CartItem> items = getItems();
        Double total = Double.valueOf(0.0D);
        if (items != null) {
            for (CartItem item : items) {
                if ((item.getProduct().getProductExt().getIslimitTime() != null) && (item.getProduct().getProductExt().getIslimitTime().booleanValue()))
                    total = Double.valueOf(total.doubleValue() + item.getLimitSubtotal().doubleValue());
                else {
                    total = Double.valueOf(total.doubleValue() + item.getSubtotal().doubleValue());
                }
            }
        }

        return total;
    }

    public int calTotalItem() {
        Set<CartItem> items = getItems();
        int count = 0;
        if (items != null) {
            for (CartItem item : items) {
                count += item.getCount().intValue();
            }
        }
        setTotalItems(Integer.valueOf(count));
        return count;
    }

    public int getTotalScore() {
        Set<CartItem> items = getItems();
        int score = 0;
        if (items != null) {
            for (CartItem item : items) {
                score += item.getScore().intValue();
            }
        }
        return score;
    }

    public int calTotalGift() {
        Set<CartGift> items = getGifts();
        int count = 0;
        if (items != null) {
            for (CartGift item : items) {
                count += item.getCount().intValue();
            }
        }
        setTotalGifts(Integer.valueOf(count));
        return count;
    }

    public void addItem(Product product, ProductFashion productFashion, PopularityGroup popularityGroup, int count, boolean isAdd) {
        CartItem item = extensionItem(product, productFashion, popularityGroup);
        count = extensionCount(product, productFashion, popularityGroup, count, isAdd);
        if (count > 0) {
            if (item == null) {
                item = new CartItem();
            }
            item.setCount(Integer.valueOf(count));
            item.setScore(Integer.valueOf(count * product.getScore().intValue()));
            if (extensionItem(product, productFashion, popularityGroup) == null) {
                if (productFashion != null) {
                    item.setProductFash(productFashion);
                }
                if (popularityGroup != null) {
                    item.setPopularityGroup(popularityGroup);
                }
                item.setProduct(product);
                addToItems(item);
            }
        } else if (item != null) {
            getItems().remove(item);
        }

        calTotalItem();
    }

    public CartItem extensionItem(Product product, ProductFashion productFashion, PopularityGroup popularityGroup) {
        CartItem item = null;
        if (productFashion != null)
            item = findItemfashion(productFashion.getId(), popularityGroup);
        else {
            item = findItem(product.getId(), popularityGroup);
        }
        return item;
    }

    public int extensionCount(Product product, ProductFashion productFashion, PopularityGroup popularityGroup, int count, boolean isAdd) {
        CartItem item = extensionItem(product, productFashion, popularityGroup);
        if ((item != null) &&
                (isAdd)) {
            count += item.getCount().intValue();
        }

        if (productFashion != null) {
            if (count > productFashion.getStockCount().intValue()) {
                count = productFashion.getStockCount().intValue();
            }
        } else if (count > product.getStockCount().intValue()) {
            count = product.getStockCount().intValue();
        }

        return count;
    }

    public void addGift(Gift gift, int count, boolean isAdd) {
        CartGift gifts = findGift(gift.getId());
        if (gifts != null) {
            if (isAdd) {
                count += gifts.getCount().intValue();
            }

            if (count <= 0) {
                getGifts().remove(gifts);
            } else {
                if (count > gift.getGiftStock().intValue()) {
                    count = gift.getGiftStock().intValue();
                }
                gifts.setCount(Integer.valueOf(count));
            }
        } else if (count > 0) {
            if (count > gift.getGiftStock().intValue()) {
                count = gift.getGiftStock().intValue();
            }
            gifts = new CartGift();
            gifts.setGift(gift);
            gifts.setCount(Integer.valueOf(count));
            addToGift(gifts);
        }

        calTotalGift();
    }

    public CartItem findItem(Long productId, PopularityGroup popularityGroup) {
        Set<CartItem> items = getItems();
        if ((items != null) && (items.size() > 0)) {
            if (popularityGroup != null) {
                for (CartItem item : items) {
                    if ((item.getPopularityGroup() != null) &&
                            (item.getProduct().getId().equals(productId)) && (item.getPopularityGroup().getId().equals(popularityGroup.getId()))) {
                        return item;
                    }
                }
            } else {
                for (CartItem item : items) {
                    if ((item.getProduct().getId().equals(productId)) && (item.getPopularityGroup() == null)) {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    public CartItem findItemfashion(Long productFashId, PopularityGroup popularityGroup) {
        Set<CartItem> items = getItems();
        if ((items != null) && (items.size() > 0)) {
            if (popularityGroup != null) {
                for (CartItem item : items) {
                    if ((item.getProductFash() != null) && (item.getPopularityGroup() != null) &&
                            (item.getProductFash().getId().equals(productFashId)) && (item.getPopularityGroup().getId().equals(popularityGroup.getId()))) {
                        return item;
                    }
                }
            } else {
                for (CartItem item : items) {
                    if ((item.getProductFash() != null) &&
                            (item.getProductFash().getId().equals(productFashId)) && (item.getPopularityGroup() == null)) {
                        return item;
                    }
                }
            }
        }

        return null;
    }

    public CartGift findGift(Long giftId) {
        Set<CartGift> gifts = getGifts();
        if ((gifts != null) && (gifts.size() > 0)) {
            for (CartGift gift : gifts) {
                if (gift.getGift().getId().equals(giftId)) {
                    return gift;
                }
            }
        }
        return null;
    }

    public void addToGift(CartGift item) {
        Set items = getGifts();
        if (items == null) {
            items = new LinkedHashSet();
            setGifts(items);
        }
        item.setWebsite(getWebsite());
        item.setCart(this);
        items.add(item);
    }

    public void addToItems(CartItem item) {
        Set items = getItems();
        if (items == null) {
            items = new LinkedHashSet();
            setItems(items);
        }
        item.setWebsite(getWebsite());
        item.setCart(this);
        items.add(item);
    }

    public List<PopularityGroup> getPopularits() {
        List list = new ArrayList();
        Set<CartItem> items = getItems();
        if (items != null) {
            for (CartItem item : items) {
                if ((item.getPopularityGroup() == null) ||
                        (list.contains(item.getPopularityGroup()))) continue;
                list.add(item.getPopularityGroup());
            }

        }

        return list;
    }

    public void init() {
        if (getTotalItems() == null)
            setTotalItems(Integer.valueOf(0));
    }

    public Cart() {
    }

    public Cart(Long id) {
        super(id);
    }

    public Cart(Long id, Website website, Integer totalItems) {
        super(id,
                website,
                totalItems);
    }
}

