package com.mint.cms.dao.impl;

import com.mint.cms.dao.ProductDao;
import com.mint.cms.entity.Product;
import com.mint.cms.entity.ProductTag;
import com.mint.cms.lucene.LuceneProduct;
import com.mint.common.hibernate4.Finder;
import com.mint.common.hibernate4.HibernateBaseDao;
import com.mint.common.page.Pagination;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDaoImpl extends HibernateBaseDao<Product, Long>
        implements ProductDao {
    public Pagination getPageByCategory(Integer ctgId, String productName, String brandName, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, int pageNo, int pageSize, boolean cacheable) {
        Finder f = getFinderForCategory(ctgId, productName, brandName, status,
                isRecommend, isSpecial, isHotsale, isNewProduct, typeId, startSalePrice, endSalePrice,
                startStock, endStock, cacheable);
        return find(f, pageNo, pageSize);
    }

    public Pagination getPageByCategoryChannel(String brandId, Integer ctgId, Boolean isRecommend, String[] names, String[] values, Boolean isSpecial, int orderBy, Double startPrice, Double endPrice, int pageNo, int pageSize, boolean cacheable) {
        Finder f = getFinderForCategoryChannel(brandId, ctgId, isRecommend,
                names, values, isSpecial, orderBy, startPrice, endPrice, cacheable);
        return find(f, pageNo, pageSize);
    }

    public Pagination getPageByStockWarning(Long webId, Integer count, Boolean status, int pageNo, int pageSize) {
        Finder f = Finder.create("select bean from Product bean");
        f.append(" where bean.website.id=:webId").setParam("webId", webId);
        f.append(" and bean.stockCount <=:count").setParam("count", count);
        f.append(" and bean.lackRemind=:status").setParam("status", status);
        f.append(" order by bean.stockCount asc");
        return find(f, pageNo, pageSize);
    }

    public List<Product> getHistoryProduct(HashSet<Long> set, Integer count) {
        if (set.size() > 0) {
            return getSession().createQuery("from Product bean where bean.id in (:ids)")
                    .setParameterList("ids", set).setMaxResults(count.intValue()).list();
        }
        return new ArrayList();
    }

    public Pagination getPageByWebsite(Long webId, String productName, String brandName, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, int pageNo, int pageSize, boolean cacheable) {
        Finder f = getFinderForWebsite(webId, productName, brandName, status,
                isRecommend, isSpecial, isHotsale, isNewProduct, typeId, startSalePrice, endSalePrice,
                startStock, endStock, cacheable);
        return find(f, pageNo, pageSize);
    }

    public Pagination getPageByTag(Long tagId, Integer ctgId, Boolean isRecommend, Boolean isSpecial, int pageNo, int pageSize, boolean cacheable) {
        Finder f = getFinderForTag(tagId, ctgId, isRecommend, isSpecial,
                cacheable);
        return find(f, pageNo, pageSize);
    }

    public List<Product> getListByCategory(Integer ctgId, Boolean isRecommend, Boolean isSpecial, int firstResult, int maxResults, boolean cacheable) {
        Finder f = getFinderForCategory(ctgId, null, null, Integer.valueOf(Product.ON_SALE_STATUS), isRecommend, isSpecial,
                null, null, null, null, null, null, null, cacheable);
        f.setFirstResult(firstResult);
        f.setMaxResults(maxResults);
        return find(f);
    }

    public List<Product> getListByWebsite(Long webId, Boolean isRecommend, Boolean isSpecial, int firstResult, int maxResults, boolean cacheable) {
        Finder f = getFinderForWebsite(webId, null, null, null, isRecommend, isSpecial,
                null, null, null, null, null, null, null, cacheable);
        f.setFirstResult(firstResult);
        f.setMaxResults(maxResults);
        return find(f);
    }

    public List<Product> getListByWebsite1(Long webId, Boolean isRecommend, Boolean isSpecial, Boolean isHostSale, Boolean isNewProduct, int firstResult, int maxResults, boolean cacheable) {
        Finder f = getFinderForWebsite1(webId, isRecommend, isSpecial, isHostSale, isNewProduct, cacheable);
        f.setFirstResult(firstResult);
        f.setMaxResults(maxResults);
        return find(f);
    }

    public List<Product> getListByTag(Long tagId, Integer ctgId, Boolean isRecommend, Boolean isSpecial, int firstResult, int maxResults, boolean cacheable) {
        Finder f = getFinderForTag(tagId, ctgId, isRecommend, isSpecial, cacheable);
        f.setFirstResult(firstResult);
        f.setMaxResults(maxResults);
        return find(f);
    }

    private Finder getFinderForCategory(Integer ctgId, String productName, String brandName, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, boolean cacheable) {
        Finder f = Finder.create("select bean from Product bean");
        f.append(" inner join bean.category node,Category parent");
        f.append(" where node.lft between parent.lft and parent.rgt");
        f.append(" and parent.id=:ctgId");
        f.append(" and ( bean.status = " + Product.ON_SALE_STATUS + " or bean.status=" + Product.NOT_SALE_STATUS + ") ");
        if (!StringUtils.isBlank(productName)) {
            f.append(" and bean.name like:productName");
            f.setParam("productName", "%" + productName + "%");
        }
        if (!StringUtils.isBlank(brandName)) {
            f.append(" and bean.brand.name like:brandName");
            f.setParam("brandName", "%" + brandName + "%");
        }
        if (status != null) {
            f.append(" and bean.status=:status");
            f.setParam("status", status);
        }
        if (isRecommend != null) {
            f.append(" and bean.recommend=:isRecommend");
            f.setParam("isRecommend", isRecommend);
        }
        if (isSpecial != null) {
            f.append(" and bean.special=:isSpecial");
            f.setParam("isSpecial", isSpecial);
        }
        if (isHotsale != null) {
            f.append(" and bean.hotsale=:isHotsale");
            f.setParam("isHotsale", isHotsale);
        }
        if (isNewProduct != null) {
            f.append(" and bean.newProduct=:isNewProduct");
            f.setParam("isNewProduct", isNewProduct);
        }
        if (typeId != null) {
            f.append(" and bean.type.id=:typeId");
            f.setParam("typeId", typeId);
        }
        if (startSalePrice != null) {
            f.append(" and bean.salePrice>:startSalePrice");
            f.setParam("startSalePrice", startSalePrice);
        }
        if (endSalePrice != null) {
            f.append(" and bean.salePrice<:endSalePrice");
            f.setParam("endSalePrice", endSalePrice);
        }
        if (startStock != null) {
            f.append(" and bean.stockCount>:startStock");
            f.setParam("startStock", startStock);
        }
        if (endStock != null) {
            f.append(" and bean.stockCount<:endStock");
            f.setParam("endStock", endStock);
        }
        f.append(" order by bean.id desc");
        f.setParam("ctgId", ctgId);
        f.setCacheable(cacheable);
        return f;
    }

    public List<Product> getList(Long typeId, Long brandId, String productName, boolean cacheable) {
        Finder f = Finder.create("from Product bean where 1=1");
        if (typeId != null) {
            f.append(" and bean.type.id=:typeId");
            f.setParam("typeId", typeId);
        }
        if (brandId != null) {
            f.append(" and bean.brand.id=:brandId");
            f.setParam("brandId", brandId);
        }
        if (!StringUtils.isBlank(productName)) {
            f.append(" and bean.name like:productName");
            f.setParam("productName", "%" + productName + "%");
        }
        f.setCacheable(cacheable);
        return find(f);
    }

    public Pagination getPage(int orderBy, int pageNo, int pageSize, boolean cacheable) {
        Finder f = Finder.create("from Product bean where 1=1");
        appendOrder(f, orderBy);
        f.setCacheable(cacheable);
        return find(f, pageNo, pageSize);
    }

    public Pagination getPage1(Long typeId, int orderBy, int pageNo, int pageSize, boolean cacheable) {
        Finder f = Finder.create("from Product bean where 1=1");
        if (typeId != null) {
            f.append(" and bean.category.id=:typeId");
            f.setParam("typeId", typeId);
        }
        appendOrder(f, orderBy);
        f.setCacheable(cacheable);
        return find(f, pageNo, pageSize);
    }

    public List<Product> findAll() {
        Finder f = Finder.create("from Product bean ");
        return find(f);
    }

    private Finder getFinderForCategoryChannel(String brandId, Integer ctgId, Boolean isRecommend, String[] names, String[] values, Boolean isSpecial, int orderBy, Double startPrice, Double endPrice, boolean cacheable) {
        Finder f = Finder.create("select distinct bean from Product bean");
        f.append(" join bean.category node,Category parent");
        String[] exendeds = null;
        if (names != null) {
            exendeds = new String[names.length];
            int i = 0;
            for (int j = names.length; i < j; i++) {
                String exended = "exended" + i;
                exendeds[i] = exended;
                f.append(" inner join bean.exendeds " + exended);
            }
        }
        f.append(" where node.lft between parent.lft and parent.rgt and bean.status=" + Product.ON_SALE_STATUS);
        f.append(" and parent.id=:ctgId");
        f.setParam("ctgId", ctgId);
        if (isRecommend != null) {
            f.append(" and node.recommend=:recommend");
            f.setParam("recommend", isRecommend);
        }
        if (isSpecial != null) {
            f.append(" and node.special=:special");
            f.setParam("special", isSpecial);
        }

        if ((brandId != null) && (!brandId.equals(""))) {
            f.append(" and bean.brand.id in(" + brandId + ")");
        }
        if (names != null) {
            for (int i = 0; i < names.length; i++) {
                String an = "attr_name" + i;
                String av = "attr_value" + i;
                if (!values[i].equals("0")) {
                    f.append(" and " + exendeds[i] + ".name=:" + an).setParam(an, names[i]);
                    f.append(" and " + exendeds[i] + ".value=:" + av).setParam(av, values[i]);
                }
            }
        }

        if (startPrice.doubleValue() > 0.0D) {
            f.append(" and bean.salePrice>=:startPrice");
            f.setParam("startPrice", startPrice);
        }
        if (endPrice.doubleValue() > 0.0D) {
            f.append(" and bean.salePrice<=:endPrice");
            f.setParam("endPrice", endPrice);
        }
        appendOrder(f, orderBy);
        f.setCacheable(cacheable);
        return f;
    }

    private void appendOrder(Finder f, int orderBy) {
        switch (orderBy) {
            case 1:
                f.append(" order by bean.id asc");
                break;
            case 2:
                f.append(" order by bean.createTime desc");
                break;
            case 3:
                f.append(" order by bean.createTime asc");
                break;
            case 4:
                f.append(" order by bean.saleCount desc, bean.createTime desc");
                break;
            case 5:
                f.append(" order by bean.saleCount desc, bean.createTime asc");
                break;
            case 6:
                f.append(" order by bean.salePrice desc, bean.id desc");
                break;
            case 7:
                f.append(" order by bean.salePrice asc,bean.id desc");
                break;
            case 8:
                f.append(" order by bean.liRun desc");
                break;
            case 9:
                f.append(" order by bean.viewCount desc");
                break;
            default:
                f.append(" order by bean.id desc");
        }
    }

    private Finder getFinderForWebsite(Long webId, String productName, String brandName, Integer status, Boolean isRecommend, Boolean isSpecial, Boolean isHotsale, Boolean isNewProduct, Long typeId, Double startSalePrice, Double endSalePrice, Integer startStock, Integer endStock, boolean cacheable) {
        Finder f = Finder.create("select bean from Product bean");
        f.append(" where bean.website.id=:webId and ( bean.status = " + Product.ON_SALE_STATUS + " or bean.status=" + Product.NOT_SALE_STATUS + ") ");
        if (!StringUtils.isBlank(productName)) {
            f.append(" and bean.name like:productName");
            f.setParam("productName", "%" + productName + "%");
        }
        if (!StringUtils.isBlank(brandName)) {
            f.append(" and bean.brand.name like:brandName");
            f.setParam("brandName", "%" + brandName + "%");
        }
        if (status != null) {
            f.append(" and bean.status=:status");
            f.setParam("status", status);
        }
        if (isRecommend != null) {
            f.append(" and bean.recommend=:isRecommend");
            f.setParam("isRecommend", isRecommend);
        }
        if (isSpecial != null) {
            f.append(" and bean.special=:isSpecial");
            f.setParam("isSpecial", isSpecial);
        }
        if (isHotsale != null) {
            f.append(" and bean.hotsale=:isHotsale");
            f.setParam("isHotsale", isHotsale);
        }
        if (isNewProduct != null) {
            f.append(" and bean.newProduct=:isNewProduct");
            f.setParam("isNewProduct", isNewProduct);
        }
        if (typeId != null) {
            f.append(" and bean.type.id=:typeId");
            f.setParam("typeId", typeId);
        }
        if (startSalePrice != null) {
            f.append(" and bean.salePrice>:startSalePrice");
            f.setParam("startSalePrice", startSalePrice);
        }
        if (endSalePrice != null) {
            f.append(" and bean.salePrice<:endSalePrice");
            f.setParam("endSalePrice", endSalePrice);
        }
        if (startStock != null) {
            f.append(" and bean.stockCount>:startStock");
            f.setParam("startStock", startStock);
        }
        if (endStock != null) {
            f.append(" and bean.stockCount<:endStock");
            f.setParam("endStock", endStock);
        }
        f.append(" order by bean.id desc");
        f.setParam("webId", webId);
        f.setCacheable(cacheable);
        return f;
    }

    public List<Product> getIsRecommend(Boolean isRecommend, int count) {
        Finder f = Finder.create("from Product bean where bean.recommend=:recommend").setParam("recommend", isRecommend);
        f.setMaxResults(count);
        return find(f);
    }

    private Finder getFinderForWebsite1(Long webId, Boolean isRecommend, Boolean isSpecial, Boolean isHostSale, Boolean isNewProduct, boolean cacheable) {
        Finder f = Finder.create("from Product bean");
        f.append(" where bean.website.id=:webId and  ( bean.status = " + Product.ON_SALE_STATUS + " or bean.status=" + Product.NOT_SALE_STATUS + ") ");
        if (isRecommend != null) {
            f.append(" and bean.recommend=:recommend");
            f.setParam("recommend", isRecommend);
        }
        if (isSpecial != null) {
            f.append(" and bean.special=:special");
            f.setParam("special", isSpecial);
        }
        if (isHostSale != null) {
            f.append(" and bean.hotsale=:hotsale");
            f.setParam("hotsale", isHostSale);
        }
        if (isNewProduct != null) {
            f.append(" and bean.newProduct=:newProduct");
            f.setParam("newProduct", isNewProduct);
        }
        f.append(" order by bean.id desc");
        f.setParam("webId", webId);
        f.setCacheable(cacheable);
        return f;
    }

    private Finder getFinderForTag(Long tagId, Integer ctgId, Boolean isRecommend, Boolean isSpecial, boolean cacheable) {
        Finder f = Finder.create("select bean from Product bean");
        f.append(" inner join bean.tags tag with tag.id=:tagId");
        f.append(" where   ( bean.status = " + Product.ON_SALE_STATUS + " or bean.status=" + Product.NOT_SALE_STATUS + ") ");
        f.setParam("tagId", tagId);
        if (ctgId != null) {
            f.append(" and bean.category.id=:ctgId");
            f.setParam("ctgId", ctgId);
        }
        if (isRecommend != null) {
            f.append(" and bean.recommend=:recommend");
            f.setParam("recommend", isRecommend);
        }
        if (isSpecial != null) {
            f.append(" and bean.special=:special");
            f.setParam("special", isSpecial);
        }
        f.append(" order by bean.id desc");
        f.setCacheable(cacheable);
        return f;
    }

    public int luceneWriteIndex(IndexWriter writer, Long webId, Date start, Date end)
            throws CorruptIndexException, IOException {
        Session session = getSession();
        Finder f = Finder.create("from Product bean where 1=1 and bean.status=" + Product.ON_SALE_STATUS);
        if (webId != null) {
            f.append(" and bean.website.id=:webId");
            f.setParam("webId", webId);
        }
        if (start != null) {
            f.append(" and bean.createTime >= :start");
            f.setParam("start", start);
        }
        if (end != null) {
            f.append(" and bean.createTime >= :end");
            f.setParam("end", end);
        }

        ScrollableResults products = f.createQuery(session).setCacheMode(
                CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);

        int count = 0;
        while (products.next()) {
            Product product = (Product) products.get(0);
            writer.addDocument(LuceneProduct.createDocument(product));
            count++;
            if (count % 20 == 0) {
                session.clear();
            }
        }
        return count;
    }

    public int deleteTagAssociation(ProductTag[] tags) {
        Long[] tagIds = new Long[tags.length];
        int i = 0;
        for (int len = tags.length; i < len; i++) {
            tagIds[i] = tags[i].getId();
        }
        Session session = getSession();
        String hql = "from Product bean inner join bean.tags tag where tag.id in (:tagIds)";
        ScrollableResults products = session.createQuery(hql).setParameterList(
                "tagIds", tagIds).setCacheMode(CacheMode.IGNORE).scroll(
                ScrollMode.FORWARD_ONLY);
        int count = 0;
        while (products.next()) {
            Product product = (Product) products.get(0);
            product.getTags().removeAll(Arrays.asList(tags));
            count++;
            if (count % 20 == 0) {
                session.flush();
                session.clear();
            }
        }
        return count;
    }

    public Integer[] getProductByTag(Long webId) {
        Long newProduct = (Long) getSession().createQuery("select count(*) from Product bean where bean.website.id=:webId  and bean.newProduct=true")
                .setParameter("webId", webId).uniqueResult();
        Long hotProduct = (Long) getSession().createQuery("select count(*) from Product bean where bean.website.id=:webId  and bean.hotsale=true")
                .setParameter("webId", webId).uniqueResult();
        Long speProduct = (Long) getSession().createQuery("select count(*) from Product bean where bean.website.id=:webId   and bean.special=true")
                .setParameter("webId", webId).uniqueResult();
        Integer[] p = new Integer[3];
        p[0] = Integer.valueOf(newProduct.intValue());
        p[1] = Integer.valueOf(hotProduct.intValue());
        p[2] = Integer.valueOf(speProduct.intValue());
        return p;
    }

    public Long getProductCount(Long webId) {
        Finder finder = Finder.create("select count(1) from Product bean where bean.status = " + Product.ON_SALE_STATUS);
        finder.append(" and bean.website.id=:webId").setParam("webId", webId);
        List list = find(finder);
        return (Long) list.get(0);
    }

    public Long getOverStock(Long webId) {
        Finder finder = Finder.create("select count(1) from Product bean  where bean.stockCount < bean.alertInventory and bean.status = " + Product.ON_SALE_STATUS);
        finder.append(" and bean.website.id=:webId").setParam("webId", webId);
        List list = find(finder);
        return (Long) list.get(0);
    }

    public Pagination getPageOverStockList(Long webId, int pageNo, int pageSize) {
        Finder finder = Finder.create(" from Product bean  where bean.stockCount < bean.alertInventory and bean.status = " + Product.ON_SALE_STATUS);
        finder.append("  and bean.website.id=:webId").setParam("webId", webId);
        finder.append(" order by bean.stockCount asc");
        return find(finder, pageNo, pageSize);
    }

    public Product findById(Long id) {
        Product entity = (Product) get(id);
        return entity;
    }

    public Product save(Product bean) {
        getSession().save(bean);
        return bean;
    }

    public Product deleteById(Long id) {
        Product entity = (Product) super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    protected Class<Product> getEntityClass() {
        return Product.class;
    }
}

