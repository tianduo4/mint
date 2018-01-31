/*     */ package com.jspgou.core.manager.impl;
/*     */ 
/*     */ import com.jspgou.common.page.Pagination;
/*     */ import com.jspgou.common.security.encoder.PwdEncoder;
/*     */ import com.jspgou.core.dao.UserDao;
/*     */ import com.jspgou.core.entity.EmailSender;
/*     */ import com.jspgou.core.entity.MessageTemplate;
/*     */ import com.jspgou.core.entity.User;
/*     */ import com.jspgou.core.entity.WebsiteExt;
import com.jspgou.core.entity.WebsiteExt.ConfigLogin;
/*     */ import com.jspgou.core.manager.UserMng;
/*     */ import com.jspgou.core.manager.WebsiteExtMng;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Date;
/*     */ import java.util.UUID;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import org.apache.commons.lang.RandomStringUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.mail.MailException;
/*     */ import org.springframework.mail.javamail.JavaMailSenderImpl;
/*     */ import org.springframework.mail.javamail.MimeMessageHelper;
/*     */ import org.springframework.mail.javamail.MimeMessagePreparator;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ @Transactional
/*     */ public class UserMngImpl
/*     */   implements UserMng
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private WebsiteExtMng websiteExtMng;
/*     */   private PwdEncoder pwdEncoder;
/*     */   private UserDao dao;
/*     */ 
/*     */   public User passwordForgotten(Long id, String base, EmailSender email, MessageTemplate tpl)
/*     */   {
/*  36 */     User entity = findById(id);
/*  37 */     String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
/*  38 */     entity.setResetKey(uuid);
/*  39 */     String resetPwd = RandomStringUtils.randomNumeric(10);
/*  40 */     entity.setResetPwd(resetPwd);
/*  41 */     senderEmail(entity.getId(), entity.getUsername(), base, entity.getEmail(), entity
/*  42 */       .getResetKey(), entity.getResetPwd(), email, tpl);
/*  43 */     return entity;
/*     */   }
/*     */ 
/*     */   public void senderActiveEmail(String userName, String base, String receiverEmail, String activationCode, EmailSender email, MessageTemplate tpl)
/*     */     throws MailException
/*     */   {
/*  51 */     JavaMailSenderImpl sender = new JavaMailSenderImpl();
/*  52 */     sender.setHost(email.getHost());
/*  53 */     sender.setUsername(email.getUsername());
/*  54 */     sender.setPassword(email.getPassword());
/*  55 */     sender.send(new MimeMessagePreparator(email, tpl, receiverEmail, userName, activationCode, base)
/*     */     {
/*     */       public void prepare(MimeMessage mimemessage) throws MessagingException, UnsupportedEncodingException
/*     */       {
/*  59 */         MimeMessageHelper msg = new MimeMessageHelper(mimemessage, 
/*  60 */           false, this.val$email.getEncoding());
/*  61 */         msg.setSubject(this.val$tpl.getActiveTitle());
/*  62 */         msg.setTo(this.val$receiverEmail);
/*  63 */         msg.setFrom(this.val$email.getUsername(), this.val$email.getPersonal());
/*  64 */         String text = this.val$tpl.getActiveTxt();
/*  65 */         text = StringUtils.replace(text, "${userName}", this.val$userName);
/*  66 */         text = StringUtils.replace(text, "${activationCode}", this.val$activationCode);
/*  67 */         text = StringUtils.replace(text, "${base}", this.val$base);
/*  68 */         msg.setText(text, true);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void senderEmail(Long uid, String username, String base, String to, String resetKey, String resetPwd, EmailSender email, MessageTemplate tpl)
/*     */     throws MailException
/*     */   {
/*  78 */     JavaMailSenderImpl sender = new JavaMailSenderImpl();
/*  79 */     sender.setHost(email.getHost());
/*  80 */     sender.setUsername(email.getUsername());
/*  81 */     sender.setPassword(email.getPassword());
///*  82 */     sender.send(new MimeMessagePreparator(email, tpl, to, uid, username, resetKey, resetPwd, base)
///*     */     {
///*     */       public void prepare(MimeMessage mimemessage) throws MessagingException, UnsupportedEncodingException
///*     */       {
///*  86 */         MimeMessageHelper msg = new MimeMessageHelper(mimemessage,
///*  87 */           false, this.val$email.getEncoding());
///*  88 */         msg.setSubject(this.val$tpl.getSubject());
///*  89 */         msg.setTo(this.val$to);
///*  90 */         msg.setFrom(this.val$email.getUsername(), this.val$email.getPersonal());
///*  91 */         String text = this.val$tpl.getText();
///*  92 */         text = StringUtils.replace(text, "${uid}", String.valueOf(this.val$uid));
///*  93 */         text = StringUtils.replace(text, "${username}", this.val$username);
///*  94 */         text = StringUtils.replace(text, "${activationCode}", this.val$resetKey);
///*  95 */         text = StringUtils.replace(text, "${resetPwd}", this.val$resetPwd);
///*  96 */         text = StringUtils.replace(text, "${base}", this.val$base);
///*  97 */         msg.setText(text, true);
///*     */       }
///*     */     });  //TODO  注释
/*     */   }
/*     */ 
/*     */   public User resetPassword(Long userId) {
/* 104 */     User entity = findById(userId);
/* 105 */     entity.setPassword(this.pwdEncoder.encodePassword(entity.getResetPwd()));
/* 106 */     entity.setResetKey(null);
/* 107 */     entity.setResetPwd(null);
/* 108 */     return entity;
/*     */   }
/*     */ 
/*     */   public boolean isPasswordValid(Long id, String password)
/*     */   {
/* 113 */     User entity = findById(id);
/* 114 */     return this.pwdEncoder.isPasswordValid(entity.getPassword(), password);
/*     */   }
/*     */ 
/*     */   public boolean usernameExist(String username)
/*     */   {
/* 119 */     return getByUsername(username) != null;
/*     */   }
/*     */ 
/*     */   public boolean emailExist(String email)
/*     */   {
/* 124 */     return getByEmail(email) != null;
/*     */   }
/*     */ 
/*     */   public User getByUsername(String username)
/*     */   {
/* 129 */     return this.dao.getByUsername(username);
/*     */   }
/*     */ 
/*     */   public User getByEmail(String email)
/*     */   {
/* 134 */     return this.dao.getByEmail(email);
/*     */   }
/*     */ 
/*     */   public void updateLoginInfo(Long userId, String ip)
/*     */   {
/* 139 */     User entity = findById(userId);
/* 140 */     entity.setLoginCount(Long.valueOf(entity.getLoginCount().longValue() + 1L));
/* 141 */     String s1 = entity.getCurrentLoginIp();
/* 142 */     Timestamp time = new Timestamp(System.currentTimeMillis());
/* 143 */     if (StringUtils.isBlank(s1)) {
/* 144 */       entity.setLastLoginIp(ip);
/* 145 */       entity.setLastLoginTime(time);
/*     */     } else {
/* 147 */       entity.setLastLoginIp(entity.getCurrentLoginIp());
/* 148 */       entity.setLastLoginTime(entity.getCurrentLoginTime());
/*     */     }
/* 150 */     entity.setCurrentLoginIp(ip);
/* 151 */     entity.setCurrentLoginTime(time);
/*     */   }
/*     */ 
/*     */   public void updateLoginSuccess(Long userId, String ip)
/*     */   {
/* 156 */     User user = findById(userId);
/* 157 */     Date now = new Timestamp(System.currentTimeMillis());
/*     */ 
/* 159 */     user.setLoginCount(Long.valueOf(user.getLoginCount().longValue() + 1L));
/* 160 */     user.setLastLoginIp(ip);
/* 161 */     user.setLastLoginTime(now);
/*     */ 
/* 163 */     user.setErrCount(Integer.valueOf(0));
/* 164 */     user.setErrTime(null);
/* 165 */     user.setErrIp(null);
/*     */   }
/*     */ 
/*     */   public void updateLoginInfo(Long userId, String ip, Date loginTime, String sessionId)
/*     */   {
/* 170 */     User user = findById(userId);
/* 171 */     if (user != null) {
/* 172 */       user.setLoginCount(Long.valueOf(user.getLoginCount().longValue() + 1L));
/* 173 */       if (StringUtils.isNotBlank(ip)) {
/* 174 */         user.setLastLoginIp(ip);
/*     */       }
/* 176 */       if (loginTime != null) {
/* 177 */         user.setLastLoginTime(loginTime);
/*     */       }
/* 179 */       user.setSessionId(sessionId);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateLoginError(Long userId, String ip)
/*     */   {
/* 185 */     User user = findById(userId);
/* 186 */     Date now = new Timestamp(System.currentTimeMillis());
/* 187 */     WebsiteExt.ConfigLogin configLogin = this.websiteExtMng.getConfigLogin();
/* 188 */     int errorInterval = configLogin.getErrorInterval().intValue();
/* 189 */     Date errorTime = user.getErrTime();
/* 190 */     user.setErrIp(ip);
/* 191 */     if ((errorTime == null) || 
/* 193 */       (errorTime.getTime() + errorInterval * 60 * 1000 < now
/* 193 */       .getTime())) {
/* 194 */       user.setErrTime(now);
/* 195 */       user.setErrCount(Integer.valueOf(1));
/*     */     } else {
/* 197 */       user.setErrCount(Integer.valueOf(user.getErrCount().intValue() + 1));
/*     */     }
/*     */   }
/*     */ 
/*     */   public User register(String username, String password, String email, String ip, Date date)
/*     */   {
/* 203 */     User entity = new User();
/* 204 */     entity.setUsername(username);
/* 205 */     entity.setPassword(password);
/* 206 */     entity.setEmail(email);
/* 207 */     entity.setRegisterIp(ip);
/* 208 */     entity.setErrCount(Integer.valueOf(0));
/* 209 */     if (date != null) {
/* 210 */       entity.setCreateTime(date);
/*     */     }
/* 212 */     return save(entity);
/*     */   }
/*     */ 
/*     */   public User register(String username, String password, String email, String ip)
/*     */   {
/* 217 */     return register(username, password, email, ip, new Date());
/*     */   }
/*     */ 
/*     */   public Pagination getPage(int pageNo, int pageSize)
/*     */   {
/* 222 */     return this.dao.getPage(pageNo, pageSize);
/*     */   }
/*     */ 
/*     */   public User findById(Long id)
/*     */   {
/* 227 */     return this.dao.findById(id);
/*     */   }
/*     */ 
/*     */   public User save(User bean)
/*     */   {
/* 232 */     String password = this.pwdEncoder.encodePassword(bean.getPassword());
/* 233 */     bean.setPassword(password);
/* 234 */     bean.init();
/* 235 */     return this.dao.save(bean);
/*     */   }
/*     */ 
/*     */   public User updateUser(Long id, String password, String email)
/*     */   {
/* 240 */     User entity = findById(id);
/* 241 */     if (!StringUtils.isBlank(password)) {
/* 242 */       entity.setPassword(this.pwdEncoder.encodePassword(password));
/*     */     }
/* 244 */     if (!StringUtils.isBlank(email)) {
/* 245 */       entity.setEmail(email);
/*     */     }
/* 247 */     return entity;
/*     */   }
/*     */ 
/*     */   public User deleteById(Long id)
/*     */   {
/* 252 */     return this.dao.deleteById(id);
/*     */   }
/*     */ 
/*     */   public User[] deleteByIds(Long[] ids)
/*     */   {
/* 257 */     User[] beans = new User[ids.length];
/* 258 */     for (int i = 0; i < ids.length; i++) {
/* 259 */       beans[i] = deleteById(ids[i]);
/*     */     }
/* 261 */     return beans;
/*     */   }
/*     */ 
/*     */   public Integer errorRemaining(String username)
/*     */   {
/* 266 */     if (StringUtils.isBlank(username)) {
/* 267 */       return null;
/*     */     }
/* 269 */     User user = getByUsername(username);
/* 270 */     if (user == null) {
/* 271 */       return null;
/*     */     }
/* 273 */     Long now = Long.valueOf(System.currentTimeMillis());
/* 274 */     WebsiteExt.ConfigLogin configLogin = this.websiteExtMng.getConfigLogin();
/* 275 */     int maxErrorTimes = configLogin.getErrorTimes().intValue();
/* 276 */     int maxErrorInterval = configLogin.getErrorInterval().intValue() * 60 * 1000;
/* 277 */     Integer errCount = user.getErrCount();
/* 278 */     Date errTime = user.getErrTime();
/* 279 */     if ((errCount.intValue() <= 0) || (errTime == null) || (errTime.getTime() + maxErrorInterval < now.longValue())) {
/* 280 */       return Integer.valueOf(maxErrorTimes);
/*     */     }
/* 282 */     return Integer.valueOf(maxErrorTimes - errCount.intValue());
/*     */   }
/*     */ 
/*     */   @Autowired
/*     */   public void setPwdEncoder(PwdEncoder pwdEncoder)
/*     */   {
/* 319 */     this.pwdEncoder = pwdEncoder;
/*     */   }
/*     */   @Autowired
/*     */   public void setDao(UserDao dao) {
/* 324 */     this.dao = dao;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.impl.UserMngImpl
 * JD-Core Version:    0.6.0
 */