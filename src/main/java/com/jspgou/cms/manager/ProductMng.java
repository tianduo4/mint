package com.jspgou.cms.manager;

import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductExt;
import com.jspgou.cms.entity.ProductTag;
import com.jspgou.common.page.Pagination;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public abstract interface ProductMng {
    public abstract Pagination getPage(int paramInt1, int paramInt2, int paramInt3);

    public abstract Pagination getPage1(Long paramLong, int paramInt1, int paramInt2, int paramInt3);

    public abstract List<Product> findAll();

    public abstract List<Product> getList(Long paramLong1, Long paramLong2, String paramString);

    public abstract List<Product> getListForTag(Long paramLong1, Integer paramInteger, Long paramLong2, Boolean paramBoolean1, Boolean paramBoolean2, Boolean paramBoolean3, Boolean paramBoolean4, int paramInt1, int paramInt2);

    public abstract Pagination getPage(Long paramLong1, Integer paramInteger1, String paramString1, String paramString2, Integer paramInteger2, Boolean paramBoolean1, Boolean paramBoolean2, Boolean paramBoolean3, Boolean paramBoolean4, Long paramLong2, Double paramDouble1, Double paramDouble2, Integer paramInteger3, Integer paramInteger4, int paramInt1, int paramInt2);

    public abstract Pagination getPageByStockWarning(Long paramLong, Integer paramInteger, Boolean paramBoolean, int paramInt1, int paramInt2);

    public abstract Pagination getPageForTag(Long paramLong1, Integer paramInteger, Long paramLong2, Boolean paramBoolean1, Boolean paramBoolean2, int paramInt1, int paramInt2);

    public abstract Product findById(Long paramLong);

    public abstract Product updateByUpdater(Product paramProduct);

    public abstract int deleteTagAssociation(ProductTag[] paramArrayOfProductTag);

    public abstract Product save(Product paramProduct, ProductExt paramProductExt, Long paramLong1, Integer paramInteger, Long paramLong2, Long[] paramArrayOfLong, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, MultipartFile paramMultipartFile);

    public abstract Product update(Product paramProduct, ProductExt paramProductExt, Integer paramInteger, Long paramLong, Long[] paramArrayOfLong, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, Map<String, String> paramMap, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, MultipartFile paramMultipartFile);

    public abstract Product update1(Product paramProduct, ProductExt paramProductExt, Integer paramInteger, Long paramLong, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, Map<String, String> paramMap, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6);

    public abstract Product update(Product paramProduct);

    public abstract void resetSaleTop();

    public abstract Product[] deleteByIds(Long[] paramArrayOfLong);

    public abstract Pagination getPageForTagChannel(String paramString, Long paramLong1, Integer paramInteger, Long paramLong2, Boolean paramBoolean1, String[] paramArrayOfString1, String[] paramArrayOfString2, Boolean paramBoolean2, int paramInt1, Double paramDouble1, Double paramDouble2, int paramInt2, int paramInt3);

    public abstract Integer getStoreByProductPattern(Long paramLong1, Long paramLong2);

    public abstract Integer getTotalStore(Long paramLong);

    public abstract List<Product> getIsRecommend(Boolean paramBoolean, int paramInt);

    public abstract Integer[] getProductByTag(Long paramLong);

    public abstract List<Product> getHistoryProduct(HashSet<Long> paramHashSet, Integer paramInteger);

    public abstract void resetProfitTop();

    public abstract void updateViewCount(Product paramProduct);

    public abstract String getTipFile(String paramString);

    public abstract Product save1(Product paramProduct, ProductExt paramProductExt, Long paramLong1, Integer paramInteger, Long paramLong2, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4);

    public abstract Product[] deleteByUpIds(Long[] paramArrayOfLong);

    public abstract Product saveByApi(Product paramProduct, ProductExt paramProductExt, Long paramLong1, Integer paramInteger, Long paramLong2, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, Long[] paramArrayOfLong1, String[] paramArrayOfString5, Long[] paramArrayOfLong2, String[] paramArrayOfString6, Boolean[] paramArrayOfBoolean, Integer[] paramArrayOfInteger, Double[] paramArrayOfDouble1, Double[] paramArrayOfDouble2, Double[] paramArrayOfDouble3)
            throws Exception;

    public abstract Product updateByApi(Product paramProduct, ProductExt paramProductExt, Long paramLong, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, Map<String, String> paramMap, String[] paramArrayOfString4, String[] paramArrayOfString5, String[] paramArrayOfString6, Integer paramInteger, String[] paramArrayOfString7, Long[] paramArrayOfLong1, String[] paramArrayOfString8, Long[] paramArrayOfLong2, Long[] paramArrayOfLong3, String[] paramArrayOfString9, Boolean[] paramArrayOfBoolean, Integer[] paramArrayOfInteger, Double[] paramArrayOfDouble1, Double[] paramArrayOfDouble2, Double[] paramArrayOfDouble3)
            throws Exception;

    public abstract Long getProductCount(Long paramLong);

    public abstract Long getOverStock(Long paramLong);

    public abstract Pagination getPageOverStockList(Long paramLong, int paramInt1, int paramInt2);
}

