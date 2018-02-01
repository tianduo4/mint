package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.entity.ShopMemberAddress;

import java.io.Serializable;

public abstract class BaseShopMemberAddress
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String REF = "ShopMemberAddress";
    public static String PROP_PHONE = "phone";
    public static String PROP_MEMBER = "member";
    public static String PROP_IS_DEFAULT = "isDefault";
    public static String PROP_PROVINCE = "province";
    public static String PROP_COUNTRY = "country";
    public static String PROP_CITY = "city";
    public static String PROP_PROVINCEID = "provinceId";
    public static String PROP_COUNTRYID = "countryId";
    public static String PROP_CITYID = "cityId";
    public static String PROP_AREA_CODE = "areaCode";
    public static String PROP_DETAILADDRESS = "detailaddress";
    public static String PROP_POST_CODE = "postCode";
    public static String PROP_USERNAME = "username";
    public static String PROP_GENDER = "gender";
    public static String PROP_EXT_NUMBER = "extNumber";
    public static String PROP_ID = "id";
    public static String PROP_TEL = "tel";

    private int hashCode = -2147483648;
    private Long id;
    private String username;
    private boolean gender;
    private String detailaddress;
    private String postCode;
    private String tel;
    private String areaCode;
    private String phone;
    private String extNumber;
    private boolean isDefault;
    private ShopMember member;
    private String province;
    private String city;
    private String country;
    private Long provinceId;
    private Long cityId;
    private Long countryId;

    public BaseShopMemberAddress() {
        initialize();
    }

    public BaseShopMemberAddress(Long id) {
        setId(id);
        initialize();
    }

    public BaseShopMemberAddress(Long id, ShopMember member, String province, String city, String country, Long provinceId, Long cityId, Long countryId, String username, String detailaddress, boolean isDefault) {
        setId(id);
        setMember(member);
        setProvince(province);
        setCity(city);
        setCountry(country);
        setProvinceId(provinceId);
        setCityId(cityId);
        setCountryId(countryId);
        setUsername(username);
        setDetailaddress(detailaddress);
        setIsDefault(isDefault);
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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getGender() {
        return this.gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getDetailaddress() {
        return this.detailaddress;
    }

    public void setDetailaddress(String detailaddress) {
        this.detailaddress = detailaddress;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExtNumber() {
        return this.extNumber;
    }

    public void setExtNumber(String extNumber) {
        this.extNumber = extNumber;
    }

    public boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public ShopMember getMember() {
        return this.member;
    }

    public void setMember(ShopMember member) {
        this.member = member;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return this.cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountryId() {
        return this.countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ShopMemberAddress)) return false;

        ShopMemberAddress shopMemberAddress = (ShopMemberAddress) obj;
        if ((getId() == null) || (shopMemberAddress.getId() == null)) return false;
        return getId().equals(shopMemberAddress.getId());
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

