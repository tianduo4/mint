/*     */ package com.jspgou.cms.lucene;
/*     */ 
/*     */ import com.jspgou.cms.dao.ProductDao;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletContext;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.analysis.standard.StandardAnalyzer;
/*     */ import org.apache.lucene.index.CorruptIndexException;
/*     */ import org.apache.lucene.index.IndexReader;
/*     */ import org.apache.lucene.index.IndexWriter;
/*     */ import org.apache.lucene.index.IndexWriter.MaxFieldLength;
/*     */ import org.apache.lucene.queryParser.ParseException;
/*     */ import org.apache.lucene.search.IndexSearcher;
/*     */ import org.apache.lucene.search.Query;
/*     */ import org.apache.lucene.search.Searcher;
/*     */ import org.apache.lucene.search.Sort;
/*     */ import org.apache.lucene.search.SortField;
/*     */ import org.apache.lucene.search.TopDocs;
/*     */ import org.apache.lucene.store.Directory;
/*     */ import org.apache.lucene.store.LockObtainFailedException;
/*     */ import org.apache.lucene.store.SimpleFSDirectory;
/*     */ import org.apache.lucene.util.Version;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class LuceneProductSvcImpl
/*     */   implements LuceneProductSvc
/*     */ {
/*     */   private ProductDao productDao;
/*     */ 
/*     */   @Autowired
/*     */   private ServletContext servletContext;
/*     */ 
/*     */   public int index(String path, Long webId, Date start, Date end)
/*     */     throws CorruptIndexException, LockObtainFailedException, IOException
/*     */   {
/*  45 */     Directory dir = new SimpleFSDirectory(new File(path));
/*  46 */     IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30), 
/*  47 */       true, IndexWriter.MaxFieldLength.LIMITED);
/*     */     try {
/*  49 */       int count = this.productDao.luceneWriteIndex(writer, webId, start, end);
/*  50 */       writer.optimize();
/*  51 */       int i = count;
/*     */       return i;
/*     */     } finally {
/*  53 */       writer.close();
/*  54 */     }throw localObject;
/*     */   }
/*     */ 
/*     */   public void createIndex(Product product) throws IOException {
/*  58 */     String path = this.servletContext.getRealPath("/WEB-INF/lucene");
/*  59 */     Directory dir = new SimpleFSDirectory(new File(path));
/*  60 */     createIndex(product, dir);
/*     */   }
/*     */ 
/*     */   public void createIndex(Product product, Directory dir) throws IOException {
/*  64 */     boolean exist = IndexReader.indexExists(dir);
/*  65 */     IndexWriter writer = new IndexWriter(dir, 
/*  66 */       new StandardAnalyzer(Version.LUCENE_30), !exist, IndexWriter.MaxFieldLength.LIMITED);
/*     */     try {
/*  68 */       writer.addDocument(LuceneProduct.createDocument(product));
/*     */     } finally {
/*  70 */       writer.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateIndex(Product product) throws IOException, ParseException {
/*  75 */     String path = this.servletContext.getRealPath("/WEB-INF/lucene");
/*  76 */     Directory dir = new SimpleFSDirectory(new File(path));
/*  77 */     updateIndex(product, dir);
/*     */   }
/*     */ 
/*     */   public void updateIndex(Product product, Directory dir) throws IOException, ParseException {
/*  81 */     boolean exist = IndexReader.indexExists(dir);
/*  82 */     IndexWriter writer = new IndexWriter(dir, 
/*  83 */       new StandardAnalyzer(Version.LUCENE_30), !exist, IndexWriter.MaxFieldLength.LIMITED);
/*     */     try {
/*  85 */       if (exist) {
/*  86 */         LuceneProduct.delete(product.getId(), writer);
/*     */       }
/*     */ 
/*  89 */       writer.addDocument(LuceneProduct.createDocument(product));
/*     */     } finally {
/*  91 */       writer.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteIndex(Product product) throws IOException, ParseException {
/*  96 */     String path = this.servletContext.getRealPath("/WEB-INF/lucene");
/*  97 */     Directory dir = new SimpleFSDirectory(new File(path));
/*  98 */     deleteIndex(product, dir);
/*     */   }
/*     */ 
/*     */   public void deleteIndex(Product product, Directory dir) throws IOException, ParseException {
/* 102 */     boolean exist = IndexReader.indexExists(dir);
/* 103 */     if (exist) {
/* 104 */       IndexWriter writer = new IndexWriter(dir, 
/* 105 */         new StandardAnalyzer(Version.LUCENE_30), false, 
/* 106 */         IndexWriter.MaxFieldLength.LIMITED);
/*     */       try {
/* 108 */         LuceneProduct.delete(product.getId(), writer);
/*     */       } finally {
/* 110 */         writer.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Pagination search(String path, String queryString, Long webId, Long ctgId, Long brandId, Date start, Date end, int orderBy, int pageNo, int pageSize)
/*     */     throws CorruptIndexException, IOException, ParseException
/*     */   {
/* 125 */     Directory dir = new SimpleFSDirectory(new File(path));
/* 126 */     Searcher searcher = new IndexSearcher(dir);
/*     */     try {
/* 128 */       Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
/* 129 */       Query query = LuceneProduct.createQuery(queryString, webId, ctgId, brandId, start, end, analyzer);
/* 130 */       Sort sort = getSort(orderBy);
/*     */       TopDocs docs;
/*     */       TopDocs docs;
/* 132 */       if (sort != null)
/* 133 */         docs = searcher.search(query, null, pageNo * pageSize, sort);
/*     */       else {
/* 135 */         docs = searcher.search(query, pageNo * pageSize);
/*     */       }
/* 137 */       Pagination p = LuceneProduct.getResult(searcher, docs, pageNo, pageSize);
/* 138 */       Pagination localPagination1 = p;
/*     */       return localPagination1;
/*     */     } finally {
/* 140 */       searcher.close();
/* 141 */     }throw localObject;
/*     */   }
/*     */ 
/*     */   private Sort getSort(int orderBy)
/*     */   {
/* 146 */     Sort sort = null;
/* 147 */     switch (orderBy) {
/*     */     case 1:
/* 149 */       sort = new Sort(new SortField("saleCount", 4, true));
/* 150 */       return sort;
/*     */     case 2:
/* 152 */       sort = new Sort(new SortField("createTime", 3, false));
/* 153 */       return sort;
/*     */     case 3:
/* 155 */       sort = new Sort(new SortField("salePrice", 7, true));
/* 156 */       return sort;
/*     */     case 4:
/* 158 */       sort = new Sort(new SortField("salePrice", 7, false));
/* 159 */       return sort;
/*     */     case 5:
/* 161 */       sort = new Sort(new SortField("viewCount", 6, true));
/* 162 */       return sort;
/*     */     }
/* 164 */     sort = null;
/*     */ 
/* 166 */     return sort;
/*     */   }
/*     */ 
/*     */   public List<LuceneProduct> getlist(String path, String queryString, Long webId, Long ctgId, Long brandId, Date start, Date end, int orderBy, int first, int max)
/*     */     throws IOException, ParseException
/*     */   {
/* 173 */     Directory dir = new SimpleFSDirectory(new File(path));
/* 174 */     Searcher searcher = new IndexSearcher(dir);
/* 175 */     Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
/* 176 */     Query query = LuceneProduct.createQuery(queryString, webId, ctgId, brandId, start, end, analyzer);
/* 177 */     Sort sort = getSort(orderBy);
/* 178 */     if (first < 0) {
/* 179 */       first = 0;
/*     */     }
/* 181 */     if (max < 0)
/* 182 */       max = 0;
/*     */     TopDocs docs;
/*     */     TopDocs docs;
/* 185 */     if (sort != null)
/* 186 */       docs = searcher.search(query, null, first + max, sort);
/*     */     else {
/* 188 */       docs = searcher.search(query, first + max);
/*     */     }
/* 190 */     List list = LuceneProduct.getResultList(searcher, docs, first, max);
/* 191 */     return list;
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setProductDao(ProductDao productDao)
/*     */   {
/* 203 */     this.productDao = productDao;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.lucene.LuceneProductSvcImpl
 * JD-Core Version:    0.6.0
 */