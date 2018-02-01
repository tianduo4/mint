/*     */ package com.jspgou.common.hibernate4;
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
/*  22 */   private static final Logger log = LoggerFactory.getLogger(TreeIntercptor.class);
/*     */   private ApplicationContext appCtx;
/*     */   private SessionFactory sessionFactory;
/*     */   public static final String SESSION_FACTORY = "sessionFactory";
/*     */ 
/*     */   public void setApplicationContext(ApplicationContext appCtx)
/*     */     throws BeansException
/*     */   {
/*  29 */     this.appCtx = appCtx;
/*     */   }
/*     */ 
/*     */   protected SessionFactory getSessionFactory() {
/*  33 */     if (this.sessionFactory == null) {
/*  34 */       this.sessionFactory = ((SessionFactory)this.appCtx.getBean("sessionFactory", 
/*  35 */         SessionFactory.class));
/*  36 */       if (this.sessionFactory == null) {
/*  37 */         throw new IllegalStateException("not found bean named 'sessionFactory',please check you spring config file.");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  42 */     return this.sessionFactory;
/*     */   }
/*     */ 
/*     */   protected Session getSession() {
/*  46 */     return getSessionFactory().getCurrentSession();
/*     */   }
/*     */ 
/*     */   public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
/*     */   {
/*  52 */     if ((entity instanceof HibernateTree)) {
/*  53 */       HibernateTree tree = (HibernateTree)entity;
/*  54 */       Number parentId = tree.getParentId();
/*  55 */       String beanName = tree.getClass().getName();
/*  56 */       Session session = getSession();
/*  57 */       FlushMode model = session.getFlushMode();
/*  58 */       session.setFlushMode(FlushMode.MANUAL);
/*     */       Integer myPosition;
/*  60 */       if (parentId != null)
/*     */       {
/*  62 */         String hql = "select bean." + tree.getRgtName() + " from " + 
/*  63 */           beanName + " bean where bean.id=:pid";
/*  64 */          myPosition =
/*  65 */           Integer.valueOf(((Number)session.createQuery(hql).setParameter(
/*  65 */           "pid", parentId).uniqueResult()).intValue());
/*  66 */         String hql1 = "update " + beanName + " bean set bean." + 
/*  67 */           tree.getRgtName() + " = bean." + tree.getRgtName() + 
/*  68 */           " + 2 WHERE bean." + tree.getRgtName() + 
/*  69 */           " >= :myPosition";
/*  70 */         String hql2 = "update " + beanName + " bean set bean." + 
/*  71 */           tree.getLftName() + " = bean." + tree.getLftName() + 
/*  72 */           " + 2 WHERE bean." + tree.getLftName() + 
/*  73 */           " >= :myPosition";
/*  74 */         if (!StringUtils.isBlank(tree.getTreeCondition())) {
/*  75 */           hql1 = hql1 + " and (" + tree.getTreeCondition() + ")";
/*  76 */           hql2 = hql2 + " and (" + tree.getTreeCondition() + ")";
/*     */         }
/*  78 */         session.createQuery(hql1)
/*  79 */           .setParameter("myPosition", myPosition).executeUpdate();
/*  80 */         session.createQuery(hql2)
/*  81 */           .setParameter("myPosition", myPosition).executeUpdate();
/*     */       }
/*     */       else {
/*  84 */         String hql = "select max(bean." + tree.getRgtName() + ") from " + 
/*  85 */           beanName + " bean";
/*  86 */         if (!StringUtils.isBlank(tree.getTreeCondition())) {
/*  87 */           hql = hql + " where " + tree.getTreeCondition();
/*     */         }
/*  89 */         Number myPositionNumber = (Number)session.createQuery(hql)
/*  90 */           .uniqueResult();
/*  92 */         if (myPositionNumber == null)
/*  93 */           myPosition = Integer.valueOf(1);
/*     */         else {
/*  95 */           myPosition = Integer.valueOf(myPositionNumber.intValue() + 1);
/*     */         }
/*     */       }
/*  98 */       session.setFlushMode(model);
/*  99 */       for (int i = 0; i < propertyNames.length; i++) {
/* 100 */         if (propertyNames[i].equals(tree.getLftName())) {
/* 101 */           state[i] = myPosition;
/*     */         }
/* 103 */         if (propertyNames[i].equals(tree.getRgtName())) {
/* 104 */           state[i] = Integer.valueOf(myPosition.intValue() + 1);
/*     */         }
/*     */       }
/* 107 */       return true;
/*     */     }
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types)
/*     */   {
/* 116 */     if (!(entity instanceof HibernateTree)) {
/* 117 */       return false;
/*     */     }
/* 119 */     HibernateTree tree = (HibernateTree)entity;
/* 120 */     for (int i = 0; i < propertyNames.length; i++) {
/* 121 */       if (propertyNames[i].equals(tree.getParentName())) {
/* 122 */         HibernateTree preParent = (HibernateTree)previousState[i];
/* 123 */         HibernateTree currParent = (HibernateTree)currentState[i];
/* 124 */         return updateParent(tree, preParent, currParent);
/*     */       }
/*     */     }
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean updateParent(HibernateTree<?> tree, HibernateTree<?> preParent, HibernateTree<?> currParent)
/*     */   {
/* 133 */     if (((preParent == null) && (currParent == null)) || (
/* 134 */       (preParent != null) && (currParent != null) && 
/* 135 */       (preParent
/* 135 */       .getId().equals(currParent.getId())))) {
/* 136 */       return false;
/*     */     }
/* 138 */     String beanName = tree.getClass().getName();
/* 139 */     if (log.isDebugEnabled()) {
/* 140 */       log.debug("update Tree {}, id={}, pre-parent id={}, curr-parent id={}", 
/* 141 */         new Object[] { 
/* 142 */         beanName, tree.getId(), 
/* 143 */         preParent == null ? null : preParent.getId(), 
/* 144 */         currParent == null ? null : currParent.getId() });
/*     */     }
/* 146 */     Session session = getSession();
/*     */ 
/* 148 */     FlushMode model = session.getFlushMode();
/* 149 */     session.setFlushMode(FlushMode.MANUAL);
/*     */     Integer currParentRgt;
/* 152 */     if (currParent != null)
/*     */     {
/* 154 */       String hql = "select bean." + tree.getLftName() + ",bean." + 
/* 155 */         tree.getRgtName() + " from " + beanName + 
/* 156 */         " bean where bean.id=:id";
/* 157 */       Object[] position = (Object[])session.createQuery(hql)
/* 158 */         .setParameter("id", tree.getId()).uniqueResult();
/* 159 */       int nodeLft = ((Number)position[0]).intValue();
/* 160 */       int nodeRgt = ((Number)position[1]).intValue();
/* 161 */       int span = nodeRgt - nodeLft + 1;
/* 162 */       log.debug("current node span={}", Integer.valueOf(span));
/*     */ 
/* 165 */       Object[] currPosition = (Object[])session.createQuery(hql)
/* 166 */         .setParameter("id", currParent.getId()).uniqueResult();
/* 167 */       int currParentLft = ((Number)currPosition[0]).intValue();
/* 168 */        currParentRgt = Integer.valueOf(((Number)currPosition[1]).intValue());
/* 169 */       log.debug("current parent lft={} rgt={}", Integer.valueOf(currParentLft), 
/* 170 */         currParentRgt);
/*     */ 
/* 173 */       String hql1 = "update " + beanName + " bean set bean." + 
/* 174 */         tree.getRgtName() + " = bean." + tree.getRgtName() + 
/* 175 */         " + " + span + " WHERE bean." + tree.getRgtName() + 
/* 176 */         " >= :parentRgt";
/* 177 */       String hql2 = "update " + beanName + " bean set bean." + 
/* 178 */         tree.getLftName() + " = bean." + tree.getLftName() + 
/* 179 */         " + " + span + " WHERE bean." + tree.getLftName() + 
/* 180 */         " >= :parentRgt";
/* 181 */       if (!StringUtils.isBlank(tree.getTreeCondition())) {
/* 182 */         hql1 = hql1 + " and (" + tree.getTreeCondition() + ")";
/* 183 */         hql2 = hql2 + " and (" + tree.getTreeCondition() + ")";
/*     */       }
/* 185 */       session.createQuery(hql1).setInteger("parentRgt", currParentRgt.intValue())
/* 186 */         .executeUpdate();
/* 187 */       session.createQuery(hql2).setInteger("parentRgt", currParentRgt.intValue())
/* 188 */         .executeUpdate();
/* 189 */       log.debug("vacated span hql: {}, {}, parentRgt={}", new Object[] { 
/* 190 */         hql1, hql2, currParentRgt });
/*     */     }
/*     */     else {
/* 193 */       String hql = "select max(bean." + tree.getRgtName() + ") from " + 
/* 194 */         beanName + " bean";
/* 195 */       if (!StringUtils.isBlank(tree.getTreeCondition())) {
/* 196 */         hql = hql + " where " + tree.getTreeCondition();
/*     */       }
/* 198 */       currParentRgt = 
/* 199 */         Integer.valueOf(((Number)session.createQuery(hql).uniqueResult())
/* 199 */         .intValue());
/* 200 */       currParentRgt = Integer.valueOf(currParentRgt.intValue() + 1);
/* 201 */       log.debug("max node left={}", currParentRgt);
/*     */     }
/*     */ 
/* 205 */     String hql = "select bean." + tree.getLftName() + ",bean." + 
/* 206 */       tree.getRgtName() + " from " + beanName + 
/* 207 */       " bean where bean.id=:id";
/* 208 */     Object[] position = (Object[])session.createQuery(hql).setParameter(
/* 209 */       "id", tree.getId()).uniqueResult();
/* 210 */     int nodeLft = ((Number)position[0]).intValue();
/* 211 */     int nodeRgt = ((Number)position[1]).intValue();
/* 212 */     int span = nodeRgt - nodeLft + 1;
/* 213 */     if (log.isDebugEnabled()) {
/* 214 */       log.debug("before adjust self left={} right={} span={}", 
/* 215 */         new Object[] { Integer.valueOf(nodeLft), Integer.valueOf(nodeRgt), Integer.valueOf(span) });
/*     */     }
/* 217 */     int offset = currParentRgt.intValue() - nodeLft;
/* 218 */     hql = "update " + beanName + " bean set bean." + tree.getLftName() + 
/* 219 */       "=bean." + tree.getLftName() + "+:offset, bean." + 
/* 220 */       tree.getRgtName() + "=bean." + tree.getRgtName() + 
/* 221 */       "+:offset WHERE bean." + tree.getLftName() + 
/* 222 */       " between :nodeLft and :nodeRgt";
/* 223 */     if (!StringUtils.isBlank(tree.getTreeCondition())) {
/* 224 */       hql = hql + " and (" + tree.getTreeCondition() + ")";
/*     */     }
/* 226 */     session.createQuery(hql).setParameter("offset", Integer.valueOf(offset)).setParameter(
/* 227 */       "nodeLft", Integer.valueOf(nodeLft)).setParameter("nodeRgt", Integer.valueOf(nodeRgt))
/* 228 */       .executeUpdate();
/* 229 */     if (log.isDebugEnabled()) {
/* 230 */       log.debug("adjust self hql: {}, offset={}, nodeLft={}, nodeRgt={}", 
/* 231 */         new Object[] { hql, Integer.valueOf(offset), Integer.valueOf(nodeLft), Integer.valueOf(nodeRgt) });
/*     */     }
/*     */ 
/* 235 */     String hql1 = "update " + beanName + " bean set bean." + 
/* 236 */       tree.getRgtName() + " = bean." + tree.getRgtName() + " - " + 
/* 237 */       span + " WHERE bean." + tree.getRgtName() + " > :nodeRgt";
/* 238 */     String hql2 = "update " + beanName + " bean set bean." + 
/* 239 */       tree.getLftName() + " = bean." + tree.getLftName() + " - " + 
/* 240 */       span + " WHERE bean." + tree.getLftName() + " > :nodeRgt";
/* 241 */     if (!StringUtils.isBlank(tree.getTreeCondition())) {
/* 242 */       hql1 = hql1 + " and (" + tree.getTreeCondition() + ")";
/* 243 */       hql2 = hql2 + " and (" + tree.getTreeCondition() + ")";
/*     */     }
/* 245 */     session.createQuery(hql1).setParameter("nodeRgt", Integer.valueOf(nodeRgt))
/* 246 */       .executeUpdate();
/* 247 */     session.createQuery(hql2).setParameter("nodeRgt", Integer.valueOf(nodeRgt))
/* 248 */       .executeUpdate();
/* 249 */     if (log.isDebugEnabled()) {
/* 250 */       log.debug("clear span hql1:{}, hql2:{}, nodeRgt:{}", new Object[] { 
/* 251 */         hql1, hql2, Integer.valueOf(nodeRgt) });
/*     */     }
/* 253 */     session.setFlushMode(model);
/* 254 */     return true;
/*     */   }
/*     */ 
/*     */   public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
/*     */   {
/* 260 */     if ((entity instanceof HibernateTree)) {
/* 261 */       HibernateTree tree = (HibernateTree)entity;
/* 262 */       String beanName = tree.getClass().getName();
/* 263 */       Session session = getSession();
/* 264 */       FlushMode model = session.getFlushMode();
/* 265 */       session.setFlushMode(FlushMode.MANUAL);
/* 266 */       String hql = "select bean." + tree.getLftName() + " from " + 
/* 267 */         beanName + " bean where bean.id=:id";
/* 268 */       Integer myPosition = 
/* 270 */         Integer.valueOf(((Number)session.createQuery(hql)
/* 269 */         .setParameter("id", tree.getId()).uniqueResult())
/* 270 */         .intValue());
/* 271 */       String hql1 = "update " + beanName + " bean set bean." + 
/* 272 */         tree.getRgtName() + " = bean." + tree.getRgtName() + 
/* 273 */         " - 2 WHERE bean." + tree.getRgtName() + " > :myPosition";
/* 274 */       String hql2 = "update " + beanName + " bean set bean." + 
/* 275 */         tree.getLftName() + " = bean." + tree.getLftName() + 
/* 276 */         " - 2 WHERE bean." + tree.getLftName() + " > :myPosition";
/* 277 */       if (!StringUtils.isBlank(tree.getTreeCondition())) {
/* 278 */         hql1 = hql1 + " and (" + tree.getTreeCondition() + ")";
/* 279 */         hql2 = hql2 + " and (" + tree.getTreeCondition() + ")";
/*     */       }
/* 281 */       session.createQuery(hql1).setInteger("myPosition", myPosition.intValue())
/* 282 */         .executeUpdate();
/* 283 */       session.createQuery(hql2).setInteger("myPosition", myPosition.intValue())
/* 284 */         .executeUpdate();
/* 285 */       session.setFlushMode(model);
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.hibernate4.TreeIntercptor
 * JD-Core Version:    0.6.0
 */