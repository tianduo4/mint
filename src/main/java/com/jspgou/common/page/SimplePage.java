/*     */ package com.jspgou.common.page;
/*     */ 
/*     */ public class SimplePage
/*     */   implements Paginable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int DEF_COUNT = 20;
/* 133 */   protected int totalCount = 0;
/* 134 */   protected int pageSize = 20;
/* 135 */   protected int pageNo = 1;
/*     */ 
/*     */   public static int cpn(Integer pageNo)
/*     */   {
/*  21 */     return (pageNo == null) || (pageNo.intValue() < 1) ? 1 : pageNo.intValue();
/*     */   }
/*     */ 
/*     */   public SimplePage()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SimplePage(int pageNo, int pageSize, int totalCount)
/*     */   {
/*  38 */     setTotalCount(totalCount);
/*  39 */     setPageSize(pageSize);
/*  40 */     setPageNo(pageNo);
/*  41 */     adjustPageNo();
/*     */   }
/*     */ 
/*     */   public void adjustPageNo()
/*     */   {
/*  48 */     if (this.pageNo == 1) {
/*  49 */       return;
/*     */     }
/*  51 */     int tp = getTotalPage();
/*  52 */     if (this.pageNo > tp)
/*  53 */       this.pageNo = tp;
/*     */   }
/*     */ 
/*     */   public int getPageNo()
/*     */   {
/*  62 */     return this.pageNo;
/*     */   }
/*     */ 
/*     */   public int getPageSize()
/*     */   {
/*  70 */     return this.pageSize;
/*     */   }
/*     */ 
/*     */   public int getTotalCount()
/*     */   {
/*  78 */     return this.totalCount;
/*     */   }
/*     */ 
/*     */   public int getTotalPage()
/*     */   {
/*  86 */     int totalPage = this.totalCount / this.pageSize;
/*  87 */     if ((totalPage == 0) || (this.totalCount % this.pageSize != 0)) {
/*  88 */       totalPage++;
/*     */     }
/*  90 */     return totalPage;
/*     */   }
/*     */ 
/*     */   public boolean isFirstPage()
/*     */   {
/*  98 */     return this.pageNo <= 1;
/*     */   }
/*     */ 
/*     */   public boolean isLastPage()
/*     */   {
/* 106 */     return this.pageNo >= getTotalPage();
/*     */   }
/*     */ 
/*     */   public int getNextPage()
/*     */   {
/* 114 */     if (isLastPage()) {
/* 115 */       return this.pageNo;
/*     */     }
/* 117 */     return this.pageNo + 1;
/*     */   }
/*     */ 
/*     */   public int getPrePage()
/*     */   {
/* 126 */     if (isFirstPage()) {
/* 127 */       return this.pageNo;
/*     */     }
/* 129 */     return this.pageNo - 1;
/*     */   }
/*     */ 
/*     */   public void setTotalCount(int totalCount)
/*     */   {
/* 143 */     if (totalCount < 0)
/* 144 */       this.totalCount = 0;
/*     */     else
/* 146 */       this.totalCount = totalCount;
/*     */   }
/*     */ 
/*     */   public void setPageSize(int pageSize)
/*     */   {
/* 156 */     if (pageSize < 1)
/* 157 */       this.pageSize = 20;
/*     */     else
/* 159 */       this.pageSize = pageSize;
/*     */   }
/*     */ 
/*     */   public void setPageNo(int pageNo)
/*     */   {
/* 169 */     if (pageNo < 1)
/* 170 */       this.pageNo = 1;
/*     */     else
/* 172 */       this.pageNo = pageNo;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.page.SimplePage
 * JD-Core Version:    0.6.0
 */