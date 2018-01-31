/*    */ package com.jspgou.common.hibernate3;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class PriorityComparator
/*    */   implements Comparator<PriorityInterface>, Serializable
/*    */ {
/* 12 */   public static final PriorityComparator INSTANCE = new PriorityComparator();
/*    */ 
/*    */   public int compare(PriorityInterface o1, PriorityInterface o2) {
/* 15 */     Number v1 = o1.getPriority();
/* 16 */     Number v2 = o2.getPriority();
/* 17 */     Number id1 = o1.getId();
/* 18 */     Number id2 = o2.getId();
/* 19 */     if ((id1 != null) && (id2 != null) && (id1.equals(id2)))
/* 20 */       return 0;
/* 21 */     if (v1 == null)
/* 22 */       return 1;
/* 23 */     if (v2 == null)
/* 24 */       return -1;
/* 25 */     if (v1.longValue() > v2.longValue())
/* 26 */       return 1;
/* 27 */     if (v1.longValue() < v2.longValue())
/* 28 */       return -1;
/* 29 */     if (id1 == null)
/* 30 */       return 1;
/* 31 */     if (id2 == null)
/* 32 */       return -1;
/* 33 */     if (id1.longValue() > id2.longValue())
/* 34 */       return 1;
/* 35 */     if (id1.longValue() < id2.longValue()) {
/* 36 */       return -1;
/*    */     }
/* 38 */     return 0;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.hibernate3.PriorityComparator
 * JD-Core Version:    0.6.0
 */