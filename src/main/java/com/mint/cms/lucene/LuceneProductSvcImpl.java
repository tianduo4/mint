package com.mint.cms.lucene;

import com.mint.cms.dao.ProductDao;
import com.mint.cms.entity.Product;
import com.mint.common.page.Pagination;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
public class LuceneProductSvcImpl
        implements LuceneProductSvc {
    private ProductDao productDao;

    @Autowired
    private ServletContext servletContext;

    public int index(String path, Long webId, Date start, Date end)
            throws CorruptIndexException, LockObtainFailedException, IOException {
        Directory dir = new SimpleFSDirectory(new File(path));
        IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30),
                true, IndexWriter.MaxFieldLength.LIMITED);
        try {
            int count = this.productDao.luceneWriteIndex(writer, webId, start, end);
            writer.optimize();
            int i = count;
            return i;
        } finally {
            writer.close();
        }
    }

    public void createIndex(Product product) throws IOException {
        String path = this.servletContext.getRealPath("/WEB-INF/lucene");
        Directory dir = new SimpleFSDirectory(new File(path));
        createIndex(product, dir);
    }

    public void createIndex(Product product, Directory dir) throws IOException {
        boolean exist = IndexReader.indexExists(dir);
        IndexWriter writer = new IndexWriter(dir,
                new StandardAnalyzer(Version.LUCENE_30), !exist, IndexWriter.MaxFieldLength.LIMITED);
        try {
            writer.addDocument(LuceneProduct.createDocument(product));
        } finally {
            writer.close();
        }
    }

    public void updateIndex(Product product) throws IOException, ParseException {
        String path = this.servletContext.getRealPath("/WEB-INF/lucene");
        Directory dir = new SimpleFSDirectory(new File(path));
        updateIndex(product, dir);
    }

    public void updateIndex(Product product, Directory dir) throws IOException, ParseException {
        boolean exist = IndexReader.indexExists(dir);
        IndexWriter writer = new IndexWriter(dir,
                new StandardAnalyzer(Version.LUCENE_30), !exist, IndexWriter.MaxFieldLength.LIMITED);
        try {
            if (exist) {
                LuceneProduct.delete(product.getId(), writer);
            }

            writer.addDocument(LuceneProduct.createDocument(product));
        } finally {
            writer.close();
        }
    }

    public void deleteIndex(Product product) throws IOException, ParseException {
        String path = this.servletContext.getRealPath("/WEB-INF/lucene");
        Directory dir = new SimpleFSDirectory(new File(path));
        deleteIndex(product, dir);
    }

    public void deleteIndex(Product product, Directory dir) throws IOException, ParseException {
        boolean exist = IndexReader.indexExists(dir);
        if (exist) {
            IndexWriter writer = new IndexWriter(dir,
                    new StandardAnalyzer(Version.LUCENE_30), false,
                    IndexWriter.MaxFieldLength.LIMITED);
            try {
                LuceneProduct.delete(product.getId(), writer);
            } finally {
                writer.close();
            }
        }
    }

    public Pagination search(String path, String queryString, Long webId, Long ctgId, Long brandId, Date start, Date end, int orderBy, int pageNo, int pageSize)
            throws CorruptIndexException, IOException, ParseException {
        Directory dir = new SimpleFSDirectory(new File(path));
        Searcher searcher = new IndexSearcher(dir);
        try {
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
            Query query = LuceneProduct.createQuery(queryString, webId, ctgId, brandId, start, end, analyzer);
            Sort sort = getSort(orderBy);
            TopDocs docs;
            if (sort != null)
                docs = searcher.search(query, null, pageNo * pageSize, sort);
            else {
                docs = searcher.search(query, pageNo * pageSize);
            }
            Pagination p = LuceneProduct.getResult(searcher, docs, pageNo, pageSize);
            Pagination localPagination1 = p;
            return localPagination1;
        } catch (Exception e) {
            throw e;
        } finally {
            searcher.close();
        }
    }

    private Sort getSort(int orderBy) {
        Sort sort = null;
        switch (orderBy) {
            case 1:
                sort = new Sort(new SortField("saleCount", 4, true));
                return sort;
            case 2:
                sort = new Sort(new SortField("createTime", 3, false));
                return sort;
            case 3:
                sort = new Sort(new SortField("salePrice", 7, true));
                return sort;
            case 4:
                sort = new Sort(new SortField("salePrice", 7, false));
                return sort;
            case 5:
                sort = new Sort(new SortField("viewCount", 6, true));
                return sort;
        }
        sort = null;

        return sort;
    }

    public List<LuceneProduct> getlist(String path, String queryString, Long webId, Long ctgId, Long brandId, Date start, Date end, int orderBy, int first, int max)
            throws IOException, ParseException {
        Directory dir = new SimpleFSDirectory(new File(path));
        Searcher searcher = new IndexSearcher(dir);
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
        Query query = LuceneProduct.createQuery(queryString, webId, ctgId, brandId, start, end, analyzer);
        Sort sort = getSort(orderBy);
        if (first < 0) {
            first = 0;
        }
        if (max < 0)
            max = 0;
        TopDocs docs;
        if (sort != null)
            docs = searcher.search(query, null, first + max, sort);
        else {
            docs = searcher.search(query, first + max);
        }
        List list = LuceneProduct.getResultList(searcher, docs, first, max);
        return list;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}

