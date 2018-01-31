/*     */ package com.jspgou.cms.webservice;
/*     */ 
/*     */ import com.jspgou.cms.entity.ShopAdmin;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopMemberGroup;
/*     */ import com.jspgou.cms.manager.ShopAdminMng;
/*     */ import com.jspgou.cms.manager.ShopMemberGroupMng;
/*     */ import com.jspgou.cms.manager.ShopMemberMng;
/*     */ import com.jspgou.cms.manager.WebserviceAuthMng;
/*     */ import com.jspgou.cms.manager.WebserviceCallRecordMng;
/*     */ import com.jspgou.core.entity.Admin;
/*     */ import com.jspgou.core.manager.RoleMng;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.context.support.SpringBeanAutowiringSupport;
/*     */ 
/*     */ public class UserService extends SpringBeanAutowiringSupport
/*     */ {
/*     */   private static final String SERVICE_CODE_USER_DELETE = "user_delete";
/*     */   private static final String SERVICE_CODE_USER_ADD = "user_add";
/*     */   private static final String SERVICE_CODE_USER_UPDATE = "user_update";
/*     */   private static final String RESPONSE_CODE_SUCCESS = "100";
/*     */   private static final String RESPONSE_CODE_AUTH_ERROR = "101";
/*     */   private static final String RESPONSE_CODE_PARAM_REQUIRED = "102";
/*     */   private static final String RESPONSE_CODE_USER_NOT_FOUND = "103";
/*     */   private static final String RESPONSE_CODE_USER_ADD_ERROR = "104";
/*     */   private static final String RESPONSE_CODE_USER_UPDATE_ERROR = "105";
/*     */   private static final String RESPONSE_CODE_USER_DELETE_ERROR = "106";
/*     */   private static final String LOCAL_IP = "127.0.0.1";
/*     */ 
/*     */   @Autowired
/*     */   private ShopAdminMng shopAdminMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberMng shopMemberMng;
/*     */ 
/*     */   @Autowired
/*     */   private RoleMng roleMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberGroupMng shopMemberGroupMng;
/*     */ 
/*     */   @Autowired
/*     */   private WebserviceAuthMng webserviceAuthMng;
/*     */ 
/*     */   @Autowired
/*     */   private WebserviceCallRecordMng webserviceCallRecordMng;
/*     */ 
/*     */   @Autowired
/*     */   private UserMng UserMng;
/*     */ 
/*     */   public String addUser(String auth_username, String auth_password, String admin, String username, String password, String email, String realname, String sex, String tel, String groupId, String role)
/*     */   {
/*  38 */     String responseCode = "101";
/*  39 */     if (validate(auth_username, auth_password)) {
/*  40 */       if ((StringUtils.isBlank(username)) || (StringUtils.isBlank(password))) {
/*  41 */         responseCode = "102";
/*     */       } else {
/*  43 */         if (StringUtils.isBlank(admin))
/*  44 */           admin = "false";
/*     */         try
/*     */         {
/*  47 */           ShopMember shopmember = new ShopMember();
/*  48 */           shopmember.setRealName(realname);
/*  49 */           if (StringUtils.isNotBlank(sex)) {
/*  50 */             if (sex.equals("true"))
/*  51 */               shopmember.setGender(Boolean.valueOf(true));
/*  52 */             else if (sex.equals("false")) {
/*  53 */               shopmember.setGender(Boolean.valueOf(false));
/*     */             }
/*     */           }
/*  56 */           shopmember.setMobile(tel);
/*  57 */           ShopMemberGroup group = null;
/*  58 */           if (StringUtils.isNotBlank(groupId)) {
/*  59 */             Long gid = Long.valueOf(Long.parseLong(groupId));
/*  60 */             group = this.shopMemberGroupMng.findById(gid);
/*     */           }
/*  62 */           if (group == null) {
/*  63 */             group = this.shopMemberGroupMng.findById(Long.valueOf(1L));
/*     */           }
/*  65 */           if (admin.equals("false")) {
/*  66 */             this.shopMemberMng.register(username, password, email, Boolean.valueOf(true), null, "127.0.0.1", Boolean.valueOf(false), Long.valueOf(1L), group.getId(), null, null, null, null, null, shopmember);
/*  67 */           } else if (admin.equals("true")) {
/*  68 */             Integer[] roleIds = null;
/*  69 */             if (StringUtils.isNotBlank(role)) {
/*  70 */               String[] roles = role.split(",");
/*  71 */               roleIds = new Integer[roles.length];
/*  72 */               for (int i = 0; i < roles.length; i++) {
/*  73 */                 roleIds[i] = Integer.valueOf(Integer.parseInt(roles[i]));
/*     */               }
/*     */             }
/*  76 */             ShopAdmin bean = new ShopAdmin();
/*  77 */             bean.setFirstname(realname);
/*  78 */             this.shopAdminMng.register(username, password, Boolean.valueOf(false), email, "127.0.0.1", false, Long.valueOf(1L), bean);
/*     */           }
/*  80 */           responseCode = "100";
/*  81 */           this.webserviceCallRecordMng.save(auth_username, "user_add");
/*     */         } catch (Exception e) {
/*  83 */           e.printStackTrace();
/*  84 */           responseCode = "104";
/*     */         }
/*     */       }
/*     */     }
/*  88 */     return responseCode;
/*     */   }
/*     */ 
/*     */   public String updateUser(String auth_username, String auth_password, String username, String password, String email, String realname, String sex, String tel, String groupId, String role)
/*     */   {
/*  94 */     String responseCode = "101";
/*  95 */     if (validate(auth_username, auth_password)) {
/*  96 */       if (StringUtils.isBlank(username)) {
/*  97 */         responseCode = "102";
/*     */       } else {
/*  99 */         ShopAdmin user = null;
/* 100 */         ShopMember member = this.shopMemberMng.getByUsername(null, username);
/* 101 */         if (member != null) {
/* 102 */           if (StringUtils.isNotBlank(realname)) {
/* 103 */             member.setRealName(realname);
/*     */           }
/* 105 */           if (StringUtils.isNotBlank(tel)) {
/* 106 */             member.setMobile(tel);
/*     */           }
/* 108 */           if (StringUtils.isNotBlank(sex)) {
/* 109 */             member.setGender(Boolean.valueOf(Boolean.parseBoolean(sex)));
/*     */           }
/*     */ 
/* 112 */           ShopMemberGroup group = null;
/* 113 */           if (StringUtils.isNotBlank(groupId)) {
/* 114 */             Long gid = Long.valueOf(Long.parseLong(groupId));
/* 115 */             group = this.shopMemberGroupMng.findById(gid);
/*     */           }
/* 117 */           if (group == null)
/* 118 */             group = this.shopMemberGroupMng.findById(Long.valueOf(1L));
/*     */           try
/*     */           {
/* 121 */             this.shopMemberMng.update(member, group.getId(), null, null, null, null, null, password, email, Boolean.valueOf(false));
/*     */           }
/*     */           catch (Exception e) {
/* 124 */             e.printStackTrace();
/* 125 */             responseCode = "105";
/*     */           }
/*     */         } else {
/* 128 */           responseCode = "103";
/*     */         }
/* 130 */         user = this.shopAdminMng.getByUsername(username);
/* 131 */         if (user != null)
/*     */           try {
/* 133 */             Integer[] roleIds = null;
/* 134 */             if (StringUtils.isNotBlank(role)) {
/* 135 */               String[] roles = role.split(",");
/* 136 */               roleIds = new Integer[roles.length];
/* 137 */               for (int i = 0; i < roles.length; i++) {
/* 138 */                 roleIds[i] = Integer.valueOf(Integer.parseInt(roles[i]));
/*     */               }
/*     */             }
/* 141 */             if (user != null)
/*     */             {
/* 143 */               user.setFirstname(realname);
/* 144 */               Admin a = user.getAdmin();
/* 145 */               Set roles = new HashSet();
/* 146 */               if (roleIds != null) {
/* 147 */                 a.getRoles().clear();
/* 148 */                 for (Integer i : roleIds) {
/* 149 */                   roles.add(this.roleMng.findById(i));
/*     */                 }
/* 151 */                 a.setRoles(roles);
/*     */               }
/* 153 */               user.setAdmin(a);
/* 154 */               this.shopAdminMng.update(user, password, Boolean.valueOf(false), email, Boolean.valueOf(false));
/*     */             }
/* 156 */             responseCode = "100";
/* 157 */             this.webserviceCallRecordMng.save(auth_username, "user_update");
/*     */           } catch (Exception e) {
/* 159 */             e.printStackTrace();
/* 160 */             responseCode = "105";
/*     */           }
/*     */         else {
/* 163 */           responseCode = "103";
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 168 */     return responseCode;
/*     */   }
/*     */ 
/*     */   public String delUser(String auth_username, String auth_password, String username)
/*     */   {
/* 174 */     String responseCode = "101";
/* 175 */     if (validate(auth_username, auth_password)) {
/* 176 */       if (StringUtils.isNotBlank(username)) {
/* 177 */         ShopMember member = this.shopMemberMng.getByUsername(Long.valueOf(1L), username);
/* 178 */         if (member != null) {
/*     */           try {
/* 180 */             this.shopMemberMng.deleteById(member.getId());
/* 181 */             responseCode = "100";
/* 182 */             this.webserviceCallRecordMng.save(auth_username, "user_delete");
/*     */           } catch (Exception e) {
/* 184 */             responseCode = "106";
/*     */           }
/*     */         }
/*     */         else {
/* 188 */           ShopAdmin user = this.shopAdminMng.getByUsername(username);
/* 189 */           if (user != null)
/*     */             try {
/* 191 */               this.shopAdminMng.deleteById(user.getId());
/* 192 */               responseCode = "100";
/* 193 */               this.webserviceCallRecordMng.save(auth_username, "user_delete");
/*     */             } catch (Exception e) {
/* 195 */               responseCode = "106";
/*     */             }
/*     */           else
/* 198 */             responseCode = "103";
/*     */         }
/*     */       }
/*     */       else {
/* 202 */         responseCode = "102";
/*     */       }
/*     */     }
/* 205 */     return responseCode;
/*     */   }
/*     */ 
/*     */   private boolean validate(String username, String password) {
/* 209 */     if ((StringUtils.isBlank(username)) || (StringUtils.isBlank(password))) {
/* 210 */       return false;
/*     */     }
/* 212 */     return this.webserviceAuthMng.isPasswordValid(username, password);
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.webservice.UserService
 * JD-Core Version:    0.6.0
 */