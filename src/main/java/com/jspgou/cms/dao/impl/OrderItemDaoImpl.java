/*     */ package com.jspgou.cms.dao.impl;
/*     */ 
/*     */ import com.jspgou.cms.dao.OrderItemDao;
/*     */ import com.jspgou.cms.entity.OrderItem;
/*     */ import com.jspgou.cms.entity.Product;
/*     */ import com.jspgou.common.hibernate4.Finder;
/*     */ import com.jspgou.common.hibernate4.HibernateBaseDao;
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.util.DateUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ @Repository
/*     */ public class OrderItemDaoImpl extends HibernateBaseDao<OrderItem, Long>
/*     */   implements OrderItemDao
/*     */ {
/*     */   protected Class<OrderItem> getEntityClass()
/*     */   {
/*  32 */     return OrderItem.class;
/*     */   }
/*     */ 
/*     */   public List<Object[]> profitTop(Long ctgid, Long typeid, Integer pageNo, Integer pageSize)
/*     */   {
/*  37 */     Query query = getSession().createQuery(getHQL(ctgid, typeid));
/*  38 */     if (ctgid != null) {
/*  39 */       query.setLong("ctgid", ctgid.longValue());
/*     */     }
/*  41 */     if (typeid != null) {
/*  42 */       query.setLong("typeid", typeid.longValue());
/*     */     }
/*  44 */     Iterator iter = query.setFirstResult((pageNo.intValue() - 1) * pageSize.intValue()).setMaxResults(pageSize.intValue()).iterate();
/*  45 */     List list = new ArrayList();
/*  46 */     while (iter.hasNext()) {
/*  47 */       list.add((Object[])iter.next());
/*     */     }
/*  49 */     return list;
/*     */   }
/*     */ 
/*     */   public Integer totalCount(Long ctgid, Long typeid) {
/*  53 */     Integer allPage = Integer.valueOf(0);
/*  54 */     Query query = getSession().createQuery(getCountHQL(ctgid, typeid));
/*  55 */     if (ctgid != null) {
/*  56 */       query.setLong("ctgid", ctgid.longValue());
/*     */     }
/*  58 */     if (typeid != null) {
/*  59 */       query.setLong("typeid", typeid.longValue());
/*     */     }
/*  61 */     Iterator iterator = query.iterate();
/*  62 */     if (iterator.hasNext()) {
/*  63 */       allPage = Integer.valueOf(Integer.parseInt((String)iterator.next()));
/*     */     }
/*  65 */     return allPage;
/*     */   }
/*     */ 
/*     */   public Pagination getPageForMember(Long memberId, Integer status, int pageNo, int pageSize)
/*     */   {
/*  71 */     Finder f = Finder.create("select bean from OrderItem bean");
/*  72 */     f.append(" join bean.ordeR indent");
/*  73 */     f.append(" where indent.member.id=:memberId and indent.status=:status");
/*  74 */     f.setParam("memberId", memberId).setParam("status", status);
/*  75 */     f.append(" order by bean.id desc");
/*  76 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public Pagination getPageForProuct(Long productId, int pageNo, int pageSize)
/*     */   {
/*  82 */     Finder f = Finder.create("select bean from OrderItem bean");
/*  83 */     if (productId != null) {
/*  84 */       f.append(" where bean.product.id=:productId");
/*  85 */       f.setParam("productId", productId);
/*     */     }
/*  87 */     f.append(" order by bean.id desc");
/*  88 */     return find(f, pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public static String getHQL(Long ctgid, Long typeid) {
/*  92 */     StringBuffer sb = new StringBuffer();
/*  93 */     sb.append("select bean.product.id, sum(bean.count), sum((bean.finalPrice-bean.costPrice)*bean.count) ");
/*  94 */     sb.append(" from OrderItem bean where 1=1 ");
/*  95 */     if (ctgid != null) {
/*  96 */       sb.append(" and bean.product.category.id=:ctgid ");
/*     */     }
/*  98 */     if (typeid != null) {
/*  99 */       sb.append(" and bean.product.type.id=:typeid ");
/*     */     }
/* 101 */     sb.append(" group by bean.product.id order by sum((bean.finalPrice-bean.costPrice)*bean.count) desc");
/* 102 */     return sb.toString();
/*     */   }
/*     */   public static String getCountHQL(Long ctgid, Long typeid) {
/* 105 */     StringBuffer sb = new StringBuffer();
/* 106 */     sb.append("select count(DISTINCT bean.product.id) ");
/* 107 */     sb.append(" from OrderItem bean where 1=1 ");
/* 108 */     if (ctgid != null) {
/* 109 */       sb.append(" and bean.product.category.id=:ctgid ");
/*     */     }
/* 111 */     if (typeid != null) {
/* 112 */       sb.append(" and bean.product.type.id=:typeid ");
/*     */     }
/* 114 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public List<Object[]> getOrderItem(Date endTime, Date beginTime)
/*     */   {
/* 119 */     String hql = "select bean,sum(bean.count) from OrderItem bean where bean.ordeR.createTime<=:endTime and bean.ordeR.createTime>=:beginTime group by bean.id,bean.productFash.attitude,bean.count,bean.salePrice,bean.memberPrice,bean.costPrice,bean.finalPrice,bean.seckillprice,bean.website.id,bean.product.id,bean.ordeR.id,bean.productFash.id order by sum(bean.count) desc";
/*     */ 
/* 123 */     List list = getSession().createQuery(hql).setParameter("endTime", endTime).setParameter("beginTime", beginTime).list();
/* 124 */     return list;
/*     */   }
/*     */ 
/*     */   public OrderItem findById(Long id)
/*     */   {
/* 129 */     OrderItem entity = (OrderItem)get(id);
/* 130 */     return entity;
/*     */   }
/*     */ 
/*     */   public OrderItem findByMember(Long memberId, Long productId, Long orderId)
/*     */   {
/* 135 */     String hql = "from OrderItem bean where bean.product.id=:productId and bean.ordeR.member.id=:memberId ";
/* 136 */     if (orderId != null) {
/* 137 */       String hql1 = hql + " and bean.ordeR.id=:orderId ";
/* 138 */       Iterator it = getSession().createQuery(hql1).setParameter("memberId", memberId).setParameter("productId", productId).setParameter("orderId", orderId)
/* 139 */         .iterate();
/* 140 */       if (it.hasNext()) {
/* 141 */         return (OrderItem)it.next();
/*     */       }
/* 143 */       return null;
/*     */     }
/*     */ 
/* 146 */     Iterator it = getSession().createQuery(hql).setParameter("memberId", memberId).setParameter("productId", productId)
/* 147 */       .iterate();
/* 148 */     if (it.hasNext()) {
/* 149 */       return (OrderItem)it.next();
/*     */     }
/* 151 */     return null;
/*     */   }
/*     */ 
/*     */   public Pagination getPageProductSaleRank(Long webId, String type, Integer categoryId, int pageNo, int pageSize, Date startTime, Date endTime)
/*     */   {
/* 158 */     Finder f = Finder.create("select bean.product.name,bean.product.category.name,sum(bean.count) from OrderItem bean");
/* 159 */     f.append("  where bean.product.status=" + Product.ON_SALE_STATUS + "  and  bean.website.id=:webId and bean.ordeR.delStatus =  1 ");
/*     */ 
/* 162 */     StringBuffer totalHql = new StringBuffer(" select count(distinct bean.product.category.name) from OrderItem bean  where  bean.product.status=" + Product.ON_SALE_STATUS + "  and bean.website.id=:webId and bean.ordeR.delStatus =  1");
/*     */ 
/* 164 */     f.setParam("webId", webId);
/* 165 */     StringBuffer whereStr = new StringBuffer();
/* 166 */     if (type != null) {
/* 167 */       Date date = new Date();
/* 168 */       if (type.equals("month")) {
/* 169 */         whereStr.append(" and bean.ordeR.createTime >=:startTime");
/* 170 */         f.setParam("startTime", DateUtils.getSpecficMonthStart(date, 0));
/* 171 */         whereStr.append(" and bean.ordeR.createTime <=:endTime");
/* 172 */         f.setParam("endTime", DateUtils.getSpecficMonthEnd(date, 0));
/* 173 */       } else if (type.equals("year")) {
/* 174 */         whereStr.append(" and bean.ordeR.createTime >=:startTime");
/* 175 */         f.setParam("startTime", DateUtils.getSpecficYearStart(date, 0));
/* 176 */         whereStr.append(" and bean.ordeR.createTime <=:endTime");
/* 177 */         f.setParam("endTime", DateUtils.getSpecficYearEnd(date, 0));
/*     */       }
/*     */     }
/* 180 */     if (startTime != null) {
/* 181 */       whereStr.append(" and bean.ordeR.createTime >=:startTime");
/* 182 */       f.setParam("startTime", DateUtils.getStartDate(startTime));
/*     */     }
/* 184 */     if (endTime != null) {
/* 185 */       whereStr.append(" and bean.ordeR.createTime <=:endTime");
/* 186 */       f.setParam("endTime", DateUtils.getFinallyDate(endTime));
/*     */     }
/*     */ 
/* 189 */     if (categoryId != null) {
/* 190 */       whereStr.append(" and bean.product.category.id =:categoryId");
/* 191 */       f.setParam("categoryId", categoryId);
/*     */     }
/* 193 */     totalHql.append(whereStr);
/* 194 */     f.append(whereStr.toString());
/* 195 */     f.append(" group by bean.product.name,bean.product.category.name ");
/* 196 */     f.append(" order by sum(bean.count)  desc ");
/* 197 */     return find(f, totalHql.toString(), pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   private Pagination find(Finder finder, String totalHql, int pageNo, int pageSize) {
/* 201 */     int totalCount = countQueryResultByGroup(finder, totalHql);
/* 202 */     Pagination p = new Pagination(pageNo, pageSize, totalCount);
/* 203 */     if (totalCount < 1) {
/* 204 */       p.setList(new ArrayList());
/* 205 */       return p;
/*     */     }
/* 207 */     Query query = getSession().createQuery(finder.getOrigHql());
/* 208 */     finder.setParamsToQuery(query);
/* 209 */     query.setFirstResult(p.getFirstResult());
/* 210 */     query.setMaxResults(p.getPageSize());
/* 211 */     if (finder.isCacheable()) {
/* 212 */       query.setCacheable(true);
/*     */     }
/* 214 */     List list = query.list();
/* 215 */     for (int i = 0; i < list.size(); i++) {
/* 216 */       Object[] arrayOfObject = (Object[])list.get(i);
/*     */     }
/* 218 */     p.setList(list);
/* 219 */     return p;
/*     */   }
/*     */ 
/*     */   protected int countQueryResultByGroup(Finder finder, String selectSql) {
/* 223 */     Query query = getSession().createQuery(selectSql);
/* 224 */     finder.setParamsToQuery(query);
/* 225 */     if (finder.isCacheable()) {
/* 226 */       query.setCacheable(true);
/*     */     }
/* 228 */     return ((Number)query.iterate().next()).intValue();
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.dao.impl.OrderItemDaoImpl
 * JD-Core Version:    0.6.0
 */