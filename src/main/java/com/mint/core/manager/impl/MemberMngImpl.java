package com.mint.core.manager.impl;

import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.common.security.UsernameNotFoundException;
import com.mint.common.security.userdetails.UserDetails;
import com.mint.common.security.userdetails.UserDetailsService;
import com.mint.core.dao.MemberDao;
import com.mint.core.entity.Member;
import com.mint.core.entity.User;
import com.mint.core.entity.Website;
import com.mint.core.manager.MemberMng;
import com.mint.core.manager.UserMng;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberMngImpl
        implements MemberMng, UserDetailsService {
    private UserMng userMng;
    private MemberDao dao;

    public UserDetails loadUser(Long id, String s)
            throws UsernameNotFoundException {
        Member member = findById(id);
        if (member == null) {
            throw new UsernameNotFoundException("member id not found '" + id + "'");
        }
        return member;
    }

    public Member getByUsername(Long webId, String username) {
        User entity = this.userMng.getByUsername(username);
        if (entity == null) {
            return null;
        }
        return getByUserId(webId, entity.getId());
    }

    public Member getByUsername(String username) {
        return this.dao.getByUsername(username);
    }

    public Member getByUserIdAndActive(String activationCode, Long userId) {
        return this.dao.getByUserIdAndActive(activationCode, userId);
    }

    public Member getByUserId(Long webId, Long userId) {
        return this.dao.getByUserId(webId, userId);
    }

    public Member register(String username, String password, String email, Boolean active, String activationCode, String ip, Boolean disabled, Website website) {
        Member member = new Member();
        Timestamp createtime = new Timestamp(System.currentTimeMillis());
        User user = this.userMng.register(username, password, email, ip, createtime);
        member.setCreateTime(createtime);
        member.setUser(user);
        member.setWebsite(website);
        member.setDisabled(disabled);
        member.setActive(active);
        member.setActivationCode(activationCode);
        return save(member);
    }

    public Member join(String username, Website website) {
        User entity = this.userMng.getByUsername(username);
        if (entity == null) {
            throw new IllegalStateException("User not found: " + username);
        }
        return join(entity, website);
    }

    public Member join(User user, Website website) {
        Member entity = getByUserId(website.getId(), user.getId());
        if (entity != null) {
            return entity;
        }
        Member member = new Member();
        member.setUser(user);
        member.setWebsite(website);
        return save(member);
    }

    public Pagination getPage(int pageNo, int pageSize) {
        return this.dao.getPage(pageNo, pageSize);
    }

    public Member findById(Long id) {
        return this.dao.findById(id);
    }

    public Member save(Member bean) {
        bean.init();
        return this.dao.save(bean);
    }

    public Member update(Member bean) {
        return this.dao.updateByUpdater(new Updater(bean));
    }

    public Member deleteById(Long id) {
        return this.dao.deleteById(id);
    }

    public Member[] deleteByIds(Long[] ids) {
        Member[] beans = new Member[ids.length];
        for (int i = 0; i < ids.length; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(MemberDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setUserMng(UserMng userMng) {
        this.userMng = userMng;
    }
}

