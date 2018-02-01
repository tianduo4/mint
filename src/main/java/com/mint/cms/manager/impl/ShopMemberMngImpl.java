package com.mint.cms.manager.impl;

import com.mint.cms.dao.ShopMemberDao;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.entity.ShopMemberGroup;
import com.mint.cms.entity.base.BaseShopMember;
import com.mint.cms.manager.CartMng;
import com.mint.cms.manager.CollectMng;
import com.mint.cms.manager.ConsultMng;
import com.mint.cms.manager.CouponMng;
import com.mint.cms.manager.DiscussMng;
import com.mint.cms.manager.GiftExchangeMng;
import com.mint.cms.manager.ShopDictionaryMng;
import com.mint.cms.manager.ShopMemberAddressMng;
import com.mint.cms.manager.ShopMemberGroupMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.cms.manager.ShopMoneyMng;
import com.mint.cms.manager.ShopScoreMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;
import com.mint.core.entity.Member;
import com.mint.core.entity.User;
import com.mint.core.entity.Website;
import com.mint.core.manager.AdminMng;
import com.mint.core.manager.MemberMng;
import com.mint.core.manager.UserMng;
import com.mint.core.manager.WebsiteMng;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShopMemberMngImpl
        implements ShopMemberMng {

    @Autowired
    private CartMng cartMng;

    @Autowired
    private UserMng userMng;

    @Autowired
    private ShopMemberDao dao;

    @Autowired
    private MemberMng memberMng;

    @Autowired
    private WebsiteMng websiteMng;

    @Autowired
    private ShopDictionaryMng shopDictionaryMng;

    @Autowired
    private ShopMemberGroupMng shopMemberGroupMng;

    @Autowired
    private ShopMemberAddressMng shopMemberAddressMng;

    @Autowired
    private CollectMng collectMng;

    @Autowired
    private ConsultMng consultMng;

    @Autowired
    private DiscussMng discussMng;

    @Autowired
    private GiftExchangeMng giftExchangeMng;

    @Autowired
    private ShopMoneyMng shopMoneyMng;

    @Autowired
    private CouponMng couponMng;

    @Autowired
    private ShopScoreMng shopScoreMng;

    @Autowired
    private AdminMng adminMng;

    public ShopMember getByUserId(Long webId, Long userId) {
        Member coreMember = this.memberMng.getByUserId(webId, userId);
        if (coreMember != null) {
            return findById(coreMember.getId());
        }
        return null;
    }

    public ShopMember getByUsername(Long webId, String username) {
        Member coreMember = this.memberMng.getByUsername(webId, username);
        if (coreMember == null) {
            return null;
        }
        return findById(coreMember.getId());
    }

    public ShopMember register(String username, String password, String email, Boolean active, String activationCode, String ip, Boolean disabled, Long webId, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, ShopMember member) {
        Website web = this.websiteMng.findById(webId);
        if (member == null) {
            member = new ShopMember();
        }
        Member coreMember = this.memberMng.register(username, password, email, active, activationCode, ip,
                disabled, web);
        member.setMember(coreMember);
        member.setWebsite(web);
        member.setFreezeScore(Integer.valueOf(0));
        if (groupId != null) {
            member.setGroup(this.shopMemberGroupMng.findById(groupId));
        }

        if (userDegreeId != null) {
            member.setUserDegree(this.shopDictionaryMng.findById(userDegreeId));
        }
        if (degreeId != null) {
            member.setDegree(this.shopDictionaryMng.findById(degreeId));
        }
        if (incomeDescId != null) {
            member.setIncomeDesc(this.shopDictionaryMng.findById(incomeDescId));
        }
        if (workSeniorityId != null) {
            member.setWorkSeniority(this.shopDictionaryMng.findById(workSeniorityId));
        }
        if (familyMembersId != null) {
            member.setFamilyMembers(this.shopDictionaryMng.findById(familyMembersId));
        }
        return save(member);
    }

    public ShopMember join(String username, Long webId, Long groupId) {
        Website web = this.websiteMng.findById(webId);
        Member coreMember = this.memberMng.join(username, web);
        ShopMember member = new ShopMember();
        member.setMember(coreMember);
        member.setWebsite(web);
        member.setGroup(this.shopMemberGroupMng.findById(groupId));
        return save(member);
    }

    public ShopMember join(Long userId, Long webId, ShopMemberGroup group) {
        User user = this.userMng.findById(userId);
        return join(user, webId, group);
    }

    public ShopMember join(User user, Long webId, ShopMemberGroup group) {
        Website web = this.websiteMng.findById(webId);
        Member coreMember = this.memberMng.join(user, web);
        ShopMember member = new ShopMember();
        member.setMember(coreMember);
        member.setWebsite(web);
        member.setGroup(group);
        return save(member);
    }

    public ShopMember register(String username, String password, String email, Boolean active, String activationCode, String ip, Boolean disabled, Long webId, Long groupId) {
        return register(username, password, email, active, activationCode, ip, disabled, webId,
                groupId, null, null, null, null, null, new ShopMember());
    }

    @Transactional(readOnly = true)
    public Pagination getPage(Long webId, int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(webId, pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public Pagination getPage(Long webId, int pageNo, int pageSize, String username) {
        Pagination page = this.dao.getPage(webId, pageNo, pageSize, username);
        return page;
    }

    @Transactional(readOnly = true)
    public ShopMember findById(Long id) {
        ShopMember entity = this.dao.findById(id);
        return entity;
    }

    public ShopMember save(ShopMember bean) {
        bean.init();
        if (bean.getGroup() == null) {
            bean.setGroup(this.shopMemberGroupMng.findGroupByScore(bean.getWebsite()
                    .getId(), bean.getScore().intValue()));
        }
        this.dao.save(bean);
        return bean;
    }

    public ShopMember update(ShopMember bean, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId, String password, String email, Boolean disabled) {
        Updater updater = new Updater(bean);
        ShopMember entity = this.dao.updateByUpdater(updater);

        this.userMng.updateUser(entity.getMember().getUser().getId(), password,
                email);

        if (disabled != null) {
            entity.getMember().setDisabled(disabled);
        }
        if (groupId != null) {
            entity.setGroup(this.shopMemberGroupMng.findById(groupId));
        }

        if (userDegreeId != null) {
            entity.setUserDegree(this.shopDictionaryMng.findById(userDegreeId));
        }
        if (degreeId != null) {
            entity.setDegree(this.shopDictionaryMng.findById(degreeId));
        }
        if (incomeDescId != null) {
            entity.setIncomeDesc(this.shopDictionaryMng.findById(incomeDescId));
        }
        if (workSeniorityId != null) {
            entity.setWorkSeniority(this.shopDictionaryMng.findById(workSeniorityId));
        }
        if (familyMembersId != null) {
            entity.setFamilyMembers(this.shopDictionaryMng.findById(familyMembersId));
        }

        this.dao.updateByUpdater(updater);

        entity.setGroup(this.shopMemberGroupMng.findGroupByScore(entity.getWebsite()
                .getId(), entity.getScore().intValue()));
        return entity;
    }

    public ShopMember update(ShopMember bean) {
        Updater updater = new Updater(bean);
        this.dao.updateByUpdater(updater);
        return bean;
    }

    public ShopMember updateScore(ShopMember bean, Integer score) {
        Integer scoreDb = this.dao.findById(bean.getId()).getScore();
        Integer scoreTotal = Integer.valueOf(scoreDb.intValue() + score.intValue());
        bean.setScore(scoreTotal);
        bean.setGroup(this.shopMemberGroupMng.findGroupByScore(bean.getWebsite()
                .getId(), scoreTotal.intValue()));
        Updater updater = new Updater(bean);
        this.dao.updateByUpdater(updater);
        return bean;
    }

    public ShopMember update(ShopMember bean, Long groupId, Long userDegreeId, Long degreeId, Long incomeDescId, Long workSeniorityId, Long familyMembersId) {
        ShopMember entity = findById(bean.getId());

        if (userDegreeId != null) {
            entity.setUserDegree(this.shopDictionaryMng.findById(userDegreeId));
        }
        if (degreeId != null) {
            entity.setDegree(this.shopDictionaryMng.findById(degreeId));
        }
        if (incomeDescId != null) {
            entity.setIncomeDesc(this.shopDictionaryMng.findById(incomeDescId));
        }
        if (workSeniorityId != null) {
            entity.setWorkSeniority(this.shopDictionaryMng.findById(workSeniorityId));
        }
        if (familyMembersId != null) {
            entity.setFamilyMembers(this.shopDictionaryMng.findById(familyMembersId));
        }
        Updater updater = new Updater(bean);
        updater.exclude(BaseShopMember.PROP_SCORE);
        this.dao.updateByUpdater(updater);
        return entity;
    }

    public ShopMember deleteById(Long id) {
        this.shopMemberAddressMng.deleteByMemberId(id);

        this.cartMng.deleteById(id);

        this.collectMng.deleteByMemberId(id);

        this.consultMng.deleteByMemberId(id);

        this.discussMng.deleteByMemberId(id);

        this.shopScoreMng.deleteByMemberId(id);

        this.giftExchangeMng.deleteByMemberId(id);

        this.couponMng.deleteByMemberId(id);

        this.shopMoneyMng.deleteByMemberId(id);

        ShopMember bean = this.dao.deleteById(id);

        if (this.adminMng.getByUsername(bean.getUsername()) == null) {
            this.userMng.deleteById(bean.getMember().getUser().getId());
        }
        return bean;
    }

    public ShopMember[] deleteByIds(Long[] ids) {
        ShopMember[] beans = new ShopMember[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public ShopMember findUsername(String username) {
        return this.dao.findUsername(username);
    }

    @Transactional(readOnly = true)
    public ShopMember findByUsername(String username) {
        ShopMember entity = this.dao.findByUsername(username);
        return entity;
    }

    public List<ShopMember> getList(String realName, Long groupId) {
        return this.dao.getList(realName, groupId);
    }

    public boolean usernameNotExist(String username) {
        return this.dao.countByUsername(username) <= 0;
    }

    public Long getMemberCount(Long webId) {
        return this.dao.getMemberCount(webId);
    }
}

