package com.mint.core.manager.impl;

import com.mint.common.page.Pagination;
import com.mint.common.security.encoder.PwdEncoder;
import com.mint.core.dao.UserDao;
import com.mint.core.entity.EmailSender;
import com.mint.core.entity.MessageTemplate;
import com.mint.core.entity.User;
import com.mint.core.entity.WebsiteExt;
import com.mint.core.manager.UserMng;
import com.mint.core.manager.WebsiteExtMng;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserMngImpl
        implements UserMng {

    @Autowired
    private WebsiteExtMng websiteExtMng;
    private PwdEncoder pwdEncoder;
    private UserDao dao;

    public User passwordForgotten(Long id, String base, EmailSender email, MessageTemplate tpl) {
        User entity = findById(id);
        String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
        entity.setResetKey(uuid);
        String resetPwd = RandomStringUtils.randomNumeric(10);
        entity.setResetPwd(resetPwd);
        senderEmail(entity.getId(), entity.getUsername(), base, entity.getEmail(), entity
                .getResetKey(), entity.getResetPwd(), email, tpl);
        return entity;
    }

    public void senderActiveEmail(String userName, String base, String receiverEmail, String activationCode, EmailSender email, MessageTemplate tpl)
            throws MailException {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(email.getHost());
        sender.setUsername(email.getUsername());
        sender.setPassword(email.getPassword());
//     sender.send(new MimeMessagePreparator(email, tpl, receiverEmail, userName, activationCode, base)
//     {
//       public void prepare(MimeMessage mimemessage) throws MessagingException, UnsupportedEncodingException
//       {
//         MimeMessageHelper msg = new MimeMessageHelper(mimemessage,
//           false, this.val$email.getEncoding());
//         msg.setSubject(this.val$tpl.getActiveTitle());
//         msg.setTo(this.val$receiverEmail);
//         msg.setFrom(this.val$email.getUsername(), this.val$email.getPersonal());
//         String text = this.val$tpl.getActiveTxt();
//         text = StringUtils.replace(text, "${userName}", this.val$userName);
//         text = StringUtils.replace(text, "${activationCode}", this.val$activationCode);
//         text = StringUtils.replace(text, "${base}", this.val$base);
//         msg.setText(text, true);
//       }
//     });  //TODO
    }

    public void senderEmail(Long uid, String username, String base, String to, String resetKey, String resetPwd, EmailSender email, MessageTemplate tpl)
            throws MailException {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(email.getHost());
        sender.setUsername(email.getUsername());
        sender.setPassword(email.getPassword());
//     sender.send(new MimeMessagePreparator(email, tpl, to, uid, username, resetKey, resetPwd, base)
//     {
//       public void prepare(MimeMessage mimemessage) throws MessagingException, UnsupportedEncodingException
//       {
//         MimeMessageHelper msg = new MimeMessageHelper(mimemessage,
//           false, this.val$email.getEncoding());
//         msg.setSubject(this.val$tpl.getSubject());
//         msg.setTo(this.val$to);
//         msg.setFrom(this.val$email.getUsername(), this.val$email.getPersonal());
//         String text = this.val$tpl.getText();
//         text = StringUtils.replace(text, "${uid}", String.valueOf(this.val$uid));
//         text = StringUtils.replace(text, "${username}", this.val$username);
//         text = StringUtils.replace(text, "${activationCode}", this.val$resetKey);
//         text = StringUtils.replace(text, "${resetPwd}", this.val$resetPwd);
//         text = StringUtils.replace(text, "${base}", this.val$base);
//         msg.setText(text, true);
//       }
//     });  //TODO  注释
    }

    public User resetPassword(Long userId) {
        User entity = findById(userId);
        entity.setPassword(this.pwdEncoder.encodePassword(entity.getResetPwd()));
        entity.setResetKey(null);
        entity.setResetPwd(null);
        return entity;
    }

    public boolean isPasswordValid(Long id, String password) {
        User entity = findById(id);
        return this.pwdEncoder.isPasswordValid(entity.getPassword(), password);
    }

    public boolean usernameExist(String username) {
        return getByUsername(username) != null;
    }

    public boolean emailExist(String email) {
        return getByEmail(email) != null;
    }

    public User getByUsername(String username) {
        return this.dao.getByUsername(username);
    }

    public User getByEmail(String email) {
        return this.dao.getByEmail(email);
    }

    public void updateLoginInfo(Long userId, String ip) {
        User entity = findById(userId);
        entity.setLoginCount(Long.valueOf(entity.getLoginCount().longValue() + 1L));
        String s1 = entity.getCurrentLoginIp();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        if (StringUtils.isBlank(s1)) {
            entity.setLastLoginIp(ip);
            entity.setLastLoginTime(time);
        } else {
            entity.setLastLoginIp(entity.getCurrentLoginIp());
            entity.setLastLoginTime(entity.getCurrentLoginTime());
        }
        entity.setCurrentLoginIp(ip);
        entity.setCurrentLoginTime(time);
    }

    public void updateLoginSuccess(Long userId, String ip) {
        User user = findById(userId);
        Date now = new Timestamp(System.currentTimeMillis());

        user.setLoginCount(Long.valueOf(user.getLoginCount().longValue() + 1L));
        user.setLastLoginIp(ip);
        user.setLastLoginTime(now);

        user.setErrCount(Integer.valueOf(0));
        user.setErrTime(null);
        user.setErrIp(null);
    }

    public void updateLoginInfo(Long userId, String ip, Date loginTime, String sessionId) {
        User user = findById(userId);
        if (user != null) {
            user.setLoginCount(Long.valueOf(user.getLoginCount().longValue() + 1L));
            if (StringUtils.isNotBlank(ip)) {
                user.setLastLoginIp(ip);
            }
            if (loginTime != null) {
                user.setLastLoginTime(loginTime);
            }
            user.setSessionId(sessionId);
        }
    }

    public void updateLoginError(Long userId, String ip) {
        User user = findById(userId);
        Date now = new Timestamp(System.currentTimeMillis());
        WebsiteExt.ConfigLogin configLogin = this.websiteExtMng.getConfigLogin();
        int errorInterval = configLogin.getErrorInterval().intValue();
        Date errorTime = user.getErrTime();
        user.setErrIp(ip);
        if ((errorTime == null) ||
                (errorTime.getTime() + errorInterval * 60 * 1000 < now
                        .getTime())) {
            user.setErrTime(now);
            user.setErrCount(Integer.valueOf(1));
        } else {
            user.setErrCount(Integer.valueOf(user.getErrCount().intValue() + 1));
        }
    }

    public User register(String username, String password, String email, String ip, Date date) {
        User entity = new User();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setEmail(email);
        entity.setRegisterIp(ip);
        entity.setErrCount(Integer.valueOf(0));
        if (date != null) {
            entity.setCreateTime(date);
        }
        return save(entity);
    }

    public User register(String username, String password, String email, String ip) {
        return register(username, password, email, ip, new Date());
    }

    public Pagination getPage(int pageNo, int pageSize) {
        return this.dao.getPage(pageNo, pageSize);
    }

    public User findById(Long id) {
        return this.dao.findById(id);
    }

    public User save(User bean) {
        String password = this.pwdEncoder.encodePassword(bean.getPassword());
        bean.setPassword(password);
        bean.init();
        return this.dao.save(bean);
    }

    public User updateUser(Long id, String password, String email) {
        User entity = findById(id);
        if (!StringUtils.isBlank(password)) {
            entity.setPassword(this.pwdEncoder.encodePassword(password));
        }
        if (!StringUtils.isBlank(email)) {
            entity.setEmail(email);
        }
        return entity;
    }

    public User deleteById(Long id) {
        return this.dao.deleteById(id);
    }

    public User[] deleteByIds(Long[] ids) {
        User[] beans = new User[ids.length];
        for (int i = 0; i < ids.length; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public Integer errorRemaining(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        User user = getByUsername(username);
        if (user == null) {
            return null;
        }
        Long now = Long.valueOf(System.currentTimeMillis());
        WebsiteExt.ConfigLogin configLogin = this.websiteExtMng.getConfigLogin();
        int maxErrorTimes = configLogin.getErrorTimes().intValue();
        int maxErrorInterval = configLogin.getErrorInterval().intValue() * 60 * 1000;
        Integer errCount = user.getErrCount();
        Date errTime = user.getErrTime();
        if ((errCount.intValue() <= 0) || (errTime == null) || (errTime.getTime() + maxErrorInterval < now.longValue())) {
            return Integer.valueOf(maxErrorTimes);
        }
        return Integer.valueOf(maxErrorTimes - errCount.intValue());
    }

    @Autowired
    public void setPwdEncoder(PwdEncoder pwdEncoder) {
        this.pwdEncoder = pwdEncoder;
    }

    @Autowired
    public void setDao(UserDao dao) {
        this.dao = dao;
    }
}

