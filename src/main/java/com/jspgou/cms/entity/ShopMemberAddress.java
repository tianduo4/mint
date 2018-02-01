package com.jspgou.cms.entity;

import com.jspgou.cms.entity.base.BaseShopMemberAddress;
import org.apache.commons.lang.StringUtils;

public class ShopMemberAddress extends BaseShopMemberAddress
        implements AddressInterface {
    private static final long serialVersionUID = 1L;

    public String getMobile() {
        String mobile = "";
        if (StringUtils.isNotBlank(getPhone())) {
            if (StringUtils.isNotBlank(getAreaCode())) {
                mobile = getAreaCode() + "-";
            }
            mobile = mobile + getPhone();
            if (StringUtils.isNotBlank(getExtNumber())) {
                mobile = mobile + "-" + getExtNumber();
            }
            return mobile;
        }
        return mobile;
    }

    public ShopMemberAddress() {
    }

    public ShopMemberAddress(Long id) {
        super(id);
    }

    public ShopMemberAddress(Long id, ShopMember member, String province, String city, String country, Long provinceId, Long cityId, Long countryId, String username, String detailaddress, boolean isDefault) {
        super(id,
                member,
                province,
                city,
                country,
                provinceId,
                cityId,
                countryId,
                username,
                detailaddress,
                isDefault);
    }
}

