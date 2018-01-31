/*    */ package com.jspgou.common.hibernate4;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class PriorityComparator
/*    */   implements Comparator<PriorityInterface>, Serializable
/*    */ {
/*  9 */   public static final PriorityComparator INSTANCE = new PriorityComparator();
/*    */ 
/*    */   public int compare(PriorityInterface o1, PriorityInterface o2) {
/* 12 */     Number v1 = o1.getPriority();
/* 13 */     Number v2 = o2.getPriority();
/* 14 */     Number id1 = o1.getId();
/* 15 */     Number id2 = o2.getId();
/* 16 */     if ((id1 != null) && (id2 != null) && (id1.equals(id2)))
/* 17 */       return 0;
/* 18 */     if (v1 == null)
/* 19 */       return 1;
/* 20 */     if (v2 == null)
/* 21 */       return -1;
/* 22 */     if (v1.longValue() > v2.longValue())
/* 23 */       return 1;
/* 24 */     if (v1.longValue() < v2.longValue())
/* 25 */       return -1;
/* 26 */     if (id1 == null)
/* 27 */       return 1;
/* 28 */     if (id2 == null)
/* 29 */       return -1;
/* 30 */     if (id1.longValue() > id2.longValue())
/* 31 */       return 1;
/* 32 */     if (id1.longValue() < id2.longValue()) {
/* 33 */       return -1;
/*    */     }
/* 35 */     return 0;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.hibernate4.PriorityComparator
 * JD-Core Version:    0.6.0
 */