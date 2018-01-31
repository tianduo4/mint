/*    */ package com.jspgou.core.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.core.entity.base.BaseRole;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
/*    */ import net.sf.json.JSONObject;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class Role extends BaseRole
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public static Integer[] fetchIds(Collection<Role> roles)
/*    */   {
/* 20 */     if (roles == null) {
/* 21 */       return null;
/*    */     }
/* 23 */     Integer[] ids = new Integer[roles.size()];
/* 24 */     int i = 0;
/* 25 */     for (Role r : roles) {
/* 26 */       ids[(i++)] = r.getId();
/*    */     }
/* 28 */     return ids;
/*    */   }
/*    */ 
/*    */   public Role()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Role(Integer id)
/*    */   {
/* 40 */     super(id);
/*    */   }
/*    */ 
/*    */   public Role(Integer id, String name, Integer priority, Boolean m_super)
/*    */   {
/* 56 */     super(id, 
/* 54 */       name, 
/* 55 */       priority, 
/* 56 */       m_super);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson() {
/* 60 */     JSONObject json = new JSONObject();
/* 61 */     json.put("id", CommonUtils.parseId(getId()));
/* 62 */     json.put("name", CommonUtils.parseStr(getName()));
/* 63 */     json.put("priority", Integer.valueOf(CommonUtils.parseInteger(getPriority())));
/* 64 */     json.put("super", CommonUtils.parseBoolean(getSuper()));
/* 65 */     if (getSuper() == null) {
/* 66 */       json.put("perms", "");
/*    */     }
/* 68 */     else if (!getSuper().booleanValue())
/* 69 */       json.put("perms", StringUtils.join(getPerms().toArray(), ","));
/*    */     else {
/* 71 */       json.put("perms", "*");
/*    */     }
/*    */ 
/* 74 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.entity.Role
 * JD-Core Version:    0.6.0
 */