package com.mint.cms.entity;

import com.mint.cms.api.CommonUtils;
import com.mint.cms.entity.base.BaseShopMember;
import com.mint.common.util.DateUtils;
import com.mint.core.entity.Website;

import java.util.Date;

import net.sf.json.JSONObject;

public class ShopMember extends BaseShopMember {
    private static final long serialVersionUID = 1L;

    public ShopMember() {
    }

    public ShopMember(Long id) {
        super(id);
    }

    public ShopMember(Long id, Website website, ShopMemberGroup group, Integer score) {
        super(id,
                website,
                group,
                score);
    }

    public void init() {
        if (getScore() == null)
            setScore(Integer.valueOf(0));
    }

    public Date getCreateTime() {
        return getMember() != null ? getMember().getCreateTime() : null;
    }

    public Boolean getDisabled() {
        return getMember() != null ? getMember().getDisabled() : null;
    }

    public String getUsername() {
        return getMember() != null ? getMember().getUsername() : "";
    }

    public String getEmail() {
        return getMember() != null ? getMember().getEmail() : "";
    }

    public String getPassword() {
        return getMember() != null ? getMember().getPassword() : "";
    }

    public Long getLoginCount() {
        return Long.valueOf(getMember() != null ? getMember().getLoginCount().longValue() : 0L);
    }

    public String getRegisterIp() {
        return getMember() != null ? getMember().getRegisterIp() : "";
    }

    public Date getLastLoginTime() {
        return getMember() != null ? getMember().getLastLoginTime() : null;
    }

    public String getLastLoginIp() {
        return getMember() != null ? getMember().getLastLoginIp() : "";
    }

    public Date getCurrentLoginTime() {
        return getMember() != null ? getMember().getCurrentLoginTime() : null;
    }

    public String getCurrentLoginIp() {
        return getMember() != null ? getMember().getCurrentLoginIp() : "";
    }

    public JSONObject converToJson() {
        JSONObject json = new JSONObject();
        json.put("id", CommonUtils.parseId(getId()));
        json.put("username", getMember() != null ? CommonUtils.parseStr(getMember().getUsername()) : "");
        json.put("email", (getMember() != null) && (getMember().getUser() != null) ? CommonUtils.parseStr(getMember().getUser().getEmail()) : "");
        json.put("groupId", getGroup() == null ? "" : CommonUtils.parseLong(getGroup().getId()));
        json.put("groupName", getGroup() == null ? "" : CommonUtils.parseStr(getGroup().getName()));
        json.put("score", Integer.valueOf(CommonUtils.parseInteger(getScore())));
        json.put("realName", CommonUtils.parseStr(getRealName()));
        json.put("gender", CommonUtils.parseBoolean(getGender()));
        json.put("birthday", CommonUtils.parseDate(getBirthday(), DateUtils.COMMON_FORMAT_STR));
        json.put("marriage", CommonUtils.parseBoolean(getMarriage()));
        json.put("mobile", CommonUtils.parseStr(getMobile()));
        json.put("tel", CommonUtils.parseStr(getTel()));
        json.put("schoolTag", CommonUtils.parseStr(getSchoolTag()));
        json.put("schoolTagDate", CommonUtils.parseDate(getSchoolTagDate(), DateUtils.COMMON_FORMAT_SHORT));
        json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
        json.put("isCar", CommonUtils.parseBoolean(getIsCar()));
        json.put("position", CommonUtils.parseStr(getPosition()));
        json.put("userDegreeId", getUserDegree() == null ? "" : CommonUtils.parseLong(getUserDegree().getId()));
        json.put("userDegreeName", getUserDegree() == null ? "" : CommonUtils.parseStr(getUserDegree().getName()));
        json.put("degreeId", getDegree() == null ? "" : CommonUtils.parseLong(getDegree().getId()));
        json.put("degreeName", getDegree() == null ? "" : CommonUtils.parseStr(getDegree().getName()));
        json.put("incomeDescId", getIncomeDesc() == null ? "" : CommonUtils.parseLong(getIncomeDesc().getId()));
        json.put("incomeDescName", getIncomeDesc() == null ? "" : CommonUtils.parseStr(getIncomeDesc().getName()));
        json.put("workSeniorityId", getWorkSeniority() == null ? "" : CommonUtils.parseLong(getWorkSeniority().getId()));
        json.put("workSeniorityName", getWorkSeniority() == null ? "" : CommonUtils.parseStr(getWorkSeniority().getName()));
        json.put("familyMembersId", getFamilyMembers() == null ? "" : CommonUtils.parseLong(getFamilyMembers().getId()));
        json.put("familyMembersName", getFamilyMembers() == null ? "" : CommonUtils.parseStr(getFamilyMembers().getName()));
        json.put("avatar", CommonUtils.parseStr(getAvatar()));
        json.put("company", CommonUtils.parseStr(getCompany()));
        json.put("address", CommonUtils.parseStr(getAddress()));
        json.put("favoriteBrand", CommonUtils.parseStr(getFavoriteBrand()));
        json.put("favoriteStar", CommonUtils.parseStr(getFavoriteStar()));
        json.put("favoriteMovie", CommonUtils.parseStr(getFavoriteMovie()));
        json.put("loginTime", (getMember() != null) && (getMember().getUser() != null) ? CommonUtils.parseDate(getMember().getUser().getCurrentLoginTime(), DateUtils.COMMON_FORMAT_STR) : "");
        return json;
    }
}

