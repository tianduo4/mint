/*    */ package com.jspgou.common.page;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Pagination extends SimplePage
/*    */   implements Serializable, Paginable
/*    */ {
/*    */   private List<?> list;
/*    */ 
/*    */   public Pagination()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Pagination(int pageNo, int pageSize, int totalCount)
/*    */   {
/* 30 */     super(pageNo, pageSize, totalCount);
/*    */   }
/*    */ 
/*    */   public Pagination(int pageNo, int pageSize, int totalCount, List<?> list)
/*    */   {
/* 46 */     super(pageNo, pageSize, totalCount);
/* 47 */     this.list = list;
/*    */   }
/*    */ 
/*    */   public int getFirstResult()
/*    */   {
/* 56 */     return (this.pageNo - 1) * this.pageSize;
/*    */   }
/*    */ 
/*    */   public List<?> getList()
/*    */   {
/* 70 */     return this.list;
/*    */   }
/*    */ 
/*    */   public void setList(List list)
/*    */   {
/* 80 */     this.list = list;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.page.Pagination
 * JD-Core Version:    0.6.0
 */