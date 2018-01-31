/*     */ package com.jspgou.common.hibernate3;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.hibernate.EmptyInterceptor;
/*     */ import org.hibernate.FlushMode;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.SessionFactory;
/*     */ import org.hibernate.type.Type;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.BeansException;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.context.ApplicationContextAware;
/*     */ 
/*     */ public class TreeIntercptor extends EmptyInterceptor
/*     */   implements ApplicationContextAware
/*     */ {
/*  24 */   private static final Logger log = LoggerFactory.getLogger(TreeIntercptor.class);
/*     */   private ApplicationContext appCtx;
/*     */   private SessionFactory sessionFactory;
/*     */   public static final String SESSION_FACTORY = "sessionFactory";
/*     */ 
/*     */   public void setApplicationContext(ApplicationContext appCtx)
/*     */     throws BeansException
/*     */   {
/*  31 */     this.appCtx = appCtx;
/*     */   }
/*     */ 
/*     */   protected SessionFactory getSessionFactory() {
/*  35 */     if (this.sessionFactory == null) {
/*  36 */       this.sessionFactory = 
/*  37 */         ((SessionFactory)this.appCtx
/*  37 */         .getBean("sessionFactory", 
/*  37 */         SessionFactory.class));
/*  38 */       if (this.sessionFactory == null) {
/*  39 */         throw new IllegalStateException("not found bean named 'sessionFactory',please check you spring config file.");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  44 */     return this.sessionFactory;
/*     */   }
/*     */ 
/*     */   protected Session getSession() {
/*  48 */     return getSessionFactory().getCurrentSession();
/*     */   }
/*     */ 
/*     */   public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
/*     */   {
/*  54 */     if ((entity instanceof HibernateTree)) {
/*  55 */       HibernateTree tree = (HibernateTree)entity;
/*  56 */       Number parentId = tree.getParentId();
/*  57 */       String beanName = tree.getClass().getName();
/*  58 */       Session session = getSession();
/*  59 */       FlushMode model = session.getFlushMode();
/*  60 */       session.setFlushMode(FlushMode.MANUAL);
/*     */       Integer myPosition;
/*  62 */       if (parentId != null)
/*     */       {
/*  64 */         String hql = "select bean." + tree.getRgtName() + " from " + 
/*  65 */           beanName + " bean where bean.id=:pid";
/*  66 */          myPosition =
/*  67 */           Integer.valueOf(((Number)session.createQuery(hql).setParameter(
/*  67 */           "pid", parentId).uniqueResult()).intValue());
/*  68 */         String hql1 = "update " + beanName + " bean set bean." + 
/*  69 */           tree.getRgtName() + " = bean." + tree.getRgtName() + 
/*  70 */           " + 2 WHERE bean." + tree.getRgtName() + 
/*  71 */           " >= :myPosition";
/*  72 */         String hql2 = "update " + beanName + " bean set bean." + 
/*  73 */           tree.getLftName() + " = bean." + tree.getLftName() + 
/*  74 */           " + 2 WHERE bean." + tree.getLftName() + 
/*  75 */           " >= :myPosition";
/*  76 */         if (!StringUtils.isBlank(tree.getTreeCondition())) {
/*  77 */           hql1 = hql1 + " and (" + tree.getTreeCondition() + ")";
/*  78 */           hql2 = hql2 + " and (" + tree.getTreeCondition() + ")";
/*     */         }
/*  80 */         session.createQuery(hql1)
/*  81 */           .setParameter("myPosition", myPosition).executeUpdate();
/*  82 */         session.createQuery(hql2)
/*  83 */           .setParameter("myPosition", myPosition).executeUpdate();
/*     */       }
/*     */       else {
/*  86 */         String hql = "select max(bean." + tree.getRgtName() + ") from " + 
/*  87 */           beanName + " bean";
/*  88 */         if (!StringUtils.isBlank(tree.getTreeCondition())) {
/*  89 */           hql = hql + " where " + tree.getTreeCondition();
/*     */         }
/*  91 */         Number myPositionNumber = (Number)session.createQuery(hql)
/*  92 */           .uniqueResult();
/*  94 */         if (myPositionNumber == null)
/*  95 */           myPosition = Integer.valueOf(1);
/*     */         else {
/*  97 */           myPosition = Integer.valueOf(myPositionNumber.intValue() + 1);
/*     */         }
/*     */       }
/* 100 */       session.setFlushMode(model);
/* 101 */       for (int i = 0; i < propertyNames.length; i++) {
/* 102 */         if (propertyNames[i].equals(tree.getLftName())) {
/* 103 */           state[i] = myPosition;
/*     */         }
/* 105 */         if (propertyNames[i].equals(tree.getRgtName())) {
/* 106 */           state[i] = Integer.valueOf(myPosition.intValue() + 1);
/*     */         }
/*     */       }
/* 109 */       return true;
/*     */     }
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types)
/*     */   {
/* 118 */     if (!(entity instanceof HibernateTree)) {
/* 119 */       return false;
/*     */     }
/* 121 */     HibernateTree tree = (HibernateTree)entity;
/* 122 */     for (int i = 0; i < propertyNames.length; i++) {
/* 123 */       if (propertyNames[i].equals(tree.getParentName())) {
/* 124 */         HibernateTree preParent = (HibernateTree)previousState[i];
/* 125 */         HibernateTree currParent = (HibernateTree)currentState[i];
/* 126 */         return updateParent(tree, preParent, currParent);
/*     */       }
/*     */     }
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean updateParent(HibernateTree tree, HibernateTree preParent, HibernateTree currParent)
/*     */   {
/* 135 */     if (((preParent == null) && (currParent == null)) || (
/* 136 */       (preParent != null) && (currParent != null) && 
/* 137 */       (preParent.getId().equals(currParent.getId())))) {
/* 138 */       return false;
/*     */     }
/* 140 */     String beanName = tree.getClass().getName();
/* 141 */     if (log.isDebugEnabled()) {
/* 142 */       log.debug("update Tree {}, id={}, pre-parent id={}, curr-parent id={}", 
/* 143 */         new Object[] { 
/* 144 */         beanName, tree.getId(), 
/* 145 */         preParent == null ? null : preParent.getId(), 
/* 146 */         currParent == null ? null : currParent.getId() });
/*     */     }
/* 148 */     Session session = getSession();
/*     */ 
/* 150 */     FlushMode model = session.getFlushMode();
/* 151 */     session.setFlushMode(FlushMode.MANUAL);
/*     */     Integer currParentRgt;
/* 154 */     if (currParent != null)
/*     */     {
/* 156 */       String hql = "select bean." + tree.getLftName() + ",bean." + 
/* 157 */         tree.getRgtName() + " from " + beanName + 
/* 158 */         " bean where bean.id=:id";
/* 159 */       Object[] position = (Object[])session.createQuery(hql)
/* 160 */         .setParameter("id", tree.getId()).uniqueResult();
/* 161 */       int nodeLft = ((Number)position[0]).intValue();
/* 162 */       int nodeRgt = ((Number)position[1]).intValue();
/* 163 */       int span = nodeRgt - nodeLft + 1;
/* 164 */       log.debug("current node span={}", Integer.valueOf(span));
/*     */ 
/* 167 */       Object[] currPosition = (Object[])session.createQuery(hql)
/* 168 */         .setParameter("id", currParent.getId()).uniqueResult();
/* 169 */       int currParentLft = ((Number)currPosition[0]).intValue();
/* 170 */        currParentRgt = Integer.valueOf(((Number)currPosition[1]).intValue());
/* 171 */       log.debug("current parent lft={} rgt={}", Integer.valueOf(currParentLft), 
/* 172 */         currParentRgt);
/*     */ 
/* 175 */       String hql1 = "update " + beanName + " bean set bean." + 
/* 176 */         tree.getRgtName() + " = bean." + tree.getRgtName() + 
/* 177 */         " + " + span + " WHERE bean." + tree.getRgtName() + 
/* 178 */         " >= :parentRgt";
/* 179 */       String hql2 = "update " + beanName + " bean set bean." + 
/* 180 */         tree.getLftName() + " = bean." + tree.getLftName() + 
/* 181 */         " + " + span + " WHERE bean." + tree.getLftName() + 
/* 182 */         " >= :parentRgt";
/* 183 */       if (!StringUtils.isBlank(tree.getTreeCondition())) {
/* 184 */         hql1 = hql1 + " and (" + tree.getTreeCondition() + ")";
/* 185 */         hql2 = hql2 + " and (" + tree.getTreeCondition() + ")";
/*     */       }
/* 187 */       session.createQuery(hql1).setInteger("parentRgt", currParentRgt.intValue())
/* 188 */         .executeUpdate();
/* 189 */       session.createQuery(hql2).setInteger("parentRgt", currParentRgt.intValue())
/* 190 */         .executeUpdate();
/* 191 */       log.debug("vacated span hql: {}, {}, parentRgt={}", new Object[] { 
/* 192 */         hql1, hql2, currParentRgt });
/*     */     }
/*     */     else {
/* 195 */       String hql = "select max(bean." + tree.getRgtName() + ") from " + 
/* 196 */         beanName + " bean";
/* 197 */       if (!StringUtils.isBlank(tree.getTreeCondition())) {
/* 198 */         hql = hql + " where " + tree.getTreeCondition();
/*     */       }
/* 200 */       currParentRgt = 
/* 201 */         Integer.valueOf(((Number)session.createQuery(hql).uniqueResult())
/* 201 */         .intValue());
/* 202 */       currParentRgt = Integer.valueOf(currParentRgt.intValue() + 1);
/* 203 */       log.debug("max node left={}", currParentRgt);
/*     */     }
/*     */ 
/* 207 */     String hql = "select bean." + tree.getLftName() + ",bean." + 
/* 208 */       tree.getRgtName() + " from " + beanName + 
/* 209 */       " bean where bean.id=:id";
/* 210 */     Object[] position = (Object[])session.createQuery(hql).setParameter(
/* 211 */       "id", tree.getId()).uniqueResult();
/* 212 */     int nodeLft = ((Number)position[0]).intValue();
/* 213 */     int nodeRgt = ((Number)position[1]).intValue();
/* 214 */     int span = nodeRgt - nodeLft + 1;
/* 215 */     if (log.isDebugEnabled()) {
/* 216 */       log.debug("before adjust self left={} right={} span={}", 
/* 217 */         new Object[] { Integer.valueOf(nodeLft), Integer.valueOf(nodeRgt), Integer.valueOf(span) });
/*     */     }
/* 219 */     int offset = currParentRgt.intValue() - nodeLft;
/* 220 */     hql = "update " + beanName + " bean set bean." + tree.getLftName() + 
/* 221 */       "=bean." + tree.getLftName() + "+:offset, bean." + 
/* 222 */       tree.getRgtName() + "=bean." + tree.getRgtName() + 
/* 223 */       "+:offset WHERE bean." + tree.getLftName() + 
/* 224 */       " between :nodeLft and :nodeRgt";
/* 225 */     if (!StringUtils.isBlank(tree.getTreeCondition())) {
/* 226 */       hql = hql + " and (" + tree.getTreeCondition() + ")";
/*     */     }
/* 228 */     session.createQuery(hql).setParameter("offset", Integer.valueOf(offset)).setParameter(
/* 229 */       "nodeLft", Integer.valueOf(nodeLft)).setParameter("nodeRgt", Integer.valueOf(nodeRgt))
/* 230 */       .executeUpdate();
/* 231 */     if (log.isDebugEnabled()) {
/* 232 */       log.debug("adjust self hql: {}, offset={}, nodeLft={}, nodeRgt={}", 
/* 233 */         new Object[] { hql, Integer.valueOf(offset), Integer.valueOf(nodeLft), Integer.valueOf(nodeRgt) });
/*     */     }
/*     */ 
/* 237 */     String hql1 = "update " + beanName + " bean set bean." + 
/* 238 */       tree.getRgtName() + " = bean." + tree.getRgtName() + " - " + 
/* 239 */       span + " WHERE bean." + tree.getRgtName() + " > :nodeRgt";
/* 240 */     String hql2 = "update " + beanName + " bean set bean." + 
/* 241 */       tree.getLftName() + " = bean." + tree.getLftName() + " - " + 
/* 242 */       span + " WHERE bean." + tree.getLftName() + " > :nodeRgt";
/* 243 */     if (!StringUtils.isBlank(tree.getTreeCondition())) {
/* 244 */       hql1 = hql1 + " and (" + tree.getTreeCondition() + ")";
/* 245 */       hql2 = hql2 + " and (" + tree.getTreeCondition() + ")";
/*     */     }
/* 247 */     session.createQuery(hql1).setParameter("nodeRgt", Integer.valueOf(nodeRgt))
/* 248 */       .executeUpdate();
/* 249 */     session.createQuery(hql2).setParameter("nodeRgt", Integer.valueOf(nodeRgt))
/* 250 */       .executeUpdate();
/* 251 */     if (log.isDebugEnabled()) {
/* 252 */       log.debug("clear span hql1:{}, hql2:{}, nodeRgt:{}", new Object[] { 
/* 253 */         hql1, hql2, Integer.valueOf(nodeRgt) });
/*     */     }
/* 255 */     session.setFlushMode(model);
/* 256 */     return true;
/*     */   }
/*     */ 
/*     */   public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
/*     */   {
/* 262 */     if ((entity instanceof HibernateTree)) {
/* 263 */       HibernateTree tree = (HibernateTree)entity;
/* 264 */       String beanName = tree.getClass().getName();
/* 265 */       Session session = getSession();
/* 266 */       FlushMode model = session.getFlushMode();
/* 267 */       session.setFlushMode(FlushMode.MANUAL);
/* 268 */       String hql = "select bean." + tree.getLftName() + " from " + 
/* 269 */         beanName + " bean where bean.id=:id";
/* 270 */       Integer myPosition = 
/* 272 */         Integer.valueOf(((Number)session.createQuery(hql)
/* 271 */         .setParameter("id", tree.getId()).uniqueResult())
/* 272 */         .intValue());
/* 273 */       String hql1 = "update " + beanName + " bean set bean." + 
/* 274 */         tree.getRgtName() + " = bean." + tree.getRgtName() + 
/* 275 */         " - 2 WHERE bean." + tree.getRgtName() + " > :myPosition";
/* 276 */       String hql2 = "update " + beanName + " bean set bean." + 
/* 277 */         tree.getLftName() + " = bean." + tree.getLftName() + 
/* 278 */         " - 2 WHERE bean." + tree.getLftName() + " > :myPosition";
/* 279 */       if (!StringUtils.isBlank(tree.getTreeCondition())) {
/* 280 */         hql1 = hql1 + " and (" + tree.getTreeCondition() + ")";
/* 281 */         hql2 = hql2 + " and (" + tree.getTreeCondition() + ")";
/*     */       }
/* 283 */       session.createQuery(hql1).setInteger("myPosition", myPosition.intValue())
/* 284 */         .executeUpdate();
/* 285 */       session.createQuery(hql2).setInteger("myPosition", myPosition.intValue())
/* 286 */         .executeUpdate();
/* 287 */       session.setFlushMode(model);
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.hibernate3.TreeIntercptor
 * JD-Core Version:    0.6.0
 */