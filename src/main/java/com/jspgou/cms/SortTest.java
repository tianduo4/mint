/*    */ package com.jspgou.cms;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import org.apache.lucene.analysis.standard.StandardAnalyzer;
/*    */ import org.apache.lucene.document.Document;
/*    */ import org.apache.lucene.document.Field;
/*    */ import org.apache.lucene.document.Field.Index;
/*    */ import org.apache.lucene.document.Field.Store;
/*    */ import org.apache.lucene.index.IndexWriter;
/*    */ import org.apache.lucene.index.IndexWriter.MaxFieldLength;
/*    */ import org.apache.lucene.index.Term;
/*    */ import org.apache.lucene.search.IndexSearcher;
/*    */ import org.apache.lucene.search.ScoreDoc;
/*    */ import org.apache.lucene.search.Sort;
/*    */ import org.apache.lucene.search.TermQuery;
/*    */ import org.apache.lucene.search.TopFieldDocs;
/*    */ import org.apache.lucene.store.Directory;
/*    */ import org.apache.lucene.store.FSDirectory;
/*    */ import org.apache.lucene.util.Version;
/*    */ 
/*    */ public class SortTest
/*    */ {
/*    */   public static void makeItem(IndexWriter writer, String bookNumber, String bookName, String publishDate)
/*    */     throws Exception
/*    */   {
/* 21 */     writer.setUseCompoundFile(false);
/* 22 */     Document doc = new Document();
/* 23 */     Field f1 = new Field("bookNumber", bookNumber, Field.Store.YES, 
/* 24 */       Field.Index.NOT_ANALYZED);
/* 25 */     Field f2 = new Field("bookName", bookName, Field.Store.YES, 
/* 26 */       Field.Index.ANALYZED);
/* 27 */     Field f3 = new Field("publishDate", publishDate, Field.Store.YES, 
/* 28 */       Field.Index.NOT_ANALYZED);
/* 29 */     doc.add(f1);
/* 30 */     doc.add(f2);
/* 31 */     doc.add(f3);
/* 32 */     writer.addDocument(doc);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 36 */     String Index_Store_Path = "D:/index/1";
/* 37 */     File file = new File(Index_Store_Path);
/*    */     try
/*    */     {
/* 40 */       Directory Index = FSDirectory.open(file);
/* 41 */       IndexWriter writer = new IndexWriter(Index, new StandardAnalyzer(Version.LUCENE_CURRENT), true, 
/* 42 */         IndexWriter.MaxFieldLength.LIMITED);
/* 43 */       writer.setUseCompoundFile(false);
/*    */ 
/* 45 */       Document doc1 = new Document();
/* 46 */       Field f11 = new Field("bookNumber", "0000001", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 47 */       Field f12 = new Field("bookName", "钢铁是怎样炼成的", Field.Store.YES, Field.Index.ANALYZED);
/* 48 */       Field f13 = new Field("publishDate", "1970-01-01", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 49 */       doc1.add(f11);
/* 50 */       doc1.add(f12);
/* 51 */       doc1.add(f13);
/*    */ 
/* 53 */       Document doc2 = new Document();
/* 54 */       Field f21 = new Field("bookNumber", "0000002", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 55 */       Field f22 = new Field("bookName", "钢铁战士", Field.Store.YES, Field.Index.ANALYZED);
/* 56 */       Field f23 = new Field("publishDate", "1970-01-01", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 57 */       doc2.add(f21);
/* 58 */       doc2.add(f22);
/* 59 */       doc2.add(f23);
/*    */ 
/* 61 */       Document doc3 = new Document();
/* 62 */       Field f31 = new Field("bookNumber", "0000003", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 63 */       Field f32 = new Field("bookName", "篱笆女人和狗", Field.Store.YES, Field.Index.ANALYZED);
/* 64 */       Field f33 = new Field("publishDate", "1970-01-01", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 65 */       doc3.add(f31);
/* 66 */       doc3.add(f32);
/* 67 */       doc3.add(f33);
/*    */ 
/* 69 */       Document doc4 = new Document();
/* 70 */       Field f41 = new Field("bookNumber", "0000004", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 71 */       Field f42 = new Field("bookName", "女人是水做的", Field.Store.YES, Field.Index.ANALYZED);
/* 72 */       Field f43 = new Field("publishDate", "1970-01-01", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 73 */       doc4.add(f41);
/* 74 */       doc4.add(f42);
/* 75 */       doc4.add(f43);
/*    */ 
/* 77 */       Document doc5 = new Document();
/* 78 */       Field f51 = new Field("bookNumber", "0000005", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 79 */       Field f52 = new Field("bookName", "英雄儿女", Field.Store.YES, Field.Index.ANALYZED);
/* 80 */       Field f53 = new Field("publishDate", "1970-01-01", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 81 */       doc5.add(f51);
/* 82 */       doc5.add(f52);
/* 83 */       doc5.add(f53);
/*    */ 
/* 85 */       Document doc6 = new Document();
/* 86 */       Field f61 = new Field("bookNumber", "0000006", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 87 */       Field f62 = new Field("bookName", "白毛女", Field.Store.YES, Field.Index.ANALYZED);
/* 88 */       Field f63 = new Field("publishDate", "1970-01-01", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 89 */       doc6.add(f61);
/* 90 */       doc6.add(f62);
/* 91 */       doc6.add(f63);
/*    */ 
/* 93 */       Document doc7 = new Document();
/* 94 */       Field f71 = new Field("bookNumber", "0000007", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 95 */       Field f72 = new Field("bookName", "我的兄弟和女儿", Field.Store.YES, Field.Index.ANALYZED);
/* 96 */       Field f73 = new Field("publishDate", "1970-01-01", Field.Store.YES, Field.Index.NOT_ANALYZED);
/* 97 */       doc7.add(f71);
/* 98 */       doc7.add(f72);
/* 99 */       doc7.add(f73);
/*    */ 
/* 101 */       writer.addDocument(doc1);
/* 102 */       writer.addDocument(doc2);
/* 103 */       writer.addDocument(doc3);
/* 104 */       writer.addDocument(doc4);
/* 105 */       writer.addDocument(doc5);
/* 106 */       writer.addDocument(doc6);
/* 107 */       writer.addDocument(doc7);
/* 108 */       writer.optimize();
/* 109 */       writer.close();
/* 110 */       IndexSearcher searcher = new IndexSearcher(Index);
/* 111 */       TermQuery q = new TermQuery(new Term("bookName", "女"));
/* 112 */       ScoreDoc[] hits = searcher.search(q, null, 1000, Sort.INDEXORDER).scoreDocs;
/* 113 */       for (int i = 0; i < hits.length; i++) {
/* 114 */         Document hitDoc = searcher.doc(hits[i].doc);
/* 115 */         System.out.print("书名 ：");
/* 116 */         System.out.println(hitDoc.get("bookName"));
/* 117 */         System.out.print("得分 ：");
/* 118 */         System.out.println(hits[i].score);
/* 119 */         System.out.print("内部ID ：");
/* 120 */         System.out.println(hits[i].doc);
/* 121 */         System.out.print("书号 ：");
/* 122 */         System.out.println(hitDoc.get("bookNumber"));
/* 123 */         System.out.print("发行日期 ：");
/* 124 */         System.out.println(hitDoc.get("publishDate"));
/*    */       }
/*    */     } catch (Exception e) {
/* 127 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.SortTest
 * JD-Core Version:    0.6.0
 */