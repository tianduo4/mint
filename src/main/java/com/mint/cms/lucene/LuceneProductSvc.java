package com.mint.cms.lucene;

import com.mint.cms.entity.Product;
import com.mint.common.page.Pagination;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;

public abstract interface LuceneProductSvc {
    public abstract int index(String paramString, Long paramLong, Date paramDate1, Date paramDate2)
            throws CorruptIndexException, LockObtainFailedException, IOException;

    public abstract void createIndex(Product paramProduct)
            throws IOException;

    public abstract void createIndex(Product paramProduct, Directory paramDirectory)
            throws IOException;

    public abstract void updateIndex(Product paramProduct)
            throws IOException, ParseException;

    public abstract void updateIndex(Product paramProduct, Directory paramDirectory)
            throws IOException, ParseException;

    public abstract void deleteIndex(Product paramProduct)
            throws IOException, ParseException;

    public abstract void deleteIndex(Product paramProduct, Directory paramDirectory)
            throws IOException, ParseException;

    public abstract Pagination search(String paramString1, String paramString2, Long paramLong1, Long paramLong2, Long paramLong3, Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, int paramInt3)
            throws CorruptIndexException, IOException, ParseException;

    public abstract List<LuceneProduct> getlist(String paramString1, String paramString2, Long paramLong1, Long paramLong2, Long paramLong3, Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, int paramInt3)
            throws IOException, ParseException;
}

