package com.mint.cms.entity.base;

import com.mint.cms.entity.Product;
import com.mint.cms.entity.ShopDictionary;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.entity.ShopMemberGroup;
import com.mint.core.entity.Member;
import com.mint.core.entity.Website;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public abstract class BaseShopMember
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "ShopMember";
    public static String PROP_STREET = "street";
    public static String PROP_MEMBER = "member";
    public static String PROP_WEBSITE = "website";
    public static String PROP_FAX = "fax";
    public static String PROP_AVATAR = "avatar";
    public static String PROP_REAL_NAME = "realName";
    public static String PROP_CITY = "city";
    public static String PROP_GROUP = "group";
    public static String PROP_BIRTHDAY = "birthday";
    public static String PROP_STATE = "state";
    public static String PROP_DISTRICT = "district";
    public static String PROP_GENDER = "gender";
    public static String PROP_SUBURB = "suburb";
    public static String PROP_MOBILE = "mobile";
    public static String PROP_FIRSTNAME = "firstname";
    public static String PROP_LASTNAME = "lastname";
    public static String PROP_ID = "id";
    public static String PROP_ZIP = "zip";
    public static String PROP_COMPANY = "company";
    public static String PROP_SCORE = "score";
    public static String PROP_TEL = "tel";
    public static String PROP_WECHAT_OPPENID = "wechatOppenid";

    private int hashCode = -2147483648;
    private Long id;
    private String realName;
    private Boolean gender;
    private Integer score;
    private Integer freezeScore;
    private Double money;
    private Date birthday;
    private String tel;
    private String mobile;
    private String avatar;
    private Boolean marriage;
    private String company;
    private Boolean isCar;
    private String position;
    private String address;
    private String schoolTag;
    private Date schoolTagDate;
    private String favoriteBrand;
    private String favoriteStar;
    private String favoriteMovie;
    private String favoritePersonage;
    private String wechatOppenid;
    private Member member;
    private Website website;
    private ShopMemberGroup group;
    private ShopDictionary userDegree;
    private ShopDictionary familyMembers;
    private ShopDictionary incomeDesc;
    private ShopDictionary workSeniority;
    private ShopDictionary degree;
    private Set<Product> favorites;

    public BaseShopMember() {
        initialize();
    }

    public BaseShopMember(Long id) {
        setId(id);
        initialize();
    }

    public BaseShopMember(Long id, Website website, ShopMemberGroup group, Integer score) {
        setId(id);
        setWebsite(website);
        setGroup(group);
        setScore(score);
        initialize();
    }

    protected void initialize() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getWechatOppenid() {
        return this.wechatOppenid;
    }

    public void setWechatOppenid(String wechatOppenid) {
        this.wechatOppenid = wechatOppenid;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Boolean getGender() {
        return this.gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getFreezeScore() {
        return this.freezeScore;
    }

    public void setFreezeScore(Integer freezeScore) {
        this.freezeScore = freezeScore;
    }

    public Double getMoney() {
        return this.money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getMarriage() {
        return this.marriage;
    }

    public void setMarriage(Boolean marriage) {
        this.marriage = marriage;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getIsCar() {
        return this.isCar;
    }

    public void setIsCar(Boolean isCar) {
        this.isCar = isCar;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSchoolTag() {
        return this.schoolTag;
    }

    public void setSchoolTag(String schoolTag) {
        this.schoolTag = schoolTag;
    }

    public Date getSchoolTagDate() {
        return this.schoolTagDate;
    }

    public void setSchoolTagDate(Date schoolTagDate) {
        this.schoolTagDate = schoolTagDate;
    }

    public String getFavoriteBrand() {
        return this.favoriteBrand;
    }

    public void setFavoriteBrand(String favoriteBrand) {
        this.favoriteBrand = favoriteBrand;
    }

    public String getFavoriteStar() {
        return this.favoriteStar;
    }

    public void setFavoriteStar(String favoriteStar) {
        this.favoriteStar = favoriteStar;
    }

    public String getFavoriteMovie() {
        return this.favoriteMovie;
    }

    public void setFavoriteMovie(String favoriteMovie) {
        this.favoriteMovie = favoriteMovie;
    }

    public String getFavoritePersonage() {
        return this.favoritePersonage;
    }

    public void setFavoritePersonage(String favoritePersonage) {
        this.favoritePersonage = favoritePersonage;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Website getWebsite() {
        return this.website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public ShopMemberGroup getGroup() {
        return this.group;
    }

    public void setGroup(ShopMemberGroup group) {
        this.group = group;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getHashCode() {
        return this.hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    public ShopDictionary getUserDegree() {
        return this.userDegree;
    }

    public void setUserDegree(ShopDictionary userDegree) {
        this.userDegree = userDegree;
    }

    public ShopDictionary getFamilyMembers() {
        return this.familyMembers;
    }

    public void setFamilyMembers(ShopDictionary familyMembers) {
        this.familyMembers = familyMembers;
    }

    public ShopDictionary getIncomeDesc() {
        return this.incomeDesc;
    }

    public void setIncomeDesc(ShopDictionary incomeDesc) {
        this.incomeDesc = incomeDesc;
    }

    public ShopDictionary getWorkSeniority() {
        return this.workSeniority;
    }

    public void setWorkSeniority(ShopDictionary workSeniority) {
        this.workSeniority = workSeniority;
    }

    public ShopDictionary getDegree() {
        return this.degree;
    }

    public void setDegree(ShopDictionary degree) {
        this.degree = degree;
    }

    public Set<Product> getFavorites() {
        return this.favorites;
    }

    public void setFavorites(Set<Product> favorites) {
        this.favorites = favorites;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopMember)) return false;

        ShopMember shopMember = (ShopMember) obj;
        if ((getId() == null) || (shopMember.getId() == null)) return false;
        return getId().equals(shopMember.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (getId() == null) return super.hashCode();

            String hashStr = getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

