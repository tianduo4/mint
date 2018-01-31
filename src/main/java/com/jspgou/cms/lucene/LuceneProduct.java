/*     */ package com.jspgou.cms.lucene;
/*     */ 
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.cms.entity.ProductInfo;
/*     */ import com.jspgou.common.file.lucene.MoneyTools;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.document.DateTools;
/*     */ import org.apache.lucene.document.DateTools.Resolution;
/*     */ import org.apache.lucene.document.Document;
/*     */ import org.apache.lucene.document.Field;
/*     */ import org.apache.lucene.document.Field.Index;
/*     */ import org.apache.lucene.document.Field.Store;
/*     */ import org.apache.lucene.document.Fieldable;
/*     */ import org.apache.lucene.index.CorruptIndexException;
/*     */ import org.apache.lucene.index.IndexWriter;
/*     */ import org.apache.lucene.index.Term;
/*     */ import org.apache.lucene.queryParser.MultiFieldQueryParser;
/*     */ import org.apache.lucene.search.*;
import org.apache.lucene.search.BooleanClause.Occur;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ import org.apache.lucene.util.Version;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class LuceneProduct
/*     */   implements ProductInfo
/*     */ {
/*  46 */   private static final Logger log = LoggerFactory.getLogger(LuceneProduct.class);
/*     */   public static final String ID = "id";
/*     */   public static final String WEBSITE_ID = "websiteId";
/*     */   public static final String CATEGORY_ID_ARRAY = "categoryIdArray";
/*     */   public static final String CATEGORY_NAME_ATTRY = "categoryNameArray";
/*     */   public static final String BRAND_ID = "brandId";
/*     */   public static final String BRAND_NAME = "brandName";
/*     */   public static final String NAME = "name";
/*     */   public static final String DESCRIPTION = "description";
/*     */   public static final String URL = "url";
/*     */   public static final String DETAIL_IMG_URL = "detailImgUrl";
/*     */   public static final String LIST_IMG_URL = "listImgUrl";
/*     */   public static final String COVER_IMG_URL = "coverImgUrl";
/*     */   public static final String MIN_IMG_URL = "minImgUrl";
/*     */   public static final String MARKET_PRICE = "marketPrice";
/*     */   public static final String SALE_PRICE = "salePrice";
/*     */   public static final String KEYWORD_ARRAY = "keywordArray";
/*     */   public static final String TAG_ARRAY = "tagArray";
/*     */   public static final String CREATE_TIME = "createTime";
/*     */   public static final String SALE_COUNT = "saleCount";
/*     */   public static final String VIEW_COUNT = "viewCount";
/* 302 */   public static final String[] QUERY_FIELD = { "name", "categoryNameArray", 
/* 303 */     "brandName", "description" };
/*     */ 
/* 304 */   public static final BooleanClause.Occur[] QUERY_FLAGS = { 
/* 305 */     BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD,
/* 306 */     BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD };
/*     */   private Long id;
/*     */   private Long websiteId;
/*     */   private Collection<Integer> categoryIdArray;
/*     */   private Collection<String> categoryNameArray;
/*     */   private String brandName;
/*     */   private String name;
/*     */   private String url;
/*     */   private String description;
/*     */   private String detailImgUrl;
/*     */   private String listImgUrl;
/*     */   private String minImgUrl;
/*     */   private Double marketPrice;
/*     */   private Double salePrice;
/*     */   private Collection<String> keywordArray;
/*     */   private Collection<String> tagArray;
/*     */   private Date createTime;
/*     */   private String coverImgUrl;
/*     */   private Integer saleCount;
/*     */   private Long viewCount;
/*     */   private Long brandId;
/*     */ 
/*     */   public static void delete(Long webId, Long ctgId, Long storeId, Double beginPrice, Double endPrice, Date startDate, Date endDate, IndexWriter writer)
/*     */     throws CorruptIndexException, IOException, org.apache.lucene.queryParser.ParseException
/*     */   {
/*  52 */     writer.deleteDocuments(createQuery(null, webId, ctgId, storeId, 
/*  53 */       startDate, endDate, null));
/*     */   }
/*     */ 
/*     */   public static void delete(Long productId, IndexWriter writer) throws CorruptIndexException, IOException, org.apache.lucene.queryParser.ParseException
/*     */   {
/*  58 */     writer.deleteDocuments(new Term("id", productId.toString()));
/*     */   }
/*     */ 
/*     */   public static Query createQuery(String queryString, Long webId, Long ctgId, Long brandId, Date start, Date end, Analyzer analyzer)
/*     */     throws org.apache.lucene.queryParser.ParseException
/*     */   {
/*  66 */     BooleanQuery bq = new BooleanQuery();
/*     */ 
/*  68 */     if (!StringUtils.isBlank(queryString)) {
/*  69 */       Query q = MultiFieldQueryParser.parse(Version.LUCENE_30, queryString, 
/*  70 */         QUERY_FIELD, QUERY_FLAGS, analyzer);
/*  71 */       bq.add(q, BooleanClause.Occur.MUST);
/*     */     }
/*     */ 
/*  74 */     if (webId != null) {
/*  75 */       Query q = new TermQuery(new Term("websiteId", webId.toString()));
/*  76 */       bq.add(q, BooleanClause.Occur.MUST);
/*     */     }
/*  78 */     if (ctgId != null) {
/*  79 */       Query q = new TermQuery(new Term("categoryIdArray", ctgId.toString()));
/*  80 */       bq.add(q, BooleanClause.Occur.MUST);
/*     */     }
/*  82 */     if (brandId != null) {
/*  83 */       Query q = new TermQuery(new Term("brandId", brandId.toString()));
/*  84 */       bq.add(q, BooleanClause.Occur.MUST);
/*     */     }
/*  86 */     if ((start != null) || (end != null)) {
/*  87 */       String startDate = null;
/*  88 */       String endDate = null;
/*  89 */       if (start != null) {
/*  90 */         startDate = DateTools.dateToString(start, DateTools.Resolution.DAY);
/*     */       }
/*  92 */       if (end != null) {
/*  93 */         endDate = DateTools.dateToString(end, DateTools.Resolution.DAY);
/*     */       }
/*  95 */       Query q = new TermRangeQuery("createTime", startDate, endDate, true, true);
/*  96 */       bq.add(q, BooleanClause.Occur.MUST);
/*     */     }
/*  98 */     return bq;
/*     */   }
/*     */ 
/*     */   public static Pagination getResult(Searcher searcher, TopDocs docs, int pageNo, int pageSize)
/*     */     throws CorruptIndexException, IOException
/*     */   {
/* 104 */     List list = new ArrayList(pageSize);
/* 105 */     ScoreDoc[] hits = docs.scoreDocs;
/* 106 */     int endIndex = pageNo * pageSize;
/* 107 */     int len = hits.length;
/* 108 */     if (endIndex > len) {
/* 109 */       endIndex = len;
/*     */     }
/* 111 */     for (int i = (pageNo - 1) * pageSize; i < endIndex; i++) {
/* 112 */       Document d = searcher.doc(hits[i].doc);
/* 113 */       list.add(createProduct(d));
/*     */     }
/* 115 */     return new Pagination(pageNo, pageSize, docs.totalHits, list);
/*     */   }
/*     */ 
/*     */   public static List<LuceneProduct> getResultList(Searcher searcher, TopDocs docs, int first, int max) throws CorruptIndexException, IOException
/*     */   {
/* 120 */     List list = new ArrayList(max);
/* 121 */     ScoreDoc[] hits = docs.scoreDocs;
/* 122 */     if (first < 0) {
/* 123 */       first = 0;
/*     */     }
/* 125 */     if (max < 0) {
/* 126 */       max = 0;
/*     */     }
/* 128 */     int last = first + max;
/* 129 */     int len = hits.length;
/* 130 */     if (last > len) {
/* 131 */       last = len;
/*     */     }
/* 133 */     for (int i = first; i < last; i++) {
/* 134 */       Document d = searcher.doc(hits[i].doc);
/* 135 */       list.add(createProduct(d));
/*     */     }
/* 137 */     return list;
/*     */   }
/*     */ 
/*     */   public static Document createDocument(Product p)
/*     */   {
/* 148 */     Document doc = new Document();
/* 149 */     doc.add(
/* 150 */       new Field("id", p.getId().toString(), Field.Store.YES, 
/* 150 */       Field.Index.NOT_ANALYZED));
/* 151 */     doc.add(
/* 152 */       new Field("websiteId", p.getWebsite().getId().toString(), 
/* 152 */       Field.Store.YES, Field.Index.NOT_ANALYZED));
/* 153 */     if (p.getBrandId() != null) {
/* 154 */       doc.add(
/* 155 */         new Field("brandId", p.getBrandId(), 
/* 155 */         Field.Store.YES, Field.Index.NOT_ANALYZED));
/*     */     }
/* 157 */     for (Integer id : p.getCategoryIdArray()) {
/* 158 */       doc.add(
/* 159 */         new Field("categoryIdArray", id.toString(), 
/* 159 */         Field.Store.YES, Field.Index.NOT_ANALYZED));
/*     */     }
/*     */ 
/* 162 */     doc.add(
/* 163 */       new Field("name", p.getName(), Field.Store.YES, 
/* 163 */       Field.Index.ANALYZED));
/* 164 */     for (String name : p.getCategoryNameArray()) {
/* 165 */       doc.add(
/* 166 */         new Field("categoryNameArray", name, Field.Store.YES, 
/* 166 */         Field.Index.ANALYZED));
/*     */     }
/* 168 */     if (!StringUtils.isBlank(p.getMdescription())) {
/* 169 */       doc.add(
/* 170 */         new Field("description", p.getMdescription(), Field.Store.YES, 
/* 170 */         Field.Index.ANALYZED));
/*     */     }
/* 172 */     if (!StringUtils.isBlank(p.getBrandName())) {
/* 173 */       doc.add(
/* 174 */         new Field("brandName", p.getBrandName(), Field.Store.YES, 
/* 174 */         Field.Index.ANALYZED));
/*     */     }
/* 176 */     doc.add(new Field("url", p.getUrl(), Field.Store.YES, Field.Index.ANALYZED));
/*     */ 
/* 178 */     if (!StringUtils.isBlank(p.getDetailImgUrl())) {
/* 179 */       doc.add(
/* 180 */         new Field("detailImgUrl", p.getDetailImgUrl(), 
/* 180 */         Field.Store.YES, Field.Index.NO));
/*     */     }
/* 182 */     if (!StringUtils.isBlank(p.getListImgUrl())) {
/* 183 */       doc.add(
/* 184 */         new Field("listImgUrl", p.getListImgUrl(), Field.Store.YES, 
/* 184 */         Field.Index.NO));
/*     */     }
/* 186 */     if (!StringUtils.isBlank(p.getCoverImgUrl())) {
/* 187 */       doc.add(
/* 188 */         new Field("coverImgUrl", p.getCoverImgUrl(), Field.Store.YES, 
/* 188 */         Field.Index.NO));
/*     */     }
/* 190 */     if (!StringUtils.isBlank(p.getMinImgUrl())) {
/* 191 */       doc.add(
/* 192 */         new Field("minImgUrl", p.getMinImgUrl(), Field.Store.YES, 
/* 192 */         Field.Index.NO));
/*     */     }
/*     */ 
/* 195 */     doc.add(
/* 196 */       new Field("marketPrice", MoneyTools.moneyToString(p
/* 196 */       .getMarketPrice()), Field.Store.YES, Field.Index.NOT_ANALYZED));
/*     */ 
/* 198 */     doc.add(new Field("salePrice", String.valueOf(p.getSalePrice()), Field.Store.YES, Field.Index.NOT_ANALYZED));
/*     */ 
/* 200 */     doc.add(new Field("saleCount", String.valueOf(p.getSaleCount()), Field.Store.YES, Field.Index.NOT_ANALYZED));
/* 201 */     doc.add(new Field("viewCount", String.valueOf(p.getViewCount()), Field.Store.YES, Field.Index.NOT_ANALYZED));
/* 202 */     doc.add(
/* 204 */       new Field("createTime", DateTools.dateToString(
/* 203 */       p.getCreateTime(), DateTools.Resolution.MILLISECOND), 
/* 204 */       Field.Store.YES, Field.Index.NOT_ANALYZED));
/*     */ 
/* 207 */     Collection keywords = p.getKeywordArray();
/* 208 */     if (keywords != null) {
/* 209 */       for (String keyword : keywords) {
/* 210 */         doc.add(
/* 211 */           new Field("keywordArray", keyword, Field.Store.YES, 
/* 211 */           Field.Index.ANALYZED));
/*     */       }
/*     */     }
/* 214 */     Collection tags = p.getTagArray();
/* 215 */     if (tags != null) {
/* 216 */       for (String tag : tags) {
/* 217 */         doc.add(
/* 218 */           new Field("tagArray", tag, Field.Store.YES, 
/* 218 */           Field.Index.ANALYZED));
/*     */       }
/*     */     }
/* 221 */     return doc;
/*     */   }
/*     */ 
/*     */   public static LuceneProduct createProduct(Document d)
/*     */   {
/* 226 */     LuceneProduct p = new LuceneProduct();
/* 227 */     List list = d.getFields();
/*     */ 
/* 229 */     for (Fieldable f : list) {
/* 230 */       String name = f.name();
/* 231 */       if (name.equals("keywordArray"))
/* 232 */         p.addToKeyworeds(f.stringValue());
/* 233 */       else if (name.equals("tagArray"))
/* 234 */         p.addToTags(f.stringValue());
/* 235 */       else if (name.equals("id"))
/* 236 */         p.setId(Long.valueOf(f.stringValue()));
/* 237 */       else if (name.equals("websiteId"))
/* 238 */         p.setWebsiteId(Long.valueOf(f.stringValue()));
/* 239 */       else if (name.equals("brandId"))
/* 240 */         p.setWebsiteId(Long.valueOf(f.stringValue()));
/* 241 */       else if (name.equals("categoryIdArray"))
/* 242 */         p.addToCategoryIds(Integer.valueOf(f.stringValue()));
/* 243 */       else if (name.equals("createTime"))
/*     */         try {
/* 245 */           p.setCreateTime(DateTools.stringToDate(f.stringValue()));
/*     */         } catch (java.text.ParseException e) {
/* 247 */           log.error("lucene date format faild", e);
/*     */         }
/* 249 */       else if (name.equals("name"))
/* 250 */         p.setName(f.stringValue());
/* 251 */       else if (name.equals("categoryNameArray"))
/* 252 */         p.addToCategoryNames(f.stringValue());
/* 253 */       else if (name.equals("brandName"))
/* 254 */         p.setBrandName(f.stringValue());
/* 255 */       else if (name.equals("description"))
/* 256 */         p.setDescription(f.stringValue());
/* 257 */       else if (name.equals("url"))
/* 258 */         p.setUrl(f.stringValue());
/* 259 */       else if (name.equals("detailImgUrl"))
/* 260 */         p.setDetailImgUrl(f.stringValue());
/* 261 */       else if (name.equals("listImgUrl"))
/* 262 */         p.setListImgUrl(f.stringValue());
/* 263 */       else if (name.equals("coverImgUrl"))
/* 264 */         p.setCoverImgUrl(f.stringValue());
/* 265 */       else if (name.equals("minImgUrl"))
/* 266 */         p.setMinImgUrl(f.stringValue());
/* 267 */       else if (name.equals("marketPrice"))
/* 268 */         p.setMarketPrice(MoneyTools.stringToMoney(f.stringValue()));
/* 269 */       else if (name.equals("salePrice"))
/* 270 */         p.setSalePrice(Double.valueOf(f.stringValue()));
/* 271 */       else if (name.equals("saleCount"))
/* 272 */         p.setSaleCount(Integer.valueOf(f.stringValue()));
/* 273 */       else if (name.equals("viewCount")) {
/* 274 */         p.setViewCount(Long.valueOf(f.stringValue()));
/*     */       }
/*     */     }
/*     */ 
/* 278 */     return p;
/*     */   }
/*     */ 
/*     */   public void addToKeyworeds(String keyword)
/*     */   {
/* 309 */     if (this.keywordArray == null) {
/* 310 */       this.keywordArray = new ArrayList();
/*     */     }
/* 312 */     this.keywordArray.add(keyword);
/*     */   }
/*     */ 
/*     */   public void addToTags(String tag) {
/* 316 */     if (this.tagArray == null) {
/* 317 */       this.tagArray = new ArrayList();
/*     */     }
/* 319 */     this.tagArray.add(tag);
/*     */   }
/*     */ 
/*     */   public void addToCategoryNames(String categoryName) {
/* 323 */     if (this.categoryNameArray == null) {
/* 324 */       this.categoryNameArray = new ArrayList();
/*     */     }
/* 326 */     this.categoryNameArray.add(categoryName);
/*     */   }
/*     */ 
/*     */   public void addToCategoryIds(Integer categoryId) {
/* 330 */     if (this.categoryIdArray == null) {
/* 331 */       this.categoryIdArray = new ArrayList();
/*     */     }
/* 333 */     this.categoryIdArray.add(categoryId);
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 359 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Long id) {
/* 363 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 368 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 372 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getDescription() {
/* 376 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description) {
/* 380 */     this.description = description;
/*     */   }
/*     */ 
/*     */   public String getDetailImgUrl()
/*     */   {
/* 385 */     return this.detailImgUrl;
/*     */   }
/*     */ 
/*     */   public void setDetailImgUrl(String detailImgUrl) {
/* 389 */     this.detailImgUrl = detailImgUrl;
/*     */   }
/*     */ 
/*     */   public String getListImgUrl()
/*     */   {
/* 394 */     return this.listImgUrl;
/*     */   }
/*     */ 
/*     */   public void setListImgUrl(String listImgUrl) {
/* 398 */     this.listImgUrl = listImgUrl;
/*     */   }
/*     */   public String getCoverImgUrl() {
/* 401 */     return this.coverImgUrl;
/*     */   }
/*     */ 
/*     */   public void setCoverImgUrl(String coverImgUrl) {
/* 405 */     this.coverImgUrl = coverImgUrl;
/*     */   }
/*     */ 
/*     */   public String getMinImgUrl()
/*     */   {
/* 410 */     return this.minImgUrl;
/*     */   }
/*     */ 
/*     */   public void setMinImgUrl(String minImgUrl) {
/* 414 */     this.minImgUrl = minImgUrl;
/*     */   }
/*     */ 
/*     */   public Double getMarketPrice()
/*     */   {
/* 419 */     return this.marketPrice;
/*     */   }
/*     */ 
/*     */   public void setMarketPrice(Double marketPrice) {
/* 423 */     this.marketPrice = marketPrice;
/*     */   }
/*     */ 
/*     */   public Double getSalePrice()
/*     */   {
/* 428 */     return this.salePrice;
/*     */   }
/*     */ 
/*     */   public void setSalePrice(Double salePrice) {
/* 432 */     this.salePrice = salePrice;
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/* 437 */     return this.url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url) {
/* 441 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public Long getWebsiteId() {
/* 445 */     return this.websiteId;
/*     */   }
/*     */ 
/*     */   public void setWebsiteId(Long websiteId) {
/* 449 */     this.websiteId = websiteId;
/*     */   }
/*     */ 
/*     */   public Date getCreateTime()
/*     */   {
/* 454 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Date createTime) {
/* 458 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public String getBrandName()
/*     */   {
/* 463 */     return this.brandName;
/*     */   }
/*     */ 
/*     */   public void setBrandName(String brandName) {
/* 467 */     this.brandName = brandName;
/*     */   }
/*     */ 
/*     */   public Collection<String> getKeywordArray()
/*     */   {
/* 472 */     return this.keywordArray;
/*     */   }
/*     */ 
/*     */   public void setKeywordArray(Collection<String> keywordArray) {
/* 476 */     this.keywordArray = keywordArray;
/*     */   }
/*     */ 
/*     */   public Collection<String> getTagArray()
/*     */   {
/* 481 */     return this.tagArray;
/*     */   }
/*     */ 
/*     */   public void setTagArray(Collection<String> tagArray) {
/* 485 */     this.tagArray = tagArray;
/*     */   }
/*     */ 
/*     */   public Collection<String> getCategoryNameArray()
/*     */   {
/* 490 */     return this.categoryNameArray;
/*     */   }
/*     */ 
/*     */   public void setCategoryNameArray(Collection<String> categoryNameArray) {
/* 494 */     this.categoryNameArray = categoryNameArray;
/*     */   }
/*     */ 
/*     */   public Collection<Integer> getCategoryIdArray()
/*     */   {
/* 499 */     return this.categoryIdArray;
/*     */   }
/*     */ 
/*     */   public void setCategoryIdArray(Collection<Integer> categoryIdArray) {
/* 503 */     this.categoryIdArray = categoryIdArray;
/*     */   }
/*     */ 
/*     */   public void setSaleCount(Integer saleCount) {
/* 507 */     this.saleCount = saleCount;
/*     */   }
/*     */ 
/*     */   public Integer getSaleCount() {
/* 511 */     return this.saleCount;
/*     */   }
/*     */ 
/*     */   public void setViewCount(Long viewCount) {
/* 515 */     this.viewCount = viewCount;
/*     */   }
/*     */ 
/*     */   public Long getViewCount() {
/* 519 */     return this.viewCount;
/*     */   }
/*     */ 
/*     */   public void setBrandId(Long brandId) {
/* 523 */     this.brandId = brandId;
/*     */   }
/*     */ 
/*     */   public Long getBrandId() {
/* 527 */     return this.brandId;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.lucene.LuceneProduct
 * JD-Core Version:    0.6.0
 */