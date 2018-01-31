package com.jspgou.cms.dao;

import com.jspgou.cms.entity.Product;
import com.jspgou.cms.entity.ProductTag;
import com.jspgou.common.hibernate4.Updater;
import com.jspgou.common.page.Pagination;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;

public abstract interface ProductDao
{
  public abstract List<Product> getList(Long paramLong1, Long paramLong2, String paramString, boolean paramBoolean);

  public abstract Pagination getPage(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);

  public abstract Pagination getPage1(Long paramLong, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);

  public abstract List<Product> findAll();

  public abstract Pagination getPageByCategory(Integer paramInteger1, String paramString1, String paramString2, Integer paramInteger2, Boolean paramBoolean1, Boolean paramBoolean2, Boolean paramBoolean3, Boolean paramBoolean4, Long paramLong, Double paramDouble1, Double paramDouble2, Integer paramInteger3, Integer paramInteger4, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract Pagination getPageByWebsite(Long paramLong1, String paramString1, String paramString2, Integer paramInteger1, Boolean paramBoolean1, Boolean paramBoolean2, Boolean paramBoolean3, Boolean paramBoolean4, Long paramLong2, Double paramDouble1, Double paramDouble2, Integer paramInteger2, Integer paramInteger3, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract Pagination getPageByTag(Long paramLong, Integer paramInteger, Boolean paramBoolean1, Boolean paramBoolean2, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract Pagination getPageByStockWarning(Long paramLong, Integer paramInteger, Boolean paramBoolean, int paramInt1, int paramInt2);

  public abstract List<Product> getListByCategory(Integer paramInteger, Boolean paramBoolean1, Boolean paramBoolean2, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract List<Product> getListByWebsite(Long paramLong, Boolean paramBoolean1, Boolean paramBoolean2, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract List<Product> getListByTag(Long paramLong, Integer paramInteger, Boolean paramBoolean1, Boolean paramBoolean2, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract int luceneWriteIndex(IndexWriter paramIndexWriter, Long paramLong, Date paramDate1, Date paramDate2)
    throws CorruptIndexException, IOException;

  public abstract List<Product> getListByWebsite1(Long paramLong, Boolean paramBoolean1, Boolean paramBoolean2, Boolean paramBoolean3, Boolean paramBoolean4, int paramInt1, int paramInt2, boolean paramBoolean);

  public abstract int deleteTagAssociation(ProductTag[] paramArrayOfProductTag);

  public abstract Product findById(Long paramLong);

  public abstract Product save(Product paramProduct);

  public abstract Product updateByUpdater(Updater<Product> paramUpdater);

  public abstract Product deleteById(Long paramLong);

  public abstract Pagination getPageByCategoryChannel(String paramString, Integer paramInteger, Boolean paramBoolean1, String[] paramArrayOfString1, String[] paramArrayOfString2, Boolean paramBoolean2, int paramInt1, Double paramDouble1, Double paramDouble2, int paramInt2, int paramInt3, boolean paramBoolean);

  public abstract List<Product> getIsRecommend(Boolean paramBoolean, int paramInt);

  public abstract Integer[] getProductByTag(Long paramLong);

  public abstract List<Product> getHistoryProduct(HashSet<Long> paramHashSet, Integer paramInteger);

  public abstract Long getProductCount(Long paramLong);

  public abstract Long getOverStock(Long paramLong);

  public abstract Pagination getPageOverStockList(Long paramLong, int paramInt1, int paramInt2);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.ProductDao
 * JD-Core Version:    0.6.0
 */